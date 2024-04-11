package org.fleetopgroup.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IUserService;
import org.fleetopgroup.validation.EmailExistsException;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
	
	@Autowired	private IUserService	userService;

	@RequestMapping(value = "/saveUserFromIVCargo", method = RequestMethod.POST)
	public HashMap<Object,Object>  saveUserFromIVCargo(@RequestBody HashMap<Object, Object> allRequestParams) throws Exception, EmailExistsException {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return userService.registerUserAccount(object).getHtData() ;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value = "/updateUserStatusFromIVCargo", method = RequestMethod.POST)
	public HashMap<Object,Object>  updateUserStatusFromIVCargo(@RequestBody HashMap<Object, Object> allRequestParams) throws Exception, EmailExistsException {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return userService.updateUserStatusFromIVCargo(object).getHtData() ;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@RequestMapping(value = "/updateIVCargoTxnChecherStatus", method = RequestMethod.POST)
	public HashMap<Object,Object>  updateIVCargoTxnChecherStatus(@RequestBody HashMap<Object, Object> allRequestParams) throws Exception, EmailExistsException {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return userService.updateIVCargoTxnChecherStatus(object).getHtData() ;
		} catch (Exception e) {
			throw e;
		}
	}
	
}
