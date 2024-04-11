package org.fleetopgroup.rest.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.TripSheetBL;
import org.fleetopgroup.persistence.bl.UreaEntriesBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.UreaEntriesDto;
import org.fleetopgroup.persistence.dto.UreaInvoiceToDetailsDto;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUreaEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IUreaInvoiceToDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class UreaEntriesController {
	
	@Autowired IPartLocationPermissionService 				partLocationPermissionService;
	@Autowired IUreaInvoiceToDetailsService					ureaInvoiceToDetailsService;
	@Autowired IUreaEntriesService							ureaEntriesService;
	@Autowired ITripSheetService							TripSheetService;
	@Autowired ICompanyService								companyService;
	@Autowired IVehicleService								vehicleService;
	@Autowired ICompanyConfigurationService					companyConfiguration;

	PartLocationsBL partLocationsBL = new PartLocationsBL();
	TripSheetBL		tripSheetBL		= new TripSheetBL();
	UreaEntriesBL	ureaEntriesBL	= new UreaEntriesBL();

	SimpleDateFormat formatSQL 	 	 = new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
	@RequestMapping(value = "/addUreaEntries", method = RequestMethod.GET)
	public ModelAndView addUreaEntries(@ModelAttribute("command") TripSheetDto TripSheetDto, BindingResult result) throws Exception {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
				HashMap<String, Object> configuration= companyConfiguration.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.UREA_CONFIGURATION_CONFIG);
			model.put("partLocationPermission", partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));
			model.put("companyId", userDetails.getCompany_id());
			model.put("configuration", configuration);
			model.put("serverDate", DateTimeUtility.getDateBeforeNoOfDays(0));
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("UreaEntriesAdd", model);
	}
	
	@RequestMapping(value = "/getLocationUreaStockSearchList", method = RequestMethod.POST)
	public void getLocationUreaStockSearchList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		List<UreaInvoiceToDetailsDto> inventory = new ArrayList<UreaInvoiceToDetailsDto>();
		List<UreaInvoiceToDetailsDto> location = ureaInvoiceToDetailsService.getLocationUreaStockDetailsForEntry(term, userDetails.getCompany_id());
		
		if (location != null && !location.isEmpty()) {
			for (UreaInvoiceToDetailsDto add : location) {
				UreaInvoiceToDetailsDto wadd = new UreaInvoiceToDetailsDto();

				wadd.setUreaInvoiceToDetailsId(add.getUreaInvoiceToDetailsId());
				wadd.setQuantity(add.getQuantity());
				wadd.setUsedQuantity(add.getUsedQuantity());
				wadd.setManufacturerId(add.getManufacturerId());
				wadd.setManufacturerName(add.getManufacturerName());
				wadd.setStockQuantity(add.getStockQuantity());
				wadd.setUreaInvoiceNumber(add.getUreaInvoiceNumber());
				wadd.setUreaInvoiceId(add.getUreaInvoiceId());
				wadd.setWareHouseLocation(add.getWareHouseLocation());
				wadd.setLocationName(add.getLocationName());
				wadd.setSubLocation(add.getSubLocation());
				wadd.setUnitprice(add.getUnitprice());
				wadd.setDiscount(add.getDiscount());
				wadd.setTax(add.getTax());
				wadd.setUreaInvoiceId(add.getUreaInvoiceId());

				inventory.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(inventory));
	}
	
	@RequestMapping(value = "/saveUreaEntriesDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveUreaEntriesDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return ureaEntriesService.saveUreaEntriesDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/UreaEntriesShowList", method = RequestMethod.GET)
	public ModelAndView ClothInventory() throws Exception {
		ModelAndView map = new ModelAndView("UreaEntriesShowList");
		try {
			
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
		}  catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/getPageWiseUreaEntriesDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPageWiseUreaEntriesDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return ureaEntriesService.getPageWiseUreaEntriesDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping("/showUreaDetails")
	public ModelAndView showUreaDetails(@RequestParam("Id") final Long ureaEntriesId,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration= companyConfiguration.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.UREA_CONFIGURATION_CONFIG);
			model.putIfAbsent("configuration", configuration);
			model.put("ureaEntriesId", ureaEntriesId);

		} catch (Exception e) {
		}
		return new ModelAndView("Urea_Show", model);
	}
	
	@RequestMapping(value = "/getShowUreaEntryDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getShowUreaEntryDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return ureaEntriesService.getShowUreaEntryDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/searchUreaEntriesByNumber", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchUreaEntriesByNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return ureaEntriesService.searchUreaEntriesByNumber(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/editUreaEntriesInvoice", method = RequestMethod.GET)
	public ModelAndView EditUreaEntriesInvoice(@RequestParam("Id") final Long Invoice_id, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			
			HashMap<String, Object> configuration= companyConfiguration.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.UREA_CONFIGURATION_CONFIG);
			model.putIfAbsent("configuration",configuration);
			model.put("UreaEntriesInvoiceId", Invoice_id);
			model.put("companyId", userDetails.getCompany_id());
			model.put("serverDate", DateTimeUtility.getDateBeforeNoOfDays(0));
			
		} catch (Exception e) {
			System.err.println("Exception "+e);
		}
		return new ModelAndView("EditUreaEntriesInvoice", model);
	}
	
	@RequestMapping(value = "/getUreaEntriesInvoiceDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getUreaEntriesInvoiceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		CustomUserDetails	userDetails				= null;
		try {
			object 				= new ValueObject(allRequestParams);
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			object.put("UreaEntriesInvoice", ureaEntriesService.getUreaEntriesDetailsByInvcId(object.getLong("ureaEntriesInvoiceId"), userDetails.getCompany_id()));
			
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/updateUreaEntriesDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateUreaEntriesDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return ureaEntriesService.updateUreaEntriesDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteUreaEntryById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteUreaEntryById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return ureaEntriesService.deleteUreaEntryById(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/addTripSheetUreaEntries", method = RequestMethod.GET)
	public ModelAndView addTripSheetUreaEntries(@ModelAttribute("command") TripSheetDto TripSheetDto, BindingResult result) throws Exception {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("partLocationPermission", partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));
		
			if(TripSheetDto.getTripSheetID() != null) {
				model.put("tripSheetId",TripSheetDto.getTripSheetID());
				
				TripSheetDto	trip		= TripSheetService.getTripSheetDetails(TripSheetDto.getTripSheetID(), userDetails);
				TripSheetDto	tripBL 		= tripSheetBL.GetTripSheetDetails(trip);
				model.put("TripSheet", tripBL);
				
				List<UreaEntriesDto> ShowAmount = ureaEntriesService.getUreaEntriesDetailsByTripSheetId(TripSheetDto.getTripSheetID(), userDetails.getCompany_id());
				model.put("urea", ShowAmount);
				model.put("totalUrea", ureaEntriesBL.prepareTotal_Tripsheet_Urea_details(ShowAmount));
				model.put("companyId", userDetails.getCompany_id());
			}
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("UreaEntriesTripSheetAdd", model);
	}
	
	@RequestMapping(value = "/getUreaEntriesTripSheetDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getUreaEntriesTripSheetDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		long 				tripSheetId				= 0;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object 							= new ValueObject(allRequestParams);
			tripSheetId						= object.getLong("tripSheetId",0);
			
			if(tripSheetId != 0) {
				
				object.put("tripSheetId",tripSheetId);
				
				TripSheetDto	trip		= TripSheetService.getTripSheetDetails(tripSheetId, userDetails);
				TripSheetDto	tripBL 		= tripSheetBL.GetTripSheetDetails(trip);
				object.put("TripSheet", tripBL);
				
				List<UreaEntriesDto> ShowAmount = ureaEntriesService.getUreaEntriesDetailsByTripSheetId(tripSheetId, userDetails.getCompany_id());
				object.put("urea", ShowAmount);
				object.put("totalUrea", ureaEntriesBL.prepareTotal_Tripsheet_Urea_details(ShowAmount));
			}
			
			return object.getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	@RequestMapping(value = "/getPreNextUreaEntires", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPreNextUreaEntires(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return ureaEntriesService.getPreNextUreaEntires(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/ureaEntriesSearch", method = RequestMethod.GET)
	public ModelAndView ureaEntriesSearch(@ModelAttribute("command") TripSheetDto TripSheetDto, BindingResult result) throws Exception {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("partLocationPermission", partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));
			model.put("companyId", userDetails.getCompany_id());
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("ureaEntriesSearch", model);
	}
	
	@RequestMapping(value = "/searchUreaEntries", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchUreaEntries(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return ureaEntriesService.searchUreaEntries(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(value = "/UraEntryAlertOfAllActiveVehicle", produces="application/json")
	public HashMap<Object, Object>  UraEntryAlertOfAllActiveVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return	 ureaEntriesService.getMissingUreaEntryAlertByCompanyId(userDetails.getCompany_id()).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getLocationUreaStockSearchListByLocationId", method = RequestMethod.POST)
	public void getLocationUreaStockSearchListByLocationId(Map<String, Object> map, @RequestParam("locationId") final Integer locationId,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String query = "AND PL.partlocation_id = "+locationId+"";
		List<UreaInvoiceToDetailsDto> inventory = new ArrayList<UreaInvoiceToDetailsDto>();
		List<UreaInvoiceToDetailsDto> location = ureaInvoiceToDetailsService.getLocationUreaStockDetailsByLocationId(query, userDetails.getCompany_id());
		
		if (location != null && !location.isEmpty()) {
			for (UreaInvoiceToDetailsDto add : location) {
				UreaInvoiceToDetailsDto wadd = new UreaInvoiceToDetailsDto();

				wadd.setUreaInvoiceToDetailsId(add.getUreaInvoiceToDetailsId());
				wadd.setQuantity(add.getQuantity());
				wadd.setUsedQuantity(add.getUsedQuantity());
				wadd.setManufacturerId(add.getManufacturerId());
				wadd.setManufacturerName(add.getManufacturerName());
				wadd.setStockQuantity(add.getStockQuantity());
				wadd.setUreaInvoiceNumber(add.getUreaInvoiceNumber());
				wadd.setUreaInvoiceId(add.getUreaInvoiceId());
				wadd.setWareHouseLocation(add.getWareHouseLocation());
				wadd.setLocationName(add.getLocationName());
				wadd.setSubLocation(add.getSubLocation());
				wadd.setUnitprice(add.getUnitprice());
				wadd.setDiscount(add.getDiscount());
				wadd.setTax(add.getTax());
				wadd.setUreaInvoiceId(add.getUreaInvoiceId());

				inventory.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(inventory));
	}
	
}
