package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.fleetopgroup.constant.CalenderBreakDownConfigurationConstants;
import org.fleetopgroup.constant.IssuesConfigurationContant;
import org.fleetopgroup.constant.IssuesTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.VehicleCommentDto;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.model.CalenderEvent;
import org.fleetopgroup.persistence.report.dao.IServiceEntriesDao;
import org.fleetopgroup.persistence.report.dao.IssuesDao;
import org.fleetopgroup.persistence.report.dao.TripSheetDao;
import org.fleetopgroup.persistence.report.dao.VehicleCommentDao;
import org.fleetopgroup.persistence.report.dao.WorkOrdersDao;
import org.fleetopgroup.persistence.serviceImpl.IVehicleBreakDownCalenderService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VehicleBreakDownCalenderService implements IVehicleBreakDownCalenderService {
	
	@PersistenceContext
	EntityManager entityManager;

	@Autowired	CompanyConfigurationService	companyConfigurationService;
	@Autowired	IServiceEntriesDao			serviceEntriesDao;
	@Autowired	WorkOrdersDao				workOrdersDao;
	@Autowired	IssuesDao					issuesDao;
	@Autowired	VehicleCommentDao			vehicleCommentDao;
	@Autowired	TripSheetDao				tripSheetDao;

	SimpleDateFormat 						dateFormatAtt 		= new SimpleDateFormat("yyyy, MM, dd");
	SimpleDateFormat 						dateFormat 			= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat 						AttendanceYear 		= new SimpleDateFormat("yyyy");
	SimpleDateFormat 						AttendanceMonth 	= new SimpleDateFormat("MM");
	SimpleDateFormat 						AttendanceDay 		= new SimpleDateFormat("dd");
	ArrayList<CalenderEvent>    			calenderEventList	= null;
	List<Date> 								dateList			= null;
	SimpleDateFormat 						format	 			= new SimpleDateFormat("yyyy-MM-dd");
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject getVehicleBreakDownDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails				= null;
		HashMap<String, Object>   	configuration			= null;
		HashMap<String, Object>   	issueConfig				= null;
		Thread						serviceEntriesThread	= null;
		Thread						workOrderThread			= null;
		Thread						issuesThread			= null;
		Thread						vehicleCommentThread	= null;
		Thread						tripSheetThread			= null;
		ValueObject					valueOutObject;
		Boolean						showIssue				= null;

		try {
		//	valueObject1 		= new ValueObject();
			valueOutObject		= new ValueObject();
			calenderEventList	= new ArrayList<>();
			dateList			= new ArrayList<>();
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("CompanyId", userDetails.getCompany_id());

			configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CALENDER_BREAK_DOWN_CONFIGURATION_CONFIG);
			issueConfig 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			showIssue		= (boolean) issueConfig.getOrDefault(IssuesConfigurationContant.SHOW_BREAK_DOWN_DETAILS, false);
			
			if((boolean) configuration.getOrDefault(CalenderBreakDownConfigurationConstants.SHOW_VEHICLE_SERVICE_ENTRIES_BREAK_DOWN, true)) {
				serviceEntriesThread	= new Thread() {
					public void run() {
						List<ServiceEntriesDto> 	serviceEntriesList		= null;
						try {
							serviceEntriesList		= getServiceEntriesList(valueObject);
							valueOutObject.put("serviceEntriesList", serviceEntriesList);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				serviceEntriesThread.start();
			}

			if((boolean) configuration.getOrDefault(CalenderBreakDownConfigurationConstants.SHOW_VEHICLE_WORK_ORDERS_BREAK_DOWN, true)) {
				workOrderThread		= new Thread() {
					public void run() {
						List<WorkOrdersDto>			workOrdersList			= null;
						try {
							workOrdersList		= getWorkOrdersList(valueObject);
							valueOutObject.put("workOrdersList", workOrdersList);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				workOrderThread.start();
			}

			if(showIssue) {
				issuesThread = new Thread() {
					public void run() {
						List<IssuesDto>			issuesList					= null;
						try {
							
							issuesList = (List<IssuesDto>) getIssuesListDetails(valueObject).get("issuesList");
							valueOutObject.put("issuesList", issuesList);
						//	valueOutObject.put("issuesDetailsList", getIssuesListDetails(valueObject).get("issuesDetailsList"));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				issuesThread.start();
			}else {
				
				if((boolean) configuration.getOrDefault(CalenderBreakDownConfigurationConstants.SHOW_VEHICLE_ISSUES_BREAK_DOWN, true)) {
					issuesThread = new Thread() {
						public void run() {
							List<IssuesDto>					issuesList			= null;
							try {
								issuesList		= getIssuesList(valueObject);
								valueOutObject.put("issuesList", issuesList);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};
					issuesThread.start();
				}
			}
			
			


			if((boolean) configuration.getOrDefault(CalenderBreakDownConfigurationConstants.SHOW_VEHICLE_COMMENTS_BREAK_DOWN, true)) {
				vehicleCommentThread	= new Thread() {
					public void run() {
						List<VehicleCommentDto>		vehicleCommentList		= null;
						try {
							vehicleCommentList		= getVehicleCommentList(valueObject);
							valueOutObject.put("vehicleCommentList", vehicleCommentList);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				vehicleCommentThread.start();
			}
			
			if((boolean) configuration.getOrDefault(CalenderBreakDownConfigurationConstants.SHOW_VEHICLE_TRIP_SHEETS_BREAK_DOWN, true)) {
				tripSheetThread		= new Thread() {
					public void run() {
						ValueObject				object				= null;
						try {
							object	= getTripSheetList(valueObject);
							valueOutObject.put("noOftripDays", object.get("noOftripDays"));
							valueOutObject.put("noOfidleDays", object.get("noOfidleDays"));
							valueOutObject.put("tripSheetList", object.get("tripSheetList"));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				tripSheetThread.start();
			}

			if(serviceEntriesThread != null) {
				serviceEntriesThread.join();
			}

			if(workOrderThread != null) {
				workOrderThread.join();
			}

			if(issuesThread != null) {
				issuesThread.join();
			}

			if(vehicleCommentThread != null) {
				vehicleCommentThread.join();
			}

			if(tripSheetThread != null) {
				tripSheetThread.join();
			}

			valueOutObject.put("calenderEventList", calenderEventList);
			valueOutObject.put("vehicleId", valueObject.getInt("vehicleId"));
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails				= null;
			serviceEntriesThread	= null;
			workOrderThread			= null;
		}
	}

	private List<ServiceEntriesDto> getServiceEntriesList(ValueObject	valueObject) throws Exception {
		ServiceEntriesDto			serviceEntriesDto		= null;
		List<ServiceEntriesDto> 	serviceEntriesList		= null;
		CalenderEvent				calenderEvent			= null;

		try {
			serviceEntriesDto	= new ServiceEntriesDto();
			serviceEntriesDto.setFromDate(valueObject.getString("startDate"));
			serviceEntriesDto.setToDate(valueObject.getString("startEnd"));
			serviceEntriesDto.setVid(valueObject.getInt("vehicleId"));
			serviceEntriesDto.setCompanyId(valueObject.getInt("CompanyId"));

			serviceEntriesList	=	serviceEntriesDao.getServiceEntriesList(serviceEntriesDto);

			if(serviceEntriesList !=null) {
				for(ServiceEntriesDto entriesDto : serviceEntriesList) {
					entriesDto.setInvoiceDate(dateFormatAtt.format(entriesDto.getInvoiceDateOn()));
					entriesDto.setInvoiceDateStr(dateFormat.format(entriesDto.getInvoiceDateOn()));

					calenderEvent	= new CalenderEvent();

					calenderEvent.setEventDay(AttendanceDay.format(entriesDto.getInvoiceDateOn()));
					calenderEvent.setEventMonth("" + (Integer.parseInt(AttendanceMonth.format(entriesDto.getInvoiceDateOn())) - 1));
					calenderEvent.setEventYear(AttendanceYear.format(entriesDto.getInvoiceDateOn()));
					calenderEvent.setEventTitle("SE-"+entriesDto.getServiceEntries_Number());
					calenderEvent.setEventUrl("ServiceEntriesParts.in?SEID="+entriesDto.getServiceEntries_id());
					calenderEvent.setEventColor("Green");
					calenderEventList.add(calenderEvent);
				}
			}

			return serviceEntriesList;
		} catch (Exception e) {
			throw e;
		} finally {
			serviceEntriesDto		= null;
			serviceEntriesList		= null;
		}
	}

	private List<WorkOrdersDto> getWorkOrdersList(ValueObject valueObject) throws Exception {
		WorkOrdersDto				workOrdersDto			= null;
		List<WorkOrdersDto>			workOrdersList			= null;
		CalenderEvent				calenderEvent			= null;
		long						dayDiff					= 0;
		try {
			workOrdersDto	= new WorkOrdersDto();
			workOrdersDto.setStart_date(valueObject.getString("startDate"));
			workOrdersDto.setCompleted_date(valueObject.getString("startEnd"));
			workOrdersDto.setVehicle_vid(valueObject.getInt("vehicleId"));
			workOrdersDto.setCompanyId(valueObject.getInt("CompanyId"));

			workOrdersList	= workOrdersDao.getWorkOrdersList(workOrdersDto);

			if(workOrdersList !=null) {
				for(WorkOrdersDto ordersDto : workOrdersList) {
					ordersDto.setStart_date(dateFormat.format(ordersDto.getStart_dateOn()));
					ordersDto.setDue_date(dateFormat.format(ordersDto.getDue_dateOn()));

					calenderEvent	= new CalenderEvent();

					calenderEvent.setEventDay(AttendanceDay.format(ordersDto.getStart_dateOn()));
					calenderEvent.setEventMonth("" + (Integer.parseInt(AttendanceMonth.format(ordersDto.getStart_dateOn())) - 1));
					calenderEvent.setEventYear(AttendanceYear.format(ordersDto.getStart_dateOn()));
					calenderEvent.setEventTitle("WO-"+ordersDto.getWorkorders_Number());
					calenderEvent.setEventUrl("showWorkOrder?woId="+ordersDto.getWorkorders_id());
					calenderEvent.setEventColor("Pink");
					calenderEventList.add(calenderEvent);

					dayDiff		= ChronoUnit.DAYS.between(ordersDto.getStart_dateOn().toInstant(), ordersDto.getDue_dateOn().toInstant());

					if(dayDiff > 1) {
						for (int i = 1; i <= dayDiff; i++) {
							calenderEvent	= new CalenderEvent();
							calenderEvent.setEventDay("" + (Integer.parseInt(AttendanceDay.format(ordersDto.getStart_dateOn())) + i));
							calenderEvent.setEventMonth("" + (Integer.parseInt(AttendanceMonth.format(ordersDto.getStart_dateOn())) - 1));
							calenderEvent.setEventYear(AttendanceYear.format(ordersDto.getStart_dateOn()));
							calenderEvent.setEventTitle("WO-"+ordersDto.getWorkorders_Number());
							calenderEvent.setEventUrl("showWorkOrder?woId="+ordersDto.getWorkorders_id());
							calenderEvent.setEventColor("Pink");
							calenderEventList.add(calenderEvent);
						}
					}

				}
			}

			return workOrdersList;
		} catch (Exception e) {
			throw e;
		} finally {
			workOrdersDto			= null;
			workOrdersList			= null;
		}
	}

	private List<IssuesDto> getIssuesList(ValueObject valueObject) throws Exception {//showing sigle issue
		List<IssuesDto>			issuesList			= null;
		IssuesDto				issuesDto			= null;
		CalenderEvent			calenderEvent		= null;

		try {

			issuesDto	= new IssuesDto();
			issuesDto.setFromDate(valueObject.getString("startDate"));
			issuesDto.setToDate(valueObject.getString("startEnd"));
			issuesDto.setISSUES_VID(valueObject.getInt("vehicleId"));
			issuesDto.setCOMPANY_ID(valueObject.getInt("CompanyId"));
			
			issuesList	= issuesDao.getIssuesList(issuesDto);
			
			if(issuesList !=null) {
				for(IssuesDto issueDto : issuesList) {
					
				/*	issueDto.setISSUES_REPORTED_DATE(format.format(issueDto.getISSUES_REPORTED_DATE_ON()));
					issueDto.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + issueDto.getISSUES_ID()));
					issueDto.setISSUES_STATUS(IssuesTypeConstant.getStatusName(issueDto.getISSUES_STATUS_ID()));*/
					
						calenderEvent	= new CalenderEvent();
	
						calenderEvent.setEventDay(AttendanceDay.format(issueDto.getISSUES_REPORTED_DATE_ON()));
						calenderEvent.setEventMonth("" + (Integer.parseInt(AttendanceMonth.format(issueDto.getISSUES_REPORTED_DATE_ON())) - 1));
						calenderEvent.setEventYear(AttendanceYear.format(issueDto.getISSUES_REPORTED_DATE_ON()));
						calenderEvent.setEventTitle("I-"+issueDto.getISSUES_NUMBER());
						calenderEvent.setEventUrl("showIssues.in?Id="+issueDto.getISSUES_ID_ENCRYPT());
						calenderEvent.setEventColor("Red");
						calenderEventList.add(calenderEvent);
					}
			}

			return issuesList;
		} catch (Exception e) {
			throw e;
		} finally {
			issuesList		= null;
		}
	}
	
	
	private ValueObject getIssuesListDetails(ValueObject valueObject) throws Exception { // continuous 
		List<IssuesDto>			issuesListDetails			= null;
		List<IssuesDto>			issuesList					= null;
		IssuesDto				issuesDto					= null;
		CalenderEvent			calenderEvent				= null;
		long 					diff 						= 0;

		try {
			issuesDto	= new IssuesDto();
			issuesDto.setFromDate(valueObject.getString("startDate"));
			issuesDto.setToDate(valueObject.getString("startEnd"));
			issuesDto.setISSUES_VID(valueObject.getInt("vehicleId"));
			issuesDto.setCOMPANY_ID(valueObject.getInt("CompanyId"));
			
			issuesList			= issuesDao.getIssuesList(issuesDto);
			issuesListDetails	= issuesDao.getIssuesAllList(issuesDto);
			
			
			if(issuesListDetails !=null) {
				for(IssuesDto issueDto : issuesListDetails) {
					
					if(issueDto.getISSUES_STATUS_ID() == IssuesTypeConstant.ISSUES_CHANGE_STATUS_RESOLVED) {
						 diff =	DateTimeUtility.getExactDayDiffBetweenTwoDates(DateTimeUtility.getTimeStampFromDate(issueDto.getISSUES_REPORTED_DATE_ON()), DateTimeUtility.getTimeStampFromDate(issueDto.getLASTUPDATED_DATE_ON()));
					}else {
						diff = DateTimeUtility.getExactDayDiffBetweenTwoDates(DateTimeUtility.getTimeStampFromDate(issueDto.getISSUES_REPORTED_DATE_ON()),DateTimeUtility.getCurrentTimeStamp());
						
					}
					
					for(int i=0; i <= diff; i++) {
						
						calenderEvent	= new CalenderEvent();
						calenderEvent.setEventDay(AttendanceDay.format(DateTimeUtility.getNextDate(issueDto.getISSUES_REPORTED_DATE(), i)));
						calenderEvent.setEventMonth("" + (Integer.parseInt(AttendanceMonth.format(DateTimeUtility.getNextDate(issueDto.getISSUES_REPORTED_DATE(), i))) - 1));
						calenderEvent.setEventYear(AttendanceYear.format(DateTimeUtility.getNextDate(issueDto.getISSUES_REPORTED_DATE(), i)));
						calenderEvent.setEventTitle("I-"+issueDto.getISSUES_NUMBER());
						calenderEvent.setEventUrl("showIssues.in?Id="+issueDto.getISSUES_ID_ENCRYPT());
						calenderEvent.setEventColor("Red");
						
						calenderEventList.add(calenderEvent);
					}
					
					
					
				}
			}
			
			
			valueObject.put("issuesList", issuesList);
			valueObject.put("issuesListDetails", issuesListDetails);
			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			issuesList		= null;
		}
	}
	

	protected List<VehicleCommentDto> getVehicleCommentList(ValueObject valueObject) throws Exception {
		List<VehicleCommentDto>			vehicleCommentList			= null;
		VehicleCommentDto				vehicleCommentDto			= null;
		CalenderEvent					calenderEvent				= null;
		try {
			vehicleCommentDto	= new VehicleCommentDto();
			vehicleCommentDto.setFromDate(valueObject.getString("startDate"));
			vehicleCommentDto.setToDate(valueObject.getString("startEnd"));
			vehicleCommentDto.setVEHICLE_ID(valueObject.getInt("vehicleId"));
			vehicleCommentDto.setCOMPANY_ID(valueObject.getInt("CompanyId"));

			vehicleCommentList	= vehicleCommentDao.getVehicleCommentListByVid(vehicleCommentDto);

			if(vehicleCommentList !=null) {
				for(VehicleCommentDto commentDto : vehicleCommentList) {
					commentDto.setCREATED_DATE(dateFormat.format(commentDto.getCREATED_DATE_ON()));

					calenderEvent	= new CalenderEvent();

					calenderEvent.setEventDay(AttendanceDay.format(commentDto.getCREATED_DATE_ON()));
					calenderEvent.setEventMonth("" + (Integer.parseInt(AttendanceMonth.format(commentDto.getCREATED_DATE_ON())) - 1));
					calenderEvent.setEventYear(AttendanceYear.format(commentDto.getCREATED_DATE_ON()));
					calenderEvent.setEventTitle("C-"+commentDto.getVEHICLE_TITLE());
					calenderEvent.setEventUrl("");
					calenderEvent.setEventDescription(commentDto.getVEHICLE_COMMENT());
					calenderEvent.setEventColor("Navy Blue");
					calenderEventList.add(calenderEvent);
				}
			}

			return vehicleCommentList;
		} catch (Exception e) {
			throw e;
		}
	}

	private ValueObject getTripSheetList(ValueObject valueObject) throws Exception {
		List<TripSheetDto>			tripSheetList			= null;
		TripSheetDto				tripSheetDto			= null;
		CalenderEvent				calenderEvent			= null;
		ValueObject					valueOutObject			= null;
		YearMonth 					yearMonthObject 		= null;
		long						noOftripDays			= 0;
		long						daysInMonth				= 0;
		long						dayDiff					= 0;
		int							currentYear				= 0;
		int							currentMonth			= 0;
		
		try {
			tripSheetDto	= new TripSheetDto();
			tripSheetDto.setTripOpenDate(valueObject.getString("startDate"));
			tripSheetDto.setClosetripDate(valueObject.getString("startEnd"));
			tripSheetDto.setVid(valueObject.getInt("vehicleId"));
			tripSheetDto.setCompanyId(valueObject.getInt("CompanyId"));

			currentYear		= Integer.parseInt(valueObject.getString("startDate").substring(0, 4));
			currentMonth	= Integer.parseInt(valueObject.getString("startDate").substring(5, 7));
			
			
			if(Integer.parseInt(AttendanceMonth.format(new Date())) == currentMonth) {
				daysInMonth	= Long.parseLong(AttendanceDay.format(new Date()));
			} else {
				yearMonthObject = YearMonth.of(currentYear, currentMonth);
				daysInMonth = yearMonthObject.lengthOfMonth();
			}
			
			tripSheetList	= tripSheetDao.getTripSheetList(tripSheetDto);

			if(tripSheetList !=null) {
				
				for(TripSheetDto tripSheet : tripSheetList) {
					
					calenderEvent	= new CalenderEvent();

					calenderEvent.setEventDay(AttendanceDay.format(tripSheet.getTripOpenDateOn()));
					calenderEvent.setEventMonth("" + (Integer.parseInt(AttendanceMonth.format(tripSheet.getTripOpenDateOn())) - 1));
					calenderEvent.setEventYear(AttendanceYear.format(tripSheet.getTripOpenDateOn()));
					calenderEvent.setEventTitle("TS-"+tripSheet.getTripSheetNumber());
					calenderEvent.setEventUrl("showTripSheet.in?tripSheetID="+tripSheet.getTripSheetID());
					calenderEvent.setEventColor("Blue");
					calenderEventList.add(calenderEvent);
					
					dayDiff		= ChronoUnit.DAYS.between(tripSheet.getTripOpenDateOn().toInstant(), tripSheet.getClosetripDateOn().toInstant());
					dateList 	= DateTimeUtility.getDaysBetweenDates(tripSheet.getTripOpenDateOn(), tripSheet.getClosetripDateOn(), dateList);
					
					if(dayDiff > 1) {
						for (int i = 1; i <= dayDiff; i++) {
							calenderEvent	= new CalenderEvent();
							calenderEvent.setEventDay("" + (Integer.parseInt(AttendanceDay.format(tripSheet.getTripOpenDateOn())) + i));
							calenderEvent.setEventMonth("" + (Integer.parseInt(AttendanceMonth.format(tripSheet.getTripOpenDateOn())) - 1));
							calenderEvent.setEventYear(AttendanceYear.format(tripSheet.getTripOpenDateOn()));
							calenderEvent.setEventTitle("TS-"+tripSheet.getTripSheetNumber());
							calenderEvent.setEventUrl("showTripSheet.in?tripSheetID="+tripSheet.getTripSheetID());
							calenderEvent.setEventColor("Blue");
							calenderEventList.add(calenderEvent);
						}
					}
				}
				
				for(Date date : dateList) {
					if(Integer.parseInt(AttendanceMonth.format(date)) == currentMonth) {
						noOftripDays++;
					}
				}
				
				valueOutObject	= new ValueObject();
				valueOutObject.put("noOftripDays", noOftripDays);
				valueOutObject.put("noOfidleDays", daysInMonth - noOftripDays);
				valueOutObject.put("tripSheetList", tripSheetList);
			}
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		} finally {
			tripSheetList			= null;
			tripSheetDto			= null;
			calenderEvent			= null;
			valueOutObject			= null;
			dayDiff					= 0;   
		}
	}
}