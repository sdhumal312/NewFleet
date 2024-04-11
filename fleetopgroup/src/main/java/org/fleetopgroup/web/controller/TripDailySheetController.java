/**
 * 
 */
package org.fleetopgroup.web.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.CashBookApprovalStatus;
import org.fleetopgroup.constant.CashBookPaymentType;
import org.fleetopgroup.constant.CashBookStatus;
import org.fleetopgroup.constant.DriverAdvanceType;
import org.fleetopgroup.constant.DriverStatus;
import org.fleetopgroup.constant.FuelConfigurationConstants;
import org.fleetopgroup.constant.PenaltyConfigurationConstants;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TollApiConfiguration;
import org.fleetopgroup.constant.TripDailySheetCollectionConfiguration;
import org.fleetopgroup.constant.TripDailySheetStatus;
import org.fleetopgroup.constant.VehicleConfigurationContant;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.persistence.bl.CashBookBL;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.FuelBL;
import org.fleetopgroup.persistence.bl.TripDailyBL;
import org.fleetopgroup.persistence.bl.TripRouteBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.bl.VehicleProfitAndLossTxnCheckerBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverAttendanceDto;
import org.fleetopgroup.persistence.dto.DriverSalaryAdvanceDto;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.TripDailyExpenseDto;
import org.fleetopgroup.persistence.dto.TripDailyGroupCollectionDto;
import org.fleetopgroup.persistence.dto.TripDailyIncomeDto;
import org.fleetopgroup.persistence.dto.TripDailyRouteSheetDto;
import org.fleetopgroup.persistence.dto.TripDailySheetDto;
import org.fleetopgroup.persistence.dto.TripRouteDto;
import org.fleetopgroup.persistence.dto.TripRoutefixedExpenseDto;
import org.fleetopgroup.persistence.dto.TripRoutefixedIncomeDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.model.CashBook;
import org.fleetopgroup.persistence.model.CashBookBalance;
import org.fleetopgroup.persistence.model.CashBookName;
import org.fleetopgroup.persistence.model.CashBookSequenceCounter;
import org.fleetopgroup.persistence.model.DriverAttendance;
import org.fleetopgroup.persistence.model.TripDailyAllGroupDay;
import org.fleetopgroup.persistence.model.TripDailyAllGroupDaySequenceCounter;
import org.fleetopgroup.persistence.model.TripDailyExpense;
import org.fleetopgroup.persistence.model.TripDailyGroupCollection;
import org.fleetopgroup.persistence.model.TripDailyGroupCollectionSequenceCounter;
import org.fleetopgroup.persistence.model.TripDailyIncome;
import org.fleetopgroup.persistence.model.TripDailyRouteSheet;
import org.fleetopgroup.persistence.model.TripDailySheet;
import org.fleetopgroup.persistence.model.TripDailySheetSequenceCounter;
import org.fleetopgroup.persistence.model.TripDailyTimeIncome;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetExpense;
import org.fleetopgroup.persistence.model.TripSheetIncome;
import org.fleetopgroup.persistence.model.VehicleExpenseDetails;
import org.fleetopgroup.persistence.model.VehicleGroup;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossIncomeTxnChecker;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.service.VehicleProfitAndLossIncomeTxnCheckerService;
import org.fleetopgroup.persistence.service.VehicleProfitAndLossTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookNameService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookSequenceService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverAttendanceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverSalaryAdvanceService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ITripDailyAllGroupDaySequenceService;
import org.fleetopgroup.persistence.serviceImpl.ITripDailyGroupCollectionSequenceService;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetSequenceService;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

/**
 * @author fleetop
 *
 */
