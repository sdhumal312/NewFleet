/**
 * 
 */
package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.MastersConfigurationConstants;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.VehicleTyreSizeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleTyreSizeDto;
import org.fleetopgroup.persistence.model.InventoryTyre;
import org.fleetopgroup.persistence.model.VehicleTyreSize;
import org.fleetopgroup.persistence.model.VehicleTyreSizeHistory;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreSizeHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreSizeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

/**
 * @author fleetop
 *
 */
@Controller
public class VehicleTyreSizeController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehicleTyreSizeService vehicleTyreSizeService;

	@Autowired
	private IVehicleTyreSizeHistoryService vehicleTyreSizeHistoryService;

	@Autowired ICompanyConfigurationService		companyConfigurationService;
	
	@Autowired IInventoryTyreService		inventoryTyreService;

	public VehicleTyreSizeController() {
		super();
	}

	VehicleTyreSizeBL TyreSizeBL = new VehicleTyreSizeBL();

	/** This is controller is create New Vehicle Tyre Size and Show Vehicle Size Also  */
	@RequestMapping(value = "/addVehicleTyresize", method = RequestMethod.GET)
	public ModelAndView addVehicleTyresize(final VehicleTyreSizeDto vehicleTyreSizeDto, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>  configuration		= null;
		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if(!(boolean) configuration.get(MastersConfigurationConstants.VEHICLE_TYRE_SIZE_MASTER_COMMON)) {
				model.put("tyreSize", vehicleTyreSizeService.findAllByCompanyId(userDetails.getCompany_id()));
			}else {
				model.put("tyreSize", vehicleTyreSizeService.findAll());
			}
			model.put("configuration", configuration);
			model.put("user", userDetails.getEmail());

		} catch (Exception e) {
			LOGGER.error("add addVehicleTyresize", e);
		}finally {
			configuration		= null;
		}
		return new ModelAndView("add_TyreSize", model);
	}

	// save the vehicle Type
	@RequestMapping(value = "/saveTyreSize", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveTyreSize(final VehicleTyreSizeDto vehicleTyreSizeDto, final HttpServletRequest request) {

		LOGGER.error("Registering saveTyreSize account with information: {}", vehicleTyreSizeDto);
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>  configuration		= null;
		VehicleTyreSize 	 	 validate_type 		= null;
		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			
			final VehicleTyreSize getvehicleType = TyreSizeBL.prepareModel(vehicleTyreSizeDto);

			if(!(boolean) configuration.get(MastersConfigurationConstants.VEHICLE_TYRE_SIZE_MASTER_COMMON)) {
				validate_type = vehicleTyreSizeService.findByTYRE_SIZE(getvehicleType.getTYRE_SIZE(), userDetails.getCompany_id());
				getvehicleType.setOwnTyreSize(true);
			}else {
				validate_type = vehicleTyreSizeService.findByTYRE_SIZE(getvehicleType.getTYRE_SIZE());
			}
			if (validate_type == null) {
				getvehicleType.setCREATEDBYID(userDetails.getId());
				getvehicleType.setLASTMODIFIEDBYID(userDetails.getId());
				getvehicleType.setCompanyId(userDetails.getCompany_id());
				vehicleTyreSizeService.registerNewVehicleType(getvehicleType);
				model.put("saveVehicleTyreSize", true);
			} else {
				model.put("alreadyVehicleTyreSize", true);
				return new ModelAndView("redirect:/addVehicleTyresize.html", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("add Vehicle Type Page:", e);
			model.put("alreadyVehicleTyreSize", true);
			return new ModelAndView("redirect:/addVehicleTyresize.html", model);
		}finally {
			configuration		= null;
		}
		return new ModelAndView("redirect:/addVehicleTyresize.html", model);
	}

	@RequestMapping(value = "/editVehicleTyreSize", method = RequestMethod.GET)
	public ModelAndView editVehicleTyreSize(final Locale locale, @RequestParam("Id") final Integer TS_id, final HttpServletRequest request) {
		HashMap<String, Object>  configuration		= null;
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			
				if(!(boolean) configuration.get(MastersConfigurationConstants.VEHICLE_TYRE_SIZE_MASTER_COMMON)) {
					model.put("TyreSize", vehicleTyreSizeService.get_Vehicle_Tyre_ID(TS_id, userDetails.getCompany_id()));
				}else {
					model.put("TyreSize", vehicleTyreSizeService.get_Vehicle_Tyre_ID(TS_id));
				}
			/*model.put("TyreSizeCount", vehicleTyreSizeService.count());*/

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("add Vehicle Tyre Size Page:", e);
			model.put("alreadyVehicleTyreSize", true);
			return new ModelAndView("redirect:/addVehicleTyresize.html", model);
		}finally {
			userDetails			= null;
			configuration		= null;
		}
		return new ModelAndView("edit_TyreSize", model);
	}

	@RequestMapping(value = "/updateTyreSize", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView updateTyreSize(final Locale locale, final VehicleTyreSizeDto vehicleTyreSizeDto,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		VehicleTyreSize 	 	 validate_type 		= null;
		HashMap<String, Object>  configuration		= null;
		VehicleTyreSize			 vehicleTyreSize 	= null;
		try {
			
			/** who Create this Issues get email id to user profile details */
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if(!(boolean) configuration.get(MastersConfigurationConstants.VEHICLE_TYRE_SIZE_MASTER_COMMON)) {
				vehicleTyreSize = vehicleTyreSizeService.get_Vehicle_Tyre_ID(vehicleTyreSizeDto.getTS_ID(), userDetails.getCompany_id());
			}else {
				vehicleTyreSize = vehicleTyreSizeService.get_Vehicle_Tyre_ID(vehicleTyreSizeDto.getTS_ID());
			}
		
			if(!vehicleTyreSize.getTYRE_SIZE().equalsIgnoreCase(vehicleTyreSizeDto.getTYRE_SIZE())) {
				if(!(boolean) configuration.get(MastersConfigurationConstants.VEHICLE_TYRE_SIZE_MASTER_COMMON)) {
					validate_type = vehicleTyreSizeService.findByTYRE_SIZE(vehicleTyreSizeDto.getTYRE_SIZE(), userDetails.getCompany_id());
				}else {
					validate_type = vehicleTyreSizeService.findByTYRE_SIZE(vehicleTyreSizeDto.getTYRE_SIZE());
				}
				if(validate_type != null) {
					model.put("alreadyVehicleTyreSize", true);
					return new ModelAndView("redirect:/addVehicleTyresize.html", model);
				}
			}
			Date currentDateUpdate = new Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			
			VehicleTyreSizeHistory		vehicleTyreSizeHistory	= TyreSizeBL.prepareHistoryModel(vehicleTyreSize);
			
			vehicleTyreSizeService.update_VehicleTyre_size(vehicleTyreSizeDto.getTYRE_SIZE(), vehicleTyreSizeDto.getTYRE_SIZE_DESCRITION(), userDetails.getId(), toDate,
					vehicleTyreSizeDto.getTS_ID(), userDetails.getCompany_id());
			vehicleTyreSizeHistoryService.registerNewVehicleTyreSizeHistory(vehicleTyreSizeHistory);
			
			model.put("updateVehicleTyreSize", true);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Edit Vehicle TyreSize Page:", e);
			model.put("alreadyVehicleTyreSize", true);
			return new ModelAndView("redirect:/addVehicleTyresize.html", model);
		}finally {
			 validate_type 		= null;
			 configuration		= null;
			 vehicleTyreSize 	= null;
		}
		return new ModelAndView("redirect:/addVehicleTyresize.html", model);
	}

	@RequestMapping(value = "/deleteTyreSize", method = RequestMethod.GET)
	public ModelAndView deleteTyreSize(final Locale locale, @RequestParam("Id") final Integer TS_Id) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		VehicleTyreSizeHistory		vehicleTyreSizeHistory	= null;
		HashMap<String, Object>  	configuration			= null;
		CustomUserDetails			userDetails				= null;
		Long				inventoryTyre			= null;
		try {
				userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
				inventoryTyre 			= inventoryTyreService.validateTyreByTyreSize(TS_Id, userDetails.getCompany_id());
				if(inventoryTyre != null && (boolean)configuration.getOrDefault("validateTyreSize", true)) {
					if(!(boolean) configuration.get(MastersConfigurationConstants.VEHICLE_TYRE_SIZE_MASTER_COMMON)) {
						model.put("tyreSize", vehicleTyreSizeService.findAllByCompanyId(userDetails.getCompany_id()));
					}else {
						model.put("tyreSize", vehicleTyreSizeService.findAll());
					}
					model.put("configuration", configuration);
					model.put("user", userDetails.getEmail());
					model.put("validateTyreSizeId", true);
					
					return new ModelAndView("add_TyreSize", model);
				}
				
				if(!(boolean) configuration.get(MastersConfigurationConstants.VEHICLE_TYRE_SIZE_MASTER_COMMON)) {
					vehicleTyreSizeHistory	= TyreSizeBL.prepareHistoryModel(vehicleTyreSizeService.get_Vehicle_Tyre_ID(TS_Id, userDetails.getCompany_id()));
				}else {
					vehicleTyreSizeHistory	= TyreSizeBL.prepareHistoryModel(vehicleTyreSizeService.get_Vehicle_Tyre_ID(TS_Id));
				}
				
			
			vehicleTyreSizeService.delete_Vehicle_TyreSize(TS_Id, userDetails.getCompany_id());
			vehicleTyreSizeHistoryService.registerNewVehicleTyreSizeHistory(vehicleTyreSizeHistory);
			
			model.put("deleteVehicleTyreSize", true);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Delete Vehicle TyreSize Page:", e);
			model.put("alreadyVehicleTyreSize", true);
			return new ModelAndView("redirect:/addVehicleTyresize.html", model);
		}finally {
			vehicleTyreSizeHistory	=	null;
			configuration			= null;
			userDetails				= null;
		}
		return new ModelAndView("redirect:/addVehicleTyresize.html", model);
	}

	// select Ajax Drop down value
	@RequestMapping(value = "/getVehicleTyresize", method = RequestMethod.GET)
	public void getVehicleTyresize(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<VehicleTyreSize> group = new ArrayList<VehicleTyreSize>();
		 List<VehicleTyreSize> list	= null;
		 HashMap<String, Object>  configuration		=  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
		
		 if(!(boolean) configuration.get(MastersConfigurationConstants.VEHICLE_TYRE_SIZE_MASTER_COMMON)) {
			 list	= vehicleTyreSizeService.findAllByCompanyId(userDetails.getCompany_id());
		 }else {
			 list	=  vehicleTyreSizeService.findAll();
		 }
		 
		 for (VehicleTyreSize add : list) {
			VehicleTyreSize wadd = new VehicleTyreSize();

			wadd.setTYRE_SIZE(add.getTYRE_SIZE());
			wadd.setTS_ID(add.getTS_ID());
			// System.out.println(add.getVid());
			group.add(wadd);
		}

		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));
		response.getWriter().write(gson.toJson(group));
	}
	
	/** This below Search Tyre Size Type Search Details */
	@RequestMapping(value = "/getSearchTyreSize", method = RequestMethod.POST)
	public void getSearchTyreSize(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<VehicleTyreSize> Size = null;
		HashMap<String, Object>  	configuration			= null;
		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<VehicleTyreSize> Tyre = new ArrayList<VehicleTyreSize>();
			configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			
			if(!(boolean) configuration.get(MastersConfigurationConstants.VEHICLE_TYRE_SIZE_MASTER_COMMON)) {
				Size = vehicleTyreSizeService.Search_VehicleTyreSizeType_select(term, userDetails.getCompany_id());
			}else {
				Size = vehicleTyreSizeService.Search_VehicleTyreSizeType_select(term);
			}
			if (Size != null && !Size.isEmpty()) {
				for (VehicleTyreSize add : Size) {

					VehicleTyreSize wadd = new VehicleTyreSize();

					wadd.setTS_ID(add.getTS_ID());
					wadd.setTYRE_SIZE(add.getTYRE_SIZE());

					Tyre.add(wadd);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(Tyre));
		} catch (Exception e) {
			throw e;
		}finally {
			Size = null;
		}
		
	}
	
}
