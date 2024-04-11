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
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.fleetopgroup.persistence.bl.VehicleGroupBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleGroupDto;
import org.fleetopgroup.persistence.model.VehicleGroup;
import org.fleetopgroup.persistence.model.VehicleGroupHistory;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
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

@Controller
public class VehicleGroupController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehicleGroupService vehicleGroupService;

	@Autowired
	private IVehicleGroupHistoryService vehicleGroupHistoryService;
	
	
	VehicleGroupBL VehGBL = new VehicleGroupBL();

	public VehicleGroupController() {
		super();
	}

	@RequestMapping(value = "/addVehicleGroup", method = RequestMethod.GET)
	public ModelAndView addVehicleGroup( final VehicleGroupDto vehicleGroupDto,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails 	currentUser		=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("vehiclegroup", vehicleGroupService.findAllVehicleGroupByCompanyId(currentUser.getCompany_id()));
			model.put("vehicleGroupCount", vehicleGroupService.findCountOfVehicleGroupByCompanyId(currentUser.getCompany_id()));

		} catch (Exception e) {
			LOGGER.error("Add Vehicle Group Page:", e);
		}
		return new ModelAndView("addVehicleGroup", model);
	}

	@RequestMapping(value = "/saveVehGroup", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveVehicleGroup(
			@Valid final VehicleGroupDto vehicleGroupDto, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails 	currentUser		=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			VehicleGroup getUIGroup = VehGBL.prepareModel(vehicleGroupDto);
			getUIGroup.setCompany_Id(currentUser.getCompany_id());
			getUIGroup.setCreatedById(currentUser.getId());
			
			final VehicleGroup validate_group = vehicleGroupService.findByVGroup(getUIGroup.getvGroup(),currentUser.getCompany_id());
			
			if (validate_group == null) {
				vehicleGroupService.registerNewVehicleGroup(getUIGroup);
				model.put("saveVehicleGroup", true);
			} else {
				model.put("alreadyVehicleGroup", true);
				return new ModelAndView("redirect:/addVehicleGroup", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("add Vehicle Group Page:", e);
			model.put("alreadyVehicleGroup", true);
			return new ModelAndView("redirect:/addVehicleGroup", model);
		}
		return new ModelAndView("redirect:/addVehicleGroup", model);
	}

	@RequestMapping(value = "/editVehicleGroup", method = RequestMethod.GET)
	public ModelAndView deleteEmployee(final Locale locale, 
			@RequestParam("gid") final long gid) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			
			model.put("vehiclegroup", vehicleGroupService.getVehicleGroupByID(gid));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("add Vehicle Group Page:", e);
			model.put("alreadyVehicleGroup", true);
			return new ModelAndView("redirect:/addVehicleGroup", model);
		}
		return new ModelAndView("editVehicleGroup", model);
	}

	@RequestMapping(value = "/updateVehGroup", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView updateVehicleGroup(final Locale locale, 
			final VehicleGroupDto vehicleGroupDto, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails 	currentUser		=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Date currentDateUpdate = new Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			final VehicleGroup validate_group = vehicleGroupService.findByVGroup(vehicleGroupDto.getvGroup(),currentUser.getCompany_id());
			
			VehicleGroupHistory     vehicleGroupHistory		=  VehGBL.prepareHistoryModel(vehicleGroupService.getVehicleGroupByID(vehicleGroupDto.getGid()));
			
			if(validate_group == null) {
				vehicleGroupService.updateVehicleGroup(vehicleGroupDto.getvGroup(), currentUser.getId(),
						toDate, vehicleGroupDto.getGid(), currentUser.getCompany_id());
				vehicleGroupHistoryService.registerNewVehicleGroupHistyory(vehicleGroupHistory);
				model.put("updateVehicleGroup", true);
			}else {
				model.put("alreadyVehicleGroup", true);
				return new ModelAndView("redirect:/addVehicleGroup", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("update Vehicle Group Page:", e);
			model.put("alreadyVehicleGroup", true);
			return new ModelAndView("redirect:/addVehicleGroup", model);
		}
		return new ModelAndView("redirect:/addVehicleGroup", model);
	}

	@RequestMapping(value = "/deleteVehicleGroup", method = RequestMethod.GET)
	public ModelAndView deleteVehicleGroup(final Locale locale, 
			@RequestParam("gid") final long gid) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			VehicleGroupHistory     vehicleGroupHistory		=  VehGBL.prepareHistoryModel(vehicleGroupService.getVehicleGroupByID(gid));
			
			vehicleGroupService.deleteVehicleGroup(gid, userDetails.getCompany_id());
			vehicleGroupHistoryService.registerNewVehicleGroupHistyory(vehicleGroupHistory);
			
			model.put("deleteVehicleGroup", true);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Delete Vehicle Group Page:", e);
			model.put("alreadyVehicleGroup", true);
			return new ModelAndView("redirect:/addVehicleGroup", model);

		}
		return new ModelAndView("redirect:/addVehicleGroup", model);
	}
	
	
	@GetMapping(value = "/getVehicleGroup")
	public void getVehicleGroup(Map<String, Object> map, HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			CustomUserDetails 	currentUser		=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<VehicleGroup> 	group			= new ArrayList<>();
			List<VehicleGroup> 	finalList		= new ArrayList<>();
			
			group = vehicleGroupService.getVehiclGroupByPermission(currentUser.getCompany_id());
			
			if(group != null && !group.isEmpty()) {
				for (VehicleGroup add : group) {
					VehicleGroup wadd = new VehicleGroup();
					wadd.setvGroup(add.getvGroup());
					wadd.setGid(add.getGid());
					finalList.add(wadd);
				}
			}

			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(group));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}