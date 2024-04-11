package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;

import org.fleetopgroup.persistence.model.MonthWiseVehicleBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MonthWiseVehicleBalanceRepository extends JpaRepository<MonthWiseVehicleBalance, Long> {

	@Query("FROM MonthWiseVehicleBalance where vid = ?1 AND monthStartDate = ?2 AND companyId = ?3 AND markForDelete = 0")
	public MonthWiseVehicleBalance validateMonthWiseVehicleBalance(Integer vid, Timestamp	firstDate, Integer companyId) throws Exception;
	
	//@Query("FROM MonthWiseVehicleBalance where vid = ?1 AND expenseMonth = ?2 AND expenseYear = ?3 AND companyId = ?4 AND markForDelete = 0")
	@Query(nativeQuery = true, value = "SELECT * FROM MonthWiseVehicleBalance where vid = ?1 AND monthStartDate < ?2  AND companyId = ?3 AND markForDelete = 0 ORDER BY monthWiseVehicleBalanceId DESC LIMIT 1")
	public MonthWiseVehicleBalance getLastMonthWiseVehicleBalance(Integer vid, Timestamp	firstDate, Integer companyId) throws Exception;
	
	
}
