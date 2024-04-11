package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksToPartsDto;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkOrdersDao {
	
	public List<WorkOrdersDto>  getWorkOrdersList(WorkOrdersDto workOrdersDto)throws Exception;
	
	public List<WorkOrdersTasksToPartsDto>  getOldPartReceivedByVehicleGroup(WorkOrdersTasksToPartsDto	workOrdersTasksToPartsDto, String sqlQuery)throws Exception;
	public List<WorkOrdersTasksToPartsDto>  getOldPartNotReceived(WorkOrdersTasksToPartsDto	workOrdersTasksToPartsDto, String sqlQuery)throws Exception;
}
