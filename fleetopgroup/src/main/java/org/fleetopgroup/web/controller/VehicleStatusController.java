package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.fleetopgroup.persistence.bl.VehicleStatusBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleStatusDto;
import org.fleetopgroup.persistence.model.VehicleStatus;
import org.fleetopgroup.persistence.model.VehicleStatusPermission;
import org.fleetopgroup.persistence.serviceImpl.IVehicleStatusService;
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
public class VehicleStatusController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehicleStatusService vehicleStatusService;
	VehicleStatusBL VehStBL = new VehicleStatusBL();

	public VehicleStatusController() {
		super();
	}

	@RequestMapping(value = "/addVehicleStatus", method = RequestMethod.GET)
	public ModelAndView addVehicleType(final VehicleStatusDto vehicleStatusDto, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			model.put("vehiclestatus", vehicleStatusService.findAll());
			model.put("vehiclestatusCount", vehicleStatusService.count());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Add Vehicle Status Page:", e);
		}
		return new ModelAndView("addVehicleStatus", model);
	}
	
	@RequestMapping(value = "/addVehicleStatusPermission", method = RequestMethod.GET)
	public ModelAndView addVehicleStatusPermission(final VehicleStatusDto vehicleStatusDto, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			model.put("vehiclestatuspermission", vehicleStatusService.findAll());
			model.put("vehiclestatus", vehicleStatusService.findAllVehicleStatus());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Add Vehicle Status Page:", e);
		}
		return new ModelAndView("addVehicleStatusWithPermission", model);
	}

	@RequestMapping(value = "/saveVehStatus", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveVehicleStatus(@RequestParam("userID") final Long userID,
			@Valid final VehicleStatusDto vehicleStatusDto, final HttpServletRequest request) {
		LOGGER.error("Registering VehicleStatus account with information: {}", vehicleStatusDto);

		Map<String, Object> model = new HashMap<String, Object>();
		try {

			final VehicleStatus save_status = VehStBL.prepareModel(vehicleStatusDto);

			// find name already is there are not
			final VehicleStatus find_name = vehicleStatusService.findByVStatus(save_status.getvStatus());
			if (find_name == null) {
				vehicleStatusService.registerNewVehicleStatus(save_status);
			} else {
				model.put("alreadyVehicleStatus", true);
				return new ModelAndView("redirect:/addVehicleStatus.html", model);
			}

			model.put("saveVehicleStatus", true);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("add Vehicle Status Page:", e);
			model.put("alreadyVehicleStatus", true);
			return new ModelAndView("redirect:/addVehicleStatus.html", model);
		}
		return new ModelAndView("redirect:/addVehicleStatus.html", model);
	}

	@RequestMapping(value = "/saveVehicleStatusPermission", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView saveVehicleStatusPermission(@RequestParam("sid") final Long sid,
			 final HttpServletRequest request) {
		//LOGGER.error("Registering VehicleStatus account with information: {}", vehicleStatusDto);

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 VehicleStatus	vehicleStatusDto	=	vehicleStatusService.getVehicleStatusByID(sid);
			
		 VehicleStatusPermission	permission =  vehicleStatusService.getVehicleStatusPermissionByID(sid, userDetails.getCompany_id());
		 
		 	if(permission == null) {
		 		final VehicleStatusPermission save_status = VehStBL.prepareModelForVehicleStatusPermission(userDetails, vehicleStatusDto);
		 		
		 		// find name already is there are not
		 		
		 		vehicleStatusService.addNewVehicleStatusPermission(save_status);
		 		
		 		model.put("saveVehicleStatus", true);
		 	}else {
		 		model.put("alreadyVehicleStatus", true);
				return new ModelAndView("redirect:/addVehicleStatusPermission.html", model);
		 	}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("add Vehicle Status Page:", e);
			model.put("alreadyVehicleStatus", true);
			return new ModelAndView("redirect:/addVehicleStatusPermission.html", model);
		}
		return new ModelAndView("redirect:/addVehicleStatusPermission.html", model);
	}

	
	@RequestMapping(value = "/editVehicleStatus", method = RequestMethod.GET)
	public ModelAndView editVehicleStatus(final Locale locale, @RequestParam("sid") final long sid) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			VehicleStatus vehiclestatus = vehicleStatusService.getVehicleStatusByID(sid);
			model.put("vehiclestatus", vehiclestatus);
			if (vehiclestatus == null) {
				model.put("emptyVehicleStatus", true);
				return new ModelAndView("redirect:/addVehicleStatus.html", model);
			}
			model.put("vehiclestatusCount", vehicleStatusService.count());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Edit Vehicle Status Page:", e);
		}
		return new ModelAndView("editVehicleStatus", model);
	}

	@RequestMapping(value = "/updateVehStatus", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView updateVehicleStatus(@RequestParam("userID") final Long userID, final Locale locale,
			@Valid final VehicleStatusDto vehicleStatusDto, final HttpServletRequest request) {
		LOGGER.error("Registering VehicleStatus account with information: {}");

		Map<String, Object> model = new HashMap<String, Object>();
		try {

			Date currentDateUpdate = new Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			vehicleStatusService.updateVehicleStatus(vehicleStatusDto.getvStatus(),
					vehicleStatusDto.getLastModifiedBy(), toDate, vehicleStatusDto.getSid());

			model.put("updateVehicleStatus", true);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("add Vehicle Status Page:", e);
			model.put("alreadyVehicleStatus", true);
			return new ModelAndView("redirect:/addVehicleStatus.html", model);
		}
		return new ModelAndView("redirect:/addVehicleStatus.html", model);
	}

	@RequestMapping(value = "/deleteVehicleStatus", method = RequestMethod.GET)
	public ModelAndView deleteVehicleStatus(final Locale locale, @RequestParam("sid") final long sid) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			vehicleStatusService.deleteVehicleStatus(sid);

			model.put("deleteVehicleStatus", true);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("add Vehicle Status Page:", e);
			model.put("alreadyVehicleStatus", true);
			return new ModelAndView("redirect:/addVehicleStatus.html", model);
		}
		return new ModelAndView("redirect:/addVehicleStatus.html", model);
	}
	@RequestMapping(value = "/deleteVehicleStatusPermission", method = RequestMethod.GET)
	public ModelAndView deleteVehicleStatusPermission(final Locale locale, @RequestParam("sid") final long sid) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleStatusService.deleteVehicleStatusPermission(sid, userDetails.getCompany_id());

			model.put("deleteVehicleStatus", true);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("add Vehicle Status Page:", e);
			model.put("alreadyVehicleStatus", true);
			return new ModelAndView("redirect:/addVehicleStatusPermission.html", model);
		}
		return new ModelAndView("redirect:/addVehicleStatusPermission.html", model);
	}
	
	
	// select Ajax Drop down value
	@RequestMapping(value = "/getVehicleStatus", method = RequestMethod.GET)
	public void getVehicleStatus(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<VehicleStatus> group = new ArrayList<VehicleStatus>();
		for (VehicleStatus add : vehicleStatusService.findAll()) {
			VehicleStatus wadd = new VehicleStatus();
			// wadd.setVid(add.getVid());
			wadd.setvStatus(add.getvStatus());
			wadd.setSid(add.getSid());
			// System.out.println(add.getVid());
			group.add(wadd);
		}

		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));
		response.getWriter().write(gson.toJson(group));
	}

}