package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.InventoryLocationDto;
import org.fleetopgroup.persistence.dto.PartWarrantyDetailsDto;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IPartWarrantyDetailsService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class PartWarrantyDetailsRestController {
	
	@Autowired IPartWarrantyDetailsService			partWarrantyDetailsService;		
	@Autowired private ICompanyConfigurationService 	companyConfigurationService;
	@Autowired private	IInventoryService 							inventoryService;
	
	@PostMapping(path = "/getPartWarrantyDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getPartWarrantyDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return partWarrantyDetailsService.getPartWarrantyDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/saveWarrantySerialNumber", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveWarrantySerialNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object 				= new ValueObject(allRequestParams);
			object.put("serialDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("serialDetails")));
			
			return partWarrantyDetailsService.saveWarrantySerialNumber(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/getWarrantyDetailsList", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getWarrantyDetailsList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return partWarrantyDetailsService.getWarrantyDetailsList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/getWarrantyPartDetailsByDseId", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getWarrantyPartDetailsByDseId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return partWarrantyDetailsService.getWarrantyPartDetailsByDseId(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/underWarrantyPartDetailsList", method = RequestMethod.POST)
	public void labourAutoComplete(Map<String, Object> map, @RequestParam("term") final String term, @RequestParam("partId") final Long partId,
			 @RequestParam("vid") final Integer vid, @RequestParam("serviceId") final Long serviceId, @RequestParam("invoiceDate") final String invoiceDate,
			 @RequestParam("companyId") final Integer companyId,@RequestParam HashMap<Object, Object> allRequestParams,
			 HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<PartWarrantyDetailsDto> 			underWarrantyList				= null;
		List<PartWarrantyDetailsDto> 			finalUnderWarrantyList			= null;
		ValueObject								valueObject						= null;
		try {
			
			valueObject					= new ValueObject(allRequestParams);
			underWarrantyList 			= new ArrayList<>();
			finalUnderWarrantyList 		= new ArrayList<>();
			
			valueObject					= partWarrantyDetailsService.underWarrantyPartDetailsList(term,valueObject);
			underWarrantyList			= (List<PartWarrantyDetailsDto>) valueObject.get("partWarrantyDetailsList");
			
			
			if(underWarrantyList != null && !underWarrantyList.isEmpty()) {	
				for (PartWarrantyDetailsDto obj : underWarrantyList) {
					PartWarrantyDetailsDto 	pwd 	= new PartWarrantyDetailsDto();
					pwd.setPartWarrantyDetailsId(obj.getPartWarrantyDetailsId());
					pwd.setPartName(obj.getPartName());
					pwd.setPartSerialNumber(obj.getPartSerialNumber());
					pwd.setServiceNumber(obj.getServiceNumber());
					pwd.setServiceId(obj.getServiceId());
					finalUnderWarrantyList.add(pwd);
				}
			}
			
			
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(finalUnderWarrantyList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*@PostMapping(path = "/getPartWarrantyDetailByVidAndPartId", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getPartWarrantyDetailByVidAndPartId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return partWarrantyDetailsService.getPartWarrantyReDetailByVidAndPartId(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}*/
	
	@RequestMapping(value = "/getAlreadyAsignedPartsByVid", method = RequestMethod.POST)
	public void getAlreadyAsignedPartsByVid(Map<String, Object> map, @RequestParam("term") final String term, 
			 @RequestParam HashMap<Object, Object> allRequestParams,
			 HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<PartWarrantyDetailsDto> 			underWarrantyList				= null;
		List<PartWarrantyDetailsDto> 			finalUnderWarrantyList			= null;
		ValueObject								valueObject						= null;
		try {
			
			valueObject					= new ValueObject(allRequestParams);
			underWarrantyList 			= new ArrayList<>();
			finalUnderWarrantyList 		= new ArrayList<>();
			
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			valueObject.put("companyId", userDetails.getCompany_id());
			
			underWarrantyList			= partWarrantyDetailsService.getAlreadyAsignedPartsByVid(term, valueObject);
			
			
			if(underWarrantyList != null && !underWarrantyList.isEmpty()) {	
				for (PartWarrantyDetailsDto obj : underWarrantyList) {
					PartWarrantyDetailsDto 	pwd 	= new PartWarrantyDetailsDto();
					pwd.setPartWarrantyDetailsId(obj.getPartWarrantyDetailsId());
					pwd.setPartSerialNumber(obj.getPartSerialNumber());
					pwd.setServiceId(obj.getServiceId());
					pwd.setServiceNumber(obj.getServiceNumber());
					
					finalUnderWarrantyList.add(pwd);
				}
			}
			
			
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(finalUnderWarrantyList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@PostMapping(path = "/updatePartWarrantyStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  updatePartWarrantyStatus(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			object.put("partWarrantyDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("partWarrantyDetails")));
			
			return partWarrantyDetailsService.updatePartWarrantyStatus(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/showAllWarrantyPart", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  showAllWarrantyPart(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return partWarrantyDetailsService.showAllWarrantyPart(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/removeAssignPartWarrantyDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  updateAssignPart(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return partWarrantyDetailsService.removeAssignPartWarrantyDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/savePartWithWarrantyDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  savePartWithWarrantyDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			object.put("partWarrantyDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("partWarrantyDetails")));
			
			return partWarrantyDetailsService.savePartWithWarrantyDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@GetMapping(value = "/warrantyPartReport")
	public ModelAndView repairViewList(final HttpServletRequest request) throws Exception {
		ModelAndView 		map 			= new ModelAndView("warrantyPartReport");
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}
	
	@PostMapping(value = "/getWarrantyPartList")
	public void getWarrantyPartList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		List<InventoryDto> 			location 		= null;
		
		location = inventoryService.getWarrantyPartList(term, userDetails.getCompany_id());
		if(location == null) {
			location= new ArrayList<>();
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(location));
	}
	
	@GetMapping(value = "/getWarrantyPartDetails")
	public ModelAndView getWarrantyPartDetails(final HttpServletRequest request) throws Exception {
		ModelAndView 		map 			= new ModelAndView("warrantyPartReport");
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}
	
	@PostMapping(path = "/getWarrantyPartDataDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getWarrantyPartDataDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return partWarrantyDetailsService.getWarrantyPartDataDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	@PostMapping(path = "/getWarrantyDetailsListDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getWarrantyDetailsListDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return partWarrantyDetailsService.getWarrantyDetailsListDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}	
	
	
}
