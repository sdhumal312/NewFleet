package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TripSheetSequenceCounter;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TripsheetSequenceRepository extends CrudRepository<TripSheetSequenceCounter, Long>{
	
	@Query("From TripSheetSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public TripSheetSequenceCounter findNextSequenceNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE TripSheetSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?2")
	public void updateNextSequenceNumber(long nextVal, long sequence_Id) throws Exception;
	
}
