package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.DealerServiceEntriesSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DealerServiceEntriesSequenceCounterRepository extends JpaRepository<DealerServiceEntriesSequenceCounter, Long>{
	
	@Query("From DealerServiceEntriesSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public DealerServiceEntriesSequenceCounter findNextDSENumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE DealerServiceEntriesSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?3 AND RRSC.companyId = ?2")
	public void updateNextDSENumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;
	
}
