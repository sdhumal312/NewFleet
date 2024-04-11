package org.fleetopgroup.web.controller.report;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TyreAssignmentConstant;
import org.fleetopgroup.constant.VehicleConfigurationContant;
import org.fleetopgroup.constant.WorkOrdersConfigurationConstants;
import org.fleetopgroup.constant.WorkOrdersType;
import org.fleetopgroup.persistence.bl.PartCategoriesBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.WorkOrdersBL;
import org.fleetopgroup.persistence.dao.WorkOrderRemarkRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.VehicleAccidentDetailsDto;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksToPartsDto;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.WorkOrderRemark;
import org.fleetopgroup.persistence.model.WorkOrders;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.service.WorkOrdersService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IPartCategoriesService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAccidentDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreAssignmentService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOTaskToPartDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.AESEncryptDecrypt;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ExceptionProcess;
import org.fleetopgroup.web.util.FileUtility;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@RestController
public class WorkOrderReportRestController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired private IWorkOrdersService						workOrdersService;
	@Autowired private CompanyConfigurationService 				companyConfigurationService;
	@Autowired private IPartLocationPermissionService 			partLocationPermissionService;
	@Autowired private IVehicleService 						    vehicleService;
	@Autowired private IPartLocationsService 					PartLocationsService;
	@Autowired private IServiceReminderService					serviceReminderService;
	@Autowired private IInventoryService						inventoryService;
	@Autowired private IVehicleAccidentDetailsService			accidentDetailsService;
	@Autowired private IWorkOTaskToPartDocumentService 			workOTaskToPartDocumentService;
	@Autowired private WorkOrderRemarkRepository				workOrderRemarkRepository;
	@Autowired 		IIssuesService issuesService;
	@Autowired private IVehicleTyreAssignmentService			vehicleTyreAssignmentService;
	@Autowired IPartCategoriesService                           partCategoriesService;
	
	PartLocationsBL 							partLocationsBL 				= new PartLocationsBL();
	WorkOrdersBL 								WOBL 							= new WorkOrdersBL();
	PartLocationsBL 							PLBL 							= new PartLocationsBL();
	SimpleDateFormat dateFormat_Name 		= new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat sqlFormat 				= new SimpleDateFormat("yyyy-MM-dd");
	
	PartCategoriesBL 	partCategoriesBL	= new PartCategoriesBL();
	
	@RequestMapping("/OldPartReceived")
	public ModelAndView OldPartReceived() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("OldPartReceived_Report", model);
	}
	
	
	@RequestMapping("/OldPartNotReceived")
	public ModelAndView OldPartNotReceived() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("OldPartNotReceived_Report", model);
	}
	
	@RequestMapping(value = "/getOldPartReceivedReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getOldPartReceivedReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject	=	workOrdersService.getOldPartNotReceived(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value = "/getOldPartNotReceivedReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getOldPartNotReceivedReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject	=	workOrdersService.getOldPartNotReceived(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value = "/createWorkOrder", method = RequestMethod.GET)
	public ModelAndView createWorkOrder(@ModelAttribute("command") WorkOrdersDto	ordersDto,
			HttpServletRequest request) {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		CustomUserDetails 			userDetails 		= null;
		HashMap<String, Object>     configuration		= null;
		HashMap<String, Object>     gpsConfiguration	= null;
		boolean						tallyConfig			= false;
		int							noOfBackDays		= 0;
		String 						minBackDate			= null; 
		Vehicle						vehicle				= null;
		int 						vid					= 0;
		long						issueId				= 0;
		String 						issue				= "0,0";
		boolean						showSubLocation		= false;
		String 						mainLocationIds		= null; 
		String 						serverDate			= null; 
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			tallyConfig			= (boolean) configuration.getOrDefault(WorkOrdersConfigurationConstants.TALLY_COMPANY_MASTER_IN_WO, false);
			noOfBackDays		= (Integer) configuration.getOrDefault(WorkOrdersConfigurationConstants.NO_OF_BACK_DAYS, 0);
			minBackDate 		= DateTimeUtility.getDateBeforeNoOfDays(noOfBackDays);
			serverDate 			= DateTimeUtility.getDateBeforeNoOfDays(0);
			showSubLocation	 	= (boolean)configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_SUB_LOCATION, false);
			mainLocationIds 	= configuration.getOrDefault(WorkOrdersConfigurationConstants.MAIN_LOCATION_IDS, "")+"";
			if(ordersDto.getIssue() != null) {
				issue = ordersDto.getIssue();
			}
			if(ordersDto.getService_id() != null) {
				ObjectMapper objectMapper = new ObjectMapper();
				
				ServiceReminderDto ServiceReminderDB = serviceReminderService
						.getServiceReminder(ordersDto.getService_id(), userDetails.getCompany_id());
				
				if (ServiceReminderDB.getServiceStatusId() > 0) {
					if (ServiceReminderDB.getServiceStatusId() == ServiceReminderDto.SERVICE_REMINDER_STATUS_INACTIVE) {
						model.put("alreadyCreatedWorkOrder", true);
					}
				}
				
				model.put("Service", objectMapper.writeValueAsString(ServiceReminderDB));
			}
			vid 					= Integer.parseInt(issue.split(",")[0]);
			issueId 				= Integer.parseInt(issue.split(",")[1]);
			vehicle					= vehicleService.getVehicel(vid, userDetails.getCompany_id());
			
			String	accident = ordersDto.getAccident();
			if(accident != null) {
				Long	accidentId = Long.parseLong(AESEncryptDecrypt.decryptBase64(accident)); 
				VehicleAccidentDetailsDto	detailsDto = accidentDetailsService.getVehicleAccidentDetailsById(accidentId, userDetails.getCompany_id(), userDetails.getId());
				if(detailsDto != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					model.put("detailsDto", objectMapper.writeValueAsString(detailsDto));
					model.put("accidentDetailsDto", detailsDto);
				}
			}
			
			model.put("issueId",issueId);
			model.put("vid",vid);
			if(vehicle != null) {
				model.put("vehicleReg",vehicle.getVehicle_registration());
			}
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			model.put("accessToken", uniqueID);
			model.put("tallyConfig", tallyConfig);
			model.put("minBackDate",minBackDate);
			model.put("serverDate",serverDate);
			model.put("partLocationPermission", partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));
			model.put("configuration", configuration);
			model.put("validateOdometerInWorkOrder", companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_WORKORDER, false));
			model.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_WORKORDER,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_WORKORDER, false));
			model.put("gpsConfiguration", gpsConfiguration);
			model.put("companyId", userDetails.getCompany_id());
			model.put("showSubLocation", showSubLocation);
			model.put("mainLocationIds", mainLocationIds);
			
			if((boolean) configuration.get("showPartCategoriesDropdown")) {	
				model.put("PartCategories", partCategoriesBL.prepareListofBean(partCategoriesService.listPartCategoriesByCompayIdAncIncIssue(userDetails.getCompany_id())));
			}
		} catch (NullPointerException e) {
			LOGGER.error("Work Order:", e);
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Work Order:", e);
		}finally {
			userDetails 		= null;
			configuration		= null;
			gpsConfiguration	= null;
			
		}
		return new ModelAndView("createWorkOrder", model);
	}
	
	@RequestMapping(value = "/createWorkOrderNew", method = RequestMethod.GET)
	public ModelAndView createWorkOrderNew(@ModelAttribute("command") WorkOrdersDto	ordersDto,
			HttpServletRequest request) {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		CustomUserDetails 			userDetails 		= null;
		HashMap<String, Object>     configuration		= null;
		HashMap<String, Object>     gpsConfiguration	= null;
		boolean						tallyConfig			= false;
		int							noOfBackDays		= 0;
		String 						minBackDate			= null; 
		Vehicle						vehicle					= null;
		int 						vid						= 0;
		long						issueId					= 0;
		String 						issue					= "0,0";
		boolean						showSubLocation			= false;
		String 						mainLocationIds			= null; 
		String 						serverDate			= null; 
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			tallyConfig			= (boolean) configuration.getOrDefault(WorkOrdersConfigurationConstants.TALLY_COMPANY_MASTER_IN_WO, false);
			noOfBackDays		= (Integer) configuration.getOrDefault(WorkOrdersConfigurationConstants.NO_OF_BACK_DAYS, 0);
			minBackDate 		= DateTimeUtility.getDateBeforeNoOfDays(noOfBackDays);
			serverDate 			= DateTimeUtility.getDateBeforeNoOfDays(0);
			showSubLocation	 	= (boolean)configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_SUB_LOCATION, false);
			mainLocationIds 	= configuration.getOrDefault(WorkOrdersConfigurationConstants.MAIN_LOCATION_IDS, "")+"";
			if(ordersDto.getIssue() != null) {
				issue = ordersDto.getIssue();
			}
			if(ordersDto.getService_id() != null) {
				ObjectMapper objectMapper = new ObjectMapper();
				
				ServiceReminderDto ServiceReminderDB = serviceReminderService
						.getServiceReminder(ordersDto.getService_id(), userDetails.getCompany_id());
				
				if (ServiceReminderDB.getServiceStatusId() > 0) {
					if (ServiceReminderDB.getServiceStatusId() == ServiceReminderDto.SERVICE_REMINDER_STATUS_INACTIVE) {
						model.put("alreadyCreatedWorkOrder", true);
					}
				}
				
				model.put("Service", objectMapper.writeValueAsString(ServiceReminderDB));
			}
			vid 					= Integer.parseInt(issue.split(",")[0]);
			issueId 				= Integer.parseInt(issue.split(",")[1]);
			vehicle					= vehicleService.getVehicel(vid, userDetails.getCompany_id());
			
			String	accident = ordersDto.getAccident();
			if(accident != null) {
				Long	accidentId = Long.parseLong(AESEncryptDecrypt.decryptBase64(accident)); 
				VehicleAccidentDetailsDto	detailsDto = accidentDetailsService.getVehicleAccidentDetailsById(accidentId, userDetails.getCompany_id(), userDetails.getId());
				if(detailsDto != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					model.put("detailsDto", objectMapper.writeValueAsString(detailsDto));
					model.put("accidentDetailsDto", detailsDto);
				}
			}
			
			
			model.put("issueId",issueId);
			model.put("vid",vid);
			if(vehicle != null) {
				model.put("vehicleReg",vehicle.getVehicle_registration());
			}
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			model.put("accessToken", uniqueID);
			model.put("tallyConfig", tallyConfig);
			model.put("minBackDate",minBackDate);
			model.put("serverDate",serverDate);
			model.put("partLocationPermission", partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));
			model.put("configuration", configuration);
			model.put("validateOdometerInWorkOrder", companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_WORKORDER, false));
			model.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_WORKORDER,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_WORKORDER, false));
			model.put("gpsConfiguration", gpsConfiguration);
			model.put("companyId", userDetails.getCompany_id());
			model.put("showSubLocation", showSubLocation);
			model.put("mainLocationIds", mainLocationIds);
			
		} catch (NullPointerException e) {
			LOGGER.error("Work Order:", e);
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Work Order:", e);
		}finally {
			userDetails 		= null;
			configuration		= null;
			gpsConfiguration	= null;
			
		}
		return new ModelAndView("createWorkOrderNew", model);
	}
	
	@PostMapping(path = "/saveWorkOrderDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveWorkOrderDetails(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object.put("workOrderDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("workOrderDetails")));
			return workOrdersService.saveWorkOrder(object).getHtData();
		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		} 
	}
	
	@RequestMapping(value = "/showWorkOrder", method = RequestMethod.GET)
	public ModelAndView showWorkOrder(@RequestParam("woId") final Long Workorders_id, final HttpServletRequest request) throws Exception {
		Map<String, Object> 		model 						= null;
		CustomUserDetails 			userDetails 				= null;
		WorkOrdersDto 				workOrdersDto 				= null;
		WorkOrdersDto 				workOrders 					= null;
		HashMap<String, Object>     configuration				= null;
		boolean						showCompanyWisePrint		= false;
		int							noOfBackDaysToReOpenWO		= 0;
		long						differenceBetTwoDate		= 0;
		boolean						reOpenWO					= false;
		boolean						showSubLocation				= false;
		String    					issueIds					= null;
		List<IssuesDto>				issueList					= null;
		try {
			model 			= new HashMap<>();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			showSubLocation	= (boolean)configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_SUB_LOCATION, false);
			workOrdersDto 	= workOrdersService.getWorkOrdersDetails(Workorders_id, userDetails.getCompany_id());
			
			if (workOrdersDto != null) {
				workOrders 		= WOBL.Show_WorkOrders(workOrdersDto);
				if((boolean) configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_START_AND_DUE_TIME_FIELD, false)) {
					DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm");
					if (workOrdersDto.getStart_dateOn() != null) {
						workOrders.setStart_date(ft.format(workOrdersDto.getStart_dateOn()));
					}
					if (workOrdersDto.getDue_dateOn() != null) {
						workOrders.setDue_date(ft.format(workOrdersDto.getDue_dateOn()));
					}
				}
				
				issueIds = workOrdersDto.getIssueIds();
				Boolean issueRemarkStatus = false;
				Boolean issueTaskStatus = false;
				if((boolean)configuration.getOrDefault("showIssueInWO", false) && issueIds != null && !issueIds.trim().equals("")) {
					issueList=issuesService.getWOIssueList(Workorders_id,issueIds,userDetails.getCompany_id());
					if(issueList != null && !issueList.isEmpty()) {
						for(IssuesDto dto:issueList) {
							if(dto.getWoRemark() == null || (dto.getWoRemark() != null && dto.getWoRemark().trim().equals(""))) 
								issueRemarkStatus = true;
							if(dto.getWorkOrderTaskId() == null || (dto.getWorkOrderTaskId() != null && dto.getWorkOrderTaskId() <= 0)) 
								issueTaskStatus = true;
							if(issueRemarkStatus && issueTaskStatus) 
								break;
						}
					}
				}
				model.put("issueList", issueList);
				model.put("issueRemarkStatus", issueRemarkStatus);
				model.put("issueTaskStatus", issueTaskStatus);
				
				workOrders.setCompanyId(userDetails.getCompany_id());				
				model.put("WorkOrder", workOrders);
				model.put("configuration", configuration);
				model.put("showSubLocation", showSubLocation);
				model.put("workOrdersOnHoldId", WorkOrdersType.WORKORDERS_STATUS_ONHOLD);
				
				if((boolean) configuration.get("showCompanyWisePrint")) {
						showCompanyWisePrint = true;
				}
				
				if((boolean) configuration.get("companyWisePrintOnClosedStatus")) {
					if(workOrders.getWorkorders_statusId() == WorkOrdersType.WORKORDERS_STATUS_COMPLETE){
						showCompanyWisePrint = false;
					}else {
						showCompanyWisePrint = true;
					}
				}
				
				model.put("companyWisePrint", showCompanyWisePrint);
				
				model.put("showJobTypeRemarkCol", configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_JOB_TYPE_REMARK_COL, false));
				model.put("showEditJobTypeRemark", configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_EDIT_JOB_TYPE_REMARK, false));
				
				boolean	allTaskCOmpleted  = true;
				
				List<WorkOrdersTasksDto> WorkOrderTask =WOBL.View_WorkOrder_Task_Details(workOrdersService.getWorkOrdersTasks(Workorders_id, userDetails.getCompany_id()));					
					if(WorkOrderTask!= null && !WorkOrderTask.isEmpty()) {
						String completeDate = sqlFormat.format(workOrdersDto.getStart_dateOn())+" "+DateTimeUtility.DAY_END_TIME;
						String	query = " AND wo.completed_date < '"+completeDate+"' " ;
						
						for(WorkOrdersTasksDto wo : WorkOrderTask) {
							WorkOrders LastOcc =workOrdersService.getLastWorkOrdersTaskToWorkOrdersdetails(wo.getJob_typetaskId(), wo.getJob_subtypetask_id(), wo.getVehicle_vid(), userDetails.getCompany_id(),Workorders_id,query);
							if (LastOcc != null) {
								wo.setLast_occurred_woId(LastOcc.getWorkorders_id());
								if (LastOcc.getStart_date() != null) {
									wo.setLast_occurred_date(dateFormat_Name.format(LastOcc.getStart_date()));
								}
								wo.setLast_occurred_odameter(LastOcc.getVehicle_Odometer());
							}else {
								wo.setLast_occurred_woId((long) 0);
							}
							if(wo.getMark_complete() == null || wo.getMark_complete() == 0) {
								allTaskCOmpleted	= false;
							}
						}
					}
					model.put("allTaskCOmpleted",allTaskCOmpleted );
					model.put("WorkOrderTask",WorkOrderTask );
				
			
				if((boolean) configuration.get("workOrderTaskToPartDocument")) {
					model.put("photoPendingForAnyPart", workOrdersService.findocumentAvailabilityByPartId(Workorders_id,(long)userDetails.getCompany_id()));
				}
				
				List<WorkOrdersTasksToPartsDto> 	dtos = workOrdersService.getWorkOrdersTasksToParts(Workorders_id, userDetails.getCompany_id());
				
				if((boolean) configuration.get("showPartCategoriesDropdown")) {	
					model.put("PartCategories", partCategoriesBL.prepareListofBean(partCategoriesService.listPartCategoriesByCompayIdAncIncIssue(userDetails.getCompany_id())));
				}
				model.put("WorkOrderTaskPart", dtos);
				model.put("allPartAssigned", WOBL.getAllPartAssigned(dtos));
				model.put("WorkOrderTaskLabor",	workOrdersService.getWorkOrdersTasksToLabor(Workorders_id, userDetails.getCompany_id()));
				model.put("accessPartToken", Utility.getUniqueToken(request));
				model.put("accessLabourToken", Utility.getUniqueToken(request));
				model.put("accessIssueTaskToken", Utility.getUniqueToken(request));
				model.put("tyreAssignment", vehicleTyreAssignmentService.getTyreAssignmentByTransactionTypeAndTransactionId(TyreAssignmentConstant.TRANSACTION_TYPE_WO, Workorders_id, userDetails.getCompany_id()));
				if((boolean) configuration.get("addWOCompletionRemark")) {
					model.put("WORemarkList", workOrdersService.getWorkOrderRemarkDtoList(Workorders_id));
				}else {
					model.put("WORemarkList", workOrdersService.getWorkOrderRemarkDtoOnHoldList(Workorders_id));
				}

				if (workOrders.getWorkorders_statusId() == WorkOrdersType.WORKORDERS_STATUS_ONHOLD) {
					return new ModelAndView("showWorkOrder", model);
				}
				
				if (workOrders.getWorkorders_statusId() == WorkOrdersType.WORKORDERS_STATUS_COMPLETE) {
					if(workOrders.getCompleted_dateOn() != null) {
						noOfBackDaysToReOpenWO				= (Integer) configuration.getOrDefault(WorkOrdersConfigurationConstants.NO_OF_BACK_DAYS_TO_REOPEN_WO, 0);
						differenceBetTwoDate 				= DateTimeUtility.getExactDayDiffBetweenTwoDates(DateTimeUtility.getTimeStampFromDate(workOrders.getCompleted_dateOn()),DateTimeUtility.getCurrentTimeStamp());
						}
					
					if(differenceBetTwoDate < noOfBackDaysToReOpenWO) {
						reOpenWO	= true;
					}
					model.put("reOpenWO", reOpenWO);
					
					return new ModelAndView("showCompleteWorkOrder", model);
				}
			}

			return new ModelAndView("showWorkOrder", model);
			
		} catch (Exception e) {
			throw e;
		} finally {
			model 				= null;
			userDetails 		= null;
			workOrdersDto 		= null;
			workOrders 			= null;
			configuration		= null;
		}
	}
	
	@PostMapping(path = "/savePartForWorkOderTask", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  savePartForWorkOderTask(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.savePartForWorkOderTask(object).getHtData();
			
		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		} 
	}
	
	@PostMapping(path = "/uploadFilePartForWorkOderTask", produces="application/json")
	public HashMap<Object, Object>  uploadFilePartForWorkOderTask(@RequestParam("partForWO") String allRequestParams ,@RequestParam("file") MultipartFile[] uploadFile, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams);
			
			return workOrdersService.workOrderTaskToPartDocumentSave(uploadFile, object).getHtData();
					
			
		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		} 
	}
	
	@PostMapping(path = "/getDocumentWOTaskToPartList", produces="application/json")
	public HashMap<Object, Object> getDocumentWOTaskToPartList(@RequestParam HashMap<Object, Object> allRequestParams ,HttpServletRequest request) throws Exception {
		ValueObject valueOutObject = null;
		ValueObject object= null;
		CustomUserDetails userDetails =null;
		try {
			
			userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object=new ValueObject(allRequestParams);
			valueOutObject = new ValueObject() ;
			valueOutObject.put("documents", workOTaskToPartDocumentService.getWorkOTaskToPartById(object.getLong("workordertaskto_partid"),userDetails.getCompany_id()));
			
			return valueOutObject.getHtData();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@PostMapping(path = "/saveLabourForWorkOderTask", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveLabourForWorkOderTask(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.saveLabourForWorkOderTask(object).getHtData();
			
		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		} 
	}
	
	@RequestMapping(value = "/saveMarkAsComplete", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveMarkAsComplete(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.saveMarkAsComplete(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveWorkOderTasks", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveWorkOderTasks(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.saveWorkOderTasks(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deletePartOfWorkOrdertask", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deletePartOfWorkOrdertask(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
	
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.deletePartOfWorkOrdertask(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteLabourOfWorkOrdertask", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteLabourOfWorkOrdertask(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.deleteLabourOfWorkOrdertask(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteWorkOrdertask", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteWorkOrdertask(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.deleteWorkOrdertask(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/updateWoGstCost", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateWoGstCost(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.updateWoGstCost(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/changeWorkorderStatusToHold", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  changeWorkorderStatusToHold(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.changeWorkorderStatusToHold(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/changeWorkorderStatusToInProgress", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  changeWorkorderStatusToInProgress(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.changeWorkorderStatusToInProgress(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/changeWorkorderStatusToComplete", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  changeWorkorderStatusToComplete(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.changeWorkorderStatusToComplete(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value="/uploadWorkOrderDocument", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  uploadWorkOrderDocument(@RequestParam HashMap<Object, Object> allRequestParams,
			@RequestParam("input-file-preview") MultipartFile uploadfile) throws Exception {
		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= workOrdersService.uploadWorkOrderDocument(JsonConvertor.toValueObjectFormSimpleJsonString(valueInObject.getString("WorkOrderData")),uploadfile);
			
			return valueOutObject.getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	
	@RequestMapping(value = "/workOrderEdit", method = RequestMethod.GET)
	public ModelAndView workOrderEdit(@RequestParam("woId") final Long Workorders_id, final HttpServletRequest request)	throws Exception {
		HashMap<String, Object>     configuration		= null;
		HashMap<String, Object>     gpsConfiguration	= null;
		Map<String, Object> 		model 				= null;
		CustomUserDetails		 	userDetails 		= null;
		WorkOrdersDto 				workOrdersDto 		= null;
		WorkOrdersDto 				workOrdersDtoBL		= null;
		boolean						tallyConfig			= false;
		int							noOfBackDays		= 0;
		String 						minBackDate			= null; 
		boolean						showSubLocation		= false;
		String 						mainLocationIds		= null; 
		List<IssuesDto>				issueList			= null;
		List<IssuesDto>				finalIssueList			= null;
		try {
			model 				= new HashMap<String, Object>();
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			tallyConfig			= (boolean) configuration.getOrDefault(WorkOrdersConfigurationConstants.TALLY_COMPANY_MASTER_IN_WO, false);
			noOfBackDays		= (Integer) configuration.getOrDefault(WorkOrdersConfigurationConstants.NO_OF_BACK_DAYS, 0);
			minBackDate 		= DateTimeUtility.getDateBeforeNoOfDays(noOfBackDays);
			showSubLocation	 	= (boolean)configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_SUB_LOCATION, false);
			mainLocationIds 	= configuration.getOrDefault(WorkOrdersConfigurationConstants.MAIN_LOCATION_IDS, "")+"";
			workOrdersDto 		= workOrdersService.getWorkOrdersDetails(Workorders_id, userDetails.getCompany_id());
			workOrdersDtoBL 	= WOBL.Show_WorkOrders(workOrdersDto);
			List<WorkOrdersTasksDto> taskList	= workOrdersService.getWorkOrdersTasks(Workorders_id, userDetails.getCompany_id());
			
			if(taskList != null && !taskList.isEmpty()) {
				String	serviceReminderIds = "";
				for (WorkOrdersTasksDto workOrdersTasksDto : taskList) {
					if(workOrdersTasksDto.getService_id() > 0) {
						serviceReminderIds += workOrdersTasksDto.getService_id()+",";
					}
				}
				if(!serviceReminderIds.equals("")) {
					ObjectMapper objectMapper = new ObjectMapper();
					model.put("serviceReminderIds", Utility.removeLastComma(serviceReminderIds));
					model.put("serviceProgramList", objectMapper.writeValueAsString(serviceReminderService.getProgramListByreminderIds(Utility.removeLastComma(serviceReminderIds), userDetails.getCompany_id())));
				}
			}
			String issueIds =workOrdersDto.getIssueIds();
			
			if((boolean)configuration.getOrDefault("showIssueInWO", false) && issueIds != null && !issueIds.trim().equals("")) {
				ObjectMapper objectMapper = new ObjectMapper();
				issueList=issuesService.getWOIssueList(Workorders_id,issueIds,userDetails.getCompany_id());
				if(issueList != null && !issueList.isEmpty()) {
					IssuesDto tempDto = null;
					finalIssueList = new ArrayList<>();
					for(IssuesDto dto :issueList ) {
						tempDto = new IssuesDto();
						tempDto.setISSUES_ID(dto.getISSUES_ID());
						tempDto.setIssuesNumberStr(dto.getIssuesNumberStr());
						finalIssueList.add(tempDto);
					}
				}
			
				model.put("issueList", 	objectMapper.writeValueAsString(finalIssueList));
			}
			
			model.put("minBackDate",minBackDate);
			model.put("WorkOrder", workOrdersDtoBL);
			if(workOrdersDtoBL.getISSUES_ID() > 0) {
				model.put("issueId", workOrdersDtoBL.getISSUES_ID());
			}
			model.put("tallyConfig", tallyConfig);
			model.put("configuration", configuration);
			model.put("gpsConfiguration", gpsConfiguration);
			model.put("companyId", userDetails.getCompany_id());
			model.put("vehicle", vehicleService.Get_Vehicle_Current_Status_TripSheetID(workOrdersDtoBL.getVehicle_vid()));
			model.put("validateOdometerInWorkOrder", companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_WORKORDER, false));
			model.put("showSubLocation", showSubLocation);
			model.put("mainLocationIds", mainLocationIds);
			model.put("serverDate", DateTimeUtility.getDateBeforeNoOfDays(0));
			
			return new ModelAndView("workOrderEdit", model);
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/updateWorkOrderDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateWorkOrderDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.updateWorkOrderDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteWorkOrderDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteWorkOrderDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.deleteWorkOrderDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping("/viewWorkOrder")
	public ModelAndView viewWorkOrder(final TripSheetDto tripsheetdto, final HttpServletRequest request) {
		HashMap<String, Object>     configuration		= null;
		Map<String, Object> 		model 				= null;
		boolean						showSubLocation		= false;
		CustomUserDetails		 	userDetails 		= null;

		try {
			model 				= new HashMap<String, Object>();
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			showSubLocation	 	= (boolean)configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_SUB_LOCATION, false);
			model.put("showSubLocation", showSubLocation);
			model.put("companyId", userDetails.getCompany_id());
			model.put("configuration", configuration);
		} catch (Exception e) {
			return new ModelAndView("redirect:/NotFound.in");
		}
		return new ModelAndView("viewWorkOrder",model);
	}
	
	@RequestMapping(value = "/getWorkOrderList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getWorkOrderList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.getWorkOrderList(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/searchWoByNumber", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchWoByNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.searchWoByNumber(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/searchWoByDifferentParameters", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchWoByDifferentParameters(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.searchWoByDifferentParameters(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping("/searchWorkOrderByData")
	public ModelAndView searchWorkOrderByData() {
		Map<String, Object> 	model 			= new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("PartLocations", PLBL.prepareListofBean(PartLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));

		} catch (Exception e) {

		}
		return new ModelAndView("searchWorkOrderByData", model);
	}
	
	@RequestMapping(value = "/getWorkOrderByData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getWorkOrderByData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.searchWorkOrderByData(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/approveWorkOrderDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  approveWorkOrderDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.approveWorkOrderDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(value = "/getInvoiceWisePartList")
	public void getInvoiceWisePartList(Map<String, Object> map, @RequestParam("term") final String term,
			@RequestParam("subLocationId") final Integer subLocationId,
			@RequestParam("mainLocationId") final Integer mainLocationId,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails 					userDetails 									= null;
		List<InventoryDto> 					location 										= null;
		HashMap<String, Object> 			configuration 									= null;
		boolean 							showSubLocation	 								= false;
		boolean								showSublocationPartDetailForGivenMainLocation  	= false;
		String								mainLocationIds 								= "";
		String[] 							mainLocationIdsArr 								= null;
		String 								locationStr										= "";
		try {
			List<InventoryDto> 	finalList = new ArrayList<>() ;
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
			
			if(showSublocationPartDetailForGivenMainLocation && subLocationId > 0) {
				locationStr = "AND R.locationId = "+mainLocationId+" AND R.subLocationId = "+subLocationId+"";
			}else {
				locationStr = "AND R.locationId = "+mainLocationId+" ";
			}
			
			location = inventoryService.getInvoiceWisePartList(term, locationStr, userDetails.getCompany_id());
			
			if (location != null && !location.isEmpty()) {
				for (InventoryDto add : location) {
					InventoryDto wadd = new InventoryDto();
						wadd.setQuantity(add.getQuantity());
						wadd.setInventory_id(add.getInventory_id());
						wadd.setPartInvoiceId(add.getPartInvoiceId());
						wadd.setLocation(add.getLocation());
						wadd.setLocationId(add.getLocationId());
						wadd.setPartname(add.getPartname());
						wadd.setPartnumber(add.getPartnumber());
						wadd.setMake(add.getMake());
						wadd.setInventory_location_id(add.getInventory_location_id());
						finalList.add(wadd);
						
				}
				
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(finalList));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
	
	
	
	@RequestMapping("/download/WorkOTaskToPart/{workordertaskto_partid}")
	public String downloadWorkOTaskToPartDoc(@PathVariable("workordertaskto_partid") Long workordertaskto_partid, HttpServletResponse response) {

		try {
			if (workordertaskto_partid == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				// Lookup file by fileId in database.
				org.fleetopgroup.persistence.document.WorkOTaskToPartDocument file = workOTaskToPartDocumentService.get_WorkOTaskToPart_Document(workordertaskto_partid, userDetails.getCompany_id());

			
				
				if (file != null) {
					if (file.getDocumentContent() != null) {
						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getDocumentFilename() + "\"");
						if(file.getDocumentContentType().equals(FileUtility.FILE_EXTENSION_PDF)){
							file.setDocumentContentType(FileUtility.getContentType(file.getDocumentContentType()));
						}
						if(file.getDocumentContentType().equals(FileUtility.FILE_EXTENSION_JPG)){
							file.setDocumentContentType(FileUtility.getContentType(file.getDocumentContentType()));
						}
						if(file.getDocumentContentType().equals(FileUtility.FILE_EXTENSION_EXCEL)){
							file.setDocumentContentType(FileUtility.getContentType(file.getDocumentContentType()));
						}
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getDocumentContentType());
						response.getOutputStream().write(file.getDocumentContent());
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
	
	@PostMapping( value="deleteWorkOTaskToPartDocument", produces="application/json")
	public HashMap<Object, Object>  deleteWorkOTaskToPartDocument(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
		
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.deletePartOfWorkOrdertaskDocument(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(value="/getTaskDetails",produces="application/json")
	public HashMap<Object, Object> getWOTaskDetails(@RequestParam HashMap<Object, Object> allrequestParam ) throws Exception {
		ValueObject object = null;
		CustomUserDetails userDetails = null;
		try {
			object = new ValueObject(allrequestParam);
			 userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object.put("WorkOrdersTasks",WOBL.View_WorkOrder_Task_Details(workOrdersService.getWorkOrdersTasks(object.getLong("workOrderId",0), userDetails.getCompany_id())));
			return object.getHtData();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@PostMapping(value="/markAllTaskCompleted",produces="application/json")
	public HashMap<Object, Object>  markAllTaskCompleted(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.markAllTaskCompleted(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(value="/getCategoryByIssueId",produces="application/json")
	public HashMap<Object, Object>   getCategoryByIssueId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return issuesService.getIssueDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
//	@PostMapping(value="/saveWorkorderIssueRemark",produces="application/json")
//	public HashMap<Object,Object> issueRemarkSave(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
//		ValueObject object = null;
//		try {
//			object = new ValueObject(allRequestParams);
//			
//			return workOrdersService.saveIssueRemark(object).getHtData();
//			
//		} catch (Exception e) {
//			throw e;
//		}
//	}
	
	@PostMapping(path = "/getTecnicianPerDaySalaryDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getTecnicianPerDaySalaryDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		CustomUserDetails userDetails =null;
		HashMap<Object, Object> hashmap             = null;
		ValueObject 		object 					= null;
		try {
			userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			hashmap          = new HashMap<Object, Object>();
			object 				= new ValueObject(allRequestParams);
			
			Double perHourDriverSalary =  workOrdersService.calculatePerHourLaborCost(object.getInt("vid"), userDetails.getCompany_id());
			
			hashmap.put("perHourTecnicianSalary", perHourDriverSalary);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return hashmap;
	}
	
}
