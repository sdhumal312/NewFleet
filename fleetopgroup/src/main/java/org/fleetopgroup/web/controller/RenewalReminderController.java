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
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.RenealReminderStatus;
import org.fleetopgroup.constant.RenewalReminderConfiguration;
import org.fleetopgroup.constant.VehicleAgentConstant;
import org.fleetopgroup.constant.VehicleConfigurationContant;
import org.fleetopgroup.constant.VehicleOwnerShip;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.persistence.bl.DriverReminderBL;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.bl.RenewalTypeBL;
import org.fleetopgroup.persistence.bl.VehicleAgentTxnCheckerBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.dao.UserRepository;
import org.fleetopgroup.persistence.dao.VehicleRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.RenewalReminderHistoryDto;
import org.fleetopgroup.persistence.dto.RenewalSubTypeDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleMandatoryDto;
import org.fleetopgroup.persistence.model.RenewalReminder;
import org.fleetopgroup.persistence.model.RenewalReminderHistory;
import org.fleetopgroup.persistence.model.RenewalReminderSequenceCounter;
import org.fleetopgroup.persistence.model.RenewalSubType;
import org.fleetopgroup.persistence.model.RenewalType;
import org.fleetopgroup.persistence.model.User;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleAgentTxnChecker;
import org.fleetopgroup.persistence.model.WorkOrders;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalSubTypeService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalTypeService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentIncomeExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleMandatoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.FileUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import au.com.bytecode.opencsv.CSVReader;

