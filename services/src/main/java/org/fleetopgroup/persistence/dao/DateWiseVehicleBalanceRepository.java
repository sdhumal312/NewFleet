package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;

import org.fleetopgroup.persistence.model.DateWiseVehicleBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DateWiseVehicleBalanceRepository extends JpaRepository<DateWiseVehicleBalance, Long>{


	@Query("FROM DateWiseVehicleBalance where vid = ?1 AND companyId = ?2 AND transactionDate = ?3 AND markForDelete = 0")
	public DateWiseVehicleBalance validateDateWiseVehicleBalance(Integer vid, Integer companyId, Timestamp	expenseDate) throws Exception;
	
	@Query(nativeQuery = true, value = "SELECT * FROM DateWiseVehicleBalance where vid = ?1 AND companyId = ?2 AND transactionDate < ?3 AND markForDelete = 0 ORDER BY dateWiseVehicleBalanceId DESC LIMIT 1")
	public DateWiseVehicleBalance getLastDateVehicleBalance(Integer vid, Integer companyId, Timestamp	expenseDate) throws Exception;
}
