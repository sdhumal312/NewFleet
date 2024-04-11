package org.fleetopgroup.web.controller.report;

import java.util.HashMap;
import java.util.Map;

import org.fleetopgroup.rest.controller.DriverLedgerAcoountService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
@RestController
public class DriverLedgerAccountController {
	
	
	@Autowired DriverLedgerAcoountService driverLedgerAcoountService;

	@GetMapping(value = "/driverLedgerAccountReport")
	public ModelAndView driverLedgerAccountReport() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
		return new ModelAndView("DriverLedgerAccountReport", model);
	} catch (NullPointerException e) {
		throw e;
	} 

	}
	
	@PostMapping(path="/getDriverLedgerAccountReport",produces="application/json")
	public HashMap<Object, Object> getDriverLedgerAccountReport(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		try {
			
			return driverLedgerAcoountService.getDriverLedgerAccountReport( new ValueObject(allRequestParam)).getHtData();
		} catch (Exception e) {
			throw e;
		}
	}
}





	
	
	
	
	
	
	

