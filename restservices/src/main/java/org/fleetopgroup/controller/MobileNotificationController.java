package org.fleetopgroup.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IMobileNotificationService;
import org.fleetopgroup.persistence.serviceImpl.IUserAlertNotificationsService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MobileNotificationController {
	@Autowired IMobileNotificationService iMobileNotificationService;
	@Autowired	private IUserAlertNotificationsService	userAlertNotificationsService;
	@RequestMapping(value="/getUserNotificationDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getUserNotificationDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		valueOutObject 		= null;
		ValueObject		object		 		= null;
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = iMobileNotificationService.getNotifications(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
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
}
