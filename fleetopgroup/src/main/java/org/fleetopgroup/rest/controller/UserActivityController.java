package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.UserActivityConstant;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.serviceImpl.IUserActivityReportService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class UserActivityController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Autowired IUserActivityReportService userActivityReportService;
	
	@Autowired IWorkOrdersService workOrdersService;
	
	@Autowired IUserProfileService userProfileService;
	
	
	
	@RequestMapping("/UA")
	public ModelAndView DriverReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("UA", model);
	}
	
	@GetMapping(value = "/viewUserActivity")
	public ModelAndView vehicleModel(final HttpServletRequest request) throws Exception {
		ModelAndView 				map 				= null;
		CustomUserDetails			userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map 			= new ModelAndView("viewUserActivities");
			map.addObject("userId",userDetails.getId());
			map.addObject("userName", userDetails.getFirstName()+" "+userDetails.getLastName());
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	@PostMapping(value="/getWOActivityCount",produces="application/json")
	public HashMap<Object, Object> getWOActivityCount(@RequestParam HashMap<Object,Object> allrequestparam ) throws Exception {
		ValueObject valueInObject = null;
		
		try {
				valueInObject = new ValueObject(allrequestparam);
				
				return userActivityReportService.getUSerWiseWOActivityCount(valueInObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		}
		
		
	}
	@PostMapping(value="/getSEActivityCount",produces="application/json")
	public HashMap<Object, Object> getSEActivityCount(@RequestParam HashMap<Object,Object> allrequestparam ) throws Exception {
		ValueObject valueInObject = null;
		
		try {
				valueInObject = new ValueObject(allrequestparam);
				
				return userActivityReportService.getUserWiseServiceEntryCount(valueInObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		}
		
		
	}
	
	@PostMapping(value="/getTripSheetActivityCount",produces="application/json")
	public HashMap<Object, Object> getTripSheetActivityCount(@RequestParam HashMap<Object,Object> allrequestparam ) throws Exception {
		ValueObject valueInObject = null;
		
		try {
				valueInObject = new ValueObject(allrequestparam);
				
				return userActivityReportService.getUserWiseTripSheetActivityCount(valueInObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		}
		
		
	}
	@PostMapping(value="/getFuelEntryActivityCount",produces="application/json")
	public HashMap<Object, Object> getFuelEntryActivityCount(@RequestParam HashMap<Object,Object> allrequestparam ) throws Exception {
		ValueObject valueInObject = null;
		
		try {
				valueInObject = new ValueObject(allrequestparam);
				
				return userActivityReportService.getUserWiseFuelEntryActivityCount(valueInObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		}
		
		
	}
	
	@PostMapping(value ="/getRRActivityCount", produces ="application/json")
	public HashMap<Object,Object> getRRActivityCount(@RequestParam HashMap<Object, Object> allrequestParam) throws Exception {
		ValueObject valueInObject = null; 
		
		try {
			
			valueInObject = new ValueObject(allrequestParam);
			
			return userActivityReportService.getUserWiseRRActivityCount(valueInObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		}
		
	
		
	}
	
	@PostMapping(value ="/getIssuesActivityCount", produces ="application/json")
	public HashMap<Object,Object> getIssuesActivityCount(@RequestParam HashMap<Object, Object> allrequestParam) throws Exception {
		ValueObject valueInObject = null; 
		
		try {
			
			valueInObject = new ValueObject(allrequestParam);
			
			return userActivityReportService.getUserWiseIssuesCount(valueInObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		}
		
	
		
	}
	
	
	@PostMapping(value = "/getUserListForActivity")
	public void getUserListForActivity(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserProfileDto> user = new ArrayList<>();
		List<UserProfileDto> userName = userProfileService.SearchUserEmail_id_and_Name(term, userDetails.getCompany_id());
		if (userName != null && !userName.isEmpty()) {
			for (UserProfileDto add : userName) {
				UserProfileDto wadd = new UserProfileDto();

				wadd.setUser_email(add.getUser_email());
				wadd.setFirstName(add.getFirstName());
				wadd.setLastName(add.getLastName());
				wadd.setUser_id(add.getUser_id());
				user.add(wadd);
			} 
			UserProfileDto wadd1 = new UserProfileDto();
			wadd1.setFirstName("All");
			wadd1.setLastName(" ");
			wadd1.setUser_id((long)0);
			user.add(wadd1);
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(user));
	}
	
	
	@GetMapping(value="/viewActivityData")
	public ModelAndView viewActivityData(final HttpServletRequest request,@RequestParam("data") String data) {
		String [] dataArr =null;
		HashMap<String,Object> model = null;
		try {
			model= new HashMap<>();
			dataArr=data.split(",");
			model.put("startDate",dataArr[0]);
			model.put("endDate",dataArr[1]);
			model.put("user", dataArr[2]);
			model.put("userName", dataArr[3]);
			model.put("activityType",dataArr[4]);
			
			switch(Short.parseShort(dataArr[4]) ) {
			case UserActivityConstant.WORK_ORDER:
				return new ModelAndView("viewWOActivityData",model);
			case UserActivityConstant.SERVICE_ENTRIES:
				return new ModelAndView("viewSEActivityData",model);
			case UserActivityConstant.TRIP_SHEET:
				return new ModelAndView("viewTSActivityData",model);
			case UserActivityConstant.FUEL_ENTRIES:
				return new ModelAndView("viewFEActivityData",model);
			case UserActivityConstant.RENEWAL_REMINDER:
				return new ModelAndView("viewRRActivityData",model);
			case UserActivityConstant.ACTIVITY_TYPE_ISSUES:
				return new ModelAndView("viewIssuesActivityData",model);
			case UserActivityConstant.ACTIVITY_TYPE_PO:
				return new ModelAndView("viewPOActivityData",model);
			case UserActivityConstant.ACTIVITY_TYPE_DSE:
				return new ModelAndView("viewDSEActivityData",model);
			default:
				return new ModelAndView("viewUserActivity",model);
			}
			
			
		} catch (Exception e) {
			throw e;
		}
	
		
		
	}
	
	
	@PostMapping(value ="/getActivityWOData", produces ="application/json")
	public HashMap<Object,Object> getActivityWOData(@RequestParam HashMap<Object, Object> allrequestParam) throws Exception {
		ValueObject valueInObject = null; 
		
		try {
			
			valueInObject = new ValueObject(allrequestParam);
			
			return userActivityReportService.getActivityWOData(valueInObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		}
		
	
		
	}
	
	
	@PostMapping(value ="/getActivitySEData", produces ="application/json")
	public HashMap<Object,Object> getActivitySEData(@RequestParam HashMap<Object, Object> allrequestParam) throws Exception {
		ValueObject valueInObject = null; 
		
		try {
			
			valueInObject = new ValueObject(allrequestParam);
			
			return userActivityReportService.getActivitySEData(valueInObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		}
		
	
		
	}
	
	@PostMapping(value ="/getActivityTSData", produces ="application/json")
	public HashMap<Object,Object> getActivityTSData(@RequestParam HashMap<Object, Object> allrequestParam) throws Exception {
		ValueObject valueInObject = null; 
		
		try {
			
			valueInObject = new ValueObject(allrequestParam);
			
			return userActivityReportService.getActivityTSData(valueInObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		}
		
	
		
	}
	
	@PostMapping(value ="/getActivityFEData", produces ="application/json")
	public HashMap<Object,Object> getActivityFEData(@RequestParam HashMap<Object, Object> allrequestParam) throws Exception {
		ValueObject valueInObject = null; 
		
		try {
			
			valueInObject = new ValueObject(allrequestParam);
			
			return userActivityReportService.getActivityFEData(valueInObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		}
	}
	

	@PostMapping(value ="/getActivityRRData", produces ="application/json")
	public HashMap<Object,Object> getActivityRRData(@RequestParam HashMap<Object, Object> allrequestParam) throws Exception {
		ValueObject valueInObject = null; 
		
		try {
			
			valueInObject = new ValueObject(allrequestParam);
			
			return userActivityReportService.getActivityRRData(valueInObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@PostMapping(value ="/getActivityIssuesData", produces ="application/json")
	public HashMap<Object,Object> getActivityIssuesData(@RequestParam HashMap<Object, Object> allrequestParam) throws Exception {
		ValueObject valueInObject = null; 
		
		try {
			
			valueInObject = new ValueObject(allrequestParam);
			
			return userActivityReportService.getActivityIssuesData(valueInObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@PostMapping(value ="/getPurchaseOActivityCount", produces ="application/json")
	public HashMap<Object,Object> getPurchaseOActivityCount(@RequestParam HashMap<Object, Object> allrequestParam) throws Exception {
		ValueObject valueInObject = null; 
		
		try {
			
			valueInObject = new ValueObject(allrequestParam);
			
			return userActivityReportService.getUserWiseCount(valueInObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@PostMapping(value ="/getActivityPOData", produces ="application/json")
	public HashMap<Object,Object> getActivityPOData(@RequestParam HashMap<Object, Object> allrequestParam) throws Exception {
		ValueObject valueInObject = null; 
		
		try {
			
			valueInObject = new ValueObject(allrequestParam);
			
			return userActivityReportService.getUserWisePoData(valueInObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		}
	}
	@PostMapping(value ="/getActivityWiseDSEData", produces ="application/json")
	public HashMap<Object,Object> getActivityWiseDSEData(@RequestParam HashMap<Object, Object> allrequestParam) throws Exception {
		ValueObject valueInObject = null; 
		
		try {
			
			valueInObject = new ValueObject(allrequestParam);
			
			return userActivityReportService.getUserWiseDSEData(valueInObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@PostMapping(value ="/getDSEActivityCount", produces ="application/json")
	public HashMap<Object,Object> getDSEActivityCount(@RequestParam HashMap<Object, Object> allrequestParam) throws Exception {
		ValueObject valueInObject = null; 
		
		try {
			
			valueInObject = new ValueObject(allrequestParam);
			valueInObject.put("DSE", true);
			
			return userActivityReportService.getUserWiseCount(valueInObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	
}
