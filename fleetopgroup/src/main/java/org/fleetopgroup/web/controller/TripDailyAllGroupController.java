/**
 * 
 */
package org.fleetopgroup.web.controller;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.TripDailySheetStatus;
import org.fleetopgroup.persistence.bl.CashBookBL;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.FuelBL;
import org.fleetopgroup.persistence.bl.TripDailyBL;
import org.fleetopgroup.persistence.bl.TripRouteBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripDailyAllGroupDayDto;
import org.fleetopgroup.persistence.dto.TripDailyGroupCollectionDto;
import org.fleetopgroup.persistence.dto.TripDailyRouteSheetDto;
import org.fleetopgroup.persistence.model.TripDailyAllGroupDay;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetService;
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

/**
 * @author fleetop
 *
 */
@Controller
public class TripDailyAllGroupController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ITripDailySheetService TripDailySheetService;

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
	SimpleDateFormat dateFormat_SQL = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	/* show Only manage Tripdaily Collection */
	@RequestMapping(value = "/TDAllGroup/{pageNumber}", method = RequestMethod.GET)
	public String TripDailyAllGroup(@PathVariable Integer pageNumber, Model model) throws Exception {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Page<TripDailyAllGroupDay> page = TripDailySheetService.getDeployment_Page_TripDailyAllGroupDay(TripDailySheetStatus.TRIP_STATUS_OPEN,
					pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("TripManage", page.getTotalElements());
			List<TripDailyAllGroupDayDto> pageList = (TripDailySheetService
					.list_TripDailyAllGroupDay_Page_StatusAllGroup(TripDailySheetStatus.TRIP_STATUS_OPEN, pageNumber, userDetails.getCompany_id()));

			model.addAttribute("TDAllDay", TDBL.prepare_TripDailyAllGroupDayDto(pageList));

		} catch (NullPointerException e) {
			e.printStackTrace();
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("TripCol Page:", e);
			e.printStackTrace();
		}
		return "new_Manage_TripDailyAllGroup";
	}

	@RequestMapping(value = "/closeTDAllGroup/{pageNumber}", method = RequestMethod.GET)
	public String CloseTripDailyAllGroup(@PathVariable Integer pageNumber, Model model) throws Exception {

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Page<TripDailyAllGroupDay> page = TripDailySheetService.getDeployment_Page_TripDailyAllGroupDay(TripDailySheetStatus.TRIP_STATUS_CLOSED,
					pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("TripManage", page.getTotalElements());
			List<TripDailyAllGroupDayDto> pageList = (TripDailySheetService
					.list_TripDailyAllGroupDay_Page_StatusAllGroup(TripDailySheetStatus.TRIP_STATUS_CLOSED, pageNumber, userDetails.getCompany_id()));

			model.addAttribute("TDAllDay", TDBL.prepare_TripDailyAllGroupDayDto(pageList));

		} catch (NullPointerException e) {
			e.printStackTrace();
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("TripCol Page:", e);
			e.printStackTrace();
		}
		return "new_Closed_TripDailyAllGroup";
	}

	@RequestMapping(value = "/closeAllGroupTDCollection", method = RequestMethod.POST)
	public ModelAndView closeAllGroupTDCollection(@RequestParam("closedate") final String TripCollectionDate,
			RedirectAttributes redir, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (TripCollectionDate != null) {
				CustomUserDetails			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				List<TripDailyRouteSheetDto> DailyRoute = TripDailySheetService
						.Validate_TripDailyRoute_All_Group_CLOSED_OR_NOT(TripCollectionDate, TripDailySheetStatus.TRIP_STATUS_OPEN, userDetails.getCompany_id());

				if (DailyRoute != null && !DailyRoute.isEmpty()) {
					String success = "";
					for (TripDailyRouteSheetDto tripCollectionSheet : DailyRoute) {

						success += "Collection " + tripCollectionSheet.getVEHICLE_GROUP() + "--"
								+ tripCollectionSheet.getTRIP_ROUTE_NAME() + "";
					}

					redir.addFlashAttribute("closeGroup", success);
					return new ModelAndView("redirect:/TDAllGroup/1.in?closeTripAll=true", model);
				} else {

					java.sql.Date fromDate = null;
					try {
						java.util.Date date = dateFormat_SQL.parse(TripCollectionDate);
						fromDate = new Date(date.getTime());
					} catch (Exception e1) {

						e1.printStackTrace();
					}

					model.put("TRIP_OPEN_DATE", TripCollectionDate);
					model.put("SEARCHDATE", TripCollectionDate);

					TripDailyAllGroupDayDto AllGroupSheet = TripDailySheetService
							.GET_DETAILS_TO_TRIPSHEET_ALLGROUPDAY_COLLECTION_DETILS(fromDate, userDetails.getCompany_id());
					if (AllGroupSheet != null) {

						// System.out.println(AllGroupSheet.getTRIP_CLOSE_STATUS());

						switch (AllGroupSheet.getTRIP_STATUS_ID()) {
						case TripDailySheetStatus.TRIP_STATUS_OPEN:
							// here show to Day collection Report details

							// Get if All Group is Open Get One Group Wise Total
							// Value Collection
							List<TripDailyGroupCollectionDto> DailyGroupCollection = TripDailySheetService
									.List_TripDailyGroupCollection_Get_all_details(fromDate, TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());

							// get Trip sheet id to get all Expense Details
							model.put("GROUPCOL", TDBL.prepare_LIST_TripDailyGroupCollectionDto(DailyGroupCollection));

							// get Trip sheet id to get all Expense Details
							model.put("TDALLGROUP", TDBL.prepare_TripDailyAllGroupDay_Dto(AllGroupSheet));

							break;

						default:
							// here already Closed Collection Report Details

							return new ModelAndView(
									"redirect:/showAllDayGTD?GID=" + AllGroupSheet.getTRIPALLGROUPID() + "");
						}

					}
				}
			}

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("close_AllDayGroup_TripDaily", model);
	}

	@RequestMapping(value = "/savecloseAllGroupTDCollection", method = RequestMethod.POST)
	public ModelAndView SavecloseAllGroupTDCollection(
			@ModelAttribute("command") TripDailyAllGroupDayDto dailyAllGroupDayDto, RedirectAttributes redir,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (dailyAllGroupDayDto != null) {
				CustomUserDetails			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				List<TripDailyRouteSheetDto> DailyRoute = TripDailySheetService
						.Validate_TripDailyRoute_All_Group_CLOSED_OR_NOT(dailyAllGroupDayDto.getTRIP_OPEN_DATE(),
								TripDailySheetStatus.TRIP_STATUS_OPEN, userDetails.getCompany_id());

				if (DailyRoute != null && !DailyRoute.isEmpty()) {
					String success = "";
					for (TripDailyRouteSheetDto tripCollectionSheet : DailyRoute) {

						success += "Collection " + tripCollectionSheet.getVEHICLE_GROUP() + "--"
								+ tripCollectionSheet.getTRIP_ROUTE_NAME() + "";
					}

					redir.addFlashAttribute("closeGroup", success);
					return new ModelAndView("redirect:/TDAllGroup/1.in?closeTripAll=true", model);
				} else {

					// prepare Total Of the Day Balance and

					TripDailyAllGroupDay dailyAllGroup = TDBL
							.prepare_Update_TD_GroupAll_Collection_Total(dailyAllGroupDayDto);

					TripDailySheetService.Update_AllGroup_Day_Collection_Closed_Details(dailyAllGroup);

					model.put("ClosedAll", true);
				}
			}

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/showAllDayGTD?GID=" + dailyAllGroupDayDto.getTRIPALLGROUPID() + "", model);
	}

	@RequestMapping(value = "/showAllDayGTD", method = RequestMethod.GET)
	public ModelAndView showAllDayGTD(@RequestParam("GID") final Long TRIPALLGROUPID, final HttpServletRequest request,
			final HttpServletResponse Response) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TripDailyAllGroupDayDto AllGroupSheet = TripDailySheetService
					.GET_DETAILS_TO_TRIPSHEET_ALLGROUPDAY_ID(TRIPALLGROUPID, userDetails.getCompany_id());

			if (AllGroupSheet != null) {

				// Get if All Group is Open Get One Group Wise Total
				// Value Collection
				List<TripDailyGroupCollectionDto> DailyGroupCollection = TripDailySheetService
						.List_TripDailyGroupCollection_Get_all_details_Date(AllGroupSheet.getTRIP_OPEN_DATE_ON(),
								TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());
				// get Trip sheet id to get all Expense Details
				model.put("GROUPCOL", TDBL.prepare_LIST_TripDailyGroupCollectionDto(DailyGroupCollection));
				// get Trip sheet id to get all Expense Details
				model.put("TDALLGROUP", TDBL.prepare_TripDailyAllGroupDay_Dto(AllGroupSheet));

				DecimalFormat df = new DecimalFormat("##,##,##0");

				df.setMaximumFractionDigits(0);
				Double COLLECTION_BALANCE = AllGroupSheet.getCOLLECTION_BALANCE();
				if (AllGroupSheet.getCOLLECTION_BALANCE() == null) {
					COLLECTION_BALANCE = 0.0;
				}
				model.put("COLLECTION_BALANCE", df.format(COLLECTION_BALANCE));

				Double EXPENSE_DAY = AllGroupSheet.getEXPENSE_DAY();
				if (AllGroupSheet.getEXPENSE_DAY() == null) {
					EXPENSE_DAY = 0.0;
				}
				model.put("EXPENSE_DAY", df.format(EXPENSE_DAY));

				Double TOTAL_BALANCE = AllGroupSheet.getTOTAL_BALANCE();
				if (AllGroupSheet.getTOTAL_BALANCE() == null) {
					TOTAL_BALANCE = 0.0;
				}
				model.put("TOTAL_BALANCE", df.format(TOTAL_BALANCE));
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("show_AllDayGroup", model);
	}
}
