package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.ClothInvoiceSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ClothInvoiceSequenceRepository extends JpaRepository<ClothInvoiceSequenceCounter, Long>{



	@Query("From ClothInvoiceSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public ClothInvoiceSequenceCounter findNextInvoiceNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE ClothInvoiceSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?2")
	public void updateNextInvoiceNumber(long nextVal, long sequence_Id) throws Exception;
	

}
