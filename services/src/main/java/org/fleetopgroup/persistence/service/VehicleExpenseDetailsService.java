package org.fleetopgroup.persistence.service;

import java.util.List;

import org.fleetopgroup.persistence.dao.VehicleExpenseDetailsRepository;
import org.fleetopgroup.persistence.model.VehicleExpenseDetails;
import org.fleetopgroup.persistence.serviceImpl.IVehicleExpenseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleExpenseDetailsService implements IVehicleExpenseDetailsService {

	@Autowired	VehicleExpenseDetailsRepository			vehicleExpenseDetailsRepository;
	@Override
	public VehicleExpenseDetails getVehicleExpenseDetails(Integer fuelId, Integer companyId, Integer vid, short type) throws Exception {
		
		return vehicleExpenseDetailsRepository.validateVehicleExpenseDetails(fuelId, companyId, vid, type);
	}
	
	@Override
	public VehicleExpenseDetails getVehicleExpenseDetails(Long fuelId, Integer companyId, Integer vid, short type)
			throws Exception {
		
		return vehicleExpenseDetailsRepository.validateVehicleExpenseDetails(fuelId, companyId, vid, type);
	}

	
	@Override
	public List<VehicleExpenseDetails> getVehicleExpenseDetailsList(Long fuelId, Integer companyId, Integer vid)
			throws Exception {
		
		return vehicleExpenseDetailsRepository.validateVehicleExpenseDetailsList(fuelId, companyId, vid);
	}
}