@Controller
public class RenewalReminderController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IRenewalTypeService RenewalTypeService;

	@Autowired
	private IRenewalSubTypeService RenewalSubTypeService;
	@Autowired
	private IRenewalReminderService RenewalReminderService;
	@Autowired
	private IVehicleService vehicleService;
	
	@Autowired
	private IVehicleMandatoryService vehicleMandatoryService;

	@Autowired
	private IRenewalReminderSequenceService renewalReminderSequenceService;

	@Autowired
	private IWorkOrdersService workOrdersService;

	@Autowired
	private IDriverService driverService;
	
	@Autowired
	private UserRepository	userService;
	
	@Autowired private IVehicleDocumentService	vehicleDocumentService;
	
	@Autowired private IRenewalReminderDocumentService	documentService;
	
	@Autowired private ICompanyConfigurationService	companyConfigurationService;
	@Autowired IVehicleAgentTxnCheckerService			vehicleAgentTxnCheckerService;
	@Autowired private	IVehicleAgentIncomeExpenseDetailsService			vehicleAgentIncomeExpenseDetailsService;
	@Autowired private	VehicleRepository			VehicleRepository;

	

	RenewalTypeBL DriDT = new RenewalTypeBL();
	RenewalReminderBL RRBL = new RenewalReminderBL();
	DriverReminderBL DriverRem = new DriverReminderBL();
	VehicleAgentTxnCheckerBL	agentTxnCheckerBL			= new VehicleAgentTxnCheckerBL();
	VehicleBL VBL = new VehicleBL();
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatName = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	SimpleDateFormat SQL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sqlFormatTime = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

	@RequestMapping(value = "/searchRenewalReminder", method = RequestMethod.POST)
	public ModelAndView searchVehicle(@RequestParam("vehicle_registration") final String Renewal_id,
			final HttpServletResponse result) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			model.put("renewal",
					RRBL.prepareList_Only_Search(RenewalReminderService.SearchRenewalReminder(Renewal_id)));

			model.put("SelectStatus", RenealReminderStatus.NOT_APPROVED);
			model.put("SelectPage", 1);
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Renewal Reminder:", e);
		}
		return new ModelAndView("Renewal_Reminder_Report", model);
	}

	@RequestMapping("/SearchRenRemShow")
	public ModelAndView SearchRenRemShow(@RequestParam("vehicle_registration") final Long Renewal_id,
			final HttpServletResponse result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (Renewal_id != null) {
				model.put("renewalReminder",
						RRBL.GetRenewalReminder(RenewalReminderService.getRenewalReminder(Renewal_id)));
			}

			model.put("SelectStatus", RenealReminderStatus.NOT_APPROVED);
			model.put("SelectStatusString",
					RenealReminderStatus.getRenewalStatusName(RenealReminderStatus.NOT_APPROVED));
			model.put("SelectPage", 1);
		} catch (NullPointerException e) {
			model.put("NotFound", true);
			return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
		} catch (Exception e) {
			model.put("NotFound", true);
			return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
		}

		return new ModelAndView("showRenewal_Reminder", model);
	}

	/* show main page of Renewal Reminder */
	@RequestMapping(value = "/RenewalReminder/{Status}/{pageNumber}", method = RequestMethod.GET)
	public String RenewalReminderList(@PathVariable("Status") short Status,
			@PathVariable("pageNumber") Integer pageNumber, Model model) throws Exception {

		long RenewalReminderCount = 0;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Page<RenewalReminder> page = RenewalReminderService.getDeployment_Page_RenewalReminder(Status, pageNumber,
					userDetails);
			if (page != null) {
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				model.addAttribute("deploymentLog", page);
				model.addAttribute("beginIndex", begin);
				model.addAttribute("endIndex", end);
				model.addAttribute("currentIndex", current);
				RenewalReminderCount = page.getTotalElements();
			}

			model.addAttribute("RenewalReminderCount", RenewalReminderCount);

			List<RenewalReminderDto> pageList = RRBL.Only_Show_ListofRenewal(RenewalReminderService.Find_listRenewalReminder(Status, pageNumber, userDetails));

			model.addAttribute("renewal", pageList);

			model.addAttribute("SelectStatus", Status);
			model.addAttribute("SelectPage", pageNumber);

			java.util.Date currentDate = new java.util.Date();

			model.addAttribute("SelectDate", dateFormatName.format(currentDate));

			// Long RenewalCount =
			// RenewalReminderService.countTodayDueRenewalReminder(SQL_DATE_FORMAT.format(currentDate));
			Long MandatoryCount = vehicleMandatoryService
					.Count_Vehicle_Mandatory_Details_GET_VEHICLEID_NOT_EXISTS_Count(
							SQL_DATE_FORMAT.format(currentDate));
			// model.addAttribute("TodayDueRenewalRemindercount", RenewalCount +
			// MandatoryCount);

			Calendar cal = Calendar.getInstance();
			java.util.Date date = cal.getTime();
			String DayOne = SQL_DATE_FORMAT.format(date);
			String DayTwo = "", DayThree = "", DayFour = "", DayFive = "";
			for (int i = 0; i < 4; i++) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
				date = cal.getTime();
				switch (i) {
				case 0:
					DayTwo = SQL_DATE_FORMAT.format(date);
					break;
				case 1:

					DayThree = SQL_DATE_FORMAT.format(date);
					break;
				case 2:
					DayFour = SQL_DATE_FORMAT.format(date);
					break;
				case 3:
					DayFive = SQL_DATE_FORMAT.format(date);
					break;

				}
			}

			model.addAttribute("DayFive", DayFive);
			model.addAttribute("DayOne", DayOne);
			model.addAttribute("DayTwo", DayTwo);
			model.addAttribute("DayThree", DayThree);
			model.addAttribute("DayFour", DayFour);
			DayOne = DayOne +  DateTimeUtility.DAY_END_TIME;
			DayTwo = DayTwo +  DateTimeUtility.DAY_END_TIME;
			DayThree = DayThree +  DateTimeUtility.DAY_END_TIME;
			DayFour = DayFour +  DateTimeUtility.DAY_END_TIME;
			DayFive = DayFive +  DateTimeUtility.DAY_END_TIME;
			Object[] count = RenewalReminderService.count_TOTAL_RENEWAL_REMINDER_FIVEDAYS_ISSUES(DayOne, DayTwo,
					DayThree, DayFour, DayFive);
			model.addAttribute("DayFive_Count", (Long) count[0]);
			model.addAttribute("DayOne_Count", (Long) count[1] + MandatoryCount);
			model.addAttribute("DayTwo_Count", (Long) count[2]);
			model.addAttribute("DayThree_Count", (Long) count[3]);
			model.addAttribute("DayFour_Count", (Long) count[4]);
			model.addAttribute("OverDueCount", (Long) count[5]);
			model.addAttribute("DueSoonCount", (Long) count[6]);

		} catch (NullPointerException e) {
			LOGGER.error("ERR"+e);
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("ERR1"+e);
			LOGGER.error("Renewal_Reminder Page:", e);
			e.printStackTrace();
		}

		switch (Status) {
		case RenealReminderStatus.NOT_APPROVED:

			return "Renewal_Reminder";
		case RenealReminderStatus.OPEN:

			return "Renewal_Reminder_Open";
		case RenealReminderStatus.IN_PROGRESS:

			return "Renewal_Reminder_Inprogress";
		case RenealReminderStatus.APPROVED:

			return "Renewal_Reminder_Approved";

		default:

			return "Renewal_Reminder";
		}
	}
	
	@RequestMapping(value = "/RenewalOverDue/{Status}/{pageNumber}", method = RequestMethod.GET)
	public String RenewalReminderOverDueList(@PathVariable("Status") short Status,
			@PathVariable("pageNumber") Integer pageNumber, Model model) throws Exception {
		long RenewalReminderCount = 0;
		try {
			ValueObject valueObject =  new ValueObject();
			valueObject.put("pageNumber", pageNumber);
			java.util.Date	todaysStrDate		= DateTimeUtility.getCurrentDate();
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Page<RenewalReminder> page = RenewalReminderService.getDeployment_Page_RenewalOverDue(todaysStrDate, valueObject,
					userDetails);
			if (page != null) {
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				model.addAttribute("deploymentLog", page);
				model.addAttribute("beginIndex", begin);
				model.addAttribute("endIndex", end);
				model.addAttribute("currentIndex", current);
				RenewalReminderCount = page.getTotalElements();
			}

			model.addAttribute("RenewalReminderCount", RenewalReminderCount);

			List<RenewalReminderDto> pageList = RRBL.Only_Show_ListofRenewal(RenewalReminderService.getAllOverDueRenewalList(todaysStrDate , valueObject, userDetails));

			model.addAttribute("renewal", pageList);
			
			model.addAttribute("SelectStatus", Status);
			model.addAttribute("SelectPage", pageNumber);

			java.util.Date currentDate = new java.util.Date();

			model.addAttribute("SelectDate", dateFormatName.format(currentDate));

			Calendar cal = Calendar.getInstance();
			java.util.Date date = cal.getTime();
			String DayOne = SQL_DATE_FORMAT.format(date);
			String DayTwo = "", DayThree = "", DayFour = "", DayFive = "";
			for (int i = 0; i < 4; i++) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
				date = cal.getTime();
				switch (i) {
				case 0:
					DayTwo = SQL_DATE_FORMAT.format(date);
					break;
				case 1:

					DayThree = SQL_DATE_FORMAT.format(date);
					break;
				case 2:
					DayFour = SQL_DATE_FORMAT.format(date);
					break;
				case 3:
					DayFive = SQL_DATE_FORMAT.format(date);
					break;

				}
			}

			model.addAttribute("DayFive", DayFive);
			model.addAttribute("DayOne", DayOne);
			model.addAttribute("DayTwo", DayTwo);
			model.addAttribute("DayThree", DayThree);
			model.addAttribute("DayFour", DayFour);
			DayOne = DayOne +  DateTimeUtility.DAY_END_TIME;
			DayTwo = DayTwo +  DateTimeUtility.DAY_END_TIME;
			DayThree = DayThree +  DateTimeUtility.DAY_END_TIME;
			DayFour = DayFour +  DateTimeUtility.DAY_END_TIME;
			DayFive = DayFive +  DateTimeUtility.DAY_END_TIME;
			Object[] count = RenewalReminderService.count_TOTAL_RENEWAL_REMINDER_FIVEDAYS_ISSUES(DayOne, DayTwo,
					DayThree, DayFour, DayFive);
			model.addAttribute("DayFive_Count", (Long) count[0]);
			model.addAttribute("DayOne_Count", (Long) count[1]);
			model.addAttribute("DayTwo_Count", (Long) count[2]);
			model.addAttribute("DayThree_Count", (Long) count[3]);
			model.addAttribute("DayFour_Count", (Long) count[4]);
			model.addAttribute("OverDueCount", (Long) count[5]);
			model.addAttribute("DueSoonCount", (Long) count[6]);

		} catch (NullPointerException e) {
			LOGGER.error("ERR"+e);
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("ERR1"+e);
			LOGGER.error("Renewal_Reminder Page:", e);
			e.printStackTrace();
		}

			return "RenewalReminderOverDue";
	}
	
	@RequestMapping(value = "/RenewalDueSoon/{Status}/{pageNumber}", method = RequestMethod.GET)
	public String RenewalReminderDueSoonList(@PathVariable("Status") short Status,
			@PathVariable("pageNumber") Integer pageNumber, Model model) throws Exception {

		long RenewalReminderCount = 0;
		try {
			ValueObject valueObject =  new ValueObject();
			valueObject.put("pageNumber", pageNumber);
			java.util.Date	todaysStrDate		= DateTimeUtility.getCurrentDate();
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Page<RenewalReminder> page = RenewalReminderService.getDeployment_Page_RenewalDueSoon(todaysStrDate, valueObject,
					userDetails);
			if (page != null) {
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				model.addAttribute("deploymentLog", page);
				model.addAttribute("beginIndex", begin);
				model.addAttribute("endIndex", end);
				model.addAttribute("currentIndex", current);
				RenewalReminderCount = page.getTotalElements();
			}

			model.addAttribute("RenewalReminderCount", RenewalReminderCount);

			List<RenewalReminderDto> pageList = RRBL.Only_Show_ListofRenewal(RenewalReminderService.getAllDueSoonRenewalList(todaysStrDate , valueObject, userDetails));

			model.addAttribute("renewal", pageList);
			
			model.addAttribute("SelectStatus", Status);
			model.addAttribute("SelectPage", pageNumber);

			java.util.Date currentDate = new java.util.Date();

			model.addAttribute("SelectDate", dateFormatName.format(currentDate));

			Calendar cal = Calendar.getInstance();
			java.util.Date date = cal.getTime();
			String DayOne = SQL_DATE_FORMAT.format(date);
			String DayTwo = "", DayThree = "", DayFour = "", DayFive = "";
			for (int i = 0; i < 4; i++) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
				date = cal.getTime();
				switch (i) {
				case 0:
					DayTwo = SQL_DATE_FORMAT.format(date);
					break;
				case 1:

					DayThree = SQL_DATE_FORMAT.format(date);
					break;
				case 2:
					DayFour = SQL_DATE_FORMAT.format(date);
					break;
				case 3:
					DayFive = SQL_DATE_FORMAT.format(date);
					break;

				}
			}

			model.addAttribute("DayFive", DayFive);
			model.addAttribute("DayOne", DayOne);
			model.addAttribute("DayTwo", DayTwo);
			model.addAttribute("DayThree", DayThree);
			model.addAttribute("DayFour", DayFour);
			DayOne = DayOne +  DateTimeUtility.DAY_END_TIME;
			DayTwo = DayTwo +  DateTimeUtility.DAY_END_TIME;
			DayThree = DayThree +  DateTimeUtility.DAY_END_TIME;
			DayFour = DayFour +  DateTimeUtility.DAY_END_TIME;
			DayFive = DayFive +  DateTimeUtility.DAY_END_TIME;
			Object[] count = RenewalReminderService.count_TOTAL_RENEWAL_REMINDER_FIVEDAYS_ISSUES(DayOne, DayTwo,
					DayThree, DayFour, DayFive);
			model.addAttribute("DayFive_Count", (Long) count[0]);
			model.addAttribute("DayOne_Count", (Long) count[1]);
			model.addAttribute("DayTwo_Count", (Long) count[2]);
			model.addAttribute("DayThree_Count", (Long) count[3]);
			model.addAttribute("DayFour_Count", (Long) count[4]);
			model.addAttribute("OverDueCount", (Long) count[5]);
			model.addAttribute("DueSoonCount", (Long) count[6]);

		} catch (NullPointerException e) {
			LOGGER.error("ERR"+e);
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("ERR1"+e);
			LOGGER.error("Renewal_Reminder Page:", e);
			e.printStackTrace();
		}

			return "RenewalRemindeDueSoon";
	}

	/* show main page of Renewal Reminder */
	@RequestMapping(value = "/DATERR", method = RequestMethod.GET)
	public ModelAndView TODayRenewalReminder(@RequestParam("DATE")  String Searchdate,
			final HttpServletResponse result) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		// show the driver list in all
		try {
			Searchdate	= Searchdate + DateTimeUtility.DAY_END_TIME;
			List<RenewalReminderDto> Renewal = RenewalReminderService.TodayRenewalReminderList(Searchdate);

			List<VehicleMandatoryDto> Mandatory = vehicleMandatoryService
					.List_Vehicle_Mandatory_Details_GET_RENEWALREMINDERVALUES(Searchdate, Searchdate);
			model.put("renewal", RRBL.Today_Show_ListofRenewal_Reminder_VehicleMandary(Renewal, Mandatory));

			DecimalFormat df = new DecimalFormat("##,##,##0");

			Double TotalPoint = RRBL.GET_Total_RenewalReminder_Amount(Renewal);
			df.setMaximumFractionDigits(0);
			String TotalIncomeShow = df.format(TotalPoint);
			model.put("RRAmount", TotalIncomeShow);
			if (Renewal != null && !Renewal.isEmpty()) {
				model.put("TodayDueRenewalRemindercount", Renewal.size());
			} else {
				model.put("TodayDueRenewalRemindercount", 0);
			}

			model.put("SelectStatus", RenealReminderStatus.NOT_APPROVED);
			model.put("SelectPage", 1);

			model.put("SelectDate", Searchdate);

			Calendar cal = Calendar.getInstance();
			java.util.Date date = cal.getTime();
			String DayOne = SQL_DATE_FORMAT.format(date);
			String DayTwo = "", DayThree = "", DayFour = "", DayFive = "";
			for (int i = 0; i < 4; i++) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
				date = cal.getTime();
				switch (i) {
				case 0:
					DayTwo = SQL_DATE_FORMAT.format(date);
					break;
				case 1:

					DayThree = SQL_DATE_FORMAT.format(date);
					break;
				case 2:
					DayFour = SQL_DATE_FORMAT.format(date);
					break;
				case 3:
					DayFive = SQL_DATE_FORMAT.format(date);
					break;

				}
			}

			model.put("DayFive", DayFive);
			model.put("DayOne", DayOne);
			model.put("DayTwo", DayTwo);
			model.put("DayThree", DayThree);
			model.put("DayFour", DayFour);
			
			DayOne = DayOne +  DateTimeUtility.DAY_END_TIME;
			DayTwo = DayTwo +  DateTimeUtility.DAY_END_TIME;
			DayThree = DayThree +  DateTimeUtility.DAY_END_TIME;
			DayFour = DayFour +  DateTimeUtility.DAY_END_TIME;
			DayFive = DayFive +  DateTimeUtility.DAY_END_TIME;
			Object[] count = RenewalReminderService.count_TOTAL_RENEWAL_REMINDER_FIVEDAYS_ISSUES(DayOne, DayTwo,
					DayThree, DayFour, DayFive);
			
			model.put("DayFive_Count", (Long) count[0]);
			model.put("DayOne_Count", (Long) count[1]);
			model.put("DayTwo_Count", (Long) count[2]);
			model.put("DayThree_Count", (Long) count[3]);
			model.put("DayFour_Count", (Long) count[4]);
			model.put("OverDueCount", (Long) count[5]);
			model.put("DueSoonCount", (Long) count[6]);

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			LOGGER.error("Renewal Reminder:", e);
		}
		return new ModelAndView("Renewal_Reminder", model);
	}

	/* show main page of Renewal Reminder */
	@RequestMapping(value = "/DRR", method = RequestMethod.POST)
	public ModelAndView DateRenewalReminder(@RequestParam("RRDate") String SearchDate,
			final HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		// show the driver list in all
		try {
			SearchDate	= SearchDate + DateTimeUtility.DAY_END_TIME;
			List<RenewalReminderDto> Renewal = RenewalReminderService.TodayRenewalReminderList(SearchDate);
			model.put("renewal", RRBL.Only_Show_ListofRenewal(Renewal));
			// show the vehicle Group service of the driver
			// model.put("RenewalReminderCount",
			// RenewalReminderService.countRenewalReminder());
			model.put("RRAmount", RRBL.GET_Total_RenewalReminder_Amount(Renewal));
			if (Renewal != null && !Renewal.isEmpty()) {
				model.put("TodayDueRenewalRemindercount", Renewal.size());
			}
			model.put("SelectStatus", RenealReminderStatus.NOT_APPROVED);
			model.put("SelectPage", 1);

			model.put("SelectDate", SearchDate);

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Renewal Reminder:", e);
			e.printStackTrace();
		}
		return new ModelAndView("Renewal_Reminder", model);
	}

	// show the add Renewal Reminder page and field
	@RequestMapping("/addRenewalReminder")
	public ModelAndView addRenewalReminder(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails			 userDetails 	= null;
		HashMap<String, Object>		 configuration	= null;
		try {
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			
			model.put("userName", userDetails.getFirstName());
			model.put("userId", userDetails.getId());
			model.put("PaymentType", PaymentTypeConstant.getPaymentTypeList());
			model.put(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE, (boolean)configuration.get(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE));
			model.put("configuration", configuration);
			model.put("SelectPage", 1);
		
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			 userDetails 	= null;
			 configuration	= null;
			 
		}

		return new ModelAndView("addRenewal_Reminder", model);
	}

	@RequestMapping(value = "/VehicleRenewalReminder", method = RequestMethod.GET)
	public ModelAndView VehicleRenewalReminder(@ModelAttribute("command") Vehicle renewal_vehiclename,
			RenewalReminderDto renewalReminderDto, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VehicleDto vehicle = VBL.prepare_Vehicle_Header_Show(
					vehicleService.Get_Vehicle_Header_Details(renewal_vehiclename.getVid()));
			if (vehicle != null) {

				model.put("vehicle", vehicle);
				java.util.Date currentDate = new java.util.Date();
				DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
				// show the RenewalReminder in Current value only dispaly list in
				// all
				model.put("renewal", RRBL.Only_Show_ListofRenewalDto(RenewalReminderService.listVehicleRenewalReminder(
						renewal_vehiclename.getVid(), ft.format(currentDate)+DateTimeUtility.DAY_END_TIME, userDetails.getCompany_id())));

				Object[] count = vehicleService
						.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(renewal_vehiclename.getVid());
				model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(renewal_vehiclename.getVid(), userDetails.getCompany_id()));
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
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/NotFound.in");

		} catch (Exception e) {
			System.err.println("Exception : " + e);
			e.printStackTrace();
			LOGGER.error("Renewal Reminder:", e);

		}

		return new ModelAndView("Show_Vehicle_RenewalReminder", model);
	}

	@RequestMapping(value = "/VehicleRenewalReminderHistory", method = RequestMethod.GET)
	public ModelAndView VehicleRenewalReminderHistory(@ModelAttribute("command") Vehicle renewal_vehiclename,
			RenewalReminderDto renewalReminderDto, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			VehicleDto vehicle = VBL.prepare_Vehicle_Header_Show(
					vehicleService.Get_Vehicle_Header_Details(renewal_vehiclename.getVid()));
			model.put("vehicle", vehicle);
			// show the RenewalReminder in Current value only dispaly list in
			// all
			model.put("renewal", RRBL.prepareListofRenewal(RenewalReminderService
					.listVehicleRenewalReminder(renewal_vehiclename.getVid(), userDetails.getCompany_id())));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Renewal Reminder:", e);

		}

		return new ModelAndView("Show_Vehicle_RenewalReminder", model);
	}

	// This vehicle Renewal Reminder show the one current vehicle renewal add
	// page Show Action to go to RenewalReminder form
	// action="saveRenewalReminder.in" page only
	@RequestMapping(value = "/VehicleRenewalReminderAdd", method = RequestMethod.GET)
	public ModelAndView VehicleRenewalReminderAdd(@ModelAttribute("command") Vehicle renewal_vehiclename,
			RenewalReminderDto renewalReminderDto, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails 				userDetails = null;
		HashMap<String, Object>			configuration	= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
		
		
			VehicleDto vehicle = VBL.prepare_Vehicle_Header_Show(
					vehicleService.Get_Vehicle_Header_Details(renewal_vehiclename.getVid()));
			model.put("vehicle", vehicle);
			model.put("configuration", configuration);
			model.put("userName", userDetails.getFirstName());
			model.put("userId", userDetails.getId());
			model.put(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE, (boolean)configuration.get(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE));
			model.put("PaymentType", PaymentTypeConstant.getPaymentTypeList());
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Renewal Reminder:", e);

		}finally {
			userDetails 	= null;
			configuration	= null;
		}

		return new ModelAndView("Add_Vehicle_RenewalReminder", model);
	}

	/* Save the Renewal Reminder in renewal page code */
	@RequestMapping(value = "/saveRenewalReminder", method = RequestMethod.POST)
	public ModelAndView saveRenewalReminder(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			@RequestParam("input-file-preview") MultipartFile file, RedirectAttributes redir) {

		RenewalReminderSequenceCounter 		counter 			= null;
		Map<String, Object> 				model 				= new HashMap<String, Object>();
		HashMap<String, Object>				configuration		= null;
		CustomUserDetails 					userDetails 		= null;
		VehicleDto 							CheckVehicleStatus	= null;
		RenewalReminder 					renewalReminder		= null;
		List<RenewalReminderDto> 			renewal				= null;
		String 								TIDMandatory 		= "";
		boolean 							VEHICLESTATUS 		= false;
		long 								renewal_R_Number 	= 0;
		long 								tripSheetNumber 	= 0;   
		String 								Link 				= "";  
		try {userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
		// Check Vehicle Status Current IN ACTIVE OR NOT
		 CheckVehicleStatus = vehicleService
				.Get_Vehicle_Current_Status_TripSheetID(renewalReminderDto.getVid());

		switch (CheckVehicleStatus.getvStatusId()) {
		case VehicleStatusConstant.VEHICLE_STATUS_ACTIVE:

			VEHICLESTATUS = true;
			break;
		case VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP:

			VEHICLESTATUS = true;
			break;
		case VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE:

			VEHICLESTATUS = true;
			break;
		case VehicleStatusConstant.VEHICLE_STATUS_SURRENDER:

			if (renewalReminderDto.getRenewal_type().equalsIgnoreCase("INSURANCE")) {
				VEHICLESTATUS = true;
			} else {

				VEHICLESTATUS = false;
			}
			break;

		default:
			VEHICLESTATUS = false;
			break;
		}

		Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
		
		if(permission.contains(new SimpleGrantedAuthority("ALLOW_ALL_STATUS_RENEWAL"))) {
			VEHICLESTATUS	= true;
		}
		
		if (VEHICLESTATUS) {
			counter = renewalReminderSequenceService.findNextRenewal_R_Number(userDetails.getCompany_id());
			if (counter == null) {
				model.put("sequenceNotFound", true);
				return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
			}
			renewalReminder = prepareRenewalRemider(renewalReminderDto);
			renewal_R_Number = counter.getNextVal();
			renewalReminder.setRenewal_R_Number(renewal_R_Number);
			if((boolean) configuration.get("alwaysApprovedRenewal")) {
				renewalReminder.setRenewal_staus_id(RenealReminderStatus.APPROVED);
				renewalReminder.setRenewal_approvedbyId(userDetails.getId());
				renewalReminder.setRenewal_approveddate(new java.util.Date());
			}
			if (!file.isEmpty() || (boolean)configuration.get(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE)) {

				renewalReminder.setRenewal_document(true);

				// Here Checking the Renewal Reminder Details Date From and
				// To
				// Value Details
				String Query = " ( RR.vid =" + renewalReminder.getVid() + " " + " AND RR.renewalTypeId ="
						+ renewalReminder.getRenewalTypeId() + " AND RR.renewal_Subid ="
						+ renewalReminder.getRenewal_Subid() + "  AND '" + renewalReminder.getRenewal_from()
						+ "' between RR.renewal_from AND RR.renewal_to  OR RR.vid=" + renewalReminder.getVid() + " "
						+ " AND RR.renewalTypeId =" + renewalReminder.getRenewalTypeId() + " AND RR.renewal_Subid ="
						+ renewalReminder.getRenewal_Subid() + "  AND '" + renewalReminder.getRenewal_to()
						+ "' between RR.renewal_from AND RR.renewal_to ) AND RR.companyId = "
						+ userDetails.getCompany_id() + " AND RR.markForDelete = 0 ";
				// show Vehicle Name Renewal Type and Renewal SubType List
				 renewal = RRBL
						.prepareListofRenewalDto(RenewalReminderService.Validate_RenewalReminder(Query));

				if (renewal != null && !renewal.isEmpty()) {
					try {
						// Checking the Value Of Mandatory Compliance
						for (RenewalReminderDto add : renewal) {
							TIDMandatory += "<span>" + add.getVehicle_registration()
							+ " Compliance <a href=\"../../showRenewalReminder.in?renewal_id="
							+ add.getRenewal_id() + " \" target=\"_blank\" >" + add.getRenewal_type() + " "
							+ add.getRenewal_subtype()
							+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
							+ add.getRenewal_from() + " To " + add.getRenewal_to() + " </span>, <br>";
						}
						redir.addFlashAttribute("already", TIDMandatory);

					} catch (Exception e) {
						LOGGER.error("Renewal Reminder:", e);
					}

					model.put("renewalRemindeAlready", true);
					return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);

				} else {
					List<RenewalReminderDto> renewalReceipt = null;
					// Here Checking the Renewal Reminder Details Receipt
					// Details
					String QueryReceipt = "RR.renewal_receipt='" + renewalReminder.getRenewal_receipt()
					+ "' AND RR.renewalTypeId =" + renewalReminder.getRenewalTypeId()
					+ " AND RR.renewal_Subid =" + renewalReminder.getRenewal_Subid()
					+ " AND RR.companyId = " + userDetails.getCompany_id() + " AND RR.markForDelete = 0";
					// Renewal Reminder Receipt
					if((boolean)configuration.get("receiptnumbershow") && (boolean)configuration.get("validateDuplicateRefnumber")) {
						
						renewalReceipt = RRBL
								.prepareListofRenewalDto(RenewalReminderService.Validate_RenewalReminder(QueryReceipt));
					}

					if (renewalReceipt != null && !renewalReceipt.isEmpty()) {

						try {
							// Checking the Value Of Mandatory Compliance
							for (RenewalReminderDto add : renewalReceipt) {
								TIDMandatory += "<span>" + add.getVehicle_registration()
								+ " Compliance <a href=\"../../showRenewalReminder.in?renewal_id="
								+ add.getRenewal_id() + "\" target=\"_blank\">" + add.getRenewal_type()
								+ " " + add.getRenewal_subtype()
								+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
								+ add.getRenewal_from() + " To " + add.getRenewal_to()
								+ " Receipt | Challan  Number " + add.getRenewal_receipt()
								+ " </span>, <br>";
							}
							redir.addFlashAttribute("ReceiptAlready", TIDMandatory);

						} catch (Exception e) {
							LOGGER.error("Renewal Reminder:", e);
						}

						model.put("renewalReceiptAlready", true);
						return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);

					} else {
						try {
							/* save Renewal Reminder */
							
							RenewalReminderService.updateNewRRCreated(renewalReminder.getVid(), renewalReminder.getRenewalTypeId(), renewalReminder.getRenewal_Subid());
							
							RenewalReminderService.addRenewalReminder(renewalReminder);
							ValueObject		ownerShipObject		= null;	
							if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED ){
								ownerShipObject	= new ValueObject();
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, renewalReminder.getRenewal_id());
								ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, renewalReminder.getVid());
								ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, renewalReminder.getRenewal_Amount());
								ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
								ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_RENEWAL);
								ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, userDetails.getCompany_id());
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, SQL_DATE_FORMAT.parse(SQL_DATE_FORMAT.format(renewalReminder.getRenewal_dateofpayment())));
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Renewal Entry");
								ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Renewal Number : "+renewalReminder.getRenewal_R_Number());
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
								ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, userDetails.getId());
								ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, -renewalReminder.getRenewal_Amount());
								
								VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
								vehicleAgentTxnCheckerService.save(agentTxnChecker);
								
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
								
							}

							org.fleetopgroup.persistence.document.RenewalReminderDocument rewalDoc = new org.fleetopgroup.persistence.document.RenewalReminderDocument();

							if(!file.isEmpty()) {
								rewalDoc.setRenewal_id(renewalReminder.getRenewal_id());
								rewalDoc.setRenewal_R_Number(renewalReminder.getRenewal_R_Number());
								try {
									byte[] bytes = file.getBytes();
									rewalDoc.setRenewal_filename(file.getOriginalFilename());
									rewalDoc.setRenewal_content(bytes);
									rewalDoc.setRenewal_contentType(file.getContentType());

									// when Add Renewal Reminder to file Renewal
									// Reminder History id is null
									rewalDoc.setRenewalHis_id((long) 0);

									/** Set Status in Issues */
									rewalDoc.setMarkForDelete(false);

									/** Set Created by email Id */
									rewalDoc.setCreatedById(userDetails.getId());
									rewalDoc.setLastModifiedById(userDetails.getId());

									java.util.Date currentDateUpdate = new java.util.Date();
									Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

									/** Set Created Current Date */
									rewalDoc.setCreated(toDate);
									rewalDoc.setLastupdated(toDate);
									rewalDoc.setCompanyId(userDetails.getCompany_id());
								} catch (IOException e) {
									e.printStackTrace();
								}

								// Note: Save RenewalReminder To Document
								//RenewalReminderService.add_RenewalReminder_Document(rewalDoc);
								documentService.save(rewalDoc);

								// Note: This Update is Document ID Update
								// Renewal
								// Reminder Details
								RenewalReminderService.Update_RenewalReminderDocument_ID_to_RenewalReminder(
										rewalDoc.get_id(), true, renewalReminder.getRenewal_id(),
										userDetails.getCompany_id());
							}

							// renewalReminderSequenceService.updateNextRenewal_R_Number(renewal_R_Number +
							// 1, userDetails.getCompany_id());

							model.put("saveRenewalReminder", true);
							
							if(ownerShipObject != null) {
								vehicleAgentIncomeExpenseDetailsService.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
							}
							if(!(boolean) configuration.get("alwaysApprovedRenewal")) {
								return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
							}else {
								return new ModelAndView("redirect:/RenewalReminder/2/1.in", model);
							}
							

						} catch (Exception e) {
							e.printStackTrace();
							return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
						}
					}

				}

			} else {
				// messages
				return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
			}

		} // checking Vehicle Status
		else {
			 tripSheetNumber 	= 0;
			 Link 				= "";
			if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
				tripSheetNumber = driverService.getCurrentTripSheetNumber(CheckVehicleStatus.getTripSheetID(),
						userDetails.getCompany_id());
				Link += "  <a href=\"getTripsheetDetails.in?tripSheetID=" + CheckVehicleStatus.getTripSheetID()
				+ "\" target=\"_blank\">TS-" + tripSheetNumber
				+ "  <i class=\"fa fa-external-link\"></i></a>";
			} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
				WorkOrders workOrders = workOrdersService.getWorkOrders(CheckVehicleStatus.getTripSheetID());
				Link += " <a href=\"showWorkOrder?woId=" + CheckVehicleStatus.getTripSheetID()
				+ "\" target=\"_blank\">WO-" + workOrders.getWorkorders_Number()
				+ "  <i class=\"fa fa-external-link\"></i></a> ";
			}
			TIDMandatory += " <span> This " + CheckVehicleStatus.getVehicle_registration() + " Vehicle in "
					+ CheckVehicleStatus.getVehicle_Status() + " Status you can't create Renewal Reminder " + " "
					+ Link + "" + " </span> ,";
			redir.addFlashAttribute("VMandatory", TIDMandatory);
			model.put("closeStatus", true);

			return new ModelAndView("redirect:/addRenewalReminder.in", model);
		}
		} catch (Exception e) {
			LOGGER.error("ERR"+e);
			System.err.println("Exception "+e);
			e.printStackTrace();
			return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
		}finally {
			configuration	= null;
			counter 		= null;
			userDetails		= null;
		}
	}

	/* Save the Renewal Reminder in vehicle Renewal Show page to save code */
	@RequestMapping(value = "/saveVehicleRenewalReminder", method = RequestMethod.POST)
	public ModelAndView saveVehicleRenewalReminder(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			@RequestParam("input-file-preview") MultipartFile file, RedirectAttributes redir) {

		Map<String, Object> model = new HashMap<String, Object>();

		boolean VEHICLESTATUS = false;
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		long renewal_R_Number = 0;
		String TIDMandatory = "";
		RenewalReminderSequenceCounter counter = null;
		HashMap<String, Object>			configuration	= null;
		try {
			
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			// Check Vehicle Status Current IN ACTIVE OR NOT
			VehicleDto CheckVehicleStatus = vehicleService
					.Get_Vehicle_Current_Status_TripSheetID(renewalReminderDto.getVid());

			switch (CheckVehicleStatus.getvStatusId()) {
			case VehicleStatusConstant.VEHICLE_STATUS_ACTIVE:

				VEHICLESTATUS = true;
				break;
			case VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP:

				VEHICLESTATUS = true;
				break;
			case VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE:

				VEHICLESTATUS = true;
				break;
			case VehicleStatusConstant.VEHICLE_STATUS_SURRENDER:

				if (renewalReminderDto.getRenewal_type().equalsIgnoreCase("INSURANCE")) {
					VEHICLESTATUS = true;
				} else {

					VEHICLESTATUS = false;
				}
				break;

			default:
				VEHICLESTATUS = false;
				break;
			}
			
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			
			if(permission.contains(new SimpleGrantedAuthority("ALLOW_ALL_STATUS_RENEWAL"))) {
				VEHICLESTATUS	= true;
			}

			if (VEHICLESTATUS) {
				// get the information in jsp page f information in one driver
				RenewalReminder renewalReminder = prepareRenewalRemider(renewalReminderDto);
				if((boolean) configuration.get("alwaysApprovedRenewal")) {
					renewalReminder.setRenewal_staus_id(RenealReminderStatus.APPROVED);
					renewalReminder.setRenewal_approvedbyId(userDetails.getId());
					renewalReminder.setRenewal_approveddate(new java.util.Date());
				
				}
				if (!file.isEmpty() || (boolean)configuration.get(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE)) {

					renewalReminder.setRenewal_document(true);

					String Query = " ( RR.vid=" + renewalReminder.getVid() + " " + " AND RR.renewalTypeId ="
							+ renewalReminder.getRenewalTypeId() + " AND RR.renewal_Subid ="
							+ renewalReminder.getRenewal_Subid() + "  AND '" + renewalReminder.getRenewal_from()
							+ "' between RR.renewal_from AND RR.renewal_to  OR  RR.vid = " + renewalReminder.getVid()
							+ " " + " AND RR.renewalTypeId =" + renewalReminder.getRenewalTypeId()
							+ " AND RR.renewal_Subid =" + renewalReminder.getRenewal_Subid() + "  AND '"
							+ renewalReminder.getRenewal_to()
							+ "' between RR.renewal_from AND RR.renewal_to ) AND RR.companyId = "
							+ userDetails.getCompany_id() + " AND RR.markForDelete = 0 ";
					// show Vehicle Name Renewal Type and Renewal SubType List
					List<RenewalReminderDto> renewal = RRBL
							.prepareListofRenewalDto(RenewalReminderService.Validate_RenewalReminder(Query));

					if (renewal != null && !renewal.isEmpty()) {

						try {

							VehicleDto vehicle = VBL.prepare_Vehicle_Header_Show(
									vehicleService.Get_Vehicle_Header_Details(renewalReminder.getVid()));
							model.put("vehicle", vehicle);
							// show the all in one vehicle renewal reminder list
							/*
							 * model.put("renewal", RRBL.prepareListofRenewalDto(RenewalReminderService
							 * .listVehicleRenewalReminder(vehicle.getVehicle_registration())));
							 */
							model.put("renewal",
									RRBL.prepareListofRenewal(RenewalReminderService.listVehicleRenewalReminder(
											renewalReminder.getVid(), userDetails.getCompany_id())));

							model.put("already", renewal);
							model.put("renewalRemindeAlready", true);

							return new ModelAndView("Show_Vehicle_RenewalReminder", model);
						} catch (NullPointerException e) {
							return new ModelAndView("redirect:/NotFound.in");
						} catch (Exception e) {
							LOGGER.error("Renewal Reminder:", e);
							return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
						}

					} else {

						String QueryReceipt = "RR.renewal_receipt='" + renewalReminder.getRenewal_receipt()
								+ "' AND RR.renewalTypeId =" + renewalReminder.getRenewalTypeId()
								+ " AND RR.renewal_Subid =" + renewalReminder.getRenewal_Subid()
								+ " AND RR.companyId = " + userDetails.getCompany_id() + " AND RR.markForDelete = 0 ";
						// Renewal Reminder Receipt
						List<RenewalReminderDto> renewalReceipt = null;
						if((boolean)configuration.get("receiptnumbershow") && (boolean)configuration.get("validateDuplicateRefnumber")) {
							renewalReceipt = RRBL
									.prepareListofRenewalDto(RenewalReminderService.Validate_RenewalReminder(QueryReceipt));
						}

						if (renewalReceipt != null && !renewalReceipt.isEmpty()) {

							try {
								VehicleDto vehicle = VBL.prepare_Vehicle_Header_Show(
										vehicleService.Get_Vehicle_Header_Details(renewalReminder.getVid()));
								model.put("vehicle", vehicle);
								// show the all in one vehicle renewal reminder
								// list
								model.put("renewal",
										RRBL.prepareListofRenewal(RenewalReminderService.listVehicleRenewalReminder(
												renewalReminder.getVid(), userDetails.getCompany_id())));
								model.put("ReceiptAlready", renewalReceipt);
								model.put("renewalReceiptAlready", true);

								return new ModelAndView("Show_Vehicle_RenewalReminder", model);
							} catch (NullPointerException e) {
								return new ModelAndView("redirect:/NotFound.in");
							} catch (Exception e) {
								LOGGER.error("Renewal Reminder:", e);
								return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
							}

						} else {
							counter = renewalReminderSequenceService
									.findNextRenewal_R_Number(userDetails.getCompany_id());
							if (counter == null) {
								model.put("sequenceNotFound", true);
								return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
							}
							renewal_R_Number = counter.getNextVal();
							/* save Renewal Reminder */
							renewalReminder.setRenewal_R_Number(renewal_R_Number);
							RenewalReminderService.updateNewRRCreated(renewalReminder.getVid(), renewalReminder.getRenewalTypeId(), renewalReminder.getRenewal_Subid());
							RenewalReminderService.addRenewalReminder(renewalReminder);
							// renewalReminderSequenceService.updateNextRenewal_R_Number(renewal_R_Number +
							
							ValueObject		ownerShipObject		= null;	
							if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED ){
								ownerShipObject	= new ValueObject();
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, renewalReminder.getRenewal_id());
								ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, renewalReminder.getVid());
								ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, renewalReminder.getRenewal_Amount());
								ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
								ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_RENEWAL);
								ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, userDetails.getCompany_id());
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, SQL_DATE_FORMAT.parse(SQL_DATE_FORMAT.format(renewalReminder.getRenewal_dateofpayment())));
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Renewal Entry");
								ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Renewal Number : "+renewalReminder.getRenewal_R_Number());
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
								ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, userDetails.getId());
								ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, -renewalReminder.getRenewal_Amount());
								
								VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
								vehicleAgentTxnCheckerService.save(agentTxnChecker);
								
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
								
							}
							
							// 1, userDetails.getCompany_id());
							org.fleetopgroup.persistence.document.RenewalReminderDocument rewalDoc = new org.fleetopgroup.persistence.document.RenewalReminderDocument();
							if (!file.isEmpty()) {
								rewalDoc.setRenewal_id(renewalReminder.getRenewal_id());
								rewalDoc.setRenewal_R_Number(renewalReminder.getRenewal_R_Number());
								try {

									byte[] bytes = file.getBytes();
									rewalDoc.setRenewal_filename(file.getOriginalFilename());
									rewalDoc.setRenewal_content(bytes);
									rewalDoc.setRenewal_contentType(file.getContentType());

									// when Add Renewal Reminder to file Renewal
									// Reminder History id is null
									rewalDoc.setRenewalHis_id((long) 0);

									/** Set Status in Issues */
									rewalDoc.setMarkForDelete(false);

									/** Set Created by email Id */
									rewalDoc.setCreatedById(userDetails.getId());
									rewalDoc.setLastModifiedById(userDetails.getId());

									java.util.Date currentDateUpdate = new java.util.Date();
									Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

									/** Set Created Current Date */
									rewalDoc.setCreated(toDate);
									rewalDoc.setLastupdated(toDate);
									rewalDoc.setCompanyId(userDetails.getCompany_id());
								} catch (IOException e) {
									e.printStackTrace();
								}

								// Note: Save RenewalReminder To Document
								//RenewalReminderService.add_RenewalReminder_Document(rewalDoc);
								documentService.save(rewalDoc);

								// Note: This Update is Document ID Update Renewal
								// Reminder Details
								RenewalReminderService.Update_RenewalReminderDocument_ID_to_RenewalReminder(
										rewalDoc.get_id(), true, renewalReminder.getRenewal_id(),
										userDetails.getCompany_id());

							}
							
							if(ownerShipObject != null) {
								vehicleAgentIncomeExpenseDetailsService.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
							}
							// save massage
							model.put("saveRenewalReminder", true);

							return new ModelAndView(
									"redirect:/VehicleRenewalReminder.in?vid=" + renewalReminder.getVid() + "", model);

						}

					}
				} else {
					// messages
					return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
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
					Link += " <a href=\"showWorkOrder?woId=" + CheckVehicleStatus.getTripSheetID()
							+ "\" target=\"_blank\">WO-" + workOrdersService
									.getWorkOrders(CheckVehicleStatus.getTripSheetID()).getWorkorders_Number()
							+ "  <i class=\"fa fa-external-link\"></i></a> ";
				}
				TIDMandatory += " <span> This " + CheckVehicleStatus.getVehicle_registration() + " Vehicle in "
						+ CheckVehicleStatus.getVehicle_Status() + " Status you can't create Renewal Reminder " + " "
						+ Link + "" + " </span> ,";
				redir.addFlashAttribute("VMandatory", TIDMandatory);
				model.put("closeStatus", true);

				return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true", model);
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
		}
	}

	/* Should be Download Driver Reminder document */
	@RequestMapping("/download/RenewalReminder/{rendoc_id}")
	public String downloadReminder(@PathVariable("rendoc_id") Long renewal_id, HttpServletResponse response) {

		try {
			if (renewal_id == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				// Lookup file by fileId in database.
				org.fleetopgroup.persistence.document.RenewalReminderDocument file = documentService.get_RenewalReminder_Document(renewal_id, userDetails.getCompany_id());

				if (file != null) {
					if (file.getRenewal_content() != null) {
						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getRenewal_filename() + "\"");
						if(file.getRenewal_contentType().equals(FileUtility.FILE_EXTENSION_PDF)){
							file.setRenewal_contentType(FileUtility.getContentType(file.getRenewal_contentType()));
						}
						if(file.getRenewal_contentType().equals(FileUtility.FILE_EXTENSION_JPG)){
							file.setRenewal_contentType(FileUtility.getContentType(file.getRenewal_contentType()));
						}
						if(file.getRenewal_contentType().equals(FileUtility.FILE_EXTENSION_EXCEL)){
							file.setRenewal_contentType(FileUtility.getContentType(file.getRenewal_contentType()));
						}
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getRenewal_contentType());
						response.getOutputStream().write(file.getRenewal_content());
						out.flush();
						out.close();
						
						
/*
						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getRenewal_filename() + "\"");*/
//						OutputStream out = response.getOutputStream();
//						System.err.println("getRenewal_contentType *** = "+file.getRenewal_contentType());
//						if(file.getRenewal_contentType().equals("pdf")){
//							file.setRenewal_contentType(FileUtility.getContentType(file.getRenewal_contentType()));
//						}
//						response.setContentType(file.getRenewal_contentType());
//						response.getOutputStream().write(file.getRenewal_content());
//						out.flush();
//						out.close();
/*
						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getRenewal_filename() + "\"");*/
//						OutputStream out = response.getOutputStream();
//						response.setContentType(file.getRenewal_contentType());
//						response.getOutputStream().write(file.getRenewal_content());
//						out.flush();
//						out.close();

						/*
						 * response.setContentType(file.getRenewal_contentType() );
						 * response.setContentLength(file.getRenewal_content(). length);
						 * response.setHeader("Content-Disposition", "attachment; filename=\"" +
						 * file.getRenewal_filename() + "\"");
						 * 
						 * FileCopyUtils.copy(file.getRenewal_content(), response.getOutputStream());
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

	/* Should be Download Driver Reminder document */
	@RequestMapping("/download/RenewalReminderHis/{rendoc_id}")
	public String downloadReminderHis(@PathVariable("rendoc_id") Long renewalhis_id, HttpServletResponse response) {
		try {
			if (renewalhis_id == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				// Lookup file by fileId in database.
				org.fleetopgroup.persistence.document.RenewalReminderDocument file = documentService.get_RenewalReminder_Document(renewalhis_id, userDetails.getCompany_id());
				if (file != null) {
					if (file.getRenewal_content() != null) {

						response.setContentType(file.getRenewal_contentType());
						response.setContentLength(file.getRenewal_content().length);
						response.setHeader("Content-Disposition",
								"attachment; filename=\"" + file.getRenewal_filename() + "\"");

						FileCopyUtils.copy(file.getRenewal_content(), response.getOutputStream());

					}
				}
			}
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
		}
		return null;
	}

	/* Edit Driver Reminder */
	@RequestMapping("/editRenewalReminder")
	public ModelAndView editRenewalReminder(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			// show edit page the driverRemider List
			model.put("renewalReminder", RRBL.GetRenewalReminder(
					RenewalReminderService.getRenewalReminderById(renewalReminderDto.getRenewal_id(), userDetails)));
			model.put("PaymentType", PaymentTypeConstant.getPaymentTypeList());
			model.put("configuration", configuration);
			model.put(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE, configuration.get(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE));
		} catch (NullPointerException e) {
			LOGGER.error("Exception : ", e);
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("editRenewal_Reminder", model);
	}
	
	@RequestMapping("/reUploadRenewalReminder")
	public ModelAndView ReUploadRenewalReminder(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> 		 model 			= new HashMap<String, Object>();
		CustomUserDetails			 userDetails 	= null;
		HashMap<String, Object>		 configuration	= null;
		try {
			
			 userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			// show edit page the driverRemider List
			model.put("renewalReminder", RRBL.GetRenewalReminder(
					RenewalReminderService.getRenewalReminderById(renewalReminderDto.getRenewal_id(), userDetails)));
			model.put("PaymentType", PaymentTypeConstant.getPaymentTypeList());
			model.put("configuration", configuration);
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ReUploadRenewal_Reminder", model);
	}

	@RequestMapping("/reviseRenewalReminder")
	public ModelAndView reviseRenewalReminder(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) throws Exception {
		Map<String, Object> 			model 				= new HashMap<String, Object>();
		CustomUserDetails 				userDetails			= null;
		HashMap<String, Object>			configuration		=null;
		
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			
			// show edit page the driverRemider List
			model.put("renewalReminder", RRBL.GetRenewalReminder(RenewalReminderService.getRenewalReminderById(renewalReminderDto.getRenewal_id(), userDetails)));
			model.put("PaymentType", PaymentTypeConstant.getPaymentTypeList());
			model.put("userName", userDetails.getFirstName());
			model.put("userId", userDetails.getId());
			model.put("configuration", configuration);
			model.put(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE, (boolean)configuration.get(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE));
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
		}
		return new ModelAndView("reviseRenewal_Reminder", model);
	}

	@RequestMapping("/{SelectStatus}/{pageNumber}/showRenewalReminder")
	public ModelAndView showRenewalReminder_SelectStatus(@PathVariable("SelectStatus") String Status,
			@PathVariable("pageNumber") Integer pageNumber, @RequestParam("renewal_id") final Long Renewal_id,
			final HttpServletResponse result) throws Exception {
		Map<String, Object> 		 model 			= new HashMap<String, Object>();
		CustomUserDetails			 userDetails 	= null;
		HashMap<String, Object>		 configuration	= null;
		RenewalReminderDto renewalReminder = null;
		try {
			 userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			renewalReminder = RenewalReminderService.getRenewalReminderById(Renewal_id, userDetails);
			model.put("renewalReminder", RRBL.GetRenewalReminder(renewalReminder));

			model.put("SelectStatus", RenealReminderStatus.NOT_APPROVED);
			model.put("RenewalReminderApproval", RRBL.getApprovalDetails(RenewalReminderService
					.Get_RenewalReminderApprovalDetails(renewalReminder.getRenewal_approvedID(), userDetails.getCompany_id())));

			model.put("SelectStatus", Status);
			model.put("SelectPage", pageNumber);
			model.put("configuration", configuration);
			model.put("SelectStatusString",
					RenealReminderStatus.getRenewalStatusName(RenealReminderStatus.NOT_APPROVED));

		} catch (NullPointerException e) {
			LOGGER.error("Exception : ", e);
			return new ModelAndView("redirect:/NotFound.in");
		}
		return new ModelAndView("showRenewal_Reminder", model);
	}

	@RequestMapping("/showRenewalReminder")
	public ModelAndView showRenewalReminder(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,BindingResult result) throws Exception {
		
		Map<String, Object> 			model 			= new HashMap<String, Object>();
		RenewalReminderDto 				renewalReminder = null;
		HashMap<String, Object>			configuration 	= null;
		CustomUserDetails 				userDetails		= null;
		try {
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			// show edit page the driverRemider List
			renewalReminder = RenewalReminderService.getRenewalReminderById(renewalReminderDto.getRenewal_id(),userDetails);
			model.put("renewalReminder", RRBL.GetRenewalReminder(renewalReminder));
			model.put("SelectStatus", RenealReminderStatus.NOT_APPROVED);
			model.put("RenewalReminderApproval",RRBL.getApprovalDetails(RenewalReminderService
					.Get_RenewalReminderApprovalDetails(renewalReminder.getRenewal_approvedID(), userDetails.getCompany_id())));
			model.put("SelectPage", 1);
			model.put("configuration",configuration);
		} catch (NullPointerException e) {
			LOGGER.error("Exception : ", e);
			return new ModelAndView("redirect:/NotFound.in");
		}
		return new ModelAndView("showRenewal_Reminder", model);
	}

	@RequestMapping(value = "/updateRenewalReminder", method = RequestMethod.POST)
	public ModelAndView updateRenewalReminder(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			@RequestParam("input-file-preview") MultipartFile file, RedirectAttributes redir) {
			
		CustomUserDetails 					userDetails 				= null;
		Map<String, Object>	 				model	 					= null;
		HashMap<String, Object>  			configuration				= null;
		String 								TIDMandatory 				= "";
		boolean 							UpdateRenewalReminder 		= false;
		RenewalReminder 					renewalReminder				= null;
		List<RenewalReminderDto> 			validateRenewalDB 			= null;
		String 								recieiptNumber 				= null;
		List<RenewalReminderDto> 			renewalReceipt 				= null;
		List<RenewalReminderDto> 			renewal 					= null;
		
		
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model 				= new HashMap<String, Object>();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			
			renewalReminder 	= RRBL.prepareRenewalRemider(renewalReminderDto);
			
			if (!file.isEmpty() || (boolean)configuration.get(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE)) {
				renewalReminder.setRenewal_document(true);
				
				String Query = "( RR.vid=" + renewalReminder.getVid() + " " + " AND RR.renewalTypeId ="
						+ renewalReminder.getRenewalTypeId() + " AND RR.renewal_Subid ="
						+ renewalReminder.getRenewal_Subid() + "  AND '" + renewalReminder.getRenewal_from()
						+ "' between RR.renewal_from AND RR.renewal_to  OR  RR.vid=" + renewalReminder.getVid() + " "
						+ " AND RR.renewalTypeId =" + renewalReminder.getRenewalTypeId() + " AND RR.renewal_Subid ="
						+ renewalReminder.getRenewal_Subid() + "  AND '" + renewalReminder.getRenewal_to()
						+ "' between RR.renewal_from AND RR.renewal_to ) AND RR.companyId = "
						+ userDetails.getCompany_id() + " AND RR.markForDelete = 0";
				// show Vehicle Name Renewal Type and Renewal SubType List
				
				
				validateRenewalDB = RenewalReminderService.Validate_RenewalReminder(Query);

				
				if (validateRenewalDB != null && !validateRenewalDB.isEmpty() ) {
					for (RenewalReminderDto renewalReminderDto2 : validateRenewalDB) {
						recieiptNumber = renewalReminderDto2.getRenewal_receipt();
						break;
					}
				}
				// System.out.println(renewal != null && !renewal.isEmpty() &&
				// (date).equalsIgnoreCase(renewalReminderDto.getRenewal_from()));
		if (validateRenewalDB != null && !validateRenewalDB.isEmpty() ) {				
			for(RenewalReminderDto renewalDB :validateRenewalDB ){	
				if (renewalDB != null  && !renewalDB.getRenewal_id().equals(renewalReminderDto.getRenewal_id())) {
					try {
						renewal = RRBL.prepareListofRenewalDto(validateRenewalDB);
						for (RenewalReminderDto add : renewal) {
							TIDMandatory += "<span>" + add.getVehicle_registration()
									+ " Compliance <a href=\"../../showRenewalReminder.in?renewal_id="
									+ add.getRenewal_id() + "\" target=\"_blank\">" + add.getRenewal_type() + " "
									+ add.getRenewal_subtype()
									+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
									+ add.getRenewal_from() + " To " + add.getRenewal_to() + " </span>, <br>";
						}
						redir.addFlashAttribute("already", TIDMandatory);

					} catch (Exception e) {
						LOGGER.error("Renewal Reminder:", e);
					}

					model.put("renewalRemindeAlready", true);
					return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);

				} else {

					// System.out.println((recieiptNumber).equalsIgnoreCase(renewalReminderDto.getRenewal_receipt()));

					if (recieiptNumber == null) {
						recieiptNumber = "";
					}

					if (!(recieiptNumber).equalsIgnoreCase(renewalReminderDto.getRenewal_receipt())) {

						try {
							String QueryReceipt = "RR.renewal_receipt='" + renewalReminder.getRenewal_receipt()
									+ "' AND RR.renewalTypeId =" + renewalReminder.getRenewalTypeId()
									+ " AND RR.renewal_Subid =" + renewalReminder.getRenewal_Subid()
									+ " AND RR.companyId = " + userDetails.getCompany_id()
									+ " AND RR.markForDelete = 0 ";
							// Renewal Reminder Receipt
							
							if((boolean)configuration.get("receiptnumbershow") && (boolean)configuration.get("validateDuplicateRefnumber")) {
								renewalReceipt = RRBL.prepareListofRenewalDto(
										RenewalReminderService.Validate_RenewalReminder(QueryReceipt));
								if (renewalReceipt != null && !renewalReceipt.isEmpty() && !renewalReceipt.get(0).getRenewal_id().equals(renewalReminderDto.getRenewal_id())) {
							
								// Checking the Value Of Mandatory Compliance
								for (RenewalReminderDto add : renewalReceipt) {
									TIDMandatory += "<span>" + add.getVehicle_registration()
											+ " Compliance <a href=\"../../showRenewalReminder.in?renewal_id="
											+ add.getRenewal_id() + "\" target=\"_blank\">" + add.getRenewal_type()
											+ " " + add.getRenewal_subtype()
											+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
											+ add.getRenewal_from() + " To " + add.getRenewal_to()
											+ " Receipt | Challan  Number " + add.getRenewal_receipt()
											+ " </span>, <br>";
								}
								redir.addFlashAttribute("ReceiptAlready", TIDMandatory);

								model.put("renewalReceiptAlready", true);
								return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
								}
							} else {
								UpdateRenewalReminder = true;
							}

						} catch (Exception e) {
							LOGGER.error("Renewal Reminder:", e);
						}

					} else {
						UpdateRenewalReminder = true;
					}
				}
			}
		}
				if (UpdateRenewalReminder) {
					java.util.Date previousDate	= null;
					Double	previousAmount		= 0.0;
					try {
						// get the reminder id to get one row to
						RenewalReminderDto renewalReminderOld = (RenewalReminderService
								.getRenewalReminderById(renewalReminderDto.getRenewal_id(), userDetails));
						
						previousDate	= SQL_DATE_FORMAT.parse(SQL_DATE_FORMAT.format(renewalReminderOld.getRenewal_payment_Date()));
						previousAmount		= renewalReminderOld.getRenewal_Amount();
						
						// get reminder Dto data change to
						// DriverReminderHistory
						RenewalReminderHistory renewalReminderHistory = RRBL
								.prepareRenewalRemiderHistory(renewalReminderOld);
						// save the RenewalHistory
						renewalReminderHistory.setCompanyId(userDetails.getCompany_id());
						renewalReminderHistory.setCreatedById(userDetails.getId());
						renewalReminderHistory.setLastModifiedById(userDetails.getId());
						RenewalReminderService.addRenewalReminderHistory(renewalReminderHistory);
						
						model.put("saveRenewalReminderHis", true);
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						renewalReminder.setRenewal_staus_id(RenealReminderStatus.NOT_APPROVED);
						// renewalReminder.setRenewal_status(RenealReminderStatus.NOT_APPROVED_NAME);
						/* update Renewal Reminder */
						RenewalReminderService.updateRenewalReminder(renewalReminder);
						
						VehicleDto	CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(renewalReminder.getVid(), userDetails.getCompany_id());
						ValueObject	ownerShipObject	= null;	
						if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED){
							if((renewalReminderDto.getRenewal_Amount() - previousAmount) != 0 || !(previousDate.equals(new Timestamp(renewalReminder.getRenewal_dateofpayment().getTime())))) {
								
								ownerShipObject	= new ValueObject();
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, renewalReminder.getRenewal_id());
								ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, renewalReminder.getVid());
								ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, renewalReminder.getRenewal_Amount());
								ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
								ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_RENEWAL);
								ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, renewalReminder.getCompanyId());
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, SQL_DATE_FORMAT.parse(SQL_DATE_FORMAT.format(renewalReminder.getRenewal_dateofpayment())));
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Urea Entry");
								ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Urea Number : "+renewalReminder.getRenewal_R_Number());
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
								ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, renewalReminder.getCreatedById());
								if(previousDate.equals(new Timestamp(renewalReminder.getRenewal_dateofpayment().getTime()))) {
									ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, - (renewalReminder.getRenewal_Amount() - previousAmount));
									ownerShipObject.put("isDateChanged", false);
								}else {
									ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, - renewalReminder.getRenewal_Amount());
									ownerShipObject.put("isDateChanged", true);
									
								}
								ownerShipObject.put("previousAmount", -previousAmount);
								ownerShipObject.put("previousDate", previousDate);
								
								
								VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
								vehicleAgentTxnCheckerService.save(agentTxnChecker);
								
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
							}
								
						}
						

						org.fleetopgroup.persistence.document.RenewalReminderDocument rewalDoc = new org.fleetopgroup.persistence.document.RenewalReminderDocument();
						if (!file.isEmpty()) {
							rewalDoc.setRenewal_id(renewalReminder.getRenewal_id());
							rewalDoc.setRenewal_R_Number(renewalReminder.getRenewal_R_Number());
							try {

								byte[] bytes = file.getBytes();
								rewalDoc.setRenewal_filename(file.getOriginalFilename());
								rewalDoc.setRenewal_content(bytes);
								rewalDoc.setRenewal_contentType(file.getContentType());

								/** Set Status in Issues */
								rewalDoc.setMarkForDelete(false);

								// when Add Renewal Reminder to file Renewal
								// Reminder History id is null
								rewalDoc.setRenewalHis_id((long) 0);

								/** Set Created by email Id */
								rewalDoc.setCreatedById(userDetails.getId());
								rewalDoc.setLastModifiedById(userDetails.getId());
								rewalDoc.setCompanyId(userDetails.getCompany_id());

								java.util.Date currentDateUpdate = new java.util.Date();
								Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

								/** Set Created Current Date */
								rewalDoc.setCreated(toDate);
								rewalDoc.setLastupdated(toDate);
							} catch (IOException e) {
								e.printStackTrace();
							}

							// Note: Save RenewalReminder To Document
							//RenewalReminderService.add_RenewalReminder_Document(rewalDoc);
							documentService.save(rewalDoc);

							// Note: This Update is Document ID Update Renewal
							// Reminder Details
							RenewalReminderService.Update_RenewalReminderDocument_ID_to_RenewalReminder(
									rewalDoc.get_id(), true, renewalReminder.getRenewal_id(),
									userDetails.getCompany_id());
						}
						
						if(ownerShipObject != null) {
							vehicleAgentIncomeExpenseDetailsService.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
						}
						model.put("updateRenewalReminder", true);
						return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
					} catch (Exception e) {

						return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
					}
				}

			} else {
				// messages
				return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
		}
		return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
	}

	@RequestMapping(value = "/ReUploadRenewalReminder", method = RequestMethod.POST)
	public ModelAndView ReUploadRenewalReminder(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			@RequestParam("input-file-preview") MultipartFile file, RedirectAttributes redir) {
		CustomUserDetails userDetails = null;
		org.fleetopgroup.persistence.document.RenewalReminderDocumentHistory	documentHistory	= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Map<String, Object> model = new HashMap<String, Object>();

			RenewalReminder renewalReminder = RRBL.prepareRenewalRemider(renewalReminderDto);
			
			org.fleetopgroup.persistence.document.RenewalReminderDocument previousDoc	=	documentService.get_RenewalReminder_Document(renewalReminderDto.getRenewal_document_id(), userDetails.getCompany_id());
			
			if(previousDoc != null) {
				documentHistory	= 	RRBL.getRenewalDocHistory(previousDoc, renewalReminder);
				documentHistory.setRendoc_id(renewalReminderDto.getRenewal_document_id());
			}
			
			if (!file.isEmpty()) {
				renewalReminder.setRenewal_document(true);

				org.fleetopgroup.persistence.document.RenewalReminderDocument rewalDoc = new org.fleetopgroup.persistence.document.RenewalReminderDocument();

				rewalDoc.setRenewal_id(renewalReminder.getRenewal_id());
				rewalDoc.setRenewal_R_Number(renewalReminder.getRenewal_R_Number());
				try {

					byte[] bytes = file.getBytes();
					rewalDoc.setRenewal_filename(file.getOriginalFilename());
					rewalDoc.setRenewal_content(bytes);
					rewalDoc.setRenewal_contentType(file.getContentType());

					/** Set Status in Issues */
					rewalDoc.setMarkForDelete(false);

					// when Add Renewal Reminder to file Renewal
					// Reminder History id is null
					rewalDoc.setRenewalHis_id((long) 0);

					/** Set Created by email Id */
					rewalDoc.setCreatedById(userDetails.getId());
					rewalDoc.setLastModifiedById(userDetails.getId());
					rewalDoc.setCompanyId(userDetails.getCompany_id());

					java.util.Date currentDateUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

					/** Set Created Current Date */
					rewalDoc.setCreated(toDate);
					rewalDoc.setLastupdated(toDate);
				} catch (IOException e) {
					e.printStackTrace();
				}
					//mongoOperations.save(rewalDoc);
				// Note: Save RenewalReminder To Document
				//RenewalReminderService.add_RenewalReminder_Document(rewalDoc);
				documentService.save(rewalDoc);
				
				if(documentHistory != null)
					RenewalReminderService.saveRenewalDocHistory(documentHistory);

				// Note: This Update is Document ID Update Renewal
				// Reminder Details
				RenewalReminderService.Update_RenewalReminderDocument_ID_to_RenewalReminder(
						rewalDoc.get_id(), true, renewalReminder.getRenewal_id(),
						userDetails.getCompany_id());

				model.put("updateRenewalReminder", true);
				return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);

				} else {
					// messages
					return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
				}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
		}
	}

	
	
	@RequestMapping(value = "/reviseUploadRenewalReminder", method = RequestMethod.POST)
	public ModelAndView reviseUploadRenewalReminder(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			@RequestParam("input-file-preview") MultipartFile file, RedirectAttributes redir) {
		CustomUserDetails userDetails = null;
		HashMap<String, Object>  configuration	= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			Map<String, Object> model = new HashMap<String, Object>();

			String TIDMandatory = "";

			boolean VEHICLESTATUS = false;

			// Check Vehicle Status Current IN ACTIVE OR NOT
			VehicleDto CheckVehicleStatus = vehicleService
					.Get_Vehicle_Current_Status_TripSheetID(renewalReminderDto.getVid());

			switch (CheckVehicleStatus.getvStatusId()) {
			case VehicleStatusConstant.VEHICLE_STATUS_ACTIVE:

				VEHICLESTATUS = true;
				break;
			case VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP:

				VEHICLESTATUS = true;
				break;
			case VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE:

				VEHICLESTATUS = true;
				break;
			case VehicleStatusConstant.VEHICLE_STATUS_SURRENDER:

				if (renewalReminderDto.getRenewal_type().equalsIgnoreCase("INSURANCE")) {
					VEHICLESTATUS = true;
				} else {

					VEHICLESTATUS = false;
				}
				break;

			default:
				VEHICLESTATUS = false;
				break;
			}
			
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			if(permission.contains(new SimpleGrantedAuthority("ALLOW_ALL_STATUS_RENEWAL"))) {
				VEHICLESTATUS	= true;
			}

			if (VEHICLESTATUS) {

				// get the information in jsp page f information in one driver
				RenewalReminder renewalReminder = RRBL.prepareRenewalRemider(renewalReminderDto);
				if((boolean) configuration.get("alwaysApprovedRenewal")) {

					renewalReminder.setRenewal_staus_id(RenealReminderStatus.APPROVED);
					renewalReminder.setRenewal_approvedbyId(userDetails.getId());
					renewalReminder.setRenewal_approveddate(new java.util.Date());
				
				}
				if (!file.isEmpty() || (boolean)configuration.get(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE)) {

					renewalReminder.setRenewal_document(true);

					String Query = " ( RR.vid=" + renewalReminder.getVid() + " " + " AND RR.renewalTypeId ="
							+ renewalReminder.getRenewalTypeId() + " AND RR.renewal_Subid = "
							+ renewalReminder.getRenewal_Subid() + "  AND '" + renewalReminder.getRenewal_from()
							+ "' between RR.renewal_from AND RR.renewal_to  OR  RR.vid=" + renewalReminder.getVid()
							+ " " + " AND RR.renewalTypeId =" + renewalReminder.getRenewalTypeId()
							+ " AND RR.renewal_Subid =" + renewalReminder.getRenewal_Subid() + "  AND '"
							+ renewalReminder.getRenewal_to()
							+ "' between RR.renewal_from AND RR.renewal_to ) AND RR.companyId = "
							+ userDetails.getCompany_id() + " AND RR.markForDelete = 0 ";
					// show Vehicle Name Renewal Type and Renewal SubType List
					List<RenewalReminderDto> renewal = RRBL
							.prepareListofRenewalDto(RenewalReminderService.Validate_RenewalReminder(Query));

					if (renewal != null && !renewal.isEmpty()) {

						try {
							// Checking the Value Of Mandatory Compliance
							for (RenewalReminderDto add : renewal) {
								TIDMandatory += "<span>" + add.getVehicle_registration()
										+ " Compliance <a href=\"../../showRenewalReminder.in?renewal_id="
										+ add.getRenewal_id() + "\" target=\"_blank\">" + add.getRenewal_type() + " "
										+ add.getRenewal_subtype()
										+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
										+ add.getRenewal_from() + " To " + add.getRenewal_to() + " </span>, <br>";
							}
							redir.addFlashAttribute("already", TIDMandatory);

						} catch (Exception e) {
							LOGGER.error("Renewal Reminder:", e);
						}

						model.put("renewalRemindeAlready", true);
						return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);

					} else {

						String QueryReceipt = "RR.renewal_receipt='" + renewalReminder.getRenewal_receipt()
								+ "' AND RR.renewalTypeId =" + renewalReminder.getRenewalTypeId()
								+ " AND RR.renewal_Subid =" + renewalReminder.getRenewal_Subid()
								+ " AND RR.companyId = " + userDetails.getCompany_id() + " AND RR.markForDelete = 0 ";
						// Renewal Reminder Receipt
						List<RenewalReminderDto> renewalReceipt = null;
						if((boolean)configuration.get("receiptnumbershow") && (boolean)configuration.get("validateDuplicateRefnumber")) {
							renewalReceipt = RRBL
									.prepareListofRenewalDto(RenewalReminderService.Validate_RenewalReminder(QueryReceipt));
						}

						if (renewalReceipt != null && !renewalReceipt.isEmpty()) {
							try {
								// Checking the Value Of Mandatory Compliance
								for (RenewalReminderDto add : renewalReceipt) {
									TIDMandatory += "<span>" + add.getVehicle_registration()
											+ " Compliance <a href=\"../../showRenewalReminder.in?renewal_id="
											+ add.getRenewal_id() + "\" target=\"_blank\">" + add.getRenewal_type()
											+ " " + add.getRenewal_subtype()
											+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
											+ add.getRenewal_from() + " To " + add.getRenewal_to()
											+ " Receipt | Challan  Number " + add.getRenewal_receipt()
											+ " </span>, <br>";
								}
								redir.addFlashAttribute("ReceiptAlready", TIDMandatory);

							} catch (Exception e) {
								LOGGER.error("Renewal Reminder:", e);
							}

							model.put("renewalReceiptAlready", true);
							return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
						} else {

							/*try {
								// get the reminder id to get one row to
								RenewalReminderDto renewalReminderOld = (RenewalReminderService
										.getRenewalReminderById(renewalReminderDto.getRenewal_id(), userDetails));
								// get reminder Dto data change to
								// DriverReminderHistory
								RenewalReminderHistory renewalReminderHistory = RRBL
										.prepareRenewalRemiderHistory(renewalReminderOld);
								renewalReminderHistory.setCompanyId(userDetails.getCompany_id());
								renewalReminderHistory.setCreatedById(userDetails.getId());
								renewalReminderHistory.setLastModifiedById(userDetails.getId());
								// save the RenewalHistory
								RenewalReminderService.addRenewalReminderHistory(renewalReminderHistory);

								model.put("saveRenewalReminderHis", true);
							} catch (NullPointerException e) {
								return new ModelAndView("redirect:/NotFound.in");
							} catch (Exception e) {
								e.printStackTrace();
							}*/
							try {

								/* save Renewal Reminder */
								
								RenewalReminderService.updateNewRRCreated(renewalReminder.getVid(), renewalReminder.getRenewalTypeId(), renewalReminder.getRenewal_Subid());
								
								RenewalReminderSequenceCounter		counter = renewalReminderSequenceService.findNextRenewal_R_Number(userDetails.getCompany_id());
								if (counter == null) {
									model.put("sequenceNotFound", true);
									return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
								}
								long renewal_R_Number = counter.getNextVal();
								
								renewalReminder.setRenewal_R_Number(renewal_R_Number);
								renewalReminder.setRenewal_id(null);
								
								RenewalReminderService.addRenewalReminder(renewalReminder);
								
								ValueObject		ownerShipObject		= null;	
								if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED ){
									ownerShipObject	= new ValueObject();
									ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
									ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, renewalReminder.getRenewal_id());
									ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, renewalReminder.getVid());
									ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, renewalReminder.getRenewal_Amount());
									ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
									ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_RENEWAL);
									ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, userDetails.getCompany_id());
									ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, SQL_DATE_FORMAT.parse(SQL_DATE_FORMAT.format(renewalReminder.getRenewal_dateofpayment())));
									ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Renewal Entry");
									ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Renewal Number : "+renewalReminder.getRenewal_R_Number());
									ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
									ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, userDetails.getId());
									ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, -renewalReminder.getRenewal_Amount());
									
									VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
									vehicleAgentTxnCheckerService.save(agentTxnChecker);
									
									ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
									ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
									
								}

								org.fleetopgroup.persistence.document.RenewalReminderDocument rewalDoc = new org.fleetopgroup.persistence.document.RenewalReminderDocument();
								if (!file.isEmpty()) {
									rewalDoc.setRenewal_id(renewalReminder.getRenewal_id());
									rewalDoc.setRenewal_R_Number(renewalReminder.getRenewal_R_Number());
									try {

										byte[] bytes = file.getBytes();
										rewalDoc.setRenewal_filename(file.getOriginalFilename());
										rewalDoc.setRenewal_content(bytes);
										rewalDoc.setRenewal_contentType(file.getContentType());

										/** Set Status in Issues */
										rewalDoc.setMarkForDelete(false);

										/** Set Created by email Id */
										rewalDoc.setCreatedById(userDetails.getId());
										rewalDoc.setLastModifiedById(userDetails.getId());

										java.util.Date currentDateUpdate = new java.util.Date();
										Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

										/** Set Created Current Date */
										rewalDoc.setCreated(toDate);
										rewalDoc.setLastupdated(toDate);
										rewalDoc.setCompanyId(userDetails.getCompany_id());
									} catch (IOException e) {
										LOGGER.error("Exception : ", e);
										e.printStackTrace();
									}

									// Note: Save RenewalReminder To Document
								//	RenewalReminderService.add_RenewalReminder_Document(rewalDoc);
									documentService.save(rewalDoc);
									// Note: This Update is Document ID Update
									// Renewal
									// Reminder Details
									RenewalReminderService.Update_RenewalReminderDocument_ID_to_RenewalReminder(
											rewalDoc.get_id(), true, renewalReminder.getRenewal_id(),
											userDetails.getCompany_id());	
								}
								if(ownerShipObject != null) {
									vehicleAgentIncomeExpenseDetailsService.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
								}
								
								model.put("saveRenewalReminder", true);
								return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
							} catch (Exception e) {
								LOGGER.error("Exception : ", e);
								return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
							}
						}

					}

				} else {
					// messages
					return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
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
					Link += " <a href=\"showWorkOrder?woId=" + CheckVehicleStatus.getTripSheetID()
							+ "\" target=\"_blank\">WO-" + workOrdersService
									.getWorkOrders(CheckVehicleStatus.getTripSheetID()).getWorkorders_Number()
							+ "  <i class=\"fa fa-external-link\"></i></a> ";
				}
				TIDMandatory += " <span> This " + CheckVehicleStatus.getVehicle_registration() + " Vehicle in "
						+ CheckVehicleStatus.getVehicle_Status() + " Status you can't create Renewal Reminder " + " "
						+ Link + "" + " </span> ,";
				redir.addFlashAttribute("VMandatory", TIDMandatory);
				model.put("closeStatus", true);

				return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true", model);
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception : ", e);
			return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
		}
	}

	/* Show to Renewal Remider History */
	@RequestMapping(value = "/RenewalReminderHis", method = RequestMethod.GET)
	public ModelAndView showRenewalReminderHis() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// show the driver list in all
			model.put("renewalHis",
					RRBL.prepareListofRenewalHistoryDto(RenewalReminderService.getRenewalReminderHistory(userDetails.getCompany_id())));
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		}
		return new ModelAndView("Renewal_ReminderHis", model);
	}

	/* Show to Renewal Remider History only that vehicle */
	@RequestMapping(value = "/VehicleRenewalReminderHis", method = RequestMethod.GET)
	public ModelAndView VehicleRenewalReminderHis(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// show the driver list in all
			model.put("renewalHis", RRBL.prepareListofRenewalHistoryDto(
					RenewalReminderService.listRenewalReminderHistory(renewalReminderDto.getVid())));
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		}
		return new ModelAndView("Renewal_ReminderHis", model);
	}

	/* Delete the Renewal Reminder */
	@RequestMapping("/deleteRenewalReminder")
	public ModelAndView deleteRenewalReminder(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// get the reminder id to get one row to
			RenewalReminderDto renewalReminder = (RenewalReminderService
					.getRenewalReminderById(renewalReminderDto.getRenewal_id(), userDetails));
			// get reminder Dto data change to DriverReminderHistory
			RenewalReminderHistory renewalReminderHistory = RRBL.prepareRenewalRemiderHistory(renewalReminder);
			renewalReminderHistory.setCompanyId(userDetails.getCompany_id());
			renewalReminderHistory.setCreatedById(userDetails.getId());
			renewalReminderHistory.setLastModifiedById(userDetails.getId());
			// save the RenewalHistory
			RenewalReminderService.addRenewalReminderHistory(renewalReminderHistory);

			// this message alert of show method
			// ((ModelAndView) model).addObject("error", true);
			model.put("saveRenewalReminderHis", true);
			
			VehicleDto	CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(renewalReminder.getVid(), userDetails.getCompany_id());
			ValueObject	ownerShipObject	= null;
			if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED && renewalReminder.getRenewal_Amount() > 0){
				ownerShipObject	= new ValueObject();
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, renewalReminder.getRenewal_id());
				ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, renewalReminder.getVid());
				ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, renewalReminder.getRenewal_Amount());
				ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
				ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_RENEWAL);
				ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, userDetails.getCompany_id());
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, SQL_DATE_FORMAT.parse(SQL_DATE_FORMAT.format(renewalReminder.getRenewal_payment_Date())));
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Renewal Entry");
				ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Renewal Number : "+renewalReminder.getRenewal_R_Number());
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
				ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, renewalReminder.getCreatedById());
				ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, renewalReminder.getRenewal_Amount());
				
				vehicleAgentIncomeExpenseDetailsService.deleteVehicleAgentIncomeExpense(ownerShipObject);
			}

			// delete method
			java.util.Date currentDate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
			RenewalReminderService.deleteRenewalReminder(renewalReminderDto.getRenewal_id(),
					userDetails.getCompany_id(),userDetails.getId(),toDate);
			// this message alert of show method
			model.put("deleteRenewalReminder", true);

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
	}

	/* Delete the Renewal Reminder */
	@RequestMapping("/deleteRenewalReminderHis")
	public ModelAndView deleteRenewalReminderHis(
			@ModelAttribute("command") RenewalReminderHistoryDto renewalReminderHistoryDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			// delete method
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			RenewalReminderService.deleteRenewalReminderHistory(renewalReminderHistoryDto.getRenewalhis_id(),
					userDetails.getCompany_id());

			/*RenewalReminderService.delete_RenewalReminderDocument_ID_History(
					renewalReminderHistoryDto.getRenewalhis_id(), userDetails.getCompany_id());*/
			documentService.delete_document(renewalReminderHistoryDto.getRenewalhis_id(), userDetails.getCompany_id());
			// this message alert of show method
			model.put("deleteRenewalReminder", true);

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Renewal Reminder:", e);
		}
		return new ModelAndView("redirect:/VehicleRenewalReminderHis.in?vid=" + renewalReminderHistoryDto.getVid() + "",
				model);
	}

	// import code
	@RequestMapping(value = "/importRenewalReminder", method = RequestMethod.POST)
	public ModelAndView saveImport(@ModelAttribute("command") @RequestParam("import") MultipartFile file,HttpServletRequest request, RedirectAttributes redir) {
		Map<String, Object> 			model 				= new HashMap<String, Object>();
		CustomUserDetails 				userDetails			= null;
		String 							rootPath			= "";
		File 							dir					= null;
		File 							serverFile			= null;
		List<RenewalType> 				renewalTypes		= null;
		List<RenewalSubTypeDto> 		renewalSubTypes		= null;
		List<PaymentTypeConstant> 		paymentList			= null;
		List<User> 						userHm				= null;
		RenewalReminder 				renewal				= null;
		RenewalReminderSequenceCounter 	counter 			= null;
		List<Vehicle> 					vehicleList			= null;
		String 							importdateFrom 	    = null;
		java.util.Date 					date 			    = null;
		java.sql.Date 					fromDate 		    = null;
		String 							importdateTo		= null;
		java.util.Date 					dateTo 		        = null;
		java.sql.Date 					toDate 		        = null;
		List<RenewalReminderDto> 		renewalReceipt		= null;
		
		HashMap<String, Object>  configuration	= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
		
			rootPath 		= request.getSession().getServletContext().getRealPath("/");
			dir 			= new File(rootPath + File.separator + "uploadedfile");
			
			if (!dir.exists()) {
				dir.mkdirs();
			}
	
			serverFile 		= new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
	
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
				 * model.put("msg", "failed to process file because : " + e.getMessage());
				 * return "mainpage";
				 */
				e.printStackTrace();
				}
	
			int 	CountSuccess 	= 0;
			int 	CountDuplicate 	= 0;
			String 	TIDMandatory 	= "";
	
			String[] nextLine;
			try {
				// read file
				// CSVReader(fileReader, ';', '\'', 1) means
				// using separator ; and using single quote ' . Skip first line when
				// read
				renewalTypes 		= RenewalTypeService.findAllByCompanyId(userDetails.getCompany_id());
				renewalSubTypes 	= RenewalSubTypeService.findAllByCompanyId(userDetails.getCompany_id());
				paymentList 		= PaymentTypeConstant.getPaymentTypeList();
				userHm 				= userService.getUserList(userDetails.getCompany_id());
				
				try (FileReader fileReader = new FileReader(serverFile);
						CSVReader reader = new CSVReader(fileReader, ';', '\'', 1);) {
						while ((nextLine = reader.readNext()) != null) {
							renewal = new RenewalReminder();
							
							for (int i = 0; i < nextLine.length; i++) {
								try {
									String[] importRenewal = nextLine[i].split(",");
									try {
										String Query = "AND V.vehicle_registration ='" + importRenewal[0]
												+ "' AND V.company_Id = " + userDetails.getCompany_id() + " ";
										
										vehicleList = VBL.prepareListofDto(vehicleService.listVehicleReportByVGPermision(Query));
										
											if (vehicleList != null && !vehicleList.isEmpty()) {
												for (Vehicle vehicleDto : vehicleList) {
													// renewal.setVehicle_registration(vehicleDto.getVehicle_registration());
													renewal.setVid(vehicleDto.getVid());
													break;
												}
												// renewal.setRenewal_type(importRenewal[1]);
												// renewal.setRenewal_subtype(importRenewal[2]);
												if (renewalTypes != null && !renewalTypes.isEmpty()) {
													for (RenewalType renewalType : renewalTypes) {
														if (renewalType.getRenewal_Type().trim().equalsIgnoreCase(importRenewal[1].trim())) {
															// renewal.setRenewal_type(renewalType.getRenewal_Type());
															renewal.setRenewalTypeId(renewalType.getRenewal_id());
															break;
														}
													}
												}
												if (renewalSubTypes != null && !renewalSubTypes.isEmpty()) {
													for (RenewalSubTypeDto renewalSubType : renewalSubTypes) {
														if (renewalSubType.getRenewal_SubType().trim().equalsIgnoreCase(importRenewal[2].trim())) {
															// renewal.setRenewal_subtype(renewalSubType.getRenewal_SubType());
															renewal.setRenewal_Subid(renewalSubType.getRenewal_Subid());
															break;
														}
													}
												}
												importdateFrom 		= new String(importRenewal[3]);
												date 				= dateFormat.parse(importdateFrom.replace('/', '-'));
												fromDate 			= new Date(date.getTime());
												
												renewal.setRenewal_from(fromDate);
												
			
												importdateTo 	= new String(importRenewal[4]);
												dateTo 			= dateFormat.parse(importdateTo.replace('/', '-'));
												toDate 			= new Date(dateTo.getTime());
												
												renewal.setRenewal_to(toDate);
												
			
												String importdatePay = new String(importRenewal[5]);
												
												if(importdatePay != null && !importdatePay.trim().equalsIgnoreCase("")) {
													
													java.util.Date 	datePay 	= dateFormat.parse(importdatePay.replace('/', '-'));
													java.sql.Date 	DateofPay 	= new Date(datePay.getTime());
													renewal.setRenewal_dateofpayment(DateofPay);
												}
												
			
												renewal.setRenewal_receipt(importRenewal[6]);
												renewal.setRenewal_Amount(Double.parseDouble(importRenewal[7]));
			
												// renewal.setRenewal_paymentType(importRenewal[8]);
												
												if(importRenewal.length > 8) {
													if (paymentList != null && !paymentList.isEmpty()) {
														for (PaymentTypeConstant constant : paymentList) {
															if (constant.getPaymentTypeName().equalsIgnoreCase(importRenewal[8])) {
																renewal.setPaymentTypeId(constant.getPaymentTypeId());
																break;
															}
														}
													}
													
												}
												
												if(importRenewal.length > 9)
													renewal.setRenewal_PayNumber(importRenewal[9]);
												if(importRenewal.length > 10)
													renewal.setRenewal_authorization(importRenewal[10]);
												if(importRenewal.length > 11)
													renewal.setRenewal_number(importRenewal[11]);
												if(importRenewal.length > 12) {
													
													for(User user : userHm) {
														if(importRenewal[12].equalsIgnoreCase(user.getFirstName()))
															renewal.setRenewal_paidbyId(user.getId());
														break;
													}
													
												}
												if(importRenewal.length > 13)
												renewal.setRenewal_timethreshold(Integer.parseInt(importRenewal[13]));
												renewal.setRenewal_periedthreshold(0);
												counter = renewalReminderSequenceService.findNextRenewal_R_Number(userDetails.getCompany_id());
												if (counter == null) {
													model.put("sequenceNotFound", true);
													return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
												}
												long renewal_R_Number = counter.getNextVal();
												renewal.setRenewal_R_Number(renewal_R_Number);
												renewal.setCompanyId(userDetails.getCompany_id());
												/* due end date */
												// get the date from database is 10/12/2015
												String 	duedate 		= importRenewal[4];
												Integer duetimeandperied;
												Integer timeandperied 	= 0;
												if(importRenewal.length > 13)
													timeandperied = Integer.parseInt(importRenewal[13]);
			
												if (timeandperied == 0) {
													duetimeandperied = 3; // 3 days
												} else {
													duetimeandperied = timeandperied; // 21
																						// days
												}
			
												String reminder_dateof = Change_CurrentDate_To_RenewalDate_SubTrackDate(duedate,duetimeandperied);
												
			
												// renewal.setRenewal_status(RenealReminderStatus.getRenewalStatusName(RenealReminderStatus.NOT_APPROVED));
												renewal.setRenewal_staus_id(RenealReminderStatus.NOT_APPROVED);
												// save renewal date
												//renewal.setRenewal_dateofRenewal(reminder_dateof);
												
												renewal.setDateofRenewal(dateFormat.parse(reminder_dateof));	
												renewal.setRenewal_document(false);
												renewal.setRenewal_document_id((long) 0);
												/** Set Status in Issues */
												renewal.setMarkForDelete(false);
			
												/**
												 * who Create this Issues get email id to user profile details
												 */
			
												/** Set Created by email Id */
												renewal.setCreatedById(userDetails.getId());
												renewal.setLastModifiedById(userDetails.getId());
												renewal.setCompanyId(userDetails.getCompany_id());
			
												java.util.Date currentDateUpdate = new java.util.Date();
												Timestamp toDatet = new java.sql.Timestamp(currentDateUpdate.getTime());
			
												/** Set Created Current Date */
												renewal.setCreated(toDatet);
												renewal.setLastupdated(toDatet);
												
			
												String Query1 = "( RR.vid =" + renewal.getVid() + " " + " AND RR.renewalTypeId ="
														+ renewal.getRenewalTypeId() + " AND RR.renewal_Subid ="
														+ renewal.getRenewal_Subid() + " AND '" + renewal.getRenewal_from()
														+ "' between RR.renewal_from AND RR.renewal_to  OR " + " RR.vid ="
														+ renewal.getVid() + " " + " AND RR.renewalTypeId ="
														+ renewal.getRenewalTypeId() + " AND RR.renewal_Subid ="
														+ renewal.getRenewal_Subid() + "  AND '" + renewal.getRenewal_to()
														+ "' between RR.renewal_from AND RR.renewal_to ) AND RR.companyId = "
														+ userDetails.getCompany_id() + " AND RR.markForDelete = 0 ";
												// show Vehicle Name Renewal Type and
												// Renewal SubType List
												List<RenewalReminderDto> renewalList = RRBL.prepareListofRenewalDto(
														RenewalReminderService.Validate_RenewalReminder(Query1));
												
												System.err.println("renewalList "+renewalList);
												
												if (renewalList != null && !renewalList.isEmpty()) {
			
													try {
														// Checking the Value Of already
														// Compliance
														for (RenewalReminderDto add : renewalList) {
															TIDMandatory += "<span>" + add.getVehicle_registration()
																	+ " Compliance <a href=\"../../showRenewalReminder.in?renewal_id="
																	+ add.getRenewal_id() + "\" target=\"_blank\">"
																	+ add.getRenewal_type() + " " + add.getRenewal_subtype()
																	+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
																	+ add.getRenewal_from() + " To " + add.getRenewal_to()
																	+ " </span>, <br>";
														}
														redir.addFlashAttribute("already", TIDMandatory);
			
													} catch (Exception e) {
														LOGGER.error("Renewal Reminder:", e);
													}
			
													model.put("renewalRemindeAlready", true);
			
												} else {
													if(renewal.getRenewal_receipt() != null && !renewal.getRenewal_receipt().trim().equalsIgnoreCase("")) {
														String QueryReceipt = "RR.renewal_receipt='" + renewal.getRenewal_receipt()
														+ "' AND RR.renewalTypeId =" + renewal.getRenewalTypeId()
														+ " AND RR.renewal_Subid = " + renewal.getRenewal_Subid()
														+ " AND RR.companyId = " + userDetails.getCompany_id()
														+ " AND RR.markForDelete = 0 ";
														
														if((boolean)configuration.get("receiptnumbershow") && (boolean)configuration.get("validateDuplicateRefnumber")) {
															renewalReceipt 		= RenewalReminderService.Validate_RenewalReminder(QueryReceipt);
														}
			
												if (renewalReceipt != null && !renewalReceipt.isEmpty()) {
													try {
														// show the Renewal Reminder
														try {
															// Checking the Value Of
															// Mandatory Compliance
															for (RenewalReminderDto add : renewalReceipt) {
																TIDMandatory += "<span>" + add.getVehicle_registration()
																		+ " Compliance <a href=\"../../showRenewalReminder.in?renewal_id="
																		+ add.getRenewal_id() + "\" target=\"_blank\">"
																		+ add.getRenewal_type() + " " + add.getRenewal_subtype()
																		+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
																		+ add.getRenewal_from() + " To " + add.getRenewal_to()
																		+ " Receipt | Challan  Number "
																		+ add.getRenewal_receipt() + " </span>, <br>";
															}
															redir.addFlashAttribute("ReceiptAlready", TIDMandatory);
			
														} catch (Exception e) {
															LOGGER.error("Renewal Reminder:", e);
														}
			
														model.put("renewalReceiptAlready", true);
													} catch (Exception e) {
			
														return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
													}
			
												}else {
													try {
														RenewalReminderService.updateNewRRCreated(renewal.getVid(), renewal.getRenewalTypeId(), renewal.getRenewal_Subid());
														RenewalReminderService.addRenewalReminder(renewal);
														CountSuccess++;
		
													} catch (final Exception e) {
														System.err.println("Exception "+e);
														++CountDuplicate;
														String Duplicate = "Vehicle =" + importRenewal[0];
														model.put("CountDuplicate", CountDuplicate);
														model.put("Duplicate", Duplicate);
														model.put("importSaveAlreadyError", true);
													}
												}
												} else {
			
														try {
															RenewalReminderService.updateNewRRCreated(renewal.getVid(), renewal.getRenewalTypeId(), renewal.getRenewal_Subid());
															RenewalReminderService.addRenewalReminder(renewal);
															CountSuccess++;
			
														} catch (final Exception e) {
															System.err.println("Exception "+e);
															++CountDuplicate;
															String Duplicate = "Vehicle =" + importRenewal[0];
															model.put("CountDuplicate", CountDuplicate);
															model.put("Duplicate", Duplicate);
															model.put("importSaveAlreadyError", true);
														}
			
													}
			
												}
			
											} // close if
										else {
											++CountDuplicate;
											String Duplicate = "Vehicle = " + importRenewal[0];
											model.put("CountDuplicate", CountDuplicate);
											model.put("Duplicate", Duplicate);
											model.put("importSaveAlreadyError", true);
										}
		
									} catch (Exception e) {
										LOGGER.error("Renewal Reminder:", e);
									}
		
								} catch (Exception e) { 
									e.printStackTrace();
									System.err.println("Exception dsad "+e);
									model.put("importSaveError", true);
		
									return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
								}
		
							} 
		
						}
				}
			} catch (Exception e) {
				System.out.println("error while reading csv and put to db : " + e.getMessage());
	
				model.put("importSaveError", true);
	
				return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
			}
	
			// show the Vehicle List
	
			model.put("CountSuccess", CountSuccess);
	
			model.put("importSave", true);
	
			return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
		}catch(Exception e) {
	
	}finally {
		userDetails			= null;
		rootPath			= "";
		dir					= null;
		serverFile			= null;
		renewalTypes		= null;
		renewalSubTypes		= null;
		paymentList			= null;
		userHm				= null;
		renewal				= null;
		counter 			= null;
		vehicleList			= null;
		importdateFrom 	    = null;
		date 			    = null;
		fromDate 		    = null;
		importdateTo		= null;
		dateTo 		        = null;
		toDate 		        = null;
		renewalReceipt		= null;
		
	}
		return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
	}

	// get logic in Renewal Reminder Information
	public RenewalReminder prepareRenewalRemider(RenewalReminderDto renewalReminderDto) throws ParseException {

		RenewalReminder renewal = new RenewalReminder();

		renewal.setRenewal_id(renewalReminderDto.getRenewal_id());
		renewal.setVid(renewalReminderDto.getVid());

		String[] From_TO_Array = null;
		try {
			/*
			 * Vehicle vehiclename = vehicleService
			 * .getVehicle_Details_Renewal_Reminder_Entries(renewalReminderDto.getVid()); if
			 * (vehiclename != null) { // show Vehicle name
			 * renewal.setVehicle_registration(vehiclename.getVehicle_registration()); }
			 */
			String From_TO = renewalReminderDto.getRenewal_from();
			// System.out.println(renewalReminderDto.getRenewal_from());
			From_TO_Array = From_TO.split("  to  ");
			// System.out.println(From_TO_Array[0]);
			// System.out.println(From_TO_Array[1]);

			java.util.Date date = dateFormat.parse(From_TO_Array[0]);
			java.sql.Date fromDate = new Date(date.getTime());
			// System.out.println(fromDate);
			renewal.setRenewal_from(fromDate);
			renewal.setRenewal_to(sqlFormatTime.parse(From_TO_Array[1] + DateTimeUtility.DAY_END_TIME));
			
			if(renewalReminderDto.getRenewal_dateofpayment()!= "" || renewalReminderDto.getRenewal_dateofpayment()!=null) {
			java.util.Date datePay = dateFormat.parse(renewalReminderDto.getRenewal_dateofpayment());
			java.sql.Date DateofPay = new Date(datePay.getTime());
			renewal.setRenewal_dateofpayment(DateofPay);
			}

		} catch (Exception e) {
			LOGGER.error("Renewal Reminder:", e);
		}
		// renewal.setRenewal_type(renewalReminderDto.getRenewal_type());
		// renewal.setRenewal_subtype(renewalReminderDto.getRenewal_subtype());

		renewal.setRenewal_receipt(renewalReminderDto.getRenewal_receipt());
		renewal.setRenewal_Amount(renewalReminderDto.getRenewal_Amount());
		renewal.setVendorId(renewalReminderDto.getVendorId());
		renewal.setTallyCompanyId(renewalReminderDto.getTallyCompanyId());
		/*
		 * renewal.setRenewal_dateofpayment(renewalReminderDto.
		 * getRenewal_dateofpayment());
		 */
		/* String startDate= renewalReminderDto.getRenewal_dateofpayment(); */

		renewal.setRenewal_PayNumber(renewalReminderDto.getRenewal_PayNumber());
		renewal.setRenewal_authorization(renewalReminderDto.getRenewal_authorization());
		renewal.setRenewal_number(renewalReminderDto.getRenewal_number());
		if(renewalReminderDto.getRenewal_paidbyId()!=null) {
		renewal.setRenewal_paidbyId(renewalReminderDto.getRenewal_paidbyId());
		}
		renewal.setRenewal_timethreshold(renewalReminderDto.getRenewal_timethreshold());
		renewal.setRenewal_periedthreshold(renewalReminderDto.getRenewal_periedthreshold());
		/* due end date */
		// get the date from database is 10/12/2015
		String duedate = From_TO_Array[1];
		// that date split the date & month & year date[0]=10,
		// date[1]=12,date[2]=2015
		/* due timethreshold and peried of reminder */
		Integer duetimeandperied;
		// calculation of the reminder before date of reminder
		Integer timeandperied = (renewalReminderDto.getRenewal_timethreshold())
				* (renewalReminderDto.getRenewal_periedthreshold());

		// timeandperied=3*0 days; or timeandperied=3*7 weeks or
		// timeandperied=3*28 month

		if (timeandperied == 0) {
			duetimeandperied = renewalReminderDto.getRenewal_timethreshold(); // 3
																				// days
		} else {
			duetimeandperied = timeandperied; // 21 days
		}

		String reminder_dateof = Change_CurrentDate_To_RenewalDate_SubTrackDate(duedate, duetimeandperied);
		// save renewal date
		//renewal.setRenewal_dateofRenewal(reminder_dateof);
		renewal.setDateofRenewal(dateFormat.parse(reminder_dateof));

		/** Set Status in Issues */
		renewal.setMarkForDelete(false);

		/** who Create this Issues get email id to user profile details */
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		/** Set Created by email Id */
		renewal.setCreatedById(userDetails.getId());
		renewal.setLastModifiedById(userDetails.getId());
		renewal.setCompanyId(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		/** Set Created Current Date */
		renewal.setCreated(toDate);
		renewal.setLastupdated(toDate);

		// Note: Document ID is Now Zero
		renewal.setRenewal_document_id((long) 0);

		renewal.setRenewal_approvedID((long) 0);

		// PaymentTypeId
		renewal.setPaymentTypeId(renewalReminderDto.getPaymentTypeId());

		renewal.setRenewalTypeId(renewalReminderDto.getRenewalTypeId());
		renewal.setRenewal_Subid(renewalReminderDto.getRenewal_Subid());

		renewal.setRenewal_staus_id(RenealReminderStatus.NOT_APPROVED);

		/** Set Status Not Approved Date */
		// renewal.setRenewal_status(RenealReminderStatus.NOT_APPROVED_NAME);
		// renewal.setRenewal_paymentType(PaymentTypeConstant.getPaymentTypeName(renewalReminderDto.getPaymentTypeId()));

		return renewal;
	}

	public String Change_CurrentDate_To_RenewalDate_SubTrackDate(String duedate, Integer duetimeandperied) {

		// get the date from database is 10-12-2015
		// that date split the date & month & year date[0]=10,
		// date[1]=12,date[2]=2015
		String due_EndDate[] = duedate.split("-");

		// convert string to integer in date
		Integer due_enddate = Integer.parseInt(due_EndDate[0]);

		// convert string to integer in month and one more remove 0 is month why
		// means calendar format is [0-11] only so i am subtract to -1 method
		Integer due_endmonth = (Integer.parseInt(due_EndDate[1].replaceFirst("^0+(?!$)", "")) - 1);

		// convert string to integer in year
		Integer due_endyear = Integer.parseInt(due_EndDate[2]);

		// create new calendar at specific date. Convert to java.util calendar
		// type
		Calendar due_endcalender = new GregorianCalendar(due_endyear, due_endmonth, due_enddate);

		// print date for default value
		// System.out.println("Past calendar : " + due_endcalender.getTime());

		// subtract 2 days from the calendar and the due_time and peried of
		// throsold integer
		due_endcalender.add(Calendar.DATE, -duetimeandperied);
		// System.out.println(duetimeandperied+"days ago: " +
		// due_endcalender.getTime());

		// this format this the [0-11] but real time month format this [1-12]
		Integer month = due_endcalender.get(Calendar.MONTH) + 1;
		// i am change the format to story the database in reminder date
		String reminder_dateof = "" + due_endcalender.get(Calendar.DATE) + "-" + month + "-"
				+ due_endcalender.get(Calendar.YEAR);

		return reminder_dateof;
	}

	@RequestMapping(value = "/updateApprovedRenewal", method = RequestMethod.POST)
	public ModelAndView updateApprovedRenewal(@ModelAttribute("command") RenewalReminder renewalReminder,
			RenewalReminderDto renewalReminderDto, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {

			/**
			 * who Create this Issues get email id to user profile details
			 */
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			if (userDetails != null) {
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
				RenewalReminderService.updateApproedBy(renewalReminder.getRenewal_id(),
						renewalReminder.getRenewal_staus_id(),
						userDetails.getId(), renewalReminder.getRenewal_approvedComment(), toDate,
						userDetails.getCompany_id());

				if (renewalReminder.getRenewal_approvedID() != null && renewalReminder.getRenewal_approvedID() != 0) {

					RenewalReminderService.Subtract_PendingAmount_Add_Approval_APPROVED_Amount_Value(
							renewalReminder.getRenewal_Amount(), renewalReminder.getRenewal_approvedID(),
							userDetails.getCompany_id());
				}
			}

		} catch (Exception e) {
			LOGGER.error("Renewal Reminder:", e);
			e.printStackTrace();

			return new ModelAndView(
					"redirect:/1/1/showRenewalReminder.in?renewal_id=" + renewalReminder.getRenewal_id() + "", model);

		}

		return new ModelAndView(
				"redirect:1/1/showRenewalReminder.in?renewal_id=" + renewalReminder.getRenewal_id() + "", model);
	}
	
	@RequestMapping(value = "/updateRenewalReriod", method = RequestMethod.POST)
	public ModelAndView updateRenewalReriod(@ModelAttribute("command") 
			RenewalReminderDto renewalReminderDto, BindingResult result, RedirectAttributes redir) {

		Map<String, Object> model = new HashMap<String, Object>();
		RenewalReminder renewalReminder = new RenewalReminder();
		try {
			String 								TIDMandatory 		= "";
			String[] From_TO_Array = null;
			String From_TO = renewalReminderDto.getRenewal_from();
			From_TO_Array = From_TO.split("  to  ");
			String duedate = From_TO_Array[1];
			
			Integer duetimeandperied;
			// calculation of the reminder before date of reminder
			Integer timeandperied = (renewalReminderDto.getRenewal_timethreshold())
					* (renewalReminderDto.getRenewal_periedthreshold());

			// timeandperied=3*0 days; or timeandperied=3*7 weeks or
			// timeandperied=3*28 month

			if (timeandperied == 0) {
				duetimeandperied = renewalReminderDto.getRenewal_timethreshold(); // 3
																					// days
			} else {
				duetimeandperied = timeandperied; // 21 days
			}

			/**
			 * who Create this Issues get email id to user profile details
			 */
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			if (userDetails != null) {
				java.util.Date currentDate = new java.util.Date();
				Timestamp updateDate = new java.sql.Timestamp(currentDate.getTime());
				
				String reminder_dateof = Change_CurrentDate_To_RenewalDate_SubTrackDate(duedate, duetimeandperied);
				// save renewal date
				//renewal.setRenewal_dateofRenewal(reminder_dateof);
				renewalReminder.setDateofRenewal(dateFormat.parse(reminder_dateof));
				renewalReminder.setLastModifiedById(userDetails.getId());
				renewalReminder.setLastupdated(updateDate);
				renewalReminder.setCompanyId(userDetails.getCompany_id());
				java.util.Date date = dateFormat.parse(From_TO_Array[0]);
				java.sql.Date fromDate = new Date(date.getTime());
				// System.out.println(fromDate);
				renewalReminder.setRenewal_from(fromDate);
				java.util.Date dateTo = dateFormat.parse(From_TO_Array[1]);
				java.sql.Date toDate = new Date(dateTo.getTime());
				// System.out.println(toDate);
				renewalReminder.setRenewal_to(toDate);
				renewalReminder.setRenewal_timethreshold(renewalReminderDto.getRenewal_timethreshold());
				renewalReminder.setRenewal_periedthreshold(renewalReminderDto.getRenewal_periedthreshold());
				renewalReminder.setRenewal_id(renewalReminderDto.getRenewal_id());
				
				RenewalReminder preReminder	=	RenewalReminderService.getRenewalReminderById(renewalReminderDto.getRenewal_id());
				
				String Query = " ( RR.vid =" + preReminder.getVid() + " " + " AND RR.renewalTypeId ="
						+ preReminder.getRenewalTypeId() + " AND RR.renewal_Subid ="
						+ preReminder.getRenewal_Subid() + "  AND '" + renewalReminder.getRenewal_from()
						+ "' between RR.renewal_from AND RR.renewal_to  OR RR.vid=" + preReminder.getVid() + " "
						+ " AND RR.renewalTypeId =" + preReminder.getRenewalTypeId() + " AND RR.renewal_Subid ="
						+ preReminder.getRenewal_Subid() + "  AND '" + renewalReminder.getRenewal_to()
						+ "' between RR.renewal_from AND RR.renewal_to ) AND RR.companyId = "
						+ userDetails.getCompany_id() + " AND RR.markForDelete = 0 ";
				// show Vehicle Name Renewal Type and Renewal SubType List
			List<RenewalReminderDto>	 renewal = RRBL
						.prepareListofRenewalDto(RenewalReminderService.Validate_RenewalReminder(Query));

				if (renewal != null && !renewal.isEmpty() && !renewal.get(0).getRenewal_id().equals(renewalReminder.getRenewal_id())) {
					try {
						// Checking the Value Of Mandatory Compliance
						for (RenewalReminderDto add : renewal) {
							TIDMandatory += "<span>" + add.getVehicle_registration()
							+ " Compliance <a href=\"../../showRenewalReminder.in?renewal_id="
							+ add.getRenewal_id() + " \" target=\"_blank\" >" + add.getRenewal_type() + " "
							+ add.getRenewal_subtype()
							+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
							+ add.getRenewal_from() + " To " + add.getRenewal_to() + " </span>, <br>";
						}
						redir.addFlashAttribute("already", TIDMandatory);

					} catch (Exception e) {
						LOGGER.error("Renewal Reminder:", e);
					}

					model.put("renewalRemindeAlready", true);
					return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
				}
				RenewalReminderService.updateRenewalPeriod(renewalReminder);
			
		}	

		} catch (Exception e) {
			LOGGER.error("Renewal Reminder:", e);
			e.printStackTrace();

			return new ModelAndView(
					"redirect:/1/1/showRenewalReminder.in?renewal_id=" + renewalReminderDto.getRenewal_id() + "", model);

		}

		return new ModelAndView(
				"redirect:1/1/showRenewalReminder.in?renewal_id=" + renewalReminderDto.getRenewal_id() + "", model);
	}

	/***************************************************************************************************************************************
	 ************** Get Vehicle List drop down less Loading to Search
	 ***************************************************************************************************************************************/

	@RequestMapping(value = "/getVehicleListRenewal", method = RequestMethod.POST)
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

	@RequestMapping(value = "/getRenewalType", method = RequestMethod.GET)
	public void getVehicleTripEditList(Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = null;
		HashMap<String, Object> configuration= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			List<RenewalType> addresses = new ArrayList<RenewalType>();

			List<RenewalType> renewal = RenewalTypeService.findAllByCompanyId(userDetails.getCompany_id());
			if (renewal != null && !renewal.isEmpty()) {
				for (RenewalType add : renewal) {
					RenewalType wadd = new RenewalType();
					wadd.setRenewal_id(add.getRenewal_id());
					wadd.setRenewal_Type(add.getRenewal_Type());
					if((boolean) configuration.getOrDefault("hideRenewalTypes",false)) {
						if(!(wadd.getRenewal_Type().equalsIgnoreCase("AUTHORISATION") || wadd.getRenewal_Type().equalsIgnoreCase("R C"))) 
						addresses.add(wadd);
					}else {
						addresses.add(wadd);
					}
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(addresses));
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value = "/getRenewalSubTypeList", method = RequestMethod.POST)
	public void getRenewalSubTypeList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<RenewalSubType> Part = new ArrayList<RenewalSubType>();
		List<RenewalSubType> Parttype = RenewalSubTypeService.listRenewalSubTypeSearch(term, userDetails.getCompany_id());
		
		if (Parttype != null && !Parttype.isEmpty()) {
			for (RenewalSubType add : Parttype) {

				RenewalSubType wadd = new RenewalSubType();

				wadd.setRenewal_id(add.getRenewal_id());
				wadd.setRenewal_SubType(add.getRenewal_SubType());
				wadd.setRenewal_Subid(add.getRenewal_Subid());
				
				Part.add(wadd);
			}
		}
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(Part));
	}

	@RequestMapping(value = "/getRenewalSubTypeChange", method = RequestMethod.GET)
	public void getFuelVehicleOdoMerete(@RequestParam(value = "RenewalType", required = true) Integer RenewalType,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<RenewalSubType> addresses = new ArrayList<RenewalSubType>();
		List<RenewalSubType> renewalsub = RenewalSubTypeService.listRenewalSubTypeSearch(RenewalType,
				userDetails.getCompany_id());
		if (renewalsub != null && !renewalsub.isEmpty()) {
			for (RenewalSubType add : renewalsub) {
				RenewalSubType wadd = new RenewalSubType();
				wadd.setRenewal_Subid(add.getRenewal_Subid());
				wadd.setRenewal_SubType(add.getRenewal_SubType());
				// System.out.println(add.getVid());
				addresses.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonODDMETER = " + gson.toJson(wadd));

		response.getWriter().write(gson.toJson(addresses));
	}

	@RequestMapping(value = "/getRenewalSubTypeById", method = RequestMethod.GET)
	public void getRenewalSubTypeChange(@RequestParam(value = "RenewalType", required = true) Integer RenewalType,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<RenewalSubType> addresses = new ArrayList<RenewalSubType>();
		List<RenewalSubType> renewalsub = RenewalSubTypeService.listRenewalSubTypeSearch(RenewalType,
				userDetails.getCompany_id());
		if (renewalsub != null && !renewalsub.isEmpty()) {
			for (RenewalSubType add : renewalsub) {
				RenewalSubType wadd = new RenewalSubType();
				wadd.setRenewal_Subid(add.getRenewal_Subid());
				wadd.setRenewal_SubType(add.getRenewal_SubType());
				// System.out.println(add.getVid());
				addresses.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonODDMETER = " + gson.toJson(wadd));

		response.getWriter().write(gson.toJson(addresses));
	}
	
	@RequestMapping("/renewalReminderAjaxAdd")
	public ModelAndView renewalReminderAjaxAdd(@RequestParam("vid") final int vid,
			@RequestParam("renewalSubTypeId") final int renewalSubTypeId, final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails			 userDetails 	= null;
		HashMap<String, Object>		 configuration	= null;
		Vehicle						 vehicle 		= null;
		try {
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			
			vehicle = VehicleRepository.getVehicel(vid, userDetails.getCompany_id());
			model.put("vehicle", vehicle);
			
			model.put("userName", userDetails.getFirstName());
			model.put("userId", userDetails.getId());
			model.put("PaymentType", PaymentTypeConstant.getPaymentTypeList());
			model.put(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE, (boolean)configuration.get(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE));
			model.put("configuration", configuration);
			model.put("SelectPage", 1);
			model.put("renewalVehicleId", vid);
			model.put("renewalSubTypeId", renewalSubTypeId);
			
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			model.put("accessToken", uniqueID);
		
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			 userDetails 	= null;
			 configuration	= null;
		}

		return new ModelAndView("RenewalReminderAjaxAdd", model);
	}
	
	@RequestMapping("/renewalReminderAjaxEdit")
	public ModelAndView renewalReminderAjaxEdit(@RequestParam("renewalId") final Long renewalId, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			
			model.put("renewalId", renewalId);
			model.put("PaymentType", PaymentTypeConstant.getPaymentTypeList());
			model.put("configuration", configuration);
			model.put(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE, configuration.get(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE));
			
			
		} catch (NullPointerException e) {
			LOGGER.error("Exception : ", e);
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("RenewalReminderAjaxEdit", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/viewRenewalReminder")
	public ModelAndView viewRenewalReminder(final TripSheetDto tripsheetdto, final HttpServletRequest request) {
		String 							todaysDate									= null;
		Map<String, Object> 			model 										= new HashMap<String, Object>();
		boolean							createApprovalPermission					= false;
		String 							tomorrowDate								= null;
		String 							dayAfterTomorrow							= null;
		String 							seventhDayFromDayAfterTomorrow				= null;
		String 							eightDayFromDayAfterTomorrow				= null;
		String 							fifteenDayFromEightDay						= null;
		String 							nextMonthStartDate							= null;
		String 							nextMonthEndDate							= null;
		String[]						nextMonthStartDateArr						= null;
		String[]						nextMonthEndDateArr							= null;
		CustomUserDetails 				userDetails 								= null;
		Collection<GrantedAuthority>	permission									= null;
		HashMap<String, Object> 		configuration	         	 				= null;
		HashMap<String, Object> 		reConfiguration	         	 				= null;
		boolean							excludeSurrnederAndInactive	        		= false;
		ValueObject						dueSoonCountByVStatus						= null;
		ValueObject						overDueCountByVStatus						= null;
		HashMap<Short, Long> 			dueSoonCountHM								= null;	
		HashMap<Short, Long> 			overDueCountHM								= null;	
		long							activeDueSoonCount							= 0;
		long							inactiveDueSoonCount						= 0;
		long							activeOverDueCount							= 0;
		long							inactiveOverDueCount						= 0;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			permission			= userDetails.getGrantedAuthoritiesList();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			reConfiguration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			
			excludeSurrnederAndInactive 	= (boolean) configuration.getOrDefault(VehicleConfigurationContant.VEHICLE_EXCLUDING_SURRENDER_AND_INACTIVE, false);
			if(permission.contains(new SimpleGrantedAuthority("CREATE_RR_APPROVAL"))) {
				createApprovalPermission = true;
			}
			todaysDate							 		= format2.format(new java.util.Date());//current day
			tomorrowDate								= DateTimeUtility.getNextDateStr(todaysDate, 1);//2nd day
			dayAfterTomorrow							= DateTimeUtility.getNextDateStr(todaysDate, 2);//3rd day
			seventhDayFromDayAfterTomorrow				= DateTimeUtility.getNextDateStr(todaysDate, 8);//3rd + 7th day 
			eightDayFromDayAfterTomorrow				= DateTimeUtility.getNextDateStr(todaysDate, 9);//7th + 1 day 
			fifteenDayFromEightDay						= DateTimeUtility.getNextDateStr(todaysDate, 23);//9th + 15 day 

			ValueObject 	nextMothStartAndEndDate 	= DateTimeUtility.getNextMonthStartAndEndDate();
			
			nextMonthStartDate			= nextMothStartAndEndDate.getString(DateTimeUtility.START_OF_MONTH) ;
			nextMonthEndDate			= nextMothStartAndEndDate.getString(DateTimeUtility.END_OF_MONTH) ;
			
			nextMonthStartDateArr 		= nextMonthStartDate.split("-");
			nextMonthEndDateArr 		= nextMonthEndDate.split("-");
			
			nextMonthStartDate 		 	= nextMonthStartDateArr[2]+"-"+nextMonthStartDateArr[1]+"-"+nextMonthStartDateArr[0];
			nextMonthEndDate 			= nextMonthEndDateArr[2]+"-"+nextMonthEndDateArr[1]+"-"+nextMonthEndDateArr[0];
			
			dueSoonCountByVStatus		= RenewalReminderService.totalDueSoonCount(todaysDate, userDetails.getCompany_id());
			overDueCountByVStatus		= RenewalReminderService.getTotalOverDueRenewalCount(todaysDate+DateTimeUtility.DAY_START_TIME, userDetails.getCompany_id());
			
			dueSoonCountHM				= (HashMap<Short, Long>) dueSoonCountByVStatus.get("dueSoonCountHM");
			overDueCountHM				= (HashMap<Short, Long>) overDueCountByVStatus.get("overDueCountHM");
			
			if(dueSoonCountHM != null ) {
				for(Entry<Short, Long> status : dueSoonCountHM.entrySet()){
					if(excludeSurrnederAndInactive &&   status.getKey()  != VehicleStatusConstant.VEHICLE_STATUS_INACTIVE && status.getKey()  != VehicleStatusConstant.VEHICLE_STATUS_SURRENDER) {
						activeDueSoonCount += status.getValue() ;
					}else if(!excludeSurrnederAndInactive){
						activeDueSoonCount += status.getValue() ;
					}
					if(status.getKey()  == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE ) {
						inactiveDueSoonCount += status.getValue() ;
					}
				}
			}
			if(overDueCountHM != null ) {
				for(Entry<Short, Long> status : overDueCountHM.entrySet()){
					if(excludeSurrnederAndInactive && status.getKey() != VehicleStatusConstant.VEHICLE_STATUS_INACTIVE && status.getKey() != VehicleStatusConstant.VEHICLE_STATUS_SURRENDER) {
						activeOverDueCount += status.getValue() ;
					}else if(!excludeSurrnederAndInactive){
						activeOverDueCount += status.getValue() ;
					}
					if(status.getKey() == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE ) {
						inactiveOverDueCount += status.getValue() ;
					}
				}
			}
			
			ValueObject 	todays	 			= RenewalReminderService.getMonthlyRenwalCountAndAmount(todaysDate+DateTimeUtility.DAY_START_TIME,todaysDate+DateTimeUtility.DAY_END_TIME, userDetails.getCompany_id());
			ValueObject 	tomorrow	 		= RenewalReminderService.getMonthlyRenwalCountAndAmount(tomorrowDate+DateTimeUtility.DAY_START_TIME,tomorrowDate+DateTimeUtility.DAY_END_TIME, userDetails.getCompany_id());
			ValueObject 	nextSeventDay	 	= RenewalReminderService.getMonthlyRenwalCountAndAmount(dayAfterTomorrow+DateTimeUtility.DAY_START_TIME,seventhDayFromDayAfterTomorrow+DateTimeUtility.DAY_END_TIME, userDetails.getCompany_id());
			ValueObject 	nextFifteentDay		= RenewalReminderService.getMonthlyRenwalCountAndAmount(eightDayFromDayAfterTomorrow+DateTimeUtility.DAY_START_TIME,fifteenDayFromEightDay+DateTimeUtility.DAY_END_TIME, userDetails.getCompany_id());
			ValueObject 	nextMonth	 		= RenewalReminderService.getMonthlyRenwalCountAndAmount(nextMonthStartDate+DateTimeUtility.DAY_START_TIME,nextMonthEndDate+DateTimeUtility.DAY_END_TIME, userDetails.getCompany_id());
			
			
			model.put("reConfiguration", reConfiguration);
			model.put("configuration", configuration);
			model.put("todaysDate", (dateFormat.format(SQL_DATE_FORMAT.parseObject(todaysDate)) +" To "+ dateFormat.format(SQL_DATE_FORMAT.parseObject(todaysDate))));
			model.put("tomorrowDate", (dateFormat.format(SQL_DATE_FORMAT.parseObject( tomorrowDate)) +" To "+ dateFormat.format(SQL_DATE_FORMAT.parseObject(tomorrowDate))));
			model.put("nextSeventDay", (dateFormat.format(SQL_DATE_FORMAT.parseObject(dayAfterTomorrow))+" To "+ dateFormat.format(SQL_DATE_FORMAT.parseObject( seventhDayFromDayAfterTomorrow))));
			model.put("nextFifteenDay", (dateFormat.format(SQL_DATE_FORMAT.parseObject(eightDayFromDayAfterTomorrow))+" To "+dateFormat.format(SQL_DATE_FORMAT.parseObject(fifteenDayFromEightDay))));
			model.put("nextMonth", (dateFormat.format(SQL_DATE_FORMAT.parseObject(nextMonthStartDate))+" To "+ dateFormat.format(SQL_DATE_FORMAT.parseObject(nextMonthEndDate))));
			
			model.put("todaysCount", todays.get("renewalCount"));
			model.put("tomorrowCount", tomorrow.get("renewalCount"));
			model.put("nextSeventDayCount", nextSeventDay.get("renewalCount"));
			model.put("nextFifteentDayCount", nextFifteentDay.get("renewalCount"));
			model.put("nextMonthCount", nextMonth.get("renewalCount"));
			
			model.put("todaysAmount", todays.get("renewalAmount"));
			model.put("tomorrowAmount", tomorrow.get("renewalAmount"));
			model.put("nextSeventDayAmount", nextSeventDay.get("renewalAmount"));
			model.put("nextFifteentDayAmount", nextFifteentDay.get("renewalAmount"));
			model.put("nextMonthAmount", nextMonth.get("renewalAmount"));
			
			
			model.put("totalDueSoonCount", activeDueSoonCount);
			model.put("totalOverDueCount", activeOverDueCount);
			model.put("inactiveDueSoonCount", inactiveDueSoonCount);
			model.put("inactiveOverDueCount", inactiveOverDueCount);
			model.put("createApprovalPermission", createApprovalPermission);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/NotFound.in");
		}
		return new ModelAndView("ViewRenewalReminder", model);
	}
	
	@RequestMapping("/renewalReminderAjaxRevise")
	public ModelAndView renewalReminderAjaxRevise(@RequestParam("renewalId") final Long renewalId, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			model.put("accessToken", uniqueID);
			model.put("renewalId", renewalId);
			model.put("PaymentType", PaymentTypeConstant.getPaymentTypeList());
			model.put("configuration", configuration);
			model.put(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE, configuration.get(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE));
			
			
		} catch (NullPointerException e) {
			LOGGER.error("Exception : ", e);
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("RenewalReminderAjaxRevise", model);
	}
	
	@RequestMapping("/showRenewalReminderDetails")
	public ModelAndView showRenewalReminderDetails(@RequestParam("renewalId") final Long renewalId, final HttpServletRequest request) throws Exception {
		Map<String, Object> 		 											model 			= new HashMap<String, Object>();
		CustomUserDetails			 											userDetails 	= null;
		HashMap<String, Object>		 											configuration	= null;
		RenewalReminderDto 														renewalReminder = null;
		List<org.fleetopgroup.persistence.document.RenewalReminderDocument>   	documents		= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			model.put("configuration", configuration);
			
			renewalReminder = RenewalReminderService.getRenewalReminderById(renewalId, userDetails);
			model.put("renewalReminder", RRBL.GetRenewalReminder(renewalReminder));
			if(renewalReminder.getRenewal_approvedID() != null && renewalReminder.getRenewal_approvedID() > 0) {
				model.put("RenewalReminderApproval",RRBL.getApprovalDetails(RenewalReminderService
						.Get_RenewalReminderApprovalDetails(renewalReminder.getRenewal_approvedID(), userDetails.getCompany_id())));
			}
			
			
			  documents = documentService.getRenewalDocumentsByRenewalId(renewalId, userDetails.getCompany_id()); 
			  
			  model.put("documents", documents);
			 

		} catch (NullPointerException e) {
			LOGGER.error("Exception : ", e);
			return new ModelAndView("redirect:/NotFound.in");
		}
		return new ModelAndView("ShowRenewalReminderDetails", model);
	}
	
	@RequestMapping("/ViewVehicleWiseRenewalReminder")
	public ModelAndView ViewVehicleWiseRenewalReminder(@ModelAttribute("command") Vehicle renewal_vehiclename, BindingResult result) {
		Map<String, Object> 	model 			= new HashMap<String, Object>();
		VehicleDto				vehicle		  	= null;
		CustomUserDetails		userDetails		= null;
		try {
			java.util.Date currentDate = new java.util.Date();
			DateFormat 	   ft 		   = new SimpleDateFormat("YYYY-MM-dd");
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicle	= vehicleService.Get_Vehicle_Header_Details(renewal_vehiclename.getVid());
			
			if(vehicle != null) {
				model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicle));
				
				model.put("renewal", RRBL.Only_Show_ListofRenewalDto(RenewalReminderService.listVehicleRenewalReminder(
						renewal_vehiclename.getVid(), ft.format(currentDate)+DateTimeUtility.DAY_END_TIME, userDetails.getCompany_id())));
				
				
				model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(renewal_vehiclename.getVid(), userDetails.getCompany_id()));
				
				Object[] count = vehicleService.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(renewal_vehiclename.getVid());
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
			}else {
				return new ModelAndView("ViewVehicleWiseRenewalReminder", model);
			}

		} catch (Exception e) {
			LOGGER.error("ViewVehicleWiseRenewalReminder:", e);
		}
		return new ModelAndView("ViewVehicleWiseRenewalReminder", model);
	}
	
	@RequestMapping(value = "/showRenewalReminderDetailsHistory", method = RequestMethod.GET)
	public ModelAndView showRenewalReminderDetailsHistory(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,	BindingResult result) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// show the driver list in all
			model.put("vid", renewalReminderDto.getVid());
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		}
		return new ModelAndView("ShowRenewalReminderDetailsHistory", model);
	}

	@GetMapping(value = "/renewalReminderSearch")
	public ModelAndView renewalReminderSearch(@RequestParam("id") final String search,final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("search",search);
			return new ModelAndView("renewalReminderSearch", model);
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}