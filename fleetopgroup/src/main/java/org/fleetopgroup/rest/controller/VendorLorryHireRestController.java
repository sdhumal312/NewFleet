package org.fleetopgroup.rest.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.TripSheetBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.VendorMarketLorryHireDetailsDto;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IVendorLorryHireDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVendorLorryHirePaymentApprovalService;
import org.fleetopgroup.persistence.serviceImpl.IVendorMarketLorryHireDetailsService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class VendorLorryHireRestController {

	@Autowired	IVendorLorryHireDetailsService		vendorLorryHireDetailsService;
	@Autowired	IVendorMarketLorryHireDetailsService		vendorMarketLorryHireDetailsService;//newy
	@Autowired	IVendorLorryHirePaymentApprovalService		vendorLorryHirePaymentApprovalService;//newy
	@Autowired	ICompanyConfigurationService				companyConfigurationService;
	@Autowired	ITripSheetService							tripSheetService;
	
	TripSheetBL	tripSheetBL = new TripSheetBL();
	
	
	@RequestMapping(value = "/addVendorLorryHireDetails")
	public ModelAndView addVendorPaymentSheet(@ModelAttribute("command") TripSheetDto TripSheetDto,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> 		model 				= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>	configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.LORRY_HIRE_CONFIG);
			
			model				= new HashMap<String, Object>();
			
			 if(TripSheetDto.getTripSheetID() != null) {
				
				model.put("tripSheetId",TripSheetDto.getTripSheetID());
				
				TripSheetDto	trip		= tripSheetService.getTripSheetDetails(TripSheetDto.getTripSheetID(), userDetails);
				model.put("tripSheet", tripSheetBL.GetTripSheetDetails(trip));
			}else {
				model.put("noTripSheet", true);
			}
			
			model.put("configuration", configuration);
			model.put("companyId", userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
			
			return new ModelAndView("addVendorLorryHire", model);
		} catch (Exception e) {
			throw e;
		} finally {
			model				= null;
		}
	}
	
	@RequestMapping(value="/saveVendorLorryHireDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveVendorLorryHireDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			object.put("expenseDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("expenseDetails")));
			
			return vendorLorryHireDetailsService.saveVendorLorryHireDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/viewVendorLorryHireDetails", method = RequestMethod.GET)
	public ModelAndView  viewVendorPaymentList() throws Exception {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		try {
			
			return new ModelAndView("viewVendorLorryHireList", model);
		} catch (NullPointerException e) {
			throw e;
		} 
	}
	
	@RequestMapping(value="/getPageWiseVendorLorryHireDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getPageWiseVendorLorryHireDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		
		try {
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			
			valueOutObject		= vendorLorryHireDetailsService.getPageWiseVendorLorryHireDetails(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}
	
	@RequestMapping(value = "/ShowLorryHireDetails", method = RequestMethod.GET)
	public ModelAndView  ShowLorryHireDetails(@RequestParam("ID") final Long lorryHireDetailsId,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> 				model 				= new HashMap<String, Object>();
		VendorMarketLorryHireDetailsDto 	paymentStatus   	= null;
		short paymentStatusId   = 0;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("lorryHireDetailsId",lorryHireDetailsId);
			
			paymentStatus = vendorLorryHireDetailsService.getPaymentStatusIdByLorryHireDetailsId(lorryHireDetailsId);
			paymentStatusId = paymentStatus.getPaymentStatusId();
			
			
			model.put("chargeNotPaid", paymentStatusId);
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();

			model.put("permissions", permission);
			
			return new ModelAndView("ShowLorryHireDetails", model);
		} catch (NullPointerException e) {
			throw e;
		} 
	}
	
	@RequestMapping(value="/ShowLorryHireDetailsById", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  ShowLorryHireDetailsById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		
		try {
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			
			valueOutObject		= vendorLorryHireDetailsService.getLorryHireDetailsById(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}
	
	@RequestMapping(value = "/deleteLorryHireChargesDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteLorryHireChargesDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			return vendorLorryHireDetailsService.deleteLorryHireChargesDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteLorryHireInvoice", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteLorryHireInvoice(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			return vendorLorryHireDetailsService.deleteLorryHireInvoice(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/makeLorryHirePayment")
	public ModelAndView makeLorryHirePayment(@ModelAttribute("command") VendorMarketLorryHireDetailsDto detailsDto,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> 		model 				= null;
		try {
			model				= new HashMap<String, Object>();
			model.put("detailsDto", detailsDto);
			
			return new ModelAndView("makeLorryHirePayment", model);
		} catch (Exception e) {
			throw e;
		} finally {
			model				= null;
		}
	}
	
	@RequestMapping(value="/getVendorLorryHirePaymentData", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVendorLorryHirePaymentData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return vendorLorryHireDetailsService.getVendorLorryHirePaymentData(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/savLorryHirePaymentDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  savLorryHirePaymentDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			object.put("lorryHirePaymentDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("lorryHirePaymentDetails")));
			
			return vendorLorryHireDetailsService.savLorryHirePaymentDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveExpenseDetailsInfo", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveExpenseDetailsInfo(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object.put("ExpenseTransfer", JsonConvertor.toValueObjectFromJsonString(object.getString("ExpenseTransfer")));
			
			return vendorLorryHireDetailsService.saveExpenseDetailsInfo(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	@RequestMapping(value="/getVendorPaymentInformation", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVendorPaymentInformation(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= vendorLorryHirePaymentApprovalService.getVendorPaymentInformation(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	
}
