package org.fleetopgroup.web.controller;

import org.fleetopgroup.persistence.dto.BankPaymentDto;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankPaymentWS {
	
	@Autowired IBankPaymentService bankPaymentService;
	
	@PostMapping(path="/getBankPaymentDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public BankPaymentDto getBankPaymentDetails(@RequestParam("moduleId") Long moduleId ,@RequestParam("moduleIdentifier") Short moduleIdentifier ) {
		return	bankPaymentService.getBankPaymentDetailsDto(moduleId, moduleIdentifier);
		
	}
}
