package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.fleetopgroup.persistence.bl.BusLocationDtailsBL;
import org.fleetopgroup.persistence.dao.BusLocationRepositary;
import org.fleetopgroup.persistence.dto.BusLocationDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.BusLocation;
import org.fleetopgroup.persistence.report.dao.BusLocationDao;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class BusLocationService implements IBusLocationService {

	@Autowired private BusLocationRepositary 	busLocationRepositary;
	@Autowired private BusLocationDao			busLocationDao;

	public ValueObject insertBusLocationDto(ValueObject vo) throws Exception {
		ValueObject 		valuesObject 		= new ValueObject();
		BusLocationDtailsBL	busLocationDtailsBL	= null;
		BusLocation			busLocation			= null;
		boolean				checkVehicleStatus	= false;

		try {
			CustomUserDetails userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			busLocationDtailsBL	= new BusLocationDtailsBL();

			busLocation			= busLocationDtailsBL.busLocationDto(vo);
			busLocation.setCompanyId(userDetails.getCompany_id());
			
			checkVehicleStatus		= busLocationDao.checkVehicleStatus(busLocation);
			
			if(checkVehicleStatus) {
				valuesObject.put("message", "Vehicle is already Created ! Change status IN to OUT !");
				return valuesObject;
			}
			
			busLocationRepositary.save(busLocation);

			valuesObject.put("success", true);

			return valuesObject;
		} catch (Exception e) {
			throw e;
		}
	}

	public ValueObject getBusLocationReport(ValueObject valueObject) throws Exception {		
		ValueObject					valueOutObject 			= null;
		List<BusLocationDto> 		busLocationList			= null;	
		String						startDate				= null;
		String						endDate					= null;
		Map<String, List<BusLocationDto>>	dateWiseInMap		= null;
		Map<String, List<BusLocationDto>>	dateWiseOutMap		= null;
		
		try {
			CustomUserDetails userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(!valueObject.getString("startDate", "").equalsIgnoreCase("")) {
				startDate 			= DateTimeUtility.getSqlStrDateFromStrDate(valueObject.getString("startDate", ""), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				endDate				= DateTimeUtility.getSqlStrDateFromStrDate(valueObject.getString("endDate", ""), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			}

			if(startDate != null && endDate != null) {		
				String query = "AND bl.busBookingDate BETWEEN '"+ startDate + "' AND '" + endDate + "' ";
				busLocationList	= busLocationDao.getBusLocationReportList(query, userDetails.getCompany_id());
			}
			
			if(busLocationList != null && !busLocationList.isEmpty()) {
				dateWiseInMap	= busLocationList.stream().filter(e -> e.getBusOutDateStr() == null).collect(Collectors.groupingBy(BusLocationDto::getBusBookingDateStr));
				dateWiseOutMap	= busLocationList.stream().filter(e -> e.getBusOutDateStr() != null).collect(Collectors.groupingBy(BusLocationDto::getBusOutDateStr));
			}
			
			valueOutObject	= new ValueObject();
			
			valueOutObject.put("dateWiseInMap", dateWiseInMap);
			valueOutObject.put("dateWiseOutMap", dateWiseOutMap);

			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public ValueObject updateBuslocationOutTime(ValueObject valueObjectIn) throws Exception {
		ValueObject 		valuesObject 		= new ValueObject();
		Timestamp			busOutDateTime		= null;

		try {
			CustomUserDetails userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(!valueObjectIn.getString("busOutDateTime", "").equalsIgnoreCase(""))
				busOutDateTime 			= DateTimeUtility.getTimeStamp(valueObjectIn.getString("busOutDateTime", ""));
			
			busLocationRepositary.updateBusLocationOutTime(busOutDateTime, valueObjectIn.getLong("busLocationId",  0), Long.parseLong(userDetails.getCompany_id() + ""));

			valuesObject.put("success", true);

			return valuesObject;
		} catch (Exception e) {
			throw e;
		}
	}
}
