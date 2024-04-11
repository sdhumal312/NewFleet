package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.BatteryInvoiceSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BatteryInvoiceSequenceRepository extends JpaRepository<BatteryInvoiceSequenceCounter, Long>{

	@Query("From BatteryInvoiceSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public BatteryInvoiceSequenceCounter findNextInvoiceNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE BatteryInvoiceSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?2")
	public void updateNextInvoiceNumber(long nextVal, long sequence_Id) throws Exception;
	
}
