package org.fleetopgroup.persistence.dao;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.model.VehicleAgentIncomeExpenseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleAgentIncomeExpenseDetailsRepository extends JpaRepository<VehicleAgentIncomeExpenseDetails, Long> {

	@Query("FROM VehicleAgentIncomeExpenseDetails where transactionId = ?1 and txnTypeId = ?2 AND identifier = ?3 and markForDelete = 0")
	public VehicleAgentIncomeExpenseDetails getVehicleAgentIncomeExpenseDetails(Long txnId, short txnTypeId, short identifier) throws Exception;
	
	@Modifying
	@Transactional
	@Query("UPDATE VehicleAgentIncomeExpenseDetails SET markForDelete = 1 where transactionId = ?1 and txnTypeId = ?2 AND identifier = ?3 and markForDelete = 0")
	public void deleteIncomeExpenseDetails(Long txnId, short txnTypeId, short identifier)throws Exception;
	
	
}
