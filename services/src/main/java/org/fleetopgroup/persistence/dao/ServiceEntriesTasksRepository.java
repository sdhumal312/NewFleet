package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.ServiceEntriesTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ServiceEntriesTasksRepository extends JpaRepository<ServiceEntriesTasks, Long> {

	// public void addServiceEntriesTask(ServiceEntriesTasks ServiceEntriesTask)
	// throws Exception;

	@Query("From ServiceEntriesTasks where serviceEntries_id = ?1 AND companyId = ?2 and markForDelete = 0")
	public List<ServiceEntriesTasks> getServiceEntriesTasks(long ServiceEntries_id, Integer companyId);

	@Query("from ServiceEntriesTasks where servicetaskid = ?1 AND companyId = ?2  AND markForDelete = 0")
	public List<ServiceEntriesTasks> getServiceEntriesTasksIDToTotalCost(long ServiceEntries_id, Integer companyId);

	// completion on task update to get
	@Query("from ServiceEntriesTasks where servicetaskid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public ServiceEntriesTasks getServiceEntriesCompletion(Long ServiceEntriesTasksID, Integer companyId) throws Exception;

	@Query("from ServiceEntriesTasks where servicetaskid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public ServiceEntriesTasks getServiceEntriesTask(long ServiceEntriestask_ID, Integer companyId);

	@Modifying
	@Query("UPDATE ServiceEntriesTasks SET markForDelete = 1 where Servicetaskid= ?1 AND companyId = ?2")
	public void deleteServiceEntriesTask(Long ServiceEntriesTask_id, Integer companyId);

	// get last ServiceEntriesjob type and sub type change details
	/*@Query("FROM ServiceEntriesTasks where service_typetask= ?1 AND service_subtypetask = ?2 AND  vid = ?3 ORDER BY servicetaskid DESC LIMIT 1")
	public List<ServiceEntriesTasks> getLast_ServiceEntriesTasks(String jobType, String jobsubtype, Integer vehicle_id);
*/
	// get last ServiceEntriesjob type and sub type change details With serviceEntries_id
	/*@Query("FROM ServiceEntriesTasks where service_typetask= ?1 AND service_subtypetask = ?2 AND  vid = ?3 AND serviceEntries_id <> ?4 ORDER BY servicetaskid DESC LIMIT 1 ORDER BY servicetaskid DESC LIMIT 1")
	public List<ServiceEntriesTasks> getLast_ServiceEntriesTasks(String jobType, String jobsubtype, Integer vehicle_id,
			Long serviceEntries_id);*/

}
