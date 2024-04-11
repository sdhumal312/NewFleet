package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.InspectionParameter;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDailyInspectionService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspctionSheetAsingmentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionParameterService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionReportService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionSheetService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionSheetToParameterService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class VehicleInspectionReportWS {
	
	private @Autowired	IVehicleInspectionParameterService					vehicleInspectionParameterService;
	private @Autowired	IVehicleInspectionReportService						vehicleInspectionReportService;	
	private @Autowired	IVehicleInspectionSheetService						vehicleInspectionSheetService;
	private @Autowired	IVehicleInspectionSheetToParameterService			vehicleInspectionSheetRepository;
	private @Autowired	IVehicleInspctionSheetAsingmentService				VehicleInspctionSheetAsingmentService;
	private @Autowired	IVehicleDailyInspectionService						VehicleDailyInspectionService;
	
	@RequestMapping("/inspectionReport")
	public ModelAndView inspectionReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("inspectionReport", model);
	}
	
	@RequestMapping("/VehicleWiseInspectionReport")
	public ModelAndView VehicleWiseUsageReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("VehicleWiseInspectionReport", model);
	}
	
	@RequestMapping("/GroupWiseInspectionReport")
	public ModelAndView GroupWiseInspectionReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("GroupWiseInspectionReport", model);
	}
	
	@RequestMapping(value = "/getInspectionParameterForDropDown", method = RequestMethod.POST)
	public void getVehicleListServiceEntries(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<InspectionParameter> addresses = new ArrayList<InspectionParameter>();
			InspectionParameter wadd = null;
			List<InspectionParameter> DateVehicle = vehicleInspectionParameterService.getInspectionParameterListForDropDown(term);
			if (DateVehicle != null && !DateVehicle.isEmpty()) {
				for (InspectionParameter add : DateVehicle) {
					wadd = new InspectionParameter();

					wadd.setInspectionParameterId(add.getInspectionParameterId());
					wadd.setParameterName(add.getParameterName());


					addresses.add(wadd);
				}
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(addresses));
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value = "/getVehicleWiseInspectionReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleWiseInspectionReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return vehicleInspectionReportService.getVehicleWiseInspctionReport(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/getGroupWiseInspectionReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getGroupWiseInspectionReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return vehicleInspectionReportService.getGroupWiseInspctionReport(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	//Devy start code for Vehicle Inspection Parameter
		@RequestMapping(value="/getVehicleInspectionParameter", produces="appl"
				+ "ication/json", method=RequestMethod.POST)
		public HashMap<Object, Object>  getVehicleInspectionParameter(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
			ValueObject			 	valueOutObject 					= null;
			ValueObject 			object 							= null;
			CustomUserDetails		userDetails						= null;
			Long 					inspectionSheetId				= null;
			try {
				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				object 				= new ValueObject(allRequestParams);				
				inspectionSheetId=object.getLong("inspectionsheetId");
				valueOutObject		        = vehicleInspectionSheetService.getVehicleInspectionParameter(userDetails.getCompany_id(),inspectionSheetId);
				return valueOutObject.getHtData();
			} catch (Exception e){
				throw e;
			} 
			finally {
				valueOutObject 	= null;
			}
		}
		//Devy end for Vehicle Inspection Parameter
		
		@RequestMapping(value = "/getInspectionSheetDetailsById", method = RequestMethod.POST, produces="application/json")
		public HashMap<Object, Object>  getClothTypesById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
			ValueObject 		object 					= null;
			
			try {
				object 				= new ValueObject(allRequestParams);
				return vehicleInspectionSheetService.getVehicleInspectionSheetFindById(object).getHtData();
			} catch (Exception e) {
				throw e;
			}
		}
		
		@RequestMapping(value="/updateInspectionSheetDetailsWS", produces="application/json", method=RequestMethod.POST)
		public HashMap<Object, Object>  updateInspectionSheetDetailsWS(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
			
			ValueObject					valueOutObject 					= null;
			ValueObject 				object 							= null;
			try {
				valueOutObject		= new ValueObject();
				object 				= new ValueObject(allRequestParams);
				
				valueOutObject.put("Parameter", JsonConvertor.toValueObjectFromJsonString(object.getString("Parameter")));
				
				if(object.getString("newParameter") != null) {
				valueOutObject.put("newParameter", JsonConvertor.toValueObjectFromJsonString(object.getString("newParameter")));
				}
				
				return vehicleInspectionSheetService.updateInspectionSheetDetails(valueOutObject).getHtData();
				
			} catch (Exception e) {
				throw e;
			} finally {
				valueOutObject 					= null;
				object 							= null;
			}
		}
		
		@RequestMapping(value="/removeInspectionSheetDetailsWS", produces="application/json", method=RequestMethod.POST)
		public HashMap<Object, Object>  removeInspectionSheetDetailsWS(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
			ValueObject					valueOutObject 					= null;
			ValueObject 				object 							= null;
			CustomUserDetails			userDetails						= null;
			long						inspectionParameterID			= 0;
			try {
				userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				valueOutObject		= new ValueObject();
				object 				= new ValueObject(allRequestParams);
				
				valueOutObject.put("inspectionSheetParameterId", object.get("inspectionSheetParameterId"));
				inspectionParameterID =  valueOutObject.getLong("inspectionSheetParameterId");
				 vehicleInspectionSheetRepository.RemoveInspectionParameter(inspectionParameterID,userDetails.getCompany_id());
				 return valueOutObject.getHtData();
			} catch (Exception e) {
				throw e;
			} finally {
				valueOutObject 					= null;
				object 							= null;
			}
			
		}
		
		@RequestMapping(value = "/getMissedInspectionListByVehicle", method = RequestMethod.POST, produces="application/json")
		public HashMap<Object, Object>  getMissedInspectionListByVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
			ValueObject 		object 					= null;
			try {
				object 	= new ValueObject(allRequestParams);
				return VehicleDailyInspectionService.getMissedInspectionListByVehicle(object).getHtData();
			} catch (Exception e) {
				throw e;
			} finally {
			}
		}
		
}
