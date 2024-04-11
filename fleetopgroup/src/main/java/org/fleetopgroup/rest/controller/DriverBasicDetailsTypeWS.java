package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.model.DriverBasicDetailsType;
import org.fleetopgroup.persistence.serviceImpl.IDriverBasicDetailsTypeService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class DriverBasicDetailsTypeWS {
	@Autowired IDriverBasicDetailsTypeService   driverBasicDetailsTypeService;
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/driverBasicDetailsTypeAutocomplete")
	public void companyWiseTyreExpenseAutocomplete(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ValueObject							valueObject 						= null;
		List<DriverBasicDetailsType> 		driverBasicDetailsTypeList			= null;
		List<DriverBasicDetailsType> 		finalDriverBasicDetailsTypeList		= null;
		DriverBasicDetailsType 				driverBasicDetailsType 				= null;
		
		try {
			valueObject								= new ValueObject();
			driverBasicDetailsTypeList 				= new ArrayList<DriverBasicDetailsType>();
			finalDriverBasicDetailsTypeList 		= new ArrayList<DriverBasicDetailsType>();
			
			valueObject						= driverBasicDetailsTypeService.getDriverAllBasicDetailsTypeList();
			driverBasicDetailsTypeList		= (List<DriverBasicDetailsType>) valueObject.get("DriverBasicDetailsTypeList");
			
			if(driverBasicDetailsTypeList != null && !driverBasicDetailsTypeList.isEmpty()) {	
				for (DriverBasicDetailsType dto : driverBasicDetailsTypeList) {
					driverBasicDetailsType 	= new DriverBasicDetailsType();
					driverBasicDetailsType.setDriverBasicDetailsTypeId(dto.getDriverBasicDetailsTypeId());
					driverBasicDetailsType.setDriverBasicDetailsTypeName(dto.getDriverBasicDetailsTypeName());

					finalDriverBasicDetailsTypeList.add(driverBasicDetailsType);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(finalDriverBasicDetailsTypeList));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			valueObject 						= null;  
			driverBasicDetailsTypeList			= null;  
			driverBasicDetailsType				= null;  
			finalDriverBasicDetailsTypeList		= null;  
		}
	}

	@GetMapping(value = "/driverBasicDetailsType")
	public ModelAndView driverBasicDetailsType(final HttpServletRequest request) {
		ModelAndView 					map 		= null;
		try {
			map = new ModelAndView("driverBasicDetailsType");
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(value="/getDriverAllBasicDetailsType", produces="application/json")
	public HashMap<Object, Object>  getDriverAllBasicDetails() throws Exception {
		try {
			return driverBasicDetailsTypeService.getDriverAllBasicDetailsTypeList().getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@PostMapping(value="/addDriverBasicDetailsType", produces="application/json")
	public HashMap<Object, Object>  addDriverBasicDetailsType(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;

		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= driverBasicDetailsTypeService.saveDriverBasicDetailsType(valueInObject);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@PostMapping(value="/getDriverBasicDetailsType", produces="application/json")
	public HashMap<Object, Object>  getDriverBasicDetailsType(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;

		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= driverBasicDetailsTypeService.getDriverBasicDetailsType(valueInObject);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@PostMapping(value="/updateDriverBasicDetailsType", produces="application/json")
	public HashMap<Object, Object>  updateDriverBasicDetailsType(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;

		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= driverBasicDetailsTypeService.updateDriverBasicDetailsType(valueInObject);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@PostMapping(value="/deleteDriverBasicDetailsType", produces="application/json")
	public HashMap<Object, Object>  deleteDriverBasicDetailsType(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;
		
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= driverBasicDetailsTypeService.deleteDriverBasicDetailsType(valueInObject);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}