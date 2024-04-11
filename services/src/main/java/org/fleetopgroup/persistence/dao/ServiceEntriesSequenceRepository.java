package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.ServiceEntriesSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ServiceEntriesSequenceRepository extends JpaRepository<ServiceEntriesSequenceCounter, Long>{
	
	@Query("From ServiceEntriesSequenceCounter RRSC where RRSC.companyId = ?1 ")
	public ServiceEntriesSequenceCounter findNextServiceEntries_Number(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE ServiceEntriesSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.companyId = ?2")
	public void updateNextServiceEntries_Number(long nextVal, Integer companyId) throws Exception;
	
}
