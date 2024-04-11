package org.fleetopgroup.rest.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.Labour;
import org.fleetopgroup.persistence.serviceImpl.ILabourService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LabourWS {
	
	@Autowired private ILabourService	labourService;
	
	Date 		currentDateUpdate 	= new Date();                                             
	Timestamp 	currentTimestamp 	= new java.sql.Timestamp(currentDateUpdate.getTime());
	

	@GetMapping(value = "/labour")
	public ModelAndView labour(final HttpServletRequest request) throws Exception {
		ModelAndView 				map 			= null;
		CustomUserDetails			userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map 		= new ModelAndView("labour");
			map.addObject("currentTimestamp", currentTimestamp);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(value="/getAllLabourMaster",produces= "application/json")
	public HashMap<Object, Object> getAllLabourMaster(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject(allRequestParam); 
			return labourService.getAllLabourMaster(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@PostMapping(value="/saveLabour",produces= "application/json")
	public HashMap<Object, Object> saveLabour(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject(allRequestParam); 
			return labourService.saveLabour(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@PostMapping(value="/getLabourMaster",produces= "application/json")
	public HashMap<Object, Object> getLabourMaster(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject(allRequestParam); 
			return labourService.getLabourMaster(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@PostMapping(value="/updateLabour",produces= "application/json")
	public HashMap<Object, Object> updateLabour(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject(allRequestParam); 
			return labourService.updateLabour(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@PostMapping(value="/deleteLabour",produces= "application/json")
	public HashMap<Object, Object> deleteLabour(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject(allRequestParam); 
			return labourService.deleteLabour(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@RequestMapping(value = "/labourAutoComplete", method = RequestMethod.POST)
	public void labourAutoComplete(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Labour> 			labourList				= null;
		List<Labour> 			finalLabourList			= null;
		CustomUserDetails		userDetails				= null;
		try {
			labourList 				= new ArrayList<Labour>();
			finalLabourList 		= new ArrayList<Labour>();
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			labourList				= labourService.getAllLabourMasterByTerm(term,userDetails.getCompany_id());
			if(labourList != null && !labourList.isEmpty()) {	
				for (Labour labourDto : labourList) {
					Labour 	labour 	= new Labour();
					labour.setLabourId(labourDto.getLabourId());
					labour.setLabourName(labourDto.getLabourName());

					finalLabourList.add(labour);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(finalLabourList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
