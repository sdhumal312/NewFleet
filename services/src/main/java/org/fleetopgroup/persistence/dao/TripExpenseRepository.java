package org.fleetopgroup.persistence.dao;

import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.TripExpense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TripExpenseRepository extends JpaRepository<TripExpense, Integer> {

	@Modifying
	@Query("update TripExpense T SET T.expenseName = ?1, T.expenseRemarks = ?2, T.lastModifiedBy = ?3,  T.lastupdated = ?4 , T.incldriverbalance =?7  where T.expenseID = ?5 and T.companyId = ?6")
	public void updateTripExpense(String expenseName, String expenseRemarks, String lastModifiedBy, Date lastupdated,
			Integer incomeID, Integer companyId, boolean includIndriverBalance) throws Exception;

	public List<TripExpense> findAll();

	@Query("From TripExpense p where p.expenseID = ?1 and p.companyId = ?2 AND p.markForDelete = 0")
	public TripExpense getTripExpense(int ExpenseID, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE TripExpense T SET T.markForDelete = 1 where T.expenseID = ?1 and T.companyId = ?2")
	public void deleteTripExpense(Integer status, Integer companyId) throws Exception;

	@Query("From TripExpense p where p.expenseName = ?1 and p.companyId = ?2 and p.markForDelete = 0")
	public List<TripExpense> ValidateTripExpense(String ExpenseNAME, Integer companyId) throws Exception;

	@Query("From TripExpense p where p.companyId = ?1 and p.markForDelete = 0")
	public List<TripExpense> listTripExpense(Integer companyId) throws Exception;

	@Query("FROM TripExpense TR  where TR.companyId= ?1 AND TR.markForDelete = 0")
	public Page<TripExpense> getDeployment_Page_TripExpense(Integer company_id, Pageable pageable);
}