package org.fleetopgroup.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.fleetopgroup.persistence.serviceImpl.IVendorLorryHirePaymentApprovalService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class VendorLorryHireReportController {

	@Autowired	IVendorLorryHirePaymentApprovalService		vendorLorryHirePaymentApprovalService;
	
	//Vendor Payment History Report Step 1
		@RequestMapping("/LorryHirePaymenReport")
		public ModelAndView VendorPaymentHistoryReport() {
			
			Map<String, Object> model = new HashMap<String, Object>();
					
			return new ModelAndView("LorryHirePaymenReport", model);
		}
		
		@RequestMapping(value = "/getVendorLorryPaymentDetailsReport", method = RequestMethod.POST, produces="application/json")
		public HashMap<Object, Object>  getVendorLorryPaymentDetailsReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
			ValueObject 	object 			= null;
			ValueObject		valueOutObject 	= null;
			try {
				object              = new ValueObject(allRequestParams);				
				valueOutObject      = vendorLorryHirePaymentApprovalService.getVendorLorryPaymentDetailsReport(object);
				return valueOutObject.getHtData();
			} catch (Exception e) {
				throw e;
			} finally {
			}
		}
		@RequestMapping(value = "/getVendorLorryPaymentOutStandingReport", method = RequestMethod.POST, produces="application/json")
		public HashMap<Object, Object>  getVendorLorryPaymentOutStandingReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
			ValueObject 	object 			= null;
			ValueObject		valueOutObject 	= null;
			try {
				object              = new ValueObject(allRequestParams);				
				valueOutObject      = vendorLorryHirePaymentApprovalService.getVendorLorryPaymentOutStandingReport(object);
				return valueOutObject.getHtData();
			} catch (Exception e) {
				throw e;
			} finally {
			}
		}
		
		//Vendor Lorry Hire Details Report Start By Devy Step 1
		@RequestMapping("/VendorLorryHireDetailsReport")
		public ModelAndView VendorLorryHireDetailsReport() {
			
			Map<String, Object> model = new HashMap<String, Object>();
					
			return new ModelAndView("VendorLorryHireDetailsReport", model);
		}
		
		@RequestMapping("/VendorLorryHireOutstandingReport")
		public ModelAndView VendorLorryHireOutstandingReport() {
			
			Map<String, Object> model = new HashMap<String, Object>();
					
			return new ModelAndView("VendorLorryHireOutstandingReport", model);
		}
		
		
		//Vendor Lorry Hire Details Report Start By Devy Step 2
		@RequestMapping(value = "/getVendorLorryHireDetailsReport", method = RequestMethod.POST, produces="application/json")
		public HashMap<Object, Object>  getVendorLorryHireDetailsReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
			
			ValueObject 	object 			= null;
			ValueObject		valueOutObject 	= null;
			try {
				object = new ValueObject(allRequestParams);
				valueOutObject = vendorLorryHirePaymentApprovalService.getVendorLorryHireDetailsReport(object);
				return valueOutObject.getHtData();
			} catch (Exception e) {
				throw e;
			} finally {
			}
		}
		//Vendor Lorry Hire Details Report Stop By Devy Step 2
}
