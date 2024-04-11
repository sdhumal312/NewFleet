package org.fleetopgroup.rest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentIncomeExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentPaymentDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class VehicleAgentPaymentController {


	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired private IVehicleService 							vehicleService;
	@Autowired private IVehicleAgentIncomeExpenseDetailsService	vehicleAgentIncomeExpenseDetailsService;
	@Autowired private IVehicleAgentPaymentDetailsService		vehicleAgentPaymentDetailsService;
	
	
	VehicleBL VBL = new VehicleBL();
	
	@RequestMapping("/VehicleAgentPayment")
	public ModelAndView VehicleBatteryLayoutCreation(@RequestParam("Id") final Integer Vehicle_ID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (Vehicle_ID != null) {
				/** Show Only Select Column Value in Vehicle Tables **/
				CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				model.put("vehicle",
						VBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(Vehicle_ID)));
				model.put("vehicleOwnerTotal", vehicleAgentIncomeExpenseDetailsService.getVehicleAgentClosingBalanceByDate(Vehicle_ID, DateTimeUtility.getCurrentDateInString()));
				model.put("userDetails", userDetails);

			} else {
				return new ModelAndView("redirect:/NotFound.in");
			}

		} catch (Exception e) {
			LOGGER.error("VehicleAgentPayment page:", e);
		}
		return new ModelAndView("VehicleAgentPayment", model);
	}
	
	@RequestMapping(value = "/getVehicleAgentPaymentDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleAgentPaymentDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleAgentIncomeExpenseDetailsService.getVehicleAgentPaymentDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveVehicleAgentPayment", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveVehicleAgentPayment(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleAgentPaymentDetailsService.saveVehicleAgentPayment(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
}
