package org.fleetopgroup.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IVendorPaymentService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class VendorPaymentRestController {
	
	@Autowired	private IVendorPaymentService			vendorPaymentService;
	@Autowired	private	ICompanyConfigurationService	companyConfigurationService;

	@RequestMapping(value = "/findPendingVendorPayment")
	public ModelAndView findPendingVendorPayment() throws Exception {
		try {
			Map<String, Object> 		model 	= new HashMap<String, Object>();
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_CONFIGURATION_CONFIG);
			model.put("companyId", userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
			model.put("configuration", configuration);
			return new ModelAndView("pendingVendorPayment", model);
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "getPendingVendorPaymentList", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getPendingVendorPaymentList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 							= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vendorPaymentService.getPendingVendorPaymentList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 							= null;
		}
	}
	
	@RequestMapping(value = "/findApprovalForPayment")
	public ModelAndView findApprovalForPayment() throws Exception {
		try {
			Map<String, Object> 		model 	= new HashMap<String, Object>();
			return new ModelAndView("findApprovalForPayment", model);
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "getVendorApprovalForPayment", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getVendorApprovalForPayment(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 							= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vendorPaymentService.getVendorApprovalForPayment(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 							= null;
		}
	}
	
}
