package org.fleetopgroup.rest.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ITallyIntegrationService;
import org.fleetopgroup.persistence.serviceImpl.IUserAlertNotificationsService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class AlertNotificationsController {
	
	@Autowired	private IUserAlertNotificationsService	userAlertNotificationsService;;
	@Autowired  private ITallyIntegrationService		tallyIntegrationService;
	@Autowired	private ICashPaymentService				cashPaymentService;
	@Autowired	private IBankPaymentService				bankPaymentService;

	@RequestMapping(value = "/getNotificationCount", method = RequestMethod.GET)
	public void GetMarqueeMessage(Map<String, Object> map, final HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
			long alertCount  = userAlertNotificationsService.getNotificationCountForUser(userDetails.getId());
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(alertCount));
	}
	
	
	
	@RequestMapping(value = "/getUserNotificationList", method = RequestMethod.GET)
	public ModelAndView UreaInventory() throws Exception {
		ModelAndView map = new ModelAndView("showNotificationList");
		try {
			
		}  catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/getUnreadNotificationList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getUnreadNotificationList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return userAlertNotificationsService.getUnreadNotificationList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/getReadNotificationList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getReadNotificationList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return userAlertNotificationsService.getReadNotificationList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/markNotificationAsRead", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  markNotificationAsRead(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return userAlertNotificationsService.markNotificationAsRead(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getSentNotificationList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getSentNotificationList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return userAlertNotificationsService.getSentNotificationList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getRRNotificationCount", method = RequestMethod.GET)
	public void getRRNotificationCount(Map<String, Object> map, final HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		ValueObject 		valueObject		= null;
		try {
			valueObject		= userAlertNotificationsService.getRRNotificationCount();
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(valueObject));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@RequestMapping(value = "/getRRNotificationList", method = RequestMethod.GET)
	public ModelAndView getRRNotificationList() {
		ModelAndView map = new ModelAndView("renewalNotificationList");
		return map;
	}
	
	@RequestMapping(value = "/getAllRenewalRemiderListByStatus", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getAllRenewalRemiderListByStatus(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return userAlertNotificationsService.getAllRenewalRemiderListByStatus(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value="insertMissingBankPayment2", produces="application/json", method=RequestMethod.GET)
	public String  insertMissingBankPayment2(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		try {
			bankPaymentService.sendBankStateMentToIv(null);
			
			return "BAnk Statement Pojo Completed Succesfully !";
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value="insertMissingCashment2", produces="application/json", method=RequestMethod.GET)
	public String  insertMissingCashment2(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		try {
			cashPaymentService.sendCashPaymentListToIV(null);
			
			return "Cash Statement Pojo Completed Succesfully !";
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@RequestMapping(value="saveVehicleModelTyreLayout2", produces="application/json", method=RequestMethod.GET)
	public String  timeZoneTest(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest	request,
			TimeZone	timeZone) throws Exception {
		try {
			
		//	layoutService.saveVehicleModelTyreLayout2(new ValueObject());
			ValueObject object = new ValueObject();
			
			object.put("fromDate", "2023-06-14 00:00:00");
			object.put("toDate", "2023-06-14 23:59:59");
			object.put("companyId", 46);
			object.put("selectedStr", "ATC 2023 24");
			
			tallyIntegrationService.getTallyIntegrationDataAtc(object);
	        
			return "Vehicle Layout Succesfully !";
			
		} catch (Exception e) {
			throw e;
		}
	}
}
