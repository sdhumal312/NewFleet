package com.fleetop.gpsservice.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fleetop.gpsservice.service.GpsApiService;
import com.fleetop.gpsservice.service.MemoryStats;
import com.fleetop.gpsservice.util.ValueObject;

@RestController
public class GpsController {
	
	@Autowired GpsApiService gpsApiService;
	
	@GetMapping("memory-status")
	public MemoryStats getMemoryStatistics() {
	    MemoryStats stats = new MemoryStats();
	    stats.setHeapSize(Runtime.getRuntime().totalMemory());
	    stats.setHeapMaxSize(Runtime.getRuntime().maxMemory());
	    stats.setHeapFreeSize(Runtime.getRuntime().freeMemory());
	    return stats;
	}

	@PostMapping(path="/getOdometerInBetweenDates")
	public Map<Object, Object> getTataOdometerInBetweenDates(@RequestParam Map<Object, Object> allRequestParam){
		return gpsApiService.getInbetweenGpsUsage(allRequestParam);
	}
	
	@PostMapping(path="/getTataOdometer")
	public Map<Object, Object> getTataOdometer(@RequestParam Map<Object, Object> allRequestParam){
		return gpsApiService.getOpeningOrClosingGpsUsage(allRequestParam);
	}
	
	
	@PostMapping(value="/getVehicleGPSOdometerData", produces="application/json", consumes ="application/json")
	public ResponseEntity<String> getVehicleGPSOdometerData(@RequestBody  HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception{
		ValueObject 		object 					= null;

		try {
			object 		= new ValueObject(allRequestParams);
			
			if(object.containsKey("t")) {
				if(request.getHeader("API_KEY") != null && request.getHeader("API_KEY").equalsIgnoreCase("a9b5f86e-44ed-4b2c-8a6e-017a57ca7702")) {
					if(object.getString("t").equalsIgnoreCase("odo")) {
						gpsApiService.saveVehicleGPSDataIntangles(object);
					}
					
					return new ResponseEntity<>(HttpStatus.OK);
				}else {
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
}
