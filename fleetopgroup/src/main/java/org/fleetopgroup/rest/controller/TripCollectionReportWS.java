package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.bl.TripSheetBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.ITripCollectionService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tripCollectionReportWS")
public class TripCollectionReportWS {

	@Autowired ITripCollectionService   iTripCollectionService;
	@Autowired ITripSheetService   		iTripSheetService;
	//@Autowired TripCollectionService   TripCollectionService;
	//@Autowired private ICompanyConfigurationService	companyService;
	@RequestMapping(value="/getDailyMonthlyYearlyTripCollectionReport", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getDailyMonthlyYearlyTripCollectionReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= iTripCollectionService.getDailyMonthlyYearlyTripCollectionReport(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}	

	@RequestMapping(value="/getDailyTripDailyCashBookReport", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getDailyTripDailyCashBookReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;
		
		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= iTripCollectionService.getDailyTripDailyCashBookReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}	
	
	@RequestMapping(value="/getDepotWiseTripSheetStatusReport", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getDepotWiseTripSheetStatusReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;
		
		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= iTripCollectionService.getDepotWiseTripSheetStatusReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	@RequestMapping(value = "/getTripCollectionExpense", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleCommentReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;
		
		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= iTripCollectionService.getTreepCollectionExpense(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
}
	
	@RequestMapping(value="/getTripCollectionExpenseName", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getTripCollectionExpenseName(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;
		
		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= iTripCollectionService.getTripCollectionExpenseName(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
/*************Trip Sheet Expenses And Total Expense ******/
	@RequestMapping(value="/TripExpense", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  TripExpense(@RequestParam("TripSheetID")final Long TripSheetID) throws Exception{
		ValueObject					valueOutObject 		= null;
		CustomUserDetails 			userDetails 		= null;
		try {
			TripSheetBL 			tripSheetBL			= new TripSheetBL();
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueOutObject 		= new ValueObject();
			valueOutObject.put("TripSheetExpense", iTripSheetService.findAllTripSheetExpense(TripSheetID, userDetails.getCompany_id()));
			valueOutObject.put("TotalExpense", tripSheetBL.GetTripSheetDetails(iTripSheetService.getTripSheetDetails(TripSheetID, userDetails)));
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	/*************Trip Sheet Income And Total Income ******/	
	@RequestMapping(value="/TripIncome", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  TripIncome(@RequestParam("TripSheetID")final Long TripSheetID) throws Exception{
		ValueObject					valueOutObject 		= null;
		CustomUserDetails 			userDetails 		= null;
		try {
			TripSheetBL 			tripSheetBL			= new TripSheetBL();
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueOutObject 		= new ValueObject();
			valueOutObject.put("TripSheetIncome", iTripSheetService.findAllTripSheetIncome(TripSheetID, userDetails.getCompany_id()));
			valueOutObject.put("TotalIncome", tripSheetBL.GetTripSheetDetails(iTripSheetService.getTripSheetDetails(TripSheetID, userDetails)));
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
}