
package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.DealerServiceEntriesLabour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DealerServiceEntriesLabourRepository extends JpaRepository<DealerServiceEntriesLabour, Long> {

	@Transactional
	@Modifying
	@Query("UPDATE DealerServiceEntriesLabour SET markForDelete = 1, lastModifiedById = ?1 ,lastUpdatedOn = ?2  where dealerServiceEntriesLabourId =?3 AND companyId = ?4")
	public void deleteDealerServiceEntriesLabour( long lastModifiedById ,Timestamp lastModifiedOn,  long dealerServiceEntriesPartId, int companyId);
	
	@Query("FROM DealerServiceEntriesLabour WHERE dealerServiceEntriesId =?1 AND companyId = ?2 AND markForDelete = 0 ")
	public List<DealerServiceEntriesLabour> getDealerServiceEntriesLabourByDealerServiceEntriesId( long dealerServiceEntriesId , int companyId);
	
}
