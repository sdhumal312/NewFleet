package org.fleetopgroup.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.GPSConstant;
import org.fleetopgroup.constant.IssuesTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.VehicleAgentConstant;
import org.fleetopgroup.constant.VehicleConfigurationContant;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.constant.VehicleGPSConfiguratinConstant;
import org.fleetopgroup.constant.VehicleOwnerShip;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.constant.WorkOrdersConfigurationConstants;
import org.fleetopgroup.constant.WorkOrdersType;
import org.fleetopgroup.persistence.bl.InventoryBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.VehicleAgentTxnCheckerBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.bl.VehicleProfitAndLossTxnCheckerBL;
import org.fleetopgroup.persistence.bl.WorkOrdersBL;
import org.fleetopgroup.persistence.dao.ServiceReminderWorkOrderHistoryRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.InventoryLocationDto;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.JobSubTypeDto;
import org.fleetopgroup.persistence.dto.JobTypeDto;
import org.fleetopgroup.persistence.dto.PartLocationPermissionDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksToPartsDto;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.EmailAlertQueue;
import org.fleetopgroup.persistence.model.Inventory;
import org.fleetopgroup.persistence.model.InventoryLocation;
import org.fleetopgroup.persistence.model.Issues;
import org.fleetopgroup.persistence.model.IssuesTasks;
import org.fleetopgroup.persistence.model.JobSubType;
import org.fleetopgroup.persistence.model.JobType;
import org.fleetopgroup.persistence.model.PartCategories;
import org.fleetopgroup.persistence.model.PartLocations;
import org.fleetopgroup.persistence.model.ReasonForRepairType;
import org.fleetopgroup.persistence.model.ServiceEntries;
import org.fleetopgroup.persistence.model.ServiceEntriesTasks;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.model.ServiceReminderWorkOrderHistory;
import org.fleetopgroup.persistence.model.SmsAlertQueue;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleAgentTxnChecker;
import org.fleetopgroup.persistence.model.VehicleExpenseDetails;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.model.WorkOrderSequenceCounter;
import org.fleetopgroup.persistence.model.WorkOrderTxnChecker;
import org.fleetopgroup.persistence.model.WorkOrders;
import org.fleetopgroup.persistence.model.WorkOrdersDocument;
import org.fleetopgroup.persistence.model.WorkOrdersTasks;
import org.fleetopgroup.persistence.model.WorkOrdersTasksToLabor;
import org.fleetopgroup.persistence.model.WorkOrdersTasksToParts;
import org.fleetopgroup.persistence.model.WorkOrdersTasksToReceived;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.service.PartCategoriesService;
import org.fleetopgroup.persistence.service.VehicleProfitAndLossTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IEmailAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IJobSubTypeService;
import org.fleetopgroup.persistence.serviceImpl.IJobTypeService;
import org.fleetopgroup.persistence.serviceImpl.IPartCategoriesService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IPartMeasurementUnitService;
import org.fleetopgroup.persistence.serviceImpl.IReasonRepairTypeService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ISmsAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentIncomeExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGPSDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrderSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrderTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

@Controller
public class WorkOrdersController extends MainActivity {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired private	IVehicleService 							vehicleService;
	@Autowired private	IDriverService 								driverService;
	@Autowired private	IWorkOrdersService 							workOrdersService;
	@Autowired private	IIssuesService 								issuesService;
	@Autowired private	IUserProfileService 						userProfileService;
	@Autowired private	IInventoryService 							inventoryService;
	@Autowired private	IWorkOrderSequenceService 					workOrderSequenceService;
	@Autowired private	IVehicleDocumentService						vehicleDocumentService;
	@Autowired private	IJobTypeService 							jobTypeService;
	@Autowired private	IJobSubTypeService 							jobSubTypeService;
	@Autowired private	IServiceReminderService 					serviceReminderService;
	@Autowired private	IVehicleOdometerHistoryService 				vehicleOdometerHistoryService;
	@Autowired private	IPartLocationPermissionService 				partLocationPermissionService;
	@Autowired private	IServiceEntriesService 						serviceEntriesService;
	@Autowired private	ServiceReminderWorkOrderHistoryRepository	orderHistoryRepository;
	@Autowired private	CompanyConfigurationService 				companyConfigurationService;
	@Autowired private	IPartLocationsService 						partLocationsService;
	@Autowired private	IWorkOrderTxnCheckerService					workOrderTxnCheckerService;
	@Autowired private  VehicleProfitAndLossTxnCheckerService		vehicleProfitAndLossTxnCheckerService;
	@Autowired private	IVehicleProfitAndLossService				vehicleProfitAndLossService;
	@Autowired private	IVehicleExpenseDetailsService				vehicleExpenseDetailsService;
	@Autowired private	IEmailAlertQueueService 					emailAlertQueueService;
	@Autowired private	ISmsAlertQueueService 						smsAlertQueueService;
	@Autowired private	IServiceReminderService 					ServiceReminderService;
	@Autowired private	IVehicleGPSDetailsService					vehicleGPSDetailsService;
	@Autowired private	IVehicleAgentTxnCheckerService				vehicleAgentTxnCheckerService;
	@Autowired private	IVehicleAgentIncomeExpenseDetailsService	vehicleAgentIncomeExpenseDetailsService;
	@Autowired private	IPartMeasurementUnitService					partMeasurementUnitService;
	@Autowired IPartCategoriesService                               partCategoriesService;
	@Autowired private	IReasonRepairTypeService 					reasonTypeService;
	

