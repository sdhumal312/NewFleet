package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.WorkOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface WorkOrdersRepository extends JpaRepository<WorkOrders, Long> {

	// public void addWorkOrders(WorkOrders WorkOrders) throws Exception;

	// public void updateWorkOrders(WorkOrders WorkOrders);

	public List<WorkOrders> findAll();

	@Query("from WorkOrders where issue_statues = ?1 AND markForDelete = 0")
	public List<WorkOrders> listVehicleWorkOrders(String WorkOrders_vehiclename);

	@Query("from WorkOrders where workorders_id = ?1 AND companyId = ?2 AND markForDelete = 0")
	public WorkOrders getWorkOrders(long WorkOrders, Integer companyId);

	@Modifying
	@Query("UPDATE WorkOrders SET lastupdated=?3,lastmodifiedById=?2, markForDelete = 1 WHERE workorders_id = ?1 AND companyId = ?4")
	public void deleteWorkOrders(Long workorders_id, long usersId, Timestamp toDate, Integer companyId);

	@Query("from WorkOrders where workorders_status= ?1 AND markForDelete = 0")
	public List<WorkOrders> listOpenWorkOrders(String WorkOrders_open);

	@Query("from WorkOrders where workorders_status= ?1 AND markForDelete = 0")
	public List<WorkOrders> listInprocessWorkOrders(String WorkOrders_Inproces);

	@Query("from WorkOrders where workorders_status= ?1 AND markForDelete = 0")
	public List<WorkOrders> listResolvedWorkOrders(String WorkOrders_Resolved);

	@Query("from WorkOrders where workorders_status= ?1 AND markForDelete = 0")
	public List<WorkOrders> listClosedWorkOrders(String WorkOrders_Closed);

	@Modifying
	@Query("UPDATE WorkOrders SET totalsubworktask_cost= ?1 , totalworkorder_cost = ?2  where workorders_id= ?3 AND companyId = ?4 ")
	public void updateWorkOrderMainTotalCost(Double TotalWorkOrderSubcost, Double TotalWorkOrdercost, Long WorkOrder_ID, Integer companyId)
			throws Exception;

	@Modifying
	@Transactional
	@Query("UPDATE WorkOrders SET workorders_statusId= ?1, lastModifiedById=?2, lastupdated=?3  where workorders_id= ?4 AND companyId = ?5")
	public void updateWorkOrderProcess(short Process, Long lastModifiedBy, Timestamp lastupdated, Long WorkOrder_ID, Integer companyId)
			throws Exception;
	
	@Modifying
	@Query("UPDATE WorkOrders SET workorders_statusId= ?1, lastModifiedById=?2, lastupdated=?3, vehicleReOpenStatusId = ?6  where workorders_id= ?4 AND companyId = ?5")
	public void updateWorkOrderProcess(short Process, Long lastModifiedBy, Timestamp lastupdated, Long WorkOrder_ID, Integer companyId, short vStatus)
			throws Exception;


	@Modifying
	@Transactional
	@Query("UPDATE WorkOrders SET workorders_statusId= ?1 , completed_date = ?2 ,lastModifiedBy=?3, lastupdated=?4  where workorders_id= ?5 AND companyId = ?6")
	public void updateWorkOrderCOMPLETE_date(short Process, Timestamp COMPLETE_date, String lastModifiedBy,
			Timestamp lastupdated, Long WorkOrder_ID, Integer companyId) throws Exception;

	// count WorkOrder
	@Query("select count(*) from WorkOrders where markForDelete = 0")
	public Long countWorkOrder() throws Exception;

	@Query("select count(*) from WorkOrders Where workorders_status= ?1 AND markForDelete = 0 ")
	public Long countWorkOrderStatues(String Statues) throws Exception;

	@Modifying
	@Query("UPDATE WorkOrders SET totalworktax_cost= ?1 , totalworkorder_cost = ?2  where workorders_id= ?3 AND companyId = ?4")
	public void updateWorkOrderMainTotalCost_TAX(Double TotalWorkOrderTAX, Double TotalWorkOrdercost, Long WorkOrder_ID, Integer companyId)
			throws Exception;

	// public List<WorkOrders> SearchWorkOrders(String WorkOrders_Search) throws
	// Exception;

	// vehicle Inside to workOrder
	@Query("from WorkOrders where vehicle_vid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<WorkOrders> VehicleToWorkOrdersList(Integer Vehicle_id, Integer companyId) throws Exception;

	@Query("SELECT p.workorders_id From WorkOrders as p "
			+ " INNER JOIN Vehicle V ON V.vid = p.vehicle_vid"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?2"
			+ " where p.workorders_statusId= ?1 AND p.companyId = ?3 AND p.markForDelete = 0")
	public Page<WorkOrders> getDeployment_Page_WorkOrders(short status, long id, Integer companyId, Pageable pageable);
	
	@Query("SELECT p.workorders_id From WorkOrders as p where p.workorders_statusId= ?1 AND p.companyId = ?2 AND p.markForDelete = 0")
	public Page<WorkOrders> getDeployment_Page_WorkOrders(short status, Integer companyId, Pageable pageable);


	@Query("SELECT p.workorders_id From WorkOrders as p where p.vehicle_vid =?1 AND companyId = ?2 AND markForDelete = 0")
	public Page<WorkOrders> getDeployment_Page_WorkOrders_VehicleId(Integer Vehicle_id, Integer companyId, Pageable pageable);

	@Modifying
	@Query("UPDATE WorkOrders SET workorders_document_id= ?1 , workorders_document= ?2  where workorders_id= ?3 AND companyId = ?4 ")
	public void Update_WorkOrdre_Document_Available_TRUE(Long workorders_document_id, boolean workOrder_document,
			Long workorder_id, Integer companyId);
	
	@Query("from WorkOrders WO "
			+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?2"
			+ " where WO.workorders_Number = ?1 AND WO.companyId = ?3 AND WO.markForDelete = 0")
	public WorkOrders getWorkOrdersByNumber(long WorkOrders, long id, Integer companyId);
	
	@Query("from WorkOrders WO where WO.workorders_Number = ?1 AND WO.companyId = ?2 AND WO.markForDelete = 0")
	public WorkOrders getWorkOrdersByNumber(long WorkOrders, Integer companyId);

	@Query("SELECT COUNT(WO) FROM WorkOrders WO where WO.workorders_Number = ?1 AND WO.companyId = ?2 AND WO.markForDelete = 0")
	public int getWorkOrderCountByNumber(long WorkOrders, Integer companyId);
	
	@Query("SELECT WO.workorders_Number FROM WorkOrders AS WO WHERE WO.companyId = ?1 AND WO.workorders_id = ?2 AND WO.markForDelete = 0")
	public Long getWorkOrders_Number(Integer companyId, Long workorders_id ) throws Exception;
	


}
