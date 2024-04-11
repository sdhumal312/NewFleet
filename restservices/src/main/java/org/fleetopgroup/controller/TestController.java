package org.fleetopgroup.controller;

import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.model.BankMaster;
import org.fleetopgroup.persistence.serviceImpl.IBankService;
import org.fleetopgroup.persistence.serviceImpl.ITallyIntegrationService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {
	
	@Autowired IBankService										bankService;
	@Autowired ITallyIntegrationService							tallyIntegrationService;

	@RequestMapping(value = "/pay")
	 public String pay() throws Exception {
		
		List<BankMaster>  bankMaster	=  bankService.getBankList();
		
		System.err.println("bankMaster " + bankMaster.size());
		
		System.err.println("Welcome to Sprng webservices");
		
		
		return "Welcome to Sprng webservices ";
	}
	
	@RequestMapping(value="/getTallyIntegrationData", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getTallyIntegrationData(@RequestBody HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			
			return tallyIntegrationService.getTallyIntegrationData(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
		}
	}
	
	@RequestMapping(value="/getTallyIntegrationDataAtc", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getTallyIntegrationDataAtc(@RequestBody HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			
			return tallyIntegrationService.getTallyIntegrationDataAtc(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
		}
	}
	
	@RequestMapping(value="/updateTallyIntegrationStatus", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateTallyIntegrationStatus(@RequestBody HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			
			return tallyIntegrationService.updateTallyIntegrationStatus(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
		}
	}
	
	@RequestMapping(value="/getTallyCompanyList", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getTallyCompanyList(@RequestBody HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			
			return tallyIntegrationService.getTallyCompanyListForSwingApp(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
		}
	}
	
}
