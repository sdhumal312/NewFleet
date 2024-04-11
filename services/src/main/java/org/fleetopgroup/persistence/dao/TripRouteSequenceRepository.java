package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TripRouteSequenceCounter;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TripRouteSequenceRepository extends CrudRepository<TripRouteSequenceCounter, Long>{

	
	@Query("From TripRouteSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public TripRouteSequenceCounter findNextSequenceNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE TripRouteSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?2")
	public void updateNextSequenceNumber(Integer nextVal, long sequence_Id) throws Exception;
	

}
