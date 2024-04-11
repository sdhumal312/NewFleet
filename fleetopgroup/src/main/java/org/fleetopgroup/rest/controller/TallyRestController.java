package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.model.TallyCompany;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.serviceImpl.ITallyIntegrationService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class TallyRestController {
	
	@Autowired private ITallyIntegrationService				TallyIntegrationService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	@RequestMapping(value = "/addTallyCompany", method = RequestMethod.GET)
	public ModelAndView addTripSheetOptions(final TripSheetDto tripsheetdto, final HttpServletRequest request) {
		ModelAndView map = new ModelAndView("addTallyCompany");
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
		}  catch (Exception e) {
			LOGGER.error("Exception addTallyCompany : ", e);
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/saveTallyCompany", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveTallyCompany(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return TallyIntegrationService.saveTallyCompany(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getTallyCompanyList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTallyCompanyList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return TallyIntegrationService.getTallyCompanyList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getTallyCompanyListById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTallyCompanyListById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return TallyIntegrationService.getTallyCompanyListById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/editTallyCompany", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  editTallyCompany(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return TallyIntegrationService.editTallyCompany(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteTallyCompany", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteTallyCompany(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return TallyIntegrationService.deleteTallyCompany(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getTallyCompanySearchList", method = RequestMethod.POST)
	public void getVendorSearchList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= null;
		try {
			List<TallyCompany> tallyCompanyList =	new ArrayList<>();
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			List<TallyCompany> tallyCompany = TallyIntegrationService.searchByTallyCompany(term, userDetails.getCompany_id(), userDetails.getId());
			if(tallyCompany != null && !tallyCompany.isEmpty()) {
				for(TallyCompany tally : tallyCompany) {
					tallyCompanyList.add(tally);
				}
			}
			
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(tallyCompanyList));
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@RequestMapping(value = "/saveTallyCompanyPermission", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveTallyCompanyPermission(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return TallyIntegrationService.saveTallyCompanyPermission(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
}