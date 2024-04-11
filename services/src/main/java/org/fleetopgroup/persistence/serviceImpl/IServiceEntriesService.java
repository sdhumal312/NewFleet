package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.CompanyDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DateWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesAndWorkOrdersDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesRemarkDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesTasksDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesTasksToPartsDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VendorPaymentNotPaidDto;
import org.fleetopgroup.persistence.model.ServiceEntries;
import org.fleetopgroup.persistence.model.ServiceEntriesDocument;
import org.fleetopgroup.persistence.model.ServiceEntriesTasks;
import org.fleetopgroup.persistence.model.ServiceEntriesTasksToLabor;
import org.fleetopgroup.persistence.model.ServiceEntriesTasksToParts;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface IServiceEntriesService {

	public ServiceEntries addServiceEntries(ServiceEntries ServiceEntries) throws Exception;

	public void addServiceEntriesTask(ServiceEntriesTasks ServiceEntriesTask) throws Exception;

	public void addServiceEntriesTask(String jobSub_name, Long ServiceEntriesID) throws Exception;

	/* work order show page to add work orders */
	public ServiceEntries getServiceEntries(long ServiceEntries) throws Exception;
	
	public ServiceEntriesDto getServiceEntriesDetails(long ServiceEntries, Integer companyId) throws Exception;
	
	public ServiceEntriesDto getServiceEntriesByNumber(long ServiceEntries, Integer companyId, Long id) throws Exception;

	public ServiceEntriesTasks getServiceEntriesTask(long ServiceEntriestask_ID);

	public ServiceEntriesTasksToParts getServiceEntriesTaskToParts_ONLY_ID(long ServiceEntriesTaskToPart_ID, Integer companyId);

	public ServiceEntriesTasksToLabor getServiceEntriesTaskToLabor_ONLY_ID(long ServiceEntriesTaskToLabor_ID, Integer companyId);

	public List<ServiceEntriesTasksDto> getServiceEntriesTasks(long ServiceEntries_id, Integer companyId) throws Exception;

	public List<ServiceEntriesTasksToPartsDto> getServiceEntriesTasksToParts(long ServiceEntries_id);

	public List<ServiceEntriesTasksToParts> getServiceEntriesTasksToParts_ID(long ServiceEntriesTASK_id, Integer companyId);

	/**
	 * This List get ServiceEntries table to get pagination last 10 entries
	 * values
	 */
	public List<ServiceEntriesDto> listOpenServiceEntries(short Status_ID,Integer pageNumber) throws Exception;

	/** This Page get ServiceEntries table to get pagination values */
	public Page<ServiceEntries> getDeployment_Page_ServiceEntries(short Status_ID, Integer pageNumber, CustomUserDetails	userDetails) throws Exception;

	/* save Workstasktoparts */
	public void addServiceEntriesTaskToParts(ServiceEntriesTasksToParts ServiceEntriesTask) throws Exception;

	// Update Task TotalCost
	public List<ServiceEntriesTasks> getServiceEntriesTasksIDToTotalCost(long ServiceEntries_id);

	public void updateServiceEntriesTask_TotalPartCost(Long TaskID)
			throws Exception;

	public void updateServiceEntriesMainTotalCost(
			Long ServiceEntries_ID) throws Exception;

	public void updateServiceEntriesProcess(short Process, Long ServiceEntries_ID, Integer companyId, Date lastupdated, Long lastmodifiedby) throws Exception;

	public void updateServiceEntriesCOMPLETE_date(short Process, Date COMPLETE_date, Long ServiceEntries_ID, Integer companyId, Long lastmodifiedby)
			throws Exception;

	public void updateServiceEntriesMainTotalCost_TAX(Double TotalServiceEntriesTAX, Double TotalServiceEntriescost,
			Long ServiceEntries_ID) throws Exception;

	/* save WorkstasktoLabor */
	public void addServiceEntriesTaskToLabor(ServiceEntriesTasksToLabor ServiceEntriesTaskLabor) throws Exception;

	public void updateServiceEntriesTask_TotalLaborCost(Long TaskID)
			throws Exception;

	public List<ServiceEntriesTasksToLabor> getServiceEntriesTasksToLabor(long ServiceEntries_id);

	public List<ServiceEntriesTasksToLabor> getServiceEntriesTasksToLabor_ID(long ServiceEntriesTask_id, Integer companyId);

	// completion on task update to get
	public ServiceEntriesTasks getServiceEntriesCompletion(Long ServiceEntriesTasksID, Integer companyId) throws Exception;

	public void updateServiceEntriesTask_Completion(Integer completionvalue, Long ServiceEntriesTasksID, Integer companyId)
			throws Exception;

	// count ServiceEntries
	public Long countServiceEntries(Integer companyId) throws Exception;

	public Long countServiceEntriestatues(String Statues, Integer companyId) throws Exception;

	public void updateServiceEntries(ServiceEntries ServiceEntries) throws Exception;

	// public void getServiceEntriesList(ServiceEntries ServiceEntries) throws
	// Exception;

	public List<ServiceEntries> listServiceEntries() throws Exception;

	public List<ServiceEntries> listVehicleServiceEntries(String ServiceEntries_vehiclename) throws Exception;


	public void deleteServiceEntries(Long ServiceEntries_id, Timestamp toDate, Long userId, Integer companyid);

	public void deleteServiceEntriesTask(Long ServiceEntriesTask_id, Integer companyId);

	public void deleteServiceEntriesTaskTOParts(Long ServiceEntriesTask_id, Integer companyId);

	public void deleteServiceEntriesTaskTOLabor(Long ServiceEntriesTask_Laborid, Integer companyId);

	/* New page */
	public List<ServiceEntriesDto> SearchServiceEntries(String ServiceEntries_Search, Integer companyId, Long userId) throws Exception;

	public List<ServiceEntriesDto> SearchServiceEntriesByNumber(Long ServiceEntries_Search, Integer companyId, Long userId) throws Exception;

	
	/* New page */
	public List<ServiceEntriesDto> ReportServiceEntries(String qurey, CustomUserDetails	userDetails) throws Exception;

	// vehicle Inside to ServiceEntries
	public List<ServiceEntriesDto> VehicleToServiceEntriesList(Integer Vehicle_id, Integer companyId) throws Exception;

	// upload Document save
	public void uploadServiceEntriesDocument(ServiceEntriesDocument ServiceEntriesdocument) throws Exception;

	// get Document
	public ServiceEntriesDocument getServiceEntriesDocument(Long ServiceEntries_id) throws Exception;

	// upload to Old Document Update
	public void updateOldServiceEntriesDocument(ServiceEntriesDocument ServiceEntries_id) throws Exception;

	// get last ServiceEntriesjob type and sub type change details
	public List<ServiceEntriesTasks> getLast_ServiceEntriesTasks(Integer jobType, Integer jobsubtype, Integer vehicle_id, Integer companyId);

	// get last ServiceEntriesjob type and sub type change details With
	// serviceEntries_id
	public List<ServiceEntriesTasks> getLast_ServiceEntriesTasks(Integer jobType, Integer jobsubtype, Integer vehicle_id,
			Long serviceEntries_id);

	// validate Vendor name and invoice number
	public List<ServiceEntries> validate_ServiceEntries(Integer vendor_Id, String invoice_no, Integer vehicle_Id, Integer companyId);

	// Report ServiceEntries Vehicle Wise Repair Report
	public List<ServiceEntriesDto> vehicle_WiseRepair_Report(String dateRangeFrom, String dateRangeTo,
			String multipleVehicle, Integer companyId) throws Exception;
	
	public List<ServiceEntriesDto> vehicle_WiseRepair_Report(String dateRangeFrom, String dateRangeTo,
			Long createdById, Integer companyId) throws Exception;
	
	public List<ServiceEntriesDto> vehicle_WiseRepair_Report(String dateRangeFrom, String dateRangeTo,
			Long createdById, String vids, Integer companyId) throws Exception;


	// Report ServiceEntries Vehicle Wise Repair Report
	public List<ServiceEntriesTasksDto> getServiceEntriesTasks_vehicle_WiseRepair_Report(String multipleServiceEntries_id, Integer companyId);

	// Report WorkOrdersTas Vehicle Wise Repair Report
	public List<ServiceEntriesTasksToPartsDto> getServiceEntriesTasksToParts_vehicle_WiseRepair_Report(
			String multipleServiceEntries_id, Integer companyId);

	// Report Search ServiceEntriesTasks Total amount Multiple Vehicle to
	// multipleServiceEntries_id
	public List<Double> ServiceEntriesTasks_vehicle_WiseRepair_TotalAmount(String multipleServiceEntries_id, Integer companyId);

	// service Entries Approval List Filter to Create Approval Entries
	public List<ServiceEntriesDto> listServiceEntries_vendor_APPROVALLISTFilter(Integer vendor_id, String ApprovalQuery, Integer companyId)
			throws Exception;

	// Vendor Approval to Update the status and Approval id also
	public void update_Vendor_ApprovalTO_Status_MULTIP_SERVICE_ID(String ApprovalService_ID, Long Approval_ID,
			short approval_Status) throws Exception;

	// Vendor Approval ID to Show Service Entries
	public List<ServiceEntriesDto> getVendorApproval_IN_SERVICE_Entries_List(Long VendorApproval_Id) throws Exception;
	
	public List<ServiceEntriesDto> getServiceEntiresInVendorApproval(Long VendorApproval_Id) throws Exception;

	// Vendor Approval ID to Show Service Entries
	public List<ServiceEntries> get_Amount_VendorApproval_IN_SERVICE_Entries(Long VendorApproval_Id) throws Exception;

	// update Approval Paid mode in Multiple Service ID
	public void update_Vendor_ApprovalTO_Status_PayDate_Multiple_Service_ID(String ApprovalService_ID,
			java.util.Date paymentDate, Long Approval_ID, short approval_Status, Integer companyId,double paidAmount, double discountAmount) throws Exception;

	/**
	 * Service Entries Round of Cost and the values Complete_date , service
	 * Status change
	 */
	public void updateServiceEntries_ROUNT_OF_COST_COMPLETE_date(Double serviceRountCost, String Process,
			Date COMPLETE_date, Long ServiceEntries_ID) throws Exception;

	/**
	 * Update Vehicle Edit to Vehicle Group using vehicle Id
	 **/
	//public void update_Vehicle_Group_USING_Vehicle_Id(String Vehicle_Group, Integer vehicle_id, long vehicleGroupId, Integer companyId);

	/**
	 * @param b
	 * @param serviceEntries_id
	 */
	public void Update_ServiceEntries_Docuemnt_AvailableValue(Long service_documentid, boolean service_document,
			Long serviceEntries_id, Integer companyId);
	
	public List<ServiceEntriesDto> listOpenServiceEntries(CustomUserDetails	userDetails) throws Exception;
	
	public int getServiceEntriesCountByNumber(Long serviceNumber, Integer companyId) throws Exception;
	
	public void Update_ServiceEntries_Docuemnt_AvailableValue(String service_documentid, boolean service_document,
			Long serviceEntries_id, Integer companyId);
	
	public List<ServiceEntriesDocument>  getServiceEntriesDocumentList(long startLimit, long endLimit) throws Exception;
	
	public long getServiceEntriesDocumentCount() throws Exception;
	
	ValueObject	getVehicleBreakDownDetails(ValueObject	valueObject)throws Exception;

	public List<Double> ServiceEntriesTasks_vehicle_WiseRepair_SumAmount(String serviceEntries_id, Integer company_id);

	public List<ServiceEntriesTasksToPartsDto> getServiceEntriesTasksTo_Most_Parts_Count_Report(Long groupID,
			String dateRangeFrom, String dateRangeTo, Integer company_id, Integer location) throws Exception;
	
	public List<ServiceEntriesDto> getServiceEntriesServiceListOfMonth(TripSheetIncomeDto	incomeDto) throws Exception;
	
	public Long getTotalServiceEntriesCount(String startDate, String endDate,Integer companyId) throws Exception;

	public HashMap<Integer, Long> getALLEmailServiceEntriesDailyWorkStatus(String startDate, String endDate) throws Exception ;
	
	public List<CompanyDto> getALLEmailServiceEntriesDailyWorkForManagers(String startDate, String endDate) throws Exception;
	
	public List<VehicleDto> getVehicleWiseServiceEntries(String query, int companyId) throws Exception;
	
	public ValueObject saveServEntInvDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject getSEInvoiceDeatils(ValueObject valueObject) throws Exception;
	
	public HashMap<Integer, Long> getALLCompletedServiceEntriesByDate(String startDate, String endDate) throws Exception ;
	
	public Long getAllOpenServiceEntriesByDateCount(int companyId) throws Exception;
	
	public ValueObject getServiceEntriesDuePaymentCountAndAmount(int companyId) throws Exception;
	
	public HashMap<Integer, Long> getALLOpenServiceEntriesByDate() throws Exception ;
	
	public Long getALLOpenServiceEntriesBetweenDates(String startDate, String endDate,Integer companyId) throws Exception;
	
	public HashMap<Integer, Long> getALLOpenSevenDaysServiceEntriesByDate(String startDate, String endDate) throws Exception ;
	
	public Long getALLOpenServiceEntriesByDate(String startDate, Integer companyId) throws Exception;
	
	public HashMap<Integer, Long> getALLOpen15MoreDaysServiceEntriesByDate(String startDate) throws Exception ;

	public long getSECountByStatusID(String startDate, String endDate, Integer company_id, short status)throws Exception ;

	public List<ServiceEntriesDto> getSEDetailsBetweenDatesByStatus(Integer compId, String sEStatusQuery)throws Exception ;

	public long getSECompletedCount(String startDate, String endDate, Integer company_id, short closeStatus)throws Exception ;

	public void updatePaymentApprovedSEDetails(Long ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Timestamp expectedPaymentDate, Integer companyId)throws Exception ;//Approval Approved Here

	public void updatePaymentPaidSEDetails(String ApprovalInvoice_ID, Timestamp approval_date, Integer companyId, short service_vendor_paymodeId)throws Exception ;//Approval Paid Here

	public List<TripSheetExpenseDto> getServiceEntriesListForTallyImport(String fromDate, String toDate, Integer companyId,
			String tallyCompany)throws Exception ;
	
	public Long	getServiceEntriesTallyPendingCount(Integer companyId, Integer vid) throws Exception;
	
	public ValueObject  getServiceEntiresPendingList(ValueObject valueObject) throws Exception;
	
	public ValueObject  saveServiceExpenseToTrip(ValueObject valueObject) throws Exception;

	public ServiceEntries getDeletedSEById(Long serviceEntries_id, Integer company_id)throws Exception;
	
	public ValueObject saveServiceEntriesDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject getServiceEntryDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject saveServiceEntryPartDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject saveServiceEntryLabourDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject saveServiceEntryNewTaskDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject deletePartDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject deleteLabourDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject deleteTaskDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject changeStatusToInProgress(ValueObject object) throws Exception;
	
	public ValueObject completeServiceEntry(ValueObject serviceEntryObject) throws Exception;
	
	public ValueObject saveRoundOfDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject uploadServiceEntryDocument(ValueObject valueObject, MultipartFile file) throws Exception;
	
	public ValueObject updateServiceEntryDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject deleteServiceEntryDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject getServiceEntriesList(ValueObject valueObject) throws Exception;
	
	public ValueObject searchServiceEntriesByNumber (ValueObject valueObject) throws Exception;
	
	public ValueObject searchServiceEntries(ValueObject valueObject) throws Exception;
	
	public ValueObject searchServiceEntriesDateWise(ValueObject valueObject) throws Exception;
	
	public ValueObject searchServiceEntriesByDifferentFilters(ValueObject valueObject) throws Exception;
	
	public List<DateWiseVehicleExpenseDto> getDateWiseServiceEntriesExpenseDtoByVid(Integer vid, String fromDate, String toDate,
			Integer companyId) throws Exception;
	
	public List<MonthWiseVehicleExpenseDto> getMonthWiseServiceEntriesExpenseDtoByVid(Integer vid, String fromDate, String toDate,
			Integer companyId) throws Exception;
	
	public List<DateWiseVehicleExpenseDto> getDateWiseServiceEntriesExpenseDtoByRouteId(Integer vid, String fromDate, String toDate,
			Integer companyId, Integer routeId) throws Exception;
	
	public List<DateWiseVehicleExpenseDto> getDateWiseServiceEntriesExpenseDtoByVTId(Integer vid, String fromDate, String toDate,
			Integer companyId, Long vehicleTypeId) throws Exception;
	
	public List<DateWiseVehicleExpenseDto> getDateWiseServiceEntriesExpenseDtoByVTRouteId(Integer vid, String fromDate, String toDate,
			Integer companyId, Long vehicleTypeId, Integer routeId) throws Exception;

	public List<ServiceEntriesAndWorkOrdersDto> getVehicleRepairAndPartConsumptionDetailsInSE(ValueObject valueObject)throws Exception ;
	
	public List<VendorPaymentNotPaidDto> vendorPaymentNotPaidSEList(Integer vendor_id, String dateFrom, String dateTo, Integer companyId) throws Exception;

	public ServiceEntries getServiceEntriesCreatedBetweenDates(String startDate, String endDate, Integer companyId) throws Exception;
	
	public ServiceEntriesDto  getLimitedSEDetails(Long serviceId, Integer companyId) throws Exception;
	
	public ServiceEntries	getServiceEntriesByInvoiceNumber(String invoiceNumber, Integer companyId) throws Exception;
	
	public void updateVendorPaymentDetails(Long approvalId, String paymentDate, short paymentStatus) throws Exception ;
	
	public void updateVendorPaymentCancelDetails(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception;
	public List<ServiceEntriesDto> getUserWiseSEActivityList(String query ,String innerQuery) throws Exception;
	
	public Object[] getWorkOrderCountByStatus(Integer companyId, Long userId) throws Exception;
	
	public List<TripSheetExpenseDto> getServiceEntriesListForTallyImportATC(String fromDate, String toDate, Integer companyId, String tallyCompany)throws Exception ;

	public List<ServiceEntriesRemarkDto> getServiceEntryRemarkDtoList(Integer serviceEntryId) throws Exception;


}
