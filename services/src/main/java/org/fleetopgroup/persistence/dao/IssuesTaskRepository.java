/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.IssuesTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface IssuesTaskRepository extends JpaRepository<IssuesTasks, Long> {

	
	/*@Query("select count(p) from Issues as p ") 
	public Long countTotalIssues() throws Exception;
	
	@Query("select count(p) from Issues as p where p.ISSUES_STATUS =?1 ") 
	public Long countStatus(String Status) throws Exception;*/
	
	@Query("from IssuesTasks as p where p.ISSUES_TASK_ID =?1 ") 
	public IssuesTasks get_Issues_VEHICLEID_TO_GET_IssuesTask_Details(Long Issues_VEHICLEID) throws Exception;
	
	
	@Query("from IssuesTasks as p where p.ISSUES.ISSUES_ID =?1 AND p.COMPANY_ID = ?2 AND p.markForDelete = 0 ORDER BY p.ISSUES_TASK_ID DESC") 
	public List<IssuesTasks> getIssuesTasksList(Long ISSUES_ID, Integer companyId) throws Exception;
}
