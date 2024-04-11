package org.fleetopgroup.web.controller;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.VehicleBodyMaker;
import org.fleetopgroup.persistence.serviceImpl.IVehicleBodyMakerService;
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
public class VehicleBodyMakerController {

	@Autowired IVehicleBodyMakerService vehicleBodyMakerService;


	@GetMapping(value = "/showVehicleBodyMakerList")
	public ModelAndView showVehicleBodyMakerList() {
		ModelAndView modelView= new ModelAndView("showVehicleBodyMaker");
		try {
			CustomUserDetails	customUserDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			modelView.addObject("list",vehicleBodyMakerService.getVehicleBodyMaker(customUserDetails.getCompany_id()));
			modelView.addObject("companyId", customUserDetails.getCompany_id());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  modelView;
	}

	@PostMapping(value="/saveVehicleBodyMaker",produces=MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object,Object> saveVehicleBodyMaker(@RequestParam HashMap<Object,Object> allReqParam){
		ValueObject valueObject = new ValueObject(allReqParam);
		vehicleBodyMakerService.validateVehicleBodyMaker(valueObject);
		return valueObject.getHtData();
	}
	
	@PostMapping(value = "/deleteVehicleBodyMaker")
	public HashMap<Object, Object> deleteVehicleBodyMaker(@RequestParam HashMap<Object, Object> allReqParam) {
		ValueObject valueObject = new ValueObject(allReqParam);
		vehicleBodyMakerService.deleteVehicleBodyMakerById(valueObject);
		return valueObject.getHtData();
	}
	
	@PostMapping(value="/updateVehicleBodyMaker")
	public HashMap<Object,Object> updateVehicleBodyMaker(@RequestParam HashMap<Object,Object> allReqParam){
		ValueObject valueObject= new ValueObject(allReqParam);
		vehicleBodyMakerService.updateVehicleBodyMaker(valueObject);
		return valueObject.getHtData();
	}
	
	@RequestMapping(value = "/getVehicleBodyMaker", method = RequestMethod.GET)
	public void getVehicleBodyMaker(@RequestParam("search") String search, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<VehicleBodyMaker> vehicleBodyMaker=  vehicleBodyMakerService.getVehicleBodyMakerList(search, userDetails.getCompany_id());
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(vehicleBodyMaker));

		} catch (Exception e) {
			throw e;
		}

	}
	
}
