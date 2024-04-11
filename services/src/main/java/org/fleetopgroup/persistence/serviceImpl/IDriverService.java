package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.document.DriverPhoto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverCommentDto;
import org.fleetopgroup.persistence.dto.DriverDocumentDto;
import org.fleetopgroup.persistence.dto.DriverDocumentHistoryDto;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.dto.DriverReminderDto;
import org.fleetopgroup.persistence.dto.DriverReminderHistoryDto;
import org.fleetopgroup.persistence.dto.DriverSalaryAdvanceDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.DriverComment;
import org.fleetopgroup.persistence.model.DriverDocument;
import org.fleetopgroup.persistence.model.DriverDocumentHistory;
import org.fleetopgroup.persistence.model.DriverReminder;
import org.fleetopgroup.persistence.model.DriverReminderHistory;
import org.fleetopgroup.persistence.model.DriverSalaryAdvance;
import org.fleetopgroup.persistence.model.UploadFile;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface IDriverService {
	
	
	public Driver addDriver(Driver driver) throws Exception;
	
	public List<Driver> ValidateDriver(String driver_DLnumber, String driver_Empnumber, Integer companyId)throws Exception;
	
	public List<Driver> ValidateEmpDriver(String driver_Empnumber, Integer companyId)throws Exception;
	
	public List<Driver> ValidateDriver(String driver_Empnumber, Integer companyId)throws Exception;

	public void updateDriver(Driver driver)throws Exception;

	public void addFileDriver(UploadFile upload) throws Exception;

	/**This Page get Driver table to get  pagination values */
	public Page<Driver> getDeployment_Page_Driver(Integer Job_Type, Integer pageNumber, CustomUserDetails userDetails) throws Exception;
	
	/**This Page get Driver table to get  pagination values */
	public List<DriverDto> listDriver(Integer Job_Type, Integer pageNumber, CustomUserDetails userDetails) throws Exception;

	public DriverDto getDriver_Details_Firstnamr_LastName(int driver_id, int companyId)throws Exception;

	//public List<Driver> fildAllDriverList()throws Exception;
	
	//public List<Driver> fildAllCleanerList()throws Exception;

	public Driver getDriver(int driver_id, CustomUserDetails userDetails)throws Exception;
	
	public DriverDto getDriverDetails(int driver_id, CustomUserDetails userDetails)throws Exception;
	
	public Long getCurrentTripSheetNumber(long tripSheetId, Integer companyId) throws Exception;
	
	/** Select Vendor Details Fuel Entries */
	public Driver getDriver_Details_Fuel_entries(int driver_id)throws Exception;
	
	public Driver getDriverReg(String driver_register)throws Exception;

	/** Select Vendor Details Fuel Import Entries */
	public Driver GetDriverEmpNumberToName(String empnumber) throws Exception;

	/** Select Deriver Header Details Show */
	public DriverDto getDriver_Header_Show_Details(Integer driver_id) throws Exception ;
	
	/** Select Deriver Header Details Show */
	public DriverDto getDriver_Header_Show_Details_RenewalReminder(Integer driver_id,Integer companyId) throws Exception;

	/** Select Deriver Header Details Show */
	public DriverDto getDriver_Header_Show_Attandance_perday_esi_Details(Integer driver_id, Integer companyId) throws Exception;

	/** Select Vendor Details Issues Entries */
	public Driver getDriver_Details_Issues_entries(int driver_id) throws Exception;
	
	
	public void deleteDriver(Long userId, Timestamp toDate, Integer driverId, Integer companyId) throws Exception;

	public void setprofilePhoto(int Driverphoto_id, int driver_id) throws Exception;

	public void addDriverReminder(DriverReminder diverreminder) throws Exception;

	public void updateDriverReminder(DriverReminder diverreminder) throws Exception;

	public List<DriverReminder> listDriverReminder() throws Exception;

	public List<DriverReminderDto> listDriverReminder(int diverreminder, Integer companyId) throws Exception;

	public List<DriverReminderDto> listLatestDriverDLReminder(int driverId, Integer companyId) throws Exception;
	
	public List<DriverReminderDto> Showlist_OF_Current_DriverReminder(int diverreminder, String Todate) throws Exception;

	public void getDriverReminderList(Integer diverreminder) throws Exception;

	public DriverReminderDto getDriverReminder(int driver_remid, Integer companyId) throws Exception;

	public void deleteDriverReminder(Integer diverreminder, Integer companyId) throws Exception;

	public Long getReminderCount(int driver_remid) throws Exception;

	public void saveDriverReminderHistory(DriverReminderHistory driverReminderHistory) throws Exception;

	public void updateDriverReminderHistory(DriverReminderHistory driverReminderHistory) throws Exception;

	public List<DriverReminderHistoryDto> listDriverReminderHis(int diverRemHis_id, Integer companyId) throws Exception;

	public DriverReminderHistory getDriverReminderHis(int driver_RemHisid, Integer companyId) throws Exception;

	public void deleteDriverReminderHis(int driver_RemHistid, Integer companyId) throws Exception;

	public void saveDriverDocument(DriverDocument driverDocument) throws Exception;

	public void updateDriverDocument(DriverDocument driverDocument) throws Exception;

	public List<DriverDocumentDto> listDriverDocument(int diverreminder, Integer companyId) throws Exception;

	public DriverDocumentDto getDriverDocuemnt(int driver_docid, Integer companyId) throws Exception;
	
	public Long getDocuemntCount(int driver_docid) throws Exception;

	public void deleteDriverDocument(int driver_documentid, Integer companyId) throws Exception;
	
	public void saveDriverDocumentHistory(DriverDocumentHistory driverDocumentHistory) throws Exception;

	public void updateDriverDocumentHistory(DriverDocumentHistory driverDocumentHistory) throws Exception;

	public List<DriverDocumentHistoryDto> listDriverDocHis(int diverDocHis_id, Integer companyId) throws Exception;

	public DriverDocumentHistory getDriverDocHis(int diverDocHis_id, Integer companyId) throws Exception;

	public void deleteDriverDocHis(int driver_doHistid, Integer companyId) throws Exception;

	public void addDriverComment(DriverComment diverComment) throws Exception;

	public void updateDriverComment(DriverComment diverComment) throws Exception;

	public List<DriverCommentDto> listDriverComment(int diverComment, Integer companyId) throws Exception;

	public DriverComment getDriverComment(Long driver_commentid) throws Exception;

	public void deleteDriverComment(Long driver_commentid, Integer companyId) throws Exception;
	
	public Long getCommentCount(int driver_docid) throws Exception;

	public void addDriverPhoto(org.fleetopgroup.persistence.document.DriverPhoto diverPhoto) throws Exception;

	public void updateDriverPhoto(org.fleetopgroup.persistence.document.DriverPhoto diverPhoto) throws Exception;

	public List<DriverPhoto> listDriverPhoto(int diverPhoto, Integer companyId) throws Exception;

	public org.fleetopgroup.persistence.document.DriverPhoto getDriverPhoto(int driver_photoid) throws Exception;
	
	public DriverPhoto getDriverPhoto(int driver_photoid, Integer companyId) throws Exception;
	
	public Long getPhotoCount(int driver_docid) throws Exception;

	public void deleteDriverPhoto(int driver_Photoid, Integer companyId) throws Exception;

	public List<Driver> listDriverJOB_LC() throws Exception;

	public List<Driver> listDriverReport() throws Exception;

	public List<DriverDto> listDriverReport(String Query, CustomUserDetails userDetails) throws Exception;

	public Long countTotalDriver() throws Exception;

	public Long countActiveDriver() throws Exception;

	public List<DriverDto> SearchDriver(String Search) throws Exception;
	
	public List<Driver> Search_ALL_Driver_LIST_AJAX(String Search) throws Exception;

	public List<Driver> SearchOnlyDriverNAME(String Search) throws Exception;
	
	public List<Driver> searchByDriverName(String Search, int companyId, long userId) throws Exception;
	
	public List<Driver> SearchOnlyAllDriverNAME(String Search) throws Exception;
	
	public List<Driver> SearchOnlyAllCleanerNAME(String Search) throws Exception;
	
	public List<Driver> SearchOnly_Techinicion_NAME(String Search, Integer companyId) throws Exception;

	public List<Driver> SearchOnlyConductorNAME(String Search) throws Exception;
	
	public List<Driver> SearchOnlyCheckingInspectorNAME(String Search) throws Exception;
	
	public List<Driver> SearchOnlyCheckingInspectorNAME(Long vehicleGroupId) throws Exception;
	
	public List<Driver> getCheckingInspectorList(Integer companyId, Long id) throws Exception;
	
	public List<Driver> getConductorList(Integer companyId, Long id, String term) throws Exception;

	public List<Driver> SearchOnlyCleanerNAME(String Search) throws Exception;
	
	public List<Driver> searchByCleanerName(String Search, int companyId, long userId) throws Exception;
	
	public Long countToDay_DL_Renewal() throws Exception;
	
	public List<DriverReminderDto> listof_ToDay_DL_Renewal(String currentdate) throws Exception;
	
	
	/** This interface is Driver count Total Driver and Today DL Renewal */
	public Object[] countTotalDriver_AND_TodayRenewal() throws Exception;
	
	/** This interface is Driver count Total Reminder, Document, Comment, Photo using to get driver_ID*/
	public Object[] countTotal_REMINDER_DOC_COMMENT__AND_PHOTO(Integer Driver_ID) throws Exception;

	/**
	 * This interface is Driver count Comment last 3 months and Fuel mileage,
	 * Issues using to get driver_ID
	 */
	public Object[] countTotal_LAST_COMMENT_ISSUES_FUELMILEAGE_REPORT(Integer Driver_ID, String fromdate, String To, Integer companyId)
			throws Exception;

	/**
	 * @param string
	 * @param tripFristDriverID
	 */
	public void Update_Driver_Status(short DriveRStatus_ID, int tripFristDriverID) throws Exception;

	/**
	 * @param string
	 * @param tripSheetID
	 * @param tripFristDriverID
	 */
	public void Update_Driver_Status_TripSheetID(short DriveRStatus_ID, Long tripSheetID, int tripFristDriverID, int compId) throws Exception;

	/**
	 * @param tripFristDriverID
	 * @return
	 */
	public DriverDto Get_Driver_Current_Status_TripSheetID(int tripFristDriverID, Integer companyId) throws Exception;

	/**
	 * @param query
	 * @return
	 */
	public List<DriverCommentDto> List_DriverComment_Remarks_Report(String query, Integer companyId) throws Exception;

	/**
	 * @param query
	 * @return
	 */
	public List<DriverReminderDto> DRIVER_RENEWALREMINDER_DL_REPORT(String Fromdate, String Todate, CustomUserDetails userDetails) throws Exception;
	
	
	public List<DriverDto> listDriver(String Job_Type) throws Exception;
	
	
	/**
	 * @param tripFristDriverID
	 * @return
	 */
	public DriverDto Get_Driver_Current_Status_TripSheetID(int tripFristDriverID) throws Exception;

	public List<Driver> Search_ALL_Driver_LIST_AJAX(String Search, CustomUserDetails 	customUserDetails) throws Exception;
	
	public List<Driver> getDriverAndConductorListByVehicleGroupId(long vehicleGroupId, Integer companyId) throws Exception;
	
	public List<Driver> getConductorListByVehicleGroupId(long vehicleGroupId, Integer companyId) throws Exception;
	
	public List<Driver> getDriverListByVehicleGroupId(long vehicleGroupId, Integer companyId) throws Exception;
	
	public List<Driver> getConductorListByVehicleGroupId(String term , long vehicleGroupId, Integer companyId) throws Exception;
	
	public List<Driver> SearchOnlyDriverNAME(String Search , CustomUserDetails 	customUserDetails) throws Exception;
	
	public DriverDto getDriver_Header_Show_Details_ESI_PF(Integer driver_id) throws Exception ;

	public void Update_DriverHalt_Status_To_Active(String UpdateDate) throws Exception ;
	
	public void updateDriverSalaryByGroupId(DriverDto driverDto, Integer companyId) throws Exception;
	
	ValueObject	getDriverCommentDetails(ValueObject valueInObeject) throws Exception;

	public void transferDriverPhoto(List<org.fleetopgroup.persistence.model.DriverPhoto> driverPhotoList) throws Exception;
	
	public long getDriverPhotoMaxId() throws Exception;

	//public List<Driver> getConductorListByVehicleGroupId(Long vehicleGroupId, Integer company_id);

	public List<Driver> getConductorListByVehicleGroupId2(long vehicleGroupId, Integer companyId) throws Exception;
	
	ValueObject		getDriverEngagementAndPerformanceReport(ValueObject	valueObject) throws Exception;

	public List<DriverReminderDto> getListDriverReminderMonthWise(String startDateOfMonth, String endDateOfMonth,Integer companyId) throws Exception;
	
	public ValueObject getDriverNameWiseList(ValueObject object) throws Exception;
	
	public ValueObject getCleanerNameWiseList(ValueObject object) throws Exception;
	
	public ValueObject getDriverTripsheetAdvanceReport(ValueObject valueObject) throws Exception;

	public ValueObject saveDriverDetails(ValueObject valueInObject,MultipartFile file)throws Exception;

	public ValueObject updateDriverDetails(ValueObject valueObjectFormSimpleJsonString)throws Exception;
	
	public ValueObject getDriverRenewalTypeList(ValueObject object) throws Exception;
	
	public ValueObject saveDriverRenewalFromMobile(ValueObject object) throws Exception;
	
	public ValueObject showDriverRenewalFromMobile(ValueObject object) throws Exception;
	
	public ValueObject searchDriverRenewalByDriverNumber(ValueObject object) throws Exception;
	
	public Driver getLimitedDriverDetails(Integer driverId) throws Exception;
	
	HashMap<Integer, DriverDto>  getDriverStatusHM(String driverIds) throws Exception;
	
	public ValueObject	getCountByDriverJObId(ValueObject	valueObject) throws Exception;

	public List<Driver> getActiveDriverList(Integer company_id, short driverStatusActive)throws Exception;

	public ValueObject getDefaultDriverForVehicle(ValueObject object)throws Exception;
	
	public ValueObject	getDriverDocuments(ValueObject	valueObject) throws Exception;

	public List<Driver> getDriverDetailsNotInSuspend(String Search, CustomUserDetails userDetails) throws Exception;
	
	public ValueObject totalDueSoonCount (Date date, int companyId) throws Exception;	
	
	public ValueObject getTotalOverDueRenewalCount(Date startDate, Integer companyId) throws Exception;
	
	public ValueObject getOverDueData(ValueObject valueObject) throws Exception;
	
	public List<Driver> SearchALLDriverTypeListAJAX(String Search ,String query, CustomUserDetails userDetails) throws Exception;
	
	public ValueObject addDriverRenewalReceipt(ValueObject object , MultipartFile file) throws  Exception;
	
	public DriverReminder getExistingDriverReminder(int driverId,Long driverRenewalTypeId, Date fromDate ,Date toDate ,Integer companyId,String query);
	
	public DriverReminder getPreviousDriverReminder(int driverId,Long driverRenewalTypeId,Integer companyId ,String query) throws Exception;
	
	public void updateOldDriverReminder(Integer diverreminder, Integer companyId) ;
	
	public ValueObject getDriverrenewalReminderCount( Integer companyId) throws Exception;
	
	
	public ValueObject deleteRenewalReciept(ValueObject object) throws Exception ;
	
	public void deleteDriverRenewalReceiptById(long driverRenewalReceiptId, int companyId ) throws Exception;
	
	public void updateDriverRenewalReceiptStatus(long driverRenewalReceiptId, int companyId ) throws Exception;
	
	public ValueObject getDriverDriverWiseIssueList(ValueObject object);
	
	public ValueObject getDriverCommentsList(ValueObject object);
	
	public ValueObject getDriverFuelMileage(ValueObject object);
	
	public ValueObject getDriverDetailsReportList(ValueObject object);
	
	public ValueObject getDriverRenewalReminderReport(ValueObject object); 
	
	public ValueObject getDriverIncidentReport(ValueObject valueObject);
	
	public ValueObject getDriverWiseIncidentDetails(ValueObject valueObject);

	public ValueObject getDriverCurrentStatusForTripsheet(ValueObject object);

	public List<TripSheetDto> getDriverSalaryReportList(String query);

	public ValueObject getAllDriverAndTripsheetDetails(ValueObject object);

	public List<DriverSalaryAdvanceDto> getDriverSalaryAdvanceDetails(String dateRangeFrom, String dateRangeTo, Integer driverId);

	public Integer getDriverByDriverLicense(String string);
	
	public List<DriverReminderDto> listof_Tomorrow_DL_Renewal(String currentdate) throws Exception;
	
	public List<DriverReminderDto> listof_nextSevenDay_DL_Renewal(String currentdate) throws Exception;
	
	public List<DriverReminderDto> listof_nextFifteenDay_DL_Renewal(String currentdate) throws Exception;
	
	public List<DriverReminderDto> listof_nextMonthDay_DL_Renewal(String currentdate) throws Exception;
	
	/** This interface is Driver count Total Driver and Tomorrow DL Renewal */
	public Object[] countTotalDriver_AND_TomorrowRenewal() throws Exception;
	
	/** This interface is Driver count Total Driver and Next Month DL Renewal */
	public Object[] countTotalDriver_AND_nextSevenDayRenewal() throws Exception;
	
	/** This interface is Driver count Total Driver and Next FifteenDay DL Renewal */
	public Object[] countTotalDriver_AND_nextFifteenDayRenewal() throws Exception;
	
	/** This interface is Driver count Total Driver and Next Month DL Renewal */
	public Object[] countTotalDriver_AND_nextMonthDayRenewal() throws Exception;

}
