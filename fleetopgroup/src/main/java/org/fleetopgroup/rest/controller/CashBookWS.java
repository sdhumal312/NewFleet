package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.ICashBookService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cashBookWS")
public class CashBookWS {
	
	@Autowired ICashBookService   cashBookService;
	
	@RequestMapping(value="/updateCashVoucherNoByCashId", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateCashVoucherNoByCashId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= cashBookService.updateCashVoucherNoByCashId(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}	
}