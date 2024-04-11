package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.VehicleCostFixingRepository;
import org.fleetopgroup.persistence.model.VehicleCostFixing;
import org.fleetopgroup.persistence.serviceImpl.IVehicleCostFixingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleCostFixingService implements IVehicleCostFixingService{

	@Autowired	VehicleCostFixingRepository			vehicleCostFixingRepository;
	
	@Override
	public void saveVehicleCostFixing(VehicleCostFixing vehicleCostFixing) throws Exception {
		
		vehicleCostFixingRepository.save(vehicleCostFixing);
	}
	
	@Override
	public VehicleCostFixing getVehicleCostFixingByTyreSubTypeId(Integer companyId, Integer tyreSubTypeId) throws Exception {
		
		try {
			return	vehicleCostFixingRepository.getVehicleCostFixingByTyreSubTypeId(companyId, tyreSubTypeId);
		} catch (Exception e) {
			throw e;
		}
		
	}
}
