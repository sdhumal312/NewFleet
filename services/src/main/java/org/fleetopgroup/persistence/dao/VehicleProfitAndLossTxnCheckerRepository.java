package org.fleetopgroup.persistence.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleProfitAndLossTxnCheckerRepository extends JpaRepository<VehicleProfitAndLossTxnChecker, Long> {

	@Query("FROM VehicleProfitAndLossTxnChecker where vehicleProfitAndLossTxnCheckerId = ?1")
	public VehicleProfitAndLossTxnChecker getVehicleProfitAndLossTxnChecker(Long vehicleProfitAndLossTxnCheckerId) throws Exception;
	
	@Modifying
	@Transactional
	@Query("UPDATE VehicleProfitAndLossTxnChecker SET isVehicleExpenseAdded = 1 where vehicleProfitAndLossTxnCheckerId = ?1")
	public void updateVehicleExpenseAdded(Long fuelEntryTxnCheckerId) throws Exception;
	
	@Modifying
	@Transactional
	@Query("UPDATE VehicleProfitAndLossTxnChecker SET isDateWiseVehicleExpenseAdded = 1 where vehicleProfitAndLossTxnCheckerId = ?1")
	public void updateDateWiseVehicleExpenseAdded(Long fuelEntryTxnCheckerId) throws Exception;
	
	@Modifying
	@Transactional
	@Query("UPDATE VehicleProfitAndLossTxnChecker SET isMonthWiseVehicleExpenseAdded = 1 where vehicleProfitAndLossTxnCheckerId = ?1")
	public void updateMonthWiseVehicleExpenseAdded(Long fuelEntryTxnCheckerId) throws Exception;
	
	@Modifying
	@Transactional
	@Query("UPDATE VehicleProfitAndLossTxnChecker SET isDateWiseVehicleBalanceUpdated = 1 where vehicleProfitAndLossTxnCheckerId = ?1")
	public void updateDateWiseVehicleBalanceUpdated(Long fuelEntryTxnCheckerId) throws Exception;
	
	@Modifying
	@Transactional
	@Query("UPDATE VehicleProfitAndLossTxnChecker SET isMonthWiseVehicleBalanceUpdated = 1 where vehicleProfitAndLossTxnCheckerId = ?1")
	public void updateMonthWiseVehicleBalanceUpdated(Long fuelEntryTxnCheckerId) throws Exception;
	
	@Modifying
	@Transactional
	@Query("UPDATE VehicleProfitAndLossTxnChecker SET isDateWiseVehicleBalanceUpdated = 1 where vehicleProfitAndLossTxnCheckerId IN (?1)")
	public void updateDateWiseVehicleBalanceMultiple(String fuelEntryTxnCheckerId) throws Exception;
	
	@Modifying
	@Transactional
	@Query("UPDATE VehicleProfitAndLossTxnChecker SET isMonthWiseVehicleBalanceUpdated = 1 where vehicleProfitAndLossTxnCheckerId IN (?1)")
	public void updateMonthWiseVehicleBalanceUpdated(String fuelEntryTxnCheckerId) throws Exception;
	
	@Query("FROM VehicleProfitAndLossTxnChecker where isVehicleExpenseAdded = 0")
	public List<VehicleProfitAndLossTxnChecker> getAllPendingTxnCheckerListForVehicleExpenseAdded() throws Exception;
	
	
	@Query("FROM VehicleProfitAndLossTxnChecker where isDateWiseVehicleExpenseAdded = 0")
	public List<VehicleProfitAndLossTxnChecker> getAllPendingTxnCheckerListForDateWiseVehicleExpenseAdded() throws Exception;
	
	@Query("FROM VehicleProfitAndLossTxnChecker where isMonthWiseVehicleExpenseAdded = 0")
	public List<VehicleProfitAndLossTxnChecker> getAllPendingTxnCheckerListForMonthWiseVehicleExpenseAdded() throws Exception;
	
	
	@Query("FROM VehicleProfitAndLossTxnChecker where isDateWiseVehicleBalanceUpdated = 0")
	public List<VehicleProfitAndLossTxnChecker> getAllPendingTxnCheckerListForDateWiseVehicleBalanceAdded() throws Exception;
	
	
	@Query("FROM VehicleProfitAndLossTxnChecker where isMonthWiseVehicleBalanceUpdated = 0")
	public List<VehicleProfitAndLossTxnChecker> getAllPendingTxnCheckerListForMOnthWiseVehicleBalanceAdded() throws Exception;
	
	@Modifying
	@Transactional
	@Query("DELETE FROM VehicleProfitAndLossTxnChecker where isFinallyEntered = 1")
	public void deleteVehicleExpenseTxnChecker() throws Exception;
	
	
}
