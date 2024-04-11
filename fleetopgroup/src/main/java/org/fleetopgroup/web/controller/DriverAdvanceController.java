package org.fleetopgroup.web.controller;

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

import org.fleetopgroup.constant.DriverAdvanceStatus;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.TripCollectionBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverAdvanceDto;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.model.DriverAdvance;
import org.fleetopgroup.persistence.model.TripCollectionSheet;
import org.fleetopgroup.persistence.serviceImpl.IDriverAdvanceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.ITripCollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DriverAdvanceController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IDriverService driverService;
	DriverBL DBL = new DriverBL();

	@Autowired
	private ITripCollectionService TripCollectionService;
	TripCollectionBL TCBL = new TripCollectionBL();


	@Autowired
	private IDriverAdvanceService driverAdvanceService;

	@Autowired
	private IDriverDocumentService	driverDocumentService;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@RequestMapping(value = "/addDriverAdvance", method = RequestMethod.GET)
	public ModelAndView addDriverAdvance(@RequestParam("ID") final Integer DRIVER_ID, TripCollectionSheet DayCollection,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Double DriverAdvanceAmount = 0.0, DriverJAMA = 0.0, TRIPJAMA_BALANCE = 0.0;
			DriverDto Trip_JAMA = driverService.getDriver_Header_Show_Details(DRIVER_ID);
			if(Trip_JAMA != null) {
				
				model.put("driver", DBL.Show_Driver_Header_Details(Trip_JAMA));
				/** Set Created by email Id */
				
				model.put("userName", userDetails.getFirstName());
				
				List<DriverAdvance> DriverAdvanvce = driverAdvanceService.DRIVER_Advance_BALANCE_TripCollectiom(DRIVER_ID, userDetails.getCompany_id());
				
				DriverAdvanceAmount += TCBL.Driver_Advance_Total_Amount(DriverAdvanvce);
				if (Trip_JAMA.getDriver_jobtitle().equalsIgnoreCase("DRIVER")) {
					
					List<TripCollectionSheet> BALANCEDRIVER = TripCollectionService
							.DRIVER_JAMA_BALANCE_TripCollectio(DRIVER_ID);
					
					DriverJAMA += TCBL.Get_Total_DriverJAMA_TripCollection(BALANCEDRIVER);
					/* list of driver reminder */
					// sutrack advance Open status amount and driver jama balance
					TRIPJAMA_BALANCE = round(DriverJAMA - DriverAdvanceAmount, 2);
					model.put("TRIPJAMA_BALANCE", TRIPJAMA_BALANCE);
					
				} else if (Trip_JAMA.getDriver_jobtitle().equalsIgnoreCase("CONDUCTOR")) {
					List<TripCollectionSheet> BALANCECONDUCTOR = TripCollectionService
							.CONDUCTOR_JAMA_BALANCE_TripCollectio(DRIVER_ID);
					
					DriverJAMA += TCBL.get_Total_ConductorJAMA_TripCollection(BALANCECONDUCTOR);
					// sutrack advance Open status amount and driver jama balance
					TRIPJAMA_BALANCE = round(DriverJAMA - DriverAdvanceAmount, 2);
					model.put("TRIPJAMA_BALANCE", TRIPJAMA_BALANCE);
				}
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
		return new ModelAndView("Add_Driver_Advance", model);
	}

	/* Save the driver information in db */
	@RequestMapping(value = "/saveDriverAdvance", method = RequestMethod.POST)
	public ModelAndView saveDriverAdvance(@ModelAttribute("command") DriverAdvanceDto AdvanceDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			DriverDto Trip_JAMA = driverService.getDriver_Header_Show_Details(AdvanceDto.getTRIP_DRIVER_ID());
			
			if(Trip_JAMA != null) {
				
				DriverAdvance Advance = DBL.prepare_Driver_Advance(AdvanceDto);
				
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
				if (Advance.getDRIVER_JAMA_BALANCE() >= Advance.getDRIVER_ADVANCE_AMOUNT()) {
					driverAdvanceService.register_New_DriverAdvance(Advance);
					
					// check Driver jama balance and Driver Advance Balance equal
					// update status in paid and closea
					if (Advance.getDRIVER_JAMA_BALANCE().equals(Advance.getDRIVER_ADVANCE_AMOUNT())) {
						
						driverAdvanceService.update_DriverAdvance_Status(DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_PAID, AdvanceDto.getTRIP_DRIVER_ID(), Advance.getCOMPANY_ID());
						
						if (Trip_JAMA.getDriver_jobtitle().equalsIgnoreCase("DRIVER")) {
							
							TripCollectionService.update_Driver_JAMA_Status(FuelVendorPaymentMode.PAYMENT_MODE_PAID, AdvanceDto.getTRIP_DRIVER_ID(), Advance.getCOMPANY_ID());
							
						} else if (Trip_JAMA.getDriver_jobtitle().equalsIgnoreCase("CONDUCTOR")) {
							
							TripCollectionService.update_Conductor_JAMA_Status(FuelVendorPaymentMode.PAYMENT_MODE_PAID, AdvanceDto.getTRIP_DRIVER_ID(), Advance.getCOMPANY_ID());
						}
						
					}
					
				} else {
					model.put("advance", true);
					return new ModelAndView(
							"redirect:/addDriverAdvance.in?ID=" + AdvanceDto.getTRIP_DRIVER_ID() + "", model);
				}
			}


		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			model.put("alreadyDriver", true);
			LOGGER.error("Driver Page:", e);
			return new ModelAndView("redirect:/getDriversList.html", model);

		}
		model.put("success", true);
		return new ModelAndView("redirect:/showDriver.html?driver_id=" + AdvanceDto.getTRIP_DRIVER_ID() + "", model);
	}

}