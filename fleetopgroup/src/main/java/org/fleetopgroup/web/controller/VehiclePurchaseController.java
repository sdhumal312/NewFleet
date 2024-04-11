package org.fleetopgroup.web.controller;

/**
 * @author fleetop
 *
 */

import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleDocTypeBL;
import org.fleetopgroup.persistence.bl.VehiclePurchaseBL;
import org.fleetopgroup.persistence.bl.VehiclePurchaseTypeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehiclePurchaseDto;
import org.fleetopgroup.persistence.model.VehiclePurchase;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehiclePurchaseInfoTypeService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VehiclePurchaseController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehiclePurchaseInfoTypeService vehiclePurTypeService;
	@Autowired
	private IVehicleService vehicleService;
	
	@Autowired IVehicleDocumentService	vehicleDocumentService;

	VehicleBL VBL = new VehicleBL();
	VehicleDocTypeBL V_Doc_TYBL = new VehicleDocTypeBL();
	VehiclePurchaseTypeBL V_PU_TyBL = new VehiclePurchaseTypeBL();
	VehiclePurchaseBL VPurBL = new VehiclePurchaseBL();

	/* Driver Reminder */
	@RequestMapping("/ShowVehiclePurchase")
	public ModelAndView ShowVehiclePurchase(@ModelAttribute("command") VehiclePurchase vehiclePurchase,
			BindingResult result) {
		Map<String, Object> model 			= new HashMap<String, Object>();
		VehicleDto				vehicle			= null;
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicle	= vehicleService.Get_Vehicle_Header_Details(vehiclePurchase.getVehid());
			if(vehicle != null) {
				model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicle));
				// vehicle Document Master Type
				model.put("vehiclepurchasetype", V_PU_TyBL.prepareListofDto(vehiclePurTypeService.findAllByCompanyId(userDetails.getCompany_id())));
				/* list of driver reminder */
				model.put("vehiclepurchaseList", vehicleService.listVehiclePurchase(vehiclePurchase.getVehid(), userDetails.getCompany_id()));
				
				Object[] count = vehicleService
						.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(vehiclePurchase.getVehid());
				model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(vehiclePurchase.getVehid(), userDetails.getCompany_id()));
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
			}
			
			
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			LOGGER.error("Add Vehicle Purchase Info Page:", e);

		}finally {
			vehicle	= null;
		}
		return new ModelAndView("Show_Vehicle_Purchase", model);
	}

	// sort the Document Name
	@RequestMapping("/sortVehiclePurchase")
	public ModelAndView sortVehiclePurchase(@ModelAttribute("command") VehiclePurchase VehiclePurchaseBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// First get the driver_remid all information
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VehiclePurchaseDto vehicleDocument = VPurBL
					.GetVehiclePurchase(vehicleService.getVehiclePurchase(VehiclePurchaseBean.getId(), userDetails.getCompany_id()));
			model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(vehicleDocument.getVehid())));
			// vehicle Document Master Type
			model.put("vehiclepurchasetype", V_PU_TyBL.prepareListofDto(vehiclePurTypeService.findAllByCompanyId(userDetails.getCompany_id())));
			/* list of driver reminder */
			model.put("vehiclepurchaseList", vehicleService.listVehiclePurchase(vehicleDocument.getVehid(), userDetails.getCompany_id()));
		} catch (Exception e) {
			LOGGER.error("Add Vehicle Purchase Info Page:", e);
		}
		return new ModelAndView("Show_Vehicle_Purchase", model);
	}

	// save vehicle Document image and value

	@RequestMapping(value = "/saveVehiclePurchase")
	public String singleUpload() {
		return "saveVehiclePurchase";
	}

	@RequestMapping(value = "/saveVehiclePurchase", method = RequestMethod.POST)
	public @ResponseBody ModelAndView singleSave(@RequestParam("fileUpload") MultipartFile file,
			VehiclePurchase photo) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			if (!file.isEmpty()) {
				try {
					CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					byte[] bytes = file.getBytes();
					photo.setFilename(file.getOriginalFilename());
					photo.setContent(bytes);
					photo.setContentType(file.getContentType());

					photo.setUploaddateOn(new Date());
					photo.setCompanyId(userDetails.getCompany_id());
					photo.setCreatedById(userDetails.getId());
					vehicleService.addVehiclePurchase(photo);

				} catch (Exception e) {
					e.printStackTrace();
					// messages
					model.put("emptyDocument", true);
					LOGGER.error("Add Vehicle Purchase Page:", e);
					return new ModelAndView("redirect:/ShowVehiclePurchase.in?vehid=" + photo.getVehid() + "", model);
				}
			} else {
				// messages
				model.put("emptyDocument", true);
				return new ModelAndView("redirect:/ShowVehiclePurchase.in?vehid=" + photo.getVehid() + "", model);

			}
			// messages
			model.put("saveVehiclePurchase", true);
		} catch (Exception e) {

			// messages
			model.put("alreadyVehiclePurchase", true);

			LOGGER.error("Add Vehicle Purchase Info Page:", e);
			return new ModelAndView("redirect:/ShowVehiclePurchase.in?vehid=" + photo.getVehid() + "", model);
		}

		return new ModelAndView("redirect:/ShowVehiclePurchase.in?vehid=" + photo.getVehid() + "", model);
	}

	// Image Document the Document id
	@RequestMapping("/downloadPurchase/{documentId}")
	public String download(@PathVariable("documentId") Integer documentId, HttpServletResponse response) {
		try {

			if (documentId == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				// Lookup file by fileId in database.
				VehiclePurchase file = vehicleService.getDownloadPurchase(documentId, userDetails.getCompany_id());
				// Check if file is actually retrieved from database.
				if (file != null) {
					if (file.getContent() != null) {

						response.setHeader("Content-Disposition", "inline;filename=\"" + file.getFilename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getContentType());
						response.getOutputStream().write(file.getContent());
						out.flush();
						out.close();

					}
				}
			}
		} catch (Exception e1) {
			LOGGER.error("Add Vehicle Purchase Info Page:", e1);
		}
		return null;
	}

	// edit Vehicle Document and revise the Image
	@RequestMapping(value = "/editVehiclePurchase", method = RequestMethod.GET)
	public ModelAndView editVehicle(@ModelAttribute("command") VehiclePurchase VehiclePurchaseBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VehiclePurchaseDto vehi = VPurBL
					.prepareVehiclePurchaseBean(vehicleService.getVehiclePurchase(VehiclePurchaseBean.getId(), userDetails.getCompany_id()));
			model.put("vehiclepurchase", vehi);
			model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(vehi.getVehid())));

		} catch (Exception e) {
			System.err.println("Exception "+e);
			LOGGER.error("Add Vehicle Purchase Info Page:", e);
		}
		return new ModelAndView("editVehiclePurchase", model);
	}

	/* Delete the Driver Document */
	@RequestMapping("/deleteVehiclePurchase")
	public ModelAndView deleteVehicleDocument(@ModelAttribute("command") VehiclePurchase VehiclePurchaseBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// delete method
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleService.deleteVehiclePurchase(VehiclePurchaseBean.getId(), userDetails.getCompany_id());
			// this message alert of show method
			model.put("deleteVehiclePurchase", true);
		} catch (Exception e) {
			LOGGER.error("Add Vehicle Purchase Info Page:", e);
			return new ModelAndView("redirect:/ShowVehiclePurchase.in?vehid=" + VehiclePurchaseBean.getVehid() + "", model);
		}
		return new ModelAndView("redirect:/ShowVehiclePurchase.in?vehid=" + VehiclePurchaseBean.getVehid() + "", model);
	}

}