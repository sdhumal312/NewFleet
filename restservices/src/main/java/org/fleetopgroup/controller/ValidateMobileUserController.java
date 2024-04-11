package org.fleetopgroup.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IValidateUser;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidateMobileUserController {
	
	 @Autowired IValidateUser 					validateUser;
	
	@RequestMapping(value="/mobileUserValidate", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  validateUser(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		valueOutObject 		= null;
		ValueObject		object		 		= null;
		try {
			object = new ValueObject(allRequestParams);
			
			valueOutObject = validateUser.validateUser(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@RequestMapping(value="/mobileRegistrationOTP", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  mobileRegistrationOTP(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		valueOutObject 		= null;
		ValueObject		object		 		= null;
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = validateUser.mobileRegistrationOTP(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@RequestMapping(value="/mobileAppUserRegistration", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  mobileAppUserRegistration(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		valueOutObject 		= null;
		ValueObject		object		 		= null;
		try {
			object = new ValueObject(allRequestParams);
			
			valueOutObject = validateUser.mobileAppUserRegistration(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@RequestMapping(value="/updateUserTokenForNotification", produces="application/json", method=RequestMethod.POST)
	public void  updateUserTokenForNotification(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		object		 		= null;
		try {
			object = new ValueObject(allRequestParams);
		    validateUser.updateUserTokenForNotification(object);
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
}
