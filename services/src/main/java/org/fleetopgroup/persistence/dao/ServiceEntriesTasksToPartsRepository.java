package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.ServiceEntriesTasksToParts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ServiceEntriesTasksToPartsRepository extends JpaRepository<ServiceEntriesTasksToParts, Long> {

	// public void addServiceEntriesTaskToParts(ServiceEntriesTasksToParts
	// ServiceEntriesTask) throws Exception;

	@Query("From ServiceEntriesTasksToParts where serviceEntries_id = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<ServiceEntriesTasksToParts> getServiceEntriesTasksToParts(long ServiceEntries_id, Integer companyId);

	@Query("From ServiceEntriesTasksToParts where Servicetaskid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<ServiceEntriesTasksToParts> getServiceEntriesTasksToParts_ID(long ServiceEntriesTASK_id, Integer companyId);

	@Query("From ServiceEntriesTasksToParts where serviceEntriesTaskto_partid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public ServiceEntriesTasksToParts getServiceEntriesTaskToParts_ONLY_ID(long ServiceEntriesTaskToPart_ID, Integer companyId);

	@Modifying
	@Query("UPDATE ServiceEntriesTasksToParts SET markForDelete = 1 where serviceEntriesTaskto_partid= ?1 AND companyId = ?2")
	public void deleteServiceEntriesTaskTOParts(Long ServiceEntriesTask_id, Integer companyId);
	
	@Query(" SELECT SEP.serviceEntriesTaskto_partid FROM ServiceEntriesTasksToParts as SEP "
			+ " INNER JOIN ServiceEntries AS S ON S.serviceEntries_id = SEP.serviceEntries_id "
			+ " Where S.completed_date between ?1 AND ?2 AND SEP.companyId =?3 AND SEP.markForDelete = 0 ORDER BY SEP.serviceEntriesTaskto_partid desc ")
	public Page<ServiceEntriesTasksToParts> getPageServiceEntryConsumptionByTransactionDate(Timestamp startDate, Timestamp endDate, int companyId, Pageable pageable);

	@Query(" SELECT SEP.serviceEntriesTaskto_partid FROM ServiceEntriesTasksToParts as SEP "
			+ " INNER JOIN ServiceEntries AS S ON S.serviceEntries_id = SEP.serviceEntries_id "
			+ " Where S.created between ?1 AND ?2 AND SEP.companyId =?3 AND SEP.markForDelete = 0 ORDER BY SEP.serviceEntriesTaskto_partid desc ")
	public Page<ServiceEntriesTasksToParts> getPageServiceEntryConsumptionByCreatedDate(Timestamp startDate, Timestamp endDate, int companyId, Pageable pageable);


}
