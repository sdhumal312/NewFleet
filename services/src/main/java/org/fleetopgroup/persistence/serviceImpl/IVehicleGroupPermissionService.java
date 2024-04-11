package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleGroupPermissionDto;
import org.fleetopgroup.persistence.model.VehicleGroupPermision;

public interface IVehicleGroupPermissionService {
	

    String getVehicleGroupPrmissionOfUser();
    
    List<VehicleGroupPermissionDto> getVehicleGroupPrmissionByUserId(Long id, Integer companyId) throws Exception; 
    
    void registerVehicleGroupPrmissionByUserId(VehicleGroupPermision vehicleGroup);

	void deleteVehicleGroupPrmissionByUserId(Long user_id, Integer companyId);

}
