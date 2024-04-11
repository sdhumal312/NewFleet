package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.UreaManufacturer;
import org.fleetopgroup.persistence.serviceImpl.IUreaManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class UreaManufacturerWS {
	
	@Autowired	private IUreaManufacturerService	ureaManufacturerService;
	
	
	@RequestMapping(value = "/getUreaManufacturerList", method = RequestMethod.POST)
	public void getUreaManufacturerList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<UreaManufacturer>   ClothTypesList	= new ArrayList<>();
			List<UreaManufacturer>   manufacturers	= ureaManufacturerService.getUreaManufacturerList(term, userDetails.getCompany_id());
			
			if(manufacturers != null && !manufacturers.isEmpty()) {
				for(UreaManufacturer clothTypes : manufacturers) {
					ClothTypesList.add(clothTypes);
				}
			}
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(ClothTypesList));
	}
}
