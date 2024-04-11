package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.bl.VehiclePhoTypeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehiclePhoTypeDto;
import org.fleetopgroup.persistence.model.VehicleAccidentTypeMaster;
import org.fleetopgroup.persistence.model.VehiclePhoType;
import org.fleetopgroup.persistence.model.VehiclePhoTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IVehiclePhoTypeHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehiclePhoTypeService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
public class VehiclePhoTypeController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehiclePhoTypeService vehiclePhoTypeService;

	@Autowired
	private IVehiclePhoTypeHistoryService vehiclePhoTypeHistoryService;
	
	VehiclePhoTypeBL VP_BL = new VehiclePhoTypeBL();

	@RequestMapping(value = "/addVehiclePhoType", method = RequestMethod.GET)
	public ModelAndView addVehiclePhoType(final VehiclePhoTypeDto vehiclePhoTypeDto, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {

			CustomUserDetails	userDetails		=		(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("vehiclePhoType", vehiclePhoTypeService.findAllVehiclePhoToTypeByCompanyId(userDetails.getCompany_id()));
			model.put("vehiclePhoTypeCount", vehiclePhoTypeService.countVehiclePhotoTypeByCompanyId(userDetails.getCompany_id()));

		} catch (Exception e) {
			LOGGER.error("Registering VehicleFuel account with information: {}", e);
		}
		return new ModelAndView("addVehiclePhoType", model);
	}

	@RequestMapping(value = "/saveVehPhoType", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveVehiclePhoType(final VehiclePhoTypeDto vehiclePhoTypeDto,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails		=		(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			final VehiclePhoType GET_UI = VP_BL.prepareModel(vehiclePhoTypeDto);
			GET_UI.setCompany_Id(userDetails.getCompany_id());
			final List<VehiclePhoType> validate = vehiclePhoTypeService.findByVPhoTypeByCompanyId(vehiclePhoTypeDto.getvPhoType(), userDetails.getCompany_id());
			if (validate == null ||  validate.size() <= 0) {
				GET_UI.setCreatedById(userDetails.getId());
				GET_UI.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				vehiclePhoTypeService.registerNewVehiclePhoType(GET_UI);
				model.put("saveVehiclePhoType", true);
			}else {
				model.put("alreadyVehiclePhoType", true);
			}


		} catch (Exception e) {

			LOGGER.error("Registering VehiclePhoType account with information: {}", e);
		}

		return new ModelAndView("redirect:/addVehiclePhoType.html", model);
	}

	@RequestMapping(value = "/editVehiclePhoType", method = RequestMethod.GET)
	public ModelAndView deleteEmployee(final Locale locale, @RequestParam("ptid") final long ptid) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {

			CustomUserDetails	userDetails		=		(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("vehiclePhoType", vehiclePhoTypeService.getVehiclePhoTypeByID(ptid, userDetails.getCompany_id()));
			model.put("vehiclePhoTypeCount", vehiclePhoTypeService.countVehiclePhotoTypeByCompanyId(userDetails.getCompany_id()));

		} catch (Exception e) {
			LOGGER.error("Registering VehiclePhoType account with information: {}", e);
		}
		return new ModelAndView("editVehiclePhoType", model);
	}

	@RequestMapping(value = "/updateVehPhoType", method = RequestMethod.POST)
	public ModelAndView updateVehiclePhoType(final Locale locale, @RequestParam("ptid") final long ptid,
			@RequestParam("vPhoType") final String vPhoType,@RequestParam("vehiclePhotoTypeId") final short vehiclePhotoTypeId) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails		=		(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			final List<VehiclePhoType> validate = vehiclePhoTypeService.validateUpdateVehiclePhotoType(vPhoType, userDetails.getCompany_id(),ptid);
			
			VehiclePhoTypeHistory		vehiclePhoTypeHistory	= VP_BL.prepareHistoryModel(vehiclePhoTypeService.getVehiclePhoTypeByID(ptid, userDetails.getCompany_id()));
			
			if(validate == null ||  validate.size() <= 0) {
				vehiclePhoTypeService.updateVehiclePhoType(vPhoType, userDetails.getEmail(), new Timestamp(System.currentTimeMillis()), vehiclePhotoTypeId, ptid, userDetails.getCompany_id());
				vehiclePhoTypeHistoryService.registerNewVehiclePhoTypeHistory(vehiclePhoTypeHistory);
				
				model.put("UpdateVehiclePhoType", true);
			}else {
				model.put("alreadyVehiclePhoType", true);
			}

		} catch (Exception e) {

			LOGGER.error("Registering VehiclePhoType account with information: {}", e);
			model.put("alreadyVehiclePhoType", true);
			return new ModelAndView("redirect:/addVehiclePhoType.html", model);
		}
		return new ModelAndView("redirect:/addVehiclePhoType.html", model);
	}

	@RequestMapping(value = "/deleteVehiclePhoType", method = RequestMethod.GET)
	public ModelAndView deleteVehicleStatus(final Locale locale, @RequestParam("ptid") final long ptid) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			VehiclePhoTypeHistory	vehiclePhoTypeHistory	= VP_BL.prepareHistoryModel(vehiclePhoTypeService.getVehiclePhoTypeByID(ptid, userDetails.getCompany_id()));
			
			vehiclePhoTypeService.deleteVehiclePhoType(ptid, userDetails.getCompany_id());
			vehiclePhoTypeHistoryService.registerNewVehiclePhoTypeHistory(vehiclePhoTypeHistory);
			model.put("deleteVehiclePhoType", true);

		} catch (Exception e) {
			model.put("alreadyVehiclePhoType", true);

			LOGGER.error("Registering VehiclePhoType account with information: {}", e);
			return new ModelAndView("redirect:/addVehiclePhoType.html", model);
		}
		return new ModelAndView("redirect:/addVehiclePhoType.html", model);
	}

	
	@GetMapping(value = "/getVehicleAccidentDocumentTypeDropdown")
	public void getVehicleAccidentTypeDropdown(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<VehiclePhoType> 		vehiclePhoTypeFinalList 	= null;
		List<VehiclePhoType> 		vehiclePhoTypeList 			= null;
		VehiclePhoType 				vehiclePhoType 				= null;
		ValueObject					valueObject					= null;
		CustomUserDetails			userDetails					= null;
		try {
			valueObject						= new ValueObject();
			vehiclePhoTypeFinalList 	= new ArrayList<VehiclePhoType>();
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("companyId", userDetails.getCompany_id());
			
			vehiclePhoTypeList 			= vehiclePhoTypeService.getAllVehicleAccidentDocumentType(valueObject);
			if (vehiclePhoTypeList != null && !vehiclePhoTypeList.isEmpty()) {
				for (VehiclePhoType add : vehiclePhoTypeList) {
					vehiclePhoType = new VehiclePhoType();

					vehiclePhoType.setPtid(add.getPtid());
					vehiclePhoType.setvPhoType(add.getvPhoType());
					vehiclePhoTypeFinalList.add(vehiclePhoType);
				}
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(vehiclePhoTypeFinalList));
		} catch (Exception e) {
			throw e;
		}
	}
}