package org.fleetopgroup.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalTypeService;
import org.fleetopgroup.persistence.serviceImpl.ITripExpenseService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
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

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ProfitAndLossReportController {
	
	@Autowired private IProfitAndLossService			profitAndLossService;
	@Autowired private ICompanyConfigurationService 	companyConfigurationService;
	@Autowired private ITripExpenseService				tripExpenseService;
	@Autowired private IRenewalTypeService				renewalTypeService;
	@Autowired private IVehicleService					vehicleService;

	@RequestMapping("/ProfitAndLoss")
	public ModelAndView VehicleReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("ProfitAndLoss", model);
	}
	
	@RequestMapping("/VehicleWiseUsageReport")
	public ModelAndView VehicleWiseUsageReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Vehicle_Usage_Report", model);
	}
	
	@RequestMapping("/GroupWiseUsageReport")
	public ModelAndView GroupWiseUsageReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Group_Usage_Report", model);
	}

	@RequestMapping("/RouteWiseUsageReport")
	public ModelAndView RouteWiseUsageReport() {
		Map<String, Object> 			model 					= null;
		CustomUserDetails 				userDetails 	          = null;
		HashMap<String, Object> 		configuration	          = null;
		
		try {
			model = new HashMap<String, Object>();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_REPORT_CONFIGURATION_CONFIG);
			
			model.put("configuration", configuration);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("Route_Usage_Report", model);
	}
	
	@RequestMapping(value = "/getVehicleUsageReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleCommentReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return profitAndLossService.getVehicleWiseUsageDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/getGroupUsageReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getGroupUsageReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return profitAndLossService.getGroupWiseUsageDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/getRouteWiseUsageReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRouteWiseUsageReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			
			return profitAndLossService.getRouteWiseUsageDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping("/VehicleWiseProfitAndLossReport")
	public ModelAndView VehicleWiseProfitAndLossReport() throws Exception {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			CustomUserDetails 				userDetails		 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();                                               
			HashMap<String, Object> 		configuration	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PROFIT_AND_LOSS_REPORT_CONFIGURATION_CONFIG);  
			model.put("configuration", configuration);
			return new ModelAndView("Vehicle_PL_Report", model);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@RequestMapping(value = "/getVehicleWiseProfitAndLossReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleWiseProfitAndLossReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return profitAndLossService.getVehicleWiseProfitAndLossReport(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping("/GroupWiseProfitAndLossReport")
	public ModelAndView GroupWiseProfitAndLossReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Group_PL_Report", model);
	}
	
	@RequestMapping(value = "/getGroupWiseProfitAndLossReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getGroupWiseProfitAndLossReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return profitAndLossService.getGroupWiseProfitAndLossReport(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/getVehicleIncomeDetailsOfMonthByIncomeId", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleIncomeDetailsOfMonthByIncomeId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return profitAndLossService.getVehicleIncomeDetailsOfMonthByIncomeId(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/getIncomDetailsWithinRangeByIncomeId", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getIncomDetailsWithinRangeByIncomeId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return profitAndLossService.getIncomDetailsWithinRangeByIncomeId(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/getVehicleExpenseDetailsOfMonthByExpenseId", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleExpenseDetailsOfMonthByExpenseId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return profitAndLossService.getVehicleExpenseDetailsOfMonthByExpenseId(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/getVehicleExpenseDetailsOfDateRangeExpenseId", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleExpenseDetailsOfDateRangeExpenseId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return profitAndLossService.getVehicleExpenseDetailsDateRangeExpenseId(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping("/DateWiseProfitAndLossReport")
	public ModelAndView DateWiseProfitAndLossReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("DateWiseProfitAndLossReport", model);
	}
	/*
	 * this  report fetch data  from DateWiseProfitAndLoss table
	 */
	@RequestMapping(value = "/getDateWiseVehicleProfitAndLoss", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDateWiseVehicleProfitAndLoss(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return profitAndLossService.getDateWiseVehicleProfitAndLoss(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	/*
	 * this  report fetch data  from Different table
	 */
	@RequestMapping(value = "/getVehicleProfitAndLossWithinRange", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleProfitAndLossWithinRange(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return profitAndLossService.getVehicleProfitAndLossWithinRange(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping("/RouteWiseProfitAndLossReport")
	public ModelAndView RouteWiseProfitAndLossReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("RouteWiseProfitAndLossReport", model);
	}
	
	@RequestMapping(value = "/getRouteWiseProfitAndLossReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRouteWiseProfitAndLossReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try { 
			object 			= new ValueObject(allRequestParams);
			return profitAndLossService.getRouteWiseProfitAndLoss(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 			= null;
		}
	}
	
	@RequestMapping("/vehicleExpenseComparison")
	public ModelAndView vehicleExpenseComparison() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.put("companyId", userDetails.getCompany_id());
		model.put("userId", userDetails.getId());
		ObjectMapper objectMapper = new ObjectMapper();
		model.put("tripExpenseList", objectMapper.writeValueAsString(tripExpenseService.listTripExpenseForCompare(userDetails.getCompany_id())));
		model.put("renewalList", objectMapper.writeValueAsString(renewalTypeService.findAllRenewalForComparision(userDetails.getCompany_id())));
		model.put("misseleniousExp", objectMapper.writeValueAsString(VehicleExpenseTypeConstant.getVehicleExpenseTypeMap()));
		String fromdate =	DateTimeUtility.getBackDateBasedOnGivenDate(DateTimeUtility.getCurrentDate(), 3);
		model.put("vehicleList", objectMapper.writeValueAsString(vehicleService.getActiveVehicleInDateRange(fromdate, DateTimeUtility.getCurrentDateInString()+DateTimeUtility.DAY_END_TIME ,userDetails.getCompany_id(), userDetails.getId())));
		
		return new ModelAndView("vehicleExpenseComparison", model);
	}
	
	@RequestMapping(value = "/getVehicleComparisionDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleComparisionDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try { 
			object 			= new ValueObject(allRequestParams);
			return profitAndLossService.getVehicleComparisionDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 			= null;
		}
	}
	
}
