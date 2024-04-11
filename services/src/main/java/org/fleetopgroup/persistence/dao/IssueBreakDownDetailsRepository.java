package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.IssueBreakDownDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IssueBreakDownDetailsRepository extends JpaRepository<IssueBreakDownDetails, Long> {
	
	@Query("FROM IssueBreakDownDetails WHERE issueId = ?1 AND companyId =?2 AND markForDelete = 0 ")
	public IssueBreakDownDetails getIssueBreakDownDetails(Long issueId, Integer companyId);

}
