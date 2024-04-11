package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
/**
 * @author fleetop
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.MastersConfigurationConstants;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.VehicleCostFixingBL;
import org.fleetopgroup.persistence.bl.VehicleTyreModelTypeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleTyreModelSubTypeDto;
import org.fleetopgroup.persistence.model.VehicleCostFixing;
import org.fleetopgroup.persistence.model.VehicleTyreModelSubType;
import org.fleetopgroup.persistence.model.VehicleTyreModelSubTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleCostFixingService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreModelSubTypeHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreModelSubTypeService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreModelTypeService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class VehicleTyreModelSubTypeController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehicleTyreModelTypeService VehicleTyreModelTypeService;

	@Autowired
	private IVehicleTyreModelSubTypeService VehicleTyreModelSubTypeService;

	@Autowired
	private IVehicleTyreModelSubTypeHistoryService vehicleTyreModelSubTypeHistoryService;
	
	@Autowired ICompanyConfigurationService		companyConfigurationService;
	
	@Autowired	private IVehicleCostFixingService		vehicleCostFixingService;
	@Autowired IInventoryTyreService		inventoryTyreService;
	VehicleTyreModelTypeBL TyreMT = new VehicleTyreModelTypeBL();

	@RequestMapping(value = "/addModelSubType", method = RequestMethod.GET)
	public ModelAndView addModelSubType(
			@ModelAttribute("command") VehicleTyreModelSubTypeDto vehicleTyreModelSubTypeDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> configuration		= null;
		try {
			CustomUserDetails	userDetails		=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			
			model.put("TyreModelType", TyreMT.Model_Tyre_ListofDto(VehicleTyreModelTypeService.findAll()));
			model.put("TyreModelSubType", VehicleTyreModelSubTypeService.findAllByCompanyId(userDetails.getCompany_id()));
			model.put("isCommonTyreModel", (boolean)configuration.get(MastersConfigurationConstants.IS_COMMON_TYRE_MODEL));
			model.put("configuration",configuration);
		} catch (Exception e) {
			LOGGER.error("Job Sub Type Page:", e);
		}finally {
			configuration		= null;
		}
		return new ModelAndView("add_ModelSubType", model);
	}

	@RequestMapping(value = "/saveModelSubType", method = RequestMethod.POST)
	public ModelAndView saveModelSubType(@ModelAttribute("command") VehicleTyreModelSubTypeDto ModelSubTypeDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails		=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VehicleTyreModelSubType DocType = TyreMT.prepare_TyreModelSub_Model(ModelSubTypeDto);
			VehicleTyreModelSubType validate = VehicleTyreModelSubTypeService
					.get_VehicleTyre_ModelSubType(ModelSubTypeDto.getTYRE_MODEL_SUBTYPE(), userDetails.getCompany_id());
			
			if (validate == null) {
				DocType.setCREATEDBYID(userDetails.getId());
				DocType.setLASTMODIFIEDBYID(userDetails.getId());
				DocType.setCOMPANY_ID(userDetails.getCompany_id());
				VehicleTyreModelSubTypeService.registerNew_VehicleTyreModelType(DocType);
				model.put("saveModelSubType", true);
				
				if(ModelSubTypeDto.getCostPerKM() != null && ModelSubTypeDto.getCostPerKM() > 0.0) {
					vehicleCostFixingService.saveVehicleCostFixing(VehicleCostFixingBL.getVehicleCostFixing(ModelSubTypeDto, userDetails, DocType));
				}
				
			} else {
				model.put("alreadyModelSubType", true);
				
				return new ModelAndView("redirect:/addModelSubType.in", model);
				
			}
		} catch (Exception e) {
			model.put("alreadyModelSubType", true);
			return new ModelAndView("redirect:/addModelSubType.in", model);
		}
		return new ModelAndView("redirect:/addModelSubType.in", model);
	}

	@RequestMapping(value = "/editModelSubType", method = RequestMethod.GET)
	public ModelAndView editModelSubType(@RequestParam("Id") final Integer TMS_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> configuration		= null;
		Long				validateTyreModel			= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VehicleTyreModelSubTypeDto	typeDto	= VehicleTyreModelSubTypeService.getVehicleTyreModel_SubTypeByID(TMS_ID, userDetails.getCompany_id());
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			validateTyreModel 			= inventoryTyreService.validateTyreByTyreModel(TMS_ID, userDetails.getCompany_id());
			if(validateTyreModel != null && (boolean)configuration.getOrDefault(MastersConfigurationConstants.VALIDATE_TYRE_MODEL,true)) {
				model.put("alreadyModelSubType", true);
				return new ModelAndView("redirect:/addModelSubType.in", model);
			}
			model.put("VehicleCostFixing", vehicleCostFixingService.getVehicleCostFixingByTyreSubTypeId(userDetails.getCompany_id(), typeDto.getTYRE_MST_ID()));
		
			model.put("TyreModelSubType", typeDto);
			model.put("configuration", configuration);

			model.put("TyreModelType", TyreMT.Model_Tyre_ListofDto(VehicleTyreModelTypeService.findAll()));

		} catch (Exception e) {
			LOGGER.error("Job Sub Type Page:", e);
		}
		return new ModelAndView("editModelSubType", model);
	}

	@RequestMapping(value = "/updateModelSubType", method = RequestMethod.POST)
	public ModelAndView updateModelSubType(@ModelAttribute("command") VehicleTyreModelSubTypeDto ModelSubTypeDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails		=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VehicleTyreModelSubType subModel = TyreMT.prepare_TyreModelSubTypeDto(ModelSubTypeDto);

			VehicleTyreModelSubTypeDto	tyreModelSubType	= VehicleTyreModelSubTypeService.getVehicleTyreModel_SubTypeByID(ModelSubTypeDto.getTYRE_MST_ID(), userDetails.getCompany_id());
			
			if(!tyreModelSubType.getTYRE_MODEL_SUBTYPE().equalsIgnoreCase(subModel.getTYRE_MODEL_SUBTYPE())) {
				final VehicleTyreModelSubType validate_type = VehicleTyreModelSubTypeService.get_VehicleTyre_ModelSubType(subModel.getTYRE_MODEL_SUBTYPE(), userDetails.getCompany_id());
				if(validate_type != null) {
					model.put("alreadyModelSubType", true);
					return new ModelAndView("redirect:/addModelSubType.html", model);
				}
			}
			
			VehicleTyreModelSubTypeHistory		tyreModelSubTypeHistory		= TyreMT.prepareHistoryModel(VehicleTyreModelSubTypeService.get_VehicleTyre_ModelSubTypeById(ModelSubTypeDto.getTYRE_MST_ID(), userDetails.getCompany_id()));
			
			subModel.setCREATEDBYID(userDetails.getId());
			subModel.setLASTMODIFIEDBYID(userDetails.getId());
			subModel.setCOMPANY_ID(userDetails.getCompany_id());
			
			VehicleTyreModelSubTypeService.registerNew_VehicleTyreModelType(subModel);
			vehicleTyreModelSubTypeHistoryService.registerNew_VehicleTyreModelTypeHistory(tyreModelSubTypeHistory);
			
			if(ModelSubTypeDto.getCostPerKM() != null && ModelSubTypeDto.getCostPerKM() > 0.0) {
				vehicleCostFixingService.saveVehicleCostFixing(VehicleCostFixingBL.getVehicleCostFixingForEdit(ModelSubTypeDto, userDetails, subModel));
			}
			
			model.put("updateModelSubType", true);
		

		} catch (Exception e) {
			LOGGER.error("Job Sub Type Page:", e);
			model.put("alreadyModelSubType", true);
			return new ModelAndView("redirect:/addModelSubType.in", model);
		}
		return new ModelAndView("redirect:/addModelSubType.in", model);
	}

	@RequestMapping(value = "/deleteModelSubType", method = RequestMethod.GET)
	public ModelAndView deleteModelType(@RequestParam("Id") final Integer TMS_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>  	configuration			= null;
		Long				validateTyreModel			= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			validateTyreModel 			= inventoryTyreService.validateTyreByTyreModel(TMS_ID, userDetails.getCompany_id());
			if(validateTyreModel != null && (boolean)configuration.getOrDefault(MastersConfigurationConstants.VALIDATE_TYRE_MODEL,true)) {
				model.put("alreadyModelSubType", true);
				return new ModelAndView("redirect:/addModelSubType.in", model);
			}
			
			VehicleTyreModelSubTypeHistory		tyreModelSubTypeHistory		= TyreMT.prepareHistoryModel(VehicleTyreModelSubTypeService.get_VehicleTyre_ModelSubTypeById(TMS_ID, userDetails.getCompany_id()));
			
			VehicleTyreModelSubTypeService.delete_VehicleTyreModel_SubType(TMS_ID, userDetails.getCompany_id());
			vehicleTyreModelSubTypeHistoryService.registerNew_VehicleTyreModelTypeHistory(tyreModelSubTypeHistory);
			
			model.put("deleteModelSubType", true);
		} catch (Exception e) {
			LOGGER.error("Job Sub Type Page:", e);
			model.put("alreadyModelSubType", true);
			return new ModelAndView("redirect:/addModelSubType.in", model);
		}
		return new ModelAndView("redirect:/addModelSubType.in", model);
	}
	
	/** This below Search Tyre Model Type Search Details */
	@RequestMapping(value = "/getSearchTyreSubModel", method = RequestMethod.POST)
	public void getSearchTyreModel(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		CustomUserDetails	userDetails		=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<VehicleTyreModelSubType> subJob = new ArrayList<VehicleTyreModelSubType>();
		List<VehicleTyreModelSubType> subJobtype = VehicleTyreModelSubTypeService.Search_VehicleTyreSubModelType_select(term, userDetails.getCompany_id());
		
		if (subJobtype != null && !subJobtype.isEmpty()) {
			for (VehicleTyreModelSubType add : subJobtype) {

				VehicleTyreModelSubType wadd = new VehicleTyreModelSubType();

				wadd.setTYRE_MST_ID(add.getTYRE_MST_ID());
				wadd.setTYRE_MODEL_SUBTYPE(add.getTYRE_MODEL_SUBTYPE());

				subJob.add(wadd);
			}
		}
		Gson gson = new Gson();
	
		response.getWriter().write(gson.toJson(subJob));
	}

	/** This below Search Tyre Model Changes and model Details */
	@RequestMapping(value = "/getTyreModelSubType", method = RequestMethod.GET)
	public void getTyreModelSubTypeChangeTyreModel(@RequestParam(value = "ModelType", required = true) Integer ModelType,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		CustomUserDetails	userDetails		=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<VehicleTyreModelSubType> subJob = new ArrayList<VehicleTyreModelSubType>();
		List<VehicleTyreModelSubType> subJobtype = VehicleTyreModelSubTypeService.SearchOnly_ModelSubType(ModelType, userDetails.getCompany_id());
		if (subJobtype != null && !subJobtype.isEmpty()) {
			for (VehicleTyreModelSubType add : subJobtype) {

				VehicleTyreModelSubType wadd = new VehicleTyreModelSubType();

				wadd.setTYRE_MST_ID(add.getTYRE_MST_ID());
				wadd.setTYRE_MODEL_SUBTYPE(add.getTYRE_MODEL_SUBTYPE());

				subJob.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonODDMETER = " + gson.toJson(wadd));

		response.getWriter().write(gson.toJson(subJob));
	}
	
	@RequestMapping(value = "/editCostPerKm", method = RequestMethod.POST)
	public ModelAndView editCostPerKm(@ModelAttribute("command") VehicleTyreModelSubTypeDto ModelSubTypeDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails		=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(ModelSubTypeDto.getCostPerKM() != null && ModelSubTypeDto.getCostPerKM() > 0.0) {
				VehicleCostFixing	costFixing	= new VehicleCostFixing();
				costFixing.setCompanyId(userDetails.getCompany_id());
				if(ModelSubTypeDto.getVehicleCostFixingId() != null && ModelSubTypeDto.getVehicleCostFixingId() > 0)
					costFixing.setVehicleCostFixingId(ModelSubTypeDto.getVehicleCostFixingId());
				costFixing.setCostPerKM(ModelSubTypeDto.getCostPerKM());
				costFixing.setTyreSubTypeId(ModelSubTypeDto.getTYRE_MST_ID());
				costFixing.setCreatedById(userDetails.getId());
				costFixing.setLastModifiedById(userDetails.getId());
				costFixing.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
				costFixing.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
				costFixing.setCostType(VehicleCostFixingBL.COST_TYPE_TYRE);
				vehicleCostFixingService.saveVehicleCostFixing(costFixing);
			}
			
			model.put("updateModelSubType", true);
		

		} catch (Exception e) {
			LOGGER.error("Job Sub Type Page:", e);
			model.put("alreadyModelSubType", true);
			return new ModelAndView("redirect:/addModelSubType.in", model);
		}
		return new ModelAndView("redirect:/addModelSubType.in", model);
	}
	
	@GetMapping(value = "/showVehicleModelSubType")
	public ModelAndView showDealerServiceEntries(@RequestParam("id") final Integer TYRE_MST_ID, final HttpServletRequest request) throws Exception {
		ModelAndView 					map 			= null;
		CustomUserDetails				userDetails		= null;
		HashMap<String, Object> 		configuration 	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration =  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			map 		= new ModelAndView("showVehicleTyreModelSubType");
			map.addObject("TYRE_MST_ID", TYRE_MST_ID);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("configuration", configuration);
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@RequestMapping(value="/getVehicleTyreModelSubTypeDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVehicleTyreModelSubTypeDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= VehicleTyreModelSubTypeService.getVehicleTyreModelSubTypeDetails(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	
	/** This below Search Tyre Model Type Search Details */
	@GetMapping(value = "/searchAllTyreModel")
	public void searchAllTyreModel(final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails		=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<VehicleTyreModelSubTypeDto> subJob = new ArrayList<VehicleTyreModelSubTypeDto>();
		List<VehicleTyreModelSubTypeDto> subJobtype = VehicleTyreModelSubTypeService.findAllByCompanyId(userDetails.getCompany_id());
		if (subJobtype != null && !subJobtype.isEmpty()) {
			for (VehicleTyreModelSubTypeDto add : subJobtype) {

				VehicleTyreModelSubTypeDto wadd = new VehicleTyreModelSubTypeDto();

				wadd.setTYRE_MST_ID(add.getTYRE_MST_ID());
				wadd.setTYRE_MODEL_SUBTYPE(add.getTYRE_MODEL_SUBTYPE());

				subJob.add(wadd);
			}
		}
		Gson gson = new Gson();
	
		response.getWriter().write(gson.toJson(subJob));
	}
	
	/** This below Search Tyre Model Type Search Details */
	@RequestMapping(value = "/getSearchTyreSubModelByTyreSize", method = RequestMethod.POST)
	public void getSearchTyreSubModelByTyreSize(Map<String, Object> map, @RequestParam("term") final String term,
			@RequestParam("tyreSizeConfig") final String tyreSizeConfig, @RequestParam("tyreSize") final String tyreSize,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<VehicleTyreModelSubType> subJobtype = null;
		CustomUserDetails	userDetails		=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<VehicleTyreModelSubType> subJob = new ArrayList<VehicleTyreModelSubType>();
		if(tyreSize != "") {
			subJobtype = VehicleTyreModelSubTypeService.getSearchTyreSubModelByTyreSize(term, userDetails.getCompany_id(),Boolean.parseBoolean(tyreSizeConfig),Integer.parseInt(tyreSize));
		}
		if (subJobtype != null && !subJobtype.isEmpty()) {
			for (VehicleTyreModelSubType add : subJobtype) {

				VehicleTyreModelSubType wadd = new VehicleTyreModelSubType();

				wadd.setTYRE_MST_ID(add.getTYRE_MST_ID());
				wadd.setTYRE_MODEL_SUBTYPE(add.getTYRE_MODEL_SUBTYPE());

				subJob.add(wadd);
			}
		}
		Gson gson = new Gson();
	
		response.getWriter().write(gson.toJson(subJob));
	}

}
