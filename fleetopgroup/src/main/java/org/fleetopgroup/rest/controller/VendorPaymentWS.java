package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.bl.VendorApprovalBL;
import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.serviceImpl.IVendorPaymentService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/vendorPaymentWS")
public class VendorPaymentWS {
	@Autowired IVendorPaymentService   	vendorPaymentService;
	VendorApprovalBL approval = new VendorApprovalBL();
	VendorBL VenBL = new VendorBL();
	
    //Dev Y Code Start for Vendor GST Report Ref:From Below Code
	@RequestMapping(value="/getVendorGstReport", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVendorGstReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		
		try {
			
			object 				= new ValueObject(allRequestParams);
			
			valueOutObject		= new ValueObject();
			
			valueOutObject		= vendorPaymentService.getVendorGstReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}
	//Dev Y Code End for Vendor GST Report
	
	@RequestMapping(value="/getVendorOpeningBalance", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVendorOpeningBalance(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		
		try {
			
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			
			valueOutObject		= vendorPaymentService.getVendorOpeningBalance(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}

	@RequestMapping(value="/saveVendorPayment", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveVendorPayment(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;

		try {
			
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= vendorPaymentService.addVendorPayment(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}

	@RequestMapping(value="/getVendorPaymentReport", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVendorPaymentReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		
		try {
			
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			
			valueOutObject		= vendorPaymentService.getVendorPaymentReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}
	
	@RequestMapping(value="/getVendorPaymentDetailsById", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVendorPaymentDetailsById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		
		try {
			
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			
			valueOutObject		= vendorPaymentService.getVendorPaymentDetailsById(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}

	@RequestMapping(value="/getPageWiseVendorPaymentDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getPageWiseVendorPaymentDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		
		try {
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			
			valueOutObject		= vendorPaymentService.getPageWiseVendorPaymentDetails(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}

	@RequestMapping(value="/deleteVendorPayment", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  deleteVendorPayment(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		
		try {
			
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= vendorPaymentService.deleteVendorPayment(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}

	@RequestMapping(value="/updateVendorPayment", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateVendorPayment(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		
		try {
			
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			
			valueOutObject		= vendorPaymentService.updateVendorPayment(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}
	
	@RequestMapping(value="/createVendorPaymentApproval", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  createVendorPaymentApproval(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		
		try {
			
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			valueOutObject.put("partInvoicePayment", JsonConvertor.toValueObjectFromJsonString(object.getString("partInvoicePayment")));
			valueOutObject.put("vendorID", object.get("vendorID"));
			return vendorPaymentService.VendorPaymentApproval(valueOutObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}
	
	
	@RequestMapping(value="/createFuelVendorPaymentApproval", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  createFuelVendorPaymentApproval(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		try {
			
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			valueOutObject.put("partInvoicePayment", JsonConvertor.toValueObjectFromJsonString(object.getString("partInvoicePayment")));
			valueOutObject.put("vendorID", object.get("vendorID"));
			return vendorPaymentService.VendorFuelPaymentApproval(valueOutObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}
	
	
	@RequestMapping(value="/getPartiallyPaidApproval", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getPartiallyPaidApproval(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		
		try {
			
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			valueOutObject.put("invoiceId", object.get("invoiceId"));
			return vendorPaymentService.getPartiallyPaidApprovalDetails(valueOutObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}
	
	//Vendor Payment History Report Start
		@RequestMapping(value = "/getVendorPaymentHistoryReport", method = RequestMethod.POST, produces="application/json")
		public HashMap<Object, Object>  getVendorPaymentHistoryReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
			ValueObject 	object 			= null;
			ValueObject		valueOutObject 	= null;
			try {
				object              = new ValueObject(allRequestParams);				
				valueOutObject      = vendorPaymentService.getVendorPaymentHistoryReport(object);
				return valueOutObject.getHtData();
			} catch (Exception e) {
				throw e;
			} finally {
			}
		}
	//Vendor Payment History Report Stop
		
		@RequestMapping(value="/getVendorWisePaymentStatusReport", produces="application/json", method=RequestMethod.POST)
		public HashMap<Object, Object>  getVendorWisePaymentStatusReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
			ValueObject 				object 							= null;
			try {
				object 				= new ValueObject(allRequestParams);
				return vendorPaymentService.getVendorWisePaymentStatusReport(object).getHtData();
				
			} catch (Exception e) {
				throw e;
			} finally {
				object 	= null;
			}
		}
	
}