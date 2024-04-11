package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.SentLaundryInvoiceSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SentLaundryInvoiceSequenceCounterRepository extends JpaRepository<SentLaundryInvoiceSequenceCounter, Long>{

	@Query("From SentLaundryInvoiceSequenceCounter RRSC where RRSC.companyId = ?1 ")
	public SentLaundryInvoiceSequenceCounter findNextInvoiceNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE SentLaundryInvoiceSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.companyId = ?2")
	public void updateNextInvoiceNumber	(long nextVal, Integer companyId) throws Exception;

}
