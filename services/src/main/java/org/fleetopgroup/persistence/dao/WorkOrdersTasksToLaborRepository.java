package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.WorkOrdersTasksToLabor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WorkOrdersTasksToLaborRepository extends JpaRepository<WorkOrdersTasksToLabor, Long> {

	/* save WorkstasktoLabor */
	// public void addWorkOrdersTaskToLabor(WorkOrdersTasksToLabor
	// workOrdersTaskLabor) throws Exception;

	@Query("from WorkOrdersTasksToLabor where workorders_id = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<WorkOrdersTasksToLabor> getWorkOrdersTasksToLabor(long WorkOrders_id, Integer companyId);

	@Query("from WorkOrdersTasksToLabor where workordertaskid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<WorkOrdersTasksToLabor> getWorkOrdersTasksToLabor_ID(long WorkOrdersTask_id, Integer companyId);

	@Query("from WorkOrdersTasksToLabor where workordertaskto_laberid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public WorkOrdersTasksToLabor getWorkOrdersTaskToLabor_ONLY_ID(long WorkOrdersTaskToLabor_ID, Integer companyId);

	@Modifying
	@Query("UPDATE WorkOrdersTasksToLabor SET markForDelete = 1 where workordertaskto_laberid= ?1 AND companyId = ?2")
	public void deleteWorkOrdersTaskTOLabor(Long WorkOrdersTask_Laborid, Integer companyId);

}
