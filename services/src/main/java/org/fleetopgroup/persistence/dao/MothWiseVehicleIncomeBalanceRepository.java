package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;

import org.fleetopgroup.persistence.model.MothWiseVehicleIncomeBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MothWiseVehicleIncomeBalanceRepository extends JpaRepository<MothWiseVehicleIncomeBalance, Long>{

	@Query("FROM MothWiseVehicleIncomeBalance where vid = ?1 AND startDateOfMonth = ?2 AND markForDelete = 0")
	public MothWiseVehicleIncomeBalance getMothWiseVehicleIncomeBalance(Integer	vid, Timestamp	startDateOfMonth) throws Exception;
	
	@Query(nativeQuery = true, value = "SELECT * FROM MothWiseVehicleIncomeBalance where vid = ?1 AND startDateOfMonth < ?2 AND markForDelete = 0 ORDER BY mothWiseVehicleIncomeBalanceId DESC LIMIT 1")
	public MothWiseVehicleIncomeBalance getLastMonthWiseVehicleBalance(Integer vid, Timestamp	firstDate) throws Exception;
	
}
