package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.WorkOrdersTasksToParts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WorkOrdersTasksToPartsRepository extends JpaRepository<WorkOrdersTasksToParts, Long> {
	
	//save
	//public void addWorkOrdersTaskToParts(WorkOrdersTasksToParts workOrdersTask) throws Exception;

	@Query("from WorkOrdersTasksToParts where workorders_id = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<WorkOrdersTasksToParts> getWorkOrdersTasksToParts(long WorkOrders_id, Integer companyId);

	@Query("from WorkOrdersTasksToParts where workordertaskid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<WorkOrdersTasksToParts> getWorkOrdersTasksToParts_ID(long WorkOrdersTASK_id, Integer companyId);

	@Query("from WorkOrdersTasksToParts where workordertaskto_partid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public WorkOrdersTasksToParts getWorkOrdersTaskToParts_ONLY_ID(long WorkOrdersTaskToPart_ID, Integer companyId);

	@Modifying
	@Query("UPDATE WorkOrdersTasksToParts SET markForDelete = 1 where workordertaskto_partid= ?1 AND companyId = ?2 ")
	public void deleteWorkOrdersTaskTOParts(Long WorkOrdersTask_id, Integer companyId);
	
	@Modifying
	@Query("UPDATE WorkOrdersTasksToParts SET woPart_document = ?1 where workordertaskto_partid =?2 AND companyId = ?3 ")
	public void updateDocumentWorkOrdersTaskTOParts(boolean woPart_document,Long WorkOrdersTask_id, Integer companyId) throws Exception;
	
	@Query("SELECT SUM(quantity) FROM WorkOrdersTasksToParts WHERE inventory_id = ?1 AND companyId = ?2 AND markForDelete = 0 ")
	public Object countNumberOfPartsUsedFromThatInventory(Long inventory_id, Integer companyId) throws Exception;

	@Query(" SELECT WOT.workordertaskto_partid FROM WorkOrdersTasksToParts as WOT "
			+ " INNER JOIN WorkOrders AS WO ON WO.workorders_id = WOT.workorders_id "
			+ " Where WO.completed_date between ?1 AND ?2 AND WOT.companyId =?3 AND WOT.markForDelete = 0 ORDER BY WOT.workordertaskto_partid desc ")
	public Page<WorkOrdersTasksToParts> getPageWorkOrderConsumptionByTransactionDate(Timestamp startDate, Timestamp endDate, int companyId, Pageable pageable);

	@Query(" SELECT WOT.workordertaskto_partid FROM WorkOrdersTasksToParts as WOT "
			+ " INNER JOIN WorkOrders AS WO ON WO.workorders_id = WOT.workorders_id "
			+ " Where WO.created between ?1 AND ?2 AND WOT.companyId =?3 AND WOT.markForDelete = 0 ORDER BY WOT.workordertaskto_partid desc ")
	public Page<WorkOrdersTasksToParts> getPageWorkOrderConsumptionByCreatedDate(Timestamp startDate, Timestamp endDate, int companyId, Pageable pageable);

	@Query("from WorkOrdersTasksToParts where workordertaskto_partid = ?1 and markForDelete = 0 ")
	public WorkOrdersTasksToParts validateAssignedNoOfParts(Long woTaskId) throws Exception ;
}
