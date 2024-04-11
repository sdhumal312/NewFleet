package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.UreaEntriesSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UreaEntriesSequenceRepository extends JpaRepository<UreaEntriesSequenceCounter, Long>{


	@Query("From UreaEntriesSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public UreaEntriesSequenceCounter findNextInvoiceNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE UreaEntriesSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?2")
	public void updateNextInvoiceNumber(long nextVal, long sequence_Id) throws Exception;
	



}
