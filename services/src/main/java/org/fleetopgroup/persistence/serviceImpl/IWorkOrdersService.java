package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.dto.CompanyDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DateWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesAndWorkOrdersDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.WorkOrderRemarkDto;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksToLaborDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksToPartsDto;
import org.fleetopgroup.persistence.model.WorkOrderRemark;
import org.fleetopgroup.persistence.model.WorkOrders;
import org.fleetopgroup.persistence.model.WorkOrdersDocument;
import org.fleetopgroup.persistence.model.WorkOrdersTasks;
import org.fleetopgroup.persistence.model.WorkOrdersTasksToLabor;
import org.fleetopgroup.persistence.model.WorkOrdersTasksToParts;
import org.fleetopgroup.persistence.model.WorkOrdersTasksToReceived;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;



public interface IWorkOrdersService {

	public WorkOrders addWorkOrders(WorkOrders workOrders) throws Exception;

	public void addWorkOrdersTask(WorkOrdersTasks workOrdersTask) throws Exception;

	public void addWorkOrdersTask(String jobSub_name, Long workOrderID) throws Exception;

	/* work order show page to add work orders */
	public WorkOrders getWorkOrders(long WorkOrders);
	
	public WorkOrdersDto getWorkOrdersDetails(long WorkOrders, Integer companyId) throws Exception;
	
	public WorkOrdersDto getWorkOrdersByNumber(long WorkOrders, long id, Integer companyId) throws Exception;

	public WorkOrdersTasks getWorkOrdersTask(long WorkOrderstask_ID, Integer companyId);

	public WorkOrdersTasksToParts getWorkOrdersTaskToParts_ONLY_ID(long WorkOrdersTaskToPart_ID, Integer companyId);

	public WorkOrdersTasksToLabor getWorkOrdersTaskToLabor_ONLY_ID(long WorkOrdersTaskToPart_ID, Integer companyId);

	public List<WorkOrdersTasksDto> getWorkOrdersTasks(long WorkOrders_id, Integer companyId);

	public List<WorkOrdersTasksToPartsDto> getWorkOrdersTasksToParts(long WorkOrders_id, Integer companyId);

	public List<WorkOrdersTasksToParts> getWorkOrdersTasksToParts_ID(long WorkOrdersTASK_id, Integer companyId);

	/** This Page get WorkOrders table to get pagination values */
	public Page<WorkOrders> getDeployment_Page_WorkOrders(short status, Integer pageNumber, CustomUserDetails userDetails) throws Exception;

	/** This Page get WorkOrders table to get pagination values */
	public List<WorkOrdersDto> listOpenWorkOrders(short WorkOrders_open, Integer pageNumber, CustomUserDetails userDetails) throws Exception;

	/** This Page get WorkOrders table to get pagination values */
	public Page<WorkOrders> getDeployment_Page_WorkOrders_VehicleId(Integer Vehicle_id, Integer pageNumbe, Integer companyId);

	/** This Page get WorkOrders table to get pagination values */
	public List<WorkOrdersDto> listOpenWorkOrders_VehicleId(Integer Vehicle_id, Integer pageNumber, Integer companyId) throws Exception;

	/* save Workstasktoparts */
	public WorkOrdersTasksToParts addWorkOrdersTaskToParts(WorkOrdersTasksToParts workOrdersTask) throws Exception;

	// Update Task TotalCost
	public List<WorkOrdersTasks> getWorkOrdersTasksIDToTotalCost(long WorkOrders_id, Integer companyId);


	public void updateWorkOrdersTask_TotalPartCost(Long TaskID, Integer companyId) throws Exception;

	
	public void updateWorkOrderMainTotalCost(Long WorkOrder_ID)
			throws Exception;

	public void updateWorkOrderProcess(short Process, Long lastModifiedBy, Timestamp lastupdated, Long WorkOrder_ID, Integer companyId)
			throws Exception;
	
	public void updateWorkOrderProcess(short Process, Long lastModifiedBy, Timestamp lastupdated, Long WorkOrder_ID, Integer companyId, short vStatus)
			throws Exception;


	public void updateWorkOrderCOMPLETE_date(short Process, Timestamp COMPLETE_date, String lastModifiedBy,
			Timestamp lastupdated, Long WorkOrder_ID, Integer companyId) throws Exception;

	public void updateWorkOrderMainTotalCost_TAX(Double TotalWorkOrderTAX, Double TotalWorkOrdercost, Long WorkOrder_ID, Integer companyId)
			throws Exception;

