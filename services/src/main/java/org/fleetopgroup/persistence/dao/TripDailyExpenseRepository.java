/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.TripDailyExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface TripDailyExpenseRepository extends JpaRepository<TripDailyExpense, Long> {

	@Query("FROM TripDailyExpense WHERE markForDelete=0 AND TRIPDAILYID =?1 ")
	public List<TripDailyExpense> findAll_TripDailySheet_ID_Expense(Long tripdailyid);

	@Query("FROM TripDailyExpense WHERE markForDelete=0 AND expenseId=?1 AND TRIPDAILYID =?2 AND companyId = ?3")
	public List<TripDailyExpense> Validate_TripDaily_Expense(Integer string, Long tripdailyid, Integer companyId);

	@Query("FROM TripDailyExpense WHERE markForDelete=0 AND TRIPDAILYID =?1 ")
	public List<TripDailyExpense> findAll_TripDaily_Expense(Long tripdailyid);

	@Query("FROM TripDailyExpense WHERE markForDelete=0 AND tripExpenseID =?1 AND companyId = ?2")
	public TripDailyExpense Get_TripDaily_Expense(Long tripExpenseID, Integer companyId);
	
	@Query("FROM TripDailyExpense WHERE markForDelete=0 AND expenseId =?1 AND companyId = ?2")
	public TripDailyExpense Get_TripDaily_Expense(Integer tripExpenseID, Integer companyId);


	@Modifying
	@Query("UPDATE TripDailyExpense SET markForDelete=1 WHERE tripExpenseID =?1 AND companyId = ?2")
	public void delete_TripDailyExpense(Long tripExpenseID, Integer companyId);

	@Modifying
	@Query("UPDATE TripDailyExpense SET markForDelete=1 WHERE TRIPDAILYID =?1 AND companyId = ?2")
	public void delete_TripDailyExpense_TRIPDAILYID(Long tripCollectionID, Integer companyId);
	
	
	@Modifying
	@Query("UPDATE TripDailyExpense SET markForDelete=1 WHERE fuel_id =?1 AND companyId = ?2")
	public void Delete_Trip_Daily_Expense_Fuel_ID_Value_Amount(Long fuel_id, Integer companyId);
	
	@Query("FROM TripDailyExpense WHERE markForDelete= 0 AND TRIPDAILYID =?1 AND expenseId=?2 ")
	public TripDailyExpense getExpenseByTripSheetIdAndExpenseId(Long tripId, Integer expenseId) throws Exception;
}
