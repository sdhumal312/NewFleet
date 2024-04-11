package org.fleetopgroup.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IVCargoShedulers {
	
	@Autowired ICashPaymentService 			cashPaymentService;
	@Autowired IBankPaymentService			bankPaymentService;
	
	@RequestMapping(value="runCashStatementScheduler", produces="application/json", method=RequestMethod.POST)
	public String  runCashStatementScheduler(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		try {
			cashPaymentService.sendCashPaymentListToIV(null);
			return "Cash Statement Pojo Completed Succesfully !";
		} catch (Exception e) {
			throw e;
		}
	}
	@RequestMapping(value="runBankStatementScheduler", produces="application/json", method=RequestMethod.POST)
	public String  runBankStatementScheduler(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		try {
			bankPaymentService.sendBankStateMentToIv(null);
			return "Cash Statement Pojo Completed Succesfully !";
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value="insertMissingBankPayment", produces="application/json", method=RequestMethod.POST)
	public String  insertMissingBankPayment(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		try {
			bankPaymentService.insertMissingBankPayment(new ValueObject(allRequestParams));
			
			return "Bank Statement Pojo Completed Succesfully !";
			
		} catch (Exception e) {
			throw e;
		}
	}
}
