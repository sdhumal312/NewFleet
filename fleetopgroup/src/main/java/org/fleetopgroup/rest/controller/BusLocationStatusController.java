package org.fleetopgroup.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.fleetopgroup.persistence.service.IBusLocationService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class BusLocationStatusController {

	@Autowired IBusLocationService iBusLocationService;
	
	@RequestMapping(value = "/vehicleLocationStatus", method = RequestMethod.GET)
	public ModelAndView  vehcleLocationStatus() throws Exception {
		Map<String, Object> 		model 			= new HashMap<String, Object>();
		
		try {
			return new ModelAndView("vehicleLocationStatus", model);
		} catch (NullPointerException e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveBusLocationData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object,Object> saveBusLocationData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		try {
			return iBusLocationService.insertBusLocationDto(new ValueObject(allRequestParams)).getHtData();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value = "/getBusLocationReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getBusLocationReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		try {
			return iBusLocationService.getBusLocationReport(new ValueObject(allRequestParams)).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	@RequestMapping(value = "/updateBuslocationOutTime", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object,Object> updateBuslocationOutTime(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		try {
			return iBusLocationService.updateBuslocationOutTime(new ValueObject(allRequestParams)).getHtData() ;
		} catch (Exception e) {
			throw e;
		} 
	}
	
}
