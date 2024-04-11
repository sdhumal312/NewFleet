package org.fleetopgroup.rest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.serviceImpl.ILHPVtoTripSheetLinkingService;
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
public class LHPVToTripSheetLinkingWS {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private @Autowired	ILHPVtoTripSheetLinkingService		lHPVtoTripSheetLinkingService;
	

	@RequestMapping(value="/syncLhpvDetailsForVehicle", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  syncLhpvDetailsForVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject						valueOutObject 					= null;
		ValueObject 					object 							= null;
		try {
			
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			
			valueOutObject		= lHPVtoTripSheetLinkingService.syncDataOfLhpvFromIvCargo(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("Error : " , e);
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}
	
	@RequestMapping(value="/syncLhpvDetailsToAddInTripSheet", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  syncLhpvDetailsToAddInTripSheet(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject						valueOutObject 					= null;
		ValueObject 					object 							= null;
		try {
			
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			
			valueOutObject		= lHPVtoTripSheetLinkingService.syncLhpvDetailsToAddInTripSheet(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("Error : " , e);
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}
	
	@RequestMapping(value="/createMultipleLhpvTripSheet", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  createMultipleLhpvTripSheet(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject						valueOutObject 					= null;
		ValueObject 					object 							= null;
		try {
			
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			
			valueOutObject		= lHPVtoTripSheetLinkingService.syncDataOfLhpvFromIvCargo(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("Error : " , e);
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}
	
	@RequestMapping("/SyncVehicleLhpvData")
	public ModelAndView SyncVehicleLhpvData() {
		Map<String, Object> model = new HashMap<String, Object>();

		// show the Group service of the driver
		try {

		} catch (Exception e) {

		}
		return new ModelAndView("SyncLhpvData", model);
	}

	@RequestMapping("/addLhpvDataToTripSheet")
	public ModelAndView addLhpvDataToTripSheet(@RequestParam("ID") final Long tripSheetId, @RequestParam("vid") final Integer vid, HttpServletRequest result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("tripSheetId", tripSheetId);
			model.put("vid", vid);
		} catch (Exception e) {

		}
		return new ModelAndView("AddLhpvToTripSheet", model);
	}
	
	@RequestMapping(value="/addLhpvDetailsToTripSheet", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  addLhpvDetailsToTripSheet(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject						valueOutObject 					= null;
		ValueObject 					object 							= null;
		try {
			
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			
			valueOutObject		= lHPVtoTripSheetLinkingService.saveLhpvDetailsToTripSheet(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("Error : " , e);
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}
}
