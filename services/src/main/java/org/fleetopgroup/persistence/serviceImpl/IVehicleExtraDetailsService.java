package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.dto.VehicleExtraDetailsDto;

public interface IVehicleExtraDetailsService {

	public VehicleExtraDetailsDto getVehicleExtraDetailsByVid(Integer vid)throws Exception;
}
