package org.fleetopgroup.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceEntriesRestController {

	@Autowired	IServiceEntriesService		serviceEntriesService;
	
	@RequestMapping(value="/getServiceEntriesPending", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getServiceEntriesPending(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= serviceEntriesService.getServiceEntiresPendingList(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value="/saveServiceExpenseToTrip", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveServiceExpenseToTrip(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			object.put("seDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("seDetails")));
			
			valueOutObject		= serviceEntriesService.saveServiceExpenseToTrip(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
}
