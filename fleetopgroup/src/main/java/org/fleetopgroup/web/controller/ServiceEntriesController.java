package org.fleetopgroup.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.IssuesTypeConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.ServiceEntriesConfigurationConstants;
import org.fleetopgroup.constant.ServiceEntriesType;
import org.fleetopgroup.constant.VehicleAgentConstant;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.constant.VehicleOwnerShip;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.constant.VendorGSTRegistered;
import org.fleetopgroup.constant.VendorTypeConfigurationConstants;
import org.fleetopgroup.constant.WorkOrdersConfigurationConstants;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.DriverReminderBL;
import org.fleetopgroup.persistence.bl.JobTypeBL;
import org.fleetopgroup.persistence.bl.MasterPartsBL;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.bl.ServiceEntriesBL;
import org.fleetopgroup.persistence.bl.ServiceReminderBL;
import org.fleetopgroup.persistence.bl.VehicleAgentTxnCheckerBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.bl.VehicleProfitAndLossTxnCheckerBL;
import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.dto.BatteryDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.MasterPartsDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesTasksDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesTasksToPartsDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksDto;
import org.fleetopgroup.persistence.model.Battery;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.EmailAlertQueue;
import org.fleetopgroup.persistence.model.Issues;
import org.fleetopgroup.persistence.model.IssuesTasks;
import org.fleetopgroup.persistence.model.JobSubType;
import org.fleetopgroup.persistence.model.JobType;
import org.fleetopgroup.persistence.model.ServiceEntries;
import org.fleetopgroup.persistence.model.ServiceEntriesDocument;
import org.fleetopgroup.persistence.model.ServiceEntriesSequenceCounter;
import org.fleetopgroup.persistence.model.ServiceEntriesTasks;
import org.fleetopgroup.persistence.model.ServiceEntriesTasksToLabor;
import org.fleetopgroup.persistence.model.ServiceEntriesTasksToParts;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.model.SmsAlertQueue;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleAgentTxnChecker;
import org.fleetopgroup.persistence.model.VehicleExpenseDetails;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.model.VendorSequenceCounter;
import org.fleetopgroup.persistence.model.VendorType;
import org.fleetopgroup.persistence.model.WorkOrders;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.service.VehicleProfitAndLossTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IEmailAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IJobSubTypeService;
import org.fleetopgroup.persistence.serviceImpl.IJobTypeService;
import org.fleetopgroup.persistence.serviceImpl.IMasterPartsService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ISmsAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentIncomeExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVendorSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.persistence.serviceImpl.IVendorTypeService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

