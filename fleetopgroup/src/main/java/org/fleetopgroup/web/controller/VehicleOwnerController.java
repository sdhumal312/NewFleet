package org.fleetopgroup.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleOwnerBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleOwnerDto;
import org.fleetopgroup.persistence.model.VehicleOwner;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOwnerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
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
public class VehicleOwnerController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehicleService vehicleService;

	@Autowired
	private IVehicleOwnerService vehicleOwnerService;

	@Autowired IVehicleDocumentService	vehicleDocumentService;
	@Autowired private ICompanyConfigurationService companyConfigurationService;
	
	
	VehicleBL VBL = new VehicleBL();
	VehicleOwnerBL VOBL = new VehicleOwnerBL();

	/* Driver Reminder */
	@RequestMapping("/ShowVehicleOwner")
	public ModelAndView ShowVehicleOwner(@RequestParam("vehid") final Integer VehicleID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		VehicleDto				vehicle	= null;
		HashMap<String, Object> 		configuration	          = null;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			
			vehicle	= vehicleService.Get_Vehicle_Header_Details(VehicleID);
			if(vehicle != null) {
				model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicle));
				
				/* list of driver reminder */
				model.put("vehOwner", vehicleOwnerService.list_Of_Vehicle_ID_VehicleOwner(VehicleID, userDetails.getCompany_id()));
				
				Object[] count = vehicleService.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(VehicleID);
				model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(VehicleID, userDetails.getCompany_id()));
				model.put("photo_Count", (Long) count[0]);
				model.put("purchase_Count", (Long) count[1]);
				model.put("reminder_Count", (Long) count[2]);
				model.put("fuelEntrie_Count", (Long) count[3]);
				model.put("serviceEntrie_Count", (Long) count[4]);
				model.put("serviceReminder_Count", (Long) count[5]);
				model.put("tripsheet_Count", (Long) count[6]);
				model.put("workOrder_Count", (Long) count[7]);
				model.put("issues_Count", (Long) count[8]);
				model.put("odometerhis_Count", (Long) count[9]);
				model.put("comment_Count", (Long) count[10]);
				model.put("breakDownCount", ((Long) count[4]+(Long) count[8]));
				model.put("dseCount", (Long) count[12]);
				
				model.put("configuration",configuration);
				
			}

		} catch (Exception e) {
			System.err.println("Exception : "+e);
			LOGGER.error("Add Vehicle Purchase Info Page:", e);

		}
		return new ModelAndView("Show_Vehicle_Owner", model);
	}
	
	

	@RequestMapping(value = "/saveVehicleOwner", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveVehicleOwner(@RequestParam("VEHID") final Integer VEHICLE_VID,
			VehicleOwnerDto Owner, final HttpServletRequest Request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<VehicleOwner> ValidateOwner = vehicleOwnerService.Validate_VehicleOwner_name(Owner.getVEH_OWNER_NAME(),
					Owner.getVEH_OWNER_SERIAL(), userDetails.getCompany_id(), VEHICLE_VID);
			if (ValidateOwner != null && !ValidateOwner.isEmpty()) {
				model.put("already", true);
				return new ModelAndView("redirect:/ShowVehicleOwner.in?vehid=" + VEHICLE_VID + "", model);
			} else {
				VehicleOwner SaveOwner = VOBL.prepareVehicleOwnerBean(Owner);
				SaveOwner.setCOMPANY_ID(userDetails.getCompany_id());

				vehicleOwnerService.Register_New_VehicleOwner(SaveOwner);

				// messages
				model.put("save", true);
			}

		} catch (Exception e) {

			// messages
			model.put("already", true);

			LOGGER.error("Add Vehicle Purchase Info Page:", e);
			return new ModelAndView("redirect:/ShowVehicleOwner.in?vehid=" + VEHICLE_VID + "", model);
		}

		return new ModelAndView("redirect:/ShowVehicleOwner.in?vehid=" + VEHICLE_VID + "", model);
	}

	// edit Vehicle Document and revise the Image
	@RequestMapping(value = "/editVehicleOwner", method = RequestMethod.GET)
	public ModelAndView editVehicleOwner(@RequestParam("VO") final Integer Owner_VID,
			@RequestParam("VEHID") final Integer VEHICLE_VID, final HttpServletRequest Request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 		configuration	          = null;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			VehicleOwnerDto vehi = VOBL.prepare_VehicleOwnerBean(vehicleOwnerService.Get_Vehicle_Owner(Owner_VID, userDetails.getCompany_id()));
			model.put("vehOwner", vehi);
			model.put("vehicle",
					VBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(VEHICLE_VID)));
			
			model.put("configuration",configuration);

		} catch (Exception e) {
			LOGGER.error("Edit Vehicle Owner Info Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/ShowVehicleOwner.in?vehid=" + VEHICLE_VID + "", model);
		}
		return new ModelAndView("editVehicleOwner", model);
	}

	@RequestMapping(value = "/updateVehicleOwner", method = RequestMethod.POST)
	public @ResponseBody ModelAndView updateVehicleOwner(@RequestParam("VEHID") final Integer VEHICLE_VID,
			VehicleOwnerDto Owner, final HttpServletRequest Request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 		configuration	         = null;
		boolean							isownerSerailNo			 = false;
		List<VehicleOwner>		 		ValidateOwner 			 = null;	
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			isownerSerailNo	= (boolean) configuration.getOrDefault(configuration.get("showVehicleOwnerSrNo"), false);
			if(isownerSerailNo == true) {
				ValidateOwner = vehicleOwnerService.Validate_VehicleOwner_name(Owner.getVEH_OWNER_NAME(),
					Owner.getVEH_OWNER_SERIAL(), userDetails.getCompany_id(), VEHICLE_VID);
			}else {
				ValidateOwner = vehicleOwnerService.Validate_VehicleOwner_name(Owner.getVEH_OWNER_NAME(), userDetails.getCompany_id(), VEHICLE_VID);
			}
			
			if (ValidateOwner != null && !ValidateOwner.isEmpty()) {

				VehicleOwner SaveOwner = VOBL.prepareVehicleOwnerBean(Owner);
				SaveOwner.setCOMPANY_ID(userDetails.getCompany_id());

				vehicleOwnerService.Register_New_VehicleOwner(SaveOwner);

				// messages
				model.put("update", true);
			} else {
				model.put("already", true);
				return new ModelAndView("redirect:/ShowVehicleOwner.in?vehid=" + VEHICLE_VID + "", model);
			}

		} catch (Exception e) {

			// messages
			model.put("already", true);

			LOGGER.error("Add Vehicle Purchase Info Page:", e);
			return new ModelAndView("redirect:/ShowVehicleOwner.in?vehid=" + VEHICLE_VID + "", model);
		}

		return new ModelAndView("redirect:/ShowVehicleOwner.in?vehid=" + VEHICLE_VID + "", model);
	}

	/* Delete the Driver Document */
	@RequestMapping("/deleteVehicleOwner")
	public ModelAndView deleteVehicleOwner(@RequestParam("VO") final Integer Owner_VID,
			@RequestParam("VEHID") final Integer VEHICLE_VID, final HttpServletRequest Request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// delete method
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleOwnerService.Delete_VehicleOwner(Owner_VID, userDetails.getCompany_id());
			// this message alert of show method
			model.put("delete", true);
		} catch (Exception e) {
			LOGGER.error("Add Vehicle Purchase Info Page:", e);
			return new ModelAndView("redirect:/ShowVehicleOwner.in?vehid=" + VEHICLE_VID + "", model);
		}
		return new ModelAndView("redirect:/ShowVehicleOwner.in?vehid=" + VEHICLE_VID + "", model);
	}
	
	/* Driver Reminder */
	
}