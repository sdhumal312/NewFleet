package org.fleetopgroup.web.controller;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.bl.PartMeasurementUnitBL;
import org.fleetopgroup.persistence.model.PartMeasurementUnit;
import org.fleetopgroup.persistence.serviceImpl.IPartMeasurementUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PartMeasurementUnitController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IPartMeasurementUnitService PartMeasurementUnitService;

	PartMeasurementUnitBL VehStBL = new PartMeasurementUnitBL();

	// Show the the Vehicle Status of
	@RequestMapping(value = "/addPartMeasurementUnit", method = RequestMethod.GET)
	public ModelAndView addVehicleType(@ModelAttribute("command") PartMeasurementUnit PartMeasurementUnitBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("PartMeasurementUnit",
					VehStBL.prepareListofBeanDto(PartMeasurementUnitService.listPartMeasurementUnitList()));
		} catch (Exception e) {
			LOGGER.error("Part Manufacturer Unit Type Page:", e);

		}
		return new ModelAndView("addPartMeasurementUnit", model);
	}

	/* save the vehicle Status */
	@RequestMapping(value = "/savePartMeasurementUnit", method = RequestMethod.POST)
	public ModelAndView savePartMeasurementUnit(@ModelAttribute("command") PartMeasurementUnit PartMeasurementUnit,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			PartMeasurementUnit status = VehStBL.prepareModel(PartMeasurementUnit);
			PartMeasurementUnit validate = PartMeasurementUnitService
					.ValidatePartMeasurementUnit(PartMeasurementUnit.getPmuName());
			if (validate == null) {
				PartMeasurementUnitService.addPartMeasurementUnit(status);
				model.put("savePartMeasurementUnit", true);
			} else {
				model.put("alreadyPartMeasurementUnit", true);
				return new ModelAndView("redirect:/addPartMeasurementUnit.in", model);
			}
		} catch (Exception e) {
			model.put("alreadyPartMeasurementUnit", true);
			LOGGER.error("Part Manufacturer Unit Type Page:", e);
			return new ModelAndView("redirect:/addPartMeasurementUnit.in", model);
		}
		return new ModelAndView("redirect:/addPartMeasurementUnit.in", model);
	}

	// update of vehicle status
	@RequestMapping(value = "/uploadPartMeasurementUnit", method = RequestMethod.POST)
	public ModelAndView uploadPartMeasurementUnit(@ModelAttribute("command") PartMeasurementUnit PartMeasurementUnit,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			
			PartMeasurementUnit status = VehStBL.prepareModel(PartMeasurementUnit);
			PartMeasurementUnitService.updatePartMeasurementUnit(status);
			model.put("uploadPartMeasurementUnit", true);
		} catch (Exception e) {
			model.put("alreadyPartMeasurementUnit", true);
			LOGGER.error("Part Manufacturer Unit Type Page:", e);
			return new ModelAndView("redirect:/addPartMeasurementUnit.in", model);
		}
		return new ModelAndView("redirect:/addPartMeasurementUnit.in", model);
	}

	@RequestMapping(value = "/editPartMeasurementUnit", method = RequestMethod.GET)
	public ModelAndView editPartMeasurementUnit(@ModelAttribute("command") PartMeasurementUnit PartMeasurementUnitBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("PartMeasurementUnit", VehStBL.preparePartMeasurementUnitBean(
					PartMeasurementUnitService.getPartMeasurementUnitDto(PartMeasurementUnitBean.getPmuid())));
			
			model.put("measurementUnit",
					VehStBL.prepareListofBeanDto(PartMeasurementUnitService.listPartMeasurementUnitList()));
			
		} catch (Exception e) {
			LOGGER.error("Part Manufacturer Unit Type Page:", e);
		}
		return new ModelAndView("editPartMeasurementUnit", model);
	}

	@RequestMapping(value = "/deletePartMeasurementUnit", method = RequestMethod.GET)
	public ModelAndView deletePartMeasurementUnit(
			@ModelAttribute("command") PartMeasurementUnit PartMeasurementUnitBean, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			PartMeasurementUnitService.deletePartMeasurementUnit(PartMeasurementUnitBean.getPmuid());
			model.put("deletePartMeasurementUnit", true);
		} catch (Exception e) {
			model.put("alreadyPartMeasurementUnit", true);
			LOGGER.error("Part Manufacturer Unit Type Page:", e);
			return new ModelAndView("redirect:/addPartMeasurementUnit.in", model);
		}
		return new ModelAndView("redirect:/addPartMeasurementUnit.in", model);
	}
}