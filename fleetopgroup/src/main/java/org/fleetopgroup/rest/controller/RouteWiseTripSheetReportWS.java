package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/routeWiseTripSheetReportWS")
public class RouteWiseTripSheetReportWS {
	
	@Autowired ITripSheetService tripSheetService;
	
	@RequestMapping(value="/getRouteWiseTripSheetReport", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getRouteWiseTripSheetReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= tripSheetService.getRouteWiseTripSheetReport(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}	
}