@Controller
public class ServiceEntriesController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired IVehicleService vehicleService;

	@Autowired IDriverService driverService;

	@Autowired IUserProfileService userProfileService;

	@Autowired IWorkOrdersService workOrdersService;

	@Autowired IVendorService vendorService;
	VendorBL VenBL = new VendorBL();

	@Autowired IServiceEntriesService ServiceEntriesService;

	@Autowired IJobTypeService JobTypeService;

	@Autowired IJobSubTypeService JobSubTypeService;
	JobTypeBL DriDT = new JobTypeBL();

	@Autowired IServiceReminderService ServiceReminderService;

	@Autowired IVehicleOdometerHistoryService vehicleOdometerHistoryService;
	
	@Autowired IVehicleDocumentService	vehicleDocumentService;

	@Autowired IMasterPartsService MasterPartsService;
	MasterPartsBL MasBL = new MasterPartsBL();

	@Autowired IWorkOrdersService WorkOrdersService;

	@Autowired IServiceEntriesSequenceService serviceEntriesSequenceService;
	

	@Autowired IServiceEntriesDocumentService	documentService;
	
	@Autowired MongoOperations	mongoOperations;
	
	@Autowired IVendorSequenceService vendorSequenceService;
	
	@Autowired IVendorTypeService VendorTypeService;
	
	@Autowired CompanyConfigurationService companyConfigurationService;
	
	
	@Autowired	IVehicleProfitAndLossService		vehicleProfitAndLossService;
	
	@Autowired	IVehicleExpenseDetailsService		vehicleExpenseDetailsService;
	
	@Autowired	private VehicleProfitAndLossTxnCheckerService	vehicleProfitAndLossTxnCheckerService;
	
	@Autowired IEmailAlertQueueService 				emailAlertQueueService;
	
	@Autowired ISmsAlertQueueService 				smsAlertQueueService;

	@Autowired IIssuesService 						issuesService;
	@Autowired private	IVehicleAgentTxnCheckerService						vehicleAgentTxnCheckerService;
	@Autowired private	IVehicleAgentIncomeExpenseDetailsService			vehicleAgentIncomeExpenseDetailsService;
	//@Autowired private	ITripSheetService									tripSheetService;
	
	@Autowired IBatteryService                        iBatteryService;

	
	VehicleProfitAndLossTxnCheckerBL	txnCheckerBL = new VehicleProfitAndLossTxnCheckerBL();	
	
	VehicleOdometerHistoryBL VOHBL = new VehicleOdometerHistoryBL();
	VehicleAgentTxnCheckerBL	agentTxnCheckerBL			= new VehicleAgentTxnCheckerBL();
	ServiceEntriesBL WOBL = new ServiceEntriesBL();
	RenewalReminderBL RRBL = new RenewalReminderBL();

	DriverReminderBL DriverRem = new DriverReminderBL();

	VehicleBL VBL = new VehicleBL();
	ServiceReminderBL SRBL = new ServiceReminderBL();
	DriverBL DBL = new DriverBL();

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	// search service Entries
	@RequestMapping(value = "/searchServiceEntries", method = RequestMethod.POST)
	public ModelAndView searchServiceEntries(@RequestParam("Search") final String ServiceEntries_id,
			final HttpServletResponse result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("ServiceEntries", WOBL.prepareListServiceEntries(ServiceEntriesService
					.SearchServiceEntries(ServiceEntries_id, userDetails.getCompany_id(), userDetails.getId())));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Renewal Reminder:", e);
		} finally {
			userDetails = null;
		}
		return new ModelAndView("ServiceEntries_Report", model);
	}

	@RequestMapping(value = "/searchServiceEntriesShow", method = RequestMethod.POST)
	public ModelAndView searchServiceEntriesShow(@RequestParam("Search") final Long ServiceEntries_Number,
			final HttpServletResponse result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		ServiceEntriesDto serviceEntries = null;
		ServiceEntriesDto work = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			int count = ServiceEntriesService.getServiceEntriesCountByNumber(ServiceEntries_Number, userDetails.getCompany_id());
			if(count == 1) {
				serviceEntries = ServiceEntriesService.getServiceEntriesByNumber(ServiceEntries_Number,
						userDetails.getCompany_id(), userDetails.getId());
				if (serviceEntries != null) {
					return new ModelAndView("redirect:/ServiceEntriesParts.in?SEID=" + serviceEntries.getServiceEntries_id() + "", model);
				} else {
					model.put("NotFound", true);
					return new ModelAndView("redirect:/newServiceEntries/1/1.in", model);
				}
			}else {
				model.put("ServiceEntries", WOBL.prepareListServiceEntries(ServiceEntriesService
						.SearchServiceEntriesByNumber(ServiceEntries_Number, userDetails.getCompany_id(), userDetails.getId())));
				return new ModelAndView("ServiceEntries_Report", model);
			}

		} catch (NullPointerException e) {
			model.put("NotFound", true);
			return new ModelAndView("redirect:/newServiceEntries/1/1.in", model);
		} catch (Exception e) {
			System.err.println("Exception : " + e);
			model.put("NotFound", true);
			LOGGER.error("Renewal Reminder:", e);
			return new ModelAndView("redirect:/newServiceEntries/1/1.in", model);
		} finally {
			userDetails = null;
			serviceEntries = null;
			work = null;
		}
	}

	/** New Show Service Entries in Main page */
	@RequestMapping(value = "/newServiceEntries/{Status}/{pageNumber}", method = RequestMethod.GET)
	public String VehicleList(@PathVariable("Status") short Status, @PathVariable("pageNumber") Integer pageNumber,
			Model model) throws Exception {
		CustomUserDetails userDetails = null;
		long totalserviceentriescount = 0;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Page<ServiceEntries> page = ServiceEntriesService.getDeployment_Page_ServiceEntries(Status, pageNumber,
					userDetails);
			if (page != null) {

				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				model.addAttribute("deploymentLog", page);
				model.addAttribute("beginIndex", begin);
				model.addAttribute("endIndex", end);
				model.addAttribute("currentIndex", current);
				totalserviceentriescount = page.getTotalElements();
			}
			model.addAttribute("totalserviceentriescount", totalserviceentriescount);

			List<ServiceEntriesDto> pageList = WOBL
					.prepareListServiceEntries(ServiceEntriesService.listOpenServiceEntries(Status, pageNumber));

			model.addAttribute("ServiceEntries", pageList);
			
			model.addAttribute("SelectStatus", Status);
			model.addAttribute("SelectPage", pageNumber);

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("ServiceEntries Page:", e);
			e.printStackTrace();
		}
		return "newServiceEntries";
	}

	// Create add Service Entries
	@RequestMapping(value = "/addServiceEntries", method = RequestMethod.GET)
	public ModelAndView addServiceEntries() {
		
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails 			userDetails 			= null;
		HashMap<String, Object>     configuration			= null;
		HashMap<String, Object>     config					= null;
		HashMap<String, Object>     gpsConfiguration		= null;
		boolean						tallyConfig				= false;
		Integer						noOfBackDays			= null;
		String 						minBackDate				= null; 
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			gpsConfiguration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			config	 				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
			tallyConfig				= (boolean) config.getOrDefault(ServiceEntriesConfigurationConstants.TALLY_COMPANY_MASTER_IN_SE, false);
			noOfBackDays			= (Integer) config.getOrDefault(ServiceEntriesConfigurationConstants.NO_OF_BACK_DAYS, 0);
			minBackDate 			= DateTimeUtility.getDateBeforeNoOfDays(noOfBackDays);
			
			model.put("minBackDate",minBackDate);
			model.put("tallyConfig", tallyConfig);
			model.put("config", config);
			model.put("configuration", configuration);
			model.put("userName", userDetails.getFirstName());
			model.put("userId", userDetails.getId());
			model.put("gpsConfiguration", gpsConfiguration);
			model.put("companyId", userDetails.getCompany_id());
			
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Renewal_Reminder Page:", e);
			e.printStackTrace();
		}finally {
			userDetails 		= null;
			configuration		= null;
			gpsConfiguration	= null;
		}

		return new ModelAndView("addServiceEntries", model);
	}

	// save DB Service Entries
	@RequestMapping(value = "/saveServiceEntries", method = RequestMethod.POST)
	public ModelAndView saveServiceEntries(@ModelAttribute("command") ServiceEntriesDto ServiceEntries,
		@RequestParam("service_id") final String serviceIds,
		@RequestParam("job_serviceId_Name") final Long job_serviceId,
		@RequestParam("service_typetask") final String[] service_typetask,
		@RequestParam("service_subtypetask") final String[] service_subtypetask,
		@RequestParam("service_ROT") final String[] Job_ROT,
		@RequestParam("taskRemark") final String[] taskRemark,
		@RequestParam("service_ROT_number") final String[] Job_ROT_number, final HttpServletRequest request,
		RedirectAttributes redir) throws Exception {

		Map<String, Object> 			model 						= new HashMap<String, Object>();
		List<ServiceEntries> 			validateSE					= null;
		CustomUserDetails 				userDetails 				= null;
		HashMap<String, Object> 		configuration				= null;
		HashMap<String, Object> 		config						= null;
		boolean 						ServiceEntriesStatus 		= false;
		Long 							serviceEntries_Number 		= (long) 0;
		Long 							ServiceEntries_id 			= (long) 0;
		Integer							companyId					= 0;
		HashMap<Long, ServiceReminderDto> dtosMap1 					= null;
		ServiceReminderDto				serviceReminderDto 			= null;
		Integer							workOrderSubTaskId			= 0;
		long							sId							= 0;
		List<ServiceReminderDto>		serviceList					= null;
		
		try {
			String service_id[] = serviceIds.split(",");
			
			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			config 				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.getOrDefault(VendorTypeConfigurationConstants.USE_COMMON_VENDOR_TYPE, true)) {
				companyId			= 0;
			} else {
				companyId			= userDetails.getCompany_id();
			}
			List<JobType> jobTypeList = JobTypeService.listJobTypeByCompanyId(userDetails.getCompany_id());
			String TIDMandatory = "";

			// Check Vehicle Status Current IN ACTIVE OR NOT
			VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(ServiceEntries.getVid());
			if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE) {
				ServiceEntriesStatus = true;
			} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
				ServiceEntriesStatus = true;
			}

			if (ServiceEntriesStatus) {
				
				validateSE = ServiceEntriesService.validate_ServiceEntries(
						ServiceEntries.getVendor_id(), ServiceEntries.getInvoiceNumber(), ServiceEntries.getVid(),
						userDetails.getCompany_id());
				
				if((boolean) config.getOrDefault(WorkOrdersConfigurationConstants.SHOW_ADD_INVOICE_DEATILS, false)) {
					validateSE.clear();
				}
				
				// validate empty only save new service entries
				if (validateSE != null && !validateSE.isEmpty()) {
					model.put("already", true);
					return new ModelAndView("redirect:/addServiceEntries.in", model);
				} else {
					// get the ServiceEntries Dto to save ServiceEntries
					ServiceEntries WorkOrd = prepareServiceEntries(ServiceEntries);
					
					if(WorkOrd.getVendor_id() == 0) {
						Vendor vendor = new Vendor();
						vendor.setVendorTermId(PaymentTypeConstant.PAYMENT_TYPE_CASH);
						VendorSequenceCounter	sequenceCounter = vendorSequenceService.findNextVendorNumber(userDetails.getCompany_id());
						vendor.setVendorNumber((int) sequenceCounter.getNextVal());
						vendor.setVendorGSTRegisteredId(VendorGSTRegistered.VENDOR_GST_NOT_REGISTERED);
						vendor.setCompanyId(userDetails.getCompany_id());
						vendor.setCreated(new Date());
						vendor.setLastupdated(new Date());
						vendor.setVendorRemarks("This Create Service Entries To Vendor");
						vendor.setVendorName(ServiceEntries.getVendor_name());
						vendor.setVendorLocation(ServiceEntries.getVendor_location());
						vendor.setAutoVendor(true);
						
						VendorType VenType = VendorTypeService.getVendorType("SERVICE-VENDOR", companyId);
						if(VenType != null) {
							vendor.setVendorTypeId(VenType.getVendor_Typeid());
						}else {
							vendor.setVendorTypeId(0);
						}
						vendorService.addVendor(vendor);
						WorkOrd.setVendor_id(vendor.getVendorId());
					}
					
					WorkOrd.setCompanyId(userDetails.getCompany_id());
					ServiceEntriesSequenceCounter counter = serviceEntriesSequenceService
							.findNextServiceEntries_Number(userDetails.getCompany_id());
					serviceEntries_Number = counter.getNextVal();
					WorkOrd.setServiceEntries_Number(serviceEntries_Number);
					ServiceEntriesService.addServiceEntries(WorkOrd);
					
					
					// serviceEntriesSequenceService.updateNextServiceEntries_Number(serviceEntries_Number
					// + 1, userDetails.getCompany_id());
					model.put("saveServiceEntries", true);
					if (service_subtypetask != null || service_typetask != null || Job_ROT != null) {
						for (int i = 0; i < service_typetask.length; i++) {
							// System.out.println(quantity.length);

							ServiceEntriesTasks WRTask = new ServiceEntriesTasks();
							JobSubType JobSubType = null;
							WRTask.setService_typetaskId(Integer.parseInt("" + service_typetask[i]));
							if(service_typetask[i] != null && (service_subtypetask != null && service_subtypetask.length > 0)){
								if(!service_subtypetask[i].isEmpty()) {
								JobSubType = JobSubTypeService.getJobSubType(Integer.parseInt(service_subtypetask[i]));
								}
							}
							if (JobSubType != null) {
								WRTask.setService_subtypetask_id(Integer.parseInt(service_subtypetask[i]));
								// WRTask.setService_subtypetask(
								// JobSubType.getJob_ROT_number() + " - " + JobSubType.getJob_ROT());
							} else {
								// add New ROT Code in WorkOrder to JOB
								JobSubType DocType = new JobSubType();
								// System.out.println("" + Job_ROT[i]);
								DocType.setJob_Type("" + service_typetask[i]);
								for (JobType jobType : jobTypeList) {
									if (jobType.getJob_Type().equalsIgnoreCase("" + service_typetask[i])) {
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
								DocType.setJob_ROT_Service_Reminder(false);
								List<JobSubType> validate = JobSubTypeService.ValidateJobSubType("" + Job_ROT[i],
										"" + service_typetask[i], userDetails.getCompany_id());
								if (validate != null && !validate.isEmpty()) {
									// System.out.println("JOB ROT ALL READY");
									for (JobSubType jobSubTypeVD : validate) {

										WRTask.setService_subtypetask_id(jobSubTypeVD.getJob_Subid());
										// WRTask.setService_subtypetask(
										// jobSubTypeVD.getJob_ROT_number() + " - " + jobSubTypeVD.getJob_ROT());
										break;
									}
								} else {
									JobSubTypeService.addJobSubType(DocType);

									WRTask.setService_subtypetask_id(DocType.getJob_Subid());
									// WRTask.setService_subtypetask(
									// DocType.getJob_ROT_number() + " - " + DocType.getJob_ROT());

								}
							}
								
								if(taskRemark != null && taskRemark.length > 0) {
									WRTask.setTaskRemark(taskRemark[i]);							
								}
								
								WRTask.setVid(WorkOrd.getVid());
								WRTask.setServiceEntries(WorkOrd);
								WRTask.setTotalpart_cost(0.0);
								WRTask.setTotallaber_cost(0.0);
								WRTask.setTotaltask_cost(0.0);
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
							
							
							ServiceEntriesService.addServiceEntriesTask(WRTask);

						}
					}
					
					
					// Add Service Id to workorderTask
					if(service_subtypetask.length > 0 ) {
						if(!service_subtypetask[0].isEmpty()) {
						workOrderSubTaskId = Integer.parseInt(""+service_subtypetask[0]);
						}
					}
					
					if (service_id != null && !serviceIds.isEmpty()) {
						dtosMap1 = workOrdersService.getJobtypeAndSubtypeFromServiceReminderId(serviceIds, userDetails.getCompany_id());
						
						for (int j = 0; j < service_id.length; j++) {
							
							serviceReminderDto	= dtosMap1.get(Long.parseLong(service_id[j]));
							
							if(!workOrderSubTaskId.equals(serviceReminderDto.getServiceSubTypeId())) {
							
								ServiceEntriesTasks WRTaskServReminder = new ServiceEntriesTasks();
								
								if(serviceReminderDto.getServiceTypeId() != null) {
								WRTaskServReminder.setService_typetaskId(serviceReminderDto.getServiceTypeId());	
								}
								if(serviceReminderDto.getServiceSubTypeId() != null) {
								WRTaskServReminder.setService_subtypetask_id(serviceReminderDto.getServiceSubTypeId());	
								}
								
								WRTaskServReminder.setServiceEntries(WorkOrd);
								WRTaskServReminder.setVid(WorkOrd.getVid());
								WRTaskServReminder.setTotalpart_cost(0.0);
								WRTaskServReminder.setTotallaber_cost(0.0);
								WRTaskServReminder.setTotaltask_cost(0.0);
								WRTaskServReminder.setCompanyId(userDetails.getCompany_id());
								WRTaskServReminder.setService_id(Long.parseLong(service_id[j]+""));
								
								ServiceEntriesService.addServiceEntriesTask(WRTaskServReminder);
							
							}
							
						}
					}	
					
					
					// update the Current Odometer vehicle Databases tables
					try {
						Integer currentODDMETER = vehicleService
								.updateCurrentOdoMeterGETVehicleToCurrentOdometer(WorkOrd.getVid());
						if(currentODDMETER != null && WorkOrd.getVehicle_Odometer() != null ) {
							if (currentODDMETER < WorkOrd.getVehicle_Odometer()) {
								
								vehicleService.updateCurrentOdoMeter(WorkOrd.getVid(), WorkOrd.getVehicle_Odometer(), userDetails.getCompany_id());
								// update current Odometer update Service Reminder
								ServiceReminderService.updateCurrentOdometerToServiceReminder(WorkOrd.getVid(),
										WorkOrd.getVehicle_Odometer(), userDetails.getCompany_id());
								
								serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(WorkOrd.getVid(), userDetails.getCompany_id(), userDetails.getId());
								if(serviceList != null) {
									for(ServiceReminderDto list : serviceList) {
										
										if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
											
											ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
											//emailAlertQueueService.sendEmailServiceOdometer(list);
											//smsAlertQueueService.sendSmsServiceOdometer(list);
										}
									}
								}
								
								VehicleOdometerHistory vehicleUpdateHistory = VOHBL
										.prepareOdometerGetHistoryToServiceEntries(WorkOrd);
								vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
								vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
							}
						}
					} catch (Exception e) {
						LOGGER.error("Renewal Reminder:", e);
						e.printStackTrace();

					}
					ServiceEntries_id = WorkOrd.getServiceEntries_id();
				}

			} // checking Vehicle Status
			else {
				String Link = "";
				if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
					Link += "  <a href=\"getTripsheetDetails.in?tripSheetID=" + CheckVehicleStatus.getTripSheetID()
							+ "\" target=\"_blank\">TS-"
							+ driverService.getCurrentTripSheetNumber(CheckVehicleStatus.getTripSheetID(),
									userDetails.getCompany_id())
							+ "  <i class=\"fa fa-external-link\"></i></a>";

				} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
					
					WorkOrders work = workOrdersService.getWorkOrders(CheckVehicleStatus.getTripSheetID());
					if(work != null) {
						Link += " <a href=\"showWorkOrder?woId=" + CheckVehicleStatus.getTripSheetID()
						+ "\" target=\"_blank\">WO-" + workOrdersService
						.getWorkOrders(CheckVehicleStatus.getTripSheetID()).getWorkorders_Number()
						+ "  <i class=\"fa fa-external-link\"></i></a> ";
					}else {
						Link += " <a href=\"showWorkOrder?woId=" + CheckVehicleStatus.getTripSheetID()
						+ "\" target=\"_blank\">WO-<i class=\"fa fa-external-link\"></i></a> ";
					}

				}

				TIDMandatory += " <span> This " + CheckVehicleStatus.getVehicle_registration() + " Vehicle in "
						+ CheckVehicleStatus.getVehicle_Status() + " Status you can't create WorkOrder " + " " + Link
						+ "" + " </span> ,";
				redir.addFlashAttribute("VMandatory", TIDMandatory);
				model.put("closeStatus", true);
				return new ModelAndView("redirect:/addServiceEntries.in", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newServiceEntries/1/1.in", model);
		}

		// return new ModelAndView("newServiceEntries", model);
		return new ModelAndView("redirect:/ServiceEntriesParts.in?SEID=" + ServiceEntries_id + "", model);
	}

	// Service Entries New to Show Page In add Service Entries part
	@RequestMapping(value = "/ServiceEntriesParts", method = RequestMethod.GET)
	public ModelAndView addServiceEntriesParts(@RequestParam("SEID") final Long ServiceEntries_id, final HttpServletRequest request) throws Exception {
			
		Map<String, Object> 					model 									= null;
		HashMap<String, Object>     			configuration							= null;
		HashMap<String, Object>     			config									= null;
		CustomUserDetails 						userDetails								= null;
		List<ServiceEntriesTasksDto>			serviceEntriesTasksDtoList				= null;
		List<ServiceEntriesTasksToPartsDto>		serviceEntriesTasksToPartsDtoList		= null;
		List<ServiceEntriesTasksToLabor>		serviceEntriesTasksToLabor				= null;
		ServiceEntriesDto 						serviceEntries							= null;
		int										noOfBackDaysToReopenSE					= 0;
		boolean									reOpenSE								= true;
		long									differenceBetTwoDate					= 0;
		try {
			
			model 					= new HashMap<String, Object>();
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			config	 				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
			noOfBackDaysToReopenSE	= (Integer) config.getOrDefault(ServiceEntriesConfigurationConstants.NO_OF_BACK_DAYS_TO_REOPEN_SE, 0);
			
			serviceEntries 						= ServiceEntriesService.getServiceEntriesDetails(ServiceEntries_id, userDetails.getCompany_id());
			serviceEntriesTasksDtoList 			= ServiceEntriesService.getServiceEntriesTasks(ServiceEntries_id, userDetails.getCompany_id());
			serviceEntriesTasksToPartsDtoList 	= ServiceEntriesService.getServiceEntriesTasksToParts(ServiceEntries_id);
			serviceEntriesTasksToLabor			= ServiceEntriesService.getServiceEntriesTasksToLabor(ServiceEntries_id);
			
			if (serviceEntries != null) {
				ServiceEntriesDto work = WOBL.Show_ServiceEntries(serviceEntries);
				
				model.put("ServiceEntries", work);
				model.put("ServiceEntriesTask",serviceEntriesTasksDtoList);
				model.put("ServiceEntriesTaskPart", serviceEntriesTasksToPartsDtoList);
				model.put("ServiceEntriesTaskLabor", serviceEntriesTasksToLabor);
				model.put("configuration", configuration);
				model.put("config", config);
				
				if( (serviceEntries.getInvoiceNumber().isEmpty()) || (serviceEntries.getService_paiddateOn() == null) || (serviceEntries.getInvoiceDateOn() == null)) {
					model.put("ValidateInvoiceDetails", true);
				} else {
					model.put("ValidateInvoiceDetails", false);
				}
				
				
				if (work.getServiceEntries_statusId() == ServiceEntriesType.SERVICE_ENTRIES_STATUS_COMPLETE) {
					
					if(work.getCompleted_dateOn() != null) {
						differenceBetTwoDate 				= DateTimeUtility.getExactDayDiffBetweenTwoDates(DateTimeUtility.getTimeStampFromDate(work.getCompleted_dateOn()),DateTimeUtility.getCurrentTimeStamp());
						}
					
					if(differenceBetTwoDate > noOfBackDaysToReopenSE) {
						reOpenSE	= false;
					}
					if(serviceEntries.isPendingForTally()) {
						reOpenSE	= false;
					}
					model.put("reOpenSE", reOpenSE);
					return new ModelAndView("addServiceEntriesPartsCOMPLETE", model);
				}
			}
			
			return new ModelAndView("addServiceEntriesParts", model);
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
		
		
	}

	@RequestMapping(value = "/PrintServiceEntries", method = RequestMethod.GET)
	public ModelAndView showServiceEntriesPrint(@RequestParam("id") final Long id, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>     configuration			= null;
		try {

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			model.put("configuration", configuration);
			
			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			ServiceEntriesDto work = WOBL
					.getServiceEntries(ServiceEntriesService.getServiceEntriesDetails(id, userDetails.getCompany_id()));
			model.put("ServiceEntries", work);

			model.put("ServiceEntriesTask",
					ServiceEntriesService.getServiceEntriesTasks(id, userDetails.getCompany_id()));

			model.put("ServiceEntriesTaskPart", ServiceEntriesService.getServiceEntriesTasksToParts(id));

			model.put("ServiceEntriesTaskLabor", ServiceEntriesService.getServiceEntriesTasksToLabor(id));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("showServiceEntriesPrint", model);
	}

	// save Service Entries Task parts and Cost total
	@RequestMapping(value = "/saveServiceEntriesTaskPart", method = RequestMethod.POST)
	public ModelAndView saveServiceEntriesTaskPart(
			@ModelAttribute("command") ServiceEntriesTasksToParts ServiceEntriesTasksToParts,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			// save ServiceEntries Task to Parts value
			ServiceEntriesTasksToParts workTaskToParts = prepareServiceEntriesTaskToPart(ServiceEntriesTasksToParts);
			workTaskToParts.setCompanyId(userDetails.getCompany_id());
			ServiceEntriesService.addServiceEntriesTaskToParts(workTaskToParts);

			try {
				ServiceEntriesService.updateServiceEntriesTask_TotalPartCost(workTaskToParts.getServicetaskid());

				// Update Main ServiceEntries Total cost
				try {
					// update service Entries main total cost
					ServiceEntriesService
							.updateServiceEntriesMainTotalCost(ServiceEntriesTasksToParts.getServiceEntries_id());

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				java.sql.Date updateDate = new java.sql.Date( new Date().getTime());

				// update the ServiceEntries to inProcess
				ServiceEntriesService.updateServiceEntriesProcess(ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS,
						ServiceEntriesTasksToParts.getServiceEntries_id(), userDetails.getCompany_id(), updateDate, userDetails.getId());

			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("redirect:/newServiceEntries/1/1.in?danger=true");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newServiceEntries/1/1.in?danger=true");
		}

		// return new ModelAndView("addServiceEntriesParts", model);
		return new ModelAndView(
				"redirect:/ServiceEntriesParts.in?SEID=" + ServiceEntriesTasksToParts.getServiceEntries_id() + "",
				model);
	}

	// save Service Entries Task Labor and Cost total
	@RequestMapping(value = "/saveServiceEntriesTaskLabor", method = RequestMethod.POST)
	public ModelAndView saveServiceEntriesTaskLabor(
			@ModelAttribute("command") ServiceEntriesTasksToLabor ServiceEntriesTasksToLabor,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		Long ServiceEntries_id = null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			// save ServiceEntries Task to Parts value
			ServiceEntriesTasksToLabor workTaskToLabor = prepareServiceEntriesTaskToLabor(ServiceEntriesTasksToLabor);
			workTaskToLabor.setCompanyId(userDetails.getCompany_id());
			ServiceEntriesService.addServiceEntriesTaskToLabor(workTaskToLabor);
			// get Tasks Total cost
			try {

				ServiceEntriesService.updateServiceEntriesTask_TotalLaborCost(workTaskToLabor.getServicetaskid());

				// Update Main ServiceEntries Total cost
				try {

					ServiceEntriesService
							.updateServiceEntriesMainTotalCost(ServiceEntriesTasksToLabor.getServiceEntries_id());

				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/newServiceEntries/1/1.in?danger=true");
				}
				
				java.sql.Date updateDate = new java.sql.Date( new Date().getTime());

				// update the ServiceEntries to inProcess
				ServiceEntriesService.updateServiceEntriesProcess(ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS,
						ServiceEntriesTasksToLabor.getServiceEntries_id(), userDetails.getCompany_id(), updateDate, userDetails.getId());

			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("redirect:/newServiceEntries/1/1.indanger=true");
			}

			ServiceEntries_id = workTaskToLabor.getServiceEntries_id();

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newServiceEntries/1/1.indanger=true");
		}

		return new ModelAndView("redirect:/ServiceEntriesParts.in?SEID=" + ServiceEntries_id + "", model);
	}

	/* Save to ServiceEntries Task and Part the values in parts */
	@RequestMapping(value = "/saveServiceEntriesTaskCompletion", method = RequestMethod.GET)
	public ModelAndView saveServiceEntriesTaskCompletion(
			@ModelAttribute("command") ServiceEntriesTasks ServiceEntriesTasks, final HttpServletRequest request)
			throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		Long ServiceEntries_id = null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			// save ServiceEntries Task to Parts value
			ServiceEntriesTasks workTask = ServiceEntriesService
					.getServiceEntriesCompletion(ServiceEntriesTasks.getServicetaskid(), userDetails.getCompany_id());
			// get Tasks task complete Total cost

			// System.out.println(workTask.getMark_complete());
			// System.out.println(workTask.getServiceEntries().getServiceEntries_id());
			if (workTask.getMark_complete() == null || workTask.getMark_complete() == 0) {
				ServiceEntriesService.updateServiceEntriesTask_Completion(1, ServiceEntriesTasks.getServicetaskid(),
						userDetails.getCompany_id());
			} else {
				ServiceEntriesService.updateServiceEntriesTask_Completion(0, ServiceEntriesTasks.getServicetaskid(),
						userDetails.getCompany_id());
			}

			ServiceEntries_id = workTask.getServiceEntries().getServiceEntries_id();

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newServiceEntries/1/1.indanger=true");
		}

		return new ModelAndView("redirect:/ServiceEntriesParts.in?SEID=" + ServiceEntries_id + "", model);

	}

	/* Save to ServiceEntries Task and Part the values in parts */
	@RequestMapping(value = "/ServiceEntries_INPROCESS", method = RequestMethod.GET)
	public ModelAndView ServiceEntries_INPROCESS(@ModelAttribute("command") ServiceEntries ServiceEntries,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			
			ServiceEntries	entries	= 	ServiceEntriesService.getServiceEntries(ServiceEntries.getServiceEntries_id());
			
			//ServiceEntriesTxnChecker	serviceEntriesTxnChecker	=	serviceEntriesTxnCheckerService.getServiceEntriesTxnChecker(entries.getServiceEntries_id(), entries.getVid());
			
			
			VehicleDto	CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(entries.getVid(), userDetails.getCompany_id());
			ValueObject	ownerShipObject	= null;
			if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED && entries.getTotalservice_cost() > 0){
				ownerShipObject	= new ValueObject();
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, entries.getServiceEntries_id());
				ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, entries.getVid());
				ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, entries.getTotalservice_cost());
				ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
				ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_SERVICE_ENTRY);
				ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, userDetails.getCompany_id());
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, sqlFormat.parse(sqlFormat.format(entries.getCompleted_date())));
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Service Entry");
				ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Service Number : "+entries.getServiceEntries_Number());
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
				ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, userDetails.getId());
				ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, entries.getTotalservice_cost());
				
				vehicleAgentIncomeExpenseDetailsService.deleteVehicleAgentIncomeExpense(ownerShipObject);
			}
			
			java.sql.Date updateDate = new java.sql.Date( new Date().getTime());
			
			ServiceEntriesService.updateServiceEntriesProcess(ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS,
					ServiceEntries.getServiceEntries_id(), userDetails.getCompany_id(), updateDate, userDetails.getId());
			
			VehicleExpenseDetails	vehicleExpenseDetails	= vehicleExpenseDetailsService.getVehicleExpenseDetails(entries.getServiceEntries_id(), userDetails.getCompany_id() ,entries.getVid(), VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
			
			if(vehicleExpenseDetails != null) {
				ValueObject		valueObject	= new ValueObject();
				valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
				valueObject.put("txnAmount", entries.getTotalservice_cost());
				valueObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(entries.getCompleted_date()));
				valueObject.put("txnTypeId", entries.getServiceEntries_id());
				valueObject.put("vid", entries.getVid());
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
			

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newServiceEntries/1/1.indanger=true");
		}

		return new ModelAndView("redirect:/ServiceEntriesParts.in?SEID=" + ServiceEntries.getServiceEntries_id() + "",
				model);

	}

	/* Save to ServiceEntries Task and Part the values in parts */
	@RequestMapping(value = "/ServiceEntries_COMPLETE", method = RequestMethod.GET)
	public ModelAndView ServiceEntries_COMPLETE(@ModelAttribute("command") ServiceEntries ServiceEntries,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>     				configuration							= null;
		ServiceEntriesDto 							entries 								= null;
		List<EmailAlertQueue>  						pendingList								= null;
		List<SmsAlertQueue>  						pendingList1							= null;
		EmailAlertQueue 							email 									= null;
		SmsAlertQueue 								sms 									= null;
		ValueObject									ownerShipObject							= null;
		java.util.Date 								currentDate 							= null;
		DateFormat 									simpleDateFormat 						= null;
		java.util.Date 								toDate 									= null;
		java.sql.Date 								COMPLETE_date 							= null;
		CustomUserDetails 							userDetails								= null;
		VehicleProfitAndLossTxnChecker				profitAndLossTxnChecker					= null;
		VehicleDto 									CheckVehicleStatus						= null;
		VehicleAgentTxnChecker						agentTxnChecker							= null;
		ServiceEntriesDto 							serviceEntriesDto						= null;
		List<ServiceEntriesTasksDto> 				ServiceEntriesTasks						= null;
		List<ServiceEntriesTasksToPartsDto> 		serviceEntriesTasksToPartsDtoList		= null;
		List<ServiceEntriesTasksToLabor> 			serviceEntriesTasksToLaborList			= null;
		List<ServiceReminder> 						serviceReminder							= null;
		ServiceReminder 							service									= null;
		Calendar 									calendar1								= null;
		java.util.Date 								alertDate1								= null; 
		java.util.Date 								alertBeforeDate							= null;
		java.util.Date 								alertAfterDate 							= null;
		IssuesDto 									issuesDto								= null;
		Issues 										Save_Issues								= null;
		IssuesTasks 								issueTask 								= null;
		java.util.Date 								currentDateUpdateIssues 				= null;
		Timestamp 									currentIsuueDate						= null;
		int											noOfBackDaysToReOpenSE					= 0;
		boolean										reOpenSE								= true;
		long										differenceBetTwoDate					= 0;
		HashMap<String, Object>     				config									= null;
		try {	
			
			currentDate 			= new java.util.Date();
			simpleDateFormat 		= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			toDate 					= dateFormatTime.parse(simpleDateFormat.format(currentDate));
			COMPLETE_date 			= new java.sql.Date(toDate.getTime());
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			config	 				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
			currentDateUpdateIssues = new java.util.Date();
			currentIsuueDate 		= new java.sql.Timestamp(currentDateUpdateIssues.getTime());
			
			entries 				= ServiceEntriesService.getServiceEntriesDetails(ServiceEntries.getServiceEntries_id(),userDetails.getCompany_id());
			noOfBackDaysToReOpenSE	= (Integer) config.getOrDefault(ServiceEntriesConfigurationConstants.NO_OF_BACK_DAYS_TO_REOPEN_SE, 0);
			
			final SimpleDateFormat format1 	= new SimpleDateFormat("yyyy-MM-dd");
			
			if (entries != null) {
				
				entries.setCompanyId(userDetails.getCompany_id());

				if(entries.getCompleted_dateOn() != null) {
					differenceBetTwoDate = DateTimeUtility.getExactDayDiffBetweenTwoDates(DateTimeUtility.getTimeStampFromDate(entries.getCompleted_dateOn()),DateTimeUtility.getCurrentTimeStamp());
					if(differenceBetTwoDate > noOfBackDaysToReOpenSE) {
						reOpenSE	= false;
					}
				}
				
				if(entries.isPendingForTally()) {
					reOpenSE	= false;
				}
				
				if(entries.getTotalservice_cost() > 0) {
					ValueObject		object	= new ValueObject();
					
					object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					object.put(VehicleProfitAndLossDto.TRANSACTION_ID, entries.getServiceEntries_id());
					object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
					object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
					object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					object.put(VehicleProfitAndLossDto.TRANSACTION_VID, entries.getVid());
					object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, entries.getServiceEntries_id());
					
					profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);
					
					vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);
					
					CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(entries.getVid(), userDetails.getCompany_id()); 
					
					if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED ){
						ownerShipObject	= new ValueObject();
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, entries.getServiceEntries_id());
						ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, entries.getVid());
						ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, entries.getTotalservice_cost());
						ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
						ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_SERVICE_ENTRY);
						ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, userDetails.getCompany_id());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, sqlFormat.parse(sqlFormat.format(currentDate)));
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Service Entry");
						ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Service Number : "+entries.getServiceEntries_Number());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
						ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, userDetails.getId());
						ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, -entries.getTotalservice_cost());
						
						agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
						vehicleAgentTxnCheckerService.save(agentTxnChecker);
						
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
						
					}
					
				}

				serviceEntriesDto = WOBL.Show_ServiceEntries(entries);
				
				ServiceEntriesService.updateServiceEntriesCOMPLETE_date( ServiceEntriesType.SERVICE_ENTRIES_STATUS_COMPLETE, COMPLETE_date, ServiceEntries.getServiceEntries_id(), userDetails.getCompany_id(), userDetails.getId());

				
				ServiceEntriesTasks 				= ServiceEntriesService.getServiceEntriesTasks(ServiceEntries.getServiceEntries_id(), userDetails.getCompany_id());
				serviceEntriesTasksToPartsDtoList 	= ServiceEntriesService.getServiceEntriesTasksToParts(ServiceEntries.getServiceEntries_id());
				serviceEntriesTasksToLaborList		= ServiceEntriesService.getServiceEntriesTasksToLabor(ServiceEntries.getServiceEntries_id());

				
				model.put("ServiceEntries", serviceEntriesDto);
				model.put("ServiceEntriesTask", ServiceEntriesTasks);
				model.put("ServiceEntriesTaskPart", serviceEntriesTasksToPartsDtoList);
				model.put("ServiceEntriesTaskLabor",serviceEntriesTasksToLaborList );
				model.put("config", config);
				
				
				// update Service Reminder into WorkOders
				if (ServiceEntriesTasks != null && !ServiceEntriesTasks.isEmpty()) {
					// many WorkOrdersTask and Update to that complete Time to
					// service Reminder
					for (ServiceEntriesTasksDto workOrdersTasksToServiceRC : ServiceEntriesTasks) {

						serviceReminder = ServiceReminderService.listSearchWorkorderToServiceReminder(serviceEntriesDto.getVid(),workOrdersTasksToServiceRC.getService_typetaskId(), workOrdersTasksToServiceRC.getService_subtypetask_id(),userDetails.getCompany_id());
						
						if (serviceReminder != null && !serviceReminder.isEmpty()) {
							for (ServiceReminder serviceReminderFindSameWorkorder : serviceReminder) {
								service 	= WOBL.ServiceEntriesTOServiceReminder(serviceReminderFindSameWorkorder, serviceEntriesDto);
								pendingList	= emailAlertQueueService.getAllEmailAlertQueueDetailsById(serviceReminderFindSameWorkorder.getService_id());
								service.setCompanyId(userDetails.getCompany_id());
								
								ServiceReminderService.updateServiceReminder(service);
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
											
											calendar1 		= Calendar.getInstance();
											calendar1.setTime(service.getTime_servicedate());
											calendar1.add(Calendar.DAY_OF_YEAR, -emailAlertQueue.getAlertBeforeValues());
											
											alertDate1 		= format1.parse(format1.format(calendar1.getTime()));
											alertBeforeDate =  new Date(alertDate1.getTime());
											
											email.setAlertBeforeDate(alertBeforeDate+"");
											email.setAlertScheduleDate(alertBeforeDate);
										
											emailAlertQueueService.updateEmailAlertQueue(email);
										} else {
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
											email.setAlertAfterValues(emailAlertQueue.getAlertAfterValues());
											email.setServiceDate(service.getTime_servicedate());
												
											calendar1	= Calendar.getInstance();
											calendar1.setTime(service.getTime_servicedate());
											calendar1.add(Calendar.DAY_OF_YEAR, emailAlertQueue.getAlertAfterValues());
											
											alertDate1 		= format1.parse(format1.format(calendar1.getTime()));
											alertAfterDate 	=  new Date(alertDate1.getTime());
											
											email.setAlertAfterDate(alertAfterDate+"");
											email.setAlertScheduleDate(alertAfterDate);
										
											emailAlertQueueService.updateEmailAlertQueue(email);
										  
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
												
											calendar1 = Calendar.getInstance();
											calendar1.setTime(service.getTime_servicedate());
											calendar1.add(Calendar.DAY_OF_YEAR, -smsAlertQueue.getAlertBeforeValues());
											
											alertDate1 		= format1.parse(format1.format(calendar1.getTime()));
											alertBeforeDate =  new Date(alertDate1.getTime());
											
											sms.setAlertBeforeDate(alertBeforeDate+"");
											sms.setAlertScheduleDate(alertBeforeDate);
										
											smsAlertQueueService.updateSmsAlertQueue(sms);
									  } else {
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
											sms.setAlertAfterValues(smsAlertQueue.getAlertAfterValues());
											sms.setServiceDate(service.getTime_servicedate());
												
											calendar1 		= Calendar.getInstance();
											calendar1.setTime(service.getTime_servicedate());
											calendar1.add(Calendar.DAY_OF_YEAR, smsAlertQueue.getAlertAfterValues());
											
											alertDate1 		= format1.parse(format1.format(calendar1.getTime()));
											alertAfterDate	=  new Date(alertDate1.getTime());
											
											sms.setAlertAfterDate(alertAfterDate+"");
											sms.setAlertScheduleDate(alertAfterDate);
										
											smsAlertQueueService.updateSmsAlertQueue(sms);
										  
									       }
										}	
									}	
								}
							}
						}
					}
				
				if (ServiceEntries.getServiceEntries_id() != null && ServiceEntries.getServiceEntries_id() != 0) {
					issuesDto = issuesService.GET_SE_ID_TO_ISSUES_DETAILS(ServiceEntries.getServiceEntries_id(), userDetails.getCompany_id());
					if (issuesDto != null) {
						try {
							Save_Issues = new Issues();
							issueTask 	= new IssuesTasks();
							
							Save_Issues.setISSUES_ID(issuesDto.getISSUES_ID());
							
							issueTask.setISSUES_TASK_STATUS_ID(issuesDto.getISSUES_STATUS_ID());
							issueTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_RESOLVED);
							issueTask.setISSUES_CREATEBY_ID(userDetails.getId());
							issueTask.setISSUES_CREATED_DATE(currentIsuueDate);
							issueTask.setISSUES(Save_Issues);
							issueTask.setCOMPANY_ID(userDetails.getCompany_id());
							issueTask.setISSUES_REASON("ServiceEntries To Issues Resovled");
							
							issuesService.registerNew_IssuesTasks(issueTask);
							issuesService.update_Create_SE_Issues_Status( IssuesTypeConstant.ISSUES_STATUS_RESOLVED,userDetails.getId(), toDate, ServiceEntries.getServiceEntries_id(), issuesDto.getISSUES_ID());
						
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
				if(entries.getTotalservice_cost() > 0) {	
					ValueObject		valueObject	= new ValueObject();
					
					valueObject.put("serviceEntries", entries);
					valueObject.put("userDetails", userDetails);
					valueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
					valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
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
					}.start();;
					
				  }
				
				if(ownerShipObject != null){
					vehicleAgentIncomeExpenseDetailsService.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
				}
				model.put("reOpenSE", reOpenSE);
				model.put("configuration", configuration);
				
			  }
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			configuration							= null;
			entries 								= null;
			pendingList								= null;
			pendingList1							= null;
			email 									= null; //9356887149
			sms 									= null;
			ownerShipObject							= null;
			currentDate 							= null;
			simpleDateFormat 						= null;
			toDate 									= null;
			COMPLETE_date 							= null;
			userDetails								= null;
			profitAndLossTxnChecker					= null;
			CheckVehicleStatus						= null;
			agentTxnChecker							= null;
			serviceEntriesDto						= null;
			ServiceEntriesTasks						= null;
			serviceEntriesTasksToPartsDtoList		= null;
			serviceEntriesTasksToLaborList			= null;
			serviceReminder							= null;
			service									= null;
			calendar1								= null;
			alertDate1								= null; 
			alertBeforeDate							= null;
			alertAfterDate 							= null;
			issuesDto								= null;
			Save_Issues								= null;
			issueTask 								= null;
			currentDateUpdateIssues 				= null;
			currentIsuueDate						= null;
			noOfBackDaysToReOpenSE					= 0;
			reOpenSE								= true;
			differenceBetTwoDate					= 0;
			config									= null;
		}

		return new ModelAndView("addServiceEntriesPartsCOMPLETE", model);
	}

	/* Save to ServiceEntries Task and Part the values in parts */
	@RequestMapping(value = "/ServiceEntriesRount_cost", method = RequestMethod.POST)
	public ModelAndView ServiceEntriesRount_cost(@RequestParam("SId") final Long ServiceEntries_id,
			@RequestParam("TOTALROUNT_COST") final Double TOTALROUNT_COST, final HttpServletResponse result)
			throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		try {

			if (ServiceEntries_id != null) {
				if (TOTALROUNT_COST != null && TOTALROUNT_COST != 0.0) {
					java.util.Date currentDate = new java.util.Date();
					DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					java.util.Date toDate = dateFormatTime.parse(ft.format(currentDate));
					java.sql.Date COMPLETE_date = new java.sql.Date(toDate.getTime());
					ServiceEntriesService.updateServiceEntries_ROUNT_OF_COST_COMPLETE_date(TOTALROUNT_COST, "COMPLETE",
							COMPLETE_date, ServiceEntries_id);
				}
				model.put("SUCCESSFULLY_ROUND", true);
			} else {
				return new ModelAndView("redirect:/NotFound.in");
			}
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:ServiceEntriesParts?SEID=" + ServiceEntries_id + "", model);
	}

	/* Save to ServiceEntries Task and Part the values in parts */
	@RequestMapping(value = "/ServiceEntriesOpenTask", method = RequestMethod.POST)
	public ModelAndView saveServiceEntriesOpenTask(@ModelAttribute("command") ServiceEntriesDto ServiceEntries,
			ServiceEntriesTasks ServiceEntriesTask, final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			ServiceEntries WorkOrd = prepareServiceEntriesToAddTask(ServiceEntries);
			ServiceEntriesTasks WorkTOTask = new ServiceEntriesTasks();

			WorkTOTask.setService_typetaskId(ServiceEntriesTask.getService_typetaskId());

			JobSubType JobSubType = JobSubTypeService.getJobSubType(ServiceEntriesTask.getService_subtypetask_id());
			if (JobSubType != null) {
				WorkTOTask.setService_subtypetask_id(ServiceEntriesTask.getService_subtypetask_id());
				// WorkTOTask.setService_subtypetask(JobSubType.getJob_ROT_number() + " - " +
				// JobSubType.getJob_ROT());
			}

			WorkTOTask.setVid(ServiceEntries.getVid());
			WorkTOTask.setTotalpart_cost(0.0);
			WorkTOTask.setTotallaber_cost(0.0);
			WorkTOTask.setTotaltask_cost(0.0);
			WorkTOTask.setServiceEntries(WorkOrd);
			WorkTOTask.setCompanyId(userDetails.getCompany_id());
			WorkTOTask.setTaskRemark(ServiceEntriesTask.getTaskRemark());
			

			ServiceEntriesService.addServiceEntriesTask(WorkTOTask);

		} catch (Exception e) {
			System.err.println("Exception : " + e);
			return new ModelAndView("redirect:/newServiceEntries/1/1.in?danger=true");
		}

		return new ModelAndView("redirect:/ServiceEntriesParts.in?SEID=" + ServiceEntries.getServiceEntries_id() + "",
				model);

	}

	/* show main page of ServiceEntries IN Process */
	@RequestMapping(value = "/ServiceEntriesINPROCESS", method = RequestMethod.GET)
	public ModelAndView ServiceEntriesINPROCESS() {

		Map<String, Object> model = new HashMap<String, Object>();

		String ServiceEntries_open = "INPROCESS";

		// show the ServiceEntries List
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			model.put("ServiceEntries",
					WOBL.prepareListServiceEntries(ServiceEntriesService.listOpenServiceEntries((short) 1, 1)));

			model.put("ServiceEntriesCount", ServiceEntriesService.countServiceEntries(userDetails.getCompany_id()));
			model.put("StatuesCount",
					ServiceEntriesService.countServiceEntriestatues(ServiceEntries_open, userDetails.getCompany_id()));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("inprocessServiceEntries", model);
	}

	/* show main page of ServiceEntries ON-HOLD */
	@RequestMapping(value = "/ServiceEntriesCOMPLETE", method = RequestMethod.GET)
	public ModelAndView ServiceEntriesCOMPLETE() {

		Map<String, Object> model = new HashMap<String, Object>();
		String ServiceEntries_open = "COMPLETE";
		CustomUserDetails userDetails = null;
		// show the ServiceEntries List
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("ServiceEntries",
					WOBL.prepareListServiceEntries(ServiceEntriesService.listOpenServiceEntries((short) 1, 1)));

			model.put("ServiceEntriesCount", ServiceEntriesService.countServiceEntries(userDetails.getCompany_id()));
			model.put("StatuesCount",
					ServiceEntriesService.countServiceEntriestatues(ServiceEntries_open, userDetails.getCompany_id()));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("closedServiceEntries", model);
	}

	// upload Service Document
	@RequestMapping(value = "/uploadServiceDocument", method = RequestMethod.POST)
	public ModelAndView uploadServiceDocument(@ModelAttribute("command") ServiceEntriesDocument serviceEntriesDocument,
			@RequestParam("input-file-preview") MultipartFile file) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			org.fleetopgroup.persistence.document.ServiceEntriesDocument serviceDocument = new org.fleetopgroup.persistence.document.ServiceEntriesDocument();
			try {
				serviceDocument.setServiceEntries_id(serviceEntriesDocument.getServiceEntries_id());

				if (!file.isEmpty()) {
					
					try {

						byte[] bytes = file.getBytes();
						serviceDocument.setService_filename(file.getOriginalFilename());
						serviceDocument.setService_content(bytes);
						serviceDocument.setService_contentType(file.getContentType());

						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

						serviceDocument.setCreated(toDate);
						serviceDocument.setLastupdated(toDate);
						serviceDocument.setCompanyId(userDetails.getCompany_id());
						serviceDocument.setCreatedById(userDetails.getId());
						serviceDocument.setLastModifiedById(userDetails.getId());
					} catch (IOException e) {

					}
				} else {
					// messages
					return new ModelAndView("redirect:/RenewalReminder.in?danger=true");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			org.fleetopgroup.persistence.document.ServiceEntriesDocument doc = documentService.getServiceEntriesDocByServiceId(serviceEntriesDocument.getServiceEntries_id(), userDetails.getCompany_id());
			
			if (doc != null) {
				
				serviceDocument.setService_documentid(doc.getService_documentid());
				//documentService.saveServiceEntriesDoc(serviceDocument);
				mongoOperations.save(serviceDocument);
				
				ServiceEntriesService.Update_ServiceEntries_Docuemnt_AvailableValue(
						serviceDocument.getService_documentid(), true, serviceEntriesDocument.getServiceEntries_id(),
						userDetails.getCompany_id());
				

			} else {
				
				documentService.saveServiceEntriesDoc(serviceDocument);
				
				ServiceEntriesService.Update_ServiceEntries_Docuemnt_AvailableValue(
						serviceDocument.getService_documentid(), true, serviceEntriesDocument.getServiceEntries_id(),
						userDetails.getCompany_id());
				
			}

			model.put("UploadSuccess", true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newServiceEntries/1/1.in?danger=true");
		}

		return new ModelAndView(
				"redirect:/ServiceEntriesParts.in?SEID=" + serviceEntriesDocument.getServiceEntries_id() + "", model);

	}

	// Should be Download Service document
	@RequestMapping("/download/serviceDocument/{serviceEntries_id}")
	public String downloadReminder(@PathVariable("serviceEntries_id") Long serviceEntries_id,
			HttpServletResponse response) throws Exception {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (serviceEntries_id == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				//ServiceEntriesDocument file = ServiceEntriesService.getServiceEntriesDocument(serviceEntries_id);
				org.fleetopgroup.persistence.document.ServiceEntriesDocument	file =		documentService.getServiceEntriesDocument(serviceEntries_id, userDetails.getCompany_id());
				if (file != null) {
					if (file.getService_content() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getService_filename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getService_contentType());
						response.getOutputStream().write(file.getService_content());
						out.flush();
						out.close();

					}
				}
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
		return null;
	}

	// get to save in prepareServiceEntries
	public ServiceEntries prepareServiceEntries(ServiceEntriesDto ServiceEntries) throws Exception {

		ServiceEntries work = new ServiceEntries();

		work.setServiceEntries_id(ServiceEntries.getServiceEntries_id());
		work.setVid(ServiceEntries.getVid());

		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// VehicleDto vehiclename =
			// vehicleService.getVehicle_Details_Service_Entries(ServiceEntries.getVid());
			// if (vehiclename != null) {
			// // show Vehicle name
			// work.setVehicle_registration(vehiclename.getVehicle_registration());
			// work.setVehicle_Group(vehiclename.getVehicle_Group());
			// work.setVehicleGroupId(vehiclename.getVehicleGroupId());
			// }
			work.setVehicle_Odometer(ServiceEntries.getVehicle_Odometer());

			if (ServiceEntries.getInvoiceDate() != null && !ServiceEntries.getInvoiceDate().isEmpty()) {
				java.util.Date date = dateFormat.parse(ServiceEntries.getInvoiceDate());
				java.sql.Date start_date = new java.sql.Date(date.getTime());
				work.setInvoiceDate(start_date);
			}
			if (ServiceEntries.getService_paiddate() != null && !ServiceEntries.getService_paiddate().isEmpty()) {
				java.util.Date date2 = dateFormat.parse(ServiceEntries.getService_paiddate());
				java.sql.Date due_date = new java.sql.Date(date2.getTime());
				work.setService_paiddate(due_date);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ServiceEntries.getDriver_id() != 0) {
			work.setDriver_id(ServiceEntries.getDriver_id());
		} else {
			work.setDriver_id(0);
		}
		if (ServiceEntries.getVendor_id() != 0) {
			work.setVendor_id(ServiceEntries.getVendor_id());
		} else {
			work.setVendor_id(0);
		}
		
		if(ServiceEntries.getInvoiceNumber() != null) {
			work.setInvoiceNumber(ServiceEntries.getInvoiceNumber());
		} 
		
		work.setJobNumber(ServiceEntries.getJobNumber());

		// payment Type is credit set vendor pay mode is NOTPAID
		work.setService_paymentTypeId(ServiceEntries.getService_paymentTypeId());
		if (ServiceEntries.getService_paymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {

			// work.setService_vendor_paymode("NOTPAID");
			work.setService_vendor_paymodeId(ServiceEntriesType.PAYMENT_MODE_NOT_PAID);

		} else {
			// work.setService_vendor_paymode("PAID");
			work.setService_vendor_paymodeId(ServiceEntriesType.PAYMENT_MODE_PAID);
			Date currentDateUpdate = new Date();
			Timestamp toDatePayment = new java.sql.Timestamp(currentDateUpdate.getTime());

			work.setService_vendor_paymentdate(toDatePayment);

			work.setService_vendor_approvalID((long) 0);
		}

		work.setService_PayNumber(ServiceEntries.getService_PayNumber());
		// work.setService_paidby(ServiceEntries.getService_paidby());
		work.setService_paidbyId(ServiceEntries.getService_paidbyId());
		work.setTotalservice_cost(0.0);
		work.setTotalserviceROUND_cost(0.0);
		// work.setServiceEntries_status("OPEN");
		work.setServiceEntries_statusId(ServiceEntriesType.SERVICE_ENTRIES_STATUS_OPEN);

		work.setService_document(false);
		work.setService_document_id((long) 0);

		work.setMarkForDelete(false);

		work.setCreatedById(userDetails.getId());
		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		work.setCreated(toDate);
		work.setLastupdated(toDate);
		work.setLastModifiedById(userDetails.getId());
		work.setGpsOdometer(ServiceEntries.getGpsOdometer());
		if(ServiceEntries.getWorkshopInvoiceAmount() != null)
		work.setWorkshopInvoiceAmount(ServiceEntries.getWorkshopInvoiceAmount());
		work.setTallyCompanyId(ServiceEntries.getTallyCompanyId());
		work.setTripSheetId(ServiceEntries.getTripSheetId());
		return work;
	}

	// get to update in prepareServiceEntries
	public ServiceEntries prepare_Update_ServiceEntries(ServiceEntriesDto ServiceEntries) throws Exception {

		ServiceEntries work = new ServiceEntries();
		work.setServiceEntries_id(ServiceEntries.getServiceEntries_id());
		work.setServiceEntries_Number(ServiceEntries.getServiceEntries_Number());
		work.setVid(ServiceEntries.getVid());

		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			work.setVehicle_Odometer(ServiceEntries.getVehicle_Odometer());

			if (ServiceEntries.getInvoiceDate() != null && !ServiceEntries.getInvoiceDate().isEmpty()) {
				java.util.Date date = dateFormat.parse(ServiceEntries.getInvoiceDate());
				java.sql.Date start_date = new java.sql.Date(date.getTime());
				work.setInvoiceDate(start_date);
			}
			if (ServiceEntries.getService_paiddate() != null && !ServiceEntries.getService_paiddate().isEmpty()) {
				java.util.Date date2 = dateFormat.parse(ServiceEntries.getService_paiddate());
				java.sql.Date due_date = new java.sql.Date(date2.getTime());
				work.setService_paiddate(due_date);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ServiceEntries.getDriver_id() != 0) {
			work.setDriver_id(ServiceEntries.getDriver_id());
		} else {
			work.setDriver_id(0);
		}
		if (ServiceEntries.getVendor_id() != 0) {
			work.setVendor_id(ServiceEntries.getVendor_id());
		} else {
			work.setVendor_id(0);
		}
		
		if(ServiceEntries.getInvoiceNumber() != null)
		work.setInvoiceNumber(ServiceEntries.getInvoiceNumber());
		
		work.setJobNumber(ServiceEntries.getJobNumber());

		// payment Type is credit set vendor pay mode is NOTPAID
		work.setService_paymentTypeId(ServiceEntries.getService_paymentTypeId());
		if (work.getService_paymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {

			work.setService_vendor_paymodeId(ServiceEntriesType.PAYMENT_MODE_NOT_PAID);

		} else {
			work.setService_vendor_paymodeId(ServiceEntriesType.PAYMENT_MODE_PAID);
			Date currentDateUpdate = new Date();
			Timestamp toDatePayment = new java.sql.Timestamp(currentDateUpdate.getTime());

			work.setService_vendor_paymentdate(toDatePayment);
		}

		work.setService_PayNumber(ServiceEntries.getService_PayNumber());

		work.setService_paidbyId(ServiceEntries.getService_paidbyId());

		work.setMarkForDelete(false);

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		work.setCreated(toDate);
		work.setLastupdated(toDate);
		work.setCompanyId(userDetails.getCompany_id());
		work.setCreatedById(userDetails.getId());
		work.setLastModifiedById(userDetails.getId());
		work.setGpsOdometer(ServiceEntries.getGpsOdometer());
		if(ServiceEntries.getWorkshopInvoiceAmount() != null) {
			work.setWorkshopInvoiceAmount(ServiceEntries.getWorkshopInvoiceAmount());
		}else {
			work.setWorkshopInvoiceAmount(0.0);
		}
		if(ServiceEntries.getTallyCompanyId() != null) {
			work.setTallyCompanyId(ServiceEntries.getTallyCompanyId());
		}
		work.setTripSheetId(ServiceEntries.getTripSheetId());

		return work;
	}

	public ServiceEntries prepareServiceEntriesToAddTask(ServiceEntriesDto ServiceEntries) {

		ServiceEntries work = new ServiceEntries();

		work.setServiceEntries_id(ServiceEntries.getServiceEntries_id());
		return work;
	}

	// get to save in prepareServiceEntries
	public ServiceEntriesTasksToParts prepareServiceEntriesTaskToPart(
			ServiceEntriesTasksToParts ServiceEntriesTasksToParts) {

		ServiceEntriesTasksToParts workPart = new ServiceEntriesTasksToParts();

		workPart.setServicetaskid(ServiceEntriesTasksToParts.getServicetaskid());
		workPart.setServiceEntries_id(ServiceEntriesTasksToParts.getServiceEntries_id());
		workPart.setPartid(ServiceEntriesTasksToParts.getPartid());

		Double Quantity = 0.0;
		Double Parteachcost = 0.0;
		Double Partdisc = 0.0;
		Double Parttax = 0.0;

		if (ServiceEntriesTasksToParts.getQuantity() != null) {
			Quantity = (ServiceEntriesTasksToParts.getQuantity());
		}
		if (ServiceEntriesTasksToParts.getParteachcost() != null) {
			Parteachcost = (ServiceEntriesTasksToParts.getParteachcost());
		}
		if (ServiceEntriesTasksToParts.getPartdisc() != null) {
			Partdisc = ServiceEntriesTasksToParts.getPartdisc();
		}
		if (ServiceEntriesTasksToParts.getParttax() != null) {
			Parttax = ServiceEntriesTasksToParts.getParttax();
		}
		Double discountCost = 0.0;
		Double TotalCost = 0.0;

		discountCost = (Quantity * Parteachcost) - ((Quantity * Parteachcost) * (Partdisc / 100));
		TotalCost = round(((discountCost) + (discountCost * (Parttax / 100))), 2);

		workPart.setQuantity(Quantity);
		workPart.setParteachcost(Parteachcost);
		workPart.setPartdisc(Partdisc);
		workPart.setParttax(Parttax);

		workPart.setTotalcost(TotalCost);

		return workPart;
	}

	// get to save in prepareServiceEntries
	public ServiceEntriesTasksToLabor prepareServiceEntriesTaskToLabor(
			ServiceEntriesTasksToLabor ServiceEntriesTasksToLabor) {

		ServiceEntriesTasksToLabor workPart = new ServiceEntriesTasksToLabor();

		workPart.setServicetaskid(ServiceEntriesTasksToLabor.getServicetaskid());
		workPart.setServiceEntries_id(ServiceEntriesTasksToLabor.getServiceEntries_id());

		workPart.setLabername(ServiceEntriesTasksToLabor.getLabername());

		Double Laberhourscost = 0.0;
		Double Eachlabercost = 0.0;
		Double laberdisc = 0.0;
		Double labertax = 0.0;

		if (ServiceEntriesTasksToLabor.getLaberhourscost() != null) {
			Laberhourscost = (ServiceEntriesTasksToLabor.getLaberhourscost());
		}
		if (ServiceEntriesTasksToLabor.getEachlabercost() != null) {
			Eachlabercost = (ServiceEntriesTasksToLabor.getEachlabercost());
		}
		if (ServiceEntriesTasksToLabor.getLaberdiscount() != null) {
			laberdisc = ServiceEntriesTasksToLabor.getLaberdiscount();
		}
		if (ServiceEntriesTasksToLabor.getLabertax() != null) {
			labertax = ServiceEntriesTasksToLabor.getLabertax();
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

	// Delete Service Entries Check all Tables
	@RequestMapping(value = "/deleteServiceEntries", method = RequestMethod.GET)
	public ModelAndView deleteServiceEntries(@RequestParam("SEID") final Long ServiceEntries_id,
			final HttpServletRequest request) throws Exception {
		CustomUserDetails userDetails = null;
		
		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<ServiceEntriesTasksDto> ServiceEntriestask = ServiceEntriesService
					.getServiceEntriesTasks(ServiceEntries_id, userDetails.getCompany_id());
			if (ServiceEntriestask != null && !ServiceEntriestask.isEmpty()) {

				return new ModelAndView("redirect:/newServiceEntries/1/1.in?deleteAllTask=true");

			} else {

				 ServiceEntriesService.deleteServiceEntries(ServiceEntries_id, toDate, userDetails.getId(), userDetails.getCompany_id());
				
				 ServiceEntries serviceEntries = ServiceEntriesService.getDeletedSEById(ServiceEntries_id,userDetails.getCompany_id());
				
				if(serviceEntries.getISSUES_ID() != null) {
					issuesService.changeIssueStatusFromSE_createdToOpen(serviceEntries.getServiceEntries_id(),IssuesTypeConstant.ISSUES_STATUS_OPEN, serviceEntries.getISSUES_ID(),userDetails);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newServiceEntries/1/1.in?danger=true");
		}

		return new ModelAndView("redirect:/newServiceEntries/1/1.in?deleteServiceEntries=true");

	}

	/* Save to ServiceEntries Task and Part the values in parts */
	@RequestMapping(value = "/deleteServiceEntriesTask", method = RequestMethod.GET)
	public ModelAndView deleteServiceEntriesTask(@ModelAttribute("command") ServiceEntriesTasks ServiceEntriesTask,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		Long ServiceEntries_id = null;
		try {

			ServiceEntriesTasks WORKTASK = ServiceEntriesService
					.getServiceEntriesTask(ServiceEntriesTask.getServicetaskid());

			List<ServiceEntriesTasksToParts> ServiceEntriestasktoParts = ServiceEntriesService
					.getServiceEntriesTasksToParts_ID(ServiceEntriesTask.getServicetaskid(), WORKTASK.getCompanyId());
			if (ServiceEntriestasktoParts != null && !ServiceEntriestasktoParts.isEmpty()) {

				model.put("deleteFristParts", true);

				return new ModelAndView("redirect:/ServiceEntriesParts.in?SEID="
						+ WORKTASK.getServiceEntries().getServiceEntries_id() + "", model);
			} else {

				List<ServiceEntriesTasksToLabor> ServiceEntriestasktoLabor = ServiceEntriesService
						.getServiceEntriesTasksToLabor_ID(ServiceEntriesTask.getServicetaskid(),
								WORKTASK.getCompanyId());
				if (ServiceEntriestasktoLabor != null && !ServiceEntriestasktoLabor.isEmpty()) {

					model.put("deleteFristParts", true);

					return new ModelAndView("redirect:/ServiceEntriesParts.in?SEID="
							+ WORKTASK.getServiceEntries().getServiceEntries_id() + "", model);
				} else {

					ServiceEntriesService.deleteServiceEntriesTask(WORKTASK.getServicetaskid(),
							WORKTASK.getCompanyId());

					// Update Main ServiceEntries Total cost
					try {

						ServiceEntriesService
								.updateServiceEntriesMainTotalCost(WORKTASK.getServiceEntries().getServiceEntries_id());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			ServiceEntries_id = WORKTASK.getServiceEntries().getServiceEntries_id();

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newServiceEntries/1/1.in?danger=true");
		}

		// return new ModelAndView("addServiceEntriesParts", model);
		return new ModelAndView("redirect:/ServiceEntriesParts.in?SEID=" + ServiceEntries_id + "", model);

	}

	/* Save to ServiceEntries Task and Part the values in parts */
	@RequestMapping(value = "/deleteServiceEntriesTaskToPart", method = RequestMethod.GET)
	public ModelAndView deleteServiceEntriesTaskToPart(
			@ModelAttribute("command") ServiceEntriesTasksToParts ServiceEntriesTaskToPart,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		Long ServiceEntries_id = null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			ServiceEntriesTasksToParts WORKTASKTOPARTS = ServiceEntriesService.getServiceEntriesTaskToParts_ONLY_ID(
					ServiceEntriesTaskToPart.getServiceEntriesTaskto_partid(), userDetails.getCompany_id());

			ServiceEntriesService.deleteServiceEntriesTaskTOParts(WORKTASKTOPARTS.getServiceEntriesTaskto_partid(),
					userDetails.getCompany_id());

			// get Tasks Total cost
			try {

				ServiceEntriesService.updateServiceEntriesTask_TotalPartCost(WORKTASKTOPARTS.getServicetaskid());

				// Update Main ServiceEntries Total cost
				try {

					ServiceEntriesService.updateServiceEntriesMainTotalCost(WORKTASKTOPARTS.getServiceEntries_id());

				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/newServiceEntries/1/1.in?danger=true");
				}

			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("redirect:/newServiceEntries/1/1.in?danger=true");
			}

			ServiceEntries_id = WORKTASKTOPARTS.getServiceEntries_id();

		} catch (Exception e) {
			return new ModelAndView("redirect:/newServiceEntries/1/1.in?danger=true");
		}

		// return new ModelAndView("addServiceEntriesParts", model);
		return new ModelAndView("redirect:/ServiceEntriesParts.in?SEID=" + ServiceEntries_id + "", model);
	}

	/* Save to ServiceEntries Task and Part the values in parts */
	@RequestMapping(value = "/deleteServiceEntriesTaskToLabor", method = RequestMethod.GET)
	public ModelAndView deleteServiceEntriesTaskToLabor(
			@ModelAttribute("command") ServiceEntriesTasksToLabor ServiceEntriesTaskToLabor,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		Long ServiceEntries_id = null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			ServiceEntriesTasksToLabor WORKTASKTOLabor = ServiceEntriesService.getServiceEntriesTaskToLabor_ONLY_ID(
					ServiceEntriesTaskToLabor.getServiceEntriesto_laberid(), userDetails.getCompany_id());

			ServiceEntriesService.deleteServiceEntriesTaskTOLabor(WORKTASKTOLabor.getServiceEntriesto_laberid(),
					userDetails.getCompany_id());

			// get Tasks Total cost
			try {

				ServiceEntriesService.updateServiceEntriesTask_TotalLaborCost(WORKTASKTOLabor.getServicetaskid());

				// Update Main ServiceEntries Total cost
				try {
					ServiceEntriesService.updateServiceEntriesMainTotalCost(WORKTASKTOLabor.getServiceEntries_id());

				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/newServiceEntries/1/1.in?danger=true");
				}

			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("redirect:/newServiceEntries/1/1.in?danger=true");
			}

			ServiceEntries_id = WORKTASKTOLabor.getServiceEntries_id();

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newServiceEntries/1/1.in?danger=true");
		}

		// return new ModelAndView("addServiceEntriesParts", model);
		return new ModelAndView("redirect:/ServiceEntriesParts.in?SEID=" + ServiceEntries_id + "", model);

	}

	// edit Service Entries
	@RequestMapping(value = "/editServiceEntries", method = RequestMethod.GET)
	public ModelAndView editServiceEntries(@RequestParam("SEID") final Long ServiceEntries_id,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		HashMap<String, Object>     configuration		= null;
		HashMap<String, Object>     config				= null;
		HashMap<String, Object>     gpsConfiguration	= null;
		CustomUserDetails 			userDetails			= null;
		ServiceEntriesDto 			serviceEntriesBL	= null;
		ServiceEntriesDto 			serviceEntriesDto	= null;
		boolean						tallyConfig			= false;
		String 						minBackDate			= null;
		Integer						noOfBackDays		= null;

		try { 
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			config	 			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			tallyConfig			= (boolean) config.getOrDefault(ServiceEntriesConfigurationConstants.TALLY_COMPANY_MASTER_IN_SE, false);
			serviceEntriesDto 	= ServiceEntriesService.getServiceEntriesDetails(ServiceEntries_id, userDetails.getCompany_id());
			serviceEntriesBL 	= WOBL.getServiceEntriesDetails(serviceEntriesDto);
			noOfBackDays		= (Integer) config.getOrDefault(ServiceEntriesConfigurationConstants.NO_OF_BACK_DAYS, 0);
			minBackDate 		= DateTimeUtility.getDateBeforeNoOfDays(noOfBackDays);
			
			model.put("minBackDate",minBackDate);
			
			model.put("ServiceEntries", serviceEntriesBL);
			model.put("tallyConfig", tallyConfig);
			model.put("config", config);
			model.put("configuration", configuration);
			model.put("companyId", userDetails.getCompany_id());
			model.put("gpsConfiguration", gpsConfiguration);

		} catch (Exception e) {
			LOGGER.error("Err"+e);
			e.printStackTrace();
		}

		return new ModelAndView("ServiceEntriesEdit", model);
	}

	// update DB Service Entries
	@RequestMapping(value = "/updateServiceEntries", method = RequestMethod.POST)
	public ModelAndView updateServiceEntries(@ModelAttribute("command") ServiceEntriesDto ServiceEntries,
			final HttpServletRequest request) throws Exception {
		List<ServiceReminderDto>		serviceList			= null;
		Map<String, Object> 			model 				= new HashMap<String, Object>();
		CustomUserDetails 				userDetails 		= null;
		ServiceEntries 					SE					= null;
		Integer 						currentODDMETER		= null;
		try {
			
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			SE 			= prepare_Update_ServiceEntries(ServiceEntries);
			ServiceEntriesService.updateServiceEntries(SE);
			
			currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(SE.getVid());
			
			if (currentODDMETER < SE.getVehicle_Odometer()) {
				
				vehicleService.updateCurrentOdoMeter(SE.getVid(), SE.getVehicle_Odometer(), userDetails.getCompany_id());
				// update current Odometer update Service Reminder
				ServiceReminderService.updateCurrentOdometerToServiceReminder(SE.getVid(),SE.getVehicle_Odometer(), userDetails.getCompany_id());
				
				serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(SE.getVid(), userDetails.getCompany_id(), userDetails.getId());
				
				if(serviceList != null) {
					for(ServiceReminderDto list : serviceList) {
						
						if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
							
							ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
							//emailAlertQueueService.sendEmailServiceOdometer(list);
							//smsAlertQueueService.sendSmsServiceOdometer(list);
						}
					}
				}

				VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToServiceEntries(SE);
				vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
				vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
				
				model.put("saveServiceEntries", true);
			}
		}catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} 
		catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newServiceEntries/1/1.in", model);
		}

		// return new ModelAndView("newServiceEntries", model);
		return new ModelAndView("redirect:/newServiceEntries/1/1.in", model);
	}

	/***************************************************************************************************************************************
	 ************** Get Vehicle List drop down less Loading to Search
	 ***************************************************************************************************************************************/

	@RequestMapping(value = "/getVehicleSearchServiceEntries", method = RequestMethod.POST)
	public void getVehicleList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<VehicleDto> addresses = new ArrayList<VehicleDto>();
		List<Vehicle> vehicle = vehicleService.SearchOnlyVehicleNAME(term);
		if (vehicle != null && !vehicle.isEmpty()) {
			for (Vehicle add : vehicle) {
				VehicleDto wadd = new VehicleDto();
				wadd.setVid(add.getVid());
				wadd.setVehicle_registration(add.getVehicle_registration());
				// System.out.println(add.getVid());
				addresses.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(addresses));
	}

	@RequestMapping(value = "/getDriverSearchListServiceEntries", method = RequestMethod.POST)
	public void getDriverSearchListFuel(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<DriverDto> Driver = new ArrayList<DriverDto>();
		List<Driver> driverType = driverService.SearchOnlyDriverNAME(term);
		if (driverType != null && !driverType.isEmpty()) {
			for (Driver add : driverType) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_firstname(add.getDriver_firstname());
				wadd.setDriver_Lastname(add.getDriver_Lastname());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				wadd.setDriver_mobnumber(add.getDriver_mobnumber());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(Driver));
	}

	@RequestMapping(value = "/getJobTypeSearchListServiceEntries", method = RequestMethod.POST)
	public void getJobTypeSearchListServiceEntries(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<JobType> JOB = new ArrayList<JobType>();
		List<JobType> JOBType = JobTypeService.SearchOnlyJobType(term, userDetails.getCompany_id());
		if (JOBType != null && !JOBType.isEmpty()) {
			for (JobType add : JOBType) {
				JobType wadd = new JobType();
				// wadd.setJob_id(add.getJob_id());
				wadd.setJob_Type(add.getJob_Type());
				JOB.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(Driver));

		response.getWriter().write(gson.toJson(JOB));
	}

	// get To Search Part List only drop down
	@RequestMapping(value = "/getPartList", method = RequestMethod.POST)
	public void getPartList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		List<MasterPartsDto> MasterParts = new ArrayList<MasterPartsDto>();
		List<MasterPartsDto> Parts = MasterPartsService.SearchMasterParts(term, userDetails.getCompany_id());
		if (Parts != null && !Parts.isEmpty()) {
			for (MasterPartsDto add : Parts) {
				MasterPartsDto wadd = new MasterPartsDto();

				wadd.setPartid(add.getPartid());
				wadd.setPartname(add.getPartname());
				wadd.setPartnumber(add.getPartnumber());
				wadd.setReorderquantity(add.getReorderquantity());
				wadd.setMake(add.getMake());
				wadd.setUnittype(add.getUnittype());
				wadd.setUnitCost(add.getUnitCost());
				wadd.setDiscount(add.getDiscount());
				wadd.setTax(add.getTax());
				
				MasterParts.add(wadd);
			}
		}
		
		Gson gson = new Gson();

		// System.out.println("jsonInventory = " + gson.toJson(Driver));

		response.getWriter().write(gson.toJson(MasterParts));
	}

	// get Inventory Quantity List to json
	@RequestMapping(value = "/getlastServiceEntries", method = RequestMethod.GET)
	public void getLastWorkOrderServicepart(@RequestParam(value = "jobtask", required = true) Integer jobType,
			@RequestParam(value = "jobsubtask", required = true) Integer jobsubtype,
			@RequestParam(value = "vehicle_id", required = true) Integer vehicle_id,
			@RequestParam(value = "serviceEntries_id", required = true) Long serviceEntries_id, Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<ServiceEntriesTasks> ServiceEntriestask = ServiceEntriesService.getLast_ServiceEntriesTasks(jobType,
				jobsubtype, vehicle_id, serviceEntries_id);

		if (ServiceEntriestask != null && !ServiceEntriestask.isEmpty()) {
			for (ServiceEntriesTasks serviceTasks : ServiceEntriestask) {

				// System.out.println(serviceTasks.getServiceEntries().getServiceEntries_id());
				ServiceEntries lastServiceEntries = ServiceEntriesService
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

					// System.out.println(diffInDays);
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

					// System.out.println( diffenceDays);
					wadd.setPriority(diffenceDays);
				}

				// System.out.println(Current_vehicleOdmeter);
				Integer workorderOdometer = 0;
				if (lastServiceEntries.getVehicle_Odometer() != null) {
					workorderOdometer = lastServiceEntries.getVehicle_Odometer();

					Integer Current_vehicleOdmeter = 0;
					if (vehicle_id != null) {
						Current_vehicleOdmeter = vehicleService
								.updateCurrentOdoMeterGETVehicleToCurrentOdometer(vehicle_id);
						// System.out.println(Current_vehicleOdmeter);
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
				// System.out.println("jsonODDMETER = " +
				// gson.toJson(wadd));

				response.getWriter().write(gson.toJson(wadd));

				break;
			}

		} else {
			// WorkOrder is Null meaning Search Service Enteries
			List<WorkOrdersTasksDto> workordertask = WorkOrdersService.getLast_WorkOrdersTasks(jobType, jobsubtype,
					vehicle_id);
			if (workordertask != null && !workordertask.isEmpty()) {
				for (WorkOrdersTasksDto workOrdersTasks : workordertask) {

					// System.out.println(workOrdersTasks.getWorkorders().getWorkorders_id());
					WorkOrders lastworkOrders = WorkOrdersService
							.getWorkOrders(workOrdersTasks.getWorkorders_id());

					WorkOrdersDto wadd = new WorkOrdersDto();

					wadd.setStart_date_D(lastworkOrders.getStart_date());
					wadd.setVehicle_Odometer(lastworkOrders.getVehicle_Odometer());

					// get Current days
					java.util.Date currentDate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

					if (lastworkOrders.getStart_date() != null) {

						int diffInDays = (int) ((lastworkOrders.getDue_date().getTime() - toDate.getTime())
								/ (1000 * 60 * 60 * 24));

						// System.out.println(diffInDays);
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

						// System.out.println( diffenceDays);
						wadd.setPriority(diffenceDays);
					}

					// System.out.println(Current_vehicleOdmeter);
					Integer workorderOdometer = 0;
					if (lastworkOrders.getVehicle_Odometer() != null) {
						workorderOdometer = lastworkOrders.getVehicle_Odometer();

						Integer Current_vehicleOdmeter = 0;
						if (vehicle_id != null) {
							Current_vehicleOdmeter = vehicleService
									.updateCurrentOdoMeterGETVehicleToCurrentOdometer(vehicle_id);
							// System.out.println(Current_vehicleOdmeter);
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
					// System.out.println("jsonODDMETER = " +
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

	/*****************************************************************************************************
	 ***************** Vehicle To Service Entries Details **********************
	 *****************************************************************************************************/
	// Show inside the vehicle Details to service Entries
	@RequestMapping(value = "/VehicleServiceEntriesDetails", method = RequestMethod.GET)
	public ModelAndView VehicleServiceDetails(@ModelAttribute("command") Vehicle vehicle_id,
			ServiceReminderDto serviceReminderDto, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		VehicleDto vehicleDetails = null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			vehicleDetails = vehicleService.Get_Vehicle_Header_Details(vehicle_id.getVid());
			if (vehicleDetails != null) {

				VehicleDto vehicle = VBL.prepare_Vehicle_Header_Show(vehicleDetails);
				model.put("vehicle", vehicle);

				model.put("ServiceEntries", WOBL.prepareListServiceEntries(ServiceEntriesService
						.VehicleToServiceEntriesList(vehicle_id.getVid(), userDetails.getCompany_id())));

				Object[] count = vehicleService
						.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(vehicle_id.getVid());
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
				model.put("dseCount", (Long) count[12]);
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Renewal Reminder:", e);
		}

		return new ModelAndView("Show_Vehicle_ServiceEntries", model);
	}

	@RequestMapping(value = "/getVehicleSearchServiceEntrie", method = RequestMethod.POST)
	public void getVehicleListServiceEntries(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<VehicleDto> addresses = new ArrayList<VehicleDto>();
			VehicleDto wadd = null;
			List<Vehicle> DateVehicle = vehicleService.Search_Service_entries_Vehicle_Name(term);
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
		} catch (Exception e) {
			throw e;
		}
	}

	@RequestMapping(value = "/getBatteryWiseHistoryReport", method = RequestMethod.POST)
	public void getBatteryWiseHistoryReport(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<BatteryDto> addresses = new ArrayList<BatteryDto>();
			BatteryDto wadd = null;
			List<Battery> DateBattery = iBatteryService.Search_Battery_History_Report(term);
			if (DateBattery != null && !DateBattery.isEmpty()) {
				for (Battery add : DateBattery) {
					wadd = new BatteryDto();
					
					wadd.setBatteryId(add.getBatteryId());
					wadd.setBatterySerialNumber(add.getBatterySerialNumber());
					
					addresses.add(wadd);
				}
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(addresses));
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value = "/getVehicleListDealerEntries", method = RequestMethod.POST)
	public void getVehicleListDealerEntries(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<VehicleDto> addresses = new ArrayList<VehicleDto>();
			VehicleDto wadd = null;
			List<VehicleDto> DateVehicle = vehicleService.Search_DealerService_Vehicle_Name(term);
			if (DateVehicle != null && !DateVehicle.isEmpty()) {
				for (VehicleDto add : DateVehicle) {
					wadd = new VehicleDto();

					wadd.setVid(add.getVid());
					wadd.setVehicle_registration(add.getVehicle_registration());
					wadd.setDriverFirstName(add.getDriverFirstName());
					wadd.setDriverId(add.getDriverId());

					addresses.add(wadd);
				}
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(addresses));
		} catch (Exception e) {
			throw e;
		}
	}

	@RequestMapping(value = "/getVendorSearchListPart", method = RequestMethod.POST)
	public void getVendorSearchListFuel(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<Vendor> addresses = new ArrayList<Vendor>();
			List<Vendor> vendor = vendorService.SearchOnly_PART_VendorName(term, userDetails.getCompany_id());
			if (vendor != null && !vendor.isEmpty()) {
				for (Vendor add : vendor) {
					Vendor wadd = new Vendor();
					wadd.setVendorId(add.getVendorId());
					wadd.setVendorName(add.getVendorName());
					wadd.setVendorLocation(add.getVendorLocation());

					addresses.add(wadd);
				}
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(addresses));
		} catch (Exception e) {
			throw e;
		}

	}
	
	@RequestMapping(value = "/searchServiceEntriesByDate", method = RequestMethod.POST)
	public ModelAndView searchServiceEntriesByDate(@RequestParam("searchDate") final String searchDate,
			RedirectAttributes redir,
			final HttpServletRequest request) {


		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String dateRangeFrom = "", dateRangeTo = "", Service_paiddate = "";

		if (searchDate != "") {

			String[] From_TO_DateRange = null;
			try {

				From_TO_DateRange = searchDate.split(" to ");

				dateRangeFrom = From_TO_DateRange[0];
				dateRangeTo = From_TO_DateRange[1]+""+DateTimeUtility.DAY_END_TIME;
			} catch (Exception e) {
				LOGGER.error("Service Entries :", e);
			}
			try {

				if (dateRangeFrom != "" && dateRangeTo != "") {
					Service_paiddate = " AND SR.created between '" + dateRangeFrom + "'  AND '" + dateRangeTo
							+ "' ";
				}

				model.put("ServiceEntries",
						WOBL.prepareListServiceEntries(ServiceEntriesService.ReportServiceEntries(Service_paiddate, userDetails)));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("ServiceEntries_Report", model);
	}

}
