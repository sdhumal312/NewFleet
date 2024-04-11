package org.fleetopgroup.web.controller;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripSheetFixedExpenseType;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.DriverCommentBL;
import org.fleetopgroup.persistence.bl.DriverJobTypeBL;
import org.fleetopgroup.persistence.bl.DriverReminderBL;
import org.fleetopgroup.persistence.bl.DriverTrainingTypeBL;
import org.fleetopgroup.persistence.bl.ReportBL;
import org.fleetopgroup.persistence.bl.TripDailyBL;
import org.fleetopgroup.persistence.bl.TripSheetBL;
import org.fleetopgroup.persistence.bl.VehicleCommentBL;
import org.fleetopgroup.persistence.bl.VehicleGroupBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverCommentDto;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.dto.DriverHaltDto;
import org.fleetopgroup.persistence.dto.DriverReminderDto;
import org.fleetopgroup.persistence.dto.DriverSalaryAdvanceDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.VehicleGroup;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverHaltService;
import org.fleetopgroup.persistence.serviceImpl.IDriverJobTypeService;
import org.fleetopgroup.persistence.serviceImpl.IDriverLedgerService;
import org.fleetopgroup.persistence.serviceImpl.IDriverSalaryAdvanceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IDriverTrainingTypeService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class DriverReportController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehicleGroupService vehicleGroupService;

	@Autowired
	private IDriverService driverService;

	@Autowired
	private IDriverHaltService driverHaltService;

	@Autowired
	private ITripSheetService TripSheetService;
	TripSheetBL TSBL = new TripSheetBL();

	@Autowired
	private IDriverTrainingTypeService driverTrainingTypeService;
	DriverTrainingTypeBL TrainingDT = new DriverTrainingTypeBL();

	@Autowired
	private IDriverJobTypeService driverJobTypeService;
	DriverJobTypeBL DriverJOBDT = new DriverJobTypeBL();

	TripDailyBL TDBL = new TripDailyBL();

	@Autowired
	private IDriverSalaryAdvanceService DriverSalaryAdvanceService;

	DriverReminderBL DriverRem = new DriverReminderBL();
	DriverCommentBL DriverCom = new DriverCommentBL();
	ReportBL RBL = new ReportBL();

	VehicleGroupBL vg = new VehicleGroupBL();

	DriverBL DBL = new DriverBL();
	
	DriverJobTypeBL 		driverJobTypeBL		 		= new DriverJobTypeBL();

	TripSheetBL TripBL = new TripSheetBL();

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");

	SimpleDateFormat dateFormatonlyDate = new SimpleDateFormat("dd-MMM-yyyy");

	DecimalFormat AMOUNTFORMAT = new DecimalFormat("##,##,##0");

	@Autowired
	private IUserProfileService userProfileService;

	@Autowired
	private IDriverLedgerService driverLedgerService;
	
	@Autowired
	CompanyConfigurationService	companyConfigurationService;
	
	VehicleCommentBL VCBL = new VehicleCommentBL();

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@RequestMapping("/DR")
	public ModelAndView DriverReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("DR", model);
	}

	@RequestMapping("/DriverReport")
	public ModelAndView DriverReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		// show the Group service of the driver
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// Driver Report
			model.put("driverJobType", DriverJOBDT.DriJobTypeListofBean(
					driverJobTypeService.listDriverJobTypeByCompanyId(userDetails.getCompany_id())));
			model.put("vehiclegroup",
					vg.prepareListofDto(vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id())));
			model.put("driverTrainingType", TrainingDT.DriTrainingTypeListofBean(
					driverTrainingTypeService.listDriverTrainingTypeByCompanyId(userDetails.getCompany_id())));

		} catch (Exception e) {

		}
		return new ModelAndView("ReportDriver", model);
	}

	/* show main page of driver */
	@RequestMapping(value = "/DriverReport", method = RequestMethod.POST)
	public ModelAndView DriverReport(@ModelAttribute("command") DriverDto driverDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		// Driver Driver = RBL.prepareModelDriver(driverDto);
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String driver_id = "", Driver_jobtitle = "", Driver_group = "", Driver_active = "", Driver_dlclass = "",
					Driver_languages = "", Driver_trainings = "";

			if (driverDto.getDriver_id() != 0) {
				driver_id = " AND R.driver_id='" + driverDto.getDriver_id() + "'";
			}

			if (driverDto.getDriJobId() != null) {
				Driver_jobtitle = " AND R.driJobId=" + driverDto.getDriJobId() + "";
			}

			if (driverDto.getVehicleGroupId()  > 0) {
				Driver_group = " AND R.vehicleGroupId=" + driverDto.getVehicleGroupId() + "";
			}

			if (driverDto.getDriverStatusId() > 0) {
				Driver_active = " AND R.driverStatusId=" + driverDto.getDriverStatusId() + "";
			}

			if (driverDto.getDriver_dlclass() != "") {
				Driver_dlclass = " AND R.driver_dlclass='" + driverDto.getDriver_dlclass() + "'";
			}

			if (driverDto.getDriver_languages() != null) {
				Driver_languages = " AND lower(R.driver_languages) Like ('%" + driverDto.getDriver_languages() + "%')";
			}

			if (driverDto.getDriver_trainings() != null) {
				Driver_trainings = " AND  lower(R.driver_trainings) Like ('%" + driverDto.getDriver_trainings() + "%')";
			}
			String query = "" + Driver_jobtitle + " " + driver_id + " " + Driver_group + " " + Driver_active + " "
					+ Driver_dlclass + " " + Driver_languages + " " + Driver_trainings + " ";
			
			List<DriverDto> driverList = driverService.listDriverReport(query, userDetails);
			
			List<DriverDto> filteredDrivers = driverList.stream()
				    .collect(Collectors.toMap(DriverDto::getDriver_id, // key: driverId
				    		driver -> driver, // value: driverDto object
				    		(existing, replacement) -> existing)) // retain existing in case of duplicates
				    		.values()
				    		.stream()
				    		.collect(Collectors.toList());
				model.put("drivers", filteredDrivers);

				model.put("companyId", userDetails.getCompany_id());
				model.put("configuration", companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("Driver_Report", model);
	}
	
	@RequestMapping("/DriverDetailsReport")
	public ModelAndView OldPartReceived() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Driver_Details_Report", model);
	}

	@RequestMapping(value = "/DriverDetailsReport", method = RequestMethod.POST)
	public ModelAndView DriverDetailsReport(@ModelAttribute("command") DriverDto driverDto,
			final HttpServletRequest request) {
		Driver driver = RBL.prepareModelDriver(driverDto);

		Map<String, Object> model = new HashMap<String, Object>();
		List<DriverDto> driverlist=null;
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			try {
				String Driver_jobtitle = "", Driver_active = "", Driver_group = "", Driver_languages = "",
						Driver_trainings = "";

				if (driver.getDriJobId() != null && driver.getDriJobId() > 0) {
					Driver_jobtitle = " AND R.driJobId=" + driver.getDriJobId() + "";
				}

				if (driver.getDriverStatusId() > 0) {
					Driver_active = " AND R.driverStatusId=" + driver.getDriverStatusId() + "";
				}
				if (driver.getVehicleGroupId() > 0) {
					Driver_group = " AND R.vehicleGroupId=" + driver.getVehicleGroupId() + "";
				}

				if (driver.getDriver_languages() != null && !driver.getDriver_languages().trim().equals("")){
					Driver_languages = " AND lower(R.driver_languages) Like ('%" + driver.getDriver_languages() + "%')";
				}

				if (driver.getDriver_trainings() != null && !driver.getDriver_trainings().trim().equals("")) {
					Driver_trainings = " AND  lower(R.driver_trainings) Like ('%" + driver.getDriver_trainings()
							+ "%')";
				}
				String query = "" + Driver_jobtitle + " " + Driver_active + " " + Driver_group + " " + " "
						+ Driver_languages + " " + Driver_trainings + " ";
				
				driverlist =driverService.listDriverReport(query, userDetails);
				
				if(driverlist == null)
				{
					model.put("NotFound", true);
					return new ModelAndView("Driver_Details_Report", model);
				}
					
				model.put("drivers",driverlist );

			} catch (Exception e) {
				e.printStackTrace();
			}

			// username

			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("Driver_Details_Report", model);
	}

	@RequestMapping("/DriverCommentRemarkReport")
	public ModelAndView DriverCommentRemarkReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("DriverComment_Remark_Report", model);
	}
	
	@RequestMapping(value = "/DriverCommentRemarkReport", method = RequestMethod.POST)
	public ModelAndView DriverCommentRemarkReport(
			@ModelAttribute("command") @RequestParam("DRIVER_ID") final Integer DRIVER_ID,
			@RequestParam("TRIP_DATE_RANGE") final String TRIP_DATE_RANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<DriverCommentDto> driverCommentlist=null; //No Record Found Validation  Logic Y
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			if (TRIP_DATE_RANGE != "" && DRIVER_ID != null) {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = TRIP_DATE_RANGE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String DriverId = "", TripOpenDate_Close = "";

					if (DRIVER_ID != null && DRIVER_ID != 0) {
						DriverId = " AND R.driver_id=" + DRIVER_ID + " ";
					}

					if (dateRangeFrom != "" && dateRangeTo != "") {
						TripOpenDate_Close = "  AND R.created between '" + dateRangeFrom + " 00:00:00' AND '"
								+ dateRangeTo + " 23:59:00' ";
					}

					String query = " " + DriverId + " " + " " + TripOpenDate_Close + " ";

					List<DriverCommentDto> Collection = driverService.List_DriverComment_Remarks_Report(query,
							userDetails.getCompany_id());
					
					driverCommentlist= DriverCom.prepare_RemarksDriverComment_Report(Collection);
					//No Record Found Validation  Logic Start Y
					if(driverCommentlist == null) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("DriverComment_Remark_Report", model);
					}
					//No Record Found Validation  Logic End  Y
					//model.put("Comment", DriverCom.prepare_RemarksDriverComment_Report(Collection));   //Original Code Before NRF Validation
					model.put("Comment",driverCommentlist);
					model.put("SEARCHDATE", TRIP_DATE_RANGE);
				} catch (Exception e) {
					e.printStackTrace();
				}

				Authentication authALL = SecurityContextHolder.getContext().getAuthentication();
				String nameALL = authALL.getName();
				
				
				
				model.put("company", userProfileService.findUserProfileByUser_email(nameALL));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("DriverComment_Remark_Report", model);
	}
	
	@RequestMapping("/DriverDLEDReport")
	public ModelAndView DriverDLEDReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Driver_DLEXPIRY_Date_Report", model);
	}

	@RequestMapping(value = "/DriverDLEDReport", method = RequestMethod.POST)
	public ModelAndView DriverDLEXPIRYDateRangeReport(
			@ModelAttribute("command") @RequestParam("TRIP_DATE_RANGE") final String TRIP_DATE_RANGE,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<DriverReminderDto> driverReminderlist=null;  //No Record Found Validation  Logic Y
		try {

			if (TRIP_DATE_RANGE != "") {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = TRIP_DATE_RANGE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
							.getAuthentication().getPrincipal();
					List<DriverReminderDto> Collection = driverService.DRIVER_RENEWALREMINDER_DL_REPORT(dateRangeFrom,
							dateRangeTo, userDetails);
					driverReminderlist=DriverRem.prepareListDriverReminderBean(Collection); //No Record Found Validation Y
					//No Record Found Validation  Logic Start Y
					if(driverReminderlist == null) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Driver_DLEXPIRY_Date_Report", model);
					}
					//No Record Found Validation  Logic End  Y
					model.put("driverReminder", driverReminderlist);
					
					//model.put("driverReminder", DriverRem.prepareListDriverReminderBean(Collection));  //Original Code Y

					model.put("SEARCHDATE", TRIP_DATE_RANGE);
				} catch (Exception e) {
					e.printStackTrace();
				}

				Authentication authALL = SecurityContextHolder.getContext().getAuthentication();
				String nameALL = authALL.getName();

				model.put("company", userProfileService.findUserProfileByUser_email(nameALL));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("Driver_DLEXPIRY_Date_Report", model);
	}
	@RequestMapping("/DriverlocalHaltReport")
	public ModelAndView DriverlocalHaltReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Driver_Local_Halt_Report", model);
	}

	@RequestMapping(value = "/DriverlocalHaltReport", method = RequestMethod.POST)
	public ModelAndView DriverlocalHaltReport(@RequestParam("DRIVER_ID") final Integer DRIVER_ID,
			@RequestParam("HALT_GROUP_ID") final long HALT_GROUP_ID,
			@RequestParam("HALT_TITLE") final Integer HALT_TITLE,
			@RequestParam("HALT_DATE_RANGE") final String HALT_DATE_RANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			if (HALT_DATE_RANGE != "" && HALT_DATE_RANGE != null) {
				String dateRangeFrom = "", dateRangeTo = "", Driver = "", Group = "", JobTitle = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = HALT_DATE_RANGE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("Halt Driver Date Range:", e);
				}

				try {

					model.put("SearchDate", HALT_DATE_RANGE);

					if (DRIVER_ID != null && DRIVER_ID != 0) {

						Driver = "AND H.DRIVERID=" + DRIVER_ID + "   ";

					}

					if (Long.valueOf(HALT_GROUP_ID) != null && HALT_GROUP_ID != 0) {

						Group = " AND DH.vehicleGroupId=" + HALT_GROUP_ID + "  ";
					}
					if (HALT_TITLE != null && HALT_TITLE > 0) {

						JobTitle = " AND DH.driJobId=" + HALT_TITLE + " ";
					}

					String query = " ( (H.HALT_DATE_TO between '" + dateRangeFrom + "' AND '" + dateRangeTo
							+ "')  AND H.TRIPSHEETID=0 " + Driver + " " + Group + " " + JobTitle + " "
							+ " ) AND H.COMPANY_ID = " + userDetails.getCompany_id() + " AND H.markForDelete = 0";

					List<DriverHaltDto> HaltCollection = driverHaltService.Report_OF_Driver_Local_Halt(query);
					
					if((HaltCollection == null) || (HaltCollection.isEmpty())) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Driver_Local_Halt_Report", model);
					}
					
					
					model.put("DriverHalt", (HaltCollection));

					Double TotalPoint = DBL.perpare_Total_DriverHalt_DATE_Halt(HaltCollection);
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					String TotalIncomeShow = AMOUNTFORMAT.format(TotalPoint);
					model.put("HALT_AMOUNT", TotalIncomeShow);

				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("Driver_Local_Halt_Report", model);
	}

	@RequestMapping(value = "/getDriver1ListOfCompany", method = RequestMethod.POST)
	public void getDriver1List(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<DriverDto> Driver = new ArrayList<DriverDto>();
		List<Driver> driverName = driverService.getDriverDetailsNotInSuspend(term, userDetails);
		if (driverName != null && !driverName.isEmpty()) {
			for (Driver add : driverName) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				wadd.setDriver_firstname(add.getDriver_firstname());
				wadd.setDriver_Lastname(add.getDriver_Lastname());
				wadd.setDriver_fathername(add.getDriver_fathername());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();

		// System.out.println("jsonStudents = " + gson.toJson(Driver));

		response.getWriter().write(gson.toJson(Driver));
	}

	/*SRS Travels TripSheet HaltBata Report By Dev Yogi Start */
	@RequestMapping("/HaltDateRangeReport")
	public ModelAndView HaltDateRangeReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Tripsheet_HaltBata_Report", model);
	}
	/*SRS Travels TripSheet HaltBata Report By Dev Yogi End  */
	
	@RequestMapping(value = "/HaltDateRangeReport", method = RequestMethod.POST)
	public ModelAndView HaltDateRangeReport(@RequestParam("HALT_DATE_RANGE") final String HALT_DATE_RANGE,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {

			if (HALT_DATE_RANGE != "" && HALT_DATE_RANGE != null) {
				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = HALT_DATE_RANGE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("Halt Driver Date Range:", e);
				}

				try {

					model.put("SearchDate", HALT_DATE_RANGE);

					String TripOpenDate_Close = "";

					TripOpenDate_Close = "(TA.expenseId=" + (int) TripSheetFixedExpenseType.EXPENSE_TYPE_HALT_BATA
							+ " AND ( TA .created between '" + dateRangeFrom + " 01:00:00' AND '" + dateRangeTo
							+ " 23:59:59')) ";

					String query = " " + TripOpenDate_Close + " ";

					List<TripSheetDto> AdvanceCollection = TripSheetService.list_Report_TripSheet_HALT_AMOUNT(query,
							userDetails);

					if((AdvanceCollection == null) || (AdvanceCollection.isEmpty())) 
					{
						model.put("NotFound", true); 
					}
					
					model.put("TripSheet", TSBL.prepare_Tripsheet_expense_Date_HaltBata_details(AdvanceCollection));

					Double TripIncomeTotal = TSBL.perpare_ExpanceTripTotal(AdvanceCollection);
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					String TotalIncomeShow = AMOUNTFORMAT.format(TripIncomeTotal);
					model.put("AdvanceExpance", TotalIncomeShow);

				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Tripsheet_HaltBata_Report", model);
	}

	@RequestMapping("/DepotWisePenaltyReport")
	public ModelAndView DepotWisePenaltyReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Depot_Wise_Penalty_Report", model);
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/DepotWisePenaltyReport", method = RequestMethod.POST)
	public ModelAndView DepotWisePenaltyReport(
			@ModelAttribute("command") @RequestParam("vehicleGroupId") final long vehicleGroupId,
			@RequestParam("TRIP_DATE_RANGE") final String dateRange, final HttpServletRequest request) {

		Map<String, Object> 			model 						= null;
		CustomUserDetails 				userDetails 				= null;
		java.sql.Date 					dateRangeFrom 				= null;
		java.sql.Date 					dateRangeTo 				= null;
		String[] 						From_TO_DateRange 			= null;
		java.util.Date 					date 						= null;
		List<DriverSalaryAdvanceDto>	driverSalaryAdvanceList		= null;
		DriverSalaryAdvanceDto 			driverSalaryAdvance			= null;
		String 							TRIP_ROUTE_NAME 			= "";
		
		try {
			
			model 				= new HashMap<String, Object>();
			List<DriverSalaryAdvanceDto> depoWisePenaltylist=null;    //No Record Found Y
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (dateRange != "" && dateRange != null) {

				From_TO_DateRange = dateRange.split(" to ");

				date = dateFormat.parse(From_TO_DateRange[0]);
				dateRangeFrom = new Date(date.getTime());

				date = dateFormat.parse(From_TO_DateRange[1]);
				dateRangeTo = new Date(date.getTime());

				model.put("SEARCHDATE", dateRange);

				if (dateRangeFrom != null && dateRangeTo != null) {

					List<String> dateSearch = new ArrayList<String>();
					Calendar calender = new GregorianCalendar();
					calender.setTime(dateRangeFrom);

					String TDINCOME_NAME = "";
					String TDINCOME_COLUMN = "";
					while (calender.getTime().getTime() <= dateRangeTo.getTime()) {

						java.util.Date result = calender.getTime();

						dateSearch.add(dateFormatSQL.format(result));
						TDINCOME_NAME += "<th>" + dateFormat.format(result) + "</th>";

						calender.add(Calendar.DATE, 1);

						// System.out.println(dateFormatSQL.format(result));
					}

					model.put("TDINCOME_NAME", TDINCOME_NAME);

					List<Object[]> FixedDriverPenalty = DriverSalaryAdvanceService.List_OF_DEPOT_WISE_PENALTY_DATE_REPORT(vehicleGroupId, dateSearch, userDetails.getCompany_id());

					if (FixedDriverPenalty != null && !FixedDriverPenalty.isEmpty()) {
						driverSalaryAdvanceList = new ArrayList<DriverSalaryAdvanceDto>();

						for (Object[] result : FixedDriverPenalty) {
							driverSalaryAdvance = new DriverSalaryAdvanceDto();

							driverSalaryAdvance.setDRIVER_ID((Integer) result[0]);
							driverSalaryAdvance.setDriver_empnumber((String) result[1]);

							driverSalaryAdvance.setDriver_firstname((String) result[2]);
							if(result[3] != null &&  !((String) result[3]).trim().equals(""))
							driverSalaryAdvance.setDriverFatherName("- " + result[3]);
							driverSalaryAdvance.setDriver_Lastname((String) result[4]);
							String COLUMN_TIP_VALUE = "";
							Double ColumDoubleTotal = 0.0;
							short ColumnResult = 5;
							for (String DateOrder : dateSearch) {

								COLUMN_TIP_VALUE += "<td>" + (Double) result[ColumnResult] + "</td>";
								ColumDoubleTotal += (Double) result[ColumnResult];
								ColumnResult++;
							}

							driverSalaryAdvance.setCREATEDBY(COLUMN_TIP_VALUE);
							driverSalaryAdvance.setADVANCE_AMOUNT(ColumDoubleTotal);

							ColumnResult = 4;
							ColumDoubleTotal = 0.0;
							if(driverSalaryAdvance.getADVANCE_AMOUNT() > 0)
								driverSalaryAdvanceList.add(driverSalaryAdvance);
						}

					}

					depoWisePenaltylist =driverSalaryAdvanceList ;//No Record Found Validation Y
					
					//No Record Found Validation  Logic Start Y
					if(depoWisePenaltylist == null || depoWisePenaltylist.isEmpty() )
					{
						model.put("NotFound", true);
						return new ModelAndView("Depot_Wise_Penalty_Report", model);
					}
					//No Record Found Validation  Logic End  Y
					model.put("DSAdvance", depoWisePenaltylist);
					
					
					//model.put("DSAdvance", driverSalaryAdvanceList);   //Original Code

					model.put("VEHICLE_NAME", TRIP_ROUTE_NAME);

					Double totalPenalty = DBL.Total_DriverSalary_Advance_Report(driverSalaryAdvanceList);
					if (totalPenalty != null) {
						String TOTAL_PENALTY = AMOUNTFORMAT.format(totalPenalty);
						model.put("TOTAL_PENALTY", TOTAL_PENALTY);

					}

					List<Object[]> FixedTypeSheetColumTOTAL = DriverSalaryAdvanceService
							.List_OF_DEPOT_WISE_PENALTY_COLUMN_Report(vehicleGroupId, dateSearch,
									userDetails.getCompany_id());

					if (FixedTypeSheetColumTOTAL != null && !FixedTypeSheetColumTOTAL.isEmpty()) {

						for (Object[] result_column : FixedTypeSheetColumTOTAL) {
							short ColumnResult = 1;
							for (String DateOrder : dateSearch) {
								TDINCOME_COLUMN += "<td>" + (Double) result_column[ColumnResult] + "</td>";
								ColumnResult++;
							}

						}
					}
					model.put("TDINCOME_COLUMN", TDINCOME_COLUMN);

				}



				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Depot_Wise_Penalty_Report", model);
	}

	@RequestMapping("/DepotDriConPenaltyReport")
	public ModelAndView DepotDriConPenaltyReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("DriCon_Wise_Penalty_Report", model);
	}

	@RequestMapping(value = "/DepotDriConPenaltyReport", method = RequestMethod.POST)
	public ModelAndView DepotDriConPenaltyReport(
			@ModelAttribute("command") @RequestParam("DRIVER_ID") final Integer DRIVER_ID,
			@RequestParam("vehicleGroupId") final long vehicleGroupId,
			@RequestParam("TRIP_DATE_RANGE") final String TRIP_DATERANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<DriverSalaryAdvanceDto> driverConductorWisePenaltyList=null; //No Record Found Validation  Logic Y
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			if (TRIP_DATERANGE != "" && TRIP_DATERANGE != null) {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = TRIP_DATERANGE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

					model.put("SEARCHDATE", TRIP_DATERANGE);

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					if (dateRangeFrom != null && dateRangeTo != null) {

						List<Object[]> FixedDriverPenalty = DriverSalaryAdvanceService
								.LIST_OF_INDIDUAL_WISE_PENALTY_DATE_REPORT(DRIVER_ID, dateRangeFrom, dateRangeTo,
										userDetails.getCompany_id());

						List<DriverSalaryAdvanceDto> Dtos = null;

						if (FixedDriverPenalty != null && !FixedDriverPenalty.isEmpty()) {
							Dtos = new ArrayList<DriverSalaryAdvanceDto>();
							DriverSalaryAdvanceDto list = null;

							for (Object[] result : FixedDriverPenalty) {
								list = new DriverSalaryAdvanceDto();

								list.setDRIVER_ID((Integer) result[0]);
								list.setDriver_empnumber((String) result[1]);

								list.setDriver_firstname((String) result[2]);
								list.setDriver_Lastname((String) result[3]);

								list.setTRIPDAILYID((Long) result[4]);
								list.setTRIP_ROUTE_NAME((String) result[5]);

								if (result[6] != null) {
									list.setADVANCE_DATE(dateFormat.format((java.util.Date) result[6]));
								}
								list.setADVANCE_AMOUNT((Double) result[7]);

								list.setADVANCE_REMARK((String) result[8]);
								if(result[9] != null && !((String) result[9]).trim().equals(""))
								list.setDriver_Lastname(list.getDriver_Lastname()+" - "+result[9]);
								Dtos.add(list);
							}

						}
						driverConductorWisePenaltyList=Dtos ;
						
						//No Record Found Validation  Logic Start Y
						if(driverConductorWisePenaltyList == null) 
						{
							model.put("NotFound", true); 
							return new ModelAndView("DriCon_Wise_Penalty_Report", model);
						}
						//No Record Found Validation  Logic End  Y
						model.put("DSAdvance", driverConductorWisePenaltyList);					
						
						//model.put("DSAdvance", Dtos);   //Original Code

						Double totalPenalty = DBL.Total_DriverSalary_Advance_Report(Dtos);
						if (totalPenalty != null) {
							String TOTAL_PENALTY = AMOUNTFORMAT.format(totalPenalty);
							model.put("TOTAL_PENALTY", TOTAL_PENALTY);

						}

					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("DriCon_Wise_Penalty_Report", model);
	}

	@RequestMapping("/DepotWiseAdvanceReport")
	public ModelAndView DepotWiseAdvanceReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Depot_Wise_advance_Report", model);
	}
	@SuppressWarnings("unused")
	@RequestMapping(value = "/DepotWiseAdvanceReport", method = RequestMethod.POST)
	public ModelAndView DepotWiseAdvanceReport(
			@ModelAttribute("command") @RequestParam("vehicleGroupId") final long vehicleGroupId,
			@RequestParam("TRIP_DATE_RANGE") final String TRIP_DATERANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<DriverSalaryAdvanceDto> depotWiseAdvanceList=null; //No Record Found Validation  Logic Y
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			if (TRIP_DATERANGE != "" && TRIP_DATERANGE != null) {

				java.sql.Date dateRangeFrom = null, dateRangeTo = null;

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = TRIP_DATERANGE.split(" to ");

					try {

						java.util.Date date = dateFormat.parse(From_TO_DateRange[0]);
						dateRangeFrom = new Date(date.getTime());

						java.util.Date dateTo = dateFormat.parse(From_TO_DateRange[1]);
						dateRangeTo = new Date(dateTo.getTime());
					} catch (Exception e) {
						LOGGER.error("Fuel Page:", e);

					}

					model.put("SEARCHDATE", TRIP_DATERANGE);

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {

					if (dateRangeFrom != null && dateRangeTo != null) {

						List<String> dateSearch = new ArrayList<String>();
						Calendar calender = new GregorianCalendar();
						calender.setTime(dateRangeFrom);

						String TDINCOME_NAME = "";
						String TDINCOME_COLUMN = "";
						while (calender.getTime().getTime() <= dateRangeTo.getTime()) {

							java.util.Date result = calender.getTime();

							dateSearch.add(dateFormatSQL.format(result));
							TDINCOME_NAME += "<th>" + dateFormat.format(result) + "</th>";

							calender.add(Calendar.DATE, 1);

						}

						model.put("TDINCOME_NAME", TDINCOME_NAME);

						List<Object[]> FixedDriverPenalty = DriverSalaryAdvanceService
								.List_OF_DEPOT_WISE_ADVANCE_DATE_REPORT(vehicleGroupId, dateSearch,
										userDetails.getCompany_id());

						List<DriverSalaryAdvanceDto> Dtos = null;

						String TRIP_ROUTE_NAME = "";
						if (FixedDriverPenalty != null && !FixedDriverPenalty.isEmpty()) {
							Dtos = new ArrayList<DriverSalaryAdvanceDto>();
							DriverSalaryAdvanceDto list = null;

							for (Object[] result : FixedDriverPenalty) {
								list = new DriverSalaryAdvanceDto();
								list.setDRIVER_ID((Integer) result[0]);
								list.setDriver_empnumber((String) result[1]);
								list.setDriver_firstname((String) result[2]);
								list.setDriver_Lastname((String) result[3]);
								if(result[4] != null && !((String) result[4]).trim().equals(""))
								list.setDriver_Lastname(list.getDriver_Lastname()+" - "+result[4]);
								
								String COLUMN_TIP_VALUE = "";
								Double ColumDoubleTotal = 0.0;
								short ColumnResult = 5;
								for (String DateOrder : dateSearch) {
									if ((Double) result[ColumnResult] != 0.0)
										COLUMN_TIP_VALUE += "<td>" + (Double) result[ColumnResult] + "</td>";
									else
										COLUMN_TIP_VALUE += "<td> </td>";
									ColumDoubleTotal += (Double) result[ColumnResult];
									ColumnResult++;
								}

								list.setCREATEDBY(COLUMN_TIP_VALUE);
								list.setADVANCE_AMOUNT(ColumDoubleTotal);

								ColumnResult = 4;
								ColumDoubleTotal = 0.0;
								if(list.getADVANCE_AMOUNT() > 0)
									Dtos.add(list);
							}

						}
						depotWiseAdvanceList=Dtos;
						
						//No Record Found Validation  Logic Start Y
						if(depotWiseAdvanceList == null) 
						{
							model.put("NotFound", true); 
							return new ModelAndView("Depot_Wise_advance_Report", model);
						}
						//No Record Found Validation  Logic End  Y
						model.put("DSAdvance", depotWiseAdvanceList);
						
						//model.put("DSAdvance", Dtos); //Original Code Before No Record Found Validation
						
						if(Dtos == null || Dtos.isEmpty())
							model.put("noRecordFound", true);
						model.put("VEHICLE_NAME", TRIP_ROUTE_NAME);

						Double totalPenalty = DBL.Total_DriverSalary_Advance_Report(Dtos);
						if (totalPenalty != null) {
							String TOTAL_PENALTY = AMOUNTFORMAT.format(totalPenalty);
							model.put("TOTAL_PENALTY", TOTAL_PENALTY);

						}

						List<Object[]> FixedTypeSheetColumTOTAL = DriverSalaryAdvanceService
								.List_OF_DEPOT_WISE_ADVANCE_COLUMN_Report(vehicleGroupId, dateSearch,
										userDetails.getCompany_id());

						if (FixedTypeSheetColumTOTAL != null && !FixedTypeSheetColumTOTAL.isEmpty()) {

							for (Object[] result_column : FixedTypeSheetColumTOTAL) {
								short ColumnResult = 1;
								for (String DateOrder : dateSearch) {
									TDINCOME_COLUMN += "<td>" + (Double) result_column[ColumnResult] + "</td>";
									ColumnResult++;
								}

							}
						}
						model.put("TDINCOME_COLUMN", TDINCOME_COLUMN);

					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Depot_Wise_advance_Report", model);
	}
	@RequestMapping("/DepotDriConAdvanceReport")
	public ModelAndView DepotDriConAdvanceReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("DriCon_Wise_advance_Report", model);
	}

	@RequestMapping(value = "/DepotDriConAdvanceReport", method = RequestMethod.POST)
	public ModelAndView DepotDriConAdvanceReport(
			@ModelAttribute("command") @RequestParam("vehicleGroupId8") final Integer vehicleGroupId8, @RequestParam("DRIVER_ID") final Integer DRIVER_ID,
			@RequestParam("TRIP_DATE_RANGE") final String TRIP_DATERANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<DriverSalaryAdvanceDto> driverConductorWiseAdvanceList=null; //No Record Found Validation  Logic Y
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {
			if (TRIP_DATERANGE != "" && TRIP_DATERANGE != null) {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = TRIP_DATERANGE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


					model.put("SEARCHDATE", TRIP_DATERANGE);

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {

					if (dateRangeFrom != null && dateRangeTo != null) {

						 String query="" , Vehicle_group="" , dId="";
						
						
						if (vehicleGroupId8 != 0) {
							Vehicle_group = " AND p.vehicleGroupId=" + vehicleGroupId8 + " ";
						}

						if (DRIVER_ID > 0) {

							dId = " AND R.DRIVER_ID=" + DRIVER_ID + " ";
						}


						query = " " + Vehicle_group + " " + dId + " ";
								
						
						List<Object[]> FixedDriverPenalty = DriverSalaryAdvanceService.LIST_OF_INDIDUAL_WISE_ADVANCE_DATE_REPORT(query, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());

						List<DriverSalaryAdvanceDto> Dtos = null;

						if (FixedDriverPenalty != null && !FixedDriverPenalty.isEmpty()) {
							Dtos = new ArrayList<DriverSalaryAdvanceDto>();
							DriverSalaryAdvanceDto list = null;

							for (Object[] result : FixedDriverPenalty) {
								list = new DriverSalaryAdvanceDto();

								list.setDRIVER_ID((Integer) result[0]);
								list.setDriver_empnumber((String) result[1]);

								list.setDriver_firstname((String) result[2]);
								list.setDriver_Lastname((String) result[3]);

								list.setTRIPDAILYID((Long) result[4]);
								list.setTRIP_ROUTE_NAME((String) result[5]);

								if (result[6] != null) {
									list.setADVANCE_DATE(dateFormat.format((java.util.Date) result[6]));
								}
								list.setADVANCE_AMOUNT((Double) result[7]);
								list.setADVANCE_REMARK((String) result[8]);
								list.setTRIPDAILYNUMBER((Long) result[9]);
								
								if(result[10] != null && !((String)result[10]).trim().equals(""))
								list.setDriver_Lastname(list.getDriver_Lastname()+" - "+result[10]);
								
								Dtos.add(list);
							}

						}
										
						driverConductorWiseAdvanceList=Dtos;
						//No Record Found Validation  Logic Start Y
						if(driverConductorWiseAdvanceList == null) 
						{
							model.put("NotFound", true); 
							return new ModelAndView("DriCon_Wise_advance_Report", model);
						}
						//No Record Found Validation  Logic End  Y	
											
						model.put("DSAdvance", driverConductorWiseAdvanceList);
						//model.put("DSAdvance", Dtos);   //Original Code Before No Record Found Validation

						Double totalPenalty = DBL.Total_DriverSalary_Advance_Report(Dtos);
						if (totalPenalty != null) {
							String TOTAL_PENALTY = AMOUNTFORMAT.format(totalPenalty);
							model.put("TOTAL_PENALTY", TOTAL_PENALTY);

						}

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
					
				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("DriCon_Wise_advance_Report", model);
		
	}
	
	@RequestMapping("/DepotAdvancePenaltyReport")
	public ModelAndView DepotAdvancePenaltyReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Driver_Advance_Penalty_Report", model);
	}

	@RequestMapping(value = "/DepotAdvancePenaltyReport", method = RequestMethod.POST)
	public ModelAndView DepotAdvancePenaltyReport(
			@ModelAttribute("command") @RequestParam("vehicleGroupId") final long vehicleGroupId,
			@RequestParam("ADVANCE_TYPE_ID") final short ADVANCE_TYPE,
			@RequestParam("TRIP_DATE_RANGE") final String TRIP_DATE_RANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<DriverSalaryAdvanceDto> advancePenaltyDepotWiseReportList=null; //No Record Found Validation  Logic Y
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null && vehicleGroupId != 0) {
				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = TRIP_DATE_RANGE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("Halt Driver Date Range:", e);
				}

				try {

					model.put("SearchDate", TRIP_DATE_RANGE.replace(" ", "_"));

					String Vehicle_group = "", DRIVER_GROUP_Job = "";

					if (vehicleGroupId != 0) {
						Vehicle_group = " AND D.vehicleGroupId=" + vehicleGroupId + " ";
						VehicleGroup vehicleGroup = vehicleGroupService.getVehicleGroupByID(vehicleGroupId);
						model.put("SearchGroup", vehicleGroup.getvGroup());

					}

					if (ADVANCE_TYPE > 0) {

						DRIVER_GROUP_Job = " AND R.ADVANCE_NAME_ID=" + ADVANCE_TYPE + " ";
					}

					if (dateRangeFrom != "" && dateRangeTo != "") {

						String query = " " + DRIVER_GROUP_Job + " " + Vehicle_group + " ";

						List<DriverSalaryAdvanceDto> Salary = DriverSalaryAdvanceService
								.Driver_Advance_Penalty_ReportDate(query, dateRangeFrom, dateRangeTo,
										userDetails.getCompany_id());		
						
						
						advancePenaltyDepotWiseReportList=Salary ;
						//No Record Found Validation  Logic Start Y
						if(advancePenaltyDepotWiseReportList == null) 
						{
							model.put("NotFound", true); 
							return new ModelAndView("Driver_Advance_Penalty_Report", model);
						}
						//No Record Found Validation  Logic End  Y					
						
						model.put("DriverAdvanvce", advancePenaltyDepotWiseReportList);
						//model.put("DriverAdvanvce", Salary);  //Original Code Before No Record Found Validation

						Double[] totalGroupSheet = TDBL.Total_Driver_Advance_Penalty_Report(Salary);
						if (totalGroupSheet != null) {
							String TotalAmount = AMOUNTFORMAT.format(totalGroupSheet[0]);
							model.put("TotalAmount", TotalAmount);

							String TotalBalance = AMOUNTFORMAT.format(totalGroupSheet[1]);
							model.put("TotalBalance", TotalBalance);
						}

					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company",
						userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Driver_Advance_Penalty_Report", model);
	}
	
	@RequestMapping("/DriverEngagementAndPerformanceReport")
	public ModelAndView DriverEngagementAndPerformanceReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("DriverEngagementAndPerformance_Report", model);
	}
	
	@RequestMapping("/DriverBasicDetailsReport")
	public ModelAndView DriverBasicDetailsReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("driverJobType", driverJobTypeBL.DriJobTypeListofBean(driverJobTypeService.listDriverJobTypeByCompanyId(userDetails.getCompany_id())));
		} catch (Exception e) {
		}
		return new ModelAndView("DriverBasicDetailsReport", model);
	}
	@RequestMapping("/DriverLedgerReport")
	public ModelAndView DriverLedgerReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("DriverLedgerReport", model);
	}
	@RequestMapping("/DriverSalary_Report")
	public ModelAndView DriverSalaryReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("DriverSalaryReport", model);
	}
}
