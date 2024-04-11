package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.MasterPartRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MasterPartRateRepository extends JpaRepository<MasterPartRate, Long>{

	@Query("FROM MasterPartRate where partId = ?1 AND companyId =?2 and markForDelete = 0")
	public MasterPartRate  validateMasterPartRate(Long partId, Integer companyId) throws Exception;
	
}
