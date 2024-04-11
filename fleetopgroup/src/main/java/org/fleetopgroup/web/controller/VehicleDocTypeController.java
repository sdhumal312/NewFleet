package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
/**
 * @author fleetop
 * @ModifiedBy manish kr singh
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.bl.VehicleDocTypeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDocTypeDto;
import org.fleetopgroup.persistence.model.VehicleDocType;
import org.fleetopgroup.persistence.model.VehicleDocTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocTypeHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VehicleDocTypeController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehicleDocTypeService vehicleDocTypeService;

	@Autowired
	private IVehicleDocTypeHistoryService vehicleDocTypeHistoryService;
	
	VehicleDocTypeBL VD_BL = new VehicleDocTypeBL();


	@RequestMapping(value = "/addVehicleDocType", method = RequestMethod.GET)
	public String addVehicleDocType(Model model, final VehicleDocTypeDto vehicleDocTypeDto,
			final HttpServletRequest request) {

		// Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.addAttribute("vehicleDocType", vehicleDocTypeService.findAllVehicleDocTypeByCompanyId(userDetails.getCompany_id()));
			model.addAttribute("vehicleDocTypeCount", vehicleDocTypeService.getVehicleDocTypeCountByCompanyId(userDetails.getCompany_id()));
/*
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName(); // get logged in username
*/
			model.addAttribute("username", userDetails.getEmail());

		} catch (Exception e) {
			LOGGER.error("Registering vehicleDocType account with information: {}", e);
		}
		return "addVehicleDocType";
	}

	@RequestMapping(value = "/saveVehDocType", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveVehicleDocType(final VehicleDocTypeDto vehicleDocTypeDto,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VehicleDocType Get_UI = VD_BL.prepareModel(vehicleDocTypeDto);
			final List<VehicleDocType> vaidate = vehicleDocTypeService.findByVDocTypeAndCompanyId(userDetails.getCompany_id(), Get_UI.getvDocType());
								 Get_UI.setCompany_Id(userDetails.getCompany_id());
			if (vaidate == null || vaidate.size() <= 0) {
				
				Get_UI.setCompany_Id(userDetails.getCompany_id());
				Get_UI.setCreatedById(userDetails.getId());
				Get_UI.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				model.put("saveVehicleDocType", true);
				vehicleDocTypeService.registerNewVehicleDocType(Get_UI);
			} else {
				model.put("alreadyVehicleDocType", true);
				return new ModelAndView("redirect:/addVehicleDocType", model);
			}

		} catch (Exception e) {
			LOGGER.error("Registering vehicleDocType account with information: {}", e);
		}
		return new ModelAndView("redirect:/addVehicleDocType", model);
	}

	@RequestMapping(value = "/editVehicleDocType", method = RequestMethod.GET)
	public ModelAndView deleteEmployee(final Locale locale, @RequestParam("dtid") final long dtid) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {

			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("vehicleDocType", vehicleDocTypeService.getVehicleDocTypeByID(dtid, userDetails.getCompany_id()));
			model.put("vehicleDocTypeCount", vehicleDocTypeService.getVehicleDocTypeCountByCompanyId(userDetails.getCompany_id()));

		} catch (Exception e) {
			LOGGER.error("Registering vehicleDocType account with information: {}", e);
		}

		return new ModelAndView("editVehicleDocType", model);
	}

	@RequestMapping(value = "/updateVehDocType", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView updateVehicleDocType(final Locale locale, @RequestParam("dtid") final long dtid,
			@RequestParam("vDocType") final String vDocType) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			final List<VehicleDocType> alreadyExists = vehicleDocTypeService.findByVDocTypeAndCompanyId(userDetails.getCompany_id(), vDocType);
			
			VehicleDocTypeHistory		docTypeHistory		= VD_BL.prepareHistoryModel(vehicleDocTypeService.getVehicleDocTypeByID(dtid, userDetails.getCompany_id()));
			
			if(alreadyExists == null || alreadyExists.size() <= 0) {
				
				vehicleDocTypeService.updateVehicleDocType(vDocType, userDetails.getId(), new Timestamp(System.currentTimeMillis()), dtid, userDetails.getCompany_id());
				vehicleDocTypeHistoryService.registerNewVehicleDocTypeHistory(docTypeHistory);
				
				model.put("updateVehicleDocType", true);
			}else {
				model.put("alreadyVehicleDocType", true);
				return new ModelAndView("redirect:/addVehicleDocType", model);
			}

		} catch (Exception e) {
			LOGGER.error("Registering vehicleDocType account with information: {}", e);
		}

		return new ModelAndView("redirect:/addVehicleDocType", model);
	}

	@RequestMapping(value = "/deleteVehicleDocType", method = RequestMethod.GET)
	public ModelAndView deleteVehicleStatus(final Locale locale, @RequestParam("dtid") final long dtid) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			VehicleDocTypeHistory		docTypeHistory		= VD_BL.prepareHistoryModel(vehicleDocTypeService.getVehicleDocTypeByID(dtid, userDetails.getCompany_id()));
			
			vehicleDocTypeService.deleteVehicleDocType(dtid, userDetails.getCompany_id());
			vehicleDocTypeHistoryService.registerNewVehicleDocTypeHistory(docTypeHistory);
			
			model.put("deleteVehicleDocType", true);

		} catch (Exception e) {

			LOGGER.error("Registering vehicleDocType account with information: {}", e);
		}

		return new ModelAndView("redirect:/addVehicleDocType", model);
	}

}