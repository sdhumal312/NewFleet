package org.fleetopgroup.web.controller;

/**
 * @author fleetop
 *
 */

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.VehicleConfigurationContant;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.RenewalTypeBL;
import org.fleetopgroup.persistence.bl.ServiceReminderBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.JobSubTypeDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.EmailAlertQueue;
import org.fleetopgroup.persistence.model.JobSubType;
import org.fleetopgroup.persistence.model.JobType;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.model.ServiceReminderSequenceCounter;
import org.fleetopgroup.persistence.model.SmsAlertQueue;
import org.fleetopgroup.persistence.model.SubscribeBox;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IEmailAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.IJobSubTypeService;
import org.fleetopgroup.persistence.serviceImpl.IJobTypeService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ISmsAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.ISubscribeBoxService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class ServiceReminderController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired private IVehicleService vehicleService;

	@Autowired private IServiceReminderService ServiceReminderService;

	@Autowired private IJobTypeService JobTypeService;

	@Autowired private IJobSubTypeService JobSubTypeService;
	
	@Autowired private ISubscribeBoxService subscribeBoxService;
	
	@Autowired private IUserProfileService userProfileService;
	
	@Autowired private IServiceReminderSequenceService			serviceReminderSequenceService;

	@Autowired private IVehicleDocumentService					vehicleDocumentService;
	@Autowired private CompanyConfigurationService 				companyConfigurationService;
	@Autowired private IPartLocationPermissionService 			partLocationPermissionService; 
	@Autowired IEmailAlertQueueService 							emailAlertQueueService;
	@Autowired ISmsAlertQueueService 							smsAlertQueueService;
	

	
	RenewalTypeBL 		DriDT 					= new RenewalTypeBL();
	VehicleBL 			VBL 					= new VehicleBL();
	ServiceReminderBL 	SRBL					= new ServiceReminderBL();
	PartLocationsBL 	partLocationsBL 		= new PartLocationsBL();
	SimpleDateFormat	dateFormat 				= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat 	dateFormatTime 			= new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	SimpleDateFormat	dateFormatSQL 			= new SimpleDateFormat("yyyy-MM-dd");

	@RequestMapping(value = "/searchServiceReminder", method = RequestMethod.POST)
	public ModelAndView searchServiceReminder(@RequestParam("Search") final String Service_id,
			final HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("Service", SRBL.prepareListofServiceReminder(
					ServiceReminderService.SearchServiceReminderByNumber(Service_id, userDetails)));
		} catch (NullPointerException e) {
			System.err.println("Exception : "+e);
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			LOGGER.error("Service Reminder:", e);
		}
		return new ModelAndView("Service_Reminder_Report", model);
	}

	@RequestMapping(value = "/searchServiceReminderShow", method = RequestMethod.POST)
	public ModelAndView searchServiceReminderShow(@RequestParam("Search") final Long Service_id,
			final HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("Service",
					SRBL.prepare_ServiceReminderDetail(ServiceReminderService.getServiceReminderByNumber(Service_id, userDetails)));

		} catch (NullPointerException e) {
			System.err.println("NullPointerException : "+e);
			model.put("NotFound", true);
			return new ModelAndView("redirect:/ServiceReminder/1.in", model);
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			model.put("NotFound", true);
			LOGGER.error("Service Reminder:", e);
			return new ModelAndView("redirect:/ServiceReminder/1.in", model);
		}
		return new ModelAndView("Show_Reminder_Service", model);
	}

	// show main page of Service Reminder
	@RequestMapping(value = "/ServiceReminder/{pageNumber}", method = RequestMethod.GET)
	public String showServiceReminderList(@PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Page<ServiceReminder> page = ServiceReminderService.getDeployment_Page_ServiceReminder(pageNumber, userDetails);
			if(page != null) {
				
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());
				
				model.addAttribute("deploymentLog", page);
				model.addAttribute("beginIndex", begin);
				model.addAttribute("endIndex", end);
				model.addAttribute("currentIndex", current);
				
				/* model.addAttribute("VehicleCount", page.getTotalElements()); */
				
				List<ServiceReminderDto> pageList = SRBL.prepareListofServiceReminder(
						ServiceReminderService.listServiceReminder(pageNumber, userDetails));
				
				model.addAttribute("Service", pageList);
			}

			// model.put("ServiceReminderCount",
			// ServiceReminderService.countServiceReminder());
			java.util.Date currentDate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
			model.addAttribute("TodayOverDueServiceRemindercount",
					ServiceReminderService.countTodayOverDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));

			model.addAttribute("TodayDueServiceRemindercount",
					ServiceReminderService.countTodayDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("Service_Reminder Page:", e);
			e.printStackTrace();
		}
		return "Service_Reminder";
	}

	// show main page of Service Reminder
	@RequestMapping(value = "/OverDueService/{pageNumber}", method = RequestMethod.GET)
	public String OverDueService(@PathVariable Integer pageNumber, Model model) {
		// show the driver list in all
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			java.util.Date currentDate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

			Page<ServiceReminder> page = ServiceReminderService.geServiceReminderOverDuePage(toDate , pageNumber, userDetails);
			if(page != null) {
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());
				
				model.addAttribute("deploymentLog", page);
				model.addAttribute("beginIndex", begin);
				model.addAttribute("endIndex", end);
				model.addAttribute("currentIndex", current);
				model.addAttribute("Service",
						SRBL.prepareListofServiceReminder(ServiceReminderService.OverDueServiceRemnder(toDate, userDetails, pageNumber)));

				model.addAttribute("TodayOverDueServiceRemindercount",
						ServiceReminderService.countTodayOverDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));

				model.addAttribute("TodayDueServiceRemindercount",
						ServiceReminderService.countTodayDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));

			}
			
			
		} catch (NullPointerException e) {
			return "OverDue_Service_Reminder";
		} catch (Exception e) {
			LOGGER.error("Service Reminder:", e);
			e.printStackTrace();
		}
		return "OverDue_Service_Reminder";
	}

	// show main page of Service Reminder
	@RequestMapping(value = "/DueSoonService", method = RequestMethod.GET)
	public ModelAndView DueSoonService() {
		Map<String, Object> model = new HashMap<String, Object>();
		// show the driver list in all
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			// model.put("ServiceReminderCount",
			// ServiceReminderService.countServiceReminder());
			java.util.Date currentDate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

			model.put("Service",
					SRBL.prepareListofServiceReminder(ServiceReminderService.DueSoonServiceRemnder(toDate, userDetails)));

			model.put("TodayOverDueServiceRemindercount",
					ServiceReminderService.countTodayOverDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));

			model.put("TodayDueServiceRemindercount",
					ServiceReminderService.countTodayDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Service Reminder:", e);
			e.printStackTrace();
		}
		return new ModelAndView("DueSoon_Service_Reminder", model);
	}

	// show the add Service Reminder page and field
	@RequestMapping("/addServiceReminder")
	public ModelAndView addServiceReminder(@ModelAttribute("command") ServiceReminder ServiceReminderDto,
			BindingResult result) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		// show the Group service of the driver
		// model.put("vehiclelist",
		// VBL.prepareListofDto(vehicleService.findAllVehiclelist()));
		// model.put("ServiceType",
		// DriDT.ServiceListofDto(ServiceTypeService.listServiceType()));
		// model.put("ServiceSubType",
		// DriDT.ServiceSubListofDto(ServiceTypeService.listServiceSubType()));
		// show the driver list in all

		return new ModelAndView("addService_Reminder", model);
	}

	@RequestMapping(value = "/saveServiceReminder", method = RequestMethod.POST)
	public ModelAndView saveServiceReminder(@ModelAttribute("command") ServiceReminder serviceReminderDto,
			@RequestParam("Select_vid") final String Select_vid, final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		Long service_Number		= (long) 0;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// Find Subscribe User email to get user first name
			String assigntoname = "";
			String assignEmail = "";
			int count	= 0;
			if (serviceReminderDto.getService_subScribedUserId() != null) {
				String username[] = serviceReminderDto.getService_subScribedUserId().split(",");

				for (String userAssignto : username) {
					UserProfileDto Orderingname = userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(Long.parseLong(userAssignto));
					assigntoname += "" + Orderingname.getFirstName() + " , ";
					if(count < username.length) {
						assignEmail  += ""+ Orderingname.getUser_email()+ " ,";
					}else {
						assignEmail  += ""+ Orderingname.getUser_email();
					}
					count++;
				}
			}

			String Vid[] = Select_vid.split(",");

			for (String Vehicle_ID : Vid) {
				// System.out.println(Vid[i]);
				try {
					VehicleDto vehiclename = (vehicleService.getVehicle_Details_Service_Reminder_Entries(Integer.parseInt(Vehicle_ID), userDetails.getCompany_id()));
					
					ServiceReminder service = new ServiceReminder();
					ServiceReminderSequenceCounter counter = serviceReminderSequenceService.findNextServiceReminderNumber(userDetails.getCompany_id());
					service_Number = counter.getNextVal();
					// show Vehicle name
					service.setService_Number(service_Number);
					service.setVid(vehiclename.getVid());
					//service.setVehicle_registration(vehiclename.getVehicle_registration());
					//service.setVehicle_Group(vehiclename.getVehicle_Group());
					service.setVehicle_currentOdometer(vehiclename.getVehicle_Odometer());
					service.setVehicleGroupId(vehiclename.getVehicleGroupId());
					// get UI to Service Reminder Details
					/*service.setService_type(ServiceReminderDto.getService_type());
					service.setService_subtype(ServiceReminderDto.getService_subtype());*/
					service.setServiceTypeId(serviceReminderDto.getServiceTypeId());
					service.setServiceSubTypeId(serviceReminderDto.getServiceSubTypeId());

					service.setMeter_interval(serviceReminderDto.getMeter_interval());
					service.setTime_interval(serviceReminderDto.getTime_interval());
					service.setTime_intervalperiodId(serviceReminderDto.getTime_intervalperiodId());

					service.setMeter_threshold(serviceReminderDto.getMeter_threshold());

					service.setTime_threshold(serviceReminderDto.getTime_threshold());
					service.setTime_thresholdperiodId(serviceReminderDto.getTime_thresholdperiodId());
					
					// Find Subscribe user first name below
					service.setService_subscribeduser_name(assigntoname);
					service.setService_subScribedUserId(serviceReminderDto.getService_subScribedUserId());
					//service.setService_subscribeduser(assignEmail);

					// get meter interval calculation to service meter
					// CurrentOdometer + Meter interval eg: 5000+500 =5500
					// service

					Integer CurrentOdometer = vehiclename.getVehicle_Odometer();
					Integer Meter_interval = serviceReminderDto.getMeter_interval();

					if (CurrentOdometer == null) {
						CurrentOdometer = 0;
					}
					if (Meter_interval == null) {
						Meter_interval = 0;
					}
					Integer ServiceOdometer = CurrentOdometer + Meter_interval;
					// save Service meter_Odometer reading
					service.setMeter_serviceodometer(ServiceOdometer);

					if (serviceReminderDto.getMeter_threshold() != null) {

						Integer meter_threshold = serviceReminderDto.getMeter_threshold();
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

					// time interval not equeal to null only enter the value
					if (serviceReminderDto.getTime_interval() != null) {

						// get time interval calculation to service to get
						// service time interval days
						Integer time_Intervalperiod = 0;
						if (serviceReminderDto.getTime_intervalperiodId() > 0) {
							time_Intervalperiod = serviceReminderDto.getTime_interval();
						}
						Integer timeserviceDaysPeriod = 0;
						switch (serviceReminderDto.getTime_intervalperiodId()) {
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

						final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

						final Calendar calendar = Calendar.getInstance();
						calendar.setTime(toDate);
						calendar.add(Calendar.DAY_OF_YEAR, timeserviceDaysPeriod);
						// System.out.println("Service time_date =" +
						// format.format(calendar.getTime()));

						// fuel date converted String to date Format
						java.util.Date servicedate = format.parse(format.format(calendar.getTime()));
						java.sql.Date Time_servicedate = new Date(servicedate.getTime());

						// save Service Time_interval Reminder
						service.setTime_servicedate(Time_servicedate);

						if (serviceReminderDto.getTime_threshold() != null) {

							Integer Time_threshold = 0;
							switch (serviceReminderDto.getTime_thresholdperiodId()) {
							case ServiceReminderDto.TIME_INTERVAL_PERIOD_DAYS:
								Time_threshold = serviceReminderDto.getTime_threshold();
								break;
							case ServiceReminderDto.TIME_INTERVAL_PERIOD_WEEKS:
								Time_threshold = serviceReminderDto.getTime_threshold() * 7;
								break;
							case ServiceReminderDto.TIME_INTERVAL_PERIOD_MONTHS:
								Time_threshold = serviceReminderDto.getTime_threshold() * 30;
								break;
							case ServiceReminderDto.TIME_INTERVAL_PERIOD_YEARS:
								Time_threshold = serviceReminderDto.getTime_threshold() * 365;
								break;

							default:
								Time_threshold = serviceReminderDto.getTime_threshold();
								break;
							}
							final Calendar calendar_advanceThreshold = Calendar.getInstance();
							// System.out.println("Service =" +
							// Time_servicedate);
							// this Time_servicedate is service time Day so that
							// day to minus advance
							// time threshold time
							calendar_advanceThreshold.setTime(Time_servicedate);
							calendar_advanceThreshold.add(Calendar.DAY_OF_YEAR, -Time_threshold);
							/*
							 * System.out.println( "Service time_advancedate ="
							 * +
							 * format.format(calendar_advanceThreshold.getTime()
							 * ));
							 */
							// fuel date converted String to date Format
							java.util.Date serviceThreshold = format
									.parse(format.format(calendar_advanceThreshold.getTime()));
							java.sql.Date Time_serviceAdvacedate = new Date(serviceThreshold.getTime());

							// save Service Time_interval Advance Threshold
							// dateReminder
							service.setTime_servicethreshold_date(Time_serviceAdvacedate);

						}
					}

					// service Status in Add ACTIVE
					//service.setServicestatus("ACTIVE");
					service.setServiceStatusId(ServiceReminderDto.SERVICE_REMINDER_STATUS_ACTIVE);
					// service time set FIRST times
					service.setService_remider_howtimes(1);

					/**
					 * who Create this vehicle get email id to user profile
					 * details
					 */
					//service.setCreatedBy(userDetails.getEmail());
					service.setCreatedById(userDetails.getId());
				//	service.setLastModifiedBy(userDetails.getEmail());
					service.setLastModifiedById(userDetails.getId());
					service.setMarkForDelete(false);
					service.setCreated(toDate);
					service.setLastupdated(toDate);
					service.setCompanyId(userDetails.getCompany_id());
					service.setServiceType(serviceReminderDto.getServiceType());

					// save the all value in DB
					ServiceReminderService.addServiceReminder(service);
					//serviceReminderSequenceService.updateNextServiceReminderNumber(service_Number + 1, userDetails.getCompany_id());
					// below Subscribe user email id to insert database in
					// subscribe data
					if (service.getService_subScribedUserId() != null) {
						String Subscribe[] = assignEmail.split(",");
						String SubscribeId[] = service.getService_subScribedUserId().split(",");
						for (int i = 0 ; i< Subscribe.length ; i++) {
							SubscribeBox subBox = new SubscribeBox();

							subBox.setSUBSCRIBE_VEHICLE_ID(service.getVid());
							//subBox.setSUBSCRIBE_VEHICLE_NAME(service.getVehicle_registration());
							subBox.setSUBSCRIBE_TYPE_ID(service.getServiceTypeId());
							subBox.setSUBSCRIBE_SUBTYPE_ID(service.getServiceSubTypeId());
							subBox.setSUBSCRIBE_LOCATION_ID(service.getService_id());
							subBox.setSUBSCRIBE_LOCATION("SERVICE_REMINDER");
							subBox.setSUBSCRIBE_USER_EMAIL(Subscribe[i]);
							subBox.setSUBSCRIBE_USER_ID(Long.parseLong(SubscribeId[i]+""));
							subBox.setSUBSCRIBE_DATE(service.getTime_servicedate());
							subBox.setSUBSCRIBE_THRESHOLD_DATE(service.getTime_servicethreshold_date());
							subBox.setCOMPANY_ID(userDetails.getCompany_id());

							subscribeBoxService.insert_Subscribe_Box(subBox);
						} 
					}
					model.put("saveService", true);

				} catch (NullPointerException e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/NotFound.in");
				} catch (Exception e) {
					LOGGER.error("Service Reminder:", e);
					e.printStackTrace();
					return new ModelAndView("redirect:/ServiceReminder/1.in?danger=true");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/ServiceReminder/1.in", model);
	}

	// show main page of Service Reminder
	@RequestMapping(value = "/ShowService", method = RequestMethod.GET)
	public ModelAndView ShowService(@ModelAttribute("command") ServiceReminder ServiceReminderDto, BindingResult result)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		// show the Service list in all
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			model.put("Service", SRBL.prepare_ServiceReminderDetail(
					ServiceReminderService.getServiceReminder(ServiceReminderDto.getService_id(), userDetails.getCompany_id())));
			model.put("companyId", userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
		} catch (NullPointerException e) {
			LOGGER.error("Service Reminder:", e);
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Service Reminder:", e);
			e.printStackTrace();
		}
		return new ModelAndView("Show_Reminder_Service", model);
	}
	
	@RequestMapping(value = "/ServiceReminderHistory", method = RequestMethod.GET)
	public ModelAndView ShowServiceHistory(@RequestParam("service_id") final Long Service_id) throws Exception {

		System.err.println("serive id "+Service_id);
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			model.put("Service", SRBL.prepare_ServiceReminderDetail(
					ServiceReminderService.getServiceReminder(Service_id, userDetails.getCompany_id())));
		
			model.put("companyId", userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
			
			
		} catch (NullPointerException e) {
			LOGGER.error("Service Reminder:", e);
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Service Reminder:", e);
			e.printStackTrace();
		}
		
		return new ModelAndView("ShowServiceHistory" , model);
	}


	// show main page of Service Reminder
	@RequestMapping(value = "/addServiceWorkOrder", method = RequestMethod.GET)
	public ModelAndView ServiceReminderToWorkOrder(@ModelAttribute("command") ServiceReminder serviceReminderDto,
			BindingResult result) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		HashMap<String, Object>     configuration	 = null;
		HashMap<String, Object>     gpsConfiguration = null;
		try {
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			gpsConfiguration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
		
			ServiceReminderDto ServiceReminderDB = ServiceReminderService
					.getServiceReminder(serviceReminderDto.getService_id(), userDetails.getCompany_id());
                     
			if (ServiceReminderDB.getServiceStatusId() > 0) {

				if (serviceReminderDto.getServiceStatusId() == ServiceReminderDto.SERVICE_REMINDER_STATUS_INACTIVE) {

					model.put("alreadyCreatedWorkOrder", true);
					return new ModelAndView("redirect:/ServiceReminder/1.in", model);
				}
			}
			model.put("vehicle", vehicleService.Get_Vehicle_Current_Status_TripSheetID(ServiceReminderDB.getVid()));
			model.put("validateOdometerInWorkOrder", companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).get(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_WORKORDER));
			model.put("Service", ServiceReminderDB);
			model.put("configuration", configuration);
			model.put("gpsConfiguration", gpsConfiguration);
			model.put("companyId", userDetails.getCompany_id());
			model.put("partLocationPermission", partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Service Reminder:", e);
			e.printStackTrace();
		}

		return new ModelAndView("Add_ServiceReminder_ToWorkOrder", model);
	}



	// delete the Service Reminder
	@RequestMapping(value = "/deleteServiceReminder", method = RequestMethod.GET)
	public ModelAndView deleteServiceReminder(@ModelAttribute("command") ServiceReminderDto ServiceReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		java.util.Date currentDate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ServiceReminderService.deleteServiceReminder(ServiceReminderDto.getService_id(),toDate,userDetails.getId(), userDetails.getCompany_id());
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Service Reminder:", e);
			e.printStackTrace();
		}

		model.put("deleteService", true);
		return new ModelAndView("redirect:/ServiceReminder/1.in", model);
	}

	/*****************************************************************************************************
	 ***************** Vehicle To Service Remider Details **********************
	 *****************************************************************************************************/

	@RequestMapping(value = "/VehicleServiceDetails", method = RequestMethod.GET)
	public ModelAndView VehicleServiceDetails(@ModelAttribute("command") Vehicle vehicle_id,
			ServiceReminderDto serviceReminderDto, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		VehicleDto vehicle =	null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicle	= vehicleService.Get_Vehicle_Header_Details(vehicle_id.getVid());
			if(vehicle != null) {
				vehicle = VBL.prepare_Vehicle_Header_Show(vehicle);
				model.put("vehicle", vehicle);
				// show the driver list in all
				
				model.put("Service",
						SRBL.prepareListofServiceReminderAjax(ServiceReminderService.OnlyVehicleServiceReminderList(vehicle_id.getVid(), userDetails.getCompany_id(), userDetails.getId())
								));
				
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
				model.put("dseCount", (Long) count[12]);
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Service Reminder:", e);
			e.printStackTrace();
		}finally {
			vehicle =	null;
		}

		return new ModelAndView("Show_Vehicle_Service", model);
	}

	@RequestMapping(value = "/VehicleServiceAdd", method = RequestMethod.GET)
	public ModelAndView VehicleServiceDetailsAdd(@ModelAttribute("command") Vehicle vehicle_id,
			ServiceReminderDto serviceReminderDto, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			VehicleDto vehicle = VBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(vehicle_id.getVid()));
			model.put("vehicle", vehicle);
			// show the driver list in all

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Service Reminder:", e);
			e.printStackTrace();
		}
		return new ModelAndView("Add_Vehicle_Service", model);
	}

	@RequestMapping(value = "/saveVehicleServiceReminder", method = RequestMethod.POST)
	public ModelAndView saveVehicleServiceReminder(@ModelAttribute("command") ServiceReminder serviceReminderDto,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		// System.out.println(Select_vid);
		ServiceReminder service = new ServiceReminder();
		CustomUserDetails	userDetails		= null;
		Long		service_Number	= (long) 0;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			try {
				VehicleDto vehiclename = vehicleService.getVehicle_Details_Service_Reminder_Entries(serviceReminderDto.getVid(), userDetails.getCompany_id());

				ServiceReminderSequenceCounter counter = serviceReminderSequenceService.findNextServiceReminderNumber(userDetails.getCompany_id());
				service_Number = counter.getNextVal();
				service.setService_Number(service_Number);
				service.setVid(vehiclename.getVid());
				//service.setVehicle_registration(vehiclename.getVehicle_registration());
				//service.setVehicle_Group(vehiclename.getVehicle_Group());
				service.setVehicle_currentOdometer(vehiclename.getVehicle_Odometer());
				service.setVehicleGroupId(vehiclename.getVehicleGroupId());
				service.setCompanyId(userDetails.getCompany_id());

				// get UI to Service Reminder Details
				//service.setService_type(ServiceReminderDto.getService_type());
				//service.setService_subtype(ServiceReminderDto.getService_subtype());
				service.setServiceTypeId(serviceReminderDto.getServiceTypeId());
				service.setServiceSubTypeId(serviceReminderDto.getServiceSubTypeId());

				service.setMeter_interval(serviceReminderDto.getMeter_interval());
				service.setTime_interval(serviceReminderDto.getTime_interval());
				service.setTime_intervalperiodId(serviceReminderDto.getTime_intervalperiodId());

				service.setMeter_threshold(serviceReminderDto.getMeter_threshold());

				service.setTime_threshold(serviceReminderDto.getTime_threshold());
				service.setTime_thresholdperiodId(serviceReminderDto.getTime_thresholdperiodId());

				// Find Subscribe User email to get user first name
				String assigntoname = "";
				String assignEmail = "";
				int count = 0;
				if (serviceReminderDto.getService_subScribedUserId() != null) {
					String username[] = serviceReminderDto.getService_subScribedUserId().split(",");

					for (String userAssignto : username) {
						UserProfileDto Orderingname = userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(Long.parseLong(userAssignto));
						assigntoname += "" + Orderingname.getFirstName() + " , ";
						if(count < username.length) {
							assignEmail  += ""+ Orderingname.getUser_email()+ " ,";
						}else {
							assignEmail  += ""+ Orderingname.getUser_email();
						}
						count++;
					}
				}
				// Find Subscribe user first name below
				service.setService_subscribeduser_name(assigntoname);
				service.setService_subScribedUserId(serviceReminderDto.getService_subScribedUserId());
			//	service.setService_subscribeduser(assignEmail);
				// get meter interval calculation to service meter
				// CurrentOdometer + Meter interval eg: 5000+500 =5500
				// service

				Integer CurrentOdometer = vehiclename.getVehicle_Odometer();
				Integer Meter_interval = serviceReminderDto.getMeter_interval();

				if (CurrentOdometer == null) {
					CurrentOdometer = 0;
				}
				if (Meter_interval == null) {
					Meter_interval = 0;
				}
				Integer ServiceOdometer = CurrentOdometer + Meter_interval;
				// save Service meter_Odometer reading
				service.setMeter_serviceodometer(ServiceOdometer);

				if (serviceReminderDto.getMeter_threshold() != null) {

					Integer meter_threshold = serviceReminderDto.getMeter_threshold();
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

				// time interval not equeal to null only enter the value
				if (serviceReminderDto.getTime_interval() != null) {

					// get time interval calculation to service to get
					// service time interval days
					Integer time_Intervalperiod = 0;
					if (serviceReminderDto.getTime_intervalperiodId() > 0) {
						time_Intervalperiod = serviceReminderDto.getTime_interval();
					}
					Integer timeserviceDaysPeriod = 0;
					switch (serviceReminderDto.getTime_intervalperiodId()) {
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

					final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

					final Calendar calendar = Calendar.getInstance();
					calendar.setTime(toDate);
					calendar.add(Calendar.DAY_OF_YEAR, timeserviceDaysPeriod);
					// System.out.println("Service time_date =" +
					// format.format(calendar.getTime()));

					// fuel date converted String to date Format
					java.util.Date servicedate = format.parse(format.format(calendar.getTime()));
					java.sql.Date Time_servicedate = new Date(servicedate.getTime());

					// save Service Time_interval Reminder
					service.setTime_servicedate(Time_servicedate);

					if (serviceReminderDto.getTime_threshold() != null) {

						Integer Time_threshold = 0;
						switch (serviceReminderDto.getTime_thresholdperiodId()) {
						case ServiceReminderDto.TIME_INTERVAL_PERIOD_DAYS:
							Time_threshold = serviceReminderDto.getTime_threshold();
							break;
						case ServiceReminderDto.TIME_INTERVAL_PERIOD_WEEKS:
							Time_threshold = serviceReminderDto.getTime_threshold() * 7;
							break;
						case ServiceReminderDto.TIME_INTERVAL_PERIOD_MONTHS:
							Time_threshold = serviceReminderDto.getTime_threshold() * 30;
							break;
						case ServiceReminderDto.TIME_INTERVAL_PERIOD_YEARS:
							Time_threshold = serviceReminderDto.getTime_threshold() * 365;
							break;

						default:
							Time_threshold = serviceReminderDto.getTime_threshold();
							break;
						}
						final Calendar calendar_advanceThreshold = Calendar.getInstance();
						// System.out.println("Service =" + Time_servicedate);
						// this Time_servicedate is service time Day so that
						// day to minus advance
						// time threshold time
						calendar_advanceThreshold.setTime(Time_servicedate);
						calendar_advanceThreshold.add(Calendar.DAY_OF_YEAR, -Time_threshold);
						/*
						 * System.out.println( "Service time_advancedate =" +
						 * format.format(calendar_advanceThreshold.getTime()));
						 */
						// fuel date converted String to date Format
						java.util.Date serviceThreshold = format
								.parse(format.format(calendar_advanceThreshold.getTime()));
						java.sql.Date Time_serviceAdvacedate = new Date(serviceThreshold.getTime());

						// save Service Time_interval Advance Threshold
						// dateReminder
						service.setTime_servicethreshold_date(Time_serviceAdvacedate);

					}
				}

				// service Status in Add ACTIVE
				//service.setServicestatus("ACTIVE");
				service.setServiceStatusId(ServiceReminderDto.SERVICE_REMINDER_STATUS_ACTIVE);
				// service time set FIRST times
				service.setService_remider_howtimes(1);
				/**
				 * who Create this vehicle get email id to user profile details
				 */
			//	service.setCreatedBy(userDetails.getEmail());
				service.setCreatedById(userDetails.getId());
				//service.setLastModifiedBy(userDetails.getEmail());
				service.setLastModifiedById(userDetails.getId());
				service.setMarkForDelete(false);
				service.setCreated(toDate);
				service.setLastupdated(toDate);
				service.setServiceType(serviceReminderDto.getServiceType());

				// save the all value in DB
				ServiceReminderService.addServiceReminder(service);
				
				//serviceReminderSequenceService.updateNextServiceReminderNumber(service_Number + 1, userDetails.getCompany_id());
				// below Subscribe user email id to insert database in subscribe
				// data
				if (service.getService_subScribedUserId() != null) {
					String Subscribe[] = assignEmail.split(",");
					String SubscribeId[] = service.getService_subScribedUserId().split(",");
					for (int i = 0 ; i< Subscribe.length ; i++) {
					
						SubscribeBox subBox = new SubscribeBox();

						subBox.setSUBSCRIBE_VEHICLE_ID(service.getVid());
						//subBox.setSUBSCRIBE_VEHICLE_NAME(service.getVehicle_registration());
						subBox.setSUBSCRIBE_TYPE_ID(service.getServiceTypeId());
						subBox.setSUBSCRIBE_SUBTYPE_ID(service.getServiceSubTypeId());
						subBox.setSUBSCRIBE_LOCATION_ID(service.getService_id());
						subBox.setSUBSCRIBE_LOCATION("SERVICE_REMINDER");
						subBox.setSUBSCRIBE_USER_EMAIL(Subscribe[i]);
						subBox.setSUBSCRIBE_USER_ID(Long.parseLong(SubscribeId[i]+""));
						subBox.setSUBSCRIBE_DATE(service.getTime_servicedate());
						subBox.setSUBSCRIBE_THRESHOLD_DATE(service.getTime_servicethreshold_date());
						subBox.setCOMPANY_ID(userDetails.getCompany_id());
						
						subscribeBoxService.insert_Subscribe_Box(subBox);
					} 
				}

			} catch (NullPointerException e) {
				return new ModelAndView("redirect:/NotFound.in");
			} catch (Exception e) {
				LOGGER.error("Service Reminder:", e);
				e.printStackTrace();
				model.put("danger", true);
				return new ModelAndView("redirect:/VehicleServiceDetails.in?vid=" + serviceReminderDto.getVid() + "",
						model);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("saveService", true);
		return new ModelAndView("redirect:/VehicleServiceDetails.in?vid=" + serviceReminderDto.getVid() + "", model);
	}

	@RequestMapping(value = "/VehicleServiceEdit", method = RequestMethod.GET)
	public ModelAndView VehicleServiceDetailsEdit(@ModelAttribute("command") Vehicle vehicle_id,
			ServiceReminder serviceReminderDto, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();

		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			ServiceReminderDto GetSERVICE = SRBL
					.GetServiceReminder(ServiceReminderService.getServiceReminder(serviceReminderDto.getService_id(), userDetails.getCompany_id()));
			model.put("Service", GetSERVICE);

			model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(GetSERVICE.getVid())));

			// show the driver list in all

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Service Reminder:", e);
			e.printStackTrace();
		}
		return new ModelAndView("Edit_Vehicle_Service", model);
	}

	@RequestMapping(value = "/updateVehicleServiceReminder", method = RequestMethod.POST)
	public ModelAndView updateVehicleServiceReminder(@ModelAttribute("command") ServiceReminder serviceReminderDto,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		// System.out.println(Select_vid);
		ServiceReminder 		service 			= new ServiceReminder();
		EmailAlertQueue 		email 				= null;
		SmsAlertQueue 			sms 				= null;
		CustomUserDetails		userDetails			= null;
		List<EmailAlertQueue>  	pendingList			= null;
		List<SmsAlertQueue>  	pendingList1		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			service.setService_id(serviceReminderDto.getService_id());

			ServiceReminderDto oldServiceReminder = ServiceReminderService
					.getServiceReminder(serviceReminderDto.getService_id(), userDetails.getCompany_id());
			try {
				VehicleDto vehiclename = (vehicleService.getVehicle_Details_Service_Reminder_Entries(serviceReminderDto.getVid(), userDetails.getCompany_id()));

				// show Vehicle name
				service.setVid(vehiclename.getVid());
				//service.setVehicle_registration(vehiclename.getVehicle_registration());
				//service.setVehicle_Group(vehiclename.getVehicle_Group());
				service.setVehicle_currentOdometer(vehiclename.getVehicle_Odometer());
				service.setVehicleGroupId(vehiclename.getVehicleGroupId());
				service.setService_Number(oldServiceReminder.getService_Number());
				
				// get UI to Service Reminder Details
				//service.setService_type(ServiceReminderDto.getService_type());
				//service.setService_subtype(ServiceReminderDto.getService_subtype());
				
				service.setServiceTypeId(serviceReminderDto.getServiceTypeId());
				service.setServiceSubTypeId(serviceReminderDto.getServiceSubTypeId());

				service.setMeter_interval(serviceReminderDto.getMeter_interval());
				service.setTime_interval(serviceReminderDto.getTime_interval());
				service.setTime_intervalperiodId(serviceReminderDto.getTime_intervalperiodId());

				service.setMeter_threshold(serviceReminderDto.getMeter_threshold());

				service.setTime_threshold(serviceReminderDto.getTime_threshold());
				service.setTime_thresholdperiodId(serviceReminderDto.getTime_thresholdperiodId());
				//service.setService_subscribeduser(ServiceReminderDto.getService_subscribeduser());
				service.setService_subScribedUserId(oldServiceReminder.getService_subScribedUserId());
				service.setService_subscribeduser_name(serviceReminderDto.getService_subscribeduser_name());

				// get meter interval calculation to service meter
				// CurrentOdometer + Meter interval eg: 5000+500 =5500
				// service

				Integer OldServiceOdometer = oldServiceReminder.getMeter_serviceodometer();
				Integer OldMeter_interval = oldServiceReminder.getMeter_interval();

				Integer Meter_interval = serviceReminderDto.getMeter_interval();

				if (OldServiceOdometer == null) {
					OldServiceOdometer = 0;
				}
				if (OldMeter_interval == null) {
					OldMeter_interval = 0;
				}
				if (Meter_interval == null) {
					Meter_interval = 0;
				}
				Integer ServiceOdometer = (OldServiceOdometer - OldMeter_interval) + Meter_interval;
				// save Service meter_Odometer reading
				service.setMeter_serviceodometer(ServiceOdometer);

				if (serviceReminderDto.getMeter_threshold() != null) {

					Integer meter_threshold = serviceReminderDto.getMeter_threshold();
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

				// time interval not equeal to null only enter the value
				if (serviceReminderDto.getTime_interval() != null) {

					// get time interval calculation to service to get
					// service time interval days
					Integer time_Intervalperiod = 0;
					if (serviceReminderDto.getTime_intervalperiodId() > 0) {
						time_Intervalperiod = serviceReminderDto.getTime_interval();
					}
					Integer timeserviceDaysPeriod = 0;
					switch (serviceReminderDto.getTime_intervalperiodId()) {
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

					final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

					final Calendar calendar = Calendar.getInstance();
					calendar.setTime(toDate);
					calendar.add(Calendar.DAY_OF_YEAR, timeserviceDaysPeriod);
					// System.out.println("Service time_date =" +
					// format.format(calendar.getTime()));

					// fuel date converted String to date Format
					java.util.Date servicedate = format.parse(format.format(calendar.getTime()));
					java.sql.Date Time_servicedate = new Date(servicedate.getTime());

					// save Service Time_interval Reminder
					service.setTime_servicedate(Time_servicedate);

					if (serviceReminderDto.getTime_threshold() != null) {

						Integer Time_threshold = 0;
						switch (serviceReminderDto.getTime_thresholdperiodId()) {
						case ServiceReminderDto.TIME_INTERVAL_PERIOD_DAYS:
							Time_threshold = serviceReminderDto.getTime_threshold();
							break;
						case ServiceReminderDto.TIME_INTERVAL_PERIOD_WEEKS:
							Time_threshold = serviceReminderDto.getTime_threshold() * 7;
							break;
						case ServiceReminderDto.TIME_INTERVAL_PERIOD_MONTHS:
							Time_threshold = serviceReminderDto.getTime_threshold() * 30;
							break;
						case ServiceReminderDto.TIME_INTERVAL_PERIOD_YEARS:
							Time_threshold = serviceReminderDto.getTime_threshold() * 365;
							break;

						default:
							Time_threshold = serviceReminderDto.getTime_threshold();
							break;
						}
						final Calendar calendar_advanceThreshold = Calendar.getInstance();
						// System.out.println("Service =" + Time_servicedate);
						// this Time_servicedate is service time Day so that
						// day to minus advance
						// time threshold time
						calendar_advanceThreshold.setTime(Time_servicedate); 							// take this service date
						calendar_advanceThreshold.add(Calendar.DAY_OF_YEAR, -Time_threshold);
						/*
						 * System.out.println( "Service time_advancedate =" +
						 * format.format(calendar_advanceThreshold.getTime()));
						 */
						// fuel date converted String to date Format
						java.util.Date serviceThreshold = format
								.parse(format.format(calendar_advanceThreshold.getTime()));
						java.sql.Date Time_serviceAdvacedate = new Date(serviceThreshold.getTime());

						// save Service Time_interval Advance Threshold
						// dateReminder
						service.setTime_servicethreshold_date(Time_serviceAdvacedate);

					}
				}

				// service Status in Add ACTIVE
				service.setServiceStatusId(oldServiceReminder.getServiceStatusId());
				// get Update Time to old data in new update same
				// service time set FIRST times
				service.setService_remider_howtimes(oldServiceReminder.getService_remider_howtimes());

				service.setCreated(oldServiceReminder.getCreatedOn());
				service.setLast_servicecompleldid(oldServiceReminder.getLast_servicecompleldid());
				service.setLast_servicecompleldbyId(oldServiceReminder.getLast_servicecompleldbyId());
				service.setLast_servicedate(oldServiceReminder.getLast_servicedate());
				service.setServiceType(serviceReminderDto.getServiceType());

				/**
				 * who Create this vehicle get email id to user profile details
				 */
			
				//service.setCreatedBy(userDetails.getEmail());
				service.setCreatedById(userDetails.getId());
			//	service.setLastModifiedBy(userDetails.getEmail());
				service.setLastModifiedById(userDetails.getId());
				service.setMarkForDelete(false);
				service.setCreated(toDate);
				service.setLastupdated(toDate);
				service.setCompanyId(userDetails.getCompany_id());

				// save the all value in DB
				ServiceReminderService.updateServiceReminder(service);
				
				
				pendingList	= emailAlertQueueService.getAllEmailAlertQueueDetailsById(serviceReminderDto.getService_id());
				
				emailAlertQueueService.deleteEmailAlertQueue(serviceReminderDto.getService_id());
				
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
							
							calendar2.add(Calendar.DAY_OF_YEAR, emailAlertQueue.getAlertAfterValues());
							java.util.Date alertDate1 = format2.parse(format2.format(calendar2.getTime()));
							java.util.Date alertAfterDate =  new Date(alertDate1.getTime());
							email1.setAlertAfterDate(alertAfterDate+"");
							email1.setAlertScheduleDate(alertAfterDate);
						
							emailAlertQueueService.updateEmailAlertQueue(email1);
						  
					       }
					}	
				}	
				
				pendingList1	= smsAlertQueueService.getAllSmsAlertQueueDetailsById(serviceReminderDto.getService_id());
				
				smsAlertQueueService.deleteSmsAlertQueue(serviceReminderDto.getService_id());
				
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
				

			} catch (NullPointerException e) {
				return new ModelAndView("redirect:/NotFound.in");
			} catch (Exception e) {
				LOGGER.error("Service Reminder:", e);
				e.printStackTrace();
				model.put("danger", true);
				return new ModelAndView("redirect:/ShowService.in?service_id=" + serviceReminderDto.getService_id() + "",
						model);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("updateService", true);
		return new ModelAndView("redirect:/ShowService.in?service_id=" + serviceReminderDto.getService_id() + "", model);
	}

	@RequestMapping(value = "/VehicleServiceDelete", method = RequestMethod.GET)
	public ModelAndView VehicleServiceDetailsDelete(@ModelAttribute("command") ServiceReminder serviceReminderDto,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		Integer vehicle = null;
		try {
		
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ServiceReminderDto GetSERVICE = SRBL
					.GetServiceReminder(ServiceReminderService.getServiceReminder(serviceReminderDto.getService_id(), userDetails.getCompany_id()));

			vehicle = GetSERVICE.getVid();
			
			java.util.Date currentDate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
			try {
				// Select the service entries in vehicle inside
				ServiceReminderService.deleteServiceReminder(serviceReminderDto.getService_id(),toDate, userDetails.getId(), userDetails.getCompany_id());

			} catch (NullPointerException e) {
				return new ModelAndView("redirect:/NotFound.in");
			} catch (Exception e) {
				LOGGER.error("Service Reminder:", e);
				e.printStackTrace();
				model.put("danger", true);

				return new ModelAndView("redirect:/VehicleServiceDetails.in?vid=" + vehicle + "", model);
			}
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Service Reminder:", e);
			e.printStackTrace();
		}
		model.put("deleteService", true);
		return new ModelAndView("redirect:/VehicleServiceDetails.in?vid=" + vehicle + "", model);
	}

	// show main page of Service Reminder
	@RequestMapping(value = "/VehicleServiceWorkOrder", method = RequestMethod.GET)
	public ModelAndView VehicleServiceWorkOrder(@ModelAttribute("command") ServiceReminder serviceReminderDto,
			BindingResult result) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		// show the Service list in all
		CustomUserDetails	userDetails		= null;
		HashMap<String, Object>     configuration	= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
		
			ServiceReminderDto ServiceReminderDB = ServiceReminderService
					.getServiceReminder(serviceReminderDto.getService_id(), userDetails.getCompany_id());

			if (ServiceReminderDB.getServiceStatusId() > 0) {

				if (serviceReminderDto.getServiceStatusId() == ServiceReminderDto.SERVICE_REMINDER_STATUS_INACTIVE) {

					model.put("alreadyCreatedWorkOrder", true);
					return new ModelAndView("redirect:/VehicleServiceDetails.in?vid=" + ServiceReminderDB.getVid() + "",
							model);
				}
			}
			model.put("vehicle", vehicleService.Get_Vehicle_Current_Status_TripSheetID(ServiceReminderDB.getVid()));
			model.put("validateOdometerInWorkOrder", companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).get(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_WORKORDER));
			model.put("Service", ServiceReminderDB);
			model.put("configuration", configuration);
			model.put("partLocationPermission", partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));


		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Service Reminder:", e);
			e.printStackTrace();
		}

		return new ModelAndView("Add_ServiceReminder_ToWorkOrder", model);
	}

	// This calculation to vehicle inside modules so no need to get current
	// query in Current vehicle List
	public List<ServiceReminderDto> vehicleTOServiceDto(List<ServiceReminderDto> ServiceReminder,
			Integer Current_vehicleOdmeter) {
		List<ServiceReminderDto> Dtos = null;
		if (ServiceReminder != null && !ServiceReminder.isEmpty()) {
			Dtos = new ArrayList<ServiceReminderDto>();
			ServiceReminderDto Service = null;
			for (ServiceReminderDto ServiceReminderDto : ServiceReminder) {
				Service = new ServiceReminderDto();

				Service.setService_id(ServiceReminderDto.getService_id());
				Service.setService_Number(ServiceReminderDto.getService_Number());
				Service.setVid(ServiceReminderDto.getVid());
				Service.setVehicle_registration(ServiceReminderDto.getVehicle_registration());
				Service.setVehicle_Group(ServiceReminderDto.getVehicle_Group());
				Service.setService_subtype(ServiceReminderDto.getService_subtype());
				Service.setService_type(ServiceReminderDto.getService_type());

				Service.setMeter_interval(ServiceReminderDto.getMeter_interval());
				Service.setTime_interval(ServiceReminderDto.getTime_interval());
				Service.setTime_intervalperiod(ServiceReminderDto.getTime_intervalperiod());

				Service.setService_subscribeduser(ServiceReminderDto.getService_subscribeduser());
				Service.setService_subscribeduser_name(ServiceReminderDto.getService_subscribeduser_name());
				Service.setServicestatus(ServiceReminderDto.getServicestatus());

				// get Current days
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

				if (ServiceReminderDto.getTime_servicedate() != null) {

					int diffInDays = (int) ((ServiceReminderDto.getTime_servicedate().getTime() - toDate.getTime())
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
					Service.setDiffrent_time_days(diffenceDays);
				}

				try {
					/*
					 * Integer Current_vehicleOdmeter = vehicleService
					 * .updateCurrentOdoMeterGETVehicleToCurrentOdometer(
					 * ServiceReminderDto.getVid());
					 */

					Integer serviceReminder = 0;
					if (ServiceReminderDto.getMeter_serviceodometer() != null) {
						serviceReminder = ServiceReminderDto.getMeter_serviceodometer();
					}

					Integer diff_Odmeter = serviceReminder - Current_vehicleOdmeter;

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

					Service.setDiffrent_meter_oddmeter(diffenceOdometer);

					// Overdue and Due soon message code logic

					String diffenceThrsholdOdometer = null;

					if (ServiceReminderDto.getMeter_servicethreshold_odometer() != null) {

						// eg: current 1000 -1500 = -500
						Integer currentThresholdDiff = Current_vehicleOdmeter
								- ServiceReminderDto.getMeter_servicethreshold_odometer();
						Integer meter_Threshold = 0;
						if (ServiceReminderDto.getMeter_threshold() != null) {
							meter_Threshold = ServiceReminderDto.getMeter_threshold();
						}

						switch (currentThresholdDiff) {

						case 1:
							diffenceThrsholdOdometer = "Due Soon";
							break;

						default:
							if (currentThresholdDiff > 1) {
								if (currentThresholdDiff < meter_Threshold) {
									diffenceThrsholdOdometer = "Due Soon";
								} else if (currentThresholdDiff == meter_Threshold) {
									diffenceThrsholdOdometer = "Today";
								} else {
									diffenceThrsholdOdometer = "Over Due";
								}

							}
							break;
						}

						Service.setDiffenceThrsholdOdometer(diffenceThrsholdOdometer);
					}

					// Due soon message in Time interval
					if (ServiceReminderDto.getTime_servicethreshold_date() != null) {

						int diffInDays_threshold = (int) ((toDate.getTime()
								- ServiceReminderDto.getTime_servicethreshold_date().getTime())
								/ (1000 * 60 * 60 * 24));

						switch (diffInDays_threshold) {

						case 1:
							diffenceThrsholdOdometer = "Due Soon";
							break;
						default:
							if (diffInDays_threshold > 1) {

								int diffInTimeServicethreshold = (int) ((ServiceReminderDto.getTime_servicedate()
										.getTime() - ServiceReminderDto.getTime_servicethreshold_date().getTime())
										/ (1000 * 60 * 60 * 24));

								if (diffInDays_threshold < diffInTimeServicethreshold) {
									diffenceThrsholdOdometer = "Due Soon";
								} else if (diffInDays_threshold == diffInTimeServicethreshold) {
									diffenceThrsholdOdometer = "Today";
								} else {
									diffenceThrsholdOdometer = "Over Due";
								}

							}
							break;
						}
						
						boolean dueSoonOrOverDue = false;
						StringBuilder due = new StringBuilder("Due");
						if(diffenceThrsholdOdometer != null && !diffenceThrsholdOdometer.trim().equals("") && diffenceThrsholdOdometer.contains(due)) {
							dueSoonOrOverDue = true;
						}
						Service.setDueSoonOrOverDue(dueSoonOrOverDue);
						Service.setDiffenceThrsholdOdometer(diffenceThrsholdOdometer);
					}

				} catch (Exception e) {

					e.printStackTrace();
				}

				Dtos.add(Service);
			}
		}
		return Dtos;
	}

	/***************************************************************************************************************************************
	 ************** Get Vehicle List drop down less Loading to Search
	 ***************************************************************************************************************************************/

	@RequestMapping(value = "/getVehicleListService", method = RequestMethod.POST)
	public void getVehicleList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<VehicleDto> addresses = new ArrayList<VehicleDto>();
		List<Vehicle> vehicle =vehicleService.SearchOnlyVehicleNAME(term);
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

	@RequestMapping(value = "/getJobTypeService", method = RequestMethod.GET)
	public void getVehicleTripEditList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<JobType> job = new ArrayList<JobType>();
		List<JobType> jobType =JobTypeService.SearchOnlyJobType(term, userDetails.getCompany_id());
		if (jobType != null && !jobType.isEmpty()) {
			for (JobType add : jobType) {

				JobType wadd = new JobType();

				wadd.setJob_Type(add.getJob_Type());
				wadd.setJob_id(add.getJob_id());

				job.add(wadd);
			} 
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));
		response.getWriter().write(gson.toJson(job));
	}

	@RequestMapping(value = "/getJobSubTypeChangeService", method = RequestMethod.GET)
	public void getFuelVehicleOdoMerete(@RequestParam(value = "JobType", required = true) String job_type,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<JobSubType> subJob = new ArrayList<JobSubType>();
		List<JobSubTypeDto> subJobtype = JobSubTypeService.SearchOnlyJobSubType(job_type, userDetails.getCompany_id());
		if (subJobtype != null && !subJobtype.isEmpty()) {
			for (JobSubTypeDto add : subJobtype) {

				JobSubType wadd = new JobSubType();

				wadd.setJob_Subid(add.getJob_Subid());
				wadd.setJob_Type(add.getJob_Type());
				wadd.setJob_TypeId(add.getJob_TypeId());
				wadd.setJob_ROT(add.getJob_ROT());
				wadd.setJob_ROT_number(add.getJob_ROT_number());
				wadd.setJob_ROT_hour(add.getJob_ROT_hour());
				wadd.setJob_ROT_amount(add.getJob_ROT_amount());

				// System.out.println(add.getVid());
				subJob.add(wadd);
			} 
		}
		Gson gson = new Gson();
		// System.out.println("jsonODDMETER = " + gson.toJson(wadd));

		response.getWriter().write(gson.toJson(subJob));
	}
	
	
	@RequestMapping(value = "/getJobSubTypeByTypeId", method = RequestMethod.GET)
	public void getJobSubTypeChangeService(@RequestParam(value = "JobType", required = true) Integer job_type,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<JobSubType> subJob = new ArrayList<JobSubType>();
		List<JobSubTypeDto> subJobtype = JobSubTypeService.SearchOnlyJobSubType(job_type, userDetails.getCompany_id());
		
		//JobSubTypeService.serachRotForServiceReminder();
		
		if (subJobtype != null && !subJobtype.isEmpty()) {
			for (JobSubTypeDto add : subJobtype) {

				JobSubType wadd = new JobSubType();

				wadd.setJob_Subid(add.getJob_Subid());
				wadd.setJob_Type(add.getJob_Type());
				wadd.setJob_TypeId(add.getJob_TypeId());
				wadd.setJob_ROT(add.getJob_ROT());
				wadd.setJob_ROT_number(add.getJob_ROT_number());
				wadd.setJob_ROT_hour(add.getJob_ROT_hour());
				wadd.setJob_ROT_amount(add.getJob_ROT_amount());
				wadd.setJob_ROT_Service_Reminder(add.getROT_Service_Reminder());
				// System.out.println(add.getVid());
				subJob.add(wadd);
			} 
		}
		Gson gson = new Gson();
		// System.out.println("jsonODDMETER = " + gson.toJson(wadd));

		response.getWriter().write(gson.toJson(subJob));
	}

	
	/** get UserProfile Drop down List user search */
	@RequestMapping(value = "/getUserEmailId_Subscrible", method = RequestMethod.POST)
	public void getUserEmailId_Subscrible(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserProfileDto> user = new ArrayList<UserProfileDto>();
		List<UserProfileDto> userName = userProfileService.SearchUserEmail_id_and_Name(term, userDetails.getCompany_id());
		if (userName != null && !userName.isEmpty()) {
			for (UserProfileDto add : userName) {
				UserProfileDto wadd = new UserProfileDto();

				wadd.setUser_email(add.getUser_email());
				wadd.setFirstName(add.getFirstName());
				wadd.setLastName(add.getLastName());
				wadd.setUser_id(add.getUser_id());
				user.add(wadd);
			} 
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(user));
	}
	
	@RequestMapping(value = "/getUserByBranchId", method = RequestMethod.GET)
	public void getUserByBranchId(@RequestParam(value = "branchId") Integer branchId,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserProfileDto> user = new ArrayList<UserProfileDto>();
		List<UserProfileDto> userName = userProfileService.SearchUserEmail_id_and_Name(branchId, userDetails.getCompany_id());
		if (userName != null && !userName.isEmpty()) {
			for (UserProfileDto add : userName) {
				UserProfileDto wadd = new UserProfileDto();

				wadd.setUser_email(add.getUser_email());
				wadd.setFirstName(add.getFirstName());
				wadd.setLastName(add.getLastName());
				wadd.setUser_id(add.getUser_id());
				user.add(wadd);
			} 
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(user));
	}
	
	@RequestMapping(value = "/getAllUserListByCompanyId", method = RequestMethod.POST)
	public void getAllUserListByCompanyId(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserProfileDto> user = new ArrayList<>();
		List<UserProfileDto> userName = userProfileService.getAllUserListByCompanyId(term, userDetails.getCompany_id());
		if (userName != null && !userName.isEmpty()) {
			for (UserProfileDto add : userName) {
				UserProfileDto wadd = new UserProfileDto();

				wadd.setUser_email(add.getUser_email());
				wadd.setFirstName(add.getFirstName());
				wadd.setLastName(add.getLastName());
				wadd.setUser_id(add.getUser_id());
				user.add(wadd);
			} 
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(user));
	}
	
	
	
	@RequestMapping("/ShowServiceReminderCalender")
	public ModelAndView ShowDriverAd(
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = 1;
			c.set(year, month, day);
			int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			String driverStart = dateFormat.format(c.getTime());
			model.put("startDate", "" + driverStart + "");
			c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
			String driverEnd = dateFormat.format(c.getTime());
			model.put("startEnd", "" + driverEnd + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ShowServiceReminderCalender", model);
	}

	@RequestMapping("/ShowServiceReminderCalenderPre")
	public ModelAndView ShowServiceReminderCalenderPre(
			@RequestParam("start") final String startDate, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			@SuppressWarnings("unused")
			java.util.Date date = dateFormatSQL.parse(startDate);// all done
			Calendar c = dateFormatSQL.getCalendar();
			c.add(Calendar.DATE, -1);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = 1;
			c.set(year, month, day);
			int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			// System.out.println("numOfDaysInMonth : " + numOfDaysInMonth);
			// driver Starting Day
			String driverStart = dateFormat.format(c.getTime());
			model.put("startDate", "" + driverStart + "");
			c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
			// driver Ending Date
			String driverEnd = dateFormat.format(c.getTime());
			model.put("startEnd", "" + driverEnd + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ShowServiceReminderCalender", model);
	}
	
	@RequestMapping("/ShowServiceReminderCalenderNext")
	public ModelAndView ShowServiceReminderCalenderNext(
			@RequestParam("end") final String endDate, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			@SuppressWarnings("unused")
			java.util.Date date = dateFormatSQL.parse(endDate);// all done
			Calendar c = dateFormatSQL.getCalendar();
			c.add(Calendar.DATE, 1);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = 1;
			c.set(year, month, day);
			int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			// System.out.println("numOfDaysInMonth : " + numOfDaysInMonth);
			// driver Starting date
			String driverStart = dateFormat.format(c.getTime());
			model.put("startDate", "" + driverStart + "");
			c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
			// driver Ending Date
			String driverEnd = dateFormat.format(c.getTime());
			model.put("startEnd", "" + driverEnd + "");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ShowServiceReminderCalender", model);
	}

}
