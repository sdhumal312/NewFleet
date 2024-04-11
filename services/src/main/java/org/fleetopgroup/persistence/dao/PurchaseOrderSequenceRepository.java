package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.PurchaseOrdersSequeceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseOrderSequenceRepository extends JpaRepository<PurchaseOrdersSequeceCounter, Long>{
	
	@Query("From PurchaseOrdersSequeceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public PurchaseOrdersSequeceCounter findNextPurchaseOrderNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE PurchaseOrdersSequeceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?2")
	public void updateNextPurchaseOrderNumber(long nextVal, long sequence_Id) throws Exception;
	
}
