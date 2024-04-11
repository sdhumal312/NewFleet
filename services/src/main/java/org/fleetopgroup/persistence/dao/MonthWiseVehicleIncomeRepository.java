package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;

import org.fleetopgroup.persistence.model.MonthWiseVehicleIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MonthWiseVehicleIncomeRepository extends JpaRepository<MonthWiseVehicleIncome, Long>{

	@Query("FROM MonthWiseVehicleIncome where vid = ?1 AND startDateOfMonth = ?2 AND companyId = ?3 AND incomeType = ?4 AND markForDelete = 0")
	public MonthWiseVehicleIncome validateMonthWiseVehicleIncome(Integer vid, Timestamp	startDate, Integer companyId, short incomeType) throws Exception;
	
	@Query("FROM MonthWiseVehicleIncome where vid = ?1 AND startDateOfMonth = ?2 AND companyId = ?3 AND incomeId = ?4 AND incomeType = ?5 AND markForDelete = 0")
	public MonthWiseVehicleIncome validateMonthWiseVehicleIncome(Integer vid, Timestamp	startDate, Integer companyId, Integer incomeId, short type) throws Exception;
	
	/*@Query("UPDATE MonthWiseVehicleIncome SET markForDelete = 1 where vid = ?1 AND startDateOfMonth = ?2 AND companyId = ?3 AND incomeId = ?4 AND markForDelete = 0")
	public void deleteMonthWiseVehicleIncome(Integer vid, Timestamp	startDate, Integer companyId, Integer incomeId, Double incomeAmount) throws Exception;*/
	
}
