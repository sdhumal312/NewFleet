package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;

import org.fleetopgroup.persistence.model.MonthWiseVehicleExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MonthWiseVehicleExpenseRepository extends JpaRepository<MonthWiseVehicleExpense, Long> {

	@Query("FROM MonthWiseVehicleExpense where vid = ?1 AND startDateOfMonth = ?2 AND companyId = ?3 AND expenseType = ?4 AND markForDelete = 0")
	public MonthWiseVehicleExpense validateMonthWiseVehicleExpense(Integer vid, Timestamp	startDate, Integer companyId, short expenseType) throws Exception;
	
	@Query("FROM MonthWiseVehicleExpense where vid = ?1 AND startDateOfMonth = ?2 AND companyId = ?3 AND expenseType = ?4 AND markForDelete = 0")
	public MonthWiseVehicleExpense getMonthWiseVehicleExpense(Integer vid, Timestamp	startDate, Integer companyId, short expenseType) throws Exception;
	
	@Query("FROM MonthWiseVehicleExpense where vid = ?1 AND startDateOfMonth = ?2 AND companyId = ?3 AND expenseId = ?4 AND markForDelete = 0")
	public MonthWiseVehicleExpense validateMonthWiseVehicleExpense(Integer vid, Timestamp	startDate, Integer companyId, Integer expenseId) throws Exception;
	
	@Query("FROM MonthWiseVehicleExpense where vid = ?1 AND startDateOfMonth = ?2 AND companyId = ?3 AND expenseId = ?4 AND markForDelete = 0")
	public MonthWiseVehicleExpense getMonthWiseVehicleExpense(Integer vid, Timestamp	startDate, Integer companyId, Integer expenseId) throws Exception;
	
	@Query("FROM MonthWiseVehicleExpense where vid = ?1 AND companyId = ?2 AND expenseId = ?3 AND markForDelete = 0")
	public MonthWiseVehicleExpense getMonthWiseVehicleExpenseByExpenseIdAndVid(Integer vid,Integer companyId, Integer expenseId) throws Exception;
	
	@Modifying
	@Query("UPDATE MonthWiseVehicleExpense SET expenseAmount = ?4 where vid = ?1 AND companyId = ?2 AND expenseId = ?3 AND markForDelete = 0")
	public void updateExpenseAmount(Integer vid,Integer companyId, Integer expenseId,double expenseAmount) throws Exception;
	
	@Query("FROM MonthWiseVehicleExpense where vid = ?1 AND companyId = ?2 AND expenseId = ?3 AND expenseType = ?4 AND txnTypeId = ?5 AND markForDelete = 0")
	public MonthWiseVehicleExpense getMonthWiseVehicleExpenseOfTripExpense(Integer vid,Integer companyId, Integer expenseId,short expenseType,long tripsheetId) throws Exception;
	
	@Modifying
	@Query("UPDATE MonthWiseVehicleExpense SET expenseAmount = ?6 where vid = ?1 AND companyId = ?2 AND expenseId = ?3 AND expenseType = ?4 AND txnTypeId = ?5 AND markForDelete = 0")
	public void updateExpenseAmountOfTripExpense(Integer vid,Integer companyId, Integer expenseId,short expenseType,long tripsheetId, double expenseAmount) throws Exception;

}
