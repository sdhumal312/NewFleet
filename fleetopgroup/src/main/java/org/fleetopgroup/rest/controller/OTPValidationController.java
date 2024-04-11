package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.serviceImpl.IOTPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OTPValidationController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired private IOTPService	oTPService;

	@RequestMapping(value = "/checkForOTPValidated", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  checkForOTPValidated(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest	request, HttpServletResponse response) throws Exception {

		try {
			
			oTPService.validateIdOTPValidated(request, response);
			return null;
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		} 
	}
}
