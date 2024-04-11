/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.IssuesPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface IssuesPhotoRepository extends JpaRepository<IssuesPhoto, Long> {

	@Query("From IssuesPhoto as C Where C.ISSUE_ID = ?1 AND C.COMPANY_ID = ?2 AND C.markForDelete = 0")
	public List<IssuesPhoto> Get_Issues_ID_Photo_Details(Long issue_ID, Integer companyId)  throws Exception;

	@Modifying
	@Query("UPDATE IssuesPhoto T SET T.markForDelete = 1 where T.ISSUE_PHOTOID = ?1 AND T.COMPANY_ID = ?2")
	public void Delete_Issues_Photo_Details(Long issueComment_ID, Integer companyId)  throws Exception;

	@Query("From IssuesPhoto as C Where C.ISSUE_PHOTOID = ?1 AND C.COMPANY_ID = ?2 AND C.markForDelete = 0")
	public IssuesPhoto Get_Issues_Photo(Long issueComment_ID, Integer companyId) throws Exception;
	
	@Query("SELECT MAX(ISSUE_PHOTOID) FROM IssuesPhoto")
	public long getIssuesPhotoMaxId() throws Exception;
	
	@Query("FROM IssuesPhoto where ISSUE_PHOTOID > ?1 AND ISSUE_PHOTOID <= ?2")
	public List<IssuesPhoto> getIssuesPhotoList(Long startLimit, Long endLimit) throws Exception;

}
