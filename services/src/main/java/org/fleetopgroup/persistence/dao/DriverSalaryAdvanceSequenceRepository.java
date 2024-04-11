package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.DriverSalaryAdvanceSequenceCounter;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DriverSalaryAdvanceSequenceRepository  extends CrudRepository<DriverSalaryAdvanceSequenceCounter, Long>{

	
	@Query("FROM DriverSalaryAdvanceSequenceCounter CBSC where companyId = ?1 AND markForDelete = 0")
	public DriverSalaryAdvanceSequenceCounter findNextSequenceNumber(Integer companyId)throws Exception;
	
	@Modifying
	@Query("UPDATE DriverSalaryAdvanceSequenceCounter SET nextVal = ?1 where sequence_Id = ?2")
	public void updateNextSequenceCounter(long nextVal , long sequence_id)throws Exception;
	
}
