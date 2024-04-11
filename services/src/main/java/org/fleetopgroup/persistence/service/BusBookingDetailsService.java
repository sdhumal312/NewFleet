package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.BusBookingDetailsBL;
import org.fleetopgroup.persistence.dao.BusBookingDetailsRepository;
import org.fleetopgroup.persistence.dto.BusBookingDetailsDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTransferDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.model.BusBookingDetails;
import org.fleetopgroup.persistence.model.BusBookingSequenceCounter;
import org.fleetopgroup.persistence.model.CalenderEvent;
import org.fleetopgroup.persistence.model.ServiceReminderWorkOrderHistory;
import org.fleetopgroup.persistence.report.dao.IBusBookingDetailsDao;
import org.fleetopgroup.persistence.serviceImpl.IBusBookingDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IBusBookingSequenceService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BusBookingDetailsService implements IBusBookingDetailsService {

	@Autowired	IBusBookingSequenceService		busBookingSequenceService;
	@Autowired	BusBookingDetailsRepository		busBookingDetailsRepository;
	@Autowired	IBusBookingDetailsDao			busBookingDetailsDao;
	@Autowired	ICompanyConfigurationService	companyConfigurationService;
	
	BusBookingDetailsBL	bookingDetailsBL	= new BusBookingDetailsBL();
	
	
	
	SimpleDateFormat 						dateFormatAtt 		= new SimpleDateFormat("yyyy, MM, dd");
	SimpleDateFormat 						dateFormat 			= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat 						AttendanceYear 		= new SimpleDateFormat("yyyy");
	SimpleDateFormat 						AttendanceMonth 	= new SimpleDateFormat("MM");
	SimpleDateFormat 						AttendanceDay 		= new SimpleDateFormat("dd");
	SimpleDateFormat 						sqlDateFormat 		= new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public ValueObject getBusBookingDetails(ValueObject valueObject) throws Exception {

		CustomUserDetails				userDetails 				= null;
		Integer							pageNumber					= null;
		ValueObject						valOutObject				= null;
		try {
			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valOutObject		= new ValueObject();
			pageNumber			= valueObject.getInt("pageNumber",0);
			Page<BusBookingDetails> page = busBookingDetailsDao.getDeployment_Page_BusBookingDetails(pageNumber, userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valOutObject.put("deploymentLog", page);
			valOutObject.put("beginIndex", begin);
			valOutObject.put("endIndex", end);
			valOutObject.put("currentIndex", current);

			valOutObject.put("vendorLorryHireCount", page.getTotalElements());
			
			List<BusBookingDetailsDto> vendorLorryHireList = busBookingDetailsDao.getPageWiseBusBookingDetails(pageNumber, userDetails.getCompany_id());
			valOutObject.put("vendorLorryHireList", vendorLorryHireList);
			valOutObject.put("SelectPage", pageNumber);
			
			return valOutObject;
		} catch (NullPointerException e) {
			throw e;
		} finally {
			userDetails			= null;
			pageNumber			= null;
			valOutObject		= null;
		}
	
	}

	@Override
	public ValueObject saveBusBookingDetails(ValueObject valueObject) throws Exception {
		BusBookingSequenceCounter		bookingSequenceCounter		= null;
		BusBookingDetails				busBookingDetails			= null;
		try {
			bookingSequenceCounter	= busBookingSequenceService.findNextNumber(valueObject.getInt("companyId", 0));
			if(bookingSequenceCounter == null) {
				valueObject.put("sequenceNotFound", true);
				return valueObject;
			}
			valueObject.put("busBookingNumber", bookingSequenceCounter.getNextVal());
			busBookingDetails	= bookingDetailsBL.getBusBookingDetailsDto(valueObject);
			
			busBookingDetailsRepository.save(busBookingDetails);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public ValueObject deleteBusBookingDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails 				= null;
		ValueObject					valOutObject				= null;
		Long						busBookingDetailsId			= null;

		try {
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			busBookingDetailsId			= valueObject.getLong("busBookingDetailsId",0);
			
			busBookingDetailsRepository.deleteBusBookingDetails(busBookingDetailsId, userDetails.getCompany_id(), userDetails.getId(), new Date());
			
			valOutObject		= new ValueObject();
			valOutObject.put("busBookingDetailsId", busBookingDetailsId);
			return valOutObject;
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails 				= null;
			valOutObject				= null;
			busBookingDetailsId				= null;
		}
	}
	
	@Override
	public BusBookingDetailsDto getBusBookingDetails(Long busBookingDetailsId) throws Exception {
		
		return busBookingDetailsDao.getBusBookingDetails(busBookingDetailsId);
	}
	
	@Override
	@Transactional
	public void updateTripSheetNumber(Long tripSheetId, Long busBookingDetailsId) throws Exception {
		
		busBookingDetailsRepository.updateTripSheetNumber(tripSheetId, busBookingDetailsId);
	}
	
	@Override
	public BusBookingDetailsDto getBusBookingDetailsByTripSheetId(Long tripSheetId) throws Exception {
		
		return busBookingDetailsDao.getBusBookingDetailsByTripSheetId(tripSheetId);
	}
	
	@Override
	public ValueObject getShowBusBookingDetails(ValueObject valueObject) throws Exception {
		BusBookingDetailsDto		bookingDetailsDto	= null;
		HashMap<String, Object> 	configuration		= null;
		try {
			bookingDetailsDto	= getBusBookingDetails(valueObject.getLong("busBookingDetailsId", 0));
			configuration		= companyConfigurationService.getCompanyConfiguration(bookingDetailsDto.getCompanyId(), PropertyFileConstants.BUS_BOOKING_CONFIGURATION_CONFIG);
			valueObject.put("busBooking", bookingDetailsDto);
			valueObject.put("configuration", configuration);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			bookingDetailsDto	= null;
			configuration		= null;
		}
	}
	
	
	@Override
	public ValueObject getBusBookingCalendarDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails						userDetails				= null;
		BusBookingDetailsDto					busBookingDetailsDto	=null;
		try {
			
			
				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				busBookingDetailsDto = new BusBookingDetailsDto();
				busBookingDetailsDto.setCompanyId(userDetails.getCompany_id());
				busBookingDetailsDto.setFromDate(valueObject.getString("fromDate"));
				busBookingDetailsDto.setToDate(valueObject.getString("toDate"));
				busBookingDetailsDto.setUserId(userDetails.getId());
				valueObject.put("busbookinglist", getFinalBusBookingCalendarList(busBookingDetailsDao.getDateWiseCount(busBookingDetailsDto)));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getMonthlyVehicleWiseCount(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		BusBookingDetailsDto busBookingDetailsDto = null;
		try {
			
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			busBookingDetailsDto = new BusBookingDetailsDto();
			busBookingDetailsDto.setCompanyId(userDetails.getCompany_id());
			busBookingDetailsDto.setFromDate(valueObject.getString("fromDate"));
			busBookingDetailsDto.setToDate(valueObject.getString("toDate"));
			busBookingDetailsDto.setUserId(userDetails.getId());
			
			valueObject.put("vehicleTypeWiseCount", busBookingDetailsDao.getVehicleTypeWiseCount(busBookingDetailsDto));
			return valueObject;
		}catch(Exception e) {
			
			throw e;
		}
		
		
		
	}
	
	@Override
	public ValueObject getBusBookingListOftheDay(ValueObject valueObject) throws Exception {
		String 									selectedDate			= null; 
		Timestamp								selectedTimeStamp		= null;
		BusBookingDetailsDto					busBookingDetailsDto		= null;
		CustomUserDetails						userDetails				= null;
		try {
			selectedDate		= valueObject.getString("Date");
			selectedTimeStamp	= DateTimeUtility.getCalendarTimeFromTimestamp(DateTimeUtility.getDateTimeFromStr(selectedDate, DateTimeUtility.YYYY_MM_DD),0,0,0,0);
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			busBookingDetailsDto	= new BusBookingDetailsDto();
			busBookingDetailsDto.setFromDate(DateTimeUtility.getDateFromTimeStamp(selectedTimeStamp, DateTimeUtility.YYYY_MM_DD));
			busBookingDetailsDto.setToDate(DateTimeUtility.getDateFromTimeStamp(selectedTimeStamp, DateTimeUtility.YYYY_MM_DD)+InventoryTransferDto.DAY_END_TIME);
			busBookingDetailsDto.setCompanyId(userDetails.getCompany_id());
			busBookingDetailsDto.setUserId(userDetails.getId());
			
			valueObject.put("busbookinglist",busBookingDetailsDao.getBusBookingCalendarDetails(busBookingDetailsDto));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getVehicleTypeWiseBusBookingList(ValueObject valueObject) throws Exception{
		
		BusBookingDetailsDto busBookingDetailsDto=null;
		CustomUserDetails userDtails= null;
		
		try {
			
			userDtails=(CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			busBookingDetailsDto= new BusBookingDetailsDto();
			busBookingDetailsDto.setCompanyId(userDtails.getCompany_id());
			busBookingDetailsDto.setUserId(userDtails.getId());
			busBookingDetailsDto.setFromDate(valueObject.getString("fromDate"));
			busBookingDetailsDto.setToDate(valueObject.getString("toDate"));
			busBookingDetailsDto.setVehicleTypeId(valueObject.getLong("vehicleTypeId"));
			valueObject.put("busbookinglist",busBookingDetailsDao.getVehicleTypeWiseList(busBookingDetailsDto));
			return valueObject;
		}catch (Exception e) {
			throw e;
			
		}
	}
	
	
	private ArrayList<CalenderEvent> getFinalBusBookingCalendarList(List<BusBookingDetailsDto> list) throws Exception {
		CalenderEvent				calenderEvent		= null;
		ArrayList<CalenderEvent>    calenderEventList	= null;
		try {
			calenderEventList	= new ArrayList<>();
			if(list != null && list.size() > 0) {
				for (BusBookingDetailsDto busBookingDto : list) {
					calenderEvent	= new CalenderEvent();
					calenderEvent.setEventDay(AttendanceDay.format(busBookingDto.getTripStartDateTime()));//.getTime_servicethreshold_date()));
					calenderEvent.setEventMonth("" + (Integer.parseInt(AttendanceMonth.format(busBookingDto.getTripStartDateTime())) - 1));
					calenderEvent.setEventYear(AttendanceYear.format(busBookingDto.getTripStartDateTime()));
					calenderEvent.setEventTitle("Total booking:"+busBookingDto.getDateWiseCount());
					calenderEvent.setEventUrl(""+sqlDateFormat.format(busBookingDto.getTripStartDateTime()));
					calenderEvent.setEventColor("Black");
					calenderEvent.setEventDate(sqlDateFormat.format(busBookingDto.getTripStartDateTime()));
					calenderEvent.setCount(busBookingDto.getDateWiseCount());
					calenderEventList.add(calenderEvent);
				}
			}
			
			return calenderEventList;
		} catch (Exception e) {
			throw e;
		}
	}
	
}
