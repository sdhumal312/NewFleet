package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.WorkOrderSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WorkOrderSequenceRepository extends JpaRepository<WorkOrderSequenceCounter, Long>{
	
	@Query("From WorkOrderSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0 ")
	public WorkOrderSequenceCounter findNextWorkOrderNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE WorkOrderSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.sequence_Id = ?2")
	public void updateNextWorkOrderNumber(long nextVal, long sequence_Id) throws Exception;
	
}
