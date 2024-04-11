package org.fleetopgroup.web.controller;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.persistence.bl.ReportBL;
import org.fleetopgroup.persistence.bl.TripCollectionBL;
import org.fleetopgroup.persistence.bl.TripDailyBL;
import org.fleetopgroup.persistence.bl.TripSheetBL;
import org.fleetopgroup.persistence.bl.VehicleGroupBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.TripCollectionSheetDto;
import org.fleetopgroup.persistence.dto.TripDailySheetDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.model.Branch;
import org.fleetopgroup.persistence.serviceImpl.IBranchService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ITripCollectionService;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetService;
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

@Controller
public class TripSheetReportController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IUserProfileService userProfileService;
	
	@Autowired
	private ITripSheetService TripSheetService;
	
	@Autowired	IVehicleGroupService	vehicleGroupService;
	
	@Autowired
	private ITripCollectionService TripCollectionService;
	
	@Autowired ICompanyConfigurationService 	companyConfigurationService;

	@Autowired
	private ITripDailySheetService tripDailySheetService;
	
	@Autowired
	private IBranchService	branchService;
	
	TripDailyBL TDBL = new TripDailyBL();

	
	TripSheetBL TSBL = new TripSheetBL();
	TripCollectionBL TCBL = new TripCollectionBL();
	VehicleGroupBL vg = new VehicleGroupBL();
	ReportBL RBL = new ReportBL();
	TripSheetBL TripBL = new TripSheetBL();

	DecimalFormat AMOUNTFORMAT = new DecimalFormat("##,##,##0");
	SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatonlyDate = new SimpleDateFormat("dd-MMM-yyyy");
	

	@RequestMapping("/TSR")
	public ModelAndView TripSheetReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> 		model = new HashMap<String, Object>();
		CustomUserDetails 			userDetails = null;
		HashMap<String, Object> 	configuration	= null;
		try {
		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
		model.put("configuration", configuration);
		}
		catch(Exception e){
			
		}
		return new ModelAndView("TSR", model);
	}

	@RequestMapping("/RouteWiseTripSheetReport")
	public ModelAndView routeWiseTripSheetReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("RouteWiseTripSheetReport", model);
	}

	
	
	
	/* SRS Travels User Wise Trip sheet Advances Report By Dev Yogi Start */
	@RequestMapping("/TripSheetAdvanceReport")
	public ModelAndView TripSheetAdvanceReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Tripsheet_Advance_Report", model);
	}	
	/* SRS Travels User Wise Trip sheet Advances Report By Dev Yogi End*/
	
	@RequestMapping(value = "/TripSheetAdvanceReport", method = RequestMethod.POST)
	public ModelAndView TripSheetAdvanceReport(
			@ModelAttribute("command") @RequestParam("TRIP_USER") final Long TRIP_USER,
			@RequestParam("TRIPSHEET_GROUP") final long TRIPSHEET_GROUP,
			@RequestParam("TRIP_DATE_RANGE") String TRIP_DATE_RANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {

			if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null && TRIP_USER != null) {
				
				TRIP_DATE_RANGE	= DateTimeUtility.getSqlStrDateFromStrDate(TRIP_DATE_RANGE, DateTimeUtility.YYYY_MM_DD);
				
				model.put("SearchDate", TRIP_DATE_RANGE);
				
				
				try {
					String advancePaidby = "", advancePlace = "", TripsheetGroup = "", TripOpenDate_Close = "";

					if (TRIP_USER != null) {
						UserProfileDto Orderingname = userProfileService
								.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(TRIP_USER);
						model.put("paidBy", Orderingname.getFirstName() + "_" + Orderingname.getLastName());
						model.put("place", Orderingname.getBranch_name());

						advancePaidby = "(TA.advancePaidbyId="+ TRIP_USER+ ")  ";

						advancePlace = " AND (TA.advancePlaceId=" + Orderingname.getBranch_id()
								+ ") ";
					}

					if (TRIPSHEET_GROUP != 0 ) {

						TripsheetGroup = "  AND ( V.vehicleGroupId=" + TRIPSHEET_GROUP
								+ " ) ";
					}

					if (TRIP_DATE_RANGE != "") {

						TripOpenDate_Close = "  AND (( TA .created between '" + TRIP_DATE_RANGE + " 01:00:00' AND '"
								+ TRIP_DATE_RANGE + " 23:59:59')) ";
					}

					String query = " " + advancePaidby + " " + advancePlace + " " + TripsheetGroup + " "
							+ TripOpenDate_Close + " ";

					List<TripSheetDto> AdvanceCollection = TripSheetService.list_Report_TripSheet_TripAdvance(query, userDetails);
					
					if((AdvanceCollection == null)||(AdvanceCollection.isEmpty())) 
					{
						model.put("NotFound", true);						
					}
					
					model.put("TripSheet", TSBL.prepare_TripsheetAdvance_Date_to_TripSheetDetails(AdvanceCollection));

					Double TripIncomeTotal = TSBL.getadvanceTripTotal(AdvanceCollection);
					if(AdvanceCollection == null || AdvanceCollection.isEmpty()) {
						model.put("norecordfound", true);
					}
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					String TotalIncomeShow = AMOUNTFORMAT.format(TripIncomeTotal);
					model.put("AdvanceTotal", TotalIncomeShow);

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
		return new ModelAndView("Tripsheet_Advance_Report", model);
	}


	// Note : Branch Wise advacne Report Values and the tripsheet entries
	
	
	/*SRS Travels Branch Wise Trip sheet Advances Report By Dev Yogi Start */
	@RequestMapping("/BranchwiseTSAdvanceReport")
	public ModelAndView BranchwiseTSAdvanceReport() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		CustomUserDetails userDetails=(CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object> configuration=companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
		model.put("configuration", configuration);

		return new ModelAndView("Branch_Wise_TripSheet_Advance_Report", model);
	}
	
	/*SRS Travels Branch Wise Trip sheet Advances Report By Dev Yogi End*/
	
	
	@RequestMapping(value = "/BranchwiseTSAdvanceReport", method = RequestMethod.POST)
	public ModelAndView BranchwiseTSAdvanceReport(
			@ModelAttribute("command") @RequestParam("TRIP_BRANCH") final Integer TRIP_BRANCH,
			@RequestParam("TRIPSHEET_GROUP") final long TRIPSHEET_GROUP,
			@RequestParam("TRIP_DATE_RANGE") String TRIP_DATE_RANGE, final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String,Object> configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
		try {
			

			if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null || TRIP_BRANCH != null) {
				
				TRIP_DATE_RANGE	= DateTimeUtility.getSqlStrDateFromStrDate(TRIP_DATE_RANGE, DateTimeUtility.YYYY_MM_DD);
				model.put("SearchDate", TRIP_DATE_RANGE);
				model.put("configuration", configuration);
				try {
					String advancePlace = "", TripOpenDate_Close = "", TripsheetGroup = "";

					if (TRIP_BRANCH != null && TRIP_BRANCH > 0) {

						model.put("place", branchService.getBranchByID(TRIP_BRANCH,userDetails.getCompany_id()).getBranch_name());
						advancePlace = " (TA.advancePlaceId=" + TRIP_BRANCH
								+ ") ";
					}

					if (TRIPSHEET_GROUP != 0) {

						TripsheetGroup = "  AND ( V.vehicleGroupId=" + TRIPSHEET_GROUP
								+ " ) ";
					}

					if (TRIP_DATE_RANGE != "") {

						TripOpenDate_Close = "  AND (( TA .created between '" + TRIP_DATE_RANGE + " 01:00:00' AND '"
								+ TRIP_DATE_RANGE + " 23:59:59')) ";
					}

					String query = " " + advancePlace + " " + TripsheetGroup + " " + TripOpenDate_Close + " ";

					List<TripSheetDto> AdvanceCollection = TripSheetService.list_Report_TripSheet_TripAdvance(query, userDetails);
					
					if((AdvanceCollection == null)||(AdvanceCollection.isEmpty())) 
					{
						model.put("NotFound", true); 						
					}
					model.put("TripSheet", TSBL.prepare_TripsheetAdvance_Date_to_TripSheetDetails(AdvanceCollection));

					Double TripIncomeTotal = TSBL.getadvanceTripTotal(AdvanceCollection);
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
		return new ModelAndView("Branch_Wise_TripSheet_Advance_Report", model);
	}
	
	/*SRS Travels Day wise Trip sheet Advances Report By Dev Yogi Start */
	@RequestMapping("/TSAllAdvanceReport")
	public ModelAndView TSAllAdvanceReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Day_Wise_TripSheet_Advances_Report", model);
	}
	/*SRS Travels Day wise Trip sheet Advances Report By Dev Yogi End */
	
	

	@RequestMapping(value = "/TSAllAdvanceReport", method = RequestMethod.POST)
	public ModelAndView TripSheetAllAdvanceReport(@RequestParam("TRIPSHEET_GROUP") final long TRIPSHEET_GROUP,
			@RequestParam("TRIP_DATE_RANGE")  String TRIP_DATE_RANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {

			if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null) {
				
				TRIP_DATE_RANGE	= DateTimeUtility.getSqlStrDateFromStrDate(TRIP_DATE_RANGE, DateTimeUtility.YYYY_MM_DD);
				model.put("SearchDate", TRIP_DATE_RANGE);
				try {
					String advancePaidby = "", advancePlace = "", TripOpenDate_Close = "", TripsheetGroup = "";

					if (TRIP_DATE_RANGE != "") {

						TripOpenDate_Close = " (( TA .created between '" + TRIP_DATE_RANGE + " 01:00:00' AND '"
								+ TRIP_DATE_RANGE + " 23:59:59')) ";
					}

					if (TRIPSHEET_GROUP != 0) {

						TripsheetGroup = "  AND ( V.vehicleGroupId=" + TRIPSHEET_GROUP
								+ ") ";
					}

					String query = " " + advancePaidby + " " + advancePlace + " " + TripOpenDate_Close + " "
							+ TripsheetGroup + " ";

					List<TripSheetDto> AdvanceCollection = TripSheetService.list_Report_TripSheet_TripAdvance(query, userDetails);
					
					if((AdvanceCollection == null)||(AdvanceCollection.isEmpty())) 
					{
						model.put("NotFound", true); 
					}
					
					model.put("TripSheet", TSBL.prepare_TripsheetAdvance_Date_to_TripSheetDetails(AdvanceCollection));

					Double TripIncomeTotal = TSBL.getadvanceTripTotal(AdvanceCollection);
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
		return new ModelAndView("Day_Wise_TripSheet_Advances_Report", model);
	}
	
	/*SRS Travels Trip Sheet Date Report By Dev Yogi Start*/
	@RequestMapping("/TripSheetDateReport")
	public ModelAndView TripSheetDateReport() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
		model.put("configuration", configuration);
		
		return new ModelAndView("Tripsheet_Date_Report", model);
	}
	/*SRS Travels Trip Sheet Date Report By Dev Yogi End*/
	
	@RequestMapping(value = "/TripSheetDateReport", method = RequestMethod.POST)
	public ModelAndView TripSheetDateReport(
			@ModelAttribute("command") @RequestParam("TRIP_DATE_RANGE")  String TRIP_DATE_RANGE,
			@RequestParam("TRIPSHEET_GROUP") final long TRIPSHEET_GROUP, 
			@RequestParam("loadTypeId") final short loadTypeId,final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {

			if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null) {
				TRIP_DATE_RANGE	= DateTimeUtility.getSqlStrDateFromStrDate(TRIP_DATE_RANGE, DateTimeUtility.YYYY_MM_DD);
				try {

					if (TRIP_DATE_RANGE != "") {
						String TripsheetGroup = "";
						java.util.Date date = dateFormatSQL.parse(TRIP_DATE_RANGE);

						if (TRIPSHEET_GROUP != 0) {

							TripsheetGroup = "  AND ( V.vehicleGroupId=" + TRIPSHEET_GROUP + ") ";
						}
						if(loadTypeId > 0) {
							TripsheetGroup += " AND T.loadTypeId = "+loadTypeId+"";
						}
						
						HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
						model.put("configuration", configuration);
						
						
						List<TripSheetDto> AdvanceCollection = TripSheetService.list_Report_TripSheet_DATE(TRIP_DATE_RANGE,
								TripsheetGroup, userDetails.getCompany_id());

						model.put("TRIP_DATE", dateFormatonlyDate.format(date));

						Date yesterday = new Date(date.getTime() - 1 * 24 * 3600 * 1000);
						model.put("YESTERDAY", dateFormatSQL.format(yesterday));
						Date tomorrow = new Date(date.getTime() + 1 * 24 * 3600 * 1000);
						model.put("TOMORROW", dateFormatSQL.format(tomorrow));

						if((AdvanceCollection == null) ||(AdvanceCollection.isEmpty())) 
						{
							model.put("NotFound", true); 							
						}
						
						
						model.put("TripSheet", TSBL.prepare_Tripsheet_DATE_to_TripSheetDetails(AdvanceCollection));

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
		return new ModelAndView("Tripsheet_Date_Report", model);
	}

	
	/*SRS Travels Daily Trip Sheet  Advances Report By Dev Yogi Start*/
	@RequestMapping("/DailyTSAllAdvanceReport")
	public ModelAndView DailyTSAllAdvanceReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Tripsheet_Daily_Advance_Report", model);
	}	
	/*SRS Travels Daily Trip Sheet  Advances Report By Dev Yogi End*/ 
	@RequestMapping(value = "/DailyTSAllAdvanceReport", method = RequestMethod.POST)
	public ModelAndView DailyTSAllAdvanceReport(
			@ModelAttribute("command") @RequestParam("TRIP_GROUP") final long TRIP_GROUP,
			@RequestParam("TRIP_DATE_RANGE")  String TRIP_DATE_RANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {

			if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null) {
				model.put("SearchDate", TRIP_DATE_RANGE);
				
				TRIP_DATE_RANGE	= DateTimeUtility.getSqlStrDateFromStrDate(TRIP_DATE_RANGE, DateTimeUtility.YYYY_MM_DD);
				try {

					String advancePaidby = "", advancePlace = "", TripsheetGroup = "", TripOpenDate_Close = "";

					if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null) {

						TripOpenDate_Close = " ( T .tripOpenDate between '" + TRIP_DATE_RANGE + "' AND '"
								+ TRIP_DATE_RANGE + "') ";
					}

					if (TRIP_GROUP != 0) {

						TripsheetGroup = "  AND V.vehicleGroupId=" + TRIP_GROUP + " ";
					}

					String query = " " + TripOpenDate_Close + " " + TripsheetGroup + " " + advancePaidby + " "
							+ advancePlace + " ";

					List<TripSheetDto> AdvanceCollection = TripSheetService.list_Report_Daily_TripSheet_Report(query, userDetails.getCompany_id());
					
					if((AdvanceCollection == null)||(AdvanceCollection.isEmpty())) 
					{
						model.put("NotFound", true);						
					}					
					
					model.put("TripSheet",
							TSBL.prepare_DailyTripsheetAdvance_Date_to_TripSheetDetails(AdvanceCollection));

					Double TripIncomeTotal = TSBL.getadvanceTripTotal(AdvanceCollection);
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					String TotalIncomeShow = AMOUNTFORMAT.format(TripIncomeTotal);
					model.put("AdvanceTotal", TotalIncomeShow);

				} catch (Exception e) {
					e.printStackTrace();
				}
				java.util.Date date = dateFormatSQL.parse(TRIP_DATE_RANGE);

				model.put("TRIP_DATE", dateFormatonlyDate.format(date));

				Date yesterday = new Date(date.getTime() - 1 * 24 * 3600 * 1000);
				model.put("YESTERDAY", dateFormatSQL.format(yesterday));
				Date tomorrow = new Date(date.getTime() + 1 * 24 * 3600 * 1000);
				model.put("TOMORROW", dateFormatSQL.format(tomorrow));

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Tripsheet_Daily_Advance_Report", model);
	}
	
	/*SRS Travels Closed wise TripSheet Advances Report By Dev Yogi Start*/
	@RequestMapping("/DailyClosedTSAllAdvanceReport")
	public ModelAndView DailyClosedTSAllAdvanceReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Tripsheet_Daily_Closed_Report", model);
	}	
	/*SRS Travels Closed wise TripSheet Advances Report By Dev Yogi End */

	@RequestMapping(value = "/DailyClosedTSAllAdvanceReport", method = RequestMethod.POST)
	public ModelAndView DailyClosedTSAllAdvanceReport(
			@ModelAttribute("command") @RequestParam("TRIP_GROUP") final long TRIP_GROUP,
			@RequestParam("TRIP_DATE_RANGE") String TRIP_DATE_RANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		/*HashMap<String, Object> 	configuration	= null;*/
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			
			/*configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			model.put("configuration", configuration);*/
			if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null) {

				model.put("SearchDate", TRIP_DATE_RANGE);
				
				TRIP_DATE_RANGE	= DateTimeUtility.getSqlStrDateFromStrDate(TRIP_DATE_RANGE, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				try {

					String advancePaidby = "", advancePlace = "", TripsheetGroup = "", TripOpenDate_Close = "";

					if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null) {

						TripOpenDate_Close = " ( T .closetripDate between '" + TRIP_DATE_RANGE + "' AND '"
								+ TRIP_DATE_RANGE + "') AND T.tripStutesId ="+TripSheetStatus.TRIP_STATUS_CLOSED+" ";
					}

					if (TRIP_GROUP != 0) {

						TripsheetGroup = "  AND V.vehicleGroupId=" + TRIP_GROUP + " ";
					}

					String query = " " + TripOpenDate_Close + " " + TripsheetGroup + " " + advancePaidby + " "
							+ advancePlace + " ";

					List<TripSheetDto> AdvanceCollection = TripSheetService.list_Report_Daily_TripSheet_Report(query, userDetails.getCompany_id());
					model.put("TripSheet",
							TSBL.prepare_DailyTripsheetAdvance_Date_to_TripSheetDetails(AdvanceCollection));

					Double TripIncomeTotal = TSBL.getadvanceTripTotal(AdvanceCollection);
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					String TotalIncomeShow = AMOUNTFORMAT.format(TripIncomeTotal);
					model.put("AdvanceTotal", TotalIncomeShow);

				} catch (Exception e) {
					e.printStackTrace();
				}
				java.util.Date date = dateFormatSQL.parse(TRIP_DATE_RANGE);

				model.put("TRIP_DATE", dateFormatonlyDate.format(date));

				Date yesterday = new Date(date.getTime() - 1 * 24 * 3600 * 1000);
				model.put("YESTERDAY", dateFormatSQL.format(yesterday));
				Date tomorrow = new Date(date.getTime() + 1 * 24 * 3600 * 1000);
				model.put("TOMORROW", dateFormatSQL.format(tomorrow));

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Tripsheet_Daily_Closed_Report", model);
	}

	/*SRS Travels Trip Sheet Collection Report By Dev Yogi Start*/
	@RequestMapping("/TripSheetCollectionDateReport")
	public ModelAndView TripSheetCollectionDateReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Tripsheet_Collection_Date_Report", model);
	}	
	/*SRS Travels Trip Sheet Collection Report By Dev Yogi End*/
	@RequestMapping(value = "/TripSheetCollectionDateReport", method = RequestMethod.POST)
	public ModelAndView TripSheetCollectionDateReport(
			@ModelAttribute("command") @RequestParam("TRIP_DATE_RANGE") final String TRIP_DATE_RANGE, TripSheetDto trip,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {

			if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null) {

				try {

					String TripsheetGroup = "", closedby = "", CloesdLocation = "";
					if (TRIP_DATE_RANGE != "") {

						String dateRangeFrom = "", dateRangeTo = "";

						String[] From_TO_DateRange = null;
						try {

							From_TO_DateRange = TRIP_DATE_RANGE.split(" to ");

							dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
							dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

							

						} catch (Exception e) {
							LOGGER.error("Renewal Reminder:", e);
						}

						java.util.Date date = dateFormatSQL.parse(TRIP_DATE_RANGE);

						if (trip.getVehicleGroupId() != 0) {
									
							model.put("TRIP_GROUP", " GROUP = " + vehicleGroupService.getVehicleGroupByID(trip.getVehicleGroupId()).getvGroup());
							TripsheetGroup = "  AND V.vehicleGroupId=" + trip.getVehicleGroupId() + "  ";
						}

						if (trip.getClosedBy() != "" && trip.getClosedBy() != null) {
							UserProfileDto	profile = userProfileService.getUserProfileByUser_id(Long.parseLong(trip.getClosedBy()+""));
							String closedBy	= "";
							if(profile != null) {
								closedBy	= profile.getUser_email();
							}
							model.put("TRIP_CLOSEDBY", " CLOSED BY = " + closedBy);
							closedby = "  AND T.closedById=" + trip.getClosedBy() + "  ";
						}

						if (trip.getCloesdLocation() != "" && trip.getCloesdLocation() != null) {
							String location = "";
						Branch branch =	branchService.getBranchByID(Integer.parseInt(trip.getCloesdLocation()),userDetails.getCompany_id());
						if(branch != null) {
							location	= branch.getBranch_name();
						}
							model.put("TRIP_LOCATION", " LOCATION = " + location);
							CloesdLocation = "  AND  T.closedLocationId=" + trip.getCloesdLocation() + "  ";
						}

						String Query = " " + TripsheetGroup + " " + closedby + " " + CloesdLocation + " ";

						List<TripSheetDto> AdvanceCollection = TripSheetService
								.list_Report_TripSheet_Collection_DATE_Report(dateRangeFrom, dateRangeTo, Query, userDetails.getCompany_id());

						model.put("TRIP_DATE", TRIP_DATE_RANGE);

						Date yesterday = new Date(date.getTime() - 1 * 24 * 3600 * 1000);
						model.put("YESTERDAY", dateFormatSQL.format(yesterday));
						Date tomorrow = new Date(date.getTime() + 1 * 24 * 3600 * 1000);
						model.put("TOMORROW", dateFormatSQL.format(tomorrow));

						if((AdvanceCollection == null)||(AdvanceCollection.isEmpty())) 
						{
							model.put("NotFound", true); 							
						}
						
						model.put("TripSheet",
								TSBL.prepare_Tripsheet_collection_DATE_to_TripSheetDtoDetails(AdvanceCollection));

						Double[] totalGroupSheet = TSBL.Tota_Tripsheet_collection_DATE(AdvanceCollection);
						if (totalGroupSheet != null) {
							String TotalTollCharge = AMOUNTFORMAT.format(totalGroupSheet[0]);
							model.put("TotalTollCharge", TotalTollCharge);

							String TotalDriverOne = AMOUNTFORMAT.format(totalGroupSheet[1]);
							model.put("TotalDriverOne", TotalDriverOne);

							String TotalDrvierTwo = AMOUNTFORMAT.format(totalGroupSheet[2]);
							model.put("TotalDrvierTwo", TotalDrvierTwo);

							String TotalCleaner = AMOUNTFORMAT.format(totalGroupSheet[3]);
							model.put("TotalCleaner", TotalCleaner);

							String TotalWelfare = AMOUNTFORMAT.format(totalGroupSheet[4]);
							model.put("TotalWelfare", TotalWelfare);

							String TotalLuggage = AMOUNTFORMAT.format(totalGroupSheet[5]);
							model.put("TotalLuggage", TotalLuggage);

							String TotalWash = AMOUNTFORMAT.format(totalGroupSheet[6]);
							model.put("TotalWash", TotalWash);

							String TotalHalting = AMOUNTFORMAT.format(totalGroupSheet[7]);
							model.put("TotalHalting", TotalHalting);

							String TotalOtherExpense = AMOUNTFORMAT.format(totalGroupSheet[8]);
							model.put("TotalOtherExpense", TotalOtherExpense);

							String TotalExpense = AMOUNTFORMAT.format(totalGroupSheet[9]);
							model.put("TotalExpense", TotalExpense);

							String TotalAdvance = AMOUNTFORMAT.format(totalGroupSheet[10]);
							model.put("TotalAdvance", TotalAdvance);

							String Totalnetpay = AMOUNTFORMAT.format(totalGroupSheet[11]);
							model.put("Totalnetpay", Totalnetpay);

						}
					}
				} 
				
				catch (Exception e) {
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
		return new ModelAndView("Tripsheet_Collection_Date_Report", model);
	}
	/* SRS Travels Trip Sheet Status Report By Dev Yogi Start */
	@RequestMapping("/TSSTATUSReport")
	public ModelAndView TSSTATUSReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Tripsheet_NotClosed_Report", model);
	}
	/* SRS Travels Trip Sheet Status Report By Dev Yogi End */

	@RequestMapping(value = "/TSSTATUSReport", method = RequestMethod.POST)
	public ModelAndView TSSTATUSReport(
			@ModelAttribute("command") @RequestParam("TRIP_DATE_RANGE") final String TRIP_DATE_RANGE, TripSheetDto trip,
			final HttpServletRequest request) {

		Map<String, Object> 		model 			= new HashMap<String, Object>();
		HashMap<String, Object> 	configuration	= null;
		try {
			CustomUserDetails		userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration			=  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null) {

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

					String TripOpenDate_Close = "", TripsheetGroup = "", closedby = "", CloesdLocation = "",
							tripStatus = "";
					if (TRIP_DATE_RANGE != "") {

						if (trip.getVehicleGroupId() != 0) {

							model.put("TRIP_GROUP", " GROUP = " + vehicleGroupService.getVehicleGroupByID(trip.getVehicleGroupId()).getvGroup());
							TripsheetGroup = "  AND  V.vehicleGroupId=" + trip.getVehicleGroupId() + "  ";
						}

						if (trip.getDispatchedBy() != "" && trip.getDispatchedBy() != null) {
							UserProfileDto	profile = userProfileService.getUserProfileByUser_id(Long.parseLong(trip.getDispatchedBy()+""));
							String closedBy	= "";
							if(profile != null) {
								closedBy	= profile.getUser_email();
							}
							model.put("TRIP_DISPATCHBY", " DISPATCHED BY = " + closedBy);
							closedby = "  AND T.dispatchedById=" + trip.getDispatchedBy() + " ";
						}

						if (trip.getDispatchedLocation() != "" && trip.getDispatchedLocation() != null) {
							Branch branch =	branchService.getBranchByID(Integer.parseInt(trip.getDispatchedLocation()+""),userDetails.getCompany_id());
							model.put("TRIP_LOCATION", " LOCATION = " + branch.getBranch_name());
							CloesdLocation = "  AND T.dispatchedLocationId=" + trip.getDispatchedLocation() + "  ";
						}

						if (trip.getTripStutes() != "" && trip.getTripStutes() != null) {

							model.put("TRIP_STATUS", " Status = " + TripSheetStatus.getTripSheetStatusName(Short.parseShort(trip.getTripStutes()+"")));
							tripStatus = "  AND T.tripStutesId=" + trip.getTripStutes() + "  ";
						}

						if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null) {
							if (trip.getTripStutes() != "" && trip.getTripStutes() != null && Short.parseShort(trip.getTripStutes()+"") == TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED) {
								TripOpenDate_Close = " AND T.closeACCtripDate  BETWEEN '" + dateRangeFrom + "' AND  '"
										+ dateRangeTo + "' ";
							}else {
								
								TripOpenDate_Close = " AND T.tripOpenDate  BETWEEN '" + dateRangeFrom + "' AND  '"
										+ dateRangeTo + "' ";
							}
						}
						
						String Query = TripOpenDate_Close + " " + TripsheetGroup + " " + closedby + " " + CloesdLocation
								+ " " + tripStatus + " ";

						List<TripSheetDto> AdvanceCollection = TripSheetService.listTripSheet(Query);

						if((AdvanceCollection == null)||(AdvanceCollection.isEmpty())) 
						{
							model.put("NotFound", true); 						
						}											
						
						model.put("TripSheet", TSBL.prepare_Tripsheet__Dto(AdvanceCollection));



					//	model.put("TripSheet", tripBL.prepare_Tripsheet__Dto(AdvanceCollection));

						//model.put("TripSheet", tripBL.prepare_Tripsheet__Dto(AdvanceCollection));

						Double TripIncomeTotal = TCBL.prepare_Total_Adavance_Amount(AdvanceCollection);
						AMOUNTFORMAT.setMaximumFractionDigits(2);
						String TotalIncomeShow = AMOUNTFORMAT.format(TripIncomeTotal);
						model.put("AdvanceTotal", TotalIncomeShow);

						




					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} 
			else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}
			model.put("configuration", configuration);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Tripsheet_NotClosed_Report", model);
	}

	/* SRS Travels Trip Sheet Route Wise Difference KM &amp; Volume Report By Dev Yogi Start  */
	@RequestMapping("/TSDiffKMVOLUMEReport")
	public ModelAndView TSDiffKMVOLUMEReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("TripSheet_Route_Wise_Difference_Report", model);
	}	
	/* SRS Travels Trip Sheet Route Wise Difference KM &amp; Volume Report By Dev Yogi End */
	
@RequestMapping(value = "/TSDiffKMVOLUMEReport", method = RequestMethod.POST)
public ModelAndView TSDiffKMVOLUMEReport(
		@ModelAttribute("command") @RequestParam("TRIP_ROUTE") final Integer TRIP_ROUTE,
		@RequestParam("VEHICLE_TC_DATERAGE") final String VEHICLE_TC_DATERAGE, final HttpServletRequest request) {

	Map<String, Object> model = new HashMap<String, Object>();
	CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	try {

		if (VEHICLE_TC_DATERAGE != "") {

			String dateRangeFrom = "", dateRangeTo = "";

			String[] From_TO_DateRange = null;
			try {

				From_TO_DateRange = VEHICLE_TC_DATERAGE.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}

			try {

				String TripRouteQury = "";
				if (TRIP_ROUTE != null && TRIP_ROUTE > 0) {

					TripRouteQury = "  AND T.routeID= " + TRIP_ROUTE + " ";
				}

				String Query = " " + TripRouteQury + " ";

				
				List<TripSheetDto> AdvanceCollection = TripSheetService
						.list_Report_TripSheet_Difference_Km_Liter_List(Query, dateRangeFrom, dateRangeTo, userDetails);
				
				if((AdvanceCollection == null)||(AdvanceCollection.isEmpty())) 
				{
					model.put("NotFound", true); 					
				}			
				model.put("TripSheet", AdvanceCollection);

				model.put("SEARCHDATE", VEHICLE_TC_DATERAGE);

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
	return new ModelAndView("TripSheet_Route_Wise_Difference_Report", model);

}


@RequestMapping("/TripSheetReport")
public ModelAndView TripSheetReport() {
	Map<String, Object> model = new HashMap<String, Object>();

	// show the Group service of the driver
	try {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// show the vehicle list List
		model.put("vehiclegroup", vg.prepareListofDto(vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id())));

	} catch (Exception e) {

	}
	return new ModelAndView("ReportTripSheet", model);
}


@RequestMapping(value = "/TripSheetReport", method = RequestMethod.POST)
public ModelAndView TripSheetReport(@RequestParam("Tripsheet_daterange") final String TripRange_daterange,
		@ModelAttribute("command") TripSheetDto tripSheetDto, BindingResult result) {
	TripSheetDto tripReport = RBL.prepareTripSheet(tripSheetDto);
	Map<String, Object> model = new HashMap<String, Object>();

	String dateRangeFrom = "", dateRangeTo = "";
	if (TripRange_daterange != "") {

		String[] From_TO_DateRange = null;
		try {

			From_TO_DateRange = TripRange_daterange.split(" to ");
			
			if(From_TO_DateRange != null) {
				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			}


		} catch (Exception e) {
			LOGGER.error("fuelmileage_vid:", e);
		}
		try {

			String Vid = "", Vehicle_group = "", CloseTripStatus = "", TripOpenDate_Close = "";

			if (tripReport.getVid() != null) {
				Vid = " AND T.vid=" + tripReport.getVid() + " ";
			}
			if (tripReport.getVehicleGroupId() != 0) {
				Vehicle_group = " AND V.vehicleGroupId =" + tripReport.getVehicleGroupId() + " ";
			}

			if (tripReport.getCloseTripStatusId() > 0) {
				CloseTripStatus = " AND T.tripStutesId=" + tripReport.getCloseTripStatusId() + " ";
			}

			if (dateRangeFrom != "" && dateRangeTo != "") {
				TripOpenDate_Close = "  AND T.closetripDate between '" + dateRangeFrom + "' AND '" + dateRangeTo
						+ "' ";
			}

			String query = " " + Vid + " " + Vehicle_group + " " + CloseTripStatus + " " + TripOpenDate_Close + " ";

			model.put("TripSheet", TripBL.prepareListofTripSheet(TripSheetService.listTripSheet(query)));

			List<Double> TripAdvanceTotal = TripSheetService.ReportTripAdvanceTotal(query);
			for (Double Total : TripAdvanceTotal) {
				AMOUNTFORMAT.setMaximumFractionDigits(0);
				if (Total == null) {
					Total = 0.0;
				}
				String TotalShow = AMOUNTFORMAT.format(Total);
				model.put("TripAdvanceTotal", TotalShow);
				break;
			}
			List<Double> TripExpenseTotal = TripSheetService.ReportTripExpenseTotal(query);
			for (Double TotalExpense : TripExpenseTotal) {
				AMOUNTFORMAT.setMaximumFractionDigits(0);
				if (TotalExpense == null) {
					TotalExpense = 0.0;
				}
				String TotalExpenseShow = AMOUNTFORMAT.format(TotalExpense);
				model.put("TripExpenseTotal", TotalExpenseShow);
				break;
			}

			List<Double> TripIncomeTotal = TripSheetService.ReportTripIncomeTotal(query);
			for (Double TotalIncome : TripIncomeTotal) {
				AMOUNTFORMAT.setMaximumFractionDigits(0);
				if (TotalIncome == null) {
					TotalIncome = 0.0;
				}
				String TotalIncomeShow = AMOUNTFORMAT.format(TotalIncome);
				model.put("TripIncomeTotal", TotalIncomeShow);
				break;
			}

			List<Double> TripAccBanlanceTotal = TripSheetService.ReportTripAccBalanceTotal(query);
			for (Double TotalBanlance : TripAccBanlanceTotal) {
				AMOUNTFORMAT.setMaximumFractionDigits(0);
				if (TotalBanlance == null) {
					TotalBanlance = 0.0;
				}
				String TotalShow = AMOUNTFORMAT.format(TotalBanlance);
				model.put("TripBanlanceTotal", TotalShow);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	return new ModelAndView("Trip_Report", model);
}

@RequestMapping("/TripColReport")
public ModelAndView TripCollectionReport() {
	Map<String, Object> model = new HashMap<String, Object>();

	// show the Group service of the driver
	try {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// show the vehicle list List
		model.put("vehiclegroup", vg.prepareListofDto(vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id())));

	} catch (Exception e) {

	}
	return new ModelAndView("ReportTripCollectiont", model);
}


@RequestMapping(value = "/TripColReport", method = RequestMethod.POST)
public ModelAndView TripColReport(@RequestParam("Tripsheet_daterange") final String TripRange_daterange,
		@ModelAttribute("command") TripCollectionSheetDto tripSheetDto, BindingResult result) {

	TripCollectionSheetDto tripReport = RBL.prepareTripCollectionSheet(tripSheetDto);

	Map<String, Object> model = new HashMap<String, Object>();

	String dateRangeFrom = "", dateRangeTo = "";
	if (TripRange_daterange != "") {

		String[] From_TO_DateRange = null;
		try {

			From_TO_DateRange = TripRange_daterange.split(" to ");

			dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


		} catch (Exception e) {
			LOGGER.error("fuelmileage_vid:", e);
		}
		try {

			String Vid = "", Vehicle_group = "", CloseTripStatus = "", TripOpenDate_Close = "";

			if (tripReport.getVID() != null) {
				Vid = " AND R.VID=" + tripReport.getVID() + " ";
			}

			if (tripReport.getVEHICLE_GROUP_ID() != 0) {
				Vehicle_group = " AND R.VEHICLE_GROUP_ID='" + tripReport.getVEHICLE_GROUP_ID() + "' ";
			}

			if (tripReport.getTRIP_CLOSE_STATUS() != "") {
				CloseTripStatus = " AND R.TRIP_CLOSE_STATUS='" + tripReport.getTRIP_CLOSE_STATUS() + "' ";
			}

			if (dateRangeFrom != "" && dateRangeTo != "") {
				TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '" + dateRangeTo
						+ "' ";
			}

			String query = " " + Vid + " " + Vehicle_group + " " + CloseTripStatus + " " + TripOpenDate_Close + " ";

			List<TripCollectionSheetDto> Collection = TripCollectionService.List_TripCollectionSheet_Report(query);
			model.put("TripCol", TCBL.prepare_TripCollectionDto(Collection));

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
	}
	return new ModelAndView("TripCollection_Report", model);
}


@RequestMapping("/TripDailyReport")
public ModelAndView TripDailyReport() {
	Map<String, Object> model = new HashMap<String, Object>();

	// show the Group service of the driver
	try {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// show the vehicle list List
		model.put("vehiclegroup", vg.prepareListofDto(vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id())));

	} catch (Exception e) {

	}
	return new ModelAndView("ReportTripDaily", model);
}


@RequestMapping(value = "/TripDailyReport", method = RequestMethod.POST)
public ModelAndView TripColReport(@RequestParam("Tripsheet_daterange") final String TripRange_daterange,
		@ModelAttribute("command") TripDailySheetDto tripSheetDto, BindingResult result) {

	TripDailySheetDto tripReport = RBL.prepareTripDailySheet(tripSheetDto);

	Map<String, Object> model = new HashMap<String, Object>();

	String dateRangeFrom = "", dateRangeTo = "";
	if (TripRange_daterange != "") {

		String[] From_TO_DateRange = null;
		try {

			From_TO_DateRange = TripRange_daterange.split(" to ");

			dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


		} catch (Exception e) {
			LOGGER.error("fuelmileage_vid:", e);
		}
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			String Vid = "", Vehicle_group = "", CloseTripStatus = "", TripOpenDate_Close = "";

			if (tripReport.getVEHICLEID() != null) {
				Vid = " AND R.VEHICLEID=" + tripReport.getVEHICLEID() + " ";
			}

			if (tripReport.getVEHICLE_GROUP_ID() != 0) {
				Vehicle_group = " AND R.VEHICLE_GROUP_ID=" + tripReport.getVEHICLE_GROUP_ID() + " ";
			}

			if (tripReport.getTRIP_STATUS_ID() > 0) {
				CloseTripStatus = " AND R.TRIP_STATUS_ID=" + tripReport.getTRIP_STATUS_ID() + " ";
			}

			if (dateRangeFrom != "" && dateRangeTo != "") {
				TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '" + dateRangeTo
						+ "' ";
			}

			String query = " " + Vid + " " + Vehicle_group + " " + CloseTripStatus + " " + TripOpenDate_Close + " ";

			List<TripDailySheetDto> Collection = tripDailySheetService.List_TripDailySheet_Report(query, userDetails);
			model.put("TripDaily", TDBL.prepare_TripDailySheetDto_ReportList(Collection));

			AMOUNTFORMAT.setMaximumFractionDigits(0);

			//Double[] totalGroupSheet = TDBL.Tota_TripDaily_Sheet_total(Collection);
			Double[] totalGroupSheet = TDBL.Total_TripDaily_Sheet_total_Details(Collection);

			/*if (totalGroupSheet != null) {
				String TotalUsageKM = AMOUNTFORMAT.format(totalGroupSheet[0]);
				model.put("TotalUsageKM", TotalUsageKM);
				model.put("TotalDiesel", totalGroupSheet[1]);
				model.put("TotalPassenger", totalGroupSheet[2]);
				model.put("TotalRFID", totalGroupSheet[3]);
				model.put("TotalCollection", totalGroupSheet[4]);
				model.put("TotalExpense", totalGroupSheet[5]);
				model.put("TotalOT", totalGroupSheet[6]);
				model.put("TotalBalance", totalGroupSheet[7]);
			}
*/
			if (totalGroupSheet != null) {
				String TotalUsageKM = AMOUNTFORMAT.format(totalGroupSheet[0]);
				model.put("TotalUsageKM", TotalUsageKM);
				String TotalDiesel = AMOUNTFORMAT.format(totalGroupSheet[1]);
				model.put("TotalDiesel", TotalDiesel);

				model.put("TotalKMPL", totalGroupSheet[2]);

				String TotalPassenger = AMOUNTFORMAT.format(totalGroupSheet[3]);
				model.put("TotalPassenger", TotalPassenger);

				String TotalRFID = AMOUNTFORMAT.format(totalGroupSheet[4]);
				model.put("TotalRFID", TotalRFID);

				String TotalRFIDAmount = AMOUNTFORMAT.format(totalGroupSheet[5]);
				model.put("TotalRFIDAmount", TotalRFIDAmount);

				String TotalCollection = AMOUNTFORMAT.format(totalGroupSheet[6]);
				model.put("TotalCollection", TotalCollection);

				String TotalWT = AMOUNTFORMAT.format(totalGroupSheet[7]);
				model.put("TotalWT", TotalWT);

				String TotalNetCollection = AMOUNTFORMAT.format(totalGroupSheet[8]);
				model.put("TotalNetCollection", TotalNetCollection);

				String TotalEPK = AMOUNTFORMAT.format(totalGroupSheet[9]);
				model.put("TotalEPK", TotalEPK);

				String TotalExpense = AMOUNTFORMAT.format(totalGroupSheet[10]);
				model.put("TotalExpense", TotalExpense);

				String TotalOT = AMOUNTFORMAT.format(totalGroupSheet[11]);
				model.put("TotalOT", TotalOT);

				String TotalBalance = AMOUNTFORMAT.format(totalGroupSheet[12]);
				model.put("TotalBalance", TotalBalance);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	return new ModelAndView("TripDaily_Report", model);
}


//Create Day Wise Report By Dev Y Start
@RequestMapping("/CreateDayWiseExpenseReport")
public ModelAndView CreateDayWiseExpenseReport() throws Exception {
	try {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
		model.put("configuration", configuration);
		return new ModelAndView("CreateDayWiseExpenseReport", model);
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}

@RequestMapping("/getTripsheetWithNoLoadingSheet")
public ModelAndView getTripsheetWithNoLoadingSheet() throws Exception {
	try {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
		model.put("configuration", configuration);
		return new ModelAndView("getTripsheetWithNoLoadingSheet", model);
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}

@RequestMapping("/VoucherWiseExpenseReport")
public ModelAndView VoucherWiseExpenseReport() throws Exception {
	Map<String, Object> 		model 			= new HashMap<String, Object>();
	HashMap<String, Object> 	configuration	= null;
	CustomUserDetails 			userDetails 	= null;
	
	userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_CONFIGURATION_CONFIG);
	
	model.put("configuration", configuration);
	return new ModelAndView("VoucherWiseExpenseReport", model);
}
//Create Day Wise Report By Dev Y Stop

/***************this method used to get Tripsheet Expense Details***************
	@RequestMapping(value = "/ShowTripSheetExpense", method = RequestMethod.GET)
public ModelAndView PrintBatteryInventory(@RequestParam("tripSheetID") final Long TripSheetID,
		final HttpServletResponse resul) {
			Map<String, Object> model = new HashMap<String, Object>();
			TripSheetDto 				trip 				= null;
			CustomUserDetails 			userDetails 		= null;
			ValueObject					valueOutObject 		= null;
	try {
		userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		trip = TripBL.GetTripSheetDetails(TripSheetService.getTripSheetDetails(TripSheetID, userDetails));
		model.put("trip", trip);
		
		
	} catch (Exception e) {
		
		LOGGER.error("Work Order:", e);
	}
	return new ModelAndView("TripSheetShowExpense", model);
	return valueOutObject.getHtData();
}*/

@RequestMapping("/allGroupTripSheetDateReport")
public ModelAndView AllGroupTripSheetDateReport() throws Exception {
	Map<String, Object> model = new HashMap<String, Object>();
	CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
	model.put("configuration", configuration);
	
	return new ModelAndView("All_Group_Tripsheet_Date_Report", model);
}


}


