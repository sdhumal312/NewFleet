package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleExpenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleExpensesRepository extends JpaRepository<VehicleExpenses, Long>{

	@Query("from VehicleExpenses where accidentId = ?1 and markForDelete = 0")
	public List<VehicleExpenses>  findByAccidentId(Long accidentId) throws Exception;
	
	@Modifying
	@Query("UPDATE VehicleExpenses SET markForDelete = 1 where vehicleExpensesId = ?1")
	public void removeExpense(Long advanceId);
	
	@Query("select COUNT(vehicleExpensesId) from  VehicleExpenses where accidentId = ?1 AND markForDelete = 0")
	public long getExpenseCountByAccidentId(Long accidentId) throws Exception;
}
