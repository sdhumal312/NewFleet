package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.WorkOrderRemarkDto;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksToPartsDto;
import org.fleetopgroup.persistence.model.InventoryLocation;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.model.ServiceReminderWorkOrderHistory;
import org.fleetopgroup.persistence.model.WorkOrderRemark;
import org.fleetopgroup.persistence.model.WorkOrderTxnChecker;
import org.fleetopgroup.persistence.model.WorkOrders;
import org.fleetopgroup.persistence.model.WorkOrdersTasks;
import org.fleetopgroup.persistence.model.WorkOrdersTasksToLabor;
import org.fleetopgroup.persistence.model.WorkOrdersTasksToParts;
import org.fleetopgroup.persistence.model.WorkOrdersTasksToReceived;
import org.fleetopgroup.persistence.serviceImpl.IWorkOTaskToPartDocumentService;
import org.fleetopgroup.web.util.AESEncryptDecrypt;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class WorkOrdersBL {
	
	@Autowired IWorkOTaskToPartDocumentService workOTaskToPartDocumentService;

	public WorkOrdersBL() {
	}

	Date currentDate = new Date();
	DateFormat ft 							= new SimpleDateFormat("dd-MM-yyyy HH:mm");
	SimpleDateFormat dateFormat 			= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormat_Name 		= new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat timeFormat 			= new SimpleDateFormat("HH:mm");
	SimpleDateFormat CreatedDateTime 		= new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	DecimalFormat    toFixedTwo				= new DecimalFormat("##.##");
	
	public static final long WORK_ORDER_DEFALAT_VALUE = 0;

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	VehicleBL VBL = new VehicleBL();

	// get Workorder Open Task Id
	public WorkOrders prepareWorkOrdersToAddTask(WorkOrdersDto workOrders) {

		WorkOrders work = new WorkOrders();

		work.setWorkorders_id(workOrders.getWorkorders_id());
		return work;
	}

	// get to save in prepareWorkOrders
	public WorkOrdersTasksToParts prepareWorkOrdersTaskToPart(WorkOrdersTasksToParts workOrdersTasksToParts, 
			InventoryDto inventoryDto, Double quantity, CustomUserDetails userDetails, 
			Double EachpartInventoryUnitprice, Double Totalcost,WorkOrdersDto workOrdersDto, WorkOrdersDto lastOccDetails ) throws Exception {

		WorkOrdersTasksToParts workPart = new WorkOrdersTasksToParts();

		workPart.setWorkordertaskid(workOrdersTasksToParts.getWorkordertaskid());
		workPart.setWorkorders_id(workOrdersTasksToParts.getWorkorders_id());

		// show Vehicle name
		workPart.setPartid(inventoryDto.getPartid());
		workPart.setLocationId(inventoryDto.getLocationId());
		workPart.setOldpart(workOrdersTasksToParts.getOldpart());

		// this get Which Inventory to coming
		workPart.setInventory_id(inventoryDto.getInventory_id());
		workPart.setQuantity(quantity);
		
		workPart.setCompanyId(userDetails.getCompany_id());
		workPart.setCreatedById(userDetails.getId());
		
		workPart.setParteachcost(EachpartInventoryUnitprice);
		
		workPart.setTotalcost(Totalcost);
		
		workPart.setVehicle_vid(workOrdersDto.getVehicle_vid());
		
		
		if(lastOccDetails != null) {
			workPart.setLast_occurred_date(DateTimeUtility.getTimeStamp(lastOccDetails.getLastupdated()));
			workPart.setLast_occ_odometer(lastOccDetails.getVehicle_Odometer());
		}
		return workPart;
	}
	
	public WorkOrdersTasksToParts prepareWorkOrdersTaskToPartInAjax(ValueObject object, InventoryDto inventoryDto, Double quantity,
			CustomUserDetails userDetails, Double EachpartInventoryUnitprice, Double Totalcost,WorkOrdersDto workOrdersDto,
			WorkOrdersDto lastOccDetails ) throws Exception {
		
		try {
	

			WorkOrdersTasksToParts workPart = new WorkOrdersTasksToParts();

			workPart.setWorkordertaskid(object.getLong("woTaskId"));
			workPart.setWorkorders_id(object.getLong("workOrderId"));
			workPart.setPartid(inventoryDto.getPartid());
			workPart.setLocationId(inventoryDto.getLocationId());
			workPart.setOldpart(object.getString("oldpart"));
			workPart.setInventory_id(inventoryDto.getInventory_id());
			workPart.setQuantity(quantity);
			workPart.setCompanyId(userDetails.getCompany_id());
			workPart.setCreatedById(userDetails.getId());
			workPart.setParteachcost(EachpartInventoryUnitprice);
			workPart.setTotalcost(Totalcost);
			workPart.setVehicle_vid(workOrdersDto.getVehicle_vid());
			workPart.setWoPart_document(object.getBoolean("woPart_document",false));
		

			if(lastOccDetails != null) {
				workPart.setLast_occurred_date(DateTimeUtility.getTimeStamp(lastOccDetails.getLastupdated()));
				workPart.setLast_occ_odometer(lastOccDetails.getVehicle_Odometer());
			}

			return workPart;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	// get to save in prepareWorkOrders
	public WorkOrdersTasksToLabor prepareWorkOrdersTaskToLabor(WorkOrdersTasksToLabor workOrdersTasksToLabor) {

		WorkOrdersTasksToLabor workPart = new WorkOrdersTasksToLabor();

		workPart.setWorkordertaskid(workOrdersTasksToLabor.getWorkordertaskid());
		workPart.setWorkorders_id(workOrdersTasksToLabor.getWorkorders_id());

		workPart.setLabername(workOrdersTasksToLabor.getLabername());
		workPart.setLaberid(workOrdersTasksToLabor.getLaberid());
		Double Laberhourscost = 0.0;
		Double Eachlabercost = 0.0;
		Double laberdisc = 0.0;
		Double labertax = 0.0;

		if (workOrdersTasksToLabor.getLaberhourscost() != null) {
			Laberhourscost = (workOrdersTasksToLabor.getLaberhourscost());
		}
		if (workOrdersTasksToLabor.getEachlabercost() != null) {
			Eachlabercost = (workOrdersTasksToLabor.getEachlabercost());
		}
		if (workOrdersTasksToLabor.getLaberdiscount() != null) {
			laberdisc = workOrdersTasksToLabor.getLaberdiscount();
		}
		if (workOrdersTasksToLabor.getLabertax() != null) {
			labertax = workOrdersTasksToLabor.getLabertax();
		}

		Double discountCost = 0.0;
		Double TotalCost = 0.0;

		discountCost = (Laberhourscost * Eachlabercost) - ((Laberhourscost * Eachlabercost) * (laberdisc / 100));
		TotalCost = round(((discountCost) + (discountCost * (labertax / 100))), 2);

		workPart.setLaberhourscost(Laberhourscost);
		workPart.setEachlabercost(Eachlabercost);
		workPart.setLaberdiscount(laberdisc);
		workPart.setLabertax(labertax);

		workPart.setTotalcost(TotalCost);

		return workPart;
	}

	// get to save in WorkOrdersTasksToReceived
	public WorkOrdersTasksToReceived prepareWorkOrdersTaskToReceiced(WorkOrdersTasksToParts workOrdersTasksToParts,
			InventoryLocation inventoryLocation) {

		WorkOrdersTasksToReceived workPart = new WorkOrdersTasksToReceived();

		workPart.setReceived_partid(inventoryLocation.getPartid());

		// workPart.setReceived_partname(workOrdersTasksToParts.getPartname());
		// workPart.setReceived_partnumber(workOrdersTasksToParts.getPartnumber());
		// workPart.setReceived_location(workOrdersTasksToParts.getLocation());
		workPart.setReceived_locationId(Integer.parseInt(inventoryLocation.getLocationId() + ""));

		workPart.setReceived_quantity(workOrdersTasksToParts.getQuantity());
		workPart.setReceived_workorders_id(workOrdersTasksToParts.getWorkorders_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		workPart.setReceived_date(toDate);
		return workPart;
	}
	
	public WorkOrdersTasksToReceived prepareWorkOrdersTaskToReceicedInAjax(ValueObject object, InventoryLocation inventoryLocation) throws Exception {

		WorkOrdersTasksToReceived workPart = new WorkOrdersTasksToReceived();

		workPart.setReceived_partid(inventoryLocation.getPartid());
		workPart.setReceived_locationId(Integer.parseInt(inventoryLocation.getLocationId() + ""));
		workPart.setReceived_quantity(object.getDouble("quantity"));
		workPart.setReceived_workorders_id(object.getLong("workOrderId"));

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		workPart.setReceived_date(toDate);
		
		return workPart;
	}

	// get WorkOrders To Update Service Reminder information in database
	public ServiceReminder WorkOrdersTOServiceReminder(ServiceReminder serviceReminderFindSameWorkorder,
			WorkOrdersDto work,CustomUserDetails userDetails) throws Exception {

		ServiceReminder service = new ServiceReminder();
		// show Vehicle name
		service.setService_id(serviceReminderFindSameWorkorder.getService_id());
		service.setService_Number(serviceReminderFindSameWorkorder.getService_Number());
		
		service.setServiceTypeId(serviceReminderFindSameWorkorder.getServiceTypeId());
		service.setServiceSubTypeId(serviceReminderFindSameWorkorder.getServiceSubTypeId());
		// show Vehicle name
		service.setVid(serviceReminderFindSameWorkorder.getVid());
		service.setVehicleGroupId(serviceReminderFindSameWorkorder.getVehicleGroupId());

		service.setMeter_interval(serviceReminderFindSameWorkorder.getMeter_interval());
		service.setTime_interval(serviceReminderFindSameWorkorder.getTime_interval());
		service.setTime_intervalperiodId(serviceReminderFindSameWorkorder.getTime_intervalperiodId());

		service.setMeter_threshold(serviceReminderFindSameWorkorder.getMeter_threshold());
		service.setServiceType(serviceReminderFindSameWorkorder.getServiceType());

		service.setTime_threshold(serviceReminderFindSameWorkorder.getTime_threshold());
		service.setTime_thresholdperiodId(serviceReminderFindSameWorkorder.getTime_thresholdperiodId());

		// subscribeduser name in old
		service.setService_subscribeduser_name(serviceReminderFindSameWorkorder.getService_subscribeduser_name());

		service.setCreated(serviceReminderFindSameWorkorder.getCreated());

		// System.out.println("Odometer" + work.getVehicle_Odometer());
		// workOrder to Get odometer
		service.setVehicle_currentOdometer(work.getVehicle_Odometer());

		// get meter interval calculation to service meter
		// CurrentOdometer + Meter interval eg: 5000+500
		// =5500
		// service

		// workOrder to Get odometer
		Integer CurrentOdometer = work.getVehicle_Odometer();
		Integer Meter_interval = serviceReminderFindSameWorkorder.getMeter_interval();

		if (CurrentOdometer == null) {
			CurrentOdometer = 0;
		}
		if (Meter_interval == null) {
			Meter_interval = 0;
		}
		Integer ServiceOdometer = CurrentOdometer + Meter_interval;
		// save Service meter_Odometer reading
		service.setMeter_serviceodometer(ServiceOdometer);

		if (serviceReminderFindSameWorkorder.getMeter_threshold() != null) {

			Integer meter_threshold = serviceReminderFindSameWorkorder.getMeter_threshold();
			if (ServiceOdometer == 0) {
				meter_threshold = 0;
			}

			Integer ServiceOdometer_threshold = ServiceOdometer - meter_threshold;
			// save advance Meter Threshold time and period
			service.setMeter_servicethreshold_odometer(ServiceOdometer_threshold);
		}

		// get Current days
		java.util.Date currentDate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
		// save current days
		service.setTime_interval_currentdate(toDate);
		// System.out.println("current date =" + toDate);

		// time interval not equeal to null only enter the
		// value
		if (serviceReminderFindSameWorkorder.getTime_interval() != null) {

			// get time interval calculation to service to
			// get
			// service time interval days
			Integer time_Intervalperiod = 0;
			if (serviceReminderFindSameWorkorder.getTime_intervalperiodId() >= 0) {
				time_Intervalperiod = serviceReminderFindSameWorkorder.getTime_interval();
			}
			Integer timeserviceDaysPeriod = 0;
			switch (serviceReminderFindSameWorkorder.getTime_intervalperiodId()) {
			case ServiceReminderDto.TIME_INTERVAL_PERIOD_DAYS:
				timeserviceDaysPeriod = time_Intervalperiod;
				break;
			case ServiceReminderDto.TIME_INTERVAL_PERIOD_WEEKS:
				timeserviceDaysPeriod = time_Intervalperiod * 7;
				break;
			case ServiceReminderDto.TIME_INTERVAL_PERIOD_MONTHS:
				timeserviceDaysPeriod = time_Intervalperiod * 30;
				break;
			case ServiceReminderDto.TIME_INTERVAL_PERIOD_YEARS:
				timeserviceDaysPeriod = time_Intervalperiod * 365;
				break;

			default:
				timeserviceDaysPeriod = time_Intervalperiod;
				break;
			}

			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(toDate);
			calendar.add(Calendar.DAY_OF_YEAR, timeserviceDaysPeriod);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			// System.out.println("Service time_date =" +
			// format.format(calendar.getTime()));

			// save Service Time_interval Reminder
			service.setTime_servicedate(calendar.getTime());

			if (serviceReminderFindSameWorkorder.getTime_threshold() != null) {

				Integer Time_threshold = 0;
				switch (serviceReminderFindSameWorkorder.getTime_thresholdperiodId()) {
				case ServiceReminderDto.TIME_INTERVAL_PERIOD_DAYS:
					Time_threshold = serviceReminderFindSameWorkorder.getTime_threshold();
					break;
				case ServiceReminderDto.TIME_INTERVAL_PERIOD_WEEKS:
					Time_threshold = serviceReminderFindSameWorkorder.getTime_threshold() * 7;
					break;
				case ServiceReminderDto.TIME_INTERVAL_PERIOD_MONTHS:
					Time_threshold = serviceReminderFindSameWorkorder.getTime_threshold() * 30;
					break;
				case ServiceReminderDto.TIME_INTERVAL_PERIOD_YEARS:
					Time_threshold = serviceReminderFindSameWorkorder.getTime_threshold() * 365;
					break;

				default:
					Time_threshold = serviceReminderFindSameWorkorder.getTime_threshold();
					break;
				}
				final Calendar calendar_advanceThreshold = Calendar.getInstance();
				// System.out.println("Service =" +
				// Time_servicedate);
				// this Time_servicedate is service time Day
				// so that
				// day to minus advance
				// time threshold time
				calendar_advanceThreshold.setTime(calendar.getTime());
				calendar_advanceThreshold.add(Calendar.DAY_OF_YEAR, -Time_threshold);
				/*
				 * System.out.println( "Service time_advancedate =" +
				 * format.format(calendar_advanceThreshold. getTime() ));
				 */
				// fuel date converted String to date Format

				// save Service Time_interval Advance
				// Threshold
				// dateReminder
				
				
				service.setTime_servicethreshold_date(calendar_advanceThreshold.getTime());

			}
		}

		// service time set FIRST times
		Integer Service_Howtimes = serviceReminderFindSameWorkorder.getService_remider_howtimes() + 1;
		service.setService_remider_howtimes(Service_Howtimes);
		//service.setServicestatus("ACTIVE");

		/**
		 * who Create this vehicle get email id to user profile details
		 */
		//service.setLastModifiedBy(userDetails.getEmail());
		service.setLastModifiedById(userDetails.getId());
		service.setLastupdated(toDate);

		service.setLast_servicecompleldid(work.getWorkorders_id());
		//service.setLast_servicecompleldby(work.getAssignee());
		service.setLast_servicecompleldbyId(work.getAssigneeId());
		service.setLast_servicedate(toDate);
		service.setWorkorders_id(work.getWorkorders_id());
		
		service.setServiceStatusId(ServiceReminderDto.SERVICE_REMINDER_STATUS_INACTIVE);
		service.setDealerServiceEntriesId(serviceReminderFindSameWorkorder.getDealerServiceEntriesId());
		service.setServiceProgramId(serviceReminderFindSameWorkorder.getServiceProgramId());
		service.setServiceScheduleId(serviceReminderFindSameWorkorder.getServiceScheduleId());
		service.setServiceType(serviceReminderFindSameWorkorder.getServiceType());
		if(serviceReminderFindSameWorkorder.getSkipRemark() != null)
		service.setSkipRemark(serviceReminderFindSameWorkorder.getSkipRemark());
		service.setSkip(serviceReminderFindSameWorkorder.isSkip());

		return service;

	}

	// get WorkOrders information in database
	public WorkOrdersDto getWorkOrders(WorkOrders workOrders) throws Exception {

		WorkOrdersDto work = new WorkOrdersDto();

		work.setWorkorders_id(workOrders.getWorkorders_id());
		work.setWorkorders_Number(workOrders.getWorkorders_Number());
		work.setVehicle_vid(workOrders.getVehicle_vid());
		// work.setVehicle_registration(workOrders.getVehicle_registration());
		work.setVehicle_Odometer(workOrders.getVehicle_Odometer());

		if (workOrders.getStart_date() != null) {
			work.setStart_date(dateFormat.format(workOrders.getStart_date()));
		}
		if (workOrders.getDue_date() != null) {
			work.setDue_date(dateFormat.format(workOrders.getDue_date()));
		}
		if (workOrders.getCompleted_date() != null) {
			work.setCompleted_date(dateFormat.format(workOrders.getCompleted_date()));
		}
		work.setWorkorders_driver_id(workOrders.getWorkorders_driver_id());
		// work.setWorkorders_drivername(workOrders.getWorkorders_drivername());
		// work.setAssignee(workOrders.getAssignee());

		// work.setWorkorders_location(workOrders.getWorkorders_location());
		work.setWorkorders_location_ID(workOrders.getWorkorders_location_ID());

		work.setIndentno(workOrders.getIndentno());
		// work.setPriority(workOrders.getPriority());
		// work.setWorkorders_status(workOrders.getWorkorders_status());
		work.setTotalsubworktask_cost(workOrders.getTotalsubworktask_cost());
		work.setTotalworktax_cost(workOrders.getTotalworktax_cost());
		work.setTotalworkorder_cost(workOrders.getTotalworkorder_cost());
		work.setInitial_note(workOrders.getInitial_note());

		work.setWorkorders_document(workOrders.isWorkorders_document());
		work.setWorkorders_document_id(workOrders.getWorkorders_document_id());

		work.setISSUES_ID(workOrders.getISSUES_ID());

		return work;
	}

	// get WorkOrders information in database
	public WorkOrdersDto Show_WorkOrders(WorkOrders workOrders) throws Exception {

		WorkOrdersDto work = new WorkOrdersDto();

		work.setWorkorders_id(workOrders.getWorkorders_id());
		work.setWorkorders_Number(workOrders.getWorkorders_Number());
		work.setVehicle_vid(workOrders.getVehicle_vid());
		// work.setVehicle_registration(workOrders.getVehicle_registration());
		work.setVehicle_Odometer(workOrders.getVehicle_Odometer());

		if (workOrders.getStart_date() != null) {
			work.setStart_date(dateFormat_Name.format(workOrders.getStart_date()));
		}
		if (workOrders.getDue_date() != null) {
			work.setDue_date(dateFormat_Name.format(workOrders.getDue_date()));
		}
		if (workOrders.getCompleted_date() != null) {
			work.setCompleted_date(dateFormat_Name.format(workOrders.getCompleted_date()));
		}
		work.setWorkorders_driver_id(workOrders.getWorkorders_driver_id());
		// work.setWorkorders_drivername(workOrders.getWorkorders_drivername());
		// work.setAssignee(workOrders.getAssignee());

		// work.setWorkorders_location(workOrders.getWorkorders_location());
		work.setWorkorders_location_ID(workOrders.getWorkorders_location_ID());

		work.setIndentno(workOrders.getIndentno());
		// work.setPriority(workOrders.getPriority());
		// work.setWorkorders_status(workOrders.getWorkorders_status());
		work.setTotalsubworktask_cost(workOrders.getTotalsubworktask_cost());
		work.setTotalworktax_cost(workOrders.getTotalworktax_cost());
		work.setTotalworkorder_cost(workOrders.getTotalworkorder_cost());
		work.setInitial_note(workOrders.getInitial_note());

		work.setWorkorders_document(workOrders.isWorkorders_document());
		work.setWorkorders_document_id(workOrders.getWorkorders_document_id());

		work.setISSUES_ID(workOrders.getISSUES_ID());

		// Create and Last updated Display
		// work.setCreatedBy(workOrders.getCreatedBy());
		if (workOrders.getCreated() != null) {
			work.setCreated(CreatedDateTime.format(workOrders.getCreated()));
		}
		// work.setLastModifiedBy(workOrders.getLastModifiedBy());
		if (workOrders.getLastupdated() != null) {
			work.setLastupdated(CreatedDateTime.format(workOrders.getLastupdated()));
		}

		return work;
	}

	public WorkOrdersDto Show_WorkOrders(WorkOrdersDto workOrders) throws Exception {

		WorkOrdersDto work = new WorkOrdersDto();

		work.setWorkorders_id(workOrders.getWorkorders_id());
		work.setWorkorders_Number(workOrders.getWorkorders_Number());
		work.setVehicle_vid(workOrders.getVehicle_vid());
		work.setVehicle_registration(workOrders.getVehicle_registration());
		work.setVehicle_Odometer(workOrders.getVehicle_Odometer());
		work.setVehicle_Odometer_old(workOrders.getVehicle_Odometer_old());

		if (workOrders.getStart_dateOn() != null) {
			work.setStart_date(dateFormat.format(workOrders.getStart_dateOn()));
			work.setStart_time(timeFormat.format(workOrders.getStart_dateOn()));
		}
		if (workOrders.getDue_dateOn() != null) {
			work.setDue_date(dateFormat.format(workOrders.getDue_dateOn()));
			work.setDue_time(timeFormat.format(workOrders.getDue_dateOn()));
		}
		if (workOrders.getCompleted_dateOn() != null) {
			work.setCompleted_dateOn(workOrders.getCompleted_dateOn());
			work.setCompleted_date(dateFormat.format(workOrders.getCompleted_dateOn()));
		}
		
		
		work.setWorkorders_driver_id(workOrders.getWorkorders_driver_id());
		work.setWorkorders_drivername(workOrders.getWorkorders_drivername());
		work.setAssignee(workOrders.getAssignee());
		work.setAssigneeId(workOrders.getAssigneeId());
		
		work.setWorkorders_driver_number(workOrders.getWorkorders_driver_number());

		work.setWorkorders_location(workOrders.getWorkorders_location());
		work.setWorkorders_location_ID(workOrders.getWorkorders_location_ID());

		work.setIndentno(workOrders.getIndentno());
		work.setPriority(workOrders.getPriority());
		work.setPriorityId(workOrders.getPriorityId());
		work.setWorkorders_status(workOrders.getWorkorders_status());
		work.setWorkorders_statusId(workOrders.getWorkorders_statusId());
		work.setTotalsubworktask_cost(Double.parseDouble(toFixedTwo.format(workOrders.getTotalsubworktask_cost())));;
		work.setTotalworktax_cost(Double.parseDouble((toFixedTwo.format((workOrders.getTotalworktax_cost())))));
		work.setTotalworkorder_cost(Double.parseDouble(toFixedTwo.format(workOrders.getTotalworkorder_cost())));
		work.setInitial_note(workOrders.getInitial_note());

		work.setWorkorders_document(workOrders.isWorkorders_document());
		work.setWorkorders_document_id(workOrders.getWorkorders_document_id());

		work.setISSUES_ID(workOrders.getISSUES_ID());

		// Create and Last updated Display
		work.setCreatedBy(workOrders.getCreatedBy());
		if (workOrders.getCreatedOn() != null) {
			work.setCreated(CreatedDateTime.format(workOrders.getCreatedOn()));
		}
		work.setLastModifiedBy(workOrders.getLastModifiedBy());
		if (workOrders.getLastupdatedOn() != null) {
			work.setLastupdated(CreatedDateTime.format(workOrders.getLastupdatedOn()));
		}

		if (workOrders.getWorkorders_driver_number() != null && workOrders.getWorkorders_driver_number() != "") {
			work.setWorkorders_driver_number(workOrders.getWorkorders_driver_number());
		}
		if (workOrders.getOut_work_station() != null && workOrders.getOut_work_station() != "") {
			work.setOut_work_station(workOrders.getOut_work_station());
		}
		if (workOrders.getWorkorders_route() != null && workOrders.getWorkorders_route() != "") {
			work.setWorkorders_route(workOrders.getWorkorders_route());
		}
		if (workOrders.getWorkorders_diesel() != null && workOrders.getWorkorders_diesel() > 0) {
			work.setWorkorders_diesel(workOrders.getWorkorders_diesel());
		}
		work.setVehicle_Odometer(workOrders.getVehicle_Odometer());
		work.setGpsWorkLocation(workOrders.getGpsWorkLocation());
		work.setGpsOdometer(workOrders.getGpsOdometer());
		work.setApprovalStatusId(workOrders.getApprovalStatusId());
		
		if(workOrders.getApprovalStatusId() > 0) {
			work.setApprovalStatus("<span class=\"btn btn-success\">Approved</span>");
			work.setApprovalStatusForMob("Approved");
			work.setApprovalColorId((short) 1);
		}else {
			work.setApprovalStatus("<span class=\"btn btn-danger\">Not Approved</span>");
			work.setApprovalStatusForMob("Not Approved");
			work.setApprovalColorId((short) 2);
		}
		
		if(workOrders.getTallyCompanyName() != null) {
			work.setTallyCompanyName(workOrders.getTallyCompanyName());
			work.setTallyCompanyId(workOrders.getTallyCompanyId());
		}
		work.setSubLocationId(workOrders.getSubLocationId());
		work.setSubLocation(workOrders.getSubLocation());
		work.setAccidentId(workOrders.getAccidentId());
		work.setIssueNumber(workOrders.getIssueNumber());
		work.setIssueSummary(workOrders.getIssueSummary());
		work.setWorkLocationId(workOrders.getWorkLocationId());
		work.setWorkLocation(workOrders.getWorkLocation());
		work.setIssueIds(workOrders.getIssueIds());
		return work;
	}

	/* list of WorkOrders get and Display to open WorkOrders */
	public List<WorkOrdersDto> prepareListofWorkOrders(List<WorkOrders> WorkOrdersList) {
		List<WorkOrdersDto> Dtos = null;
		if (WorkOrdersList != null && !WorkOrdersList.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersDto>();
			WorkOrdersDto work = null;
			for (WorkOrders workOrders : WorkOrdersList) {
				work = new WorkOrdersDto();

				work.setWorkorders_id(workOrders.getWorkorders_id());
				work.setWorkorders_Number(workOrders.getWorkorders_Number());
				work.setVehicle_vid(workOrders.getVehicle_vid());
				// work.setVehicle_registration(workOrders.getVehicle_registration());
				work.setVehicle_Odometer(workOrders.getVehicle_Odometer());
				if (workOrders.getStart_date() != null) {
					work.setStart_date(dateFormat_Name.format(workOrders.getStart_date()));
				}
				if (workOrders.getDue_date() != null) {
					work.setDue_date(dateFormat_Name.format(workOrders.getDue_date()));
				}
				// work.setCompleted_date(dateFormat.format(workOrders.getCompleted_date()));
				// work.setWorkorders_drivername(workOrders.getWorkorders_drivername());
				// work.setAssignee(workOrders.getAssignee());
				// work.setWorkorders_location(workOrders.getWorkorders_location());
				// work.setPriority(workOrders.getPriority());
				if (workOrders.getCompleted_date() != null) {
					work.setCompleted_date(dateFormat_Name.format(workOrders.getCompleted_date()));
				}

				work.setTotalworkorder_cost(workOrders.getTotalworkorder_cost());
				// work.setWorkorders_status(workOrders.getWorkorders_status());

				work.setWorkorders_document(workOrders.isWorkorders_document());
				work.setWorkorders_document_id(workOrders.getWorkorders_document_id());

				Dtos.add(work);
			}
		}
		return Dtos;
	}

	public List<WorkOrdersDto> prepareListWorkOrders(List<WorkOrdersDto> WorkOrdersList) {
		List<WorkOrdersDto> Dtos = null;
		if (WorkOrdersList != null && !WorkOrdersList.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersDto>();
			WorkOrdersDto work = null;
			for (WorkOrdersDto workOrders : WorkOrdersList) {
				work = new WorkOrdersDto();
				if (workOrders.getStart_dateOn() != null) {
					work.setStart_date(dateFormat_Name.format(workOrders.getStart_dateOn()));
				}
				if (workOrders.getDue_dateOn() != null) {
					work.setDue_date(dateFormat_Name.format(workOrders.getDue_dateOn()));
				}
				
				getWorkOrderDTO(work, workOrders);
				
				Dtos.add(work);
			}
		}
		return Dtos;
	}

	private void getWorkOrderDTO(WorkOrdersDto work, WorkOrdersDto workOrders) {
		work.setWorkorders_id(workOrders.getWorkorders_id());
		work.setWorkorders_Number(workOrders.getWorkorders_Number());
		work.setVehicle_vid(workOrders.getVehicle_vid());
		work.setVehicle_registration(workOrders.getVehicle_registration());
		work.setVehicle_Odometer(workOrders.getVehicle_Odometer());
		work.setWorkorders_drivername(workOrders.getWorkorders_drivername());
		work.setAssignee(workOrders.getAssignee());
		work.setWorkorders_location(workOrders.getWorkorders_location());
		work.setPriority(workOrders.getPriority());
		work.setTotalworkorder_cost(workOrders.getTotalworkorder_cost());
		work.setWorkorders_status(workOrders.getWorkorders_status());				
		work.setWorkorders_document(workOrders.isWorkorders_document());
		work.setWorkorders_document_id(workOrders.getWorkorders_document_id());
		work.setSubLocationId(workOrders.getSubLocationId());
		work.setSubLocation(workOrders.getSubLocation());
		if (workOrders.getCompleted_dateOn() != null) {
			work.setCompleted_date(dateFormat_Name.format(workOrders.getCompleted_dateOn()));
		}
		work.setApprovalStatusId(workOrders.getApprovalStatusId());
		work.setWorkorders_statusId(workOrders.getWorkorders_statusId());
		work.setIssueSummary(workOrders.getIssueSummary());
	}

	public List<WorkOrdersDto> prepareWorkOrdersList(List<WorkOrdersDto> WorkOrdersList) {
		List<WorkOrdersDto> Dtos = null;
		if (WorkOrdersList != null && !WorkOrdersList.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersDto>();
			WorkOrdersDto work = null;
			for (WorkOrdersDto workOrders : WorkOrdersList) {
				work = new WorkOrdersDto();
				if (workOrders.getStart_dateOn() != null) {
					work.setStart_date(ft.format(workOrders.getStart_dateOn()));
				}
				if (workOrders.getDue_dateOn() != null) {
					work.setDue_date(ft.format(workOrders.getDue_dateOn()));
				}

				getWorkOrderDTO(work, workOrders);
				
				Dtos.add(work);
			}
		}
		return Dtos;
	}

	// get WorkOrders information in database
	public WorkOrdersTasksDto getWorkOrdersTask(WorkOrdersTasks WorkOrdersTask) {

		WorkOrdersTasksDto WorkOrders = new WorkOrdersTasksDto();

		/*
		 * WorkOrders.setWorkordertaskid(WorkOrdersTask.getWorkordertaskid());
		 * WorkOrders.setWorkorders_id(WorkOrdersTask.getWorkorders());
		 */
		return WorkOrders;
	}

	/* delete the WorkOrders */
	public WorkOrders deleteWorkOrders(WorkOrders WorkOrders) {

		/*
		 * WorkOrders WorkOrders=new WorkOrders();
		 * 
		 * 
		 * WorkOrders.setIssue_id(WorkOrders.getIssue_id());
		 * 
		 * 
		 * WorkOrders.setIssue_vehicle(WorkOrders.getIssue_vehicle());
		 * WorkOrders.setIssue_reportdate(WorkOrders.getIssue_reportdate());
		 * WorkOrders.setIssue_summary(WorkOrders.getIssue_summary());
		 * WorkOrders.setIssue_description(WorkOrders.getIssue_description());
		 * WorkOrders.setIssue_odometer(WorkOrders.getIssue_odometer());
		 * WorkOrders.setIssue_odometer_attributes(WorkOrders.
		 * getIssue_odometer_attributes());
		 * WorkOrders.setIssue_labelNo(WorkOrders.getIssue_labelNo());
		 * WorkOrders.setIssue_flag(WorkOrders.getIssue_flag());
		 * WorkOrders.setIssue_reportedby(WorkOrders.getIssue_reportedby());
		 * WorkOrders.setIssue_assignedto(WorkOrders.getIssue_assignedto());
		 * 
		 * WorkOrders.setCreated(WorkOrders.getCreated());
		 * WorkOrders.setLastupdated(WorkOrders.getLastupdated());
		 */

		return WorkOrders;
	}

	/**
	 * @param workOrdersTasks
	 * @return
	 */
	public List<WorkOrdersTasksDto> View_WorkOrder_Task_Details(List<WorkOrdersTasksDto> WOTasks) {
		List<WorkOrdersTasksDto> Dtos = null;
		if (WOTasks != null && !WOTasks.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersTasksDto>();
			WorkOrdersTasksDto Task = null;
			for (WorkOrdersTasksDto workTasks : WOTasks) {
				Task = new WorkOrdersTasksDto();

				Task.setWorkordertaskid(workTasks.getWorkordertaskid());
				Task.setJob_subtypetask_id(workTasks.getJob_subtypetask_id());
				Task.setJob_subtypetask(workTasks.getJob_subtypetask());
				Task.setJob_typetask(workTasks.getJob_typetask());
				Task.setJob_typetaskId(workTasks.getJob_typetaskId());
				if (workTasks.getLast_occurred_dateOn() != null) {
					Task.setLast_occurred_date(dateFormat_Name.format(workTasks.getLast_occurred_dateOn()));
				}
				Task.setLast_occurred_odameter(workTasks.getLast_occurred_odameter());
				Task.setLast_occurred_woId(workTasks.getLast_occurred_woId());
				Task.setMark_complete(workTasks.getMark_complete());
				Task.setTotallaber_cost(Double.parseDouble(toFixedTwo.format(workTasks.getTotallaber_cost())));
				Task.setTotalpart_cost(Double.parseDouble(toFixedTwo.format(workTasks.getTotalpart_cost())));
				Task.setTotaltask_cost(Double.parseDouble(toFixedTwo.format(workTasks.getTotaltask_cost())));
				Task.setVehicle_vid(workTasks.getVehicle_vid());
				Task.setWorkorders_id(workTasks.getWorkorders_id());
				Task.setJobTypeRemark(workTasks.getJobTypeRemark());
				Task.setService_id(workTasks.getService_id());
				Task.setService_Number(workTasks.getService_Number());
				Task.setVehicle_registration(workTasks.getVehicle_registration());
				Task.setService_NumberStr(workTasks.getService_NumberStr());
				Task.setIssueIds(workTasks.getIssueIds());
				if(workTasks.getIssueIds() != null && workTasks.getIssueIds() > 0)
				Task.setIssueIdsEncry(AESEncryptDecrypt.encryptBase64(""+workTasks.getIssueIds()));
				Task.setIssueNumber(workTasks.getIssueNumber());
				Task.setIssueSummary(workTasks.getIssueSummary());
				Task.setPcName(workTasks.getPcName());
				Dtos.add(Task);
			}
		}

		return Dtos;
	}
	
	public boolean getAllPartAssigned(List<WorkOrdersTasksToPartsDto>  tasksToPartsDtos) {
		if(tasksToPartsDtos != null && !tasksToPartsDtos.isEmpty()) {
			for (WorkOrdersTasksToPartsDto workOrdersTasksToPartsDto : tasksToPartsDtos) {
				if(workOrdersTasksToPartsDto.isWarrantyApplicable() || workOrdersTasksToPartsDto.isAssetIdRequired()) {
					if(workOrdersTasksToPartsDto.getAssignedNoPart() < workOrdersTasksToPartsDto.getQuantity()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * @param workOrder
	 * @return
	 */
	public List<WorkOrdersTasksToPartsDto> prepare_Part_Consuming_WorkOrdersTasksToParts(
			List<WorkOrdersTasksToPartsDto> workOrder) {
		// NOTE: part Report Consuming Report
		List<WorkOrdersTasksToPartsDto> Dtos = null;
		if (workOrder != null && !workOrder.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersTasksToPartsDto>();
			WorkOrdersTasksToPartsDto Task = null;
			for (WorkOrdersTasksToPartsDto workTasks : workOrder) {
				Task = new WorkOrdersTasksToPartsDto();

				if (workTasks.getInventory_id() != 0) {
					Task.setPartid(workTasks.getPartid());
					Task.setPartname(workTasks.getPartname());
					Task.setPartnumber(workTasks.getPartnumber());
					Task.setInventory_id(workTasks.getInventory_id());
					Dtos.add(Task);
				}
			}
		}

		return Dtos;
	}
	
	public ServiceReminderWorkOrderHistory getServiceWorkOrderDto(ServiceReminder	serviceReminder, WorkOrdersDto	workOrdersDto, CustomUserDetails userDetails)throws Exception{
		ServiceReminderWorkOrderHistory		orderHistory	= null;
		try {
			orderHistory	= new ServiceReminderWorkOrderHistory();
			
			orderHistory.setService_id(serviceReminder.getService_id());
			orderHistory.setService_Number(serviceReminder.getService_Number());
			orderHistory.setWorkOrderId(workOrdersDto.getWorkorders_id());
			orderHistory.setServiceThresholdDate(serviceReminder.getTime_servicethreshold_date());
			orderHistory.setServiceDate(serviceReminder.getTime_servicedate());
			orderHistory.setServiceTheshHoldOdometer(serviceReminder.getMeter_servicethreshold_odometer());
			orderHistory.setServiceOdometer(serviceReminder.getMeter_serviceodometer());
			orderHistory.setWorkOrderCompletedOn(new Date());
			orderHistory.setWorkOrderOdometer(workOrdersDto.getVehicle_Odometer());
			orderHistory.setWorkOrderCompletedById(userDetails.getId());
			orderHistory.setCompanyId(userDetails.getCompany_id());
			orderHistory.setVid(serviceReminder.getVid());
			
			return orderHistory;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public WorkOrderTxnChecker getWorkOrderTxnChecker(WorkOrdersDto	workOrders) throws Exception{
		WorkOrderTxnChecker			workOrderTxnChecker		= null;
		try {
			workOrderTxnChecker	= new WorkOrderTxnChecker();
			
			workOrderTxnChecker.setCompanyId(workOrders.getCompanyId());
			workOrderTxnChecker.setTxnInsertionDateTime(DateTimeUtility.getCurrentTimeStamp());
			workOrderTxnChecker.setVid(workOrders.getVehicle_vid());
			workOrderTxnChecker.setWorkOrderId(workOrders.getWorkorders_id());
			
			return workOrderTxnChecker;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Map<Long, List<WorkOrdersTasksToPartsDto>> getPartConsumedListInWorkorders(List<WorkOrdersTasksToPartsDto> newWorkOrdersTasksDtoList) {
		Map<Long, List<WorkOrdersTasksToPartsDto>> 			partWiseHM 	= null;
		List<WorkOrdersTasksToPartsDto>						workOrdersTasksList	= null;
		try {
			if(newWorkOrdersTasksDtoList != null) {
				partWiseHM 		= new HashMap<>();
				
				for(WorkOrdersTasksToPartsDto dto : newWorkOrdersTasksDtoList) {
					workOrdersTasksList	=  partWiseHM.get(dto.getPartid());
					if(workOrdersTasksList == null) {
						workOrdersTasksList 			= new ArrayList<>();
					}
					workOrdersTasksList.add(dto);
					partWiseHM.put(dto.getPartid(), workOrdersTasksList);
				}
			}
		} catch (Exception e) {
			LOGGER.error("ERR"+e);
		}
		return partWiseHM;
	}
	
	public WorkOrders saveWorkOrderDetails(ValueObject object) throws Exception {
		CustomUserDetails 			userDetails 			= null;
		try {
			userDetails = (CustomUserDetails) object.get("userDetails");
			WorkOrders  work  =  new WorkOrders();

			work.setVehicle_vid(object.getInt("vid"));
			work.setWorkorders_driver_id(object.getInt("driverId",0));
			work.setWorkorders_driver_number(object.getString("driverNumber",""));
			work.setAssigneeId(object.getLong("assignedTo"));
			if (object.getString("woStartDate") != null) {
				String start_time = "00:00";
				if(object.getString("woStartTime") != null && object.getString("woStartTime") != "") {
					start_time	= object.getString("woStartTime");
				}
				java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(object.getString("woStartDate"), start_time);
				java.sql.Date start_date = new java.sql.Date(date.getTime());
				work.setStart_date(start_date);
			}
			
			if (object.getString("woEndDate") != null) {
				String end_time = "00:00";
				if(object.getString("woEndTime") != null && object.getString("woEndTime") != "") {
					end_time	= object.getString("woEndTime");
				}
				java.util.Date date2 = DateTimeUtility.getDateTimeFromDateTimeString(object.getString("woEndDate"), end_time);
				java.sql.Date due_date = new java.sql.Date(date2.getTime());
				work.setDue_date(due_date);
			}
			
			work.setOut_work_station(object.getString("outWorkStation",""));
			work.setGpsWorkLocation(object.getString("gpsWorkLocation",""));
			work.setWorkorders_location_ID((int)object.get("locationId"));
			work.setWorkorders_route(object.getString("workorders_route",""));
			work.setVehicle_Odometer_old(object.getInt("vehicle_Odometer_Old", 0));
			work.setVehicle_Odometer(object.getInt("vehicle_Odometer"));
			work.setGpsOdometer(object.getDouble("gpsOdometer",0));
			work.setWorkorders_diesel(object.getLong("workorders_diesel",0));
			work.setIndentno(object.getString("indentno",""));
			work.setPriorityId(object.getShort("priority"));
			work.setInitial_note(object.getString("initial_note",""));
			work.setTallyCompanyId(object.getLong("tallyCompanyId",0));
			work.setWorkorders_document(false);
			work.setWorkorders_document_id(WORK_ORDER_DEFALAT_VALUE);
			if(object.getBoolean("multiIssueInWO",false)) {
				work.setIssueIds(object.getString("issueId",""));
			}else {
				work.setISSUES_ID(object.getLong("issueId",0));
			}

			Date currentDateUpdate = new Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			work.setCreated(toDate);
			work.setLastupdated(toDate);
			work.setMarkForDelete(false);
			work.setCreatedById(userDetails.getId());
			work.setLastModifiedById(userDetails.getId());
			work.setCompanyId(userDetails.getCompany_id());
			work.setSubLocationId(object.getInt("subLocationId",0));
			work.setAccidentId(object.getLong("accidentId",0));
			work.setWorkLocationId(object.getInt("workLocationId",0));
			return work;
			
		} catch (Exception e) {
			LOGGER.error("ERR", e);
			throw e;
		}
	}
	
	public WorkOrdersTasksToLabor saveLabourForWorkOderTask(ValueObject object) throws Exception {

		WorkOrdersTasksToLabor workPart = new WorkOrdersTasksToLabor();

		workPart.setWorkordertaskid(object.getLong("woTaskId"));
		workPart.setWorkorders_id(object.getLong("workOrderId"));
		
		if (object.getString("laberid") != null
				&& WorkOrdersDto.getParseDriverID_STRING_TO_ID(object.getString("laberid")) != 0) {
			workPart.setLaberid(object.getInt("laberid",0));
			
		}else {
			workPart.setLaberid(object.getInt("laberid",0));
			workPart.setLabername(object.getString("laberid"));
		}
		
		Double Laberhourscost = 0.0;
		Double Eachlabercost = 0.0;
		Double laberdisc = 0.0;
		Double labertax = 0.0;

		if (object.getString("laberhourscost") != null) {
			Laberhourscost = object.getDouble("laberhourscost");
		}
		
		if (object.getString("eachlabercost") != null) {
			Eachlabercost = object.getDouble("eachlabercost");
		}
		
		if (object.getString("laberdiscount") != null) {
			laberdisc = object.getDouble("laberdiscount");
		}
		
		if (object.getString("labertax") != null) {
			labertax = object.getDouble("labertax");
		}

		Double discountCost = 0.0;
		Double TotalCost = 0.0;

		discountCost = (Laberhourscost * Eachlabercost) - ((Laberhourscost * Eachlabercost) * (laberdisc / 100));
		TotalCost = round(((discountCost) + (discountCost * (labertax / 100))), 2);

		workPart.setLaberhourscost(Laberhourscost);
		workPart.setEachlabercost(Eachlabercost);
		workPart.setLaberdiscount(laberdisc);
		workPart.setLabertax(labertax);

		workPart.setTotalcost(TotalCost);

		return workPart;
	}
	
	public WorkOrders updateWorkOrderDetails(ValueObject object) throws Exception {
		CustomUserDetails 			userDetails 			= null;
		try {
			userDetails = (CustomUserDetails) object.get("userDetails");
			WorkOrders  work  =  new WorkOrders();
			
			work.setWorkorders_id(object.getLong("workOrderId"));
			work.setWorkorders_Number(object.getLong("woNumber"));
			work.setVehicle_vid(object.getInt("vid"));
			work.setWorkorders_driver_id(object.getInt("driverId",0));
			work.setWorkorders_driver_number(object.getString("driverNumber",""));
			work.setAssigneeId(object.getLong("assignedTo"));

			if (object.getString("woStartDate") != null) {
				String start_time = "00:00";
				if(object.getString("woStartTime") != null && object.getString("woStartTime") != "") {
					start_time	= object.getString("woStartTime");
				}
				java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(object.getString("woStartDate"), start_time);
				java.sql.Date start_date = new java.sql.Date(date.getTime());
				work.setStart_date(start_date);
			}
			
			if (object.getString("woEndDate") != null) {
				String end_time = "00:00";
				if(object.getString("woEndTime") != null && object.getString("woEndTime") != "") {
					end_time	= object.getString("woEndTime");
				}
				java.util.Date date2 = DateTimeUtility.getDateTimeFromDateTimeString(object.getString("woEndDate"), end_time);
				java.sql.Date due_date = new java.sql.Date(date2.getTime());
				work.setDue_date(due_date);
			}
			
			work.setOut_work_station(object.getString("outWorkStation",""));
			work.setGpsWorkLocation(object.getString("gpsWorkLocation",""));
			work.setWorkorders_location_ID(object.getInt("location"));
			work.setWorkorders_route(object.getString("workorders_route",""));
			work.setVehicle_Odometer_old(object.getInt("vehicle_Odometer_Old"));
			work.setVehicle_Odometer(object.getInt("vehicle_Odometer"));
			work.setGpsOdometer(object.getDouble("gpsOdometer",0));
			work.setWorkorders_diesel(object.getLong("workorders_diesel",0));
			work.setIndentno(object.getString("indentno",""));
			work.setPriorityId(object.getShort("priority"));
			work.setInitial_note(object.getString("initial_note",""));
			work.setTallyCompanyId(object.getLong("tallyCompanyId",0));
			work.setWorkorders_document(false);
			work.setWorkorders_document_id(WORK_ORDER_DEFALAT_VALUE);
			work.setISSUES_ID(WORK_ORDER_DEFALAT_VALUE);

			Date currentDateUpdate = new Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			
			work.setLastupdated(toDate);
			work.setMarkForDelete(false);
			work.setLastModifiedById(userDetails.getId());
			work.setCompanyId(userDetails.getCompany_id());
			work.setSubLocationId(object.getInt("subLocationId", 0));
			work.setAccidentId(object.getLong("accidentId",0));
			work.setWorkLocationId(object.getInt("workLocationId", 0));
			return work;
			
		} catch (Exception e) {
			LOGGER.error("ERR", e);
			throw e;
		}
	}
	
	public static WorkOrderRemark getWorkOrderRemarkDto(WorkOrdersDto	ordersDto, ValueObject	valueObject) throws Exception{
		
		WorkOrderRemark	orderRemark = new  WorkOrderRemark();
		orderRemark.setRemark(valueObject.getString("woRemark"));
		orderRemark.setWorkOrderId(ordersDto.getWorkorders_id());
		orderRemark.setCreatedOn(new Date());
		orderRemark.setCreatedById(valueObject.getLong("userId",0));
		orderRemark.setCompanyId(valueObject.getInt("companyId",0));
		orderRemark.setDriverId(valueObject.getInt("driverId",0));
		orderRemark.setAssignee(valueObject.getLong("assignee",0));
		orderRemark.setMarkForDelete(false);
		
		return orderRemark;
	}
	
	public  List<WorkOrderRemarkDto> getWorkOrderRemarkList(List<WorkOrderRemark>	remarkList) throws Exception{
	
		List<WorkOrderRemarkDto>	list = new ArrayList<WorkOrderRemarkDto>();
		if(remarkList != null && !remarkList.isEmpty()) {
			WorkOrderRemarkDto	orderRemark = null;
			for (WorkOrderRemark remark : remarkList) {
				orderRemark	= new WorkOrderRemarkDto();
				orderRemark.setRemark(remark.getRemark());
				orderRemark.setWorkOrderId(remark.getWorkOrderId());
				orderRemark.setCreatedOn(ft.format(remark.getCreatedOn()));
				orderRemark.setRemarkTypeId(remark.getRemarkTypeId());
				if(remark.getRemarkTypeId() == 1) {
					orderRemark.setRemarkType("Completion Remark");
				}
				else if(remark.getRemarkTypeId() == 2) {
					orderRemark.setRemarkType("ReOpen Remark");
				}
				else if(remark.getRemarkTypeId() == 3) {
					orderRemark.setRemarkType("Hold Remark");
				}
				else if(remark.getRemarkTypeId() == 4) {
					orderRemark.setRemarkType("In-Process Remark");
				}
				else {
					orderRemark.setRemarkType("");
				}
			
				
				list.add(orderRemark);
			}
		}
		
		return list;
	}
	
	public WorkOrdersTasks  getWorkOrdersTasksModal(ServiceReminderDto	reminderDto, WorkOrders	orders) throws Exception {
		WorkOrdersTasks		workOrdersTasks		= null;
		try {
			workOrdersTasks	= new WorkOrdersTasks();
			
			return workOrdersTasks;
		} catch (Exception e) {
			throw e;
		}
		
	}
}