	/* save WorkstasktoLabor */
	public void addWorkOrdersTaskToLabor(WorkOrdersTasksToLabor workOrdersTaskLabor) throws Exception;

	public void updateWorkOrdersTask_TotalLaborCost(Long TaskID, Integer companyId)
			throws Exception;

	public List<WorkOrdersTasksToLaborDto> getWorkOrdersTasksToLabor(long WorkOrders_id, Integer companyId);

	public List<WorkOrdersTasksToLabor> getWorkOrdersTasksToLabor_ID(long WorkOrdersTask_id, Integer companyId);

	// completion on task update to get
	public WorkOrdersTasks getWorkOrdersCompletion(Long workOrdersTasksID, Integer companyid) throws Exception;

	public void updateWorkOrdersTask_Completion(Integer completionvalue, Long workOrdersTasksID, Integer companyId) throws Exception;

	// count WorkOrder
	public Long countWorkOrder() throws Exception;

	public Long countWorkOrderStatues(String Statues) throws Exception;

	public void updateWorkOrders(WorkOrders workOrders) throws Exception;

	public List<WorkOrders> listWorkOrders() throws Exception;

	public List<WorkOrders> listVehicleWorkOrders(String WorkOrders_vehiclename) throws Exception;

	public List<WorkOrders> listInprocessWorkOrders(String WorkOrders_Inproces);

	public List<WorkOrders> listResolvedWorkOrders(String WorkOrders_Resolved);

	public List<WorkOrders> listClosedWorkOrders(String WorkOrders_Closed);

	public void deleteWorkOrders(Long workorders_id, long usersId, Timestamp toDate, Integer companyId);

	public void deleteWorkOrdersTask(Long WorkOrdersTask_id, Integer companyId);

	public void deleteWorkOrdersTaskTOParts(Long WorkOrdersTask_id, Integer companyId);

	public void deleteWorkOrdersTaskTOLabor(Long WorkOrdersTask_Laborid, Integer companyId);

	/* New page */
	public List<WorkOrdersDto> SearchWorkOrders(String WorkOrders_Search, Integer companyId, long id) throws Exception;
	
	
	public List<WorkOrdersDto> SearchWorkOrdersList(Long WorkOrders_Search, Integer companyId, long id) throws Exception;

	// vehicle Inside to workOrder
	public List<WorkOrdersDto> VehicleToWorkOrdersList(Integer Vehicle_id, Integer companyId) throws Exception;

	// upload Document save
	public void uploadWorkOrdersDocument(org.fleetopgroup.persistence.document.WorkOrdersDocument WorkOrdersdocument) throws Exception;

	// get Document
	public org.fleetopgroup.persistence.document.WorkOrdersDocument getWorkOrdersDocument(Long WorkOrders_id, Integer companyId) throws Exception;

	// upload to Old Document Update
	public void updateOldWorkOrdersDocument(org.fleetopgroup.persistence.document.WorkOrdersDocument WorkOrders_id) throws Exception;

	// create WorkOrdersTasksTo_Received_Part_id
	public void addWorkOrdersToReceived(WorkOrdersTasksToReceived workOrders) throws Exception;

	// Report WorkOrders
	public List<WorkOrdersDto> ReportWorkOrders(String WorkOrderQuery, String RangeFrom, String RangeTo, CustomUserDetails userDetails) throws Exception;

	// get last workOrder job type and sub type change details
	public List<WorkOrdersTasksDto> getLast_WorkOrdersTasks(Integer jobType, Integer jobsubtype, Integer vehicle_id) throws Exception;

	// get last workOrder job type and sub type change details
	public List<WorkOrders> getLast_WorkOrdersTask_To_WorkOrders_details(String jobType, String jobsubtype,
			Integer vehicle_id, Integer companyId);
	
	public List<WorkOrders> getLast_WorkOrdersTask_To_WorkOrders_details(Integer jobType, Integer jobsubtype,
			Integer vehicle_id, Integer companyId);

	// get last workOrderTaskID to workOrder details
	public WorkOrders getLast_WorkOrdersTaskID_To_WorkOrders_details(Long workOrderTaskId, Integer companyId);

	// get last workOrderTaskTo_partId to workOrder details
	public WorkOrders getLast_WorkOrdersTasktoPARTID_To_WorkOrders_details(Long workOrderTasktoPartID, Integer companyId);

