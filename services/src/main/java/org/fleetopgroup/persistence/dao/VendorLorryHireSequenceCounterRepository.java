package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VendorLorryHireSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VendorLorryHireSequenceCounterRepository extends JpaRepository<VendorLorryHireSequenceCounter, Long>{

	@Query("From VendorLorryHireSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public VendorLorryHireSequenceCounter findNextNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE VendorLorryHireSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?2")
	public void updateNextNumber(long nextVal, long sequence_Id) throws Exception;
}
