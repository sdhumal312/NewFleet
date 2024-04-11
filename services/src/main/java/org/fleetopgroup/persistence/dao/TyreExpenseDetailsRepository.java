/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.TyreExpenseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface TyreExpenseDetailsRepository extends JpaRepository<TyreExpenseDetails, Long> {

	@Modifying
	@Query("UPDATE TyreExpenseDetails SET markForDelete = 1 WHERE tyreId=?1 AND companyId = ?2")
	public void deleteTyreExpensedetails(Long tyre_id, Integer companyId);

	@Query("From TyreExpenseDetails TE where TE.companyId = ?1 and TE.markForDelete = 0 ")
	List<TyreExpenseDetails> findAllByCompanyId(Integer companyId);
	
	@Query("From TyreExpenseDetails TE where TE.tyreId = ?1 AND TE.tyreTypeId = ?2 AND TE.companyId = ?3 AND TE.markForDelete = 0 ")
	public List<TyreExpenseDetails> findByTyreTypeId(Long tyreId , short tyreTypeId , Integer companyId);
	
	@Query("From TyreExpenseDetails TE where TE.tyreExpenseDetailsId = ?1 AND TE.companyId = ?2 AND TE.markForDelete = 0 ")
	public TyreExpenseDetails findByTyreExpensDetailsId(Integer tyreExpenseId , Integer companyId);
	
	@Modifying
	@Query("UPDATE TyreExpenseDetails TE SET TE.tyreExpenseId =?1, TE.tyreExpenseAmount =?2 where TE.tyreExpenseDetailsId = ?3 AND TE.companyId = ?4 AND TE.markForDelete = 0 ")
	public void updateTyreExpenseDetailsByExpenseDetailsId(Integer tyreExpenseId, Double tyreExpenseAmount, Long tyreExpenseDetailsId, Integer company_id);
	
	@Modifying
	@Query("UPDATE TyreExpenseDetails TE SET TE.markForDelete = 1  where TE.tyreExpenseDetailsId = ?1 AND TE.companyId = ?2 ")
	public void deleteTyreExpenseByExpenseDetailsId(Long tyreExpenseDetailsId,  Integer company_id);

	@Modifying
	@Query("UPDATE TyreExpenseDetails TE SET TE.markForDelete = 1  where TE.tyreId = ?1  AND TE.tyreTypeId =?2 AND TE.companyId = ?3 ")
	public void deleteRetreadTyreExpenseByTyreIdAndInvoiceNumber(Long tyreId, short tyreType, Integer company_id);
	
	@Modifying
	@Query("UPDATE TyreExpenseDetails TE SET TE.totalTyreExpenseAmount = ?1, TE.lastUpdatedBy = ?2, TE.lastUpdatedOn = ?3 where  TE.tyreId =?4 AND TE.companyId = ?5 ")
	public void updateRetreadTyreExpenseDetails(Double retread_COST, long lastUpdatedBy, Timestamp lastupdated, Long tyre_ID, Integer company_id);

	@Modifying
	@Query("UPDATE TyreExpenseDetails TE SET tyreExpenseDetailsDocumentId = ?1, tyreExpenseDetailsDocument = ?2 where tyreExpenseDetailsId= ?3 AND companyId = ?4")
	public void updateTyreExpenseDetailsDocumentId(Long tyreExpenseDetailsDocId, boolean tyreExpenseDetailsDocument, Long tyreExpenseDetailsId, Integer companyId);

	
}