	// get last workOrder job type and sub type change details With WorkOrder_id
	public List<WorkOrdersTasksDto> getLast_WorkOrdersTasks(Integer jobType, Integer jobsubtype, Integer vehicle_id,
			Long wrokorders_id, Integer companyId);
	
	// Report WorkOrders Vehicle Wise Repair Report
	public List<WorkOrdersDto> vehicle_WiseRepair_Report(String dateRangeFrom, String dateRangeTo,  String multipleVehicle, Integer companyId) throws Exception;
	
	public List<WorkOrdersDto> vehicle_WiseRepair_Report(String dateRangeFrom, String dateRangeTo,  Long userId, Integer companyId) throws Exception;
	
	public List<WorkOrdersDto> vehicle_WiseRepair_Report(String dateRangeFrom, String dateRangeTo,  Long userId, String vids, Integer companyId) throws Exception;

	// Report WorkOrdersTask Vehicle Wise Repair Report
	public List<WorkOrdersTasksDto> getWorkOrdersTasks_vehicle_WiseRepair_Report(String multipleWorkOrders_id, Integer companyId);
	
	// Report WorkOrdersTas Vehicle Wise Repair Report
	public List<WorkOrdersTasksToPartsDto> getWorkOrdersTasksToParts_vehicle_WiseRepair_Report(String multipleWorkOrders_id, Integer companyId);
	
	//Report Search WorkOrderTask Total amount Multiple Vehicle to workOrders_ID
	public List<Double> WorkOrdersTasks_vehicle_WiseRepair_TotalAmount(String multipleWorkOrders_id, Integer companyid);

	/**
	 * @param b
	 * @param workorder_id
	 */
	public void Update_WorkOrdre_Document_Available_TRUE(Long workorders_document_id, boolean workOrder_document,
			Long workorder_id, Integer companyId);

	/**
	 * @param workOrderQuery
	 * @param dateRangeFrom
	 * @param dateRangeTo
	 * @return
	 */
	public List<WorkOrdersTasksToPartsDto> WorkOrders_Part_Consuming_WorkOrdersTasksToParts(String workOrderQuery,
			String dateRangeFrom, String dateRangeTo);
	
	public int getWorkOrderCountByNumber(long WorkOrders, Integer companyId) throws Exception;

	/**
	 * Transfer all the document details from mysql to mongodb 
	 * @param list
	 * @throws Exception
	 */
	public void transferWorkOrderDocument(List<WorkOrdersDocument> list) throws Exception;
	
	public long getWorkOrderDocumentMaxId()throws Exception;
	
	ValueObject	getOldPartNotReceived(ValueObject	valueObject) throws Exception;
	
	ValueObject	getOldPartReceived(ValueObject	valueObject) throws Exception;

	/*
	 * Get Work Orders Details with Vehicle Details
	 */
	public WorkOrdersDto getWorkOrdersWithVehicleDetails(long workorders_id, Integer company_id) throws Exception;
	/*
	 * Get Work Order Tasks Details with Mechanic Details
	 */
	public List<WorkOrdersTasksDto> getWorkOrderTasksWithMechanic(long workorders_id, Integer company_id) throws Exception;

	/*
	 * Update Task Remark By Id
	 */
	public ValueObject updateTaskRemarkById(ValueObject valueObject) throws Exception;


	public List<WorkOrdersDto> depot_wise_Report(String dateRangeFrom, String location, String dateRangeTo,
			String repair_vid, Integer company_id) throws Exception;


	public List<WorkOrdersDto> vehicle_WiseRepair_Report_location(String dateRangeFrom, String dateRangeTo,
			String repair_vid, String locationId, Integer company_id) throws Exception;

	public Object getWorkOrdersTasksTo_Most_Parts_Count_Report(Long GroupId ,String dateRangeFrom, String dateRangeTo, Integer company_id, Integer LocationId) throws Exception;

	public List<WorkOrdersDto> getWorkOrdereListOfMonthByVid(TripSheetIncomeDto	incomeDto) throws Exception;

	public HashMap<Long ,ServiceReminderDto> getJobtypeAndSubtypeFromServiceReminderId(String serviceIds, Integer company_id) throws Exception;
	
	public HashMap<Integer, Long> getALLEmailWorkOrderDailyWorkStatus(String startDate, String endDate) throws Exception ;

	public List<CompanyDto> getALLEmailWorkOrderDailyWorkForManagers(String startDate, String endDate) throws Exception;
	
