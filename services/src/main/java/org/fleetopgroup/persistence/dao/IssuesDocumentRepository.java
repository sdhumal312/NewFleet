/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.IssuesDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface IssuesDocumentRepository extends JpaRepository<IssuesDocument, Long> {

	/*@Query("From IssuesPhoto as C Where C.ISSUE_ID = ?1")
	public List<IssuesPhoto> Get_Issues_ID_Photo_Details(Long issue_ID)  throws Exception;
*/
	@Modifying
	@Query("UPDATE IssuesDocument T SET T.markForDelete = 1 where T.ISSUE_DOCUMENTID = ?1 AND T.COMPANY_ID = ?2")
	public void Delete_Issues_Document_Details(Long issueComment_ID, Integer companyId)  throws Exception;

	@Query("From IssuesDocument as C Where C.ISSUE_DOCUMENTID = ?1 AND C.COMPANY_ID = ?2 AND C.markForDelete = 0")
	public IssuesDocument Get_Issues_Document(Long issueComment_ID, Integer companyId) throws Exception;
	
	@Query("SELECT MAX(ISSUE_DOCUMENTID) FROM IssuesDocument")
	public long getIssuesDocumentMaxId() throws Exception;
	
	@Query("FROM IssuesDocument where ISSUE_DOCUMENTID > ?1 AND ISSUE_DOCUMENTID <= ?2")
	public List<IssuesDocument> getIssuesDocumentList(Long startLimit, Long endLimit) throws Exception;

}
