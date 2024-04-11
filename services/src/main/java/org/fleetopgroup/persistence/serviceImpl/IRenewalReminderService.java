package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.dto.CompanyDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderApprovalDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.RenewalReminderHistoryDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.model.RenewalMailConfiguration;
import org.fleetopgroup.persistence.model.RenewalReminder;
import org.fleetopgroup.persistence.model.RenewalReminderApproval;
import org.fleetopgroup.persistence.model.RenewalReminderDocument;
import org.fleetopgroup.persistence.model.RenewalReminderHistory;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;



public interface IRenewalReminderService {

	public void addRenewalReminder(RenewalReminder renewalReminder);

	public void updateRenewalReminder(RenewalReminder renewalReminder);

	public void updateApproedBy(Long renewal_id, short renewal_status, Long renewal_approvedby,
			String renewal_approvedComment, Date renewal_approveddate, Integer companyId);

	//public void getRenewalReminderList(RenewalReminder renewalReminder);

	/**This List get RenewalReminder table to get  pagination  last 10 entries values */
	public List<RenewalReminderDto> Find_listRenewalReminder(short status, Integer pageNumber, CustomUserDetails userDetails) throws Exception;
	
	/**This Page get RenewalReminder table to get  pagination values */
	public Page<RenewalReminder> getDeployment_Page_RenewalReminder(short status, Integer pageNumber, CustomUserDetails 	userDetails) throws Exception;
	

	public List<RenewalReminderDto> listVehicleRenewalReminder(String renewal_vehiclename);
	
	public List<RenewalReminderDto> listVehicleRenewalReminder(Integer vid, Integer companyId);

	public List<RenewalReminderDto> listVehicleRenewalReminder(Integer renewal_vehiclename, String Todate, Integer companyId);

	public RenewalReminderDto getRenewalReminder(Long renewal_id) throws Exception;

	public void deleteRenewalReminder(Long renewalReminder, Integer companyId ,Long userId ,Timestamp toDate);

	public void addRenewalReminderHistory(RenewalReminderHistory renewalReminderHistory);

	public void updateRenewalReminderHistory(RenewalReminderHistory renewalReminderHistory);

	public void getRenewalReminderHistoryList(Integer driver_id);

	public List<RenewalReminderHistoryDto> getRenewalReminderHistory(Integer companyId) throws Exception;

	public List<RenewalReminderHistoryDto> listRenewalReminderHistory(Integer vehicle_id) throws Exception;

	public RenewalReminderHistory getRenewalReminderHistory(Long renewalhis_id);

	public void deleteRenewalReminderHistory(Long renewalReminderHistory, Integer companyId);

	public List<RenewalReminder> listRenewalReminderReport(RenewalReminderDto renewalReminder) throws Exception;

	public List<RenewalReminder> listRenewalReminderReportVehicle(RenewalReminderDto renewalReminder);

	public List<RenewalReminder> listRenewalReminderReportType(RenewalReminderDto renewalReminder);

	public List<RenewalReminder> listRenewalReminderReportSubType(RenewalReminderDto renewalReminder);

	public List<RenewalReminderDto> listRenewalReminder(String query,ValueObject object) throws Exception;
	

	public List<RenewalReminderDto> Validate_RenewalReminder(String query);

	public Long countRenewalReminder() throws Exception;

	public Long countTodayDueRenewalReminder(String toDate) throws Exception;
	
	public List<RenewalReminderDto> SearchRenewalReminder(String Search) throws Exception;

	public List<RenewalReminderDto> TodayRenewalReminderList(String toDate) throws Exception;

	// RenewalReminder Monthly  Report
	public String getRenewalReminderMonthlyReport(Date sql);

	/**
	 * @param query
	 * @return
	 */
	public List<RenewalReminderDto> Validate_RenewalReminder_VehicleMandatoryCompliances(String query);

	/**
	 * @param rewalDoc
	 */
	public void add_RenewalReminder_Document(RenewalReminderDocument rewalDoc);

	/**
	 * @param renewal_id
	 * @return
	 */
	public RenewalReminderDocument get_RenewalReminder_Document(Long renewal_id);

	/**
	 * @param renewalhis_id
	 * @return
	 */
	public RenewalReminderDocument get_RenewalReminderHistory_Document(Long renewalhis_id);



