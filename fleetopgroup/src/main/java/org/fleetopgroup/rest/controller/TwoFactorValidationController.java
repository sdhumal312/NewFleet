package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IOTPService;
import org.fleetopgroup.persistence.serviceImpl.IUserDetailsCacheService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwoFactorValidationController {
	
	@Autowired IUserProfileService		userProfileService;
	@Autowired IUserDetailsCacheService	userDetailsCacheService;
	@Autowired IOTPService				iOTPService;

	@RequestMapping(value = "/saveTwoFactorEnableDisableState", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveTwoFactorEnableDisableState(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return userProfileService.saveTwoFactorEnableDisableState(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveTwoFactorAuthDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveTwoFactorAuthDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return iOTPService.saveTwoFactorAuthDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getTwoFactorAuthDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTwoFactorAuthDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return iOTPService.getTwoFactorAuthDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/resendOTPValidationCode", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  resendOTPValidationCode(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return iOTPService.resendOTPValidationCode(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getUserDetailsFromCache", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getUserDetailsFromCache(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return userDetailsCacheService.getUserDetailsByKey(object.getLong("id")).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
}
