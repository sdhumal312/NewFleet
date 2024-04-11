package org.fleetopgroup.web.controller.report;

import java.util.HashMap;
import java.util.Map;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.IDriverBasicDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IDriverJobTypeService;
import org.fleetopgroup.persistence.serviceImpl.IDriverLedgerService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleCheckingDetailsService;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DriverReportRestController {

	@Autowired private IDriverService					driverService;
	@Autowired private IVehicleCheckingDetailsService	vehicleCheckingDetailsService;
	@Autowired private  IDriverJobTypeService			driverJobTypeService;
	@Autowired private IDriverBasicDetailsService		driverBasicDetailsService;
	@Autowired private IDriverLedgerService driverLedgerService;
	
	@RequestMapping("/DriverCommentReport")
	public ModelAndView DepotWiseAttReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Driver_Comment_Report", model);
	}
	
	@RequestMapping(value = "/getDriverCommentReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleCommentReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject	=	driverService.getDriverCommentDetails(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	

	@RequestMapping(value = "/getCheckingEntryReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getCheckingEntryReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject	=	vehicleCheckingDetailsService.getCheckingEntryReport(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping("/DriverTripsheetAdvanceReport")
	public ModelAndView DriverTripsheetAdvanceReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("DriverTripsheetAdvanceReport", model);
	}
	
	@RequestMapping("/driverESIPFTripWiseReport")
	public ModelAndView driverESIPFTripWiseReport() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.put("driverJobType", driverJobTypeService.listDriverJobTypeByCompanyId(userDetails.getCompany_id()));
		return new ModelAndView("driverESIPFTripWiseReport", model);
	}
	
	@RequestMapping(value = "/getDriverTripsheetAdvanceReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDriverTripsheetAdvanceReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return driverService.getDriverTripsheetAdvanceReport(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getDriverBasicDetailsReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDriverBasicDetailsReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject 		= driverBasicDetailsService.getAllDriverTypeBasicDetails(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@GetMapping(path="/driverDetailsReport")
	public ModelAndView driverDetailsReport (@RequestParam HashMap<Object,Object> allReqParam){
		ModelAndView view = new ModelAndView("driverDetailsReport");
		
		return view;
	}
	
	@PostMapping(path="/getDriverDetailsReport" ,produces=MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object,Object> getDriverDetailsreport(@RequestParam HashMap<Object,Object> allReqParam){
		return driverService.getDriverDetailsReportList(new ValueObject(allReqParam)).getHtData();
	}
	
	@GetMapping(path="/driverRenewalReminderReport")
	public ModelAndView driverRenewalReminderReport (@RequestParam HashMap<Object,Object> allReqParam){
		ModelAndView model = new ModelAndView("driverRenewalReminderReport");
		CustomUserDetails userDetails =Utility.getObject(null);
		model.addObject("companyId", userDetails.getCompany_id());
		return model;
	}
	
	@PostMapping(path="/getDriverRenewalReminderReport" ,produces=MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object,Object> getDriverRenewalReminderReport(@RequestParam HashMap<Object,Object> allReqParam){
		return driverService.getDriverRenewalReminderReport(new ValueObject(allReqParam)).getHtData();
	}
	
	@GetMapping(path="/driverIncidentReport")
	public ModelAndView getdriverIncidentReport(){
		CustomUserDetails userDetails = Utility.getObject(null);
		ModelAndView model = new ModelAndView("driverIncidentReport");
		model.addObject("companyId",userDetails.getCompany_id());
		return model;
	}
	
	@PostMapping(path="/getDriverIncidentReport",produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object,Object> getVehicleIncidentDetails(@RequestParam HashMap<Object,Object> allReqParam){
			return driverService.getDriverIncidentReport(new ValueObject(allReqParam)).getHtData();
	}
	
	@PostMapping(path="/getDriverWiseIncidentDetails")
	public HashMap<Object,Object> getDriverWiseIncidentDetails (@RequestParam HashMap<Object, Object> allReqParam){
		return driverService.getDriverWiseIncidentDetails(new ValueObject(allReqParam)).getHtData() ;	
	}
	
	@RequestMapping(value = "/DriverLedgerReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPartPurchaseInvoiceReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {   
		ValueObject 				object 				= null;
		ValueObject					valueOutObject 		= null;
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = driverLedgerService.getDriverLedgertReportList(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	

	@RequestMapping(value = "/DriverSalaryReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object> DriverSalaryReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {   
		ValueObject 				object 				= null;
		ValueObject					valueOutObject 		= null;
		
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = driverService.getAllDriverAndTripsheetDetails(object);
				return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
}
