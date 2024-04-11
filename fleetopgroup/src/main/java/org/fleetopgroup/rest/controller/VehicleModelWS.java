package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.VehicleManufacturerRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleModelDto;
import org.fleetopgroup.persistence.model.VehicleModel;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleModelService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleModelTyreLayoutService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class VehicleModelWS {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired 	IVehicleModelService   	vehicleModelService   ;
	@Autowired	VehicleManufacturerRepository 	manufacturerRepository ;
	@Autowired ICompanyConfigurationService		companyConfigurationService;
	@Autowired 	IVehicleModelTyreLayoutService   	vehicleModelTyreLayoutService   ;
	
	@RequestMapping(value = "/vehicleModel", method = RequestMethod.GET)
	public ModelAndView vehicleModel(final HttpServletRequest request) throws Exception {
		ModelAndView 					map 		= null;
		try {
			HashMap<String, Object> 		configuration 	= null;
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration =  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			map = new ModelAndView("vehicleModel");
			map.addObject("configuration",configuration);
			map.addObject("companyId",userDetails.getCompany_id());
			map.addObject("manufacturer", manufacturerRepository.findBycompanyId(userDetails.getCompany_id()));
			
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/vehicleModelAutocomplete", method = RequestMethod.GET)
	public void companyWiseTyreExpenseAutocomplete(Map<String, Object> map, 
			@RequestParam HashMap<Object, Object> allRequestParams,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ValueObject						valueObject 					= null;
		List<VehicleModelDto> 				vehicleModelList				= null;
		List<VehicleModel> 				finalVehicleModelListList		= null;
		VehicleModel 					vehicleModel 					= null;
		
		try {
			String	query	= "";
			vehicleModelList 				= new ArrayList<>();
			finalVehicleModelListList 		= new ArrayList<VehicleModel>();
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();                  
			
				valueObject		= new ValueObject(allRequestParams);
				
				if(valueObject.getString("manufacturer",null) != null) {
					query += " AND PL.vehicleManufacturerId IN ( "+valueObject.getString("manufacturer")+" ) ";
				}
				
			vehicleModelList		= vehicleModelService.getVehicleModelListByCompanyIdAndManufacturer(userDetails.getCompany_id(), query);
			
			if(vehicleModelList != null && !vehicleModelList.isEmpty()) {	
				for (VehicleModelDto dto : vehicleModelList) {
					vehicleModel 	= new VehicleModel();
					vehicleModel.setVehicleModelId(dto.getVehicleModelId());
					vehicleModel.setVehicleModelName(dto.getVehicleModelName());

					finalVehicleModelListList.add(vehicleModel);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(finalVehicleModelListList));
		} catch (Exception e) {
			LOGGER.error("Err", e);
		}finally {
			valueObject 					= null;  
			vehicleModelList			= null;  
			finalVehicleModelListList		= null;  
		}
	}
/*	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/vehicleModelAutocomplete", method = RequestMethod.POST)
	public void vehicleModelAutocomplete(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		ValueObject						valueObject 					= null;
		List<VehicleModel> 				vehicleModelList				= null;
		List<VehicleModel> 				finalVehicleModelListList		= null;
		VehicleModel 					vehicleModel 					= null;
		
		try {
			valueObject						= new ValueObject();
			vehicleModelList 				= new ArrayList<VehicleModel>();
			finalVehicleModelListList 		= new ArrayList<VehicleModel>();
			
			valueObject				= vehicleModelService.getVehicleModelAutocomplete(term);
			vehicleModelList		= (List<VehicleModel>) valueObject.get("vehicleModelList");
			
			if(vehicleModelList != null && !vehicleModelList.isEmpty()) {	
				for (VehicleModel dto : vehicleModelList) {
					vehicleModel 	= new VehicleModel();
					vehicleModel.setVehicleModelId(dto.getVehicleModelId());
					vehicleModel.setVehicleModelName(dto.getVehicleModelName());

					finalVehicleModelListList.add(vehicleModel);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(finalVehicleModelListList));
		} catch (Exception e) {
			LOGGER.error("Err", e);
		}finally {
			valueObject 					= null;  
			vehicleModelList			= null;  
			finalVehicleModelListList		= null;  
		}
	}
	*/
	@RequestMapping(value="/getAllVehicleModel", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getAllVehicleModel(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject();
			valueOutObject		= vehicleModelService.getAllVehicleModel();
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@RequestMapping(value="/addVehicleModel", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  addVehicleModel(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= vehicleModelService.saveVehicleModel(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	@RequestMapping(value="/getVehicleModelById", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVehicleModelById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= vehicleModelService.getVehicleModelById(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	@RequestMapping(value="/updateVehicleModel", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateVehicleModel(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= vehicleModelService.updateVehicleModel(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	@RequestMapping(value="/deleteVehicleModel", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  deleteVehicleModel(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= vehicleModelService.deleteVehicleModel(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@GetMapping(value = "/vehicleModelTyreLayout")
	public ModelAndView vehicleModelTyreLayout(@RequestParam("id") final Integer vehicleModelId, final HttpServletRequest request) throws Exception {
		ModelAndView 					map 			= null;
		CustomUserDetails				userDetails		= null;
		HashMap<String, Object> 		configuration 	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration =  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			map 		= new ModelAndView("vehicleModelTyreLayout");
			map.addObject("vehicleModelId", vehicleModelId);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("configuration", configuration);
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(value="/saveVehicleModelTyreLayout", produces="application/json")
	public HashMap<Object, Object>  saveVehicleModelTyreLayout(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= vehicleModelTyreLayoutService.saveVehicleModelTyreLayout(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@GetMapping(value = "/showVehicleModelTyreLayout")
	public ModelAndView showVehicleModelTyreLayout(@RequestParam("id") final Integer vehicleModelId, final HttpServletRequest request) throws Exception {
		ModelAndView 					map 			= null;
		CustomUserDetails				userDetails		= null;
		HashMap<String, Object> 		configuration 	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration =  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			map 		= new ModelAndView("showVehicleModelTyreLayout");
			map.addObject("vehicleModelId", vehicleModelId);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("configuration", configuration);
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@PostMapping(value="/getVehicleModelTyreLayout", produces="application/json")
	public HashMap<Object, Object>  getDealerServiceEntriesList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= vehicleModelTyreLayoutService.getVehicleModelTyreLayout(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@PostMapping(value="/getVehicleModelTyreLayoutPositionByPosition", produces="application/json")
	public HashMap<Object, Object>  getVehicleModelTyreLayoutPositionByPosition(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= vehicleModelTyreLayoutService.getVehicleModelTyreLayoutPositionByPosition(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@PostMapping(value="/deleteVehicleModelTyreLayout", produces="application/json")
	public HashMap<Object, Object>  deleteVehicleModelTyreLayout(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= vehicleModelTyreLayoutService.deleteVehicleModelTyreLayout(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
}
