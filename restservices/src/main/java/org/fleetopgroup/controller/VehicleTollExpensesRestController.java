package org.fleetopgroup.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.model.TollExpenseData;
import org.fleetopgroup.persistence.serviceImpl.ITollExpensesDetailsService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tollapi")
public class VehicleTollExpensesRestController {
	
	@Autowired	ITollExpensesDetailsService		tollExpensesDetailsService;

	@PostMapping(value = "/karinsFasttagExpenses2", produces="application/json")
	public ResponseEntity<Object> karinsfasttagexpenses2(@RequestBody List<TollExpenseData>  tollExpenseList, HttpServletRequest	request) throws Exception {
		ValueObject 		object 					= null;
		try {
			System.err.println("API_KEY : "+request.getHeader("API_KEY"));
			System.err.println("tollExpenseList : "+tollExpenseList);
			if(request.getHeader("API_KEY") != null && request.getHeader("API_KEY").equalsIgnoreCase("a9b5f86e-44ed-4b2c-8a6e-017a57ca7702")) {
				object = tollExpensesDetailsService.karinsFasttagExpenses(tollExpenseList);
				
			}else
				return new ResponseEntity<Object>("UnAuthorized Access!", HttpStatus.UNAUTHORIZED);
			
			return new ResponseEntity<Object>(object.getHtData(), HttpStatus.OK);
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(value = "/karinsFasttagExpenses", produces="application/json")
	public ResponseEntity<Object> karinsfasttagexpenses(@RequestBody HashMap<Object, Object> allRequestParams, HttpServletRequest	request) throws Exception {
		try {
			System.err.println("API_KEY : "+request.getHeader("API_KEY"));
			System.err.println("allRequestParams : "+allRequestParams);
			if(request.getHeader("API_KEY") != null && request.getHeader("API_KEY").equalsIgnoreCase("a9b5f86e-44ed-4b2c-8a6e-017a57ca7702")) {
				
				return new ResponseEntity<Object>(tollExpensesDetailsService.karinsFasttagExpenses(allRequestParams), HttpStatus.OK);
			}else 
				return new ResponseEntity<Object>("UnAuthorized Access!", HttpStatus.UNAUTHORIZED);
			
		} catch (Exception e) {
			throw e;
		} 
	}
}
