package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.ILoadTypeService;
import org.fleetopgroup.persistence.serviceImpl.ITallyIntegrationService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutoCompleteMobileRestController{
	
	@Autowired private IDriverService						DriverService;
	@Autowired private ITripRouteService					TripRouteService;
	@Autowired private ILoadTypeService						LoadTypeService;
	
	
	private final Logger LOGGER	= LoggerFactory.getLogger(getClass());
	
	
	@RequestMapping(value = "/mobileDriverNameWiseList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  mobileDriverNameWiseList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return DriverService.getDriverNameWiseList(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("DriverNameList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/mobileCleanerNameWiseList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  mobileCleanerNameWiseList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return DriverService.getCleanerNameWiseList(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("CleanerNameList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/mobileTripRouteNameWiseList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  mobileTripRouteNameWiseList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return TripRouteService.getTripRouteNameWiseList(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("TripRouteNameList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/mobileLoadTypeNameWiseList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  mobileLoadTypeNameWiseList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return LoadTypeService.getLoadTypeNameWiseList(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("TripRouteNameList:"+ e);
			throw e;
		} 
	}
	
}
