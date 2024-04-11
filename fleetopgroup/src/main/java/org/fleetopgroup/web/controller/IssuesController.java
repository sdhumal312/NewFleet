
/**
 * 
 */
package org.fleetopgroup.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.fleetopgroup.constant.GPSConstant;
import org.fleetopgroup.constant.IssuesConfigurationContant;
import org.fleetopgroup.constant.IssuesTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.VehicleConfigurationContant;
import org.fleetopgroup.constant.VehicleGPSConfiguratinConstant;
import org.fleetopgroup.persistence.bl.IssuesBL;
import org.fleetopgroup.persistence.bl.JobTypeBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.dao.CommentTypeRepository;
import org.fleetopgroup.persistence.dto.BranchDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DepartmentDto;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.dto.IssueAnalysisDto;
import org.fleetopgroup.persistence.dto.IssuesCommentDto;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.IssuesTasksDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleTyreLayoutPositionDto;
import org.fleetopgroup.persistence.model.Branch;
import org.fleetopgroup.persistence.model.CommentType;
import org.fleetopgroup.persistence.model.Department;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.Issues;
import org.fleetopgroup.persistence.model.IssuesComment;
import org.fleetopgroup.persistence.model.IssuesDocument;
import org.fleetopgroup.persistence.model.IssuesPhoto;
import org.fleetopgroup.persistence.model.IssuesSequenceCounter;
import org.fleetopgroup.persistence.model.IssuesTasks;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.serviceImpl.IBranchService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDepartmentService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IIssueAnalysisService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGPSDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreAssignmentService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.error.MailSentExistException;
import org.fleetopgroup.web.util.AESEncryptDecrypt;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

import com.google.gson.Gson;

/**
 * @author fleetop
 *
 */
