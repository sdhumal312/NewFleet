package org.fleetopgroup.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.serviceImpl.ITollExpensesDetailsService;
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

	@PostMapping(value = "/karinsFasttagExpenses", produces="application/json")
	public ResponseEntity<Object> karinsfasttagexpenses(@RequestBody HashMap<Object, Object> allRequestParams, HttpServletRequest	request) throws Exception {
		try {
			System.err.println("API_KEY : "+request.getHeader("API_KEY"));
			System.err.println("allRequestParams : "+allRequestParams);
			if(request.getHeader("API_KEY") != null && request.getHeader("API_KEY").equalsIgnoreCase("55038d95371af0842bcb893843e377964637a921b8863e0d5d4322828026a784")) {
				
				return new ResponseEntity<Object>(tollExpensesDetailsService.karinsFasttagExpenses(allRequestParams), HttpStatus.OK);
			}else 
				return new ResponseEntity<Object>("UnAuthorized Access!", HttpStatus.UNAUTHORIZED);
			
		} catch (Exception e) {
			throw e;
		} 
	}

}
