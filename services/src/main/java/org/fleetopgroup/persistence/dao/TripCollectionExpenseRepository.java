package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.TripCollectionExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TripCollectionExpenseRepository extends JpaRepository<TripCollectionExpense, Long> {

	@Query("FROM TripCollectionExpense WHERE TRIPCOLLID = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<TripCollectionExpense> findAll_TripCollection_Expense(Long TRIPCOLLID, Integer companyId) throws Exception;

	@Query("FROM TripCollectionExpense  WHERE expenseId=?1 AND TRIPCOLLID = ?2 AND companyId = ?3 AND markForDelete = 0")
	public List<TripCollectionExpense> Validate_TripCollection_Expense(Integer expenceName, Long TRIPCOLLID, Integer companyId)
			throws Exception;

	@Query("FROM TripCollectionExpense AS T WHERE T.tripExpenseID=?1 AND T.companyId = ?2 AND T.markForDelete =0")
	public TripCollectionExpense Get_TripCollection_Expense(Long TripSheet_Expenseid, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE TripCollectionExpense AS T SET T.markForDelete = 1 WHERE T.tripExpenseID=?1 AND T.companyId = ?2")
	public void delete_TripCollectionExpense(Long TripExpenseid, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE TripCollectionExpense SET markForDelete = 1 WHERE TRIPCOLLID=?1 AND companyId = ?2")
	public void delete_TripCollectionExpense_collectionID(Long tripCollectionID, Integer companyId) throws Exception;
}
