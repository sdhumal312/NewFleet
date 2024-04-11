package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.CashBookSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CashBookSequenceRepository extends JpaRepository<CashBookSequenceCounter, Long>{
	
	@Query("FROM CashBookSequenceCounter CBSC where companyId = ?1 AND markForDelete = 0")
	public CashBookSequenceCounter findNextSequenceNumber(Integer companyId)throws Exception;
	
	@Modifying
	@Query("UPDATE CashBookSequenceCounter SET nextVal = ?1 where sequence_Id = ?2")
	public void updateNextSequenceCounter(long nextVal , long sequence_id)throws Exception;
}
