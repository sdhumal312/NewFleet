package org.fleetopgroup.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleBatteryLayoutDto;
import org.fleetopgroup.persistence.model.VehicleBatteryLayout;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleBatteryLayoutService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
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
public class VehicleBatteryLayoutController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Autowired private ICompanyConfigurationService 							companyConfigurationService;
	@Autowired private IVehicleService 					vehicleService;
	@Autowired private IVehicleBatteryLayoutService		vehicleBatteryLayoutService;

	VehicleBL VBL = new VehicleBL();

	@RequestMapping("/VehicleBatteryDetails")
	public ModelAndView VehicleBatteryDetails(@RequestParam("Id") final Integer Vehicle_ID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails 				userDetails 	          = null;
		HashMap<String, Object> 		configuration	          = null;
		try {
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.BATTERY_CONFIGURATION_CONFIG);

			model.put("configuration", configuration);
			model.put("vid", Vehicle_ID);
		} catch (Exception e) {
			LOGGER.error("Add Vehicle Status Page:", e);
		}
		return new ModelAndView("Show_Vehicle_Battery_Details", model);
	}

	@RequestMapping("/VehicleBatteryLayout")
	public ModelAndView VehicleBatteryLayoutCreation(@RequestParam("Id") final Integer Vehicle_ID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (Vehicle_ID != null) {
				/** Show Only Select Column Value in Vehicle Tables **/
				model.put("vehicle",
						VBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(Vehicle_ID)));

			} else {
				return new ModelAndView("redirect:/NotFound.in");
			}

		} catch (Exception e) {
			LOGGER.error("Add Vehicle Status Page:", e);
		}
		return new ModelAndView("Show_Vehicle_Battery", model);
	}
	
	@RequestMapping(value = "/saveBatteryLayout", method = RequestMethod.POST)
	public ModelAndView saveTyreLayout(@RequestParam("vid") final Integer vid,
			@RequestParam("noOfBattery") final Integer noOfBattery,@RequestParam("batteryCapacityId") final Long batteryCapacityId,
			VehicleBatteryLayout layout,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			/**
			 * who Create this Issues get email id to user profile
			 * details
			 */
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (vid != null) {
				for (int i = 1; i <= noOfBattery; i++) {
					VehicleBatteryLayout batteryLayout = new VehicleBatteryLayout();

					batteryLayout.setVid(vid);
					batteryLayout.setNoOfBattery(noOfBattery);
					batteryLayout.setLayoutPosition(Short.parseShort(i+""));
					batteryLayout.setCompanyId(userDetails.getCompany_id());
					batteryLayout.setCreatedById(userDetails.getId());
					batteryLayout.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
					batteryLayout.setBatteryCapacityId(batteryCapacityId);
					
					vehicleBatteryLayoutService.createBatteryLayout(batteryLayout);
				}

			} else {

				return new ModelAndView("redirect:/showVehicle?vid=" + vid + "&danger=true", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("save Vehicle Status Page:");
			return new ModelAndView("redirect:/showVehicle?vid=" + vid + "&danger=true");
		}
		return new ModelAndView("redirect:/VehicleBatteryDetails?Id=" + vid + "&Success=true");

	}
	
	@RequestMapping(value = "/getBatteryLayoutDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getBatteryLayoutDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object 				= new ValueObject(allRequestParams);
			List<VehicleBatteryLayoutDto> Layout = vehicleBatteryLayoutService.getVehicleBatteryLayout(object.getInt("vid",0), userDetails.getCompany_id());
			object.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(object.getInt("vid",0))));
			object.put("vid", object.getInt("vid",0));
			object.put("batteryLayout", Layout);
			
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/mountBatteryDetailstoVehicle", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  mountBatteryDetailstoVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleBatteryLayoutService.mountVehicleBatteryDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/disMountBatteryDetailstoVehicle", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  disMountBatteryDetailstoVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleBatteryLayoutService.disMountVehicleBatteryDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getBatteryAsignmentDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getBatteryAsignmentDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleBatteryLayoutService.getBatteryAsignmentDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}

	@RequestMapping(value = "/deleteVehicleBatteryLayout", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteVehicleBatteryLayout(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleBatteryLayoutService.deleteVehicleBatteryLayout(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
}
