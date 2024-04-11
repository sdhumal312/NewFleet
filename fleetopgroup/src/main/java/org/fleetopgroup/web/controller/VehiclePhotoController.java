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
import org.fleetopgroup.persistence.bl.VehiclePhoTypeBL;
import org.fleetopgroup.persistence.bl.VehiclePhotoBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.VehiclePhoto;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehiclePhoTypeService;
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
public class VehiclePhotoController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehicleService vehicleService;
	@Autowired
	private IVehiclePhoTypeService vehiclePhoTypeService;
	
	@Autowired IVehicleDocumentService	vehicleDocumentService;

	VehiclePhoTypeBL V_Pho_TYBL = new VehiclePhoTypeBL();
	VehicleBL VBL = new VehicleBL();
	VehiclePhotoBL V_PHO_BL = new VehiclePhotoBL();
	
	ByteImageConvert ByteImageConvert = new ByteImageConvert();

	/* Driver Reminder */
	@RequestMapping("/ShowVehiclePhoto")
	public ModelAndView ShowVehicleDocument(@ModelAttribute("command") VehiclePhoto vehicleDocument,
			BindingResult result) {
		Map<String, Object> model 		= new HashMap<String, Object>();
		VehicleDto				vehicle		= null;
		CustomUserDetails	userDetails	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicle	= vehicleService.Get_Vehicle_Header_Details(vehicleDocument.getVehid());
			if(vehicle != null) {
				model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicle));
				// vehicle Document Master Type
				model.put("vehiclephotype", V_Pho_TYBL.prepareListofDto(vehiclePhoTypeService.findAllVehiclePhoToTypeByCompanyId(userDetails.getCompany_id())));
				/* list of driver reminder */
				model.put("vehiclephotoList", vehicleService.listVehiclePhoto(vehicleDocument.getVehid(), userDetails.getCompany_id()));
				
				Object[] count = vehicleService
						.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(vehicleDocument.getVehid());
				model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(vehicleDocument.getVehid(), userDetails.getCompany_id()));
				model.put("photo_Count", vehicleDocumentService.getVehiclePhotoCount(vehicleDocument.getVehid(), userDetails.getCompany_id()));
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
			System.err.println("Exception  "+e);
			LOGGER.error("Add Vehicle Photo Page:", e);

		}
		return new ModelAndView("Show_Vehicle_Photo", model);
	}

	// sort the Document Name
	@RequestMapping("/sortVehiclePhoto")
	public ModelAndView sortVehiclePhoto(@ModelAttribute("command") VehiclePhoto VehicleDocumentBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		// First get the driver_remid all information
		org.fleetopgroup.persistence.document.VehiclePhoto vehiclePhoto;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehiclePhoto = V_PHO_BL.GetVehiclePhotoProfile(vehicleService.getVehiclePhoto(VehicleDocumentBean.getId(), userDetails.getCompany_id()));
			model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(vehiclePhoto.getVehid())));
			// vehicle Document Master Type
			model.put("vehiclephotype", V_Pho_TYBL.prepareListofDto(vehiclePhoTypeService.findAllVehiclePhoToTypeByCompanyId(userDetails.getCompany_id())));
		} catch (Exception e) {
			LOGGER.error("Add Vehicle Photo Page:", e);
		}
		return new ModelAndView("Show_Vehicle_Photo", model);
	}

	// save vehicle Document image and value

	@RequestMapping(value = "/saveVehiclePhoto")
	public String singleUpload() {
		return "saveVehiclePhoto";
	}

	@RequestMapping(value = "/saveVehiclePhoto", method = RequestMethod.POST)
	public @ResponseBody ModelAndView singleSave(@RequestParam("fileUpload") MultipartFile file, org.fleetopgroup.persistence.document.VehiclePhoto photo) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (!file.isEmpty()) {
				try {
					CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					byte[] bytes = file.getBytes();
					photo.setFilename(file.getOriginalFilename());
					photo.setContent(ByteImageConvert.scale(bytes, 350, 350));
					photo.setContentType(file.getContentType());

					photo.setUploaddateOn(new Date());
					photo.setCompanyId(userDetails.getCompany_id());
					photo.setCreatedById(userDetails.getId());

					vehicleService.addVehiclePhoto(photo);

				} catch (Exception e) {
					e.printStackTrace();
					// messages
					model.put("alreadyVehiclePhoto", true);
					LOGGER.error("Add Vehicle Status Page:", e);
					return new ModelAndView("redirect:/ShowVehiclePhoto.in?vehid=" + photo.getVehid() + "", model);
				}
			} else {
				// messages
				model.put("emptyPhoto", true);
				return new ModelAndView("redirect:/ShowVehiclePhoto.in?vehid=" + photo.getVehid() + "", model);

			}
			// messages
			model.put("saveVehiclePhoto", true);
		} catch (Exception e) {
			e.printStackTrace();
			// messages
			model.put("alreadyVehicleDocument", true);
			LOGGER.error("Add Vehicle Status Page:", e);
			return new ModelAndView("redirect:/ShowVehiclePhoto.in?vehid=" + photo.getVehid() + "", model);

		}
		return new ModelAndView("redirect:/ShowVehiclePhoto.in?vehid=" + photo.getVehid() + "", model);
	}

	// Image Document the Document id
	@RequestMapping("/downloadPhoto/{documentId}")
	public String download(@PathVariable("documentId") Integer documentId, HttpServletResponse response) {
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (documentId == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				org.fleetopgroup.persistence.document.VehiclePhoto file = vehicleService.getVehiclePhoto(documentId, userDetails.getCompany_id());
				// Check if file is actually retrieved from database.
				if (file != null) {
					if (file.getContent() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getFilename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getContentType());
						response.getOutputStream().write(file.getContent());
						out.flush();
						out.close();

					}
				}
			}
		} catch (Exception e1) {
			LOGGER.error("Add Vehicle Photo Page:", e1);
		}
		return null;
	}

	/* Delete the Driver Document */
	@RequestMapping("/deleteVehiclePhoto")
	public ModelAndView deleteVehiclePhoto(@ModelAttribute("command") VehiclePhoto VehiclePhotoBean,
			HttpServletResponse response) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VehicleDto vehicle = VBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(VehiclePhotoBean.getVehid()));
			// get vehicle name
			if ((vehicle.getVehicle_photoid()).equals(VehiclePhotoBean.getId())) {
				// this message alert of show method
				model.put("deleteVehiclePhoto_was_already", true);
				return new ModelAndView("redirect:/ShowVehiclePhoto.in?vehid=" + VehiclePhotoBean.getVehid() + "",
						model);
			} else {
				// delete method
				vehicleService.deleteVehiclePhoto(VehiclePhotoBean.getId(), userDetails.getCompany_id());
				// this message alert of show method
				model.put("deleteVehiclePhoto", true);
			}
		} catch (Exception e) {
			LOGGER.error("Add Vehicle Photo Page:", e);
			model.put("deleteVehiclePhoto_was_already", true);
			return new ModelAndView("redirect:/ShowVehiclePhoto.in?vehid=" + VehiclePhotoBean.getVehid() + "", model);
		}
		return new ModelAndView("redirect:/ShowVehiclePhoto.in?vehid=" + VehiclePhotoBean.getVehid() + "", model);
	}

	/* show the image of the driver photo */
	@RequestMapping("/getImageVehicle/{vehicle_photoid}")
	public String getImage(@PathVariable("vehicle_photoid") Integer driver_vehicleid, HttpServletResponse response) {
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (driver_vehicleid == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				org.fleetopgroup.persistence.document.VehiclePhoto file = vehicleService.getVehiclePhoto(driver_vehicleid, userDetails.getCompany_id());
				// Check if file is actually retrieved from database.
				if (file != null) {
					if (file.getContent() != null) {

						response.setContentType(file.getContentType());
						response.setHeader("Content-Length", String.valueOf(file.getContent().length));
						// Write file content to response.
						response.getOutputStream().write(file.getContent());

					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Add Vehicle Photo Page:", e);
		}
		return null;
	}

	/* Set Profile Photo in vehicle */
	@RequestMapping("/setVehiclePhoto")
	public ModelAndView setVehiclephoto(@ModelAttribute("command") VehiclePhoto vehiclePhotoBean,
			HttpServletResponse response) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// delete method
			vehicleService.setprofilePhoto(vehiclePhotoBean.getId(), vehiclePhotoBean.getVehid(), userDetails.getCompany_id());
			// messages
			model.put("setVehicleProfilePhoto", true);
		} catch (Exception e) {
			LOGGER.error("Add Vehicle Status Page:", e);
			return new ModelAndView("redirect:/ShowVehiclePhoto.in?vehid=" + vehiclePhotoBean.getVehid() + "", model);
		}
		return new ModelAndView("redirect:/ShowVehiclePhoto.in?vehid=" + vehiclePhotoBean.getVehid() + "", model);
	}
	
	@RequestMapping("/removeVehiclePhoto")
	public ModelAndView removeVehiclePhoto(@ModelAttribute("command") VehiclePhoto vehiclePhotoBean,
			HttpServletResponse response) {

		Map<String, Object> model = new HashMap<String, Object>();
		Integer			defaultPhotoId			= 1;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// delete method
			vehicleService.setprofilePhoto(defaultPhotoId, vehiclePhotoBean.getVehid(), userDetails.getCompany_id());
			// messages
			model.put("setVehicleProfilePhoto", true);
		} catch (Exception e) {
			LOGGER.error("Add Vehicle Status Page:", e);
			return new ModelAndView("redirect:/ShowVehiclePhoto.in?vehid=" + vehiclePhotoBean.getVehid() + "", model);
		}
		return new ModelAndView("redirect:/ShowVehiclePhoto.in?vehid=" + vehiclePhotoBean.getVehid() + "", model);
	}
}