	public List<VehicleDto> getVehicleWiseWorkOrders(String query, int companyId) throws Exception;
	
	public Long getALLOpenWorkOrderCount(Integer companyId) throws Exception;
	
	public HashMap<Integer, Long> getALLOpenWorkOrder() throws Exception;
	
	public Long getALLOpenWorkOrderBetweenDates(String startDate, String endDate,Integer companyId) throws Exception;
	
	public HashMap<Integer, Long> getALLOpenWorkOrderFromDateRange(String startDate, String endDate) throws Exception;
	
	public HashMap<Integer, Long> getALLOnHoldWorkOrder() throws Exception;
	
	public Long getTotalWorkOrderCompleteCount(String startDate, String endDate,Integer companyId) throws Exception;
	
	public HashMap<Integer, Long> getALLCOMPLETEDWorkOrder(String startDate, String endDate) throws Exception ;
	
	public Long getALLOpenWorkOrderByDate(String startDate, Integer companyId) throws Exception;
	
	public HashMap<Integer, Long> getALLOpenWorkOrderFrom15Days(String startDate) throws Exception;

	public Map<Integer, List<WorkOrdersTasksToPartsDto>> getAllPartsInWorkorders(String startDate, String endDate)throws Exception;

	public ValueObject configureDailyPartConsumedEmailBody(ValueObject valueOutObject)throws Exception;

	public long getWorkOrderCreatedCount(String startDate, String endDate, Integer companyId) throws Exception;
	
	public long getWorkOrderCountByStatusID(String startDate, String endDate, Integer companyId, short status) throws Exception;

	public List<WorkOrdersDto> getWorkorderDetailsBetweenDatesByStatus(Integer compId, String wOStatusQuery)throws Exception;

	public List<WorkOrdersDto> getLocationWiseWorkOrderCreatedCount(String startDate, String endDate, Integer companyID)throws Exception;

	public List<WorkOrdersDto> getLocationWiseWorkOrderCloseCount(String startDate, String endDate, Integer companyID)throws Exception;

	public WorkOrdersTasksToParts getLastOccorredOnPartDetailsByVehicleId(Integer vehicle_vid, Long partid,
			Integer company_id)throws Exception;

	public WorkOrdersDto getWorkOrderLastUpdatedDetails(Long workorders_id,Integer companyId)throws Exception;

	public List<TripSheetExpenseDto> getWorkOrdersListForTallyImport(String fromDate, String toDate, Integer companyId, String companyName)throws Exception;

	
	public WorkOrdersDto getWorkOrdersLimitedDetails(long WorkOrders, Integer companyId) throws Exception;
	
	public List<DateWiseVehicleExpenseDto> getDateWiseWorkOrderExpenseDtoByVid(Integer vid, String fromDate, String toDate,
			Integer companyId) throws Exception;
	public List<MonthWiseVehicleExpenseDto> getMonthWiseWorkOrderExpenseDtoByVid(Integer vid, String fromDate, String toDate,
			Integer companyId) throws Exception;
	
	public List<DateWiseVehicleExpenseDto> getDateWiseWorkOrderExpenseDtoVehicleTypeId(Integer vid, String fromDate, String toDate,
			Integer companyId, Long vehicleTypeId) throws Exception;
	
	public ValueObject saveWorkOrder(ValueObject valueObject) throws Exception;
	
	public ValueObject savePartForWorkOderTask(ValueObject object) throws Exception;
	
	public ValueObject saveLabourForWorkOderTask(ValueObject object) throws Exception;
	
	public ValueObject saveMarkAsComplete(ValueObject object) throws Exception;
	
	public ValueObject saveWorkOderTasks(ValueObject object) throws Exception;
	
	public ValueObject updateWoGstCost(ValueObject object) throws Exception;
	
	public ValueObject changeWorkorderStatusToHold(ValueObject object) throws Exception;
	
	public ValueObject changeWorkorderStatusToInProgress(ValueObject object) throws Exception;
	
	public ValueObject changeWorkorderStatusToComplete(ValueObject woObject) throws Exception;
	
	public ValueObject uploadWorkOrderDocument(ValueObject object, MultipartFile file) throws Exception;
	
	public ValueObject deletePartOfWorkOrdertask(ValueObject object) throws Exception;
	
	public ValueObject deleteLabourOfWorkOrdertask(ValueObject object) throws Exception;
	
