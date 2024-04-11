package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.InventoryRequisitionSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRequisitionSequenceRepository extends JpaRepository<InventoryRequisitionSequenceCounter, Long>{
	
	@Query("From InventoryRequisitionSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public InventoryRequisitionSequenceCounter findNextInventoryRequisitionNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE InventoryRequisitionSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?2")
	public void updateNextInventoryRequisitionNumber(long nextVal, long sequence_Id) throws Exception;
	
}
