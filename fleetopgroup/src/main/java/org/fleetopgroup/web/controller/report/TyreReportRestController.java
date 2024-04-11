package org.fleetopgroup.web.controller.report;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TyreReportRestController {
	
	@Autowired private ICompanyConfigurationService  companyConfigurationService;
	
	@Autowired
	private IInventoryTyreService iventoryTyreService;
	/*Tyre Purchase Report Code By Dev Yogi Start*/
	@RequestMapping("/TyrePurchaseReport")
	public ModelAndView TyrePurchaseReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Tyre_PurchaseReport_Report", model);
	}
	
	/*Tyre Purchase Report Code By Dev Yogi Start*/
	
	
	
	
	/*@RequestMapping("/TyrePurchaseReport")
	public ModelAndView DepotWiseAttReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Tyre_PurchaseReport_Report", model);
	}*/
	
	@RequestMapping("/vehicleTyreAssignmentHistoryReport")
	public ModelAndView vehicleTyreAssignmentHistoryReport() throws Exception {
		Map<String, Object> 			model 				= null; 
		CustomUserDetails 				userDetails 	    = null;
		HashMap<String, Object> 		configuration	    = null;
		try {
			model 			= new HashMap<String, Object>();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TYRE_INVENTORY_CONFIG);
			
			model.put("configuration", configuration);
			return new 	ModelAndView("vehicleTyreAssignmentHistoryReport",model);	
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@RequestMapping(value = "/getVehicleTyreAssignmentHistoryReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleTyreAssignmentHistoryReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 	object 			= null;
		ValueObject		valueOutObject 	= null;
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = iventoryTyreService.getVehicleTyreAssignmentHistoryReport(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}	
	
	@RequestMapping(value = "/TyreExpenseDetailsReport", method = RequestMethod.GET)
	public ModelAndView TyreExpenseDetailsReport(final HttpServletRequest request) {
		ModelAndView 					map 		= null;
		try {
			map = new ModelAndView("TyreExpenseDetailsReport");
			return map;
		}  catch (Exception e) {
		//	LOGGER.error("Exception : ", e);
			throw e;
		}
	}

}
