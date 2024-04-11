package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.TripCheckPointParameter;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ITripCheckPointService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class TripCheckPointWS {
	@Autowired private ICompanyConfigurationService 	companyConfigurationService;
	@Autowired 	ITripCheckPointService   	tripCheckPointService   ;
	
	@PostMapping(value="/getAllTripCheckPointParameter", produces="application/json")
	public HashMap<Object, Object>  getAllTripCheckPointParameter(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			return tripCheckPointService.getAllTripCheckPointParameter(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			valueObject 					= null;
		}
	}
	@RequestMapping(value="/saveTripCheckPointParameter", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveTripCheckPointParameter(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			return tripCheckPointService.saveTripCheckPointParameter(valueObject).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueObject 					= null;
		}
	}
	
	@RequestMapping(value="/deleteTripCheckPointParameter", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  deleteTripCheckPointParameter(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			
			valueObject =	tripCheckPointService.getAvailabilityOfTripCheckPointParamterInInspection(valueObject);
			if(valueObject.getBoolean("parameterUsed")) {
				return valueObject.getHtData();
			}else {
				return tripCheckPointService.deleteTripCheckPointParameter(valueObject).getHtData();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			valueObject 					= null;
		}
	}
	
	@PostMapping(value="/getRouteWiseTripCheckPoint", produces="application/json")
	public HashMap<Object, Object>  getRouteWiseTripCheckPoint(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			return tripCheckPointService.getRouteWiseTripCheckPoint(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			valueObject 					= null;
		}
	}
	@RequestMapping(value="/saveTripCheckPoint", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveTripCheckPoint(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			return tripCheckPointService.saveTripCheckPoint(valueObject).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueObject 					= null;
		}
	}
	
	@RequestMapping(value="/deleteTripCheckPoint", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  deleteTripCheckPoint(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			valueObject =	tripCheckPointService.getAvailabilityOfTripCheckPointInInspection(valueObject);
			if(valueObject.getBoolean("checkPointUsed")) {
				return valueObject.getHtData();
			}else {
				return tripCheckPointService.deleteTripCheckPoint(valueObject).getHtData();
			}
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			valueObject 					= null;
		}
	}
	
	@RequestMapping(value = "/tripCheckPointInspection", method = RequestMethod.GET)
	public ModelAndView checkPointInspection(@RequestParam("tripSheetId") final Long tripSheetId,final HttpServletRequest request) throws Exception {
		ModelAndView 					map 				= null;
		HashMap<String, Object> 		configuration	    = null;
		CustomUserDetails 				userDetails 		= null;
		try {
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			map 			= new ModelAndView("tripCheckPointInspection");
			
			map.addObject("tripSheetId", tripSheetId);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("configuration", configuration);
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/tripCheckPointParameterAutocomplete")
	public void getCompanyInformationDetails(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ValueObject									valueObject 				= null;
		List<TripCheckPointParameter> 				parameterList				= null;
		List<TripCheckPointParameter> 				finalParameterList			= null;
		TripCheckPointParameter 					tripCheckPointParameter 	= null;
		CustomUserDetails							userDetails					= null;
		try {
			
			valueObject				= new ValueObject();
			parameterList 			= new ArrayList<TripCheckPointParameter>();
			finalParameterList 		= new ArrayList<TripCheckPointParameter>();
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("companyId", userDetails.getCompany_id());
			valueObject				= tripCheckPointService.getAllTripCheckPointParameter(valueObject);
			parameterList			= (List<TripCheckPointParameter>) valueObject.get("allTripCheckPointParameter");
			if(parameterList != null && !parameterList.isEmpty()) {	
				for (TripCheckPointParameter dto : parameterList) {
					tripCheckPointParameter 	= new TripCheckPointParameter();
					tripCheckPointParameter.setTripCheckPointParameterId(dto.getTripCheckPointParameterId());
					tripCheckPointParameter.setTripCheckParameterName(dto.getTripCheckParameterName());

					finalParameterList.add(tripCheckPointParameter);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(finalParameterList));
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(value="/getInspectedTripCheckPointParameter", produces="application/json")
	public HashMap<Object, Object>  getInspectedTripCheckPointParameter(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			return tripCheckPointService.getInspectedTripCheckPointParameter(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			valueObject 					= null;
		}
	}
	
	@RequestMapping(value="/inspectTripCheckPointParameter", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  inspectTripCheckPointParameter(@RequestParam("parameterList") String allRequestParams,
			@RequestParam("input-file-preview") MultipartFile[] uploadfile) throws Exception {
		ValueObject					valueObject 					= null;
		try {
			valueObject		= JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams);
			valueObject.put("parameterList", JsonConvertor.toValueObjectFromJsonString(valueObject.getString("parameter")));
			return tripCheckPointService.inspectTripCheckPointParameter(valueObject, uploadfile).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueObject 					= null;
		}
	}
	
	@PostMapping(value="/removeInspectedParameter", produces="application/json")
	public HashMap<Object, Object>  removeInspectedParameter(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			return tripCheckPointService.removeInspectedParameter(valueObject).getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			valueObject 					= null;
		}
	}

}