	/**
	 * @param renewalhis_id
	 */
	public void delete_RenewalReminderDocument_ID_History(Long renewalhis_id, Integer companyId);

	/**
	 * @param rendoc_id
	 * @param b
	 * @param renewal_id
	 */
	public void Update_RenewalReminderDocument_ID_to_RenewalReminder(Long rendoc_id, boolean b, Long renewal_id, Integer companyId);

	/**
	 * @param approval
	 */
	public void add_RenewalReminderApproval(RenewalReminderApproval approval);

	/**
	 * @param renewalApproval_id
	 */
	public void Update_ApproavalPayment_Amount_Get_Apprival_RR(Long renewalApproval_id, Integer companyId);

	/**
	 * @param approvalID
	 * @return
	 */
	public RenewalReminderApproval Get_RenewalReminderApproval(Long approvalID, Integer companyId) throws Exception;
	
	public RenewalReminderApprovalDto Get_RenewalReminderApprovalDetails(Long approvalID, Integer companyId) throws Exception;

	/**
	 * @param approvalID
	 * @return
	 */
	public List<RenewalReminder> Find_Approval_RenewalReminder(Long approvalID, Integer companyId) throws Exception;
	
	
	public List<RenewalReminderDto> Find_Approval_RenewalReminderList(Long approvalID, CustomUserDetails userDetails) throws Exception;

	/**
	 * @param pageNumber
	 * @return
	 */
	public Page<RenewalReminderApproval> getDeployment_Page_RenewalReminderApproval(short Status, Integer pageNumber, CustomUserDetails	userDetails) throws Exception;

	/**
	 * @param pageNumber
	 * @return
	 */
	public List<RenewalReminderApprovalDto> Find_listRenewalReminderApproval(short Status, Integer pageNumber, CustomUserDetails	userDetails) throws Exception;

	/**
	 * @param renewalApproval_id
	 * @return
	 */
	public Double Get_ApprovalID_to_RenewalReminder_Amount(Long renewalApproval_id);

	/**
	 * @param renewal_Amount
	 * @param renewalApproval_id
	 */
	public void Update_ADD_ApproavalPayment_Amount(Double renewal_Amount, Long renewalApproval_id);

	/**
	 * @param renewalApproval_id
	 */
	public void delete_RenewalReminder_To_Approval_ID(Long renewalApproval_id, Integer companyId);

	/**
	 * @param renewalApproval_id
	 */
	public void delete_RenewalReminderApproval_ID(Long renewalApproval_id, Integer companyId);

	/**
	 * @param approvalCreated_By
	 * @param toDate
	 * @param string
	 * @param renewalApproval_id
	 */
	public void update_RenewalReminderApproval_ApprovedBY_andDate(Long approvalCreated_By, Timestamp toDate,
			 Long renewalApproval_id, Integer companyId, short statusid);

	/**
	 * @param approvalPayment_Type
	 * @param approvalPay_Number
	 * @param toDate
	 * @param approvalPayment_By
	 * @param string
	 * @param lastUpdatedBy
	 * @param toDate2
	 * @param renewalApproval_id
	 */
	public void Update_RenewalReminder_ApprovedPayment_Details(Double approvalPending_Amount,
			String approvalPay_Number,
			Timestamp toDate, Long approvalPayment_By, Long lastUpdatedBy, Timestamp toDate2,
			Long renewalApproval_id, Integer companyId, short staus, short paymentTypeId);

	/**
	 * @param approvalPayment_Type
	 * @param approvalPay_Number
	 * @param approvalPayment_By
	 * @param toDate
	 * @param string
	 * @param renewalApproval_id
	 */
	public void Update_RenewalReminderApproval_ID_To_Payment_Status_Change(
			String approvalPay_Number, Long approvalPayment_By, Timestamp toDate, 
			Long renewalApproval_id, Integer companyId, short status, short paymentTypeId);

	/**
	 * @param renewal_receipt
	 * @param renewal_document
	 * @param renewal_authorization
	 * @param renewal_number
	 * @param renewal_status
	 * @param renewal_id
	 */
	public void Update_RenewalReminder_Upload_File(String renewal_receipt, boolean renewal_document,
			String renewal_authorization, String renewal_number, short renewal_status, Long renewal_id, Integer companyId);

