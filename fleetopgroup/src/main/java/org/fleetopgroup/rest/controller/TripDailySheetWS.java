package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tripDailySheetWS")
public class TripDailySheetWS {
	
	@Autowired ITripDailySheetService tripDailySheetService;
	@Autowired ITripRouteService	  tripRouteService;
	
	@RequestMapping(value="/updateTimeIncomeAddAmount", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateTimeIncomeAddAmount(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;
		Authentication 		auth 					= null;
		CustomUserDetails 	userDetails 			= null; 
		Double 				incomeAmount			= 0.0;
		long 				tripincomeID			= 0;
		long 				tripdailyid				= 0;

		try {
			
			object 			= new ValueObject(allRequestParams);
			auth 			= SecurityContextHolder.getContext().getAuthentication();
			userDetails 	= (CustomUserDetails) auth.getPrincipal();

			incomeAmount	= object.getDouble("incomeAmount", 0);
			tripincomeID	= object.getLong("tripincomeID", 0);
			tripdailyid		= object.getLong("tripdailyid", 0);

			tripDailySheetService.Update_TripDailyTimeIncome_Amount_By_TDTIMEID(incomeAmount, userDetails.getId(), tripincomeID);

			tripDailySheetService.update_TripDaily_TotalTimeIncome_INCOME_COLLECTION(tripdailyid);
			
			
			valueOutObject	= new ValueObject();
			valueOutObject.put("updateIncome", true);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 			= null;
			object 					= null;
			auth 					= null;
			userDetails 			= null;
			incomeAmount			= 0.0; 
			tripincomeID			= 0;   
			tripdailyid				= 0;   
		}
	}	
	
	@RequestMapping(value = "/getFixedExpenseDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getFixedExpenseDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		CustomUserDetails	userDetails				= null;
		try {
			object 				= new ValueObject(allRequestParams);
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			object.put("routeList", tripRouteService.listTripRouteFixedExpeneList(object.getInt("routeId"), object.getLong("tripSheetId",0), userDetails.getCompany_id()));
			
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails				= null;
		}
	}
	
	@RequestMapping(value = "/updateFixedExpenses", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateFixedExpenses(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			object.put("expensesDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("expensesDetails")));			
			return tripDailySheetService.updateFixedExpenses(object).getHtData();
		} catch (Exception e) {
			throw e;
		}	
	}
	@RequestMapping(value = "/getExpensesDetailsforModel", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getExpensesDetailsforModel(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return tripDailySheetService.getExpensesDetailsforModel(object).getHtData();
		} catch (Exception e) {
			System.err.println("Exception "+e);
			throw e;
		}	
	}
	
	
	@RequestMapping(value = "/getChaloDetailsforModel", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getChaloDetailsforModel(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
						
			object 				= new ValueObject(allRequestParams);			
			valueOutObject =new ValueObject();
			valueOutObject.put("chaloUpdatedRows", tripDailySheetService.update_Chalo_Details(object.getLong("tripdailyId"),object.getInt("chalokm"),object.getDouble("chaloAmount")));
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/getEnteredChaloDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getEnteredChaloDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		CustomUserDetails	userDetails				= null;
		try {
						
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object 				= new ValueObject(allRequestParams);
			long tripdailyId = object.getLong("tripdailyId");					
			valueOutObject =new ValueObject();
			valueOutObject.put("getChaloDetails", tripDailySheetService.findChaloInformation(tripdailyId, userDetails.getCompany_id()));
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
}