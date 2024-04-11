package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.bl.TripIncomeBL;
import org.fleetopgroup.persistence.serviceImpl.ITripIncomeService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TripIncomeWS {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired 	ITripIncomeService 			tripIncomeService;
	
	TripIncomeBL	tripIncomeBL 		= new TripIncomeBL();
	
	@RequestMapping(value="/addTripIncomeWithRate", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  addTyreIncomeWithRate(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= tripIncomeService.saveTripIncomeWithRate(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("ERR"+e);
			throw e;
		} finally {
			valueInObject 					= null;
			valueOutObject 					= null;
		}
	}
	
	@RequestMapping(value="/getTripIncomeById", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getTripIncomeById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= tripIncomeService.getTripIncomeById(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	

	@RequestMapping(value="/updateTripIncomeWithRate", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateTripIncomeWithRate(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= tripIncomeService.updateTripIncomeWithRate(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
}
