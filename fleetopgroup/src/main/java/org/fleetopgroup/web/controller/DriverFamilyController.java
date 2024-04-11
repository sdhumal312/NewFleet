/**
 * 
 */
package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.model.DriverFamily;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IDriverFamilyService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author fleetop
 *
 */
@Controller
public class DriverFamilyController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IDriverFamilyService driverFamilyService;

	@Autowired
	private IUserProfileService userProfileService;
	
	@Autowired
	private IDriverDocumentService driverDocumentService;

	@Autowired
	private IDriverService driverService;
	DriverBL DBL = new DriverBL();

	@RequestMapping(value = "/showDriverFamily", method = RequestMethod.GET)
	public ModelAndView showDriverFamily(@RequestParam("driver_id") final Integer Driver_id,
			final HttpServletRequest request, final HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		DriverDto		driver		= null;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driver = driverService.getDriver_Header_Show_Details(Driver_id);
			if(driver != null) {
				model.put("driver", DBL.Show_Driver_Header_Details(driver));
				/* list of driver reminder */
				model.put("DFamily",
						DBL.Show_Driver_Family_list(driverFamilyService.list_Of_DriverID_to_DriverFamily(Driver_id, userDetails.getCompany_id())));
				
				Object[] count = driverService.countTotal_REMINDER_DOC_COMMENT__AND_PHOTO(Driver_id);
				
				model.put("ReminderCount", (Long) count[0]);
				//model.put("DocumentCount", (Long) count[1]);
				model.put("CommentCount", (Long) count[1]);
				model.put("PhotoCount", (Long) count[2]);
				model.put("DocumentCount", driverDocumentService.getDriverDocumentCount(Driver_id, userDetails.getCompany_id()));
		
			}

		} catch (Exception e) {
			LOGGER.error("driver Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("ShowDriverFamily", model);
	}

	// update Expense TripSheet and also Amount
	@RequestMapping(value = "/saveDriverFamily", method = RequestMethod.POST)
	public ModelAndView saveDriverFamily(@RequestParam("DRIVERID") final Integer DriverID,
			@RequestParam("DF_NAME") final String[] familyName, @RequestParam("DF_SEX") final String[] familySex,
			@RequestParam("DF_AGE") final String[] familyAge,
			@RequestParam("DF_RELATIONSHIP") final String[] expenseRelationship, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			for (int i = 0; i < familyName.length; i++) {

				DriverFamily family = new DriverFamily();
				
				family.setDRIVERID(DriverID);
				family.setDF_NAME("" + familyName[i]);
				family.setDF_SEX_ID(Short.parseShort("" + familySex[i]));
				family.setDF_AGE(Integer.parseInt("" + familyAge[i]));
				family.setDF_RELATIONSHIP_ID(Short.parseShort("" + expenseRelationship[i]));

				CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				family.setCREATEDBYID(userDetails.getId());
				family.setLASTMODIFIEDBYID(userDetails.getId());
				family.setCOMPANY_ID(userDetails.getCompany_id());
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				family.setCREATED_DATE(toDate);
				family.setLASTUPDATED_DATE(toDate);
				family.setMarkForDelete(false);

				List<DriverFamily> validate = driverFamilyService.Validate_Driver_Family_Member_Name("" + familyName[i],
						DriverID);
				if (validate != null && !validate.isEmpty()) {
					model.put("already", true);
				} else {

					driverFamilyService.Registered_DriverFamily_Details(family);
					model.put("success", true);
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/showDriverFamily.in?driver_id=" + DriverID + "", model);
	}

	/* Delete the Driver Reminder */
	@RequestMapping(value = "/deleteDriverFamily", method = RequestMethod.GET)
	public ModelAndView deleteDriverFamily(@RequestParam("DFID") final Long DFID,
			@RequestParam("DID") final Long DriverID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// delete method
			driverFamilyService.Delete_DriverFamily_Details(DFID);

			model.put("delete", true);
			return new ModelAndView("redirect:/showDriverFamily.in?driver_id=" + DriverID + "", model);

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			// messages
			model.put("danger", true);
			return new ModelAndView("redirect:/showDriverFamily.in?driver_id=" + DriverID + "", model);
		}
	}

	@RequestMapping(value = "/PrintDriverFamily", method = RequestMethod.GET)
	public ModelAndView PrintDriverFamily(@RequestParam("id") final Integer Driver_id,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));
			model.put("driver", DBL.GetDriverID(driverService.getDriverDetails(Driver_id, userDetails)));
			model.put("DFamily",
					DBL.Show_Driver_Family_list(driverFamilyService.list_Of_DriverID_to_DriverFamily(Driver_id, userDetails.getCompany_id())));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("showDriverFamilyPrint", model);
	}

}
