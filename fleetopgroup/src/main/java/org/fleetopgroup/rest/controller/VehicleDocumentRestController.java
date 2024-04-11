package org.fleetopgroup.rest.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDocumentDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.VehicleDocType;
import org.fleetopgroup.persistence.model.VehicleDocument;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class VehicleDocumentRestController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired private IVehicleDocumentService 							VehicleDocumentService;
	
	
	@RequestMapping(value = "/saveVehicleDocumentDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveVehicleDocumentDetails(@RequestParam("vehicleDocumentData") String allRequestParams, 
			@RequestParam("input-file-preview") MultipartFile uploadfile) throws Exception {
		try {
			
			return VehicleDocumentService.saveVehicleDocumentDetails(JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams),uploadfile).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getDocumentList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDocumentList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return VehicleDocumentService.getDocumentList(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("getDocumentList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getVehicleDocumentById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleDocumentById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return VehicleDocumentService.getVehicleDocumentById(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("getVehicleDocumentById:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteVehicleDocumentById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteVehicleDocumentById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return VehicleDocumentService.deleteVehicleDocumentById(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("getVehicleDocumentById:"+ e);
			throw e;
		} 
	}
	
}