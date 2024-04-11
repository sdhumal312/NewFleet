package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.model.VehicleManufacturer;
import org.fleetopgroup.persistence.serviceImpl.IVehicleManufacturerService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
@RestController
public class VehicleManufacturerWS {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired 	IVehicleManufacturerService   	vehicleManufacturerService   ;
	
	@RequestMapping(value = "/vehicleManufacturer", method = RequestMethod.GET)
	public ModelAndView VehicleManufacturer(final HttpServletRequest request) {
		ModelAndView 					map 		= null;
		try {
			map = new ModelAndView("vehicleManufacturer");
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/vehicleManufacturerAutocomplete", method = RequestMethod.GET)
	public void companyWiseTyreExpenseAutocomplete(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ValueObject						valueObject 					= null;
		List<VehicleManufacturer> 		vehicleManufacturerList			= null;
		List<VehicleManufacturer> 		finalVehicleManufacturerList	= null;
		VehicleManufacturer 			vehicleManufacturer 			= null;
		
		try {
			valueObject						= new ValueObject();
			vehicleManufacturerList 		= new ArrayList<VehicleManufacturer>();
			finalVehicleManufacturerList 	= new ArrayList<VehicleManufacturer>();
			
			valueObject				= vehicleManufacturerService.getAllVehicleManufacturer();
			vehicleManufacturerList	= (List<VehicleManufacturer>) valueObject.get("allVehicleManufacturer");
			
			if(vehicleManufacturerList != null && !vehicleManufacturerList.isEmpty()) {	
				for (VehicleManufacturer dto : vehicleManufacturerList) {
					vehicleManufacturer 	= new VehicleManufacturer();
					vehicleManufacturer.setVehicleManufacturerId(dto.getVehicleManufacturerId());
					vehicleManufacturer.setVehicleManufacturerName(dto.getVehicleManufacturerName());

					finalVehicleManufacturerList.add(vehicleManufacturer);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(finalVehicleManufacturerList));
		} catch (Exception e) {
			LOGGER.error("Err", e);
		}finally {
			valueObject 					= null;  
			vehicleManufacturerList			= null;  
			finalVehicleManufacturerList	= null;  
		}
	}
	
	/*@SuppressWarnings("unchecked")
	@RequestMapping(value = "/vehicleManufacturerAutocomplete", method = RequestMethod.POST)
	public void VehicleManufacturerAutocomplete(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		ValueObject						valueObject 					= null;
		List<VehicleManufacturer> 		vehicleManufacturerList			= null;
		List<VehicleManufacturer> 		finalVehicleManufacturerList	= null;
		VehicleManufacturer 			vehicleManufacturer 			= null;
		
		try {
			valueObject						= new ValueObject();
			vehicleManufacturerList 		= new ArrayList<VehicleManufacturer>();
			finalVehicleManufacturerList 	= new ArrayList<VehicleManufacturer>();
			
			valueObject				= vehicleManufacturerService.getVehicleManufacturerAutocomplete(term);
			vehicleManufacturerList	= (List<VehicleManufacturer>) valueObject.get("vehicleManufacturerList");
			
			if(vehicleManufacturerList != null && !vehicleManufacturerList.isEmpty()) {	
				for (VehicleManufacturer dto : vehicleManufacturerList) {
					vehicleManufacturer 	= new VehicleManufacturer();
					vehicleManufacturer.setVehicleManufacturerId(dto.getVehicleManufacturerId());
					vehicleManufacturer.setVehicleManufacturerName(dto.getVehicleManufacturerName());

					finalVehicleManufacturerList.add(vehicleManufacturer);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(finalVehicleManufacturerList));
		} catch (Exception e) {
			LOGGER.error("Err", e);
		}finally {
			valueObject 					= null;  
			vehicleManufacturerList			= null;  
			finalVehicleManufacturerList	= null;  
		}
	}*/
	
	@RequestMapping(value="/getAllVehicleManufacturer", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getAllVehicleManufacturer(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject();
			valueOutObject		= vehicleManufacturerService.getAllVehicleManufacturer();
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@RequestMapping(value="/addVehicleManufacturer", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  addVehicleManufacturer(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= vehicleManufacturerService.saveVehicleManufacturer(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	@RequestMapping(value="/getVehicleManufacturerById", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVehicleManufacturerById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= vehicleManufacturerService.getVehicleManufacturerById(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	@RequestMapping(value="/updateVehicleManufacturer", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateVehicleManufacturer(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= vehicleManufacturerService.updateVehicleManufacturer(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	@RequestMapping(value="/deleteVehicleManufacturer", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  deleteVehicleManufacturer(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= vehicleManufacturerService.deleteVehicleManufacturer(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
}