@Controller
public class TripDailySheetController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ITripDailySheetService TripDailySheetService;

	@Autowired
	private IVehicleService vehicleService;

	@Autowired
	private ITripRouteService TripRouteService;

	@Autowired
	private IServiceReminderService ServiceReminderService;

	@Autowired
	private IVehicleOdometerHistoryService vehicleOdometerHistoryService;

	@Autowired
	private IUserProfileService userProfileService;

	@Autowired
	private ICompanyConfigurationService companyConfigurationService;

	@Autowired
	private IFuelService fuelService;

	@Autowired
	private IDriverAttendanceService DriverAttendanceService;

	@Autowired
	private IDriverSalaryAdvanceService DriverSalaryAdvanceService;

	@Autowired
	private ICashBookService CashBookService;

	@Autowired
	ICashBookNameService cashBookNameService;

	@Autowired
	private ITripDailySheetSequenceService tripDailySheetSequenceService;

	@Autowired
	IVehicleGroupService vehicleGroupService;

	@Autowired
	private ITripDailyGroupCollectionSequenceService tripDailyGroupCollectionSequenceService;

	@Autowired
	private ICashBookSequenceService cashBookSequenceService;

	@Autowired
	private ITripDailyAllGroupDaySequenceService tripDailyAllGroupDaySequenceService;
	
	@Autowired	private VehicleProfitAndLossTxnCheckerService	vehicleProfitAndLossTxnCheckerService;
	@Autowired	IVehicleProfitAndLossService		vehicleProfitAndLossService;
	@Autowired	VehicleProfitAndLossIncomeTxnCheckerService		vehicleProfitAndLossIncomeTxnCheckerService;
	@Autowired	IVehicleExpenseDetailsService		vehicleExpenseDetailsService;
	
	VehicleProfitAndLossTxnCheckerBL	txnCheckerBL = new VehicleProfitAndLossTxnCheckerBL();

	// In Trip Daily Sheet Show to Main Page In All TripDailySheet Entries
	// Controller name in /newTripDaily

	TripDailyBL TDBL = new TripDailyBL();
	TripRouteBL TRoute = new TripRouteBL();
	VehicleBL VBL = new VehicleBL();
	DriverBL DBL = new DriverBL();
	VehicleOdometerHistoryBL VOHBL = new VehicleOdometerHistoryBL();
	FuelBL FuBL = new FuelBL();
	CashBookBL CBBL = new CashBookBL();

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat driverAttendanceFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@RequestMapping(value = "/searchTripDaily", method = RequestMethod.POST)
	public ModelAndView searchTripDaily(@RequestParam("tripStutes") final String TripStutes,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			List<TripDailySheetDto> TripCollection = TripDailySheetService.Search_TripDailySheet(TripStutes,
					userDetails);

			model.put("TripDaily", TDBL.prepare_TripDailySheetDto(TripCollection));

			model.put("TripSheetToday", TripCollection.size());

		} catch (Exception e) {
			System.out.println("Exception : " + e);
			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("new_TripDaily", model);
	}

	@RequestMapping(value = "/searchTripDailyShow", method = RequestMethod.POST)
	public ModelAndView searchTripDailyShow(@RequestParam("tripStutes") final Long tripdailyid,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TripDailySheetDto TripCol = TripDailySheetService.Get_Showing_TripDaily_SheetByNumber(tripdailyid,
					userDetails);
			// get the Trip sheet ID to Details
			model.put("TripDaily", TDBL.Show_TripDailySheet_page(TripCol));

			// get Trip sheet id to get all Expense Details
			model.put("TripColExpense", TripDailySheetService
					.findAll_TripDailySheet_ID_Expense(TripCol.getTRIPDAILYID(), userDetails.getCompany_id()));

			// get Trip sheet id to get all Income Details
			model.put("TripColIncome", TripDailySheetService.findAll_TripDailySheet_ID_Income(TripCol.getTRIPDAILYID(),
					userDetails.getCompany_id()));

			DecimalFormat df = new DecimalFormat("##,##,##0");

			df.setMaximumFractionDigits(0);
			Double TotalExpense = TripCol.getTOTAL_EXPENSE();
			if (TripCol.getTOTAL_EXPENSE() == null) {
				TotalExpense = 0.0;
			}
			model.put("expenseTotal", df.format(TotalExpense));

			df.setMaximumFractionDigits(0);
			Double Totalincome = TripCol.getTOTAL_INCOME();
			if (TripCol.getTOTAL_INCOME() == null) {
				Totalincome = 0.0;
			}
			model.put("incomeTotal", df.format(Totalincome));

			df.setMaximumFractionDigits(0);
			Double TotalBALANCE = TripCol.getTOTAL_BALANCE();
			if (TripCol.getTOTAL_BALANCE() == null) {
				TotalBALANCE = 0.0;
			}
			model.put("incomeBalance", df.format(TotalBALANCE));

		} catch (NullPointerException e) {
			model.put("NotFound", true);
			return new ModelAndView("redirect:/newTripDaily.in", model);
		} catch (Exception e) {
			model.put("NotFound", true);
			return new ModelAndView("redirect:/newTripDaily.in", model);
		}
		return new ModelAndView("show_TripDaily", model);
	}

	@RequestMapping(value = "/searchTripDailyManage", method = RequestMethod.POST)
	public ModelAndView searchTripDailyManage(@RequestParam("tripStutes") final String Search,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			List<TripDailySheetDto> TripCollection = TripDailySheetService.Search_STATUS_TripDailySheet(Search,
					TripDailySheetStatus.TRIP_STATUS_OPEN);

			model.put("TripDaily", TDBL.prepare_TripDailySheetDto(TripCollection));

			model.put("TripManage", TripCollection.size());

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("newManageTripDaily", model);
	}

	@RequestMapping(value = "/searchTripDailyClosed", method = RequestMethod.POST)
	public ModelAndView searchTripDailyClosed(@RequestParam("tripStutes") final String Search,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			List<TripDailySheetDto> TripCollection = TripDailySheetService.Search_STATUS_TripDailySheet(Search,
					TripDailySheetStatus.TRIP_STATUS_CLOSED);

			model.put("TripDaily", TDBL.prepare_TripDailySheetDto(TripCollection));

			model.put("TripClose", TripCollection.size());

		} catch (Exception e) {
			System.err.println("inside exceptiob " + e);
			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("newCloseTripDaily", model);
	}

	// Show the the Trip Sheet menu
	@RequestMapping(value = "/searchTripDailyDate", method = RequestMethod.POST)
	public ModelAndView searchTripDailyDate(@RequestParam("TripColDate") final String TripColDate,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			List<TripDailySheetDto> TripCollection = TripDailySheetService.list_Today_TripDailySheet(TripColDate,
					userDetails);
			if(TripCollection != null && !TripCollection.isEmpty()) {
				
				model.put("TripDaily", TDBL.prepare_TripDailySheetDtoList(TripCollection));
				model.put("TripSheetToday", TripCollection.size());
			}

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("new_TripDaily", model);
	}

	// Show the the Trip Sheet menu
	@RequestMapping(value = "/newTripDaily", method = RequestMethod.GET)
	public ModelAndView newTripDaily(final HttpServletRequest request, final HttpServletResponse Response) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			java.util.Date currentDate = new java.util.Date();
			DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");

			List<TripDailySheetDto> TripCollection = TripDailySheetService
					.list_Today_TripDailySheet(ft.format(currentDate), userDetails);

			if (TripCollection != null && !TripCollection.isEmpty()) {
				model.put("TripSheetToday", TripCollection.size());
			}
			model.put("TripDaily", TDBL.prepare_TripDailySheetDtoList(TripCollection));

			// model.put("TripSheetToday", TripCollection.size());

		} catch (Exception e) {
			LOGGER.error("Trip Daily Sheet Page:", e);
		}
		return new ModelAndView("new_TripDaily", model);
	}

	/* show Only manage Tripdaily Collection */
	@RequestMapping(value = "/manageTripDaily/{pageNumber}", method = RequestMethod.GET)
	public String manageTripDaily(@PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Page<TripDailySheet> page = TripDailySheetService
					.getDeployment_Page_TripDailySheet(TripDailySheetStatus.TRIP_STATUS_OPEN, pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("TripManage", page.getTotalElements());
			List<TripDailySheetDto> pageList = (TripDailySheetService
					.list_TripDailySheet_Page_Status(TripDailySheetStatus.TRIP_STATUS_OPEN, pageNumber, userDetails));

			model.addAttribute("TripDaily", TDBL.prepare_TripDailySheetDtoList(pageList));

		} catch (NullPointerException e) {
			e.printStackTrace();
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("TripCol Page:", e);
			e.printStackTrace();
		}
		return "newManageTripDaily";
	}

	/* show only Closed TripDaily Collection */
	@RequestMapping(value = "/closeTripDaily/{pageNumber}", method = RequestMethod.GET)
	public String closeTripDaily(@PathVariable Integer pageNumber, Model model) throws Exception {

		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Page<TripDailySheet> page = TripDailySheetService.getDeployment_Page_TripDailySheet(
					TripDailySheetStatus.TRIP_STATUS_CLOSED, pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("TripClose", page.getTotalElements());
			List<TripDailySheetDto> pageList = (TripDailySheetService
					.list_TripDailySheet_Page_Status(TripDailySheetStatus.TRIP_STATUS_CLOSED, pageNumber, userDetails));

			model.addAttribute("TripDaily", TDBL.prepare_TripDailySheetDtoList(pageList));
			model.addAttribute("SelectStatus", "CLOSED");

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("TripCol Page:", e);
			e.printStackTrace();
		}
		return "newCloseTripDaily";
	}

	/* show only Closed TripDaily Collection */
	@RequestMapping(value = "/YeTrDay", method = RequestMethod.GET)
	public String YesterdayClosedTrip(@RequestParam("Loc") final String Location, Model model) throws Exception {

		try {

			Calendar cal = Calendar.getInstance();
			java.util.Date date = cal.getTime();
			String DayTwo = "";
			for (int i = 0; i < 2; i++) {
				cal.add(Calendar.DAY_OF_MONTH, -1);
				date = cal.getTime();
				switch (i) {
				case 1:
					DayTwo = driverAttendanceFormat.format(date);
					break;
				}
			}
			List<TripDailySheet> TripCollection = TripDailySheetService.list_Today_TripDailySheet(DayTwo, "CLOSED",
					Location);

			if (TripCollection != null && !TripCollection.isEmpty()) {
				model.addAttribute("TripSheetToday", TripCollection.size());
			}
			model.addAttribute("TripDaily", TDBL.prepare_TripDailySheetDto_GET(TripCollection));

			model.addAttribute("SelectStatus", Location);
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("TripCol Page:", e);
			e.printStackTrace();
		}
		return "newCloseTripDaily";
	}

	// This TripDaily Sir Durgamba Trip sheet Details to Created
	@RequestMapping(value = "/addTripDaily", method = RequestMethod.GET)
	public ModelAndView addTripDaily(final HttpServletRequest request, final HttpServletResponse Response) {
		try {
		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("add_TripDaily");
	}

	// create OLd Trip sheet to Details
	@RequestMapping(value = "/copyTripDaily", method = RequestMethod.GET)
	public ModelAndView copyTripDaily(@RequestParam("ID") final Long TRIPCOLLID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			TripDailySheetDto TripCol = TripDailySheetService.Get_Showing_TripDaily_Sheet(TRIPCOLLID, userDetails);

			if (TripCol != null) {
				Integer vehiclename = vehicleService
						.updateCurrentOdoMeterGETVehicleToCurrentOdometer(TripCol.getVEHICLEID());
				model.put("CurrentOdometer", vehiclename);
			}
			// get the Trip sheet ID to Details
			model.put("TripDaily", TDBL.Show_TripDailySheet_page(TripCol));

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("copyTripDaily", model);
	}

	// Save trip Daily Collection Details and income and expense make zero
	@RequestMapping(value = "/saveTripDaily", method = RequestMethod.POST)
	public ModelAndView saveTripDaily(@ModelAttribute("command") TripDailySheetDto dailySheetDto,
			RedirectAttributes redir, final HttpServletRequest request) throws Exception {
		
		Map<String, Object> 		model 					= new HashMap<String, Object>();
		java.sql.Date 				fromDate 				= null;
		java.util.Date 				date					= null;
		CustomUserDetails 			userDetails				= null;
		
		userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			 date 			= dateFormat.parse(dailySheetDto.getTRIP_OPEN_DATE());
			 fromDate 		= new Date(date.getTime());
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		
		/*CHECK STATUS OF VEHICLE AND VALIDATE AS PER THE STATUS*/
		/*if(dailySheetDto.getVEHICLEID()!=0){
			vStatusId 	= TripDailySheetService.getvehicleStatusByVid(dailySheetDto.getVEHICLEID());
			statusId	= vStatusId.getvStatusId();
				if(statusId!=VehicleStatusConstant.VEHICLE_STATUS_ACTIVE) {
				model.put("status", true);
				return new ModelAndView("redirect:/addTripDaily.in?status=true");
				}
			}
*/
		// Validate The tripCollectionSheet Date and Vehicle Already Created OR
		// Not Check
		/*
		 * List<TripDailySheet> validateAlready = TripDailySheetService
		 * .Validate_TripCollectionSheet_Date_VehicleName(fromDate,
		 * dailySheetDto.getVEHICLEID()); if (validateAlready != null &&
		 * !validateAlready.isEmpty()) { return new
		 * ModelAndView("redirect:/addTripDaily.in?already=true"); } else {
		 */

		List<TripDailyRouteSheet> GroupSheet = TripDailySheetService.List_TripDailySheetService_closeDate(
				dailySheetDto.getVEHICLE_GROUP_ID(), driverAttendanceFormat.format(fromDate),
				TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());
		if (GroupSheet != null && !GroupSheet.isEmpty()) {
			redir.addFlashAttribute("closeDate", dailySheetDto.getTRIP_OPEN_DATE());
			return new ModelAndView("redirect:/addTripDaily.in?alreadyClose=true");
		} else {
			TripDailySheetSequenceCounter sequenceCounter = tripDailySheetSequenceService
					.findNextSequenceNumber(userDetails.getCompany_id());
			TripDailySheet Sheet = TDBL.prepareModel_Save_TripDailySheet(dailySheetDto);

			try {
				if (Sheet.getVEHICLEID() != 0 && Sheet.getTRIP_DRIVER_ID() != 0 && Sheet.getTRIP_CONDUCTOR_ID() != 0
						&& Sheet.getTRIP_ROUTE_ID() != 0) {
					if (sequenceCounter == null) {
						return new ModelAndView("redirect:/addTripDaily.in?sequenceNotFound=true");
					}
					Sheet.setTRIPDAILYNUMBER(sequenceCounter.getNextVal());
					try {
						TripDailySheetService.add_TripDailySheet(Sheet);
					} catch (Exception e) {
						e.printStackTrace();
						model.put("already", true);
						LOGGER.error("TripSheet Page:", e);
						return new ModelAndView("redirect:/newTripDaily.in?error=true", model);
					}

					// tripDailySheetSequenceService.updateNextSequenceNumber(sequenceCounter.getNextVal()
					// + 1, sequenceCounter.getSequence_Id());
					if (Sheet.getTRIP_ROUTE_ID() != 0) {
						// not equal to zreo get all fixed expense
						List<TripRoutefixedExpenseDto> RoutefixedExpense = TripRouteService
								.listTripRouteFixedExpene(Sheet.getTRIP_ROUTE_ID(), userDetails.getCompany_id());
						if (RoutefixedExpense != null && !RoutefixedExpense.isEmpty()) {
							Double tripTotalExpense = 0.0;
							for (TripRoutefixedExpenseDto tripRoutefixedExpense : RoutefixedExpense) {

								TripDailyExpense TripExpense = new TripDailyExpense(
										tripRoutefixedExpense.getExpenseAmount(),
										tripRoutefixedExpense.getExpenseRefence());
								TripExpense.setTripDailysheet(Sheet);
								java.util.Date currentDateUpdate = new java.util.Date();
								Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

								TripExpense.setCreated(toDate);
								TripExpense.setCompanyId(userDetails.getCompany_id());
								TripExpense.setExpenseId(tripRoutefixedExpense.getExpenseId());
								TripExpense.setFixedTypeId(tripRoutefixedExpense.getFixedTypeId());
								TripExpense.setCreatedById(userDetails.getId());

								TripDailySheetService.add_TripDailyExpense(TripExpense);
								tripTotalExpense += tripRoutefixedExpense.getExpenseAmount();
							}
							TripDailySheetService.update_TripDailySheet_TotalExpense(Sheet.getTRIPDAILYID());
						}

						List<TripRoutefixedIncomeDto> RoutefixedIncome = TripRouteService
								.listTripRouteFixedIncome(Sheet.getTRIP_ROUTE_ID(), userDetails.getCompany_id());
						if (RoutefixedIncome != null && !RoutefixedIncome.isEmpty()) {
							Double tripTotalIncome = 0.0;
							for (TripRoutefixedIncomeDto tripRoutefixedIncome : RoutefixedIncome) {

								TripDailyIncome TripIncome = new TripDailyIncome(tripRoutefixedIncome.getIncomeAmount(),

										tripRoutefixedIncome.getIncomeRefence());
								TripIncome.setTripDailysheet(Sheet);
								java.util.Date currentDateUpdate = new java.util.Date();
								Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

								TripIncome.setCreated(toDate);
								TripIncome.setCompanyId(userDetails.getCompany_id());

								TripIncome.setCreatedById(userDetails.getId());
								TripIncome.setIncomeId(tripRoutefixedIncome.getIncomeId());
								TripIncome.setIncomeTypeId(tripRoutefixedIncome.getIncomeFixedId());
								TripIncome.setIncomeCollectedById(userDetails.getId());

								TripDailySheetService.add_TripDailyIncome(TripIncome);
								tripTotalIncome += tripRoutefixedIncome.getIncomeAmount();
							}

							TripDailySheetService.update_TripDaily_TotalIncome(Sheet.getTRIPDAILYID());
						}
					}
					model.put("save", true);

					// this Update Current Odometer in Vehicle Getting Trip
					// Daily Sheet
					Update_Current_OdometerinVehicle_getTripDailySheetTo(Sheet);

				} else {
					return new ModelAndView("redirect:/addTripDaily.in?error=true");
				}
			} catch (Exception e) {

				model.put("already", true);
				LOGGER.error("TripSheet Page:", e);
				return new ModelAndView("redirect:/newTripDaily.in?error=true", model);
			}

			return new ModelAndView("redirect:/showTripDaily.in?ID=" + Sheet.getTRIPDAILYID() + "", model);
		}
		/* } */
	}

	@RequestMapping(value = "/showTripDaily", method = RequestMethod.GET)
	public ModelAndView ShowTripsheet(@RequestParam("ID") final Long tripdailyid, final HttpServletRequest request,
			final HttpServletResponse Response) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			HashMap<String, Object> 	configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TOLL_API_CONFIG);

			HashMap<String, Object> 	companyconfig	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIP_COLLECTION_CONFIG);	
			
			TripDailySheetDto TripCol = TripDailySheetService.Get_Showing_TripDaily_Sheet(tripdailyid, userDetails);
			// get the Trip sheet ID to Details
			
			if(TripCol != null) {
				model.put("TripDaily", TDBL.Show_TripDailySheet_page(TripCol));

				if (TripCol.getTOTAL_EXPENSE() != null && TripCol.getTOTAL_EXPENSE() != 0.0) {
					// get Trip sheet id to get all Expense Details
					model.put("TripColExpense", TripDailySheetService.findAll_TripDailySheet_ID_Expense(tripdailyid,
							userDetails.getCompany_id()));
				}
				if (TripCol.getTOTAL_INCOME() != null && TripCol.getTOTAL_INCOME() != 0.0) {
					// get Trip sheet id to get all Income Details
					model.put("TripColIncome", TripDailySheetService.findAll_TripDailySheet_ID_Income(tripdailyid,
							userDetails.getCompany_id()));
				}
				if (TripCol.getTOTAL_INCOME_COLLECTION() != null && TripCol.getTOTAL_INCOME_COLLECTION() != 0.0) {
					// get Trip sheet id to get all Income Details
					model.put("TripColTimeIncome", TripDailySheetService.findAll_TripDailySheet_ID_TIME_Income(tripdailyid,
							userDetails.getCompany_id()));
				}
				
				
				if (TripCol.getTOTAL_WT() != null && TripCol.getTOTAL_WT() != 0.0) {
					List<DriverSalaryAdvanceDto> DriverAdvanvce = DriverSalaryAdvanceService
							.List_OF_TRIPDAILYSHEET_ID_PENALTY_DETAILS(tripdailyid,
									DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY, userDetails.getCompany_id());
					model.put("DriverAdvanvce", DriverAdvanvce);
				}
				DecimalFormat df = new DecimalFormat("##,##,##0");

				df.setMaximumFractionDigits(0);
				Double TotalExpense = TripCol.getTOTAL_EXPENSE();
				if (TripCol.getTOTAL_EXPENSE() == null) {
					TotalExpense = 0.0;
				}
				model.put("expenseTotal", df.format(TotalExpense));

				df.setMaximumFractionDigits(0);
				Double Totalincome = TripCol.getTOTAL_INCOME();
				if (TripCol.getTOTAL_INCOME() == null) {
					Totalincome = 0.0;
				}
				model.put("incomeTotal", df.format(Totalincome));

				df.setMaximumFractionDigits(0);
				Double TotalBALANCE = TripCol.getTOTAL_BALANCE();
				if (TripCol.getTOTAL_BALANCE() == null) {
					TotalBALANCE = 0.0;
				}
				model.put("incomeBalance", df.format(TotalBALANCE));
				
				model.put(TollApiConfiguration.ALLOW_TOLL_APII_NTEGRATION, (boolean)configuration.get(TollApiConfiguration.ALLOW_TOLL_APII_NTEGRATION));
				
				model.put("companyconfig", companyconfig);
			}else {

				model.put("NotFound", true);
				return new ModelAndView("redirect:/newTripDaily.in", model);
			
			}
			

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("show_TripDaily", model);
	}

	// save Issues in databases
	@RequestMapping(value = "/showTripDailyPrint", method = RequestMethod.GET)
	public ModelAndView showTripSheetPrint(@RequestParam("ID") final Long tripdailyid,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			TripDailySheetDto TripCol = TripDailySheetService.Get_Showing_TripDaily_Sheet(tripdailyid, userDetails);
			// get the Trip sheet ID to Details
			model.put("TripDaily", TDBL.Show_TripDailySheet_page(TripCol));


		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("showTripDailyPrint", model);
	}

	// Here TripDailySheet to add Trip many expense Detail it will update
	// TripDailySheet
	@RequestMapping(value = "/addDailyExpense", method = RequestMethod.GET)
	public ModelAndView addDailyExpense(@RequestParam("ID") final Long tripdailyid, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			TripDailySheetDto TripCol = TripDailySheetService.Get_Showing_TripDaily_Sheet(tripdailyid, userDetails);
			// get the Trip sheet ID to Details
			model.put("TripDaily", TDBL.Show_TripDailySheet_page(TripCol));

			// get Trip sheet id to get all Expense Details
			model.put("TripColExpense",
					TripDailySheetService.findAll_TripDailySheet_ID_Expense(tripdailyid, userDetails.getCompany_id()));
			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(0);
			Double TotalExpense = TripCol.getTOTAL_EXPENSE();
			if (TripCol.getTOTAL_EXPENSE() == null) {
				TotalExpense = 0.0;
			}
			model.put("expenseTotal", df.format(TotalExpense));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ExpenseTripDaily", model);
	}

	// update Expense TripDailySheet and also Amount
	@RequestMapping(value = "/updateDailyExpense", method = RequestMethod.POST)
	public ModelAndView updateDailyExpense(@ModelAttribute("command") TripDailySheetDto TripSheet,
			TripSheetExpense TripSheetExpense, @RequestParam("expenseName") final String[] expenseName,
			@RequestParam("Amount") final String[] expenseAmount,
			@RequestParam("expenseRefence") final String[] expenseRefence, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			TripDailySheet tsheet = new TripDailySheet();
			tsheet.setTRIPDAILYID(TripSheet.getTRIPDAILYID());

			for (int i = 0; i < expenseName.length; i++) {

				
				TripDailyExpense TripExpense = new TripDailyExpense();
				TripExpense.setExpenseId(Integer.parseInt(expenseName[i] + ""));
				TripExpense.setTripDailysheet(tsheet);

				TripExpense.setCreatedById(userDetails.getId());
				TripExpense.setExpenseAmount(Double.parseDouble("" + expenseAmount[i]));
				TripExpense.setExpenseRefence(expenseRefence[i]);
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				TripExpense.setCreated(toDate);
				TripExpense.setCompanyId(userDetails.getCompany_id());

				List<TripDailyExpense> validate = TripDailySheetService.Validate_TripDaily_Expense(
						Integer.parseInt(expenseName[i] + ""), tsheet.getTRIPDAILYID(), userDetails.getCompany_id());
				if (validate != null && !validate.isEmpty()) {
					model.put("alreadyExpense", true);

				} else {

					TripDailySheetService.add_TripDailyExpense(TripExpense);
					model.put("updateExpense", true);

				}

			}

			// update total Expense amount
			TripDailySheetService.update_TripDailySheet_TotalExpense(TripSheet.getTRIPDAILYID());

		} catch (

		Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/showTripDaily.in?ID=" + TripSheet.getTRIPDAILYID() + "", model);
	}
	
	

	// removeAdvance TripSheet and also Amount
	@RequestMapping(value = "/removeDailyExpense", method = RequestMethod.GET)
	public ModelAndView removeExpenseTripsheet(@RequestParam("ID") final Long TripExpenseID,
			final HttpServletRequest request) {

		Long TripSheetID = null;
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TripDailyExpense TripAdvance = TripDailySheetService.Get_TripDaily_Expense(TripExpenseID,
					userDetails.getCompany_id());

			if (TripAdvance != null) {
				TripSheetID = TripAdvance.getTripDailysheet().getTRIPDAILYID();

				TripDailySheetService.delete_TripDailyExpense(TripAdvance.getTripExpenseID(),
						userDetails.getCompany_id());

				TripDailySheetService.update_TripDailySheet_TotalExpense(TripSheetID);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/addDailyExpense.in?ID=" + TripSheetID + "");
	}

	// Here TripDailySheet to add Trip many Income Detail it will update
	// TripDailySheet
	@RequestMapping(value = "/addDailyIncome", method = RequestMethod.GET)
	public ModelAndView addDailyIncome(@RequestParam("ID") final Long tripdailyid, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			TripDailySheetDto TripCol = TripDailySheetService.Get_Showing_TripDaily_Sheet(tripdailyid, userDetails);
			// get the Trip sheet ID to Details
			model.put("TripDaily", TDBL.Show_TripDailySheet_page(TripCol));

			// get Trip sheet id to get all Income Details
			model.put("TripColIncome",
					TripDailySheetService.findAll_TripDailySheet_ID_Income(tripdailyid, userDetails.getCompany_id()));
			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(0);
			Double Totalincome = TripCol.getTOTAL_INCOME();
			if (TripCol.getTOTAL_INCOME() == null) {
				Totalincome = 0.0;
			}
			model.put("incomeTotal", df.format(Totalincome));

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("IncomeTripDaily", model);
	}	
	
	
 	
	@RequestMapping(value = "/addDailyPassengerDetails", method = RequestMethod.GET)
	public ModelAndView addDailyPassengerDetails(@RequestParam("ID") final Long tripdailyid, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_COLLECTION_CONFIG);
			TripDailySheetDto TripCol = TripDailySheetService.Get_Showing_TripDaily_Sheet(tripdailyid, userDetails);
			// get the Trip sheet ID to Details
			model.put("TripDaily", TDBL.Show_TripDailySheet_page(TripCol));
			model.put("configuration", configuration);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("PassengerTripDaily", model);
	}

	// update Expense TripSheet and also Amount
	@RequestMapping(value = "/updateDailyIncome", method = RequestMethod.POST)
	public ModelAndView updateIncomeTripsheet(@ModelAttribute("command") TripDailySheetDto TripSheet,
			TripSheetIncome TripSheetIncome, @RequestParam("incomeName") final String[] incomeName,
			@RequestParam("Amount") final String[] incomeAmount,
			@RequestParam("incomeRefence") final String[] incomeRefence, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userName = (CustomUserDetails) auth.getPrincipal();

			TripDailySheet tsheet = new TripDailySheet();
			tsheet.setTRIPDAILYID(TripSheet.getTRIPDAILYID());

			for (int i = 0; i < incomeName.length; i++) {


				TripDailyIncome TripIncome = new TripDailyIncome();
				TripIncome.setIncomeId(Integer.parseInt(incomeName[i] + ""));
				TripIncome.setIncomeAmount(Double.parseDouble("" + incomeAmount[i]));
				TripIncome.setIncomeRefence(incomeRefence[i]);
				TripIncome.setIncomeCollectedById(userName.getId());
				TripIncome.setTripDailysheet(tsheet);
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				TripIncome.setCreated(toDate);
				TripIncome.setCompanyId(userName.getCompany_id());

				TripIncome.setCreatedById(userName.getId());

				List<TripDailyIncome> validate = TripDailySheetService.Validate_TripDaily_Income(
						Integer.parseInt(incomeName[i] + ""), tsheet.getTRIPDAILYID(), userName.getCompany_id());
				if (validate != null && !validate.isEmpty()) {
					model.put("alreadyIncome", true);

				} else {

					TripDailySheetService.add_TripDailyIncome(TripIncome);
					model.put("updateIncome", true);
				}

			}

			TripDailySheetService.update_TripDaily_TotalIncome(TripSheet.getTRIPDAILYID());

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/showTripDaily.in?ID=" + TripSheet.getTRIPDAILYID() + "", model);
	}
	
	// update Expense TripSheet and also Amount
	@RequestMapping(value = "/UpdateTripDailyPassengerDetails", method = RequestMethod.POST)
	public ModelAndView UpdateTripDailyPassengerDetails(@ModelAttribute("command") TripDailySheetDto TripSheet,
			 final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userName = (CustomUserDetails) auth.getPrincipal();
			
			TripSheet.setCOMPANY_ID(userName.getCompany_id());
			TripSheet.setLASTMODIFIED_BY_ID(userName.getId());
			
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp DeletedTime = new java.sql.Timestamp(currentDateUpdate.getTime());
			
			TripSheet.setLASTUPDATEDON(DeletedTime);

			TripDailySheetService.updatePassengerDetails(TripSheet);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/showTripDaily.in?ID=" + TripSheet.getTRIPDAILYID() + "&error=true", model);
		}
		return new ModelAndView("redirect:/showTripDaily.in?ID=" + TripSheet.getTRIPDAILYID() + "&updatePassenger=true", model);
	}
	
	@RequestMapping(value = "/updateNoOfRoundTrip", method = RequestMethod.POST)
	public ModelAndView updateNoOfRoundTrip(@ModelAttribute("command") TripDailySheetDto TripSheet,
			 final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userName = (CustomUserDetails) auth.getPrincipal();
			
			TripSheet.setCOMPANY_ID(userName.getCompany_id());
			TripSheet.setLASTMODIFIED_BY_ID(userName.getId());
			
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp DeletedTime = new java.sql.Timestamp(currentDateUpdate.getTime());
			
			TripSheet.setLASTUPDATEDON(DeletedTime);

			TripDailySheetService.updateNoOfRoundTrip(TripSheet);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/showTripDaily.in?ID=" + TripSheet.getTRIPDAILYID() + "&error=true", model);
		}
		return new ModelAndView("redirect:/addTripDailyFuel.in?ID=" + TripSheet.getTRIPDAILYID() + "", model);
	}

	@RequestMapping(value = "/UpdateIncomeAddAmount", method = RequestMethod.POST)
	public ModelAndView updateIncomeTripsheet(@RequestParam("TRIPDAILYID") final Long TRIPDAILYID,
			@RequestParam("tripincomeID") final Long tripincomeID,
			@RequestParam("incomeAmount") final Double incomeAmount, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userName = (CustomUserDetails) auth.getPrincipal();

			TripDailySheetService.Update_TripDailyIncome_Amount_By_tripincomeID(incomeAmount, userName.getId(),
					tripincomeID, userName.getCompany_id());
			model.put("updateIncome", true);

			TripDailySheetService.update_TripDaily_TotalIncome(TRIPDAILYID);

		} catch (Exception e) {

			e.printStackTrace();
			return new ModelAndView("redirect:/showTripDaily.in?ID=" + TRIPDAILYID + "", model);
		}
		return new ModelAndView("redirect:/addDailyIncome.in?ID=" + TRIPDAILYID + "", model);
	}

	// removeAdvance TripSheet and also Amount
	@RequestMapping(value = "/removeDailyIncome", method = RequestMethod.GET)
	public ModelAndView removeIncomeTripsheet(@RequestParam("ID") final Long TripIncomeID,
			final HttpServletRequest request) {

		Long TripSheetID = null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			TripDailyIncome TripIncome = TripDailySheetService.Get_TripDaily_Income(TripIncomeID,
					userDetails.getCompany_id());

			if (TripIncome != null) {
				TripSheetID = TripIncome.getTripDailysheet().getTRIPDAILYID();

				TripDailySheetService.delete_TripDaily_Income(TripIncome.getTripincomeID(),
						userDetails.getCompany_id());

				TripDailySheetService.update_TripDaily_TotalIncome(TripSheetID);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/addDailyIncome.in?ID=" + TripSheetID + "");
	}

	// Here TripDailySheet to add Trip many Time Income Detail it will update
	// TripDailySheet
	@RequestMapping(value = "/addDailyTimeIncome", method = RequestMethod.GET)
	public ModelAndView addDailyTimeIncome(@RequestParam("ID") final Long tripdailyid,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			TripDailySheetDto TripCol = TripDailySheetService.Get_Showing_TripDaily_Sheet(tripdailyid, userDetails);
			// get the Trip sheet ID to Details
			model.put("TripDaily", TDBL.Show_TripDailySheet_page(TripCol));

			// get Trip sheet id to get all Income Details
			model.put("TripColIncome", TripDailySheetService.findAll_TripDaily_TIME_Income(tripdailyid));
			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(0);
			Double Totalincome = TripCol.getTOTAL_INCOME_COLLECTION();
			if (TripCol.getTOTAL_INCOME_COLLECTION() == null) {
				Totalincome = 0.0;
			}
			model.put("TimeIncomeTotal", df.format(Totalincome));

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("TimeIncomeTripDaily", model);
	}

	// Save trip Daily Collection Details and income and expense make zero
	@RequestMapping(value = "/saveFixedTripTimeIncomeAdd", method = RequestMethod.POST)
	public ModelAndView saveFixedTripTimeIncomeAdd(@RequestParam("TRIPDAILYID") final Long TRIPDAILYID,
			@RequestParam("TRIP_ROUTE_ID") final Integer TRIP_ROUTE_ID, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (TRIPDAILYID != null && TRIP_ROUTE_ID != null) {
				CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				List<TripRoutefixedIncomeDto> RoutefixedIncome = TripRouteService
						.listTripRouteFixedIncome(TRIP_ROUTE_ID, userDetails.getCompany_id());

				if (RoutefixedIncome != null && !RoutefixedIncome.isEmpty()) {
					Authentication auth = SecurityContextHolder.getContext().getAuthentication();
					CustomUserDetails userName = (CustomUserDetails) auth.getPrincipal();

					TripDailySheet Sheet = new TripDailySheet();
					Sheet.setTRIPDAILYID(TRIPDAILYID);

					for (TripRoutefixedIncomeDto tripRoutefixedIncome : RoutefixedIncome) {

						TripDailyTimeIncome TripIncome = new TripDailyTimeIncome();
						TripIncome.setIncomeAmount(tripRoutefixedIncome.getIncomeAmount());
						TripIncome.setIncomeRefence(tripRoutefixedIncome.getIncomeRefence());
						TripIncome.setIncomeFixedId(tripRoutefixedIncome.getIncomeFixedId());
						TripIncome.setIncomeId(tripRoutefixedIncome.getIncomeId());
						TripIncome.setCreatedById(userDetails.getId());
						TripIncome.setTripDailysheet(Sheet);
						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

						TripIncome.setCreated(toDate);
						TripIncome.setCompanyId(userName.getCompany_id());
						TripIncome.setIncomeCollectedById(userDetails.getId());

						TripDailySheetService.add_TripDailyTimeIncome(TripIncome);
					}

					TripDailySheetService.update_TripDaily_TotalTimeIncome_INCOME_COLLECTION_TRIP_SUBROUTE_ID(
							TRIP_ROUTE_ID, Sheet.getTRIPDAILYID(), userName.getCompany_id());

				} else {
					return new ModelAndView("redirect:/addDailyTimeIncome.in?ID=" + TRIPDAILYID + "&error=true");
				}

			} else {
				return new ModelAndView("redirect:/addDailyTimeIncome.in?ID=" + TRIPDAILYID + "&error=true");
			}
		} catch (Exception e) {
			model.put("already", true);

			LOGGER.error("TripSheet Page:", e);
			return new ModelAndView("redirect:/addDailyTimeIncome.in?ID=" + TRIPDAILYID + "&error=true", model);
		}

		return new ModelAndView("redirect:/addDailyTimeIncome.in?ID=" + TRIPDAILYID + "", model);
	}

	// update Expense TripSheet and also Amount
	@RequestMapping(value = "/updateDailyTimeIncome", method = RequestMethod.POST)
	public ModelAndView updateTimeIncomeTripsheet(@ModelAttribute("command") TripDailySheetDto TripSheet,
			TripSheetIncome TripSheetIncome, @RequestParam("incomeName") final String[] incomeName,
			@RequestParam("incomeAmount") final String[] incomeAmount,
			@RequestParam("incomeRefence") final String[] incomeRefence, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userName = (CustomUserDetails) auth.getPrincipal();

			TripDailySheet tsheet = new TripDailySheet();
			tsheet.setTRIPDAILYID(TripSheet.getTRIPDAILYID());

			for (int i = 0; i < incomeName.length; i++) {

				TripDailyTimeIncome TripIncome = new TripDailyTimeIncome();
				TripIncome.setIncomeId(Integer.parseInt("" + incomeName[i]));
				TripIncome.setIncomeAmount(Double.parseDouble("" + incomeAmount[i]));
				TripIncome.setIncomeRefence(incomeRefence[i]);
				TripIncome.setCreatedById(userName.getId());

				TripIncome.setTripDailysheet(tsheet);
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				TripIncome.setCreated(toDate);
				TripIncome.setCompanyId(userName.getCompany_id());
				TripIncome.setIncomeCollectedById(userName.getId());

				List<TripDailyTimeIncome> validate = TripDailySheetService.Validate_TripDaily_TIME_Income(
						Integer.parseInt("" + incomeName[i]), tsheet.getTRIPDAILYID(), userName.getCompany_id());
				if (validate != null && !validate.isEmpty()) {
					model.put("alreadyIncome", true);

				} else {

					TripDailySheetService.add_TripDailyTimeIncome(TripIncome);
					model.put("updateIncome", true);
				}

			}

			TripDailySheetService.update_TripDaily_TotalTimeIncome_INCOME_COLLECTION(TripSheet.getTRIPDAILYID());

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/showTripDaily.in?ID=" + TripSheet.getTRIPDAILYID() + "", model);
	}

	@RequestMapping(value = "/UpdateTimeIncomeAddAmount", method = RequestMethod.POST)
	public ModelAndView updateTimeIncomeTripsheet(@RequestParam("TRIPDAILYID") final Long TRIPDAILYID,
			@RequestParam("TDTIMEID") final Long TDTIMEID, @RequestParam("incomeAmount") final Double incomeAmount,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userName = (CustomUserDetails) auth.getPrincipal();

			TripDailySheetService.Update_TripDailyTimeIncome_Amount_By_TDTIMEID(incomeAmount, userName.getId(),
					TDTIMEID);
			model.put("updateIncome", true);

			TripDailySheetService.update_TripDaily_TotalTimeIncome_INCOME_COLLECTION(TRIPDAILYID);

		} catch (Exception e) {

			e.printStackTrace();
			return new ModelAndView("redirect:/showTripDaily.in?ID=" + TRIPDAILYID + "", model);
		}
		return new ModelAndView("redirect:/addDailyTimeIncome.in?ID=" + TRIPDAILYID + "", model);
	}

	// removeAdvance TripSheet and also Amount
	@RequestMapping(value = "/removeDailyTimeIncome", method = RequestMethod.GET)
	public ModelAndView removeIncomeTimeTripsheet(@RequestParam("ID") final Long TripIncomeID,
			final HttpServletRequest request) {

		Long TripSheetID = null;

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			TripDailyTimeIncome TripIncome = TripDailySheetService.Get_TripDaily_TIME_Income(TripIncomeID,
					userDetails.getCompany_id());

			if (TripIncome != null) {
				TripSheetID = TripIncome.getTripDailysheet().getTRIPDAILYID();

				TripDailySheetService.delete_TripDaily_TIME_Income(TripIncome.getTDTIMEID(),
						userDetails.getCompany_id());

				TripDailySheetService.update_TripDaily_TotalTimeIncome_INCOME_COLLECTION(TripSheetID);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/addDailyTimeIncome.in?ID=" + TripSheetID + "");
	}

	// this add Trip Daily Fuel Entries in The List
	@RequestMapping(value = "/addTripDailyFuel", method = RequestMethod.GET)
	public ModelAndView addTSFuel(@RequestParam("ID") final Long tripdailyid, final HttpServletRequest result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			TripDailySheetDto TripCol = TripDailySheetService.Get_Showing_TripDaily_Sheet(tripdailyid, userDetails);
			// get the Trip sheet ID to Details
			model.put("TripDaily", TDBL.Show_TripDailySheet_page(TripCol));

			List<FuelDto> ShowAmount = fuelService.Get_TripSheet_IN_FuelTable_List(tripdailyid, userDetails.getCompany_id(), userDetails.getId());
			model.put("fuel", FuBL.prepareListofFuel(ShowAmount));

			model.put("fTDiesel", FuBL.prepareTotal_Tripsheet_fuel_details(ShowAmount));
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName();

			UserProfileDto Orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(name);

			model.put("PAIDBY", Orderingname.getFirstName() + "_" + Orderingname.getLastName());
			model.put("place", Orderingname.getBranch_name());
			model.put("placeId", Orderingname.getBranch_id());

			HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id()
					,PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			HashMap<String, Object> config = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id()
					,PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			
			model.put(FuelConfigurationConstants.SHOW_VEHICLE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_VEHICLE_COL, true));
			model.put(FuelConfigurationConstants.SHOW_VEHICLE_DEPOT_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_VEHICLE_DEPOT_COL, true));
			model.put(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL, true));
			model.put(FuelConfigurationConstants.SHOW_REFERENCE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_REFERENCE_COL, true));
			model.put(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK, configuration.getOrDefault(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK, true));
			model.put(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION, true));
			model.put(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD, true));
			model.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_TRIPDAILY_SHEET, config.getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_TRIPDAILY_SHEET, true));
			model.put(VehicleConfigurationContant.ALLOWED_PER_DIFF_IN_ODOMETER, config.getOrDefault(VehicleConfigurationContant.ALLOWED_PER_DIFF_IN_ODOMETER, true));
			model.put("tripRoute", TripRouteService.getTripRouteApproxKm(TripCol.getTRIP_ROUTE_ID()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("add_TripDaily_FuelAdd", model);
	}

	@RequestMapping(value = "/addCloseTripDaily", method = RequestMethod.GET)
	public ModelAndView CloseTripDailyRoute(@RequestParam("ID") final Long tripdailyid,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			HashMap<String, Object>  configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_COLLECTION_CONFIG);
			HashMap<String, Object> 	companyconfig	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIP_COLLECTION_CONFIG);
			TripDailySheetDto TripCol = TripDailySheetService.Get_Showing_TripDaily_Sheet(tripdailyid, userDetails);
			// get the Trip sheet ID to Details
			model.put("TripDaily", TDBL.Show_TripDailySheet_page(TripCol));

			model.put("CurrentOdometer",
					vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(TripCol.getVEHICLEID()));
			
			
			model.put("tripRoute", TripRouteService.getTripRouteApproxKm(TripCol.getTRIP_ROUTE_ID()));

			// get Trip sheet id to get all Expense Details
			model.put("TripColExpense",
					TripDailySheetService.findAll_TripDailySheet_ID_Expense(tripdailyid, userDetails.getCompany_id()));

			// get Trip sheet id to get all Income Details
			model.put("TripColIncome",
					TripDailySheetService.findAll_TripDailySheet_ID_Income(tripdailyid, userDetails.getCompany_id()));

			List<DriverSalaryAdvanceDto> DriverAdvanvce = DriverSalaryAdvanceService
					.List_OF_TRIPDAILYSHEET_ID_PENALTY_DETAILS(tripdailyid,
							DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY, userDetails.getCompany_id());

			model.put("DriverAdvanvce", DriverAdvanvce);
			model.put("companyconfig", companyconfig);
			

			DecimalFormat df = new DecimalFormat("##,##,##0");

			df.setMaximumFractionDigits(0);
			Double TotalExpense = TripCol.getTOTAL_EXPENSE();
			if (TripCol.getTOTAL_EXPENSE() == null) {
				TotalExpense = 0.0;
			}
			model.put("expenseTotal", df.format(TotalExpense));

			df.setMaximumFractionDigits(0);
			Double Totalincome = TripCol.getTOTAL_INCOME();
			if (TripCol.getTOTAL_INCOME() == null) {
				Totalincome = 0.0;
			}
			model.put("incomeTotal", df.format(Totalincome));

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userName = (CustomUserDetails) auth.getPrincipal();

			model.put("CloseBy", userName.getFirstName() + "_" + userName.getLastName());

			df.setMaximumFractionDigits(2);
			Double Totalexpense = 0.0;
			if(TripCol.getTOTAL_EXPENSE() == null ) {
				TripCol.setTOTAL_EXPENSE(0.0) ;
			}
			if(TripCol.getTRIP_OVERTIME() == null) {
				TripCol.setTRIP_OVERTIME(0.0);
			}
			Totalexpense = TripCol.getTOTAL_EXPENSE() + TripCol.getTRIP_OVERTIME();
			if(TripCol.getTOTAL_WT() == null) {
				TripCol.setTOTAL_WT(0.0);
			}
			Double NetIncome = Totalincome - TripCol.getTOTAL_WT();
			Double TotalBALANCE = 0.0;
			
			if(!(boolean) configuration.get(TripDailySheetCollectionConfiguration.DIFF_BALANCE_FORMULA)) {
				TotalBALANCE = round((NetIncome - Totalexpense), 2);
				
			}else {
				//Double fuelAmount = TripDailySheetService.getFuelExpensesOfTrip(TripCol.getTRIPDAILYID(), userDetails.getCompany_id());
				TotalBALANCE = round((Totalincome - TripCol.getTOTAL_EXPENSE()), 2);
			}
			
			if (TripCol.getTOTAL_BALANCE() == null) {
				TotalBALANCE = 0.0;
			}
			model.put("incomeBalance", TotalBALANCE);
			model.put("configuration", configuration);
			model.put("incomeBalanceAmount", df.format(TotalBALANCE));

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("closeTripDaily", model);
	}

	// update of vehicle status
	@RequestMapping(value = "/CloseTripDaily", method = RequestMethod.POST)
	public ModelAndView CloseTripDaily(@ModelAttribute("command") TripDailySheet TripCollection,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		Integer ClosingKM = 0;
		Integer UsageKM = 0;
		Double balance = 0.0;
		HashMap<Long, VehicleProfitAndLossTxnChecker>			expenseTxnCheckerHashMap		= null;
		HashMap<Long, TripSheetExpenseDto> 					tripSheetExpenseHM				= null;
		HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>		incomeTxnCheckerHashMap			= null;
		HashMap<Long, TripSheetIncomeDto> 					tripSheetIncomeDtoHM			= null;
		VehicleProfitAndLossTxnChecker							driverSalaryTxn					= null;
		VehicleProfitAndLossDto									driverSalaryExpense				= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			
			tripSheetExpenseHM			= new HashMap<>();
			expenseTxnCheckerHashMap	= new HashMap<>();
			incomeTxnCheckerHashMap		= new HashMap<>();
			tripSheetIncomeDtoHM		= new HashMap<>();
			
			HashMap<String, Object>		configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_COLLECTION_CONFIG);
			TripDailySheetDto TripCol = TripDailySheetService
					.Get_Showing_TripDaily_Sheet(TripCollection.getTRIPDAILYID(), userDetails);
			if (TripCollection.getTRIP_CLOSE_KM() != null) {
				ClosingKM = TripCollection.getTRIP_CLOSE_KM();

				UsageKM = ClosingKM - TripCol.getTRIP_OPEN_KM();
			}

			Double TOTAL_NET_INCOME = 0.0, TotalExpense = 0.0, Income = 0.0, Expense = 0.0, OverTime = 0.0,
					TOTAL_WT = 0.0;

			if (TripCol.getTOTAL_INCOME() != null && TripCol.getTOTAL_INCOME() != 0) {
				Income = TripCol.getTOTAL_INCOME();
			}

			if (TripCol.getTOTAL_EXPENSE() != null && TripCol.getTOTAL_EXPENSE() != 0) {
				Expense = TripCol.getTOTAL_EXPENSE();
			}
			if (TripCol.getTRIP_OVERTIME() != null && TripCol.getTRIP_OVERTIME() != 0) {
				OverTime = TripCol.getTRIP_OVERTIME();
			}

			if (TripCol.getTOTAL_WT() != null && TripCol.getTOTAL_WT() != 0) {
				TOTAL_WT = TripCol.getTOTAL_WT();
			}
			TotalExpense = Expense + OverTime;
			TOTAL_NET_INCOME = Income - TOTAL_WT;
			if(!(boolean) configuration.get(TripDailySheetCollectionConfiguration.DIFF_BALANCE_FORMULA)) {
				balance = round((TOTAL_NET_INCOME - TotalExpense), 2);
			}else {
				
				balance = round((Income - Expense), 2);
			}
			

			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp LASTUPDATED = new java.sql.Timestamp(currentDateUpdate.getTime());

			TripDailySheetService.Update_Close_TripDailySheet(ClosingKM, UsageKM,
					TripDailySheetStatus.TRIP_STATUS_CLOSED, TOTAL_NET_INCOME, balance, LASTUPDATED,
					TripCol.getTRIPDAILYID(), userDetails.getCompany_id(), userDetails.getId(), TripCollection.getNoOfRoundTrip());
			
			
			
			/**
			 * Transaction Checker For Expense
			 */
			List<TripDailyExpenseDto> 	expenseList	=	TripDailySheetService.findAll_TripDailySheet_ID_Expense(TripCol.getTRIPDAILYID(), userDetails.getCompany_id());
			VehicleProfitAndLossTxnChecker		profitAndLossExpenseTxnChecker	= null;
			if(expenseList != null) {
				for(TripDailyExpenseDto	expenseDto : expenseList) {
					if(expenseDto.getExpenseAmount() > 0.0 && (expenseDto.getFuel_id() == null || expenseDto.getFuel_id() == 0) ) {
						
						ValueObject		object	= new ValueObject();
						
						object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
						object.put(VehicleProfitAndLossDto.TRANSACTION_ID, TripCol.getTRIPDAILYID());
						object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
						object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
						object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
						object.put(VehicleProfitAndLossDto.TRANSACTION_VID, TripCol.getVEHICLEID());
						object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, expenseDto.getExpenseId());
						object.put(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, expenseDto.getTripExpenseID());
						
						profitAndLossExpenseTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);
						
						vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossExpenseTxnChecker);
						
						expenseTxnCheckerHashMap.put(profitAndLossExpenseTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossExpenseTxnChecker);
						
						TripSheetExpenseDto	dto	= new TripSheetExpenseDto();
						dto.setExpenseId(expenseDto.getExpenseId());
						dto.setExpenseAmount(expenseDto.getExpenseAmount());
						
						tripSheetExpenseHM.put(expenseDto.getTripExpenseID(), dto);
					}
				}
			}
			
			List<TripDailyIncomeDto>	tripSheetIncomeList		=		TripDailySheetService.findAll_TripDailySheet_ID_Income(TripCol.getTRIPDAILYID(), userDetails.getCompany_id());
			if(tripSheetIncomeList != null) {
				
				for(TripDailyIncomeDto	incomeDto : tripSheetIncomeList) {
					if(incomeDto.getIncomeAmount() > 0.0) {
						
						ValueObject		incomeObject	= new ValueObject();
						
						
						incomeObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
						incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, TripCol.getTRIPDAILYID());
						incomeObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_INCOME);
						incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
						incomeObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
						incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, TripCol.getVEHICLEID());
						incomeObject.put(VehicleProfitAndLossDto.TXN_INCOME_ID, incomeDto.getIncomeId());
						incomeObject.put(VehicleProfitAndLossDto.TRIP_INCOME_ID, incomeDto.getTripincomeID());
						
						VehicleProfitAndLossIncomeTxnChecker		profitAndLossIncomeTxnChecker	= txnCheckerBL.getVehicleProfitAndLossIncomeTxnChecker(incomeObject);
						//vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossIncomeTxnChecker);
						vehicleProfitAndLossIncomeTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossIncomeTxnChecker);
						
						incomeTxnCheckerHashMap.put(profitAndLossIncomeTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossIncomeTxnChecker);
						
						TripSheetIncomeDto	dto = new TripSheetIncomeDto();
						dto.setIncomeId(incomeDto.getIncomeId());
						dto.setIncomeAmount(incomeDto.getIncomeAmount());
						
						tripSheetIncomeDtoHM.put(incomeDto.getTripincomeID(), dto);
					}
				}
				
			}
			

			model.put("save", true);

			// validate TripDailyRouteSheet date Or not
			List<TripDailyRouteSheet> GroupCollection = TripDailySheetService.Validate_TripDailyRoute_SheetIs_or_not(
					driverAttendanceFormat.format(TripCol.getTRIP_OPEN_DATE_D()), TripCol.getTRIP_ROUTE_ID(),
					TripCol.getVEHICLE_GROUP_ID(), userDetails.getCompany_id());

			if (GroupCollection != null && !GroupCollection.isEmpty()) {
				for (TripDailyRouteSheet tripGroupCollection : GroupCollection) {
					TripDailySheetService.update_TripDailyRoute_already(
							driverAttendanceFormat.format(tripGroupCollection.getTRIP_OPEN_DATE()),
							tripGroupCollection.getTRIP_ROUTE_ID(), tripGroupCollection.getVEHICLE_GROUP_ID(),
							tripGroupCollection.getTRIPROUTEID(), userDetails.getCompany_id());
					break;
				}

			} else {

				// check Group already Not Created in Create New Group Date
				TripDailyRouteSheet GropTripCol = TDBL.Collection_Trip_toAdd_RouteCollection(TripCol, UsageKM,
						TOTAL_NET_INCOME, balance);
				GropTripCol.setCOMPANY_ID(userDetails.getCompany_id());
				TripDailySheetService.add_TripDailyRoute_Collection(GropTripCol);
			}

			// this Update Current Odometer in Vehicle Getting Trip
			// Daily Sheet
			Update_Current_OdometerinVehicle_getTripDailySheetTo(TripCol, TripCollection.getTRIP_CLOSE_KM());

			try {
				if (TripCol.getTRIP_DRIVER_ID() != 0) {

					// this Driver Attendance
					Add_TripDailySheet_TO_driver_Attendance(TripCol.getTRIP_DRIVER_ID(), TripCol.getTRIP_DRIVER_NAME(),
							TripCol);

				}

				if (TripCol.getTRIP_CONDUCTOR_ID() != 0) {

					// this Conductor Attendance
					Add_TripDailySheet_TO_driver_Attendance(TripCol.getTRIP_CONDUCTOR_ID(),
							TripCol.getTRIP_CONDUCTOR_NAME(), TripCol);

				}

				if (TripCol.getTRIP_CLEANER_ID() != 0) {

					// this Cleaner Attendance
					Add_TripDailySheet_TO_driver_Attendance(TripCol.getTRIP_CLEANER_ID(),
							TripCol.getTRIP_CLEANER_NAME(), TripCol);

				}
				
				
				 List<DriverAttendanceDto>	attendanceList	=	DriverAttendanceService.getDriverAttandanceListOfTripSheet(TripCol.getTRIPDAILYID(), userDetails.getCompany_id());
				 if(attendanceList != null && !attendanceList.isEmpty()) {
					 
					 driverSalaryExpense	= new VehicleProfitAndLossDto();
						
						driverSalaryExpense.setTxnTypeId(TripCol.getTRIPDAILYID());
						driverSalaryExpense.setExpenseId(TripCol.getTRIPDAILYID());
						driverSalaryExpense.setVid(TripCol.getVEHICLEID());
						driverSalaryExpense.setTransactionDateTime(new Timestamp(TripCol.getTRIP_OPEN_DATE_D().getTime()));
						
					 	
						for(DriverAttendanceDto	attendanceDto : attendanceList) {
							Double	salary	= 0.0;
							
							if(attendanceDto.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_DAY) {
								salary	= attendanceDto.getPerDaySalary();
							}else if(attendanceDto.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_TRIP) {
								salary	= attendanceDto.getPerDaySalary();
							}else if(attendanceDto.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_KM) {
								salary	= attendanceDto.getPerDaySalary() * attendanceDto.getTripUsageKM();
							}else {
								salary	= attendanceDto.getPerDaySalary();
							}
							
							if(salary != null) {
								driverSalaryExpense.setTxnAmount((salary + driverSalaryExpense.getTxnAmount()));
							}
							
					 }
					 
						if(driverSalaryExpense.getTxnAmount() > 0) {
							
							ValueObject		object	= new ValueObject();
							
							object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
							object.put(VehicleProfitAndLossDto.TRANSACTION_ID, TripCol.getTRIPDAILYID());
							object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
							object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY);
							object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
							object.put(VehicleProfitAndLossDto.TRANSACTION_VID, TripCol.getVEHICLEID());
							object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, Integer.parseInt(TripCol.getTRIPDAILYID()+""));

							driverSalaryTxn	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);

							vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(driverSalaryTxn);
						}
					 	
						
						
				 }
				
				
				if(tripSheetExpenseHM != null && tripSheetExpenseHM.size() > 0) {
					
					ValueObject	valueObject	= new ValueObject();
					
					
					TripSheet	tripSheet = new TripSheet();
					tripSheet.setVid(TripCol.getVEHICLEID());
					tripSheet.setTripSheetID(TripCol.getTRIPDAILYID());
					tripSheet.setClosetripDate(TripCol.getTRIP_OPEN_DATE_D());
					
					valueObject.put("tripSheet", tripSheet);
					valueObject.put("userDetails", userDetails);
					valueObject.put("tripSheetExpenseHM", tripSheetExpenseHM);
					valueObject.put("expenseTxnCheckerHashMap", expenseTxnCheckerHashMap);
					
					vehicleProfitAndLossService.runThreadForTripSheetExpenses(valueObject);
				}
				
				if(tripSheetIncomeDtoHM != null && tripSheetIncomeDtoHM.size() > 0) {
					TripSheet	tripSheet = new TripSheet();
					tripSheet.setVid(TripCol.getVEHICLEID());
					tripSheet.setTripSheetID(TripCol.getTRIPDAILYID());
					tripSheet.setClosetripDate(TripCol.getTRIP_OPEN_DATE_D());
					
					ValueObject	valueObject	= new ValueObject();
					valueObject.put("tripSheet",tripSheet);
					valueObject.put("userDetails", userDetails);
					valueObject.put("tripSheetIncomeHM", tripSheetIncomeDtoHM);
					valueObject.put("incomeTxnCheckerHashMap", incomeTxnCheckerHashMap);
					
					vehicleProfitAndLossService.runThreadForTripSheetIncome(valueObject);
				}
				
				if(driverSalaryTxn != null && driverSalaryExpense.getTxnAmount() > 0) {
					
					ValueObject	valueObject	= new ValueObject();
					valueObject.put("driverSalaryExpense", driverSalaryExpense);
					valueObject.put("userDetails", userDetails);
					valueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, driverSalaryTxn.getVehicleProfitAndLossTxnCheckerId());
					valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY);
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

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("alreadyTripSheet", true);
			return new ModelAndView("redirect:/newTripDaily.in", model);
		}
		return new ModelAndView("redirect:/showTripDaily.in?ID=" + TripCollection.getTRIPDAILYID() + "");
	}

	// This ReOpen the Value Off TripDailySheet Table
	@RequestMapping(value = "/reopenTripDaily", method = RequestMethod.GET)
	public ModelAndView reopenTripDaily(@RequestParam("ID") final Long TripCollectionID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (TripCollectionID != null) {
				
				TripDailySheetDto CollectionSheet = (TripDailySheetService
						.ReOpen_TripDaily_Sheet_ClosedOnly(TripCollectionID, userDetails.getCompany_id()));
				
				if(CollectionSheet != null) {
					
					if( CollectionSheet.getVEHICLEID() != null) {
							ValueObject		valueObject	= new ValueObject();
							valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
							valueObject.put("vid", CollectionSheet.getVEHICLEID());
							valueObject.put("companyId", userDetails.getCompany_id());
							valueObject.put("txnTypeId", TripCollectionID);
							valueObject.put("userDetails", userDetails);
							valueObject.put("transactionDate", DateTimeUtility.geTimeStampFromDate(CollectionSheet.getTRIP_OPEN_DATE_D()));
							valueObject.put("expenseList", TripDailySheetService.findAll_TripDailySheet_ID_Expense(TripCollectionID, userDetails.getCompany_id()));
							valueObject.put("incomeList", TripDailySheetService.findAll_TripDailySheet_ID_Income(TripCollectionID, userDetails.getCompany_id()));
							
							vehicleProfitAndLossService.runThreadForDeleteExpenseDetailsOFTripSheet(valueObject);
							
						if (CollectionSheet != null) {
							 VehicleProfitAndLossDto									driverSalaryExpense				= null;
							List<VehicleExpenseDetails>	vehicleExpenseDetails	= vehicleExpenseDetailsService.getVehicleExpenseDetailsList(TripCollectionID, userDetails.getCompany_id() , CollectionSheet.getVEHICLEID());
							
							if(vehicleExpenseDetails != null && !vehicleExpenseDetails.isEmpty()) {
								 List<DriverAttendanceDto>	attendanceList	=	DriverAttendanceService.getDriverAttandanceListOfTripSheet(TripCollectionID, userDetails.getCompany_id());
								 if(attendanceList != null && !attendanceList.isEmpty()) {
									 	
									
										driverSalaryExpense	= new VehicleProfitAndLossDto();
										
										driverSalaryExpense.setTxnTypeId(TripCollectionID);
										driverSalaryExpense.setExpenseId(TripCollectionID);
									 	
									 for(DriverAttendanceDto	attendanceDto : attendanceList) {
											Double	salary	= 0.0;
											
											if(attendanceDto.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_DAY) {
												salary	= attendanceDto.getPerDaySalary();
											}else if(attendanceDto.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_TRIP) {
												salary	= attendanceDto.getPerDaySalary();
											}else if(attendanceDto.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_KM) {
												salary	= attendanceDto.getPerDaySalary() * attendanceDto.getTripUsageKM();
											}else {
												salary	= attendanceDto.getPerDaySalary();
											}
											
											if(salary != null) {
												driverSalaryExpense.setTxnAmount((salary + driverSalaryExpense.getTxnAmount()));
											}
											
									 }
								 }
								 
							}

							String TripCollectionDate = driverAttendanceFormat.format(CollectionSheet.getTRIP_OPEN_DATE_D());
							List<TripDailyRouteSheet> GroupSheet = TripDailySheetService.List_TripDailySheetService_closeDate(
									CollectionSheet.getVEHICLE_GROUP_ID(), TripCollectionDate,
									TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());
							if (GroupSheet != null && !GroupSheet.isEmpty()) {

								model.put("alreadyClosed", true);
								return new ModelAndView("redirect:/showTripDaily.in?ID=" + TripCollectionID + "", model);

							} else {

								TripDailySheetService.Update_ReOpen_Status_TripDailySheet(CollectionSheet.getTRIPDAILYID(),
										TripDailySheetStatus.TRIP_STATUS_OPEN, userDetails.getCompany_id());

								TripDailySheetService.Update_ReOpen_To_Subtrack_TripDailyRouteSheet_to_tripDaily(
										driverAttendanceFormat.format(CollectionSheet.getTRIP_OPEN_DATE_D()),
										CollectionSheet.getVEHICLE_GROUP_ID(), CollectionSheet.getTRIP_ROUTE_ID(),
										userDetails.getCompany_id());

								// After Remove Attendance Details Code
								DriverAttendanceService.DELETE_DRIVERATTENDANCE_TRIPSHEET_ID(TripCollectionID, userDetails.getCompany_id());

								model.put("RO", true);
							}
							if(driverSalaryExpense != null && driverSalaryExpense.getTxnAmount() > 0) {
								
								ValueObject		valueDelObject	= new ValueObject();
								valueDelObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY);
								valueDelObject.put("txnAmount", driverSalaryExpense.getTxnAmount());
								valueDelObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(CollectionSheet.getTRIP_OPEN_DATE_D()));
								valueDelObject.put("txnTypeId", TripCollectionID);
								valueDelObject.put("expenseId", TripCollectionID);
								valueDelObject.put("vid", CollectionSheet.getVEHICLEID());
								valueDelObject.put("companyId", userDetails.getCompany_id());
								
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
						} 
						}
					
				}

					
			}

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/showTripDaily.in?ID=" + TripCollectionID + "", model);
	}

	@RequestMapping(value = "/closeDailyTripDaily", method = RequestMethod.POST)
	public ModelAndView closeDailyTripDaily(@RequestParam("closedate") final String TripCollectionDate,
			@RequestParam("VEHICLEGROUP_ID") final long VEHICLEGROUP_ID, RedirectAttributes redir,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		VehicleGroup group = null;
		HashMap<String, Object>  configuration	= null;
		try {
			if (TripCollectionDate != null) {
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_COLLECTION_CONFIG);
				group = vehicleGroupService.getVehicleGroupByID(VEHICLEGROUP_ID);

				List<TripDailySheetDto> OpenCollectionSheet = TripDailySheetService
						.List_TripDailySheet_closeDate_with_Group(TripCollectionDate, VEHICLEGROUP_ID,
								TripDailySheetStatus.TRIP_STATUS_OPEN, userDetails.getCompany_id());

				if (OpenCollectionSheet != null && !OpenCollectionSheet.isEmpty()) {
					String success = "";
					for (TripDailySheetDto tripCollectionSheet : OpenCollectionSheet) {

						success += "<a href=\"showTripDaily.in?ID=" + tripCollectionSheet.getTRIPDAILYID()
								+ "\" target=\"_blank\">TS-" + tripCollectionSheet.getTRIPDAILYNUMBER()
								+ "  <i class=\"fa fa-external-link\"></i></a> ,";
					}

					redir.addFlashAttribute("closeID", success);
					return new ModelAndView("redirect:/newTripDaily.in?closeTrip=true", model);
				} else {

					List<TripDailyRouteSheetDto> GroupSheet = TripDailySheetService
							.List_TripDailyRouteSheet_closeDate_to_get_details(TripCollectionDate, VEHICLEGROUP_ID,
									TripDailySheetStatus.TRIP_STATUS_OPEN, userDetails.getCompany_id());
					
					
					if (GroupSheet != null && !GroupSheet.isEmpty()) {
						List<TripDailySheetDto> CollectionSheet = TripDailySheetService
								.List_TripDailySheet_closeDate_with_Group(TripCollectionDate, VEHICLEGROUP_ID,
										TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());
						
						HashMap<String, TripDailySheetDto>  fuelAmountHm	= null;

						model.put("TRIP_OPEN_DATE", TripCollectionDate);
						model.put("SEARCHDATE", TripCollectionDate);
						model.put("VEHICLEGROUP", group.getvGroup());
						model.put("VEHICLEGROUPID", group.getGid());
						model.put("configuration", configuration);
						Double[] totalGroupSheet = null;
						if(!(boolean) configuration.get(TripDailySheetCollectionConfiguration.DIFF_BALANCE_FORMULA)) {
							totalGroupSheet = TDBL
									.Group_Total_details_to_TripDaily_all_total_CLOSED_VALUE(GroupSheet,CollectionSheet);
						}else {
							totalGroupSheet = TDBL
									.Group_Total_details_to_TripDaily_all_total_VALUE(GroupSheet,CollectionSheet);
						}
						
						if (totalGroupSheet != null) {
							model.put("TotalUsageKM", totalGroupSheet[0]);
							model.put("TotalDiesel", totalGroupSheet[1]);
							model.put("TotalKMPL", totalGroupSheet[2]);
							model.put("TotalPassenger", totalGroupSheet[3]);
							model.put("TotalPass", totalGroupSheet[4]);
							model.put("TotalRFID", totalGroupSheet[5]);
							model.put("TotalRFIDAmount", totalGroupSheet[6]);
							model.put("TotalCollection", totalGroupSheet[7]);
							model.put("TotalWT", totalGroupSheet[8]);
							model.put("TotalNetCollection", totalGroupSheet[9]);
							model.put("TotalExpense", totalGroupSheet[10]);
							model.put("TotalOT", totalGroupSheet[11]);
							model.put("TotalBalance", totalGroupSheet[12]);
							model.put("TotalChaloKm", totalGroupSheet[13]);
							model.put("TotalChaloAmount", totalGroupSheet[14]);
						}
						if((boolean) configuration.get(TripDailySheetCollectionConfiguration.SHOW_DIESEL_AMOUNT)) {
							fuelAmountHm	= TripDailySheetService.getFuelAmountOfTripSheet(TripCollectionDate, VEHICLEGROUP_ID,
									TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());
							if(fuelAmountHm != null && fuelAmountHm.size() > 0) {
								Double totalDieselAmount = 0.0;
								 for (Map.Entry<String, TripDailySheetDto> entry : fuelAmountHm.entrySet()) {
								        totalDieselAmount += entry.getValue().getTRIP_DIESEL_AMOUNT();
								 }
								 model.put("totalDieselAmount", round(totalDieselAmount, 2));
							}
							model.put("TDRoute", TDBL.CloseDaily_tripDailyRoute_Sheet(GroupSheet, CollectionSheet, fuelAmountHm));
							
						}else {
							model.put("TDRoute", TDBL.CloseDaily_tripDailyRoute_Sheet(GroupSheet, CollectionSheet));
						}

						

					} else {
						model.put("alreadyClosed", true);
						return new ModelAndView("redirect:/closeDailyGroupShow.in?date=" + TripCollectionDate + "&VG="
								+ VEHICLEGROUP_ID + "", model);
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		} finally {
			userDetails = null;
			group = null;
		}
		return new ModelAndView("closeDayGroupTripDaily", model);
	}

	@RequestMapping(value = "/ReOpenDailyTripDaily", method = RequestMethod.POST)
	public ModelAndView ReOpenDailyTripDaily(@RequestParam("closedate") final String TripCollectionDate,
			@RequestParam("VEHICLEGROUP_ID") final long VEHICLEGROUP_ID, RedirectAttributes redir,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails   userDetails 				= null;
		CashBookName 		cashBookName 				= null;
		CashBookBalance 	currentdatevalidate			= null;
		VehicleGroup		vehicleGroup				= null;
		CashBook			cashBook					= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<TripDailyRouteSheetDto> GroupSheet = TripDailySheetService
					.List_TripDailyRouteSheet_closeDate_to_get_details(TripCollectionDate, VEHICLEGROUP_ID,
							TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());
			
			if (GroupSheet != null && !GroupSheet.isEmpty()) {
				java.util.Date date = driverAttendanceFormat.parse(TripCollectionDate);
				java.sql.Date fromDate = new java.sql.Date(date.getTime());
				
				cashBookName = cashBookNameService.getCashBookByVehicleGroupId(VEHICLEGROUP_ID, userDetails.getCompany_id());
				currentdatevalidate	= CashBookService.Validate_Last_DayCashBook_CloseOrNot(fromDate, cashBookName.getNAMEID(), CashBookStatus.CASH_BOOK_STATUS_CLOSED, userDetails.getCompany_id());
				if (currentdatevalidate == null) {
					
					vehicleGroup	= vehicleGroupService.getVehicleGroupByID(VEHICLEGROUP_ID);
					TripDailyGroupCollection totalGroupSheet = TripDailySheetService.GET_TripDailyGroupCollectionOnCloseDate(fromDate, VEHICLEGROUP_ID, TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());
					
					/*
					 * Logic for CashBook
					 */
					
					String CVoucher = vehicleGroup.getvGroup().substring(0, 3) + "-" + dateFormat.format(fromDate) + "-" + totalGroupSheet.getTRIPGROUPID()
					+ "-CR";
					
					cashBook	= CashBookService.getCashBookToReopenTripCollection(cashBookName.getNAMEID(), fromDate, CVoucher, userDetails.getCompany_id());
					
					
					if(cashBook != null) {
						CashBookService.Delete_CashBook_ID(cashBook.getCASHID(), userDetails.getCompany_id());
						CashBookService.updateCashBookBalanceToReopenTripSheet(cashBook.getCASH_AMOUNT(), cashBook.getCASH_BOOK_ID(), cashBook.getCASH_DATE(), userDetails.getCompany_id());
					}
					
					/*
					 * Re Open Trip Route
					 */
					
					TripDailySheetService.Update_TripDailyRoute_Closed_Status(
							TripDailySheetStatus.TRIP_STATUS_OPEN, TripCollectionDate, VEHICLEGROUP_ID,
							userDetails.getCompany_id());
					/*
					 * delete trip daily group
					 */
					
					
					
					TripDailySheetService.deleteTripDailyGroupCollectionById(TripCollectionDate, VEHICLEGROUP_ID, userDetails.getCompany_id());
					
					List<TripDailyAllGroupDay> validateAllCollection = TripDailySheetService
							.Validate_All_GroupDepartment_CloseOrNot_list(totalGroupSheet.getTRIP_OPEN_DATE(),
									TripDailySheetStatus.TRIP_STATUS_OPEN, userDetails.getCompany_id());
					if (validateAllCollection != null && !validateAllCollection.isEmpty()) {
						
						// Already Created Group entries Only Update
						for (TripDailyAllGroupDay tripDailyAllGroupDay : validateAllCollection) {
							TripDailySheetService.Subtract_TripDailyAllGroupDay_Collection_To_GroupCollection(
									totalGroupSheet, tripDailyAllGroupDay.getTRIPALLGROUPID());
							break;
						}

					} 
					
				}else {
					model.put("closedCB", true);
					return new ModelAndView("redirect:/newTripDaily.in", model);
				}
				model.put("reOpenSuccess", true);
			}else {
				model.put("alreadyOpen", true);
				return new ModelAndView("redirect:/newTripDaily.in", model);
			
			}
		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		} finally {
			userDetails 				= null;
			cashBookName 				= null;
			currentdatevalidate			= null;
			cashBook					= null;
			vehicleGroup				= null;
		}
		return new ModelAndView("redirect:/newTripDaily.in", model);
	}

	
	
	@RequestMapping(value = "/closeDayTripDailyGroup", method = RequestMethod.POST)
	public ModelAndView closeDayTripDailyGroup(@RequestParam("CLOSED_DATE") final String TripCollectionDate,
			@RequestParam("VEHICLEGROUP") final String VEHICLEGROUP,
			@RequestParam("VEHICLEGROUPID") final long VEHICLEGROUPID,
			@RequestParam("CLOSED_REMARKS") final String CLOSED_REMARKS, RedirectAttributes redir,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		TripDailyGroupCollectionSequenceCounter sequenceCounter = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (TripCollectionDate != null) {
				HashMap<String, Object>		configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
				String linkingNotNeededToGroup = configuration.get("linkingNotNeededToGroup")+"";  
				
				String[] linkingNotNeededArray	= 	linkingNotNeededToGroup.split(",");
				
				List<Integer> cashBookIdList = new ArrayList<>();
 			    
				for(int i= 0; i< linkingNotNeededArray.length; i++) {
					cashBookIdList.add(Integer.parseInt(linkingNotNeededArray[i]));
				}
				
				
				// This Search TripDailyRouteSheet To Open Status and the value
				// get Total to All the value
				List<TripDailyRouteSheetDto> GroupSheet = TripDailySheetService
						.List_TripDailyRouteSheet_closeDate_to_get_details(TripCollectionDate, VEHICLEGROUPID,
								TripDailySheetStatus.TRIP_STATUS_OPEN, userDetails.getCompany_id());
				if (GroupSheet != null && !GroupSheet.isEmpty()) {

					// Prepare TripDailyGroupCollection Total Values
					TripDailyGroupCollection totalGroupSheet = TDBL
							.prepare_TripDailyGroupCollection_to_TripDaily_all_total(GroupSheet);
					if (totalGroupSheet != null) {

						try {
							java.util.Date date = driverAttendanceFormat.parse(TripCollectionDate);
							java.sql.Date fromDate = new Date(date.getTime());
							totalGroupSheet.setTRIP_OPEN_DATE(fromDate);
						} catch (Exception e) {
							e.printStackTrace();
						}

						totalGroupSheet.setTRIP_STATUS_ID(TripDailySheetStatus.TRIP_STATUS_CLOSED);
						totalGroupSheet.setTRIP_CLOSE_REMARKS(CLOSED_REMARKS);
						totalGroupSheet.setCOMPANY_ID(userDetails.getCompany_id());
						totalGroupSheet.setVEHICLE_GROUP_ID(VEHICLEGROUPID);
						sequenceCounter = tripDailyGroupCollectionSequenceService
								.findNextSequenceNumber(userDetails.getCompany_id());
						if (sequenceCounter == null) {
							return new ModelAndView("redirect:/newTripDaily.in?sequenceCounterNotFound=true");
						}
						totalGroupSheet.setTRIPGROUPNUMBER(sequenceCounter.getNextVal());

						// Save One Location Department Wise Trip Daily
						// Collection Report
						TripDailySheetService.add_TripDailyGroup_Collection(totalGroupSheet);

						/**
						 * This Update IN TripDailyRoute Collection Open To CLosed If TripDailyGroup
						 * Closed To Close Route Status Details
						 **/
						TripDailySheetService.Update_TripDailyRoute_Closed_Status(
								TripDailySheetStatus.TRIP_STATUS_CLOSED, TripCollectionDate, VEHICLEGROUPID,
								userDetails.getCompany_id());

						// Below Close Group Balance To Create Cash BOOK Details
						boolean ClosedValidate = false, ClosedCurent = false;
						try {
							// get the issues Dto to save issues
							CashBookName cashBookName = cashBookNameService.getCashBookByVehicleGroupId(
									totalGroupSheet.getVEHICLE_GROUP_ID(), userDetails.getCompany_id());
							
							
							
							CashBook cash = CBBL.prepareCashBook_Value_TripDailyGroupCollection_TotalAmount(
									totalGroupSheet, VEHICLEGROUP);
							
							if (cashBookName != null) {
								cash.setCASH_BOOK_ID(cashBookName.getNAMEID());
								cash.setCASH_BOOK_NO(cashBookName.getCASHBOOK_NAME());
							}
							
							if(!cashBookIdList.contains(cashBookName.getNAMEID())) {
								
								int n = 1;
								// this check Before Begin Opening Balances
								Date openingDate = new Date(cash.getCASH_DATE().getTime() - n * 24 * 3600 * 1000);
								List<CashBookBalance> validateCloseLast = CashBookService
										.Validate_Last_DayCashBook_CloseOrNot_list(openingDate, cash.getCASH_BOOK_ID(),
												userDetails.getCompany_id());
								if (validateCloseLast != null && !validateCloseLast.isEmpty()) {
									for (CashBookBalance CloseLast : validateCloseLast) {

										if (CloseLast.getCASH_STATUS_ID() == CashBookStatus.CASH_BOOK_STATUS_CLOSED) {
											// check yesterday cash book closed
											// enter today entries
											ClosedValidate = true;
										} else {
											// this else condition only temper
											ClosedValidate = true;
										}

									}
								} else {

									// check yesterday cash book not closed enter
									// create close balance entries
									ClosedValidate = true;
									/** This Balance To Create New Date Balance */
									// get the Cash to save CashBalance
									CashBookBalance cashBalance = CBBL.prepare_CLOSED_LAST_Value(openingDate,
											cash.getCASH_BOOK_NO());
									// save to databases
									cashBalance.setCASH_BOOK_ID(cash.getCASH_BOOK_ID());
									cashBalance.setCOMPANY_ID(userDetails.getCompany_id());
									CashBookService.registerNewCashBookBalance(cashBalance);
								}
								// Checking last day entries close or not
								if (ClosedValidate) {

									try {
										CashBookBalance currentdatevalidate = CashBookService
												.Validate_Last_DayCashBook_CloseOrNot(cash.getCASH_DATE(),
														cash.getCASH_BOOK_ID(), CashBookStatus.CASH_BOOK_STATUS_CLOSED,
														userDetails.getCompany_id());
										if (currentdatevalidate != null) {
											ClosedCurent = false;
										} else {
											ClosedCurent = true;
										}
										if (ClosedCurent) {
											CashBookSequenceCounter bookSequenceCounter = cashBookSequenceService
													.findNextSequenceNumber(userDetails.getCompany_id());
											cash.setCASH_NUMBER(bookSequenceCounter.getNextVal());

											// save to databases
											CashBookService.registerNewCashBook(cash);

											String success = " " + cash.getCASH_NUMBER();
											redir.addFlashAttribute("successID", success);
											// show the driver list in all
											model.put("success", true);

											SaveToCashBookBalance_Update(cash);

										} else {

											return new ModelAndView("redirect:/addOldCashBook.in?closedCB=true");
										}
									} catch (Exception e) {
										e.printStackTrace();
										return new ModelAndView("redirect:/addOldCashBook.in?danger=true");
									}
								}
							}

						} catch (Exception e) {
							e.printStackTrace();
							return new ModelAndView("redirect:/addOldCashBook.in?danger=true");
						}

						// Below One Department Collection Amount to Update or
						// Save One Day wise OverAll Trip Collection Report

						// Validate Day Wise Total Trip Collection Close Or Not
						// and Created Or Not

						List<TripDailyAllGroupDay> validateAllCollection = TripDailySheetService
								.Validate_All_GroupDepartment_CloseOrNot_list(totalGroupSheet.getTRIP_OPEN_DATE(),
										TripDailySheetStatus.TRIP_STATUS_OPEN, userDetails.getCompany_id());
						if (validateAllCollection != null && !validateAllCollection.isEmpty()) {
							// Already Created Group entries Only Update
							for (TripDailyAllGroupDay tripDailyAllGroupDay : validateAllCollection) {
								TripDailySheetService.Update_TripDailyAllGroupDay_Collection_To_GroupCollection(
										totalGroupSheet, tripDailyAllGroupDay.getTRIPALLGROUPID());
								break;
							}

						} else {
							// Here If not Created To New Entries
							TripDailyAllGroupDay allGroup = TDBL
									.prepare_TD_GroupCollection_To_All_TD_Group_Day_Collection(totalGroupSheet);

							TripDailyAllGroupDaySequenceCounter counter = tripDailyAllGroupDaySequenceService
									.findNextSequenceNumber(userDetails.getCompany_id());
							if (counter == null) {
								return new ModelAndView("redirect:/newTripDaily.in?sequenceCounterNotFound=true");
							}
							allGroup.setTRIPALLGROUPNUMBER(counter.getNextVal());
							TripDailySheetService.add_TripDailyAllGroupDay_Collection(allGroup);

						}

					}

				} else {
					model.put("alreadyClosed", true);
					return new ModelAndView("redirect:/closeDailyGroupShow.in?date=" + TripCollectionDate + "&VG="
							+ VEHICLEGROUPID + "", model);
				}

			}

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		}
		model.put("success", true);
		return new ModelAndView(
				"redirect:/closeDailyGroupShow.in?date=" + TripCollectionDate + "&VG=" + VEHICLEGROUPID + "", model);
	}

	@RequestMapping(value = "/closeDailyGroupShow", method = RequestMethod.GET)
	public ModelAndView closeDailyColShow(@RequestParam("date") final String TripCollectionDate,
			@RequestParam("VG") final Long VEHICLEGROUP_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			
			
			HashMap<String, TripDailySheetDto>  fuelAmountHm	= null;
			if (TripCollectionDate != null) {
				
				HashMap<String, Object>   configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_COLLECTION_CONFIG);
				List<TripDailyRouteSheetDto> GroupSheet = TripDailySheetService
						.List_TripDailyRouteSheet_closeDate_to_get_details(TripCollectionDate, VEHICLEGROUP_ID,
								TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());
				if (GroupSheet != null && !GroupSheet.isEmpty()) {

					List<TripDailySheetDto> CollectionSheet = TripDailySheetService
							.List_TripDailySheet_closeDate_with_Group(TripCollectionDate, VEHICLEGROUP_ID,
									TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());
					
					if((boolean) configuration.get(TripDailySheetCollectionConfiguration.SHOW_DIESEL_AMOUNT)) {

						fuelAmountHm	= TripDailySheetService.getFuelAmountOfTripSheet(TripCollectionDate, VEHICLEGROUP_ID,
								TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());
						if(fuelAmountHm != null && fuelAmountHm.size() > 0) {
							Double totalDieselAmount = 0.0;
							 for (Map.Entry<String, TripDailySheetDto> entry : fuelAmountHm.entrySet()) {
							        totalDieselAmount += entry.getValue().getTRIP_DIESEL_AMOUNT();
							 }
							 model.put("totalDieselAmount", round(totalDieselAmount, 2));
						}
						model.put("TDRoute", TDBL.CloseDaily_tripDailyRoute_Sheet(GroupSheet, CollectionSheet, fuelAmountHm));
					
					}else {
						model.put("TDRoute", TDBL.CloseDaily_tripDailyRoute_Sheet(GroupSheet, CollectionSheet));
					}
					
					model.put("configuration", configuration);

					try {
						java.util.Date date = driverAttendanceFormat.parse(TripCollectionDate);
						java.sql.Date fromDate = new Date(date.getTime());
						/*	start	List Of TripDailysheet*/
						
						
						/*	end	List Of TripDailysheet*/
						TripDailyGroupCollectionDto GroupCollection = TripDailySheetService
								.GET_TripDailyGroupCollection_CloseDate(fromDate, VEHICLEGROUP_ID,
										TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());
						model.put("TDGroupCol", TDBL.CLOSED_DAILY_GROUP_SHEET(GroupCollection,CollectionSheet));

					} catch (Exception e) {
						e.printStackTrace();
					}
					model.put("SEARCHDATE", TripCollectionDate);

				} else {
					return new ModelAndView("redirect:/newTripDaily.in?nodata=true");
				}
			}
		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("closeDayGroupTripDailyShow", model);
	}

	@RequestMapping(value = "/editTripDaily", method = RequestMethod.GET)
	public ModelAndView editTripDaily(@RequestParam("ID") final Long TripCollectionID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (TripCollectionID != null) {
				CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();

				TripDailySheetDto CollectionSheet = TDBL.Update_TripSheet(
						TripDailySheetService.Get_Showing_TripDaily_Sheet(TripCollectionID, userDetails));
				if (CollectionSheet != null) {
					model.put("TripDaily", CollectionSheet);
				} else {
					return new ModelAndView("redirect:/newTripDaily.in?error=true");
				}
			}

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("editTripDaily", model);
	}

	@RequestMapping(value = "/updateTripDaily", method = RequestMethod.POST)
	public ModelAndView updateTripDaily(@ModelAttribute("command") TripDailySheetDto CollectionSheetDto,
			final HttpServletRequest request, final RedirectAttributes redirectAttributes) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			if (CollectionSheetDto.getVEHICLEID() != 0 && CollectionSheetDto.getTRIP_DRIVER_ID() != 0
					&& CollectionSheetDto.getTRIP_CONDUCTOR_ID() != 0 && CollectionSheetDto.getTRIP_ROUTE_ID() != 0) {
				TripDailySheetDto oldCollectionSheet = TripDailySheetService
						.Get_Showing_TripDaily_Sheet(CollectionSheetDto.getTRIPDAILYID(), userDetails);
				if (oldCollectionSheet != null) {

					TripDailySheet Sheet = TDBL.prepare_Model_UPDATE_TripCollectionSheet(CollectionSheetDto,
							oldCollectionSheet);
					TripDailySheetService.add_TripDailySheet(Sheet);
					redirectAttributes.addAttribute("update", true);
					model.put("update", true);

					Update_Current_OdometerinVehicle_getTripDailySheetTo(Sheet);
				}
			} else {
				return new ModelAndView("redirect:/addTripDaily.in?error=true");
			}

		} catch (Exception e) {
			redirectAttributes.addAttribute("already", true);
			model.put("already", true);
			LOGGER.error("TripSheet Page:", e);
			return new ModelAndView("redirect:/newTripDaily.in?error=true");

		}
		return new ModelAndView("redirect:/showTripDaily.in?ID=" + CollectionSheetDto.getTRIPDAILYID() + "");
	}

	@RequestMapping(value = "/deleteTripDaily", method = RequestMethod.GET)
	public ModelAndView deleteTripDaily(@RequestParam("ID") final Long TripCollectionID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			if (TripCollectionID != null) {
				TripDailySheetService.delete_TripDailyExpense_TRIPDAILYID(TripCollectionID,
						userDetails.getCompany_id());
				TripDailySheetService.delete_TripDailyIncome_TRIPDAILYID(TripCollectionID, userDetails.getCompany_id());

				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp DeletedTime = new java.sql.Timestamp(currentDateUpdate.getTime());

				TripDailySheetService.delete_TripDailySheet_TRIPDAILYID(userDetails.getId(), DeletedTime,
						TripCollectionID, userDetails.getCompany_id());

				// After Remove Attendance Details Code
				DriverAttendanceService.DELETE_DRIVERATTENDANCE_TRIPSHEET_ID(TripCollectionID, userDetails.getCompany_id());

				// After Remove penalty in tripsheet details
				DriverSalaryAdvanceService.DELETE_PENALTY_IN_TRIPDailySheet_Delete_ID(TripCollectionID);

				model.put("delete", true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("redirect:/newTripDaily.in", model);
	}

	@RequestMapping(value = "/addDailyPenalty", method = RequestMethod.GET)
	public ModelAndView addDailyPenalty(@RequestParam("ID") final Long tripdailyid, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (tripdailyid != null) {

				CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				TripDailySheetDto TripCol = TripDailySheetService.Get_Showing_TripDaily_Sheet(tripdailyid, userDetails);
				// get the Trip sheet ID to Details
				model.put("TripDaily", TDBL.Show_TripDailySheet_page(TripCol));

				List<DriverSalaryAdvanceDto> DriverAdvanvce = DriverSalaryAdvanceService.List_OF_TRIPDAILYSHEET_ID_PENALTY_DETAILS(tripdailyid,
								DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY, userDetails.getCompany_id());

				model.put("DriverAdvanvce", DriverAdvanvce);

				/**
				 * who Create this get email id to user profile details
				 */
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				CustomUserDetails userName = (CustomUserDetails) auth.getPrincipal();
				model.put("userName", userName.getFirstName());

				HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id()
						,PropertyFileConstants.PENALTY_CONFIGURATION_CONFIG);
				
				model.put(PenaltyConfigurationConstants.SHOW_MODE_OF_PAYMENT_COL, configuration.getOrDefault(PenaltyConfigurationConstants.SHOW_MODE_OF_PAYMENT_COL, true));
				model.put(PenaltyConfigurationConstants.SHOW_CASH_RECEIPT_COL, configuration.getOrDefault(PenaltyConfigurationConstants.SHOW_CASH_RECEIPT_COL, true));
				model.put(PenaltyConfigurationConstants.SHOW_PENALTY_DATE_COL, configuration.getOrDefault(PenaltyConfigurationConstants.SHOW_PENALTY_DATE_COL, true));
				model.put(PenaltyConfigurationConstants.SHOW_PENALTY_PAID_BY_COL, configuration.getOrDefault(PenaltyConfigurationConstants.SHOW_PENALTY_PAID_BY_COL, true));
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("addDailyPenalty", model);
	}

	/********************************
	 * This set Up Code
	 *******************************/

	// update balance update cash book to value
	private void SaveToCashBookBalance_Update(CashBook cash) throws Exception {

		CashBookBalance validateBalance = CashBookService.Validate_CashBookBalance_value(cash.getCASH_DATE(),
				cash.getCASH_BOOK_ID(), cash.getCOMPANY_ID());

		try {
			if (validateBalance != null) {
				Long CBBID = validateBalance.getCBBID();
				/**
				 * cash Balance Already Create in DB To Update old Balance to New
				 */
				if (cash.getPAYMENT_TYPE_ID() == CashBookPaymentType.PAYMENT_TYPE_DEBIT) {

					CashBookService.Update_DEBIT_CashBalance_SubtrackBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED,
							driverAttendanceFormat.format(cash.getCASH_APPROVALDATE()), cash.getCASH_BOOK_ID(),
							cash.getPAYMENT_TYPE_ID(), CBBID,cash.getCOMPANY_ID());
				} else {

					CashBookService.Update_CREDIT_CashBalance_AddBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED,
							driverAttendanceFormat.format(cash.getCASH_APPROVALDATE()), cash.getCASH_BOOK_ID(),
							cash.getPAYMENT_TYPE_ID(), CBBID,cash.getCOMPANY_ID());
				}

			} else {

				/** This Balance To Create New Date Balance */
				// get the Cash to save CashBalance
				CashBookBalance cashBalance = CBBL.prepareCashBookBalance_Value(cash);
				// save to databases
				CashBookService.registerNewCashBookBalance(cashBalance);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param trip_DRIVER_ID
	 * @param trip_DRIVER_NAME
	 * @param tripCol
	 */
	private void Add_TripDailySheet_TO_driver_Attendance(int trip_DRIVER_ID, String trip_DRIVER_NAME,
			TripDailySheetDto TripCol) {


		LocalDate start = LocalDate.parse(driverAttendanceFormat.format(TripCol.getTRIP_OPEN_DATE_D())),
				end = LocalDate.parse(driverAttendanceFormat.format(TripCol.getTRIP_OPEN_DATE_D()));

		LocalDate next = start.minusDays(1);
		while ((next = next.plusDays(1)).isBefore(end.plusDays(1))) {

			try {
				if (next != null) {

					DriverAttendance attendance = DBL.Driver_Attendance_to_tripDaily(trip_DRIVER_ID, trip_DRIVER_NAME,
							TripCol.getTRIPDAILYID(), TripCol.getTRIP_ROUTE_ID());
					/**
					 * Reported_Date converted String to date Format
					 */
					java.util.Date attendanceDate = driverAttendanceFormat.parse("" + next);
					java.sql.Date Reported_Date = new Date(attendanceDate.getTime());
					attendance.setATTENDANCE_DATE(Reported_Date);

					DriverAttendanceService.addDriverAttendance(attendance);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// this Update Current Odometer in Vehicle Getting Trip Daily Sheet
	public void Update_Current_OdometerinVehicle_getTripDailySheetTo(TripDailySheet Sheet) {
		List<ServiceReminderDto>		serviceList					= null;
		Integer currentODDMETER;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(Sheet.getVEHICLEID());

			try {
				if (Sheet.getVEHICLEID() != 0) {
					if (currentODDMETER < Sheet.getTRIP_OPEN_KM()) {
						
						vehicleService.updateCurrentOdoMeter(Sheet.getVEHICLEID(), Sheet.getTRIP_OPEN_KM(), userDetails.getCompany_id());
						ServiceReminderService.updateCurrentOdometerToServiceReminder(Sheet.getVEHICLEID(),
								Sheet.getTRIP_OPEN_KM(), userDetails.getCompany_id());
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(Sheet.getVEHICLEID(), userDetails.getCompany_id(), userDetails.getId());
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
								.prepareOdometerGetHistoryToTripDailySheet(Sheet);
						vehicleUpdateHistory.setCompanyId(Sheet.getCOMPANY_ID());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
					}
				}
			} catch (Exception e) {
				LOGGER.error("TripDaily Page:", e);
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void Update_Current_OdometerinVehicle_getTripDailySheetTo(TripDailySheetDto Sheet, Integer closingKm) {
		CustomUserDetails			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<ServiceReminderDto>	serviceList		= null;
		Integer 					currentODDMETER;
		try {
			currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(Sheet.getVEHICLEID());
			try {
				if (Sheet.getVEHICLEID() != 0) {
					if (currentODDMETER < closingKm) {
						
						vehicleService.updateCurrentOdoMeter(Sheet.getVEHICLEID(), closingKm, userDetails.getCompany_id());
						ServiceReminderService.updateCurrentOdometerToServiceReminder(Sheet.getVEHICLEID(),
								closingKm, userDetails.getCompany_id());
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(Sheet.getVEHICLEID(), userDetails.getCompany_id(), userDetails.getId());
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
								.prepareOdometerGetHistoryToTripDailySheet(Sheet);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleUpdateHistory.setVehicle_Odometer(closingKm);
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
					}
				}
			} catch (Exception e) {
				LOGGER.error("TripDaily Page:", e);
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@RequestMapping(value = "/getTripRouteApproxKm", method = RequestMethod.GET)
	public void getFuelVehicleOdoMerete(@RequestParam(value = "routeId", required = true) Integer routeID,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails			userDetails					= null;
		HashMap<String, Object> 	configuration				= null;
		TripRouteDto				tripRouteDto				= null;
		int							allowedPerDiffInOdometer	= 0;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			tripRouteDto	= TripRouteService.getTripRouteDetails(routeID, userDetails.getCompany_id());
			
			if(tripRouteDto != null) {
				allowedPerDiffInOdometer	= (int) configuration.getOrDefault(VehicleConfigurationContant.ALLOWED_PER_DIFF_IN_ODOMETER, 0);
				tripRouteDto.setMaxAllowedOdometer(tripRouteDto.getRouteApproximateKM() + (tripRouteDto.getRouteApproximateKM() * allowedPerDiffInOdometer/100));
			}
			
			Gson gson = new Gson();
			
			response.getWriter().write(gson.toJson(tripRouteDto));
		} catch (Exception e) {
			throw e;
		}
		
	}
}
