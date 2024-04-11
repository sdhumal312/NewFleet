package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.WorkOrdersTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface WorkOrdersTasksRepository extends JpaRepository<WorkOrdersTasks, Long> {

	// public void addWorkOrdersTask(WorkOrdersTasks workOrdersTask) throws
	// Exception;

	@Query("from WorkOrdersTasks where workorders_id = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<WorkOrdersTasks> getWorkOrdersTasks(long WorkOrders_id, Integer companyId);

	@Query("from WorkOrdersTasks where workordertaskid = ?1  AND companyId = ?2 AND markFordelete = 0")
	public List<WorkOrdersTasks> getWorkOrdersTasksIDToTotalCost(long WorkOrders_id, Integer companyId);

	// Update Task TotalCost
//	@Modifying
//	@Query("UPDATE WorkOrdersTasks SET totalpart_cost= ?1,  totaltask_cost =?2 where workordertaskid= ?3 AND companyId = ?4")
//	public void updateWorkOrdersTask_TotalPartCost(Double TaskPartCost, Double TotalCost, Long TaskID, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE WorkOrdersTasks SET totallaber_cost= ?1 , totaltask_cost = ?2 where workordertaskid= ?3 AND companyId = ?4 ")
	public void updateWorkOrdersTask_TotalLaborCost(Double TaskLaborCost, Double TotalCost, Long TaskID, Integer companyId)
			throws Exception;

	// completion on task update to get
	@Query("from WorkOrdersTasks where workordertaskid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public WorkOrdersTasks getWorkOrdersCompletion(Long workOrdersTasksID, Integer companyid) throws Exception;

	@Modifying
	@Query("UPDATE WorkOrdersTasks SET mark_complete = ?1 where workordertaskid= ?2 AND companyId = ?3")
	public void updateWorkOrdersTask_Completion(Integer completionvalue, Long workOrdersTasksID, Integer companyId) throws Exception;

	@Query("from WorkOrdersTasks where workordertaskid = ?1 AND companyId = ?2 AND markForDelete = 0 ")
	public WorkOrdersTasks getWorkOrdersTask(long WorkOrderstask_ID, Integer companyId);

	@Modifying
	@Query("UPDATE WorkOrdersTasks SET markForDelete = 1 where workordertaskid= ?1 AND companyId = ?2")
	public void deleteWorkOrdersTask(Long WorkOrdersTask_id, Integer companyId);
	
	@Modifying
	@Query("UPDATE WorkOrdersTasks SET jobTypeRemark = ?1 where workordertaskid= ?2 AND companyId = ?3")
	public void updateTaskRemarkById(String taskRemark, Long WorkOrdersTask_id, Integer companyId);
	
	@Modifying
	@Query("UPDATE WorkOrdersTasks SET mark_complete = 1 where workorders.workorders_id = ?1 AND companyId = ?2")
	public void markAllTaskCompleted(Long WorkOrdersTask_id, Integer companyId);


	/*@Modifying
	@Query("INSERT INTO WorkOrdersTasks (job_subtypetask, workorders_id) VALUES ( ?1 , ?2) ")
	public void addWorkOrdersTask(String jobSub_name, Long workOrderID) throws Exception;*/

	// get last workOrder job type and sub type change details
	/*@Query("FROM WorkOrdersTasks where job_typetask= ?1 AND job_subtypetask = ?2 AND  vehicle_vid = ?3 ORDER BY workordertaskid DESC LIMIT 1")
	public List<WorkOrdersTasks> getLast_WorkOrdersTasks(String jobType, String jobsubtype, Integer vehicle_id);

	// get last workOrder job type and sub type change details
	@Query("FROM WorkOrdersTasks where job_typetask= ?1 AND job_subtypetask = ?2 AND  vehicle_vid = ?3 AND workorders_id <> ?4 ORDER BY workordertaskid DESC LIMIT 1")
	public List<WorkOrdersTasks> getLast_WorkOrdersTasks(String jobType, String jobsubtype, Integer vehicle_id,
			Long workorders_id);*/

}
