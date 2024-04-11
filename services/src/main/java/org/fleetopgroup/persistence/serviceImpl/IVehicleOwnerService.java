package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleOwnerDto;
import org.fleetopgroup.persistence.model.VehicleOwner;

public interface IVehicleOwnerService {

	// Save Vehicle Owner Information Details
	VehicleOwner Register_New_VehicleOwner(VehicleOwner accountDto) throws Exception;

	// Show Vehicle Owner List in Using Vehicle Id to Details
	public List<VehicleOwnerDto> list_Of_Vehicle_ID_VehicleOwner(Integer vehicle_id, Integer companyId) throws Exception;

	// Show Vehicle Owner List in Using Vehicle Id to Details
	public List<VehicleOwner> Validate_VehicleOwner_name(String vehicleOwnerName, String vehicleOwnerSerial, Integer companyId, Integer vid)
			throws Exception;

	// Edir Vehicle Owner List in Using Vehicle Owner Id to Details
	public VehicleOwner Get_Vehicle_Owner(int doc_id, Integer companyId) throws Exception;

	// Delete Vehicle Owner List in Using Vehicle Owner Id to Details
	public void Delete_VehicleOwner(int doc_id, Integer companyId) throws Exception;

	public List<VehicleOwner> Validate_VehicleOwner_name(String veh_OWNER_NAME, Integer company_id, Integer vEHICLE_VID)throws Exception;

}
