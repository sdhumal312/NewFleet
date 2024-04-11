package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksToPartsDto;
import org.fleetopgroup.web.util.ValueObject;

public interface IPartReportDao {

	public List<WorkOrdersTasksToPartsDto>  getWorkOrderPartConsumptionList(String query, Integer companyId) throws Exception;
	
	public List<WorkOrdersTasksToPartsDto>  getServiceEntiresPartConsumptionList(String query, Integer companyId) throws Exception;
	
	public List<WorkOrdersDto>  getTechnicianWisePartReportList(String query, Integer companyId) throws Exception;

	public List<WorkOrdersTasksToPartsDto> getSEPartConsumptionList(ValueObject object)throws Exception;
	
	public List<WorkOrdersTasksToPartsDto> getWOPartConsumptionList(ValueObject object)throws Exception;
	
	public List<WorkOrdersTasksToPartsDto> getWorkOrderWarrantyPartDetails(String query, Integer companyId)
			throws Exception;
	
	public List<WorkOrdersTasksToPartsDto> getDSEPartDetails(String query, Integer companyId)
			throws Exception ;
}
