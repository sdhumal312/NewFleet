package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.TripSheetBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.fleetopgroup.persistence.serviceImpl.IRefreshmentEntryService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class RefreshmentEntryRestController {

	@Autowired IPartLocationPermissionService 				partLocationPermissionService;
	@Autowired ITripSheetService							tripSheetService;
	@Autowired IRefreshmentEntryService						refreshmentEntryService;
	
	PartLocationsBL partLocationsBL = new PartLocationsBL();
	TripSheetBL		tripSheetBL		= new TripSheetBL();
	
	@RequestMapping(value = "/refreshmentEntry", method = RequestMethod.GET)
	public ModelAndView refreshmentEntry(@ModelAttribute("command") TripSheetDto TripSheetDto, BindingResult result) throws Exception {
	
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("partLocationPermission", partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));
		
			if(TripSheetDto.getTripSheetID() != null) {
				model.put("tripSheetId",TripSheetDto.getTripSheetID());
				
				TripSheetDto	trip		= tripSheetService.getTripSheetDetails(TripSheetDto.getTripSheetID(), userDetails);
				TripSheetDto	tripBL 		= tripSheetBL.GetTripSheetDetails(trip);
				model.put("TripSheet", tripBL);
				
			}
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("refreshmentEntry", model);
	}
	
	@RequestMapping(value = "/getInventoryrefreshment", method = RequestMethod.POST)
	public void getInventoryrefreshment(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		List<InventoryDto> inventory = new ArrayList<InventoryDto>();
		List<InventoryDto> location	 = refreshmentEntryService.getRefreshmentEntryList(term, userDetails.getCompany_id());
		
			if(location != null && !location.isEmpty()) {
				inventory.addAll(location);
			}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(inventory));
	}
	
	@RequestMapping(value = "/saveRefreshmentEntriesDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveRefreshmentEntriesDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return refreshmentEntryService.saveRefreshmentEntriesDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}

	
	@RequestMapping(value = "/saveReturnRefreshment", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveReturnRefreshment(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return refreshmentEntryService.saveReturnRefreshment(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}

	@RequestMapping(value = "/getRefreshmentListDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRefreshmentListDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return refreshmentEntryService.getRefreshmentListDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/removeRefreshment", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  removeRefreshment(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return refreshmentEntryService.deleteRefreshmentEntry(object).getHtData();
			
		} catch (Exception e) {
			throw e;
	
		} 
	}
	
	@RequestMapping("/RefreshmentConsumptionReport")
	public ModelAndView RefreshmentConsumptionReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> 		model = new HashMap<String, Object>();
		try {
			
		}
		catch(Exception e){
			
		}
		return new ModelAndView("RefreshmentConsumptionReport", model);
	}
	
	@RequestMapping("/RefreshmentStockReport")
	public ModelAndView RefreshmentStockReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> 		model = new HashMap<String, Object>();
		try {
			
		}
		catch(Exception e){
			
		}
		return new ModelAndView("RefreshmentStockReport", model);
	}

	@RequestMapping(value = "/getRefreshmentConsumptionReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRefreshmentConsumptionReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject 		= refreshmentEntryService.getRefreshmentConsumptionReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/getTripSheetForDate", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripSheetForDate(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return refreshmentEntryService.getTripSheetForDate(object).getHtData();
			
		} catch (Exception e) {
			throw e;
	
		} 
	}
	
	@RequestMapping(value = "/getTripSheetDataForRefreshmentAdd", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripSheetDataForRefreshmentAdd(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetService.getTripSheetDataForRefreshmentAdd(object).getHtData();
			
		} catch (Exception e) {
			throw e;
	
		} 
	}
	
	@RequestMapping(value = "/RefreshmentEntriesList", method = RequestMethod.GET)
	public ModelAndView RefreshmentEntriesList() throws Exception {
		ModelAndView map = new ModelAndView("RefreshmentEntriesList");
		try {
			
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
		}  catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/getPageWiseRefreshmentsDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPageWiseRefreshmentsDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return refreshmentEntryService.getPageWiseRefreshmentsDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
	
		} 
	}
	
	@RequestMapping(value = "/deleteRefreshmentEntry", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteRefreshmentEntry(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return refreshmentEntryService.deleteRefreshmentEntry(object).getHtData();
			
		} catch (Exception e) {
			throw e;
	
		} 
	}
	
	@RequestMapping(value = "/getRefreshmentStockReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRefreshmentStockReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject 		= refreshmentEntryService.getRefreshmentStockReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	@RequestMapping(value = "/searchRefreshmentEntriesByNumber", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchRefreshmentEntriesByNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return refreshmentEntryService.searchRefreshmentEntriesByNumber(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
}
