package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.bl.RenewalTypeBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleOwnerBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleMandatoryDto;
import org.fleetopgroup.persistence.model.VehicleMandatory;
import org.fleetopgroup.persistence.serviceImpl.IRenewalSubTypeService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalTypeService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleMandatoryService;
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
public class VehicleMandatoryController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehicleService vehicleService;

	@Autowired
	private IVehicleMandatoryService vehicleMandatoryService;

	@Autowired
	private IRenewalTypeService RenewalTypeService;

	@Autowired	IVehicleDocumentService	vehicleDocumentService;
	
	@Autowired
	private IRenewalSubTypeService RenewalSubTypeService;
	RenewalTypeBL DriDT = new RenewalTypeBL();

	VehicleBL VBL = new VehicleBL();
	VehicleOwnerBL VOBL = new VehicleOwnerBL();

	/* Driver Reminder */
	@RequestMapping("/ShowVehicleMandatory")
	public ModelAndView ShowVehicleMandatory(@RequestParam("vehid") final Integer VehicleID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		VehicleDto vehicle = null;
		try {
			vehicle = vehicleService.Get_Vehicle_Header_Details(VehicleID);
			if (vehicle != null) {
				CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicle));

				Object[] count = vehicleService
						.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(VehicleID);
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

				model.put("mandatory", vehicleMandatoryService.List_Vehicle_Mandatory_Details_GET_VEHICLEID(VehicleID,
						userDetails.getCompany_id()));
			}

		} catch (Exception e) {
			LOGGER.error("Add Vehicle Purchase Info Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("Show_Vehicle_Mandatory", model);
	}

	/* This Vehicle Mandatory Compliance To Create Details */
	@RequestMapping("/ShowVehicleMandatoryAdd")
	public ModelAndView ShowVehicleMandatoryAdd(@RequestParam("vid") final Integer VehicleID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		VehicleDto vehicle = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicle = vehicleService.Get_Vehicle_Header_Details(VehicleID);
			if (vehicle != null) {

				model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicle));

				model.put("renewalType", DriDT.RenewalListofDto(
						RenewalTypeService.findAll_VehicleMandatory_Renewal_Type(userDetails.getCompany_id())));
				model.put("renewalSubType", DriDT.RenewalSubListofDto(
						RenewalSubTypeService.findAll_VehicleMandatory_Renewal_Sub_Type(userDetails.getCompany_id())));
			}

		} catch (Exception e) {
			LOGGER.error("Add Vehicle Mandatory Info Page:", e);
			e.printStackTrace();
		} finally {
			userDetails = null;
			vehicle = null;
		}
		return new ModelAndView("Add_Vehicle_Mandatory", model);
	}

	/* This Vehicle Mandatory Compliance To Save Details */
	@RequestMapping(value = "/saveVehicleMandatory", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveVehicleMandatory(@RequestParam("VEHID") final Integer VEHICLE_VID,
			@RequestParam("VEHICLE_REGISTRATION") final String VEHICLE_REGISTRATION,
			@RequestParam("MANDATORY_ID") final String[] MANDATORYNAME, VehicleMandatory Mandatory,
			final HttpServletRequest Request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			if (VEHICLE_VID != 0 && VEHICLE_VID != null) {

				if (MANDATORYNAME != null) {
					for (int i = 0; i < MANDATORYNAME.length; i++) {
						// checking empty are not
						if (!MANDATORYNAME[i].equalsIgnoreCase("") && MANDATORYNAME[i] != null) {
							VehicleMandatory SaveMandatory = new VehicleMandatory();

							SaveMandatory.setVEHICLE_ID(VEHICLE_VID);
							// SaveMandatory.setVEHICLE_REGISTRATION(VEHICLE_REGISTRATION);
							SaveMandatory.setMANDATORY_RENEWAL_SUBID(Integer.parseInt(MANDATORYNAME[i]));

							CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
									.getAuthentication().getPrincipal();

							SaveMandatory.setCREATEDBYID(userDetails.getId());
							SaveMandatory.setLASTMODIFIEDBYID(userDetails.getId());
							java.util.Date currentDateUpdate = new java.util.Date();
							Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
							SaveMandatory.setCOMPANY_ID(userDetails.getCompany_id());
							SaveMandatory.setCREATED_DATE(toDate);
							SaveMandatory.setLASTUPDATED_DATE(toDate);
							SaveMandatory.setMarkForDelete(false);

							vehicleMandatoryService.register_New_VehicleMandatory(SaveMandatory);
						}
					}
				}
				// messages
				model.put("save", true);
			}

		} catch (Exception e) {

			// messages
			model.put("already", true);
			LOGGER.error("Add Vehicle Purchase Info Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/ShowVehicleMandatory.in?vehid=" + VEHICLE_VID + "", model);
		}

		return new ModelAndView("redirect:/ShowVehicleMandatory.in?vehid=" + VEHICLE_VID + "", model);
	}

	// edit Vehicle Document and revise the Image

	@RequestMapping(value = "/editVehicleMandatory", method = RequestMethod.GET)
	public ModelAndView editVehicleMandatory(@RequestParam("VEHID") final Integer VehicleID,

			@RequestParam("VEHID") final Integer VEHICLE_VID, final HttpServletRequest Request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		VehicleDto vehicle = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicle = vehicleService.Get_Vehicle_Header_Details(VehicleID);
			if (vehicle != null) {
				model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicle));

				model.put("renewalType", DriDT.RenewalListofDto(
						RenewalTypeService.findAll_VehicleMandatory_Renewal_Type(userDetails.getCompany_id())));
				model.put("renewalSubType", DriDT.RenewalSubListofDto(
						RenewalSubTypeService.findAll_VehicleMandatory_Renewal_Sub_Type(userDetails.getCompany_id())));

				final List<String> Mandatory = new ArrayList<String>();

				List<VehicleMandatoryDto> SelectedMandatory = vehicleMandatoryService
						.List_Vehicle_Mandatory_Details_GET_VEHICLEID(VehicleID, userDetails.getCompany_id());

				if (SelectedMandatory != null && !SelectedMandatory.isEmpty()) {
					for (VehicleMandatoryDto artist : SelectedMandatory) {

						Mandatory.add("'" + artist.getMANDATORY_RENEWAL_SUBID() + "'");
					}

				}
				model.put("mandatory", Mandatory);
			}

		} catch (Exception e) {
			LOGGER.error("Add Vehicle Mandatory Info Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/ShowVehicleMandatory.in?vehid=" + VEHICLE_VID + "");
		} finally {
			userDetails = null;
			vehicle = null;
		}

		return new ModelAndView("editVehicle_Mandatory", model);
	}

	/* This Vehicle Mandatory Compliance To Update Details */
	@RequestMapping(value = "/updateVehicleMandatory", method = RequestMethod.POST)
	public @ResponseBody ModelAndView updateVehicleMandatory(@RequestParam("VEHID") final Integer VEHICLE_VID,
			@RequestParam("VEHICLE_REGISTRATION") final String VEHICLE_REGISTRATION,
			VehicleMandatoryDto Mandatory,
			final HttpServletRequest Request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String[] MANDATORYNAME = Mandatory.getMANDATORY_ID();
			
			if (VEHICLE_VID != 0 && VEHICLE_VID != null) {

				CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				vehicleMandatoryService.Delete_VehicleMandatory_Old_Vehicle_ID(VEHICLE_VID,
						userDetails.getCompany_id());
				if (MANDATORYNAME != null) {
					for (int i = 0; i < MANDATORYNAME.length; i++) {
						// checking empty are not
						if (!MANDATORYNAME[i].equalsIgnoreCase("") && MANDATORYNAME[i] != null) {
							VehicleMandatory SaveMandatory = new VehicleMandatory();

							SaveMandatory.setVEHICLE_ID(VEHICLE_VID);
							// SaveMandatory.setVEHICLE_REGISTRATION(VEHICLE_REGISTRATION);
							SaveMandatory.setMANDATORY_RENEWAL_SUBID(Integer.parseInt(MANDATORYNAME[i]));

							SaveMandatory.setCREATEDBYID(userDetails.getId());
							SaveMandatory.setLASTMODIFIEDBYID(userDetails.getId());
							java.util.Date currentDateUpdate = new java.util.Date();
							Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

							SaveMandatory.setCREATED_DATE(toDate);
							SaveMandatory.setLASTUPDATED_DATE(toDate);
							SaveMandatory.setMarkForDelete(false);
							SaveMandatory.setCOMPANY_ID(userDetails.getCompany_id());

							vehicleMandatoryService.register_New_VehicleMandatory(SaveMandatory);
						}
					}
				}
		
				// messages
				model.put("save", true);
			}

		} catch (Exception e) {

			// messages
			model.put("already", true);
			LOGGER.error("Add Vehicle Purchase Info Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/ShowVehicleMandatory.in?vehid=" + VEHICLE_VID + "", model);
		}

		return new ModelAndView("redirect:/ShowVehicleMandatory.in?vehid=" + VEHICLE_VID + "", model);
	}

}