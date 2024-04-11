package org.fleetopgroup.rest.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.util.JSON;

@RestController
public class ServiceReminderRestController {

	@Autowired private IServiceReminderService			serviceReminderService;
	@Autowired private IVehicleService					vehicleService;
	@Autowired private ICompanyConfigurationService		companyConfigurationService;
	
	@RequestMapping(value="/getServiceReminderDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getServiceReminderDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= serviceReminderService.getServiceReminderCalendarDetails(object);
			

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value="/getServiceReminderListOftheDay", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getServiceReminderListOftheDay(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= serviceReminderService.getServiceReminderListOftheDay(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value = "/ViewServiceReminderList", method = RequestMethod.GET)
	public ModelAndView getServiceReminderList(final HttpServletRequest request) throws Exception {
		ModelAndView 					map 		= null;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map = new ModelAndView("ServiceReminderList");
			
			HashMap<String, Object> 	configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.SERVICE_REMINDER_CONFIG);
			
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			if(permission.contains(new SimpleGrantedAuthority("WORKORDER_SERVICE_REMINDER"))) {
				map.addObject("createWoOnService", true);
			}
			if(permission.contains(new SimpleGrantedAuthority("EDIT_SERVICE_REMINDER"))) {
				map.addObject("editServiceReminder", true);
			}
			if(permission.contains(new SimpleGrantedAuthority("DELETE_SERVICE_REMINDER"))) {
				map.addObject("deleteServiceReminder", true);
			}
			if(permission.contains(new SimpleGrantedAuthority("SKIP_SERVICE_REMINDER"))) {
				map.addObject("skipServiceReminder", true);
			}
			if(permission.contains(new SimpleGrantedAuthority("DEALER_SERVICE_REMINDER"))) {
				map.addObject("createDSEOnService", true);
			}
			
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("configuration", configuration);
			
			return map;
		}  catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value="/getServiceReminderList", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getServiceReminderList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= serviceReminderService.getServiceReminderList(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value="/getOverDueServiceList", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getOverDueServiceList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= serviceReminderService.getOverDueServiceList(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value="/getDueSoonServiceList", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getDueSoonServiceList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= serviceReminderService.getDueSoonServiceList(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	
	
	@RequestMapping(value="/getDueSoonServiceListGroupBySProgram", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getDueSoonServiceListGroupBySProgram(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= serviceReminderService.getDueSoonServiceListGroupBySProgram(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value="/getOverDueServiceListGroupBySProgram", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getOverDueServiceListGroupBySProgram(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= serviceReminderService.getOverDueServiceListGroupBySProgram(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping("/viewDueSoonGroupedList")
	public ModelAndView viewDueSoonGroupedList(@ModelAttribute("command") ServiceReminder ServiceReminderDto,
			BindingResult result, final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.put("companyId", userDetails.getCompany_id());
		model.put("userId", userDetails.getId());
		if(ServiceReminderDto.getVid() != null) {
			model.put("vehicle", vehicleService.getVehicle_Details_Service_Reminder_Entries(ServiceReminderDto.getVid(), userDetails.getCompany_id()));
		}
		String uniqueID = UUID.randomUUID().toString();
		request.getSession().setAttribute(uniqueID, uniqueID);
		model.put("accessToken", uniqueID);
		return new ModelAndView("viewDueSoonGroupedList", model);
	}
	
	@RequestMapping("/viewOverDueGroupedList")
	public ModelAndView viewOverDueGroupedList(@ModelAttribute("command") ServiceReminder ServiceReminderDto,
			BindingResult result, final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.put("companyId", userDetails.getCompany_id());
		model.put("userId", userDetails.getId());
		if(ServiceReminderDto.getVid() != null) {
			model.put("vehicle", vehicleService.getVehicle_Details_Service_Reminder_Entries(ServiceReminderDto.getVid(), userDetails.getCompany_id()));
		}
		String uniqueID = UUID.randomUUID().toString();
		request.getSession().setAttribute(uniqueID, uniqueID);
		model.put("accessToken", uniqueID);
		return new ModelAndView("viewOverDueGroupedList", model);
	}
	
	@RequestMapping("/addServiceReminderEntry")
	public ModelAndView addServiceReminder(@ModelAttribute("command") ServiceReminder ServiceReminderDto,
			BindingResult result, final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.put("companyId", userDetails.getCompany_id());
		model.put("userId", userDetails.getId());
		if(ServiceReminderDto.getVid() != null) {
			model.put("vehicle", vehicleService.getVehicle_Details_Service_Reminder_Entries(ServiceReminderDto.getVid(), userDetails.getCompany_id()));
		}
		String uniqueID = UUID.randomUUID().toString();
		request.getSession().setAttribute(uniqueID, uniqueID);
		model.put("accessToken", uniqueID);
		return new ModelAndView("addServiceReminderEntry", model);
	}
	@RequestMapping("/editServiceReminderEntry")
	public ModelAndView editServiceReminderEntry(@ModelAttribute("command") ServiceReminder serviceReminderDto,
			BindingResult result) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.put("companyId", userDetails.getCompany_id());
		model.put("userId", userDetails.getId());
		model.put("service_id", serviceReminderDto.getService_id());
		return new ModelAndView("EditServiceReminderEntry", model);
	}
	
	@RequestMapping(value="/saveServiceReminderDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveServiceReminderDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= serviceReminderService.saveServiceReminderDetails(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value="/getServiceReminderDetailsById", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getServiceReminderData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= serviceReminderService.getServiceReminderData(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value="/updateServiceReminderDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateServiceReminderDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= serviceReminderService.updateServiceReminderDetails(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value = "/ViewOverDueServiceList", method = RequestMethod.GET)
	public ModelAndView ViewOverDueServiceList(final HttpServletRequest request) throws Exception {
		ModelAndView 					map 		= null;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map = new ModelAndView("ServiceReminderList");
			
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			
			return map;
		}  catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value="/searchServiceReminderByNumber", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  searchServiceReminderByNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= serviceReminderService.searchServiceReminderByNumber(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value="/searchServiceReminderByMultiple", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  searchServiceReminderByMultiple(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= serviceReminderService.searchServiceReminderByMultiple(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value="/deleteServiceReminderById", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  deleteServiceReminderById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= serviceReminderService.deleteServiceReminderById(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@PostMapping(value = "/getVehicleWiseActiveServiceReminderReport", produces="application/json")
	public HashMap<Object, Object>  getVehicleRepairAndPartConsumptionDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceReminderService.getVehicleWiseActiveSR(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@PostMapping(value = "/getServiceReminderByProgramIdVid", produces="application/json")
	public HashMap<Object, Object>  getServiceReminderByProgramIdVid(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceReminderService.getServiceReminderByProgramIdVid(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@PostMapping(value = "/getServiceReminderByServiceId", produces="application/json")
	public HashMap<Object, Object>  getServiceReminderByServiceId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceReminderService.getServiceReminderByServiceId(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getServiceReminderOfVehicle", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getServiceReminderOfVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return serviceReminderService.getServiceReminderOfVehicle(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getOverDueReminderOfVehicle", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getOverDueReminderOfVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return serviceReminderService.getOverDueReminderOfVehicle(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getDueSoonReminderOfVehicle", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDueSoonReminderOfVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return serviceReminderService.getDueSoonReminderOfVehicle(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(value = "/skipServiceReminderWithRemark", produces="application/json")
	public HashMap<Object, Object>  skipServiceReminderWithRemark(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return serviceReminderService.skipSrWithRemark(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(value = "/checkDueAndOverDueStatus", produces="application/json")
	public HashMap<Object, Object>  checkDueAndOverDueStatus(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return serviceReminderService.getServiceReminderForDueAndOverDue(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}

   @RequestMapping(value ="/validateJobTypeAndsubType", method=RequestMethod.POST, produces="application/json")
	public HashMap<Object,Object> validateJobTypeAndsubType(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject     object 				= null;
		try{
			object       = new ValueObject(allRequestParams);
			
			return   serviceReminderService.checkServiceReminderForJobTypeAndSubType(object).getHtData();
		}catch(Exception e) {
			throw e;
		}
		
	}
	
	@PostMapping(value = "/getServiceReminderHistory", produces="application/json")
	public HashMap<Object, Object>  getServiceReminderHistory(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
				
				System.err.println("inside get histiry");
			
			return serviceReminderService.getServiceReminderHistory(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}

	@RequestMapping(value="/getTodayOverDueServiceList", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getTodaysOverDueServiceList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= serviceReminderService.getTodaysOverDueServiceList(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}

	@RequestMapping(value="/getUpcomingOverDueServiceList", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getUpcomingOverDueServiceList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= serviceReminderService.getUpcomingOverDueServiceList(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
}
