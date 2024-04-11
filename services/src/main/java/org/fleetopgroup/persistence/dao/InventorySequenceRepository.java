package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.InventorySequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InventorySequenceRepository extends JpaRepository<InventorySequenceCounter, Long>{
	
	@Query("From InventorySequenceCounter RRSC where RRSC.companyId = ?1 AND  RRSC.type = ?2 AND RRSC.markForDelete = 0 ")
	public InventorySequenceCounter findNextInventoryNumber(Integer companyId, short type) throws Exception;
	
	@Modifying
	@Query("UPDATE InventorySequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?3 AND RRSC.companyId = ?2 AND  RRSC.type = ?4")
	public void updateNextInventoryNumber(long nextVal, Integer companyId, long sequence_Id, short type) throws Exception;
	
}
