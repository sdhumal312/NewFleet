package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.MastersConfigurationConstants;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.PartManufacturerBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.PartManufacturer;
import org.fleetopgroup.persistence.model.PartManufacturerHistory;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IPartManufacturerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IPartManufacturerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PartManufacturerController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IPartManufacturerService PartManufacturerService;

	@Autowired
	private IPartManufacturerHistoryService partManufacturerHistoryService;
	
	@Autowired ICompanyConfigurationService		companyConfigurationService;

	PartManufacturerBL VehStBL = new PartManufacturerBL();

	// Show the the Vehicle Status of
	@RequestMapping(value = "/addPartManufacturer", method = RequestMethod.GET)
	public ModelAndView addVehicleType(@ModelAttribute("command") PartManufacturer PartManufacturerBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		 userDetails		= null;
		HashMap<String, Object>  configuration		= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			model.put("PartManufacturer", VehStBL.prepareListofBean(PartManufacturerService.listPartManufacturerByCompanyId(userDetails.getCompany_id())));
			model.put("userDetails", userDetails);
			model.put(MastersConfigurationConstants.COMMON_MANUFACTURER, (boolean)configuration.get(MastersConfigurationConstants.COMMON_MANUFACTURER));
		} catch (Exception e) {
			LOGGER.error("Part Manufacturer Type Page:", e);

		}finally {
			userDetails			= null;
			configuration		= null;
		}
		return new ModelAndView("addPartManufacturer", model);
	}

	/* save the vehicle Status */
	@RequestMapping(value = "/savePartManufacturer", method = RequestMethod.POST)
	public ModelAndView savePartManufacturer(@ModelAttribute("command") PartManufacturer PartManufacturer,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails					 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			PartManufacturer status		 = VehStBL.prepareModel(PartManufacturer);
			PartManufacturer validate	 = PartManufacturerService.ValidateManufacturerName(PartManufacturer.getPmName(), userDetails.getCompany_id());
			if (validate == null) {
				status.setCompanyId(userDetails.getCompany_id());
				status.setCreatedById(userDetails.getId());
				status.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				PartManufacturerService.addPartManufacturer(status);
				status.setCreatedBy(userDetails.getEmail());
				model.put("savePartManufacturer", true);
			} else {
				model.put("alreadyPartManufacturer", true);
				return new ModelAndView("redirect:/addPartManufacturer.in", model);
			}

		} catch (Exception e) {
			model.put("alreadyPartManufacturer", true);
			LOGGER.error("Part Manufacturer Type Page:", e);
			return new ModelAndView("redirect:/addPartManufacturer.in", model);
		}
		return new ModelAndView("redirect:/addPartManufacturer.in", model);
	}

	// update of vehicle status
	@RequestMapping(value = "/uploadPartManufacturer", method = RequestMethod.POST)
	public ModelAndView uploadPartManufacturer(@ModelAttribute("command") PartManufacturer PartManufacturer,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		CustomUserDetails	userDetails		= null;
		try {
			userDetails					 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			PartManufacturer 	status 			= VehStBL.prepareModel(PartManufacturer);
			PartManufacturer	manufacturer	= PartManufacturerService.getPartManufacturer(status.getPmid(), userDetails.getCompany_id());
			if(!manufacturer.getPmName().trim().equalsIgnoreCase(status.getPmName().trim())) {
				PartManufacturer validate	 = PartManufacturerService.ValidateManufacturerName(status.getPmName(), userDetails.getCompany_id());
				if(validate != null) {
					model.put("alreadyPartManufacturer", true);
					return new ModelAndView("redirect:/addPartManufacturer.in", model);
				}
			}
			status.setLastModifiedById(userDetails.getId());
			status.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
			status.setCompanyId(userDetails.getCompany_id());
			
			PartManufacturerHistory		partManufacturerHistory		= VehStBL.prepareHistoryModel(manufacturer);
			
			PartManufacturerService.updatePartManufacturer(status);
			partManufacturerHistoryService.addPartManufacturerHistory(partManufacturerHistory);
			
			model.put("uploadPartManufacturer", true);
		} catch (Exception e) {
			model.put("alreadyPartManufacturer", true);
			LOGGER.error("Part Manufacturer Type Page:", e);
			return new ModelAndView("redirect:/addPartManufacturer.in", model);
		}
		return new ModelAndView("redirect:/addPartManufacturer.in", model);
	}

	@RequestMapping(value = "/editPartManufacturer", method = RequestMethod.GET)
	public ModelAndView editPartManufacturer(@ModelAttribute("command") PartManufacturer PartManufacturerBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			model.put("PartManufacturer", VehStBL.preparePartManufacturerBean(
					PartManufacturerService.getPartManufacturer(PartManufacturerBean.getPmid(), userDetails.getCompany_id())));
		} catch (Exception e) {
			LOGGER.error("Part Manufacturer Type Page:", e);
		}
		return new ModelAndView("editPartManufacturer", model);
	}

	@RequestMapping(value = "/deletePartManufacturer", method = RequestMethod.GET)
	public ModelAndView deletePartManufacturer(@ModelAttribute("command") PartManufacturer PartManufacturerBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			PartManufacturerHistory		partManufacturerHistory		= VehStBL.prepareHistoryModel(PartManufacturerService.getPartManufacturer(PartManufacturerBean.getPmid(), userDetails.getCompany_id()));
			
			PartManufacturerService.deletePartManufacturer(PartManufacturerBean.getPmid(), userDetails.getCompany_id());
			partManufacturerHistoryService.addPartManufacturerHistory(partManufacturerHistory);
			
			model.put("deletePartManufacturer", true);
		} catch (Exception e) {
			model.put("alreadyPartManufacturer", true);
			LOGGER.error("Part Manufacturer Type Page:", e);
			return new ModelAndView("redirect:/addPartManufacturer.in", model);
		}
		return new ModelAndView("redirect:/addPartManufacturer.in", model);
	}
}