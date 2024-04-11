package org.fleetopgroup.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverRestControllerForMobile {
	
	@Autowired 
	IDriverService 				driverService;

	
	
	@RequestMapping(value = "/getDriverRenewalTypeList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDriverRenewalTypeList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 						object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return driverService.getDriverRenewalTypeList(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveDriverRenewalFromMobile", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveDriverRenewalFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		object		 		= null;
		
		try {
			object = new ValueObject(allRequestParams);
			return driverService.saveDriverRenewalFromMobile(JsonConvertor.toValueObjectFormSimpleJsonString(object.getString("driverRenewalDetails"))).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@RequestMapping(value = "/showDriverRenewalFromMobile", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  showDriverRenewalFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		object		 		= null;
		try {
			object = new ValueObject(allRequestParams);
			
			return driverService.showDriverRenewalFromMobile(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@RequestMapping(value = "/searchDriverRenewalByDriverNumber", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchDriverRenewalByDriverNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		object		 		= null;
		try {
			object = new ValueObject(allRequestParams);
			
			return driverService.searchDriverRenewalByDriverNumber(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
}