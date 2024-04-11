package org.fleetopgroup.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.ISummaryService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SummaryMobileController {
	
	@Autowired ISummaryService 						SummaryService;
	
	@RequestMapping(value = "/getTripSheetDataCountOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripSheetDataCountOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getTripSheetDataCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getTripSheetTableDataOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripSheetTableDataOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getTripSheetTableData(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/getFuelDataCountOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getFuelDataCountOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getFuelDataCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getFuelTableDataOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getFuelTableDataOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getFuelTableData(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getWorkOrderDataCountOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getWorkOrderDataCountOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getWorkOrderDataCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getWorkOrderTableDataOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getWorkOrderTableDataOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getWorkOrderTableData(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getLocationWiseWorkOrderDataCountOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getLocationWiseWorkOrderDataCountOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getLocationWiseWorkOrderDataCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getServiceReminderDataCountOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getServiceReminderDataCountOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getServiceReminderDataCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getServiceReminderTableDataOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getServiceReminderTableDataOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getServiceReminderTableData(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getRenewalReminderDataCountOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalReminderDataCountOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getRenewalReminderDataCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getRenewalReminderTableDataOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalReminderTableDataOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getRenewalReminderTableData(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getIssueDataCountOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getIssueDataCountOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getIssueDataCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getIssueTableDataOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getIssueTableDataOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getIssueTableData(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getServiceEntryDataCountOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getServiceEntryDataCountOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getServiceEntryDataCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getServiceEntryTableDataOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getServiceEntryTableDataOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getServiceEntryTableData(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getTripSheetCountDataOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripSheetCountDataOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getTripsheetCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/getFuelCountDataOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getFuelCountDataOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getFuelCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/getWorkOrderCountDataOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getWorkOrderCountDataOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getWorkOrderCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/getServiceReminderCountDataOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getServiceReminderCountDataOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getServiceReminderCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/getRRCountDataOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRRCountDataOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getRRCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/getIssueCountDataOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getIssueCountDataOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getIssueCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getServiceEntryCountDataOnMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getServiceEntryCountDataOnMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getServiceEntryCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
}
