package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IBatteryService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryTransferService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class BatteryTransferReportWS {
	
	@Autowired private IBatteryTransferService batteryTransferService;
	
	@Autowired private IBatteryService		batteryService;
	
	
	@RequestMapping(value = "/batteryReport", method = RequestMethod.GET)
	public ModelAndView batteryReport() {
		return new ModelAndView("BatteryReport");
	}

	@RequestMapping(value = "/batteryTransferReport", method = RequestMethod.GET)
	public ModelAndView batteryTransferReport() {
		return new ModelAndView("BatteryTransferReport");
	}
	
	@RequestMapping(value="/batteryReportWS/getBatteryTransferReport", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getBatteryTransferReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= batteryTransferService.getBatteryTransferReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}	
	
	@RequestMapping(value="/batteryReportWS/getBatteryScrapReport", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getBatteryScrapReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= batteryService.getBatteryScrapReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value = "/batteryScrapReport", method = RequestMethod.GET)
	public ModelAndView batteryScrapReport() {
		return new ModelAndView("BatteryScrapReport");
	}
	
	@RequestMapping(value = "/batteryStockReport", method = RequestMethod.GET)
	public ModelAndView batteryStockReport() {
		return new ModelAndView("BatteryStockReport");
	}
	
	@RequestMapping(value = "/batteryPurchaseInvoiceReport", method = RequestMethod.GET)
	public ModelAndView batteryPurchaseInvoiceReport() {
		return new ModelAndView("batteryPurchaseInvoiceReport");
	}
	
	@RequestMapping(value = "/getBatteryPurchaseInvoiceReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getBatteryPurchaseInvoiceReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 				= null;
		ValueObject					valueOutObject 		= null;
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = batteryService.getBatteryPurchaseInvoiceReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
}