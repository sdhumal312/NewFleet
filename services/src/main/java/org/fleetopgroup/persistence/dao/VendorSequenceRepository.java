package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VendorSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VendorSequenceRepository extends JpaRepository<VendorSequenceCounter, Long> {
	
	@Query("From VendorSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public VendorSequenceCounter findNextVendorNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE VendorSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?3 AND RRSC.companyId = ?2")
	public void updateNextVendorNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;
	
}
