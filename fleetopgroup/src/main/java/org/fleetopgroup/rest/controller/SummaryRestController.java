package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.VehicleCommentBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.model.Company;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyService;
import org.fleetopgroup.persistence.serviceImpl.ISummaryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class SummaryRestController{
	
	@Autowired ISummaryService 						SummaryService;
	@Autowired IVehicleService 						vehicleService;
	@Autowired ICompanyService 						CompanyService;
	@Autowired ICompanyConfigurationService			companyConfigurationService;
	
	VehicleCommentBL VCBL = new VehicleCommentBL();
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value = "/viewSummary", method = RequestMethod.GET)
	public ModelAndView viewSummary(final TripSheetDto tripsheetdto, final HttpServletRequest request) throws Exception {
		ModelAndView 		map 			= new ModelAndView("viewSummary");
		CustomUserDetails	userDetails		= null;
		Company				company			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			company    	= CompanyService.getCompanyByID(userDetails.getCompany_id());
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			
			map.addObject("CompanyId", company.getCompany_id());
			map.addObject("CompanyName", company.getCompany_name());
			map.addObject("permissions", permission);
			map.addObject("SHOW_COMPANYLIST_DASHBOARD", permission.contains(new SimpleGrantedAuthority("SHOW_COMPANYLIST_DASHBOARD")));
			map.addObject("vehicleComment",
					VCBL.prepare_VehicleComment_ListofDto(vehicleService.Get_Recent_Comment_Details(userDetails.getCompany_id())));
			
		
			
		}  catch (Exception e) {
			LOGGER.error("Exception viewSummary : ", e);
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/getCompanyDetailsList", method = RequestMethod.GET)
	public void getCompanyInformationDetails(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	List<Company> companyList = new ArrayList<>();
	ArrayList<Company> 			CompanyDetailsList 				= null;
	try {
		CompanyDetailsList = (ArrayList<Company>) CompanyService.findAll();
	
		if(CompanyDetailsList != null && !CompanyDetailsList.isEmpty()) {
			for (Company CompanyDetails : CompanyDetailsList) {
			Company companyDto = new Company();
			companyDto.setCompany_id(CompanyDetails.getCompany_id());
			companyDto.setCompany_name(CompanyDetails.getCompany_name());
		
			companyList.add(companyDto);
			}
		}
	
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(companyList));
		
		}catch(Exception e) {
			LOGGER.error("Exception addClothTypes : ", e);
		}
	}
	
	/*******************Dashboard Main Counts Data*****************/
	@RequestMapping(value = "/getAllCountData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getAllCountData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getAllCountDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/getTripSheetCountData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripSheetCountData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getTripsheetCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/getFuelCountData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getFuelCountData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getFuelCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/getWorkOrderCountData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getWorkOrderCountData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getWorkOrderCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/getServiceReminderCountData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getServiceReminderCountData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getServiceReminderCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/getRRCountData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRRCountData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getRRCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/getIssueCountData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getIssueCountData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getIssueCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getServiceEntryCountData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getServiceEntryCountData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getServiceEntryCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getPickAndDropCount", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPickAndDropCount(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getPickAndDropCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	/************************Workorder Summary***********************/
	
	@RequestMapping(value = "/getWorkOrderSummaryData", method = RequestMethod.GET)//Return WorkOrder JSP
	public ModelAndView getWorkOrderSummaryData(@RequestParam("DateRange")  final String DateRange , final HttpServletRequest request) {
		ModelAndView map = new ModelAndView("WorkOrderSummaryData");
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
			map.addObject("DateRange",DateRange);
		}  catch (Exception e) {
			LOGGER.error("Exception getFuelSummaryData : ", e);
			throw e;
		}
		return map;
	}
	
	
	@RequestMapping(value = "/getAllWorkOrderCountData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getAllWorkOrderCountData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getWorkOrderDataCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	@RequestMapping(value = "/getAllLocationWiseWorkOrderCount", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getAllLocationWiseWorkOrderCount(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getLocationWiseWorkOrderDataCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getWorkOrderTableData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getWorkOrderTableData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getWorkOrderTableData(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	/**********************Fuel Summary***********************/
	
	@RequestMapping(value = "/getFuelSummaryData", method = RequestMethod.GET)
	public ModelAndView getFuelSummaryData(@RequestParam("DateRange")  final String DateRange ,
			final TripSheetDto tripsheetdto, final HttpServletRequest request) {
		ModelAndView map = new ModelAndView("fuelSummaryData");
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
			map.addObject("DateRange",DateRange);
		}  catch (Exception e) {
			LOGGER.error("Exception getFuelSummaryData : ", e);
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/getFuelData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getFuelData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getFuelDataCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getFuelTableData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getFuelTableData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getFuelTableData(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	/**************************Issue Summary
	  ****************************/
	
	@RequestMapping(value = "/getIssueSummaryData", method = RequestMethod.GET)   // Return ISUUE JSp
	public ModelAndView getIssueSummaryData(@RequestParam("DateRange")  final String DateRange, final HttpServletRequest request) throws Exception {
		ModelAndView map = new ModelAndView("IssueSummaryData");
		
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			map.addObject("configuration",configuration);
			map.addObject("permissions", permission);
			map.addObject("DateRange", DateRange);
		}  catch (Exception e) {
			LOGGER.error("Exception getFuelSummaryData : ", e);
			throw e;
		}
		return map;
	}
	
	
	@RequestMapping(value = "/getAllIssueCountData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getAllIssueCountData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getIssueDataCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getIssueTableData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getIssueTableData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getIssueTableData(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	/*********************TripSheet Summary*****************************/
	
	@RequestMapping(value = "/getTripSheetSummaryData", method = RequestMethod.GET)
	public ModelAndView getTripSheetSummaryData(@RequestParam("DateRange")  final String DateRange ,final TripSheetDto tripsheetdto, 
			final HttpServletRequest request) {
		ModelAndView map = new ModelAndView("tripSheetSummaryData");
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
			map.addObject("DateRange",DateRange);
		}  catch (Exception e) {
			LOGGER.error("Exception getTripSheetSummaryData : ", e);
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/getTripSheetData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripSheetData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getTripSheetDataCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getTripSheetTableData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripSheetTableData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getTripSheetTableData(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	/*************************Renewal Reminder Summary****************/
	
	@RequestMapping(value = "/getRenewalReminderSummaryData", method = RequestMethod.GET)
	public ModelAndView getRenewalReminderSummaryData(@RequestParam("DateRange")  final String DateRange ,final TripSheetDto tripsheetdto, 
			final HttpServletRequest request) {
		ModelAndView map = new ModelAndView("renewalReminderSummaryData");
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
			map.addObject("DateRange",DateRange);
		}  catch (Exception e) {
			LOGGER.error("Exception renewalReminderSummaryData : ", e);
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/getRenewalReminderData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalReminderData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getRenewalReminderDataCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getRenewalReminderTableData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalReminderTableData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getRenewalReminderTableData(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	/********************Service Reminder Summary**********************/
	
	@RequestMapping(value = "/getServiceReminderSummaryData", method = RequestMethod.GET)
	public ModelAndView getServiceReminderSummaryData(@RequestParam("DateRange")  final String DateRange, 
			final TripSheetDto tripsheetdto, final HttpServletRequest request) {
		ModelAndView map = new ModelAndView("serviceReminderSummaryData");
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
			map.addObject("DateRange",DateRange);
		}  catch (Exception e) {
			LOGGER.error("Exception serviceReminderSummaryData : ", e);
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/getServiceReminderData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getServiceReminderData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getServiceReminderDataCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getServiceReminderTableData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getServiceReminderTableData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getServiceReminderTableData(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	/************************Service Entry Summary***********************/
	
	@RequestMapping(value = "/getServiceEntrySummaryData", method = RequestMethod.GET)
	public ModelAndView getServiceEntrySummaryData(@RequestParam("DateRange")  final String DateRange, final HttpServletRequest request) {
		ModelAndView map = new ModelAndView("serviceEntrySummaryData");
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
			map.addObject("DateRange", DateRange);
		}  catch (Exception e) {
			LOGGER.error("Exception serviceEntrySummaryData : ", e);
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/getAllSECountData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getAllSECountData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getServiceEntryDataCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getServiceEntryTableData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getSETableData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getServiceEntryTableData(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
/**************************Pick And Drop Summary****************************/
	
	@RequestMapping(value = "/getPickAndDropSummaryData", method = RequestMethod.GET)// Return ISUUE JSp
	public ModelAndView getPickAndDropSummaryData(@RequestParam("DateRange")  final String DateRange, final HttpServletRequest request) {
		ModelAndView map = new ModelAndView("PickAndDropSummaryData");
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
			map.addObject("DateRange", DateRange);
		}  catch (Exception e) {
			LOGGER.error("Exception PickAndDropSummaryData : ", e);
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/getPickAndDropDataCount", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPickAndDropDataCount(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getPickAndDropDataCount(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getPickAndDropTableData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPickAndDropTableData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getPickAndDropTableData(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/tyreStockCount", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  tyreStockCount(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return SummaryService.tyreStockCount(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@RequestMapping(value = "/viewTyreSummary", method = RequestMethod.GET)// Return ISUUE JSp
	public ModelAndView viewTyreSummary(final HttpServletRequest request) {
		ModelAndView map = new ModelAndView("viewTyreSummary");
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
			map.addObject("compId", userDetails.getCompany_id());
		}  catch (Exception e) {
			LOGGER.error("Exception PickAndDropSummaryData : ", e);
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/getTyreStockDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTyreStockDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getTyreStockDetails(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@RequestMapping(value = "/getAllLocationTyreStockDetailsByStatus", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getAllLocationTyreStockDetailsByStatus(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getAllLocationTyreStockDetailsByStatus(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@RequestMapping(value = "/getAssignedTyreAllocation", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getAssignedTyreAllocation(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getAssignedTyreAllocation(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@RequestMapping(value = "/getMaxTyreRun", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getMaxTyreRun(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getMaxTyreRun(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getMinTyreRun", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getMinTyreRun(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getMinTyreRun(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getVehicleWithoutTyre", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleWithoutTyre(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getVehicleWithoutTyre(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@RequestMapping(value = "/getIssueTyreDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getIssueTyreDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return SummaryService.getIssueTyreDetails(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
 
}