/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.IssueAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface IssueAnalysisRepository extends JpaRepository<IssueAnalysis, Long> {


@Query("FROM IssueAnalysis WHERE issueAnalysisId =?1 AND companyId =?2 AND markForDelete =0")
public IssueAnalysis getIssueAnalysisById(Long issueAnalysisId ,Integer companyId); 

	
}
