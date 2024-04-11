package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleProfitAndLossIncomeTxnChecker;

public interface IVehicleProfitAndLossIncomeTxnCheckerService {

	public void saveVehicleProfitAndLossTxnChecker(VehicleProfitAndLossIncomeTxnChecker	profitAndLossTxnChecker) throws Exception;
	
	public List<VehicleProfitAndLossIncomeTxnChecker>  getAllPendingTxnCheckerListForVehicleExpenseAdded() throws Exception;
	
	public List<VehicleProfitAndLossIncomeTxnChecker> getAllPendingTxnCheckerListForMonthWiseVehicleIncomeAdded() throws Exception;
	
	public List<VehicleProfitAndLossIncomeTxnChecker> getAllPendingTxnCheckerListForMonthWiseVehicleBalanceAdded() throws Exception;
}
