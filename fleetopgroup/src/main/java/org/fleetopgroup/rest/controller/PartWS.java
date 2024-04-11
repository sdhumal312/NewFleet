package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IPartCategoriesService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IPartReportService;
import org.fleetopgroup.persistence.serviceImpl.ITripCollectionService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/PartWS")
public class PartWS {

	@Autowired ITripCollectionService   iTripCollectionService;
	@Autowired ITripSheetService   		iTripSheetService;
	@Autowired IPartCategoriesService   PartCategoriesService;
	@Autowired IPartReportService		partReportService;
	@Autowired
	private IPartLocationsService PartLocationsService;
	@Autowired 
	IInventoryService 					inventoryService;

	@RequestMapping(value = "/getParts_Count_Report", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getParts_Count_Report(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		ValueObject		valueOutObject 	= null;
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = PartCategoriesService.getMostPartConsumedReport(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/getPartWiseConsumptionReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPartWiseConsumptionReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return partReportService.getPartWiseConsumtionReport(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	@RequestMapping(value = "/getPartStockReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPartStockReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return partReportService.getPartStockReport(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/getPartPurchaseInvoiceReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPartPurchaseInvoiceReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 				= null;
		ValueObject					valueOutObject 		= null;
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = partReportService.getPartPurchaseInvoiceReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	//Technician Wise Part Report Start
	@RequestMapping(value = "/getTechnicianWisePartReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTechnicianWisePartReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 	object 			= null;
		ValueObject		valueOutObject 	= null;
		try {
			object              = new ValueObject(allRequestParams);			
			valueOutObject      = partReportService.getTechnicianWisePartReport(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	//Technician Wise Part Report Stop..
	@PostMapping(value="/getSubLocationByMainLocationId", produces="application/json")
	public HashMap<Object, Object>  getSubLocationByMainLocationId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= PartLocationsService.getSubLocationByMainLocationId(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@RequestMapping(value = "/getPartRequisitionStatusWiseReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPartRequisitionStatusWiseReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return partReportService.getPartRequisitionStatusWiseReport(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
}