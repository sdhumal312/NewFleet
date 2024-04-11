package org.fleetopgroup.web.controller;
/**
 * @author fleetop
 *
 */

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleFuelDto;
import org.fleetopgroup.persistence.model.VehicleFuel;
import org.fleetopgroup.persistence.model.VehicleFuelPermission;
import org.fleetopgroup.persistence.serviceImpl.IVehicleFuelService;
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

@Controller
public class VehicleFuelController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehicleFuelService vehicleFuelService;

	@RequestMapping(value = "/addVehicleFuel", method = RequestMethod.GET)
	public ModelAndView addVehicleFuel(final VehicleFuelDto vehicleFuelDto, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {

			model.put("vehiclefuel", vehicleFuelService.findAll());
			model.put("vehicleFuelCount", vehicleFuelService.count());

		} catch (Exception e) {
			LOGGER.error("Registering VehicleFuel account with information: {}", e);
		}
		return new ModelAndView("addVehicleFuel", model);
	}
	
	@RequestMapping(value = "/addVehicleFuelPermission", method = RequestMethod.GET)
	public ModelAndView addVehicleFuelPermission(final VehicleFuelDto vehicleFuelDto, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("vehiclefuelPermission", vehicleFuelService.findAll());
			model.put("vehiclefuel", vehicleFuelService.findAllVehicleFuel());

		} catch (Exception e) {
			LOGGER.error("Registering VehicleFuel account with information: {}", e);
		}
		return new ModelAndView("addVehicleFuelPermission", model);
	}
	
	@RequestMapping(value = "/saveVehFuelPermission", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView saveVehFuelPermission(@RequestParam("fid") final Long fid, final HttpServletRequest request) {


		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		 VehicleFuelPermission	fuelPermission = 	vehicleFuelService.getVehicleFuelPermissionByID(fid, userDetails.getCompany_id());
			if(fuelPermission == null) {
				final VehicleFuel registered = vehicleFuelService.getVehicleFuelByID(fid);
				if (registered != null) {
					VehicleFuelPermission	permission	= new VehicleFuelPermission();
					permission.setCompanyId(userDetails.getCompany_id());
					permission.setFid(registered.getFid());
					permission.setvFuel(registered.getvFuel());
					permission.setCreatedBy(userDetails.getEmail());
					permission.setCreatedOn(new Timestamp(System.currentTimeMillis()));
					vehicleFuelService.addNewVehicleFuel(permission);
					model.put("saveVehicleFuel", true);
				} else {
					model.put("alreadyVehicleFuel", true);
					return new ModelAndView("redirect:/addVehicleFuelPermission.html", model);
				}
			}else {
				model.put("alreadyVehicleFuel", true);
				return new ModelAndView("redirect:/addVehicleFuelPermission.html", model);
			}
		} catch (Exception e) {
			model.put("alreadyVehicleFuel", true);
			LOGGER.error("Registering vehicleFuel account with information: {}", e);
			return new ModelAndView("redirect:/addVehicleFuelPermission.html", model);
		}
		return new ModelAndView("redirect:/addVehicleFuelPermission.html", model);
	}


	@RequestMapping(value = "/saveVehFuel", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveVehicleFuel(final VehicleFuelDto vehicleFuelDto, final HttpServletRequest request) {

		LOGGER.error("Registering vehicleFuel account with information: {}", vehicleFuelDto);

		Map<String, Object> model = new HashMap<String, Object>();
		try {

			final VehicleFuel registered = vehicleFuelService.findByVFuel(vehicleFuelDto.getvFuel());
			if (registered == null) {
				vehicleFuelService.registerNewVehicleFuel(vehicleFuelDto);
				model.put("saveVehicleFuel", true);
			} else {
				model.put("alreadyVehicleFuel", true);
				return new ModelAndView("redirect:/addVehicleFuel.html", model);
			}
		} catch (Exception e) {
			model.put("alreadyVehicleFuel", true);
			LOGGER.error("Registering vehicleFuel account with information: {}", e);
			return new ModelAndView("redirect:/addVehicleFuel.html", model);
		}
		return new ModelAndView("redirect:/addVehicleFuel.html", model);
	}

	@RequestMapping(value = "/editVehicleFuel", method = RequestMethod.GET)
	public ModelAndView deleteEmployee(final Locale locale, @RequestParam("fid") final long fid) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {

			model.put("vehiclefuel", vehicleFuelService.getVehicleFuelByID(fid));
			model.put("vehicleFuelCount", vehicleFuelService.count());

		} catch (Exception e) {
			LOGGER.error("Registering vehicleFuel account with information: {}", e);
		}
		return new ModelAndView("editVehicleFuel", model);
	}

	@RequestMapping(value = "/updateVehFuel", method = RequestMethod.POST)
	public ModelAndView updateVehicleFuel(final Locale locale, @RequestParam("fid") final long fid,
			@RequestParam("vFuel") final String vFuel) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {

			vehicleFuelService.updateVehicleFuel(vFuel, fid);
			model.put("updateVehicleFuel", true);

		} catch (Exception e) {
			model.put("alreadyVehicleFuel", true);
			LOGGER.error("Registering vehicleFuel account with information: {}", e);
			return new ModelAndView("redirect:/addVehicleFuel.html", model);
		}
		return new ModelAndView("redirect:/addVehicleFuel.html", model);
	}

	@RequestMapping(value = "/deleteVehicleFuel", method = RequestMethod.GET)
	public ModelAndView deleteVehicleFuel(final Locale locale, @RequestParam("fid") final long fid) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			vehicleFuelService.deleteVehicleFuel(fid);
			model.put("deleteVehicleFuel", true);
		} catch (Exception e) {
			model.put("alreadyVehicleFuel", true);
			LOGGER.error("Registering vehicleFuel account with information: {}", e);
			return new ModelAndView("redirect:/addVehicleFuel.html", model);
		}
		return new ModelAndView("redirect:/addVehicleFuel.html", model);
	}
	
	@RequestMapping(value = "/deleteVehicleFuelPermission", method = RequestMethod.GET)
	public ModelAndView deleteVehicleFuelPermission(@RequestParam("fid") final long fid) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleFuelService.deleteVehicleFuelPermission(fid, userDetails.getCompany_id());
			model.put("deleteVehicleFuel", true);
		} catch (Exception e) {
			model.put("alreadyVehicleFuel", true);
			LOGGER.error("Registering vehicleFuel account with information: {}", e);
			return new ModelAndView("redirect:/addVehicleFuelPermission.html", model);
		}
		return new ModelAndView("redirect:/addVehicleFuelPermission.html", model);
	}

	// select Ajax Drop down value
	@RequestMapping(value = "/getVehicleFuel", method = RequestMethod.GET)
	public void getVehicleFuel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<VehicleFuel> group = new ArrayList<VehicleFuel>();
		for (VehicleFuel add : vehicleFuelService.findAll()) {
			VehicleFuel wadd = new VehicleFuel();
			wadd.setFid(add.getFid());
			wadd.setvFuel(add.getvFuel());
			// System.out.println(add.getVid());
			group.add(wadd);
		}

		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));
		response.getWriter().write(gson.toJson(group));
	}
}