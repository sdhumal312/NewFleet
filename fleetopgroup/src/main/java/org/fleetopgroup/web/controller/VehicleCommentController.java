package org.fleetopgroup.web.controller;
/**
 * @author fleetop
 *
 *
 *
 */

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleCommentBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.VehicleComment;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VehicleCommentController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehicleService vehicleService;
	
	@Autowired private IVehicleDocumentService	vehicleDocumentService;

	
	VehicleBL VBL = new VehicleBL();
	VehicleCommentBL VCBL = new VehicleCommentBL();

	/** This controller show vehicle comment details */
	@RequestMapping("/VehicleCommentDetails")
	public ModelAndView VehicleCommentDetails(@RequestParam("Id") final Integer Vehicle_ID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		VehicleDto vehicle = null;
		try {
			if (Vehicle_ID != null) {
				/** Show Only Select Column Value in Vehicle Tables **/
				vehicle = vehicleService.Get_Vehicle_Header_Details(Vehicle_ID);
				if (vehicle != null) {
					CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
							.getAuthentication().getPrincipal();
					model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicle));
					// vehicle Document Master Type
					model.put("vehicleComment", VCBL.prepare_VehicleComment_ListofDto(
							vehicleService.Get_Vehicle_ID_Comment_Details(Vehicle_ID, userDetails.getCompany_id())));

					Object[] count = vehicleService
							.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(Vehicle_ID);
					model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(Vehicle_ID, userDetails.getCompany_id()));
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
			} else {
				return new ModelAndView("redirect:/NotFound.in");
			}

		} catch (Exception e) {
			System.err.println("Exception "+e);
			LOGGER.error("Add Vehicle Status Page:", e);
		}
		return new ModelAndView("Show_Vehicle_Comment", model);
	}

	/** This controller request in Vehicle Comment Show Page Vehicle list */
	@RequestMapping(value = "/saveVehicleComment", method = RequestMethod.POST)
	public ModelAndView saveVehicleComment(@RequestParam("VID") final Integer Vehicle_ID,
			final VehicleComment vehicleComment, final HttpServletRequest request) {
		try {
			/** who Create this Vehicle get email id to user profile details */
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			VehicleComment saveComment = new VehicleComment();

			saveComment.setVEHICLE_ID(Vehicle_ID);
			saveComment.setVEHICLE_TITLE(vehicleComment.getVEHICLE_TITLE());
			saveComment.setVEHICLE_COMMENT(vehicleComment.getVEHICLE_COMMENT());

			saveComment.setCREATEDBYID(userDetails.getId());

			saveComment.setMarkForDelete(false);
			saveComment.setCOMPANY_ID(userDetails.getCompany_id());
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			/** Set Created Current Date */
			saveComment.setCREATED_DATE(toDate);

			vehicleService.add_Vehicle_Comment_Details(saveComment);

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Vehicle Page:", e);
			return new ModelAndView("redirect:/VehicleCommentDetails?Id=" + Vehicle_ID + "&danger=true");
		}
		return new ModelAndView("redirect:/VehicleCommentDetails?Id=" + Vehicle_ID + "&saveComment=true");
	}

	/** this Controller Delete Vehicle Comment Details to ID */
	@RequestMapping("/deleteVehicleComment")
	public ModelAndView deleteVehicleComment(@RequestParam("VId") final Integer Vehicle_ID,
			@RequestParam("CId") final Long Vehicle_Comment_ID, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			// delete method
			vehicleService.Delete_Vehicle_Comment_Details(Vehicle_Comment_ID, userDetails.getCompany_id());
			// this message alert of show method
			model.put("deleteComment", true);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Add Vehicle Status Page:", e);
			return new ModelAndView("redirect:/VehicleCommentDetails?Id=" + Vehicle_ID + "", model);
		}
		return new ModelAndView("redirect:/VehicleCommentDetails?Id=" + Vehicle_ID + "", model);
	}

}