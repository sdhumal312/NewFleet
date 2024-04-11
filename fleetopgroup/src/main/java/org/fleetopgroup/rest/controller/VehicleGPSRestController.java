package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.serviceImpl.IVehicleGPSDetailsService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleGPSRestController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired	private IVehicleGPSDetailsService	vehicleGPSDetailsService;

	@RequestMapping(value="/getVehicleGPSDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVehicleGPSDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;
		ValueObject			outObject				= null;
		try {
			object 				= new ValueObject(allRequestParams);
			outObject			= vehicleGPSDetailsService.getVehicleGPSData(object);
			if(outObject != null) {
				return vehicleGPSDetailsService.getVehicleGPSData(object).getHtData();
			}else {
				return null;
			}
			
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
			System.err.println("Exception "+e);
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value="/getVehicleGPSDataAtTime", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVehicleGPSDataAtTime(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			object = vehicleGPSDetailsService.getVehicleGPSDataAtSpecifiedTime(object);
			if(object != null) {
				return object.getHtData();
			}else {
				return null;
			}
			
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
			System.err.println("Exception "+e);
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value="/getVehicleGPSDetailsAtSpecifiedTime", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVehicleGPSDetailsAtSpecifiedTime(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleGPSDetailsService.getVehicleGPSDetailsAtSpecifiedTime(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value="/getVehicleGPSOdometerData", produces="application/json", consumes ="application/json", method=RequestMethod.POST)
	public ResponseEntity<String> getVehicleGPSOdometerData(@RequestBody  HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception{
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			
			if(object.containsKey("t")) {
				if(request.getHeader("API_KEY") != null && request.getHeader("API_KEY").equalsIgnoreCase("a9b5f86e-44ed-4b2c-8a6e-017a57ca7702")) {
					if(object.getString("t").equalsIgnoreCase("odo")) {
						vehicleGPSDetailsService.saveVehicleGPSDataIntangles(object);
					}
					
					return new ResponseEntity<>(HttpStatus.OK);
				}else {
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
		}
	}
	
	
	@RequestMapping(value="/getVehicleGPSData", produces="application/json", consumes ="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVehicleGPSData(@RequestBody  HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception{
		ValueObject 		object 					= null;
		ValueObject 		outobject 				= null;

		try {
			object 				= new ValueObject(allRequestParams);
			outobject	 = vehicleGPSDetailsService.saveVehicleGPSDataIdeagami(object);
			if(outobject != null) {
				return outobject.getHtData();
			}else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
		}
		
		
	}
}
