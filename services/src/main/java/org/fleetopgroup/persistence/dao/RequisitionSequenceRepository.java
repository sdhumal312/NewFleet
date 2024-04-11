package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.RequisitionSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RequisitionSequenceRepository extends JpaRepository<RequisitionSequenceCounter, Long> {
	
	@Query("From RequisitionSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public RequisitionSequenceCounter findNextNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE RequisitionSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequenceId = ?3 AND RRSC.companyId = ?2")
	public void updateNextNumber(long nextVal, Integer companyId, long sequenceId) throws Exception;

}
