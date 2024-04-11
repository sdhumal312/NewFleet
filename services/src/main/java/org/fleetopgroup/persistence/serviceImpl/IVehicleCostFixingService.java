package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.VehicleCostFixing;

public interface IVehicleCostFixingService {

	public void saveVehicleCostFixing(VehicleCostFixing		vehicleCostFixing) throws Exception;
	
	public VehicleCostFixing getVehicleCostFixingByTyreSubTypeId(Integer companyId, Integer tyreSubTypeId) throws Exception;
}