	VehicleOdometerHistoryBL 					vehicleOdometerHistoryBL 		= new VehicleOdometerHistoryBL();
	InventoryBL 								inventoryBL 					= new InventoryBL();
	WorkOrdersBL 								workOrdersBL 					= new WorkOrdersBL();
	VehicleBL 									vehicleBL 						= new VehicleBL();
	PartLocationsBL 							partLocationsBL 				= new PartLocationsBL();
	VehicleProfitAndLossTxnCheckerBL			txnCheckerBL 					= new VehicleProfitAndLossTxnCheckerBL();
	VehicleAgentTxnCheckerBL					agentTxnCheckerBL				= new VehicleAgentTxnCheckerBL();

	
	SimpleDateFormat sqlFormat 		= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sqldateTime 	= new SimpleDateFormat("dd-MM-yyyy");

	
	// Note: This /searchWorkOrder Controller is WorkOrder Details Search Based
	// one WorkOrder ID AND Vehicle Name
	@RequestMapping(value = "/searchWorkOrder", method = RequestMethod.POST)
	public ModelAndView searchWorkOrders(@RequestParam("Search") final String Workorders_id,
			final HttpServletResponse resul) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("WorkOrder", workOrdersBL.prepareListWorkOrders(workOrdersService.SearchWorkOrders(Workorders_id,	userDetails.getCompany_id(), userDetails.getId())));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Work Order:", e);
		} finally {
			userDetails = null;
		}
		return new ModelAndView("WorkOrder_Report", model);
	}

	// Note: This /searchWorkOrder Controller is WorkOrder Details Search Based
	// one WorkOrder ID AND Vehicle Name
	@RequestMapping(value = "/searchWorkOrderShow", method = RequestMethod.POST)
	public ModelAndView searchWorkOrdersShow(@RequestParam("Search") final Long Workorders_id,
			final HttpServletResponse result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		WorkOrdersDto workOrders = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			int count = workOrdersService.getWorkOrderCountByNumber(Workorders_id, userDetails.getCompany_id());
			if(count == 1) {
				workOrders = workOrdersService.getWorkOrdersByNumber(Workorders_id, userDetails.getId(), userDetails.getCompany_id());
				if (workOrders != null) {
					
					WorkOrdersDto work = workOrdersBL.Show_WorkOrders(workOrders);
					model.put("WorkOrder", work);
					
					model.put("WorkOrderTask", workOrdersBL.View_WorkOrder_Task_Details(
							workOrdersService.getWorkOrdersTasks(work.getWorkorders_id(), userDetails.getCompany_id())));
					
					model.put("WorkOrderTaskPart", workOrdersService.getWorkOrdersTasksToParts(work.getWorkorders_id(),	userDetails.getCompany_id()));
					
					model.put("WorkOrderTaskLabor", workOrdersService.getWorkOrdersTasksToLabor(work.getWorkorders_id(), userDetails.getCompany_id()));
					
					if (work.getWorkorders_statusId() == WorkOrdersType.WORKORDERS_STATUS_ONHOLD) {
						
						return new ModelAndView("addWorkOrdersPartsOnHold", model);
					}
					if (work.getWorkorders_statusId() == WorkOrdersType.WORKORDERS_STATUS_COMPLETE) {
						
						return new ModelAndView("addWorkOrdersPartsCOMPLETE", model);
					}
				} else {
					model.put("NotFound", true);
					return new ModelAndView("redirect:/viewWorkOrder.in", model);
				}
			}else {
				model.put("WorkOrder", workOrdersBL.prepareListWorkOrders(workOrdersService.SearchWorkOrdersList(Workorders_id,	userDetails.getCompany_id(), userDetails.getId())));
				return new ModelAndView("WorkOrder_Report", model);
			
			}

		} catch (NullPointerException e) {
			model.put("NotFound", true);
			return new ModelAndView("redirect:/viewWorkOrder.in", model);
		} catch (Exception e) {
			model.put("NotFound", true);
			return new ModelAndView("redirect:/viewWorkOrder.in", model);
		}

		return new ModelAndView("addWorkOrdersParts", model);
	}

	// Note: This /viewWorkOrder Controller is WorkOrder Show Page in Open
	// Status Only
	@RequestMapping(value = "/newWorkOrders/{pageNumber}", method = RequestMethod.GET)
	public String newWorkOrders(@PathVariable Integer pageNumber, Model model) throws Exception {
		short 						WorkOrders_open 		= WorkOrdersType.WORKORDERS_STATUS_OPEN;
		CustomUserDetails 			userDetails 			= null;
		HashMap<String, Object>  	configuration			= null;
		List<WorkOrdersDto> 		pageList 				= null; 
		
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			
			Page<WorkOrders> page = workOrdersService.getDeployment_Page_WorkOrders(WorkOrders_open, pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("WorkOrderCount", page.getTotalElements());
			
			if((boolean) configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_START_AND_DUE_TIME_FIELD, false)) {
				pageList = workOrdersBL.prepareWorkOrdersList(workOrdersService.listOpenWorkOrders(WorkOrders_open, pageNumber, userDetails));				
			} else {
				pageList = workOrdersBL.prepareListWorkOrders(workOrdersService.listOpenWorkOrders(WorkOrders_open, pageNumber, userDetails));
			}

			model.addAttribute("WorkOrder", pageList);
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("newWorkOrders Page:", e);
			e.printStackTrace();
		}
		return "newWorkOrders";
	}

	// Note: This /addWorkOrder Controller is Add New WorkOrder Entries To Show
	// Filed Details
	@RequestMapping(value = "/addWorkOrder", method = RequestMethod.GET)
	public ModelAndView addWorkOrders() {

		Map<String, Object> 		model 				= new HashMap<String, Object>();
		CustomUserDetails 			userDetails 		= null;
		HashMap<String, Object>     configuration		= null;
		HashMap<String, Object>     gpsConfiguration	= null;
		boolean						tallyConfig			= false;
		int							noOfBackDays		= 0;
		String 						minBackDate			= null; 
		try {
			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			tallyConfig			= (boolean) configuration.getOrDefault(WorkOrdersConfigurationConstants.TALLY_COMPANY_MASTER_IN_WO, false);
			noOfBackDays		= (Integer) configuration.getOrDefault(WorkOrdersConfigurationConstants.NO_OF_BACK_DAYS, 0);
			minBackDate 		= DateTimeUtility.getDateBeforeNoOfDays(noOfBackDays);
			
			model.put("tallyConfig", tallyConfig);
			model.put("minBackDate",minBackDate);
			model.put("partLocationPermission", partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));
			model.put("configuration", configuration);
			model.put("validateOdometerInWorkOrder", companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_WORKORDER, false));
			model.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_WORKORDER,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_WORKORDER, false));
			model.put("gpsConfiguration", gpsConfiguration);
			model.put("companyId", userDetails.getCompany_id());
			
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Work Order:", e);
		}finally {
			userDetails 		= null;
			configuration		= null;
			gpsConfiguration	= null;
			
		}
		return new ModelAndView("addWorkOrders", model);
	}

	// Note: This /saveWorkOrder Controller is Create New WorkOrder entries To
	// Save Module via Enter WorkOrder Entries Details
	@RequestMapping(value = "/saveWorkOrder", method = RequestMethod.POST)
	public ModelAndView saveWorkOrders(@ModelAttribute("command") WorkOrdersDto workOrders,
			@RequestParam("service_id") final String serviceIds,
			@RequestParam("job_serviceId_Name") final Long job_serviceId,
			@RequestParam("job_typetaskId") final String[] job_typetask,
			@RequestParam("job_subtypetask") final String[] job_subtypetask_ID,
			@RequestParam("Job_ROT") final String[] Job_ROT,
			@RequestParam("Job_ROT_number") final String[] Job_ROT_number,
			@RequestParam("JobTypeRemark") final String[] JobTypeRemark, final HttpServletRequest request,
			RedirectAttributes redir) throws Exception {

		Map<String, Object> 			model 						= new HashMap<String, Object>();
		WorkOrderSequenceCounter 		sequenceCounter 			= null;
		List<JobType> 					jobTypeList 				= null; 
		CustomUserDetails 				userDetails 				= null;
		VehicleDto 						CheckVehicleStatus 			= null;
		PartLocations					partLocations				= null;
		String 							TIDMandatory 				= "";
		long 							Workorders_id				= 0;
		HashMap<Long, ServiceReminderDto> dtosMap1 					= null;
		ServiceReminderDto				serviceReminderDto 			= null;
		Integer							workOrderSubTaskId			= 0;
		long							sId							= 0;
		List<ServiceReminderDto>		serviceList					= null;
		boolean							allowToCreateWOInTripRouteStatus	= false;
		
		try {
			
			String service_id[] = serviceIds.split(",");
			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			jobTypeList 		= jobTypeService.listJobTypeByCompanyId(userDetails.getCompany_id());
			sequenceCounter 	= workOrderSequenceService.findNextWorkOrderNumber(userDetails.getCompany_id());
			HashMap<String, Object> 	configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			
			if (sequenceCounter == null) {
				model.put("sequenceNotFound", true);
				return new ModelAndView("redirect:/viewWorkOrder.in", model);
			}

			// Check Vehicle Status Current IN ACTIVE OR NOT
			CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(workOrders.getVehicle_vid());
			
			
			if(workOrders.getWorkorders_location_ID() == null) {
				partLocations	= new PartLocations();
				partLocations.setPartlocation_name(workOrders.getNew_workorders_location());
				partLocations.setMarkForDelete(false);
				partLocations.setAutoCreated(true);
				partLocations.setCompanyId(userDetails.getCompany_id());
				partLocations.setCreatedById(userDetails.getId());
				partLocations.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				partLocationsService.addPartLocations(partLocations);
				if(partLocations != null && partLocations.getPartlocation_id() > 0) {
					workOrders.setWorkorders_location_ID(partLocations.getPartlocation_id());
				}
				
				
			}
			
			if(CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE && (boolean) configuration.get("allowToCreateWOInTripRouteStatus")) {
				allowToCreateWOInTripRouteStatus	= true;
			}
			
			if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE || allowToCreateWOInTripRouteStatus) {
				if (workOrders.getStart_date() != "" && workOrders.getStart_date() != null
						&& workOrders.getWorkorders_location_ID() > 0) {
					// get the WorkOrders bean to save WorkOrders
					WorkOrders WorkOrd = prepareWorkOrders(workOrders);
					
					WorkOrd.setWorkorders_statusId(WorkOrdersType.WORKORDERS_STATUS_OPEN);
					WorkOrd.setTotalsubworktask_cost(WORK_ORDER_DEFALAT_AMOUNT);
					WorkOrd.setTotalworktax_cost(WORK_ORDER_DEFALAT_AMOUNT);
					WorkOrd.setTotalworkorder_cost(WORK_ORDER_DEFALAT_AMOUNT);
					WorkOrd.setCompanyId(userDetails.getCompany_id());
					WorkOrd.setWorkorders_Number(sequenceCounter.getNextVal());
					

					workOrdersService.addWorkOrders(WorkOrd);
					// Note : When Vehicle Created Work Order That time Vehicle
					// Status update to 'WORKSHOP'
					vehicleService.Update_Vehicle_Status_TripSheetID(WorkOrd.getWorkorders_id(), WorkOrd.getVehicle_vid(), 
							userDetails.getCompany_id(), VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP);

					model.put("saveWorkOrder", true);

					if (job_subtypetask_ID != null || job_typetask != null ) {

						for (int i = 0; i < job_subtypetask_ID.length; i++) {
							if(job_typetask[i] != null && !job_typetask[i].trim().equalsIgnoreCase("")) {
								
								WorkOrdersTasks WRTask = new WorkOrdersTasks();
								WRTask.setJob_typetaskId(Integer.parseInt("" + job_typetask[i]));
								WRTask.setJob_subtypetask_id(Integer.parseInt(job_subtypetask_ID[i]));
								if(JobTypeRemark != null && JobTypeRemark.length > 0) {
									WRTask.setJobTypeRemark(JobTypeRemark[i]);								
								}
								
								if (Integer.parseInt(job_subtypetask_ID[i]) == 0) {
									
									// add New ROT Code in WorkOrder to JOB
									JobSubType DocType = new JobSubType();
									
									for (JobType jobType : jobTypeList) {
										if (jobType.getJob_id() == Integer.parseInt("" + job_typetask[i])) {
											DocType.setJob_TypeId(jobType.getJob_id());
											DocType.setJob_Type(jobType.getJob_Type());
											break;
										}
									}
									DocType.setJob_ROT("" + Job_ROT[i]);
									DocType.setJob_ROT_number("" + Job_ROT_number[i]);
									DocType.setJob_ROT_hour("" + 0);
									DocType.setJob_ROT_amount("" + 0);
									DocType.setCompanyId(userDetails.getCompany_id());
									DocType.setCreatedBy(userDetails.getEmail());
									DocType.setCreatedById(userDetails.getId());
									DocType.setCreatedOn(new Timestamp(System.currentTimeMillis()));
									DocType.setJob_ROT_Service_Reminder(false);
									
									List<JobSubType> validate = jobSubTypeService.ValidateJobRotnumber("" + Job_ROT[i],
											userDetails.getCompany_id());
									if (validate != null && !validate.isEmpty()) {
										for (JobSubType jobSubTypeVD : validate) {
											
											WRTask.setJob_subtypetask_id(jobSubTypeVD.getJob_Subid());
											// WRTask.setJob_subtypetask(
											// jobSubTypeVD.getJob_ROT_number() + " - " + jobSubTypeVD.getJob_ROT());
											break;
										}
									} else {
										jobSubTypeService.addJobSubType(DocType);
										
										WRTask.setJob_subtypetask_id(DocType.getJob_Subid());
										// WRTask.setJob_subtypetask(
										// DocType.getJob_ROT_number() + " - " + DocType.getJob_ROT());
										
									}
								}
								
								WRTask.setWorkorders(WorkOrd);
								WRTask.setVehicle_vid(WorkOrd.getVehicle_vid());
								WRTask.setTotalpart_cost(WORK_ORDER_DEFALAT_AMOUNT);
								WRTask.setTotallaber_cost(WORK_ORDER_DEFALAT_AMOUNT);
								WRTask.setTotaltask_cost(WORK_ORDER_DEFALAT_AMOUNT);
								WRTask.setCompanyId(userDetails.getCompany_id());
								if(service_id != null && job_serviceId != null) {
									if (sId != job_serviceId) {
										WRTask.setService_id(job_serviceId);
										sId = WRTask.getService_id();
									} else {
										WRTask.setService_id(null);
									}
									
								}else {
									WRTask.setService_id(null);
								}
								
								List<WorkOrders> LastOcc = workOrdersService.getLast_WorkOrdersTask_To_WorkOrders_details(WRTask.getJob_typetaskId(), WRTask.getJob_subtypetask_id(), WorkOrd.getVehicle_vid(), userDetails.getCompany_id());
								
								if (LastOcc != null && !LastOcc.isEmpty()) {
									for (WorkOrders lastOccWO : LastOcc) {
										
										WRTask.setLast_occurred_woId(lastOccWO.getWorkorders_id());
										WRTask.setLast_occurred_date(lastOccWO.getStart_date());
										WRTask.setLast_occurred_odameter(lastOccWO.getVehicle_Odometer());
										break;
									}
								} else {
									WRTask.setLast_occurred_woId(WORK_ORDER_DEFALAT_VALUE);
									WRTask.setLast_occurred_date(null);
									WRTask.setLast_occurred_odameter(WORK_ORDER_DEFALAT_ODAMETER);
									
								}
								workOrdersService.addWorkOrdersTask(WRTask);
							}

						}
					}
					workOrderSubTaskId = Integer.parseInt(job_subtypetask_ID[0]);
					// Add Service Id to workorderTask
					
					if (service_id != null && !serviceIds.isEmpty()) {
						dtosMap1 = workOrdersService.getJobtypeAndSubtypeFromServiceReminderId(serviceIds, userDetails.getCompany_id());
						
						for (int j = 0; j < service_id.length; j++) {
							
							serviceReminderDto	= dtosMap1.get(Long.parseLong(service_id[j]));
							
							if(!workOrderSubTaskId.equals(serviceReminderDto.getServiceSubTypeId())) {
							
								WorkOrdersTasks WRTaskServReminder = new WorkOrdersTasks();
								
								if(serviceReminderDto.getServiceTypeId() != null) {
								WRTaskServReminder.setJob_typetaskId(serviceReminderDto.getServiceTypeId());
								}
								if(serviceReminderDto.getServiceSubTypeId() != null) {
								WRTaskServReminder.setJob_subtypetask_id(serviceReminderDto.getServiceSubTypeId());
								}
								if(JobTypeRemark != null && JobTypeRemark.length > 0) {
									WRTaskServReminder.setJobTypeRemark(JobTypeRemark[j]);								
								}
								WRTaskServReminder.setWorkorders(WorkOrd);
								WRTaskServReminder.setVehicle_vid(WorkOrd.getVehicle_vid());
								WRTaskServReminder.setTotalpart_cost(WORK_ORDER_DEFALAT_AMOUNT);
								WRTaskServReminder.setTotallaber_cost(WORK_ORDER_DEFALAT_AMOUNT);
								WRTaskServReminder.setTotaltask_cost(WORK_ORDER_DEFALAT_AMOUNT);
								WRTaskServReminder.setCompanyId(userDetails.getCompany_id());
								WRTaskServReminder.setLast_occurred_woId(WORK_ORDER_DEFALAT_VALUE);
								WRTaskServReminder.setLast_occurred_date(null);
								WRTaskServReminder.setLast_occurred_odameter(WORK_ORDER_DEFALAT_ODAMETER);
								WRTaskServReminder.setService_id(Long.parseLong(service_id[j]+""));
								
								workOrdersService.addWorkOrdersTask(WRTaskServReminder);
							
							}
							
						}
					}	
					
					// update the Current Odometer vehicle Databases tables
					try {
						Integer currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(WorkOrd.getVehicle_vid());

						if (currentODDMETER < WorkOrd.getVehicle_Odometer()) {
							
							vehicleService.updateCurrentOdoMeter(WorkOrd.getVehicle_vid(), WorkOrd.getVehicle_Odometer(), userDetails.getCompany_id());
							// update current Odometer update Service Reminder
							serviceReminderService.updateCurrentOdometerToServiceReminder(WorkOrd.getVehicle_vid(),
									WorkOrd.getVehicle_Odometer(), userDetails.getCompany_id());
							
							serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(WorkOrd.getVehicle_vid(), userDetails.getCompany_id(), userDetails.getId());
							if(serviceList != null) {
								for(ServiceReminderDto list : serviceList) {
									
									if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
										
										ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
										//emailAlertQueueService.sendEmailServiceOdometer(list);
										//smsAlertQueueService.sendSmsServiceOdometer(list);
									}
								}
							}

							VehicleOdometerHistory vehicleUpdateHistory = vehicleOdometerHistoryBL
									.prepareOdometerGetHistoryToWorkOrder(WorkOrd);
							vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
							vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
						}
					} catch (Exception e) {
						LOGGER.error("Work Order:", e);
						e.printStackTrace();

					}

					Workorders_id = WorkOrd.getWorkorders_id();
				} else {
					model.put("emptyWO", true);
					return new ModelAndView("redirect:/addWorkOrder.in", model);
				}

			} // checking Vehicle Status
			else {
				String Link = "";
				if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
					Link += "  <a href=\"getTripsheetDetails.in?tripSheetID=" + CheckVehicleStatus.getTripSheetID()
							+ "\" target=\"_blank\">TS-"
							+ driverService.getCurrentTripSheetNumber(CheckVehicleStatus.getTripSheetID(), userDetails.getCompany_id())
							+ "  <i class=\"fa fa-external-link\"></i></a>";

				} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
					WorkOrders orders = workOrdersService.getWorkOrders(CheckVehicleStatus.getTripSheetID());
					Link += " <a href=\"showWorkOrder?woId=" + CheckVehicleStatus.getTripSheetID()
							+ "\" target=\"_blank\">WO-" + orders.getWorkorders_Number()
							+ "  <i class=\"fa fa-external-link\"></i></a> ";

				}

				TIDMandatory += " <span> This " + CheckVehicleStatus.getVehicle_registration() + " Vehicle in "
						+ VehicleStatusConstant.getVehicleStatus(CheckVehicleStatus.getvStatusId())
						+ " Status you can't create WorkOrder " + " " + Link + "" + " </span> ,";
				redir.addFlashAttribute("VMandatory", TIDMandatory);
				model.put("closeStatus", true);
				return new ModelAndView("redirect:/addWorkOrder.in", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/viewWorkOrder.in", model);
		}

		return new ModelAndView("redirect:/showWorkOrder?woId=" + Workorders_id + "", model);
	}

	// Note: This /saveServiceWorkOrder Controller is Create New WorkOrder
	// entries To Save Module via Enter ServiceReminder Entries Details
	@RequestMapping(value = "/saveServiceWorkOrder", method = RequestMethod.POST)
	public ModelAndView saveServiceWorkOrder(@ModelAttribute("command") WorkOrdersDto workOrders,
			ServiceReminder serviceReminder, WorkOrdersTasks workOrdersTasks, final HttpServletRequest request,
			RedirectAttributes redir) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		WorkOrderSequenceCounter 		sequenceCounter 			= null;
		PartLocations					partLocations				= null;
		List<ServiceReminderDto>		serviceList					= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			sequenceCounter = workOrderSequenceService.findNextWorkOrderNumber(userDetails.getCompany_id());
			if (sequenceCounter == null) {
				model.put("sequenceNotFound", true);
				return new ModelAndView("redirect:/ShowService.in?Service_id=" + serviceReminder.getService_id() + "",
						model);
			}
			String TIDMandatory = "";

			// Check Vehicle Status Current IN ACTIVE OR NOT
			VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(workOrders.getVehicle_vid());
			if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE) {


				if(workOrders.getWorkorders_location_ID() == null || workOrders.getWorkorders_location_ID() <= 0) {
					partLocations	= new PartLocations();
					partLocations.setPartlocation_name(workOrders.getNew_workorders_location());
					partLocations.setMarkForDelete(false);
					partLocations.setAutoCreated(true);
					partLocations.setCompanyId(userDetails.getCompany_id());
					partLocations.setCreatedById(userDetails.getId());
					partLocations.setCreatedOn(new Timestamp(System.currentTimeMillis()));
					partLocationsService.addPartLocations(partLocations);
					if(partLocations != null && partLocations.getPartlocation_id() > 0) {
						workOrders.setWorkorders_location_ID(partLocations.getPartlocation_id());
					}
					
					
				}
				
				
				if (workOrders.getStart_date() != "" && workOrders.getStart_date() != null
						&& workOrders.getWorkorders_location_ID() != null
						&& workOrders.getWorkorders_location_ID() > 0) {
					// get the WorkOrders Dto to save WorkOrders
					WorkOrders WorkOrd = prepareWorkOrders(workOrders);
					WorkOrd.setWorkorders_statusId(WorkOrdersType.WORKORDERS_STATUS_OPEN);
					WorkOrd.setTotalsubworktask_cost(WORK_ORDER_DEFALAT_AMOUNT);
					WorkOrd.setTotalworktax_cost(WORK_ORDER_DEFALAT_AMOUNT);
					WorkOrd.setTotalworkorder_cost(WORK_ORDER_DEFALAT_AMOUNT);
					WorkOrd.setCompanyId(userDetails.getCompany_id());
					WorkOrd.setWorkorders_Number(sequenceCounter.getNextVal());
					workOrdersService.addWorkOrders(WorkOrd);
					// Note : When Vehicle Created Work Order That time Vehicle
					// Status update to 'WORKSHOP'
					vehicleService.Update_Vehicle_Status_TripSheetID(WorkOrd.getWorkorders_id(), WorkOrd.getVehicle_vid(), 
							userDetails.getCompany_id(), VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP);

					model.put("saveWorkOrder", true);

					WorkOrdersTasks WRTask = new WorkOrdersTasks();
					WRTask.setVehicle_vid(WorkOrd.getVehicle_vid());
					WRTask.setWorkorders(WorkOrd);
					WRTask.setTotalpart_cost(WORK_ORDER_DEFALAT_AMOUNT);
					WRTask.setTotallaber_cost(WORK_ORDER_DEFALAT_AMOUNT);
					WRTask.setTotaltask_cost(WORK_ORDER_DEFALAT_AMOUNT);
					WRTask.setCompanyId(userDetails.getCompany_id());
					WRTask.setJob_typetaskId(workOrdersTasks.getJob_typetaskId());
					WRTask.setJob_subtypetask_id(workOrdersTasks.getJob_subtypetask_id());
					WRTask.setJobTypeRemark(workOrdersTasks.getJobTypeRemark());

					List<WorkOrders> LastOcc = workOrdersService.getLast_WorkOrdersTask_To_WorkOrders_details(WRTask.getJob_typetaskId(), WRTask.getJob_subtypetask_id(), WorkOrd.getVehicle_vid(),	userDetails.getCompany_id());

					if (LastOcc != null && !LastOcc.isEmpty()) {
						for (WorkOrders lastOccWO : LastOcc) {

							WRTask.setLast_occurred_woId(lastOccWO.getWorkorders_id());
							WRTask.setLast_occurred_date(lastOccWO.getStart_date());
							WRTask.setLast_occurred_odameter(lastOccWO.getVehicle_Odometer());
							break;
						}
					} else {
						WRTask.setLast_occurred_woId(WORK_ORDER_DEFALAT_VALUE);
						WRTask.setLast_occurred_date(null);
						WRTask.setLast_occurred_odameter(0);
					}
					workOrdersService.addWorkOrdersTask(WRTask);

					// update Service Reminder Stutes in workOrder Created
					serviceReminderService.updateWORKOrderToServiceReminder(serviceReminder.getService_id(),
							ServiceReminderDto.SERVICE_REMINDER_STATUS_INACTIVE, userDetails.getCompany_id(),
							WorkOrd.getWorkorders_id());

					// update the Current Odometer vehicle Databases tables
					try {
						Integer currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(WorkOrd.getVehicle_vid());

						if (currentODDMETER < WorkOrd.getVehicle_Odometer()) {
							
							vehicleService.updateCurrentOdoMeter(WorkOrd.getVehicle_vid(), WorkOrd.getVehicle_Odometer(), userDetails.getCompany_id());
							// update current Odometer update Service Reminder
							serviceReminderService.updateCurrentOdometerToServiceReminder(WorkOrd.getVehicle_vid(),
									WorkOrd.getVehicle_Odometer(), userDetails.getCompany_id());
							
							serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(WorkOrd.getVehicle_vid(), userDetails.getCompany_id(), userDetails.getId());
							if(serviceList != null) {
								for(ServiceReminderDto list : serviceList) {
									
									if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
										
										ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
										//emailAlertQueueService.sendEmailServiceOdometer(list);
										//smsAlertQueueService.sendSmsServiceOdometer(list);
									}
								}
							}

							VehicleOdometerHistory vehicleUpdateHistory = vehicleOdometerHistoryBL
									.prepareOdometerGetHistoryToWorkOrder(WorkOrd);
							vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
							vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
						}
					} catch (NullPointerException e) {
						return new ModelAndView("redirect:/NotFound.in");
					} catch (Exception e) {
						LOGGER.error("Work Order:", e);
					}
					redir.addFlashAttribute("successWOID", WorkOrd.getWorkorders_Number());
				} else {
					model.put("emptyWO", true);
					return new ModelAndView(
							"redirect:/ShowService.in?Service_id=" + serviceReminder.getService_id() + "", model);
				}

			} // checking Vehicle Status
			else {
				String Link = "";
				if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
					Link += "  <a href=\"getTripsheetDetails.in?tripSheetID=" + CheckVehicleStatus.getTripSheetID()
							+ "\" target=\"_blank\">TS-"
							+ driverService.getCurrentTripSheetNumber(CheckVehicleStatus.getTripSheetID(), userDetails.getCompany_id())
							+ "  <i class=\"fa fa-external-link\"></i></a>";

				} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
					WorkOrders orders = workOrdersService.getWorkOrders(CheckVehicleStatus.getTripSheetID());
					Link += " <a href=\"showWorkOrder?woId=" + CheckVehicleStatus.getTripSheetID()
							+ "\" target=\"_blank\">WO-" + orders.getWorkorders_Number()
							+ "  <i class=\"fa fa-external-link\"></i></a> ";

				}

				TIDMandatory += " <span> This " + CheckVehicleStatus.getVehicle_registration() + " Vehicle in "
						+ CheckVehicleStatus.getVehicle_Status() + " Status you can't create WorkOrder " + " " + Link
						+ "" + " </span> ,";
				redir.addFlashAttribute("VMandatory", TIDMandatory);
				model.put("closeStatus", true);
				return new ModelAndView("redirect:/ShowService.in?Service_id=" + serviceReminder.getService_id() + "",
						model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("emptyWO", true);
			return new ModelAndView("redirect:/ShowService.in?Service_id=" + serviceReminder.getService_id() + "",
					model);
		}

		return new ModelAndView("redirect:/ShowService.in?Service_id=" + serviceReminder.getService_id() + "", model);
	}

	// Note: This /saveServiceWorkOrder Controller is Create New WorkOrder
	// entries To Save Module via Enter Issues Entries Details
	@RequestMapping(value = "/saveissuesViaWorkOrder", method = RequestMethod.POST)
	public ModelAndView saveissuesViaWorkOrder(@ModelAttribute("command") WorkOrdersDto workOrders,
			@RequestParam("ISSUES_ID") final Long issues_ID, @RequestParam("ISSUES_STATUS_ID") final short Status,
			@RequestParam("job_typetaskId") final String[] job_typetask,
			@RequestParam("job_subtypetask_id") final String[] job_subtypetask,
			@RequestParam("Job_ROT") final String[] Job_ROT,
			@RequestParam("Job_ROT_number") final String[] Job_ROT_number,
			@RequestParam("JobTypeRemark") final String[] JobTypeRemark, final HttpServletRequest request,
			RedirectAttributes redir) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		WorkOrderSequenceCounter 		sequenceCounter 		= null;
		PartLocations			 		partLocations			= null;
		List<ServiceReminderDto>		serviceList				= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<JobType> jobTypeList = jobTypeService.listJobTypeByCompanyId(userDetails.getCompany_id());
			sequenceCounter = workOrderSequenceService.findNextWorkOrderNumber(userDetails.getCompany_id());
			if (sequenceCounter == null) {
				model.put("sequenceNotFound", true);
				return new ModelAndView("redirect:/viewWorkOrder.in", model);
			}
			String TIDMandatory = "";

			// Check Vehicle Status Current IN ACTIVE OR NOT
			VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(workOrders.getVehicle_vid());
			if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE) {
				
				if(workOrders.getWorkorders_location_ID() == null) {
					partLocations	= new PartLocations();
					partLocations.setPartlocation_name(workOrders.getNew_workorders_location());
					partLocations.setMarkForDelete(false);
					partLocations.setAutoCreated(true);
					partLocations.setCompanyId(userDetails.getCompany_id());
					partLocations.setCreatedById(userDetails.getId());
					partLocations.setCreatedOn(new Timestamp(System.currentTimeMillis()));
					partLocationsService.addPartLocations(partLocations);
					if(partLocations != null && partLocations.getPartlocation_id() > 0) {
						workOrders.setWorkorders_location_ID(partLocations.getPartlocation_id());
					}
					
					
				}

				if (workOrders.getStart_date() != "" && workOrders.getStart_date() != null
						&& workOrders.getWorkorders_location_ID() != null
						&& workOrders.getWorkorders_location_ID() > 0) {
					// get the WorkOrders bean to save WorkOrders
					WorkOrders WorkOrd = prepareWorkOrders(workOrders);
					WorkOrd.setISSUES_ID(issues_ID);
					WorkOrd.setWorkorders_statusId(WorkOrdersType.WORKORDERS_STATUS_OPEN);
					WorkOrd.setTotalsubworktask_cost(WORK_ORDER_DEFALAT_AMOUNT);
					WorkOrd.setTotalworktax_cost(WORK_ORDER_DEFALAT_AMOUNT);
					WorkOrd.setTotalworkorder_cost(WORK_ORDER_DEFALAT_AMOUNT);
					WorkOrd.setCompanyId(userDetails.getCompany_id());
					WorkOrd.setWorkorders_Number(sequenceCounter.getNextVal());
					workOrdersService.addWorkOrders(WorkOrd);
					// Note : When Vehicle Created Work Order That time Vehicle
					// Status update to 'WORKSHOP'
					vehicleService.Update_Vehicle_Status_TripSheetID(WorkOrd.getWorkorders_id(), WorkOrd.getVehicle_vid(), 
							userDetails.getCompany_id(), VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP);

					model.put("saveWorkOrder", true);

					if (job_subtypetask != null) {
						for (int i = 0; i < job_subtypetask.length; i++) {

							WorkOrdersTasks WRTask = new WorkOrdersTasks();

							WRTask.setJob_typetaskId(Integer.parseInt("" + job_typetask[i]));
							JobSubType JobSubType = jobSubTypeService
									.getJobSubType(Integer.parseInt(job_subtypetask[i]));
							if (JobSubType != null) {
								WRTask.setJob_subtypetask_id(Integer.parseInt(job_subtypetask[i]));
								// WRTask.setJob_subtypetask(
								// JobSubType.getJob_ROT_number() + " - " + JobSubType.getJob_ROT());
							} else {
								// add New ROT Code in WorkOrder to JOB
								JobSubType DocType = new JobSubType();

								DocType.setJob_Type("" + job_typetask[i]);
								for (JobType jobType : jobTypeList) {
									if (jobType.getJob_Type().equalsIgnoreCase("" + job_typetask[i])) {
										DocType.setJob_TypeId(jobType.getJob_id());
										DocType.setJob_Type(jobType.getJob_Type());
										break;
									}
								}
								DocType.setJob_ROT("" + Job_ROT[i]);
								DocType.setJob_ROT_number("" + Job_ROT_number[i]);
								DocType.setJob_ROT_hour("" + 0);
								DocType.setJob_ROT_amount("" + 0);
								DocType.setCompanyId(userDetails.getCompany_id());
								DocType.setCreatedBy(userDetails.getEmail());
								DocType.setCreatedOn(new Timestamp(System.currentTimeMillis()));
								DocType.setCreatedById(userDetails.getId());

								List<JobSubType> validate = jobSubTypeService.ValidateJobRotnumber("" + Job_ROT[i],
										userDetails.getCompany_id());
								if (validate != null && !validate.isEmpty()) {
									for (JobSubType jobSubTypeVD : validate) {

										WRTask.setJob_subtypetask_id(jobSubTypeVD.getJob_Subid());
										// WRTask.setJob_subtypetask(
										// jobSubTypeVD.getJob_ROT_number() + " - " + jobSubTypeVD.getJob_ROT());
										break;
									}
								} else {
									jobSubTypeService.addJobSubType(DocType);

									WRTask.setJob_subtypetask_id(DocType.getJob_Subid());
									// WRTask.setJob_subtypetask(
									// DocType.getJob_ROT_number() + " - " + DocType.getJob_ROT());

								}
							}
							
							if(JobTypeRemark != null && JobTypeRemark.length > 0) {
								WRTask.setJobTypeRemark(JobTypeRemark[i]);								
							}
							
							WRTask.setWorkorders(WorkOrd);
							WRTask.setVehicle_vid(WorkOrd.getVehicle_vid());
							WRTask.setTotalpart_cost(WORK_ORDER_DEFALAT_AMOUNT);
							WRTask.setTotallaber_cost(WORK_ORDER_DEFALAT_AMOUNT);
							WRTask.setTotaltask_cost(WORK_ORDER_DEFALAT_AMOUNT);
							WRTask.setCompanyId(userDetails.getCompany_id());

							List<WorkOrders> LastOcc = workOrdersService.getLast_WorkOrdersTask_To_WorkOrders_details(WRTask.getJob_typetaskId(), WRTask.getJob_subtypetask_id(),WorkOrd.getVehicle_vid(), userDetails.getCompany_id());

							if (LastOcc != null && !LastOcc.isEmpty()) {
								for (WorkOrders lastOccWO : LastOcc) {

									WRTask.setLast_occurred_woId(lastOccWO.getWorkorders_id());
									WRTask.setLast_occurred_date(lastOccWO.getStart_date());
									WRTask.setLast_occurred_odameter(lastOccWO.getVehicle_Odometer());
									break;
								}
							} else {
								WRTask.setLast_occurred_woId(WORK_ORDER_DEFALAT_VALUE);
								WRTask.setLast_occurred_date(null);
								WRTask.setLast_occurred_odameter(0);
							}

							workOrdersService.addWorkOrdersTask(WRTask);

						}

						if (issues_ID != null && issues_ID != 0) {

							try {

								Issues Save_Issues = new Issues();
								Save_Issues.setISSUES_ID(issues_ID);
								IssuesTasks WRTask = new IssuesTasks();
								/** Task new from to create */
								WRTask.setISSUES_TASK_STATUS_ID(Status);
								/** Task new from to create change status */
								WRTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_WOCREATED);
								/** Set Created by email Id */
								WRTask.setISSUES_CREATEBY_ID(userDetails.getId());
								//WRTask.setISSUES_CREATEBY_NAME(userDetails.getFirstName());
								java.util.Date currentDateUpdate = new java.util.Date();
								Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
								/** Set Created Current Date */
								WRTask.setISSUES_CREATED_DATE(toDate);
								WRTask.setISSUES(Save_Issues);
								WRTask.setCOMPANY_ID(userDetails.getCompany_id());
								WRTask.setISSUES_REASON("CREATED via Issues to WorkOrder OPEN STATUS");
								/** save to IssuesTask values */
								issuesService.registerNew_IssuesTasks(WRTask);

								issuesService.update_Create_WorkOrder_Issues_Status(
										IssuesTypeConstant.ISSUES_STATUS_WOCREATED, userDetails.getId(), toDate,
										WorkOrd.getWorkorders_id(), issues_ID,userDetails.getCompany_id());

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
					// update the Current Odometer vehicle Databases tables
					try {
						Integer currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(WorkOrd.getVehicle_vid());
						if(WorkOrd.getVehicle_Odometer() != null) {
							
							if (currentODDMETER < WorkOrd.getVehicle_Odometer()) {
								
								vehicleService.updateCurrentOdoMeter(WorkOrd.getVehicle_vid(), WorkOrd.getVehicle_Odometer(), userDetails.getCompany_id());
								// update current Odometer update Service Reminder
								serviceReminderService.updateCurrentOdometerToServiceReminder(WorkOrd.getVehicle_vid(),
										WorkOrd.getVehicle_Odometer(), userDetails.getCompany_id());
								
								serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(WorkOrd.getVehicle_vid(), userDetails.getCompany_id(), userDetails.getId());
								if(serviceList != null) {
									for(ServiceReminderDto list : serviceList) {
										
										if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
											
											ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
											//emailAlertQueueService.sendEmailServiceOdometer(list);
											//smsAlertQueueService.sendSmsServiceOdometer(list);
										}
									}
								}
								
								VehicleOdometerHistory vehicleUpdateHistory = vehicleOdometerHistoryBL
										.prepareOdometerGetHistoryToWorkOrder(WorkOrd);
								vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
								vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
							}
						}			
					} catch (Exception e) {
						LOGGER.error("Work Order:", e);
						e.printStackTrace();

					}

				} else {
					model.put("emptyWO", true);
					return new ModelAndView(
							"redirect:/showIssues.in?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID) + "",
							model);
				}

			} // checking Vehicle Status
			else {
				String Link = "";
				if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
					Link += "  <a href=\"getTripsheetDetails.in?tripSheetID=" + CheckVehicleStatus.getTripSheetID()
							+ "\" target=\"_blank\">TS-"
							+ driverService.getCurrentTripSheetNumber(CheckVehicleStatus.getTripSheetID(), userDetails.getCompany_id())
							+ "  <i class=\"fa fa-external-link\"></i></a>";

				} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
					WorkOrders orders = workOrdersService.getWorkOrders(CheckVehicleStatus.getTripSheetID());
					Link += " <a href=\"showWorkOrder?woId=" + CheckVehicleStatus.getTripSheetID()
							+ "\" target=\"_blank\">WO-" + orders.getWorkorders_Number()
							+ "  <i class=\"fa fa-external-link\"></i></a> ";

				}

				TIDMandatory += " <span> This " + CheckVehicleStatus.getVehicle_registration() + " Vehicle in "
						+ CheckVehicleStatus.getVehicle_Status() + " Status you can't create WorkOrder " + " " + Link
						+ "" + " </span> ,";
				redir.addFlashAttribute("VMandatory", TIDMandatory);
				model.put("closeStatus", true);
				return new ModelAndView(
						"redirect:/showIssues.in?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID) + "", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("emptyWO", true);
			return new ModelAndView(
					"redirect:/showIssues.in?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID) + "", model);
		}

		return new ModelAndView("redirect:/showIssues.in?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID) + "",
				model);
	}

	// Note: This /editWorkOrders Controller is Edit Old WorkOrder Entries
	// Details
	@RequestMapping(value = "/editWorkOrders", method = RequestMethod.GET)
	public ModelAndView editWorkOrders(@RequestParam("ID") final Long Workorders_id, final HttpServletRequest request)
			throws Exception {
		HashMap<String, Object>     configuration		= null;
		HashMap<String, Object>     gpsConfiguration	= null;
		Map<String, Object> 		model 				= null;
		CustomUserDetails		 	userDetails 		= null;
		WorkOrdersDto 				workOrdersDto 		= null;
		WorkOrdersDto 				workOrdersDtoBL		= null;
		boolean						tallyConfig			= false;
		int							noOfBackDays		= 0;
		String 						minBackDate			= null; 
		
		try {
			model 				= new HashMap<String, Object>();
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			tallyConfig			= (boolean) configuration.getOrDefault(WorkOrdersConfigurationConstants.TALLY_COMPANY_MASTER_IN_WO, false);
			noOfBackDays		= (Integer) configuration.getOrDefault(WorkOrdersConfigurationConstants.NO_OF_BACK_DAYS, 0);
			minBackDate 		= DateTimeUtility.getDateBeforeNoOfDays(noOfBackDays);
			
			workOrdersDto 		= workOrdersService.getWorkOrdersDetails(Workorders_id, userDetails.getCompany_id());
			workOrdersDtoBL 	= workOrdersBL.Show_WorkOrders(workOrdersDto);
			
			model.put("minBackDate",minBackDate);
			model.put("WorkOrder", workOrdersDtoBL);
			model.put("tallyConfig", tallyConfig);
			model.put("configuration", configuration);
			model.put("gpsConfiguration", gpsConfiguration);
			model.put("companyId", userDetails.getCompany_id());
			model.put("vehicle", vehicleService.Get_Vehicle_Current_Status_TripSheetID(workOrdersDtoBL.getVehicle_vid()));
			model.put("validateOdometerInWorkOrder", companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_WORKORDER, false));
			
			
			return new ModelAndView("editWorkOrders", model);
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
	}

	// Note: This /updateWorkOrder Controller is Update Old WorkOrder entries To
	// update Module via Enter WorkOrder Entries Details
	@RequestMapping(value = "/updateWorkOrder", method = RequestMethod.POST)
	public ModelAndView updateWorkOrders(@ModelAttribute("command") WorkOrdersDto workOrders,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		Long Workorders_id;
		CustomUserDetails 				userDetails 	= null;
		List<ServiceReminderDto>		serviceList		= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (workOrders.getStart_date() != "" && workOrders.getStart_date() != null
					&& workOrders.getWorkorders_location_ID() != null && workOrders.getWorkorders_location_ID() > 0) {

				WorkOrdersDto Oldwork = workOrdersBL.Show_WorkOrders(workOrdersService.getWorkOrdersDetails(workOrders.getWorkorders_id(), userDetails.getCompany_id()));

				// get the WorkOrders bean to save WorkOrders
				WorkOrders WorkOrd = prepareWorkOrders(workOrders);
				
				WorkOrd.setWorkorders_statusId(Oldwork.getWorkorders_statusId());
				WorkOrd.setTotalsubworktask_cost(Oldwork.getTotalsubworktask_cost());
				WorkOrd.setTotalworktax_cost(Oldwork.getTotalworktax_cost());
				WorkOrd.setTotalworkorder_cost(Oldwork.getTotalworkorder_cost());
				WorkOrd.setWorkorders_Number(Oldwork.getWorkorders_Number());

				WorkOrd.setWorkorders_document(Oldwork.isWorkorders_document());

				WorkOrd.setISSUES_ID(Oldwork.getISSUES_ID());
				WorkOrd.setCompanyId(userDetails.getCompany_id());
				// Note : Update WorkOrders Details
				workOrdersService.addWorkOrders(WorkOrd);

				model.put("saveWorkOrder", true);

				// update the Current Odometer vehicle Databases tables
				try {
					Integer currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(WorkOrd.getVehicle_vid());

					if (currentODDMETER < WorkOrd.getVehicle_Odometer()) {
						
						vehicleService.updateCurrentOdoMeter(WorkOrd.getVehicle_vid(), WorkOrd.getVehicle_Odometer(), userDetails.getCompany_id());
						// update current Odometer update Service Reminder
						serviceReminderService.updateCurrentOdometerToServiceReminder(WorkOrd.getVehicle_vid(),
								WorkOrd.getVehicle_Odometer(), userDetails.getCompany_id());
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(WorkOrd.getVehicle_vid(), userDetails.getCompany_id(), userDetails.getId());
						if(serviceList != null) {
							for(ServiceReminderDto list : serviceList) {
								
								if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
									
									ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
									//emailAlertQueueService.sendEmailServiceOdometer(list);
									//smsAlertQueueService.sendSmsServiceOdometer(list);
								}
							}
						}

						VehicleOdometerHistory vehicleUpdateHistory = vehicleOdometerHistoryBL
								.prepareOdometerGetHistoryToWorkOrder(WorkOrd);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
					}
				} catch (Exception e) {
					LOGGER.error("Work Order:", e);
					e.printStackTrace();

				}

				Workorders_id = WorkOrd.getWorkorders_id();
			} else {
				model.put("emptyWO", true);
				return new ModelAndView("redirect:/addWorkOrder.in", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/viewWorkOrder.in", model);
		}

		return new ModelAndView("redirect:/showWorkOrder?woId=" + Workorders_id + "", model);
	}

	// Note: This /WorkOrdersParts Controller is Show All WorkOrder Entries
	// Details
	@RequestMapping(value = "/WorkOrdersParts", method = RequestMethod.GET)
	public ModelAndView addWorkOrdersParts(@RequestParam("ID") final Long Workorders_id,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> 		model 						= null;
		
		try {
			model 			= new HashMap<String, Object>();
			
			return new ModelAndView("redirect:/showWorkOrder?woId=" + Workorders_id + "", model);

		} catch (Exception e) {
			throw e;
		} finally {
			model 				= null;
		}
	}

	// Note: This /PrintWorkOrders Controller is Print All WorkOrder Entries
	// Details
	@RequestMapping(value = "/PrintWorkOrders", method = RequestMethod.GET)
	public ModelAndView showUserProfilePrint(@RequestParam("id") final Long id, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>     configuration			= null;
		CustomUserDetails 	userDetails = null;
		boolean 							showSubLocation	 								= false;

		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			showSubLocation	 	= (boolean)configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_SUB_LOCATION, false);
			model.put("configuration", configuration);
			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));
			model.put("showSubLocation", showSubLocation);

			WorkOrdersDto work = workOrdersBL.Show_WorkOrders(workOrdersService.getWorkOrdersDetails(id, userDetails.getCompany_id()));
			model.put("WorkOrder", work);

			model.put("WorkOrderTask", workOrdersService.getWorkOrdersTasks(id, userDetails.getCompany_id()));

			model.put("WorkOrderTaskPart", workOrdersService.getWorkOrdersTasksToParts(id, userDetails.getCompany_id()));

			model.put("WorkOrderTaskLabor", workOrdersService.getWorkOrdersTasksToLabor(id, userDetails.getCompany_id()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("showWorkOrdersPrint", model);
	}

	// Note: This /saveWorkOrderTaskPart Controller is Save WorkOrder New Task
	// Parts Entries Details also Update Total Part Cost in One Task wise and
	// Update Total Amount Cost in WorkOrder Details
	@RequestMapping(value = "/saveWorkOrderTaskPart", method = RequestMethod.POST)
	public ModelAndView saveWorkOrderTaskPart(@ModelAttribute("command") WorkOrdersTasksToParts workOrdersTasksToParts,
			final HttpServletRequest request) throws Exception {

		CustomUserDetails 					userDetails 						= null;
		Map<String, Object> 				model 								= new HashMap<String, Object>();
		List<PartLocationPermissionDto> 	PartLocPermissionList				= null;
		Long 								Workorders_id 						= null;
		List<PartLocations>					partlocationList					= null;
		WorkOrders							workOrders							= null;
		WorkOrdersDto 						workOrdersDto						= null;
		Double 								workOrdersQuantity					= null;
		List<InventoryDto> 					get_InventoryList					= null;
		WorkOrdersTasksToParts 				workTaskToParts						= null;
		Date 								currentDateUpdate					= null;
		Timestamp							LastUpdate							= null;
		Double 								EachpartInventoryUnitprice			= WORK_ORDER_DEFALAT_AMOUNT;
		Double 								Totalcost 							= WORK_ORDER_DEFALAT_AMOUNT;
		boolean 							isFilled_GET_Permission				= false;
		InventoryLocation 					inventoryLocation					= null;
		WorkOrdersTasksToReceived 			TasksToReceived						= null;
		WorkOrdersTasksToParts				preWorkOrdersTasksToParts			= null;
		WorkOrdersDto						preWorkOrderLastUpdatedDetails		= null;
		String								subLocationQuery					= "";
		try {
			currentDateUpdate 		= new Date();
			LastUpdate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			partlocationList		= partLocationPermissionService.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id());
			PartLocPermissionList 	= partLocationsBL.prepareListofPartLocation(partlocationList);
			
			workOrders				= workOrdersService.getWorkOrders(workOrdersTasksToParts.getWorkorders_id());
			workOrdersDto 			= workOrdersBL.getWorkOrders(workOrders);

			isFilled_GET_Permission	= inventoryBL.isFilled_GET_Permission(workOrdersDto.getWorkorders_location_ID(), PartLocPermissionList);
			
			if (isFilled_GET_Permission) {
				workOrdersQuantity 	= workOrdersTasksToParts.getQuantity();
				/*if(object.getBoolean("showSubLocation") == true) {
					subLocationQuery 	= " AND I.subLocationId="+object.getInt("subLocationId")+" ";
				}*/
				get_InventoryList 	= inventoryService.Get_WorkOrder_InventoryLocation_id_To_Inventory(workOrdersTasksToParts.getPartid(), userDetails.getCompany_id(),subLocationQuery);

				if (get_InventoryList != null && !get_InventoryList.isEmpty()) {
					for (InventoryDto inventoryDto : get_InventoryList) {
						if (workOrdersQuantity <= inventoryDto.getQuantity()) {
							
							EachpartInventoryUnitprice 		= round((inventoryDto.getTotal() / inventoryDto.getHistory_quantity()), 2);
							Totalcost 						= workOrdersQuantity * EachpartInventoryUnitprice;
							preWorkOrdersTasksToParts		= workOrdersService.getLastOccorredOnPartDetailsByVehicleId(workOrdersDto.getVehicle_vid(),inventoryDto.getPartid(),userDetails.getCompany_id());
							
							if(preWorkOrdersTasksToParts != null) {
								preWorkOrderLastUpdatedDetails	= workOrdersService.getWorkOrderLastUpdatedDetails(preWorkOrdersTasksToParts.getWorkorders_id(), userDetails.getCompany_id());
							}
							workTaskToParts = workOrdersBL.prepareWorkOrdersTaskToPart(workOrdersTasksToParts, inventoryDto, workOrdersQuantity, userDetails, EachpartInventoryUnitprice, Totalcost, workOrdersDto, preWorkOrderLastUpdatedDetails);
							workOrdersService.addWorkOrdersTaskToParts(workTaskToParts);
							// Subtract in Inventory Quantity So Assign
							// workOrdersQuantity
							inventoryService.Subtract_update_Inventory_from_Workorder(workOrdersQuantity,inventoryDto.getInventory_id());
							// Subtract in InventoryLocation Quantity So Assign
							// workOrdersQuantity
							inventoryService.Subtract_update_InventoryLocation_from_Workorder(inventoryDto.getPartid(),inventoryDto.getLocationId(), userDetails.getCompany_id());
							// Subtract in InventoryAll Quantity So Assign
							// workOrdersQuantity
							inventoryService.Subtract_update_InventoryAll_from_Workorder(inventoryDto.getPartid(), userDetails.getCompany_id());

							try {
								workOrdersService.updateWorkOrdersTask_TotalPartCost(workTaskToParts.getWorkordertaskid(), userDetails.getCompany_id());
								// Update Main WorkOrder Total cost
								workOrdersService.updateWorkOrderMainTotalCost(workOrdersTasksToParts.getWorkorders_id());

								/**
								 * who Create this vehicle get email id to user profile details
								 */
								
								// update the WorkOrders to inProcess
								workOrdersService.updateWorkOrderProcess(WorkOrdersType.WORKORDERS_STATUS_INPROCESS, userDetails.getId(), LastUpdate, workOrdersTasksToParts.getWorkorders_id(), userDetails.getCompany_id());

								Workorders_id = workOrdersTasksToParts.getWorkorders_id();

							} catch (Exception e) {
								e.printStackTrace();
								return new ModelAndView("redirect:/viewWorkOrder.indanger=true");
							}

							break;
						} else {
							
							// ex: wo_Qty =100, Inven_Qty =200, 200-100=100 in
							// subtrack qty

							EachpartInventoryUnitprice 	= round(inventoryDto.getTotal() / inventoryDto.getHistory_quantity(), 2);
							Totalcost 					= inventoryDto.getQuantity() * EachpartInventoryUnitprice;
							workTaskToParts = workOrdersBL.prepareWorkOrdersTaskToPart(workOrdersTasksToParts, inventoryDto, inventoryDto.getQuantity(), userDetails, EachpartInventoryUnitprice, Totalcost, workOrdersDto, preWorkOrderLastUpdatedDetails);
							//comments .... (E)
							
							workOrdersService.addWorkOrdersTaskToParts(workTaskToParts);

							// Subtract in Inventory Quantity So
							inventoryService.Subtract_update_Inventory_from_Workorder(inventoryDto.getQuantity(), inventoryDto.getInventory_id());

							// Subtract in InventoryLocation Quantity So Assign
							inventoryService.Subtract_update_InventoryLocation_from_Workorder(inventoryDto.getPartid(),inventoryDto.getLocationId(), userDetails.getCompany_id());

							// Subtract in InventoryAll Quantity So Assign
							inventoryService.Subtract_update_InventoryAll_from_Workorder(inventoryDto.getPartid(),userDetails.getCompany_id());

							// Subtract create workOrder part and reminder
							workOrdersQuantity = (workOrdersQuantity - inventoryDto.getQuantity());

							try {
								workOrdersService.updateWorkOrdersTask_TotalPartCost(workTaskToParts.getWorkordertaskid(), userDetails.getCompany_id());

							//  Update Main WorkOrder Total cost
								workOrdersService.updateWorkOrderMainTotalCost(workOrdersTasksToParts.getWorkorders_id());

								/**
								 * who Create this vehicle get email id to user profile details
								 */

							//  update the WorkOrders to inProcess
								workOrdersService.updateWorkOrderProcess(WorkOrdersType.WORKORDERS_STATUS_INPROCESS, userDetails.getId(), LastUpdate, workOrdersTasksToParts.getWorkorders_id(), userDetails.getCompany_id());
								Workorders_id = workOrdersTasksToParts.getWorkorders_id();

							} catch (Exception e) {
								e.printStackTrace();
								return new ModelAndView("redirect:/viewWorkOrder.indanger=true");
							}
						}
					} 
					if (workOrdersTasksToParts.getOldpart() != null) {
						inventoryLocation 	= inventoryService.getInventoryLocation(workOrdersTasksToParts.getPartid());
						TasksToReceived 	= workOrdersBL.prepareWorkOrdersTaskToReceiced(workOrdersTasksToParts, inventoryLocation);
						TasksToReceived.setCompanyId(userDetails.getCompany_id());
						workOrdersService.addWorkOrdersToReceived(TasksToReceived);
					}
				}
			} else {
				Workorders_id = workOrdersTasksToParts.getWorkorders_id();
				model.put("NoAuthen", true);
				return new ModelAndView("redirect:/showWorkOrder?woId=" + Workorders_id + "", model);
			}

			Workorders_id = workOrdersTasksToParts.getWorkorders_id();

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/viewWorkOrder.in?danger=true");
		}

		return new ModelAndView("redirect:/showWorkOrder?woId=" + Workorders_id + "", model);
	}

	// Note: This /saveWorkOrderTaskLabor Controller is Save WorkOrder New Labor
	// cost
	// Entries Details also Update Total Labor Cost in One Task wise and
	// Update Total Amount Cost in WorkOrder Details
	@RequestMapping(value = "/saveWorkOrderTaskLabor", method = RequestMethod.POST)
	public ModelAndView saveWorkOrderTaskLabor(@ModelAttribute("command") WorkOrdersTasksToLabor workOrdersTasksToLabor,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		Long Workorders_id = null;
		CustomUserDetails userDetails = null;
		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<PartLocationPermissionDto> PartLocPermission = partLocationsBL.prepareListofPartLocation(partLocationPermissionService
					.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

			WorkOrdersDto work = workOrdersBL
					.getWorkOrders(workOrdersService.getWorkOrders(workOrdersTasksToLabor.getWorkorders_id()));
			// check user branch location and workOrder location if same
			if (inventoryBL.isFilled_GET_Permission(work.getWorkorders_location_ID(), PartLocPermission)) {
				// save workOrder Task to Parts value
				WorkOrdersTasksToLabor workTaskToLabor = workOrdersBL.prepareWorkOrdersTaskToLabor(workOrdersTasksToLabor);
				workTaskToLabor.setCompanyId(userDetails.getCompany_id());
				workOrdersService.addWorkOrdersTaskToLabor(workTaskToLabor);

				try {

					workOrdersService.updateWorkOrdersTask_TotalLaborCost(workTaskToLabor.getWorkordertaskid(),	userDetails.getCompany_id());

					// Update Main WorkOrder Total cost
					try {

						workOrdersService.updateWorkOrderMainTotalCost(workOrdersTasksToLabor.getWorkorders_id());

					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView("redirect:/viewWorkOrder.indanger=true");
					}

					/**
					 * who Create this vehicle get email id to user profile details
					 */

					Date currentDateUpdate = new Date();
					Timestamp LastUpdate = new java.sql.Timestamp(currentDateUpdate.getTime());

					// update the WorkOrders to inProcess
					workOrdersService.updateWorkOrderProcess(WorkOrdersType.WORKORDERS_STATUS_INPROCESS, userDetails.getId(), LastUpdate, workOrdersTasksToLabor.getWorkorders_id(), userDetails.getCompany_id());

				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/viewWorkOrder.indanger=true");
				}

			} else {
				Workorders_id = workOrdersTasksToLabor.getWorkorders_id();
				model.put("NoAuthen", true);
				return new ModelAndView("redirect:/showWorkOrder?woId=" + Workorders_id + "", model);
			}
			Workorders_id = workOrdersTasksToLabor.getWorkorders_id();

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/viewWorkOrder.indanger=true");
		}

		return new ModelAndView("redirect:/showWorkOrder?woId=" + Workorders_id + "", model);
	}

	// Note: This /saveWorkOrderTaskCompletion Controller is Save Mark Task to
	// Completed
	// in WorkOrder Details
	@RequestMapping(value = "/saveWorkOrderTaskCompletion", method = RequestMethod.GET)
	public ModelAndView saveWorkOrderTaskCompletion(@ModelAttribute("command") WorkOrdersTasks workOrdersTasks,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		Long Workorders_id = null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			// save workOrder Task to Parts value
			WorkOrdersTasks workTask = workOrdersService.getWorkOrdersCompletion(workOrdersTasks.getWorkordertaskid(), userDetails.getCompany_id());
			// get Tasks task complete Total cost

			if (workTask.getMark_complete() == null || workTask.getMark_complete() == 0) {
				workOrdersService.updateWorkOrdersTask_Completion(1, workOrdersTasks.getWorkordertaskid(), userDetails.getCompany_id());
			} else {
				workOrdersService.updateWorkOrdersTask_Completion(0, workOrdersTasks.getWorkordertaskid(), userDetails.getCompany_id());
			}

			Workorders_id = workTask.getWorkorders().getWorkorders_id();

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/viewWorkOrder.indanger=true");
		}

		return new ModelAndView("redirect:/showWorkOrder?woId=" + Workorders_id + "", model);

	}

	// Note: This /workOrdersUpdate_tax_cost Controller is Save Total Tax Cost
	// WorkOrder
	// Total Tax Details
	@RequestMapping(value = "/workOrdersUpdate_tax_cost", method = RequestMethod.POST)
	public ModelAndView workOrdersUpdate_tax_cost(@ModelAttribute("command") WorkOrders workOrders,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			// save workOrder Task to Parts value
			WorkOrders workTask = workOrdersService.getWorkOrders(workOrders.getWorkorders_id());
			// get Tasks task complete Total cost

			Double TotalWorkOrdercost = WORK_ORDER_DEFALAT_AMOUNT;

			if (workTask != null) {
				if (workTask.getTotalworktax_cost() == null || workTask.getTotalworktax_cost() == 0) {

					Double TotalWorkOrdercost_DB = workTask.getTotalworkorder_cost();
					if (TotalWorkOrdercost_DB == null) {
						TotalWorkOrdercost_DB = WORK_ORDER_DEFALAT_AMOUNT;
					}
					TotalWorkOrdercost = TotalWorkOrdercost_DB + workOrders.getTotalworktax_cost();

				} else {
					Double TotalWorkOrdercost_DB = workTask.getTotalworkorder_cost();
					Double TotalWorkOrderTax_DB = workTask.getTotalworktax_cost();
					if (TotalWorkOrdercost_DB == null) {
						TotalWorkOrdercost_DB = WORK_ORDER_DEFALAT_AMOUNT;
					}
					Double TotalWorkOrderNowSubtrackAfter = WORK_ORDER_DEFALAT_AMOUNT;

					TotalWorkOrderNowSubtrackAfter = (TotalWorkOrdercost_DB - TotalWorkOrderTax_DB);
					TotalWorkOrdercost = TotalWorkOrderNowSubtrackAfter + workOrders.getTotalworktax_cost();

				}

				workOrdersService.updateWorkOrderMainTotalCost_TAX(workOrders.getTotalworktax_cost(), round(TotalWorkOrdercost, 2), workOrders.getWorkorders_id(), userDetails.getCompany_id());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/viewWorkOrder.in", model);
		}

		return new ModelAndView("redirect:/showWorkOrder?woId=" + workOrders.getWorkorders_id() + "", model);
	}

	// Note: This /work_orders_INPROCESS Controller is Change WorkOrder Status
	// Open To INPROCESS To save WordOrder Details
	@RequestMapping(value = "/work_orders_INPROCESS", method = RequestMethod.GET)
	public ModelAndView work_orders_INPROCESS(@RequestParam("workorders_id") final Long Workorders_id,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		WorkOrdersDto workOrders = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			workOrders = workOrdersService.getWorkOrdersDetails(Workorders_id, userDetails.getCompany_id());
			if (workOrders != null) {
				VehicleDto	vehicleStatus	=	vehicleService.Get_Vehicle_Current_Status_TripSheetID(workOrders.getVehicle_vid(), userDetails.getCompany_id());
				WorkOrdersDto work = workOrdersBL.Show_WorkOrders(workOrders);

				List<PartLocationPermissionDto> PartLocPermission = partLocationsBL.prepareListofPartLocation(partLocationPermissionService
						.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

				if (inventoryBL.isFilled_GET_Permission(work.getWorkorders_location_ID(), PartLocPermission)) {

					/**
					 * who Create this vehicle get email id to user profile details
					 */

					Date currentDateUpdate = new Date();
					Timestamp LastUpdate = new java.sql.Timestamp(currentDateUpdate.getTime());

					if (work.getISSUES_ID() != 0) {

						try {

							Issues Save_Issues = new Issues();
							Save_Issues.setISSUES_ID(work.getISSUES_ID());
							IssuesTasks WRTask = new IssuesTasks();

							/** Task new from to create */
							WRTask.setISSUES_TASK_STATUS_ID(work.getWorkorders_statusId());

							/** Task new from to create change status */
							WRTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_WOCREATED);
							/** Set Created by email Id */
							WRTask.setISSUES_CREATEBY_ID(userDetails.getId());
							//WRTask.setISSUES_CREATEBY_NAME(userDetails.getFirstName());
							/** Set Created Current Date */
							WRTask.setISSUES_CREATED_DATE(LastUpdate);
							WRTask.setISSUES(Save_Issues);
							WRTask.setCOMPANY_ID(userDetails.getCompany_id());
							WRTask.setISSUES_REASON("WorkOrder RE-OPEN Status ");
							/** save to IssuesTask values */
							issuesService.registerNew_IssuesTasks(WRTask);

							issuesService.update_Create_WorkOrder_Issues_Status(
									IssuesTypeConstant.ISSUES_STATUS_WOCREATED, userDetails.getId(), LastUpdate,
									work.getWorkorders_id(), work.getISSUES_ID(),userDetails.getCompany_id());

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					
					
					ValueObject	ownerShipObject	= null;
					if(vehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED){
						ownerShipObject	= new ValueObject();
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, workOrders.getWorkorders_id());
						ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, workOrders.getVehicle_vid());
						ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, workOrders.getTotalworkorder_cost());
						ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
						ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_WORKORDER);
						ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, userDetails.getCompany_id());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, sqlFormat.parse(sqlFormat.format(currentDateUpdate)));
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "WorkOrder Entry");
						ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "WorkOrder Number : "+workOrders.getWorkorders_Number());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
						ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, userDetails.getId());
						ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, workOrders.getTotalworkorder_cost());
						
						vehicleAgentIncomeExpenseDetailsService.deleteVehicleAgentIncomeExpense(ownerShipObject);
					}
					if(vehicleStatus.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE && vehicleStatus.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
						workOrdersService.updateWorkOrderProcess(WorkOrdersType.WORKORDERS_STATUS_INPROCESS, userDetails.getId(), LastUpdate, Workorders_id, userDetails.getCompany_id(), vehicleStatus.getvStatusId());
					}else {
						workOrdersService.updateWorkOrderProcess(WorkOrdersType.WORKORDERS_STATUS_INPROCESS, userDetails.getId(), LastUpdate, Workorders_id, userDetails.getCompany_id());
					}

					// Note : When Vehicle Created Work Order That time Vehicle
					// Status update to 'WORKSHOP'
					vehicleService.Update_Vehicle_Status_TripSheetID(Workorders_id, work.getVehicle_vid(), 
							userDetails.getCompany_id(), VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP);
					
