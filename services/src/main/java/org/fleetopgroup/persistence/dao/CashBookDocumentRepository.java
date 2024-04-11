/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.CashBookDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface CashBookDocumentRepository extends JpaRepository<CashBookDocument, Long> {

	@Query("FROM CashBookDocument WHERE CASHDOCID = ?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	public CashBookDocument get_CASH_BOOK_Document_Details_CASHID(Long CASHDOCID, Integer companyId);
	
	@Query("SELECT MAX(CASHDOCID) FROM CashBookDocument")
	public long getCashBookDocumentMaxId() throws Exception;
	
	@Query("FROM CashBookDocument where CASHDOCID > ?1 AND CASHDOCID <= ?2")
	public List<CashBookDocument> getCashBookDocumentList(Integer startLimit, Integer endLimit) throws Exception;
}
