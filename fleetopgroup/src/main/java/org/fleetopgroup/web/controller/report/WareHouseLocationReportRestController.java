package org.fleetopgroup.web.controller.report;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.serviceImpl.IWareHouseLocationService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class WareHouseLocationReportRestController {
	
	@Autowired private IWareHouseLocationService		WareHouseLocationService;
	
	@RequestMapping("/WLR")
	public ModelAndView WarehouseLocationReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("WLR", model);
	}
	
	@RequestMapping("/WareHouseLocationWiseCostReport")
	public ModelAndView WareHouseLocationWiseCostReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new 	ModelAndView("WareHouseLocationWiseCostReport",model);	
	}
	
	@RequestMapping(value = "/getWareHouseLocationWiseCostReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getWareHouseLocationWiseCostReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return WareHouseLocationService.getWareHouseLocationWiseCostReport(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	
}