package org.fleetopgroup.persistence.service;

import java.util.List;

import org.fleetopgroup.persistence.dao.VehicleProfitAndLossIncomeTxnCheckerRepository;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossIncomeTxnChecker;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossIncomeTxnCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleProfitAndLossIncomeTxnCheckerService implements IVehicleProfitAndLossIncomeTxnCheckerService {
	
	@Autowired private VehicleProfitAndLossIncomeTxnCheckerRepository	vehicleProfitAndLossIncomeTxnCheckerRepository;

	@Override
	public void saveVehicleProfitAndLossTxnChecker(VehicleProfitAndLossIncomeTxnChecker profitAndLossTxnChecker)
			throws Exception {

		vehicleProfitAndLossIncomeTxnCheckerRepository.save(profitAndLossTxnChecker);
	}
	
	
	@Override
	public List<VehicleProfitAndLossIncomeTxnChecker> getAllPendingTxnCheckerListForVehicleExpenseAdded()
			throws Exception {
		
		return vehicleProfitAndLossIncomeTxnCheckerRepository.getAllPendingTxnCheckerListForVehicleIncomeAdded();
	}
	
	@Override
	public List<VehicleProfitAndLossIncomeTxnChecker> getAllPendingTxnCheckerListForMonthWiseVehicleIncomeAdded()
			throws Exception {
		
		return vehicleProfitAndLossIncomeTxnCheckerRepository.getAllPendingTxnCheckerListForMonthWiseVehicleIncomeAdded();
	}
	
	@Override
	public List<VehicleProfitAndLossIncomeTxnChecker> getAllPendingTxnCheckerListForMonthWiseVehicleBalanceAdded()
			throws Exception {
	
		return vehicleProfitAndLossIncomeTxnCheckerRepository.getAllPendingTxnCheckerListForMonthWiseVehicleBalanceAdded();
	}
}