	/**
	 * @param searchDate
	 * @return
	 */
	public List<RenewalReminderApprovalDto> Search_RenewalReminderApproval(String searchDate);

	/**
	 * @param renewal_Amount
	 * @param renewal_approvedID
	 */
	public void Subtract_PendingAmount_Add_Approval_APPROVED_Amount_Value(Double renewal_Amount,
			Long renewal_approvedID, Integer companyId);

	/**
	 * @param rewalDoc
	 */
	public void add_RenewalReminderApproval_Document(org.fleetopgroup.persistence.document.RenewalReminderAppDocument rewalDoc);

	/**
	 * @param rendoc_id
	 * @param b
	 * @param renewalApproval_id
	 */
	public void Update_RenewalReminderApprovalDocument_ID_to_RenewalReminderApproval(Long rendoc_id, boolean b,
			Long renewalApproval_id, Integer companyId);

	/**
	 * @param renewal_id
	 * @return
	 */
	public org.fleetopgroup.persistence.document.RenewalReminderAppDocument Get_RenewalReminderApproval_Document(Long renewal_id);

	/**
	 * @param renewalAmount
	 * @param renewalID
	 */
	public void Update_RenewalReminder_Approval_Amount(Double renewalAmount, Long renewalID, Integer companyId);

	/**
	 * @param renewal_paymentType
	 * @param renewal_PayNumber
	 * @param renewal_paidby
	 * @param renewal_dateofpayment
	 * @param renewal_id
	 */
	public void update_RenewalReminder_DD_Number(String renewal_PayNumber,
			Long renewal_paidby, java.util.Date renewal_dateofpayment, Long renewal_id, Integer companyId, short paymentTypeId);

	/**
	 * @param renewal_status
	 * @param firstName
	 * @param renewal_approvedComment
	 * @param date
	 * @param date2
	 * @param renewal_id
	 */
	public void update_Cancel_ApprovalID_to_Status_changeDate_From_To(short renewal_status, Long firstName,
			String renewal_approvedComment, java.util.Date From, java.util.Date To, Long renewal_id, Integer companyId);

	/**
	 * @param renewal_Amount
	 * @param renewal_approvedID
	 */
	public void Add_Approval_Cancel_Amount_Value(Double renewal_Amount, Long renewal_approvedID, Integer companyId);

	/**
	 * @param vEHICLEGROUP
	 * @param format
	 * @return
	 */
	public ArrayList<RenewalReminderDto> RENEWAL_REMINDER_GROUP_WISE_REPORT(String vEHICLEGROUP, String format);

	public Object[] count_TOTAL_RENEWAL_REMINDER_FIVEDAYS_ISSUES(String dayOne, String dayTwo, String dayThree,
			String dayFour, String dayFive) throws Exception;
	
	public List<RenewalReminderDto> Find_listRenewalReminder(String status) throws Exception;
	
	public RenewalReminder getRenewalReminderById(Long renewal_id) throws Exception;
	
	//public List<RenewalReminderApproval> Find_listRenewalReminderApproval(String Status) throws Exception;
	
	/**
	 * @param approval_Number
	 * @return
	 */
	public RenewalReminderApprovalDto Get_RenewalReminderApprovalByApprovalNumber(Long approval_Number, Integer companyId, Long userId) throws Exception;

	public RenewalReminderDto getRenewalReminderById(Long renewal_id, CustomUserDetails	userDetails) throws Exception;
	
	public void saveRenewalDocHistory(org.fleetopgroup.persistence.document.RenewalReminderDocumentHistory	documentHistory) throws Exception;
	
	public void updateRenewalPeriod(RenewalReminder renewalReminder) throws Exception;
	
	public List<RenewalReminderDto> getVehicleRenewalExpenses(Integer vid, Timestamp startDateOfMonth, Integer companyId, String endDateOfMonth) throws Exception;
	
	public List<RenewalReminderDto> getGroupRenewalExpenses(long vehicleGroupId, Timestamp startDateOfMonth, Integer companyId, String toEndDate) throws Exception;

