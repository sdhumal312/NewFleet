/**
 * 
 */
package org.fleetopgroup.web.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.VehicleConfigurationContant;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.InventoryTyreHistoryDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.VehicleTyreLayoutDto;
import org.fleetopgroup.persistence.model.InventoryTyre;
import org.fleetopgroup.persistence.model.InventoryTyreHistory;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.VehicleTyreLayout;
import org.fleetopgroup.persistence.model.VehicleTyreLayoutPosition;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreLayoutService;
import org.fleetopgroup.web.util.DateTimeUtility;
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
public class VehicleTyreLayoutController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehicleTyreLayoutService VehicleTyreLayoutService;

	@Autowired
	private IVehicleService vehicleService;
	VehicleBL VBL = new VehicleBL();

	@Autowired
	private IInventoryTyreService iventoryTyreService;

	@Autowired
	private IServiceReminderService ServiceReminderService;

	@Autowired
	private IVehicleOdometerHistoryService vehicleOdometerHistoryService;
	
	@Autowired	ICompanyConfigurationService	companyConfigurationService;
	
	VehicleOdometerHistoryBL VOHBL = new VehicleOdometerHistoryBL();

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	/** This controller show Vehicle Tyre details */
	@RequestMapping("/VehicleTyreLayout")
	public ModelAndView VehicleTyreLayout(@RequestParam("Id") final Integer Vehicle_ID,
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
		return new ModelAndView("Show_Vehicle_Tyre", model);
	}
	
	

	/** This controller show Vehicle Tyre details */
	@RequestMapping("/VehicleTyreDetails")
	public ModelAndView VehicleTyreDetails(@RequestParam("Id") final Integer Vehicle_ID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			if (Vehicle_ID != null) {
				/** Show Only Select Column Value in Vehicle Tables **/
				List<VehicleTyreLayoutDto> Layout = VehicleTyreLayoutService.Get_Vehicle_Type_Details(Vehicle_ID, userDetails.getCompany_id());
				
				if (Layout != null && !Layout.isEmpty()) {

					model.put("vehicle",
							VBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(Vehicle_ID)));

					model.put("tyrelayout", Layout);
					model.put("configuration", configuration);
					model.put("tyrelayoutPosition",
							VehicleTyreLayoutService.Get_Vehicle_TyreLayout_Position_Details(Vehicle_ID, userDetails.getCompany_id()));
				} else {
					if((boolean) configuration.get("vehicleModelTyreLayout")) {
						
						return new ModelAndView("redirect:/showVehicleTyreAssignedDetails?id=" + Vehicle_ID + "", model);
					}else {
						return new ModelAndView("redirect:/VehicleTyreLayout?Id=" + Vehicle_ID + "", model);
						
					}
				}
			} else {
				return new ModelAndView("redirect:/NotFound.in");
			}
			model.put("vehicleTyreAssignment", true);
		} catch (Exception e) {
			LOGGER.error("Add Vehicle Status Page:", e);
		}
		return new ModelAndView("Show_Vehicle_Tyre_Details", model);
	}
	
	
	// Save Vehicle Details to Database
	@RequestMapping(value = "/saveTyreLayout", method = RequestMethod.POST)
	public ModelAndView saveTyreLayout(@RequestParam("VID") final Integer VEHICLE_ID,
			@RequestParam("VEHICLE_REGIS") final String VEHICLE_REGIS, @RequestParam("AXLE") final Integer AXLE,
			@RequestParam("POSITION") final String POSITION, VehicleTyreLayout layout,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			/**
			 * who Create this Issues get email id to user profile
			 * details
			 */
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			/*
			 * System.out.println(VEHICLE_ID);
			 * System.out.println(VEHICLE_REGIS); System.out.println(POSITION);
			 */
			if (VEHICLE_ID != null) {
				/** Below save Tyre layout Axle number to details */
				for (int i = 1; i <= AXLE; i++) {
					VehicleTyreLayout Tyre = new VehicleTyreLayout();

					Tyre.setVEHICLE_ID(VEHICLE_ID);
					//Tyre.setVEHICLE_REGISTRATION(VEHICLE_REGIS);
					Tyre.setAXLE(i);

					Tyre.setTYRE_FRONT_SIZE_ID(layout.getTYRE_FRONT_SIZE_ID());
					Tyre.setTYRE_FRONT_PRESSURE(layout.getTYRE_FRONT_PRESSURE());
					Tyre.setTYRE_REAR_SIZE_ID(layout.getTYRE_REAR_SIZE_ID());
					Tyre.setTYRE_REAR_PRESSURE(layout.getTYRE_REAR_PRESSURE());
					
					Tyre.setCREATEDBYID(userDetails.getId());
					Tyre.setCOMPANY_ID(userDetails.getCompany_id());

					java.util.Date currentDateUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
					Tyre.setCREATED_DATE(toDate);
					VehicleTyreLayoutService.registerNewTyreLayout(Tyre);
				}

				/**
				 * Below save For loop Tyre layout Axle Position number to
				 * details
				 */
				String[] posi = POSITION.split(",");
				for (String SavePosition : posi) {
					VehicleTyreLayoutPosition TyrePosition = new VehicleTyreLayoutPosition();

					TyrePosition.setVEHICLE_ID(VEHICLE_ID);
					TyrePosition.setCOMPANY_ID(userDetails.getCompany_id());

					String[] Tyreaxle = SavePosition.split("-");

					TyrePosition.setPOSITION(SavePosition);
					TyrePosition.setAXLE(Integer.parseInt(Tyreaxle[1]));

					switch (SavePosition) {
					case "LO-1":

						TyrePosition.setTYRE_SIZE_ID(layout.getTYRE_FRONT_SIZE_ID());
						TyrePosition.setTYRE_PRESSURE(layout.getTYRE_FRONT_PRESSURE());
						break;
					case "LI-1":
						TyrePosition.setTYRE_SIZE_ID(layout.getTYRE_FRONT_SIZE_ID());
						TyrePosition.setTYRE_PRESSURE(layout.getTYRE_FRONT_PRESSURE());
						break;
					case "RI-1":
						TyrePosition.setTYRE_SIZE_ID(layout.getTYRE_FRONT_SIZE_ID());
						TyrePosition.setTYRE_PRESSURE(layout.getTYRE_FRONT_PRESSURE());
						break;
					case "RO-1":
						TyrePosition.setTYRE_SIZE_ID(layout.getTYRE_FRONT_SIZE_ID());
						TyrePosition.setTYRE_PRESSURE(layout.getTYRE_FRONT_PRESSURE());
						break;
					default:
						TyrePosition.setTYRE_SIZE_ID(layout.getTYRE_REAR_SIZE_ID());
						TyrePosition.setTYRE_PRESSURE(layout.getTYRE_REAR_PRESSURE());
						break;
					}

					VehicleTyreLayoutService.registerNewTyreLayoutPosition(TyrePosition);

				}
			} else {

				return new ModelAndView("redirect:/showVehicle?vid=" + VEHICLE_ID + "&danger=true", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("save Vehicle Status Page:");
			return new ModelAndView("redirect:/showVehicle?vid=" + VEHICLE_ID + "&danger=true");
		}
		return new ModelAndView("redirect:/VehicleTyreDetails?Id=" + VEHICLE_ID + "&Success=true");

	}

	// Save Vehicle Details to Database
	@RequestMapping(value = "/Tyremount", method = RequestMethod.POST)
	public ModelAndView saveTyremount(@RequestParam("VID") final Integer VEHICLE_ID,
			@RequestParam("VEHICLE_REGIS") final String VEHICLE_REGIS,
			@RequestParam("POSITION_ID") final Long POSITION_ID,
			@RequestParam("POSITION_AXLE") final String POSITION_AXLE, @RequestParam("PRESSURE") final String PRESSURE,
			@RequestParam("TYRE_ID") final Long TYRE_ID, @RequestParam("TYRE_ODOMETER") final Integer TYRE_ODOMETER,
			@RequestParam("MOUNTED_DATE") final String MOUNTED_DATE, @RequestParam("COMMENT") final String COMMENT,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails				userDetails		= null;
		List<ServiceReminderDto>		serviceList		= null;
		InventoryTyreHistoryDto			inventoryTyreHistoryDto	= null;
		String							remark			= "";
		
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			java.util.Date date = dateFormat.parse(MOUNTED_DATE);
			java.sql.Date MoundDate = new Date(date.getTime());
			if (VEHICLE_ID != null && POSITION_ID != null && TYRE_ID != null) {

				/** Below Search Tyre Inventory Id To Details */
				InventoryTyre Tyre = iventoryTyreService.Get_TYRE_ID_InventoryTyre(TYRE_ID);

				/**  Below save For Tyre History Axle Position number to Vehicle Details  */
				InventoryTyreHistory TyreHistory = new InventoryTyreHistory();
				
				TyreHistory.setTYRE_ID(Tyre.getTYRE_ID());
				TyreHistory.setTYRE_NUMBER(Tyre.getTYRE_NUMBER());
				TyreHistory.setVEHICLE_ID(VEHICLE_ID);

				if (POSITION_AXLE != null) {
					String[] Tyreaxle = POSITION_AXLE.split("-");
					TyreHistory.setPOSITION(Tyreaxle[0]);
					TyreHistory.setAXLE(Tyreaxle[1]);
				}
				TyreHistory.setTYRE_STATUS_ID(InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_MOUNT);
				TyreHistory.setOPEN_ODOMETER(TYRE_ODOMETER);
				TyreHistory.setTYRE_USEAGE(0);
				TyreHistory.setTYRE_ASSIGN_DATE(MoundDate);
				TyreHistory.setTYRE_COMMENT(COMMENT);
				TyreHistory.setCOMPANY_ID(userDetails.getCompany_id());
				TyreHistory.setCreatedById(userDetails.getId());
				TyreHistory.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
				
				inventoryTyreHistoryDto = iventoryTyreService.getPreTyreMountDismountHistory(Tyre.getTYRE_ID(),TyreHistory,InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_DISMOUNT);// used to get pre record of this tyre
				
				if(inventoryTyreHistoryDto != null) {
					TyreHistory.setPRE_OPEN_ODOMETER(inventoryTyreHistoryDto.getOPEN_ODOMETER());
					TyreHistory.setPRE_TYRE_ASSIGN_DATE(inventoryTyreHistoryDto.getTYRE_ASSIGN_DATE_TIMESTAMP());
					TyreHistory.setPRE_TYRE_NUMBER(inventoryTyreHistoryDto.getTYRE_NUMBER());
				}
				
				iventoryTyreService.add_Inventory_TYRE_History(TyreHistory);

				iventoryTyreService.update_Assign_TYRE_To_Vehicle_Mount(VEHICLE_ID, TYRE_ODOMETER,
						InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_SERVICE, MoundDate, TYRE_ID, userDetails.getCompany_id(),(short)0);
				remark = COMMENT;
				VehicleTyreLayoutService.update_Position_Assign_TYRE_To_Vehicle(TYRE_ID, Tyre.getTYRE_NUMBER(), true,
						POSITION_ID,remark);

				// update the Current Odometer vehicle Databases tables
				try {
					Integer currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(VEHICLE_ID);
					/*Integer	Useage = Tyre.getTYRE_USEAGE();
					Integer OpenOdometer = Tyre.getOPEN_ODOMETER();*/
					if (currentODDMETER < TYRE_ODOMETER) {
						
						vehicleService.updateCurrentOdoMeter(VEHICLE_ID, TYRE_ODOMETER, userDetails.getCompany_id());
						/*Useage=OpenOdometer;*/
						// update current Odometer update Service Reminder
						ServiceReminderService.updateCurrentOdometerToServiceReminder(VEHICLE_ID, TYRE_ODOMETER, userDetails.getCompany_id());
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(VEHICLE_ID, userDetails.getCompany_id(), userDetails.getId());
						if(serviceList != null) {
							for(ServiceReminderDto list : serviceList) {
								if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
									ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
								}
							}
						}

						VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToTyreAssign(
								VEHICLE_ID, VEHICLE_REGIS, TYRE_ODOMETER, TYRE_ID);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
					}else if(currentODDMETER == TYRE_ODOMETER) {
						VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToTyreAssign(VEHICLE_ID, VEHICLE_REGIS, TYRE_ODOMETER, TYRE_ID);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
					}
				} catch (Exception e) {
					LOGGER.error("Work Order:", e);
					e.printStackTrace();

				}

			} else {

				return new ModelAndView("redirect:/showVehicle?vid=" + VEHICLE_ID + "&danger=true", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("save Vehicle Status Page:");
			return new ModelAndView("redirect:/showVehicle?vid=" + VEHICLE_ID + "&danger=true");
		}
		return new ModelAndView("redirect:/VehicleTyreDetails?Id=" + VEHICLE_ID + "&MountSuccess=true");

	}

	// Save Vehicle Details to Database
	@RequestMapping(value = "/TyreDismount", method = RequestMethod.POST)
	public ModelAndView saveTyreDismount(@RequestParam("VID") final Integer VEHICLE_ID,
			@RequestParam("VEHICLE_REGIS") final String VEHICLE_REGIS,
			@RequestParam("POSITION_ID") final Long POSITION_ID,
			@RequestParam("POSITION_AXLE") final String POSITION_AXLE, @RequestParam("PRESSURE") final String PRESSURE,
			@RequestParam("TYRE_ID") final Long TYRE_ID, @RequestParam("TYRE_ODOMETER") final Integer DISMOUNT_ODOMETER,
			@RequestParam("MOUNTED_DATE") final String MOUNTED_DATE, @RequestParam("COMMENT") final String COMMENT,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails				userDetails		= null;
		List<ServiceReminderDto>		serviceList		= null;
		InventoryTyreHistoryDto			inventoryTyreHistoryDto = null;
		String 							remark			= "";
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (VEHICLE_ID != null && POSITION_ID != null && TYRE_ID != null) {

				/** Below Search Tyre Inventory Id To Details */
				InventoryTyre Tyre = iventoryTyreService.Get_TYRE_ID_InventoryTyre(TYRE_ID);

				/** Below save Dismount For Tyre History Axle Position number to Vehicle details  */
				InventoryTyreHistory TyreHistory = new InventoryTyreHistory();

				TyreHistory.setTYRE_ID(Tyre.getTYRE_ID());
				TyreHistory.setTYRE_NUMBER(Tyre.getTYRE_NUMBER());
				TyreHistory.setVEHICLE_ID(VEHICLE_ID);

				if (POSITION_AXLE != null) {
					String[] Tyreaxle = POSITION_AXLE.split("-");
					TyreHistory.setPOSITION(Tyreaxle[0]);
					TyreHistory.setAXLE(Tyreaxle[1]);
				}
				TyreHistory.setTYRE_STATUS_ID(InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_DISMOUNT);
				TyreHistory.setOPEN_ODOMETER(DISMOUNT_ODOMETER);

				/** this story one dis mount Tyre Useage */
				Integer TyreHistoryUseage = 0;

				switch (POSITION_AXLE) {
				case "ST-1":
					TyreHistory.setTYRE_USEAGE(TyreHistoryUseage);
					break;

				default:

					if (Tyre.getOPEN_ODOMETER() != null) {
						Integer OpenOdometer = Tyre.getOPEN_ODOMETER();

						if (DISMOUNT_ODOMETER > OpenOdometer) {
							TyreHistoryUseage = DISMOUNT_ODOMETER - OpenOdometer;
						}
					}
					TyreHistory.setTYRE_USEAGE(TyreHistoryUseage);

					break;
				}
				
				java.util.Date date = dateFormat.parse(MOUNTED_DATE);
				java.sql.Date MoundDate = new Date(date.getTime());

				TyreHistory.setTYRE_ASSIGN_DATE(MoundDate);

				TyreHistory.setTYRE_COMMENT(COMMENT);
				TyreHistory.setCOMPANY_ID(userDetails.getCompany_id());
				
				inventoryTyreHistoryDto = iventoryTyreService.getPreTyreMountDismountHistory(Tyre.getTYRE_ID(),TyreHistory,InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_MOUNT);
				
				if(inventoryTyreHistoryDto != null) {
					
					TyreHistory.setPRE_OPEN_ODOMETER(inventoryTyreHistoryDto.getOPEN_ODOMETER());
					TyreHistory.setPRE_TYRE_ASSIGN_DATE(inventoryTyreHistoryDto.getTYRE_ASSIGN_DATE_TIMESTAMP());
					TyreHistory.setPRE_TYRE_NUMBER(inventoryTyreHistoryDto.getTYRE_NUMBER());
				}
				
				iventoryTyreService.add_Inventory_TYRE_History(TyreHistory);

				Integer Useage = 0;
				//Integer OpenOdometer = Tyre.getOPEN_ODOMETER();
				switch (POSITION_AXLE) {
				// this update Stepney not calculation use kilometer
				case "ST-1":
					if (Tyre.getTYRE_USEAGE() != null) {
						Useage = Tyre.getTYRE_USEAGE();
					}
					break;
				// this update calculation usage kilometer
				default:
					
					if (Tyre.getTYRE_USEAGE() != null) {
						Useage = Tyre.getTYRE_USEAGE();
					}
					if (Tyre.getOPEN_ODOMETER() != null) {
						Integer OpenOdometer = Tyre.getOPEN_ODOMETER();
						

						if (DISMOUNT_ODOMETER > OpenOdometer) {
							Useage += (DISMOUNT_ODOMETER - OpenOdometer);
						}
						/*if(DISMOUNT_ODOMETER <= OpenOdometer) {
							Useage += OpenOdometer;
						}*/
						
					}
					//Useage = OpenOdometer;
					break;
					
				}
				

				/* UPDate DisMount Value and Total Username */
				iventoryTyreService.update_Assign_TYRE_To_Vehicle_DisMount(VEHICLE_ID, DISMOUNT_ODOMETER,
						Useage, InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE, TYRE_ID);

				VehicleTyreLayoutService.update_Position_Assign_TYRE_To_Vehicle(null, null, false, POSITION_ID,remark);

				// update the Current Odometer vehicle Databases tables
				try {
					Integer currentODDMETER = vehicleService
							.updateCurrentOdoMeterGETVehicleToCurrentOdometer(VEHICLE_ID);

					if (currentODDMETER < DISMOUNT_ODOMETER) {
						
						vehicleService.updateCurrentOdoMeter(VEHICLE_ID, DISMOUNT_ODOMETER, userDetails.getCompany_id());
						// update current Odometer update Service Reminder
						ServiceReminderService.updateCurrentOdometerToServiceReminder(VEHICLE_ID, DISMOUNT_ODOMETER, userDetails.getCompany_id());
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(VEHICLE_ID, userDetails.getCompany_id(), userDetails.getId());
						if(serviceList != null) {
							for(ServiceReminderDto list : serviceList) {
								
								if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
									
									ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
									//emailAlertQueueService.sendEmailServiceOdometer(list);
									//smsAlertQueueService.sendSmsServiceOdometer(list);
								}
							}
						}

						VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToTyreAssign(
								VEHICLE_ID, VEHICLE_REGIS, DISMOUNT_ODOMETER, TYRE_ID);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
						
					}else if(currentODDMETER == DISMOUNT_ODOMETER) {
						VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToTyreAssign(
								VEHICLE_ID, VEHICLE_REGIS, DISMOUNT_ODOMETER, TYRE_ID);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
					}
				} catch (Exception e) {
					LOGGER.error("Work Order:", e);
					e.printStackTrace();

				}

			} else {

				return new ModelAndView("redirect:/showVehicle?vid=" + VEHICLE_ID + "&danger=true", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("save Vehicle Status Page:");
			return new ModelAndView("redirect:/showVehicle?vid=" + VEHICLE_ID + "&danger=true");
		}
		return new ModelAndView("redirect:/VehicleTyreDetails?Id=" + VEHICLE_ID + "&DismountSuccess=true");

	}

	// Save Vehicle Details to Database
	@RequestMapping(value = "/TyreRotation", method = RequestMethod.POST)
	public ModelAndView saveTyreRotation(@RequestParam("VID") final Integer VEHICLE_ID,
		@RequestParam("VEHICLE_REGIS") final String VEHICLE_REGIS,
		@RequestParam("POSITION_ID") final Long POSITION_ID, @RequestParam("TYRE_SIZE_ID") final Integer TYRE_SIZE,
		@RequestParam("POSITION_AXLE_FROM") final String POSITION_AXLE,
		@RequestParam("ROTATION_TO") final String ROTATION_TO, @RequestParam("TYRE_ID") final Long TYRE_ID,
		@RequestParam("TYRE_ODOMETER") final Integer DISMOUNT_ODOMETER,
		@RequestParam("MOUNTED_DATE") final String MOUNTED_DATE, @RequestParam("COMMENT") final String COMMENT,final HttpServletRequest request) {
		
		Map<String, Object> 			model 					= new HashMap<String, Object>();
		CustomUserDetails				userDetails				= null;
		List<ServiceReminderDto>		serviceList				= null;
		HashMap<String, Object> 		configuration			= null;
		VehicleTyreLayoutPosition 		RotationPosition_To		= null;
		VehicleTyreLayoutPosition 		RotationPosition_From	= null;
		InventoryTyreHistoryDto			inventoryTyreHistoryDto	= null;
		String 							remark			= "";
		
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			
			/*
			 * System.out.println(VEHICLE_ID);
			 * System.out.println(VEHICLE_REGIS); System.out.println(POSITION);
			 */
			
			if (VEHICLE_ID != null && POSITION_ID != null && ROTATION_TO != null) {
				
				if (!POSITION_AXLE.equalsIgnoreCase(ROTATION_TO)) {
					/**
					 * Below Search Tyre Rotation Position To get position Id To
					 * Details
					 */
					RotationPosition_To = VehicleTyreLayoutService.Get_Position_name_to_GetDetails(VEHICLE_ID, ROTATION_TO, userDetails.getCompany_id());
					
					if (RotationPosition_To != null) {
						// check two tyre same tyre or checking if true
						if (TYRE_SIZE == RotationPosition_To.getTYRE_SIZE_ID() || (boolean)configuration.get(VehicleConfigurationContant.ASIGN_ANY_SIZE_TYRE)) {
							// true go to return to values
							// false to get the values rotation

							Long 		RO_TO_PositionID 		= RotationPosition_To.getLP_ID();
							Long 		RO_TO_TYRE_ID 			= RotationPosition_To.getTYRE_ID();
							String 		RO_TO_TYRE_SERIAL_NO 	= RotationPosition_To.getTYRE_SERIAL_NO();
							Boolean 	RO_TO_TYRE_ASSIGNED 	= RotationPosition_To.isTYRE_ASSIGNED();
							
							/** Check Rotation TO Tyre Id Assign or not*/
							if (RO_TO_TYRE_ID != null) {
								/**
								 * Below Get Rototion Postion to Id to Get Tyre
								 * ID Get Update Tyre History Details
								 */
								
								InventoryTyre 			TyreTo 		= iventoryTyreService.Get_TYRE_ID_InventoryTyre(RO_TO_TYRE_ID);
								/** Position Only in Tyre History Only */
								InventoryTyreHistory 	TyreHistory = Rotation_TyreHistoryDetails(TyreTo, POSITION_AXLE, VEHICLE_ID, VEHICLE_REGIS, DISMOUNT_ODOMETER, MOUNTED_DATE, COMMENT);
								
								TyreHistory.setCOMPANY_ID(userDetails.getCompany_id());
								
								
								iventoryTyreService.add_Inventory_TYRE_History(TyreHistory);

								/**
								 * Below Update Tyre Usage Details in Rotition
								 * Values
								 */
								Integer Useage_TO = 0;

								switch (ROTATION_TO) {
								// this update Stepney not calculation use
								// kilometer
								case "ST-1":
									if (TyreTo.getTYRE_USEAGE() != null) {
										Useage_TO = TyreTo.getTYRE_USEAGE();
									}
									break;
								// this update calculation usage kilometer
								default:
									if (TyreTo.getTYRE_USEAGE() != null) {
										Useage_TO = TyreTo.getTYRE_USEAGE();
									}
									if (TyreTo.getOPEN_ODOMETER() != null) {
										Integer OpenOdometer = TyreTo.getOPEN_ODOMETER();

										if (DISMOUNT_ODOMETER > OpenOdometer) {
											Useage_TO += DISMOUNT_ODOMETER - OpenOdometer;
										}
									}
									break;
								}

								/* UPDate DisMount Value and Total Username */
								iventoryTyreService.update_Assign_TYRE_To_Vehicle_Rotation(Useage_TO, RO_TO_TYRE_ID);
							}

							RotationPosition_From = VehicleTyreLayoutService.Get_Position_ID_to_GetDetails(POSITION_ID, userDetails.getCompany_id());
							if (RotationPosition_From != null) {
								Long 		RO_FROM_TYRE_ID 		= RotationPosition_From.getTYRE_ID();
								String 		RO_FROM_TYRE_SERIAL_NO 	= RotationPosition_From.getTYRE_SERIAL_NO();
								Boolean 	RO_FROM_TYRE_ASSIGNED 	= RotationPosition_From.isTYRE_ASSIGNED();

								/** Check Rotation From Tyre Id Assign or not*/
								if (RO_FROM_TYRE_ID != null) {
									/**
									 * Below Get Rototion Postion to Id to Get
									 * Tyre ID Get Update Tyre History Details
									 */
									InventoryTyre 			TyreFROM = iventoryTyreService.Get_TYRE_ID_InventoryTyre(RO_FROM_TYRE_ID);
									/** Position Only in Tyre History Only */
									InventoryTyreHistory 	TyreHistory = Rotation_TyreHistoryDetails(TyreFROM,ROTATION_TO, VEHICLE_ID, VEHICLE_REGIS, DISMOUNT_ODOMETER, MOUNTED_DATE,COMMENT);
									
									TyreHistory.setCOMPANY_ID(userDetails.getCompany_id());
									if(TyreHistory.getTYRE_STATUS_ID() == InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_MOUNT) {
										inventoryTyreHistoryDto = iventoryTyreService.getPreTyreRotationHistory(RO_FROM_TYRE_ID,TyreHistory,InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_MOUNT);// used to get pre record of this tyre
									}else if(TyreHistory.getTYRE_STATUS_ID() == InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_ROTATION) {
										inventoryTyreHistoryDto = iventoryTyreService.getPreTyreRotationHistory(RO_FROM_TYRE_ID,TyreHistory,InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_ROTATION);// used to get pre record of this tyre
									}
									
									if(inventoryTyreHistoryDto != null && inventoryTyreHistoryDto.getTYRE_ASSIGN_DATE_TIMESTAMP() != null) {
										TyreHistory.setPRE_TYRE_ASSIGN_DATE(inventoryTyreHistoryDto.getTYRE_ASSIGN_DATE_TIMESTAMP());
									}
									if(inventoryTyreHistoryDto != null && inventoryTyreHistoryDto.getTYRE_NUMBER() != null) {
										TyreHistory.setPRE_TYRE_NUMBER(inventoryTyreHistoryDto.getTYRE_NUMBER());
									}
									if(inventoryTyreHistoryDto != null && inventoryTyreHistoryDto.getOPEN_ODOMETER() != null) {
										TyreHistory.setPRE_OPEN_ODOMETER(inventoryTyreHistoryDto.getOPEN_ODOMETER());
									}
									
									iventoryTyreService.add_Inventory_TYRE_History(TyreHistory);

									/**
									 * Below Update Tyre Usage Details in
									 * Rotition Values
									 */
									Integer Useage_FROM = 0;

									switch (POSITION_AXLE) {
									// this update Stepney not calculation use
									// kilometer
									case "ST-1":
										if (TyreFROM.getTYRE_USEAGE() != null) {
											Useage_FROM = TyreFROM.getTYRE_USEAGE();
										}
										break;
									// this update calculation usage kilometer
									default:
										if (TyreFROM.getTYRE_USEAGE() != null) {
											Useage_FROM = TyreFROM.getTYRE_USEAGE();
										}
										if (TyreFROM.getOPEN_ODOMETER() != null) {
											Integer OpenOdometer = TyreFROM.getOPEN_ODOMETER();

											if (DISMOUNT_ODOMETER > OpenOdometer) {
												Useage_FROM += DISMOUNT_ODOMETER - OpenOdometer;
											}
										}
										break;
									}

									/*
									 * UPDate DisMount Value and Total Username
									 */
									/*iventoryTyreService.update_Assign_TYRE_To_Vehicle_Rotation(Useage_FROM,
											RO_FROM_TYRE_ID);*/
								}

								/**
								 * Rotation From to Change Rotation To Update
								 * Details
								 */
								VehicleTyreLayoutService.update_Position_Assign_TYRE_To_Vehicle(RO_FROM_TYRE_ID,
										RO_FROM_TYRE_SERIAL_NO, RO_FROM_TYRE_ASSIGNED, RO_TO_PositionID,remark);

							}

							/**
							 * Rotation From to Change Rotation To Update
							 * Details
							 */
							VehicleTyreLayoutService.update_Position_Assign_TYRE_To_Vehicle(RO_TO_TYRE_ID,
									RO_TO_TYRE_SERIAL_NO, RO_TO_TYRE_ASSIGNED, POSITION_ID,remark);

						} else {
							return new ModelAndView("redirect:/VehicleTyreDetails?Id=" + VEHICLE_ID + "&TyreSize=true");
						}
					} else {
						return new ModelAndView("redirect:/VehicleTyreDetails?Id=" + VEHICLE_ID + "&TyreSize=true");
					}

					// update the Current Odometer vehicle Databases tables
					try {
						Integer currentODDMETER = vehicleService
								.updateCurrentOdoMeterGETVehicleToCurrentOdometer(VEHICLE_ID);

						if (currentODDMETER < DISMOUNT_ODOMETER) {
							
							vehicleService.updateCurrentOdoMeter(VEHICLE_ID, DISMOUNT_ODOMETER, userDetails.getCompany_id());
							// update current Odometer update Service Reminder
							ServiceReminderService.updateCurrentOdometerToServiceReminder(VEHICLE_ID,
									DISMOUNT_ODOMETER, userDetails.getCompany_id());
							
							serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(VEHICLE_ID, userDetails.getCompany_id(), userDetails.getId());
							if(serviceList != null) {
								for(ServiceReminderDto list : serviceList) {
									
									if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
										
										ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
										//emailAlertQueueService.sendEmailServiceOdometer(list);
										//smsAlertQueueService.sendSmsServiceOdometer(list);
									}
								}
							}

							VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToTyreAssign(
									VEHICLE_ID, VEHICLE_REGIS, DISMOUNT_ODOMETER, TYRE_ID);
							vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
							vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
						}
					} catch (Exception e) {
						LOGGER.error("Work Order:", e);
						e.printStackTrace();

					}

				} else {

					return new ModelAndView("redirect:/showVehicle?vid=" + VEHICLE_ID + "&danger=true", model);
				}
			}else{
				return new ModelAndView("redirect:/showVehicle?vid=" + VEHICLE_ID + "&danger=true", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("save Vehicle Status Page:");
			return new ModelAndView("redirect:/showVehicle?vid=" + VEHICLE_ID + "&danger=true");
		}
		return new ModelAndView("redirect:/VehicleTyreDetails?Id=" + VEHICLE_ID + "&RotationSuccess=true");

	}

	/** This controller show Vehicle Tyre details */
	@RequestMapping("/deleteVehicleTyreLayout")
	public ModelAndView deleteVehicleTyreLayout(@RequestParam("Id") final Integer Vehicle_ID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>  configuration	= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);

			if (Vehicle_ID != null) {
				/** Show Only Select Column Value in Vehicle Tables **/
				List<VehicleTyreLayoutPosition> validateTyreAssign = VehicleTyreLayoutService
						.Validate_Vehicle_Tyre_Position_AssignOrNot(Vehicle_ID, true, userDetails.getCompany_id());
				if (validateTyreAssign != null && !validateTyreAssign.isEmpty()) {

					model.put("RemoveAssignTyre", true);
					return new ModelAndView("redirect:/VehicleTyreDetails?Id=" + Vehicle_ID + "", model);

				} else {
					VehicleTyreLayoutService.Delete_VehicleTyreLayoutPosition(Vehicle_ID, userDetails.getCompany_id());

					VehicleTyreLayoutService.Delete_VehicleTyreLayout(Vehicle_ID, userDetails.getCompany_id());

					model.put("deleteSuccess", true);

				}
			} else {
				return new ModelAndView("redirect:/NotFound.in");
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Add Vehicle Status Page:", e);
		}
		if((boolean) configuration.get("vehicleModelTyreLayout")) {
			
			return new ModelAndView("redirect:/showVehicleTyreAssignedDetails?id=" + Vehicle_ID + "", model);
		}else {
			return new ModelAndView("redirect:/VehicleTyreLayout?Id=" + Vehicle_ID + "", model);
			
		}
	}

	private InventoryTyreHistory Rotation_TyreHistoryDetails(InventoryTyre Tyre, String POSITION_AXLE,
			Integer VEHICLE_ID, String VEHICLE_REGIS, Integer DISMOUNT_ODOMETER, String MOUNTED_DATE, String COMMENT) {

		InventoryTyreHistory TyreHistory = new InventoryTyreHistory();

		TyreHistory.setTYRE_ID(Tyre.getTYRE_ID());
		TyreHistory.setTYRE_NUMBER(Tyre.getTYRE_NUMBER());

		TyreHistory.setVEHICLE_ID(VEHICLE_ID);
		//TyreHistory.setVEHICLE_REGISTRATION(VEHICLE_REGIS);
		

		if (POSITION_AXLE != null) {
			String[] Tyreaxle = POSITION_AXLE.split("-");
			TyreHistory.setPOSITION(Tyreaxle[0]);
			TyreHistory.setAXLE(Tyreaxle[1]);
		}
		TyreHistory.setTYRE_STATUS_ID(InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_ROTATION);
		TyreHistory.setOPEN_ODOMETER(DISMOUNT_ODOMETER);

		/** this story one dis mount Tyre Useage */
		Integer TyreHistoryUseage = 0;

		switch (POSITION_AXLE) {
		case "ST-1":
			TyreHistory.setTYRE_USEAGE(TyreHistoryUseage);
			break;

		default:

			if (Tyre.getOPEN_ODOMETER() != null) {
				Integer OpenOdometer = Tyre.getOPEN_ODOMETER();

				if (DISMOUNT_ODOMETER > OpenOdometer) {
					TyreHistoryUseage = DISMOUNT_ODOMETER - OpenOdometer;
				}
			}
			TyreHistory.setTYRE_USEAGE(TyreHistoryUseage);

			break;
		}
		try {
			/*
			 * java.util.Date currentDateUpdate = new java.util.Date();
			 * Timestamp toDate = new
			 * java.sql.Timestamp(currentDateUpdate.getTime());
			 */
			// fuel date converted String to date Format
			java.util.Date date = dateFormat.parse(MOUNTED_DATE);
			java.sql.Date MoundDate = new Date(date.getTime());
			TyreHistory.setTYRE_ASSIGN_DATE(MoundDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		TyreHistory.setTYRE_COMMENT(COMMENT);

		return TyreHistory;
	}

}
