package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IPurchasePartForVehicleService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchasePartForVehicleWS {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired 	IPurchasePartForVehicleService   	purchasePartForVehicleService   ;
	
	
	@RequestMapping(value="/addPurchasePartForVehicle", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  addVehicleModel(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueInObject.put("vehicleDetailsFinalList", JsonConvertor.toValueObjectFromJsonString(valueInObject.getString("vehicleDetailsFinalList")));
			valueOutObject		= purchasePartForVehicleService.savePurchasePartForVehicle(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("e"+e);
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value="/getAllPurchasePartForVehicle", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getAllPurchasePartForVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= purchasePartForVehicleService.getAllPurchasePartForVehicle(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	
	@RequestMapping(value="/deletePurchasePartForVehicle", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  deletePurchasePartForVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= purchasePartForVehicleService.deletePurchasePartForVehicle(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	
}
