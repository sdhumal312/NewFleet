package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.PartInvoiceSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PartInvoiceSequenceRepository extends JpaRepository<PartInvoiceSequenceCounter, Long>{
	
	@Query("From PartInvoiceSequenceCounter PISC where PISC.companyId = ?1 AND  PISC.type = ?2 AND PISC.markForDelete = 0 ")
	public PartInvoiceSequenceCounter findNextInventoryNumber(Integer companyId, short type) throws Exception;
	
	@Modifying
	@Query("UPDATE PartInvoiceSequenceCounter PISC SET PISC.nextVal = ?1  where PISC.sequence_Id = ?3 AND PISC.companyId = ?2 AND  PISC.type = ?4")
	public void updateNextInventoryNumber(long nextVal, Integer companyId, long sequence_Id, short type) throws Exception;
	
}