	public Object saveEmailRenewalReminder(ValueObject object)throws Exception;

	List<RenewalMailConfiguration> getAllEmailRenewalReminder() throws Exception;

	public List<RenewalReminderDto> get_listRenewalReminderMonthWise(String startDate, String endDate, Integer companyId) throws Exception;	

	List<RenewalMailConfiguration> getAllEmail_ById(Integer companyId) throws Exception;

	void update_Email_ById(String email, long userId, Long configurationId,Integer companyId) throws Exception;

	public List<RenewalMailConfiguration> getAllEmailTripSheetDailyWorkStatus() throws Exception;
	
	public Long getRenewalReminderCount(String startDate, String endDate,Integer companyId) throws Exception;

	public  HashMap<Integer, Long> getALLEmailRenewalRemindersDailyWorkStatus(String startDate, String endDate) throws Exception; 
	
	public ValueObject configureDailyWorkStatusEmailBody(ValueObject valueInObject) throws Exception;

	public List<CompanyDto> getALLEmailRenewalRemindersDailyWorkForManagers(String startDate, String endDate) throws Exception;

	public List<RenewalReminderDto> getRenewalReminderOverDue(Long userId, Integer companyId)throws Exception;
	
	ValueObject	getRenewalExpiryDataReport(ValueObject	valueObject) throws Exception;

	public void updateNewRRCreated(Integer	vid, Integer renewalType, Integer renewalSubType) throws Exception;
	
	public ValueObject getTotalOverDueRenewalCount(String startDate, Integer companyId) throws Exception;
	
	public  HashMap<Integer, Long> getAllOverDueRenewalReminders(String startDate) throws Exception;
	
	public Long getTotalOverDueRenewalCountBetweenDates(String startDate, String endDate,Integer companyId) throws Exception;
	
	public  HashMap<Integer, Long> getAllOverDueRenewalReminders(String startDate, String endDate) throws Exception;
	
	public List<RenewalReminderDto> getVehicleRenewalExpensesDateRange(Integer vid, String fromDate, String toDate) throws Exception;
	
	public List<RenewalReminderDto> getAllOverDueRenewalList(Date startDate,ValueObject valueObject, CustomUserDetails 	userDetails) throws Exception;
	
	public Page<RenewalReminder> getDeployment_Page_RenewalOverDue(Date startDate, ValueObject valueObject, CustomUserDetails 	userDetails) throws Exception;
	
	public  Long getAllOverDueRenewalRemindersCount(Date startDate, CustomUserDetails 	userDetails) throws Exception;
	
	public List<RenewalReminderDto> getAllDueSoonRenewalList(Date startDate, ValueObject valueObject, CustomUserDetails 	userDetails) throws Exception;
	
	public Page<RenewalReminder> getDeployment_Page_RenewalDueSoon(Date startDate, ValueObject valueObject, CustomUserDetails 	userDetails) throws Exception;

	public long todaysTotalRenewalCount (String startDate, String endDate, int companyId) throws Exception;
	
	public ValueObject totalDueSoonCount (String date, int companyId) throws Exception;	

	public ValueObject getMonthlyRenwalCountAndAmount(String startDate, String endDate, int companyId) throws Exception;
	
	public long totalDueSoonCountBetweenDates (String startDate, String endDate, int companyId) throws Exception;
	
	public List<RenewalReminderDto> getRenewalReminderTableListBetweenDates(String query,Integer companyID) throws Exception;
	
	public List<RenewalReminderDto> getRenewalReminderGroupByRenewalTypeBetweenDates(String query, Integer companyID) throws Exception;
	
	public double totalRenewalExpense (String currentDate, int companyId) throws Exception;
	
	ValueObject	getRenewalOverDueReport(ValueObject	valueObject) throws Exception;
	
	public Long	getRenewalTallyPendingCount(Integer companyId, Integer vid) throws Exception;
	
	public ValueObject  getRenewalReminderPendingList(ValueObject	valueObject) throws Exception;
	
	public ValueObject  saveRenewalPendingDetails(ValueObject	valueObject) throws Exception;
	
	public void updatePendingStatus(Long id, short status) throws Exception;
	
	public ValueObject getRenewalReminderInitialConfigDataForMobile(ValueObject object) throws Exception;
	
