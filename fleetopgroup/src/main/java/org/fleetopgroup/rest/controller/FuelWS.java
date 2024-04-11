package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/FuelWS")
public class FuelWS {

	@Autowired IFuelService   FuelService;
	
	@RequestMapping(value = "/MonthlyVehicleWiseFuelReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  MonthlyVehicleWiseFuelReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
		
			object 				= new ValueObject(allRequestParams);
			valueOutObject 		= FuelService.getMonthlyVehicleWiseFuelReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	//Dev Y Start Code for FuelRangeCashCreditWiseReport
	@RequestMapping(value = "/FuelRangeCashCreditWiseReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  FuelRangeCashCreditWiseReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
		
			object 				= new ValueObject(allRequestParams);
			valueOutObject 		= FuelService.getFuelRangeCashCreditWiseReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	//Dev Y End Code for FuelRangeCashCreditWiseReport
	
	@RequestMapping(value = "/FuelEfficiencyDataReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  FuelEfficiencyDataReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject 		= FuelService.getFuelEfficiencyDataReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/getdateWiseFuelEntryReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  dateWiseFuelEntryReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject 		= FuelService.getdateWiseFuelEntryDetailsReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	
	@RequestMapping(value = "/allVehiclesMileageReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  allVehiclesMileageReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject 		= FuelService.getallVehiclesMileageReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	
	@RequestMapping(value = "/getUserWiseFuelEntryReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  dateUserFuelEntryReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject 		= FuelService.getUserWiseFuelEntryDetailsReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	@RequestMapping(value = "/creationDateWiseFuelEntryReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  creationDateWiseFuelEntryReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject 		= FuelService.creationDateWiseFuelEntryReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/getTripSheetWiseFuelEntryReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripSheetWiseFuelEntryReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject 		= FuelService.getTripSheetWiseFuelEntryDetailsReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/getDriverWiseFuelEntryReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDriverWiseFuelEntryReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject 		= FuelService.getDriverWiseFuelEntryReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
}