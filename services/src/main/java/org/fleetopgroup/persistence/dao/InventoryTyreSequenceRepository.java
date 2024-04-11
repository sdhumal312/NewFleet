package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.InventoryTyreSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InventoryTyreSequenceRepository extends JpaRepository<InventoryTyreSequenceCounter, Long>{

	
	@Query("From InventoryTyreSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public InventoryTyreSequenceCounter findNextTyreNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE InventoryTyreSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?3 AND RRSC.companyId = ?2")
	public void updateNextTyreNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;
	

}
