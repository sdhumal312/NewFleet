package org.fleetopgroup.rest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripSheetConfigurationConstant;
import org.fleetopgroup.constant.VehicleConfigurationContant;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleEmiDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class VehicleEMIController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IVehicleService vehicleService;
	
	@Autowired private IVehicleEmiDetailsService 		vehicleEmiDetailsService;
	@Autowired private ICompanyConfigurationService 	companyConfigurationService;
	
	VehicleBL VBL = new VehicleBL();

	@RequestMapping("/VehicleEmiDetails")
	public ModelAndView VehicleTyreDetails(@RequestParam("Id") final Integer Vehicle_ID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 		configuration	          		= null;
		CustomUserDetails 				userDetails 	         	 	= null;
		boolean					 		vehicleMonthlyEMIPayment	  	= false;
		try {
			userDetails 				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			vehicleMonthlyEMIPayment 	= (boolean) configuration.getOrDefault(VehicleConfigurationContant.VEHICLE_MONTHLY_EMI_PAYMENT, false);
			
			if (Vehicle_ID != null) {
				model.put("vehicleMonthlyEMIPayment", vehicleMonthlyEMIPayment);
				model.put("vid", Vehicle_ID);
				model.put("vehicle",
						VBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(Vehicle_ID)));

			} else {
				return new ModelAndView("redirect:/NotFound.in");
			}

		} catch (Exception e) {
			LOGGER.error("Add Vehicle Status Page:", e);
		}
		return new ModelAndView("Show_EMI_Details", model);
	}
	
	@RequestMapping(value = "/getVehicleEmiDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleEmiDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject	=	vehicleEmiDetailsService.getVehicleEmiDetails(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value = "/saveVehicleEmiDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveVehicleEmiDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject	=	vehicleEmiDetailsService.saveVehicleEmiDetails(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value = "/getVehicleEmiDetailsById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleEmiDetailsById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;
		
		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject	=	vehicleEmiDetailsService.getVehicleEmiDetailsById(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value = "/updateVehicleEmiDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateVehicleEmiDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject	=	vehicleEmiDetailsService.updateVehicleEmiDetails(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value = "/deleteVehicleEmiDetailsById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteVehicleEmiDetailsById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleEmiDetailsService.deleteVehicleEmiDetailsById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/emiPaymentDetails", method = RequestMethod.GET)
	public ModelAndView emiPaymentDetails(@RequestParam("vid")  final String data) throws Exception {
		Map<String, Object> model 					= new HashMap<String, Object>();
		String[] 			paymentData				= null;
		int 				vid   					= 0;
		long				vehicleEmiDetailsId		= 0;
		try {
			
			paymentData  			= data.split("_");
			vid  		 			= Integer.parseInt(paymentData[0]);
			vehicleEmiDetailsId		= Long.parseLong(paymentData[1]);
			
			model.put("vid", vid);
			model.put("vehicleEmiDetailsId", vehicleEmiDetailsId);
			model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(vid)));
			
		}  catch (Exception e) {
			throw e;
		}
		return new ModelAndView("EmiPaymentDetails", model);
	}
	
	@RequestMapping(value = "/getVehicleWiseEmiPaymentPendingList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleWiseEmiPaymentPendingList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleEmiDetailsService.getVehicleWiseEmiPaymentPendingList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveVehicleEmiPaymentDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveVehicleEmiPaymentDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			object.put("emiPaymentDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("emiPaymentDetails")));
			
			return vehicleEmiDetailsService.saveVehicleEmiPaymentDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getVehicleWiseEmiPaidList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleWiseEmiPaidList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleEmiDetailsService.getVehicleWiseEmiPaidList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getPreEmiSettlementDetail", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPreEmiSettlementDetail(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleEmiDetailsService.getPreEmiSettlementDetail(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/savePreEmiSettlementDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  savePreEmiSettlementDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleEmiDetailsService.savePreEmiSettlementDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
}
