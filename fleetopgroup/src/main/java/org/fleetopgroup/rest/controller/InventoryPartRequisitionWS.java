package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.model.PartLocations;
import org.fleetopgroup.persistence.serviceImpl.IInventoryRequisitionService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class InventoryPartRequisitionWS {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IPartLocationPermissionService partLocationPermissionService;
	
	@Autowired
	private IInventoryRequisitionService InventoryRequisitionService;
	
	PartLocationsBL PLBL = new PartLocationsBL();
	
	@RequestMapping("/SearchPartRequisition")
	public ModelAndView SearchPartRequisition(@ModelAttribute("command") RenewalReminderDto renewalReminderDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("SearchPartRequisition", model);
	}
	
	
	@RequestMapping(value = "/getPartRequistionLocationList", method = RequestMethod.POST)
	public void getClothTypesList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<PartLocations>   PartLocationList	= new ArrayList<>();
		List<PartLocations>   location	= partLocationPermissionService
				.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id());
			
			if(location != null && !location.isEmpty()) {
				for(PartLocations loactionTypes : location) {
					PartLocationList.add(loactionTypes);
				}
			}
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(PartLocationList));
	}
	
	@RequestMapping(value = "/getSearchPartRequisition", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getSearchPartRequisition(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 				= null;
		ValueObject					valueOutObject 		= null;
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = InventoryRequisitionService.getSearchPartRequisition(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/getPartRequisitionDetailsByINVRID", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPartRequisitionDetailsByINVRID(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		valueInObject 		= null;
		ValueObject 		valueOutObject 		= null;
		try {	
			
			valueInObject       = new ValueObject(allRequestParams);
			valueOutObject      = new ValueObject();
			
			valueOutObject     = InventoryRequisitionService.getPartRequisitionDetailsByINVRID(valueInObject);

			 return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} finally {
			valueInObject 		= null;            
			valueOutObject 		= null;            
		}
	}
	@RequestMapping(value = "/rejectParRequisition", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  rejectParRequisition(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		valueInObject 		= null;
		ValueObject 		valueOutObject 		= null;
		try {	
			valueInObject       = new ValueObject(allRequestParams);
			valueOutObject      = new ValueObject();
			
			valueOutObject     = InventoryRequisitionService.rejectParRequisitionByInventory_transfer_id(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} finally {
			valueInObject 		= null;            
			valueOutObject 		= null;            
		}
	}
}