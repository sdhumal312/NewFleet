package org.fleetopgroup.web.controller;
/**
 * @author fleetop
 *
 */

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehiclePurchaseInfoTypeDto;
import org.fleetopgroup.persistence.model.VehiclePurchaseInfoType;
import org.fleetopgroup.persistence.serviceImpl.IVehiclePurchaseInfoTypeHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehiclePurchaseInfoTypeService;
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

@Controller
public class VehiclePurchaseInfoTypeController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehiclePurchaseInfoTypeService vehiclePurchaseInfoTypeService;

	@Autowired
	private IVehiclePurchaseInfoTypeHistoryService vehiclePurchaseInfoTypeHistoryService;

	@RequestMapping(value = "/addVehiclePurchaseInfoType", method = RequestMethod.GET)
	public ModelAndView addVehiclePurchaseInfoType(final VehiclePurchaseInfoTypeDto vehiclePurchaseInfoTypeDto,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("vehiclePurchaseInfoType", vehiclePurchaseInfoTypeService.findAllByCompanyId(userDetails.getCompany_id()));
			model.put("vehiclePurchaseInfoTypeCount", vehiclePurchaseInfoTypeService.getCountByCompanyId(userDetails.getCompany_id()));

		} catch (Exception e) {
			LOGGER.error("Registering VehiclePurchaseInfoType account with information: {}", e);
		}
		return new ModelAndView("addVehiclePurchaseInfoType", model);
	}

	@RequestMapping(value = "/saveVehPurchaseInfoType", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveVehiclePurchaseInfoType(final VehiclePurchaseInfoTypeDto PurchaseInfoTypeDto,
			final HttpServletRequest request) {

		LOGGER.error("Registering vehiclePurchaseInfoType account with information: {}", PurchaseInfoTypeDto);

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			final VehiclePurchaseInfoType registered = vehiclePurchaseInfoTypeService.findByVPurchaseInfoType(PurchaseInfoTypeDto.getvPurchaseInfoType(), userDetails.getCompany_id());
			if (registered == null) {
				vehiclePurchaseInfoTypeService.registerNewVehiclePurchaseInfoType(PurchaseInfoTypeDto, userDetails.getCompany_id(), userDetails.getId(), new Timestamp(System.currentTimeMillis()));
				model.put("saveVehiclePurchaseInfoType", true);
			} else {
				model.put("alreadyVehiclePurchaseInfoType", true);
				return new ModelAndView("redirect:/addVehiclePurchaseInfoType.in", model);
			}
		} catch (Exception e) {

			model.put("alreadyVehiclePurchaseInfoType", true);
			LOGGER.error("Registering vehiclePurchaseInfoType account with information: {}", e);
			return new ModelAndView("redirect:/addVehiclePurchaseInfoType.in", model);
		}
		return new ModelAndView("redirect:/addVehiclePurchaseInfoType.in", model);
	}

	@RequestMapping(value = "/editVehiclePurchaseInfoType", method = RequestMethod.GET)
	public ModelAndView deleteEmployee(final Locale locale, @RequestParam("ptid") final long fid) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("vehiclePurchaseInfoType", vehiclePurchaseInfoTypeService.getVehiclePurchaseInfoTypeByID(fid, userDetails.getCompany_id()));
			model.put("vehiclePurchaseInfoTypeCount", vehiclePurchaseInfoTypeService.getCountByCompanyId(userDetails.getCompany_id()));

		} catch (Exception e) {
			LOGGER.error("Registering vehiclePurchaseInfoType account with information: {}", e);
		}
		return new ModelAndView("editVehiclePurchaseInfoType", model);
	}

	@RequestMapping(value = "/updateVehPurchaseInfoType", method = RequestMethod.POST)
	public ModelAndView updateVehiclePurchaseInfoType(final Locale locale, @RequestParam("ptid") final long fid,
			@RequestParam("vPurchaseInfoType") final String vPurchaseInfoType) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			final VehiclePurchaseInfoType registered = vehiclePurchaseInfoTypeService.findByVPurchaseInfoType(vPurchaseInfoType, userDetails.getCompany_id());
			
			if(registered == null) {
				
				vehiclePurchaseInfoTypeService.updateVehiclePurchaseInfoType(vPurchaseInfoType, userDetails.getId(), new Timestamp(System.currentTimeMillis()) ,fid, userDetails.getCompany_id());
				vehiclePurchaseInfoTypeHistoryService.registerNewVehiclePurchaseInfoTypeHIstory(vehiclePurchaseInfoTypeService.getVehiclePurchaseInfoTypeByID(fid, userDetails.getCompany_id()));
				
				model.put("updateVehiclePurchaseInfoType", true);
			}else {
				model.put("alreadyVehiclePurchaseInfoType", true);
			}

		} catch (Exception e) {

			LOGGER.error("Registering vehiclePurchaseInfoType account with information: {}", e);
			return new ModelAndView("redirect:/addVehiclePurchaseInfoType.in", model);
		}
		return new ModelAndView("redirect:/addVehiclePurchaseInfoType.in", model);
	}

	@RequestMapping(value = "/deleteVehiclePurchaseInfoType", method = RequestMethod.GET)
	public ModelAndView deleteVehiclePurchaseInfoType(final Locale locale, @RequestParam("ptid") final long fid) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			
			vehiclePurchaseInfoTypeService.deleteVehiclePurchaseInfoType(fid, userDetails.getCompany_id());
			vehiclePurchaseInfoTypeHistoryService.registerNewVehiclePurchaseInfoTypeHIstory(vehiclePurchaseInfoTypeService.getVehiclePurchaseInfoTypeByID(fid, userDetails.getCompany_id()));
			model.put("deleteVehiclePurchaseInfoType", true);

		} catch (Exception e) {

			model.put("alreadyVehiclePurchaseInfoType", true);
			LOGGER.error("Registering vehiclePurchaseInfoType account with information: {}", e);
			return new ModelAndView("redirect:/addVehiclePurchaseInfoType.in", model);
		}
		return new ModelAndView("redirect:/addVehiclePurchaseInfoType.in", model);
	}

}