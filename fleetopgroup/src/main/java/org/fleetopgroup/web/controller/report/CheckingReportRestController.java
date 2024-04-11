package org.fleetopgroup.web.controller.report;

import java.util.HashMap;
import java.util.Map;

import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleCheckingDetailsService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class CheckingReportRestController {

	@Autowired private IDriverService					driverService;
	@Autowired private IVehicleCheckingDetailsService	vehicleCheckingDetailsService;

	@RequestMapping("/CR")
	public ModelAndView FuelReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("CR", model);
	}
	
	@RequestMapping("/checkingReport")
	public ModelAndView checkingReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("checkingReport", model);
	}
	
	@RequestMapping("/conductorHistoryReport")
	public ModelAndView PartWiseConsumptionReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("conductorHistoryReport", model);
	}
	
	@RequestMapping(value = "/getConductorHistoryReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getConductorHistoryReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		ValueObject		valueOutObject 	= null;
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = vehicleCheckingDetailsService.getConductorHistoryReport(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
}	