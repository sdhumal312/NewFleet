package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleBatteryLayoutDto;
import org.fleetopgroup.persistence.model.VehicleBatteryLayout;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IVehicleBatteryLayoutService {

	List<VehicleBatteryLayoutDto>  getVehicleBatteryLayout(Integer vid, Integer companyId) throws Exception;
	
	public void createBatteryLayout(VehicleBatteryLayout vehicleBatteryLayout) throws Exception;
	
	ValueObject	mountVehicleBatteryDetails(ValueObject valueObject) throws Exception;
	
	ValueObject	disMountVehicleBatteryDetails(ValueObject valueObject) throws Exception;
	
	public void update_Position_Assign_Battery_To_Vehicle(Long batteryId ,boolean batteryAsigned, Long layoutId, Long batteryCapacityId) throws Exception;
	
	ValueObject getBatteryAsignmentDetails(ValueObject valueObject) throws Exception;
	
	ValueObject deleteVehicleBatteryLayout(ValueObject valueObject) throws Exception;
	
}
