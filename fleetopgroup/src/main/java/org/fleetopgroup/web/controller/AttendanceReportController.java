package org.fleetopgroup.web.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.DriverAttandancePointType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverAttendanceDto;
import org.fleetopgroup.persistence.dto.DriverSalaryDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.model.TripExpense;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverAdvanceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverSalaryService;
import org.fleetopgroup.persistence.serviceImpl.ITripExpenseService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AttendanceReportController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IDriverAdvanceService driverAdvanceService;
	
	@Autowired
	private IUserProfileService userProfileService;
	
	@Autowired	ITripSheetService	tripSheetService;
	
	@Autowired
	private IDriverSalaryService driverSalaryService;
	
	
	@Autowired
	private IVehicleGroupService	vehicleGroupService;

	@Autowired ITripExpenseService       tripExpenseService;
	
	@Autowired
	CompanyConfigurationService	companyConfigurationService;
	
	DriverBL DBL = new DriverBL();
	DecimalFormat AMOUNTFORMAT = new DecimalFormat("##,##,##0");

	@RequestMapping("/AR")
	public ModelAndView AttendanceReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("AR", model);
	}

	
	@RequestMapping("/DriverAttReport")
	public ModelAndView DriverAttReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Driver_Attendance_Report", model);
	}
	@RequestMapping(value = "/DriverAttReport", method = RequestMethod.POST)
	public ModelAndView DriverAttReport(@ModelAttribute("command") @RequestParam("DRIVER_ID") final Integer DRIVER_ID,
			@RequestParam("HALT_DATE_RANGE") final String HALT_DATE_RANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {

			if (HALT_DATE_RANGE != "" && HALT_DATE_RANGE != null && DRIVER_ID != null) {
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

					List<DriverAttendanceDto> AdvanceCollection = driverAdvanceService
							.Report_OF_Driver_Attendance(DRIVER_ID, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					
					if((AdvanceCollection == null) || (AdvanceCollection.isEmpty())) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Driver_Attendance_Report", model);
					}
					
					
					model.put("DA", prepare_DriverAttendance_Report(AdvanceCollection));

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
		return new ModelAndView("Driver_Attendance_Report", model);
	}

	public Object prepare_DriverAttendance_to_Report(List<DriverAttendanceDto> TRIPSHEETPOINT) throws Exception {
		SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");
		List<DriverAttendanceDto> Dtos = null;

		if (TRIPSHEETPOINT != null && !TRIPSHEETPOINT.isEmpty()) {
			Dtos = new ArrayList<DriverAttendanceDto>();
			DriverAttendanceDto DA = null;
			for (DriverAttendanceDto ATTENDANCE : TRIPSHEETPOINT) {
				DA = new DriverAttendanceDto();

				DA.setDRIVERID(ATTENDANCE.getDRIVERID());
				DA.setDRIVER_NAME(ATTENDANCE.getDRIVER_NAME());
				if (ATTENDANCE.getATTENDANCE_DATE() != null) {
					DA.setATTENDANCE_DATE(dateFormat_Name.format(ATTENDANCE.getATTENDANCE_DATE()));
				}
				// driver Group Service
				DA.setDRIVER_GROUP(ATTENDANCE.getCREATEDBY());
				DA.setTRIPSHEETID(ATTENDANCE.getTRIPSHEETID());
				if(ATTENDANCE.getTRIPSHEETID() != null && ATTENDANCE.getTRIPSHEETID() > 0) {
					TripSheet	tripSheet	= tripSheetService.getTripSheet(ATTENDANCE.getTRIPSHEETID());
					if(tripSheet != null) {
						DA.setTRIPSHEETNUMBER(tripSheet.getTripSheetNumber());
					}
				}
				if (ATTENDANCE.getCREATED() != null) {
					// tripOpenDate is only display propose show CREATED Halt
					DA.setTRIP_OPEN_DATE(dateFormat_Name.format(ATTENDANCE.getCREATED()));
				}
				if (ATTENDANCE.getLASTUPDATED() != null) {
					// tripCloseDate is only display propose show LASTUPDATED
					// Halt
					DA.setTRIP_CLOSE_DATE(dateFormat_Name.format(ATTENDANCE.getLASTUPDATED()));
				}

				DA.setTRIP_ROUTE_NAME(ATTENDANCE.getTRIP_ROUTE_NAME());
				// vehicle_registration is only display propose TRIP_ROUTE_NAME
				// Halt
				DA.setTRIP_VEHICLE_NAME(ATTENDANCE.getPOINT_STATUS());

				DA.setDRIVER_POINT(ATTENDANCE.getDRIVER_POINT());
				DA.setPOINT_TYPE(ATTENDANCE.getPOINT_TYPE());

				Dtos.add(DA);
			}
		}

		return Dtos;
	}

	public Object prepare_DriverAttendance_Report(List<DriverAttendanceDto> TRIPSHEETPOINT) throws Exception {
		SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");
		List<DriverAttendanceDto> Dtos = null;

		if (TRIPSHEETPOINT != null && !TRIPSHEETPOINT.isEmpty()) {
			Dtos = new ArrayList<DriverAttendanceDto>();
			DriverAttendanceDto DA = null;
			for (DriverAttendanceDto ATTENDANCE : TRIPSHEETPOINT) {
				DA = new DriverAttendanceDto();

				DA.setDRIVERID(ATTENDANCE.getDRIVERID());
				DA.setDRIVER_NAME(ATTENDANCE.getDRIVER_NAME());
				if (ATTENDANCE.getD_ATTENDANCE_DATE() != null) {
					DA.setATTENDANCE_DATE(dateFormat_Name.format(ATTENDANCE.getD_ATTENDANCE_DATE()));
				}
				// driver Group Service
				DA.setDRIVER_GROUP(ATTENDANCE.getCREATEDBY());
				DA.setTRIPSHEETID(ATTENDANCE.getTRIPSHEETID());
				DA.setTRIPSHEETNUMBER(ATTENDANCE.getTRIPSHEETNUMBER());
				
				if (ATTENDANCE.getCREATED() != null) {
					// tripOpenDate is only display propose show CREATED Halt
					DA.setTRIP_OPEN_DATE(dateFormat_Name.format(ATTENDANCE.getCREATED()));
				}
				if (ATTENDANCE.getLASTUPDATED() != null) {
					// tripCloseDate is only display propose show LASTUPDATED
					// Halt
					DA.setTRIP_CLOSE_DATE(dateFormat_Name.format(ATTENDANCE.getLASTUPDATED()));
				}

				DA.setTRIP_ROUTE_NAME(ATTENDANCE.getTRIP_ROUTE_NAME());
				// vehicle_registration is only display propose TRIP_ROUTE_NAME
				// Halt
				DA.setTRIP_VEHICLE_NAME(ATTENDANCE.getPOINT_STATUS());

				DA.setDRIVER_POINT(ATTENDANCE.getDRIVER_POINT());
				DA.setPOINT_TYPE_ID(ATTENDANCE.getPOINT_TYPE_ID());
				DA.setPOINT_TYPE(DriverAttandancePointType.getPointTypeName(ATTENDANCE.getPOINT_TYPE_ID()));
				DA.setTripSheetFlavor(ATTENDANCE.getTripSheetFlavor());

				Dtos.add(DA);
			}
		}

		return Dtos;
	}

	/*SRS Travels Group Wise Driver Attendance Report By Dev Yogi Start*/
	@RequestMapping("/GroupDriverAttReport")
	public ModelAndView GroupDriverAttReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Group_Driver_Attendance_Report", model);
	}	
	/*SRS Travels Group Wise Driver Attendance Report By Dev Yogi End*/
	
	@RequestMapping(value = "/GroupDriverAttReport", method = RequestMethod.POST)
	public ModelAndView GroupDriverAttReport(@ModelAttribute("command") @RequestParam("DRIVER_GROUP_ID") final long driver_Group_Id,
			@RequestParam("DRIVER_JOBTITLE") final Integer driver_Jobtitle_Id,
			@RequestParam("HALT_DATE_RANGE") final String trip_Date_Range, final HttpServletRequest request) {

		Map<String, Object> 				model 							= null;
		CustomUserDetails					userDetails						= null;
		HashMap<Integer, TripSheetDto>		driverWiseTripSheetHM			= null;
		List<DriverAttendanceDto>	 		driverAttendanceList			= null; 
		ValueObject							dateRange						= null;
		String 								dateRangeFrom 					= "";
		String 								dateRangeTo 					= "";
		String 								driverGroupQuery 				= "";
		List<DriverAttendanceDto>	 		driverAttendanceList1			= null; 
		TripExpense							tripExpense                     = null;
		HashMap<String, Object> 	        configuration				    = null;
		
		try {
			model 			= new HashMap<String, Object>();
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			
			if (trip_Date_Range != "" && trip_Date_Range != null && driver_Group_Id != 0) {

				dateRange		= DateTimeUtility.getStartAndEndDateStr(trip_Date_Range);
				dateRangeFrom 	= dateRange.getString(DateTimeUtility.FROM_DATE);
				dateRangeTo 	= dateRange.getString(DateTimeUtility.TO_DATE);

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(dateRangeFrom, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(dateRangeTo, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				
				model.put("SearchDate", trip_Date_Range.replace(" ", "_"));

				if (driver_Group_Id != 0 && driver_Jobtitle_Id < 0) {
					driverGroupQuery = "SELECT DA.driver_id FROM Driver AS DA WHERE DA.vehicleGroupId="
							+ driver_Group_Id + " AND DA.companyId = "+userDetails.getCompany_id()+"";
				}

				if (driver_Group_Id != 0 && driver_Jobtitle_Id > 0) {
					driverGroupQuery = "SELECT DA.driver_id FROM Driver AS DA WHERE DA.vehicleGroupId="
							+ driver_Group_Id + " AND DA.driJobId=" + driver_Jobtitle_Id + " AND DA.companyId = "+userDetails.getCompany_id()+"";
				}

				driverAttendanceList 		= driverAdvanceService.Report_Group_Driver_Attendance(driverGroupQuery, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
				driverWiseTripSheetHM		= driverAdvanceService.Report_Group_Driver_Trip_And_KMUsage_Lastmonth_salaryDetails(driverGroupQuery, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
				
				
				if((driverWiseTripSheetHM == null)||(driverWiseTripSheetHM.isEmpty())) 
				{
					model.put("NotFound", true); 
				}
				
				if((boolean) configuration.get("IncTripExpenseInDriverSalary")){	
					try {
						Date FromDate = DateTimeUtility.getDateFromString(dateRangeFrom, DateTimeUtility.YYYY_MM_DD);
						Date ToDate = DateTimeUtility.getDateFromString(dateRangeTo, DateTimeUtility.YYYY_MM_DD);
						for (DriverAttendanceDto driverAttendance : driverAttendanceList) {
							driverAttendanceList1 	= driverAdvanceService.Report_OF_Driver_AttendanceForDriverSalary(driverAttendance.getDRIVERID(), dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
							
							Double ExpenseAmount = 0.0;
							for(DriverAttendanceDto driverAtt : driverAttendanceList1){
								TripSheet trip = tripSheetService.getTripSheetDetails(driverAtt.getTRIPSHEETID());
								Date tripDate = trip.getClosetripDate();
								
								if( (tripDate.after(FromDate) && tripDate.before(ToDate)) || (tripDate.getTime() == FromDate.getTime() || tripDate.getTime() == ToDate.getTime()) ) {
								    // Code to be executed if the close trip date is within the specified range
								     tripExpense  = tripExpenseService.getTripExpense(driverAtt.getExpenseId(),userDetails.getCompany_id());
								     if(tripExpense.getIncldriverbalance()){	
								    	 ExpenseAmount += driverAtt.getExpenseAmount();
								     }
								     driverAttendance.setIncldriverbalance(tripExpense.getIncldriverbalance());
								}
							}
							if(ExpenseAmount>0){
								driverAttendance.setExpenseAmount(ExpenseAmount);
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				model.put("configuration", configuration);
				model.put("DA", DBL.prepare_Driver_Group_DriverAttendance_Report(driverAttendanceList, driverWiseTripSheetHM));				
				
				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));
			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}
		} catch (Exception e) {
			LOGGER.error("err", e);
		}
		return new ModelAndView("Group_Driver_Attendance_Report", model);
	}

	/*SRS Travels Driver Attendance Point Report By Dev Yogi Start */
	@RequestMapping("/DriverHaltPointReport")
	public ModelAndView DriverHaltPointReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Driver_HaltPoint_Report", model);
	}
	/*SRS Travels Driver Attendance Point Report By Dev Yogi End  */
	
	@RequestMapping(value = "/DriverHaltPointReport", method = RequestMethod.POST)
	public ModelAndView DriverHaltPointReport(
			@ModelAttribute("command") @RequestParam("DRIVER_ID") final Integer DRIVER_ID,
			@RequestParam("HALT_DATE_RANGE") final String HALT_DATE_RANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {

			if (HALT_DATE_RANGE != "" && HALT_DATE_RANGE != null && DRIVER_ID != null) {
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

					List<DriverAttendanceDto> TRIPSHEETPOINT = driverAdvanceService.Report_OF_Driver_Halt_Point(DRIVER_ID,
							dateRangeFrom, dateRangeTo, userDetails.getCompany_id());

					List<DriverAttendanceDto> LocalHALT = driverAdvanceService.Report_OF_Driver_LocalHalt_Point(DRIVER_ID,
							dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					
					if(((TRIPSHEETPOINT == null)&&(LocalHALT==null))) 
					{
						model.put("NotFound", true); 						
					}
					
					model.put("DA", DBL.prepare_DriverAttendance_Date_to_Report(TRIPSHEETPOINT, LocalHALT));

					Double TotalPoint = DBL.perpare_Total_DriverAttendance_Point(TRIPSHEETPOINT, LocalHALT);
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					String TotalIncomeShow = AMOUNTFORMAT.format(TotalPoint);
					model.put("TotalPoint", TotalIncomeShow);

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
		return new ModelAndView("Driver_HaltPoint_Report", model);
	}

	
	/*SRS Travels Group Wise Driver Point Report By Dev Yogi Start*/
	@RequestMapping("/GroupDriverHaltPointReport")
	public ModelAndView GroupDriverHaltPointReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Group_Driver_HaltPoint_Report", model);
	}
	/*SRS Travels Group Wise Driver Point Report By Dev Yogi End*/
	
	@RequestMapping(value = "/GroupDriverHaltPointReport", method = RequestMethod.POST)
	public ModelAndView GroupDriverHaltPointReport(
			@ModelAttribute("command") @RequestParam("DRIVER_GROUP_ID") final long DRIVER_GROUP_ID,
			@RequestParam("DRIVER_JOBTITLE") final Integer DRIVER_JOBTITLE,
			@RequestParam("HALT_DATE_RANGE") final String HALT_DATE_RANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		try {

			if (HALT_DATE_RANGE != "" && HALT_DATE_RANGE != null && DRIVER_GROUP_ID != 0) {
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

					model.put("SearchDate", HALT_DATE_RANGE.replace(" ", "_"));

					String DRIVER_GROUP_Query = "";

					if (DRIVER_GROUP_ID != 0 && DRIVER_JOBTITLE < 0) {

						DRIVER_GROUP_Query = "SELECT DA.driver_id FROM Driver AS DA WHERE DA.vehicleGroupId="
								+ DRIVER_GROUP_ID + " AND DA.companyId = "+userDetails.getCompany_id()+"";
					}

					if (DRIVER_GROUP_ID != 0 && DRIVER_JOBTITLE > 0) {

						DRIVER_GROUP_Query = "SELECT DA.driver_id FROM Driver AS DA WHERE DA.vehicleGroupId="
								+ DRIVER_GROUP_ID + " AND DA.driJobId=" + DRIVER_JOBTITLE + "  AND DA.companyId = "+userDetails.getCompany_id()+"";
					}

					List<DriverAttendanceDto> AdvanceCollection = driverAdvanceService
							.Report_OF_Driver_Group_Attendance_Point(DRIVER_GROUP_Query, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());

					if((AdvanceCollection == null)||(AdvanceCollection.isEmpty())) 
					{
						model.put("NotFound", true); 						
					}
					
					
					model.put("DA", DBL.prepare_Driver_Group_DriverAttendance_POINT_Report(AdvanceCollection));

					Double TotalPoint = DBL.perpare_Total_Driver_Group_DriverAttendance_Point(AdvanceCollection);
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					String TotalIncomeShow = AMOUNTFORMAT.format(TotalPoint);
					model.put("TotalPoint", TotalIncomeShow);

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
		return new ModelAndView("Group_Driver_HaltPoint_Report", model);
	}

	@RequestMapping(value = "/GetDriverHaltPointReport", method = RequestMethod.GET)
	public ModelAndView GetDriverHaltPointReport(
			@ModelAttribute("command") @RequestParam("DID") final Integer DRIVER_ID,
			@RequestParam("DATE") final String HALT_DATE_RANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {

			if (HALT_DATE_RANGE != "" && HALT_DATE_RANGE != null && DRIVER_ID != null) {
				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = HALT_DATE_RANGE.split("_to_");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("Halt Driver Date Range:", e);
				}

				try {

					model.put("SearchDate", HALT_DATE_RANGE);

					List<DriverAttendanceDto> TRIPSHEETPOINT = driverAdvanceService.Report_OF_Driver_Halt_Point(DRIVER_ID,
							dateRangeFrom, dateRangeTo, userDetails.getCompany_id());

					List<DriverAttendanceDto> LocalHALT = driverAdvanceService.Report_OF_Driver_LocalHalt_Point(DRIVER_ID,
							dateRangeFrom, dateRangeTo, userDetails.getCompany_id());

					model.put("DA", DBL.prepare_DriverAttendance_Date_to_Report(TRIPSHEETPOINT, LocalHALT));

					Double TotalPoint = DBL.perpare_Total_DriverAttendance_Point(TRIPSHEETPOINT, LocalHALT);
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					String TotalIncomeShow = AMOUNTFORMAT.format(TotalPoint);
					model.put("TotalPoint", TotalIncomeShow);

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
		return new ModelAndView("Driver_HaltPoint_Report", model);
	}
	
	@RequestMapping("/DepotDriverAttReport")
	public ModelAndView DepotWiseAttReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Depot_Wise_Attendance_Report", model);
	}

	@RequestMapping(value = "/DepotDriverAttReport", method = RequestMethod.POST)
	public ModelAndView DepotDriverAttReport(
			@ModelAttribute("command") @RequestParam("DRIVER_GROUP_ID") final long DRIVER_GROUP_ID,
			@RequestParam("DRIVER_JOBTITLE") final Integer DRIVER_JOBTITLE,
			@RequestParam("HALT_DATE_RANGE") final String HALT_DATE_RANGE, final HttpServletRequest request) {

		Map<String, Object> 	model 				= new HashMap<String, Object>();
		CustomUserDetails		userDetails			= null;
		String[] 				From_TO_DateRange 	= null;
		String 					dateRangeFrom 		= "";
		String 					dateRangeTo 		= "";
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (HALT_DATE_RANGE != "" && HALT_DATE_RANGE != null && DRIVER_GROUP_ID != 0) {

				try {

					From_TO_DateRange 	= HALT_DATE_RANGE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("Halt Driver Date Range:", e);
				}

				try {

					model.put("SearchDate", HALT_DATE_RANGE.replace(" ", "_"));

					String Vehicle_group = "", DRIVER_GROUP_Job = "";

					if (DRIVER_GROUP_ID != 0 ) {
						Vehicle_group = " AND D.vehicleGroupId='" + DRIVER_GROUP_ID + "' ";
						
						model.put("SearchGroup", vehicleGroupService.getVehicleGroupByID(DRIVER_GROUP_ID).getvGroup());

					}

					if (DRIVER_JOBTITLE != null && DRIVER_JOBTITLE > 0) {

						DRIVER_GROUP_Job = " AND D.driJobId=" + DRIVER_JOBTITLE + " ";
					}

					if (dateRangeFrom != "" && dateRangeTo != "") {

						String query = " " + Vehicle_group + " " + DRIVER_GROUP_Job + " ";

						List<DriverSalaryDto> Salary = driverSalaryService
								.Driver_MONTH_WISE_ATTENDANCE_ReportDate(query, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
						System.out.println("I am here ");
						model.put("salary", Salary);

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
		return new ModelAndView("Depot_Driver_Attendance_Report", model);
	}
	
	@RequestMapping(value = "/GetDriverAttReport", method = RequestMethod.GET)
	public ModelAndView GetDriverAttReport(@ModelAttribute("command") @RequestParam("DID") final Integer DRIVER_ID,
			@RequestParam("DATE") final String dateRange, final HttpServletRequest request) {

		Map<String, Object> 			model 						= null;
		CustomUserDetails				userDetails					= null;
		List<DriverAttendanceDto> 		driverAttendanceList 		= null; 
		String[]						From_TO_DateRange			= null;
		String 							dateRangeFrom 				= "";
		String 							dateRangeTo 				= "";
		
		try {

			model 						= new HashMap<String, Object>();                                                            
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (dateRange != "" && dateRange != null && DRIVER_ID != null) {

				From_TO_DateRange 		= dateRange.split("_to_");
				
				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				driverAttendanceList 	= driverAdvanceService.Report_OF_Driver_Attendance(DRIVER_ID, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));
				model.put("DA", prepare_DriverAttendance_Report(driverAttendanceList));
				model.put("SearchDate", dateRange);

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Driver_Attendance_Report", model);
	}


}
