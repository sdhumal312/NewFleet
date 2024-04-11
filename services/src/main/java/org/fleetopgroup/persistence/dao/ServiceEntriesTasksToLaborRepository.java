package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.ServiceEntriesTasksToLabor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ServiceEntriesTasksToLaborRepository extends JpaRepository<ServiceEntriesTasksToLabor, Long> {

	/* save WorkstasktoLabor */
	// public void addServiceEntriesTaskToLabor(ServiceEntriesTasksToLabor ServiceEntriesTaskLabor) throws Exception;

	@Query("from ServiceEntriesTasksToLabor where ServiceEntries_id = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<ServiceEntriesTasksToLabor> getServiceEntriesTasksToLabor(long ServiceEntries_id, Integer companyId );

	@Query("from ServiceEntriesTasksToLabor where Servicetaskid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<ServiceEntriesTasksToLabor> getServiceEntriesTasksToLabor_ID(long ServiceEntriesTask_id, Integer companyId);

	@Query("from ServiceEntriesTasksToLabor where serviceEntriesto_laberid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public ServiceEntriesTasksToLabor getServiceEntriesTaskToLabor_ONLY_ID(long ServiceEntriesTaskToLabor_ID, Integer companyId);

	@Modifying
	@Query("UPDATE  ServiceEntriesTasksToLabor SET markForDelete = 1 where serviceEntriesto_laberid= ?1 AND companyId = ?2")
	public void deleteServiceEntriesTaskTOLabor(Long ServiceEntriesTask_Laborid, Integer companyId);

}