VehicleExpenseDetails	vehicleExpenseDetails	= vehicleExpenseDetailsService.getVehicleExpenseDetails(workOrders.getWorkorders_id(), userDetails.getCompany_id() ,workOrders.getVehicle_vid(), VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER);
					
					if(vehicleExpenseDetails != null) {
						ValueObject		valueObject	= new ValueObject();
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER);
						valueObject.put("txnAmount", workOrders.getTotalworkorder_cost());
						valueObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(workOrders.getCompleted_dateOn()));
						valueObject.put("txnTypeId", workOrders.getWorkorders_id());
						valueObject.put("vid", workOrders.getVehicle_vid());
						valueObject.put("companyId", userDetails.getCompany_id());
						
						new Thread() {
							public void run() {
								try {
									vehicleProfitAndLossService.runThreadForDeleteVehicleExpenseDetails(valueObject);
									vehicleProfitAndLossService.runThreadForDeleteDateWiseVehicleExpenseDetails(valueObject);
									vehicleProfitAndLossService.runThreadForDeleteMonthWiseVehicleExpenseDetails(valueObject);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}		
						}.start();
					}
					

					return new ModelAndView("redirect:/showWorkOrder?woId=" + Workorders_id + "", model);

				} else {
					model.put("NoAuthen", true);
					return new ModelAndView("redirect:/showWorkOrder?woId=" + Workorders_id + "", model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("addWorkOrdersParts", model);
	}

	// Note: This /work_orders_ONHOLD Controller is Change WorkOrder Status
	// Open To ONHOLD To save WordOrder Details
	@RequestMapping(value = "/work_orders_ONHOLD", method = RequestMethod.GET)
	public ModelAndView work_orders_ONHOLD(@RequestParam("workorders_id") final Long Workorders_id,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		WorkOrdersDto workOrders = null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			workOrders = workOrdersService.getWorkOrdersDetails(Workorders_id, userDetails.getCompany_id());
			if (workOrders != null) {

				WorkOrdersDto work = workOrdersBL.Show_WorkOrders(workOrders);

				List<PartLocationPermissionDto> PartLocPermission = partLocationsBL.prepareListofPartLocation(partLocationPermissionService
						.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

				if (inventoryBL.isFilled_GET_Permission(work.getWorkorders_location_ID(), PartLocPermission)) {

					/**
					 * who Create this vehicle get email id to user profile details
					 */
					VehicleDto	vehicleStatus	=	vehicleService.Get_Vehicle_Current_Status_TripSheetID(workOrders.getVehicle_vid(), userDetails.getCompany_id());
					Date currentDateUpdate = new Date();
					Timestamp LastUpdate = new java.sql.Timestamp(currentDateUpdate.getTime());
					
					workOrdersService.updateWorkOrderProcess(WorkOrdersType.WORKORDERS_STATUS_ONHOLD, userDetails.getId(), LastUpdate, Workorders_id, userDetails.getCompany_id());

					// Note : When Vehicle Created Work Order That time Vehicle
					// Status update to 'WORKSHOP'
					if(workOrders.getVehicleReOpenStatusId() > 0 && vehicleStatus.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE && vehicleStatus.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
						vehicleService.Update_Vehicle_Status_TripSheetID(TRIP_SHEET_DEFALAT_VALUE, work.getVehicle_vid(), 
								userDetails.getCompany_id(), workOrders.getVehicleReOpenStatusId());
					}else {
						vehicleService.Update_Vehicle_Status_TripSheetID(TRIP_SHEET_DEFALAT_VALUE, work.getVehicle_vid(), 
								userDetails.getCompany_id(), VehicleStatusConstant.VEHICLE_STATUS_ACTIVE);
					}

					return new ModelAndView("redirect:/showWorkOrder?woId=" + Workorders_id + "", model);

				} else {
					model.put("NoAuthen", true);
					return new ModelAndView("redirect:/showWorkOrder?woId=" + Workorders_id + "", model);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("addWorkOrdersPartsOnHold", model);
	}

	// Note: This /work_orders_COMPLETE Controller is Change WorkOrder Status
	// Open To COMPLETE To save WordOrder Details and Also Update Vehicle Status
	// WORKSHOP To ACTIVE Status
	@RequestMapping(value = "/work_orders_COMPLETE", method = RequestMethod.GET)
	public ModelAndView work_orders_COMPLETE(@RequestParam("workorders_id") final Long Workorders_id, final HttpServletRequest request) throws Exception {

		Map<String, Object> 				model 				= new HashMap<String, Object>();
		CustomUserDetails 					userDetails 		= null;
		WorkOrdersDto 						workOrders 			= null;
		List<PartLocationPermissionDto> 	PartLocPermission 	= null;
		WorkOrdersDto 						work 				= null;
		List<EmailAlertQueue>  				pendingList			= null;
		List<SmsAlertQueue>  				pendingList1		= null;
		EmailAlertQueue 					email 				= null;
		SmsAlertQueue 						sms 				= null;
		ValueObject							ownerShipObject		= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			workOrders = workOrdersService.getWorkOrdersDetails(Workorders_id, userDetails.getCompany_id());
			
			if (workOrders != null) {
				work 				= workOrdersBL.Show_WorkOrders(workOrders);
				workOrders.setCompanyId(userDetails.getCompany_id());
				PartLocPermission 	= partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

				if (inventoryBL.isFilled_GET_Permission(work.getWorkorders_location_ID(), PartLocPermission)) {

					Date currentDateUpdate = new Date();
					Timestamp COMPLETE_date = new java.sql.Timestamp(currentDateUpdate.getTime());

					workOrdersService.updateWorkOrderCOMPLETE_date(WorkOrdersType.WORKORDERS_STATUS_COMPLETE, COMPLETE_date, userDetails.getEmail(), COMPLETE_date, Workorders_id,
							userDetails.getCompany_id());
					VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker	= null;
					if(workOrders.getTotalworkorder_cost() > 0) {
						
						ValueObject		object	= new ValueObject();
						
						object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
						object.put(VehicleProfitAndLossDto.TRANSACTION_ID, workOrders.getWorkorders_id());
						object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
						object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER);
						object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
						object.put(VehicleProfitAndLossDto.TRANSACTION_VID, workOrders.getVehicle_vid());
						object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, workOrders.getWorkorders_id());
						
						
						profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);
						
						vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);
						
						VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(workOrders.getVehicle_vid(), userDetails.getCompany_id()); 
						if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED ){
							ownerShipObject	= new ValueObject();
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, workOrders.getWorkorders_id());
							ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, workOrders.getVehicle_vid());
							ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, workOrders.getTotalworkorder_cost());
							ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
							ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_WORKORDER);
							ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, userDetails.getCompany_id());
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, sqlFormat.parse(sqlFormat.format(currentDateUpdate)));
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "WorkOrder Entry");
							ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "WorkOrder Number : "+workOrders.getWorkorders_Number());
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
							ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, userDetails.getId());
							ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, -workOrders.getTotalworkorder_cost());
							
							VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
							vehicleAgentTxnCheckerService.save(agentTxnChecker);
							
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
							
						}
						
						
					}

					// Note : When Vehicle Created Work Order That time Vehicle
					// Status update to 'WORKSHOP'
					if(workOrders.getVehicleReOpenStatusId() > 0) {
						vehicleService.Update_Vehicle_Status_TripSheetID(TRIP_SHEET_DEFALAT_VALUE, work.getVehicle_vid(), 
								userDetails.getCompany_id(), workOrders.getVehicleReOpenStatusId());
					}else {
						vehicleService.Update_Vehicle_Status_TripSheetID(TRIP_SHEET_DEFALAT_VALUE, work.getVehicle_vid(), 
								userDetails.getCompany_id(), VehicleStatusConstant.VEHICLE_STATUS_ACTIVE);
					}
					List<WorkOrdersTasksDto> workOrdersTasks = workOrdersService.getWorkOrdersTasks(Workorders_id, userDetails.getCompany_id());
					
					WorkOrderTxnChecker	workOrderTxnChecker	= workOrdersBL.getWorkOrderTxnChecker(workOrders);
					
					workOrderTxnCheckerService.saveWorkOrderTxnChecker(workOrderTxnChecker);

					// update Service Reminder into WorkOders
					if (workOrdersTasks != null && !workOrdersTasks.isEmpty()) {
						// many WorkOrdersTask and Update to that complete Time to
						// service Reminder
						for (WorkOrdersTasksDto workOrdersTasksToServiceRC : workOrdersTasks) {

							List<ServiceReminder> serviceReminder = serviceReminderService
									.listSearchWorkorderToServiceReminder(work.getVehicle_vid(),
											workOrdersTasksToServiceRC.getJob_typetaskId(),
											workOrdersTasksToServiceRC.getJob_subtypetask_id(),
											userDetails.getCompany_id());
							if (serviceReminder != null && !serviceReminder.isEmpty()) {
								// Serach to find
								for (ServiceReminder serviceReminderFindSameWorkorder : serviceReminder) {

									ServiceReminder service = workOrdersBL.WorkOrdersTOServiceReminder(serviceReminderFindSameWorkorder, work,userDetails);
									// update the all value in DB
									service.setCompanyId(userDetails.getCompany_id());
									serviceReminderService.updateServiceReminder(service);
									
									
									pendingList	= emailAlertQueueService.getAllEmailAlertQueueDetailsById(serviceReminderFindSameWorkorder.getService_id());
									
									emailAlertQueueService.deleteEmailAlertQueue(serviceReminderFindSameWorkorder.getService_id());
									
									if(pendingList != null && !pendingList.isEmpty()) {
										
										for(EmailAlertQueue	emailAlertQueue : pendingList) {
											
											if(emailAlertQueue.getAlertBeforeDate() != null) {
											email = new EmailAlertQueue();
											email.setVid(emailAlertQueue.getVid());
											email.setContent(emailAlertQueue.getContent());
											email.setAlertType(emailAlertQueue.getAlertType());
											email.setEmailId(emailAlertQueue.getEmailId());
											email.setCompanyId(emailAlertQueue.getCompanyId());
											email.setTransactionId(emailAlertQueue.getTransactionId());
											email.setTransactionNumber(emailAlertQueue.getTransactionNumber());
											email.setOverDueAlert(false);
											email.setEmailSent(false);
											email.setAlertBeforeValues(emailAlertQueue.getAlertBeforeValues());
											email.setServiceDate(service.getTime_servicedate());
											
												
											final SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
											Calendar calendar1 = Calendar.getInstance();
											calendar1.setTime(service.getTime_servicedate());
											
											calendar1.add(Calendar.DAY_OF_YEAR, -emailAlertQueue.getAlertBeforeValues());
											java.util.Date alertDate1 = format1.parse(format1.format(calendar1.getTime()));
											java.util.Date alertBeforeDate =  new Date(alertDate1.getTime());
											email.setAlertBeforeDate(alertBeforeDate+"");
											email.setAlertScheduleDate(alertBeforeDate);
										
											emailAlertQueueService.updateEmailAlertQueue(email);
										  } else {
											   
											  EmailAlertQueue email1 = new EmailAlertQueue();
												email1.setVid(emailAlertQueue.getVid());
												email1.setContent(emailAlertQueue.getContent());
												email1.setAlertType(emailAlertQueue.getAlertType());
												email1.setEmailId(emailAlertQueue.getEmailId());
												email1.setCompanyId(emailAlertQueue.getCompanyId());
												email1.setTransactionId(emailAlertQueue.getTransactionId());
												email1.setTransactionNumber(emailAlertQueue.getTransactionNumber());
												email1.setOverDueAlert(false);
												email1.setEmailSent(false);
												email1.setAlertAfterValues(emailAlertQueue.getAlertAfterValues());
												email1.setServiceDate(service.getTime_servicedate());
													
												final SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
												Calendar calendar2 = Calendar.getInstance();
												calendar2.setTime(service.getTime_servicedate());
												if(emailAlertQueue.getAlertAfterValues() != null) {
													calendar2.add(Calendar.DAY_OF_YEAR, emailAlertQueue.getAlertAfterValues());
													java.util.Date alertDate1 = format2.parse(format2.format(calendar2.getTime()));
													java.util.Date alertAfterDate =  new Date(alertDate1.getTime());
													email1.setAlertAfterDate(alertAfterDate+"");
													email1.setAlertScheduleDate(alertAfterDate);
												}
											
												emailAlertQueueService.updateEmailAlertQueue(email1);
											  
										       }
										}	
									}	
									
									
									pendingList1	= smsAlertQueueService.getAllSmsAlertQueueDetailsById(serviceReminderFindSameWorkorder.getService_id());
									
									smsAlertQueueService.deleteSmsAlertQueue(serviceReminderFindSameWorkorder.getService_id());
									
										if(pendingList1 != null && !pendingList1.isEmpty()) {
										
										for(SmsAlertQueue	smsAlertQueue : pendingList1) {
											
											if(smsAlertQueue.getAlertBeforeDate() != null) {
											sms = new SmsAlertQueue();
											sms.setVid(smsAlertQueue.getVid());
											sms.setContent(smsAlertQueue.getContent());
											sms.setAlertType(smsAlertQueue.getAlertType());
											sms.setMobileNumber(smsAlertQueue.getMobileNumber());
											sms.setCompanyId(smsAlertQueue.getCompanyId());
											sms.setTransactionId(smsAlertQueue.getTransactionId());
											sms.setTransactionNumber(smsAlertQueue.getTransactionNumber());
											sms.setOverDueAlert(false);
											sms.setSmsSent(false);
											sms.setAlertBeforeValues(smsAlertQueue.getAlertBeforeValues());
											sms.setServiceDate(service.getTime_servicedate());
											
												
											final SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
											Calendar calendar1 = Calendar.getInstance();
											calendar1.setTime(service.getTime_servicedate());
											
											calendar1.add(Calendar.DAY_OF_YEAR, -smsAlertQueue.getAlertBeforeValues());
											java.util.Date alertDate1 = format1.parse(format1.format(calendar1.getTime()));
											java.util.Date alertBeforeDate =  new Date(alertDate1.getTime());
											sms.setAlertBeforeDate(alertBeforeDate+"");
											sms.setAlertScheduleDate(alertBeforeDate);
										
											smsAlertQueueService.updateSmsAlertQueue(sms);
										  } else {
											   
											  SmsAlertQueue sms1 = new SmsAlertQueue();
												sms1.setVid(smsAlertQueue.getVid());
												sms1.setContent(smsAlertQueue.getContent());
												sms1.setAlertType(smsAlertQueue.getAlertType());
												sms1.setMobileNumber(smsAlertQueue.getMobileNumber());
												sms1.setCompanyId(smsAlertQueue.getCompanyId());
												sms1.setTransactionId(smsAlertQueue.getTransactionId());
												sms1.setTransactionNumber(smsAlertQueue.getTransactionNumber());
												sms1.setOverDueAlert(false);
												sms1.setSmsSent(false);
												sms1.setAlertAfterValues(smsAlertQueue.getAlertAfterValues());
												sms1.setServiceDate(service.getTime_servicedate());
													
												final SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
												Calendar calendar2 = Calendar.getInstance();
												calendar2.setTime(service.getTime_servicedate());
												
												calendar2.add(Calendar.DAY_OF_YEAR, smsAlertQueue.getAlertAfterValues());
												java.util.Date alertDate1 = format2.parse(format2.format(calendar2.getTime()));
												java.util.Date alertAfterDate =  new Date(alertDate1.getTime());
												sms1.setAlertAfterDate(alertAfterDate+"");
												sms1.setAlertScheduleDate(alertAfterDate);
											
												smsAlertQueueService.updateSmsAlertQueue(sms1);
											  
										       }
										}	
									}	
									
									
									ServiceReminderWorkOrderHistory	orderHistory =	workOrdersBL.getServiceWorkOrderDto(serviceReminderFindSameWorkorder, work, userDetails);
									ServiceReminderWorkOrderHistory	oldHistory =	serviceReminderService.getServiceWorkHistory(Workorders_id);
									if(oldHistory != null)
										orderHistory.setServiceReminderWorkOrderHistoryId(oldHistory.getServiceReminderWorkOrderHistoryId());
									orderHistoryRepository.save(orderHistory);
									
									serviceReminderService.updateServiceReminder(service);
								}
							}

						}
					}

					// Note : via Issues to Create WorkOrder To Resolves The values

					if (work.getWorkorders_id() != null && work.getWorkorders_id() != 0) {

						List<IssuesDto> WOISSUESS = issuesService.GET_WORKORDER_ID_TO_ISSUES_DETAILS(work.getWorkorders_id(),
								userDetails.getCompany_id());
						if (WOISSUESS != null && !WOISSUESS.isEmpty() ) {
							
							for( IssuesDto WOISSUES : WOISSUESS) {
							/**
							 * who Create this Issues get email id to user profile details
							 */
							try {

								Issues Save_Issues = new Issues();
								Save_Issues.setISSUES_ID(WOISSUES.getISSUES_ID());
								IssuesTasks WRTask = new IssuesTasks();
								/** Task new from to create */
								WRTask.setISSUES_TASK_STATUS_ID(WOISSUES.getISSUES_STATUS_ID());
								/** Task new from to create change status */
								WRTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_RESOLVED);
								/** Set Created by email Id */
								WRTask.setISSUES_CREATEBY_ID(userDetails.getId());
								//WRTask.setISSUES_CREATEBY_NAME(userDetails.getFirstName());
								java.util.Date currentDateUpdateIssues = new java.util.Date();
								Timestamp toDate = new java.sql.Timestamp(currentDateUpdateIssues.getTime());
								/** Set Created Current Date */
								WRTask.setISSUES_CREATED_DATE(toDate);
								WRTask.setISSUES(Save_Issues);
								WRTask.setCOMPANY_ID(userDetails.getCompany_id());
								WRTask.setISSUES_REASON("WorkOrder To Issues Resovled");
								/** save to IssuesTask values */
								issuesService.registerNew_IssuesTasks(WRTask);

								issuesService.update_Create_WorkOrder_Issues_Status(
										IssuesTypeConstant.ISSUES_STATUS_RESOLVED, userDetails.getId(), toDate,
										work.getWorkorders_id(), WOISSUES.getISSUES_ID(),userDetails.getCompany_id());

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						}
					}
					
					// To Update  anyPartNumberAsign column in PartInvoice Table hence Part wouldn't be edited in partInvoice.
					List<WorkOrdersTasksToPartsDto> workOrdersTasksToPart = workOrdersService.getWorkOrdersTasksToParts(Workorders_id, userDetails.getCompany_id());
					if (workOrdersTasksToPart != null && !workOrdersTasksToPart.isEmpty()) {
						
						for (WorkOrdersTasksToPartsDto workOrdersTasksToPartInvoice : workOrdersTasksToPart) {
							
							Inventory inventory = inventoryService.getInventoryDetails(workOrdersTasksToPartInvoice.getInventory_id(), userDetails.getCompany_id());
							if(inventory != null && inventory.getPartInvoiceId() != null) {
								
								Inventory inv = new Inventory();
								
									inv.setInventory_id(inventory.getInventory_id());
									inv.setPartInvoiceId(inventory.getPartInvoiceId());
									inv.setCompanyId(inventory.getCompanyId());
									
									inventoryService.update_anyPartNumberAsign_InPartInvoice(inv.getPartInvoiceId(), inv.getCompanyId());
							}
						
						}
					}
					
					if(profitAndLossTxnChecker != null) {
						
						ValueObject	valueObject	= new ValueObject();
						valueObject.put("workOrders", workOrders);
						valueObject.put("userDetails", userDetails);
						valueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER);
						valueObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
						
						new Thread() {
							public void run() {
								try {
									vehicleProfitAndLossService.runThreadForVehicleExpenseDetails(valueObject);
									vehicleProfitAndLossService.runThreadForDateWiseVehicleExpenseDetails(valueObject);
									vehicleProfitAndLossService.runThreadForMonthWiseVehicleExpenseDetails(valueObject);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}		
						}.start();
					}
					
					if(ownerShipObject != null) {
						vehicleAgentIncomeExpenseDetailsService.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
					}

					return new ModelAndView("redirect:/showWorkOrder?woId=" + Workorders_id + "", model);

				} else {
					model.put("NoAuthen", true);
					return new ModelAndView("redirect:/showWorkOrder?woId=" + Workorders_id + "", model);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("addWorkOrdersPartsCOMPLETE", model);
	}

	// Note: This /saveWorkOrdersOpenTask Controller is Add New Task inside
	// WorkOrder To Created Details
	@RequestMapping(value = "/saveWorkOrdersOpenTask", method = RequestMethod.POST)
	public ModelAndView saveWorkOrdersOpenTask(@ModelAttribute("command") WorkOrdersDto workOrders,
			WorkOrdersTasks workOrdersTask, final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			WorkOrders WorkOrd = workOrdersBL.prepareWorkOrdersToAddTask(workOrders);
			WorkOrdersTasks WorkTOTask = new WorkOrdersTasks();

			WorkTOTask.setVehicle_vid(workOrdersTask.getVehicle_vid());

			WorkTOTask.setJob_typetaskId(workOrdersTask.getJob_typetaskId());

			JobSubType JobSubType = jobSubTypeService.getJobSubType(workOrdersTask.getJob_subtypetask_id());
			if (JobSubType != null) {
				WorkTOTask.setJob_subtypetask_id(workOrdersTask.getJob_subtypetask_id());
			}
			
			if(workOrdersTask.getJobTypeRemark() != null && workOrdersTask.getJobTypeRemark() != "") {
				WorkTOTask.setJobTypeRemark(workOrdersTask.getJobTypeRemark());								
			}

			WorkTOTask.setTotalpart_cost(WORK_ORDER_DEFALAT_AMOUNT);
			WorkTOTask.setTotallaber_cost(WORK_ORDER_DEFALAT_AMOUNT);
			WorkTOTask.setTotaltask_cost(WORK_ORDER_DEFALAT_AMOUNT);
			WorkTOTask.setWorkorders(WorkOrd);
			WorkTOTask.setCompanyId(userDetails.getCompany_id());

			List<WorkOrders> LastOcc = workOrdersService.getLast_WorkOrdersTask_To_WorkOrders_details(WorkTOTask.getJob_typetaskId(), WorkTOTask.getJob_subtypetask_id(), WorkTOTask.getVehicle_vid(), userDetails.getCompany_id());

			if (LastOcc != null && !LastOcc.isEmpty()) {
				for (WorkOrders lastOccWO : LastOcc) {

					WorkTOTask.setLast_occurred_woId(lastOccWO.getWorkorders_id());
					WorkTOTask.setLast_occurred_date(lastOccWO.getStart_date());
					WorkTOTask.setLast_occurred_odameter(lastOccWO.getVehicle_Odometer());
					break;
				}
			} else {
				WorkTOTask.setLast_occurred_woId(WORK_ORDER_DEFALAT_VALUE);
				WorkTOTask.setLast_occurred_date(null);
				WorkTOTask.setLast_occurred_odameter(0);
			}

			workOrdersService.addWorkOrdersTask(WorkTOTask);

		} catch (Exception e) {
			return new ModelAndView("redirect:/viewWorkOrder.indanger=true");
		}

		return new ModelAndView("redirect:/showWorkOrder?woId=" + workOrders.getWorkorders_id() + "", model);

	}

	// Note: This /getVehicleList Controller is Get Vehicle List Details
	@RequestMapping(value = "/getVehicleList", method = RequestMethod.GET)
	public void getAddressList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<VehicleDto> addresses = new ArrayList<VehicleDto>();
		List<Vehicle> vehicle = vehicleService.findAllVehiclelist();
		if (vehicle != null && !vehicle.isEmpty()) {
			for (Vehicle add : vehicle) {
				VehicleDto wadd = new VehicleDto();
				wadd.setVid(add.getVid());
				wadd.setVehicle_registration(add.getVehicle_registration());
				addresses.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(addresses));
	}

	// get Job Drop down list
	@RequestMapping(value = "/getJobTypeList", method = RequestMethod.GET)
	public void getJobTypeList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<JobTypeDto> JobType = new ArrayList<JobTypeDto>();
		List<JobType> Job = jobTypeService.listJobTypeByCompanyId(userDetails.getCompany_id());
		if (Job != null && !Job.isEmpty()) {
			for (JobType add : Job) {
				JobTypeDto wadd = new JobTypeDto();
				/* wadd.setVid(add.getVid()); */
				wadd.setJob_Type(add.getJob_Type());
				wadd.setJob_id(add.getJob_id());

				JobType.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(JobType));
	}

	// get JobSub Drop down list
	@RequestMapping(value = "/getJobSubTypeList", method = RequestMethod.GET)
	public void getJobSubTypeList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		List<JobSubTypeDto> JobType = new ArrayList<JobSubTypeDto>();
		List<JobSubTypeDto> JobSub = jobSubTypeService.listJobSubTypeByCompanyId(userDetails.getCompany_id());
		if (JobSub != null && !JobSub.isEmpty()) {
			for (JobSubTypeDto add : JobSub) {
				JobSubTypeDto wadd = new JobSubTypeDto();

				wadd.setJob_Subid(add.getJob_Subid());
				wadd.setJob_Type(add.getJob_Type());
				wadd.setJob_TypeId(add.getJob_TypeId());
				wadd.setJob_ROT(add.getJob_ROT());
				wadd.setJob_ROT_number(add.getJob_ROT_number());
				wadd.setJob_ROT_hour(add.getJob_ROT_hour());
				wadd.setJob_ROT_amount(add.getJob_ROT_amount());

				JobType.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(JobType));
	}

	// get Vehicle Drop down To Current Odometer
	@RequestMapping(value = "/getVehicleOdoMerete", method = RequestMethod.GET)
	public void getVehicleOdoMerete(@RequestParam(value = "vehicleID", required = true) Integer vehicleID,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		VehicleDto wadd = new VehicleDto();
		if(vehicleID != null) {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			Vehicle oddmeter = vehicleService.getVehicel(vehicleID, userDetails.getCompany_id());
			
			if (oddmeter != null) {
				
				HashMap<String, Object> 	gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
				if((boolean) gpsConfiguration.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
					
					ValueObject	object	= new ValueObject();
					
					object.put("companyId", userDetails.getCompany_id());
					object.put("vehicleId", vehicleID);
					
					ValueObject	gpsObject	= vehicleGPSDetailsService.getVehicleGPSData(object);
					if(gpsObject != null) {
						if(gpsObject.containsKey(GPSConstant.VEHICLE_TOTAL_KM_NAME)) {
							wadd.setGpsOdameter(gpsObject.getDouble(GPSConstant.VEHICLE_TOTAL_KM_NAME, 0));
							wadd.setGpsLocation(gpsObject.getString(GPSConstant.GPS_LOCATION_NAME));
						}
					}
					
				}
				wadd.setVehicle_Odometer(oddmeter.getVehicle_Odometer());
				wadd.setVehicle_ExpectedOdameter(oddmeter.getVehicle_ExpectedOdameter());
				wadd.setvStatusId(oddmeter.getvStatusId());
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(wadd));
		
	}

	// get Inventory List to json
	@RequestMapping(value = "/getInventoryList", method = RequestMethod.GET)
	public void getInventoryList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Inventory> Driver = new ArrayList<Inventory>();
		List<Inventory> inventory = inventoryService.findAllListInventory(userDetails.getCompany_id());
		if (inventory != null && !inventory.isEmpty()) {
			for (Inventory add : inventory) {
				Inventory wadd = new Inventory();
				/* wadd.setDriver_id(add.getDriver_id()); */
				wadd.setInventory_id(add.getInventory_id());
				// wadd.setLocation(add.getLocation());
				// wadd.setPartname(add.getPartname());
				// wadd.setPartnumber(add.getPartnumber());

				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(Driver));
	}

	// Get Search Inventory List to drop down
	@RequestMapping(value = "/getInventorySearchList", method = RequestMethod.POST)
	public void getInventorySearchList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		HashMap<String, Object> configuration = null ;
		HashMap<String, InventoryLocationDto> partHash = new HashMap<>() ;

		List<InventoryLocationDto> inventory = new ArrayList<>();
		List<InventoryLocationDto> location = inventoryService.SearchlistInventoryLocation(term, userDetails.getCompany_id());
		
		configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);

		if((boolean) configuration.getOrDefault("showSubstitutePartsInList", false)) {
			List<InventoryLocationDto> substitudePartList = inventoryService.allpartList(term, userDetails.getCompany_id());
			if(substitudePartList != null && !substitudePartList.isEmpty()) {
				List <InventoryLocationDto> finalSubstitudePartList = inventoryService.preparePartIdsString(substitudePartList,  userDetails.getCompany_id());
				if(location == null) {
					location = new ArrayList<>();
				}
				if(finalSubstitudePartList != null && !finalSubstitudePartList.isEmpty())
					location.addAll(finalSubstitudePartList);
			}
		}
		if (location != null && !location.isEmpty()) {
			for (InventoryLocationDto add : location) {
				InventoryLocationDto wadd = new InventoryLocationDto();

				wadd.setInventory_location_id(add.getInventory_location_id());
				wadd.setLocation(add.getLocation());
				wadd.setLocationId(add.getLocationId());
				wadd.setPartname(add.getPartname());
				wadd.setPartnumber(add.getPartnumber());
				wadd.setLocation_quantity(add.getLocation_quantity());
				wadd.setPartManufacturer(add.getPartManufacturer());
				wadd.setWarrantyApplicable(add.isWarrantyApplicable());
				wadd.setRepairable(add.isRepairable());
				wadd.setPartid(add.getPartid());
				wadd.setLocationId(add.getLocationId());
				wadd.setUnitTypeId(add.getUnitTypeId());
				if(partHash.get(add.getPartid()+""+add.getLocationId()) == null) {
					partHash.put(add.getPartid()+""+add.getLocationId(), wadd);
					}
			}
			inventory.addAll(partHash.values());
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(inventory));
	}
	// Get Search Inventory List to drop down
	@PostMapping(value = "/getInventorySearchListByMainAndSubLocation")
	public void getInventorySearchListByMainAndSubLocation(Map<String, Object> map, @RequestParam("term") final String term,
			@RequestParam("subLocationId") final Integer subLocationId,
			@RequestParam("mainLocationId") final Integer mainLocationId,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails 					userDetails 									= null;
		List<InventoryLocationDto> 			location 										= null;
		HashMap<String, Object> 			configuration 									= null;
		boolean 							showSubLocation	 								= false;
		boolean								showSublocationPartDetailForGivenMainLocation  	= false;
		String								mainLocationIds 								= "";
		String[] 							mainLocationIdsArr 								= null;
		try {
			HashMap<Long, InventoryLocationDto> 		inventoryHM 	= new HashMap<>();
			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			showSubLocation	 	= (boolean)configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_SUB_LOCATION, false);
			mainLocationIds 	= configuration.getOrDefault(WorkOrdersConfigurationConstants.MAIN_LOCATION_IDS, "")+"";
			mainLocationIdsArr 	= mainLocationIds.split(",");
			
			if(showSubLocation && mainLocationIdsArr.length > 0) {
				for(int i=0; i<mainLocationIdsArr.length; i++) {
					if(mainLocationId == Integer.parseInt(mainLocationIdsArr[i])) {
						showSublocationPartDetailForGivenMainLocation = true;
						break;
					}
				}
			}
			
			if(showSublocationPartDetailForGivenMainLocation) {
				location = inventoryService.searchlistInventorySubLocation(term, subLocationId, userDetails.getCompany_id());
			}else{
				location = inventoryService.SearchlistInventoryLocation(term, userDetails.getCompany_id());
			}
			
			if (location != null && !location.isEmpty()) {
				for (InventoryLocationDto add : location) {
					InventoryLocationDto wadd = new InventoryLocationDto();
					if(inventoryHM.containsKey(add.getInventory_location_id())) {
						wadd.setLocation_quantity(add.getLocation_quantity()+inventoryHM.get(add.getInventory_location_id()).getLocation_quantity());
						wadd.setInventory_location_id(add.getInventory_location_id());
						wadd.setLocation(add.getLocation());
						wadd.setLocationId(add.getLocationId());
						wadd.setPartname(add.getPartname());
						wadd.setPartnumber(add.getPartnumber());
						wadd.setPartManufacturer(add.getPartManufacturer());
						inventoryHM.put(add.getInventory_location_id(), wadd);
					}else {
						wadd.setInventory_location_id(add.getInventory_location_id());
						wadd.setLocation(add.getLocation());
						wadd.setLocationId(add.getLocationId());
						wadd.setPartname(add.getPartname());
						wadd.setPartnumber(add.getPartnumber());
						wadd.setLocation_quantity(add.getLocation_quantity());
						wadd.setPartManufacturer(add.getPartManufacturer());
						inventoryHM.put(add.getInventory_location_id(), wadd);
					}
					
				}
				
			}
			
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(inventoryHM.values()));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	// get Inventory Quantity List to json
	@RequestMapping(value = "/getInventoryQuantityList", method = RequestMethod.GET)
	public void getInventoryQuantityList(@RequestParam(value = "inventoryID", required = true) Long inventoryID,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		InventoryLocation InventoryQuantity = inventoryService.getInventoryLocation(inventoryID);
		Inventory wadd = new Inventory();
		if (InventoryQuantity != null) {
			wadd.setQuantity(InventoryQuantity.getLocation_quantity());
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(wadd));
	}

	// get Inventory Quantity List to json
	@RequestMapping(value = "/getlastworkorderservice", method = RequestMethod.GET)
	public void getLastWorkOrderServicepart(@RequestParam(value = "jobtask", required = true) Integer jobType,
			@RequestParam(value = "jobsubtask", required = true) Integer jobsubtype,
			@RequestParam(value = "vehicle_id", required = true) Integer vehicle_id,
			@RequestParam(value = "workorders_id", required = true) Long workorders_id, Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		List<WorkOrdersTasksDto> workordertask = workOrdersService.getLast_WorkOrdersTasks(jobType, jobsubtype,	vehicle_id, workorders_id, userDetails.getCompany_id());

		if (workordertask != null && !workordertask.isEmpty()) {
			for (WorkOrdersTasksDto workOrdersTasks : workordertask) {

				WorkOrders lastworkOrders = workOrdersService.getWorkOrders(workOrdersTasks.getWorkorders_id());

				WorkOrdersDto wadd = new WorkOrdersDto();

				wadd.setStart_date_D(lastworkOrders.getStart_date());
				wadd.setVehicle_Odometer(lastworkOrders.getVehicle_Odometer());

				// get Current days
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

				if (lastworkOrders.getStart_date() != null) {

					int diffInDays = (int) ((lastworkOrders.getDue_date().getTime() - toDate.getTime())
							/ (1000 * 60 * 60 * 24));

					String diffenceDays = null;

					switch (diffInDays) {
					case 0:
						diffenceDays = ("Today");
						break;
					case -1:
						diffenceDays = ("YesterDay");
						break;
					case 1:
						diffenceDays = ("Tomorrow");
						break;
					default:
						if (diffInDays < -1) {
							long days = -diffInDays;
							if (days >= 365) {
								diffenceDays = (days / 365 + " years & " + (days % 365) + " months ago");
							} else if (days > 30) {
								diffenceDays = (days / 30 + " months & " + days % 30 + " days ago");
							} else
								diffenceDays = (-diffInDays + " days ago");
						} else if (diffInDays > 1) {
							if (diffInDays >= 365) {
								diffenceDays = (diffInDays / 365 + " years & " + (diffInDays % 365)
										+ " months from now");
							} else if (diffInDays > 30) {
								diffenceDays = (diffInDays / 30 + " months & " + diffInDays % 30 + " days from now");
							} else
								diffenceDays = (diffInDays + " days from now");
						}
						break;
					}

					wadd.setPriority(diffenceDays);
				}

				Integer workorderOdometer = 0;
				if (lastworkOrders.getVehicle_Odometer() != null) {
					workorderOdometer = lastworkOrders.getVehicle_Odometer();

					Integer Current_vehicleOdmeter = 0;
					if (vehicle_id != null) {
						Current_vehicleOdmeter = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(vehicle_id);
					}

					Integer diff_Odmeter = workorderOdometer - Current_vehicleOdmeter;

					String diffenceOdometer = null;

					switch (diff_Odmeter) {
					case 0:
						diffenceOdometer = ("Current Km Today");
						break;
					case -1:
						diffenceOdometer = (-diff_Odmeter + " Km ago");
						break;
					case 1:
						diffenceOdometer = (diff_Odmeter + " km from now");
						break;
					default:
						if (diff_Odmeter < -1) {

							diffenceOdometer = (-diff_Odmeter + " Km ago");

						} else if (diff_Odmeter > 1) {

							diffenceOdometer = (diff_Odmeter + " km from now");
						}
						break;
					}

					wadd.setWorkorders_status(diffenceOdometer);
				}
				wadd.setAssignee("WorkOrders");
				wadd.setWorkorders_id(lastworkOrders.getWorkorders_id());
				Gson gson = new Gson();

				response.getWriter().write(gson.toJson(wadd));

				break;
			}
		} else {
			// WorkOrder is Null meaning Search Service Enteries
			List<ServiceEntriesTasks> ServiceEntriestask = serviceEntriesService.getLast_ServiceEntriesTasks(jobType,
					jobsubtype, vehicle_id, userDetails.getCompany_id());

			if (ServiceEntriestask != null && !ServiceEntriestask.isEmpty()) {
				for (ServiceEntriesTasks serviceTasks : ServiceEntriestask) {

					ServiceEntries lastServiceEntries = serviceEntriesService
							.getServiceEntries(serviceTasks.getServiceEntries().getServiceEntries_id());

					WorkOrdersDto wadd = new WorkOrdersDto();

					wadd.setStart_date_D(lastServiceEntries.getCreated());
					wadd.setVehicle_Odometer(lastServiceEntries.getVehicle_Odometer());

					// get Current days
					java.util.Date currentDate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

					if (lastServiceEntries.getCreated() != null) {

						int diffInDays = (int) ((lastServiceEntries.getCreated().getTime() - toDate.getTime())
								/ (1000 * 60 * 60 * 24));

						String diffenceDays = null;

						switch (diffInDays) {
						case 0:
							diffenceDays = ("Today");
							break;
						case -1:
							diffenceDays = ("YesterDay");
							break;
						case 1:
							diffenceDays = ("Tomorrow");
							break;
						default:
							if (diffInDays < -1) {
								long days = -diffInDays;
								if (days >= 365) {
									diffenceDays = (days / 365 + " years & " + (days % 365) + " months ago");
								} else if (days > 30) {
									diffenceDays = (days / 30 + " months & " + days % 30 + " days ago");
								} else
									diffenceDays = (-diffInDays + " days ago");
							} else if (diffInDays > 1) {
								if (diffInDays >= 365) {
									diffenceDays = (diffInDays / 365 + " years & " + (diffInDays % 365)
											+ " months from now");
								} else if (diffInDays > 30) {
									diffenceDays = (diffInDays / 30 + " months & " + diffInDays % 30
											+ " days from now");
								} else
									diffenceDays = (diffInDays + " days from now");
							}
							break;
						}

						wadd.setPriority(diffenceDays);
					}

					Integer workorderOdometer = 0;
					if (lastServiceEntries.getVehicle_Odometer() != null) {
						workorderOdometer = lastServiceEntries.getVehicle_Odometer();

						Integer Current_vehicleOdmeter = 0;
						if (vehicle_id != null) {
							Current_vehicleOdmeter = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(vehicle_id);
						}

						Integer diff_Odmeter = workorderOdometer - Current_vehicleOdmeter;

						String diffenceOdometer = null;

						switch (diff_Odmeter) {
						case 0:
							diffenceOdometer = ("Current Km Today");
							break;
						case -1:
							diffenceOdometer = (-diff_Odmeter + " Km ago");
							break;
						case 1:
							diffenceOdometer = (diff_Odmeter + " km from now");
							break;
						default:
							if (diff_Odmeter < -1) {

								diffenceOdometer = (-diff_Odmeter + " Km ago");

							} else if (diff_Odmeter > 1) {

								diffenceOdometer = (diff_Odmeter + " km from now");
							}
							break;
						}

						wadd.setWorkorders_status(diffenceOdometer);
					}
					wadd.setAssignee("ServiceEntries");
					wadd.setWorkorders_id(lastServiceEntries.getServiceEntries_id());
					Gson gson = new Gson();
					// gson.toJson(wadd));

					response.getWriter().write(gson.toJson(wadd));

					break;
				}
			} else {

				WorkOrders wadd = new WorkOrders();

				Gson gson = new Gson();

				response.getWriter().write(gson.toJson(wadd));
			}
		}

	}

	@RequestMapping(value = "/WorkOrdersINPROCESS/{pageNumber}", method = RequestMethod.GET)
	public String newWorkOrdersINPROCESS(@PathVariable Integer pageNumber, Model model) throws Exception {
		short 						WorkOrders_open 		= WorkOrdersType.WORKORDERS_STATUS_INPROCESS;
		CustomUserDetails 			userDetails 			= null;
		HashMap<String, Object>  	configuration			= null;
		List<WorkOrdersDto> 		pageList 				= null; 
		
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);

			Page<WorkOrders> page = workOrdersService.getDeployment_Page_WorkOrders(WorkOrders_open, pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("WorkOrderCount", page.getTotalElements());
			
			if((boolean) configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_START_AND_DUE_TIME_FIELD, false)) {
				pageList = workOrdersBL.prepareWorkOrdersList(workOrdersService.listOpenWorkOrders(WorkOrders_open, pageNumber, userDetails));				
			} else {
				pageList = workOrdersBL.prepareListWorkOrders(workOrdersService.listOpenWorkOrders(WorkOrders_open, pageNumber, userDetails));
			}

			model.addAttribute("WorkOrder", pageList);
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("newWorkOrders Page:", e);
			e.printStackTrace();
		}
		return "inprocessWorkOrders";
	}

	// show main page of WorkOrders ON-HOLD
	@RequestMapping(value = "/WorkOrdersONHOLD/{pageNumber}", method = RequestMethod.GET)
	public String newWorkOrdersONHOLD(@PathVariable Integer pageNumber, Model model) throws Exception {
		short 						WorkOrders_open 		= WorkOrdersType.WORKORDERS_STATUS_ONHOLD;
		CustomUserDetails 			userDetails 			= null;
		HashMap<String, Object>  	configuration			= null;
		List<WorkOrdersDto> 		pageList 				= null; 
		
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			
			Page<WorkOrders> page = workOrdersService.getDeployment_Page_WorkOrders(WorkOrders_open, pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("WorkOrderCount", page.getTotalElements());
			
			if((boolean) configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_START_AND_DUE_TIME_FIELD, false)) {
				pageList = workOrdersBL.prepareWorkOrdersList(workOrdersService.listOpenWorkOrders(WorkOrders_open, pageNumber, userDetails));				
			} else {
				pageList = workOrdersBL.prepareListWorkOrders(workOrdersService.listOpenWorkOrders(WorkOrders_open, pageNumber, userDetails));
			}

			model.addAttribute("WorkOrder", pageList);
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("newWorkOrders Page:", e);
			e.printStackTrace();
		}
		return "onholdWorkOrders";
	}

	// show main page of WorkOrders ON-HOLD
	@RequestMapping(value = "/WorkOrdersCOMPLETE/{pageNumber}", method = RequestMethod.GET)
	public String newWorkOrdersCOMPLETE(@PathVariable Integer pageNumber, Model model) throws Exception {
		short 						WorkOrders_open 		= WorkOrdersType.WORKORDERS_STATUS_COMPLETE;
		CustomUserDetails 			userDetails 			= null;
		HashMap<String, Object>  	configuration			= null;
		List<WorkOrdersDto> 		pageList 				= null; 
		
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			
			Page<WorkOrders> page = workOrdersService.getDeployment_Page_WorkOrders(WorkOrders_open, pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("WorkOrderCount", page.getTotalElements());
			
			if((boolean) configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_START_AND_DUE_TIME_FIELD, false)) {
				pageList = workOrdersBL.prepareWorkOrdersList(workOrdersService.listOpenWorkOrders(WorkOrders_open, pageNumber, userDetails));				
			} else {
				pageList = workOrdersBL.prepareListWorkOrders(workOrdersService.listOpenWorkOrders(WorkOrders_open, pageNumber, userDetails));
			}

			model.addAttribute("WorkOrder", pageList);

			// model.put("StatuesCount",
			// workOrdersService.countWorkOrderStatues(WorkOrders_open));
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("newWorkOrders Page:", e);
			e.printStackTrace();
		}
		return "closedWorkOrders";
	}

	// get to save in prepareWorkOrders
	public WorkOrders prepareWorkOrders(WorkOrdersDto workOrders) throws Exception {

		WorkOrders work = new WorkOrders();

		work.setWorkorders_id(workOrders.getWorkorders_id());
		work.setWorkorders_Number(workOrders.getWorkorders_Number());

		work.setVehicle_vid(workOrders.getVehicle_vid());

		work.setVehicle_Odometer_old(workOrders.getVehicle_Odometer_old());

		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (workOrders.getStart_date() != null) {
				String start_time = "00:00";
				if(workOrders.getStart_time() != null && workOrders.getStart_time() != "") {
					start_time	= workOrders.getStart_time();
				}
				java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(workOrders.getStart_date(), start_time);
				java.sql.Date start_date = new java.sql.Date(date.getTime());
				work.setStart_date(start_date);
			}
			if (workOrders.getDue_date() != null) {
				String end_time = "00:00";
				if(workOrders.getDue_time() != null && workOrders.getDue_time() != "") {
					end_time	= workOrders.getDue_time();
				}
				java.util.Date date2 = DateTimeUtility.getDateTimeFromDateTimeString(workOrders.getDue_date(), end_time);
				java.sql.Date due_date = new java.sql.Date(date2.getTime());
				work.setDue_date(due_date);
			}
			if(workOrders.getTallyCompanyId() != null) {
				work.setTallyCompanyId(workOrders.getTallyCompanyId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (workOrders.getWorkorders_driver_id() != null && workOrders.getWorkorders_driver_id() != 0) {
			work.setWorkorders_driver_id(workOrders.getWorkorders_driver_id());
		} else {
			work.setWorkorders_driver_id(0);
		}

		if (workOrders.getWorkorders_driver_number() != null && workOrders.getWorkorders_driver_number() != "") {
			work.setWorkorders_driver_number(workOrders.getWorkorders_driver_number());
		} else {
			work.setWorkorders_driver_number("");
		}
		if (workOrders.getOut_work_station() != null && workOrders.getOut_work_station() != "") {
			work.setOut_work_station(workOrders.getOut_work_station());
		} else {
			work.setOut_work_station("");
		}
		if (workOrders.getWorkorders_route() != null && workOrders.getWorkorders_route() != "") {
			work.setWorkorders_route(workOrders.getWorkorders_route());
		} else {
			work.setWorkorders_route("");
		}
		if (workOrders.getWorkorders_diesel() != null && workOrders.getWorkorders_diesel() > 0) {
			work.setWorkorders_diesel(workOrders.getWorkorders_diesel());
		} else {
			work.setWorkorders_diesel((long) 0);
		}
		
		work.setAssigneeId(workOrders.getAssigneeId());

		work.setWorkorders_location_ID(workOrders.getWorkorders_location_ID());
		work.setVehicle_Odometer(workOrders.getVehicle_Odometer());
		work.setIndentno(workOrders.getIndentno());
		work.setPriorityId(workOrders.getPriorityId());
		work.setInitial_note(workOrders.getInitial_note());

		work.setWorkorders_document(false);
		work.setWorkorders_document_id(WORK_ORDER_DEFALAT_VALUE);

		work.setISSUES_ID(WORK_ORDER_DEFALAT_VALUE);

		work.setCreatedById(userDetails.getId());
		work.setLastModifiedById(userDetails.getId());
		work.setCompanyId(userDetails.getCompany_id());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		work.setCreated(toDate);
		work.setLastupdated(toDate);
		work.setMarkForDelete(false);
		work.setGpsOdometer(workOrders.getGpsOdometer());
		work.setGpsWorkLocation(workOrders.getGpsWorkLocation());
		if(workOrders.getTallyCompanyId() != null) {
			work.setTallyCompanyId(workOrders.getTallyCompanyId());
		}

		return work;
	}

	@RequestMapping(value = "/uploadWorkOrderDocument", method = RequestMethod.POST)
	public ModelAndView uploadServiceDocument(@ModelAttribute("command") WorkOrdersDocument workordersDocument,
			@RequestParam("input-file-preview") MultipartFile file) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			org.fleetopgroup.persistence.document.WorkOrdersDocument workDocument = new org.fleetopgroup.persistence.document.WorkOrdersDocument();
			try {
				workDocument.setWorkorder_id(workordersDocument.getWorkorder_id());

				byte[] bytes = file.getBytes();
				workDocument.setWorkorder_filename(file.getOriginalFilename());
				workDocument.setWorkorder_content(bytes);
				workDocument.setWorkorder_contentType(file.getContentType());
				workDocument.setCompanyId(userDetails.getCompany_id());

				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				workDocument.setCreated(toDate);
				workDocument.setLastupdated(toDate);
			} catch (IOException e) {
				e.printStackTrace();
			}

			org.fleetopgroup.persistence.document.WorkOrdersDocument doc = workOrdersService.getWorkOrdersDocument(workordersDocument.getWorkorder_id(),
					userDetails.getCompany_id());
			if (doc != null) {
				workDocument.set_id(doc.get_id());
				workOrdersService.updateOldWorkOrdersDocument(workDocument);

				// Note: WorkOrder_ Document update available TRUE
				workOrdersService.Update_WorkOrdre_Document_Available_TRUE(workDocument.get_id(), true,
						workordersDocument.getWorkorder_id(), userDetails.getCompany_id());
			} else {
				workOrdersService.uploadWorkOrdersDocument(workDocument);

				// Note: WorkOrder_ Document update available TRUE
				workOrdersService.Update_WorkOrdre_Document_Available_TRUE(workDocument.get_id(), true,
						workordersDocument.getWorkorder_id(), userDetails.getCompany_id());
			}

			model.put("UploadSuccess", true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/viewWorkOrder.in?danger=true");
		}

		return new ModelAndView("redirect:/showWorkOrder?woId=" + workordersDocument.getWorkorder_id() + "", model);

	}

	// Should be Download WorkOrder Document
	@RequestMapping("/download/workorderDocument/{workorders_id}")
	public String downloadReminder(@PathVariable("workorders_id") Long workorder_id,
			HttpServletResponse response) throws Exception {
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			if (workorder_id == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				org.fleetopgroup.persistence.document.WorkOrdersDocument file = workOrdersService.getWorkOrdersDocument(workorder_id,
						userDetails.getCompany_id());

				if (file != null) {
					if (file.getWorkorder_content() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getWorkorder_filename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getWorkorder_contentType());
						response.getOutputStream().write(file.getWorkorder_content());
						out.flush();
						out.close();

					}
				}
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {

		}
		return null;
	}

	// Delete to WorkOrders
	@RequestMapping(value = "/deleteWorkOrder", method = RequestMethod.GET)
	public ModelAndView deleteWorkOrderTask(@RequestParam("workorders_id") final Long workorders_id,
			@RequestParam("vid") final Integer vehicle_id, final HttpServletRequest request) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<WorkOrdersTasksDto> WorkOrdertask = workOrdersService.getWorkOrdersTasks(workorders_id,
					userDetails.getCompany_id());
			if (WorkOrdertask != null && !WorkOrdertask.isEmpty()) {

				return new ModelAndView("redirect:/viewWorkOrder.in?deleteAllTask=true");

			} else {

				// Note : When Vehicle Created Work Order That time Vehicle
				// Status update to 'WORKSHOP'
				vehicleService.Update_Vehicle_Status_TripSheetID(TRIP_SHEET_DEFALAT_VALUE, vehicle_id, 
						userDetails.getCompany_id(), VehicleStatusConstant.VEHICLE_STATUS_ACTIVE);
				
				
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
				workOrdersService.deleteWorkOrders(workorders_id, userDetails.getId(),toDate,userDetails.getCompany_id());

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/viewWorkOrder.in?danger=true");
		}

		return new ModelAndView("redirect:/viewWorkOrder.in?deleteSuccess=true");
	}

	// Delete to WorkOrders Task
	@RequestMapping(value = "/deleteWorkOrderTask", method = RequestMethod.GET)
	public ModelAndView deleteWorkOrderTask(@RequestParam("workordertaskid") final Long Workordertaskid,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		WorkOrders work = workOrdersService.getLast_WorkOrdersTaskID_To_WorkOrders_details(Workordertaskid,
				userDetails.getCompany_id());
		try {
			if(work != null) {
				
				List<PartLocationPermissionDto> PartLocPermission = partLocationsBL.prepareListofPartLocation(partLocationPermissionService
						.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));
				/** Set Created by email Id */
				if (inventoryBL.isFilled_GET_Permission(work.getWorkorders_location_ID(), PartLocPermission)) {
					
					WorkOrdersTasks WORKTASK = workOrdersService.getWorkOrdersTask(Workordertaskid,
							userDetails.getCompany_id());
					
					List<WorkOrdersTasksToParts> WorkOrdertasktoParts = workOrdersService
							.getWorkOrdersTasksToParts_ID(Workordertaskid, userDetails.getCompany_id());
					if (WorkOrdertasktoParts != null && !WorkOrdertasktoParts.isEmpty()) {
						
						model.put("deleteFristParts", true);
						
						return new ModelAndView("redirect:/showWorkOrder?woId=" + work.getWorkorders_id() + "", model);
					} else {
						
						List<WorkOrdersTasksToLabor> WorkOrdertasktoLabor = workOrdersService
								.getWorkOrdersTasksToLabor_ID(Workordertaskid, userDetails.getCompany_id());
						if (WorkOrdertasktoLabor != null && !WorkOrdertasktoLabor.isEmpty()) {
							
							model.put("deleteFristParts", true);
							
							return new ModelAndView("redirect:/showWorkOrder?woId=" + work.getWorkorders_id() + "",
									model);
						} else {
							
							workOrdersService.deleteWorkOrdersTask(WORKTASK.getWorkordertaskid(),
									userDetails.getCompany_id());
							
							// Update Main WorkOrder Total cost
							try {
								
								workOrdersService.updateWorkOrderMainTotalCost(WORKTASK.getWorkorders().getWorkorders_id());
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					
				} else {
					model.put("NoAuthen", true);
					return new ModelAndView("redirect:/showWorkOrder?woId=" + work.getWorkorders_id() + "", model);
				}
			}else {
				return new ModelAndView("redirect:/viewWorkOrder.in?alreadyDeleted=true");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/viewWorkOrder.in?danger=true");
		}

		return new ModelAndView("redirect:/showWorkOrder?woId=" + work.getWorkorders_id() + "", model);

	}

	// Delete to WorkOrders Task_To_Parts
	@RequestMapping(value = "/deleteWorkOrderTaskToPart", method = RequestMethod.GET)
	public ModelAndView deleteWorkOrderTaskToPart(
			@RequestParam("workordertaskto_partid") final Long Workordertaskto_partid, final HttpServletRequest request)
			throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		Long Workorders_id = null;
		CustomUserDetails userDetails = null;
		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<PartLocationPermissionDto> PartLocPermission = partLocationsBL.prepareListofPartLocation(partLocationPermissionService
					.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

			WorkOrders work = workOrdersService.getLast_WorkOrdersTasktoPARTID_To_WorkOrders_details(
					Workordertaskto_partid, userDetails.getCompany_id());
			/** Set Created by email Id */
			if (inventoryBL.isFilled_GET_Permission(work.getWorkorders_location_ID(), PartLocPermission)) {

				WorkOrdersTasksToParts WORKTASKTOPARTS = workOrdersService
						.getWorkOrdersTaskToParts_ONLY_ID(Workordertaskto_partid, userDetails.getCompany_id());

				try {
					InventoryDto inventoryParts = inventoryService.getInventory(WORKTASKTOPARTS.getInventory_id());
					if (WORKTASKTOPARTS.getQuantity() != null) {

						Double workOrdersQuantity = WORKTASKTOPARTS.getQuantity();
						// Add in Inventory Quantity So Assign
						// workOrdersQuantity
						inventoryService.Add_update_Inventory_from_Workorder(workOrdersQuantity,
								inventoryParts.getInventory_id(), userDetails.getCompany_id());

						// Add in InventoryLocation Quantity So Assign
						// workOrdersQuantity
						inventoryService.Add_update_InventoryLocation_from_Workorder(workOrdersQuantity,
								inventoryParts.getInventory_location_id(), userDetails.getCompany_id());

						// Add in InventoryAll Quantity So Assign
						// workOrdersQuantity
						inventoryService.Add_update_InventoryAll_from_Workorder(workOrdersQuantity,
								inventoryParts.getInventory_all_id(), userDetails.getCompany_id());

					}

				} catch (Exception e) {

					e.printStackTrace();
					return new ModelAndView("redirect:/viewWorkOrder.in?danger=true");
				}

				workOrdersService.deleteWorkOrdersTaskTOParts(WORKTASKTOPARTS.getWorkordertaskto_partid(),
						userDetails.getCompany_id());

				try {

					workOrdersService.updateWorkOrdersTask_TotalPartCost(WORKTASKTOPARTS.getWorkordertaskid(),
							userDetails.getCompany_id());

					// Update Main WorkOrder Total cost
					try {

						workOrdersService.updateWorkOrderMainTotalCost(WORKTASKTOPARTS.getWorkorders_id());

					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView("redirect:/viewWorkOrder.in?danger=true");
					}

				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/viewWorkOrder.in?danger=true");
				}

				Workorders_id = WORKTASKTOPARTS.getWorkorders_id();

			} else {
				model.put("NoAuthen", true);
				return new ModelAndView("redirect:/showWorkOrder?woId=" + work.getWorkorders_id() + "", model);
			}

		} catch (Exception e) {
			return new ModelAndView("redirect:/viewWorkOrder.in?danger=true");
		}

		return new ModelAndView("redirect:/showWorkOrder?woId=" + Workorders_id + "", model);
	}

	// Delete to WorkOrders Task_To_Labor
	@RequestMapping(value = "/deleteWorkOrderTaskToLabor", method = RequestMethod.GET)
	public ModelAndView deleteWorkOrderTaskToLabor(
			@ModelAttribute("command") WorkOrdersTasksToLabor workOrdersTaskToLabor, final HttpServletRequest request)
			throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		Long Workorders_id = null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			WorkOrdersTasksToLabor WORKTASKTOLabor = workOrdersService.getWorkOrdersTaskToLabor_ONLY_ID(
					workOrdersTaskToLabor.getWorkordertaskto_laberid(), userDetails.getCompany_id());

			try {

				workOrdersService.deleteWorkOrdersTaskTOLabor(WORKTASKTOLabor.getWorkordertaskto_laberid(),
						userDetails.getCompany_id());

				workOrdersService.updateWorkOrdersTask_TotalLaborCost(WORKTASKTOLabor.getWorkordertaskid(),
						userDetails.getCompany_id());

				// Update Main WorkOrder Total cost
				try {

					workOrdersService.updateWorkOrderMainTotalCost(WORKTASKTOLabor.getWorkorders_id());

				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/viewWorkOrder.in?danger=true");
				}

			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("redirect:/viewWorkOrder.in?danger=true");
			}

			Workorders_id = WORKTASKTOLabor.getWorkorders_id();

		} catch (Exception e) {
			return new ModelAndView("redirect:/viewWorkOrder.in?danger=true");
		}

		return new ModelAndView("redirect:/showWorkOrder?woId=" + Workorders_id + "", model);
	}

	/***************************************************************************************************************************************
	 ************** Get Vehicle List drop down less Loading to Search
	 ***************************************************************************************************************************************/

	@RequestMapping(value = "/getVehicleSearchWorkOrder", method = RequestMethod.POST)
	public void getVehicleList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<VehicleDto> addresses = new ArrayList<VehicleDto>();
		VehicleDto wadd = null;
		List<Vehicle> DateVehicle = vehicleService.SearchOnlyVehicleNAME(term);
		if (DateVehicle != null && !DateVehicle.isEmpty()) {
			for (Vehicle add : DateVehicle) {
				wadd = new VehicleDto();

				wadd.setVid(add.getVid());
				wadd.setVehicle_registration(add.getVehicle_registration());
				addresses.add(wadd);

			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(addresses));
	}

	@RequestMapping(value = "/getDriverSearchListWorkOrder", method = RequestMethod.POST)
	public void getDriverSearchListFuel(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<DriverDto> Driver = new ArrayList<DriverDto>();
		List<Driver> Drivertype = driverService.SearchOnlyDriverNAME(term);
		if (Drivertype != null && !Drivertype.isEmpty()) {
			for (Driver add : Drivertype) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_firstname(add.getDriver_firstname());
				wadd.setDriver_Lastname(add.getDriver_Lastname());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				wadd.setDriver_mobnumber(add.getDriver_mobnumber());
				wadd.setDriver_fathername(add.getDriver_fathername());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(Driver));
	}

	@RequestMapping(value = "/getTechinicianWorkOrder", method = RequestMethod.POST)
	public void getTechinicianWorkOrder(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<DriverDto> Driver = new ArrayList<DriverDto>();
		List<Driver> Drivertype = driverService.SearchOnly_Techinicion_NAME(term, userDetails.getCompany_id());
		if (Drivertype != null && !Drivertype.isEmpty()) {
			for (Driver add : Drivertype) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_firstname(add.getDriver_firstname());
				wadd.setDriver_Lastname(add.getDriver_Lastname());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(Driver));
	}

	@RequestMapping(value = "/getJobTypeSearchListWorkOrder", method = RequestMethod.POST)
	public void getJobTypeSearchListWorkOrder(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<JobType> JOB = new ArrayList<JobType>();
		List<JobType> JOBtype = jobTypeService.SearchOnlyJobType(term, userDetails.getCompany_id());
		if (JOBtype != null && !JOBtype.isEmpty()) {
			for (JobType add : JOBtype) {
				JobType wadd = new JobType();
				// wadd.setJob_id(add.getJob_id());
				wadd.setJob_Type(add.getJob_Type());
				JOB.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(JOB));
	}

	/*****************************************************************************************************
	 ***************** Vehicle To Service Remider Details **********************
	 *****************************************************************************************************/

	@RequestMapping(value = "/VehicleWorkOrderDetails", method = RequestMethod.GET)
	public ModelAndView VehicleServiceDetails(@ModelAttribute("command") Vehicle vehicle_id,
			ServiceReminderDto serviceReminderDto, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VehicleDto vehicle = vehicleBL
					.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(vehicle_id.getVid()));
			model.put("vehicle", vehicle);
			// show the driver list in all

			model.put("WorkOrder", workOrdersBL.prepareListWorkOrders(
					workOrdersService.VehicleToWorkOrdersList(vehicle_id.getVid(), userDetails.getCompany_id())));

			Object[] count = vehicleService.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(vehicle_id.getVid());
			model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(vehicle_id.getVid(), userDetails.getCompany_id()));
			model.put("photo_Count", (Long) count[0]);
			model.put("purchase_Count", (Long) count[1]);
			model.put("reminder_Count", (Long) count[2]);
			model.put("fuelEntrie_Count", (Long) count[3]);
			model.put("serviceEntrie_Count", (Long) count[4]);
			model.put("serviceReminder_Count", (Long) count[5]);
			model.put("tripsheet_Count", (Long) count[6]);
			model.put("workOrder_Count", (Long) count[7]);
			model.put("issues_Count", (Long) count[8]);
			model.put("odometerhis_Count", (Long) count[9]);
			model.put("comment_Count", (Long) count[10]);
			model.put("breakDownCount", ((Long) count[4]+(Long) count[8]));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Work Order:", e);
		}

		return new ModelAndView("Show_Vehicle_WorkOrder", model);
	}

	@RequestMapping(value = "/VehicleWorkOrderDetails/{VehicleId}/{pageNumber}", method = RequestMethod.GET)
	public String VehicleWorkOrderDetails(@PathVariable("VehicleId") Integer vehicle_id,
			@PathVariable("pageNumber") Integer pageNumber, Model model) throws Exception {

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Page<WorkOrders> page = workOrdersService.getDeployment_Page_WorkOrders_VehicleId(vehicle_id, pageNumber,
					userDetails.getCompany_id());
			VehicleDto vehicle = vehicleBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(vehicle_id));
			model.addAttribute("vehicle", vehicle);

			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("WorkOrderCount", page.getTotalElements());

			List<WorkOrdersDto> pageList = workOrdersBL.prepareListWorkOrders(workOrdersService
					.listOpenWorkOrders_VehicleId(vehicle_id, pageNumber, userDetails.getCompany_id()));

			model.addAttribute("WorkOrder", pageList);

			Object[] count = vehicleService.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(vehicle_id);
			model.addAttribute("document_Count", vehicleDocumentService.getVehicleDocumentCount(vehicle_id, userDetails.getCompany_id()));
			model.addAttribute("photo_Count", (Long) count[0]);
			model.addAttribute("purchase_Count", (Long) count[1]);
			model.addAttribute("reminder_Count", (Long) count[2]);
			model.addAttribute("fuelEntrie_Count", (Long) count[3]);
			model.addAttribute("serviceEntrie_Count", (Long) count[4]);
			model.addAttribute("serviceReminder_Count", (Long) count[5]);
			model.addAttribute("tripsheet_Count", (Long) count[6]);
			model.addAttribute("workOrder_Count", (Long) count[7]);
			model.addAttribute("issues_Count", (Long) count[8]);
			model.addAttribute("odometerhis_Count", (Long) count[9]);
			model.addAttribute("comment_Count", (Long) count[10]);
			model.addAttribute("breakDownCount", ((Long) count[4]+(Long) count[8]));
			model.addAttribute("dseCount", (Long) count[12]);

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("newWorkOrders Page:", e);
			e.printStackTrace();
		}
		return "Show_Vehicle_WorkOrder";
	}

	@RequestMapping(value = "/getJobSubTypeRTOCost", method = RequestMethod.GET)
	public void getJobSubTypeRTOCost(@RequestParam(value = "JobSUBTypeID") Integer job_SubtypeID,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (job_SubtypeID != null) {
			JobSubType add = jobSubTypeService.getJobSubType(job_SubtypeID);
			JobSubType wadd = new JobSubType();
			if(add != null) {
				
				wadd.setJob_Subid(add.getJob_Subid());
				if(add.getJob_ROT_hour()!=null) {
					wadd.setJob_ROT_hour(add.getJob_ROT_hour());
				}else {
					wadd.setJob_ROT_hour("0");
				}
				if(add.getJob_ROT_amount() != null) {
					wadd.setJob_ROT_amount(add.getJob_ROT_amount());
				}else {
					wadd.setJob_ROT_amount("0");
				}
			}	
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(wadd));
		}
	}

	@RequestMapping(value = "/getJobTypeWorkOrder", method = RequestMethod.GET)
	public void getJobTypeWorkOrder(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<JobType> job = new ArrayList<JobType>();
		List<JobType> jobType = jobTypeService.SearchOnlyJobType(term, userDetails.getCompany_id());
		if (jobType != null && !jobType.isEmpty()) {
			for (JobType add : jobType) {
				JobType wadd = new JobType();
				wadd.setJob_Type(add.getJob_Type());
				wadd.setJob_id(add.getJob_id());
				job.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(job));
	}

	@RequestMapping(value = "/getJobSubTypeWorkOrder", method = RequestMethod.GET)
	public void getJobSubTypeWorkOrder(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		List<JobSubType> subJob = new ArrayList<JobSubType>();
		List<JobSubType> subJobtype = jobSubTypeService.SearchJobSubType_ROT(term, userDetails.getCompany_id());
		if (subJobtype != null && !subJobtype.isEmpty()) {
			for (JobSubType add : subJobtype) {

				JobSubType wadd = new JobSubType();

				wadd.setJob_Subid(add.getJob_Subid());
				wadd.setJob_ROT(add.getJob_ROT());
				if(add.getJob_ROT_number() != null) {
					wadd.setJob_ROT_number(add.getJob_ROT_number());
				}else {
					wadd.setJob_ROT_number("--");
				}
				

				subJob.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(subJob));
	}

	@RequestMapping(value = "/getJobSubTypeChangeWorkOrder", method = RequestMethod.GET)
	public void getFuelVehicleOdoMerete(@RequestParam(value = "JobType", required = true) Integer job_type,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<JobSubType> subJob = new ArrayList<JobSubType>();
		List<JobSubTypeDto> subJobtype = jobSubTypeService.SearchOnlyJobSubType(job_type, userDetails.getCompany_id());
		if (subJobtype != null && !subJobtype.isEmpty()) {
			for (JobSubTypeDto add : subJobtype) {

				JobSubType wadd = new JobSubType();

				wadd.setJob_Subid(add.getJob_Subid());
				if(add.getJob_Type() != null)
				wadd.setJob_Type(add.getJob_Type().trim());
				wadd.setJob_TypeId(add.getJob_TypeId());
				if(add.getJob_ROT() != null)
				wadd.setJob_ROT(add.getJob_ROT().trim());
				if(add.getJob_ROT_number() != null) {
					wadd.setJob_ROT_number(add.getJob_ROT_number().trim());
					wadd.setJob_ROT_hour(add.getJob_ROT_hour());
					wadd.setJob_ROT_amount(add.getJob_ROT_amount());
				}else {
					wadd.setJob_ROT_number("");
					wadd.setJob_ROT_hour("");
					wadd.setJob_ROT_amount("");
				}

				subJob.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(subJob));
	}
	
	@RequestMapping(value = "/getPartCategoriesList", method = RequestMethod.GET)
	public void getPartCategoriesList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		List<PartCategories> partCat = new ArrayList<PartCategories>();
		
		List<PartCategories> partCategory = partCategoriesService.getPartCategoryList(userDetails.getCompany_id());
		if (partCategory != null && !partCategory.isEmpty()) {
			for (PartCategories add : partCategory) {
				PartCategories wadd = new PartCategories();
				wadd.setPcid(add.getPcid());
				wadd.setPcName(add.getPcName());
				partCat.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(partCat));
	}
	

	@RequestMapping(value = "/getJobSubTypeChangeOnlyID", method = RequestMethod.GET)
	public void getJobSubTypeChangeOnlyID(@RequestParam(value = "JobSub_ID", required = true) Integer JobSub_ID,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(JobSub_ID != null) {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			JobSubType wadd = new JobSubType();
			JobSubTypeDto add = jobSubTypeService.getJobSub_ID_Only_TypeName(JobSub_ID, userDetails.getCompany_id());
			if (add != null) {
				wadd.setJob_Type(add.getJob_Type());
				wadd.setJob_TypeId(add.getJob_TypeId());
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(wadd));
		}
		
	}
	
	@RequestMapping(value = "/PrintCompanyWiseWorkOrders", method = RequestMethod.GET)
	public ModelAndView PrintCompanyWiseWorkOrders(@RequestParam("workorders_id") final Integer Workorders_id,
			final HttpServletResponse resul) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("workorders_id", Workorders_id);
		} catch (Exception e) {
			LOGGER.error("Work Order:", e);
		}
		return new ModelAndView("PrintCompanyWiseWorkOrderPrint", model);
	}
	
	// get service reminder list drop down for particular vehicle
		@RequestMapping(value = "/getVehicleServiceReminderList", method = RequestMethod.GET)
		public void getVehicleServiceReminderList(@RequestParam(value = "vehicleID", required = true) Integer vehicleID,
				Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtility.DD_MM_YY);
			SimpleDateFormat sdf1 = new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD);
			
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			
			int noOfDay = companyConfigurationService.getServiceReminderListByDate(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			
			java.util.Date currentDate = new java.util.Date();
			String currDate = sdf1.format(currentDate);
			String dueSoon = DateTimeUtility.getDateAfterNoOfDays(noOfDay);
			Date parsedDate1 = sdf.parse(dueSoon);
			String newdueSoon = sdf1.format(parsedDate1);
			
			
			List<ServiceReminderDto> servReminder = new ArrayList<ServiceReminderDto>();
			List<ServiceReminderDto> servReminderList = null;
			if(!(boolean) configuration.get("showServiceProgramINWO")) {
				servReminderList = ServiceReminderService.OnlyVehicleServiceReminderListByDate(vehicleID, currDate, newdueSoon,userDetails.getCompany_id());
			}else {
				servReminderList = ServiceReminderService.getServiceProgramListByVid(vehicleID, currDate, newdueSoon,userDetails.getCompany_id());
			}
			
			if (servReminderList != null && !servReminderList.isEmpty()) {
				for (ServiceReminderDto add : servReminderList) {

					ServiceReminderDto wadd = new ServiceReminderDto();
					
					if(!(boolean) configuration.get("showServiceProgramINWO")) {
						wadd.setService_id(add.getService_id());
					}else {
						wadd.setService_id(add.getServiceProgramId());
					}
					wadd.setService_type(add.getService_type());
					wadd.setService_subtype(add.getService_subtype());
					wadd.setTime_servicedate(add.getTime_servicedate());
					if(add.getTime_servicedate() != null)
					wadd.setServceDate(sqldateTime.format(add.getTime_servicedate()));
					wadd.setServiceTypeId(add.getServiceTypeId());
					wadd.setServiceSubTypeId(add.getServiceSubTypeId());
					wadd.setService_Number(add.getService_Number());
					
					if(add.getServiceProgram() != null) {
						wadd.setService_NumberStr(add.getServiceProgram()+" : SR-"+add.getService_Number()+"-"+add.getService_type()+"-"+add.getService_subtype()+"-"+add.getTime_servicedate());
					}else {
						wadd.setService_NumberStr("SR-"+add.getService_Number()+"-"+add.getService_type()+"-"+add.getService_subtype()+"-"+add.getTime_servicedate());
					}
					
					if((boolean) configuration.get("showServiceProgramINWO")) {
						wadd.setService_NumberStr(add.getServiceProgram());
					}
					

					servReminder.add(wadd);
				}
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(servReminder));
			
		}
		
		@GetMapping("/showMultiServiceReminderPrint")
		public ModelAndView showMultiServiceReminderPrint(@RequestParam("serviceProgramIds") final String serviceProgramIds, @RequestParam("vid") final String vid) {
			
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ModelAndView view = new ModelAndView("showMultiServiceReminderPrint");
			Integer vehicleId = Integer.parseInt(vid);
			if(serviceProgramIds != null && !serviceProgramIds.trim().equals("")) {
				Map<String,Object> map = ServiceReminderService.getMultiPrintDataForServiceReminder(vehicleId,serviceProgramIds,userDetails.getCompany_id());
				view.addAllObjects(map);
			}
			return view;
		}
		
		@RequestMapping(value = "/getReasonTypeWorkOrder", method = RequestMethod.GET)
		public void getReasonTypeWorkOrder(Map<String, Object> map, @RequestParam("term") final String term,
				final HttpServletRequest request, HttpServletResponse response) throws Exception {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			List<ReasonForRepairType> reason = new ArrayList<ReasonForRepairType>();
			List<ReasonForRepairType> reasonType = reasonTypeService.SearchOnlyReasonType(term, userDetails.getCompany_id());
			if (reasonType != null && !reasonType.isEmpty()) {
				for (ReasonForRepairType add : reasonType) {
					ReasonForRepairType wadd = new ReasonForRepairType();
					wadd.setReason_Type(add.getReason_Type());
					wadd.setReason_id(add.getReason_id());
					
					reason.add(wadd);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(reason));
		}

}
