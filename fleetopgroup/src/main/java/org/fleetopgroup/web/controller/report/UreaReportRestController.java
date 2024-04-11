package org.fleetopgroup.web.controller.report;

import java.util.HashMap;
import java.util.Map;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IUreaEntriesService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UreaReportRestController {
	
	@Autowired
	private ICompanyConfigurationService companyConfigurationService;
	@Autowired
	private IUreaEntriesService ureaEntriesService;
	
	@RequestMapping("/UreaReport")
	public ModelAndView WarehouseLocationReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("UreaReport", model);
	}
	
	@RequestMapping("/DateWiseUreaEntryReport")// date wise Urea Report Input
	public ModelAndView dateWiseFuelEntryReport() {
		HashMap<String, Object> 	configuration		= null;
		CustomUserDetails 			userDetails			= null;
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			model.put("configuration",configuration);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new 	ModelAndView("DateWiseUreaEntryReport",model);	
	}
	
	@RequestMapping(value = "/getdateWiseUreaEntryReport", method = RequestMethod.POST, produces="application/json")// date wise Urea Report OutPut
	public HashMap<Object, Object>  dateWiseFuelEntryReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject 		= ureaEntriesService.getdateWiseUreaEntryDetailsReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
}