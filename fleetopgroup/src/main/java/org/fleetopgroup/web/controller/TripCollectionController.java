package org.fleetopgroup.web.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.TripDailySheetStatus;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.TripCollectionBL;
import org.fleetopgroup.persistence.bl.TripExpenseBL;
import org.fleetopgroup.persistence.bl.TripIncomeBL;
import org.fleetopgroup.persistence.bl.TripRouteBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.TripCollectionSheetDto;
import org.fleetopgroup.persistence.dto.TripGroupCollectionDto;
import org.fleetopgroup.persistence.dto.TripRoutefixedExpenseDto;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.DriverAttendance;
import org.fleetopgroup.persistence.model.TripCollectionExpense;
import org.fleetopgroup.persistence.model.TripCollectionIncome;
import org.fleetopgroup.persistence.model.TripCollectionSequenceCounter;
import org.fleetopgroup.persistence.model.TripCollectionSheet;
import org.fleetopgroup.persistence.model.TripDayCollection;
import org.fleetopgroup.persistence.model.TripGroupCollection;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetExpense;
import org.fleetopgroup.persistence.model.TripSheetIncome;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.serviceImpl.IDriverAttendanceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ITripCollectionService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetCollectionSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

@Controller
public class TripCollectionController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ITripCollectionService TripCollectionService;

	@Autowired
	private IVehicleService vehicleService;

	@Autowired
	private IDriverService driverService;

	@Autowired
	private ITripRouteService TripRouteService;

	@Autowired
	private IUserProfileService userProfileService;

	TripExpenseBL expenseBL = new TripExpenseBL();

	@Autowired
	private IServiceReminderService ServiceReminderService;

	@Autowired
	private IVehicleOdometerHistoryService vehicleOdometerHistoryService;

	@Autowired
	private IDriverAttendanceService DriverAttendanceService;
	
	@Autowired
	private ITripSheetCollectionSequenceService	tripSheetCollectionSequenceService;

	/*
	 * @Autowired private IVehicleGroupService vehicleGroupService;
	 */

	VehicleOdometerHistoryBL VOHBL = new VehicleOdometerHistoryBL();

	TripIncomeBL incomeBL = new TripIncomeBL();

	TripRouteBL TRoute = new TripRouteBL();
	VehicleBL VBL = new VehicleBL();

	DriverBL DBL = new DriverBL();

	TripCollectionBL TCBL = new TripCollectionBL();

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat driverAttendanceFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	SimpleDateFormat dateFormatName = new SimpleDateFormat("dd-MMM-yyyy");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@RequestMapping(value = "/searchTripCol", method = RequestMethod.POST)
	public ModelAndView searchTripCol(
			@RequestParam("tripStutes") final String TripStutes, final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("TripCol", TCBL.prepare_TripCollectionList(TripCollectionService.Search_TripCollectionSheet(TripStutes, userDetails)));

		} catch (Exception e) {

			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("newTripCollection", model);
	}

	@RequestMapping(value = "/searchTripColShow", method = RequestMethod.POST)
	public ModelAndView searchTripColShow(@RequestParam("tripStutes") final Long TRIPCOLLID,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TripCollectionSheetDto TripCol = TripCollectionService.Get_Showing_TripCollection_Sheetby_Number(TRIPCOLLID, userDetails);
				// get the Trip sheet ID to Details
				model.put("TripCol", TripCol);
				
				// get Trip sheet id to get all Expense Details
				model.put("TripColExpense", TripCollectionService.findAll_TripCollection_Expense(TripCol.getTRIPCOLLID(), userDetails.getCompany_id()));
				
				// get Trip sheet id to get all Income Details
				model.put("TripColIncome", TripCollectionService.findAll_TripCollection_Income(TripCol.getTRIPCOLLID(), userDetails.getCompany_id()));
				
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
			return new ModelAndView("redirect:/newTripCol.in", model);
		} catch (Exception e) {
			model.put("NotFound", true);
			return new ModelAndView("redirect:/newTripCol.in", model);
		}
		return new ModelAndView("showTripCollection", model);
	}

	@RequestMapping(value = "/searchTripClosed", method = RequestMethod.POST)
	public ModelAndView searchTripClosed(
			@RequestParam("tripStutes") final String TripStutes, final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			model.put("TripCol", TCBL.prepare_TripCollectionList(TripCollectionService.Search_TripCollectionSheet_Statues(TripDailySheetStatus.TRIP_STATUS_CLOSED, TripStutes)));

		} catch (Exception e) {

			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("newCloseTripCollection", model);
	}

	// Show the the Trip Sheet menu
	@RequestMapping(value = "/searchTripColDate", method = RequestMethod.POST)
	public ModelAndView searchTripColDate(@RequestParam("TripColDate") final String TripColDate,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			List<TripCollectionSheetDto> TripCollection = TripCollectionService
					.list_Today_TripCollectionSheet(TripColDate, userDetails);

			model.put("TripCol", TCBL.prepare_TripCollectionList(TripCollection));

			if (TripCollection != null) {
				model.put("TripSheetToday", TripCollection.size());
			}
			model.put("SelectDate", TripColDate);

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("newTripCollection", model);
	}

	// Show the the Trip Sheet menu
	@RequestMapping(value = "/newTripCol", method = RequestMethod.GET)
	public ModelAndView newTripCol(@ModelAttribute("command") TripSheet TripSheetDto,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("TripSheetCount", TripCollectionService.count_TripCollectionSheet(userDetails));
			java.util.Date currentDate = new java.util.Date();
			DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");

			List<TripCollectionSheetDto> TripCollection = TripCollectionService
					.list_Today_TripCollectionSheet(ft.format(currentDate), userDetails);

			model.put("TripCol", TCBL.prepare_TripCollectionList(TripCollection));

			if (TripCollection != null) {
				model.put("TripSheetToday", TripCollection.size());
			}
			model.put("SelectDate", dateFormatName.format(currentDate));
		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("newTripCollection", model);
	}

	// create Trip sheet Details
	@RequestMapping(value = "/addTripCol", method = RequestMethod.GET)
	public ModelAndView addTripCollection(final HttpServletRequest request) {
		try {

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("addTripCollection");
	}

	// create OLd Trip sheet to Details
	@RequestMapping(value = "/copyTripCol", method = RequestMethod.GET)
	public ModelAndView copyTripCol(@RequestParam("ID") final Long TRIPCOLLID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TripCollectionSheetDto TripCol = TripCollectionService.Get_Showing_TripCollection_Sheet(TRIPCOLLID, userDetails);
			// get the Trip sheet ID to Details
			model.put("TripCol", TripCol);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("copyTripCollection", model);
	}

	/* save the vehicle Status */
	@RequestMapping(value = "/saveTripCol", method = RequestMethod.POST)
	public ModelAndView saveTripCol(@ModelAttribute("command") TripCollectionSheetDto CollectionSheetDto,
			RedirectAttributes redir, final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails				userDetails		= null;
		TripCollectionSequenceCounter	sequenceCounter	= null;
		java.sql.Date fromDate = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			java.util.Date date = dateFormat.parse(CollectionSheetDto.getTRIP_OPEN_DATE());
			fromDate = new Date(date.getTime());
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// Validate The tripCollectionSheet Date and Vehicle Already Created OR
		// Not Check
		List<TripCollectionSheet> validateAlready = TripCollectionService
				.Validate_TripCollectionSheet_Date_VehicleName(fromDate, CollectionSheetDto.getVID(), userDetails.getCompany_id());
		if (validateAlready != null && !validateAlready.isEmpty()) {
			return new ModelAndView("redirect:/addTripCol.in?already=true");
		} else {
			List<TripGroupCollectionDto> GroupSheet = TripCollectionService
					.List_TripGroupCollection_closeDate(driverAttendanceFormat.format(fromDate), TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());
			if (GroupSheet != null && !GroupSheet.isEmpty()) {
				redir.addFlashAttribute("closeDate", CollectionSheetDto.getTRIP_OPEN_DATE());
				return new ModelAndView("redirect:/addTripCol.in?alreadyClose=true");
			} else {
				TripCollectionSheet Sheet = prepareModel_Save_TripCollectionSheet(CollectionSheetDto);
				sequenceCounter	= tripSheetCollectionSequenceService.findNextSequenceNumber(userDetails.getCompany_id());
				if(sequenceCounter == null) {
					return new ModelAndView("redirect:/addTripCol.in?sequenceNotFound=true");
				}
				Sheet.setTRIPCOLLNUMBER(sequenceCounter.getNextVal());
				try {

					if (Sheet.getVID() != 0 && Sheet.getTRIP_DRIVER_ID() != 0 && Sheet.getTRIP_CONDUCTOR_ID() != 0
							&& Sheet.getTRIP_ROUTE_ID() != 0) {
						TripCollectionService.add_TripCollectionSheet(Sheet);
						//tripSheetCollectionSequenceService.updateNextSequenceNumber(sequenceCounter.getNextVal() + 1, sequenceCounter.getSequence_Id());
						if (Sheet.getTRIP_ROUTE_ID() != 0) {
							// not equal to zreo get all fixed expense

							List<TripRoutefixedExpenseDto> RoutefixedExpense = TripRouteService
									.listTripRouteFixedExpene(Sheet.getTRIP_ROUTE_ID(), userDetails.getCompany_id());

							if (RoutefixedExpense != null && !RoutefixedExpense.isEmpty()) {
								Double tripTotalExpense = 0.0;
								for (TripRoutefixedExpenseDto tripRoutefixedExpense : RoutefixedExpense) {

									/*TripCollectionExpense TripExpense = new TripCollectionExpense(
											tripRoutefixedExpense.getExpenseName(),
											tripRoutefixedExpense.getExpenseAmount(),
											tripRoutefixedExpense.getExpenseRefence());*/
									
									TripCollectionExpense TripExpense = new TripCollectionExpense();
									TripExpense.setExpenseId(tripRoutefixedExpense.getExpenseId());
									TripExpense.setExpenseAmount(tripRoutefixedExpense.getExpenseAmount());
									TripExpense.setExpenseRefence(tripRoutefixedExpense.getExpenseRefence());
									TripExpense.setTripCollectionsheet(Sheet);
									TripExpense.setCreatedById(userDetails.getId());
									java.util.Date currentDateUpdate = new java.util.Date();
									Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

									TripExpense.setCreated(toDate);
									TripExpense.setCompanyId(userDetails.getCompany_id());

									TripCollectionService.add_TripCollectionExpense(TripExpense);

									tripTotalExpense += tripRoutefixedExpense.getExpenseAmount();

								}
								TripCollectionService.update_TripCollection_TotalExpense(tripTotalExpense,
										Sheet.getTRIPCOLLID(), userDetails.getCompany_id());

							}

						}
						model.put("saveTripCol", true);

						// this Update Current Odometer in Vehicle Getting Trip
						// Daily Sheet
						Update_Current_OdometerinVehicle_getTripDailySheetTo(Sheet);

					} else {
						return new ModelAndView("redirect:/addTripCol.in?error=true");
					}

				} catch (Exception e) {
					System.err.println("Exception : "+e);
					model.put("alreadyTripCol", true);

					LOGGER.error("TripSheet Page:", e);
					return new ModelAndView("newTripCol", model);

				}
				return new ModelAndView("redirect:/showTripCol.in?ID=" + Sheet.getTRIPCOLLID() + "");
			}
		}
	}

	// save Issues in databases
	@RequestMapping(value = "/showTripCol", method = RequestMethod.GET)
	public ModelAndView ShowTripsheet(@RequestParam("ID") final Long TRIPCOLLID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TripCollectionSheetDto TripCol = TripCollectionService.Get_Showing_TripCollection_Sheet(TRIPCOLLID, userDetails);
			// get the Trip sheet ID to Details
			model.put("TripCol", TripCol);

			// get Trip sheet id to get all Expense Details
			model.put("TripColExpense", TripCollectionService.findAll_TripCollection_Expense(TRIPCOLLID, userDetails.getCompany_id()));

			// get Trip sheet id to get all Income Details
			model.put("TripColIncome", TripCollectionService.findAll_TripCollection_Income(TRIPCOLLID, userDetails.getCompany_id()));

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

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("showTripCollection", model);
	}

	// save Issues in databases
	@RequestMapping(value = "/showTripColPrint", method = RequestMethod.GET)
	public ModelAndView showTripSheetPrint(@RequestParam("ID") final Long TRIPCOLLID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			TripCollectionSheetDto TripCol = TripCollectionService.Get_Showing_TripCollection_Sheet(TRIPCOLLID, userDetails);
			// get the Trip sheet ID to Details
			model.put("TripCol", TripCol);

			// get Trip sheet id to get all Expense Details
			model.put("TripColExpense", TripCollectionService.findAll_TripCollection_Expense(TRIPCOLLID, userDetails.getCompany_id()));

			// get Trip sheet id to get all Income Details
			model.put("TripColIncome", TripCollectionService.findAll_TripCollection_Income(TRIPCOLLID, userDetails.getCompany_id()));

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

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("showTripCollectionPrint", model);
	}

	/* show main page of Trip */

	@RequestMapping(value = "/manageTripCol/{pageNumber}", method = RequestMethod.GET)
	public String manageTripSheet(@PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Page<TripCollectionSheet> page = TripCollectionService.getDeployment_Page_TripCollectionSheet(TripDailySheetStatus.TRIP_STATUS_OPEN,
					pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("TripManage", page.getTotalElements());
			List<TripCollectionSheetDto> pageList = (TripCollectionService.list_TripCollectionSheet_Page_Status(TripDailySheetStatus.TRIP_STATUS_OPEN,
					pageNumber, userDetails));

			model.addAttribute("TripCol", TCBL.prepare_TripCollectionList(pageList));

		} catch (NullPointerException e) {
			e.printStackTrace();
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("TripCol Page:", e);
			e.printStackTrace();
		}
		return "newManageTripCollection";
	}

	/* show main page of Trip */

	@RequestMapping(value = "/closeTripCol/{pageNumber}", method = RequestMethod.GET)
	public String closeTripSheet(@PathVariable Integer pageNumber, Model model) throws Exception {

		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
			Page<TripCollectionSheet> page = TripCollectionService.getDeployment_Page_TripCollectionSheet(TripDailySheetStatus.TRIP_STATUS_CLOSED,
					pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("TripClose", page.getTotalElements());
			List<TripCollectionSheetDto> pageList = (TripCollectionService.list_TripCollectionSheet_Page_Status(TripDailySheetStatus.TRIP_STATUS_CLOSED,
					pageNumber, userDetails));

			model.addAttribute("TripCol", TCBL.prepare_TripCollectionList(pageList));

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("TripCol Page:", e);
			e.printStackTrace();
		}
		return "newCloseTripCollection";
	}

	// Show Add Expense Page
	@RequestMapping(value = "/addColExpense", method = RequestMethod.GET)
	public ModelAndView addColExpense(@RequestParam("ID") final Long TRIPCOLLID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TripCollectionSheetDto TripCol = (TripCollectionService.Get_Showing_TripCollection_Sheet(TRIPCOLLID, userDetails));
			// get the Trip sheet ID to Details
			model.put("TripCol", TripCol);

			// get Trip sheet id to get all Expense Details
			model.put("TripColExpense", TripCollectionService.findAll_TripCollection_Expense(TRIPCOLLID, userDetails.getCompany_id()));
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
		return new ModelAndView("ExpenseTripCollection", model);
	}

	// update Expense TripSheet and also Amount
	@RequestMapping(value = "/updateColExpense", method = RequestMethod.POST)
	public ModelAndView updateExpenseTripsheet(@ModelAttribute("command") TripCollectionSheetDto TripSheet,
			TripSheetExpense TripSheetExpense, @RequestParam("expenseName") final String[] expenseName,
			@RequestParam("Amount") final String[] expenseAmount,
			@RequestParam("expenseRefence") final String[] expenseRefence, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TripCollectionSheet tsheet = new TripCollectionSheet();
			tsheet.setTRIPCOLLID(TripSheet.getTRIPCOLLID());

			for (int i = 0; i < expenseName.length; i++) {

				/*TripCollectionExpense TripExpense = new TripCollectionExpense("" + expenseName[i],
						Double.parseDouble("" + expenseAmount[i]), "" + expenseRefence[i]);*/
				TripCollectionExpense TripExpense = new TripCollectionExpense();
				TripExpense.setExpenseId(Integer.parseInt("" + expenseName[i]));
				TripExpense.setExpenseAmount(Double.parseDouble("" + expenseAmount[i]));
				TripExpense.setExpenseRefence("" + expenseRefence[i]);
				TripExpense.setTripCollectionsheet(tsheet);

				TripExpense.setCreatedById(userDetails.getId());
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				TripExpense.setCreated(toDate);
				TripExpense.setCompanyId(userDetails.getCompany_id());

				List<TripCollectionExpense> validate = TripCollectionService
						.Validate_TripCollection_Expense(Integer.parseInt("" + expenseName[i]), tsheet.getTRIPCOLLID(), userDetails.getCompany_id());
				if (validate != null && !validate.isEmpty()) {
					model.put("alreadyExpense", true);

				} else {

					TripCollectionService.add_TripCollectionExpense(TripExpense);
					model.put("updateExpense", true);

					Object[]  expenseIds = TripCollectionService.getDriverAdvanceAndBataExpenseId(userDetails.getCompany_id());
					
					if(expenseIds != null) {
						if(expenseIds[0] != null && (Integer)expenseIds[0] == Integer.parseInt("" + expenseName[i])) {
							TripCollectionService.update_TripCollection_Driver_JAM(
									Double.parseDouble("" + expenseAmount[i]), TripSheet.getTRIPCOLLID(), userDetails.getCompany_id());
							
						}
						if(expenseIds[1] != null && (Integer)expenseIds[1] == Integer.parseInt("" + expenseName[i])) {
							TripCollectionService.update_TripCollection_Conductor_JAM(
									Double.parseDouble("" + expenseAmount[i]), TripSheet.getTRIPCOLLID(), userDetails.getCompany_id());
						
						}
					}	
					
					/*switch (expenseName[i]) {
					case "DRIVER_JAMA":

						TripCollectionService.update_TripCollection_Driver_JAM(
								Double.parseDouble("" + expenseAmount[i]), TripSheet.getTRIPCOLLID(), userDetails.getCompany_id());
						break;

					case "CONDUCTOR_JAMA":

						TripCollectionService.update_TripCollection_Conductor_JAM(
								Double.parseDouble("" + expenseAmount[i]), TripSheet.getTRIPCOLLID(), userDetails.getCompany_id());
						break;
					}*/

				}

			}

			Double tripTotalExpense = 0.0;
			for (TripCollectionExpense amount : TripCollectionService
					.findAll_TripCollection_Expense(TripSheet.getTRIPCOLLID())) {

				tripTotalExpense += amount.getExpenseAmount();

			}
			// update total Expense amount
			TripCollectionService.update_TripCollection_TotalExpense(tripTotalExpense, TripSheet.getTRIPCOLLID(), userDetails.getCompany_id());

		} catch (

		Exception e) {

			e.printStackTrace();
		}finally {
			userDetails = null;
		}
		return new ModelAndView("redirect:/showTripCol.in?ID=" + TripSheet.getTRIPCOLLID() + "", model);
	}

	// removeAdvance TripSheet and also Amount
	@RequestMapping(value = "/removeColExpense", method = RequestMethod.GET)
	public ModelAndView removeExpenseTripsheet(@RequestParam("ID") final Long TripExpenseID,
			final HttpServletRequest request) {

		Long TripSheetID = null;
		CustomUserDetails	userDetails		= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TripCollectionExpense TripAdvance = TripCollectionService.Get_TripCollection_Expense(TripExpenseID, userDetails.getCompany_id());

			if (TripAdvance != null) {
				TripSheetID = TripAdvance.getTripCollectionsheet().getTRIPCOLLID();
				// System.out.println("Before Delete tripsheet id
				// ="+TripSheetID);
				TripCollectionService.delete_TripCollectionExpense(TripAdvance.getTripExpenseID(), userDetails.getCompany_id());
				// System.out.println("after Delete tripsheet id
				// ="+TripSheetID);
				Double tripTotalExpense = 0.0;
				for (TripCollectionExpense amount : TripCollectionService.findAll_TripCollection_Expense(TripSheetID)) {

					tripTotalExpense += amount.getExpenseAmount();

				}
				TripCollectionService.update_TripCollection_TotalExpense(tripTotalExpense, TripSheetID, userDetails.getCompany_id());
			}

		} catch (Exception e) {

			e.printStackTrace();
		}finally {
			userDetails = null;
		}
		return new ModelAndView("redirect:/addColExpense.in?ID=" + TripSheetID + "");
	}

	// Show Add Expense Page
	@RequestMapping(value = "/addColIncome", method = RequestMethod.GET)
	public ModelAndView addIncome(@RequestParam("ID") final Long TRIPCOLLID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		

			TripCollectionSheetDto TripCol = (TripCollectionService.Get_Showing_TripCollection_Sheet(TRIPCOLLID, userDetails));
			// get the Trip sheet ID to Details
			model.put("TripCol", TripCol);

			// get Trip sheet id to get all Income Details
			model.put("TripColIncome", TripCollectionService.findAll_TripCollection_Income(TRIPCOLLID, userDetails.getCompany_id()));
			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(0);
			Double Totalincome = TripCol.getTOTAL_INCOME();
			if (TripCol.getTOTAL_INCOME() == null) {
				Totalincome = 0.0;
			}
			model.put("incomeTotal", df.format(Totalincome));

		} catch (Exception e) {

			e.printStackTrace();
		}finally {
			userDetails		= null;
		}
		return new ModelAndView("IncomeTripCollection", model);
	}

	// update Expense TripSheet and also Amount
	@RequestMapping(value = "/updateColIncome", method = RequestMethod.POST)
	public ModelAndView updateIncomeTripsheet(@ModelAttribute("command") TripCollectionSheetDto TripSheet,
			TripSheetIncome TripSheetIncome, @RequestParam("incomeName") final String[] incomeName,
			@RequestParam("Amount") final String[] incomeAmount,
			@RequestParam("incomeRefence") final String[] incomeRefence, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			TripCollectionSheet tsheet = new TripCollectionSheet();
			tsheet.setTRIPCOLLID(TripSheet.getTRIPCOLLID());

			for (int i = 0; i < incomeName.length; i++) {

				/*TripCollectionIncome TripIncome = new TripCollectionIncome("" + incomeName[i],
						Double.parseDouble("" + incomeAmount[i]), "" + incomeRefence[i], "" + incomeCollectedBy);*/
				TripCollectionIncome TripIncome = new TripCollectionIncome();
				TripIncome.setIncomeId(Integer.parseInt(("" + incomeName[i])));
				TripIncome.setIncomeAmount(Double.parseDouble("" + incomeAmount[i]));
				TripIncome.setIncomeRefence(incomeRefence[i]);
				TripIncome.setIncomeCollectedById(userDetails.getId());
				TripIncome.setTripCollectionsheet(tsheet);
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				TripIncome.setCreatedById(userDetails.getId());
				TripIncome.setCreated(toDate);
				TripIncome.setCompanyId(userDetails.getCompany_id());

				List<TripCollectionIncome> validate = TripCollectionService
						.Validate_TripCollection_Income(Integer.parseInt(("" + incomeName[i])), tsheet.getTRIPCOLLID(), userDetails.getCompany_id());
				if (validate != null && !validate.isEmpty()) {
					model.put("alreadyIncome", true);

				} else {

					TripCollectionService.add_TripCollectionIncome(TripIncome);
					model.put("updateIncome", true);
				}

			}

			Double tripTotalIncome = 0.0;
			for (TripCollectionIncome amount : TripCollectionService
					.findAll_TripCollection_Income(TripSheet.getTRIPCOLLID())) {

				tripTotalIncome += amount.getIncomeAmount();

			}
			// update total income amount
			TripCollectionService.update_TripCollection_TotalIncome(tripTotalIncome, TripSheet.getTRIPCOLLID(), userDetails.getCompany_id());

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/showTripCol.in?ID=" + TripSheet.getTRIPCOLLID() + "", model);
	}

	// removeAdvance TripSheet and also Amount
	@RequestMapping(value = "/removeColIncome", method = RequestMethod.GET)
	public ModelAndView removeIncomeTripsheet(@RequestParam("ID") final Long TripIncomeID,
			final HttpServletRequest request) {

		Long TripSheetID = null;
		CustomUserDetails	userDetails	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			TripCollectionIncome TripIncome = TripCollectionService.Get_TripCollection_Income(TripIncomeID, userDetails.getCompany_id());

			if (TripIncome != null) {
				TripSheetID = TripIncome.getTripCollectionsheet().getTRIPCOLLID();
				// System.out.println("Before Delete tripsheet id
				// ="+TripSheetID);
				TripCollectionService.delete_TripCollectionIncome(TripIncome.getTripincomeID(), userDetails.getCompany_id());
				// System.out.println("after Delete tripsheet id
				// ="+TripSheetID);
				Double tripTotalIncome = 0.0;
				for (TripCollectionIncome amount : TripCollectionService.findAll_TripCollection_Income(TripSheetID)) {

					tripTotalIncome += amount.getIncomeAmount();

				}
				TripCollectionService.update_TripCollection_TotalIncome(tripTotalIncome, TripSheetID, userDetails.getCompany_id());
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/addColIncome.in?ID=" + TripSheetID + "");
	}

	@RequestMapping(value = "/addCloseTripCol", method = RequestMethod.GET)
	public ModelAndView CloseTripCollection(@RequestParam("ID") final Long TRIPCOLLID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


			TripCollectionSheetDto TripCol = TripCollectionService.Get_Showing_TripCollection_Sheet(TRIPCOLLID, userDetails);
			// get the Trip sheet ID to Details
			model.put("TripCol", TripCol);

			// get Trip sheet id to get all Expense Details
			model.put("TripColExpense", TripCollectionService.findAll_TripCollection_Expense(TRIPCOLLID, userDetails.getCompany_id()));

			// get Trip sheet id to get all Income Details
			model.put("TripColIncome", TripCollectionService.findAll_TripCollection_Income(TRIPCOLLID, userDetails.getCompany_id()));

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

			
			model.put("CloseBy", userDetails.getFirstName() + "_" + userDetails.getLastName());
			model.put("CloseById", userDetails.getId());

			df.setMaximumFractionDigits(0);
			Double TotalBALANCE = round((TripCol.getTOTAL_INCOME() - TripCol.getTOTAL_EXPENSE()), 2);
			if (TripCol.getTOTAL_BALANCE() == null) {
				TotalBALANCE = 0.0;
			}
			model.put("incomeBalance", df.format(TotalBALANCE));

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("closeTripCollection", model);
	}

	// update of vehicle status
	@RequestMapping(value = "/CloseTripCollection", method = RequestMethod.POST)
	public ModelAndView uploadClose(@ModelAttribute("command") TripCollectionSheet TripCollection,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer ClosingKM = 0;
		Integer UsageKM = 0;
		Double balance = 0.0;
		try {

			TripCollectionSheetDto TripCol = TripCollectionService
					.Get_Showing_TripCollection_Sheet(TripCollection.getTRIPCOLLID(), userDetails);
			TripCol.setCOMPANY_ID(userDetails.getCompany_id());
			if (TripCollection.getTRIP_CLOSE_KM() != null) {
				ClosingKM = TripCollection.getTRIP_CLOSE_KM();

				UsageKM = ClosingKM - TripCol.getTRIP_OPEN_KM();
			}

			balance = round(TripCol.getTOTAL_INCOME() - TripCol.getTOTAL_EXPENSE(), 2);

			TripCollectionService.update_Close_TripCollection(ClosingKM, UsageKM, TripDailySheetStatus.TRIP_STATUS_CLOSED, balance,
					TripCollection.getLASTMODIFIEDBYID(), TripCol.getTRIPCOLLID(), userDetails.getCompany_id());

			model.put("saveTripSheet", true);

			List<TripGroupCollection> GroupCollection = TripCollectionService.Validate_TripGroupCollection(
					driverAttendanceFormat.format(TripCol.getTRIP_OPEN_DATE_ON()), TripCol.getVEHICLE_GROUP_ID(), userDetails.getCompany_id());
			if (GroupCollection != null && !GroupCollection.isEmpty()) {
				for (TripGroupCollection tripGroupCollection : GroupCollection) {

					Integer Buss = 1;
					// check Group already Created in update Code here
					TripCollectionService.update_TripGroupCollection(TripCol.getTOTAL_INCOME(),
							TripCol.getTOTAL_EXPENSE(), balance, Buss, TripCol.getTRIP_DIESEL_LITER(),
							TripCol.getTRIP_SINGL(), tripGroupCollection.getTRIPGROUPID(), userDetails.getCompany_id());
					break;
				}

				// System.out.println("UPATE");
			} else {
				// System.out.println("ADD");
				// check Group already Not Created in Create New Group Date
				TripGroupCollection GropTripCol = TCBL.Collection_Trip_toAdd_GroupCollection(TripCol, balance);
				GropTripCol.setCOMPANY_ID(userDetails.getCompany_id());
				TripCollectionService.add_TripGroupCollection(GropTripCol);
			}

			// System.out.println(currentODDMETER);
			// update the Current Odometer vehicle Databases tables
			// this Update Current Odometer in Vehicle Getting Trip
			// Daily Sheet
			Update_Current_OdometerinVehicle_getTripDailySheetTo(TripCol);

			try {
				// TripCollectionSheetDto TripCol =
				// TCBL.Get_TripCollectionShee_To_DriverAttendance(TripCol);
				if (TripCol.getTRIP_DRIVER_ID() != 0) {

					// this driver attendance
					Add_TripCollection_TO_driver_Attendance(TripCol.getTRIP_DRIVER_ID(), TripCol.getTRIP_DRIVER_NAME(),
							TripCol);

				}

				if (TripCol.getTRIP_CONDUCTOR_ID() != 0) {

					// this Conductor attendance
					Add_TripCollection_TO_driver_Attendance(TripCol.getTRIP_CONDUCTOR_ID(),
							TripCol.getTRIP_CONDUCTOR_NAME(), TripCol);

				}

				if (TripCol.getTRIP_CLEANER_ID() != 0) {

					// this Cleaner attendance
					Add_TripCollection_TO_driver_Attendance(TripCol.getTRIP_CLEANER_ID(),
							TripCol.getTRIP_CLEANER_NAME(), TripCol);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("alreadyTripSheet", true);
			return new ModelAndView("redirect:/newTripCol.in", model);
		}
		return new ModelAndView("redirect:/showTripCol.in?ID=" + TripCollection.getTRIPCOLLID() + "");
	}

	@RequestMapping(value = "/closeDailyTripCol", method = RequestMethod.POST)
	public ModelAndView closeDailyTripCol(@RequestParam("closedate") final String TripCollectionDate,
			RedirectAttributes redir, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (TripCollectionDate != null) {

				List<TripCollectionSheetDto> OpenCollectionSheet = TripCollectionService
						.List_TripCollectionSheet_closeDate(TripCollectionDate, TripDailySheetStatus.TRIP_STATUS_OPEN, userDetails);

				if (OpenCollectionSheet != null && !OpenCollectionSheet.isEmpty()) {
					String success = "";
					for (TripCollectionSheetDto tripCollectionSheet : OpenCollectionSheet) {

						success += "<a href=\"showTripCol.in?ID=" + tripCollectionSheet.getTRIPCOLLID()
								+ "\" target=\"_blank\">TS-" + tripCollectionSheet.getTRIPCOLLNUMBER()
								+ "  <i class=\"fa fa-external-link\"></i></a> ,";
					}

					redir.addFlashAttribute("closeID", success);
					return new ModelAndView("redirect:/newTripCol.in?closeTrip=true", model);
				} else {

					List<TripGroupCollectionDto> GroupSheet = TripCollectionService
							.List_TripGroupCollection_closeDate(TripCollectionDate, TripDailySheetStatus.TRIP_STATUS_OPEN, userDetails.getCompany_id());
					if (GroupSheet != null && !GroupSheet.isEmpty()) {

						List<TripCollectionSheetDto> CollectionSheet = TripCollectionService
								.List_TripCollectionSheet_closeDate(TripCollectionDate, TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails);

						model.put("TOTAL_COLLECTION", TCBL.Total_Collection(GroupSheet));

						model.put("TRIP_OPEN_DATE", TripCollectionDate);
						model.put("SEARCHDATE", TripCollectionDate);

						model.put("TripGroupCol", TCBL.CloseDaily_tripCollection_Sheet(GroupSheet, CollectionSheet));

						Integer[] totalGroupSheet = TCBL.Total_Runing_Bus_Details(GroupSheet);
						if (totalGroupSheet != null) {
							model.put("TRUNINGBUS", totalGroupSheet[0]);
							model.put("TSINGL", totalGroupSheet[1]);
							Integer TotalSingle = totalGroupSheet[0] * 14;
							Integer CutSingle = TotalSingle - totalGroupSheet[1];
							model.put("TCUTSINGL", CutSingle);
						}

					} else {
						model.put("alreadyClosed", true);
						return new ModelAndView("redirect:/closeDailyColShow.in?date=" + TripCollectionDate + "",
								model);
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("closeDayTripCollection", model);
	}

	@RequestMapping(value = "/closeDayTripCollection", method = RequestMethod.POST)
	public ModelAndView closeDayTripCollection(@RequestParam("closedate") final String TripCollectionDate,
			TripDayCollection DayCollection, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (TripCollectionDate != null) {

				List<TripGroupCollectionDto> GroupSheet = TripCollectionService
						.List_TripGroupCollection_closeDate(TripCollectionDate, TripDailySheetStatus.TRIP_STATUS_OPEN, userDetails.getCompany_id());
				if (GroupSheet != null && !GroupSheet.isEmpty()) {
					TripDayCollection CollectionClose = Calucution_Trip_day_Collection(GroupSheet, DayCollection,
							TripCollectionDate);
					
					CollectionClose.setCOMPANY_ID(userDetails.getCompany_id());
					CollectionClose.setTRIP_CLOSED_BY_ID(userDetails.getId());
					TripCollectionService.add_TripDayCollection(CollectionClose);
					model.put("CloseSuccess", true);
					TripCollectionService.update_TripGroup_Ac_collectionTotal_Amount(
							CollectionClose.getPER_SINGL_COLLECTION(), TripCollectionDate, userDetails.getCompany_id());
				} else {
					return new ModelAndView("redirect:/newTripCol.in?alreadyclose=ture", model);
				}
			}

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/closeDailyColShow.in?date=" + TripCollectionDate + "", model);
	}

	@RequestMapping(value = "/closeDailyColShow", method = RequestMethod.GET)
	public ModelAndView closeDailyColShow(@RequestParam("date") final String TripCollectionDate,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (TripCollectionDate != null) {

				List<TripGroupCollectionDto> GroupSheet = TripCollectionService
						.List_TripGroupCollection_closeDate(TripCollectionDate, TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());
				if (GroupSheet != null && !GroupSheet.isEmpty()) {

					List<TripCollectionSheetDto> CollectionSheet = TripCollectionService
							.List_TripCollectionSheet_closeDate(TripCollectionDate, TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails);

					model.put("TripGroupCol", TCBL.Show_CloseDaily_tripCollection_Sheet(GroupSheet, CollectionSheet));

					model.put("SEARCHDATE", TripCollectionDate);
					model.put("TripDayCol",
							TripCollectionService.List_TripDayCollection_CloseDate(TripCollectionDate, TripDailySheetStatus.TRIP_STATUS_CLOSED));

				} else {
					return new ModelAndView("redirect:/newTripCol.in?nodata=true");
				}
			}
		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("closeDayTripCollectionShow", model);
	}

	/**
	 * @param trip_DRIVER_ID
	 * @param trip_DRIVER_NAME
	 * @param tripCol
	 */
	private void Add_TripCollection_TO_driver_Attendance(int trip_DRIVER_ID, String trip_DRIVER_NAME,
			TripCollectionSheetDto TripCol) {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// Monday, February 29 is a leap day in 2016 (otherwise,
		// February only has 28 days)
		// LocalDate start = LocalDate.parse("2016-06-01"), end =
		// LocalDate.parse("2016-06-01");

		LocalDate start = LocalDate.parse(driverAttendanceFormat.format(TripCol.getTRIP_OPEN_DATE_ON())),
				end = LocalDate.parse(driverAttendanceFormat.format(TripCol.getTRIP_OPEN_DATE_ON()));

		LocalDate next = start.minusDays(1);
		while ((next = next.plusDays(1)).isBefore(end.plusDays(1))) {
			// System.out.println(next);

			try {
				if (next != null) {

					DriverAttendance attendance = DBL.Driver_Attendance_to_tripCollection(trip_DRIVER_ID,
							trip_DRIVER_NAME, TripCol);
					/**
					 * Reported_Date converted String to date Format
					 */
					java.util.Date attendanceDate = driverAttendanceFormat.parse("" + next);
					java.sql.Date Reported_Date = new Date(attendanceDate.getTime());
					attendance.setATTENDANCE_DATE(Reported_Date);
					attendance.setCOMPANY_ID(userDetails.getCompany_id());
					
					/*
					 * DriverAttendance Validate = DriverAttendanceService
					 * .ValidateDriverAttendance(TripCol.
					 * getTripFristDriverID(), Reported_Date); if (Validate !=
					 * null) { } else {
					 */
					DriverAttendanceService.addDriverAttendance(attendance);
					/* } */

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param groupSheet
	 * @param dayCollection
	 * @param tripCollectionDate
	 * @return
	 */
	private TripDayCollection Calucution_Trip_day_Collection(List<TripGroupCollectionDto> GroupSheet,
			TripDayCollection DayCollection, String TripCollectionDate) {

		TripDayCollection closeCollection = new TripDayCollection();

		try {

			Double totalCollection = 0.0, totalExpense = 0.0, totalBalance = 0.0;
			Integer totalDiesel = 0, totalRunSingle = 0, totalBus = 0;
			if (GroupSheet != null && !GroupSheet.isEmpty()) {

				for (TripGroupCollectionDto tripGroupCollection : GroupSheet) {
					// calucution of total collection
					if (tripGroupCollection.getTOTAL_COLLECTION() != null) {
						totalCollection += tripGroupCollection.getTOTAL_COLLECTION();
					}
					// calucution of total expense
					if (tripGroupCollection.getTOTAL_EXPENSE() != null) {
						totalExpense += tripGroupCollection.getTOTAL_EXPENSE();
					}
					// calucution of total diesel
					if (tripGroupCollection.getTOTAL_DIESEL() != null) {
						totalDiesel += tripGroupCollection.getTOTAL_DIESEL();
					}
					// calucution of total balance
					if (tripGroupCollection.getTOTAL_BALANCE() != null) {
						totalBalance += tripGroupCollection.getTOTAL_BALANCE();
					}
					// calucution of total Running Single
					if (tripGroupCollection.getTOTAL_SINGL() != null) {
						totalRunSingle += tripGroupCollection.getTOTAL_SINGL();
					}
					// calucution of total Running Bus
					if (tripGroupCollection.getTOTAL_BUS() != null) {
						totalBus += tripGroupCollection.getTOTAL_BUS();
					}

				} // close Group collection for loop

			} else {
				return null;
			}
			// add details

			java.util.Date date = driverAttendanceFormat.parse(TripCollectionDate);
			java.sql.Date CollectionDate = new Date(date.getTime());
			closeCollection.setTRIP_OPEN_DATE(CollectionDate);

			closeCollection.setTOTAL_COLLECTION(totalCollection);
			closeCollection.setTOTAL_EXPENSE(totalExpense);
			closeCollection.setTOTAL_DIESEL(totalDiesel);
			closeCollection.setTOTAL_BALANCE(totalBalance);
			closeCollection.setTOTAL_RUN_SINGL(totalRunSingle);
			closeCollection.setTOTAL_BUS(totalBus);

			// calucation of grand total
			Double GrandTotal = 0.0, perSingleCost = 0.0, daycloseingExpese = 0.0, ticketRoll = 0.0, ROLL_NUMBER = 0.0,
					ROLL_PRICE = 0.0, staffSalary = 0.0, mechanic = 0.0, IncDF = 0.0, DCBonus = 0.0, netTotal = 0.0,
					eachBusCollection = 0.0;
			Integer perDaySingl = 0;
			// calucution of total per day one bus Single
			if (DayCollection.getPER_DAY_SINGL() != null) {
				perDaySingl += DayCollection.getPER_DAY_SINGL();
			}
			// calucution of total roll number
			if (DayCollection.getROLL_NUMBER() != null) {
				ROLL_NUMBER += DayCollection.getROLL_NUMBER();
			}
			// calucution of total roll price
			if (DayCollection.getROLL_PRICE() != null) {
				ROLL_PRICE += DayCollection.getROLL_PRICE();
			}

			// calucution of total roll cost
			ticketRoll = round((ROLL_NUMBER * ROLL_PRICE), 2);

			// calucution of total staff salary cost
			if (DayCollection.getSTAFF_SALARY() != null) {
				staffSalary = DayCollection.getSTAFF_SALARY();
			}
			// calucution of total mechanic cost
			if (DayCollection.getMECHANIC_MAINTANCE() != null) {
				mechanic = DayCollection.getMECHANIC_MAINTANCE();
			}

			// calucution of total insurence cost
			if (DayCollection.getINSURENCE_MAINTANCE() != null) {
				IncDF = DayCollection.getINSURENCE_MAINTANCE();
			}
			// calucution of total bonus cost
			if (DayCollection.getDC_BONUS() != null) {
				DCBonus = DayCollection.getDC_BONUS();
			}
			// calucution of total day expense cost
			daycloseingExpese += (staffSalary + ticketRoll + mechanic + IncDF + DCBonus);
			// calucution of total Grand Total cost
			GrandTotal += round((totalCollection - daycloseingExpese), 2);
			// calucution of total per single Total cost
			perSingleCost += round((GrandTotal / totalRunSingle), 2);

			closeCollection.setSTAFF_SALARY(staffSalary);
			closeCollection.setROLL_NUMBER(ROLL_NUMBER);
			closeCollection.setROLL_PRICE(ROLL_PRICE);
			closeCollection.setTICKET_ROLL(ticketRoll);
			closeCollection.setMECHANIC_MAINTANCE(mechanic);
			closeCollection.setINSURENCE_MAINTANCE(IncDF);
			closeCollection.setDC_BONUS(DCBonus);
			closeCollection.setPER_DAY_SINGL(perDaySingl);

			closeCollection.setGRAND_TOTAL(GrandTotal);

			closeCollection.setTOTAL_GROUP_COLLECTION(GrandTotal);
			closeCollection.setPER_SINGL_COLLECTION(perSingleCost);

			// calucution of total per all bus single
			Integer TotalPerDaySingleALL = totalBus * perDaySingl;
			closeCollection.setTOTAL_SINGL(TotalPerDaySingleALL);
			// calucution of total netTotal = (totalsingle*totalbus) *
			// perdaysinglecost
			netTotal += (TotalPerDaySingleALL * perSingleCost);
			closeCollection.setNET_TOTAL(round(netTotal, 2));
			// calucution of total each bus cost
			eachBusCollection += (netTotal / totalBus);
			closeCollection.setEACH_BUS_COLLECTION(round(eachBusCollection, 2));

			Integer cut_Singl = TotalPerDaySingleALL - totalRunSingle;
			closeCollection.setTOTAL_CUT_SINGL(cut_Singl);
			closeCollection.setTRIP_CLOSE_STATUS_ID(TripDailySheetStatus.TRIP_STATUS_CLOSED);
			closeCollection.setMarkForDelete(false);

			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			closeCollection.setTRIP_CLOSED_DATE(toDate);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return closeCollection;
	}

	@RequestMapping(value = "/reopenTripCol", method = RequestMethod.GET)
	public ModelAndView reopenTripCol(@RequestParam("ID") final Long TripCollectionID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (TripCollectionID != null) {
				TripCollectionSheetDto CollectionSheet = (TripCollectionService
						.ReOpen_TripCollection_Sheet(TripCollectionID, userDetails.getCompany_id()));
				if (CollectionSheet != null) {

					String TripCollectionDate = driverAttendanceFormat.format(CollectionSheet.getTRIP_OPEN_DATE_ON());
					List<TripGroupCollectionDto> GroupSheet = TripCollectionService
							.List_TripGroupCollection_closeDate(TripCollectionDate, TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());
					if (GroupSheet != null && !GroupSheet.isEmpty()) {

						model.put("alreadyClosed", true);
						return new ModelAndView("redirect:/closeDailyColShow.in?date=" + TripCollectionDate + "",
								model);

					} else {

						TripCollectionService.Update_ReOpen_Status_TripCollectionSheet(CollectionSheet.getTRIPCOLLID(),
								TripDailySheetStatus.TRIP_STATUS_OPEN, userDetails.getCompany_id());

						TripCollectionService.Update_ReOpen_To_Subtrack_TripGroupCollection(
								CollectionSheet.getTOTAL_INCOME(), CollectionSheet.getTOTAL_EXPENSE(),
								CollectionSheet.getTOTAL_BALANCE(), CollectionSheet.getTRIP_DIESEL_LITER(),
								CollectionSheet.getTRIP_SINGL(), 1, TripCollectionDate,
								CollectionSheet.getVEHICLE_GROUP_ID(), userDetails.getCompany_id());
						model.put("RO", true);
					}

				} else {
					return new ModelAndView("redirect:/newTripCol.in?error=true");
				}
			}

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		}finally {
			userDetails		= null;
		}
		return new ModelAndView("redirect:/showTripCol.in?ID=" + TripCollectionID + "", model);
	}

	@RequestMapping(value = "/editTripCol", method = RequestMethod.GET)
	public ModelAndView editTripSheet(@RequestParam("ID") final Long TripCollectionID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (TripCollectionID != null) {

				TripCollectionSheetDto CollectionSheet = TripCollectionService.EDIT_TripCollection_Sheet(TripCollectionID, userDetails.getCompany_id());
				if (CollectionSheet != null) {
					model.put("TripCol", CollectionSheet);
				} else {
					return new ModelAndView("redirect:/newTripCol.in?error=true");
				}
			}

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		}finally {
			userDetails	= null;
		}
		return new ModelAndView("editTripCollection", model);
	}

	@RequestMapping(value = "/updateTripCol", method = RequestMethod.POST)
	public ModelAndView updateTripCol(@ModelAttribute("command") TripCollectionSheetDto CollectionSheetDto,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		CustomUserDetails	userDetails	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (CollectionSheetDto.getVID() != 0 && CollectionSheetDto.getTRIP_DRIVER_ID() != 0
					&& CollectionSheetDto.getTRIP_CONDUCTOR_ID() != 0 && CollectionSheetDto.getTRIP_ROUTE_ID() != 0) {
				TripCollectionSheetDto oldCollectionSheet = TripCollectionService
						.EDIT_TripCollection_Sheet(CollectionSheetDto.getTRIPCOLLID(), userDetails.getCompany_id());
				if (oldCollectionSheet != null) {

					TripCollectionSheet Sheet = prepare_Model_UPDATE_TripCollectionSheet(CollectionSheetDto,
							oldCollectionSheet);

					TripCollectionService.add_TripCollectionSheet(Sheet);

					model.put("updateTripCol", true);
					// System.out.println(currentODDMETER);
					// this Update Current Odometer in Vehicle Getting Trip
					// Daily Sheet
					Update_Current_OdometerinVehicle_getTripDailySheetTo(Sheet);
				}
			} else {
				return new ModelAndView("redirect:/addTripCol.in?error=true");
			}

		} catch (Exception e) {
			System.err.println("inside catch "+e);
			model.put("alreadyTripCol", true);

			LOGGER.error("TripSheet Page:", e);
			return new ModelAndView("redirect:/newTripCol.in", model);

		}finally {
			userDetails = null;
		}
		return new ModelAndView("redirect:/showTripCol.in?ID=" + CollectionSheetDto.getTRIPCOLLID() + "");
	}

	@RequestMapping(value = "/deleteTripCol", method = RequestMethod.GET)
	public ModelAndView deleteTripCol(@RequestParam("ID") final Long TripCollectionID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (TripCollectionID != null) {
				TripCollectionService.delete_TripCollectionExpense_collectionID(TripCollectionID, userDetails.getCompany_id());
				TripCollectionService.delete_TripCollectionIncome_collectionId(TripCollectionID, userDetails.getCompany_id());
				TripCollectionService.delete_TripCollectionSheet(TripCollectionID, userDetails.getCompany_id());
				model.put("deleteSucess", true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("redirect:/newTripCol.in", model);
	}

	/*****************************************************************************
	 * Testing Loading select json new waY
	 ******************************************************************************/

	@RequestMapping(value = "/getConductorList", method = RequestMethod.POST)
	public void getDriver2List(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<DriverDto> Driver = new ArrayList<DriverDto>();
		List<Driver> driverDtos = driverService.SearchOnlyConductorNAME(term);
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
	@RequestMapping(value = "/getCheckingInspectorList", method = RequestMethod.POST)
	public void getCheckingInspector(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<DriverDto> Driver = new ArrayList<DriverDto>();
		List<Driver> driverDtos = driverService.SearchOnlyCheckingInspectorNAME(term);
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
	@RequestMapping(value = "/getCheckingInspectorListByGroupId", method = RequestMethod.GET)
	public void getCheckingInspectorByGroupId(Map<String, Object> map, @RequestParam(value = "vehicleGroupId", required = true) Long vehicleGroupId,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<DriverDto> Driver = new ArrayList<DriverDto>();
		List<Driver> driverDtos = driverService.SearchOnlyCheckingInspectorNAME(vehicleGroupId);
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
	
	@RequestMapping(value = "/getAllCheckingInspectorList", method = RequestMethod.GET)
	public void getAllCheckingInspectorList(Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<DriverDto> Driver = new ArrayList<DriverDto>();
			List<Driver> driverDtos = driverService.getCheckingInspectorList(userDetails.getCompany_id(), userDetails.getId());
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
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value = "/getAllCConductorList", method = RequestMethod.POST)
	public void getAllCConductorList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<DriverDto> Driver = new ArrayList<DriverDto>();
			List<Driver> driverDtos = driverService.getConductorList(userDetails.getCompany_id(), userDetails.getId(), term);
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
		} catch (Exception e) {
			throw e;
		}
	}



	/*****************************************************************************
	 * Testing Loading datatable using Gson Close
	 ******************************************************************************/

	// this Update Current Odometer in Vehicle Getting Trip Daily Sheet
	public void Update_Current_OdometerinVehicle_getTripDailySheetTo(TripCollectionSheet Sheet) {
		List<ServiceReminderDto>		serviceList					= null;
		Integer currentODDMETER;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(Sheet.getVID());

			// System.out.println(currentODDMETER);
			// update the Current Odometer vehicle Databases tables
			try {
				if (Sheet.getVID() != 0) {
					if (currentODDMETER < Sheet.getTRIP_OPEN_KM()) {
						
						vehicleService.updateCurrentOdoMeter(Sheet.getVID(), Sheet.getTRIP_OPEN_KM(), userDetails.getCompany_id());
						// update current Odometer update Service
						// Reminder
						ServiceReminderService.updateCurrentOdometerToServiceReminder(Sheet.getVID(),
								Sheet.getTRIP_OPEN_KM(), userDetails.getCompany_id());
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(Sheet.getVID(), userDetails.getCompany_id(), userDetails.getId());
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
								.prepareOdometerGetHistoryToTripCollectionSheet(Sheet);
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

	public void Update_Current_OdometerinVehicle_getTripDailySheetTo(TripCollectionSheetDto Sheet) {
		List<ServiceReminderDto>		serviceList					= null;
		Integer currentODDMETER;
		try {
			currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(Sheet.getVID());
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			// System.out.println(currentODDMETER);
			// update the Current Odometer vehicle Databases tables
			try {
				if (Sheet.getVID() != 0) {
					if (currentODDMETER < Sheet.getTRIP_OPEN_KM()) {
						
						vehicleService.updateCurrentOdoMeter(Sheet.getVID(), Sheet.getTRIP_OPEN_KM(), userDetails.getCompany_id());
						// update current Odometer update Service
						// Reminder
						ServiceReminderService.updateCurrentOdometerToServiceReminder(Sheet.getVID(),
								Sheet.getTRIP_OPEN_KM(), userDetails.getCompany_id());
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(Sheet.getVID(), userDetails.getCompany_id(), userDetails.getId());
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
								.prepareOdometerGetHistoryToTripCollectionSheet(Sheet);
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

	
	// save the TripSheet Model
	public TripCollectionSheet prepareModel_Save_TripCollectionSheet(TripCollectionSheetDto CollectionDto) {
		TripCollectionSheet status = new TripCollectionSheet();

		status.setTRIPCOLLID(CollectionDto.getTRIPCOLLID());
		status.setVID(CollectionDto.getVID());

		try {
			/*if (CollectionDto.getVID() != 0) {
				VehicleDto vehiclename = vehicleService.getVehicle_Details_TripSheet_Entries(CollectionDto.getVID());
				if (vehiclename != null) {
					// show Vehicle name
					status.setVEHICLE_REGISTRATION(vehiclename.getVehicle_registration());
					status.setVEHICLE_GROUP(vehiclename.getVehicle_Group());
					status.setVEHICLE_GROUP_ID(vehiclename.getVehicleGroupId());
				}
			}*/
			status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());

			java.util.Date date = dateFormat.parse(CollectionDto.getTRIP_OPEN_DATE());
			java.sql.Date fromDate = new Date(date.getTime());
			status.setTRIP_OPEN_DATE(fromDate);

			status.setTRIP_ROUTE_ID(CollectionDto.getTRIP_ROUTE_ID());
			/*if (CollectionDto.getTRIP_ROUTE_ID() != 0) {
				TripRoute TripRoute = TRoute
						.prepareTripRouteBean(TripRouteService.getTripRoute(CollectionDto.getTRIP_ROUTE_ID()));
				status.setTRIP_ROUTE_NAME(TripRoute.getRouteName());
			}*/
			// System.out.println(TripSheetDto.getTripFristDriverID());
			status.setTRIP_DRIVER_ID(CollectionDto.getTRIP_DRIVER_ID());
			/*if (CollectionDto.getTRIP_DRIVER_ID() != 0) {
				DriverDto driverDto = DBL.GetDriverID(
						driverService.getDriver_Details_Firstnamr_LastName(CollectionDto.getTRIP_DRIVER_ID()));
				status.setTRIP_DRIVER_NAME(driverDto.getDriver_firstname() + "_" + driverDto.getDriver_Lastname());

			}*/
			// System.out.println(TripSheetDto.getTripSecDriverID());
			status.setTRIP_CONDUCTOR_ID(CollectionDto.getTRIP_CONDUCTOR_ID());
			/*if (CollectionDto.getTRIP_CONDUCTOR_ID() != 0) {
				DriverDto driverDtoCon = DBL.GetDriverID(
						driverService.getDriver_Details_Firstnamr_LastName(CollectionDto.getTRIP_CONDUCTOR_ID()));
				status.setTRIP_CONDUCTOR_NAME(
						driverDtoCon.getDriver_firstname() + "_" + driverDtoCon.getDriver_Lastname());

			}*/
			// System.out.println(TripSheetDto.getTripCleanerID());
			status.setTRIP_CLEANER_ID(CollectionDto.getTRIP_CLEANER_ID());
			/*if (CollectionDto.getTRIP_CLEANER_ID() != 0) {
				DriverDto driverDtoClen = DBL.GetDriverID(driverService.getDriver_Details_Firstnamr_LastName(CollectionDto.getTRIP_CLEANER_ID()));
				status.setTRIP_CLEANER_NAME(
						driverDtoClen.getDriver_firstname() + "_" + driverDtoClen.getDriver_Lastname());

			}*/

		} catch (Exception e) {
			e.printStackTrace();
		}

		status.setTRIP_DIESEL_LITER(CollectionDto.getTRIP_DIESEL_LITER());
		status.setTRIP_SINGL(CollectionDto.getTRIP_SINGL());

		status.setTOTAL_BALANCE(0.0);
		status.setTOTAL_EXPENSE(0.0);
		status.setTOTAL_INCOME(0.0);
		status.setTRIP_DRIVER_JAMA(0.0);
		status.setTRIP_CONDUCTOR_JAMA(0.0);
		status.setDRIVER_ADVANCE_STATUS_ID(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
		status.setCONDUCTOR_ADVANCE_STATUS_ID(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
		status.setTRIP_CLOSE_STATUS_ID(TripDailySheetStatus.TRIP_STATUS_OPEN);

		status.setTRIP_REMARKS(CollectionDto.getTRIP_REMARKS());
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		status.setCREATED(toDate);
		status.setlASTUPDATED(toDate);
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		status.setCREATEDBYID(userDetails.getId());
		status.setLASTMODIFIEDBYID(userDetails.getId());
		status.setCOMPANY_ID(userDetails.getCompany_id());
		status.setMarkForDelete(false);

		return status;
	}

	// save the TripSheet Model
	public TripCollectionSheet prepare_Model_UPDATE_TripCollectionSheet(TripCollectionSheetDto CollectionDto,
			TripCollectionSheetDto OLDCollectionDto) {
		TripCollectionSheet status = new TripCollectionSheet();

		status.setTRIPCOLLID(OLDCollectionDto.getTRIPCOLLID());
		status.setTRIPCOLLNUMBER(OLDCollectionDto.getTRIPCOLLNUMBER());
		status.setVID(CollectionDto.getVID());

		try {
			/*if (CollectionDto.getVID() != 0) {
				VehicleDto vehiclename = vehicleService.getVehicle_Details_TripSheet_Entries(CollectionDto.getVID());
				if (vehiclename != null) {
					// show Vehicle name
					status.setVEHICLE_REGISTRATION(vehiclename.getVehicle_registration());
					status.setVEHICLE_GROUP(vehiclename.getVehicle_Group());
					status.setVEHICLE_GROUP_ID(vehiclename.getVehicleGroupId());
				}
			}*/
			
			status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());
			java.util.Date date = dateFormat.parse(CollectionDto.getTRIP_OPEN_DATE());
			java.sql.Date fromDate = new Date(date.getTime());
			status.setTRIP_OPEN_DATE(fromDate);

			status.setTRIP_ROUTE_ID(CollectionDto.getTRIP_ROUTE_ID());
			/*if (CollectionDto.getTRIP_ROUTE_ID() != 0) {
				TripRoute TripRoute = TRoute
						.prepareTripRouteBean(TripRouteService.getTripRoute(CollectionDto.getTRIP_ROUTE_ID()));
				status.setTRIP_ROUTE_NAME(TripRoute.getRouteName());
			}*/
			// System.out.println(TripSheetDto.getTripFristDriverID());
			status.setTRIP_DRIVER_ID(CollectionDto.getTRIP_DRIVER_ID());
			/*if (CollectionDto.getTRIP_DRIVER_ID() != 0) {
				DriverDto driverDto = DBL.GetDriverID(
						driverService.getDriver_Details_Firstnamr_LastName(CollectionDto.getTRIP_DRIVER_ID()));
				status.setTRIP_DRIVER_NAME(driverDto.getDriver_firstname() + "_" + driverDto.getDriver_Lastname());

			}*/
			// System.out.println(TripSheetDto.getTripSecDriverID());
			status.setTRIP_CONDUCTOR_ID(CollectionDto.getTRIP_CONDUCTOR_ID());
			/*if (CollectionDto.getTRIP_CONDUCTOR_ID() != 0) {
				DriverDto driverDtoCon = DBL.GetDriverID(
						driverService.getDriver_Details_Firstnamr_LastName(CollectionDto.getTRIP_CONDUCTOR_ID()));
				status.setTRIP_CONDUCTOR_NAME(
						driverDtoCon.getDriver_firstname() + "_" + driverDtoCon.getDriver_Lastname());

			}*/
			// System.out.println(TripSheetDto.getTripCleanerID());
			status.setTRIP_CLEANER_ID(CollectionDto.getTRIP_CLEANER_ID());
			/*if (CollectionDto.getTRIP_CLEANER_ID() != 0) {
				DriverDto driverDtoCle = DBL.GetDriverID(
						driverService.getDriver_Details_Firstnamr_LastName(CollectionDto.getTRIP_CLEANER_ID()));
				status.setTRIP_CLEANER_NAME(
						driverDtoCle.getDriver_firstname() + "_" + driverDtoCle.getDriver_Lastname());

			}*/

		} catch (Exception e) {
			e.printStackTrace();
		}

		status.setTRIP_DIESEL_LITER(CollectionDto.getTRIP_DIESEL_LITER());
		status.setTRIP_SINGL(CollectionDto.getTRIP_SINGL());

		status.setTOTAL_BALANCE(OLDCollectionDto.getTOTAL_BALANCE());
		status.setTOTAL_EXPENSE(OLDCollectionDto.getTOTAL_EXPENSE());
		status.setTOTAL_INCOME(OLDCollectionDto.getTOTAL_INCOME());
		status.setTRIP_DRIVER_JAMA(OLDCollectionDto.getTRIP_DRIVER_JAMA());
		status.setTRIP_CONDUCTOR_JAMA(OLDCollectionDto.getTRIP_CONDUCTOR_JAMA());
		status.setTRIP_CONDUCTOR_JAMA(OLDCollectionDto.getTRIP_CONDUCTOR_JAMA());
		status.setDRIVER_ADVANCE_STATUS_ID(OLDCollectionDto.getDRIVER_ADVANCE_STATUS_ID());
		status.setCONDUCTOR_ADVANCE_STATUS_ID(OLDCollectionDto.getCONDUCTOR_ADVANCE_STATUS_ID());
		status.setTRIP_CLOSE_STATUS_ID(OLDCollectionDto.getTRIP_CLOSE_STATUS_ID());

		status.setTRIP_REMARKS(CollectionDto.getTRIP_REMARKS());
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		//status.setCREATED(OLDCollectionDto.getCREATED());
		status.setlASTUPDATED(toDate);
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//status.setCREATEDBY(OLDCollectionDto.getCREATEDBY());
		status.setLASTMODIFIEDBYID(userDetails.getId());
		status.setMarkForDelete(false);
		status.setCOMPANY_ID(userDetails.getCompany_id());

		return status;
	}

}