package org.fleetopgroup.persistence.dao;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.model.VehicleAgentTxnChecker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleAgentTxnCheckerRepository extends JpaRepository<VehicleAgentTxnChecker, Long>{

	@Query("FROM VehicleAgentTxnChecker where vehicleAgentTxnCheckerId = ?1")
	public VehicleAgentTxnChecker getVehicleAgentTxnChecker(Long vehicleAgentTxnCheckerId) throws Exception;
	
	@Modifying
	@Transactional
	@Query("UPDATE VehicleAgentTxnChecker SET isIncomeExpenseAdded = 1 where vehicleAgentTxnCheckerId = ?1")
	public void updateVehicleAgentTxnChecker(Long vehicleAgentTxnCheckerId)throws Exception;
}
