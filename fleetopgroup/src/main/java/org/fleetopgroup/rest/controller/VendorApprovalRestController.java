package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.serviceImpl.IVendorApprovalService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class VendorApprovalRestController {

	@Autowired	IVendorApprovalService		vendorApprovalService;
	
	@PostMapping(path = "/createVendorApproval", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  createVendorApproval(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject valueObject = null;

		try {
			valueObject  = new ValueObject(allRequestParams);
			valueObject.put("selectedInvoice", JsonConvertor.toValueObjectFromJsonString(valueObject.getString("selectedInvoice")));
			return vendorApprovalService.createVendorApproval(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@PostMapping(path = "/makeVendorApprovalPayment", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  makeVendorApprovalPayment(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject valueObject = null;

		try {
			valueObject  = new ValueObject(allRequestParams);
			valueObject.put("selectedInvoice", JsonConvertor.toValueObjectFromJsonString(valueObject.getString("selectedInvoice")));
			return vendorApprovalService.makeVendorApprovalPayment(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@GetMapping("/viewApprovalEntries")
	public ModelAndView viewServiceEntries(final TripSheetDto tripsheetdto, final HttpServletRequest request) {
		
		return new ModelAndView("viewApprovalEntries");
	}
	
	@PostMapping(path = "/getApprovalListByStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getApprovalListByStatus(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject valueObject = null;

		try {
			valueObject  = new ValueObject(allRequestParams);
			return vendorApprovalService.getApprovalListByStatus(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@PostMapping(path = "/removeInvoiceFromApproval", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  removeInvoiceFromApproval(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject valueObject = null;

		try {
			valueObject  = new ValueObject(allRequestParams);
			return vendorApprovalService.removeInvoiceFromApproval(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@PostMapping(path = "/approveVendorApprovalEntry", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  approveVendorApprovalEntry(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject valueObject = null;

		try {
			valueObject 		= new ValueObject(allRequestParams);
			valueObject.put("invoiceDetails", JsonConvertor.toValueObjectFromJsonString(valueObject.getString("invoiceDetails")));
			return vendorApprovalService.approveVendorApprovalEntry(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
}