@Controller
public class IssuesController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IIssuesService issuesService;
	IssuesBL IBL = new IssuesBL();

	@Autowired
	private IVehicleService vehicleService;

	@Autowired
	private IServiceReminderService ServiceReminderService;

	@Autowired
	private IVehicleOdometerHistoryService vehicleOdometerHistoryService;
	
	@Autowired IVehicleDocumentService	vehicleDocumentService;
	
	VehicleOdometerHistoryBL VOHBL = new VehicleOdometerHistoryBL();

	@Autowired
	private IDriverService driverService;

	@Autowired
	private IDepartmentService departmentService;

	@Autowired
	private IBranchService branchService;

	@Autowired
	private IUserProfileService userProfileService;
	
	@Autowired
	private IVehicleGroupService	vehicleGroupService;
	
	@Autowired
	private IIssuesSequenceService	issuesSequenceService;
	
	@Autowired
	private CommentTypeRepository	commentTypeRepository;
	
	
	@Autowired ICompanyConfigurationService			companyConfigurationService;
	@Autowired IPartLocationPermissionService 		partLocationPermissionService;
	@Autowired IVehicleGPSDetailsService			vehicleGPSDetailsService;
	@Autowired IServiceEntriesService				ServiceEntriesService;
	@Autowired IWorkOrdersService					WorkOrdersService;
	@Autowired IIssueAnalysisService				issueAnalysisService;
	@Autowired IVehicleTyreAssignmentService		vehicleTyreAssignmentService;
	
	JobTypeBL DriDT = new JobTypeBL();

	VehicleBL VBL = new VehicleBL();
	PartLocationsBL partLocationsBL = new PartLocationsBL();

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	SimpleDateFormat SQL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	SimpleDateFormat dateFormatonlyDateTime = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");

	ByteImageConvert ByteImageConvert = new ByteImageConvert();
	
	java.util.Date currentDate = new java.util.Date();
	DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");

	/** This controller request in show issues list */
	@RequestMapping(value = "/SearchIYShow", method = RequestMethod.POST)
	public ModelAndView SearchIYShow(@RequestParam("Search") final Long issues_ID, final HttpServletRequest request) {
		try {
			CustomUserDetails auth = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (issues_ID != null) {
				IssuesDto issue = issuesService.get_IssuesDetailsByNumber(issues_ID, auth);
				if (issue != null) {
						return new ModelAndView("redirect:/showIssues?Id="+AESEncryptDecrypt.encryptBase64("" + issue.getISSUES_ID()));
					
				} else {
					return new ModelAndView("redirect:/NotFound.in");
				}

			} else {
				return new ModelAndView("redirect:/NotFound.in");
			}

		} catch (NullPointerException e) {
			LOGGER.error("Issues Err:", e);
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
			return new ModelAndView("redirect:/NotFound.in");
		}
	}

	/** This controller request in show issues list */
	@RequestMapping(value = "/SearchIYAllShow", method = RequestMethod.POST)
	public ModelAndView SearchIYALLShow(@RequestParam("Search") final Long issues_ID,
			final HttpServletRequest request) {
		try {
			CustomUserDetails auth = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (issues_ID != null) {
				IssuesDto issue = issuesService.get_IssuesDetailsByNumber(issues_ID, auth);
				if (issue != null) {
						return new ModelAndView("redirect:/showIssues?Id="+AESEncryptDecrypt.encryptBase64("" + issue.getISSUES_ID()));
					
				} else {
					return new ModelAndView("redirect:/NotFound.in");
				}

			} else {
				return new ModelAndView("redirect:/NotFound.in");
			}

		} catch (NullPointerException e) {
			LOGGER.error("Issues Err:", e);
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
			return new ModelAndView("redirect:/NotFound.in");
		}
	}

	/** This controller request in open issues list */
	@RequestMapping(value = "/issues/{pageNumber}", method = RequestMethod.GET)
	public String IssuesALL(@PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails				userDetails					= null;
		boolean 						operateAllIssuePermission 	= false;
		Collection<GrantedAuthority>	permission					= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			permission					= userDetails.getGrantedAuthoritiesList();
			operateAllIssuePermission 	= permission.contains(new SimpleGrantedAuthority("OPERATE_ALL_ISSUES"));
			
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			Page<Issues> page = issuesService.get_Issues_All_pagination(pageNumber, userDetails.getCompany_id(), userDetails.getId());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			model.addAttribute("SelectStatus", "ALL");
			model.addAttribute("operateAllIssuePermission", operateAllIssuePermission);
			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("configuration", configuration);
			model.addAttribute("createdById", userDetails.getId());
			model.addAttribute("email", userDetails.getEmail());

			model.addAttribute("IssuesCount", page.getTotalElements());

			List<IssuesDto> pageList = IBL.prepare_Issues_ListDto(issuesService.find_Issues_All_Status(pageNumber, userDetails));

			model.addAttribute("Issues", pageList);
			
			DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
			Calendar cal = Calendar.getInstance();
			java.util.Date date = cal.getTime();
			String DayOne = SQL_DATE_FORMAT.format(date);
			String DayOneNew = dateFormat.format(date);
			String DayTwo ="", DayThree ="", DayFour ="", DayFive ="";
			String DayTwoNew ="", DayThreeNew ="", DayFourNew ="", DayFiveNew ="";
			for (int i = 0; i < 4; i++) {
			    cal.add(Calendar.DAY_OF_MONTH, -1);
			    date = cal.getTime();
			    switch (i) {
				case 0:
					DayTwo = SQL_DATE_FORMAT.format(date);
					DayTwoNew = dateFormat.format(date);
					break;
               case 1:
					
            	   DayThree = SQL_DATE_FORMAT.format(date);
            	   DayThreeNew = dateFormat.format(date);
					break;
               case 2:
            	   DayFour = SQL_DATE_FORMAT.format(date);
            	   DayFourNew = dateFormat.format(date);
            	   break;
               case 3:
            	   DayFive = SQL_DATE_FORMAT.format(date);
            	   DayFiveNew = dateFormat.format(date);
            	   break;
              	
            	   }
			}
			
			model.addAttribute("DayFive", DayFive);
			model.addAttribute("DayOne", DayOne);
			model.addAttribute("DayTwo", DayTwo);
			model.addAttribute("DayThree", DayThree);
			model.addAttribute("DayFour", DayFour);
			Object[] count = issuesService.count_TOTAL_ISSUES_NOT_CLOSED_FIVEDAYS_ISSUES(DayOne,DayTwo,DayThree, DayFour, DayFive, userDetails);
			model.addAttribute("DayOne_Count", (Long) count[1]);
			model.addAttribute("DayTwo_Count", (Long) count[2]);
			model.addAttribute("DayThree_Count", (Long) count[3]);
			model.addAttribute("DayFour_Count", (Long) count[4]);
			model.addAttribute("DayFive_Count", (Long) count[0]);
			
	Object[] statusCount = issuesService.countTotalIssues_AND_IssuesOpenStatus(userDetails);
			
			model.addAttribute("IssuesYou", (Long) statusCount[0]);
			model.addAttribute("IssuesOpenCount", (Long) statusCount[1]);
			model.addAttribute("IssuesCount", (Long) statusCount[2]);
			try {
			model.addAttribute("overDueCount", IBL.prepareOverDueList(issuesService.find_Issues_Overdue_Status(pageNumber,userDetails, ft.format(currentDate))).size());
			}catch(Exception e) {
				model.addAttribute("overDueCount", 0);
				e.printStackTrace();
			}
			model.addAttribute("woCreatedCount", (Long) statusCount[4]);
			model.addAttribute("seCreatedCount", (Long) statusCount[5]);
			model.addAttribute("resolveCount", (Long) statusCount[6]);
			model.addAttribute("rejectCount", (Long) statusCount[7]);
			model.addAttribute("closeCount", (Long) statusCount[8]);
			model.addAttribute("dseCount", (Long) statusCount[9]);
			model.addAttribute("vBreakDownCount", (Long) statusCount[10]);
			model.addAttribute("DayOneNew", DayOneNew);
			model.addAttribute("DayTwoNew", DayTwoNew);
			model.addAttribute("DayThreeNew", DayThreeNew);
			model.addAttribute("DayFourNew", DayFourNew);
			model.addAttribute("DayFiveNew", DayFiveNew);
			
			if((boolean) configuration.get("showDateWiseOverdue")) {
				Object[] countOverDue = issuesService.countOverdueIssues(userDetails);
				model.addAttribute("overDueCount", (Long) countOverDue[0]+(Long) countOverDue[1]+(Long) countOverDue[2]);
			}
			

		} catch (NullPointerException e) {
			e.printStackTrace();
			return "redirect:/NotFound.in";

		} catch (Exception e) {
			LOGGER.error("OpenIssues Page:", e);
			e.printStackTrace();
		}
		return "IssuesAll";

	}
	
	/** This controller request in open issues list */
	@RequestMapping(value = "/NOTOPENISSUES", method = RequestMethod.GET)
	public String NOTOPENISSUES(@RequestParam("DATE") final String Searchdate, Model model) throws Exception {
		CustomUserDetails		userDetails		= null;
		boolean 						operateAllIssuePermission 	= false;
		Collection<GrantedAuthority>	permission					= null;
		List<IssuesDto> pageList = null;
		List<IssuesDto> pageListBL = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			permission					= userDetails.getGrantedAuthoritiesList();
			operateAllIssuePermission 	= permission.contains(new SimpleGrantedAuthority("OPERATE_ALL_ISSUES"));

			HashMap<String, Object> 	configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			
			Calendar cal = Calendar.getInstance();
			java.util.Date date = cal.getTime();
			String DayOne = SQL_DATE_FORMAT.format(date);
			String DayOneNew = dateFormat.format(date);
			String DayTwo ="", DayThree ="", DayFour ="", DayFive ="";
			String DayTwoNew ="", DayThreeNew ="", DayFourNew ="", DayFiveNew ="";
			for (int i = 0; i < 4; i++) {
			    cal.add(Calendar.DAY_OF_MONTH, -1);
			    date = cal.getTime();
			    switch (i) {
				case 0:
					DayTwo = SQL_DATE_FORMAT.format(date);
					DayTwoNew = dateFormat.format(date);
					break;
               case 1:
					
            	   DayThree = SQL_DATE_FORMAT.format(date);
            	   DayThreeNew = dateFormat.format(date);
					break;
               case 2:
            	   DayFour = SQL_DATE_FORMAT.format(date);
            	   DayFourNew = dateFormat.format(date);
            	   break;
               case 3:
            	   DayFive = SQL_DATE_FORMAT.format(date);
            	   DayFiveNew = dateFormat.format(date);
            	   break;
              	
            	   }
			}
			
			if(Searchdate.equals(DayFive)) {
				pageList = issuesService.findOpenIsueBeforeFiveDays(IssuesTypeConstant.ISSUES_STATUS_OPEN, Searchdate, userDetails.getCompany_id());
			}else {
			 pageList = issuesService.find_NOTOPENISSUES_Status_SearchDate(IssuesTypeConstant.ISSUES_STATUS_OPEN, Searchdate, userDetails.getCompany_id());
			}
			pageListBL = IBL.prepare_Issues_ListDto(pageList);
			model.addAttribute("Issues", pageListBL);
			model.addAttribute("operateAllIssuePermission", operateAllIssuePermission);
			model.addAttribute("DayFive", DayFive);
			model.addAttribute("DayOne", DayOne);
			model.addAttribute("DayTwo", DayTwo);
			model.addAttribute("DayThree", DayThree);
			model.addAttribute("DayFour", DayFour);
			Object[] count = issuesService.count_TOTAL_ISSUES_NOT_CLOSED_FIVEDAYS_ISSUES(DayOne,DayTwo,DayThree, DayFour, DayFive, userDetails);
			model.addAttribute("DayOne_Count", (Long) count[1]);
			model.addAttribute("DayTwo_Count", (Long) count[2]);
			model.addAttribute("DayThree_Count", (Long) count[3]);
			model.addAttribute("DayFour_Count", (Long) count[4]);
			model.addAttribute("DayFive_Count", (Long) count[0]);
			model.addAttribute("createdById", userDetails.getId());
			model.addAttribute("email", userDetails.getEmail());
			
			model.addAttribute("DayOneNew", DayOneNew);
			model.addAttribute("DayTwoNew", DayTwoNew);
			model.addAttribute("DayThreeNew", DayThreeNew);
			model.addAttribute("DayFourNew", DayFourNew);
			model.addAttribute("DayFiveNew", DayFiveNew);
			
			
			Object[] statusCount = issuesService.countTotalIssues_AND_IssuesOpenStatus(userDetails);
			
			model.addAttribute("IssuesYou", (Long) statusCount[0]);
			model.addAttribute("IssuesOpenCount", (Long) statusCount[1]);
			model.addAttribute("IssuesCount", (Long) statusCount[2]);
			model.addAttribute("overDueCount", (Long) statusCount[3]);
			model.addAttribute("woCreatedCount", (Long) statusCount[4]);
			model.addAttribute("seCreatedCount", (Long) statusCount[5]);
			model.addAttribute("resolveCount", (Long) statusCount[6]);
			model.addAttribute("rejectCount", (Long) statusCount[7]);
			model.addAttribute("closeCount", (Long) statusCount[8]);
			model.addAttribute("dseCount", (Long) count[9]);
			
			if((boolean) configuration.get("showDateWiseOverdue")) {
				Object[] countOverDue = issuesService.countOverdueIssues(userDetails);
				model.addAttribute("overDueCount", (Long) countOverDue[0]+(Long) countOverDue[1]+(Long) countOverDue[2]+(Long) countOverDue[3]);
			}
			

		} catch (NullPointerException e) {
			e.printStackTrace();
			return "redirect:/NotFound.in";

		} catch (Exception e) {
			LOGGER.error("OpenIssues Page:", e);
			e.printStackTrace();
		}
		return "IssuesAll";

	}

	/** This controller request in open issues list */
	@RequestMapping(value = "/YourIssues/{pageNumber}", method = RequestMethod.GET)
	public String YourIssues(@PathVariable Integer pageNumber, Model model) throws Exception {
		/** who Create this Issues get email id to user profile details */
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);

			Page<Issues> page = issuesService.get_Issues_Your_pagination(pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("configuration", configuration);

		//	model.addAttribute("IssuesOpenCount", page.getTotalElements());

			List<IssuesDto> pageList = IBL
					.prepare_Issues_ListDto(issuesService.find_Issues_Your_Status(pageNumber, userDetails));

			model.addAttribute("SelectStatus", "YOUR_ISSUE");
			model.addAttribute("Issues", pageList);
	Object[] count = issuesService.countTotalIssues_AND_IssuesOpenStatus(userDetails);
			
			model.addAttribute("IssuesYou", (Long) count[0]);
			model.addAttribute("IssuesOpenCount", (Long) count[1]);
			model.addAttribute("IssuesCount", (Long) count[2]);
			try {
				model.addAttribute("overDueCount", IBL.prepareOverDueList(issuesService.find_Issues_Overdue_Status(pageNumber,userDetails, ft.format(currentDate))).size() );			
			}catch(Exception e) {
				model.addAttribute("overDueCount", 0);
				e.printStackTrace();
			}
			
			model.addAttribute("woCreatedCount", (Long) count[4]);
			model.addAttribute("seCreatedCount", (Long) count[5]);
			model.addAttribute("resolveCount", (Long) count[6]);
			model.addAttribute("rejectCount", (Long) count[7]);
			model.addAttribute("closeCount", (Long) count[8]);
			model.addAttribute("dseCount", (Long) count[9]);
			model.addAttribute("vBreakDownCount", (Long) count[10]);
			
			if((boolean) configuration.get("showDateWiseOverdue")) {
				Object[] countOverDue = issuesService.countOverdueIssues(userDetails);
				model.addAttribute("overDueCount", (Long) countOverDue[0]+(Long) countOverDue[1]+(Long) countOverDue[2]);
			}
			
			// EMI REMINDER REPORT CHECK
			/*
			 * Calculate c = new Calculate();
			 * c.repayment_schedule(10000000.00, 12.00, 60, 60, 0);
			 * c.repayment_schedule(10000000.00, 12.00, 60); c.repayment_schedule_1(1000,
			 * 10, 4, 9, 0, 7);
			 */

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("YourIssues Page:", e);
			e.printStackTrace();
		}
		return "YourIssues";

	}

	/** This controller request in open issues list */
	@RequestMapping(value = "/issuesOpen/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView OpenIssues(@ModelAttribute("command") Issues IsuessDto, BindingResult result, @PathVariable Integer pageNumber) {

		Map<String, Object> model = new HashMap<String, Object>();
		boolean 						operateAllIssuePermission 	= false;
		Collection<GrantedAuthority>	permission					= null;
		try {
			/** who Create this Issues get email id to user profile details */
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			permission					= userDetails.getGrantedAuthoritiesList();
			operateAllIssuePermission 	= permission.contains(new SimpleGrantedAuthority("OPERATE_ALL_ISSUES"));
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			List<IssuesDto> OpenIssues = issuesService.find_Issues_Open_Status(pageNumber, userDetails.getId(), userDetails);

			Page<Issues> page = issuesService.get_OpenIssues_All_pagination(pageNumber, userDetails.getCompany_id(), userDetails.getId(),IssuesTypeConstant.ISSUES_STATUS_OPEN);
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			model.put("deploymentLog", page);
			model.put("beginIndex", begin);
			model.put("endIndex", end);
			model.put("currentIndex", current);
			model.put("createdById", userDetails.getId());
			model.put("issueUrl", "issuesOpen");
			model.put("OpenIssuesCount", page.getTotalElements());
			
			List<IssuesDto> pageList = IBL.prepare_Issues_ListDto(issuesService.find_Issues_Open_Status(pageNumber,userDetails.getId(), userDetails));
			model.put("Issues", pageList);
			if (OpenIssues != null) {
				model.put("Count", OpenIssues.size());
			}
			model.put("operateAllIssuePermission", operateAllIssuePermission);
			model.put("Issues", IBL.prepare_Issues_ListDto(OpenIssues));
			model.put("createdById", userDetails.getId());
			model.put("email", userDetails.getEmail());
			model.put("SelectStatus", "OPEN");
			model.put("configuration", configuration);

			Object[] count = issuesService.countTotalIssues_AND_IssuesOpenStatus(userDetails);
			
			model.put("IssuesYou", (Long) count[0]);
			model.put("IssuesOpenCount", (Long) count[1]);
			model.put("IssuesCount", (Long) count[2]);
			try {
				model.put("overDueCount", IBL.prepareOverDueList(issuesService.find_Issues_Overdue_Status(pageNumber, userDetails, ft.format(currentDate))).size());
			} catch (Exception e) {
				model.put("overDueCount", 0);
				e.printStackTrace();
			}
			model.put("woCreatedCount", (Long) count[4]);
			model.put("seCreatedCount", (Long) count[5]);
			model.put("resolveCount", (Long) count[6]);
			model.put("rejectCount", (Long) count[7]);
			model.put("closeCount", (Long) count[8]);
			model.put("dseCount", (Long) count[9]);
			model.put("vBreakDownCount", (Long) count[10]);
			
			if((boolean) configuration.get("showDateWiseOverdue")) {
				Object[] countOverDue = issuesService.countOverdueIssues(userDetails);
				model.put("overDueCount", (Long) countOverDue[0]+(Long) countOverDue[1]+(Long) countOverDue[2]);
			}			

		} catch (NullPointerException e) {
			LOGGER.error("err"+e);
			e.printStackTrace();
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("OpenIssues", model);
	}

	/** This controller request in overdue issues list */
	@RequestMapping(value = "/issuesOverdue/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView issuesOverdue(@ModelAttribute("command") Issues IsuessDto, BindingResult result, @PathVariable Integer pageNumber) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			/** who Create this Issues get email id to user profile details */
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			
			List<IssuesDto> OverDueIssues = issuesService.find_Issues_Overdue_Status(pageNumber, userDetails, ft.format(currentDate));
			if (OverDueIssues != null) {
				model.put("Count", OverDueIssues.size());
			}
			model.put("Issues", IBL.prepareOverDueList(OverDueIssues));

			model.put("SelectStatus", "OVERDUE");
			model.put("configuration", configuration);

			Page<Issues> page = issuesService.get_OverdueIssues_All_pagination(pageNumber, userDetails.getCompany_id(), userDetails.getId(),IssuesTypeConstant.ISSUES_STATUS_OPEN,DateTimeUtility.getCurrentTimeStamp());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			model.put("deploymentLog", page);
			model.put("beginIndex", begin);
			model.put("endIndex", end);
			model.put("currentIndex", current);
			model.put("createdById", userDetails.getId());
			model.put("OverdueIssuesCount", page.getTotalElements());
			List<IssuesDto> pageList = IBL.prepare_Issues_ListDto(issuesService.find_Issues_Overdue_Status(pageNumber, userDetails, ft.format(currentDate)));
			model.put("Issues", pageList);
			
			Object[] count = issuesService.countTotalIssues_AND_IssuesOpenStatus(userDetails);
			
			model.put("IssuesYou", (Long) count[0]);
			model.put("IssuesOpenCount", (Long) count[1]);
			model.put("IssuesCount", (Long) count[2]);
			try {
			model.put("overDueCount",IBL.prepareOverDueList(OverDueIssues).size());
			}catch(Exception e) {
				model.put("overDueCount", 0);
				e.printStackTrace();
			}
			model.put("woCreatedCount", (Long) count[4]);
			model.put("seCreatedCount", (Long) count[5]);
			model.put("resolveCount", (Long) count[6]);
			model.put("rejectCount", (Long) count[7]);
			model.put("closeCount", (Long) count[8]);
			model.put("dseCount", (Long) count[9]);
			model.put("vBreakDownCount", (Long) count[10]);
			
			
			if((boolean) configuration.get("showDateWiseOverdue")) {
				Object[] countOverDue = issuesService.countOverdueIssues(userDetails);
				 long countOver= (Long) countOverDue[0]+(Long) countOverDue[1]+(Long) countOverDue[2] ;
				model.put("overDueCount",countOver);
				model.put("Count", countOver);
			}
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("OverdueIssues", model);
	}

	/** This controller request in resolved issues list */
	@RequestMapping(value = "/issuesWoCreated/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView issuesWoCreated(@ModelAttribute("command") Issues IsuessDto, BindingResult result, @PathVariable Integer pageNumber) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			/** who Create this Issues get email id to user profile details */
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);

			List<IssuesDto> ResolvedIssues = issuesService.find_Issues_WoCreated_Status(pageNumber, userDetails);
			Page<Issues> page = issuesService.get_WOCreatedIssues_All_pagination(pageNumber, userDetails.getCompany_id(), userDetails.getId(),IssuesTypeConstant.ISSUES_STATUS_WOCREATED);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			model.put("deploymentLog", page);
			model.put("beginIndex", begin);
			model.put("endIndex", end);
			model.put("currentIndex", current);
			model.put("createdById", userDetails.getId());
			model.put("issueUrl", "issuesWoCreated");
			model.put("WoCreatedIssuesCount", page.getTotalElements());
			List<IssuesDto> pageList = IBL.prepare_Issues_ListDto(issuesService.find_Issues_WoCreated_Status(pageNumber, userDetails));
			model.put("Issues", pageList);
			
			if (ResolvedIssues != null) {
				model.put("Count", ResolvedIssues.size());
			}
			model.put("Issues", IBL.prepare_Issues_ListDto(ResolvedIssues));

			model.put("SelectStatus", "WOCREATED");
			model.put("configuration", configuration);

	Object[] count = issuesService.countTotalIssues_AND_IssuesOpenStatus(userDetails);
			
			model.put("IssuesYou", (Long) count[0]);
			model.put("IssuesOpenCount", (Long) count[1]);
			model.put("IssuesCount", (Long) count[2]);
			try {
			model.put("overDueCount", IBL.prepareOverDueList(issuesService.find_Issues_Overdue_Status(pageNumber, userDetails, ft.format(currentDate))).size());
			}catch(Exception e) {
				model.put("overDueCount", 0);
				e.printStackTrace();
			}
			model.put("woCreatedCount", (Long) count[4]);
			model.put("seCreatedCount", (Long) count[5]);
			model.put("resolveCount", (Long) count[6]);
			model.put("rejectCount", (Long) count[7]);
			model.put("closeCount", (Long) count[8]);
			model.put("dseCount", (Long) count[9]);
			model.put("vBreakDownCount", (Long) count[10]);
			
			if((boolean) configuration.get("showDateWiseOverdue")) {
				Object[] countOverDue = issuesService.countOverdueIssues(userDetails);
				model.put("overDueCount", (Long) countOverDue[0]+(Long) countOverDue[1]+(Long) countOverDue[2]);
			}
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("OpenIssues", model);
	}

	/** This controller request in resolved issues list */
	@RequestMapping(value = "/issuesResolved/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView issuesResolved(@ModelAttribute("command") Issues IsuessDto, BindingResult result, @PathVariable Integer pageNumber) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			/** who Create this Issues get email id to user profile details */
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);

			List<IssuesDto> ResolvedIssues = issuesService.find_Issues_Resolved_Status(pageNumber,userDetails);
			Page<Issues> page = issuesService.get_Resolved_All_pagination(pageNumber, userDetails.getCompany_id(), userDetails.getId(),IssuesTypeConstant.ISSUES_STATUS_RESOLVED);

			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			model.put("deploymentLog", page);
			model.put("beginIndex", begin);
			model.put("endIndex", end);
			model.put("currentIndex", current);
			model.put("createdById", userDetails.getId());
			model.put("issueUrl", "issuesResolved");
			model.put("OpenIssuesCount", page.getTotalElements());
			List<IssuesDto> pageList = IBL.prepare_Issues_ListDto(issuesService.find_Issues_Resolved_Status(pageNumber, userDetails));
			model.put("Issues", pageList);
			if (ResolvedIssues != null) {
				model.put("Count", ResolvedIssues.size());
			}
			model.put("Issues", IBL.prepare_Issues_ListDto(ResolvedIssues));
			model.put("configuration", configuration);

			model.put("SelectStatus", "RESOLVED");

	Object[] count = issuesService.countTotalIssues_AND_IssuesOpenStatus(userDetails);
			
			model.put("IssuesYou", (Long) count[0]);
			model.put("IssuesOpenCount", (Long) count[1]);
			model.put("IssuesCount", (Long) count[2]);
			try {
			model.put("overDueCount", IBL.prepareOverDueList(issuesService.find_Issues_Overdue_Status(pageNumber,userDetails, ft.format(currentDate))).size());
			}catch(Exception e) {
				model.put("overDueCount", 0);
				e.printStackTrace();
			}
			model.put("woCreatedCount", (Long) count[4]);
			model.put("seCreatedCount", (Long) count[5]);
			model.put("resolveCount", (Long) count[6]);
			model.put("rejectCount", (Long) count[7]);
			model.put("closeCount", (Long) count[8]);
			model.put("dseCount", (Long) count[9]);
			model.put("vBreakDownCount", (Long) count[10]);
			
			if((boolean) configuration.get("showDateWiseOverdue")) {
				Object[] countOverDue = issuesService.countOverdueIssues(userDetails);
				model.put("overDueCount", (Long) countOverDue[0]+(Long) countOverDue[1]+(Long) countOverDue[2]);
			}
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("OpenIssues", model);
	}

	/** This controller request in reject issues list */
	@RequestMapping(value = "/issuesReject/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView issuesReject(@ModelAttribute("command") Issues IsuessDto, BindingResult result, @PathVariable Integer pageNumber) {
		Map<String, Object> model = new HashMap<String, Object>();
		boolean 						operateAllIssuePermission 	= false;
		Collection<GrantedAuthority>	permission					= null;
		try {
			/** who Create this Issues get email id to user profile details */
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			permission					= userDetails.getGrantedAuthoritiesList();
			operateAllIssuePermission 	= permission.contains(new SimpleGrantedAuthority("OPERATE_ALL_ISSUES"));
			List<IssuesDto> RejectIssues = issuesService.find_Issues_ReJect_Status(pageNumber,userDetails);
			Page<Issues> page = issuesService.get_RejectIssues_All_pagination(pageNumber, userDetails.getCompany_id(), userDetails.getId(),IssuesTypeConstant.ISSUES_STATUS_REJECT);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			model.put("deploymentLog", page);
			model.put("beginIndex", begin);
			model.put("endIndex", end);
			model.put("currentIndex", current);
			model.put("createdById", userDetails.getId());
			model.put("issueUrl", "issuesReject");
			model.put("OpenIssuesCount", page.getTotalElements());

			List<IssuesDto> pageList = IBL.prepare_Issues_ListDto(issuesService.find_Issues_ReJect_Status(pageNumber,userDetails));
			model.put("Issues", pageList);
			if (RejectIssues != null) {
				model.put("Count", RejectIssues.size());
			}
			model.put("Issues", IBL.prepare_Issues_ListDto(RejectIssues));

			model.put("SelectStatus", "REJECT");
			model.put("configuration", configuration);
			model.put("operateAllIssuePermission", operateAllIssuePermission);
			model.put("createdById", userDetails.getId());
			model.put("email", userDetails.getEmail());

Object[] count = issuesService.countTotalIssues_AND_IssuesOpenStatus(userDetails);
			
			model.put("IssuesYou", (Long) count[0]);
			model.put("IssuesOpenCount", (Long) count[1]);
			model.put("IssuesCount", (Long) count[2]);
			try {
				model.put("overDueCount", IBL.prepareOverDueList(issuesService.find_Issues_Overdue_Status(pageNumber, userDetails, ft.format(currentDate))).size());
			}catch(Exception e) {
				model.put("overDueCount", 0);
			}
			model.put("woCreatedCount", (Long) count[4]);
			model.put("seCreatedCount", (Long) count[5]);
			model.put("resolveCount", (Long) count[6]);
			model.put("rejectCount", (Long) count[7]);
			model.put("closeCount", (Long) count[8]);
			model.put("dseCount", (Long) count[9]);
			model.put("vBreakDownCount", (Long) count[10]);
			
			if((boolean) configuration.get("showDateWiseOverdue")) {
				Object[] countOverDue = issuesService.countOverdueIssues(userDetails);
				model.put("overDueCount", (Long) countOverDue[0]+(Long) countOverDue[1]+(Long) countOverDue[2]);
			}
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("OpenIssues", model);
	}

	/** This controller request in close issues list */
	@RequestMapping(value = "/issuesClosed/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView issuesClose(@ModelAttribute("command") Issues IsuessDto, BindingResult result, @PathVariable Integer pageNumber) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			/** who Create this Issues get email id to user profile details */
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);

			List<IssuesDto> ClosedIssues = issuesService.find_Issues_Closed_Status(pageNumber, userDetails);
			Page<Issues> page = issuesService.get_CloseIssues_All_pagination(pageNumber, userDetails.getCompany_id(), userDetails.getId(), IssuesTypeConstant.ISSUES_STATUS_CLOSED);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			model.put("deploymentLog", page);
			model.put("beginIndex", begin);
			model.put("endIndex", end);
			model.put("currentIndex", current);
			model.put("createdById", userDetails.getId());
			model.put("issueUrl", "issuesClosed");
			model.put("OpenIssuesCount", page.getTotalElements());
			List<IssuesDto> pageList = IBL.prepare_Issues_ListDto(issuesService.find_Issues_Closed_Status(pageNumber,userDetails));
			model.put("Issues", pageList);
			if (ClosedIssues != null) {
				model.put("Count", ClosedIssues.size());
			}
			model.put("Issues", IBL.prepare_Issues_ListDto(ClosedIssues));

			model.put("SelectStatus", "CLOSED");
			model.put("configuration", configuration);

	Object[] count = issuesService.countTotalIssues_AND_IssuesOpenStatus(userDetails);
			
			model.put("IssuesYou", (Long) count[0]);
			model.put("IssuesOpenCount", (Long) count[1]);
			model.put("IssuesCount", (Long) count[2]);
			try {
			model.put("overDueCount", IBL.prepareOverDueList(issuesService.find_Issues_Overdue_Status(pageNumber, userDetails, ft.format(currentDate))).size());
			}catch(Exception e) {
				model.put("overDueCount", 0);
				e.printStackTrace();
			}
			model.put("woCreatedCount", (Long) count[4]);
			model.put("seCreatedCount", (Long) count[5]);
			model.put("resolveCount", (Long) count[6]);
			model.put("rejectCount", (Long) count[7]);
			model.put("closeCount", (Long) count[8]);
			model.put("dseCount", (Long) count[9]);
			model.put("vBreakDownCount", (Long) count[10]);
			
			if((boolean) configuration.get("showDateWiseOverdue")) {
				Object[] countOverDue = issuesService.countOverdueIssues(userDetails);
				model.put("overDueCount", (Long) countOverDue[0]+(Long) countOverDue[1]+(Long) countOverDue[2]);
			}
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("OpenIssues", model);
	}

	/** This controller request in search issues list */
	@RequestMapping(value = "/searchIssues", method = RequestMethod.POST)
	public ModelAndView searchIssues(@RequestParam("Search") final String Search, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			/** who Create this Issues get email id to user profile details */
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String UserEmail = auth.getName();
			model.put("Issues", IBL.prepare_Issues_ListDto(issuesService.find_Issues_Search(UserEmail, Search)));

			model.put("SelectStatus", "OPEN");
			model.put("IssuesCount", 0);
			model.put("IssuesOpenCount", 0);
			// model.put("IssuesOpenCount", issuesService.countStatus("OPEN"));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("OpenIssues", model);
	}

	/** This controller request in search issues list */
	@RequestMapping(value = "/searchYourIssues", method = RequestMethod.POST)
	public ModelAndView searchYourIssues(@RequestParam("Search") final String Search,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			/** who Create this Issues get email id to user profile details */
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String UserEmail = auth.getName();
			model.put("Issues", IBL.prepare_Issues_ListofDto(issuesService.find_Issues_SearchYour(UserEmail, Search)));
			model.put("SelectStatus", "OPEN");
			model.put("IssuesCount", 0);
			model.put("IssuesOpenCount", 0);
			// model.put("IssuesOpenCount", issuesService.countStatus("OPEN"));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("OpenIssues", model);
	}

	/** This controller request in add issues list */
	@RequestMapping(value = "/addIssues", method = RequestMethod.GET)
	public ModelAndView addIssues() {
		Map<String, Object> 		model 					= new HashMap<String, Object>();
		HashMap<String, Object>   	configuration			= null;
		HashMap<String, Object>   	gpsConfiguration		= null;
		CustomUserDetails			userDetails 			= null;
		boolean					 	customerIssue	      	= false;
		try {

			/**
			 * who Create this get email id to user profile details
			 */
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
			configuration	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			gpsConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			
			model.put("userName", userDetails.getFirstName());
			model.put("userId", userDetails.getId());
			model.put("gpsConfiguration", gpsConfiguration);
			model.put("companyId", userDetails.getCompany_id());
			model.put("vehiclegroup", vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id()));
			
			customerIssue	= (boolean) configuration.getOrDefault(IssuesConfigurationContant.SHOW_CUSTOMER_NAME, false);
			if(customerIssue == true) {
				model.put("IssueType", IssuesTypeConstant.getIssuesTypeList());  
			}
			else {
				model.put("IssueType", IssuesTypeConstant.getIssuesTypeListWithoutCustomerIssue());
			}
			
			model.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_ISSUES, (boolean)companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).get(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_ISSUES));
			model.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_ISSUES,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_ISSUES, false));
			model.put(IssuesConfigurationContant.SHOW_BREAK_DOWN_SELECTION, (boolean) configuration.getOrDefault(IssuesConfigurationContant.SHOW_BREAK_DOWN_SELECTION, false));
			model.put("configuration", configuration);
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("AddIssues", model);
	}

	/** This controller request in save issues list */
	@RequestMapping(value = "/saveVehicleIssues", method = RequestMethod.POST)
	public ModelAndView saveVehicleIssues(@Valid final IssuesDto issuesDto, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails				userDetails			= null;
		long							nextIssueNumber 	= 0;	
		List<ServiceReminderDto>		serviceList			= null;
		try {
			//issuesDto.setCUSTOMER_NAME(issuesDto.getCUSTOMER_NAME());
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Issues GET_issues = prepareIssues_Save(issuesDto);
			if (GET_issues != null) {

				try {
					IssuesSequenceCounter	sequenceCounter = issuesSequenceService.findNextIssuesSequence_Number(userDetails.getCompany_id());
					nextIssueNumber	= sequenceCounter.getNextVal();
					GET_issues.setCOMPANY_ID(userDetails.getCompany_id());
					GET_issues.setISSUES_NUMBER(nextIssueNumber);
					/**
					 * below object save to db in Vehicle issues Status in and VehicleTasks Values
					 */
					Issues Save_Issues = issuesService.registerNew_Issues(GET_issues);
					
					IssuesTasks WRTask = new IssuesTasks();
					/** Task new from to create */
					WRTask.setISSUES_TASK_STATUS_ID(IssuesTypeConstant.ISSUES_TASK_STATUS_CREATE);
					/** Task new from to create change status */
					WRTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_OPEN);

					
					UserProfileDto Orderingname = userProfileService.get_UserProfile_Issues_Details(userDetails.getEmail());
					/** Set Created by email Id */

					WRTask.setISSUES_CREATEBY_ID(Orderingname.getUserprofile_id());
					//WRTask.setISSUES_CREATEBY_NAME(userDetails.getFirstName());

					java.util.Date currentDateUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

					/** Set Created Current Date */
					WRTask.setISSUES_CREATED_DATE(toDate);

					WRTask.setISSUES(Save_Issues);
					WRTask.setCOMPANY_ID(userDetails.getCompany_id());

					/** save to IssuesTask values */
					issuesService.registerNew_IssuesTasks(WRTask);

					if (Save_Issues.getISSUES_VID() != null && Save_Issues.getISSUES_VID() != 0) {

						Integer currentODDMETER = vehicleService
								.updateCurrentOdoMeterGETVehicleToCurrentOdometer(issuesDto.getISSUES_VID());
						/**
						 * update the Current Odometer vehicle Databases tables
						 */
						if (Save_Issues.getISSUES_VID() != 0 && currentODDMETER != null && Save_Issues.getISSUES_ODOMETER() != null) {
							if (currentODDMETER < Save_Issues.getISSUES_ODOMETER()) {
								
								vehicleService.updateCurrentOdoMeter(Save_Issues.getISSUES_VID(),
										Save_Issues.getISSUES_ODOMETER(), userDetails.getCompany_id());
								// update current Odometer update Service
								// Reminder
								ServiceReminderService.updateCurrentOdometerToServiceReminder(
										Save_Issues.getISSUES_VID(), Save_Issues.getISSUES_ODOMETER(), userDetails.getCompany_id());
								
								serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(Save_Issues.getISSUES_VID(), userDetails.getCompany_id(), userDetails.getId());
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
										.prepareOdometerGetHistoryToIssues(Save_Issues);
								vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
								vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);

							}
						}
					}

					model.put("saveIssues", true);

					return new ModelAndView("redirect:/showIssues.in?Id="
							+ AESEncryptDecrypt.encryptBase64("" + Save_Issues.getISSUES_ID()) + "", model);

				} catch (Exception e) {
					LOGGER.error("TripSheet Page:", e);
					e.printStackTrace();

				}

			} else {
				model.put("alreadyIssues", true);
				return new ModelAndView("redirect:/addIssues.in", model);
			}

		} catch (NullPointerException e) {
			// return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			model.put("alreadyIssues", true);
			LOGGER.error("Issues Create Page:", e);
			throw new MailSentExistException();
		}
		return new ModelAndView("redirect:/addIssues.in", model);
	}

	/** This controller request in show issues list */
	@RequestMapping(value = "/showIssues", method = RequestMethod.GET)
	public ModelAndView showIssues(@RequestParam("Id") final String issues_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>     configuration		= null;
		boolean 						operateAllIssuePermission 	= false;
		Collection<GrantedAuthority>	permission					= null;
		VehicleTyreLayoutPositionDto vehicleTyreLayoutPositionDto = null;
		try {
			ValueObject valueObject = new ValueObject();
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			permission					= userDetails.getGrantedAuthoritiesList();
			operateAllIssuePermission 	= permission.contains(new SimpleGrantedAuthority("OPERATE_ALL_ISSUES"));
			if (issues_ID != null && issues_ID != "") {
				IssuesDto issue = issuesService.get_IssuesDetails(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID)), userDetails.getCompany_id());
				if (issue != null) {
					// Show the Issues
					IssuesDto issueDTO = IBL.Showing_Issues_Details(issue);
					if(issue.getISSUES_TYPE_ID() == IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN) {
						model.put("breakDownDetails", issuesService.getIssueBreakDownDetailsByIssueId(issue.getISSUES_ID()));
					}
					valueObject.put("position", "SP-0");
					valueObject.put("vid", issue.getISSUES_VID());
					valueObject.put("companyId", userDetails.getCompany_id());
					valueObject = vehicleTyreAssignmentService.getTyreDetailsOfPosition(valueObject);
					vehicleTyreLayoutPositionDto = (VehicleTyreLayoutPositionDto) valueObject.get("vehicleTyreLayoutPosition");
					if(vehicleTyreLayoutPositionDto != null && vehicleTyreLayoutPositionDto.getTYRE_ID() != null) {
						model.put("vehicleTyreLayoutPosition", vehicleTyreLayoutPositionDto);
						model.put("tyreId", true);
					}
					
					ArrayList<IssuesTasksDto> IssuestaskDtos = null;
					IssuestaskDtos = new ArrayList<IssuesTasksDto>();
					IssuesTasksDto task = null;
					List<IssuesTasksDto>   issuesTasksList		= issuesService.getIssuesTasksList(issueDTO.getISSUES_ID(), userDetails.getCompany_id());
					List<CommentType>  	   commentType			= commentTypeRepository.getCommentTypeList(userDetails.getCompany_id());
					
					if(issuesTasksList != null && !issuesTasksList.isEmpty()) {
						
						for (IssuesTasksDto taskDto : issuesTasksList) {
							task = new IssuesTasksDto();
							task.setISSUES_TASK_ID(taskDto.getISSUES_TASK_ID());
							task.setISSUES_CREATEBY_ID(taskDto.getISSUES_CREATEBY_ID());
							//task.setISSUES_CREATEBY_NAME(taskDto.getISSUES_CREATEBY_NAME());
							
							if (taskDto.getISSUES_CREATED_DATE_ON() != null) {
								task.setISSUES_CREATED_DATE(
										dateFormatonlyDateTime.format(taskDto.getISSUES_CREATED_DATE_ON()));
							}
							
							task.setISSUES_TASK_STATUS_ID(taskDto.getISSUES_TASK_STATUS_ID());
							task.setISSUES_TASK_STATUS(IssuesTypeConstant.getIssuesTaskStatusName(taskDto.getISSUES_TASK_STATUS_ID()));
							
							task.setISSUES_CHANGE_STATUS_ID(taskDto.getISSUES_CHANGE_STATUS_ID());
							task.setISSUES_CHANGE_STATUS(IssuesTypeConstant.getIssuesTaskChangeStatusName(taskDto.getISSUES_CHANGE_STATUS_ID()));
							
							task.setISSUES_REASON(taskDto.getISSUES_REASON());
							
							IssuestaskDtos.add(task);
							
						}
					}
					/* Sorting in decreasing order */
					// Sorting
					Collections.sort(IssuestaskDtos, new IssuesTasksDto());
					/* Sort statement */
					model.put("operateAllIssuePermission", operateAllIssuePermission);
					model.put("createdById", userDetails.getId());
					model.put("email", userDetails.getEmail());
					model.put("configuration", configuration);
					model.put("Isstask", IssuestaskDtos);
					model.put("Issues", issueDTO);
					model.put("commentType", commentType);
					model.put("Comment_Count", issuesService.countIssues_Comment(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID))));
					model.put("Document_Count", issuesService.countIssues_Document(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID))));
					model.put("Photo_Count", issuesService.countIssues_Photo(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID))));
					model.put("IssuesComment", IBL.prepare_IssuesComment_ListofDto(issuesService
							.Get_Issues_ID_Comment_Details(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID)), userDetails.getCompany_id())));
				
					model.put("IssuesDocument", IBL.Showing_IssuesDocument_Details(issuesService
							.Get_Issues_ID_Document_Details(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID)), userDetails.getCompany_id())));
					
					model.put("IssuesPhoto", IBL.Showing_IssuesPhoto_Details(issuesService
							.Get_Issues_ID_Photo_Details(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID)), userDetails.getCompany_id())));
					
					model.put("currentUseremail", userDetails.getEmail());
					model.put("companyId", userDetails.getCompany_id());
					model.put("issueEncId", issues_ID);
				} else {
					return new ModelAndView("redirect:/NotFound.in");
				}

			} else {
				return new ModelAndView("redirect:/NotFound.in");
			}

		} catch (NullPointerException e) {
			LOGGER.error("Issues Err:", e);
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
			return new ModelAndView("redirect:/NotFound.in");
		}
		return new ModelAndView("ShowIssues", model);
	}

	/** delete Issues */
	@RequestMapping(value = "/deleteIssues", method = RequestMethod.GET)
	public ModelAndView deleteDriver(@RequestParam("Id") final String issues_ID, final HttpServletRequest request) {
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (issues_ID != null && issues_ID != "") {
				Issues checkAssign = issuesService.check_Issues_Assign_Details(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID)), userDetails);
				
				if (checkAssign != null && checkAssign.getISSUES_STATUS_ID() == IssuesTypeConstant.ISSUES_CHANGE_STATUS_OPEN) {
					
					if(checkAssign.getISSUES_LABELS_ID() == IssuesTypeConstant.ISSUE_TYPE_VEHICLE) {
						IssuesDto 	dto					= issuesService.get_IssuesDetails(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID)), userDetails.getCompany_id());
						VehicleDto 	CheckVehicleStatus 	= vehicleService.Get_Vehicle_Current_Status_TripSheetID(dto.getISSUES_VID());
						
						if(CheckVehicleStatus.getVehicle_Odometer().equals(dto.getISSUES_ODOMETER())) { 
							VehicleOdometerHistory odometerHistoryLsit	= vehicleOdometerHistoryService.getVehicleOdometerHistoryByVidExceptCurrentEntry(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID)),dto.getISSUES_VID(), userDetails.getCompany_id());
							if(odometerHistoryLsit != null ) {
								if(odometerHistoryLsit.getVehicle_Odometer() < dto.getISSUES_ODOMETER()) { //  found either pre entry or post entry
									vehicleService.updateCurrentOdoMeter(dto.getISSUES_VID(), odometerHistoryLsit.getVehicle_Odometer(), userDetails.getCompany_id());
									vehicleOdometerHistoryService.deleteVehicleOdometerHistory(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID)), userDetails.getCompany_id());
								}
							
							}
							
						}
					}
					java.util.Date 		currentDateUpdate 	= new java.util.Date();
					Timestamp 			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
					issuesService.delete_Issues(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID)), userDetails.getCompany_id(),userDetails.getId(),toDate);
				} else {
					return new ModelAndView("redirect:/issuesOpen/1?notAssign=true");
				}

			} else {
				return new ModelAndView("redirect:/NotFound.in");
			}

			return new ModelAndView("redirect:/issuesOpen/1?deletesuccess=true");
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/issuesOpen/1.in?deletedanger=true");
		}
	}

	@RequestMapping(value = "/editIssues", method = RequestMethod.GET)
	public ModelAndView editIssues(@RequestParam("Id") final String issues_ID, final HttpServletRequest request) {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		HashMap<String, Object>     configuration		= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			
			if (issues_ID != null && issues_ID != "") {
				IssuesDto issueDTO = IBL.Edit_Issues_Details(
						issuesService.get_IssuesDetails(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID)), userDetails.getCompany_id()));
				model.put("Issues", issueDTO);
				if(issueDTO.getISSUES_VID() != null) {
					model.put("vehicle", vehicleService.Get_Vehicle_Current_Status_TripSheetID(issueDTO.getISSUES_VID()));
					model.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_ISSUES, (boolean)companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).get(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_ISSUES));
					model.put(IssuesConfigurationContant.SHOW_BREAK_DOWN_SELECTION, (boolean) configuration.getOrDefault(IssuesConfigurationContant.SHOW_BREAK_DOWN_SELECTION, false));
				}
			} else {
				return new ModelAndView("redirect:/issuesOpen?notfound=true");
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("EditIssues", model);
	}

	/** This controller request in save issues list */
	@RequestMapping(value = "/updateIssues", method = RequestMethod.POST)
	public @ResponseBody ModelAndView updateIssues(@Valid final IssuesDto issuesDto, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails				userDetails		= null;
		List<ServiceReminderDto>		serviceList		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Issues GET_issues = prepareIssues_Update(issuesDto);
			GET_issues.setCOMPANY_ID(userDetails.getCompany_id());

			if (GET_issues != null) {
				switch (GET_issues.getISSUE_TYPE_ID()) {

				case IssuesTypeConstant.ISSUE_TYPE_VEHICLE:

					try {

						issuesService.update_Vehicle_Issues(GET_issues);

						if (GET_issues.getISSUES_VID() != null && GET_issues.getISSUES_VID() != 0) {

							Integer currentODDMETER = vehicleService
									.updateCurrentOdoMeterGETVehicleToCurrentOdometer(issuesDto.getISSUES_VID());
							/**
							 * update the Current Odometer vehicle Databases tables
							 */
							if (GET_issues.getISSUES_VID() != 0 && currentODDMETER != null) {
								if (currentODDMETER < GET_issues.getISSUES_ODOMETER()) {
									
									vehicleService.updateCurrentOdoMeter(GET_issues.getISSUES_VID(),
											GET_issues.getISSUES_ODOMETER(), userDetails.getCompany_id());
									// update current Odometer update Service
									// Reminder
									ServiceReminderService.updateCurrentOdometerToServiceReminder(
											GET_issues.getISSUES_VID(), GET_issues.getISSUES_ODOMETER(), userDetails.getCompany_id());
									
									serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(GET_issues.getISSUES_VID(), userDetails.getCompany_id(), userDetails.getId());
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
											.prepareOdometerGetHistoryToIssues(GET_issues);
									vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
									vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);

								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;

				case IssuesTypeConstant.ISSUE_TYPE_DRIVER:

					try {
						issuesService.update_Driver_Issues(GET_issues);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
					
				case IssuesTypeConstant.ISSUE_TYPE_CUSTOMER:	
					try {
						issuesService.update_Customer_Issues(GET_issues);
					}catch(Exception e){
						e.printStackTrace();
					}
					
				case IssuesTypeConstant.ISSUE_TYPE_OTHER:

					try {
						issuesService.update_Other_Issues(GET_issues);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case IssuesTypeConstant.ISSUE_TYPE_REFUND:

					try {
						issuesService.update_Other_Issues(GET_issues);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				} // close switch case

			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			model.put("alreadyIssues", true);
			LOGGER.error("Issues Create Page:", e);
			throw new MailSentExistException();
		}
		return new ModelAndView("redirect:/showIssues?Id="
				+ AESEncryptDecrypt.encryptBase64("" + issuesDto.getISSUES_ID()) + "&updateSuccess=true");
	}

	/** Reject Issues in Status on Reject reason */
	@RequestMapping(value = "/saveRejectIssues", method = RequestMethod.POST)
	public ModelAndView saveRejectIssues(@RequestParam("Id") final Long issues_ID,
			@RequestParam("Status") final short Status,
			@RequestParam("ISSUES_REJECT_REASON") final String issues_Reject_Reason, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		HashMap<String, Object>   	configuration			    = null;
		boolean                     allowedMobNotification      = false;
		Issues 							checkAssign 	= null;
		boolean						operateAllIssue		= false;
		UserProfileDto				profile 			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			allowedMobNotification  = (boolean) configuration.getOrDefault(IssuesConfigurationContant.ALLOWED_MOB_NOTIFICATION, false);
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			if (issues_ID != null && issues_ID != 0) {

				/**
				 * who Create this Issues get email id to user profile details
				 */

				if(permission.contains(new SimpleGrantedAuthority("OPERATE_ALL_ISSUES"))) {
					operateAllIssue = true;
				}
				checkAssign = issuesService.check_Issues_Assign_Details(issues_ID, userDetails);
				if (checkAssign != null || operateAllIssue) {
					try {
						if(checkAssign == null) {
							profile = userProfileService.getUserProfileByUser_id(userDetails.getId());
						}else {
							profile = userProfileService.getUserProfileByUser_id(Long.parseLong(checkAssign.getCREATEDBYID()+""));
							
						}
						Issues Save_Issues = new Issues();
						Save_Issues.setISSUES_ID(issues_ID);
						IssuesTasks WRTask = new IssuesTasks();
						/** Task new from to create */
						WRTask.setISSUES_TASK_STATUS_ID(Status);
						/** Task new from to create change status */
						WRTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_REJECT);
						UserProfileDto Orderingname = userProfileService.get_UserProfile_Issues_Details(userDetails.getEmail());
						/** Set Created by email Id */
						WRTask.setISSUES_CREATEBY_ID(Orderingname.getUserprofile_id());
						//WRTask.setISSUES_CREATEBY_NAME(Orderingname.getFirstName());
						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
						/** Set Created Current Date */
						WRTask.setISSUES_CREATED_DATE(toDate);
						WRTask.setISSUES(Save_Issues);

						WRTask.setISSUES_REASON(issues_Reject_Reason);
						WRTask.setCOMPANY_ID(userDetails.getCompany_id());
						/** save to IssuesTask values */
						issuesService.registerNew_IssuesTasks(WRTask);
						
						if((checkAssign != null && checkAssign.getCREATEDBYID() != userDetails.getId())) {
							issuesService.check_Issues_Update_Details(issues_ID, profile.getUser_email(),userDetails);
							issuesService.saveIssuestask(WRTask,userDetails);
							if(allowedMobNotification){
								issuesService.sendNotificationWhenIssueIsRejected(issues_ID,userDetails.getId(),userDetails.getCompany_id());
							}
						}else {
							issuesService.update_Reject_Issues_Status(IssuesTypeConstant.ISSUES_STATUS_REJECT, userDetails.getId(), toDate, issues_ID, userDetails.getCompany_id());
						}

						return new ModelAndView("redirect:/showIssues?Id="
								+ AESEncryptDecrypt.encryptBase64("" + issues_ID) + "&RejectSuccess=true", model);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					return new ModelAndView("redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID)
							+ "&NotAssign=true", model);
				}
			} else {
				return new ModelAndView("redirect:/issuesOpen?notfound=true");
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID) + "",
				model);
	}

	/** Close Issues in Status on close reason */
	@RequestMapping(value = "/saveCloseIssues", method = RequestMethod.POST)
	public ModelAndView saveCloseIssues(@RequestParam("Id") final Long issues_ID,
			@RequestParam("Status") final short Status,
			@RequestParam("ISSUES_CLOSE_REASON") final String issues_Close_Reason, @ModelAttribute("command") IssuesCommentDto dto, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails				userDetails		= null;
		Issues 							checkAssign 	= null;
		boolean						operateAllIssue		= false;
		HashMap<String, Object>   	configuration			= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			
			if (issues_ID != null && issues_ID != 0) {
				if(permission.contains(new SimpleGrantedAuthority("OPERATE_ALL_ISSUES"))) {
					operateAllIssue = true;
				}
				checkAssign = issuesService.check_Issues_Assign_Details(issues_ID, userDetails);
				/** This close only Create person only */
				if (checkAssign != null || operateAllIssue) {
					try {
						
						if(checkAssign != null && (boolean) configuration.getOrDefault("issueAnalysis", false) && checkAssign.getISSUE_TYPE_ID() == IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN) {
							IssueAnalysisDto 	issueAnalysisDto = issueAnalysisService.getIssueAnalysisDetailsByIssueId(issues_ID);
							if(issueAnalysisDto == null ) {
								return new ModelAndView("redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID)
								+ "&anaysisNotFound=true");
							}
						
						}
						
						Issues Save_Issues = new Issues();
						Save_Issues.setISSUES_ID(issues_ID);
						IssuesTasks WRTask = new IssuesTasks();
						/** Task new from to create */
						WRTask.setISSUES_TASK_STATUS_ID(Status);
						/** Task new from to create change status */
						WRTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_CLOSED);
						UserProfileDto Orderingname = userProfileService.get_UserProfile_Issues_Details(userDetails.getEmail());
						/** Set Created by email Id */
						WRTask.setISSUES_CREATEBY_ID(Orderingname.getUserprofile_id());
						//WRTask.setISSUES_CREATEBY_NAME(Orderingname.getFirstName());
						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
						/** Set Created Current Date */
						WRTask.setISSUES_CREATED_DATE(toDate);
						WRTask.setISSUES(Save_Issues);

						WRTask.setISSUES_REASON(issues_Close_Reason);
						WRTask.setCOMPANY_ID(userDetails.getCompany_id());
						/** save to IssuesTask values */
						issuesService.registerNew_IssuesTasks(WRTask);

						issuesService.update_Close_Issues_Status(IssuesTypeConstant.ISSUES_STATUS_CLOSED, userDetails.getId(), toDate, issues_ID, userDetails.getCompany_id());
						if((boolean) configuration.getOrDefault("closureWithRemark",false)) {
						IssuesComment saveComment = new IssuesComment();
						saveComment.setISSUES_ID(issues_ID);
						saveComment.setISSUE_COMMENT(dto.getISSUE_COMMENT());
						saveComment.setCREATEDBYID(userDetails.getId());
						saveComment.setAssignee(dto.getAssignee());
						saveComment.setDriverId(dto.getDriverId());
						saveComment.setMarkForDelete(false);
						saveComment.setCREATED_DATE(toDate);
						saveComment.setCOMPANY_ID(userDetails.getCompany_id());
						saveComment.setISSUE_TITLE(dto.getISSUE_TITLE());
						issuesService.add_Issues_Comment_Details(saveComment);
						}
						
						return new ModelAndView("redirect:/showIssues?Id="
								+ AESEncryptDecrypt.encryptBase64("" + issues_ID) + "&closeSuccess=true", model);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					return new ModelAndView("redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID)
							+ "&NotAssign=true", model);
				}

			} else {
				return new ModelAndView("redirect:/issuesOpen?notfound=true");
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID) + "",
				model);
	}

	/** Reopen Issues in Status on close reason */
	@RequestMapping(value = "/saveReopenIssues", method = RequestMethod.POST)
	public ModelAndView issuesReopen(@RequestParam("Id") final Long issues_ID,
			@RequestParam("Status") final short Status,
			@RequestParam("ISSUES_REOPEN_REASON") final String issues_Reopen_Reason, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		Issues 							checkAssign 	= null;
		boolean						operateAllIssue		= false;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			if (issues_ID != null && issues_ID != 0) {

				/**
				 * who Create this Issues get email id to user profile details
				 */
				String ReopenEmail = userDetails.getEmail();

				if(permission.contains(new SimpleGrantedAuthority("OPERATE_ALL_ISSUES"))) {
					operateAllIssue = true;
				}
				checkAssign = issuesService.check_Issues_Assign_Details(issues_ID, userDetails);
				if (checkAssign != null || operateAllIssue) {
					try {

						Issues Save_Issues = new Issues();
						Save_Issues.setISSUES_ID(issues_ID);
						IssuesTasks WRTask = new IssuesTasks();
						/** Task new from to create */
						WRTask.setISSUES_TASK_STATUS_ID(Status);
						/** Task new from to create change status */
						WRTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_OPEN);
						UserProfileDto Orderingname = userProfileService.get_UserProfile_Issues_Details(ReopenEmail);
						/** Set Created by email Id */
						WRTask.setISSUES_CREATEBY_ID(Orderingname.getUserprofile_id());
						//WRTask.setISSUES_CREATEBY_NAME(Orderingname.getFirstName());
						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
						/** Set Created Current Date */
						WRTask.setISSUES_CREATED_DATE(toDate);
						WRTask.setISSUES(Save_Issues);

						WRTask.setISSUES_REASON(issues_Reopen_Reason);
						WRTask.setCOMPANY_ID(userDetails.getCompany_id());
						/** save to IssuesTask values */
						issuesService.registerNew_IssuesTasks(WRTask);

						issuesService.update_Reopen_Issues_Status(IssuesTypeConstant.ISSUES_STATUS_OPEN, userDetails.getId(), toDate, issues_ID, userDetails.getCompany_id());

						return new ModelAndView("redirect:/showIssues?Id="
								+ AESEncryptDecrypt.encryptBase64("" + issues_ID) + "&ReopenSuccess=true", model);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					return new ModelAndView("redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID)
							+ "&NotAssign=true", model);
				}
			} else {
				return new ModelAndView("redirect:/issuesOpen?notfound=true");
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID) + "",
				model);
	}

	/** Reopen Issues in Status on close reason */
	@RequestMapping(value = "/saveResolveIssues", method = RequestMethod.POST)
	public ModelAndView saveResolveIssues(@RequestParam("Id") final Long issues_ID,
			@RequestParam("Status") final short Status,
			@RequestParam("ISSUES_RESOLVE_REASON") final String issues_Resolve_Reason,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		HashMap<String, Object>   	configuration			= null;
		boolean                     allowedMobNotification  = false;
		Issues 							checkAssign 	= null;
		boolean						operateAllIssue		= false;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			allowedMobNotification  = (boolean) configuration.getOrDefault(IssuesConfigurationContant.ALLOWED_MOB_NOTIFICATION, false);
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList(); 
			if (issues_ID != null && issues_ID != 0) {

				
				String ReSolveEmail = userDetails.getEmail();

				if(permission.contains(new SimpleGrantedAuthority("OPERATE_ALL_ISSUES"))) {
					operateAllIssue	= true;
				}
				checkAssign = issuesService.check_Issues_Assign_Details(issues_ID, userDetails);
				IssuesDto issue = issuesService.get_IssuesDetails(issues_ID, userDetails.getCompany_id());
				
				if (checkAssign != null || operateAllIssue) {
					try {

						Issues Save_Issues = new Issues();
						Save_Issues.setISSUES_ID(issues_ID);
						IssuesTasks WRTask = new IssuesTasks();
						/** Task new from to create */
						WRTask.setISSUES_TASK_STATUS_ID(Status);
						/** Task new from to create change status */
						WRTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_RESOLVED);
						UserProfileDto Orderingname = userProfileService.get_UserProfile_Issues_Details(ReSolveEmail);
						/** Set Created by email Id */
						WRTask.setISSUES_CREATEBY_ID(Orderingname.getUserprofile_id());
						//WRTask.setISSUES_CREATEBY_NAME(Orderingname.getFirstName());
						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
						/** Set Created Current Date */
						WRTask.setISSUES_CREATED_DATE(toDate);
						WRTask.setISSUES(Save_Issues);

						WRTask.setISSUES_REASON(issues_Resolve_Reason);
						WRTask.setCOMPANY_ID(userDetails.getCompany_id());
						/** save to IssuesTask values */
						issuesService.registerNew_IssuesTasks(WRTask);

						issuesService.update_ReSolved_Issues_Status(IssuesTypeConstant.ISSUES_STATUS_RESOLVED, userDetails.getId(), toDate, issues_ID, userDetails.getCompany_id());
						if(issue!=null && allowedMobNotification){
							issuesService.sendNotificationWhenIssueResolved(issues_ID,userDetails.getCompany_id(),userDetails.getCompany_id());
						}
						return new ModelAndView("redirect:/showIssues?Id="
								+ AESEncryptDecrypt.encryptBase64("" + issues_ID) + "&ResolvedSuccess=true", model);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					return new ModelAndView("redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID)
							+ "&NotAssign=true", model);
				}
			} else {
				return new ModelAndView("redirect:/issuesOpen?notfound=true");
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID) + "",
				model);
	}

	/** Show Issues via Work Order value in work Orders */
	@RequestMapping(value = "/issuesViaWorkOrder", method = RequestMethod.GET)
	public ModelAndView issuesViaWorkOrder(@RequestParam("Id") final Long issues_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		HashMap<String, Object> configuration		= null;
		HashMap<String, Object> gpsConfiguration	= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG); 
			if (issues_ID != null && issues_ID != 0) {

			
				Issues checkAssign = issuesService.check_Issues_Assign_Details(issues_ID, userDetails);
				if (checkAssign != null) {
					try {
						IssuesDto issues = issuesService.get_IssuesDetails(issues_ID, userDetails.getCompany_id());
						model.put("Issues", IBL.Showing_Issues_Details(issues));
						model.put("IssuesAESID", AESEncryptDecrypt.encryptBase64("" + issues_ID));
						model.put("configuration", configuration);
						model.put("gpsConfiguration", gpsConfiguration);
						model.put("companyId", userDetails.getCompany_id());
						model.put("partLocationPermission", partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));
						model.put("vehicle", vehicleService.Get_Vehicle_Current_Status_TripSheetID(issues.getISSUES_VID()));
						model.put("validateOdometerInWorkOrder", 
								companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_WORKORDER, false));

						
						return new ModelAndView("workOrderIssues", model);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					return new ModelAndView("redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID)
							+ "&NotAssign=true", model);
				}
			} else {
				return new ModelAndView("redirect:/issuesOpen?notfound=true");
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID) + "",
				model);
	}
	
	
	/** Show Issues via Service Entries */  //latest
	//latest Start
	@RequestMapping(value = "/issuesServiceEntries", method = RequestMethod.GET)
	public ModelAndView issuesServiceEntries(@RequestParam("Id") final Long issues_ID, final HttpServletRequest request) {
		Map<String, Object> 	model 				= null;
		CustomUserDetails		userDetails			= null;
		HashMap<String, Object> configuration		= null;
		HashMap<String, Object> gpsConfiguration	= null;
		
		try {
			model 					= new HashMap<String, Object>();
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			gpsConfiguration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			
			if (issues_ID != null && issues_ID != 0) {
			
				Issues checkAssign = issuesService.check_Issues_Assign_Details(issues_ID, userDetails);
				
				if (checkAssign != null) {
					try {
						IssuesDto issues = issuesService.get_IssuesDetails(issues_ID, userDetails.getCompany_id());
						model.put("Issues", IBL.Showing_Issues_Details(issues));
						model.put("IssuesAESID", AESEncryptDecrypt.encryptBase64("" + issues_ID));
						model.put("configuration", configuration);
						model.put("gpsConfiguration", gpsConfiguration);
						model.put("companyId", userDetails.getCompany_id());
						model.put("partLocationPermission", partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));
						model.put("vehicle", vehicleService.Get_Vehicle_Current_Status_TripSheetID(issues.getISSUES_VID()));
						model.put("validateOdometerInWorkOrder", 
								companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_WORKORDER, false));
						model.put("userName", userDetails.getFirstName());
						model.put("userId", userDetails.getId());
						model.put("vehicleRegistration", issues.getISSUES_VEHICLE_REGISTRATION());
						model.put("ISSUES_VID", issues.getISSUES_VID());
						
						
						return new ModelAndView("serviceEntriesIssues", model);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					return new ModelAndView("redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID)
							+ "&NotAssign=true", model);
				}
			} else {
				return new ModelAndView("redirect:/issuesOpen?notfound=true");
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID) + "",
				model);
	}
	//latest Stop
	
	/** This value of Issues save in db to get UI prepare Model to save 
	 * @throws Exception */
	public Issues prepareIssues_Save(IssuesDto issuesDto) throws Exception {

		Issues issue = new Issues();

		issue.setISSUE_TYPE_ID(issuesDto.getISSUES_TYPE_ID());
		switch (issuesDto.getISSUES_TYPE_ID()) {

		case IssuesTypeConstant.ISSUE_TYPE_VEHICLE:

			try {
				if (issuesDto.getISSUES_VID() != null && issuesDto.getISSUES_VID() != 0) {
					issue.setISSUES_VID(issuesDto.getISSUES_VID());
					issue.setGPS_ODOMETER(issuesDto.getGPS_ODOMETER());

					/** Search Fuel_ vehicle_id to get details */
					//Vehicle vehiclename = (vehicleService.getVehicel_Details_Fuel_entries(issuesDto.getISSUES_VID()));
					/** Search vehicle to get registration_no, vehicle_group */
					//issue.setISSUES_VEHICLE_REGISTRATION(vehiclename.getVehicle_registration());
					//issue.setISSUES_VEHICLE_GROUP(vehiclename.getVehicle_Group());
				} else {
					issue.setISSUES_VID(0);
				}
				/** issues Odameter */
				issue.setISSUES_ODOMETER(issuesDto.getISSUES_ODOMETER());
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (issuesDto.getISSUES_DRIVER_ID() != null && issuesDto.getISSUES_DRIVER_ID() != 0) {
					issue.setISSUES_DRIVER_ID(issuesDto.getISSUES_DRIVER_ID());
					//Driver driverDto = driverService.getDriver_Details_Issues_entries(issuesDto.getISSUES_DRIVER_ID());
					//issue.setISSUES_DRIVER_NAME(driverDto.getDriver_firstname());
				} else {
					issue.setISSUES_DRIVER_ID(0);
					//issue.setISSUES_DRIVER_NAME(issuesDto.getISSUES_DRIVER_NAME());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// close vehicle issues
			break;

		case IssuesTypeConstant.ISSUE_TYPE_DRIVER:

			try {
				if (issuesDto.getISSUES_DRIVER_ID() != null && issuesDto.getISSUES_DRIVER_ID() != 0) {
					issue.setISSUES_DRIVER_ID(issuesDto.getISSUES_DRIVER_ID());
					//Driver driverDto = driverService.getDriver_Details_Issues_entries(issuesDto.getISSUES_DRIVER_ID());
					//issue.setISSUES_DRIVER_NAME(driverDto.getDriver_firstname());
				} else {
					issue.setISSUES_DRIVER_ID(0);
					//issue.setISSUES_DRIVER_NAME(issuesDto.getISSUES_DRIVER_NAME());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// close driver issues
			break;
			
		case IssuesTypeConstant.ISSUE_TYPE_CUSTOMER:

			try {
				if (issuesDto.getCUSTOMER_NAME() != null && issuesDto.getCUSTOMER_NAME() != "") {
					issue.setCUSTOMER_NAME(issuesDto.getCUSTOMER_NAME());
					//Driver driverDto = driverService.getDriver_Details_Issues_entries(issuesDto.getISSUES_DRIVER_ID());
					//issue.setISSUES_DRIVER_NAME(driverDto.getDriver_firstname());
				} else {
					issue.setCUSTOMER_NAME("");
					//issue.setISSUES_DRIVER_NAME(issuesDto.getISSUES_DRIVER_NAME());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// close driver issues
			break;	
			

		default:

			try {
				if (issuesDto.getISSUES_BRANCH_ID() != null && issuesDto.getISSUES_BRANCH_ID() != 0) {

					issue.setISSUES_BRANCH_ID(issuesDto.getISSUES_BRANCH_ID());
					/**
					 * This value search Branch_id to get Branch_name in Branch table to issues
					 * table
					 */
					//Branch branch = branchService.get_Issues_BranchID_getBranchName(issuesDto.getISSUES_BRANCH_ID());

					//issue.setISSUES_BRANCH_NAME(branch.getBranch_name());
				} else {
					issue.setISSUES_BRANCH_ID(0);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (issuesDto.getISSUES_DEPARTNMENT_ID() != null && issuesDto.getISSUES_DEPARTNMENT_ID() != 0) {

					issue.setISSUES_DEPARTNMENT_ID(issuesDto.getISSUES_DEPARTNMENT_ID());
				} else {
					issue.setISSUES_DEPARTNMENT_ID(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// close Other issues
			break;
		} // close switch case

		try {
			if (issuesDto.getISSUES_REPORTED_DATE() != null) {
				String start_time = "00:00";
				if(issuesDto.getIssue_start_time() != null && issuesDto.getIssue_start_time() != "") {
					start_time	= issuesDto.getIssue_start_time();
				}
				java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(issuesDto.getISSUES_REPORTED_DATE(), start_time);
				java.sql.Date Reported_Date = new java.sql.Date(date.getTime());
				issue.setISSUES_REPORTED_DATE(Reported_Date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		issue.setISSUES_SUMMARY(issuesDto.getISSUES_SUMMARY());
		issue.setISSUES_DESCRIPTION(issuesDto.getISSUES_DESCRIPTION());
		issue.setISSUES_LABELS_ID(issuesDto.getISSUES_LABELS_ID());
		issue.setISSUES_REPORTED_BY_ID(issuesDto.getISSUES_REPORTED_BY_ID());
	/*he*/	issue.setCUSTOMER_NAME(issuesDto.getCUSTOMER_NAME());
		issue.setISSUES_ASSIGN_TO(issuesDto.getISSUES_ASSIGN_TO());

		if (issuesDto.getISSUES_ASSIGN_TO() != null) {
			String username[] = issuesDto.getISSUES_ASSIGN_TO().split(",");
			String assigntoname = "";
			for (String userAssignto : username) {
				UserProfileDto Orderingname = userProfileService.get_UserProfile_Issues_Details(userAssignto);
				assigntoname += "" + Orderingname.getFirstName() + " , ";
			}
			issue.setISSUES_ASSIGN_TO_NAME(assigntoname);
		}
		issue.setISSUES_STATUS_ID(IssuesTypeConstant.ISSUES_STATUS_OPEN);

		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		/** Set Status in Issues */
		issue.setMarkForDelete(false);
		issue.setCREATEDBYID(userDetails.getId());
		issue.setLASTMODIFIEDBYID(userDetails.getId());


		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		/** Set Created Current Date */
		issue.setCREATED_DATE(toDate);
		issue.setLASTUPDATED_DATE(toDate);

		issue.setISSUES_WORKORDER_DATE(toDate);
		issue.setISSUES_SE_DATE(toDate);
		issue.setVEHICLE_GROUP_ID(issuesDto.getVEHICLE_GROUP_ID());
		//VehicleGroup	vehicleGroup	= vehicleGroupService.getVehicleGroupByID(issuesDto.getVEHICLE_GROUP_ID());
		//issue.setISSUES_VEHICLE_GROUP(vehicleGroup.getvGroup());
		//issue.setISSUES_TYPE(IssuesTypeConstant.getIssueTypeName(issue.getISSUE_TYPE_ID()).toUpperCase());
		
		return issue;
	}

	/** This value of Issues update in db to get UI prepare Model to save 
	 * @throws Exception */
	public Issues prepareIssues_Update(IssuesDto issuesDto) throws Exception {

		Issues issue = new Issues();

		issue.setISSUES_ID(issuesDto.getISSUES_ID());
		issue.setISSUE_TYPE_ID(issuesDto.getISSUES_TYPE_ID());
		//issue.setISSUES_TYPE(IssuesTypeConstant.getIssueTypeName(issuesDto.getISSUES_TYPE_ID()));
		if(issuesDto.getVEHICLE_GROUP_ID() != 0) {
			issue.setVEHICLE_GROUP_ID(issuesDto.getVEHICLE_GROUP_ID());
		//	issue.setISSUES_VEHICLE_GROUP(vehicleGroupService.getVehicleGroupByID(issuesDto.getVEHICLE_GROUP_ID()).getvGroup());
		}

		switch (issuesDto.getISSUES_TYPE_ID()) {

		case IssuesTypeConstant.ISSUE_TYPE_VEHICLE:

			if (issuesDto.getISSUES_VID() != null && issuesDto.getISSUES_VID() != 0) {
				issue.setISSUES_VID(issuesDto.getISSUES_VID());
				/** issues Odameter */
				issue.setISSUES_ODOMETER(issuesDto.getISSUES_ODOMETER());
				issue.setGPS_ODOMETER(issuesDto.getGPS_ODOMETER());
			}

			// vehicle Change Driver in edit
			try {
				if (issuesDto.getISSUES_DRIVER_ID() != null && issuesDto.getISSUES_DRIVER_ID() != 0) {
					issue.setISSUES_DRIVER_ID(issuesDto.getISSUES_DRIVER_ID());
					//Driver driverDto = driverService.getDriver_Details_Issues_entries(issuesDto.getISSUES_DRIVER_ID());
				//	issue.setISSUES_DRIVER_NAME(driverDto.getDriver_firstname());
				} else {
					issue.setISSUES_DRIVER_ID(0);
				//	issue.setISSUES_DRIVER_NAME(issuesDto.getISSUES_DRIVER_NAME());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// close vehicle issues
			break;

		case IssuesTypeConstant.ISSUE_TYPE_OTHER:

			try {
				if (issuesDto.getISSUES_BRANCH_ID() != null && issuesDto.getISSUES_BRANCH_ID() != 0) {

					issue.setISSUES_BRANCH_ID(issuesDto.getISSUES_BRANCH_ID());
					/**
					 * This value search Branch_id to get Branch_name in Branch table to issues
					 * table
					 */
					//Branch branch = branchService.get_Issues_BranchID_getBranchName(issuesDto.getISSUES_BRANCH_ID());

				//	issue.setISSUES_BRANCH_NAME(branch.getBranch_name());
				} else {
					issue.setISSUES_BRANCH_ID(0);
				//  issue.setISSUES_BRANCH_NAME(issuesDto.getISSUES_BRANCH_NAME());

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (issuesDto.getISSUES_DEPARTNMENT_ID() != null && issuesDto.getISSUES_DEPARTNMENT_ID() != 0) {

					issue.setISSUES_DEPARTNMENT_ID(issuesDto.getISSUES_DEPARTNMENT_ID());
					/**
					 * This value search department_id to get department name in department table to
					 * issues table
					 */
//					Department department = departmentService
//							.get_Issues_DepartmentByID_to_DepartmentName(issuesDto.getISSUES_DEPARTNMENT_ID());
				//	issue.setISSUES_DEPARTNMENT_NAME(department.getDepart_name());
				} else {
					issue.setISSUES_DEPARTNMENT_ID(0);
				//	issue.setISSUES_DEPARTNMENT_NAME(issuesDto.getISSUES_DEPARTNMENT_NAME());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// close Other issues
			break;
		case IssuesTypeConstant.ISSUE_TYPE_REFUND:

			try {
				if (issuesDto.getISSUES_BRANCH_ID() != null && issuesDto.getISSUES_BRANCH_ID() != 0) {

					issue.setISSUES_BRANCH_ID(issuesDto.getISSUES_BRANCH_ID());
					/**
					 * This value search Branch_id to get Branch_name in Branch table to issues
					 * table
					 */
					//Branch branch = branchService.get_Issues_BranchID_getBranchName(issuesDto.getISSUES_BRANCH_ID());

				//	issue.setISSUES_BRANCH_NAME(branch.getBranch_name());
				} else {
					issue.setISSUES_BRANCH_ID(0);
				//	issue.setISSUES_BRANCH_NAME(issuesDto.getISSUES_BRANCH_NAME());

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (issuesDto.getISSUES_DEPARTNMENT_ID() != null && issuesDto.getISSUES_DEPARTNMENT_ID() != 0) {

					issue.setISSUES_DEPARTNMENT_ID(issuesDto.getISSUES_DEPARTNMENT_ID());
					/**
					 * This value search department_id to get department name in department table to
					 * issues table
					 */
//					Department department = departmentService
//							.get_Issues_DepartmentByID_to_DepartmentName(issuesDto.getISSUES_DEPARTNMENT_ID());
				//	issue.setISSUES_DEPARTNMENT_NAME(department.getDepart_name());
				} else {
					issue.setISSUES_DEPARTNMENT_ID(0);
				//	issue.setISSUES_DEPARTNMENT_NAME(issuesDto.getISSUES_DEPARTNMENT_NAME());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// close Other issues
			break;

		} // close switch case

		try {
			if (issuesDto.getISSUES_REPORTED_DATE() != null) {
				/** Reported_Date converted String to date Format */
				java.util.Date date = dateFormat.parse(issuesDto.getISSUES_REPORTED_DATE());
				java.sql.Date Reported_Date = new Date(date.getTime());
				issue.setISSUES_REPORTED_DATE(Reported_Date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		issue.setISSUES_SUMMARY(issuesDto.getISSUES_SUMMARY());
		issue.setISSUES_DESCRIPTION(issuesDto.getISSUES_DESCRIPTION());
		issue.setISSUES_LABELS_ID(issuesDto.getISSUES_LABELS_ID());
		issue.setISSUES_REPORTED_BY_ID(issuesDto.getISSUES_REPORTED_BY_ID());
		issue.setISSUES_ASSIGN_TO(issuesDto.getISSUES_ASSIGN_TO());
		issue.setISSUES_STATUS_ID(issuesDto.getISSUES_STATUS_ID());

		String username[] = issuesDto.getISSUES_ASSIGN_TO().split(",");

		String assigntoname = "";

		for (String userAssignto : username) {
			UserProfileDto Orderingname = userProfileService.get_UserProfile_Issues_Details(userAssignto);
			assigntoname += "" + Orderingname.getFirstName() + " , ";
		}

		issue.setISSUES_ASSIGN_TO_NAME(assigntoname);

	CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		/** Set Status in Issues */
		issue.setMarkForDelete(false);
		/** Set Created by email Id */

		//issue.setLASTMODIFIEDBY(userDetails.getEmail());
		issue.setLASTMODIFIEDBYID(userDetails.getId());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		/** Set Created Current Date */
		issue.setLASTUPDATED_DATE(toDate);

		return issue;
	}

	/** This controller request in Issues Comment Show Page issues list */
	@RequestMapping(value = "/issuesComment", method = RequestMethod.GET)
	public ModelAndView issuesComment(@RequestParam("Id") final String issues_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		IssuesDto					issues			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			issues	= issuesService
					.Show_Header_Issues_Details(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID)), userDetails);
			if(issues != null) {
				model.put("Issues", IBL.Showing_Issues_Details(issues));
				
				model.put("IssuesComment", IBL.prepare_IssuesComment_ListofDto(issuesService
						.Get_Issues_ID_Comment_Details(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID)), userDetails.getCompany_id())));
				
				model.put("Comment_Count", issuesService.countIssues_Comment(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID))));
				model.put("Document_Count", issuesService.countIssues_Document(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID))));
				model.put("Photo_Count", issuesService.countIssues_Photo(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID))));
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
			return new ModelAndView("redirect:/NotFound.in");
		}
		return new ModelAndView("CommentIssues", model);
	}

	/** This controller request in Issues Comment Show Page issues list */
	@RequestMapping(value = "/saveIssuesComment", method = RequestMethod.POST)
	public ModelAndView saveIssuesComment(@RequestParam("ISSUES_ID") final Long issues_ID,
			final IssuesComment issuesComment, final HttpServletRequest request) {
		try {
			/** who Create this Issues get email id to user profile details */
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			IssuesComment saveComment = new IssuesComment();

			saveComment.setISSUES_ID(issues_ID);
			saveComment.setISSUE_TITLE(issuesComment.getISSUE_TITLE());
			saveComment.setCOMMENT_TYPE_ID(issuesComment.getCOMMENT_TYPE_ID());
			saveComment.setISSUE_COMMENT(issuesComment.getISSUE_COMMENT());

			try {
				saveComment.setCREATEDBYID(userDetails.getId());
			} catch (Exception e) {
			}

			//saveComment.setCREATED_EMAIL(userDetails.getEmail());
			saveComment.setMarkForDelete(false);
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			/** Set Created Current Date */
			saveComment.setCREATED_DATE(toDate);
			saveComment.setCOMPANY_ID(userDetails.getCompany_id());

			issuesService.add_Issues_Comment_Details(saveComment);

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
			return new ModelAndView(
					"redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID) + "&danger=true");
		}
		return new ModelAndView(
				"redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID) + "&saveComment=true");
	}

	/** This controller request in Issues Comment Delete the value */
	@RequestMapping(value = "/deleteIssuesComment", method = RequestMethod.GET)
	public ModelAndView deleteComment(@RequestParam("Id") final String issues_ID,
			@RequestParam("CID") final String comment_ID, final IssuesComment issuesComment,
			final HttpServletRequest request) {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			issuesService.Delete_Issues_Comment_Details(Long.parseLong(AESEncryptDecrypt.decryptBase64(comment_ID)), userDetails.getCompany_id());

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
			return new ModelAndView("redirect:/showIssues?Id=" + issues_ID + "&danger=true");
		}
		return new ModelAndView("redirect:/showIssues?Id=" + issues_ID + "&deleteComment=true");
	}

	/** This controller request in Issues Comment Show Page issues list */
	@RequestMapping(value = "/issuesDocument", method = RequestMethod.GET)
	public ModelAndView issuesDocument(@RequestParam("Id") final String issues_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		IssuesDto					issues			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			issues	= issuesService
					.Show_Header_Issues_Details(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID)), userDetails);
			if(issues != null) {
				
				model.put("Issues", IBL.Showing_Issues_Details(issues));
				
				model.put("IssuesDocument", IBL.Showing_IssuesDocument_Details(issuesService
						.Get_Issues_ID_Document_Details(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID)), userDetails.getCompany_id())));
				
				model.put("Comment_Count", issuesService.countIssues_Comment(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID))));
				model.put("Document_Count", issuesService.countIssues_Document(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID))));
				model.put("Photo_Count", issuesService.countIssues_Photo(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID))));
			}


		} catch (NullPointerException e) {
			LOGGER.error("Issues Page:", e);
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
			return new ModelAndView("redirect:/NotFound.in");
		}
		return new ModelAndView("DocumentIssues", model);
	}

	/** This controller request in Issues Document Show Page issues list */
	@RequestMapping(value = "/saveIssuesDocument")
	public String uploadDriverDocument() {
		return "saveIssuesDocument";
	}

	@RequestMapping(value = "/saveIssuesDocument", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveDriverDocumentUpload(@RequestParam("ISSUES_ID") final Long issues_ID,
			IssuesDocument Document, @RequestParam("INPUT_FILE") MultipartFile file) throws Exception {

		if (!file.isEmpty()) {
			try {
				CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				org.fleetopgroup.persistence.document.IssuesDocument IssuesDocument = new org.fleetopgroup.persistence.document.IssuesDocument();

				IssuesDocument.setISSUE_ID(issues_ID);
				IssuesDocument.setISSUE_DOCUMENTNAME(Document.getISSUE_DOCUMENTNAME());

				byte[] bytes = file.getBytes();
				IssuesDocument.setISSUE_FILENAME(file.getOriginalFilename());
				IssuesDocument.setISSUE_CONTENT(bytes);
				IssuesDocument.setISSUE_CONTENTTYPE(file.getContentType());

				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				/** Set Created Current Date */
				IssuesDocument.setISSUE_UPLOADDATE(toDate);
				IssuesDocument.setCOMPANY_ID(userDetails.getCompany_id());
				IssuesDocument.setCREATEDBYID(userDetails.getId());

				issuesService.add_Issues_Document_Details(IssuesDocument);

			} catch (NullPointerException e) {
				return new ModelAndView("redirect:/NotFound.in");
			} catch (IOException e) {
				return new ModelAndView("redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID)
						+ "&danger=true");
			}

			return new ModelAndView("redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID)
					+ "&saveDocument=true");
		} else {
			// messages
			return new ModelAndView("redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID)
					+ "&emptyDocument=true");

		}

	}

	/** This controller request in Issues Document Download Document */
	@RequestMapping("/download/issueDocument/{Id}")
	public String download(@PathVariable("Id") final String ISSUE_DOCUMENTID, final HttpServletResponse response) {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			org.fleetopgroup.persistence.document.IssuesDocument file = issuesService
					.Get_Issues_Document(Long.parseLong(AESEncryptDecrypt.decryptBase64(ISSUE_DOCUMENTID)), userDetails.getCompany_id());
			try {
				if (ISSUE_DOCUMENTID == null) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
				} else {
					if (file != null) {
						if (file.getISSUE_CONTENT() != null) {
							response.setHeader("Content-Disposition",
									"inline;filename=\"" + file.getISSUE_FILENAME() + "\"");
							OutputStream out = response.getOutputStream();
							response.setContentType(file.getISSUE_CONTENTTYPE());
							response.getOutputStream().write(file.getISSUE_CONTENT());
							out.flush();
							out.close();

						}
					}
				}

			} catch (NullPointerException e) {
				return "redirect:/NotFound.in";
			} catch (IOException e) {
				return ("redirect:/issuesOpen?danger=true");
			}
		} catch (Exception e) {
			return ("redirect:/issuesOpen?danger=true");
		}
		return null;
	}

	/** This controller request in Issues Document Delete the value */
	@RequestMapping(value = "/deleteIssuesDocument", method = RequestMethod.GET)
	public ModelAndView deleteDocument(@RequestParam("Id") final String issues_ID,
			@RequestParam("DID") final String document_ID, final HttpServletRequest request) {
		try {
			/** who Create this Issues get email id to user profile details */
			/*
			 * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			 * String name = auth.getName();
			 */
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			issuesService.Delete_Issues_Document_Details(Long.parseLong(AESEncryptDecrypt.decryptBase64(document_ID)), userDetails.getCompany_id());

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
			return new ModelAndView("redirect:/showIssues?Id=" + issues_ID + "&danger=true");
		}
		return new ModelAndView("redirect:/showIssues?Id=" + issues_ID + "&deleteDocument=true");
	}

	/** This controller request in Issues Comment Show Page issues list */
	@RequestMapping(value = "/issuesPhoto", method = RequestMethod.GET)
	public ModelAndView issuesPhoto(@RequestParam("Id") final String issues_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		IssuesDto					issues			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			issues	= issuesService
					.Show_Header_Issues_Details(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID)), userDetails);
			if(issues != null) {
				model.put("Issues", IBL.Showing_Issues_Details(issues));
				
				model.put("IssuesPhoto", IBL.Showing_IssuesPhoto_Details(issuesService
						.Get_Issues_ID_Photo_Details(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID)), userDetails.getCompany_id())));
				
				model.put("Comment_Count", issuesService.countIssues_Comment(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID))));
				model.put("Document_Count", issuesService.countIssues_Document(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID))));
				model.put("Photo_Count", issuesService.countIssues_Photo(Long.parseLong(AESEncryptDecrypt.decryptBase64(issues_ID))));
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("PhotoIssues", model);
	}

	/** This controller request in Issues Photo Show Page issues list */
	@RequestMapping(value = "/saveIssuesPhoto", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveDriverPhoto(@RequestParam("ISSUES_ID") final Long issues_ID,
			IssuesPhoto issuePhoto, @RequestParam("INPUT_FILE") MultipartFile file) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			if (!file.isEmpty()) {
				CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				org.fleetopgroup.persistence.document.IssuesPhoto issuePho = new org.fleetopgroup.persistence.document.IssuesPhoto();
				try {
					
					issuePho.setISSUE_ID(issues_ID);

					issuePho.setISSUE_PHOTONAME(issuePhoto.getISSUE_PHOTONAME());

					byte[] bytes = file.getBytes();
					issuePho.setISSUE_FILENAME(file.getOriginalFilename());

					issuePho.setISSUE_CONTENT(ByteImageConvert.scale(bytes, 350, 350));
					issuePho.setISSUE_CONTENTTYPE(file.getContentType());
				} catch (IOException e) {

				}

				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				/** Set Created Current Date */
				issuePho.setISSUE_UPLOADDATE(toDate);
				issuePho.setCOMPANY_ID(userDetails.getCompany_id());
				issuePho.setCREATEDBYID(userDetails.getId());
				issuesService.add_Issues_Photo_Details(issuePho);

				model.put("addDriverPhoto", true);
				return new ModelAndView("redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID)
						+ "&savePhoto=true");
			} else {
				return new ModelAndView("redirect:/showIssues?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID)
						+ "&emptyPhoto=true");

			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			return new ModelAndView(
					"redirect:/issuesPhoto?Id=" + AESEncryptDecrypt.encryptBase64("" + issues_ID) + "&danger=true");
		}
	}

	/**
	 * This controller request in Issues Photo Show list
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/getIssuePhoto/{ISSUE_PHOTOID}")
	public String getImage(@PathVariable("ISSUE_PHOTOID") final String issue_photoid,
			final HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		org.fleetopgroup.persistence.document.IssuesPhoto filePhoto = issuesService
				.Get_Issues_Photo(Long.parseLong(AESEncryptDecrypt.decryptBase64(issue_photoid)), userDetails.getCompany_id());
		try {
			if (issue_photoid == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				if (filePhoto != null) {
					if (filePhoto.getISSUE_CONTENT() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + filePhoto.getISSUE_FILENAME() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(filePhoto.getISSUE_CONTENTTYPE());
						response.getOutputStream().write(filePhoto.getISSUE_CONTENT());
						out.flush();
						out.close();
					}
				}
			}
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {

		}
		return null;
	}

	/** This controller request in Issues Photo Delete the value */
	@RequestMapping(value = "/deleteIssuesPhoto", method = RequestMethod.GET)
	public ModelAndView deletePhoto(@RequestParam("Id") final String issues_ID,
			@RequestParam("PID") final String photo_ID, final IssuesPhoto issuesPhoto,
			final HttpServletRequest request) {
		try {
			
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			issuesService.Delete_Issues_Photo_Details(Long.parseLong(AESEncryptDecrypt.decryptBase64(photo_ID)), userDetails.getCompany_id());

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
			return new ModelAndView("redirect:/showIssues?Id=" + issues_ID + "&danger=true");
		}
		return new ModelAndView("redirect:/showIssues?Id=" + issues_ID + "&deletePhoto=true");
	}

	/*****************************************************************************************************
	 ***************** Vehicle To Trip Sheet Details **********************
	 *****************************************************************************************************/

	@RequestMapping(value = "/VehicleIssuesDetails", method = RequestMethod.GET)
	public ModelAndView VehicleIssuesDetails(@ModelAttribute("command") Vehicle vehicle_id,
			ServiceReminderDto serviceReminderDto, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		VehicleDto					vehicle			= null;
		boolean 						operateAllIssuePermission 	= false;
		Collection<GrantedAuthority>	permission					= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			permission					= userDetails.getGrantedAuthoritiesList();
			operateAllIssuePermission 	= permission.contains(new SimpleGrantedAuthority("OPERATE_ALL_ISSUES"));
			vehicle  = vehicleService.Get_Vehicle_Header_Details(vehicle_id.getVid());
			if(vehicle != null) {
				
				vehicle = VBL.prepare_Vehicle_Header_Show(vehicle);
				model.put("vehicle", vehicle);
				// show the driver list in all
				
				/** who Create this Issues get email id to user profile details */
				model.put("Issues", IBL.prepare_Issues_IN_Vehicle_ListofDto(
						issuesService.find_Issues_Open_andVehicle_ID_Status(userDetails.getEmail(), vehicle_id.getVid(), userDetails.getCompany_id(), userDetails.getId())));
				
				Object[] count = vehicleService
						.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(vehicle_id.getVid());
				model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(vehicle_id.getVid(), userDetails.getCompany_id()));
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
				model.put("dseCount", (Long) count[12]);
				model.put("createdById", userDetails.getId());
				model.put("email", userDetails.getEmail());
				model.put("operateAllIssuePermission", operateAllIssuePermission);
			}

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);

		}finally {
			userDetails		= null;
			vehicle			= null;
		}

		return new ModelAndView("Show_Vehicle_Issue", model);
	}

	/***************************************************************************************************************************************
	 ************** Get Vehicle List drop down less Loading to Search
	 ***************************************************************************************************************************************/

	@RequestMapping(value = "/getIssuesVehicleList", method = RequestMethod.POST)
	public void getIssuesVehicleList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<VehicleDto> addresses = new ArrayList<VehicleDto>();
		List<Vehicle> vehicle = vehicleService.SearchOnlyVehicleNAME(term);
		if (vehicle != null && !vehicle.isEmpty()) {
			for (Vehicle add : vehicle) {
				VehicleDto wadd = new VehicleDto();
				wadd.setVid(add.getVid());
				wadd.setVehicle_registration(add.getVehicle_registration());
				addresses.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(addresses));
	}

	@RequestMapping(value = "/getIssuesDriverList", method = RequestMethod.POST)
	public void getIssuesDriverList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<DriverDto> Driver = new ArrayList<DriverDto>();

		List<Driver> driver = driverService.SearchOnlyDriverNAME(term);
		if (driver != null && !driver.isEmpty()) {
			for (Driver add : driver) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_firstname(add.getDriver_firstname());
				wadd.setDriver_Lastname(add.getDriver_Lastname());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				wadd.setDriver_mobnumber(add.getDriver_mobnumber());
				wadd.setDriver_fathername(add.getDriver_fathername());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(Driver));
	}

	@RequestMapping(value = "/getIssuesBranch", method = RequestMethod.POST)
	public void getIssuesBranch(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<BranchDto> Driver = new ArrayList<BranchDto>();
		List<Branch> branch = branchService.Search_Olny_Branch_name(term, userDetails);
		if (branch != null && !branch.isEmpty()) {
			for (Branch add : branch) {
				BranchDto wadd = new BranchDto();
				wadd.setBranch_id(add.getBranch_id());
				wadd.setBranch_name(add.getBranch_name());
				wadd.setBranch_code(add.getBranch_code());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(Driver));
	}

	@RequestMapping(value = "/getIssuesDepartnment", method = RequestMethod.POST)
	public void getIssuesDepartnment(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<DepartmentDto> Driver = new ArrayList<DepartmentDto>();
		List<Department> driver = departmentService.Search_Only_Department_name(term, userDetails);
		if (driver != null && !driver.isEmpty()) {
			for (Department add : driver) {
				DepartmentDto wadd = new DepartmentDto();
				wadd.setDepart_id(add.getDepart_id());
				wadd.setDepart_name(add.getDepart_name());
				wadd.setDepart_code(add.getDepart_code());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(Driver));
	}

	@RequestMapping(value = "/getIssuesVehicleOdoMerete", method = RequestMethod.GET)
	public void getIssuesVehicleOdoMerete(@RequestParam(value = "FuelvehicleID", required = true) Integer vehicleID,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Vehicle oddmeter = vehicleService.getVehicle_DropDown_Issues_ADD_entries(vehicleID);
		VehicleDto wadd = new VehicleDto();
		if (oddmeter != null) {
			HashMap<String, Object> 	gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			if((boolean) gpsConfiguration.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
				ValueObject	object	= new ValueObject();
				
				object.put("companyId", userDetails.getCompany_id());
				object.put("vehicleId", vehicleID);
			ValueObject	gpsObject	=	vehicleGPSDetailsService.getVehicleGPSData(object);
				if(gpsObject != null) {
						wadd.setOdometerReading(gpsObject.getBoolean("isOdometerReading"));
					if(gpsObject.containsKey(GPSConstant.VEHICLE_TOTAL_KM_NAME)) {
						wadd.setGpsOdameter(gpsObject.getDouble(GPSConstant.VEHICLE_TOTAL_KM_NAME, 0));
						wadd.setGpsLocation(gpsObject.getString(GPSConstant.GPS_LOCATION_NAME));
					}
				}
			}
			wadd.setVehicle_Odometer(oddmeter.getVehicle_Odometer());
			wadd.setVehicle_ExpectedOdameter(oddmeter.getVehicle_ExpectedOdameter());
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(wadd));
	}

	/** get UserProfile Drop down List user search */
	@RequestMapping(value = "/getUserEmailId_Assignto", method = RequestMethod.POST)
	public void getUserEmailId_Assignto(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<UserProfileDto> user = new ArrayList<UserProfileDto>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserProfileDto> userName = userProfileService.SearchUserEmail_id_and_Name(term , userDetails.getCompany_id());
		if (userName != null && !userName.isEmpty()) {
			for (UserProfileDto add : userName) {
				UserProfileDto wadd = new UserProfileDto();

				wadd.setUser_email(add.getUser_email());
				wadd.setFirstName(add.getFirstName());
				wadd.setLastName(add.getLastName());
				wadd.setUser_id(add.getUser_id());
				user.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(user));
	}
	
	@RequestMapping(value = "/issuesSE_Created/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView issuesSECreated(@ModelAttribute("command") Issues IsuessDto, BindingResult result, @PathVariable Integer pageNumber) {
			Map<String, Object> 		model 			= null;
			CustomUserDetails			userDetails 	= null;	
			List<IssuesDto> 			ResolvedIssues	= null;
		
		try {
			model			= new HashMap<String, Object>();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);

			ResolvedIssues = issuesService.find_Issues_SE_Created_Status(pageNumber,userDetails,IssuesTypeConstant.ISSUES_STATUS_SE_CREATED);

			Page<Issues> page = issuesService.get_SECreatedIssues_All_pagination(pageNumber, userDetails.getCompany_id(), userDetails.getId(),IssuesTypeConstant.ISSUES_STATUS_SE_CREATED);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			model.put("deploymentLog", page);
			model.put("beginIndex", begin);
			model.put("endIndex", end);
			model.put("currentIndex", current);
			model.put("createdById", userDetails.getId());
			model.put("issueUrl", "issuesSE_Created");
			model.put("SECreatedIssuesCount", page.getTotalElements());
			List<IssuesDto> pageList = IBL.prepare_Issues_ListDto(issuesService.find_Issues_SE_Created_Status(pageNumber,userDetails,IssuesTypeConstant.ISSUES_STATUS_SE_CREATED));
			model.put("Issues", pageList);
			if (ResolvedIssues != null) {
				model.put("Count", ResolvedIssues.size());
			}
			model.put("Issues", IBL.prepare_Issues_ListDto(ResolvedIssues));

			model.put("SelectStatus", "SE_CREATED");
			model.put("configuration", configuration);

	Object[] count = issuesService.countTotalIssues_AND_IssuesOpenStatus(userDetails);
			
			model.put("IssuesYou", (Long) count[0]);
			model.put("IssuesOpenCount", (Long) count[1]);
			model.put("IssuesCount", (Long) count[2]);
			try {
			model.put("overDueCount", IBL.prepareOverDueList(issuesService.find_Issues_Overdue_Status(pageNumber,userDetails, ft.format(currentDate))).size());
			}catch(Exception e) {
				model.put("overDueCount", 0);
				e.printStackTrace();
			}
			model.put("woCreatedCount", (Long) count[4]);
			model.put("seCreatedCount", (Long) count[5]);
			model.put("resolveCount", (Long) count[6]);
			model.put("rejectCount", (Long) count[7]);
			model.put("closeCount", (Long) count[8]);
			model.put("dseCount", (Long) count[9]);
			model.put("vBreakDownCount", (Long) count[10]);
			
			if((boolean) configuration.get("showDateWiseOverdue")) {
				Object[] countOverDue = issuesService.countOverdueIssues(userDetails);
				model.put("overDueCount", (Long) countOverDue[0]+(Long) countOverDue[1]+(Long) countOverDue[2]);
			}
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("OpenIssues", model);
	}
	
	@RequestMapping(value = "/issuesDSE_Created/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView issuesDSECreated(@ModelAttribute("command") Issues IsuessDto, BindingResult result, @PathVariable Integer pageNumber) {
			Map<String, Object> 		model 			= null;
			CustomUserDetails			userDetails 	= null;	
			List<IssuesDto> 			ResolvedIssues	= null;
		
		try {
			model			= new HashMap<String, Object>();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);

			ResolvedIssues = issuesService.find_Issues_SE_Created_Status(pageNumber, userDetails,IssuesTypeConstant.ISSUES_STATUS_DSE_CREATED);

			Page<Issues> page = issuesService.get_DSE_Created_Issues_All_pagination(pageNumber, userDetails.getCompany_id(), userDetails.getId(),IssuesTypeConstant.ISSUES_STATUS_DSE_CREATED);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			model.put("deploymentLog", page);
			model.put("beginIndex", begin);
			model.put("endIndex", end);
			model.put("currentIndex", current);
			model.put("createdById", userDetails.getId());
			model.put("issueUrl", "issuesDSE_Created");
			model.put("OpenIssuesCount", page.getTotalElements());
			List<IssuesDto> pageList = IBL.prepare_Issues_ListDto(issuesService.find_Issues_SE_Created_Status(pageNumber, userDetails,IssuesTypeConstant.ISSUES_STATUS_DSE_CREATED));
			model.put("Issues", pageList);
			if (ResolvedIssues != null) {
				model.put("Count", ResolvedIssues.size());
			}
			model.put("Issues", IBL.prepare_Issues_ListDto(ResolvedIssues));

			model.put("SelectStatus", "DSE_CREATED");
			model.put("configuration", configuration);

	Object[] count = issuesService.countTotalIssues_AND_IssuesOpenStatus(userDetails);
			
			model.put("IssuesYou", (Long) count[0]);
			model.put("IssuesOpenCount", (Long) count[1]);
			model.put("IssuesCount", (Long) count[2]);
			try {
			model.put("overDueCount", IBL.prepareOverDueList(issuesService.find_Issues_Overdue_Status(pageNumber,userDetails, ft.format(currentDate))).size());
			}catch(Exception e) {
				model.put("overDueCount", 0);
				e.printStackTrace();
			}
			model.put("woCreatedCount", (Long) count[4]);
			model.put("seCreatedCount", (Long) count[5]);
			model.put("resolveCount", (Long) count[6]);
			model.put("rejectCount", (Long) count[7]);
			model.put("closeCount", (Long) count[8]);
			model.put("dseCount", (Long) count[9]);
			model.put("vBreakDownCount", (Long) count[10]);
			
			if((boolean) configuration.get("showDateWiseOverdue")) {
				Object[] countOverDue = issuesService.countOverdueIssues(userDetails);
				model.put("overDueCount", (Long) countOverDue[0]+(Long) countOverDue[1]+(Long) countOverDue[2]);
			}
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("OpenIssues", model);
	}
	
	
	
	@RequestMapping(value = "/vehicleBreakDownIssuesList/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView vehicleBreakDownIssuesList(@ModelAttribute("command") Issues IsuessDto, BindingResult result, @PathVariable Integer pageNumber) {
			Map<String, Object> 		model 			= null;
			CustomUserDetails			userDetails 	= null;	
			List<IssuesDto> 			ResolvedIssues	= null;
		
		try {
			model			= new HashMap<String, Object>();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);

			ResolvedIssues = issuesService.findIssueListByIssueType(userDetails,IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN);
			
			Page<Issues> page = issuesService.get_VehicleBreakDownIssues_All_pagination(pageNumber, userDetails.getCompany_id(), userDetails.getId(),IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			model.put("deploymentLog", page);
			model.put("beginIndex", begin);
			model.put("endIndex", end);
			model.put("currentIndex", current);
			model.put("createdById", userDetails.getId());
			model.put("OpenIssuesCount", page.getTotalElements());
			List<IssuesDto> pageList = IBL.prepare_Issues_ListDto(issuesService.findIssueListByIssueType(userDetails,IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN));
			model.put("Issues", pageList);
			if (ResolvedIssues != null) {
				model.put("Count", ResolvedIssues.size());
			}
			model.put("Issues", IBL.prepare_Issues_ListDto(ResolvedIssues));

			model.put("SelectStatus", "Break_Down");
			model.put("configuration", configuration);

	Object[] count = issuesService.countTotalIssues_AND_IssuesOpenStatus(userDetails);
			
			model.put("IssuesYou", (Long) count[0]);
			model.put("IssuesOpenCount", (Long) count[1]);
			model.put("IssuesCount", (Long) count[2]);
			try {
			model.put("overDueCount", IBL.prepareOverDueList(issuesService.find_Issues_Overdue_Status(pageNumber, userDetails, ft.format(currentDate))).size() );
			}catch(Exception e) {
				model.put("overDueCount", 0);
				e.printStackTrace();
			}
			model.put("woCreatedCount", (Long) count[4]);
			model.put("seCreatedCount", (Long) count[5]);
			model.put("resolveCount", (Long) count[6]);
			model.put("rejectCount", (Long) count[7]);
			model.put("closeCount", (Long) count[8]);
			model.put("dseCount", (Long) count[9]);
			model.put("vBreakDownCount", (Long) count[10]);
			
			Object[] breakDownCount = issuesService.getBreakDownIssuesCount(userDetails);
			
			model.put("breakDownOpen",breakDownCount[0]);
			model.put("breakDownReject",breakDownCount[4]);
			model.put("breakDownResolve",breakDownCount[3]);
			
			if((boolean) configuration.get("showDateWiseOverdue")) {
				Object[] countOverDue = issuesService.countOverdueIssues(userDetails);
				model.put("overDueCount", (Long) countOverDue[0]+(Long) countOverDue[1]+(Long) countOverDue[2]);
			}
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
		}
		return new ModelAndView("IssuesType", model);
	}


}

/* EMI REMInder TEST CODE */

/*
 * class Calculate { double arr[], Install_Amt;
 * 
 * public double calc_install_amt(double principal_amt, double interest_rate,
 * int no_of_payments, int tenure, double Residual_Val) { double Install_Amt, P,
 * i, RV; int t, n; P = principal_amt; i = interest_rate; t = no_of_payments; n
 * = tenure; RV = Residual_Val; double Cal1, Val1, Val2;
 * 
 * Cal1 = (i / 100) / (double) t; Val1 = (P * Cal1) - ((RV * Cal1) / Math.pow((1
 * + Cal1), (double) n)); Val2 = (1 - (1 / Math.pow((1 + Cal1), (double) n)));
 * Install_Amt = Val1 / Val2;
 * 
 * return Install_Amt; }
 * 
 * public double calc_install_amt(double principal_amt, double interest_rate,
 * int tenure) { double Install_Amt = 0.0, P = 0.0, i = 0.0; int n = 0; P =
 * principal_amt; i = interest_rate; n = tenure;
 * 
 * double Irate = 0.0, rateMount = 0.0, Cal1 = 0.0, Cal2 = 0.0, Val1 = 0.0, Val2
 * = 0.0;
 * 
 * rateMount = i / 12; Irate = (rateMount / 100); Cal1 = Math.pow((1 + Irate),
 * (double) n); Cal2 = Cal1 - 1; Val1 = (P * Irate); Val2 = (Cal1 / Cal2);
 * Install_Amt = round((Val1 * Val2), 2);
 * 
 * return Install_Amt; }
 * 
 * public void repayment_schedule(double principal_amt, double interest_rate,
 * int no_of_payments, int tenure, double Residual_Val) { double P, i, RV; int
 * t, n; P = principal_amt; i = interest_rate;
 * 
 * t = no_of_payments; n = tenure; RV = Residual_Val; arr = new double[n];
 * DecimalFormat df = new DecimalFormat("###.##"); Install_Amt =
 * calc_install_amt(P, i, n); //
 * Install_Amt=Math.round(Install_Amt*100.0)/100.0; i = i / 100; double In, OPn,
 * Pn, OPnn; OPn = P; for (int j = 0; j < n; j++) { In = OPn * (i) * (1 /
 * (double) t); // In=Math.round(In*100.0)/100.0; Pn = Install_Amt - In; //
 * Pn=Math.round(Pn*100.0)/100.0; OPnn = OPn - Pn; //
 * OPnn=Math.round(OPnn*100.0)/100.0; arr[j] = OPn; System.out.print((j + 1) +
 * "\t"); System.out.print(df.format(OPn) + "\t");
 * System.out.print(df.format(Pn) + "\t"); System.out.print(df.format(In) +
 * "\t"); System.out.print(df.format(Install_Amt) + "\t");
 * System.out.print(df.format(Math.abs(OPnn)) + "\t"); //
 * 
 * } }
 * 
 * public void repayment_schedule(double principal_amt, double interest_rate,
 * int tenure) { double P, i; int n; P = principal_amt; i = interest_rate; n =
 * tenure;
 * 
 * arr = new double[n]; DecimalFormat df = new DecimalFormat("###.##");
 * Install_Amt = calc_install_amt(P, i, n); //
 * Install_Amt=Math.round(Install_Amt*100.0)/100.0; i = 100; double In, OPn, Pn,
 * OPnn; OPn = P; for (int j = 0; j < n; j++) { In = (OPn / i); //
 * In=Math.round(In*100.0)/100.0; Pn = Install_Amt - In; //
 * Pn=Math.round(Pn*100.0)/100.0; OPnn = OPn - Pn; //
 * OPnn=Math.round(OPnn*100.0)/100.0; arr[j] = OPn; System.out.print((j + 1) +
 * "\t"); System.out.print(df.format(Install_Amt) + "\t");
 * System.out.print(df.format(In) + "\t"); System.out.print(df.format(Pn) +
 * "\t"); // System.out.print(df.format(OPn) + "\t");
 * System.out.print(df.format(Math.abs(OPnn)) + "\t"); //
 * 
 * } }
 * 
 * public void repayment_schedule_1(double principal_amt, double interest_rate,
 * int no_of_payments, int tenure, double Residual_Val, int num) { double i; int
 * t;
 * 
 * i = interest_rate; i = i / 100;
 * 
 * t = no_of_payments;
 * 
 * DecimalFormat df = new DecimalFormat("###.##"); // Install_Amt
 * =calc_install_amt(P,i,t,n,RV); double In = 0, OPn, Pn, OPnn;
 * 
 * OPn = arr[num - 1]; In = OPn * (i) * (1 / (double) t); Pn = Install_Amt - In;
 * OPnn = OPn - Pn; System.out.print((num) + "\t");
 * System.out.print(df.format(OPn) + "\t"); System.out.print(df.format(Pn) +
 * "\t"); System.out.print(df.format(In) + "\t");
 * System.out.print(df.format(Install_Amt) + "\t");
 * System.out.print(df.format(Math.abs(OPnn)) + "\t");
 * 
 * }
 * 
 * public static double round(double value, int places) { if (places < 0) throw
 * new IllegalArgumentException();
 * 
 * long factor = (long) Math.pow(10, places); value = value * factor; long tmp =
 * Math.round(value); return (double) tmp / factor; }
 * 
 * }
 */
