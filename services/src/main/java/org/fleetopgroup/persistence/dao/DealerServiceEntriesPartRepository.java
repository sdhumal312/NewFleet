/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.DealerServiceEntriesPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fleetop
 *
 */
public interface DealerServiceEntriesPartRepository extends JpaRepository<DealerServiceEntriesPart, Long> {

	
	@Transactional
	@Modifying
	@Query("UPDATE DealerServiceEntriesPart SET markForDelete = 1, lastModifiedById = ?1 ,lastUpdatedOn = ?2  where dealerServiceEntriesPartId =?3 AND companyId = ?4")
	public void deleteDealerServiceEntriesPart( long lastModifiedById ,Timestamp lastModifiedOn,  long dealerServiceEntriesPartId, int companyId);

	@Query("FROM DealerServiceEntriesPart WHERE dealerServiceEntriesId =?1 AND companyId = ?2 AND markForDelete = 0 ")
	public List<DealerServiceEntriesPart> getDealerServiceEntriesPartByDealerServiceEntriesId(long long1, int int1);
	
	@Query("FROM DealerServiceEntriesPart WHERE DealerServiceEntriesPartId =?1 AND companyId = ?2 AND markForDelete = 0 ")
	public DealerServiceEntriesPart getDealerServiceEntriesPartById(long long1, int int1);

	
}
