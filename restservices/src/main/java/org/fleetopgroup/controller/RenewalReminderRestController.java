package org.fleetopgroup.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalSubTypeService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RenewalReminderRestController {

	@Autowired private IRenewalReminderService		renewalReminderService;
	@Autowired private IRenewalSubTypeService		RenewalSubTypeService;
	
	@RequestMapping(value="/getRenewalReminderPending", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getRenewalReminderPending(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= renewalReminderService.getRenewalReminderPendingList(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value="/saveRenewalPendingDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveRenewalPendingDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object.put("seDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("seDetails")));
			
			valueOutObject		= renewalReminderService.saveRenewalPendingDetails(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value = "/getRenewalReminderInitialConfigDataForMobile", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalReminderInitialConfigDataForMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 						object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return renewalReminderService.getRenewalReminderInitialConfigDataForMobile(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getRenewalReminderSubTypeList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalReminderSubTypeList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalSubTypeService.mobileRenewalSubTypeList(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveRenewalReminderFromMobile", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveRenewalReminderFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		object		 		= null;
		try {
			object = new ValueObject(allRequestParams);
			return renewalReminderService.saveRenewalReminderFromMobile(JsonConvertor.toValueObjectFormSimpleJsonString(object.getString("renewalData")),null).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getRenewalReminderShowDataForMobile", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalReminderShowDataForMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 						object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return renewalReminderService.getRenewalReminderShowDataForMobile(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@RequestMapping(value = "/searchRenewalByNumber", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchRenewalByNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 						object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return renewalReminderService.searchRenewalByNumber(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@RequestMapping(value = "/searchRenewalByVehicle", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchRenewalByVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 						object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return renewalReminderService.searchRenewalByVehicle(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getMandatoryListOfRenewal", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getMandatoryListOfRenewal(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 						object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return renewalReminderService.getRenewalMandatoryList(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@RequestMapping(value="/getRenewalReminderDetailsByVehicleNoAndReneType", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getRenewalReminderDetailsByVehicleNoAndReneType(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= renewalReminderService.searchRenRemndReportForMob(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
}
