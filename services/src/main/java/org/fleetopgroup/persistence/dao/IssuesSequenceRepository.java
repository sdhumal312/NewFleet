package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.IssuesSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IssuesSequenceRepository extends JpaRepository<IssuesSequenceCounter, Long>{
	
	@Query("From IssuesSequenceCounter RRSC where RRSC.companyId = ?1 ")
	public IssuesSequenceCounter findNextIssuesSequence_Number(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE IssuesSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.companyId = ?2")
	public void updateNextIssuesSequence_Number(long nextVal, Integer companyId) throws Exception;
}
