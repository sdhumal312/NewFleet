package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VehicleAccidentSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleAccidentSequenceCounterRepository extends JpaRepository<VehicleAccidentSequenceCounter, Long>{

	@Query("From VehicleAccidentSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public VehicleAccidentSequenceCounter findNextNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE VehicleAccidentSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?2")
	public void updateNextNumber(long nextVal, long sequence_Id) throws Exception;
}
