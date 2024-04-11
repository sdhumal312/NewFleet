package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.bl.BatteryBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.IBatteryService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping(value = "/BatteryWS")
public class BatteryWS {
	@Autowired IBatteryService   		batteryService;
	@Autowired ICompanyService 			companyService;	
	BatteryBL batteryBL = new BatteryBL();
	
	@RequestMapping(value="/getBatteryInventoryPrint", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getWorkOrderPrint(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		
		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		CustomUserDetails			userDetails						= null;
		long						batteryId						= 0;

		try {
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			batteryId			= object.getLong("batteryId", 0);
			
			valueOutObject.put("company_id", userDetails.getCompany_id());
			valueOutObject.put("companyDetails", companyService.getCompanyByID(userDetails.getCompany_id()));
			valueOutObject.put("Battery", batteryBL.getFinalBatteryDto((batteryService.getBatteryInfo(batteryId,userDetails.getCompany_id()))));
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
			userDetails						= null;
			batteryId						= 0;   
		}
	}
	
	@RequestMapping(value="getBatteryStockReport", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getBatteryStockReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= batteryService.getBatteryStockReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	

}
