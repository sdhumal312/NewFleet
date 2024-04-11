package org.fleetopgroup.rest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.serviceImpl.IDriverBasicDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DriverBasicDetailsWS {
	@Autowired IDriverService 					driverService;
	@Autowired IDriverBasicDetailsService   driverBasicDetailsService;
	@Autowired IDriverDocumentService			driverDocumentService;
	DriverBL 				driverBL			 		= new DriverBL();

	@GetMapping(value = "/driverBasicDetails")
	public ModelAndView driverBasicDetails(@RequestParam("driver_id") final Integer Driver_id,
			final HttpServletRequest request, final HttpServletResponse response) {
		Map<String, Object> 		model 				= null;
		DriverDto					driver				= null;
		CustomUserDetails			userDetails 		= null;
		Object[] 					count 				= null;
		Long						documentCount 		= null;
		try {
			model 		= new HashMap<String, Object>();
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driver 		= driverService.getDriver_Header_Show_Details(Driver_id);
			
			if(driver != null) {
				count 			= driverService.countTotal_REMINDER_DOC_COMMENT__AND_PHOTO(Driver_id);
				documentCount 	= driverDocumentService.getDriverDocumentCount(Driver_id, userDetails.getCompany_id());
				
				model.put("driver", driverBL.Show_Driver_Header_Details(driver));
				model.put("ReminderCount", count[0]);
				model.put("CommentCount", count[1]);
				model.put("PhotoCount", count[2]);
				model.put("DocumentCount", documentCount);
		
			}

		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		return new ModelAndView("DriverBasicDetails", model);
	}
	
	@PostMapping(value="/getDriverAllBasicDetails", produces="application/json")
	public HashMap<Object, Object>  getDriverAllBasicDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject = driverBasicDetailsService.getDriverAllBasicDetails(valueInObject);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@PostMapping(value="/addDriverBasicDetails", produces="application/json")
	public HashMap<Object, Object>  addDriverBasicDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;

		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= driverBasicDetailsService.saveDriverBasicDetails(valueInObject);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@PostMapping(value="/getDriverBasicDetails", produces="application/json")
	public HashMap<Object, Object>  getDriverBasicDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;

		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= driverBasicDetailsService.getDriverBasicDetails(valueInObject);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@PostMapping(value="/updateDriverBasicDetails", produces="application/json")
	public HashMap<Object, Object>  updateDriverBasicDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;

		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= driverBasicDetailsService.updateDriverBasicDetails(valueInObject);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@PostMapping(value="/deleteDriverBasicDetails", produces="application/json")
	public HashMap<Object, Object>  deleteDriverBasicDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;
		
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= driverBasicDetailsService.deleteDriverBasicDetails(valueInObject);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}