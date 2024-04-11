package org.fleetopgroup.rest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.report.bll.TripSheetReportBll;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetMobileService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
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
public class TripSheetReportRestController {

	@Autowired  TripSheetReportBll			tripSheetReportBll;
	@Autowired  ITripSheetMobileService		TripSheetMobileService;
	@Autowired 	ITripSheetService     		tripSheetService;
	
	@RequestMapping("/tripSheetCollectionReport")
	public ModelAndView tripSheetCollectionReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> 		model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("companyId", userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
		}
		catch(Exception e){
			
		}
		return new ModelAndView("tripSheetCollectionReport", model);
	}
	
	@RequestMapping(value = "/getTripSheetStatusCollectionDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripSheetStatusCollectionDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return tripSheetReportBll.getTripSheetStatusCollectionDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/tripIncomeReportByIncomeName", method = RequestMethod.GET)
	public ModelAndView addTripSheetOptions(final HttpServletRequest request) {
		ModelAndView 					map 		= null;
		try {
			map = new ModelAndView("tripIncomeReportByIncomeName");
			return map;
		}  catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value = "/getTripIncomeReportByIncomeName", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripIncomeReportByIncomeName(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return tripSheetReportBll.getTripIncomeReportByIncomeName(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping("/kmComparisonReport")
	public ModelAndView kmComparisonReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("kmComparisonReport", model);
	}
	
	@RequestMapping(value = "/getManualVsGpsKMComparison", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getManualVsGpsKMComparison(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 		= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return TripSheetMobileService.getManualVsGpsKMComparison(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/allGroupTripSheetDateReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  allVehiclesMileageReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
									
			valueOutObject 		= tripSheetService.getAllGroupTripsheetDateReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/getTripsheetDetailsithoutLoadingSheet", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripsheetDetailsithoutLoadingSheet(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
									
			valueOutObject 		= tripSheetService.getTripsheetDetailsithoutLoadingSheet(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/showLSDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  showLSDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
									
			valueOutObject 		= tripSheetService.showLSDetails(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
}