	public ValueObject deleteWorkOrdertask(ValueObject object) throws Exception;
	
	public ValueObject updateWorkOrderDetails(ValueObject object) throws Exception;
	
	public ValueObject deleteWorkOrderDetails(ValueObject object) throws Exception;
	
	public ValueObject getWorkOrderList(ValueObject object) throws Exception;
	
	public ValueObject searchWoByNumber(ValueObject object) throws Exception;
	
	public ValueObject searchWoByDifferentParameters(ValueObject object) throws Exception;
	
	public ValueObject searchWorkOrderByData(ValueObject valueObject) throws Exception;
	
	public List<ServiceEntriesAndWorkOrdersDto> getVehicleRepairAndPartConsumptionDetailsInWO(ValueObject valueObject)throws Exception;

	public WorkOrders getWorkordersCreatedBetweenDates(String startDate, String endDate, Integer companyId) throws Exception;
	
	public HashMap<Integer ,ServiceReminderDto> getJobtypeAndSubtypeFromServiceId (String serviceIds, Integer company_id) throws Exception;

	public ValueObject approveWorkOrderDetails(ValueObject object) throws Exception;
	
	public ValueObject	getWorkOrderInitialDetails(ValueObject	valueObject) throws Exception;
	public ValueObject	getUserEmailId_Subscrible(ValueObject valueObject) throws Exception;
	public ValueObject getVehicleOdoMeter(Integer companyId,Integer vehicleID) throws Exception;
	public ValueObject	getJobTypeWorkOrder(Integer companyId,String term) throws Exception;
	public ValueObject getJobSubTypeWorkOrder(Integer companyId,String term) throws Exception;
	public ValueObject getJobDetailsFromSubJobId(Integer JobSub_ID,Integer companyId) throws Exception;
	public ValueObject showWorkOrderDetailsFromMob(ValueObject valueObjectIn) throws Exception;
	public ValueObject vehicleWiseWorkorderServiceDetails(ValueObject valueObjectIn)throws Exception;
	public ValueObject getInvoiceWisePartList(ValueObject valIn) throws Exception;
	public ValueObject getTechinicianWorkOrder(String term ,int companyId) throws Exception;
	public WorkOrders getWorkOrders(long WorkOrders,int companyId)throws Exception;
	public ValueObject getworkOrderEditInitialDetails(ValueObject valueObject)	throws Exception;
	public ValueObject getVehicleServiceReminderList(ValueObject valueObject) throws Exception;

	public ValueObject workOrderTaskToPartDocumentSave(MultipartFile[] fileUpload, ValueObject object) throws Exception ;
	
	public Boolean   findocumentAvailabilityByPartId(Long workOrderId,Long companyId) throws Exception;
	
	public ValueObject deletePartOfWorkOrdertaskDocument(ValueObject object) throws Exception ;
	
	public List<WorkOrdersDto> getUserWiseWOActivityList(String query ,String innerQuery) throws Exception;
	
	public Object[] getWorkOrderCountByStatus(Integer companyId, Long userId) throws Exception;
	
	public ArrayList<String> getWorkOrderTaskLimitredDetails(Long workOrderId) throws Exception;
	
	List<WorkOrderRemarkDto>  getWorkOrderRemarkDtoList(Long orkOrderId) throws Exception;
	
	public WorkOrders getLastWorkOrdersTaskToWorkOrdersdetails(Integer jobType, Integer jobsubtype,
			Integer vehicle_id, Integer companyId ,Long Workorders_id ,String completeQuery)throws Exception;
	
	public ValueObject markAllTaskCompleted(ValueObject object) throws Exception;
	
//	public ValueObject saveIssueRemark(ValueObject object) throws Exception;
	
	public void updateAssignedNoOfPart(Long	woTaskId, int noOfPart) throws Exception;
	
	public WorkOrdersTasksToParts validateAssignedNoOfParts(Long	woTaskId) throws Exception;

	public WorkOrdersDto getWorkOrdersTasksByTaskId(long WorkOrderTaskId, Integer companyId);

	
	public ValueObject markAllTaskCompletedFromMob(ValueObject object) throws Exception;
	
	ValueObject getWorkOrderAndDseDetails(ValueObject valueObject) throws Exception;
	
	public List<WorkOrderRemarkDto> getWorkOrderRemarkDtoOnHoldList(Long orkOrderId) throws Exception;
	
	public double calculatePerHourLaborCost(Integer driverId, Integer companyId) throws Exception;
}