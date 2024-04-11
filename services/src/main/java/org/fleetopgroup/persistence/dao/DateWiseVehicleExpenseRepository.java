package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;

import org.fleetopgroup.persistence.model.DateWiseVehicleExpense;
import org.fleetopgroup.persistence.model.MonthWiseVehicleExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DateWiseVehicleExpenseRepository extends JpaRepository<DateWiseVehicleExpense, Long>{

	@Query("FROM DateWiseVehicleExpense where expenseDate = ?4 AND expenseType = ?1 AND companyId = ?2 AND vid = ?3 AND markForDelete = 0")
	public DateWiseVehicleExpense validateDateWiseVehicleExpense(short expenseType, Integer companyId, Integer vid, Timestamp	expenseDate) throws Exception;
	
	@Query("FROM DateWiseVehicleExpense where expenseDate = ?4 AND expenseId = ?1 AND companyId = ?2 AND vid = ?3 AND markForDelete = 0")
	public DateWiseVehicleExpense validateDateWiseVehicleExpense(Integer expenseId, Integer companyId, Integer vid, Timestamp	expenseDate) throws Exception;
	
	@Query("FROM DateWiseVehicleExpense where vid = ?1 AND companyId = ?2 AND expenseId = ?3 AND expenseType = ?4 AND txnTypeId = ?5 AND markForDelete = 0")
	public DateWiseVehicleExpense getDateWiseVehicleExpenseOfTripExpense(Integer vid,Integer companyId, Integer expenseId,short expenseType,long tripsheetId) throws Exception;
	
	@Modifying
	@Query("UPDATE DateWiseVehicleExpense SET expenseAmount = ?6 where vid = ?1 AND companyId = ?2 AND expenseId = ?3 AND expenseType = ?4 AND txnTypeId = ?5 AND markForDelete = 0")
	public void updateExpenseAmountOfTripExpense(Integer vid,Integer companyId, Integer expenseId,short expenseType,long tripsheetId, double expenseAmount) throws Exception;
}
