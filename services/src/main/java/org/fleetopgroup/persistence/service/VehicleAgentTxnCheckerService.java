package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.VehicleAgentTxnCheckerRepository;
import org.fleetopgroup.persistence.model.VehicleAgentTxnChecker;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentTxnCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleAgentTxnCheckerService implements IVehicleAgentTxnCheckerService {
	
	@Autowired	private VehicleAgentTxnCheckerRepository		vehicleAgentTxnCheckerRepository;
	
	@Override
	public void save(VehicleAgentTxnChecker agentTxnChecker) throws Exception {
		
		vehicleAgentTxnCheckerRepository.save(agentTxnChecker);
	}

}
