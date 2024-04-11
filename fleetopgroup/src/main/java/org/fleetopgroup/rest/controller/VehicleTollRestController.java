package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.serviceImpl.IVehicleTollService;
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
public class VehicleTollRestController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired	IVehicleTollService		vehicleTollService;
	
	@RequestMapping(value="/addVehicleToll", method = RequestMethod.GET)
	public ModelAndView addToolBox(final HttpServletRequest request){
		try {
			ModelAndView map = new ModelAndView("addVehicleTollDetails");
			return map;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/getVehicleTollList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleTollList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception 
	{
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleTollService.getVehicleTollDetailsList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	@RequestMapping(value = "/saveVehicleToll", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveVehicleToll(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleTollService.saveVehicleToll(object).getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getVehicleTollById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleTollById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleTollService.getVehicleTollById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/editVehicleToll", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  editVehicleToll(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleTollService.editVehicleToll(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteVehicleToll", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteVehicleToll(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleTollService.getDeleteVehicleToll(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
}
