package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;

public interface IVehicleProfitAndLossTxnCheckerService {

	public void saveVehicleProfitAndLossTxnChecker(VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker) throws Exception;
	
	List<VehicleProfitAndLossTxnChecker>  getAllPendingTxnCheckerListForVehicleExpenseAdded() throws Exception;
	
	List<VehicleProfitAndLossTxnChecker>  getAllPendingTxnCheckerListForDateWiseVehicleExpenseAdded() throws Exception;
	
	List<VehicleProfitAndLossTxnChecker>  getAllPendingTxnCheckerListForMonthWiseVehicleExpenseAdded() throws Exception;
	
	List<VehicleProfitAndLossTxnChecker>  getAllPendingTxnCheckerListForDateWiseVehicleBalanceAdded() throws Exception;
	
	List<VehicleProfitAndLossTxnChecker>  getAllPendingTxnCheckerListForMonthWiseVehicleBalanceAdded() throws Exception;
}
