package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.IssuesTypeConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.ServiceEntriesType;
import org.fleetopgroup.constant.TripSheetFlavorConstant;
import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.constant.TripsheetPickAndDropConstant;
import org.fleetopgroup.constant.TyreAssignmentConstant;
import org.fleetopgroup.constant.WorkOrdersType;
import org.fleetopgroup.persistence.dao.FuelRepository;
import org.fleetopgroup.persistence.dao.PartLocationsRepository;
import org.fleetopgroup.persistence.dao.RenewalReminderRepository;
import org.fleetopgroup.persistence.dao.RenewalSubTypeRepository;
import org.fleetopgroup.persistence.dao.ServiceReminderRepository;
import org.fleetopgroup.persistence.dao.VehicleRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.TollExpensesDetailsDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripsheetPickAndDropDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleTyreLayoutPositionDto;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.RenewalReminder;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IPickAndDropLocationService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ISummaryService;
import org.fleetopgroup.persistence.serviceImpl.ITollExpensesDetailsService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreAssignmentService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.AESEncryptDecrypt;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SummaryService implements ISummaryService {
	
	@Autowired ICompanyConfigurationService 	companyConfigurationService;
	@Autowired IRenewalReminderService 			RenewalReminderService;
	@Autowired IServiceReminderService 			serviceReminderService;
	@Autowired ITripSheetService 				tripSheetService;
	@Autowired IServiceEntriesService			serviceEntriesService;
	@Autowired IWorkOrdersService			    workOrdersService;
	@Autowired PartLocationsRepository			partLocationsRepository;
	@Autowired IFuelService						fuelService;
	@Autowired IIssuesService					issuesService;
	@Autowired ITollExpensesDetailsService		TollExpensesDetailsService;
	@Autowired IVehicleService					VehicleService;
	@Autowired VehicleRepository				VehicleRepository;
	@Autowired ServiceReminderRepository		ServiceReminderRepository;
	@Autowired RenewalReminderRepository		RenewalReminderRepository;
	@Autowired FuelRepository					FuelRepository;
	@Autowired RenewalSubTypeRepository			RenewalSubTypeRepository;
	@Autowired IPickAndDropLocationService		PickAndDropLocationService;
	@Autowired IInventoryTyreService		iventoryTyreService;
	@Autowired IVehicleTyreAssignmentService		vehicleTyreAssignmentService;
	
	@PersistenceContext
	public EntityManager entityManager;
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public ValueObject getAllCountDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		String 						startDate						= null;
		String 						endDate							= null;
		ValueObject					valueOutObject					= null;
		Integer						companyID						= 0;
		long						workOrderCount					= 0;
		long						serviceReminderCount			= 0;
		long						RRCount							= 0;
		long						issueCount						= 0;
		long						fuelCount						= 0;
		long						tripCount						= 0;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			companyID		= userDetails.getCompany_id();
			startDate 		= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate 		= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			valueOutObject 	= new ValueObject();
			
			//startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			HashMap<Integer, Long>  workOrderCounts 	= workOrdersService.getALLEmailWorkOrderDailyWorkStatus(startDate, endDate);
			HashMap<Integer, Long>  serviceReminder		= serviceReminderService.getALLEmailServiceReminderDailyWorkStatus(startDate, endDate);
			HashMap<Integer, Long>  renewalCounts 	   	= RenewalReminderService.getALLEmailRenewalRemindersDailyWorkStatus(startDate, endDate);
			
			HashMap<Integer, Long>  issuesCreatedHM 	= issuesService.issuesCreatedOnDate(startDate, endDate);
			HashMap<Integer, Long>  fuelCountHM			= fuelService.getFuelEntriesCountHM(startDate, endDate);
			HashMap<Integer, Long>  tripCountHM	   		= tripSheetService.getALLEmailTripDailyWorkStatusHM(startDate, endDate,userDetails.getCompany_id());
			
			if(workOrderCounts != null && workOrderCounts.get(companyID) != null)
				workOrderCount	= workOrderCounts.get(companyID);	
			
			if(serviceReminder != null && serviceReminder.get(companyID) != null)
				serviceReminderCount = serviceReminder.get(companyID);
			
			if(renewalCounts != null && renewalCounts.get(companyID) != null)
				RRCount				= renewalCounts.get(companyID);
			
			if(issuesCreatedHM != null && issuesCreatedHM.get(companyID) != null)
				issueCount			= issuesCreatedHM.get(companyID);
				
			if(fuelCountHM != null && fuelCountHM.get(companyID) != null)
				fuelCount			= fuelCountHM.get(companyID);
		
			if(tripCountHM != null && tripCountHM.get(companyID) != null)
				tripCount			= tripCountHM.get(companyID);
				
			valueOutObject.put("workOrderCounts", workOrderCount);
			valueOutObject.put("serviceReminder", serviceReminderCount);
			valueOutObject.put("renewalCounts", RRCount);
			valueOutObject.put("tripCountHM", tripCount);
			valueOutObject.put("issuesCreatedHM",issueCount);
			valueOutObject.put("fuelCountHM", fuelCount);
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
		}
	}
	
	@Override
	public ValueObject getTripsheetCount(ValueObject valueObject) throws Exception {
		String 						startDate						= null;
		String 						endDate							= null;
		ValueObject					valueOutObject					= null;
		HashMap<Integer, Long>  	tripCountHM						= null;
		Integer						companyID						= 0;
		long						tripCount						= 0;
		try {
			companyID		= valueObject.getInt("compId");
			startDate 		= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate 		= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			valueOutObject 	= new ValueObject();
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			tripCountHM	   	= tripSheetService.getALLEmailTripDailyWorkStatusHM(startDate, endDate,companyID);
		
			if(tripCountHM != null && tripCountHM.get(companyID) != null)
				tripCount			= tripCountHM.get(companyID);
				
			valueOutObject.put("tripSheetCount", tripCount);
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject		= null;
		}
	}
	
	@Override
	public ValueObject getFuelCount(ValueObject valueObject) throws Exception {
		String 						startDate						= null;
		String 						endDate							= null;
		ValueObject					valueOutObject					= null;
		HashMap<Integer, Long>  	fuelCountHM						= null;
		Integer						companyID						= 0;
		long						fuelCount						= 0;
		try {
			companyID		= valueObject.getInt("compId");
			startDate 		= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate 		= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			valueOutObject 	= new ValueObject();
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			fuelCountHM		= fuelService.getFuelEntriesCountHM(startDate, endDate);
			
			if(fuelCountHM != null && fuelCountHM.get(companyID) != null)
				fuelCount			= fuelCountHM.get(companyID);
			
			valueOutObject.put("fuelCount", fuelCount);
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject		= null;
		}
	}
	@Override
	public ValueObject getWorkOrderCount(ValueObject valueObject) throws Exception {
		String 						startDate						= null;
		String 						endDate							= null;
		ValueObject					valueOutObject					= null;
		HashMap<Integer, Long>  	workOrderCounts 				= null;
		Integer						companyID						= 0;
		long						workOrderCount					= 0;
		try {
			companyID		= valueObject.getInt("compId");
			startDate 		= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate 		= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			valueOutObject 	= new ValueObject();
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			workOrderCounts = workOrdersService.getALLEmailWorkOrderDailyWorkStatus(startDate, endDate);
			
			if(workOrderCounts != null && workOrderCounts.get(companyID) != null)
				workOrderCount	= workOrderCounts.get(companyID);	
			
			valueOutObject.put("workOrderCounts", workOrderCount);
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject		= null;
		}
	}
	@Override
	public ValueObject getServiceReminderCount(ValueObject valueObject) throws Exception {
		String 						startDate						= null;
		String 						endDate							= null;
		ValueObject					valueOutObject					= null;
		HashMap<Integer, Long>  	serviceReminder					= null;
		Integer						companyID						= 0;
		long						serviceReminderCount			= 0;
		try {
			companyID		= valueObject.getInt("compId");
			startDate 		= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate 		= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			valueOutObject 	= new ValueObject();
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			serviceReminder	= serviceReminderService.getALLEmailServiceReminderDailyWorkStatus(startDate, endDate);
			
			if(serviceReminder != null && serviceReminder.get(companyID) != null)
				serviceReminderCount = serviceReminder.get(companyID);
			
			valueOutObject.put("serviceReminderCount", serviceReminderCount);
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject		= null;
		}
	}
	@Override
	public ValueObject getRRCount(ValueObject valueObject) throws Exception {
		String 						startDate						= null;
		String 						endDate							= null;
		ValueObject					valueOutObject					= null;
		HashMap<Integer, Long>  	renewalCounts 					= null;
		Integer						companyID						= 0;
		long						RRCount							= 0;
		try {
			companyID		= valueObject.getInt("compId");
			startDate 		= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate 		= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			valueOutObject 	= new ValueObject();
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
		
			renewalCounts 	= RenewalReminderService.getALLEmailRenewalRemindersDailyWorkStatus(startDate, endDate);
			
			if(renewalCounts != null && renewalCounts.get(companyID) != null)
				RRCount				= renewalCounts.get(companyID);
			
			valueOutObject.put("renewalCounts", RRCount);
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject		= null;
		}
	}
	@Override
	public ValueObject getIssueCount(ValueObject valueObject) throws Exception {
		String 						startDate						= null;
		String 						endDate							= null;
		ValueObject					valueOutObject					= null;
		HashMap<Integer, Long>  	issuesCreatedHM 				= null;
		Integer						companyID						= 0;
		long						issueCount						= 0;
		try {
			companyID		= valueObject.getInt("compId");
			startDate 		= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate 		= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			valueOutObject 	= new ValueObject();
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			issuesCreatedHM = issuesService.issuesCreatedOnDate(startDate, endDate);
			
			if(issuesCreatedHM != null && issuesCreatedHM.get(companyID) != null)
				issueCount			= issuesCreatedHM.get(companyID);
			
			valueOutObject.put("issuesCount",issueCount);
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject		= null;
		}
	}
	@Override
	public ValueObject getServiceEntryCount(ValueObject valueObject) throws Exception {
		String 						startDate						= null;
		String 						endDate							= null;
		ValueObject					valueOutObject					= null;
		HashMap<Integer, Long> 		serviceEntriesCounts			= null;
		Integer						companyID						= 0;
		long						serviceEntryCount				= 0;
		try {
			companyID		= valueObject.getInt("compId");
			startDate 		= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate 		= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			valueOutObject 	= new ValueObject();
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			serviceEntriesCounts  	= serviceEntriesService.getALLEmailServiceEntriesDailyWorkStatus(startDate, endDate);
			
			if(serviceEntriesCounts != null && serviceEntriesCounts.get(companyID) != null)
				serviceEntryCount			= serviceEntriesCounts.get(companyID);
			
			valueOutObject.put("serviceEntryCount",serviceEntryCount);
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject		= null;
		}
	}
	
	@Override
	public ValueObject getPickAndDropCount(ValueObject valueObject) throws Exception {
		String 						startDate						= null;
		String 						endDate							= null;
		ValueObject					valueOutObject					= null;
		HashMap<Integer, Long>  	pickAndDropCreatedHM 			= null;
		Integer						companyID						= 0;
		long						pickAndDropCount				= 0;
		try {
			companyID		= valueObject.getInt("compId");
			startDate 		= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate 		= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			valueOutObject 	= new ValueObject();
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			pickAndDropCreatedHM = PickAndDropLocationService.pickAndDropCreatedBetweenDates(startDate, endDate);
			
			if(pickAndDropCreatedHM != null && pickAndDropCreatedHM.get(companyID) != null)
				pickAndDropCount			= pickAndDropCreatedHM.get(companyID);
			
			valueOutObject.put("pickAndDropCount",pickAndDropCount);
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject		= null;
		}
	}
	
	/********************WorkOrder Summary Data******************/
	@Override
	public ValueObject getWorkOrderDataCount(ValueObject valueObject) throws Exception {
		String 						startDate						= null;
		String 						endDate							= null;
		String 						eightDaysBackDate				= null;
		String 						fifteenDaysBackDate				= null;
		Integer						companyID						= 0;
		String 						sevenDaysBackDate				= null;
		long						noOfDaysInDateRange				= 0;
		long						from7Days						= 0;
		long						from15Days						= 0;
		long						from30Days						= 0;
		long  						workOrderCreatedCounts          = 0;
		long  						workOrderAllOpenCounts          = 0;
		long  						workOrderOpenCounts 	        = 0;
		long  						workOrderProcessCounts          = 0;
		long  						workOrderHoldCounts 	        = 0;
		long  						workOrderCloseCounts 	        = 0;
		short						openStatus						= WorkOrdersType.WORKORDERS_STATUS_OPEN;
		short						processStatus					= WorkOrdersType.WORKORDERS_STATUS_INPROCESS;
		short						holdStatus						= WorkOrdersType.WORKORDERS_STATUS_ONHOLD;
		short						closedStatus					= WorkOrdersType.WORKORDERS_STATUS_COMPLETE;
		ValueObject					valueOutObject					= null;
		String						todaysStrDate					= null;
		String						forteenDaysBackDate				= null;
		try {
			valueOutObject 		= new ValueObject();
			companyID			= valueObject.getInt("compId");
			
			sevenDaysBackDate	= LocalDate.now().minusDays(7)+"";
			eightDaysBackDate	= LocalDate.now().minusDays(8)+"";
			forteenDaysBackDate	= LocalDate.now().minusDays(14)+"";
			fifteenDaysBackDate	= LocalDate.now().minusDays(15)+"";
			todaysStrDate		= LocalDate.now()+"";
			
			startDate 			= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate 			= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			noOfDaysInDateRange = DateTimeUtility.getDayDiffBetweenTwoDates(DateTimeUtility.getTimeStamp(startDate,DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(endDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			
			workOrderCreatedCounts 	= workOrdersService.getWorkOrderCreatedCount(startDate, endDate, companyID);
			workOrderOpenCounts 	= workOrdersService.getWorkOrderCountByStatusID(startDate, endDate, companyID, openStatus);
			workOrderProcessCounts 	= workOrdersService.getWorkOrderCountByStatusID(startDate, endDate, companyID, processStatus);
			workOrderHoldCounts 	= workOrdersService.getWorkOrderCountByStatusID(startDate, endDate, companyID, holdStatus);
			workOrderCloseCounts   	= workOrdersService.getWorkOrderCountByStatusID(startDate, endDate, companyID, closedStatus);
			workOrderAllOpenCounts 	= workOrdersService.getALLOpenWorkOrderCount(companyID);
		//	workOrderCloseCounts 	= workOrdersService.getWorkOrderCloseCount(startDate, endDate, userDetails.getCompany_id(),closeStatus);
			
			from7Days 	   			= workOrdersService.getALLOpenWorkOrderBetweenDates(sevenDaysBackDate + DateTimeUtility.DAY_START_TIME, todaysStrDate+" "+DateTimeUtility.DAY_END_TIME, companyID);
			from15Days 	  			= workOrdersService.getALLOpenWorkOrderBetweenDates(forteenDaysBackDate + DateTimeUtility.DAY_START_TIME, eightDaysBackDate+ DateTimeUtility.DAY_END_TIME, companyID);
			from30Days 	   			= workOrdersService.getALLOpenWorkOrderByDate(fifteenDaysBackDate+ DateTimeUtility.DAY_END_TIME, companyID);
			
			valueOutObject.put("workOrderCreatedCounts", workOrderCreatedCounts);
			valueOutObject.put("workOrderOpenCounts", workOrderOpenCounts);
			valueOutObject.put("workOrderProcessCounts", workOrderProcessCounts);
			valueOutObject.put("workOrderHoldCounts", workOrderHoldCounts);
			valueOutObject.put("workOrderCloseCounts", workOrderCloseCounts);
			
			valueOutObject.put("workOrderAllOpenCounts", workOrderAllOpenCounts);
			valueOutObject.put("from7Days", from7Days);
			valueOutObject.put("from15Days", from15Days);
			valueOutObject.put("from30Days", from30Days);
			
			valueOutObject.put("noOfDaysInDateRange", noOfDaysInDateRange);
			
			return valueOutObject;
		
		} catch (Exception e) {
			LOGGER.error("ERR"+e);
			throw e;
		}
		finally {
			startDate						= null;                                       
			endDate							= null;                                       
			eightDaysBackDate				= null;                                       
			fifteenDaysBackDate				= null;                                       
			companyID						= 0;                                          
			sevenDaysBackDate				= null;                                       
			from7Days						= 0;                                          
			from15Days						= 0;                                          
			from30Days						= 0;                                          
			workOrderCreatedCounts          = 0;                                          
			workOrderOpenCounts 	        = 0;                                          
			workOrderProcessCounts          = 0;                                          
			workOrderHoldCounts 	        = 0;                                          
			workOrderCloseCounts 	        = 0;                                          
			openStatus						= WorkOrdersType.WORKORDERS_STATUS_OPEN;      
			processStatus					= WorkOrdersType.WORKORDERS_STATUS_INPROCESS; 
			holdStatus						= WorkOrdersType.WORKORDERS_STATUS_ONHOLD;    
			//closeStatus						= WorkOrdersType.WORKORDERS_STATUS_COMPLETE;  
			valueOutObject					= null;
		}
		
	}
	
	@Override
	public ValueObject getLocationWiseWorkOrderDataCount(ValueObject valueObject) throws Exception {
		ValueObject							valueOutObject					= null;
		HashMap<Integer , WorkOrdersDto> 	map 							= null; 
		List<WorkOrdersDto>					openList						= null;
		List<WorkOrdersDto>					closeList						= null;
		List<WorkOrdersDto>					finalList						= null;
		WorkOrdersDto 						workOrdersDto 					= null;
		String 								startDate						= null;
		String 								endDate							= null;
		Integer								companyID						= 0;
		
		try {
			valueOutObject 				= new ValueObject();
			map 						= new HashMap<Integer ,WorkOrdersDto>();
			finalList 					= new ArrayList<WorkOrdersDto>();
			companyID					= valueObject.getInt("compId");
			startDate 					= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate 					= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			openList					= workOrdersService.getLocationWiseWorkOrderCreatedCount(startDate, endDate, companyID);
			closeList					= workOrdersService.getLocationWiseWorkOrderCloseCount(startDate, endDate, companyID);
			
			if(openList != null && !openList.isEmpty())
				finalList.addAll(openList);
			if(closeList != null && !closeList.isEmpty())
				finalList.addAll(closeList);
			
			
			if(finalList != null && finalList.size() > 0) {
				
				for(WorkOrdersDto dto : finalList) {
					
					if(map.containsKey(dto.getWorkorders_location_ID())) {
						workOrdersDto 			= map.get(dto.getWorkorders_location_ID());
						workOrdersDto.setWorkorders_location(dto.getWorkorders_location());
						
						
						if(dto.getWorkorders_statusId() == WorkOrdersType.WORKORDERS_STATUS_COMPLETE) {
							workOrdersDto.setWorkOrderCloseCount(workOrdersDto.getWorkOrderCloseCount() + dto.getWorkOrderCloseCount());
						}else{
							workOrdersDto.setWorkOrderOpenCount(workOrdersDto.getWorkOrderOpenCount() + dto.getWorkOrderOpenCount());
						}
					}else {
						
						if(dto.getWorkorders_statusId() == WorkOrdersType.WORKORDERS_STATUS_COMPLETE) {
							dto.setWorkOrderCloseCount(dto.getWorkOrderCloseCount());
							dto.setWorkOrderOpenCount((long) 0);
						}else{
							dto.setWorkOrderOpenCount(dto.getWorkOrderOpenCount());
							dto.setWorkOrderCloseCount((long) 0);
						}
						map.put(dto.getWorkorders_location_ID(),dto);
					}
				}
				
			}
			valueOutObject.put("locationWiseWOCount", map.values());
			return valueOutObject;// changes 
			
		} catch (Exception e) {
			LOGGER.error("ERR"+e);
			throw e;
		}
		
	}
	
	
	@Override
	public ValueObject getWorkOrderTableData(ValueObject valueObject) throws Exception {
		ValueObject					valueOutObject								= null;
		int							compId										= 0;
		short						type										= 0;
		List<WorkOrdersDto>			workOrder									= null;
		String 						startDate									= null;
		String 						endDate										= null;
		String 						todaysStrDate 								= null;
		String 						sevenDaysBackDate 							= null;
		String 						eightDaysBackDate 							= null;
		String 						forteenDaysBackDate 						= null;
		String 						fifteenDaysBackDate							= null;
		final short					WORKORDER_CREATED							= 0;
		final short					WORKORDER_OPEN 								= WorkOrdersType.WORKORDERS_STATUS_OPEN;//1
		final short					WORKORDER_IN_PROCESS						= WorkOrdersType.WORKORDERS_STATUS_INPROCESS;//2
		final short					WORKORDER_HOLD 								= WorkOrdersType.WORKORDERS_STATUS_ONHOLD;//3
		final short					WORKORDER_CLOSED 							= WorkOrdersType.WORKORDERS_STATUS_COMPLETE;//4
		final short					WORKORDER_ALL_OPEN							= 5;
		final short					WORKORDER_0_TO_7 							= 6;
		final short					WORKORDER_8_TO_15 							= 7;
		final short					WORKORDER_15UP 								= 8;
		String 						WOStatusQuery								= "";
		try {
			valueOutObject 		= new ValueObject();
			
			compId				= valueObject.getInt("compId");
			startDate			= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate				= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			type				= valueObject.getShort("type");
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			sevenDaysBackDate	= LocalDate.now().minusDays(7)+"";
			eightDaysBackDate	= LocalDate.now().minusDays(8)+"";
			forteenDaysBackDate	= LocalDate.now().minusDays(14)+"";
			fifteenDaysBackDate	= LocalDate.now().minusDays(15)+"";
			todaysStrDate		= LocalDate.now()+"";
			
			switch (type) {
			 
			case WORKORDER_CREATED:// WorkOrder Created Details Between Two Dates case1 AND No Status Hense Return All Status Details ie WorkOrder Created Details
				  WOStatusQuery	= " WO.start_date between '"+startDate+"' AND '"+endDate+"'";
				  workOrder		= workOrdersService.getWorkorderDetailsBetweenDatesByStatus(compId, WOStatusQuery);// WOStatusQuery is ""
				  
				  if(workOrder != null)
					valueOutObject.put("workOrder", workOrder);
				  	break;
			        
			  case WORKORDER_OPEN: // WorkOrder Details Between Two Dates By Their Status For all Cases by their respective Status
				  WOStatusQuery = " WO.start_date between '"+startDate+"' AND '"+endDate+"'AND WO.workorders_statusId ="+WORKORDER_OPEN+" ";
				  workOrder	= workOrdersService.getWorkorderDetailsBetweenDatesByStatus(compId, WOStatusQuery);
				   
				  if(workOrder != null)
					valueOutObject.put("workOrder", workOrder);
				    break;
			        
			  case WORKORDER_IN_PROCESS: 
				  WOStatusQuery = "WO.start_date between '"+startDate+"' AND '"+endDate+"' AND WO.workorders_statusId ="+WORKORDER_IN_PROCESS+" ";
				  workOrder	= workOrdersService.getWorkorderDetailsBetweenDatesByStatus(compId, WOStatusQuery);
				  
				  if(workOrder != null)
					valueOutObject.put("workOrder", workOrder);
			        break;
			        
			  case WORKORDER_HOLD: 
				  WOStatusQuery = " WO.start_date between '"+startDate+"' AND '"+endDate+"' AND WO.workorders_statusId ="+WORKORDER_HOLD+" ";
				  workOrder	= workOrdersService.getWorkorderDetailsBetweenDatesByStatus(compId, WOStatusQuery);
				  
				  if(workOrder != null)	
					valueOutObject.put("workOrder", workOrder);
			        break;
			        
			  case WORKORDER_CLOSED: 
				  WOStatusQuery = " WO.start_date between '"+startDate+"' AND '"+endDate+"' AND WO.workorders_statusId ="+WORKORDER_CLOSED+" ";
				  workOrder	= workOrdersService.getWorkorderDetailsBetweenDatesByStatus(compId, WOStatusQuery);
				  
				  if(workOrder != null)	
					valueOutObject.put("workOrder", workOrder);
				    break;
			  
			  case WORKORDER_ALL_OPEN:// WorkOrder Created Details Between Two Dates case1 AND No Status Hense Return All Status Details ie WorkOrder Created Details
					WOStatusQuery	= "WO.workorders_statusId NOT IN("+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+") ";
					workOrder		= workOrdersService.getWorkorderDetailsBetweenDatesByStatus(compId, WOStatusQuery);// WOStatusQuery is ""
					
					if(workOrder != null)
						valueOutObject.put("workOrder", workOrder);
					break;	    
				    
			  case WORKORDER_0_TO_7: // No Status ie WOStatusQuery is blank Hense Return All Status Details
				  WOStatusQuery	= " WO.start_date between '"+sevenDaysBackDate+""+DateTimeUtility.DAY_START_TIME+"' AND '"+todaysStrDate+""+DateTimeUtility.DAY_END_TIME+"' AND  WO.workorders_statusId NOT IN("+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+")";
				  workOrder	= workOrdersService.getWorkorderDetailsBetweenDatesByStatus(compId, WOStatusQuery);
				  
				  if(workOrder != null)	
					valueOutObject.put("workOrder", workOrder);
				    break;
			 
			  case WORKORDER_8_TO_15: 
				  WOStatusQuery	= " WO.start_date between '"+forteenDaysBackDate+""+DateTimeUtility.DAY_START_TIME+"' AND '"+ eightDaysBackDate+""+DateTimeUtility.DAY_END_TIME+"' AND  WO.workorders_statusId NOT IN("+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+")";
				  workOrder	= workOrdersService.getWorkorderDetailsBetweenDatesByStatus(compId, WOStatusQuery);
				  
				  if(workOrder != null)	
					valueOutObject.put("workOrder", workOrder);
				    break;
			 
			  case WORKORDER_15UP: 
				  WOStatusQuery	= " WO.start_date <= '"+fifteenDaysBackDate+""+DateTimeUtility.DAY_END_TIME+"' AND  WO.workorders_statusId NOT IN("+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+")";
				  workOrder	= workOrdersService.getWorkorderDetailsBetweenDatesByStatus(compId, WOStatusQuery);
				  
				  if(workOrder != null)	
					valueOutObject.put("workOrder", workOrder);
				    break;
			 
			  default:
			        break;
			}
				
			return valueOutObject;
			
		} catch (Exception e) {
			LOGGER.error("getWorkOrderTableData Exception"+e);
			throw e;
		}finally {
			compId										= 0;                                                 
			startDate									= null;                                              
			endDate										= null;                                              
			sevenDaysBackDate 							= null;                                              
			eightDaysBackDate 							= null;                                              
			fifteenDaysBackDate							= null;                                              
			type										= 0;                                                 
			workOrder									= null;                                              
			WOStatusQuery								= ""; 
			valueOutObject								= null;
		}
	}	
	
	/********************ISSSUE SUMMARY Data**************************/
	
	@Override
	public ValueObject getIssueDataCount(ValueObject valueObject) throws Exception {
		String 						startDate						= null;
		String 						endDate							= null;
		ValueObject					valueOutObject					= null;
		Integer						companyID						= 0;
		String 						todaysStrDate					= null;
		String 						sevenDaysBackDate				= null;
		String 						eightDaysBackDate				= null;
		String 						forteenDaysBackDate				= null;
		String 						fifteenDaysBackDate				= null;
		long						noOfDaysInDateRange				= 0;
		long						from7Days						= 0;
		long						from15Days						= 0;
		long						from30Days						= 0;
		long  						issueCreatedCounts          	= 0;
		long  						issueOpenCounts 	       		= 0;
		long  						issueProcessCounts          	= 0;
		long  						issueResolveCounts 	       		= 0;
		long  						issueCloseCounts          		= 0;
		long  						issueOverDueCounts          	= 0;
		long  						issueRejectedCounts          	= 0;
		long  						issueAllOpenCounts          	= 0;
		short						openStatus						= IssuesTypeConstant.ISSUES_CHANGE_STATUS_OPEN;
		short						processStatus					= IssuesTypeConstant.ISSUES_CHANGE_STATUS_WOCREATED;
		short						closeStatus						= IssuesTypeConstant.ISSUES_CHANGE_STATUS_CLOSED;
		short						RejectedStatus					= IssuesTypeConstant.ISSUES_CHANGE_STATUS_REJECT;
		short						ResolvedStatus					= IssuesTypeConstant.ISSUES_CHANGE_STATUS_RESOLVED;
		long 						vehicleGroupId					= 0;
		String 						vGroupQuery						= " ";
		try {	
			companyID				= valueObject.getInt("compId");
			valueOutObject 			= new ValueObject();
			
			startDate 				= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate 				= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			vehicleGroupId			= valueObject.getLong("group",0);
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			noOfDaysInDateRange = DateTimeUtility.getDayDiffBetweenTwoDates(DateTimeUtility.getTimeStamp(startDate,DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(endDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			
			sevenDaysBackDate		= LocalDate.now().minusDays(7)+"";
			eightDaysBackDate		= LocalDate.now().minusDays(8)+"";
			forteenDaysBackDate		= LocalDate.now().minusDays(14)+"";
			fifteenDaysBackDate		= LocalDate.now().minusDays(15)+"";
			todaysStrDate			= LocalDate.now()+"";
			
			if(vehicleGroupId > 0) {
				vGroupQuery = " AND V.vehicleGroupId ="+vehicleGroupId+" ";
			}
			
			issueCreatedCounts 		= issuesService.getTotalIssuesCreatedCount(startDate, endDate, companyID ,vGroupQuery);// email
			 
			issueOpenCounts			= issuesService.issuesCountByStatus(startDate, endDate, openStatus, companyID,vGroupQuery);
			issueProcessCounts		= issuesService.issuesCountByInProcessStatus(startDate, endDate, companyID,vGroupQuery);
			issueResolveCounts		= issuesService.issuesCountByStatus(startDate, endDate, ResolvedStatus, companyID,vGroupQuery);
			issueRejectedCounts		= issuesService.issuesCountByStatus(startDate, endDate, RejectedStatus, companyID,vGroupQuery);
			issueCloseCounts		= issuesService.issuesCountByStatus(startDate, endDate, closeStatus, companyID,vGroupQuery);
		
			issueOverDueCounts		= issuesService.issuesOverDueCount(todaysStrDate+""+DateTimeUtility.DAY_END_TIME, companyID,vGroupQuery);
			issueAllOpenCounts 	  	= issuesService.getIssuesAllopenCount(companyID,vGroupQuery);
			from7Days 				= issuesService.getIssuesOpenCountBetweenDates(sevenDaysBackDate+" "+DateTimeUtility.DAY_START_TIME, todaysStrDate+" "+DateTimeUtility.DAY_END_TIME, companyID,vGroupQuery);
			from15Days 				= issuesService.getIssuesOpenCountBetweenDates(forteenDaysBackDate+" "+DateTimeUtility.DAY_START_TIME, eightDaysBackDate+" "+DateTimeUtility.DAY_END_TIME, companyID,vGroupQuery);
			from30Days 				= issuesService.getIssuesOpenCountByDate(fifteenDaysBackDate+" "+DateTimeUtility.DAY_END_TIME, companyID,vGroupQuery);
			
			valueOutObject.put("issueCreatedCounts", issueCreatedCounts);
			valueOutObject.put("issueOpenCounts", issueOpenCounts);
			valueOutObject.put("issueProcessCounts", issueProcessCounts);
			valueOutObject.put("issueResolveCounts", issueResolveCounts);
			valueOutObject.put("issueRejectedCounts", issueRejectedCounts);
			valueOutObject.put("issueCloseCounts", issueCloseCounts);
			
			valueOutObject.put("issueOverDueCounts", issueOverDueCounts);
			valueOutObject.put("issueAllOpenCounts", issueAllOpenCounts);
			valueOutObject.put("from7Days", from7Days);
			valueOutObject.put("from15Days", from15Days);
			valueOutObject.put("from30Days", from30Days);
			
			valueOutObject.put("noOfDaysInDateRange", noOfDaysInDateRange);
			
			return valueOutObject;
		
		} catch (Exception e) {
			LOGGER.error("getWorkOrderTableData Exception"+e);
			throw e;
		}finally {
			startDate						                         = null;                                                                                       
			endDate							                         = null;                                                                                       
			valueOutObject					                         = null;                                                                                       
			companyID						                         = 0;                                                                                          
			sevenDaysBackDate				                         = null;                                                                                       
			eightDaysBackDate				                         = null;                                                                                       
			fifteenDaysBackDate				                         = null;                                                                                       
			from7Days						                         = 0;                                                                                          
			from15Days						                         = 0;                                                                                          
			from30Days						                         = 0;                                                                                          
			issueCreatedCounts          	                         = 0;                                                                                          
			issueOpenCounts 	       		                         = 0;                                                                                          
			issueProcessCounts          	                         = 0;                                                                                          
			issueResolveCounts 	       		                         = 0;                                                                                          
			issueCloseCounts          		                         = 0;                                                                                          
			issueOverDueCounts          	                         = 0;                                                                                          
			issueRejectedCounts          	                         = 0;                                                                                          
			openStatus						                         = IssuesTypeConstant.ISSUES_CHANGE_STATUS_OPEN;                                               
			processStatus					                         = IssuesTypeConstant.ISSUES_CHANGE_STATUS_WOCREATED;                                          
			closeStatus						                         = IssuesTypeConstant.ISSUES_CHANGE_STATUS_CLOSED;                                             
			RejectedStatus					                         = IssuesTypeConstant.ISSUES_CHANGE_STATUS_REJECT;                                             
			ResolvedStatus					                         = IssuesTypeConstant.ISSUES_CHANGE_STATUS_RESOLVED;                                           
		}                                                                                                                        
		
		
	}
	
	@Override
	public ValueObject getIssueTableData(ValueObject valueObject) throws Exception {
		int							compId										= 0;
		String 						startDate									= null;
		String 						endDate										= null;
		String 						todaysStrDate 								= null;
		String 						sevenDaysBackDate 							= null;
		String 						eightDaysBackDate 							= null;
		String 						forteenDaysBackDate 						= null;
		String 						fifteenDaysBackDate							= null;
		short						type										= 0;
		List<IssuesDto>				issue										= null;
		final short					ISSUE_CREATED 								= 0;
		final short					ISSUE_OPEN 									= IssuesTypeConstant.ISSUES_CHANGE_STATUS_OPEN;
		final short					ISSUE_CLOSED 	 							= IssuesTypeConstant.ISSUES_CHANGE_STATUS_CLOSED;
		final short					ISSUE_IN_PROCESS							= IssuesTypeConstant.ISSUES_CHANGE_STATUS_WOCREATED;
		final short					ISSUE_IN_SE_CREATED							= IssuesTypeConstant.ISSUES_STATUS_SE_CREATED;
		final short					ISSUE_RESOLVED 								= IssuesTypeConstant.ISSUES_CHANGE_STATUS_RESOLVED;
		final short					ISSUE_REJECTED 								= IssuesTypeConstant.ISSUES_CHANGE_STATUS_REJECT;
		final short					ISSUE_OVREDUE 								= 6;
		final short					ISSUE_ALL_OPEN 								= 7;
		final short					ISSUE_0_TO_7 								= 8;
		final short					ISSUE_8_TO_15 								= 9;
		final short					ISSUE_BEFORE_15Days 						= 10;
		String 						issueStatusQuery							= "";
		String 						vGroupQuery									= "";
		ValueObject					valueOutObject								= null;
		long						vGroup										= 0;
		try {
			valueOutObject 		= new ValueObject();
			
			compId				= valueObject.getInt("compId");
			startDate			= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate				= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			type				= valueObject.getShort("type");
			vGroup				= valueObject.getLong("vGroup",0);
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			sevenDaysBackDate	= LocalDate.now().minusDays(7)+"";
			eightDaysBackDate	= LocalDate.now().minusDays(8)+"";
			forteenDaysBackDate	= LocalDate.now().minusDays(14)+"";
			fifteenDaysBackDate	= LocalDate.now().minusDays(15)+"";
			todaysStrDate		= LocalDate.now()+"";
			
			if(vGroup>0) {
				vGroupQuery=" AND V.vehicleGroupId ="+vGroup+" "; 
			}
			
			switch (type) {
			 
			case ISSUE_CREATED:
				  issueStatusQuery	= " I.ISSUES_REPORTED_DATE BETWEEN '"+startDate+"' AND '"+endDate+"'";
				  issue	= issuesService.getIssueDetailsBetweenDatesByStatus(compId, issueStatusQuery,vGroupQuery);// WOStatusQuery is ""
				  
				  if(issue != null)
					valueOutObject.put("issue", issue);
			        break;
			        
			  case ISSUE_OPEN:
				  issueStatusQuery = " I.ISSUES_REPORTED_DATE BETWEEN '"+startDate+"' AND '"+endDate+"'AND I.ISSUES_STATUS_ID ="+ISSUE_OPEN+" ";
				  issue	= issuesService.getIssueDetailsBetweenDatesByStatus(compId, issueStatusQuery,vGroupQuery);

				  if(issue != null)
					valueOutObject.put("issue", issue);
				    break;
			        
			  case ISSUE_CLOSED: 
				  issueStatusQuery = "I.ISSUES_REPORTED_DATE BETWEEN '"+startDate+"' AND '"+endDate+"' AND I.ISSUES_STATUS_ID ="+ISSUE_CLOSED+" ";
				  issue	= issuesService.getIssueDetailsBetweenDatesByStatus(compId, issueStatusQuery,vGroupQuery);
				  
				  if(issue != null)
					valueOutObject.put("issue", issue);
			        break;
			        
			  case ISSUE_IN_PROCESS: 
				  issueStatusQuery = " I.ISSUES_REPORTED_DATE BETWEEN '"+startDate+"' AND '"+endDate+"' AND I.ISSUES_STATUS_ID IN("+ISSUE_IN_PROCESS+", "+ISSUE_IN_SE_CREATED+") ";
				  issue	= issuesService.getIssueDetailsBetweenDatesByStatus(compId, issueStatusQuery,vGroupQuery);
				  
				  if(issue != null)
					valueOutObject.put("issue", issue);
			        break;
			        
			  case ISSUE_RESOLVED: 
				  issueStatusQuery = " I.ISSUES_REPORTED_DATE BETWEEN '"+startDate+"' AND '"+endDate+"' AND I.ISSUES_STATUS_ID ="+ISSUE_RESOLVED+" ";
				  issue	= issuesService.getIssueDetailsBetweenDatesByStatus(compId, issueStatusQuery,vGroupQuery);
				 
				  if(issue != null)
					valueOutObject.put("issue", issue);
				    break;
			 
			  case ISSUE_REJECTED: // No Status ie WOStatusQuery is blank Hense Return All Status Details
				  issueStatusQuery = " I.ISSUES_REPORTED_DATE BETWEEN '"+startDate+"' AND '"+endDate+"' AND I.ISSUES_STATUS_ID ="+ISSUE_REJECTED+" ";
				  issue	= issuesService.getIssueDetailsBetweenDatesByStatus(compId, issueStatusQuery,vGroupQuery);
				  
				  if(issue != null)
					valueOutObject.put("issue", issue);
				    break;
			
			  case ISSUE_OVREDUE: // No Status ie WOStatusQuery is blank Hense Return All Status Details
				  issueStatusQuery = " I.ISSUES_REPORTED_DATE <= '"+todaysStrDate+""+DateTimeUtility.DAY_END_TIME+"' AND I.ISSUES_STATUS_ID IN ("+IssuesTypeConstant.ISSUES_STATUS_OPEN+", "+IssuesTypeConstant.ISSUES_STATUS_WOCREATED+", "+IssuesTypeConstant.ISSUES_STATUS_SE_CREATED+") ";
				  issue	= issuesService.getIssueDetailsBetweenDatesByStatus(compId, issueStatusQuery,vGroupQuery);
				 
				  if(issue != null)
					valueOutObject.put("issue", issue);
				    break;
				    
			  case ISSUE_ALL_OPEN:
					issueStatusQuery	= " I.ISSUES_STATUS_ID IN ("+IssuesTypeConstant.ISSUES_STATUS_OPEN+", "+IssuesTypeConstant.ISSUES_STATUS_WOCREATED+")";
					issue	= issuesService.getIssueDetailsBetweenDatesByStatus(compId, issueStatusQuery,vGroupQuery);// WOStatusQuery is ""
					
					if(issue != null)
						valueOutObject.put("issue", issue);
					break;	    
			 
			  case ISSUE_0_TO_7: 
				  issueStatusQuery	= " I.ISSUES_REPORTED_DATE BETWEEN '"+sevenDaysBackDate + DateTimeUtility.DAY_START_TIME+"' AND '"+ todaysStrDate+" "+DateTimeUtility.DAY_END_TIME+"' AND I.ISSUES_STATUS_ID IN ("+IssuesTypeConstant.ISSUES_STATUS_OPEN+", "+IssuesTypeConstant.ISSUES_STATUS_WOCREATED+", "+IssuesTypeConstant.ISSUES_STATUS_SE_CREATED+")";
				  issue	= issuesService.getIssueDetailsBetweenDatesByStatus(compId, issueStatusQuery,vGroupQuery);
				  
				  if(issue != null)
					valueOutObject.put("issue", issue);
				    break;
			 
			  case ISSUE_8_TO_15: 
				  issueStatusQuery	= " I.ISSUES_REPORTED_DATE BETWEEN '"+forteenDaysBackDate + DateTimeUtility.DAY_START_TIME+"' AND '"+ eightDaysBackDate+" "+DateTimeUtility.DAY_END_TIME+"' AND I.ISSUES_STATUS_ID IN ("+IssuesTypeConstant.ISSUES_STATUS_OPEN+", "+IssuesTypeConstant.ISSUES_STATUS_WOCREATED+", "+IssuesTypeConstant.ISSUES_STATUS_SE_CREATED+")";
				  issue	= issuesService.getIssueDetailsBetweenDatesByStatus(compId, issueStatusQuery,vGroupQuery);
				  
				  if(issue != null)
					valueOutObject.put("issue", issue);
				    break;
			  
			  case ISSUE_BEFORE_15Days: 
				  issueStatusQuery	= " I.ISSUES_REPORTED_DATE <= '"+fifteenDaysBackDate+""+DateTimeUtility.DAY_END_TIME+"' AND I.ISSUES_STATUS_ID IN ("+IssuesTypeConstant.ISSUES_STATUS_OPEN+", "+IssuesTypeConstant.ISSUES_STATUS_WOCREATED+", "+IssuesTypeConstant.ISSUES_STATUS_SE_CREATED+") ";
				  issue	= issuesService.getIssueDetailsBetweenDatesByStatus(compId, issueStatusQuery,vGroupQuery);
				  
				  if(issue != null)
					valueOutObject.put("issue", issue);
				    break;
			 
			  default:
			        break;
			}
			return valueOutObject;
			
		} catch (Exception e) {
			LOGGER.error("getIssueTableData"+e);
			throw e;
		}finally {
			compId										= 0;                                                 
			startDate									= null;                                              
			endDate										= null;                                              
			sevenDaysBackDate 							= null;                                              
			eightDaysBackDate 							= null;                                              
			fifteenDaysBackDate							= null;                                              
			type										= 0;                                                 
			issue										= null;                                              
			issueStatusQuery							= "";        
			valueOutObject								= null;
		}
	}
	
	/*****************TripSheet Summary Data ****************/
	@Override
	public ValueObject getTripSheetDataCount(ValueObject valueObject) throws Exception {
		int							compId										= 0;
		String 						todaysDate									= null;
		String 						startDate									= null;
		String 						endDate										= null;
		long						noOfDaysInDateRange							= 0;
		long						tripsheetCreatedCount						= 0;
		long 						tripSheetCountsInOpenState					= 0;
		TripSheetDto 				tripSheetCountsInClosedState				= null;
		long						tripSheetClosedCount						= 0;
		long 						tripSheetCountsInAccountClosedState			= 0;
		long						totalRunCount								= 0;
		long						todaysTripOpenStatusCount					= 0;
		long						tripSheetDispatchedCount					= 0;
		long						tripSheetSavedCount							= 0;
		long						dayDiff										= 0;
		long						tripSheetNumber								= 0;
		long						oldestTripOpenId							= 0;
		TripSheetDto				tripSheetCountsOldestOpenFlavorOne			= null;
		TripSheetDto				tripSheetIncomeExpense						= null;
		TollExpensesDetailsDto		toll										= null;
		double						tripIncome									= 0.0;
		double						tripExpense									= 0.0;
		double						tollExpense									= 0.0;
		double						tripAdvance									= 0.0;
		long                        tripClosedToday                             = 0;
		try {
			compId		= valueObject.getInt("compId");
			todaysDate	= format2.format(new Date());
			startDate 	= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate   	= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			noOfDaysInDateRange = DateTimeUtility.getDayDiffBetweenTwoDates(DateTimeUtility.getTimeStamp(startDate,DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(endDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			
			Date today = format2.parse(todaysDate);		
			Timestamp timestampDate = new java.sql.Timestamp(today.getTime());
			
			tripsheetCreatedCount	 		= tripSheetService.getTripSheetCreatedCount(startDate, endDate, compId);
			tripSheetCountsInClosedState	= tripSheetService.getTripClosed(startDate, endDate, compId);
			
			int flavor = companyConfigurationService.getTripSheetFlavor(compId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
			   tripSheetCountsInOpenState			= tripSheetService.getTripInOpenState(startDate, endDate, compId);
			   tripSheetCountsInAccountClosedState	= tripSheetService.getTripAccountClosedCount(startDate, endDate, compId);
			   todaysTripOpenStatusCount 			= tripSheetService.getTodaysTripOpenStatusCount(todaysDate+DateTimeUtility.DAY_START_TIME, todaysDate+DateTimeUtility.DAY_END_TIME, compId);	
			   tripSheetDispatchedCount 			= tripSheetService.getTripSheetDispatchedCount(todaysDate+DateTimeUtility.DAY_END_TIME, compId);	
			   tripSheetSavedCount 					= tripSheetService.getTripSheetSavedCount(todaysDate+DateTimeUtility.DAY_END_TIME, compId);	
			   tripSheetCountsOldestOpenFlavorOne   = tripSheetService.getTripOldestOpenFlavorOne(compId, startDate);
			   tripSheetIncomeExpense				= tripSheetService.getTripIncomeAndExpense(startDate, endDate, compId);
			   totalRunCount						= tripSheetService.getTripTotalRunKM(startDate, endDate, compId);
			   toll									= TollExpensesDetailsService.getTripTotalTollExpenseAmount(startDate, endDate, compId);
			   tripClosedToday                      = tripSheetService.getTripSheetClosedTodayCount(startDate, endDate, compId);
			}
			
			
			if(tripSheetCountsInClosedState != null) {
				tripSheetClosedCount	= tripSheetCountsInClosedState.getTripSheetID();
			}
			
			if(tripSheetCountsOldestOpenFlavorOne != null) {
				dayDiff =	DateTimeUtility.getDayDiffBetweenTwoDates(new Timestamp(tripSheetCountsOldestOpenFlavorOne.getTripOpenDateOn().getTime()),timestampDate);
				tripSheetNumber = tripSheetCountsOldestOpenFlavorOne.getTripSheetNumber();
				oldestTripOpenId = tripSheetCountsOldestOpenFlavorOne.getTripSheetID();
			}
			
			if(toll.getTransactionAmount() != null) {
				tollExpense = toll.getTransactionAmount();
			}
			
			if(tripSheetIncomeExpense.getTripTotalAdvance() != null) {
				tripAdvance  = tripSheetIncomeExpense.getTripTotalAdvance();
			}
			
			if(tripSheetIncomeExpense.getTripTotalincome() != null) {
				tripIncome  = tripSheetIncomeExpense.getTripTotalincome();
			}
			
			if(tripSheetIncomeExpense.getTripTotalexpense() != null) {
				tripExpense  = tripSheetIncomeExpense.getTripTotalexpense();
			}
			
			tripExpense = tripExpense + tollExpense;
			
			valueObject.put("tripsheetCreatedCount", tripsheetCreatedCount);
			valueObject.put("tripSheetCountsInOpenState", tripSheetCountsInOpenState);
			valueObject.put("tripSheetClosedCount", tripSheetClosedCount);
			valueObject.put("tripSheetCountsInAccountClosedState", tripSheetCountsInAccountClosedState);
			valueObject.put("totalRunCount", totalRunCount);
			
			valueObject.put("tripIncome", tripIncome);
			valueObject.put("tripExpense", tripExpense);
			valueObject.put("tripAdvance", tripAdvance);
			
			valueObject.put("todaysTripOpenStatusCount", todaysTripOpenStatusCount);
			valueObject.put("tripSheetDispatchedCount", tripSheetDispatchedCount);
			valueObject.put("tripSheetSavedCount", tripSheetSavedCount);
			valueObject.put("tripSheetNumber", tripSheetNumber);
			valueObject.put("oldestTripOpenId", oldestTripOpenId);
			valueObject.put("dayDiff", dayDiff);
			valueObject.put("noOfDaysInDateRange", noOfDaysInDateRange);
			valueObject.put("tripClosedToday",tripClosedToday);
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			tripSheetCountsInClosedState		= null;
		}
	}
	
	@Override
	public ValueObject getTripSheetTableData(ValueObject valueObject) throws Exception {
		int							compId										= 0;
		String 						startDate									= null;
		String 						endDate										= null;
		String 						todaysDate									= null;
		short						type										= 0;
		List<TripSheetDto>			tripSheet									= null;
		final short					TRIP_SHEET_CREATED							= 1;
		final short					TRIP_USAGE_KM								= 5;
		final short					TRIP_LEFT_TO_CLOSE							= 6;
		final short					DISPATCHED_TRIP								= 7;
		final short					SAVED_TRIP									= 8;
		boolean 					closed										= false;
		try {
			compId				= valueObject.getInt("compId");
			startDate 			= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate   			= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
		
			closed 				= valueObject.getBoolean("newClosed");
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			todaysDate			= format2.format(new Date());
			type				= valueObject.getShort("type");

			switch (type) {
			
			  case TRIP_SHEET_CREATED:
				  tripSheet	= tripSheetService.getTotalTripSheetCreatedDetailsBetweenDates(startDate, endDate, compId);
				  if(tripSheet != null)
				  valueObject.put("tripSheetCreated", tripSheet);
			        break;
			        
			  case TripSheetStatus.TRIP_STATUS_DISPATCHED: 
				  tripSheet	= tripSheetService.getTotalTripSheetOpenDetailsBetweenDates(startDate, endDate, compId);
				  valueObject.put("tripSheetOpen", tripSheet);
			        break;
			        
			  case TripSheetStatus.TRIP_STATUS_CLOSED: 

				  if(closed) {
					  tripSheet	= tripSheetService.getTotalTripSheetClosedTodayDetailsBetweenDates(startDate, endDate, compId);
					  valueObject.put("tripSheetClosed", tripSheet);
				      break;
				  }
				  else{
					  tripSheet	= tripSheetService.getTotalTripSheetClosedDetailsBetweenDates(startDate, endDate, compId);
					  valueObject.put("tripSheetClosed", tripSheet);
				      break;
				  }				  
					  
			  case TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED: 
				  tripSheet	= tripSheetService.getTotalTripSheetAcntClosedDetailsBetweenDates(startDate, endDate, compId);
				  valueObject.put("tripSheetAccountClosed", tripSheet);
			        break;
			        
			  case TRIP_USAGE_KM: 
				  tripSheet	= tripSheetService.getTotalRunKmList(startDate, endDate, compId);
				  valueObject.put("tripSheetUsageKM", tripSheet);
				  break;
			  
			  case TRIP_LEFT_TO_CLOSE: 
				  tripSheet	= tripSheetService.getTodaysTripOpenStatusList(todaysDate+DateTimeUtility.DAY_START_TIME, todaysDate+DateTimeUtility.DAY_END_TIME, compId);
				  valueObject.put("tripMissedClosing", tripSheet);
				  break;
				  
			  case DISPATCHED_TRIP: 
				  tripSheet	= tripSheetService.getTripSheetDispatchedOverdueList(todaysDate+DateTimeUtility.DAY_END_TIME, compId);
				  valueObject.put("tripMissedClosing", tripSheet);
				  break;
				  
			  case SAVED_TRIP: 
				  tripSheet	= tripSheetService.getTripSheetSavedOverdueList(todaysDate+DateTimeUtility.DAY_END_TIME, compId);
				  valueObject.put("tripMissedClosing", tripSheet);
				  break;		  
			 
			  default:
			        break;
			}
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			tripSheet	= null;
		}
	}
	
	/*****************Renewal Reminder Summary Data*************/
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject getRenewalReminderDataCount(ValueObject valueObject) throws Exception {
		int							compId										= 0;
		String 						todaysDate									= null;
		String 						startDateWithTime							= null;
		String 						endDateWithTime								= null;
		long 						totalVehicleCount							= 0;
		long 						renewalCreatedOnVehicle						= 0;
		long 						renewalNotCreatedOnVehicle					= 0;
		long						renewalCreatedCount							= 0;
		long						todaysRenewalCount							= 0;
		long						totalDueSoonCount							= 0;
		double						totalRenewalExpense							= 0;
		long						totalOverDueCount							= 0;
		long						totalSevenDaysCount							= 0;
		long						totalFifteenDaysCount						= 0;
		long						totalFifteenPlusDaysCount					= 0;
		ValueObject					totalFifteenPlusByVStatus					= null;
		String						sevenDaysBackDate							= null;
		String						fourteenDaysBackDate						= null;
		long						mandateAndNonMandate						= 0;
		ValueObject						dueSoonCountByVStatus						= null;
		ValueObject						overDueCountByVStatus						= null;
		HashMap<Short, Long> 			dueSoonCountHM								= null;	
		HashMap<Short, Long> 			overDueCountHM								= null;	
		HashMap<Short, Long> 			fifteenPlusoverDueCountHM					= null;	
		try {
			compId				 = valueObject.getInt("compId");
			todaysDate			 = format2.format(new Date());
			startDateWithTime	 = valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDateWithTime		 = valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			
			startDateWithTime = DateTimeUtility.formatDate(startDateWithTime, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDateWithTime   = DateTimeUtility.formatDate(endDateWithTime, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			sevenDaysBackDate 	 = DateTimeUtility.getBackDateBasedOnGivenDate(todaysDate,7);
			fourteenDaysBackDate = DateTimeUtility.getBackDateBasedOnGivenDate(todaysDate,14);
		    
		    Timestamp currentMonthFirstDate = DateTimeUtility.getFirstDayOfMonth(DateTimeUtility.getTimeStamp(todaysDate,DateTimeUtility.YYYY_MM_DD));
			Timestamp currentMonthLastDate  = DateTimeUtility.getLastDayOfMonth(DateTimeUtility.getTimeStamp(todaysDate+DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			
			ValueObject thisMonthCount    = RenewalReminderService.getMonthlyRenwalCountAndAmount(currentMonthFirstDate+"", currentMonthLastDate+"", compId);
			long thisMonthRenewalCount    = thisMonthCount.getLong("renewalCount");
			double thisMonthRenewalAmount = thisMonthCount.getDouble("renewalAmount");
			
			ValueObject nextMonthDates = DateTimeUtility.getNextMonthStartAndEndDate();
			String nextMonthFirstDate  = format2.format(format.parse(nextMonthDates.getString("startOfMonth")));
			String nextMonthLastDate   = format2.format(format.parse(nextMonthDates.getString("endOfMonth")));
			
			ValueObject nextMonthCount    = RenewalReminderService.getMonthlyRenwalCountAndAmount(nextMonthFirstDate+DateTimeUtility.DAY_START_TIME, nextMonthLastDate+DateTimeUtility.DAY_END_TIME, compId);
			long nextMonthRenewalCount    = nextMonthCount.getLong("renewalCount");
			double nextMonthRenewalAmount = nextMonthCount.getDouble("renewalAmount");
			
			int thisMonth = DateTimeUtility.getMonthFromDate(currentMonthFirstDate);
			int nextMonth = DateTimeUtility.getMonthFromDate(DateTimeUtility.getTimeStamp(nextMonthFirstDate,DateTimeUtility.YYYY_MM_DD));
			
			totalVehicleCount 	       = VehicleRepository.countTotalVehicles(compId);
			renewalCreatedOnVehicle    = RenewalReminderRepository.distinctRenewalRemndCreatedOnVehicles(compId);
			renewalNotCreatedOnVehicle = totalVehicleCount - renewalCreatedOnVehicle;
			mandateAndNonMandate	   = totalVehicleCount;
			
			renewalCreatedCount = RenewalReminderService.getRenewalReminderCount(startDateWithTime, endDateWithTime, compId);
			todaysRenewalCount = RenewalReminderService.todaysTotalRenewalCount(startDateWithTime, endDateWithTime, compId);
			
			
			dueSoonCountByVStatus		= RenewalReminderService.totalDueSoonCount(todaysDate+DateTimeUtility.DAY_END_TIME,compId);
			dueSoonCountHM				= (HashMap<Short, Long>) dueSoonCountByVStatus.get("dueSoonCountHM");
			if(dueSoonCountHM != null ) {
				for(Entry<Short, Long> status : dueSoonCountHM.entrySet()){
					totalDueSoonCount += status.getValue() ;
				}
			}
			
			overDueCountByVStatus 		= RenewalReminderService.getTotalOverDueRenewalCount(todaysDate+DateTimeUtility.DAY_START_TIME, compId);
			totalFifteenPlusByVStatus 	= RenewalReminderService.getTotalOverDueRenewalCount(fourteenDaysBackDate+ DateTimeUtility.DAY_START_TIME, compId);
			overDueCountHM				= (HashMap<Short, Long>) overDueCountByVStatus.get("overDueCountHM");
			fifteenPlusoverDueCountHM	= (HashMap<Short, Long>) totalFifteenPlusByVStatus.get("overDueCountHM");
			if(overDueCountHM != null ) {
				for(Entry<Short, Long> status : overDueCountHM.entrySet()){
					totalOverDueCount += status.getValue() ;
					}
			}
			
			if(fifteenPlusoverDueCountHM != null ) {
				for(Entry<Short, Long> status : fifteenPlusoverDueCountHM.entrySet()){
					totalFifteenPlusDaysCount += status.getValue() ;
				}
			}
			
			totalRenewalExpense 		= RenewalReminderService.totalRenewalExpense(todaysDate, compId);
			totalSevenDaysCount = RenewalReminderService.getTotalOverDueRenewalCountBetweenDates(sevenDaysBackDate + DateTimeUtility.DAY_END_TIME, todaysDate+DateTimeUtility.DAY_START_TIME, compId);
			totalFifteenDaysCount = RenewalReminderService.getTotalOverDueRenewalCountBetweenDates(fourteenDaysBackDate + DateTimeUtility.DAY_END_TIME, sevenDaysBackDate+ DateTimeUtility.DAY_START_TIME, compId);
			
			valueObject.put("totalVehicleCount", totalVehicleCount);
			valueObject.put("renewalCreatedOnVehicle", renewalCreatedOnVehicle);
			valueObject.put("renewalNotCreatedOnVehicle", renewalNotCreatedOnVehicle);
			valueObject.put("mandateAndNonMandate", mandateAndNonMandate);
			
			valueObject.put("renewalCreatedCount", renewalCreatedCount);
			valueObject.put("todaysRenewalCount", todaysRenewalCount);
			
			valueObject.put("totalDueSoonCount", totalDueSoonCount);
			valueObject.put("totalRenewalExpense", totalRenewalExpense);
			valueObject.put("thisMonthRenewalCount", thisMonthRenewalCount);
			valueObject.put("thisMonthRenewalAmount", thisMonthRenewalAmount);
			valueObject.put("nextMonthRenewalCount", nextMonthRenewalCount);
			valueObject.put("nextMonthRenewalAmount", nextMonthRenewalAmount);
			valueObject.put("thisMonth", thisMonth);
			valueObject.put("nextMonth", nextMonth);
			
			valueObject.put("totalOverDueCount", totalOverDueCount);
			valueObject.put("totalSevenDaysCount", totalSevenDaysCount);
			valueObject.put("totalFifteenDaysCount", totalFifteenDaysCount);
			valueObject.put("totalFifteenPlusDaysCount", totalFifteenPlusDaysCount);
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			todaysDate	= null;
		}
	}	
	
	@Override
	public ValueObject  getRenewalReminderTableData(ValueObject valueObject) throws Exception {
		Integer						companyID										= 0;
		String 						todaysDate										= null;
		String 						startDateWithTime								= null;
		String 						endDateWithTime									= null;
		String						sevenDaysBackDate								= null;
		String						fourteenDaysBackDate							= null;
		short						type											= 0;
		long						mandatoryCount									= 0;
		List<RenewalReminderDto>    renewalReminder									= null; 
		List<RenewalReminderDto>    renewalReminderGroupByType						= null; 
		String						query											= "";
		List<VehicleDto>    		vehicleList										= null;
		List<RenewalReminder>		rrCreatedList									= null;
		StringBuilder 				result 											= new StringBuilder();
		
		final short					RENEWAL_CREATED									= 1;
		final short					TODAY_RENEWAL									= 2;
		final short					RENEWAL_DUESOON									= 3;
		final short                 RENEWAL_TOTAL_EXPENSE							= 4;
		final short                 THIS_MONTH										= 5;
		final short                 NEXT_MONTH										= 6;
		final short					RENEWAL_OVERDUE									= 7;
		final short					RENEWAL_OVERDUE_ZERO_TO_SEVEN_DAYS_BACK			= 8;
		final short					RENEWAL_OVERDUE_EIGHT_TO_FIFTEEN_DAYS_BACK  	= 9;
		final short					RENEWAL_OVERDUE_FIFTEEN_DAYS_BACK_AND_ABOVE 	= 10;
		final short					MANDATORY_AND_NON_MANDATORY 					= 11;
		
		try {
			companyID				= valueObject.getInt("compId");
			todaysDate			 	= format2.format(new Date()); 
			startDateWithTime	 	= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDateWithTime		 	= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			type					= valueObject.getShort("type");
			
			startDateWithTime = DateTimeUtility.formatDate(startDateWithTime, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDateWithTime   = DateTimeUtility.formatDate(endDateWithTime, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			Timestamp currentMonthFirstDate = DateTimeUtility.getFirstDayOfMonth(DateTimeUtility.getTimeStamp(todaysDate,DateTimeUtility.YYYY_MM_DD));
			Timestamp currentMonthLastDate  = DateTimeUtility.getLastDayOfMonth(DateTimeUtility.getTimeStamp(todaysDate+DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			
			ValueObject nextMonthDates = DateTimeUtility.getNextMonthStartAndEndDate();
			String nextMonthFirstDate  = format2.format(format.parse(nextMonthDates.getString("startOfMonth")));
			String nextMonthLastDate   = format2.format(format.parse(nextMonthDates.getString("endOfMonth")));
			
			sevenDaysBackDate 		= DateTimeUtility.getBackDateBasedOnGivenDate(todaysDate,7);
			fourteenDaysBackDate 	= DateTimeUtility.getBackDateBasedOnGivenDate(todaysDate,14);
			
			switch (type) {
			
			case	RENEWAL_CREATED:  
				
					renewalReminder = RenewalReminderService.getListOfNumberOfRRCreatedOnVehicles(companyID);
					
					if(renewalReminder != null) {
						valueObject.put("renewalReminder", renewalReminder);	
					}
					break;
					
			case	TODAY_RENEWAL: 
			
					rrCreatedList = RenewalReminderRepository.listRenewalRemndCreatedOnVehicles(companyID);
					
					if(!rrCreatedList.isEmpty()) {
						for(int i =0; i <rrCreatedList.size() ; i++) 
					    { 
							 result.append(rrCreatedList.get(i)+"");
						  	 result.append(","); 
					    }
					    
					    query = result.substring(0, result.length() - 1);
					} else {
						query = "0";
					}
			    
				    vehicleList = VehicleService.getListOfNumberOfRRNotCreatedOnVehicles(query, companyID);
					
					if(vehicleList != null) {
						valueObject.put("renewalReminder", vehicleList);	
					}
					break;
					
			case	RENEWAL_DUESOON: 
				
				query = " R.dateofRenewal <= '"+todaysDate+DateTimeUtility.DAY_END_TIME+"' AND R.renewal_to > '"+todaysDate+DateTimeUtility.DAY_END_TIME+"' "+" AND R.newRRCreated = 0 AND ";
				
				renewalReminder = RenewalReminderService.getRenewalReminderTableListBetweenDates(query,companyID);
				renewalReminderGroupByType = RenewalReminderService.getRenewalReminderGroupByRenewalTypeBetweenDates(query,companyID);
				
				if(renewalReminder != null) {
					valueObject.put("renewalReminder", renewalReminder);	
				}
				
				if(renewalReminderGroupByType != null) {
					valueObject.put("renewalReminderGroupByType", renewalReminderGroupByType);	
				}
				break;	
			
			case	RENEWAL_TOTAL_EXPENSE:  
				
				query = " R.renewal_from < '"+todaysDate+"' ";									
				renewalReminder = RenewalReminderService.getRenewalReminderTableListBetweenDates(query,companyID);
				
				if(renewalReminder != null) {
					valueObject.put("renewalReminder", renewalReminder);	
				}
				break;
				
			case	THIS_MONTH:  
				
				query = " R.renewal_to BETWEEN '"+currentMonthFirstDate+DateTimeUtility.DAY_START_TIME+"' AND '"+currentMonthLastDate+DateTimeUtility.DAY_END_TIME+"' ";									
				
				renewalReminder = RenewalReminderService.getRenewalReminderTableListBetweenDates(query,companyID);
				renewalReminderGroupByType = RenewalReminderService.getRenewalReminderGroupByRenewalTypeBetweenDates(query,companyID);
				
				if(renewalReminder != null) {
					valueObject.put("renewalReminder", renewalReminder);	
				}
				
				if(renewalReminderGroupByType != null) {
					valueObject.put("renewalReminderGroupByType", renewalReminderGroupByType);	
				}
				break;
				
			case	NEXT_MONTH:  
				
				query = " R.renewal_to BETWEEN '"+nextMonthFirstDate+DateTimeUtility.DAY_START_TIME+"' AND '"+nextMonthLastDate+DateTimeUtility.DAY_END_TIME+"' ";									
				
				renewalReminder = RenewalReminderService.getRenewalReminderTableListBetweenDates(query,companyID);
				renewalReminderGroupByType = RenewalReminderService.getRenewalReminderGroupByRenewalTypeBetweenDates(query,companyID);
				
				if(renewalReminder != null) {
					valueObject.put("renewalReminder", renewalReminder);	
				}
				
				if(renewalReminderGroupByType != null) {
					valueObject.put("renewalReminderGroupByType", renewalReminderGroupByType);	
				}
				break;
					
			case	RENEWAL_OVERDUE: 
				
					query = " R.renewal_to <= '"+todaysDate+DateTimeUtility.DAY_START_TIME+"' ";
					
					renewalReminder = RenewalReminderService.getRenewalReminderTableListBetweenDates(query,companyID);
					renewalReminderGroupByType = RenewalReminderService.getRenewalReminderGroupByRenewalTypeBetweenDates(query,companyID);
					
					if(renewalReminder != null) {
						valueObject.put("renewalReminder", renewalReminder);	
					}
					
					if(renewalReminderGroupByType != null) {
						valueObject.put("renewalReminderGroupByType", renewalReminderGroupByType);	
					}
					break;
			
			case 	RENEWAL_OVERDUE_ZERO_TO_SEVEN_DAYS_BACK: 
					
					query = " R.renewal_to BETWEEN '"+sevenDaysBackDate + DateTimeUtility.DAY_END_TIME+"' AND '"+todaysDate+DateTimeUtility.DAY_START_TIME+"' ";
				
					renewalReminder = RenewalReminderService.getRenewalReminderTableListBetweenDates(query,companyID);
					renewalReminderGroupByType = RenewalReminderService.getRenewalReminderGroupByRenewalTypeBetweenDates(query,companyID);
				
					if(renewalReminder != null) {
						valueObject.put("renewalReminder", renewalReminder);	
					}
					
					if(renewalReminderGroupByType != null) {
						valueObject.put("renewalReminderGroupByType", renewalReminderGroupByType);	
					}
					break;
					
			case 	RENEWAL_OVERDUE_EIGHT_TO_FIFTEEN_DAYS_BACK:  
					
					query = " R.renewal_to BETWEEN '"+fourteenDaysBackDate+DateTimeUtility.DAY_END_TIME+"' AND '"+sevenDaysBackDate+ DateTimeUtility.DAY_START_TIME+"' ";
					
					renewalReminder = RenewalReminderService.getRenewalReminderTableListBetweenDates(query,companyID);
					renewalReminderGroupByType = RenewalReminderService.getRenewalReminderGroupByRenewalTypeBetweenDates(query,companyID);
					
					if(renewalReminder != null) {
						valueObject.put("renewalReminder", renewalReminder);	
					}
					
					if(renewalReminderGroupByType != null) {
						valueObject.put("renewalReminderGroupByType", renewalReminderGroupByType);	
					}
					break;
					
			case	RENEWAL_OVERDUE_FIFTEEN_DAYS_BACK_AND_ABOVE: 
				
					query = " R.renewal_to < '"+fourteenDaysBackDate+ DateTimeUtility.DAY_START_TIME+"' ";
					
					renewalReminder = RenewalReminderService.getRenewalReminderTableListBetweenDates(query,companyID);
					renewalReminderGroupByType = RenewalReminderService.getRenewalReminderGroupByRenewalTypeBetweenDates(query,companyID);
					
					if(renewalReminder != null) {
						valueObject.put("renewalReminder", renewalReminder);	
					}
					
					if(renewalReminderGroupByType != null) {
						valueObject.put("renewalReminderGroupByType", renewalReminderGroupByType);	
					}
					break;
			
			case	MANDATORY_AND_NON_MANDATORY:  
				
				mandatoryCount  = RenewalSubTypeRepository.totalMandatorySubTypeRenewals(companyID);
				renewalReminder = RenewalReminderService.getListOfAllRRVehicleWise(companyID);
				
				if(renewalReminder != null) {
					valueObject.put("mandatoryCount", mandatoryCount);	
					valueObject.put("renewalReminder", renewalReminder);	
				}
				break;		
				
			default:
					System.err.println("This is Default");
					break;
				
			}
			
			return valueObject;
		}
		catch(Exception e){
			throw e;
		}finally {
			renewalReminder	= null;
		}
	}
	
	/*************Service Reminder Summary Data************************/
	
	@Override
	public ValueObject getServiceReminderDataCount(ValueObject valueObject) throws Exception {
		int											compId										= 0;
		String 										todaysDate									= null;
		String 										startDateWithTime							= null;
		String 										endDateWithTime								= null;
		long 										totalVehicleCount							= 0;
		long 										serviceReminderCreated						= 0;
		long 										serviceReminderNotCreated					= 0;
		long										serviceCreatedCount							= 0;
		long										todaysServiceCount							= 0;
		long										totalOverDueCount							= 0;
		long										totalDueSoonCount							= 0;
		long										totalSevenDaysCount							= 0;
		long										totalFifteenDaysCount						= 0;
		long										totalFifteenPlusDaysCount					= 0;
		String										sevenDaysBackDate							= null;
		String										eightDaysBackDate							= null;
		String										fourteenDaysBackDate						= null;
		String										fifteenDaysBackDate							= null;
		List<ServiceReminder>						serviceids									= null;
		List<ServiceReminder>						serviceidsFifteenDays						= null;
		StringBuilder 								result 										= new StringBuilder();
		StringBuilder 								finalResult 								= new StringBuilder();
		String										query										= null;
		String										finalQuery									= null;
		try {
			compId				 = valueObject.getInt("compId");
			serviceids			 = new ArrayList<ServiceReminder>();
			todaysDate			 = format2.format(new Date());
			startDateWithTime	 = valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDateWithTime		 = valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			
			startDateWithTime = DateTimeUtility.formatDate(startDateWithTime, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDateWithTime   = DateTimeUtility.formatDate(endDateWithTime, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			sevenDaysBackDate 	 = DateTimeUtility.getBackDateBasedOnGivenDate(todaysDate,7);
			eightDaysBackDate 	 = DateTimeUtility.getBackDateBasedOnGivenDate(todaysDate,8);
			fourteenDaysBackDate = DateTimeUtility.getBackDateBasedOnGivenDate(todaysDate,14);
			fifteenDaysBackDate  = DateTimeUtility.getBackDateBasedOnGivenDate(todaysDate,15);
			
			totalVehicleCount 	      = VehicleRepository.countTotalVehicles(compId);
			serviceReminderCreated 	  = ServiceReminderRepository.distinctServRemndCreatedOnVehicles(compId);
			serviceReminderNotCreated = totalVehicleCount - serviceReminderCreated;
			
			serviceCreatedCount = serviceReminderService.getTotalServiceReminderCount(startDateWithTime, endDateWithTime, compId);
			todaysServiceCount  = serviceReminderService.todaysServiceCount(startDateWithTime, endDateWithTime, compId);
			
			totalDueSoonCount = serviceReminderService.totalDueSoonCount(todaysDate+ DateTimeUtility.DAY_END_TIME, compId);
			
			totalOverDueCount = serviceReminderService.getTotalServiceReminderOverDueCount(todaysDate+ DateTimeUtility.DAY_END_TIME, compId);
			totalSevenDaysCount = serviceReminderService.getTotalServiceReminderOverDueCountBetweenDates(sevenDaysBackDate + DateTimeUtility.DAY_START_TIME, todaysDate+ DateTimeUtility.DAY_END_TIME, compId);
			
			serviceids = serviceReminderService.getTotalServiceReminderOverDueListBetweenDates(sevenDaysBackDate + DateTimeUtility.DAY_START_TIME, todaysDate+ DateTimeUtility.DAY_START_TIME, compId);
			
			for(ServiceReminder serv : serviceids) {
				result.append(serv.getService_id()+"");
			  	result.append(",");
			}
		    
		    query = result.substring(0, result.length() - 1);
			
			totalFifteenDaysCount = serviceReminderService.getDistinctTotalServiceReminderOverDueCountBetweenDates(fourteenDaysBackDate + DateTimeUtility.DAY_START_TIME, eightDaysBackDate+ DateTimeUtility.DAY_START_TIME, query, compId);
			serviceidsFifteenDays = serviceReminderService.getDistinctTotalServiceReminderOverDueListBetweenDates(fourteenDaysBackDate + DateTimeUtility.DAY_START_TIME, eightDaysBackDate+ DateTimeUtility.DAY_START_TIME, query, compId);
			
			ServiceReminder list;
			for(ServiceReminder serve : serviceidsFifteenDays) {
				list = new ServiceReminder();
				list.setService_id(serve.getService_id());
				serviceids.add(list);
			}
			
			for(ServiceReminder serv : serviceids) {
				finalResult.append(serv.getService_id()+"");
				finalResult.append(",");
			}
		    
			finalQuery = finalResult.substring(0, finalResult.length() - 1);
			
			totalFifteenPlusDaysCount = serviceReminderService.getDistinctTotalServiceReminderOverDueCount(fifteenDaysBackDate+ DateTimeUtility.DAY_END_TIME, finalQuery, compId);
			
			valueObject.put("totalVehicleCount", totalVehicleCount);
			valueObject.put("serviceReminderCreated", serviceReminderCreated);
			valueObject.put("serviceReminderNotCreated", serviceReminderNotCreated);
			
			valueObject.put("serviceCreatedCount", serviceCreatedCount);
			valueObject.put("todaysServiceCount", todaysServiceCount);
			
			valueObject.put("totalDueSoonCount", totalDueSoonCount);
			
			valueObject.put("totalOverDueCount", totalOverDueCount);
			valueObject.put("totalSevenDaysCount", totalSevenDaysCount);
			valueObject.put("totalFifteenDaysCount", totalFifteenDaysCount);
			valueObject.put("totalFifteenPlusDaysCount", totalFifteenPlusDaysCount);
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			todaysDate	= null;
		}
	}	
	
	@Override
	public ValueObject  getServiceReminderTableData(ValueObject valueObject) throws Exception {
		Integer						companyID										= 0;
		String 						todaysDate										= null;
		String 						startDateWithTime								= null;
		String 						endDateWithTime									= null;
		String						sevenDaysBackDate								= null;
		String						eightDaysBackDate								= null;
		String						fourteenDaysBackDate							= null;
		String						fifteenDaysBackDate								= null;
		short						type											= 0;
		List<ServiceReminderDto>    serviceReminder									= null; 
		List<VehicleDto>    		vehicleList										= null; 
		String						query											= "";
		List<ServiceReminder>		srCreatedList									= null;
		StringBuilder 				result 											= new StringBuilder();
		List<ServiceReminder>		serviceids									    = null;
		List<ServiceReminder>		serviceidsFifteenDays							= null;
		StringBuilder 				firstResult 									= new StringBuilder();
		StringBuilder 				finalResult 									= new StringBuilder();
		String						firstQuery										= null;
		String						finalQuery										= null;
		
		final short					SERVICE_CREATED									= 1;
		final short					TODAY_SERVICE									= 2;
		final short					SERVICE_DUESOON									= 3;
		final short					SERVICE_OVERDUE									= 4;
		final short					SERVICE_OVERDUE_ZERO_TO_SEVEN_DAYS_BACK			= 5;
		final short					SERVICE_OVERDUE_EIGHT_TO_FIFTEEN_DAYS_BACK  	= 6;
		final short					SERVICE_OVERDUE_FIFTEEN_DAYS_BACK_AND_ABOVE 	= 7;
		
		try {
			companyID				= valueObject.getInt("compId");
			todaysDate			 	= format2.format(new Date());
			startDateWithTime	 	= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDateWithTime		 	= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			type					= valueObject.getShort("type");
			
			startDateWithTime = DateTimeUtility.formatDate(startDateWithTime, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDateWithTime   = DateTimeUtility.formatDate(endDateWithTime, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			sevenDaysBackDate 		= DateTimeUtility.getBackDateBasedOnGivenDate(todaysDate,7);
			eightDaysBackDate 		= DateTimeUtility.getBackDateBasedOnGivenDate(todaysDate,8);
			fourteenDaysBackDate 	= DateTimeUtility.getBackDateBasedOnGivenDate(todaysDate,14);
			fifteenDaysBackDate 	= DateTimeUtility.getBackDateBasedOnGivenDate(todaysDate,15);
			
			serviceids = serviceReminderService.getTotalServiceReminderOverDueListBetweenDates(sevenDaysBackDate + DateTimeUtility.DAY_START_TIME, todaysDate+ DateTimeUtility.DAY_START_TIME, companyID);
			
			for(ServiceReminder serv : serviceids) {
				firstResult.append(serv.getService_id()+"");
				firstResult.append(",");
			}
		    
			firstQuery = firstResult.substring(0, firstResult.length() - 1);
			
			serviceidsFifteenDays = serviceReminderService.getDistinctTotalServiceReminderOverDueListBetweenDates(fourteenDaysBackDate + DateTimeUtility.DAY_START_TIME, eightDaysBackDate+ DateTimeUtility.DAY_START_TIME, firstQuery, companyID);
			
			ServiceReminder list;
			for(ServiceReminder serve : serviceidsFifteenDays) {
				list = new ServiceReminder();
				list.setService_id(serve.getService_id());
				serviceids.add(list);
			}
			
			for(ServiceReminder serv : serviceids) {
				finalResult.append(serv.getService_id()+"");
				finalResult.append(",");
			}
		    
			finalQuery = finalResult.substring(0, finalResult.length() - 1);
			
			
			switch (type) {
			
			case	SERVICE_CREATED:  
				
					serviceReminder = serviceReminderService.getListOfNumberOfSRCreatedOnVehicles(companyID);
					
					if(serviceReminder != null) {
						valueObject.put("serviceReminder", serviceReminder);	
					}
					break;
					
			case	TODAY_SERVICE: 
				
					srCreatedList = ServiceReminderRepository.listServRemndCreatedOnActVehicles(companyID);
					
					if(!srCreatedList.isEmpty()) {
						for(int i =0; i <srCreatedList.size() ; i++) 
					    { 
							 result.append(srCreatedList.get(i)+"");
						  	 result.append(","); 
					    }
					    
					    query = result.substring(0, result.length() - 1);
					} else {
						query = "0";
					}
					
				    vehicleList = VehicleService.getListOfNumberOfSRNotCreatedOnVehicles(query,companyID);
					
					if(vehicleList != null) {
						valueObject.put("serviceReminder", vehicleList);	
					}
					break;
					
			case	SERVICE_DUESOON: 
				
					query = " (SR.time_servicethreshold_date <= '"+todaysDate+DateTimeUtility.DAY_END_TIME+"' AND  SR.time_servicedate >= '"+todaysDate+DateTimeUtility.DAY_END_TIME+"' OR "
						+ " SR.meter_servicethreshold_odometer <= SR.vehicle_currentOdometer AND SR.meter_serviceodometer >= SR.vehicle_currentOdometer)";
					serviceReminder = serviceReminderService.getServiceReminderTableListBetweenDates(query,companyID);
				
				if(serviceReminder != null) {
					valueObject.put("serviceReminder", serviceReminder);	
				}
				break;	
					
			case	SERVICE_OVERDUE: 
				
				query = " ( SR.time_servicedate <= '"+todaysDate+DateTimeUtility.DAY_END_TIME+"' OR "
						+ " (SR.serviceOdometerUpdatedDate <= '"+todaysDate+DateTimeUtility.DAY_END_TIME+"' ) ) ";
					serviceReminder = serviceReminderService.getServiceReminderTableListBetweenDates(query,companyID);
					
					if(serviceReminder != null) {
						valueObject.put("serviceReminder", serviceReminder);	
					}
					break;
			
			case 	SERVICE_OVERDUE_ZERO_TO_SEVEN_DAYS_BACK: 
					
					query = "( ( SR.time_servicedate between '"+sevenDaysBackDate+DateTimeUtility.DAY_START_TIME+"' AND '"+todaysDate+DateTimeUtility.DAY_END_TIME+"' ) "
							+ " OR (SR.serviceOdometerUpdatedDate between '"+sevenDaysBackDate+DateTimeUtility.DAY_START_TIME+"' AND '"+todaysDate+DateTimeUtility.DAY_END_TIME+"' "
							+ " AND SR.time_servicedate NOT between '"+sevenDaysBackDate+DateTimeUtility.DAY_START_TIME+"' AND '"+todaysDate+DateTimeUtility.DAY_END_TIME+"') ) ";
					serviceReminder = serviceReminderService.getServiceReminderTableListBetweenDates(query,companyID);
					
					if(serviceReminder != null) {
						valueObject.put("serviceReminder", serviceReminder);	
					}
					break;
					
			case 	SERVICE_OVERDUE_EIGHT_TO_FIFTEEN_DAYS_BACK:  
					
					query = "( SR.time_servicedate between '"+fourteenDaysBackDate+DateTimeUtility.DAY_START_TIME+"' AND '"+eightDaysBackDate+ DateTimeUtility.DAY_START_TIME+"'  "
							+ " OR SR.serviceOdometerUpdatedDate between '"+fourteenDaysBackDate+DateTimeUtility.DAY_START_TIME+"' AND '"+eightDaysBackDate+ DateTimeUtility.DAY_START_TIME+"' ) "
							+ " AND SR.service_id NOT IN ("+firstQuery+")";
					
					serviceReminder = serviceReminderService.getServiceReminderTableListBetweenDates(query,companyID);
					
					if(serviceReminder != null) {
						valueObject.put("serviceReminder", serviceReminder);	
					}
					break;
					
			case	SERVICE_OVERDUE_FIFTEEN_DAYS_BACK_AND_ABOVE: 
				
					query = " ( SR.time_servicedate <= '"+fifteenDaysBackDate+DateTimeUtility.DAY_END_TIME+"' OR "
							+ " SR.serviceOdometerUpdatedDate <= '"+fifteenDaysBackDate+DateTimeUtility.DAY_END_TIME+"' ) "
							+ " AND SR.service_id NOT IN ("+finalQuery+") ";
					serviceReminder = serviceReminderService.getServiceReminderTableListBetweenDates(query,companyID);
					
					if(serviceReminder != null) {
						valueObject.put("serviceReminder", serviceReminder);	
					}
					break;
				
			default:
					System.err.println("This is Default");
					break;
				
			}
			
			return valueObject;
		}
		catch(Exception e){
			throw e;
		}finally {
			serviceReminder	= null;
		}
	}
	
	/****************Fuel Summary Data************************/
	
	@Override
	public ValueObject getFuelDataCount(ValueObject valueObject) throws Exception {
		int							compId										= 0;
		String 						startDateWithTime							= null;
		String 						endDateWithTime								= null;
		long						noOfDaysInDateRange							= 0;
		long						fuelCreatedCount							= 0;
		double						todaysTotalFuelCost							= 0.0;
		double						todaysTotalFuelLiter						= 0.0;
		double						todaysAverageFuelPrice						= 0.0;
		long						activeVehicleCount							= 0;
		List<FuelDto>				fuel										= null;
		long						belowRange									= 0;
		long						betweenRange								= 0;
		long						aboveRange									= 0;
		long 						totalVehicleCount							= 0;
		long 						feCreatedOnVehicles							= 0;
		long 						feNotCreatedOnVehicles					    = 0;
		long 						countOfvehiclesWithoutKMPL					= 0;
		try {
			compId				 = valueObject.getInt("compId");
			startDateWithTime	 = valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDateWithTime		 = valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			
			startDateWithTime = DateTimeUtility.formatDate(startDateWithTime, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDateWithTime   = DateTimeUtility.formatDate(endDateWithTime, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			noOfDaysInDateRange = DateTimeUtility.getDayDiffBetweenTwoDates(DateTimeUtility.getTimeStamp(startDateWithTime,DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(endDateWithTime, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			
			totalVehicleCount 	       = VehicleRepository.countTotalVehicles(compId);
			feCreatedOnVehicles 	   = FuelRepository.fuelEntriesCreatedOnDistinctVehicles(compId);
			feNotCreatedOnVehicles 	   = totalVehicleCount - feCreatedOnVehicles;
			countOfvehiclesWithoutKMPL = VehicleRepository.countOfVehiclesWithoutMileage(compId);
			
			fuelCreatedCount = fuelService.todaysFuelEntriesCount(startDateWithTime, endDateWithTime, compId);
			
			fuel	= fuelService.getTotalFuelCreatedDetailsBetweenDates(startDateWithTime, endDateWithTime, compId);
			
			if(fuel != null) {
				for(FuelDto fuelDto : fuel) {
					if(fuelDto.getVehicle_ExpectedMileage() != null && fuelDto.getVehicle_ExpectedMileage_to() != null) {
						if(fuelDto.getFuel_kml() > fuelDto.getVehicle_ExpectedMileage_to()) {
							aboveRange++;
						}
						
						if(fuelDto.getFuel_kml() >= fuelDto.getVehicle_ExpectedMileage() && fuelDto.getFuel_kml() <= fuelDto.getVehicle_ExpectedMileage_to()) {
							betweenRange++;
						}
						
						if(fuelDto.getFuel_kml() < fuelDto.getVehicle_ExpectedMileage()) {
							belowRange++;
						}
					}

				}
			}
			
			todaysTotalFuelCost = fuelService.todaysTotalFuelCost(startDateWithTime, endDateWithTime, compId);
			todaysTotalFuelLiter = fuelService.todaysTotalFuelLiter(startDateWithTime, endDateWithTime, compId);
			todaysAverageFuelPrice = fuelService.todaysAverageFuelPrice(startDateWithTime, endDateWithTime, compId);
			activeVehicleCount = VehicleService.getActiveVehicleCount(compId);
			
			valueObject.put("totalVehicleCount", totalVehicleCount);
			valueObject.put("feCreatedOnVehicles", feCreatedOnVehicles);
			valueObject.put("feNotCreatedOnVehicles", feNotCreatedOnVehicles);
			valueObject.put("countOfvehiclesWithoutKMPL", countOfvehiclesWithoutKMPL);
			
			valueObject.put("fuelCreatedCount", fuelCreatedCount);
			valueObject.put("belowRange", belowRange);
			valueObject.put("betweenRange", betweenRange);
			valueObject.put("aboveRange", aboveRange);
			
			valueObject.put("todaysTotalFuelCost", todaysTotalFuelCost);
			valueObject.put("todaysTotalFuelLiter", todaysTotalFuelLiter);
			valueObject.put("todaysAverageFuelPrice", todaysAverageFuelPrice);
			valueObject.put("activeVehicleCount", activeVehicleCount);
			
			valueObject.put("noOfDaysInDateRange", noOfDaysInDateRange);
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			startDateWithTime	= null;
		}
	}	
	
	@Override
	public ValueObject getFuelTableData(ValueObject valueObject) throws Exception {
		int							compId										= 0;
		String 						startDate									= null;
		String 						endDate										= null;
		short						type										= 0;
		List<FuelDto>				fuel										= null;
		List<FuelDto>				fuelRange									= new ArrayList<FuelDto>();
		List<VehicleDto>    		vehicleList									= null; 
		StringBuilder 				result 										= new StringBuilder();
		List<Fuel>					fuelList									= null;
		String						query										= null;
		
		final short					FUELENTRIES_CREATED							= 1;
		final short					FUELENTRIES_NOT_CREATED						= 2;
		final short					KMPL_NOT_CREATED							= 3;
		final short					FUEL_DETAILS								= 4;
		final short					BELOW_RANGE									= 5;
		final short					BETWEEN_RANGE								= 6;
		final short					ABOVE_RANGE									= 7;
		//final short					ACTIVE_VEHICLES								= 2;
		
		try {
			compId				= valueObject.getInt("compId");
			startDate 			= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate   			= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			type				= valueObject.getShort("type"); 
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			switch (type) {
			
			 case	FUELENTRIES_CREATED:  
					
				 	fuel	= fuelService.getListOfNumberOfFECreatedOnVehicles(compId);
				 	
				    if(fuel != null)
				    	valueObject.put("fuel", fuel);
				    
					break;
			
			 case FUELENTRIES_NOT_CREATED:
				 	
				 	fuelList = FuelRepository.listFuelEmtriesCreatedOnVehicles(compId);
					
				 	if(!fuelList.isEmpty()) {
				 		for(int i =0; i <fuelList.size() ; i++) 
					    { 
							 result.append(fuelList.get(i)+"");
						  	 result.append(","); 
					    }
					    
					    query = result.substring(0, result.length() - 1);
				 	} else {
				 		query = "0";
				 	}
				  
				    vehicleList	= VehicleService.getListOfNumberOfFENotCreatedOnVehicles(query, compId);
				   
				   if(vehicleList != null)
					   valueObject.put("fuel", vehicleList);
			        break;		
			        
			 case	KMPL_NOT_CREATED:  
					
				 	vehicleList	= VehicleService.listOfVehiclesWithoutMileage(compId);
				 	
				    if(vehicleList != null)
				    	valueObject.put("fuel", vehicleList);
				    
					break;       
			
			 case FUEL_DETAILS:
				  
				   fuel	= fuelService.getTotalFuelCreatedDetailsBetweenDates(startDate, endDate, compId);
				   
				   if(fuel != null)
					   valueObject.put("fuel", fuel);
			        break;
			        
			  case BELOW_RANGE:
				  
				  fuel	= fuelService.getTotalFuelCreatedDetailsBetweenDates(startDate, endDate, compId);
				  if(fuel != null) {
					  
					  for(FuelDto fuelDto : fuel) {
							
							if(fuelDto.getFuel_kml() < fuelDto.getVehicle_ExpectedMileage()) {
								fuelRange.add(fuelDto);
							}
					  }
					  
					  if(fuelRange != null)
					  valueObject.put("fuel", fuelRange);
					  
				  }
			        break;
			        
			  case BETWEEN_RANGE:
				  
				  fuel	= fuelService.getTotalFuelCreatedDetailsBetweenDates(startDate, endDate, compId);
				  if(fuel != null) {
					  
					  for(FuelDto fuelDto : fuel) {
						  
						  if(fuelDto.getFuel_kml() >= fuelDto.getVehicle_ExpectedMileage() && fuelDto.getFuel_kml() <= fuelDto.getVehicle_ExpectedMileage_to()) {
									  fuelRange.add(fuelDto);
						  }
					  }
					  
					  if(fuelRange != null)
						  valueObject.put("fuel", fuelRange);
					  
				  }
				  break; 
				  
			  case ABOVE_RANGE:
				  
				  fuel	= fuelService.getTotalFuelCreatedDetailsBetweenDates(startDate, endDate, compId);
				  if(fuel != null) {
					  
					  for(FuelDto fuelDto : fuel) {
						  
						  if(fuelDto.getFuel_kml() > fuelDto.getVehicle_ExpectedMileage_to()) {
							  fuelRange.add(fuelDto);
						  }
					  }
					  
					  if(fuelRange != null)
						  valueObject.put("fuel", fuelRange);
					  
				  }
				  break; 
				  
				/*
				 * case ACTIVE_VEHICLES: vehicle = VehicleService.getActiveVehicleList(compId);
				 * if(vehicle != null) valueObject.put("vehicle", vehicle); break;
				 */ 
			 
			  default:
			        break;
			}
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			fuel	= null;
		}
	}
	
	/*******************Services Entry*********/
	@Override
	public ValueObject getServiceEntryDataCount(ValueObject valueObject) throws Exception {
		String 						startDate						= null;
		String 						endDate							= null;
		ValueObject					valueOutObject					= null;
		Integer						companyID						= 0;
		String 						todaysStrDate					= null;
		String 						sevenDaysBackDate				= null;
		String 						eightDaysBackDate				= null;
		String 						forteenDaysBackDate				= null;
		String 						fifteenDaysBackDate				= null;
		long						noOfDaysInDateRange				= 0;
		long						from7Days						= 0;
		long						from15Days						= 0;
		long						from30Days						= 0;
		long  						SECreatedCounts         	 	= 0;
		long  						SEOpenCounts 	        		= 0;
		long						SEAllOpenCount					= 0;
		long  						SEProcessCounts          		= 0;
		long  						SECloseCounts 	       	 		= 0;
		short						openStatus						= ServiceEntriesType.SERVICE_ENTRIES_STATUS_OPEN;
		short						processStatus					= ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS;
		short						closeStatus						= ServiceEntriesType.SERVICE_ENTRIES_STATUS_COMPLETE;
		try {
			valueOutObject 	= new ValueObject();
			
			companyID		= valueObject.getInt("compId");
			startDate 		= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate 		= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			noOfDaysInDateRange = DateTimeUtility.getDayDiffBetweenTwoDates(DateTimeUtility.getTimeStamp(startDate,DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(endDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			
			sevenDaysBackDate	= LocalDate.now().minusDays(7)+"";
			eightDaysBackDate	= LocalDate.now().minusDays(8)+"";
			forteenDaysBackDate	= LocalDate.now().minusDays(14)+"";
			fifteenDaysBackDate	= LocalDate.now().minusDays(15)+"";
			todaysStrDate		= LocalDate.now()+"";
			
			SECreatedCounts  	= serviceEntriesService.getTotalServiceEntriesCount(startDate, endDate, companyID);
			SEOpenCounts 		= serviceEntriesService.getSECountByStatusID(startDate, endDate, companyID, openStatus);
			SEProcessCounts 	= serviceEntriesService.getSECountByStatusID(startDate, endDate, companyID, processStatus);
			SECloseCounts 		= serviceEntriesService.getSECountByStatusID(startDate, endDate, companyID, closeStatus);
			
			SEAllOpenCount		= serviceEntriesService.getAllOpenServiceEntriesByDateCount(companyID);
			
			from7Days 	  		= serviceEntriesService.getALLOpenServiceEntriesBetweenDates(sevenDaysBackDate+" "+DateTimeUtility.DAY_START_TIME, todaysStrDate+" "+DateTimeUtility.DAY_END_TIME, companyID );
			from15Days			= serviceEntriesService.getALLOpenServiceEntriesBetweenDates(forteenDaysBackDate+" "+DateTimeUtility.DAY_START_TIME, eightDaysBackDate+" "+DateTimeUtility.DAY_END_TIME, companyID );
			from30Days  		= serviceEntriesService.getALLOpenServiceEntriesByDate(fifteenDaysBackDate+" "+DateTimeUtility.DAY_END_TIME, companyID );
			
			ValueObject duePaymentDetails = serviceEntriesService.getServiceEntriesDuePaymentCountAndAmount(companyID);
			
			valueOutObject.put("SECreatedCounts", SECreatedCounts);
			valueOutObject.put("SEOpenCounts", SEOpenCounts);
			valueOutObject.put("SEProcessCounts", SEProcessCounts);
			valueOutObject.put("SECloseCounts", SECloseCounts);
			
			valueOutObject.put("duePaymentCount", duePaymentDetails.get("duePaymentCount"));
			valueOutObject.put("duePaymentAmount", duePaymentDetails.get("duePaymentAmount"));
			
			valueOutObject.put("SEAllOpenCounts", SEAllOpenCount);
			valueOutObject.put("from7Days", from7Days);
			valueOutObject.put("from15Days", from15Days);
			valueOutObject.put("from30Days", from30Days);
			valueOutObject.put("noOfDaysInDateRange", noOfDaysInDateRange);
			
			return valueOutObject;
		
		} catch (Exception e) {
			LOGGER.error("getServiceEntryDataCount"+e);
			throw e;
		}
	}
	
	
	@Override
	public ValueObject getServiceEntryTableData(ValueObject valueObject) throws Exception {
		ValueObject					valueOutObject								= null;
		int							compId										= 0;
		String 						startDate									= null;
		String 						endDate										= null;
		String 						todaysStrDate 								= null;
		String 						sevenDaysBackDate 							= null;
		String 						eightDaysBackDate 							= null;
		String 						forteenDaysBackDate 						= null;
		String 						fifteenDaysBackDate							= null;
		short						type										= 0;
		List<ServiceEntriesDto>		serviceEntry								= null;
		final short					SE_CREATED									= 0;
		final short					SE_OPEN 									= ServiceEntriesType.SERVICE_ENTRIES_STATUS_OPEN;
		final short					SE_IN_PROCESS								= ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS;
		final short					SE_CLOSED 									= ServiceEntriesType.SERVICE_ENTRIES_STATUS_COMPLETE;
		final short					DUE_PAYMENT 								= 4;
		final short					SE_ALL_OPEN 								= 5;
		final short					SE_0_TO_7 									= 6;
		final short					SE_8_TO_15 									= 7;
		final short					SE_15UP 									= 8;
		String 						SEStatusQuery								= "";
			
		try {
			valueOutObject 	= new ValueObject();
			
			compId			= valueObject.getInt("compId");
			startDate		= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate			= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			type			= valueObject.getShort("type");
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			sevenDaysBackDate	= LocalDate.now().minusDays(7)+"";
			eightDaysBackDate	= LocalDate.now().minusDays(8)+"";
			forteenDaysBackDate	= LocalDate.now().minusDays(14)+"";
			fifteenDaysBackDate	= LocalDate.now().minusDays(15)+"";
			todaysStrDate		= LocalDate.now()+"";

			switch (type) {
			  
			case SE_CREATED:
				
				  SEStatusQuery	= " SE.invoiceDate between '"+startDate+"' AND '"+endDate+"'";
				  serviceEntry	= serviceEntriesService.getSEDetailsBetweenDatesByStatus(compId, SEStatusQuery);// WOStatusQuery is ""
				 
				  if(serviceEntry != null)
					valueOutObject.put("serviceEntry", serviceEntry);
			        break;
			        
			  case SE_OPEN: 
				  
				  SEStatusQuery = " SE.invoiceDate between '"+startDate+"' AND '"+endDate+"'AND SE.serviceEntries_statusId ="+SE_OPEN+" ";
				  serviceEntry	= serviceEntriesService.getSEDetailsBetweenDatesByStatus(compId, SEStatusQuery);
				  
				  if(serviceEntry != null)
				    valueOutObject.put("serviceEntry", serviceEntry);
			        break;
			        
			  case SE_IN_PROCESS: 
				  
				  SEStatusQuery = " SE.invoiceDate between '"+startDate+"' AND '"+endDate+"' AND SE.serviceEntries_statusId ="+SE_IN_PROCESS+" ";
				  serviceEntry	= serviceEntriesService.getSEDetailsBetweenDatesByStatus(compId, SEStatusQuery);
				 
				  if(serviceEntry != null)
				  	valueOutObject.put("serviceEntry", serviceEntry);
			        break;
			        
			  case SE_CLOSED: 
				  
				  SEStatusQuery = " SE.invoiceDate between '"+startDate+"' AND '"+endDate+"' AND SE.serviceEntries_statusId ="+SE_CLOSED+" ";
				  serviceEntry	= serviceEntriesService.getSEDetailsBetweenDatesByStatus(compId, SEStatusQuery);
				  
				  if(serviceEntry != null)
				  	valueOutObject.put("serviceEntry", serviceEntry);
				  	break;
				  	
			  case DUE_PAYMENT: 
				  
				  SEStatusQuery = " SE.service_vendor_paymentdate is null AND service_paymentTypeId = "+PaymentTypeConstant.PAYMENT_TYPE_CREDIT+" ";
				  serviceEntry	= serviceEntriesService.getSEDetailsBetweenDatesByStatus(compId, SEStatusQuery);
				  
				  if(serviceEntry != null)
					  valueOutObject.put("serviceEntry", serviceEntry);
				  break;
				  	
			  case SE_ALL_OPEN:
				  
					SEStatusQuery	= " SE.serviceEntries_statusId IN ("+ServiceEntriesType.SERVICE_ENTRIES_STATUS_OPEN+", "+ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS+")  AND SE.invoiceDate <> null";
					serviceEntry	= serviceEntriesService.getSEDetailsBetweenDatesByStatus(compId, SEStatusQuery);// WOStatusQuery is ""
					
					if(serviceEntry != null)
						valueOutObject.put("serviceEntry", serviceEntry);
					break;	  	
			 
			  case SE_0_TO_7: // No Status ie WOStatusQuery is blank Hense Return All Status Details
				  
				  SEStatusQuery	= " SE.invoiceDate between '"+sevenDaysBackDate+""+DateTimeUtility.DAY_START_TIME+"' AND '"+ todaysStrDate+""+DateTimeUtility.DAY_END_TIME+"' AND  SE.serviceEntries_statusId IN ("+ServiceEntriesType.SERVICE_ENTRIES_STATUS_OPEN+", "+ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS+")";
				  serviceEntry	= serviceEntriesService.getSEDetailsBetweenDatesByStatus(compId, SEStatusQuery);
				 
				  if(serviceEntry != null)
				  	valueOutObject.put("serviceEntry", serviceEntry);
				  	break;
			 
			  case SE_8_TO_15: 
				  
				  SEStatusQuery	= " SE.invoiceDate between '"+forteenDaysBackDate+""+DateTimeUtility.DAY_START_TIME+"' AND '"+ eightDaysBackDate+""+DateTimeUtility.DAY_END_TIME+"' AND  SE.serviceEntries_statusId IN ("+ServiceEntriesType.SERVICE_ENTRIES_STATUS_OPEN+", "+ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS+")";
				  serviceEntry	= serviceEntriesService.getSEDetailsBetweenDatesByStatus(compId, SEStatusQuery);
				  
				  if(serviceEntry != null)
				  	valueOutObject.put("serviceEntry", serviceEntry);
				  	break;
			 
			  case SE_15UP: 
				  
				  SEStatusQuery	= " SE.invoiceDate <= '"+fifteenDaysBackDate+""+DateTimeUtility.DAY_END_TIME+"' AND  SE.serviceEntries_statusId IN ("+ServiceEntriesType.SERVICE_ENTRIES_STATUS_OPEN+", "+ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS+")";
				  serviceEntry	= serviceEntriesService.getSEDetailsBetweenDatesByStatus(compId, SEStatusQuery);
				 
				  if(serviceEntry != null)
				  	valueOutObject.put("serviceEntry", serviceEntry);
				  	break;
			 
			  default:
			        break;
			}
				
			return valueOutObject;
			
		} catch (Exception e) {
			LOGGER.error("getServiceEntryTableData Exception"+e);
			throw e;
		}finally {
			valueOutObject								= null;                                                 
			compId										= 0;                                                    
			startDate									= null;                                                 
			endDate										= null;                                                 
			sevenDaysBackDate 							= null;                                                 
			eightDaysBackDate 							= null;                                                 
			fifteenDaysBackDate							= null;                                                 
			type										= 0;                                                    
			serviceEntry								= null;                                                 
			SEStatusQuery								= "";                                                   
		}
	}
	
/********************Pick And Drop SUMMARY Data**************************/
	
	@Override
	public ValueObject getPickAndDropDataCount(ValueObject valueObject) throws Exception {
		String 						startDate						= null;
		String 						endDate							= null;
		ValueObject					valueOutObject					= null;
		Integer						companyID						= 0;
		long 						totalVehicleCount				= 0;
		long						noOfDaysInDateRange				= 0;
		long  						pickDropCreatedCounts          	= 0;
		long  						vehiclesWithPickDrop 	       	= 0;
		long  						vehiclesWithoutPickDrop         = 0;
		double  					totalAmount 	       			= 0;
		long  						totalPickUps          			= 0;
		long  						totalDrops          			= 0;
		try {	
			companyID				= valueObject.getInt("compId");
			valueOutObject 			= new ValueObject();
			
			startDate 				= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate 				= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			noOfDaysInDateRange = DateTimeUtility.getDayDiffBetweenTwoDates(DateTimeUtility.getTimeStamp(startDate,DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(endDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			totalVehicleCount 	= VehicleRepository.countTotalVehicles(companyID);
			
			pickDropCreatedCounts 	= PickAndDropLocationService.pickAndDropCreatedBetweenDatesByCompanyId(startDate, endDate, companyID);// email
			vehiclesWithPickDrop	= PickAndDropLocationService.vehiclesWithPickAndDropBetweenDates(startDate, endDate, companyID);
			vehiclesWithoutPickDrop	= totalVehicleCount - vehiclesWithPickDrop;
			totalAmount				= PickAndDropLocationService.pickAndDropTotalAmountBetweenDates(startDate, endDate, companyID);
			totalPickUps			= PickAndDropLocationService.totalPickUpsBetweenDates(startDate, endDate, companyID);
			totalDrops				= PickAndDropLocationService.totalDropsBetweenDates(startDate, endDate, companyID);
			
			valueOutObject.put("pickDropCreatedCounts", pickDropCreatedCounts);
			valueOutObject.put("vehiclesWithPickDrop", vehiclesWithPickDrop);
			valueOutObject.put("vehiclesWithoutPickDrop", vehiclesWithoutPickDrop);
			valueOutObject.put("totalAmount", totalAmount);
			valueOutObject.put("totalPickUps", totalPickUps);
			valueOutObject.put("totalDrops", totalDrops);
			
			valueOutObject.put("noOfDaysInDateRange", noOfDaysInDateRange);
			
			return valueOutObject;
		
		} catch (Exception e) {
			LOGGER.error("getWorkOrderTableData Exception"+e);
			throw e;
		}finally {
			startDate						                         = null;                                                                                       
			endDate							                         = null;                                                                                       
			valueOutObject					                         = null;                                                                                       
			companyID						                         = 0;                                                                                          
			pickDropCreatedCounts          	                         = 0;                                                                                          
			vehiclesWithPickDrop 	       		                     = 0;                                                                                          
			vehiclesWithoutPickDrop          	                     = 0;                                                                                          
			totalAmount 	       		                         	 = 0;                                                                                          
			totalPickUps          		                        	 = 0;                                                                                          
			totalDrops          	                         		 = 0;                                                                                          
		}                                                                                                                        
		
	}
	
	@Override
	public ValueObject getPickAndDropTableData(ValueObject valueObject) throws Exception {
		int								compId										= 0;
		String 							startDate									= null;
		String 							endDate										= null;
		short							type										= 0;
		List<TripsheetPickAndDropDto>	pickAndDrop									= null;
		List<TripsheetPickAndDropDto>	vehiclesWithPickDropList					= null;
		List<VehicleDto>    			vehicleList									= null; 
		StringBuilder 					result 										= new StringBuilder();
		String							query										= null;
		final short						PICKDROP_CREATED 							= 0;
		final short						VEHICLES_WITH_PICKDROP 						= 1;
		final short						VEHICLES_WITHOUT_PICKDROP 	 				= 2;
		final short						TOTAL_AMOUNT								= 3;
		final short						TOTAL_PICKUPS								= 4;
		final short						TOTAL_DROPS 								= 5;
		String 							pickDropStatusQuery							= "";
		ValueObject						valueOutObject								= null;
		try {
			valueOutObject 		= new ValueObject();
			
			compId				= valueObject.getInt("compId");
			startDate			= valueObject.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME;
			endDate				= valueObject.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME;
			type				= valueObject.getShort("type");
			
			startDate = DateTimeUtility.formatDate(startDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate   = DateTimeUtility.formatDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			switch (type) {
			 
			case PICKDROP_CREATED:
				pickDropStatusQuery	= " TPD.journeyDate BETWEEN '"+startDate+"' AND '"+endDate+"' ";
				pickAndDrop	= PickAndDropLocationService.pickAndDropCreatedBetweenDatesList(compId, pickDropStatusQuery);
				  
				  if(pickAndDrop != null)
					valueOutObject.put("pickAndDrop", pickAndDrop);
			        break;
			        
			case VEHICLES_WITH_PICKDROP:
				  pickDropStatusQuery = " TPD.journeyDate BETWEEN '"+startDate+"' AND '"+endDate+"' ";
				  pickAndDrop	= PickAndDropLocationService.vehiclesWithPickAndDropBetweenDatesList(compId, pickDropStatusQuery);

				  if(pickAndDrop != null)
					valueOutObject.put("pickAndDrop", pickAndDrop);
				    break;
			        
			case VEHICLES_WITHOUT_PICKDROP:
				  
				  vehiclesWithPickDropList = PickAndDropLocationService.vehiclesWithPickAndDropBetweenDatesWithUniqueVehicleId(startDate, endDate, compId);
					
				 	if(vehiclesWithPickDropList != null && !vehiclesWithPickDropList.isEmpty()) {
				 		
				 		for(TripsheetPickAndDropDto pickDrops : vehiclesWithPickDropList) {
				 			result.append(pickDrops.getVid()+"");
						  	result.append(","); 
				 		}
					    query = result.substring(0, result.length() - 1);
					    
				 	} else {
				 		query = "0";
				 	}
				  
				    vehicleList	= VehicleService.getListOfNumberOfFENotCreatedOnVehicles(query, compId);
				  
					if(vehicleList != null)
					   valueOutObject.put("pickAndDrop", vehicleList);
			           break;
			        
			 case TOTAL_AMOUNT: 
				  pickDropStatusQuery = " TPD.journeyDate BETWEEN '"+startDate+"' AND '"+endDate+"' ";
				  pickAndDrop	= PickAndDropLocationService.pickAndDropCreatedBetweenDatesList(compId, pickDropStatusQuery);
				  
				  if(pickAndDrop != null)
					valueOutObject.put("pickAndDrop", pickAndDrop);
			        break;
			        
			 case TOTAL_PICKUPS: 
				  pickDropStatusQuery = " TPD.journeyDate BETWEEN '"+startDate+"' AND '"+endDate+"' AND TPD.pickOrDropStatus = "+TripsheetPickAndDropConstant.PICKUP+" ";
				  pickAndDrop	= PickAndDropLocationService.pickAndDropCreatedBetweenDatesList(compId, pickDropStatusQuery);
				 
				  if(pickAndDrop != null)
					valueOutObject.put("pickAndDrop", pickAndDrop);
				    break;
			 
			 case TOTAL_DROPS: 
				  pickDropStatusQuery = " TPD.journeyDate BETWEEN '"+startDate+"' AND '"+endDate+"' AND TPD.pickOrDropStatus = "+TripsheetPickAndDropConstant.DROP+" ";
				  pickAndDrop	= PickAndDropLocationService.pickAndDropCreatedBetweenDatesList(compId, pickDropStatusQuery);
				  
				  if(pickAndDrop != null)
					valueOutObject.put("pickAndDrop", pickAndDrop);
				    break;
			 
			 default:
			        break;
			}
			return valueOutObject;
			
		} catch (Exception e) {
			LOGGER.error("getIssueTableData"+e);
			throw e;
		}finally {
			compId										= 0;                                                 
			startDate									= null;                                              
			endDate										= null;                                              
			type										= 0;                                                 
			pickAndDrop									= null;                                              
			pickDropStatusQuery							= "";        
			valueOutObject								= null;
		}
	}
	
	@Transactional
	@Override
	public ValueObject tyreStockCount(ValueObject valueObject) throws Exception {
		TypedQuery<Object[]> query = null;
		InventoryTyreDto 	inventoryTyreDto 	= null;
		String				statusQuery			= "";
		String				locatinoQuery			= "";
		String				queryStr			= "";
		long				allTyreCount		= 0;
		List<InventoryTyreDto> inventoryTyreList = null;
		Map<Short, Long> 		inventoryTyreHM	= null;
		try {
			inventoryTyreHM 	= new HashMap<>();
			if(valueObject.getShort("statusId",(short)0) > 0) {
				statusQuery = "AND TYRE_ASSIGN_STATUS_ID = "+valueObject.getShort("statusId",(short)0)+"";
			}
			if(valueObject.getLong("locationId",(long)0) > 0) {
				locatinoQuery = "AND WAREHOUSE_LOCATION_ID = "+valueObject.getLong("locationId",(long)0)+"";
			}
			queryStr = ""+statusQuery+" "+locatinoQuery+"";
			
			 query = entityManager.createQuery(
					"SELECT count(TYRE_ID),TYRE_ASSIGN_STATUS_ID  From InventoryTyre "
						+ " WHERE COMPANY_ID = "+valueObject.getInt("compId",0)+" "+queryStr+" AND markForDelete = 0 GROUP BY TYRE_ASSIGN_STATUS_ID ",
						Object[].class);
			 
				List<Object[]> results = query.getResultList();

				if (results != null && !results.isEmpty()) {
					inventoryTyreList = new ArrayList<InventoryTyreDto>();
					
						for (Object[] result : results) {
							inventoryTyreDto = new InventoryTyreDto();
							
							inventoryTyreDto.setStatusWiseTyreCount((Long)result[0]);
							inventoryTyreDto.setTYRE_ASSIGN_STATUS_ID((short)result[1]);
							allTyreCount += inventoryTyreDto.getStatusWiseTyreCount();
							inventoryTyreHM.put(inventoryTyreDto.getTYRE_ASSIGN_STATUS_ID(), inventoryTyreDto.getStatusWiseTyreCount());
					
							inventoryTyreList.add(inventoryTyreDto);
						}
				}
				valueObject.put("tyreStockCount", allTyreCount);
				valueObject.put("availableCount", inventoryTyreHM.getOrDefault(InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE, (long) 0));
				valueObject.put("inServiceCount", inventoryTyreHM.getOrDefault(InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_SERVICE, (long) 0));
				valueObject.put("scrapCount", inventoryTyreHM.getOrDefault(InventoryTyreDto.TYRE_ASSIGN_STATUS_SCRAPED, (long) 0));
				valueObject.put("sentRetreadCount", inventoryTyreHM.getOrDefault(InventoryTyreDto.TYRE_ASSIGN_STATUS_SENT_RETREAD, (long) 0));
				valueObject.put("inProcessCount", inventoryTyreHM.getOrDefault(InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_PROCESS, (long) 0));
				valueObject.put("soldCount", inventoryTyreHM.getOrDefault(InventoryTyreDto.TYRE_ASSIGN_STATUS_SOLD, (long) 0));
				valueObject.put("unAvailableCount", inventoryTyreHM.getOrDefault(InventoryTyreDto.TYRE_ASSIGN_STATUS_UNAVAILABLE, (long) 0));
				valueObject.put("soldCount", inventoryTyreHM.getOrDefault(InventoryTyreDto.TYRE_ASSIGN_STATUS_SOLD, (long) 0));
				
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Transactional
	@Override
	public ValueObject getTyreStockDetails(ValueObject valueObject) throws Exception {
		TypedQuery<Object[]> 		query 					= null;
		InventoryTyreDto 			inventoryTyreDto 		= null;
		String						statusQuery				= "";
		String						locatinoQuery			= "";
		String						queryStr				= "";
		List<InventoryTyreDto> 		inventoryTyreList		= null;
		try {
			if(valueObject.getShort("statusId",(short)0) > 0) {
				statusQuery = "AND IT.TYRE_ASSIGN_STATUS_ID = "+valueObject.getShort("statusId",(short)0)+"";
			}
			if(valueObject.getLong("locationId",(long)0) > 0) {
				locatinoQuery = "AND IT.WAREHOUSE_LOCATION_ID = "+valueObject.getLong("locationId",(long)0)+"";
			}
			queryStr = ""+statusQuery+" "+locatinoQuery+"";
			
			 query = entityManager.createQuery(
					"SELECT IT.TYRE_ID, IT.TYRE_NUMBER, IT.TYRE_ASSIGN_STATUS_ID, IT.WAREHOUSE_LOCATION_ID,"
						+ " P.partlocation_name, IT.TYRE_MODEL_ID, TM.TYRE_MODEL_SUBTYPE, IT.dismountedTyreStatusId  From InventoryTyre AS IT "
						+ " INNER JOIN PartLocations AS P ON P.partlocation_id = IT.WAREHOUSE_LOCATION_ID "
						+ " INNER JOIN VehicleTyreModelSubType AS TM ON TM.TYRE_MST_ID = IT.TYRE_MODEL_ID "
						+ " WHERE IT.COMPANY_ID = "+valueObject.getInt("compId",0)+" "+queryStr+" AND IT.markForDelete = 0  ",
						Object[].class);
			 
				List<Object[]> results = query.getResultList();

				if (results != null && !results.isEmpty()) {
					inventoryTyreList = new ArrayList<InventoryTyreDto>();
					
						for (Object[] result : results) {
							inventoryTyreDto = new InventoryTyreDto();
							inventoryTyreDto.setTYRE_ID((long)result[0]);
							inventoryTyreDto.setTYRE_NUMBER((String)result[1]);
							inventoryTyreDto.setTYRE_ASSIGN_STATUS_ID((short)result[2]);
							inventoryTyreDto.setTYRE_ASSIGN_STATUS(InventoryTyreDto.getPaymentModeName((short)result[2]));
							inventoryTyreDto.setWAREHOUSE_LOCATION_ID((Integer)result[3]);
							inventoryTyreDto.setWAREHOUSE_LOCATION((String)result[4]);
							inventoryTyreDto.setTYRE_MODEL_ID((Integer)result[5]);
							inventoryTyreDto.setTYRE_MODEL((String)result[6]);
							if(result[7] != null) {
								inventoryTyreDto.setDismountedTyreStatusId((short)result[7]);
								inventoryTyreDto.setDismountedTyreStatus(TyreAssignmentConstant.getOldTyreMovedTo((short)result[7]));
							}
							inventoryTyreList.add(inventoryTyreDto);
						}
				}
				valueObject.put("inventoryTyreList", inventoryTyreList);
																																
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Transactional
	@Override
	public ValueObject getAllLocationTyreStockDetailsByStatus(ValueObject valueObject) throws Exception {
		TypedQuery<Object[]> 		query 					= null;
		InventoryTyreDto 			inventoryTyreDto 		= null;
		String						statusQuery				= "";
		String						queryStr				= "";
		List<InventoryTyreDto> 		inventoryTyreList		= null;
		Map<Short, Long> 			inventoryTyreHM	= null;
		try {
			inventoryTyreHM 	= new HashMap<>();
			if(valueObject.getShort("statusId",(short)0) > 0) {
				statusQuery = "AND IT.TYRE_ASSIGN_STATUS_ID = "+valueObject.getShort("statusId",(short)0)+"";
			}
			
			queryStr = ""+statusQuery+"";
			
			 query = entityManager.createQuery(
					"SELECT count(IT.TYRE_ID), IT.WAREHOUSE_LOCATION_ID, P.partlocation_name, IT.TYRE_ASSIGN_STATUS_ID  From InventoryTyre AS IT "
						+ " INNER JOIN PartLocations AS P ON P.partlocation_id = IT.WAREHOUSE_LOCATION_ID "
						+ " WHERE IT.COMPANY_ID = "+valueObject.getInt("compId",0)+" "
						+ " "+queryStr+" AND IT.markForDelete = 0 GROUP BY WAREHOUSE_LOCATION_ID ",
						Object[].class);
			 
				List<Object[]> results = query.getResultList();

				if (results != null && !results.isEmpty()) {
					inventoryTyreList = new ArrayList<InventoryTyreDto>();
						for (Object[] result : results) {
							inventoryTyreDto = new InventoryTyreDto();
							inventoryTyreDto.setStatusWiseTyreCount((Long)result[0]);
							inventoryTyreDto.setWAREHOUSE_LOCATION_ID((Integer)result[1]);
							inventoryTyreDto.setWAREHOUSE_LOCATION((String)result[2]);
							inventoryTyreDto.setTYRE_ASSIGN_STATUS_ID((short)result[3]);
							inventoryTyreDto.setTYRE_ASSIGN_STATUS(InventoryTyreDto.getPaymentModeName((short)result[3]));
							inventoryTyreList.add(inventoryTyreDto);
						}
				}
				valueObject.put("inventoryTyreList",inventoryTyreList);
				
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	@Override
	public ValueObject getAssignedTyreAllocation(ValueObject valueObject) throws Exception {
		List<VehicleTyreLayoutPositionDto> 	vehicleTyreLayoutPositionList 	= null;
		String query ="";
		try {
			vehicleTyreLayoutPositionList = new ArrayList<>();
			if(valueObject.getLong("vid",0) > 0) {
				query = " AND VTLP.TYRE_ASSIGNED = 1  AND VTLP.VEHICLE_ID = "+valueObject.getLong("vid",0)+"";
				
			}else {
				query = " AND VTLP.TYRE_ASSIGNED = 1 ";
			}
			valueObject.put("query", query);
			vehicleTyreLayoutPositionList = vehicleTyreAssignmentService.getVehicleTyreLayoutPositionList(valueObject);
			valueObject.put("vehicleTyreLayoutPositionList", vehicleTyreLayoutPositionList);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	@Override
	public ValueObject getMaxTyreRun(ValueObject valueObject) throws Exception {
		List<InventoryTyreDto> 	maxTyreList 	= null;
		String query = ""; 
		try {
			if(valueObject.getInt("maxLimit",0) > 0) {
				query = "AND R.TYRE_USEAGE >= "+valueObject.getInt("maxLimit",0)+"";
			}else {
				query = "AND R.TYRE_USEAGE IN (SELECT MAX(TYRE_USEAGE) FROM InventoryTyre WHERE markForDelete = 0  AND COMPANY_ID = "+valueObject.getInt("companyId")+" )";
				
			}
			
			valueObject.put("query", query);
			maxTyreList = new ArrayList<>();
			maxTyreList = iventoryTyreService.getMaxRunTyre(valueObject);
			valueObject.put("maxTyreList", maxTyreList);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Transactional
	@Override
	public ValueObject getMinTyreRun(ValueObject valueObject) throws Exception {
		List<InventoryTyreDto> 	minTyreList 	= null;
		String query = ""; 
		try {
			if(valueObject.getInt("minLimit",0) > 0) {
				query = 	"AND R.TYRE_USEAGE <= "+valueObject.getInt("minLimit",0)+" AND R.TYRE_ASSIGN_STATUS_ID ="+InventoryTyreDto.TYRE_ASSIGN_STATUS_SCRAPED+"";
			}else {
				query = "AND R.TYRE_USEAGE IN (SELECT MIN(TYRE_USEAGE) FROM InventoryTyre  WHERE markForDelete = 0  AND COMPANY_ID = "+valueObject.getInt("companyId")+"  ) AND R.TYRE_ASSIGN_STATUS_ID ="+InventoryTyreDto.TYRE_ASSIGN_STATUS_SCRAPED+")";
				
			}
			valueObject.put("query", query);
			minTyreList = new ArrayList<>();
			minTyreList = iventoryTyreService.getMinRunScrapTyre(valueObject);
			
			valueObject.put("minTyreList", minTyreList);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	@Override
	public ValueObject getVehicleWithoutTyre(ValueObject valueObject) throws Exception {
		List<VehicleTyreLayoutPositionDto> 	vehicleTyreLayoutPositionList 	= null;
		List<VehicleDto> 	tyreLayoutNotCreatedVehicle		= null;
		String query ="";
		Map<Integer, Integer> vehicleHM					= null;
		int count					= 0;
		try {
			vehicleHM					= new HashMap<>();
			vehicleTyreLayoutPositionList = new ArrayList<>();
			tyreLayoutNotCreatedVehicle = new ArrayList<>();
			query = " AND VTLP.TYRE_ASSIGNED = 0 AND VTLP.TYRE_ID IS NULL ";
			valueObject.put("query", query);
			vehicleTyreLayoutPositionList 	= vehicleTyreAssignmentService.getVehicleTyreLayoutPositionList(valueObject);
			tyreLayoutNotCreatedVehicle 	= tyreLayoutNotCreatedVehicle(valueObject);
			if(vehicleTyreLayoutPositionList != null && !vehicleTyreLayoutPositionList.isEmpty()) {
				for(VehicleTyreLayoutPositionDto dto : vehicleTyreLayoutPositionList) {
					if(!vehicleHM.containsKey(dto.getVEHICLE_ID())) {
						vehicleHM.put(dto.getVEHICLE_ID(), dto.getVEHICLE_ID());
						count ++;
					}
				}
			}
			valueObject.put("tyreNotAssignedCount", count);
			valueObject.put("tyreLayoutNotCreatedVehicleList", tyreLayoutNotCreatedVehicle);
			valueObject.put("vehicleWithoutTyreList", vehicleTyreLayoutPositionList);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<VehicleDto> tyreLayoutNotCreatedVehicle(ValueObject valueObject) throws Exception {
		TypedQuery<Object[]> 				query 							= null;
		List<VehicleDto> 	vehicleList		= null;
		VehicleDto			vehicleDto		= null;	
		try {
			
			 query = entityManager.createQuery(
					"SELECT V.vid, V.vehicle_registration,V.vehicleModalId, VM.vehicleModelName FROM Vehicle as V "
						+ " INNER JOIN  VehicleModel AS VM on VM.vehicleModelId  = V.vehicleModalId "
						+ " LEFT JOIN  VehicleTyreLayoutPosition as VL on VL.VEHICLE_ID  = V.vid "
						+ " WHERE V.company_Id = "+valueObject.getInt("companyId")+" "
						+ " AND  V.markForDelete = 0 AND VL.VEHICLE_ID IS NULL " ,
						Object[].class);
			
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				vehicleList = new ArrayList<VehicleDto>();
				
				for (Object[] result : results) {
					vehicleDto = new VehicleDto();
					vehicleDto.setVid((Integer)result[0]);
					vehicleDto.setVehicle_registration((String)result[1]);
					vehicleDto.setVehicleModelId((Long)result[2]);
					vehicleDto.setVehicle_Model((String)result[3]);
					vehicleList.add(vehicleDto);
				}
			}
			
			return vehicleList;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	@Override
	public ValueObject getIssueTyreDetails(ValueObject valueObject) throws Exception {
		TypedQuery<Object[]> query = null;
		IssuesDto 	issueDto 	= null;
		String				statusQuery			= "";
		List<IssuesDto> IssuesList = null;
		try {
			if(valueObject.getShort("statusId",(short)0) > 0) {
				statusQuery = "AND ISSUES_STATUS_ID <> "+IssuesTypeConstant.ISSUES_STATUS_RESOLVED+"";
			}
			
			
			 query = entityManager.createQuery(
					"SELECT I.ISSUES_ID, I.ISSUES_NUMBER, I.ISSUES_VID, V.vehicle_registration, I.ISSUES_SUMMARY  From Issues AS I"
						+ " INNER JOIN Vehicle AS V ON V.vid = I.ISSUES_VID "
						+ " INNER JOIN VehicleTyreLayoutPosition AS VL ON VL.LP_ID = I.issueLP_ID "
						+ " WHERE I.COMPANY_ID = "+valueObject.getInt("compId",0)+" "+statusQuery+" AND I.markForDelete = 0  ",
						Object[].class);
			 
				List<Object[]> results = query.getResultList();

				if (results != null && !results.isEmpty()) {
					IssuesList = new ArrayList<IssuesDto>();
					
						for (Object[] result : results) {
							issueDto = new IssuesDto();
							
							issueDto.setISSUES_ID((Long)result[0]);
							issueDto.setISSUES_NUMBER((Long)result[1]);
							issueDto.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64(""+(Long)result[0]+""));
					//		issueDto.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64(""+(Long)result[0]));
							issueDto.setISSUES_VID((Integer)result[2]);
							issueDto.setVehicleNumber((String)result[3]);
							issueDto.setISSUES_SUMMARY((String)result[4]);
					
							IssuesList.add(issueDto);
						}
				}
				valueObject.put("issuesList", IssuesList);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
