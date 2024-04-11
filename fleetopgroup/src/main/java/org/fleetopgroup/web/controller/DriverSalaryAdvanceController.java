package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author fleetop
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.DriverAdvanceType;
import org.fleetopgroup.constant.TripDailySheetStatus;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.TripCollectionBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.dto.DriverPaidAdvanceDto;
import org.fleetopgroup.persistence.dto.DriverSalaryAdvanceDto;
import org.fleetopgroup.persistence.model.DriverSalaryAdvance;
import org.fleetopgroup.persistence.model.DriverSalaryAdvanceSequenceCounter;
import org.fleetopgroup.persistence.model.TripCollectionSheet;
import org.fleetopgroup.persistence.model.TripDailySheet;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IDriverPaidAdvanceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverSalaryAdvanceSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverSalaryAdvanceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DriverSalaryAdvanceController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IDriverService driverService;
	DriverBL DBL = new DriverBL();

	TripCollectionBL TCBL = new TripCollectionBL();

	@Autowired
	private IDriverSalaryAdvanceService DriverSalaryAdvanceService;

	@Autowired
	private ITripDailySheetService TripDailySheetService;

	@Autowired
	private IDriverPaidAdvanceService DriverPaidAdvanceService;

	@Autowired
	private IDriverSalaryAdvanceSequenceService	sequenceService;
	
	@Autowired IDriverDocumentService	driverDocumentService;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@RequestMapping(value = "/DriverSalaryAdvance", method = RequestMethod.GET)
	public ModelAndView NewDriverSalaryAdvance(@RequestParam("ID") final Integer DRIVER_ID,
			TripCollectionSheet DayCollection, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			DriverDto Trip_JAMA = driverService.getDriver_Header_Show_Details(DRIVER_ID);
			if(Trip_JAMA != null) {
				model.put("driver", DBL.Show_Driver_Header_Details(Trip_JAMA));
				
				List<DriverSalaryAdvance> DriverAdvanvce = DriverSalaryAdvanceService
						.List_Total_OF_DriverSalaryAdvance_details(DRIVER_ID, userDetails.getCompany_id());
				
				model.put("DriverAdvanvce", DBL.prepare_DriverASalaryAdvance(DriverAdvanvce));
				
				Object[] count = driverService.countTotal_REMINDER_DOC_COMMENT__AND_PHOTO(DRIVER_ID);
				
				model.put("ReminderCount", (Long) count[0]);
				//model.put("DocumentCount", (Long) count[1]);
				model.put("CommentCount", (Long) count[1]);
				model.put("PhotoCount", (Long) count[2]);
				model.put("DocumentCount", driverDocumentService.getDriverDocumentCount(DRIVER_ID, userDetails.getCompany_id()));
		
			}

		} catch (Exception e) {
			LOGGER.error("driver Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("New_DriverSalary_Advance", model);
	}

	@RequestMapping(value = "/paidDriSarAdv", method = RequestMethod.GET)
	public ModelAndView paidDriSarAdv(@RequestParam("DSID") final Long DRIVER_SALARYID,
			@RequestParam("DID") final Integer DRIVER_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			DriverDto Trip_JAMA = driverService.getDriver_Header_Show_Details(DRIVER_ID);
			if(Trip_JAMA != null) {
				model.put("driver", DBL.Show_Driver_Header_Details(Trip_JAMA));
				DriverSalaryAdvance DriverAdvanvce = DriverSalaryAdvanceService
						.GET_DRIVER_SALARY_ADVANCE_ID(DRIVER_SALARYID, userDetails.getCompany_id());
				
				model.put("DriverAdvanvce", DBL.getDriverASalaryAdvance(DriverAdvanvce));
				List<DriverPaidAdvanceDto> DriverPay = DriverPaidAdvanceService
						.GET_DRIVER_PAY_SALARY_ADVACNE_ID(DRIVER_SALARYID, userDetails.getCompany_id());
				model.put("DriverPayAdvance", DriverPay);
			}

		} catch (Exception e) {
			LOGGER.error("driver Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("DriverSAPayHistory", model);
	}

	@RequestMapping(value = "/deleteDriSarAdv", method = RequestMethod.GET)
	public ModelAndView deleteDriSarAdv(@RequestParam("DSID") final Long DRIVER_SALARYID,
			@RequestParam("DID") final Integer DRIVER_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<DriverPaidAdvanceDto> DriverPay = DriverPaidAdvanceService
					.GET_DRIVER_PAY_SALARY_ADVACNE_ID(DRIVER_SALARYID, userDetails.getCompany_id());

			if (DriverPay != null && !DriverPay.isEmpty()) {
				model.put("danger", true);
			} else {
				DriverSalaryAdvanceService.DELETE_DRIVER_SALARY_ADVANCE_ID(DRIVER_SALARYID, userDetails.getCompany_id());

				model.put("deleted", true);
			}

		} catch (Exception e) {
			LOGGER.error("driver Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/DriverSalaryAdvance.in?ID=" + DRIVER_ID + "", model);
	}

	@RequestMapping(value = "/addDriSalAdv", method = RequestMethod.GET)
	public ModelAndView addDriverSalaryAdvance(@RequestParam("ID") final Integer DRIVER_ID,
			TripCollectionSheet DayCollection, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			// Double DriverSalaryAdvanceAmount = 0.0;
			DriverDto Trip_JAMA = driverService.getDriver_Header_Show_Details(DRIVER_ID);
			if(Trip_JAMA != null) {
				model.put("driver", DBL.Show_Driver_Header_Details(Trip_JAMA));
				
				/**
				 * who Create this get email id to user profile details
				 */
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				CustomUserDetails userName = (CustomUserDetails) auth.getPrincipal();
				model.put("userName", userName.getFirstName());
				
				Double DriverAdvanvce = DriverSalaryAdvanceService.DRIVER_Advance_BALANCE_TripCollectiom(DRIVER_ID);
				
				// DriverSalaryAdvanceAmount +=
				// TCBL.DriverSalaryAdvance_Total_Amount(DriverAdvanvce);
				
				model.put("AdvanceBalance", round(DriverAdvanvce, 2));
				
				Object[] count = driverService.countTotal_REMINDER_DOC_COMMENT__AND_PHOTO(DRIVER_ID);
				
				model.put("ReminderCount", (Long) count[0]);
				//model.put("DocumentCount", (Long) count[1]);
				model.put("CommentCount", (Long) count[1]);
				model.put("PhotoCount", (Long) count[2]);
				model.put("DocumentCount", driverDocumentService.getDriverDocumentCount(DRIVER_ID, userName.getCompany_id()));
		
			}

		} catch (Exception e) {
			LOGGER.error("driver Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("Add_DriverSalary_Advance", model);
	}

	/* Save the driver information in db */
	@RequestMapping(value = "/saveDriverSalaryAdvance", method = RequestMethod.POST)
	public ModelAndView saveDriverSalaryAdvance(@ModelAttribute("command") DriverSalaryAdvanceDto AdvanceDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		DriverSalaryAdvanceSequenceCounter	sequenceCounter	= null;
		try {

			DriverSalaryAdvance Advance = DBL.prepare_DriverSalary_Advance(AdvanceDto);
			sequenceCounter	= sequenceService.findNextSequenceNumber(Advance.getCOMPANY_ID());
			if(sequenceCounter == null) {
				model.put("sequenceNotFound", true);
				return new ModelAndView("redirect:/getDriversList.html", model);
			}
			Advance.setDSANUMBER(sequenceCounter.getNextVal());
			try {
				if (AdvanceDto.getADVANCE_DATE() != null) {
					/** Reported_Date converted String to date Format */
					java.util.Date date = dateFormat.parse(AdvanceDto.getADVANCE_DATE());
					Date Reported_Date = new Date(date.getTime());
					Advance.setADVANCE_DATE(Reported_Date);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (Advance.getADVANCE_AMOUNT() != 0.0 && Advance.getADVANCE_AMOUNT() != null) {

				DriverSalaryAdvanceService.register_New_DriverSalaryAdvance(Advance);
				//sequenceService.updateNextSequenceCounter(sequenceCounter.getNextVal() +1, sequenceCounter.getSequence_Id());

			} else {
				model.put("advance", true);
				return new ModelAndView("redirect:/DriverSalaryAdvance.in?ID=" + AdvanceDto.getDRIVER_ID() + "", model);
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			model.put("alreadyDriver", true);
			LOGGER.error("Driver Page:", e);
			return new ModelAndView("redirect:/getDriversList.html", model);

		}
		model.put("success", true);
		return new ModelAndView("redirect:/DriverSalaryAdvance.html?ID=" + AdvanceDto.getDRIVER_ID() + "", model);
	}

	/* Save the driver information in db */
	@RequestMapping(value = "/saveTripDailyPenalty", method = RequestMethod.POST)
	public ModelAndView saveTripDailyPenalty(@ModelAttribute("command") DriverSalaryAdvanceDto AdvanceDto,
			BindingResult result, final RedirectAttributes redirectAttributes) {
		Map<String, Object> model = new HashMap<String, Object>();
		DriverSalaryAdvanceSequenceCounter	sequenceCounter	= null;
		try {

			DriverSalaryAdvance Advance = DBL.prepare_DriverSalary_Advance_TRIPDAILY_SHEET(AdvanceDto);
			sequenceCounter	= sequenceService.findNextSequenceNumber(Advance.getCOMPANY_ID());
			
			if(sequenceCounter == null) {
				redirectAttributes.addFlashAttribute("alreadyDriver", true);
				model.put("sequenceNotFound", true);
				
				return new ModelAndView("redirect:/getDriversList.html", model);
			}
			Advance.setDSANUMBER(sequenceCounter.getNextVal());
			try {
				if (AdvanceDto.getADVANCE_DATE() != null && AdvanceDto.getADVANCE_DATE() != "") {
					/** Reported_Date converted String to date Format */
					java.util.Date date = dateFormat.parse(AdvanceDto.getADVANCE_DATE());
					Date Reported_Date = new Date(date.getTime());
					Advance.setADVANCE_DATE(Reported_Date);
				} else {
					String currentDate = dateFormat.format(new java.util.Date());
					java.util.Date currentDateUpdate = dateFormat.parse(currentDate);
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
					Advance.setADVANCE_DATE(toDate);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (Advance.getADVANCE_AMOUNT() != 0.0 && Advance.getADVANCE_AMOUNT() != null) {
				DriverSalaryAdvanceService.register_New_DriverSalaryAdvance(Advance);

				TripDailySheetService.UpDate_TRIP_PENALTY_AMOUNT_IN_WT(AdvanceDto.getTRIPDAILYID(), DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY);

			} else {
				model.put("advance", true);
				return new ModelAndView("redirect:/showTripDaily.in?ID=" + AdvanceDto.getTRIPDAILYID() + "", model);
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("alreadyDriver", true);
			model.put("alreadyDriver", true);
			LOGGER.error("Driver Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/getDriversList.html", model);

		}
		model.put("success", true);
		return new ModelAndView("redirect:/showTripDaily.in?ID=" + AdvanceDto.getTRIPDAILYID() + "", model);
	}

	/* Save the driver information in db */
	@RequestMapping(value = "/removeDailyPenalty", method = RequestMethod.GET)
	public ModelAndView removeDailyPenalty(@RequestParam("TSID") final Long TRIPDAILYID,
			@RequestParam("AID") final Long Advance_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			if (TRIPDAILYID != null && Advance_ID != null) {

				TripDailySheet validate = TripDailySheetService.Get_Showing_TripDaily_Sheet(TRIPDAILYID);
				if (validate != null) {
					if (validate.getTRIP_STATUS_ID() != TripDailySheetStatus.TRIP_STATUS_CLOSED) {
						DriverSalaryAdvanceService.DELETE_PENALTY_IN_TRIPSHEET_AID(Advance_ID);

						TripDailySheetService.UpDate_TRIP_PENALTY_AMOUNT_IN_WT(TRIPDAILYID, DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY);
						model.put("success", true);
					} else {
						model.put("advance", true);
						return new ModelAndView("redirect:/showTripDaily.in?ID=" + TRIPDAILYID + "", model);
					}
				}
			} else {
				model.put("advance", true);
				return new ModelAndView("redirect:/showTripDaily.in?ID=" + TRIPDAILYID + "", model);
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			model.put("alreadyDriver", true);
			LOGGER.error("Driver Page:", e);
			return new ModelAndView("redirect:/showTripDaily.in?ID=" + TRIPDAILYID + "", model);

		}
		
		return new ModelAndView("redirect:/addDailyPenalty.in?ID=" + TRIPDAILYID + "", model);
	}

}