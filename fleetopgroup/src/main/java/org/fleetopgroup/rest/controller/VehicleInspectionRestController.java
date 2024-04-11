package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.IInspectionToParameterDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDailyInspectionService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspctionSheetAsingmentService;
import org.fleetopgroup.web.util.ExceptionProcess;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleInspectionRestController {
	
	@Autowired	private IVehicleDailyInspectionService		vehicleDailyInspectionService;
	
	private	@Autowired 	IVehicleInspctionSheetAsingmentService			asingmentService;
	
	@Autowired private IInspectionToParameterDocumentService  inspectionToParameterDocumentService;
	

	@RequestMapping(value = "/getMissedInspectedDatesByVId", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getMissedInspectedDatesByVId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object 				= new ValueObject(allRequestParams);
			object.put("companyId", userDetails.getCompany_id());
			
			return vehicleDailyInspectionService.getMissedInspectedDatesByVId(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	
	@PostMapping(value ="/removeInspectionByVehicleType", produces="application/json")
	public HashMap<Object,Object> getActivityIssuesData(@RequestParam HashMap<Object, Object> allrequestParam,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ValueObject valueInObject = null; 
		try {
			valueInObject = new ValueObject(allrequestParam);
			
			return asingmentService.deleteVehicleInspectionSheetByVehicleType(valueInObject).getHtData();
			
		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		}
	}
	
	
	@PostMapping(value ="/skipInspection", produces="application/json")
	public HashMap<Object,Object> skipInspection(@RequestParam HashMap<Object, Object> allrequestParam,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ValueObject valueInObject = null; 
		try {
			valueInObject = new ValueObject(allrequestParam);
			
			return vehicleDailyInspectionService.skipInspectionSheet(valueInObject).getHtData();
			
		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		}
	}
	
	@PostMapping(path = "/getParameterDocumentList", produces="application/json")
	public HashMap<Object, Object> getParameterDocumentList(@RequestParam HashMap<Object, Object> allRequestParams ,HttpServletRequest request) throws Exception {
		ValueObject valueOutObject = null;
		ValueObject object= null;
		CustomUserDetails userDetails =null;
		try {
			
			userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object=new ValueObject(allRequestParams);
			valueOutObject = new ValueObject() ;
			valueOutObject.put("documents", inspectionToParameterDocumentService.getVehicleParameterDocumentList (object.getLong("completionToParameterId",0),userDetails.getCompany_id()));
			
			return valueOutObject.getHtData();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	
}
