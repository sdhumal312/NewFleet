package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PartType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.PartCategoriesBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.PartManufacturerBL;
import org.fleetopgroup.persistence.bl.PartMeasurementUnitBL;
import org.fleetopgroup.persistence.dao.PartSubCategoryRepoitory;
import org.fleetopgroup.persistence.dao.VehicleManufacturerRepository;
import org.fleetopgroup.persistence.dao.VehicleModelRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.MasterParts;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IMasterPartsService;
import org.fleetopgroup.persistence.serviceImpl.IPartCategoriesService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IPartManufacturerService;
import org.fleetopgroup.persistence.serviceImpl.IPartMeasurementUnitService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MasterPartsRestController {
	
	@Autowired	VehicleManufacturerRepository	vehicleManufacturerRepository;
	@Autowired	IPartManufacturerService		partManufacturerService;
	@Autowired	IPartCategoriesService			partCategoriesService;
	@Autowired IPartLocationsService 			partLocationsService;
	@Autowired IPartMeasurementUnitService 		partMeasurementUnitService;
	@Autowired ICompanyConfigurationService		companyConfigurationService;
	@Autowired PartSubCategoryRepoitory			partSubCategoryRepoitory;
	@Autowired VehicleModelRepository			vehicleModelRepository;
	@Autowired IMasterPartsService				masterPartsService;
	
	PartMeasurementUnitBL 			partMeasurementUnitBL 			= new PartMeasurementUnitBL();
	PartManufacturerBL 				partManufacturerBL	 			= new PartManufacturerBL();
	PartCategoriesBL 				partCategoriesBL	 			= new PartCategoriesBL();
	PartLocationsBL 				partLocationsBL 				= new PartLocationsBL();

	@GetMapping(path = "/addNewMasterParts")
	public ModelAndView addNewMasterParts(final HttpServletRequest request) throws Exception {
		ModelAndView 				map 				= null;
		CustomUserDetails			userDetails			= null;
		HashMap<String, Object> 	configuration		= null;
		try {
			map = new ModelAndView("addNewMasterParts");
			
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
			
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("vehicleManufacturer", vehicleManufacturerRepository.findBycompanyId(userDetails.getCompany_id()));
			map.addObject("partManufacturer", partManufacturerService.listPartManufacturerByCompanyId(userDetails.getCompany_id()));
			map.addObject("PartCategories", partCategoriesBL.prepareListofBean(partCategoriesService.listPartCategoriesByCompayId(userDetails.getCompany_id())));
			map.addObject("PartLocations", partLocationsBL.prepareListofBean(partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));
			map.addObject("PartCategories", partCategoriesBL.prepareListofBean(partCategoriesService.listPartCategoriesByCompayId(userDetails.getCompany_id())));
			map.addObject("PartType", PartType.getPartTypeList());
			map.addObject("PartMeasurementUnit", partMeasurementUnitBL.prepareListofBean(partMeasurementUnitService.listPartMeasurementUnit()));
			map.addObject("configuration", configuration);
			map.addObject("partSubCategories", partSubCategoryRepoitory.findAllByCompanyId(userDetails.getCompany_id()));
			map.addObject("vehicleModel", vehicleModelRepository.findBycompanyId(userDetails.getCompany_id()));
			
			return map;
			
		}catch (Exception e) {
			throw e;
		}
	}
	
	@PostMapping(path = "/saveNewMasterPartsDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveNewMasterPartsDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object.put("locationDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("locationDetails")));
			return masterPartsService.saveNewMasterPartsDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/updateNewMasterPartsDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  updateNewMasterPartsDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object.put("locationDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("locationDetails")));
			return masterPartsService.updateNewMasterPartsDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@GetMapping(path = "/editNewMasterParts")
	public ModelAndView editMasterParts(@ModelAttribute("command") MasterParts MasterPartsBean,
			final HttpServletRequest request) throws Exception {
		ModelAndView 				map 				= null;
		CustomUserDetails			userDetails			= null;
		HashMap<String, Object> 	configuration		= null;
		try {
			map = new ModelAndView("editNewMasterParts");
			
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
			
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("partId", MasterPartsBean.getPartid());
			map.addObject("vehicleManufacturer", vehicleManufacturerRepository.findBycompanyId(userDetails.getCompany_id()));
			map.addObject("partManufacturer", partManufacturerService.listPartManufacturerByCompanyId(userDetails.getCompany_id()));
			map.addObject("PartCategories", partCategoriesBL.prepareListofBean(partCategoriesService.listPartCategoriesByCompayId(userDetails.getCompany_id())));
			map.addObject("PartLocations", partLocationsBL.prepareListofBean(partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));
			map.addObject("PartCategories", partCategoriesBL.prepareListofBean(partCategoriesService.listPartCategoriesByCompayId(userDetails.getCompany_id())));
			map.addObject("PartType", PartType.getPartTypeList());
			map.addObject("PartMeasurementUnit", partMeasurementUnitBL.prepareListofBean(partMeasurementUnitService.listPartMeasurementUnit()));
			map.addObject("configuration", configuration);
			map.addObject("partSubCategories", partSubCategoryRepoitory.findAllByCompanyId(userDetails.getCompany_id()));
			map.addObject("vehicleModel", vehicleModelRepository.findBycompanyId(userDetails.getCompany_id()));
			
			return map;
			
		}catch (Exception e) {
			throw e;
		}
	}
	
	@PostMapping(path = "/getMasterPartsDetailsForEdit", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getMasterPartsDetailsForEdit(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return masterPartsService.getMasterPartsDetailsForEdit(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/removeLowStockDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  removeLowStockDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 		= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return masterPartsService.removeLowStockDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
}
