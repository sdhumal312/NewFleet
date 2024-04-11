package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VendorPaymentSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VendorPaymentSequenceRepository extends JpaRepository<VendorPaymentSequenceCounter, Long> {
	
	@Query("From VendorPaymentSequenceCounter VPSC where VPSC.companyId = ?1 AND VPSC.markForDelete = 0 ")
	public VendorPaymentSequenceCounter findNextVendorPaymentNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE VendorPaymentSequenceCounter VPSC SET VPSC.nextVal = ?1  where VPSC.sequence_Id = ?3 AND VPSC.companyId = ?2")
	public void updateNextVendorPaymentNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;
	
}
