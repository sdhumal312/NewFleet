package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IRouteFixedAllowanceService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteExpenseRangeService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TripRouteExpenseRangeRestController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired 	ITripRouteExpenseRangeService   	tripRouteExpenseRangeService;
	@Autowired 	ITripRouteService   				tripRouteExpense;
	@Autowired	private IRouteFixedAllowanceService	routeFixedAllowanceService;
	
	@RequestMapping(value="/getTripExpenseListToSetRouteWiseExpenseRange", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getExpenseRangeListByRouteId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= tripRouteExpenseRangeService.getTripExpenseListToSetRouteWiseExpenseRange(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	
	@RequestMapping(value="/addTripRouteExpenseRange", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  addTripRouteExpenseRange(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueInObject.put("finalList", JsonConvertor.toValueObjectFromJsonString(valueInObject.getString("finalList")));
			
			valueOutObject		= tripRouteExpenseRangeService.saveTripRouteExpenseRange(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;
		} finally {
			valueInObject 					= null;
			valueOutObject 					= null;
		}
	}

	@RequestMapping(value="/getExpenseMaxLimit", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getExpenseMaxLimit(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= tripRouteExpenseRangeService.getExpenseRangeByRouteIdAndExpenseId(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	
	
	@RequestMapping(value="/getExpenseUsage", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getExpenseUsage(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= tripRouteExpense.getTripRouteFixedExpeneByExpenseId(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value="/saveAllowanceDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveAllowanceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= routeFixedAllowanceService.saveAllowanceDetails(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;
		} finally {
			valueInObject 					= null;
			valueOutObject 					= null;
		}
	}
	
	@RequestMapping(value="/removeAllowanceDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  removeAllowanceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= routeFixedAllowanceService.removeAllowanceDetails(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;
		} finally {
			valueInObject 					= null;
			valueOutObject 					= null;
		}
	}
}
