package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VendorApprovalSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VendorApprovalSequenceRepository extends JpaRepository<VendorApprovalSequenceCounter, Long>{
	
	@Query("FROM VendorApprovalSequenceCounter AS VASC WHERE VASC.companyId = ?1 AND VASC.markForDelete = 0")
	public VendorApprovalSequenceCounter findNextSequenceNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE VendorApprovalSequenceCounter  VASC SET VASC.nextVal = ?1 WHERE VASC.sequence_Id = ?2")
	public void updateNextSequenceNumber(long nextVal, long sequece_id)throws Exception;

}