	public ValueObject saveRenewalReminderFromMobile(ValueObject valueObject, MultipartFile[] file) throws Exception;
	
	public ValueObject getRenewalReminderShowDataForMobile(ValueObject object) throws Exception;
	
	public ValueObject searchRenewalByNumber(ValueObject object) throws Exception;
	
	public List<RenewalReminderDto> getListOfNumberOfRRCreatedOnVehicles(Integer companyID) throws Exception;
	
	public List<RenewalReminderDto> getListOfAllRRVehicleWise(Integer companyID) throws Exception;
	
	public List<RenewalReminderDto> getRenewalReminderListByVehicle(int vehicleId, CustomUserDetails userDetails) throws Exception;
	
	public ValueObject searchRenewalByVehicle(ValueObject object) throws Exception;
	
	public List<TripSheetExpenseDto> getRenewalListForTallyImport(String fromDate, String toDate, Integer companyId,
			String tallyCompany, boolean setFixedRenewalLedgerName, String renewalLedgerName)throws Exception ;
	
	public List<TripSheetExpenseDto> getRenewalListForTallyImportATC(String fromDate, String toDate, Integer companyId,
			String tallyCompany, boolean setFixedRenewalLedgerName, String renewalLedgerName)throws Exception ;
	
	public ValueObject getRenewalMandatoryList(ValueObject object) throws Exception;
	
	public ValueObject getVehicleAndRenewalTypeDetails(ValueObject object) throws Exception;
	
	public ValueObject updateRenewalReminderDetails(ValueObject valueObject, MultipartFile[] file) throws Exception;
	
	public ValueObject getRenewalReminderListByVehicle(ValueObject object) throws Exception;
	
	public ValueObject getRenewalDetailsByRenewalId(ValueObject object) throws Exception;
	
	public ValueObject deleteRenewalReminderById(ValueObject object) throws Exception;
	
	public ValueObject getRenewalReminderDetailsOpenList(ValueObject valueObject) throws Exception;
	
	public ValueObject getRenewalReminderDetailsOverdueList(ValueObject valueObject) throws Exception;
	
	public ValueObject getVehicleWiseRenewalReminder(ValueObject object) throws Exception;
	
	public ValueObject getRenewalReminderHistoryByVehicle(ValueObject object) throws Exception;
	
	public ValueObject getRenewalReminderDetailsDueSoonList(ValueObject valueObject) throws Exception;
	
	public ValueObject serachRenewalReminderByNumber(ValueObject object) throws Exception;
	
	public ValueObject searchRRByDifferentFilter(ValueObject object) throws Exception;
	
	public ValueObject searchRRDateWise(ValueObject object) throws Exception;
	
	public ValueObject searchRenRemndReport(ValueObject object) throws Exception;
	
	public ValueObject reviseRenewalReminderDetails(ValueObject valueObject, MultipartFile[] file) throws Exception;
	
	public ValueObject updateRenewalPeriodDetails(ValueObject object) throws Exception;
	
	public ValueObject updateRenewalDocumentsDetails(ValueObject object, MultipartFile[] file) throws Exception;
	
	public ValueObject uploadRenewalCSVFile(ValueObject object, MultipartFile file, HttpServletRequest request) throws Exception;

	public ValueObject getRRListByDate(ValueObject object)throws Exception;

	public List<RenewalReminderDto> listVehicleActiveRenewalReminder(Integer vid, Integer companyId);
	
	
	public List<RenewalReminderDto> getRenewalMandatoryListByVehicle(String query,
			int companyId) throws Exception;

	public ValueObject getMandatoryRenewalPendingReport(ValueObject object)throws Exception;
	
	public ValueObject searchRenRemndReportForMob(ValueObject object) throws Exception;
	
	public List<RenewalReminderDto> getUserWiseActivityRRData(String queryStr,String innerQuery) throws Exception;
	
	public ValueObject removeRenewalDocument(ValueObject object) throws Exception;
	
	public ValueObject ignoreRenewalReminder(ValueObject object);
	
	public ValueObject getRenewalReminderReport(ValueObject object);

	public long getRenewalReminderListBeforeFiveDaysDue(Integer companyId, Integer threshold) throws Exception;
}
