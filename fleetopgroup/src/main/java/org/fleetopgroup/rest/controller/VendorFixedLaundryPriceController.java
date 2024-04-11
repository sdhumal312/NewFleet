package org.fleetopgroup.rest.controller;

import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.IVendorFixedLaundryRateService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class VendorFixedLaundryPriceController {
	
	@Autowired	IVendorFixedLaundryRateService		vendorFixedLaundryRateService;
	
	PartLocationsBL PLBL = new PartLocationsBL();

	@RequestMapping(value = "/VendorLaundryPrice", method = RequestMethod.GET)
	public ModelAndView VendorLaundryPrice(@RequestParam("Id") final Long vendorId,
			final HttpServletRequest request) throws Exception {
		ModelAndView map = new ModelAndView("vendor_Fixed_Laundry");
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
			map.addObject("vendorId", vendorId);
		}  catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/getPageWiseVendorLaundryRate", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPageWiseVendorLaundryRate(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vendorFixedLaundryRateService.getPageWiseVendorLaundryRate(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveVendorLaundryRate", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveVendorLaundryRate(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vendorFixedLaundryRateService.saveVendorLaundryRate(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/updateVendorLaundryRate", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateVendorLaundryRate(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vendorFixedLaundryRateService.updateVendorLaundryRate(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteLaundryRate", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteLaundryRate(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vendorFixedLaundryRateService.deleteLaundryRate(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getLaundryRateById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getLaundryRateById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vendorFixedLaundryRateService.getLaundryRateById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
}
