package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.VehicleAccidentTypeMaster;
import org.fleetopgroup.persistence.report.dao.impl.IVehicleAccidentTypeMasterService;
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
public class VehicleAccidentTypeMasterWS {
 
	@Autowired 	IVehicleAccidentTypeMasterService   	vehicleAccidentTypeMasterService   ;
	
	@GetMapping(value = "/vehicleAccidentTypeMaster")
	public ModelAndView vehicleModel(final HttpServletRequest request) throws Exception {
		ModelAndView 		map 		= null;
		CustomUserDetails	userDetails	= null;
		try {
			map 		= new ModelAndView("vehicleAccidentTypeMaster");
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(value="/getAllVehicleAccidentTypeMaster", produces="application/json")
	public HashMap<Object, Object>  getAllVehicleAccidentTypeMaster(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= vehicleAccidentTypeMasterService.getAllVehicleAccidentTypeMaster(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@RequestMapping(value="/saveVehicleAccidentTypeMaster", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveVehicleAccidentTypeMaster(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= vehicleAccidentTypeMasterService.saveVehicleAccidentTypeMaster(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@RequestMapping(value="/getVehicleAccidentTypeMasterById", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVehicleAccidentTypeMasterById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= vehicleAccidentTypeMasterService.getVehicleAccidentTypeMasterById(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@RequestMapping(value="/updateVehicleAccidentTypeMaster", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateVehicleAccidentTypeMaster(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= vehicleAccidentTypeMasterService.updateVehicleAccidentTypeMaster(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	@RequestMapping(value="/deleteVehicleAccidentTypeMaster", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  deleteVehicleAccidentType(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= vehicleAccidentTypeMasterService.deleteVehicleAccidentTypeMaster(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/getVehicleAccidentTypeMasterDropdown")
	public void getVehicleAccidentTypeDropdown(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<VehicleAccidentTypeMaster> 	vehicleAccidentTypeFinalList 	= null;
		List<VehicleAccidentTypeMaster> 	vehicleAccidentTypeList 		= null;
		VehicleAccidentTypeMaster 		vehicleAccidentType 			= null;
		ValueObject					valueObject						= null;
		CustomUserDetails			userDetails						= null;
		try {
			valueObject						= new ValueObject();
			vehicleAccidentTypeFinalList 	= new ArrayList<VehicleAccidentTypeMaster>();
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("companyId", userDetails.getCompany_id());
			
			valueObject 			= vehicleAccidentTypeMasterService.getAllVehicleAccidentTypeMaster(valueObject);
			vehicleAccidentTypeList = (List<VehicleAccidentTypeMaster>) valueObject.get("VehicleAccidentTypeList");
			
			if (vehicleAccidentTypeList != null && !vehicleAccidentTypeList.isEmpty()) {
				for (VehicleAccidentTypeMaster add : vehicleAccidentTypeList) {
					vehicleAccidentType = new VehicleAccidentTypeMaster();

					vehicleAccidentType.setVehicleAccidentTypeMasterId(add.getVehicleAccidentTypeMasterId());
					vehicleAccidentType.setVehicleAccidentTypeMasterName(add.getVehicleAccidentTypeMasterName());
					vehicleAccidentTypeFinalList.add(vehicleAccidentType);
				}
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(vehicleAccidentTypeFinalList));
		} catch (Exception e) {
			throw e;
		}
	}
}
