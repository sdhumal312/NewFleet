package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.MasterPartRateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MasterPartRateHistoryRepository extends JpaRepository<MasterPartRateHistory, Long>{

	@Query("FROM MasterPartRateHistory where partId = ?1 and companyId = ?2 and markForDelete = 0 order by partRateHistoryId desc")
	public List<MasterPartRateHistory> getMasterPartRateHistoryByPartId(Long partId, Integer companyId) throws Exception;
}
