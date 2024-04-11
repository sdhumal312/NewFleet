package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleInspectionSheetDto;
import org.fleetopgroup.persistence.dto.VehicleTypeAssignmebtDetailsDto;

public interface IVehicleTypeAssignmentDetailsService {
	public VehicleTypeAssignmebtDetailsDto  getvehicleTypeAssignmebtDetailsByTypeId(Long vehicleTypeId) throws Exception;
	public long checkIfSheetAssignedToVehicleType(String vehiceType,Integer companyId) throws Exception;
	
	public List<VehicleInspectionSheetDto> getvehicleTypeAssignmebtDetailsBySheetId(Long inspectionSheetId) throws Exception ;
}
