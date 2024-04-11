package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.RenewalApprovalSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RenewalApprovalSequenceRepository extends JpaRepository<RenewalApprovalSequenceCounter, Long> {

	@Query("From RenewalApprovalSequenceCounter RRSC where RRSC.companyId = ?1 ")
	public RenewalApprovalSequenceCounter findNextRenewalApproval_Number(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE RenewalApprovalSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.companyId = ?2")
	public void updateNextRenewalApproval_Number(long nextVal, Integer companyId) throws Exception;
}
