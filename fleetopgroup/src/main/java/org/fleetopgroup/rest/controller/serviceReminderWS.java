package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IEmailAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.ISmsAlertQueueService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/serviceReminderWS")
public class serviceReminderWS {
	
	@Autowired IEmailAlertQueueService emailAlertQueueService;
	@Autowired ISmsAlertQueueService   smsAlertQueueService;
	
	@RequestMapping(value="/saveEmail", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveVendorPayment(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		int 						vid								= 107;
		try {
			
			object 				= new ValueObject(allRequestParams);
			
			valueOutObject		= new ValueObject();
			valueOutObject.put("Email", emailAlertQueueService.saveEmailServiceReminder(object));
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}
	
	@RequestMapping(value="/saveSms", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveSms(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			valueOutObject		= new ValueObject();
			valueOutObject.put("Sms", smsAlertQueueService.saveSmsServiceReminder(object));
			
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}
	
	@RequestMapping(value="/checkEmail", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  checkEmail(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
								
			valueOutObject		= new ValueObject();
			valueOutObject.put("Email",emailAlertQueueService.getAllEmailAlertQueueDetailsById(object.getLong("serviceId")));
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}
	
	@RequestMapping(value="/checkSms", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  checkSms(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
								
			valueOutObject		= new ValueObject();
			valueOutObject.put("SMS", smsAlertQueueService.getAllSmsAlertQueueDetailsById(object.getLong("serviceId")));
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}
	
}