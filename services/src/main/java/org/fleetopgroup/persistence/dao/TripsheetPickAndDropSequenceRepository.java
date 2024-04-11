package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TripsheetPickAndDropSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TripsheetPickAndDropSequenceRepository extends JpaRepository<TripsheetPickAndDropSequenceCounter, Long>{


	@Query("From TripsheetPickAndDropSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public TripsheetPickAndDropSequenceCounter findNextInvoiceNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE TripsheetPickAndDropSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?2")
	public void updateNextInvoiceNumber(long nextVal, long sequence_Id) throws Exception;
	

}
