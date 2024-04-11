package org.fleetopgroup.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
/**
 * @author fleetop
 *
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.DriverConfigurationContant;
import org.fleetopgroup.constant.DriverStatus;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.DriverCommentBL;
import org.fleetopgroup.persistence.bl.DriverDocTypeBL;
import org.fleetopgroup.persistence.bl.DriverDocumentBL;
import org.fleetopgroup.persistence.bl.DriverJobTypeBL;
import org.fleetopgroup.persistence.bl.DriverReminderBL;
import org.fleetopgroup.persistence.bl.DriverTrainingTypeBL;
import org.fleetopgroup.persistence.bl.VehicleGroupBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverCommentDto;
import org.fleetopgroup.persistence.dto.DriverDocumentDto;
import org.fleetopgroup.persistence.dto.DriverDocumentHistoryDto;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.dto.DriverPhotoDto;
import org.fleetopgroup.persistence.dto.DriverReminderDto;
import org.fleetopgroup.persistence.dto.DriverReminderHistoryDto;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.DriverComment;
import org.fleetopgroup.persistence.model.DriverDocumentHistory;
import org.fleetopgroup.persistence.model.DriverJobType;
import org.fleetopgroup.persistence.model.DriverPhoto;
import org.fleetopgroup.persistence.model.DriverReminder;
import org.fleetopgroup.persistence.model.DriverReminderHistory;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleDocument;
import org.fleetopgroup.persistence.model.VehicleGroup;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocTypeService;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IDriverJobTypeService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IDriverTrainingTypeService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.FileUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import au.com.bytecode.opencsv.CSVReader;

@Controller
public class DriverController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired 
	IVehicleGroupService 		vehicleGroupService;

	@Autowired 
	IDriverService 				driverService;

	@Autowired 
	IUserProfileService 		userProfileService;

	@Autowired 
	IDriverDocTypeService 		driverDocTypeService;

	@Autowired 
	IDriverTrainingTypeService driverTrainingTypeService;

	@Autowired 
	IDriverJobTypeService 		driverJobTypeService;
	
	@Autowired
	IDriverDocumentService		driverDocumentService;

	@Autowired
	CompanyConfigurationService	companyConfigurationService;
	
	@Autowired 
	MongoOperations				mongoOperations;
	
	@Autowired IVehicleService	vehicleService;
	
	DriverTrainingTypeBL 	driverTrainingTypeBL 		= new DriverTrainingTypeBL();
	DriverJobTypeBL 		driverJobTypeBL		 		= new DriverJobTypeBL();
	DriverBL 				driverBL			 		= new DriverBL();
	DriverReminderBL 		driverReminderBL		 	= new DriverReminderBL();
	DriverDocumentBL 		driverDocumentBL		 	= new DriverDocumentBL();
	DriverCommentBL 		driverCommentBL			 	= new DriverCommentBL();
	DriverDocTypeBL 		driverDocTypeBL			 	= new DriverDocTypeBL();
	VehicleGroupBL 			vehicleGroupBL	 			= new VehicleGroupBL();

	ByteImageConvert ByteImageConvert = new ByteImageConvert();

	SimpleDateFormat dateFormat_SQL = new SimpleDateFormat("YYYY-MM-dd");
	SimpleDateFormat dateFormatSQL = new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
	SimpleDateFormat format = new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);
	SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

	public static final Integer DRIVER_DEFALAT_ZERO = 0;

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
	@RequestMapping(value = "/getDriversList", method = RequestMethod.GET)
	public ModelAndView vendorHome(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Map<String, Object> 		model 			= new HashMap<String, Object>();
		CustomUserDetails			userDetails		= null;
		DriverJobType				driverJobType	= null;
		try {
			userDetails	= (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driverJobType	= driverJobTypeService.getTopDriverJobType(userDetails.getCompany_id());
			if(driverJobType != null) {
				redirectAttributes.addAllAttributes(request.getParameterMap());
				return new ModelAndView("redirect:/driver/"+driverJobType.getDriJobId()+"/1.in");
			}else {
				model.put("noVendorTypeFound", true);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}finally {
			userDetails		= null;
			driverJobType		= null;
		}
		return new ModelAndView("driver_JobType", model);
	}


	/* show main page of driver */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/driver/{jobType}/{pageNumber}", method = RequestMethod.GET)
	public String driverJobTypeList(@PathVariable("jobType") Integer jobType,
			@PathVariable("pageNumber") Integer pageNumber, Model model) throws Exception {
			CustomUserDetails				userDetails				= null;	
			long 							driverCount				= 0;
			HashMap<String, Object> 		configuration	        = null;
			Date 							todaysDate				= null;
			ValueObject						dueSoonCountByDStatus	= null;
			ValueObject						overDueCountByDStatus	= null;
			ValueObject						DriverrenewalReminderCount	= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			Page<Driver> page = driverService.getDeployment_Page_Driver(jobType, pageNumber, userDetails);
			if(page != null) {
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());
				
				model.addAttribute("deploymentLog", page);
				model.addAttribute("beginIndex", begin);
				model.addAttribute("endIndex", end);
				model.addAttribute("currentIndex", current);
				driverCount	= page.getTotalElements();
			}
			todaysDate					= DateTimeUtility.getCurrentDate();//current day
			
			dueSoonCountByDStatus		= driverService.totalDueSoonCount(todaysDate, userDetails.getCompany_id());
			overDueCountByDStatus		= driverService.getTotalOverDueRenewalCount(todaysDate, userDetails.getCompany_id());
			DriverrenewalReminderCount 	= driverService.getDriverrenewalReminderCount( userDetails.getCompany_id());
			model.addAttribute("totalDueSoonCount", dueSoonCountByDStatus.get("dueSoonCount"));
			model.addAttribute("totalOverDueCount", overDueCountByDStatus.get("overDueCount"));
			model.addAttribute("DriverrenewalReminderCount", DriverrenewalReminderCount.get("DriverrenewalReminder"));
			
			
			
			model.addAttribute("DriverCount", driverCount);
			List<DriverDto> pageList = driverService.listDriver(jobType, pageNumber, userDetails);
			model.addAttribute("driverDocType", driverDocTypeBL.DriDocTypeListofDto(driverDocTypeService.findAllByCompanyId(userDetails.getCompany_id())));
			model.addAttribute("drivers", pageList);

			model.addAttribute("driverJobType", driverJobTypeBL.DriJobTypeListofBean(driverJobTypeService.listDriverJobTypeByCompanyId(userDetails.getCompany_id())));
			if(pageList != null) {
				model.addAttribute("SelectDriverJob", pageList.get(0).getDriver_jobtitle());
			}else {
				model.addAttribute("SelectDriverJob", driverJobTypeService.getDriverJobType(jobType, userDetails.getCompany_id()).getDriJobType());
			}

			model.addAttribute("SelectJob", jobType);
			model.addAttribute("SelectPage", pageNumber);

			Object[] count = driverService.countTotalDriver_AND_TodayRenewal();
			
			Object[] countTomorrow = driverService.countTotalDriver_AND_TomorrowRenewal();
			
			Object[] countNextFifteen = driverService.countTotalDriver_AND_nextFifteenDayRenewal();
			
			Object[] countNextSeven = driverService.countTotalDriver_AND_nextSevenDayRenewal();
			Object[] countNextMonth = driverService.countTotalDriver_AND_nextMonthDayRenewal();
			
			model.addAttribute("TomorrowDLRenewalcount", (Long) countTomorrow[1]);
			model.addAttribute("NextFifteenDLRenewalcount", (Long) countNextFifteen[1]);
			model.addAttribute("NextSevenDLRenewalcount", (Long) countNextSeven[1]);
			model.addAttribute("NextMonthDLRenewalcount", (Long) countNextMonth[1]);

			// model.addAttribute("DriverCount", (Long) count[0]);
			model.addAttribute("TodayDLRenewalcount", (Long) count[1]);
			model.addAttribute("configuration",configuration);
			

		} catch (NullPointerException e) {
			LOGGER.error("driver Page:", e);
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("driver Page:", e);
			e.printStackTrace();
		}

		return "driver_JobType";
	}

	/* show main page of driver */
	@RequestMapping(value = "/TodayDLRenewal", method = RequestMethod.GET)
	public ModelAndView TodayDLRenewal() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// show the driver list in all
			Object[] count = driverService.countTotalDriver_AND_TodayRenewal();

			model.put("DriverCount", (Long) count[0]);
			model.put("TodayDLRenewalcount", (Long) count[1]);
			
			Object[] countTomorrow = driverService.countTotalDriver_AND_TomorrowRenewal();
			model.put("TomorrowDLRenewalcount", (Long) countTomorrow[1]);
			
			Object[] countNextSeven = driverService.countTotalDriver_AND_nextSevenDayRenewal();
			model.put("NextSevenDLRenewalcount", (Long) countNextSeven[1]);
			
			Object[] countNextFifteen = driverService.countTotalDriver_AND_nextFifteenDayRenewal();
			model.put("NextFifteenDLRenewalcount", (Long) countNextFifteen[1]);
			
			Object[] countNextMonth = driverService.countTotalDriver_AND_nextMonthDayRenewal();
			model.put("NextMonthDLRenewalcount", (Long) countNextMonth[1]);

			model.put("SelectJob", "DRIVER");
			model.put("SelectPage", 1);

			java.util.Date currentDate = new java.util.Date();
			DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");

			model.put("driverReminder", driverReminderBL.prepareListDriverReminderBean(driverService.listof_ToDay_DL_Renewal(ft.format(currentDate))));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("todayDLRenewal", model);
	} // TodayDLRenewal()

	/* show main page of driver */
	@RequestMapping(value = "/DriverR", method = RequestMethod.POST)
	public ModelAndView DriverR(@RequestParam("RRDate") String SearchDate, final HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// show the driver list in all
			Object[] count = driverService.countTotalDriver_AND_TodayRenewal();

			model.put("DriverCount", (Long) count[0]);
			model.put("TodayDLRenewalcount", (Long) count[1]);
			
			Object[] countTomorrow = driverService.countTotalDriver_AND_TomorrowRenewal();
			model.put("TomorrowDLRenewalcount", (Long) countTomorrow[1]);
			
			Object[] countNextSeven = driverService.countTotalDriver_AND_nextSevenDayRenewal();
			model.put("NextSevenDLRenewalcount", (Long) countNextSeven[1]);
			
			Object[] countNextFifteen = driverService.countTotalDriver_AND_nextFifteenDayRenewal();
			
			model.put("NextFifteenDLRenewalcount", (Long) countNextFifteen[1]);
			
			Object[] countNextMonth = driverService.countTotalDriver_AND_nextMonthDayRenewal();
			model.put("NextMonthDLRenewalcount", (Long) countNextMonth[1]);

			model.put("SelectJob", "DRIVER");
			model.put("SelectPage", 1);

			model.put("driverReminder", driverReminderBL.prepareListDriverReminderBean(driverService.listof_ToDay_DL_Renewal(SearchDate)));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("todayDLRenewal", model);
	} // TodayDLRenewal()

	@RequestMapping(value = "/searchDriver", method = RequestMethod.POST)
	public ModelAndView searchDriver(@ModelAttribute("command") DriverDto driverDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails			userDetails					= null;
		HashMap<String, Object> 	configuration				= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			model.put("drivers", driverBL.prepareList_Driver_mail_show(driverService.SearchDriver(driverDto.getDriver_firstname())));
			model.put("configuration", configuration);
			model.put("SelectJob", "DRIVER");
			model.put("SelectPage", 1);
			model.put("companyId", userDetails.getCompany_id());

		} catch (Exception e) {
			LOGGER.error("Driver Page:", e);
		}
		return new ModelAndView("Driver_Report", model);
	}

	@RequestMapping("/addDriver")
	public ModelAndView adddriver() throws Exception {
		Map<String, Object> 		model 						= new HashMap<String, Object>();
		CustomUserDetails			userDetails					= null;
		HashMap<String, Object> 	configuration				= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			
			model.put("vehiclegroup", driverBL.prepareListofDto(vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id())));
			model.put("driverTrainingType",driverTrainingTypeBL.DriTrainingTypeListofBean(driverTrainingTypeService.listDriverTrainingTypeByCompanyId(userDetails.getCompany_id())));
			model.put("driverJobType", driverJobTypeBL.DriJobTypeListofBean(driverJobTypeService.listDriverJobTypeByCompanyId(userDetails.getCompany_id())));
			model.put("driverDocType", driverDocTypeBL.DriDocTypeListofDto(driverDocTypeService.findAllByCompanyId(userDetails.getCompany_id())));
			model.put("driverDocType1", driverDocTypeService.findByDocType(userDetails.getCompany_id()));
			model.put("flavorWiseDriverSalary", configuration.getOrDefault(DriverConfigurationContant.FLAVOR_WISE_DRIVER_SALARY, false));
			model.put("user", userDetails.getEmail());
			model.put("vehicle", vehicleService.getVehicleListByCompanyId(userDetails.getCompany_id()));
			model.put("configuration", configuration);

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Driver Page:", e);
		}

		return new ModelAndView("adddriver", model);
	}

	// show the adddriver page and field
	@RequestMapping("/NotFound")
	public ModelAndView NotFound() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName(); // get logged in username
			model.put("user", name);

		} catch (Exception e) {
			LOGGER.error("Driver Page:", e);
		}

		return new ModelAndView("notFound", model);
	}

	
	/* Save the driver information in db */
	@RequestMapping(value = "/saveDriver", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveDriver(@ModelAttribute("command") DriverDto driverDto, DriverReminderDto driverreminderDto, 
			@RequestParam("input-file-preview") MultipartFile file, BindingResult result) {
		Map<String, Object> 			model 			= new HashMap<String, Object>();
		CustomUserDetails				userDetails		= null;
		Driver 							driver			= null;
		DriverReminder					driverReminder	= null;
		List<Driver> 					validate		= null;
		//System.err.println("driverreminderDto"+driverreminderDto);
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driver 				= driverBL.prepareModel(driverDto);
			/*addDriverRenewal*/
			if(driver.getDriver_dlnumber() != null) {
				if(driver.getDriver_dlnumber().trim() == "") {
					driver.setDriver_dlnumber(null);
				}
			}	
				
			validate = driverService.ValidateDriver(driver.getDriver_dlnumber(), driver.getDriver_empnumber(), userDetails.getCompany_id());
			
			if (validate != null && !validate.isEmpty()) {
				model.put("alreadyDriver", true);
				return new ModelAndView("redirect:/getDriversList.html", model);
			} else {
				/* save Driver */
				driverService.addDriver(driver);
				
			if(driverreminderDto != null) {
				if(!driverreminderDto.getDriver_dlfrom_show().isEmpty() ) {
					driverReminder		= driverReminderBL.prepareDriverReminder(driverreminderDto);
					driverReminder.setDriver_id(driver.getDriver_id());
					driverReminder.setCompanyId(userDetails.getCompany_id());
					driverReminder.setCreatedById(userDetails.getId());
					driverReminder.setCreated(new Date());
					if (!file.isEmpty()) {
						try {
							byte[] bytes = file.getBytes();
							driverReminder.setDriver_filename(file.getOriginalFilename());
							driverReminder.setDriver_content(bytes);
							driverReminder.setDriver_contentType(file.getContentType());
	
						} catch (IOException e) {
							LOGGER.error("Err1"+e);
							e.printStackTrace();
						}
					}
					driverService.addDriverReminder(driverReminder);
					model.put("addDriverReminder", true);
				}
			}	
				model.put("saveDriver", true);
			}
			return new ModelAndView("redirect:/showDriver.html?driver_id=" + driver.getDriver_id() + "", model);

		} catch (NullPointerException e) {
			LOGGER.error("Err"+e);
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			model.put("alreadyDriver", true);
			LOGGER.error("Driver Page:", e);

			return new ModelAndView("redirect:/getDriversList.html", model);
		}
		//return new ModelAndView("redirect:/getDriversList.html", model);
	}

	/* Save and Another Driver */
	@RequestMapping(value = "/{SelectJob}/{pageNumber}/showDriver", method = RequestMethod.GET)
	public ModelAndView showDriver_JOBTYPE(@PathVariable("SelectJob") String jobType,
			@PathVariable("pageNumber") Integer pageNumber, @RequestParam("driver_id") final Integer Driver_id,
			final HttpServletResponse result) {
		CustomUserDetails		userDetails		= null;
		DriverDto					driver			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> 	configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			/* show vehicle */
			Map<String, Object> model = new HashMap<String, Object>();
			driver	= driverService.getDriverDetails(Driver_id, userDetails);
			if(driver != null) {
				model.put("driver", driverBL.GetDriverID(driver));
				model.put("SelectJob", driver.getDriver_jobtitle());
				model.put("SelectPage", pageNumber);
				model.put("configuration", configuration);
				Calendar aCalendar = Calendar.getInstance();
				
				model.put("tripSheetNumber", driver.getTripSheetNumber());
				// read it
				Date CurrentDate = aCalendar.getTime();
				// add -1 month to current month
				aCalendar.add(Calendar.MONTH, -3);
				// set DATE to 1, so first date of previous month
				aCalendar.set(Calendar.DATE, 1);
				
				Date firstDateOfPreviousMonth = aCalendar.getTime();
				
				try {
					Object[] LastMonthcount = driverService.countTotal_LAST_COMMENT_ISSUES_FUELMILEAGE_REPORT(Driver_id,
							dateFormat_SQL.format(firstDateOfPreviousMonth), dateFormat_SQL.format(CurrentDate), userDetails.getCompany_id());
					model.put("LastComment", (Long) LastMonthcount[0]);
					if (LastMonthcount[1] != null) {
						model.put("LastFuelmileage", round(((Double) LastMonthcount[1]), 2));
					} else {
						model.put("LastFuelmileage", "0.0");
					}
					model.put("LastIssues", (Long) LastMonthcount[2]);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Object[] count = driverService.countTotal_REMINDER_DOC_COMMENT__AND_PHOTO(Driver_id);
				model.put("ReminderCount", (Long) count[0]);
				//model.put("DocumentCount", (Long) count[1]);
				model.put("CommentCount", (Long) count[1]);
				model.put("PhotoCount",driverDocumentService.getDriverPhotoCount(Driver_id, userDetails.getCompany_id()));
				model.put("DocumentCount", driverDocumentService.getDriverDocumentCount(Driver_id, userDetails.getCompany_id()));
		
				
				
				model.put("driverReminder", driverReminderBL.getDriverReminder_Showing_Details(driverService.Showlist_OF_Current_DriverReminder(Driver_id, dateFormat_SQL.format(CurrentDate))));
			}else {
				model.put("NoRecordFound", true);
			}
			

			return new ModelAndView("showDriverPage", model);

		} catch (NullPointerException e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/getDriversList?alreadyDriver=true");
		}

	}

	/* Save and Another Driver */
	@RequestMapping(value = "/showDriver", method = RequestMethod.GET)
	public ModelAndView showDriver(@RequestParam("driver_id") final Integer Driver_id,
			final HttpServletResponse result) {
		CustomUserDetails		userDetails			= null;
		DriverDto					driver				= null;
		try {
			/* show vehicle */
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> 	configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			Map<String, Object> model = new HashMap<String, Object>();
			driver	= driverService.getDriverDetails(Driver_id, userDetails);
			if(driver != null) {
				
				model.put("driver", driverBL.GetDriverID(driver));
				
				model.put("tripSheetNumber", driver.getTripSheetNumber());
				Calendar aCalendar = Calendar.getInstance();
				
				// read it
				Date CurrentDate = aCalendar.getTime();
				// add -1 month to current month
				aCalendar.add(Calendar.MONTH, -3);
				// set DATE to 1, so first date of previous month
				aCalendar.set(Calendar.DATE, 1);
				
				Date firstDateOfPreviousMonth = aCalendar.getTime();
				
				try {
					Object[] LastMonthcount = driverService.countTotal_LAST_COMMENT_ISSUES_FUELMILEAGE_REPORT(Driver_id,
							dateFormatSQL.format(firstDateOfPreviousMonth), dateFormatSQL.format(CurrentDate), userDetails.getCompany_id());
					model.put("LastComment", (Long) LastMonthcount[0]);
					if (LastMonthcount[1] != null) {
						model.put("LastFuelmileage", round(((Double) LastMonthcount[1]), 2));
					} else {
						model.put("LastFuelmileage", "0.0");
					}
					model.put("LastIssues", (Long) LastMonthcount[2]);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Object[] count = driverService.countTotal_REMINDER_DOC_COMMENT__AND_PHOTO(Driver_id);

				model.put("PhotoCount", driverDocumentService.getDriverPhotoCount(Driver_id, userDetails.getCompany_id()));   
				
				model.put("SelectJob", driver.getDriver_jobtitle());
				model.put("SelectPage", 1);
				model.put("configuration", configuration);
				
				model.put("ReminderCount", (Long) count[0]);
				//model.put("DocumentCount", (Long) count[1]);
				model.put("CommentCount", (Long) count[1]);
				model.put("DocumentCount", driverDocumentService.getDriverDocumentCount(Driver_id, userDetails.getCompany_id()));
		
				
				java.util.Date currentDate = new java.util.Date();
				model.put("driverReminder", driverReminderBL.getDriverReminder_Showing_Details(driverService.Showlist_OF_Current_DriverReminder(Driver_id, dateFormat_SQL.format(currentDate))));
			}else {
				model.put("NoRecordFound", true);
			}
			return new ModelAndView("showDriverPage", model);
		} catch (NullPointerException e) {
			System.err.println("NullPointerException "+e);
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/getDriversList?alreadyDriver=true");
		}

	}

	@RequestMapping(value = "/PrintDriver", method = RequestMethod.GET)
	public ModelAndView showUserProfilePrint(@RequestParam("id") final Integer id, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			String name = userDetails.getEmail(); // get logged in username

			model.put("company", userProfileService.findUserProfileByUser_email(name));
			model.put("configuration", configuration);

			model.put("driver", driverBL.GetDriverID(driverService.getDriverDetails(id, userDetails)));

			java.util.Date currentDate = new java.util.Date();
			DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
			model.put("driverReminder", driverReminderBL.getDriverReminder_Showing_Details(driverService.Showlist_OF_Current_DriverReminder(id, ft.format(currentDate))));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("showDriverPrint", model);
	}

	/* delete driver */
	@RequestMapping(value = "/deleteDriver", method = RequestMethod.GET)
	public ModelAndView deleteDriver(@RequestParam("driver_id") final Integer Driver_id,
			final HttpServletResponse result, RedirectAttributes redir) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String TID = "";
			if (Driver_id != DRIVER_DEFALAT_ZERO && Driver_id != null) {

				DriverDto CheckDriver = driverService.Get_Driver_Current_Status_TripSheetID(Driver_id, userDetails.getCompany_id());
				if (CheckDriver.getDriverStatusId() == DriverStatus.DRIVER_STATUS_TRIPROUTE) {
					TID += "<span> This Driver  <a href=\"../../showDriver.in?driver_id=" + CheckDriver.getDriver_id()
							+ "\" target=\"_blank\">" + CheckDriver.getDriver_firstname()
							+ " <i class=\"fa fa-external-link\"></i></a> In Other TripSheet <a href=\"../../showTripSheet.in?tripSheetID="
							+ CheckDriver.getTripSheetID() + "\" target=\"_blank\">TS-" + driverService.getCurrentTripSheetNumber(CheckDriver.getTripSheetID(), userDetails.getCompany_id())
							+ "  <i class=\"fa fa-external-link\"></i></a> you can't Edit and Delete,</span>, <br>";

					redir.addFlashAttribute("InAnother", TID);
					model.put("InAnotherTrip", true);
					return new ModelAndView("redirect:/getDriversList", model);
				} else if (CheckDriver.getDriverStatusId() == DriverStatus.DRIVER_STATUS_ACTIVE
						|| CheckDriver.getDriverStatusId() == DriverStatus.DRIVER_STATUS_INACTIVE) {
					//assign the current date 
					Date currentDateUpdate = new Date();
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
					// delete the one driver id
					driverService.deleteDriver(userDetails.getId(),toDate,Driver_id,userDetails.getCompany_id());
					return new ModelAndView("redirect:/getDriversList?deletesuccess=true");
				}
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {

			return new ModelAndView("redirect:/getDriversList?deletedanger=true");
		}
		return new ModelAndView("redirect:/getDriversList", model);
	}

	/* Edit Driver */
	@RequestMapping(value = "/editDriver", method = RequestMethod.GET)
	public ModelAndView editDriver(@RequestParam("driver_id") final Integer Driver_id,
			final HttpServletResponse result, RedirectAttributes redir) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	configuration				= null;
		// get editing driver information
		CustomUserDetails	userDetails		= null;
		boolean 			driverDetailsAjax			= false;		
		try {
			
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			driverDetailsAjax	= (boolean) configuration.getOrDefault(DriverConfigurationContant.ADD_DRIVER_DETAILS_AJAX, false);
			String TID = "";
			if (Driver_id != DRIVER_DEFALAT_ZERO && Driver_id != null) {

				DriverDto CheckDriver = driverService.Get_Driver_Current_Status_TripSheetID(Driver_id, userDetails.getCompany_id());
				if (CheckDriver.getDriverStatusId() == DriverStatus.DRIVER_STATUS_TRIPROUTE) {
					TID += "<span> This Driver  <a href=\"../../showDriver.in?driver_id=" + CheckDriver.getDriver_id()
							+ "\" target=\"_blank\">" + CheckDriver.getDriver_firstname()
							+ " <i class=\"fa fa-external-link\"></i></a> In Other TripSheet <a href=\"../../showTripSheet.in?tripSheetID="
							+ CheckDriver.getTripSheetID() + "\" target=\"_blank\">TS-" + driverService.getCurrentTripSheetNumber(CheckDriver.getTripSheetID(), userDetails.getCompany_id())
							+ "  <i class=\"fa fa-external-link\"></i></a> you can't Edit and Delete,</span>, <br>";
					redir.addFlashAttribute("InAnother", TID);
					model.put("InAnotherTrip", true);
					return new ModelAndView("redirect:/getDriversList", model);
				} else if (CheckDriver.getDriverStatusId() == DriverStatus.DRIVER_STATUS_ACTIVE
						|| CheckDriver.getDriverStatusId() == DriverStatus.DRIVER_STATUS_INACTIVE
						|| CheckDriver.getDriverStatusId() == DriverStatus.DRIVER_STATUS_SUSPEND
						|| CheckDriver.getDriverStatusId() == DriverStatus.DRIVER_STATUS_HOLD
						|| CheckDriver.getDriverStatusId() == DriverStatus.DRIVER_STATUS_RESIGN) {

					model.put("driver", driverBL.GetDriverID(driverService.getDriverDetails(Driver_id, userDetails)));
					
					model.put("vehiclegroup", driverBL.prepareListofDto(vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id())));
					model.put("driverTrainingType", driverTrainingTypeBL.DriTrainingTypeListofBean(driverTrainingTypeService.listDriverTrainingTypeByCompanyId(userDetails.getCompany_id())));
					model.put("driverJobType", driverJobTypeBL.DriJobTypeListofBean(driverJobTypeService.listDriverJobTypeByCompanyId(userDetails.getCompany_id())));
					model.put("flavorWiseDriverSalary", configuration.getOrDefault(DriverConfigurationContant.FLAVOR_WISE_DRIVER_SALARY, false));
					model.put("user", userDetails.getEmail());
					model.put("configuration", configuration);

				}
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {

		}
		if(driverDetailsAjax) {
			return new ModelAndView("editDriverDetails", model);
		}else {
			return new ModelAndView("editDriver", model);
		}
		
	}

	/* Update Driver */
	@RequestMapping(value = "/updateDriver", method = RequestMethod.POST)
	public ModelAndView updateDriver(@ModelAttribute("command") Driver driverDto, BindingResult result,
			RedirectAttributes redir) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		

			String TID = "";
			if (driverDto.getDriver_id() != DRIVER_DEFALAT_ZERO) {

				DriverDto CheckDriver = driverService.getDriverDetails(driverDto.getDriver_id(), userDetails);

				if (CheckDriver.getDriverStatusId() == DriverStatus.DRIVER_STATUS_TRIPROUTE) {
					TID += "<span> This Driver  <a href=\"../../showDriver.in?driver_id=" + CheckDriver.getDriver_id()
							+ "\" target=\"_blank\">" + CheckDriver.getDriver_firstname()
							+ " <i class=\"fa fa-external-link\"></i></a> In Other TripSheet <a href=\"../../showTripSheet.in?tripSheetID="
							+ CheckDriver.getTripSheetID() + "\" target=\"_blank\">TS-" + driverService.getCurrentTripSheetNumber(CheckDriver.getTripSheetID(), userDetails.getCompany_id())
							+ "  <i class=\"fa fa-external-link\"></i></a> you can't Edit and Delete,</span>, <br>";

					redir.addFlashAttribute("InAnother", TID);
					model.put("InAnotherTrip", true);
					return new ModelAndView("redirect:/getDriversList", model);
				} else if (CheckDriver.getDriverStatusId() == DriverStatus.DRIVER_STATUS_ACTIVE
						|| CheckDriver.getDriverStatusId() == DriverStatus.DRIVER_STATUS_INACTIVE) {
					/* save vehicle */

					Driver driver = driverBL.prepareModelUpdate(driverDto, CheckDriver);
					

					driverService.updateDriver(driver);
				}
			}

		} catch (NullPointerException e) {
			LOGGER.error("Driver Page:", e);
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Driver Page:", e);
			return new ModelAndView("redirect:/getDriversList?deletedanger=true");

		}
		return new ModelAndView("redirect:/showDriver.in?driver_id=" + driverDto.getDriver_id() + "&update=true");
	}

	/* Driver Reminder */

	@RequestMapping("/ShowDriverReminder")
	public ModelAndView ShowdriverReminder(@ModelAttribute("command") DriverDto driverDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		DriverDto					driver			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driver	= driverService.getDriver_Header_Show_Details(driverDto.getDriver_id());
			if(driver != null) {
				model.put("driver", driverBL.Show_Driver_Header_Details(driver));
				/* list of driver reminder */
				model.put("driverReminder", driverReminderBL.getDriverReminder_Showing_Details(driverService.listDriverReminder(driverDto.getDriver_id(), userDetails.getCompany_id())));
				
				Object[] count = driverService.countTotal_REMINDER_DOC_COMMENT__AND_PHOTO(driverDto.getDriver_id());
				
				model.put("ReminderCount", (Long) count[0]);
				//model.put("DocumentCount", (Long) count[1]);
				model.put("CommentCount", (Long) count[1]);
				model.put("PhotoCount", (Long) count[2]);
				model.put("DocumentCount", driverDocumentService.getDriverDocumentCount(driverDto.getDriver_id(), userDetails.getCompany_id()));
		
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ShowDriverReminder", model);
	}

	/* add Driver Reminder */
	@RequestMapping("/addDriverReminder")
	public ModelAndView adddriverReminder(@ModelAttribute("command") Driver driverDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		DriverDto					driver			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driver	= driverService.getDriver_Header_Show_Details_RenewalReminder(driverDto.getDriver_id(), userDetails.getCompany_id());
			if(driver != null) {
				model.put("driver", driverBL.Show_Driver_Header_Details(driver));
				// show the vehicle Group service of the driver
				
				model.put("driverDocType", driverDocTypeBL.DriDocTypeListofDto(driverDocTypeService.findAllByCompanyId(userDetails.getCompany_id())));
			}
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {

		}
		return new ModelAndView("addDriverReminder", model);
	}

	/* Show the Reminder History */
	@RequestMapping("/ShowReminderHis")
	public ModelAndView addReminderHis(@ModelAttribute("command") Driver driverDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		DriverDto		driver	= null;
		try {
			driver	= driverService.getDriver_Header_Show_Details(driverDto.getDriver_id());
			if(driver != null) {
				CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				model.put("driver", driverBL.Show_Driver_Header_Details(driver));
				// show the vehicle Group service of the driver
				model.put("driverReminder", driverService.listDriverReminderHis(driverDto.getDriver_id(), userDetails.getCompany_id()));
			}
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}finally {
			driver	= null;
		}
		return new ModelAndView("showReminderHis", model);
	}

	/* Save Reminder Driver */
	@RequestMapping(value = "/saveDriverReminder")
	public String singleUpload() {
		return "saveDriverReminder";
	}

	@RequestMapping(value = "/saveDriverReminder", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveDriverReminder(@ModelAttribute("command") DriverReminderDto driverreminderDto,
			@RequestParam("input-file-preview") MultipartFile file) {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		CustomUserDetails			userDetails			= null;
		DriverReminder 				driverreminder		= null;
		DriverReminder 				validateDriverreminder	= null;
		
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			driverreminder = driverReminderBL.prepareDriverReminder(driverreminderDto);
			driverreminder.setCompanyId(userDetails.getCompany_id());
			driverreminder.setCreatedById(userDetails.getId());
			driverreminder.setCreated(new Date());
			
			validateDriverreminder =driverService.getExistingDriverReminder(driverreminder.getDriver_id(),driverreminder.getDriverRenewalTypeId(), driverreminder.getDriver_dlfrom(), driverreminder.getDriver_dlto(), userDetails.getCompany_id()," ");
			
			if(validateDriverreminder == null) {

			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					driverreminder.setDriver_filename(file.getOriginalFilename());
					driverreminder.setDriver_content(bytes);
					driverreminder.setDriver_contentType(file.getContentType());

				} catch (IOException e) {
					e.printStackTrace();
				}
				String blankQuery =" ";
				DriverReminder oldDriverReminder =driverService.getPreviousDriverReminder(driverreminder.getDriver_id(), driverreminder.getDriverRenewalTypeId(), userDetails.getCompany_id(),blankQuery);
				if(oldDriverReminder != null) {
					driverService.updateOldDriverReminder(oldDriverReminder.getDriver_remid() , userDetails.getCompany_id());
					if(oldDriverReminder.getRenewalRecieptId() != null)
					driverService.updateDriverRenewalReceiptStatus(oldDriverReminder.getRenewalRecieptId(), userDetails.getCompany_id());					
				}
				
				driverService.addDriverReminder(driverreminder);
				model.put("addDriverReminder", true);
				

				return new ModelAndView(
						"redirect:/ShowDriverReminder.in?driver_id=" + driverreminder.getDriver_id() + "", model);
			} else {
				// messages
				model.put("emptyDocument", true);
				return new ModelAndView(
						"redirect:/ShowDriverReminder.in?driver_id=" + driverreminderDto.getDriver_id() + "", model);
			}
			}else {
				model.put("alreadyExist", true);
				return new ModelAndView(
						"redirect:/ShowDriverReminder.in?driver_id=" + driverreminderDto.getDriver_id() + "", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView(
					"redirect:/ShowDriverReminder.in?driver_id=" + driverreminderDto.getDriver_id() + "", model);
		}
	}

	/* Edit Driver Reminder */
	@RequestMapping("/editDriverReminder")
	public ModelAndView editdriverReminder(@ModelAttribute("command") DriverReminderDto driverReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= 		null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// First get the driver_remid all information
			DriverReminderDto driverreminder = driverReminderBL.GetDriverReminder(driverService.getDriverReminder(driverReminderDto.getDriver_remid(), userDetails.getCompany_id()));
			// that getDriverRemindar is show to all details get Driver_id
			// information to driver
			if(driverreminder != null) {
				model.put("driver", driverBL.Show_Driver_Header_Details(driverService.getDriver_Header_Show_Details(driverreminder.getDriver_id())));
				// show edit page the driverRemider List
				model.put("driverreminder", driverReminderBL.GetDriverReminder(driverService.getDriverReminder(driverReminderDto.getDriver_remid(), userDetails.getCompany_id())));
			}
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {

		}
		return new ModelAndView("editDriverReminder", model);
	}

	/*
	 * update DriverReminder and save the previews data save reminder history
	 * table
	 */
	@RequestMapping(value = "/updateDriverReminder", method = RequestMethod.POST)
	public @ResponseBody ModelAndView updateDriverReminder(
			@ModelAttribute("command") DriverReminderDto driverreminderDto,
			@RequestParam("input-file-preview") MultipartFile file) {
		Map<String, Object> model = new HashMap<String, Object>();

		CustomUserDetails		userDetails		= 		null;
		DriverReminder 				validateDriverreminder	= null;
		DriverReminderHistory driverReminderHistory = null;
		String query = " ";
		DriverReminderDto driverreminder1 = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
			try {
				// get the reminder id to get one row to
				driverreminder1 = driverReminderBL.GetDriverReminder(driverService.getDriverReminder(driverreminderDto.getDriver_remid(), userDetails.getCompany_id()));

				// get reminder Dto data change to DriverReminderHistory
				driverReminderHistory =driverReminderBL.prepareDriverReminderHistroy(driverreminder1);
				driverReminderHistory.setCompanyId(userDetails.getCompany_id());
				
				// this message alert of show method
				// ((ModelAndView) model).addObject("error", true);
			} catch (Exception e) {

			}
			DriverReminder driverreminder = driverReminderBL.prepareDriverReminder(driverreminderDto);
			driverreminder.setCompanyId(userDetails.getCompany_id());
			driverreminder.setLastModifiedById(userDetails.getId());
			driverreminder.setLastModified(new Date());
			driverreminder.setNewDriverReminder(false);
			query=" AND D.driver_remid <> "+driverreminder.getDriver_remid()+" ";
			validateDriverreminder =driverService.getExistingDriverReminder(driverreminder.getDriver_id(),driverreminder.getDriverRenewalTypeId(), driverreminder.getDriver_dlfrom(), driverreminder.getDriver_dlto(), userDetails.getCompany_id(),query);
			if(validateDriverreminder == null) {
				if (!file.isEmpty()) {
					try {
						byte[] bytes = file.getBytes();
						driverreminder.setDriver_filename(file.getOriginalFilename());
						driverreminder.setDriver_content(bytes);
						driverreminder.setDriver_contentType(file.getContentType());
					} catch (IOException e) {
						
					}
					driverService.saveDriverReminderHistory(driverReminderHistory);
					driverService.updateDriverReminder(driverreminder);
					
					if(driverreminder1 != null && driverreminder1.getRenewalRecieptId() != null)
					driverService.updateDriverRenewalReceiptStatus(driverreminder1.getRenewalRecieptId(), userDetails.getCompany_id());
					
					model.put("saveDriverReminderHis", true);
					model.put("updateDriverReminder", true);
					return new ModelAndView(
							"redirect:/ShowDriverReminder.in?driver_id=" + driverreminder.getDriver_id() + "", model);
				} else {
					// messages
					model.put("emptyDocument", true);
					return new ModelAndView(
							"redirect:/ShowDriverReminder.in?driver_id=" + driverreminderDto.getDriver_id() + "", model);
					
				}
			}else 
				{
					model.put("alreadyExist", true);
					return new ModelAndView(
							"redirect:/ShowDriverReminder.in?driver_id=" + driverreminderDto.getDriver_id() + "", model);
				}
				

		} catch (Exception e) {
			
			model.put("emptyDocument", true);
			return new ModelAndView(
					"redirect:/ShowDriverReminder.in?driver_id=" + driverreminderDto.getDriver_id() + "", model);

		}
	}

	/* Should be Download Driver Reminder document */
	@RequestMapping("/download/driverReminder/{driver_reminderid}")
	public String downloadReminder(@PathVariable("driver_reminderid") Integer driver_reminderid,
			HttpServletResponse response) throws Exception {
		CustomUserDetails		userDetails		= 		null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			DriverReminderDto file = driverService.getDriverReminder(driver_reminderid, userDetails.getCompany_id());
			if (driver_reminderid == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				if (file != null) {
					if (file.getDriver_content() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getDriver_filename() + "\"");
						if(file.getDriver_contentType().equals(FileUtility.FILE_EXTENSION_PDF)){
							file.setDriver_contentType(FileUtility.getContentType(file.getDriver_contentType()));
						}
						if(file.getDriver_contentType().equals(FileUtility.FILE_EXTENSION_JPG)){
							file.setDriver_contentType(FileUtility.getContentType(file.getDriver_contentType()));
						}
						if(file.getDriver_contentType().equals(FileUtility.FILE_EXTENSION_EXCEL)){
							file.setDriver_contentType(FileUtility.getContentType(file.getDriver_contentType()));
						}
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getDriver_contentType());
						response.getOutputStream().write(file.getDriver_content());
						out.flush();
						out.close();

					}
				}
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (IOException e) {

		}
		return null;
	}

	/* Should be Download Driver Reminder document */
	@RequestMapping("/download/driverReminderHis/{driver_reminderid}")
	public String downloadReminderHis(@PathVariable("driver_reminderid") Integer driver_reminderid,
			HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			DriverReminderHistory file = driverService.getDriverReminderHis(driver_reminderid, userDetails.getCompany_id());
			if (driver_reminderid == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				if (file != null) {
					if (file.getDriver_content() != null) {
						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getDriver_filename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getDriver_contentType());
						response.getOutputStream().write(file.getDriver_content());
						out.flush();
						out.close();

					}
				}

			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (IOException e) {
		}
		return null;
	}

	/* Delete the Driver Reminder */
	@RequestMapping("/deleteDriverReminder")
	public ModelAndView deletedriverReminder(@ModelAttribute("command") DriverReminderDto driverReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// delete method
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driverService.deleteDriverReminder(driverReminderDto.getDriver_remid(), userDetails.getCompany_id());
			model.put("deleteDriverReminder", true);
			return new ModelAndView(
					"redirect:/ShowDriverReminder.in?driver_id=" + driverReminderDto.getDriver_id() + "", model);

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			// messages
			model.put("danger", true);
			return new ModelAndView(
					"redirect:/ShowDriverReminder.in?driver_id=" + driverReminderDto.getDriver_id() + "", model);

		}
	}

	/* Delete the Driver Reminder History */
	@RequestMapping("/deleteDriverReminderHis")
	public ModelAndView deletedriverReminderHistory(
			@ModelAttribute("command") DriverReminderHistoryDto driverReminderDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			// delete method
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driverService.deleteDriverReminderHis(driverReminderDto.getDriver_rhid(), userDetails.getCompany_id());
			// this message alert of show method
			model.put("deleteDriverReminder", true);
			return new ModelAndView("redirect:/ShowReminderHis.in?driver_id=" + driverReminderDto.getDriver_id() + "",
					model);

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			// messages
			model.put("danger", true);
			return new ModelAndView("redirect:/ShowReminderHis.in?driver_id=" + driverReminderDto.getDriver_id() + "",
					model);

		}
	}

	/*
	 *******************************************************************************************************************************************************************	*/
	/* Driver Document */
	/*
	 *******************************************************************************************************************************************************************	*/
	@RequestMapping("/ShowDriverDocument")
	public ModelAndView ShowdriverDocument(@ModelAttribute("command") DriverDocumentDto driverDocument,
			DriverDto driverDto, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		DriverDto				driver		= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driver	= driverService.getDriver_Header_Show_Details(driverDto.getDriver_id()) ;
			if(driver != null) {
				model.put("driver", driverBL.Show_Driver_Header_Details(driver));
				// List of the driverDocument
				model.put("driverDocument", driverDocumentBL.prepareListDriverDocument(driverDocumentService.listDriverDocument(driverDocument.getDriver_id(), userDetails.getCompany_id())));
				
				Object[] count = driverService.countTotal_REMINDER_DOC_COMMENT__AND_PHOTO(driverDto.getDriver_id());
				
				model.put("ReminderCount", (Long) count[0]);
				model.put("CommentCount", (Long) count[1]);
				model.put("PhotoCount", (Long) count[2]);
				model.put("DocumentCount", driverDocumentService.getDriverDocumentCount(driverDto.getDriver_id(), userDetails.getCompany_id()));
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {

		}
		return new ModelAndView("showDriverDocument", model);
	}

	/* add Driver Document */
	@RequestMapping("/addDriverDocument")
	public ModelAndView adddriverDocument(@ModelAttribute("command") DriverDocumentDto driverDocument,
			DriverDto driverDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			model.put("driver", driverBL.Show_Driver_Header_Details(driverService.getDriver_Header_Show_Details(driverDto.getDriver_id())));
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {

		}
		return new ModelAndView("addDriverDocument", model);
	}

	/* save Driver Document */
	@RequestMapping(value = "/uploadDriverDocument")
	public String uploadDriverDocument() {
		return "uploadDriverDocument";
	}

	@RequestMapping(value = "/uploadDriverDocument", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveDriverDocumentUpload(@ModelAttribute("command") org.fleetopgroup.persistence.document.DriverDocument driverDocument,
			DriverDocumentHistory dochistory, @RequestParam("input-file-preview") MultipartFile file) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		if (!file.isEmpty()) {
			try {
				CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				byte[] bytes = file.getBytes();
				driverDocument.setDriver_filename(file.getOriginalFilename());
				driverDocument.setDriver_content(bytes);
				driverDocument.setDriver_contentType(file.getContentType());
				driverDocument.setCompanyId(userDetails.getCompany_id());
				driverDocument.setCreatedById(userDetails.getId());
				driverDocument.setUploaddate(new Date());
			} catch (IOException e) {

			}
			driverDocumentService.saveDriverDoc(driverDocument);
			//driverService.saveDriverDocument(driverDocument);
			// this message alert of show method
			model.put("addDriverDocument", true);

			return new ModelAndView("redirect:/ShowDriverDocument.in?driver_id=" + driverDocument.getDriver_id() + "",
					model);
		} else {
			// messages
			model.put("emptyDocument", true);
			return new ModelAndView("redirect:/ShowDriverReminder.in?driver_id=" + driverDocument.getDriver_id() + "",
					model);

		}

	}

	/* Revise Driver Document Show the Details */
	@RequestMapping("/editDriverDocument")
	public ModelAndView editdriverDocument(@ModelAttribute("command") DriverDocumentDto driverDocumentDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// First get the driver_remid all information
			DriverDocumentDto driverDocument = driverDocumentBL.GetDriverDocument(driverDocumentService.getDriverDocument(driverDocumentDto.getDriver_documentid(), userDetails.getCompany_id()));
			// that getDriverRemindar is show to all details get Driver_id
			// information to driver
			model.put("driver", driverBL.Show_Driver_Header_Details(driverService.getDriver_Header_Show_Details(driverDocument.getDriver_id())));
			// show edit page the driverRemider List
			model.put("driverdocument", driverDocument);
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
		}
		return new ModelAndView("editDriverDocument", model);
	}

	/* add Driver Document revise */
	@RequestMapping(value = "/reviseDriverDocument", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveDriverDocumentRevise(
			@ModelAttribute("driverdocument") org.fleetopgroup.persistence.document.DriverDocument driverDocument, @RequestParam("file") MultipartFile file) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// First get the driver_remid all information
		org.fleetopgroup.persistence.document.DriverDocument driverDocumentOLD = driverDocumentService.getDriverDocument(driverDocument.getDriver_documentid(), userDetails.getCompany_id());

		DriverDocumentHistory dochistory = new DriverDocumentHistory();
		if (!file.isEmpty()) {
			try {
				dochistory.setDriver_filename(driverDocumentOLD.getDriver_filename());
				dochistory.setDriver_content(driverDocumentOLD.getDriver_content());
				dochistory.setDriver_contentType(driverDocumentOLD.getDriver_contentType());
			} catch (Exception e) {

			}
		}
		dochistory.setDriver_docHisname(driverDocumentOLD.getDriver_documentname());
		dochistory.setDriver_docHisFromDate(driverDocumentOLD.getDriver_docFromDate());
		dochistory.setDriver_docHisToDate(driverDocumentOLD.getDriver_docToDate());
		dochistory.setDriver_id(driverDocumentOLD.getDriver_id());
		dochistory.setUploaddate(new Date());
		dochistory.setCompanyId(userDetails.getCompany_id());
		dochistory.setCreatedById(userDetails.getId());

		model.put("updateDriverDocumentHis", true);
		driverService.saveDriverDocumentHistory(dochistory);

		// show the driver Revice in header
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				driverDocument.setDriver_filename(file.getOriginalFilename());
				driverDocument.setDriver_content(bytes);
				driverDocument.setDriver_contentType(file.getContentType());
			} catch (IOException e) {

			}
			driverDocument.setRevdate(new Date());
			driverDocument.getDriver_documentid();
			driverDocument.setCompanyId(userDetails.getCompany_id());
			driverDocument.setCreatedById(userDetails.getId());
			// System.out.println("id="+driverDocument.getDriver_id());
			mongoOperations.save(driverDocument);
			//driverDocumentService.saveDriverDoc(driverDocument);
			model.put("updateDriverDocument", true);
			return new ModelAndView("redirect:/ShowDriverDocument.in?driver_id=" + driverDocument.getDriver_id() + "",
					model);

		} else {
			// messages
			model.put("emptyDocument", true);
			return new ModelAndView("redirect:/ShowDriverReminder.in?driver_id=" + driverDocument.getDriver_id() + "",
					model);

		}
	}

	/* Should be Download Driver Document */
	@RequestMapping("/download/driverDocument/{driver_documentid}")
	public String download(@PathVariable("driver_documentid") Integer driver_documentid, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		org.fleetopgroup.persistence.document.DriverDocument file = driverDocumentService.getDriverDocument(driver_documentid, userDetails.getCompany_id());
		try {
			if (driver_documentid == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				if (file != null) {
					if (file.getDriver_content() != null) {
						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getDriver_filename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getDriver_contentType());
						response.getOutputStream().write(file.getDriver_content());
						out.flush();
						out.close();

					}
				}
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (IOException e) {

		}
		return null;
	}

	/* Delete the Driver Document */
	@SuppressWarnings("null")
	@RequestMapping("/deleteDriverDocument")
	public ModelAndView deletedriverDocument(@ModelAttribute("command") DriverDocumentDto driverDocumentDto,
			BindingResult result) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		if (driverDocumentDto != null) {
			// delete method
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driverDocumentService.deleteDriverDocument(driverDocumentDto.getDriver_documentid(), userDetails.getCompany_id());
			// this message alert of show method
			model.put("deleteDriverDocument", true);
			return new ModelAndView(
					"redirect:/ShowDriverDocument.in?driver_id=" + driverDocumentDto.getDriver_id() + "", model);
		} else {
			// messages
			model.put("emptyDocument", true);
			return new ModelAndView(
					"redirect:/ShowDriverReminder.in?driver_id=" + driverDocumentDto.getDriver_id() + "", model);
		}
	}

	/* Driver Document History */
	@RequestMapping("/ShowDriverDocHis")
	public ModelAndView ShowdriverDocHis(@ModelAttribute("command") DriverDocumentHistoryDto driverDocument,
			DriverDto driverDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("driver", driverBL.Show_Driver_Header_Details(driverService.getDriver_Header_Show_Details(driverDto.getDriver_id())));
			// List of the driverDocument
			model.put("driverDocumentHistory", driverDocumentBL.prepareListDriverDocHis(driverService.listDriverDocHis(driverDto.getDriver_id(), userDetails.getCompany_id())));
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
		return new ModelAndView("showDriverDocHis", model);
	}

	/* Should be Download Driver Document History */
	@RequestMapping("/download/driverDocHis/{driver_docHisid}")
	public String downloadDocHis(@PathVariable("driver_docHisid") Integer driver_docHisid,
			HttpServletResponse response) throws Exception {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			DriverDocumentHistory file = driverService.getDriverDocHis(driver_docHisid, userDetails.getCompany_id());
			if (driver_docHisid == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				if (file != null) {
					if (file.getDriver_content() != null) {
						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getDriver_filename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getDriver_contentType());
						response.getOutputStream().write(file.getDriver_content());
						out.flush();
						out.close();

					}
				}
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (IOException e) {

		}
		return null;
	}

	/* Delete the Driver Document */
	@SuppressWarnings("null")
	@RequestMapping("/deleteDriverDocHis")
	public ModelAndView deletedriverDocHis(@ModelAttribute("command") DriverDocumentHistoryDto driverDocumentDto,
			BindingResult result) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		if (driverDocumentDto != null) {
			// delete method
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driverService.deleteDriverDocHis(driverDocumentDto.getDriver_doHisid(), userDetails.getCompany_id());
			// this message alert of show method
			model.put("deleteDriverDocumentHis", true);
			return new ModelAndView("redirect:/ShowDriverDocHis.in?driver_id=" + driverDocumentDto.getDriver_id() + "",
					model);

		} else {
			// messages
			model.put("emptyDocument", true);
			return new ModelAndView("redirect:/ShowDriverDocHis.in?driver_id=" + driverDocumentDto.getDriver_id() + "",
					model);
		}
	}

	/* Driver Document */
	@RequestMapping("/ShowDriverComment")
	public ModelAndView ShowdriverComment(@ModelAttribute("command") DriverDocumentDto driverDocument,
			DriverDto driverDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		DriverDto		driver	= null;
		try {
			driver	= driverService.getDriver_Header_Show_Details(driverDto.getDriver_id());
			if(driver != null) {
				CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				model.put("driver", driverBL.Show_Driver_Header_Details(driver));
				// List of the driverComment
				model.put("driverComment", driverService.listDriverComment(driverDto.getDriver_id(), userDetails.getCompany_id()));
				
				model.put("user", userDetails.getEmail());
				
				Object[] count = driverService.countTotal_REMINDER_DOC_COMMENT__AND_PHOTO(driverDto.getDriver_id());
				
				model.put("ReminderCount", (Long) count[0]);
				//model.put("DocumentCount", (Long) count[1]);
				model.put("CommentCount", (Long) count[1]);
				model.put("PhotoCount", (Long) count[2]);
				model.put("DocumentCount", driverDocumentService.getDriverDocumentCount(driverDto.getDriver_id(), userDetails.getCompany_id()));
		
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {

		}
		return new ModelAndView("showDriverComment", model);
	}

	/* Save the Driver Comments */
	@RequestMapping(value = "/saveDriverComment", method = RequestMethod.POST)
	public ModelAndView saveDriverComment(@ModelAttribute("command") DriverCommentDto driverCommentDto,
			BindingResult result) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		DriverComment driverComment = driverCommentBL.prepareDriverComment(driverCommentDto);
		if (driverComment != null) {
			driverComment.setCompanyId(userDetails.getCompany_id());
			driverComment.setCreatedById(userDetails.getId());
			driverService.addDriverComment(driverComment);
			// this message alert of show method
			model.put("SaveComment", true);
			return new ModelAndView("redirect:/ShowDriverComment.in?driver_id=" + driverCommentDto.getDriver_id() + "",
					model);
		} else {
			// messages
			model.put("emptyDocument", true);
			return new ModelAndView("redirect:/ShowDriverComment.in?driver_id=" + driverCommentDto.getDriver_id() + "",
					model);
		}
	}

	/* Delete the Driver Comment */
	@SuppressWarnings("null")
	@RequestMapping(value = "deleteDriverComment")
	public ModelAndView deleteComment(@ModelAttribute("commend") DriverCommentDto driverCommentDto,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			//driver_commentid , driver_id
			if (driverCommentDto != null) {
				CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				// delete method
				driverService.deleteDriverComment(driverCommentDto.getDriver_commentid(), userDetails.getCompany_id());
				// this message alert of show method
				model.put("deleteDriverComment", true);
				return new ModelAndView(
						"redirect:/ShowDriverComment.in?driver_id=" + driverCommentDto.getDriver_id() + "", model);
			} else {
				// messages
				model.put("danger", true);
				return new ModelAndView(
						"redirect:/ShowDriverComment.in?driver_id=" + driverCommentDto.getDriver_id() + "", model);
			}
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			model.put("danger", true);
			return new ModelAndView("redirect:/ShowDriverComment.in?driver_id=" + driverCommentDto.getDriver_id() + "",
					model);
		}
	}

	/*
	 *******************************************************************************************************************************************************************	*/
	/* Driver Photo */
	/*
	 *******************************************************************************************************************************************************************	*/
	/* Driver Photo */
	@RequestMapping("/ShowDriverPhoto")
	public ModelAndView Showdriverphoto(@ModelAttribute("command") DriverPhotoDto driverPhoto, DriverDto driverDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		DriverDto		driver		= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driver	= driverService.getDriver_Header_Show_Details(driverDto.getDriver_id());
			if(driver != null) {
				
				model.put("driver", driverBL.Show_Driver_Header_Details(driver));
				// List of the driverDocument
				model.put("driverPhoto", driverDocumentBL.prepareListDriverPhoto(driverService.listDriverPhoto(driverPhoto.getDriver_id(), userDetails.getCompany_id())));
				
				Object[] count = driverService.countTotal_REMINDER_DOC_COMMENT__AND_PHOTO(driverDto.getDriver_id());
				
				model.put("ReminderCount", (Long) count[0]);
				//model.put("DocumentCount", (Long) count[1]);
				model.put("CommentCount", (Long) count[1]);
				model.put("PhotoCount", driverDocumentService.getDriverPhotoCount(driverDto.getDriver_id(), userDetails.getCompany_id()));
				model.put("DocumentCount", driverDocumentService.getDriverDocumentCount(driverDto.getDriver_id(), userDetails.getCompany_id()));
		
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			driver = null;
		}
		return new ModelAndView("showDriverPhoto", model);
	}

	/* save Driver Document */
	@RequestMapping(value = "/uploadDriverPhoto", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveDriverPhoto(@ModelAttribute("command") org.fleetopgroup.persistence.document.DriverPhoto driverPhoto,
			@RequestParam("file") MultipartFile file) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					driverPhoto.setDriver_filename(file.getOriginalFilename());

					driverPhoto.setDriver_content(ByteImageConvert.scale(bytes, 350, 350));
					driverPhoto.setDriver_contentType(file.getContentType());
					driverPhoto.setCompanyId(userDetails.getCompany_id());
				} catch (IOException e) {

				}

				driverPhoto.setUploaddate(new Date());
				driverPhoto.setCreatedById(userDetails.getId());
				driverService.addDriverPhoto(driverPhoto);

				model.put("addDriverPhoto", true);
				return new ModelAndView("redirect:/ShowDriverPhoto.in?driver_id=" + driverPhoto.getDriver_id() + "",
						model);
			} else {
				// messages
				model.put("emptyDocument", true);
				return new ModelAndView("redirect:/ShowDriverPhoto.in?driver_id=" + driverPhoto.getDriver_id() + "",
						model);

			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			model.put("emptyDocument", true);
			return new ModelAndView("redirect:/ShowDriverPhoto.in?driver_id=" + driverPhoto.getDriver_id() + "", model);

		}
	}

	/* show the image of the driver photo */
	@RequestMapping("/getImage/{driver_photoid}")
	public String getImage(@PathVariable("driver_photoid") Integer driver_photoid, HttpServletResponse response) throws Exception {
		try {
			
			org.fleetopgroup.persistence.document.DriverPhoto file = driverService.getDriverPhoto(driver_photoid);
			if (driver_photoid == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				if (file != null) {
					if (file.getDriver_content() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getDriver_filename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getDriver_contentType());
						response.getOutputStream().write(file.getDriver_content());
						out.flush();
						out.close();

						/*
						 * response.setContentType(
						 * "image/jpeg, image/jpg, image/png, image/gif");
						 * response.getOutputStream().write(file.
						 * getDriver_content());
						 * response.getOutputStream().close();
						 */

					}
				}
			}
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {

		}
		return null;
	}

	/* Delete the Driver Photo */
	@RequestMapping(value = "deleteDriverPhoto")
	public ModelAndView deletePhoto(@ModelAttribute("command") DriverPhoto driverPhotoDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			DriverDto driver = driverBL.Show_Driver_Header_Details(driverService.getDriver_Header_Show_Details(driverPhotoDto.getDriver_id()));
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if ((driver.getDriver_photoid()).equals(driverPhotoDto.getDriver_photoid())) {
				// this message alert of show method
				model.put("deleteDriverPhoto_was_already", true);

				return new ModelAndView("redirect:/ShowDriverPhoto.in?driver_id=" + driverPhotoDto.getDriver_id() + "",
						model);

			} else {
				// delete method
				driverService.deleteDriverPhoto(driverPhotoDto.getDriver_photoid(), userDetails.getCompany_id());
				// this message alert of show method
				model.put("deleteDriverPhoto", true);
				return new ModelAndView("redirect:/ShowDriverPhoto.in?driver_id=" + driverPhotoDto.getDriver_id() + "",
						model);
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			model.put("danger", true);
			return new ModelAndView("redirect:/ShowDriverPhoto.in?driver_id=" + driverPhotoDto.getDriver_id() + "",
					model);
		}
	}

	/* Driver Photo */
	@RequestMapping("/setPhoto")
	public ModelAndView setphoto(@ModelAttribute("command") DriverPhoto driverPhotoDto, BindingResult result) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		org.fleetopgroup.persistence.document.DriverPhoto driverPhoto = driverDocumentBL.GetDriverPhoto(driverService.getDriverPhoto(driverPhotoDto.getDriver_photoid(), userDetails.getCompany_id()));

		try {
			// First get the driver_remid all information
			// delete method
			model.put("updateDriverPhoto", true);
			driverService.setprofilePhoto(driverPhoto.get_id(), driverPhoto.getDriver_id());
			// this message alert of show method
			return new ModelAndView("redirect:/ShowDriverPhoto.in?driver_id=" + driverPhoto.getDriver_id() + "", model);
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			model.put("danger", true);
			return new ModelAndView("redirect:/ShowDriverPhoto.in?driver_id=" + driverPhotoDto.getDriver_id() + "",
					model);
		}
	}

	@RequestMapping(value = "/importDriver", method = RequestMethod.POST)
	public ModelAndView saveImport(@ModelAttribute("command") VehicleDocument photo,
			@RequestParam("import") MultipartFile file, HttpServletRequest request) throws Exception {
		CustomUserDetails		userDetails			= null;
		List<VehicleGroup>		vehicleGroupList	= null;
		boolean					vehicleGroupFound	= false;
		List<DriverJobType>  	driverJobTypes		= null;
		List<String>            duplicateDriver		= null;
		List<Vehicle>			vehicleList			= null;
		try { 
			Map<String, Object> model = new HashMap<String, Object>();
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleGroupList	= vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id());
			String rootPath 	= request.getSession().getServletContext().getRealPath("/");
			driverJobTypes		= driverJobTypeService.listDriverJobTypeByCompanyId(userDetails.getCompany_id());
			vehicleList			= vehicleService.getVehicleListByCompanyId(userDetails.getCompany_id());
			
			duplicateDriver	= new ArrayList<>();
			
			File dir = new File(rootPath + File.separator + "uploadedfile");
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());

			try {
				try (InputStream is = file.getInputStream();
						BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
					int i;
					// write file to server
					while ((i = is.read()) != -1) {
						stream.write(i);
					}
					stream.flush();
				}
			} catch (IOException e) {
				/*
				 * model.put("msg", "failed to process file because : " +
				 * e.getMessage()); return "mainpage";
				 */
				e.printStackTrace();
			}

			// Count How many Import SuccessFully
			int CountSuccess = 0;
			int CountDuplicate = 0;

			String[] nextLine;
			try {
				// read file
				// CSVReader(fileReader, ';', '\'', 1) means
				// using separator ; and using single quote ' . Skip first line when
				// read

				try (FileReader fileReader = new FileReader(serverFile);
						CSVReader reader = new CSVReader(fileReader, ';', '\'', 1);) {
					while ((nextLine = reader.readNext()) != null) {
						Driver driver = new Driver();

						for (int i = 0; i < nextLine.length; i++) {
							try {
								
								String[] importDriver = nextLine[i].split(",");
								
								// getting import CSV file column 1 Redistration
								driver.setDriver_firstname(importDriver[0]);
								driver.setDriver_Lastname(importDriver[1]);
								driver.setDriver_fathername(importDriver[2]);
								driver.setDriver_dateofbirth(importDriver[3]);
								driver.setDriver_Qualification(importDriver[4]);
								driver.setDriver_bloodgroup(importDriver[5]);
								driver.setDriver_languages(importDriver[6]);
								//driver.setDriver_group(importDriver[7]);
								driver.setDriver_email(importDriver[8]);
								driver.setDriver_mobnumber(importDriver[9]);
								driver.setDriver_homenumber(importDriver[10]);
								driver.setDriver_address(importDriver[11]);
								driver.setDriver_address2(importDriver[12]);
								driver.setDriver_city(importDriver[13]);
								driver.setDriver_state(importDriver[14]);
								driver.setDriver_pincode(importDriver[15]);
								driver.setDriver_country(importDriver[16]);
								driver.setDriver_empnumber(importDriver[17]);
								driver.setDriver_insuranceno(importDriver[18]);
								driver.setDriver_esino(importDriver[19]);
								driver.setDriver_pfno(importDriver[20]);
							//	driver.setDriver_jobtitle(importDriver[21]);
								for(DriverJobType driverJobType : driverJobTypes) {
									if(driverJobType.getDriJobType().equalsIgnoreCase(importDriver[21].trim())) {
										driver.setDriJobId(driverJobType.getDriJobId());
										break;
									}
								}
								driver.setDriver_startdate(importDriver[22]);

								driver.setDriver_dlnumber(importDriver[23]);
								driver.setDriver_badgenumber(importDriver[24]);
								driver.setDriver_dlclass(importDriver[25]);
								driver.setDriver_dlprovince(importDriver[26]);
								driver.setDriverSalaryTypeId(DriverStatus.DRIVER_SALARY_TYPE_PER_DAY);
								driver.setDriver_authorised(importDriver[27]);
								driver.setDriver_reffristname(importDriver[28]);
								driver.setDriver_reflastname(importDriver[29]);
								driver.setDriver_refcontect(importDriver[30]);
								driver.setDriverStatusId(DriverStatus.DRIVER_STATUS_ACTIVE);
								driver.setDriver_aadharnumber(importDriver[31]);
								driver.setDriver_banknumber(importDriver[32]);
								driver.setDriver_bankifsc(importDriver[33]);
								driver.setDriver_bankname(importDriver[34]);
								//driver.setDriver_active(importDriver[31]);
								driver.setCompanyId(userDetails.getCompany_id());
								
								if(vehicleGroupList != null) {
									for(VehicleGroup vehicleGroup : vehicleGroupList) {
										if(importDriver[7].trim().equalsIgnoreCase(vehicleGroup.getvGroup())) {
										   driver.setVehicleGroupId(vehicleGroup.getGid());
										   //driver.setDriver_group(vehicleGroup.getvGroup());
										   vehicleGroupFound	= true;
										}
									}
								}
								
								
								  if(vehicleList != null) {
									  for(Vehicle vehicle : vehicleList) {
										  if(importDriver[35].trim().equalsIgnoreCase(vehicle.getVehicle_registration())) { 
											  driver.setVid(vehicle.getVid()); 
											  break; 
										   } 
									  } 
								  }
								 
																
								if(!vehicleGroupFound) {
									///LOGGER.info(driver.getDriver_group() +" Does not Exists !");
									model.put("vehicleGroupNotFound", true);
									continue;
								}
								/**
								 * who Create this vehicle get email id to user
								 * profile details
								 */
								
								driver.setCreatedById(userDetails.getId());
								driver.setLastModifiedById(userDetails.getId());

								Date currentDateUpdate = new Date();
								Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

								driver.setCreated(toDate);
								driver.setLastupdated(toDate);

								driver.setMarkForDelete(false);

								driver.setDriver_photoid(1);
								
								
								
								try {
									List<Driver> validate =  null;
									if(importDriver[21].trim().equalsIgnoreCase("Driver")) {
										validate = driverService.ValidateDriver(driver.getDriver_dlnumber(),
												driver.getDriver_empnumber(), userDetails.getCompany_id());
									}else {
										validate = driverService.ValidateEmpDriver(driver.getDriver_empnumber(), userDetails.getCompany_id());
									}
									
									
									if (validate == null || validate.isEmpty()) {
										driverService.addDriver(driver);
										CountSuccess++;
										
										if (!importDriver[37].trim().equalsIgnoreCase("")) {
											final DriverReminder driverReminder = new DriverReminder();
											driverReminder.setDriver_id(driver.getDriver_id());
											driverReminder.setDriverRenewalTypeId((long) 1);
											driverReminder.setDriver_dlnumber(driver.getDriver_dlnumber());
											driverReminder.setDriver_dlfrom(format.parse(importDriver[36].trim()));
											driverReminder.setDriver_dlto(format.parse(importDriver[37].trim()));
											if (!importDriver[38].trim().equalsIgnoreCase("")) {
												driverReminder.setDriver_timethreshold(
														Integer.parseInt(importDriver[38].trim() + ""));
											} else {
												driverReminder.setDriver_timethreshold(1);
											}
											driverReminder.setDriver_periedthreshold(7);
											driverReminder.setDriver_renewaldate(importDriver[37].trim());
											driverReminder.setCompanyId(userDetails.getCompany_id());
											driverReminder.setCreatedById(userDetails.getId());
											driverReminder.setCreated(DateTimeUtility.getCurrentDate());

											driverService.addDriverReminder(driverReminder);
										}
										
									} else {
										++CountDuplicate;
										duplicateDriver.add(driver.getDriver_firstname() +"_"+driver.getDriver_Lastname()+"_"+driver.getDriver_dlnumber()+"_"+driver.getDriver_empnumber());
										model.put("alreadyDriver", true);
									}
								} catch (Exception e) {
									++CountDuplicate;
									String Duplicate = "Driver =" + driver.getDriver_firstname() + " "
											+ driver.getDriver_Lastname();
									duplicateDriver.add(driver.getDriver_firstname() +"_"+driver.getDriver_Lastname()+"_"+driver.getDriver_dlnumber()+"_"+driver.getDriver_empnumber());
									model.put("CountDuplicate", CountDuplicate);
									model.put("Duplicate", Duplicate);
									model.put("importSaveError", true);
									System.err.println("Exception : "+e);
								}

							} catch (Exception e) { // catch for For loop in get
													// import file
								// System.out.println("error while reading csv and
								// put to db : " + e.getMessage());
								e.printStackTrace();

								model.put("importSaveError", true);
								System.err.println("Exception : "+e);
								return new ModelAndView("redirect:/getDriversList.html", model);
							}

						} // for loop close

					}
				}
			} catch (Exception e) {
				// System.out.println("error while reading csv and put to db : " +
				// e.getMessage());
				try {
					model.put("CountDuplicate", CountDuplicate);
					model.put("importSaveError", true);
					System.err.println("Exception : "+e);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
				return new ModelAndView("redirect:/getDriversList.html", model);
			}
			LOGGER.error("Duplicate Driver List  ", duplicateDriver);
			System.err.println("Duplicate Driver List  "+ duplicateDriver);
			// show the Vehicle List
			try {
				model.put("CountSuccess", CountSuccess);
				model.put("importSave", true);
				LOGGER.error("Duplicate Driver List  ", duplicateDriver);

			} catch (Exception e) {
				LOGGER.error("Driver List  ", e);
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/getDriversList.html", model);
		
		} catch (Exception e) {
			throw e;
		}
	}

	@RequestMapping(value = "/getDriverALLList", method = RequestMethod.POST)
	public void getDriverALLList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<DriverDto> Driver = new ArrayList<DriverDto>();
		List<Driver> drivertype = driverService.Search_ALL_Driver_LIST_AJAX(term);
		if (drivertype != null && !drivertype.isEmpty()) {
			for (Driver add : drivertype) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_firstname(add.getDriver_firstname());
				wadd.setDriver_Lastname(add.getDriver_Lastname());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(Driver));
	}
	
	@RequestMapping(value = "/getDriverALLListOfCompany", method = RequestMethod.POST)
	public void getDriverALLListOfCompany(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<DriverDto> Driver = new ArrayList<DriverDto>();
		List<Driver> drivertype = driverService.Search_ALL_Driver_LIST_AJAX(term, userDetails);
		if (drivertype != null && !drivertype.isEmpty()) {
			for (Driver add : drivertype) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_firstname(add.getDriver_firstname());
				wadd.setDriver_Lastname(add.getDriver_Lastname());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				wadd.setDriver_fathername(add.getDriver_fathername());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(Driver));
	}
	
	@RequestMapping("/EditDriverSalaryForGroup")
	public ModelAndView EditDriverSalaryForGroup() {
		Map<String, Object> model = new HashMap<String, Object>();

		// show the Group service of the driver
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// Driver Report
			model.put("driverJobType", driverJobTypeBL.DriJobTypeListofBean(driverJobTypeService.listDriverJobTypeByCompanyId(userDetails.getCompany_id())));
			model.put("vehiclegroup", vehicleGroupBL.prepareListofDto(vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id())));

		} catch (Exception e) {

		}
		return new ModelAndView("EditDriverSalaryForGroup", model);
	}

	/* show main page of driver */
	@RequestMapping(value = "/saveDriverSalaryEdit", method = RequestMethod.POST)
	public ModelAndView DriverReport(@ModelAttribute("command") DriverDto driverDto, BindingResult result,RedirectAttributes redirectAttributes) {
		Map<String, Object> model = new HashMap<String, Object>();

		// Driver Driver = RBL.prepareModelDriver(driverDto);
		CustomUserDetails userDetails = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			driverService.updateDriverSalaryByGroupId(driverDto, userDetails.getCompany_id());
			
		} catch (Exception e) {
			model.put("salaryUpdateError", true);
			e.printStackTrace();
		}
		model.put("salaryUpdated", true);
		//return new ModelAndView("EditDriverSalaryForGroup", model);
		//return new ModelAndView("redirect:/EditDriverSalaryForGroup.in", model);
		return new ModelAndView("redirect:/driver/"+driverDto.getDriJobId()+"/1.in", model);
	}
	
	@RequestMapping(value = "/getDriverAndConductorByVehicleGroupId", method = RequestMethod.GET)
	public void getDriverByVehicleGroupId(@RequestParam(value = "vehicleGroupId", required = true) Long vehicleGroupId,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<DriverDto> Driver = new ArrayList<DriverDto>();
		List<Driver> drivertype = driverService.getDriverAndConductorListByVehicleGroupId(vehicleGroupId, userDetails.getCompany_id());
		if (drivertype != null && !drivertype.isEmpty()) {
			for (Driver add : drivertype) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_firstname(add.getDriver_firstname());
				wadd.setDriver_Lastname(add.getDriver_Lastname());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(Driver));
	}
	@RequestMapping(value = "/getConductorListByVehicleGroupId", method = RequestMethod.POST)
	public void getConductorListByVehicleGroupId(@RequestParam(value = "vehicleGroupId", required = true) Long vehicleGroupId,
			@RequestParam("term") final String term,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<DriverDto> Driver = new ArrayList<DriverDto>();
		List<Driver> drivertype = driverService.getConductorListByVehicleGroupId(term, vehicleGroupId, userDetails.getCompany_id());
		if (drivertype != null && !drivertype.isEmpty()) {
			for (Driver add : drivertype) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_firstname(add.getDriver_firstname());
				wadd.setDriver_Lastname(add.getDriver_Lastname());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(Driver));
	}
	@RequestMapping(value = "/getConductorByVehicleGroupId", method = RequestMethod.GET)
	public void getConductorByVehicleGroupId(Map<String, Object> map, @RequestParam(value = "vehicleGroupId", required = true) Long vehicleGroupId,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<DriverDto> Driver = new ArrayList<DriverDto>();
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Driver> driverDtos = driverService.getConductorListByVehicleGroupId(vehicleGroupId, userDetails.getCompany_id());
		if (driverDtos != null && !driverDtos.isEmpty()) {
			for (Driver add : driverDtos) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				wadd.setDriver_firstname(add.getDriver_firstname());
				wadd.setDriver_Lastname(add.getDriver_Lastname());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();

		// System.out.println("jsonStudents = " + gson.toJson(Driver));

		response.getWriter().write(gson.toJson(Driver));
	}
	
	@RequestMapping(value = "/getDriverByVehicleGroupId", method = RequestMethod.GET)
	public void getDriverByVehicleGroupId(Map<String, Object> map, @RequestParam(value = "vehicleGroupId", required = true) Long vehicleGroupId,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<DriverDto> Driver = new ArrayList<DriverDto>();
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Driver> driverDtos = driverService.getDriverListByVehicleGroupId(vehicleGroupId, userDetails.getCompany_id());
		if (driverDtos != null && !driverDtos.isEmpty()) {
			for (Driver add : driverDtos) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());				
				wadd.setDriver_empnumber(add.getDriver_empnumber());				
				wadd.setDriver_firstname(add.getDriver_firstname());				
				wadd.setDriver_Lastname(add.getDriver_Lastname());				
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();


		response.getWriter().write(gson.toJson(Driver));
	}
	
	@RequestMapping("/removeDriverPhoto")
	public ModelAndView removeDriverPhoto(@ModelAttribute("command") DriverPhoto driverPhotoDto, BindingResult result) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		Integer			defaultPhotoId			= 1;
		try {
			// delete method
			driverService.setprofilePhoto(defaultPhotoId, driverPhotoDto.getDriver_id());
			// messages
			model.put("updateDriverPhoto", true);
			return new ModelAndView("redirect:/ShowDriverPhoto.in?driver_id=" + driverPhotoDto.getDriver_id() + "", model);
		} catch (Exception e) {
			LOGGER.error("Add Vehicle Status Page:", e);
			return new ModelAndView("redirect:/NotFound.in");
		}
	}
	
	
	@RequestMapping(value = "/getDriverTypeListOfCompany", method = RequestMethod.POST)
	public void getDriverTypeListOfCompany(Map<String, Object> map, @RequestParam("term") final String term,@RequestParam("driverType") final int driverType,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<DriverDto> Driver = new ArrayList<DriverDto>();
		try {
			
	
		String query =" ";
		if(driverType > 0) {
			
			query=" AND d.driJobId ="+driverType+" ";
		}
		List<Driver> drivertype = driverService.SearchALLDriverTypeListAJAX(term,query, userDetails);
		if (drivertype != null && !drivertype.isEmpty()) {
			for (Driver add : drivertype) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_firstname(add.getDriver_firstname());
				wadd.setDriver_Lastname(add.getDriver_Lastname());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				wadd.setDriver_fathername(add.getDriver_fathername());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(Driver));
	}
	 catch (Exception e) {
		e.printStackTrace();
	}
	}
		
	@RequestMapping(value = "/TomorrowDLRenewal", method = RequestMethod.GET)
	public ModelAndView TomorrowDLRenewal() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// show the driver list in all
			
			Object[] count = driverService.countTotalDriver_AND_TodayRenewal();
			model.put("TodayDLRenewalcount", (Long) count[1]);
			
			Object[] countTomorrow = driverService.countTotalDriver_AND_TomorrowRenewal();
			
			model.put("DriverCount", (Long) countTomorrow[0]);
			model.put("TomorrowDLRenewalcount", (Long) countTomorrow[1]);
			
			Object[] countNextSeven = driverService.countTotalDriver_AND_nextSevenDayRenewal();
			model.put("NextSevenDLRenewalcount", (Long) countNextSeven[1]);
			
			Object[] countNextFifteen = driverService.countTotalDriver_AND_nextFifteenDayRenewal();
			model.put("NextFifteenDLRenewalcount", (Long) countNextFifteen[1]);
			
			Object[] countNextMonth = driverService.countTotalDriver_AND_nextMonthDayRenewal();
			model.put("NextMonthDLRenewalcount", (Long) countNextMonth[1]);

			model.put("SelectJob", "DRIVER");
			model.put("SelectPage", 1);

			java.util.Date currentDate = new java.util.Date();
			DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");

			model.put("driverReminder", driverReminderBL.prepareListDriverReminderBean(driverService.listof_Tomorrow_DL_Renewal(ft.format(currentDate))));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("tomorrowDLRenewal", model);
	}
	
		@RequestMapping(value = "/NextSevenDLRenewal", method = RequestMethod.GET)
		public ModelAndView NextSevenDLRenewal() {
			Map<String, Object> model = new HashMap<String, Object>();
			try {
				// show the driver list in all
				Object[] count = driverService.countTotalDriver_AND_TodayRenewal();
				model.put("TodayDLRenewalcount", (Long) count[1]);
				
				Object[] countTomorrow = driverService.countTotalDriver_AND_TomorrowRenewal();
				model.put("TomorrowDLRenewalcount", (Long) countTomorrow[1]);
				
				Object[] countNextSeven = driverService.countTotalDriver_AND_nextSevenDayRenewal();
				model.put("DriverCount", (Long) countNextSeven[0]);
				model.put("NextSevenDLRenewalcount", (Long) countNextSeven[1]);
				
				Object[] countNextFifteen = driverService.countTotalDriver_AND_nextFifteenDayRenewal();
				
				model.put("NextFifteenDLRenewalcount", (Long) countNextFifteen[1]);
				
				Object[] countNextMonth = driverService.countTotalDriver_AND_nextMonthDayRenewal();
				model.put("NextMonthDLRenewalcount", (Long) countNextMonth[1]);

				model.put("SelectJob", "DRIVER");
				model.put("SelectPage", 1);

				java.util.Date currentDate = new java.util.Date();
				DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");

				model.put("driverReminder", driverReminderBL.prepareListDriverReminderBean(driverService.listof_nextSevenDay_DL_Renewal(ft.format(currentDate))));

			} catch (NullPointerException e) {
				return new ModelAndView("redirect:/NotFound.in");
			} catch (Exception e) {

				e.printStackTrace();
			}
			return new ModelAndView("nextSevenDLRenewal", model);
		} 
			
	@RequestMapping(value = "/NextFifteenDLRenewal", method = RequestMethod.GET)
	public ModelAndView NextFifteenDLRenewal() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// show the driver list in all
			Object[] count = driverService.countTotalDriver_AND_TodayRenewal();
			model.put("TodayDLRenewalcount", (Long) count[1]);
			
			Object[] countTomorrow = driverService.countTotalDriver_AND_TomorrowRenewal();
			model.put("TomorrowDLRenewalcount", (Long) countTomorrow[1]);
			
			Object[] countNextSeven = driverService.countTotalDriver_AND_nextSevenDayRenewal();
			model.put("NextSevenDLRenewalcount", (Long) countNextSeven[1]);
			
			Object[] countNextFifteen = driverService.countTotalDriver_AND_nextFifteenDayRenewal();
			model.put("DriverCount", (Long) countNextFifteen[0]);
			model.put("NextFifteenDLRenewalcount", (Long) countNextFifteen[1]);
			
			Object[] countNextMonth = driverService.countTotalDriver_AND_nextMonthDayRenewal();
			model.put("NextMonthDLRenewalcount", (Long) countNextMonth[1]);

			model.put("SelectJob", "DRIVER");
			model.put("SelectPage", 1);

			java.util.Date currentDate = new java.util.Date();
			DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");

			model.put("driverReminder", driverReminderBL.prepareListDriverReminderBean(driverService.listof_nextFifteenDay_DL_Renewal(ft.format(currentDate))));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("nextFifteenDLRenewal", model);
	} 

	@RequestMapping(value = "/NextMonthDLRenewal", method = RequestMethod.GET)
	public ModelAndView NextMonthDLRenewal() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// show the driver list in all
			Object[] count = driverService.countTotalDriver_AND_TodayRenewal();
			model.put("TodayDLRenewalcount", (Long) count[1]);
			
			Object[] countTomorrow = driverService.countTotalDriver_AND_TomorrowRenewal();
			model.put("TomorrowDLRenewalcount", (Long) countTomorrow[1]);
			
			Object[] countNextSeven = driverService.countTotalDriver_AND_nextSevenDayRenewal();
			model.put("NextSevenDLRenewalcount", (Long) countNextSeven[1]);
			
			Object[] countNextFifteen = driverService.countTotalDriver_AND_nextFifteenDayRenewal();
			
			model.put("NextFifteenDLRenewalcount", (Long) countNextFifteen[1]);
			
			Object[] countNextMonth = driverService.countTotalDriver_AND_nextMonthDayRenewal();
			model.put("DriverCount", (Long) countNextMonth[0]);
			model.put("NextMonthDLRenewalcount", (Long) countNextMonth[1]);

			model.put("SelectJob", "DRIVER");
			model.put("SelectPage", 1);

			java.util.Date currentDate = new java.util.Date();
			DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");

			model.put("driverReminder", driverReminderBL.prepareListDriverReminderBean(driverService.listof_nextMonthDay_DL_Renewal(ft.format(currentDate))));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("nextMonthDLRenewal", model);
	} 
}
