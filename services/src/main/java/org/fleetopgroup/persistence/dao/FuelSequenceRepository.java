package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.FuelSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FuelSequenceRepository extends JpaRepository<FuelSequenceCounter, Long>{
	
	@Query("From FuelSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public FuelSequenceCounter findNextFuelNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE FuelSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?3 AND RRSC.companyId = ?2")
	public void updateNextFuelNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;
	
}
