package org.fleetopgroup.persistence.bl;

import java.util.Date;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleTypeDto;
import org.fleetopgroup.persistence.model.VehicleTypeToServiceProgram;

public class VehicleTypeToServiceProgramBL {

	public static VehicleTypeToServiceProgram getVehicleTypeToServiceProgramDto(VehicleTypeDto dto, CustomUserDetails userDetails) {
		VehicleTypeToServiceProgram		vehicleTypeToServiceProgram		= null;
		try {
			vehicleTypeToServiceProgram	= new VehicleTypeToServiceProgram();
			
			vehicleTypeToServiceProgram.setServiceProgramId(dto.getServiceProgramId());
			vehicleTypeToServiceProgram.setCreatedById(userDetails.getId());
			vehicleTypeToServiceProgram.setCreatedOn(new Date());
			vehicleTypeToServiceProgram.setLastModifiedBy(userDetails.getId());
			vehicleTypeToServiceProgram.setLastModifiedOn(new Date());
			vehicleTypeToServiceProgram.setCompanyId(userDetails.getCompany_id());
			
			return vehicleTypeToServiceProgram;
		} catch (Exception e) {
			throw e;
		}
	}
}
