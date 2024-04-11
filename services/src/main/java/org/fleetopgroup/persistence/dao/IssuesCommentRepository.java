/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.IssuesComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface IssuesCommentRepository extends JpaRepository<IssuesComment, Long> {

	@Query("From IssuesComment as C Where C.ISSUES_ID = ?1 AND C.markForDelete = 0")
	public List<IssuesComment> Get_Issues_ID_Comment_Details(Long issue_ID) throws Exception;

	@Modifying
	@Query("UPDATE IssuesComment T SET T.markForDelete = 1 where T.ISSUE_COMMENTID = ?1 AND T.COMPANY_ID = ?2")
	public void Delete_Issues_Comment_Details(Long issueComment_ID, Integer companyId) throws Exception;

}
