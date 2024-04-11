package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import org.fleetopgroup.persistence.bl.VehicleTyreModelTypeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleTyreModelTypeDto;
import org.fleetopgroup.persistence.model.VehicleTyreModelType;
import org.fleetopgroup.persistence.model.VehicleTyreModelTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreModelSubTypeService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreModelTypeHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreModelTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class VehicleTyreModelTypeController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired IVehicleTyreModelTypeService vehicleTyreModelTypeService;

	@Autowired IVehicleTyreModelTypeHistoryService vehicleTyreModelTypeHistoryService;
	
	@Autowired IVehicleTyreModelSubTypeService vehicleTyreModelSubTypeService;
	
	@Autowired ICompanyConfigurationService		companyConfigurationService;
	
	VehicleTyreModelTypeBL TyreMT = new VehicleTyreModelTypeBL();

	@RequestMapping(value = "/addTyreModelType", method = RequestMethod.GET)
	public ModelAndView addTyreModelType(@ModelAttribute("command") VehicleTyreModelType vehicleTyreModelType,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>    configuration		= null;
		CustomUserDetails			userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			model.put("TyreModelType", TyreMT.Model_Tyre_ListofDto(vehicleTyreModelTypeService.findAll()));
			model.put("isOwnType", (boolean)configuration.get(MastersConfigurationConstants.IS_COMMON_TYRE_MODEL));
		} catch (Exception e) {
			LOGGER.error("Job Type Page:", e);
		}finally {
			configuration		= null;
			userDetails			= null;
		}
		return new ModelAndView("add_TyreModelType", model);
	}

	@RequestMapping(value = "/saveTyreModelType", method = RequestMethod.POST)
	public ModelAndView saveTyreModelType(@ModelAttribute("command") VehicleTyreModelTypeDto vehicleTyreModelTypeDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>    configuration		= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			
			VehicleTyreModelType DocType = TyreMT.prepare_VehicleTyreModelType(vehicleTyreModelTypeDto);

			VehicleTyreModelType validate = vehicleTyreModelTypeService
					.get_VehicleTyreModelType(DocType.getTYRE_MODEL(), userDetails.getCompany_id());
			if (validate == null) {
				DocType.setCREATEDBYID(userDetails.getId());
				DocType.setLASTMODIFIEDBYID(userDetails.getId());
				DocType.setCOMPANY_ID(userDetails.getCompany_id());
				if(!(boolean)configuration.get(MastersConfigurationConstants.IS_COMMON_TYRE_MODEL)) {
					DocType.setOwnTyreModel(true);
				}
				
				vehicleTyreModelTypeService.registerNew_VehicleTyreModelType(DocType);
				model.put("saveTyreModelType", true);
			} else {
				model.put("alreadyTyreModelType", true);
				return new ModelAndView("redirect:/addTyreModelType.in", model);
			}
		} catch (Exception e) {
			LOGGER.error("Job TyreModelType Page:", e);
			model.put("alreadyTyreModelType", true);
			return new ModelAndView("redirect:/addTyreModelType.in", model);
		}
		return new ModelAndView("redirect:/addTyreModelType.in", model);
	}

	@RequestMapping(value = "/editTyreModelType", method = RequestMethod.GET)
	public ModelAndView editTyreModelType(@RequestParam("Id") final Integer TM_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			model.put("TyreModelType",
					TyreMT.prepareJobTypeDto(vehicleTyreModelTypeService.getVehicleTyreModelTypeByID(TM_ID, userDetails.getCompany_id())));
		} catch (Exception e) {
			LOGGER.error("Job Type Page:", e);
		}
		return new ModelAndView("editTyreModelType", model);
	}

	@RequestMapping(value = "/updateTyreModelType", method = RequestMethod.POST)
	public ModelAndView updateTyreModelType(@ModelAttribute("command") VehicleTyreModelTypeDto vehicleTyreModelTypeDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			/** who Create this Issues get email id to user profile details */
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Date currentDateUpdate = new Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			VehicleTyreModelType DocType = TyreMT.prepare_VehicleTyreModelType(vehicleTyreModelTypeDto);
			
			VehicleTyreModelType	modelType	= vehicleTyreModelTypeService.getVehicleTyreModelTypeByID(DocType.getTYRE_MT_ID(), userDetails.getCompany_id());
			if(!modelType.getTYRE_MODEL().equalsIgnoreCase(DocType.getTYRE_MODEL())) {
				VehicleTyreModelType validate = vehicleTyreModelTypeService
						.get_VehicleTyreModelType(DocType.getTYRE_MODEL(), userDetails.getCompany_id());
				if (validate != null) {
					model.put("alreadyTyreModelType", true);
					return new ModelAndView("redirect:/addTyreModelType.in", model);
				}
			}
			
			VehicleTyreModelTypeHistory		tyreModelTypeHistory	= TyreMT.prepareVehicleTyreModelTypeHistoryModel(modelType);
			
			vehicleTyreModelTypeService.update_VehicleTyreModelType(DocType.getTYRE_MODEL(),DocType.getTYRE_MODEL_DESCRITION(), userDetails.getId(), toDate, DocType.getTYRE_MT_ID(), userDetails.getCompany_id());
			vehicleTyreModelTypeHistoryService.registerNew_VehicleTyreModelTypeHistory(tyreModelTypeHistory);
			
			model.put("updateTyreModelType", true);
		} catch (Exception e) {
			LOGGER.error("Job TyreModelType Page:", e);
			model.put("alreadyTyreModelType", true);
			return new ModelAndView("redirect:/addTyreModelType.in", model);
		}
		return new ModelAndView("redirect:/addTyreModelType.in", model);
	}

	@RequestMapping(value = "/deleteTyreModelType", method = RequestMethod.GET)
	public ModelAndView deleteTyreModelType(@RequestParam("Id") final Integer TM_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		Long manufacturerId = null;
		HashMap<String, Object>  	configuration			= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			manufacturerId 			= vehicleTyreModelSubTypeService.validateTyreManufacturer(TM_ID,userDetails.getCompany_id());
			
			if(manufacturerId != null && (boolean) configuration.get(MastersConfigurationConstants.VALIDATE_TYRE_MANUFACTURER)) {
				model.put("alreadyTyreModelType", true);
				return new ModelAndView("redirect:/addTyreModelType.in", model);
			}
			
			VehicleTyreModelTypeHistory		tyreModelTypeHistory	= TyreMT.prepareVehicleTyreModelTypeHistoryModel(vehicleTyreModelTypeService.getVehicleTyreModelTypeByID(TM_ID, userDetails.getCompany_id()));
			
			vehicleTyreModelTypeService.delete_VehicleTyreModelType(TM_ID, userDetails.getCompany_id());
			vehicleTyreModelTypeHistoryService.registerNew_VehicleTyreModelTypeHistory(tyreModelTypeHistory);
			
			model.put("deleteTyreModelType", true);
		} catch (Exception e) {
			LOGGER.error("Job Tyre ModelType Page:", e);
			model.put("alreadyTyreModelType", true);
			return new ModelAndView("redirect:/addTyreModelType.in", model);
		}
		return new ModelAndView("redirect:/addTyreModelType.in", model);
	}

	/** This below Search Tyre Model Type Search Details */
	@RequestMapping(value = "/getSearchTyreModel", method = RequestMethod.POST)
	public void getSearchTyreModel(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<VehicleTyreModelType> Tyre = new ArrayList<VehicleTyreModelType>();
		List<VehicleTyreModelType> TyreType = vehicleTyreModelTypeService.Search_VehicleTyreModelType_select(term, userDetails.getCompany_id());
		if (TyreType != null && !TyreType.isEmpty()) {
			for (VehicleTyreModelType add : TyreType) {

				VehicleTyreModelType wadd = new VehicleTyreModelType();

				wadd.setTYRE_MT_ID(add.getTYRE_MT_ID());
				wadd.setTYRE_MODEL(add.getTYRE_MODEL());

				Tyre.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(Tyre));
		/*String gson = new Gson().toJson(Tyre);
		response.getWriter().write(gson);*/
	}
}