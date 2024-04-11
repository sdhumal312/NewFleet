package org.fleetopgroup.web.controller;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.IssuesConfigurationContant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.IssuesBL;
import org.fleetopgroup.persistence.bl.PartCategoriesBL;
import org.fleetopgroup.persistence.bl.ReportBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IPartCategoriesService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleModelService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IssuesReportController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IIssuesService issuesService;
	
	@Autowired
	private IUserProfileService userProfileService;

	@Autowired
	private CompanyConfigurationService companyConfigurationService;
	
	@Autowired IPartCategoriesService partCategoriesService;
	
	@Autowired 	IVehicleModelService   	vehicleModelService   ;

	IssuesBL IBL = new IssuesBL();

	ReportBL RBL = new ReportBL();
	
	PartCategoriesBL 	partCategoriesBL	= new PartCategoriesBL();
	SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	@RequestMapping("/IR")
	public ModelAndView IssuesReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {

		Map<String, Object> 		model 				= new HashMap<String, Object>();
		HashMap<String, Object>     configuration		= null;
		CustomUserDetails			userDetails			= null; 
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			
			model.put("configuration", configuration);
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
		}

		return new ModelAndView("IR", model);
	}
	
	/* Issues Daily Status Report By Dev Yogi */
	@RequestMapping("/IssuesDailyStatusReport")
	public ModelAndView IssuesDailyStatusReport() throws Exception {
		Map<String, Object> 		model 					= null;
		HashMap<String, Object>   	configuration			= null;
		CustomUserDetails			userDetails 			= null;
		try {
			model 			= new HashMap<String, Object>();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			model.put(IssuesConfigurationContant.SHOW_BREAK_DOWN_SELECTION, (boolean) configuration.getOrDefault(IssuesConfigurationContant.SHOW_BREAK_DOWN_SELECTION, false));
			model.put("configuration",configuration);
			return new ModelAndView("Issues_Daily_StatusReport", model);
		}catch(Exception e) {
			LOGGER.error("ERR"+e);
			throw e;
		}
	}
	
	/* Issues Daily Status Report By Dev Yogi */
	
	
	@RequestMapping(value = "/IssuesDailyStatusReport", method = RequestMethod.POST)
	public ModelAndView IssuesDailyStatusReport(@ModelAttribute("command") IssuesDto issuesDto,
			@RequestParam("ISSUES_DATE_RANGE") final String ISSUES_DATE_RANGE, final HttpServletRequest request) {
		String 						IssuesType 				= "";
		String 						IssuesStatus 			= "";
		String 						IssuesLable 			= "";
		HashMap<String, Object>   	configuration			= null;
		List<IssuesDto> 			pageList				= null;
		Map<String, Object> 		model 					= new HashMap<String, Object>();

		try {
			IssuesDto issuesReport = RBL.prepareIssues(issuesDto);
			
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			model.put("configuration",configuration);

			String dateRangeFrom = "", dateRangeTo = "";

			String[] From_TO_DateRange = null;
			try {

				From_TO_DateRange = ISSUES_DATE_RANGE.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


			} catch (Exception e) {
				LOGGER.error("Renewal Reminder:", e);
			}


			if (issuesReport.getISSUES_TYPE_ID() > 0) {
				IssuesType = " AND R.ISSUE_TYPE_ID=" + issuesReport.getISSUES_TYPE_ID() + " ";
			}

			if (issuesReport.getISSUES_STATUS_ID() > 0) {
				IssuesStatus = " AND  R.ISSUES_STATUS_ID=" + issuesReport.getISSUES_STATUS_ID() + " ";
			}
			if (issuesReport.getISSUES_LABELS_ID() > 0) {
				IssuesLable = " AND  R.ISSUES_LABELS_ID=" + issuesReport.getISSUES_LABELS_ID() + " ";
			}

			if (dateRangeFrom != "" && dateRangeTo != "") {

				String Search_Query = " " + IssuesType + " " + IssuesStatus + " "+IssuesLable+" ";

				pageList = IBL.prepare_Issues_ListDto(issuesService.Report_daily_Issues_Search(dateRangeFrom, dateRangeTo, Search_Query, userDetails));

				if((pageList == null)||(pageList.isEmpty())) 
				{
					model.put("NotFound", true); 
					return new ModelAndView("Issues_Daily_StatusReport", model);
				}
				model.put("Issues", pageList);
			}

			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("Issues_Daily_StatusReport", model);
	}
	
	@RequestMapping("/IssuesReportedReport")
	public ModelAndView IssuesReportedReport() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object>	configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
		model.put("configuration", configuration);


		return new ModelAndView("Issues_Reported_Report", model);
	}
	
	
	@RequestMapping(value = "/IssuesReportedReport", method = RequestMethod.POST)
	public ModelAndView IssuesReportedReport(@RequestParam("ISSUES_REPORTED_DATE") final String ISSUES_REPORTED_DATE,
			@ModelAttribute("command") IssuesDto issuesDto, final HttpServletRequest request) {

		IssuesDto issuesReport = RBL.prepareIssues(issuesDto);

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		try {

			String IssuesType = "", IssuesStatus = "", Reported = "", AssignTo = "", IssuesLabel = "";

			if (ISSUES_REPORTED_DATE != "" && ISSUES_REPORTED_DATE != " ") {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = ISSUES_REPORTED_DATE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}
				if (issuesReport.getISSUES_TYPE_ID() > 0) {
					IssuesType = " AND R.ISSUE_TYPE_ID=" + issuesReport.getISSUES_TYPE_ID() + " ";
				}

				if (issuesReport.getISSUES_STATUS_ID() > 0) {
					IssuesStatus = " AND  R.ISSUES_STATUS_ID=" + issuesReport.getISSUES_STATUS_ID() + " ";
				}

				if (issuesReport.getISSUES_REPORTED_BY_ID() != null && issuesReport.getISSUES_REPORTED_BY_ID() > 0) {
					Reported = " AND  R.CREATEDBYID=" + issuesReport.getISSUES_REPORTED_BY_ID() + " ";
				}

				if (issuesReport.getISSUES_ASSIGN_TO() != "") {
					AssignTo = " AND  lower(R.ISSUES_ASSIGN_TO) Like ('%" + issuesReport.getISSUES_ASSIGN_TO() + "%') ";
				}
				
				if (issuesReport.getISSUES_LABELS_ID() > 0) {
					IssuesLabel = " AND  R.ISSUES_LABELS_ID=" + issuesReport.getISSUES_LABELS_ID() + " ";
				}

				if (issuesReport.getISSUES_REPORTED_DATE() != "") {

					String Search_Query = " " + IssuesType + " " + IssuesStatus + " " + Reported + " " + AssignTo + " " + IssuesLabel + " ";

					
					List<IssuesDto> pageList = IBL.prepare_Issues_ListDto(
							issuesService.Report_Issues_Reported_Search(dateRangeFrom, dateRangeTo, Search_Query, userDetails));

					
					if((pageList == null) || (pageList.isEmpty())) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Issues_Reported_Report", model);
					}
					
					
					model.put("Issues", pageList);
				}
			}


			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("Issues_Reported_Report", model);
	}
	
	@RequestMapping("/YourIssuesStatusReport")
	public ModelAndView YourIssuesStatusReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Issues_Reported_BY_Report", model);
	}
	
	
	
	
	@RequestMapping(value = "/YourIssuesStatusReport", method = RequestMethod.POST)
	public ModelAndView YourIssuesStatusReport(@RequestParam("ISSUES_REPORTED_DATE") final String ISSUES_REPORTED_DATE,
			@ModelAttribute("command") IssuesDto issuesDto, final HttpServletRequest request) {

		IssuesDto issuesReport = RBL.prepareIssues(issuesDto);

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {

			String IssuesStatus = "";

			if (ISSUES_REPORTED_DATE != "" && ISSUES_REPORTED_DATE != " ") {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = ISSUES_REPORTED_DATE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				if (issuesReport.getISSUES_STATUS_ID() > 0) {
					IssuesStatus = " AND  R.ISSUES_STATUS_ID=" + issuesReport.getISSUES_STATUS_ID() + " ";
				}

				if (issuesReport.getISSUES_REPORTED_DATE() != "") {

					String Search_Query = " " + IssuesStatus + " ";
					
					List<IssuesDto> pageList = IBL.prepare_Issues_ListDto(issuesService
							.Report_YourIssues_Status_Search(userDetails.getEmail(), dateRangeFrom, dateRangeTo, Search_Query, userDetails.getCompany_id(), userDetails.getId()));

					if((pageList == null) || (pageList.isEmpty())) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Issues_Reported_BY_Report", model);
					}
					
					
					model.put("Issues", pageList);
				}
			}


			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("Issues_Reported_BY_Report", model);
	}


	/** Report and Serch Page Show in vehicle */
	@RequestMapping("/IssuesReport")
	public ModelAndView IssuesReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		// show the Group service of the driver

		return new ModelAndView("ReportIssues", model);
	}

	@RequestMapping(value = "/IssuesReport", method = RequestMethod.POST)
	public ModelAndView IssuesReport(@RequestParam("ISSUES_REPORTED_DATE") final String daterange, IssuesDto issues,
			final HttpServletRequest request) {
		boolean 						operateAllIssuePermission 	= false;
		Collection<GrantedAuthority>	permission					= null;

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		permission					= userDetails.getGrantedAuthoritiesList();
		operateAllIssuePermission 	= permission.contains(new SimpleGrantedAuthority("OPERATE_ALL_ISSUES"));
		if (daterange != "" && daterange != "") {

			String dateRangeFrom = "", dateRangeTo = "";

			String[] Issues_TO_DateRange = null;
			try {

				Issues_TO_DateRange = daterange.split(" to ");

				dateRangeFrom = dateFormatSQL.format(dateFormat.parse(Issues_TO_DateRange[0]))+" "+DateTimeUtility.DAY_START_TIME;
				dateRangeTo = dateFormatSQL.format(dateFormat.parse(Issues_TO_DateRange[1]))+" "+DateTimeUtility.DAY_END_TIME ;

			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}

			try {
				String ISSUES_VID = "", ISSUES_DRIVER_ID = "", ISSUES_BRANCH_ID = "", ISSUES_DEPARTNMENT_ID = "",
						ISSUES_LABELS = "", ISSUES_TYPE = "";

				if (issues.getISSUES_VID() != null) {
					ISSUES_VID = " AND ISSUES_VID=" + issues.getISSUES_VID() + "";
				}

				if (issues.getISSUES_DRIVER_ID() != null) {
					ISSUES_DRIVER_ID = " AND ISSUES_DRIVER_ID=" + issues.getISSUES_DRIVER_ID() + "";
				}

				if (issues.getISSUES_BRANCH_ID() != null) {
					ISSUES_BRANCH_ID = " AND ISSUES_BRANCH_ID=" + issues.getISSUES_BRANCH_ID() + "";
				}

				if (issues.getISSUES_DEPARTNMENT_ID() != null) {
					ISSUES_DEPARTNMENT_ID = " AND ISSUES_DEPARTNMENT_ID=" + issues.getISSUES_DEPARTNMENT_ID() + "";
				}

				if (issues.getISSUES_LABELS_ID() > 0) {
					ISSUES_LABELS = " AND ISSUES_LABELS_ID=" + issues.getISSUES_LABELS_ID() + "";
				}

				if (issues.getISSUES_TYPE_ID() > 0) {
					ISSUES_TYPE = " AND ISSUE_TYPE_ID=" + issues.getISSUES_TYPE_ID() + "";
				}
				String query = "" + ISSUES_VID + " " + ISSUES_DRIVER_ID + " " + ISSUES_BRANCH_ID + " "
						+ ISSUES_DEPARTNMENT_ID + " " + ISSUES_LABELS + " " + ISSUES_TYPE + " ";

				model.put("Issues", IBL.prepare_Issues_ListDto(
						issuesService.Report_find_Issues_Search(dateRangeFrom, dateRangeTo, query)));
				model.put("createdById", userDetails.getId());
				model.put("email", userDetails.getEmail());
				model.put("operateAllIssuePermission",operateAllIssuePermission);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			model.put("emptyNotRange", true);
			return new ModelAndView("redirect:/IssuesReport", model);
		}

		return new ModelAndView("Issues_Report", model);
	}

	@RequestMapping("/breakDownAnalysisReport")
	public ModelAndView AllVehiclesMileageReport() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails  userDetails = null;
		HashMap<String, Object> configuration = null;

		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			model.put("configuration",configuration);
			model.put("PartCategories", partCategoriesBL.prepareListofBean(partCategoriesService.listPartCategoriesByCompayId(userDetails.getCompany_id())));
			return new ModelAndView("breakDownAnalysisReport", model);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping("/changedTobreakDownReport")
	public ModelAndView changedTobreakDownReport() throws Exception {
		Map<String, Object> model = new HashMap<>();
		CustomUserDetails  userDetails = null;
		HashMap<String, Object> configuration = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			model.put("configuration",configuration);
			model.put("PartCategories", partCategoriesBL.prepareListofBean(partCategoriesService.listPartCategoriesByCompayId(userDetails.getCompany_id())));
			model.put("modelList",vehicleModelService.getVehicleModelListByCompanyId(userDetails.getCompany_id()));
			return new ModelAndView("changedToBreakDownReport", model);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping("/vehicleandBreakdownReport")
	public ModelAndView vehicleandBreakdownReport() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails  userDetails = null;
		HashMap<String, Object> configuration = null;

		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			model.put("configuration",configuration);
			model.put("PartCategories", partCategoriesBL.prepareListofBean(partCategoriesService.listPartCategoriesByCompayId(userDetails.getCompany_id())));
			return new ModelAndView("vehicleandBreakdownReport", model);
		} catch (Exception e) {
			throw e;
		}
	}

}
