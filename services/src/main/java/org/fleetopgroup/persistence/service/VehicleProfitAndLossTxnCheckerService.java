package org.fleetopgroup.persistence.service;

import java.util.List;

import org.fleetopgroup.persistence.dao.VehicleProfitAndLossTxnCheckerRepository;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossTxnCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleProfitAndLossTxnCheckerService implements IVehicleProfitAndLossTxnCheckerService {
	
	@Autowired	private VehicleProfitAndLossTxnCheckerRepository		vehicleProfitAndLossTxnCheckerRepository;

	@Override
	public void saveVehicleProfitAndLossTxnChecker(VehicleProfitAndLossTxnChecker profitAndLossTxnChecker)
			throws Exception {
		
		vehicleProfitAndLossTxnCheckerRepository.save(profitAndLossTxnChecker);
	}

	
	@Override
	public List<VehicleProfitAndLossTxnChecker> getAllPendingTxnCheckerListForVehicleExpenseAdded() throws Exception {
		
		return vehicleProfitAndLossTxnCheckerRepository.getAllPendingTxnCheckerListForVehicleExpenseAdded();
	}


	@Override
	public List<VehicleProfitAndLossTxnChecker> getAllPendingTxnCheckerListForDateWiseVehicleExpenseAdded()
			throws Exception {
		return vehicleProfitAndLossTxnCheckerRepository.getAllPendingTxnCheckerListForDateWiseVehicleExpenseAdded();
	}


	@Override
	public List<VehicleProfitAndLossTxnChecker> getAllPendingTxnCheckerListForMonthWiseVehicleExpenseAdded()
			throws Exception {
		return vehicleProfitAndLossTxnCheckerRepository.getAllPendingTxnCheckerListForMonthWiseVehicleExpenseAdded();
	}


	@Override
	public List<VehicleProfitAndLossTxnChecker> getAllPendingTxnCheckerListForDateWiseVehicleBalanceAdded()
			throws Exception {
		return vehicleProfitAndLossTxnCheckerRepository.getAllPendingTxnCheckerListForDateWiseVehicleBalanceAdded();
	}


	@Override
	public List<VehicleProfitAndLossTxnChecker> getAllPendingTxnCheckerListForMonthWiseVehicleBalanceAdded()
			throws Exception {
		return vehicleProfitAndLossTxnCheckerRepository.getAllPendingTxnCheckerListForMOnthWiseVehicleBalanceAdded();
	}
}
