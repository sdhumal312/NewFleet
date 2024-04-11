package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.LorryHirePaymentSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LorryHirePaymentSequenceRepository extends JpaRepository<LorryHirePaymentSequenceCounter, Long>{

	@Query("From LorryHirePaymentSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public LorryHirePaymentSequenceCounter findNextNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE LorryHirePaymentSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?2")
	public void updateNextNumber(long nextVal, long sequence_Id) throws Exception;
}
