package org.fleetopgroup.web.controller;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VehicleOdometerHistoryController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehicleOdometerHistoryService vehicleOdometerHistoryService;

	@Autowired
	private IServiceReminderService ServiceReminderService;
	
	@Autowired IVehicleDocumentService vehicleDocumentService;

	VehicleBL VBL = new VehicleBL();

	VehicleOdometerHistoryBL VOHBL = new VehicleOdometerHistoryBL();

	@Autowired
	private IVehicleService vehicleService;

	@RequestMapping(value = "/VehicleOdometerDetails", method = RequestMethod.GET)
	public ModelAndView VehicleOdometerDetails(@ModelAttribute("command") Vehicle vehicle_id,
			ServiceReminderDto serviceReminderDto, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		VehicleDto				vehicle			= null;
		CustomUserDetails	userDetails		= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 vehicle = vehicleService.Get_Vehicle_Header_Details(vehicle_id.getVid());
			 
			 if(vehicle != null) {
				 
				 vehicle = VBL.prepare_Vehicle_Header_Show(vehicle);
				 model.put("vehicle", vehicle);
				 // show the driver list in all
				 
				 model.put("VehicleOdmeter",
						 VOHBL.vehicleToHistoryOdometerDetails(
								 vehicleOdometerHistoryService.listVehicleOdometerHistory(vehicle_id.getVid(), userDetails.getCompany_id()),
								 vehicle.getVehicle_Odometer()));
				 
				 Object[] count = vehicleService
						 .countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(vehicle_id.getVid());
				 	model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(vehicle_id.getVid(), userDetails.getCompany_id()));
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
			 
		} catch (Exception e) {

			LOGGER.error("Add Vehicle Odometer Page:", e);
			e.printStackTrace();

		}

		return new ModelAndView("Show_Vehicle_Odometer", model);
	}

	@RequestMapping(value = "/updateVehicleodometer", method = RequestMethod.POST)
	public ModelAndView updateVehicleodometer(@ModelAttribute("command") Vehicle vehicle_id,
			ServiceReminderDto serviceReminderDto, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<ServiceReminderDto>		serviceList		= null;
		CustomUserDetails				userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// update the Current Odometer vehicle Databases tables

			Integer currentODDMETER = vehicleService
					.updateCurrentOdoMeterGETVehicleToCurrentOdometer(vehicle_id.getVid());
			if(currentODDMETER != null && vehicle_id.getVehicle_Odometer() != null) {
				if (currentODDMETER < vehicle_id.getVehicle_Odometer()) {
					
					vehicleService.updateCurrentOdoMeter(vehicle_id.getVid(), vehicle_id.getVehicle_Odometer(), userDetails.getCompany_id());
					// update current Odometer update Service Reminder
					ServiceReminderService.updateCurrentOdometerToServiceReminder(vehicle_id.getVid(),
							vehicle_id.getVehicle_Odometer(), userDetails.getCompany_id());
					
					serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(vehicle_id.getVid(), userDetails.getCompany_id(), userDetails.getId());
					if(serviceList != null) {
						for(ServiceReminderDto list : serviceList) {
							
							if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
								
								ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
								//emailAlertQueueService.sendEmailServiceOdometer(list);
								//smsAlertQueueService.sendSmsServiceOdometer(list);
							}
						}
					}

					VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToVehicle(vehicle_id);
					vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
					vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
				}
				// check frist after update the odometer
				vehicleService.updateCurrentOdoMeter(vehicle_id.getVid(), vehicle_id.getVehicle_Odometer(), userDetails.getCompany_id());
			}
			

		} catch (Exception e) {
			LOGGER.error("Add Vehicle Odometer Page:", e);
			e.printStackTrace();

		}

		return new ModelAndView("redirect:/VehicleOdometerDetails.in?vid=" + vehicle_id.getVid() + "", model);
	}

}