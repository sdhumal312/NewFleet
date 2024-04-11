package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.RefreshmentSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RefreshmentSequenceCounterRepository extends JpaRepository<RefreshmentSequenceCounter, Long> {

	@Query("From RefreshmentSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public RefreshmentSequenceCounter findNextNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE RefreshmentSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?2")
	public void updateNextNumber(long nextVal, long sequence_Id) throws Exception;

}
