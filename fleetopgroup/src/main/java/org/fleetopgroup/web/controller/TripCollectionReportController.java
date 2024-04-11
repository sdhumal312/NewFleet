package org.fleetopgroup.web.controller;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.TripDailySheetStatus;
import org.fleetopgroup.persistence.bl.TripCollectionBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverAdvanceDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.TripCollectionSheetDto;
import org.fleetopgroup.persistence.dto.TripGroupCollectionDto;
import org.fleetopgroup.persistence.model.TripDayCollection;
import org.fleetopgroup.persistence.serviceImpl.IDriverAdvanceService;
import org.fleetopgroup.persistence.serviceImpl.ITripCollectionService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
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

@Controller
public class TripCollectionReportController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Autowired
	private ITripCollectionService TripCollectionService;
	
	@Autowired
	private IUserProfileService userProfileService;
	
	@Autowired
	private IDriverAdvanceService driverAdvanceService;


	TripCollectionBL TCBL = new TripCollectionBL();
	
	SimpleDateFormat dateFormatonlyDate = new SimpleDateFormat("dd-MMM-yyyy");

	SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
	DecimalFormat AMOUNTFORMAT = new DecimalFormat("##,##,##0");
	
	@RequestMapping("/TCR")
	public ModelAndView TripCollectionReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("TCR", model);
	}
	
	@RequestMapping(value = "/DailyTripCollectionReport", method = RequestMethod.POST)
	public ModelAndView DailyTripCollectionReport(
			@ModelAttribute("command") @RequestParam("TRIP_DATE") final String TRIP_DATERANGE,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		try {
			if (TRIP_DATERANGE != "" && TRIP_DATERANGE != null) {

				java.util.Date date = dateFormatSQL.parse(TRIP_DATERANGE);
				try {
					List<TripGroupCollectionDto> GroupSheet = TripCollectionService
							.List_TripGroupCollection_closeDate(TRIP_DATERANGE, TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());
					if (GroupSheet != null && !GroupSheet.isEmpty()) {

						List<TripCollectionSheetDto> CollectionSheet = TripCollectionService
								.List_TripCollectionSheet_closeDate(TRIP_DATERANGE, TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails);

						model.put("TripGroupCol",
								TCBL.Show_CloseDaily_tripCollection_Sheet(GroupSheet, CollectionSheet));

						model.put("SEARCHDATE", TRIP_DATERANGE);

						model.put("TripDayCol",
								TripCollectionService.List_TripDayCollection_CloseDate(TRIP_DATERANGE, TripDailySheetStatus.TRIP_STATUS_CLOSED));

					}

					model.put("TRIPCOL_DATE", dateFormatonlyDate.format(date));

					Date yesterday = new Date(date.getTime() - 1 * 24 * 3600 * 1000);
					model.put("YESTERDAY", dateFormatSQL.format(yesterday));
					Date tomorrow = new Date(date.getTime() + 1 * 24 * 3600 * 1000);
					model.put("TOMORROW", dateFormatSQL.format(tomorrow));

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
		return new ModelAndView("Daily_Trip_Collection_Report", model);
	}

	@RequestMapping(value = "/VehicleWiseTripColReport", method = RequestMethod.POST)
	public ModelAndView VehicleWiseTripColReport(
			@ModelAttribute("command") @RequestParam("VEHICLE_ID") final Integer VEHICLE_ID,
			@RequestParam("VEHICLE_TC_DATERAGE") final String VEHICLE_TC_DATERAGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {

			if (VEHICLE_TC_DATERAGE != "" && VEHICLE_ID != null) {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = VEHICLE_TC_DATERAGE.split(" to ");

					dateRangeFrom = From_TO_DateRange[0];
					dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String Vid = "", TripOpenDate_Close = "";

					if (VEHICLE_ID != null && VEHICLE_ID != 0) {
						Vid = " AND R.VID=" + VEHICLE_ID + " ";
					}

					if (dateRangeFrom != "" && dateRangeTo != "") {
						TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '"
								+ dateRangeTo + "' ";
					}

					String query = " " + Vid + " " + " " + TripOpenDate_Close + " ";

					List<TripCollectionSheetDto> Collection = TripCollectionService.List_TripCollectionSheet_Report(query);
					model.put("TripCol", TCBL.prepare_TripCollectionList(Collection));

					Double TripExpenseTotal = TCBL.Total_Expense_TripCollection(Collection);
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					String TotalExpenseShow = AMOUNTFORMAT.format(TripExpenseTotal);
					model.put("TripExpenseTotal", TotalExpenseShow);

					Double TripIncomeTotal = TCBL.Total_Income_TripCollection(Collection);
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					String TotalIncomeShow = AMOUNTFORMAT.format(TripIncomeTotal);
					model.put("TripIncomeTotal", TotalIncomeShow);

					Double TripAccBanlanceTotal = TCBL.Total_Balance_TripCollection(Collection);
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					String TotalShow = AMOUNTFORMAT.format(TripAccBanlanceTotal);
					model.put("TripBanlanceTotal", TotalShow);

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

		return new ModelAndView("Vehicle_Wise_TripCollection_Report", model);
	}

	@RequestMapping(value = "/GroupWiseTripColReport", method = RequestMethod.POST)
	public ModelAndView GroupWiseTripColReport(
			@ModelAttribute("command") @RequestParam("TRIP_GROUP") final long TRIP_GROUP,
			@RequestParam("VEHICLE_TC_DATERAGE") final String VEHICLE_TC_DATERAGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		try {

			if (VEHICLE_TC_DATERAGE != "" && TRIP_GROUP != 0) {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = VEHICLE_TC_DATERAGE.split(" to ");

					dateRangeFrom = From_TO_DateRange[0];
					dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {

					String Vehicle_group = "", TripOpenDate_Close = "";

					if (TRIP_GROUP != 0) {
						Vehicle_group = " AND R.VEHICLE_GROUP_ID=" + TRIP_GROUP + " ";
					}

					if (dateRangeFrom != "" && dateRangeTo != "") {
						TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '"
								+ dateRangeTo + "' ";
					}

					String query = " " + Vehicle_group + " " + TripOpenDate_Close + " ";

					List<TripGroupCollectionDto> Collection = TripCollectionService.List_TripGroupCollection_Report(query, userDetails.getCompany_id());
					model.put("TripGroupCol", TCBL.prepare_Trip_Group_CollectionDto(Collection));

					Double TripExpenseTotal = TCBL.Total_Expense_TripGroupCollection(Collection);
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					String TotalExpenseShow = AMOUNTFORMAT.format(TripExpenseTotal);
					model.put("TripExpenseTotal", TotalExpenseShow);

					Double TripIncomeTotal = TCBL.Total_Income_TripGroupCollection(Collection);
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					String TotalIncomeShow = AMOUNTFORMAT.format(TripIncomeTotal);
					model.put("TripIncomeTotal", TotalIncomeShow);

					Double TripAccBanlanceTotal = TCBL.Total_Balance_TripGroupCollection(Collection);
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					String TotalShow = AMOUNTFORMAT.format(TripAccBanlanceTotal);
					model.put("TripBanlanceTotal", TotalShow);

					Double TripGroupCollectioTotal = TCBL.Total_GroupCol_TripGroupCollection(Collection);
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					String TotalShowGroup = AMOUNTFORMAT.format(TripGroupCollectioTotal);
					model.put("TripGroupTotal", TotalShowGroup);

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
		return new ModelAndView("Group_Wise_TripCollection_Report", model);

	}

	@RequestMapping(value = "/DateWiseTripColReport", method = RequestMethod.POST)
	public ModelAndView DateWiseTripColReport(
			@ModelAttribute("command") @RequestParam("TRIP_DATE_RANGE") final String TRIP_DATE_RANGE,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		try {

			if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null) {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = TRIP_DATE_RANGE.split(" to ");

					dateRangeFrom = From_TO_DateRange[0];
					dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String TripOpenDate_Close = "";

					if (dateRangeFrom != "" && dateRangeTo != "") {
						TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '"
								+ dateRangeTo + "' ";
					}

					String query = " " + TripOpenDate_Close + " ";

					List<TripDayCollection> Collection = TripCollectionService.List_TripDayCollection_Report(query, userDetails);
					model.put("TripDayCol", TCBL.prepare_Trip_Day_CollectionDto(Collection));

					Double TripExpenseTotal = TCBL.Total_Expense_TripDayCollection(Collection);
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					String TotalExpenseShow = AMOUNTFORMAT.format(TripExpenseTotal);
					model.put("TripExpenseTotal", TotalExpenseShow);

					Double TripIncomeTotal = TCBL.Total_Income_TripDayCollection(Collection);
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					String TotalIncomeShow = AMOUNTFORMAT.format(TripIncomeTotal);
					model.put("TripIncomeTotal", TotalIncomeShow);

					Double TripAccBanlanceTotal = TCBL.Total_Balance_TripDayCollection(Collection);
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					String TotalShow = AMOUNTFORMAT.format(TripAccBanlanceTotal);
					model.put("TripBanlanceTotal", TotalShow);

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
		return new ModelAndView("DateRange_Wise_TripCollection_Report", model);
	}

	// Note: This Total Driver Date Range Driver Balance and Advance Details
		// Show Report
		@RequestMapping(value = "/TotalDriverBalanceReport", method = RequestMethod.POST)
		public ModelAndView TotalDriverBalanceReport(
				@ModelAttribute("command") @RequestParam("TRIP_DRIVER_ID") final Integer TRIP_DRIVER_ID,
				@RequestParam("TRIP_DATE_RANGE") final String TRIP_DATE_RANGE, final HttpServletRequest request) {

			Map<String, Object> model = new HashMap<String, Object>();
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			try {

				if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null) {

					String dateRangeFrom = "", dateRangeTo = "";

					String[] From_TO_DateRange = null;
					try {

						From_TO_DateRange = TRIP_DATE_RANGE.split(" to ");

						dateRangeFrom = From_TO_DateRange[0];
						dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

					} catch (Exception e) {
						LOGGER.error("fuelmileage_vid:", e);
					}

					String DRIVER_ID = "", CLEANER_ID = "", TripOpenDate_Close = "", ADVANCE_DATE = "";
					try {

						if (TRIP_DRIVER_ID != 0) {
							DRIVER_ID = " AND TRIP_DRIVER_ID =" + TRIP_DRIVER_ID + " ";
						}

						if (dateRangeFrom != "" && dateRangeTo != "") {
							TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '"
									+ dateRangeTo + "' ";

							ADVANCE_DATE = "  AND R.ADVANCE_DATE between '" + dateRangeFrom + "' AND '" + dateRangeTo
									+ "' ";
						}

						String TripCollection_Query = " " + DRIVER_ID + " " + CLEANER_ID + " " + TripOpenDate_Close + " ";

						List<TripCollectionSheetDto> Collection = TripCollectionService
								.List_DriverJAMA_TripCollectionSheet_Report(TripCollection_Query, userDetails.getCompany_id());

						String ADVANCE_Query = " " + DRIVER_ID + " " + ADVANCE_DATE + " ";

						List<DriverAdvanceDto> AdvanceCollection = driverAdvanceService
								.List_DriverAdvance_TOTAL_Report(ADVANCE_Query, userDetails.getCompany_id());

						model.put("TripCol", TCBL.prepare_Driver_JAMA_AND_Adavance_Report(Collection, AdvanceCollection));

						Double TripAccBanlanceTotal = TCBL.Total_DriverJAMA_TripCollection(Collection);
						AMOUNTFORMAT.setMaximumFractionDigits(0);
						String TotalShow = AMOUNTFORMAT.format(TripAccBanlanceTotal);
						model.put("DriverJAMATotal", TotalShow);

						Double TripIncomeTotal = TCBL.Driver_Advance_Total_Amt(AdvanceCollection);
						AMOUNTFORMAT.setMaximumFractionDigits(0);
						String TotalIncomeShow = AMOUNTFORMAT.format(TripIncomeTotal);
						model.put("AdvanceTotal", TotalIncomeShow);

						AMOUNTFORMAT.setMaximumFractionDigits(0);
						String TotalBalance = AMOUNTFORMAT.format(TripAccBanlanceTotal - TripIncomeTotal);
						model.put("DriverBalance", TotalBalance);

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
			return new ModelAndView("Driver_Total_Balance_Report", model);
		}

		// Note: This Total Driver Date Range Driver Balance and Advance Details
		// Show Report
		@RequestMapping(value = "/TotalConductorBalanceReport", method = RequestMethod.POST)
		public ModelAndView TotalConductorBalanceReport(
				@ModelAttribute("command") @RequestParam("TRIP_CONDUCTOR_ID") final Integer TRIP_CONDUCTOR_ID,
				@RequestParam("TRIP_DATE_RANGE") final String TRIP_DATE_RANGE, final HttpServletRequest request) {

			Map<String, Object> model = new HashMap<String, Object>();
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			try {

				if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null) {

					String dateRangeFrom = "", dateRangeTo = "";

					String[] From_TO_DateRange = null;
					try {

						From_TO_DateRange = TRIP_DATE_RANGE.split(" to ");

						dateRangeFrom = From_TO_DateRange[0];
						dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

					} catch (Exception e) {
						LOGGER.error("fuelmileage_vid:", e);
					}

					String CONDUCTOR_ID = "", DRIVER_ID = "", TripOpenDate_Close = "", ADVANCE_DATE = "";
					try {

						if (TRIP_CONDUCTOR_ID != 0) {
							CONDUCTOR_ID = " AND TRIP_CONDUCTOR_ID=" + TRIP_CONDUCTOR_ID + " ";
						}

						if (TRIP_CONDUCTOR_ID != 0) {
							DRIVER_ID = " AND TRIP_DRIVER_ID =" + TRIP_CONDUCTOR_ID + " ";
						}
						if (dateRangeFrom != "" && dateRangeTo != "") {
							TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '"
									+ dateRangeTo + "' ";

							ADVANCE_DATE = "  AND R.ADVANCE_DATE between '" + dateRangeFrom + "' AND '" + dateRangeTo
									+ "' ";
						}

						String TripCollection_Query = " " + CONDUCTOR_ID + " " + TripOpenDate_Close + " ";

						List<TripCollectionSheetDto> Collection = TripCollectionService
								.List_DriverJAMA_TripCollectionSheet_Report(TripCollection_Query, userDetails.getCompany_id());

						String ADVANCE_Query = " " + DRIVER_ID + " " + ADVANCE_DATE + " ";

						List<DriverAdvanceDto> AdvanceCollection = driverAdvanceService
								.List_DriverAdvance_TOTAL_Report(ADVANCE_Query, userDetails.getCompany_id());

						model.put("TripCol", TCBL.prepare_Driver_JAMA_AND_Adavance_Report(Collection, AdvanceCollection));

						Double TripExpenseTotal = TCBL.Total_ConductorJAMA_TripCollection(Collection);
						AMOUNTFORMAT.setMaximumFractionDigits(0);
						String TotalExpenseShow = AMOUNTFORMAT.format(TripExpenseTotal);
						model.put("ConductorJAMATotal", TotalExpenseShow);

						Double TripIncomeTotal = TCBL.Driver_Advance_Total_Amt(AdvanceCollection);
						AMOUNTFORMAT.setMaximumFractionDigits(0);
						String TotalIncomeShow = AMOUNTFORMAT.format(TripIncomeTotal);
						model.put("AdvanceTotal", TotalIncomeShow);

						AMOUNTFORMAT.setMaximumFractionDigits(0);
						String TotalBalance = AMOUNTFORMAT.format(TripExpenseTotal - TripIncomeTotal);
						model.put("DriverBalance", TotalBalance);

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
			return new ModelAndView("Conductor_Total_Balance_Report", model);
		}

		@RequestMapping(value = "/DriverJamaTripColReport", method = RequestMethod.POST)
		public ModelAndView DriverJamaTripColReport(
				@ModelAttribute("command") @RequestParam("TRIP_DRIVER_ID") final Integer TRIP_DRIVER_ID,
				@RequestParam("TRIP_DATE_RANGE") final String TRIP_DATE_RANGE, final HttpServletRequest request) {

			Map<String, Object> model = new HashMap<String, Object>();
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			try {

				if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null) {

					String dateRangeFrom = "", dateRangeTo = "";

					String[] From_TO_DateRange = null;
					try {

						From_TO_DateRange = TRIP_DATE_RANGE.split(" to ");

						dateRangeFrom = From_TO_DateRange[0];
						dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

					} catch (Exception e) {
						LOGGER.error("fuelmileage_vid:", e);
					}

					try {
						String DRIVER_ID = "", CONDUCTOR_ID = "", CLEANER_ID = "", TripOpenDate_Close = "";

						if (TRIP_DRIVER_ID != 0) {
							DRIVER_ID = " AND TRIP_DRIVER_ID =" + TRIP_DRIVER_ID + " ";
						}

						/*
						 * if (CollectionSheet.getTRIP_CLEANER_ID() != 0) { CLEANER_ID =
						 * " AND TRIP_CLEANER_ID=" + CollectionSheet.getTRIP_CLEANER_ID() + " "; }
						 */

						if (dateRangeFrom != "" && dateRangeTo != "") {
							TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '"
									+ dateRangeTo + "' ";
						}

						String query = " " + DRIVER_ID + " " + CONDUCTOR_ID + " " + CLEANER_ID + " " + TripOpenDate_Close
								+ " ";

						List<TripCollectionSheetDto> Collection = TripCollectionService
								.List_DriverJAMA_TripCollectionSheet_Report(query, userDetails.getCompany_id());
						model.put("TripCol", TCBL.prepare_Driver_JAMA_TripCollectionDto(Collection));

						Double TripIncomeTotal = TCBL.Total_Income_TripCollection(Collection);
						AMOUNTFORMAT.setMaximumFractionDigits(0);
						String TotalIncomeShow = AMOUNTFORMAT.format(TripIncomeTotal);
						model.put("TripIncomeTotal", TotalIncomeShow);

						Double TripAccBanlanceTotal = TCBL.Total_DriverJAMA_TripCollection(Collection);
						AMOUNTFORMAT.setMaximumFractionDigits(0);
						String TotalShow = AMOUNTFORMAT.format(TripAccBanlanceTotal);
						model.put("DriverJAMATotal", TotalShow);

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
			return new ModelAndView("Driver_Jama_Collection_Report", model);
		}

		@RequestMapping(value = "/ConductorJamaTripColReport", method = RequestMethod.POST)
		public ModelAndView ConductorJamaTripColReport(
				@ModelAttribute("command") @RequestParam("TRIP_CONDUCTOR_ID") final Integer TRIP_CONDUCTOR_ID,
				@RequestParam("TRIP_DATE_RANGE") final String TRIP_DATE_RANGE, final HttpServletRequest request) {

			Map<String, Object> model = new HashMap<String, Object>();
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			try {

				if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null) {

					String dateRangeFrom = "", dateRangeTo = "";

					String[] From_TO_DateRange = null;
					try {

						From_TO_DateRange = TRIP_DATE_RANGE.split(" to ");

						dateRangeFrom = From_TO_DateRange[0];
						dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

					} catch (Exception e) {
						LOGGER.error("fuelmileage_vid:", e);
					}

					try {
						String CONDUCTOR_ID = "", CLEANER_ID = "", TripOpenDate_Close = "";

						if (TRIP_CONDUCTOR_ID != 0) {
							CONDUCTOR_ID = " AND TRIP_CONDUCTOR_ID=" + TRIP_CONDUCTOR_ID + " ";
						}

						if (dateRangeFrom != "" && dateRangeTo != "") {
							TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '"
									+ dateRangeTo + "' ";
						}

						String query = " " + CONDUCTOR_ID + " " + CLEANER_ID + " " + TripOpenDate_Close + " ";

						List<TripCollectionSheetDto> Collection = TripCollectionService
								.List_DriverJAMA_TripCollectionSheet_Report(query, userDetails.getCompany_id());
						model.put("TripCol", TCBL.prepare_Driver_JAMA_TripCollectionDto(Collection));

						Double TripIncomeTotal = TCBL.Total_Income_TripCollection(Collection);
						AMOUNTFORMAT.setMaximumFractionDigits(0);
						String TotalIncomeShow = AMOUNTFORMAT.format(TripIncomeTotal);
						model.put("TripIncomeTotal", TotalIncomeShow);

						Double TripExpenseTotal = TCBL.Total_ConductorJAMA_TripCollection(Collection);
						AMOUNTFORMAT.setMaximumFractionDigits(0);
						String TotalExpenseShow = AMOUNTFORMAT.format(TripExpenseTotal);
						model.put("ConductorJAMATotal", TotalExpenseShow);

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
			return new ModelAndView("Conductor_Jama_Collection_Report", model);
		}


		@RequestMapping(value = "/AdvanceJamaReport", method = RequestMethod.POST)
		public ModelAndView AdvanceJamaReport(
				@ModelAttribute("command") @RequestParam("TRIP_DRIVER_ID") final Integer TRIP_DRIVER_ID,
				@RequestParam("TRIP_DATE_RANGE") final String TRIP_DATE_RANGE, final HttpServletRequest request) {

			Map<String, Object> model = new HashMap<String, Object>();
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			try {

				if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null) {

					String dateRangeFrom = "", dateRangeTo = "";

					String[] From_TO_DateRange = null;
					try {

						From_TO_DateRange = TRIP_DATE_RANGE.split(" to ");

						dateRangeFrom = From_TO_DateRange[0];
						dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

					} catch (Exception e) {
						LOGGER.error("fuelmileage_vid:", e);
					}

					try {
						String DRIVER_ID = "", TripOpenDate_Close = "";

						if (TRIP_DRIVER_ID != 0) {
							DRIVER_ID = " AND R.TRIP_DRIVER_ID=" + TRIP_DRIVER_ID + " ";
						}

						if (dateRangeFrom != "" && dateRangeTo != "") {
							TripOpenDate_Close = "  AND R.ADVANCE_DATE between '" + dateRangeFrom + "' AND '" + dateRangeTo
									+ "' ";
						}

						String query = " " + DRIVER_ID + " " + TripOpenDate_Close + " ";

						List<DriverAdvanceDto> AdvanceCollection = driverAdvanceService.List_DriverAdvance_TOTAL_Report(query, userDetails.getCompany_id());
						model.put("DriverAdvance", TCBL.prepare_Driver_ADVANCE_JAMA_TripDto(AdvanceCollection));

						Double TripIncomeTotal = TCBL.Driver_Advance_Total_Amt(AdvanceCollection);
						AMOUNTFORMAT.setMaximumFractionDigits(0);
						String TotalIncomeShow = AMOUNTFORMAT.format(TripIncomeTotal);
						model.put("AdvanceTotal", TotalIncomeShow);

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
			return new ModelAndView("Driver_Advance_Report", model);
		}


}
