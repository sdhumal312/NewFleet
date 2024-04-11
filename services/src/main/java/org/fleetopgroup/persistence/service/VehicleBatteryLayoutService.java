package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.BatteryConstant;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.dao.BatteryRepository;
import org.fleetopgroup.persistence.dao.VehicleBatteryLayoutRepository;
import org.fleetopgroup.persistence.dto.BatteryDto;
import org.fleetopgroup.persistence.dto.BatteryHistoryDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.VehicleBatteryLayoutDto;
import org.fleetopgroup.persistence.model.Battery;
import org.fleetopgroup.persistence.model.BatteryHistory;
import org.fleetopgroup.persistence.model.VehicleBatteryLayout;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.serviceImpl.IBatteryHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleBatteryLayoutService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class VehicleBatteryLayoutService implements IVehicleBatteryLayoutService{

	@PersistenceContext	EntityManager entityManager;

	@Autowired private VehicleBatteryLayoutRepository		vehicleBatteryLayoutRepository;
	@Autowired private BatteryRepository					batteryRepository;
	@Autowired private IBatteryHistoryService				batteryHistoryService;
	@Autowired private IBatteryService						batteryService;
	@Autowired private IVehicleService 						vehicleService;
	@Autowired private IServiceReminderService				ServiceReminderService;
	@Autowired private IVehicleOdometerHistoryService 		vehicleOdometerHistoryService;
	
	VehicleOdometerHistoryBL VOHBL = new VehicleOdometerHistoryBL();
	
	@Override
	public List<VehicleBatteryLayoutDto> getVehicleBatteryLayout(Integer vid, Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> query = null;
		
				 query = entityManager.createQuery(
						"SELECT V.layoutId, V.vid, VH.vehicle_registration, V.noOfBattery, V.batteryCapacityId, V.createdById, BC.batteryCapacity,"
						+ " U.email, V.createdOn, V.layoutPosition, VH.vehicle_Odometer, V.batteryAsigned, BA.batterySerialNumber, BA.batteryId, BC.batteryCapacityId "
						+ " from VehicleBatteryLayout AS V "
						+ " INNER JOIN Vehicle VH ON VH.vid = V.vid"
						+ " LEFT JOIN Battery BA ON BA.batteryId = V.batteryId"
						+ " LEFT JOIN BatteryCapacity BC ON BC.batteryCapacityId = V.batteryCapacityId"
						+ " LEFT JOIN User U ON U.id = V.createdById"
						+ " WHERE V.vid ="+vid+" AND V.companyId = "+companyId+" AND V.markForDelete = 0 ", Object[].class);

				
			List<Object[]> results = query.getResultList();
			
			List<VehicleBatteryLayoutDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleBatteryLayoutDto>();
				VehicleBatteryLayoutDto select = null;
				for (Object[] vehicle : results) {
					select = new VehicleBatteryLayoutDto();
					
					select.setLayoutId((Long) vehicle[0]);
					select.setVid((Integer) vehicle[1]);
					select.setVehicle_registration((String) vehicle[2]);
					select.setNoOfBattery((Integer) vehicle[3]);
					select.setBatteryCapacityId((Long) vehicle[4]);
					select.setCreatedById((Long) vehicle[5]);
					select.setBatteryCapacity((String) vehicle[6]);
					select.setCreatedBy((String) vehicle[7]);
					select.setCreatedOn((Timestamp) vehicle[8]);
					select.setCreationDate(DateTimeUtility.getDateFromTimeStamp(select.getCreatedOn(), DateTimeUtility.DD_MM_YYYY));
					select.setLayoutPosition((short) vehicle[9]);
					select.setVehicle_Odometer((Integer) vehicle[10]);
					select.setBatteryAsigned((boolean) vehicle[11]);
					select.setBatterySerialNumber((String) vehicle[12]);
					select.setBatteryId((Long) vehicle[13]);
					select.setBatteryCapacityId((Long) vehicle[14]);
					
					Dtos.add(select);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public void createBatteryLayout(VehicleBatteryLayout vehicleBatteryLayout) throws Exception {
		
		vehicleBatteryLayoutRepository.save(vehicleBatteryLayout);
		
	}
	
	@Override
	@Transactional
	public ValueObject mountVehicleBatteryDetails(ValueObject valueObject) throws Exception {
		VehicleBatteryLayout		vehicleBatteryLayout		= null;
		Battery						battery						= null;
		CustomUserDetails			userDetails					= null;
		Long						layoutId					= null;
		Long						batteryId					= null;
		BatteryHistory				batteryHistory				= null;
		List<ServiceReminderDto>	serviceList					= null;
		short						layoutPosition				= 0;
		BatteryHistoryDto			batteryHistoryDto			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			layoutId	= valueObject.getLong("layoutId", 0);
			batteryId	= valueObject.getLong("batteryId", 0);
			layoutPosition	= valueObject.getShort("layoutPosition");
		
			if(batteryId != 0 && layoutId != 0) {
				battery					= batteryRepository.findById(batteryId).get();
				vehicleBatteryLayout	= vehicleBatteryLayoutRepository.findById(layoutId).get();
				batteryHistoryDto		= batteryHistoryService.getPreBatteryHistory(vehicleBatteryLayout,layoutPosition,BatteryConstant.BATTERY_ASIGNED_STATUS_IN_DISMOUNT, userDetails.getCompany_id());
				batteryHistory			= getBatteryHistory(valueObject, battery, vehicleBatteryLayout,batteryHistoryDto);
				batteryHistory.setCreatedById(userDetails.getId());
				batteryHistory.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
				batteryHistoryService.save(batteryHistory);
				if(battery.getBatteryFirstAsignedDate() != null) {
					if(battery.getBatteryFirstAsignedDate().after(batteryHistory.getBatteryAsignDate())) {
						batteryService.update_Assign_Battry_To_Vehicle_Mount(vehicleBatteryLayout.getVid(), batteryHistory.getOpenOdometer(), BatteryConstant.BATTERY_ASIGNED_STATUS_IN_SERVICE, batteryHistory.getBatteryAsignDate(), batteryId, userDetails.getCompany_id(), batteryHistory.getBatteryAsignDate());
					}else {
						batteryService.update_Assign_Battry_To_Vehicle_Mount(vehicleBatteryLayout.getVid(), batteryHistory.getOpenOdometer(), BatteryConstant.BATTERY_ASIGNED_STATUS_IN_SERVICE, batteryHistory.getBatteryAsignDate(), batteryId, userDetails.getCompany_id());
					}
					
				}else {
					batteryService.update_Assign_Battry_To_Vehicle_Mount(vehicleBatteryLayout.getVid(), batteryHistory.getOpenOdometer(), BatteryConstant.BATTERY_ASIGNED_STATUS_IN_SERVICE, batteryHistory.getBatteryAsignDate(), batteryId, userDetails.getCompany_id(), batteryHistory.getBatteryAsignDate());
				}
				
				vehicleBatteryLayoutRepository.update_Position_Assign_Battery_To_Vehicle(batteryId, true, layoutId);
			
				try {
					Integer currentODDMETER = vehicleService
							.updateCurrentOdoMeterGETVehicleToCurrentOdometer(vehicleBatteryLayout.getVid());

					if (currentODDMETER < batteryHistory.getOpenOdometer()) {
						
						vehicleService.updateCurrentOdoMeter(vehicleBatteryLayout.getVid(), batteryHistory.getOpenOdometer(), userDetails.getCompany_id());
						// update current Odometer update Service Reminder
						ServiceReminderService.updateCurrentOdometerToServiceReminder(vehicleBatteryLayout.getVid(), batteryHistory.getOpenOdometer(), userDetails.getCompany_id());
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(vehicleBatteryLayout.getVid(), userDetails.getCompany_id(), userDetails.getId());
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
								vehicleBatteryLayout.getVid(), null, batteryHistory.getOpenOdometer(), batteryId);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
					}else if(currentODDMETER.equals(batteryHistory.getOpenOdometer())){
						VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToTyreAssign(
								vehicleBatteryLayout.getVid(), null, batteryHistory.getOpenOdometer(), batteryId);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
					}
				} catch (Exception e) {
					e.printStackTrace();

				}

			}
			valueObject.put("vid", vehicleBatteryLayout.getVid());
			valueObject.put("message", "Battery Mounted Successfully !");
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleBatteryLayout		= null;
			battery						= null;
			userDetails					= null;
			layoutId					= null;
			batteryId					= null;
			batteryHistory				= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject disMountVehicleBatteryDetails(ValueObject valueObject) throws Exception {
		VehicleBatteryLayout		vehicleBatteryLayout		= null;
		Battery						battery						= null;
		CustomUserDetails			userDetails					= null;
		Long						layoutId					= null;
		Long						batteryId					= null;
		short						layoutPosition				= 0;
		BatteryHistory				batteryHistory				= null;
		BatteryHistoryDto			batteryHistoryDto			= null;
		List<ServiceReminderDto>	serviceList					= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			layoutId	= valueObject.getLong("layoutId", 0);
			batteryId	= valueObject.getLong("batteryId", 0);
			layoutPosition	= valueObject.getShort("layoutPosition");
			
			if(batteryId != 0 && layoutId != 0) {
				battery					= batteryRepository.findById(batteryId).get();
				vehicleBatteryLayout	= vehicleBatteryLayoutRepository.findById(layoutId).get();
				batteryHistoryDto		= batteryHistoryService.getPreBatteryHistory(vehicleBatteryLayout,layoutPosition,BatteryConstant.BATTERY_ASIGNED_STATUS_IN_MOUNT,userDetails.getCompany_id());
				batteryHistory			= getBatteryHistoryForDismount(valueObject, battery, vehicleBatteryLayout,batteryHistoryDto);
				/*battery history last mount date and odometer................................*/
				batteryHistoryService.save(batteryHistory);
				
				
				Integer Useage = 0; 
				Long usesNoOfTime =	(long) 0;
				Integer OpenOdometer = 0;

					if (battery.getBatteryUsesOdometer() != null) {
						Useage = battery.getBatteryUsesOdometer();
					}
					if (battery.getOpenOdometer() != null) {
						OpenOdometer = battery.getOpenOdometer();

						if (batteryHistory.getOpenOdometer() > OpenOdometer) {
							Useage += batteryHistory.getOpenOdometer() - OpenOdometer;
						}
					}
					if(battery.getUsesNoOfTime() != null) {
						usesNoOfTime = battery.getUsesNoOfTime();
					}
					if(battery.getBatteryAsignedDate().equals(batteryHistory.getBatteryAsignDate())) {
						if(batteryHistory.getOpenOdometer() > OpenOdometer) {
							usesNoOfTime +=	(long) 1;
						}
					}else {
						usesNoOfTime +=	 DateTimeUtility.getDayDiffBetweenTwoDates(battery.getBatteryAsignedDate(), batteryHistory.getBatteryAsignDate());
					}
				if(valueObject.getBoolean("oldBatteryMoveConfig",false)) {
					if(valueObject.getShort("oldBatteryMoveId") == BatteryConstant.OLD_BATTERY_MOVED_TO_WORKSHOP) {
						batteryService.updateDismountStatusId(vehicleBatteryLayout.getVid(), 
								batteryHistory.getOpenOdometer(), Useage, BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE,valueObject.getShort("oldBatteryMoveId"), batteryId, usesNoOfTime);
						
					}else {
						batteryService.updateDismountStatusId(vehicleBatteryLayout.getVid(), 
								batteryHistory.getOpenOdometer(), Useage, BatteryConstant.BATTERY_ASIGNED_STATUS_IN_UNAVAILABLE, valueObject.getShort("oldBatteryMoveId"), batteryId, usesNoOfTime);
					}
				}else {
					batteryService.update_Assign_Battry_To_Vehicle_DisMount(vehicleBatteryLayout.getVid(), 
							batteryHistory.getOpenOdometer(), Useage, BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE, batteryId, usesNoOfTime);
					
				}
				
				vehicleBatteryLayoutRepository.update_Position_Assign_Battery_To_Vehicle(null, false, layoutId);
			
				try {
					Integer currentODDMETER = vehicleService
							.updateCurrentOdoMeterGETVehicleToCurrentOdometer(vehicleBatteryLayout.getVid());

					if (currentODDMETER < batteryHistory.getOpenOdometer()) {
						
						vehicleService.updateCurrentOdoMeter(vehicleBatteryLayout.getVid(), batteryHistory.getOpenOdometer(), userDetails.getCompany_id());
						// update current Odometer update Service Reminder
						ServiceReminderService.updateCurrentOdometerToServiceReminder(vehicleBatteryLayout.getVid(), batteryHistory.getOpenOdometer(), userDetails.getCompany_id());

						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(vehicleBatteryLayout.getVid(), userDetails.getCompany_id(), userDetails.getId());
						if(serviceList != null) {
							for(ServiceReminderDto list : serviceList) {
								
								if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
									
									ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
									//emailAlertQueueService.sendEmailServiceOdometer(list);
									//smsAlertQueueService.sendSmsServiceOdometer(list);
								}
							}
						}
						
						VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToTyreAssign(vehicleBatteryLayout.getVid(), null, batteryHistory.getOpenOdometer(), batteryId);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
					}else if(currentODDMETER.equals(batteryHistory.getOpenOdometer())) {
						VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToTyreAssign(vehicleBatteryLayout.getVid(), null, batteryHistory.getOpenOdometer(), batteryId);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
					}
				} catch (Exception e) {
					e.printStackTrace();

				}

			}
			valueObject.put("vid", vehicleBatteryLayout.getVid());
			valueObject.put("message", "Battery DisMounted Successfully !");
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleBatteryLayout		= null;
			battery						= null;
			userDetails					= null;
			layoutId					= null;
			batteryId					= null;
			batteryHistory				= null;
		}
	}
	
	private BatteryHistory getBatteryHistory(ValueObject valueObject, Battery battery, VehicleBatteryLayout batteryLayout, BatteryHistoryDto batteryHistoryDto) throws Exception{
		BatteryHistory		batteryHistory		= null;
		try {
			batteryHistory	= new BatteryHistory();
			batteryHistory.setBatteryId(battery.getBatteryId());
			batteryHistory.setBatterySerialNumber(battery.getBatterySerialNumber());
			batteryHistory.setVid(batteryLayout.getVid());
			batteryHistory.setLayoutPosition(batteryLayout.getLayoutPosition());
			batteryHistory.setBatteryStatusId(BatteryConstant.BATTERY_ASIGNED_STATUS_IN_MOUNT);
			batteryHistory.setOpenOdometer(valueObject.getInt("vehicle_Odometer", 0));
			batteryHistory.setBatteryUseage(0);
			batteryHistory.setUsesNoOfDay((long) 0);
			batteryHistory.setBatteryAsignDate(DateTimeUtility.getDateTimeFromStr(valueObject.getString("mountDate"), DateTimeUtility.DD_MM_YYYY));
			batteryHistory.setBatteryComment(valueObject.getString("remark"));
			batteryHistory.setCompanyId(battery.getCompanyId());
			batteryHistory.setBatteryCost(battery.getBatteryAmount());
			if(batteryHistoryDto != null) {
				batteryHistory.setPreBatterySerialNumber(batteryHistoryDto.getBatterySerialNumber());
				batteryHistory.setPreBatteryAsignDate(batteryHistoryDto.getBatteryAsignDate());
			}
			
			return batteryHistory;
		} catch (Exception e) {
			throw e;
		}
	}
	private BatteryHistory getBatteryHistoryForDismount(ValueObject valueObject, Battery battery, VehicleBatteryLayout batteryLayout,BatteryHistoryDto batteryHistoryDto) throws Exception{
		BatteryHistory		batteryHistory		= null;
		try {
			batteryHistory	= new BatteryHistory();
			batteryHistory.setBatteryId(battery.getBatteryId());
			batteryHistory.setBatterySerialNumber(battery.getBatterySerialNumber());
			batteryHistory.setVid(batteryLayout.getVid());
			batteryHistory.setLayoutPosition(batteryLayout.getLayoutPosition());
			batteryHistory.setBatteryStatusId(BatteryConstant.BATTERY_ASIGNED_STATUS_IN_DISMOUNT);
			batteryHistory.setOpenOdometer(valueObject.getInt("vehicle_Odometer", 0));
			batteryHistory.setBatteryUseage(valueObject.getInt("vehicle_Odometer", 0) - battery.getOpenOdometer());
			batteryHistory.setBatteryAsignDate(DateTimeUtility.getDateTimeFromStr(valueObject.getString("mountDate"), DateTimeUtility.DD_MM_YYYY));
			batteryHistory.setBatteryComment(valueObject.getString("remark"));
			batteryHistory.setCompanyId(battery.getCompanyId());
			batteryHistory.setBatteryCost(battery.getBatteryAmount());
			batteryHistory.setUsesNoOfDay(DateTimeUtility.getDayDiffBetweenTwoDates(battery.getBatteryAsignedDate(), DateTimeUtility.getCurrentTimeStamp()));
			batteryHistory.setPreBatterySerialNumber(batteryHistoryDto.getBatterySerialNumber());
			batteryHistory.setPreBatteryAsignDate(batteryHistoryDto.getBatteryAsignDate());
			return batteryHistory;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void update_Position_Assign_Battery_To_Vehicle(Long batteryId ,boolean batteryAsigned, Long layoutId, Long batteryCapacityId) throws Exception {
		
		vehicleBatteryLayoutRepository.update_Position_Assign_Battery_To_Vehicle(batteryId, batteryAsigned, layoutId);
	}
	
	@Override
	public ValueObject getBatteryAsignmentDetails(ValueObject valueObject) throws Exception {
		BatteryDto		batteryLayout		= null;
		try {
			batteryLayout	=	batteryService.getVehicleBatteryDetails(valueObject.getLong("layoutId", 0), valueObject.getLong("batteryId", 0));
			valueObject.put("battery", batteryLayout);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			batteryLayout		= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject deleteVehicleBatteryLayout(ValueObject valueObject) throws Exception {
		try {
			
			vehicleBatteryLayoutRepository.deleteBatteryLayout(valueObject.getInt("vid", 0));
			
			valueObject.put("deleteLayout", true);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
}