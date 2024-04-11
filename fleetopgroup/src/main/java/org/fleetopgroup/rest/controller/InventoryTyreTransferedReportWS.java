package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/inventoryTyreTransferedReportWS")
public class InventoryTyreTransferedReportWS {
	
	@Autowired
	private IInventoryTyreService iventoryTyreService;
	
	
	@RequestMapping(value="/getInventoryTyreTransferedReport", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getInventoryTyreTransferedReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= iventoryTyreService.getInventoryTyreTransferedReport(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}	
	
	@RequestMapping(value="/getTyreSentForRetreadingReport", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getTyreSentForRetreadingReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= iventoryTyreService.getTyreSentForRetreadingReport(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}	
}