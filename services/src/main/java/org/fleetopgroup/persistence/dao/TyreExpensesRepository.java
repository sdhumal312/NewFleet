/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.TyreExpenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface TyreExpensesRepository extends JpaRepository<TyreExpenses, Integer> {

	@Query("From TyreExpenses TE where TE.companyId = ?1 and TE.markForDelete = 0 ")
	List<TyreExpenses> findAllByCompanyId(Integer companyId);
	
	@Query("From TyreExpenses TE where TE.markForDelete = 0 ")
	List<TyreExpenses> findAll();

	@Query("From TyreExpenses TE where TE.tyreExpenseName =?1 AND TE.companyId = ?2 AND TE.markForDelete = 0 ")
	public TyreExpenses findByName(String tyreExpenseName, Integer companyId);
	
	@Query("From TyreExpenses TE where TE.tyreExpenseId = ?1 AND TE.companyId = ?2 AND TE.markForDelete = 0 ")
	public TyreExpenses findByTyreExpenseId(Integer tyreExpenseId , Integer companyId);

	@Modifying
	@Query("UPDATE TyreExpenses TE SET TE.tyreExpenseName =?2, TE.description =?3 where TE.tyreExpenseId = ?1 AND TE.companyId = ?4 AND TE.markForDelete = 0 ")
	public void updateTyreExpenseByExpenseId(Integer TyreExpense_ID, String TyreExpense_Name, String description, Integer company_id);
	
	@Modifying
	@Query("UPDATE TyreExpenses TE SET TE.markForDelete = 1  where TE.tyreExpenseId = ?1 AND TE.companyId = ?2 ")
	public void deleteTyreExpenseByExpenseId(Integer TyreExpense_ID,  Integer company_id);
}
