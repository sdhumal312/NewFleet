package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FuelEntriesController {
	
	@Autowired	private IFuelService fuelService;
	@Autowired	private IDriverService driverService;
	@Autowired private  ICompanyConfigurationService companyConfigurationService;
	
	
	@PostMapping(path = "/saveFuelEntryDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveFuelEntryDetails(@RequestParam("FuelData") String allRequestParams, 
			@RequestParam("input-file-preview") MultipartFile uploadfile) throws Exception {
		try {
			CustomUserDetails usetDetails= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String,Object> configuration				= companyConfigurationService.getCompanyConfiguration(usetDetails.getCompany_id(), PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.getOrDefault("getStockFromInventory", false)) {
				return fuelService.prepareInvoiceWiseFuelEntry(JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams), uploadfile,usetDetails,configuration).getHtData();
			}else {
				return fuelService.saveFuelEntryDetails(JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams),uploadfile).getHtData();
			}
			
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getActiveTripsheetDataAtTime", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getActiveTripsheetDataAtTime(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return fuelService.getActiveTripsheetDataAtTime(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getFuelDetailsById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getFuelDetailsById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return fuelService.getFuelDetailsById(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/updateFuelEntries", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateFuelEntries(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return fuelService.updateFuelEntries(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/updateFuelGPSUsage", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateFuelGPSUsage(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return fuelService.updateFuelGPSUsage(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(value = "/FuelEntryAlertOfAllActiveVehicle", produces="application/json")
	public HashMap<Object, Object>  FuelEntryAlertOfAllActiveVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			return	 fuelService.getMissingFuelEntryAlertByCompanyId(userDetails.getCompany_id()).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/getDefaultDriverForVehicle", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getDefaultDriverForVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return driverService.getDefaultDriverForVehicle(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
}	