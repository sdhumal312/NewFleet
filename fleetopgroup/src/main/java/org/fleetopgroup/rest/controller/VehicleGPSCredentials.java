package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.serviceImpl.IVehicleGPSCredentialService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class VehicleGPSCredentials {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired	IVehicleGPSCredentialService		vehicleGPSCredentialService;

	@RequestMapping(value="/addVehicleGps", method = RequestMethod.GET)
	public ModelAndView addToolBox(final HttpServletRequest request){
		try {
			ModelAndView map = new ModelAndView("addVehicleGps");
			return map;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/getVehicleGPSCredentialList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleGPSCredentialList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception 
	{
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleGPSCredentialService.getVehicleGPSCredentialList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveVehicleGPSCredential", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveVehicleGPSCredential(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleGPSCredentialService.saveVehicleGPSCredential(object).getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getVehicleGPSCredentialById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleGPSCredentialById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleGPSCredentialService.getVehicleGPSCredentialById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteVehicleGPSCredentialById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteVehicleGPSCredentialById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleGPSCredentialService.deleteVehicleGPSCredential(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/editVehicleGPSCredential", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  editVehicleGPSCredential(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleGPSCredentialService.editVehicleGPSCredential(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
}
