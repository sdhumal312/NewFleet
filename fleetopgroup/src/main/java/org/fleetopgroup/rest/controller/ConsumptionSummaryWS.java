package org.fleetopgroup.rest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.ConsumptionSummaryConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IConsumptionSummaryService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ConsumptionSummaryWS {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired 	IConsumptionSummaryService   					consumptionSummaryService   ;
	@Autowired private 	ICompanyConfigurationService 			companyConfigurationService;
	
	@GetMapping(value = "/viewConsumption")
	public ModelAndView vehicleModel(final HttpServletRequest request) throws Exception {
		ModelAndView 				map 				= null;
		CustomUserDetails			userDetails			= null;
		HashMap<String, Object> 	configuration		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			map 		= new ModelAndView("viewConsumption");
			map.addObject("companyDropdownConfig", configuration.getOrDefault("showCompanyDropdownInConsumptionSummary", false));
			map.addObject("CompanyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@PostMapping(value="/getFuelConsumptionCount", produces="application/json")
	public HashMap<Object, Object>  getFuelConsumptionCount(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject					= new ValueObject(allRequestParams);
			return consumptionSummaryService.getFuelConsumptionCount(valueOutObject).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@PostMapping(value="/getUreaConsumptionCount", produces="application/json")
	public HashMap<Object, Object>  getUreaConsumptionCount(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				object 					= null;
		try {
			object					= new ValueObject(allRequestParams);
			return consumptionSummaryService.getUreaConsumptionCount(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
		}
	}
	@PostMapping(value="/getUpholsteryConsumptionCount", produces="application/json")
	public HashMap<Object, Object>  getUpholsteryConsumptionCount(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				object 					= null;
		try {
			object					= new ValueObject(allRequestParams);
			return consumptionSummaryService.getUpholsteryConsumptionCount(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
		}
	}

	
	@PostMapping(value="/getPartConsumptionCount", produces="application/json")
	public HashMap<Object, Object>  getPartConsumptionCount(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				object 					= null;
		try {
			object					= new ValueObject(allRequestParams);
			return consumptionSummaryService.getPartConsumptionCount(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
		}
	}
	
	@PostMapping(value="/getTyreConsumptionCount", produces="application/json")
	public HashMap<Object, Object>  getTyreConsumptionCount(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				object 					= null;
		try {
			object					= new ValueObject(allRequestParams);
			Long tyreConsumptionCount		= consumptionSummaryService.getTyreConsumptionCount(object);
			object.put("tyreConsumptionCount", tyreConsumptionCount);
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
		}
	}
	
	@PostMapping(value="/getBatteryConsumptionCount", produces="application/json")
	public HashMap<Object, Object>  getBatteryConsumptionCount(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				object 					= null;
		try {
			object					= new ValueObject(allRequestParams);
			Long batteryConsumptionCount		= consumptionSummaryService.getBatteryConsumptionCount(object);
			object.put("batteryConsumptionCount", batteryConsumptionCount);
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
		}
	}
	
	@PostMapping(value="/getRefreshmentConsumptionCount", produces="application/json")
	public HashMap<Object, Object>  getRefreshmentConsumptionCount(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				object 					= null;
		try {
			object					= new ValueObject(allRequestParams);
			return consumptionSummaryService.getRefreshmentConsumptionCount(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@GetMapping(value = "/viewConsumptionSummaryData")
	public ModelAndView viewConsumptionSummaryData1(final HttpServletRequest request,@RequestParam("data") String finalString) throws Exception {
		Map<String, Object> model = null;
		String[]			stringArr = null; 		
		try {
			model = new HashMap<String, Object>();
			stringArr = finalString.split(",");
			model.put("companyId", stringArr[0]);
			model.put("userId", stringArr[1]);
			model.put("startDate", stringArr[2]);
			model.put("endDate", stringArr[3]);
			model.put("dateType", stringArr[4]);
			model.put("consumptionType", stringArr[5]);
			
			switch (Short.parseShort(stringArr[5])) {
			case ConsumptionSummaryConstant.CONSUMPTION_TYPE_FUEL:
				return new ModelAndView("viewFuelConsumptionData", model); 
			case ConsumptionSummaryConstant.CONSUMPTION_TYPE_UREA:
				return new ModelAndView("viewUreaConsumptionData", model); 
			case ConsumptionSummaryConstant.CONSUMPTION_TYPE_UPHOLSTERY:
				return new ModelAndView("viewUpholsteryConsumptionData", model); 
			case ConsumptionSummaryConstant.CONSUMPTION_TYPE_WO:
				return new ModelAndView("viewWorkOrderConsumptionData", model); 
			case ConsumptionSummaryConstant.CONSUMPTION_TYPE_SE:
				return new ModelAndView("viewServiceEntryConsumptionData", model); 
			case ConsumptionSummaryConstant.CONSUMPTION_TYPE_TYRE:
				return new ModelAndView("viewTyreConsumptionData", model); 
			case ConsumptionSummaryConstant.CONSUMPTION_TYPE_BATTERY:
				return new ModelAndView("viewBatteryConsumptionData", model);
			case ConsumptionSummaryConstant.CONSUMPTION_TYPE_REFRESHMENT:
				return new ModelAndView("viewRefreshmentConsumptionData", model); 
			default:
				return new ModelAndView("viewConsumption", model); 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}
	
	@PostMapping(value="/getConsumptionSummaryData", produces="application/json")
	public HashMap<Object, Object>  getConsumptionSummaryData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				object 					= null;
		try {
			object					= new ValueObject(allRequestParams);
			return consumptionSummaryService.getConsumptionSummaryData(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
		}
	}
	
}
