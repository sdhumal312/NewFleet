package org.fleetopgroup.controller;

import java.util.HashMap;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IFuelInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FuelEntryControllerOnMobile {
	
	@Autowired	private IFuelService 		fuelService;
	@Autowired	private IVehicleService 	vehicleService;
	@Autowired	private	IFuelInvoiceService				fuelInvoiceService;
	@Autowired private  ICompanyConfigurationService companyConfigurationService;
	
	private final Logger LOGGER	= LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/mobileAppFuelVehicleOdoMerete", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getFuelVehicleOdoMerete(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		valueOutObject 		= null;
		ValueObject		object		 		= null;
		try {
			object = new ValueObject(allRequestParams);
			
			valueOutObject = fuelService.getFuelVehicleOdoMerete(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("getFuelVehicleOdoMerete:"+ e);
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@RequestMapping(value="/mobileAppGetFuelVendorList", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getFuelVendorList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		valueOutObject 		= null;
		ValueObject		object		 		= null;
		try {
			object = new ValueObject(allRequestParams);
			
			valueOutObject = fuelService.getFuelVendorList(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("getFuelVendorList:"+ e);
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@RequestMapping(value="/mobileAppInitializeFuelEntry", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  initializeFuelEntry(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		valueOutObject 		= null;
		ValueObject		object		 		= null;
		try {
			object = new ValueObject(allRequestParams);
			
			valueOutObject = fuelService.initializeFuelEntry(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("initializeFuelEntry:"+ e);
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@RequestMapping(value = "/mobileAppSaveFuelEntryDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  mobileAppSaveFuelEntryDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		    object		 		  = null;
		CustomUserDetails   userDetails           = null;
		try {
			object = new ValueObject(allRequestParams);
			object = JsonConvertor.toValueObjectFormSimpleJsonString(object.getString("FuelData"));
			HashMap<String,Object> configuration				= companyConfigurationService.getCompanyConfiguration(object.getInt("companyId",0), PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			userDetails  =  new CustomUserDetails(object.getInt("companyId",0), object.getLong("userId",0));
			if((boolean) configuration.getOrDefault("getStockFromInventory", false)) {
				return fuelService.prepareInvoiceWiseFuelEntry(object, null,userDetails,configuration).getHtData();
			}else {
				return fuelService.saveFuelEntryDetails(object,null).getHtData();
			}
		} catch (Exception e) {
			LOGGER.error("saveFuelEntryDetails:"+ e);
			throw e;
		}finally{
			object		 		  = null;   
			userDetails           = null;   
		}
	}
	
	@RequestMapping(value="/mobileAppGetFuelDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getFuelDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		valueOutObject 		= null;
		ValueObject		object		 		= null;
		try {
			object = new ValueObject(allRequestParams);
			
			valueOutObject = fuelService.getFuelDetailsForMobile(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("getFuelDetailsForMobile:"+ e);
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@RequestMapping(value = "/updateVehicleKMPLDetailsFromMobile", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateVehicleKMPLDetailsFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		object		 		= null;
		try {
			object = new ValueObject(allRequestParams);
			return vehicleService.saveVehicleKmplDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("updateVehicleKMPLDetailsFromMobile:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getActiveTripSheetAndBackDateData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getActiveTripSheetAndBackDateData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		object		 		= null;
		try {
			System.err.println("1...");
			object = new ValueObject(allRequestParams);
			return fuelService.getActiveTripsheetDataAtTime(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("getActiveTripSheetAndBackDateData:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/searchFuelEntriesByVehicle", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchFuelEntriesByVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		object		 		= null;
		try {
			object = new ValueObject(allRequestParams);
			return fuelService.searchFuelEntriesByVehicle(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("searchFuelEntriesByVehicle:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(path = "/getFuelStockDetailsFromMob", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getFuelStockDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return fuelInvoiceService.getFuelStockDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(path = "/searchFuelEntriesByVehicleNumberAndDateRangeFromMob", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  searchFuelEntriesByVehicleNumberAndDateRangeFromMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return fuelService.searchFuelEntriesByVehicleNumberAndDateRange(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
}
