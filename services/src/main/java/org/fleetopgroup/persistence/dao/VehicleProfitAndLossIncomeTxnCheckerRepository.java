package org.fleetopgroup.persistence.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.model.VehicleProfitAndLossIncomeTxnChecker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleProfitAndLossIncomeTxnCheckerRepository extends JpaRepository<VehicleProfitAndLossIncomeTxnChecker, Long>{

	@Query("FROM VehicleProfitAndLossIncomeTxnChecker where vehicleProfitAndLossTxnCheckerId = ?1")
	public VehicleProfitAndLossIncomeTxnChecker getVehicleProfitAndLossTxnChecker(Long vehicleProfitAndLossTxnCheckerId) throws Exception;
	
	@Modifying
	@Transactional
	@Query("UPDATE VehicleProfitAndLossIncomeTxnChecker SET isVehicleIncomeAdded = 1 where vehicleProfitAndLossTxnCheckerId = ?1")
	public void updateVehicleIncomeAdded(Long fuelEntryTxnCheckerId) throws Exception;
	
	@Modifying
	@Transactional
	@Query("UPDATE VehicleProfitAndLossIncomeTxnChecker SET isMonthWiseVehicleIncomeAdded = 1 where vehicleProfitAndLossTxnCheckerId = ?1")
	public void updateMonthWiseVehicleIncomeAdded(Long fuelEntryTxnCheckerId) throws Exception;
	
	@Query("FROM VehicleProfitAndLossIncomeTxnChecker where isVehicleIncomeAdded = 0")
	public List<VehicleProfitAndLossIncomeTxnChecker> getAllPendingTxnCheckerListForVehicleIncomeAdded() throws Exception;
	
	@Query("FROM VehicleProfitAndLossIncomeTxnChecker where isMonthWiseVehicleIncomeAdded = 0")
	public List<VehicleProfitAndLossIncomeTxnChecker> getAllPendingTxnCheckerListForMonthWiseVehicleIncomeAdded() throws Exception;
	
	
	@Query("FROM VehicleProfitAndLossIncomeTxnChecker where isMonthWiseVehicleBalanceUpdated = 0")
	public List<VehicleProfitAndLossIncomeTxnChecker> getAllPendingTxnCheckerListForMonthWiseVehicleBalanceAdded() throws Exception;
	
	
	@Modifying
	@Transactional
	@Query("DELETE FROM VehicleProfitAndLossIncomeTxnChecker where isFinallyEntered = 1")
	public void deleteVehicleExpenseTxnChecker() throws Exception;
	
}
