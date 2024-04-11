package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TripCollectionSequenceCounter;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TripSheetCollectionSequenceRepository extends CrudRepository<TripCollectionSequenceCounter, Long>{
	
	@Query("From TripCollectionSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public TripCollectionSequenceCounter findNextSequenceNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE TripCollectionSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?2")
	public void updateNextSequenceNumber(long nextVal, long sequence_Id) throws Exception;
	
}
