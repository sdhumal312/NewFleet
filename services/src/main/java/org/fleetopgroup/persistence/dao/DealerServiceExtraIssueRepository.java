package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.DealerServiceExtraIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DealerServiceExtraIssueRepository  extends JpaRepository<DealerServiceExtraIssue, Long>{

	@Query("FROM DealerServiceExtraIssue WHERE companyId =?1 AND markForDelete=0 ")
	List<DealerServiceExtraIssue> getAllDealerServiceExtraIssue(Integer companyId);

	@Query("FROM DealerServiceExtraIssue WHERE dealerServiceEntriesId =?1 AND companyId =?2 AND markForDelete=0 ")
	public List<DealerServiceExtraIssue> getDealerServiceExtraIssue(long dealerServiceEntriesId, int companyId);
	
}
