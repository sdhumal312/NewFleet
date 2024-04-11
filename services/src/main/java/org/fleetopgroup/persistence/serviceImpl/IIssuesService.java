/**
 * 
 */
package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.IssueBreakDownDetailsDto;
import org.fleetopgroup.persistence.dto.IssuesCommentDto;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.IssuesTasksDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.Issues;
import org.fleetopgroup.persistence.model.IssuesComment;
import org.fleetopgroup.persistence.model.IssuesDocument;
import org.fleetopgroup.persistence.model.IssuesPhoto;
import org.fleetopgroup.persistence.model.IssuesTasks;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;

/**
 * @author fleetop
 *
 */
public interface IIssuesService {

	/** This interface is save Issues */
	public Issues registerNew_Issues(Issues IssuesDto) throws Exception;

	public void update_Vehicle_Issues(Issues IssuesDto) throws Exception;

	public void update_Driver_Issues(Issues IssuesDto) throws Exception;

	public void update_Customer_Issues(Issues IssuesDto) throws Exception;
	
	public void update_Other_Issues(Issues IssuesDto) throws Exception;

	public List<Issues> findAll();

	/** This interface is Issues Search only that user */
	public List<IssuesDto> find_Issues_Search(String user_email, String Search);

	/** This interface is Issues Search only Your that user */
	public List<IssuesDto> find_Issues_SearchYour(String user_email, String Search);

	/** This Page get Issues table to get pagination values */
	public Page<Issues> get_Issues_All_pagination(Integer pageNumber, Integer companyId, Long id) throws Exception;

	/** This interface is Issues count Total Issues */
	public List<IssuesDto> find_Issues_All_Status(Integer pageNumber, CustomUserDetails userDetails) throws Exception;

	/** This interface is Issues count Total Issues */
	public List<IssuesDto> find_Issues_Open_Status(Integer pageNumber, Long user_id, CustomUserDetails	userDetails) throws Exception;

	/** This interface is Issues Overdue Total Issues */
	public List<IssuesDto> find_Issues_Overdue_Status(Integer pageNumber, CustomUserDetails	userDetails, String date) throws Exception;

	/** This interface is Issues Resolved Total Issues */
	public List<IssuesDto> find_Issues_Resolved_Status(Integer pageNumber, CustomUserDetails userDetails) throws Exception;

	/** This interface is Issues WoCreated Total Issues */
	public List<IssuesDto> find_Issues_WoCreated_Status(Integer pageNumber,CustomUserDetails userDetails) throws Exception;

	/** This interface is Issues Reject Total Issues */
	public List<IssuesDto> find_Issues_ReJect_Status(Integer pageNumber,CustomUserDetails userDetails) throws Exception;

	/** This interface is Issues Closed Total Issues */
	public List<IssuesDto> find_Issues_Closed_Status(Integer pageNumber,CustomUserDetails userDetails) throws Exception;

	/** This Page get Issues table to get pagination values */
	public Page<Issues> get_Issues_Your_pagination(Integer pageNumber, CustomUserDetails userDetails) throws Exception;

	/** This interface is Issues Your Total Issues */
	public List<IssuesDto> find_Issues_Your_Status(Integer pageNumber, CustomUserDetails userDetails) throws Exception;

	/** This interface is Issues count Total Issues */
	public Long countTotalIssues() throws Exception;

	/** This interface is Issues countStatus */
	//public Long countStatus(String Status) throws Exception;

	/** This interface is Issues count Total Issues */
	public Object[] countTotalIssues_AND_IssuesOpenStatus(CustomUserDetails userDetails) throws Exception;

	/** This interface is find Issues id to Details */
	public IssuesDto get_IssuesDetails(Long Issues_ID, Integer companyId) throws Exception;

	public Issues get_IssuesDetails_Your(Long Issues_ID) throws Exception;

	/** This interface is Upload Issues Status to Details Closing */
	public void update_Close_Issues_Status(short Status, Long CloseBy, Date close_date, Long Issues_ID, Integer companyId);

	/** This interface is Upload Issues Status to Details Closing */
	public void update_Reject_Issues_Status(short Status, Long CloseBy, Date close_date, Long Issues_ID, Integer companyId);

	/** This interface is Upload Issues Status to Details Reopen */
	public void update_Reopen_Issues_Status(short Status, Long CloseBy, Date close_date, Long Issues_ID, Integer companyId);

	/** This interface is Upload Issues Status to Details ReSolved */
	public void update_ReSolved_Issues_Status(short Status, Long CloseBy, Date close_date, Long Issues_ID, Integer companyId);

	/**
	 * This interface is Upload Issues Status and WorkOrder_id And WorkOrder
	 * Created date to Details ReSolved
	 */
	public void update_Create_WorkOrder_Issues_Status(short Status, Long CloseBy, Date close_date, Long WorkOrder_ID,
			Long Issues_ID,int companyId);

	/** This interface is Delete Issues id to Details */
	public void delete_Issues(Long Issues_ID, Integer companyId,Long userId,Timestamp todate);

	/** This interface is Check Issues Assign this user are not Details */
	public Issues check_Issues_Assign_Details(Long Issues_ID, CustomUserDetails	userDetails) throws Exception;

	/**
	 * This interface is Check Issues Assign to close issues this user are not
	 * Details
	 */
	public Issues check_Issues_Created_Issues_Details(Long Issues_ID, CustomUserDetails	userDetails) throws Exception;

	/** Issues Task for only vehicle Issues only */

	/** This interface is IssuesTasks save IssuesTasks */
	public IssuesTasks registerNew_IssuesTasks(IssuesTasks IssuestaskDto) throws Exception;

	/** This interface is find Issues id to get IssuesTask Details */
	public IssuesTasks get_Issues_VEHICLEID_TO_GET_IssuesTask_Details(Long Issues_ID) throws Exception;

	/** This interface is Check Show_Header_Issues Details */
	public IssuesDto Show_Header_Issues_Details(Long Issues_ID, CustomUserDetails	userDetails) throws Exception;

	/**
	 * Issues Comment in details *
	 */

	/** This Interface is save Issues Comment Details */
	public void add_Issues_Comment_Details(IssuesComment issueComment) throws Exception;

	/** This Interface is List of one Issues Comment Details */
	public List<IssuesCommentDto> Get_Issues_ID_Comment_Details(Long issue_ID, Integer companyId) throws Exception;

	/** This Interface is Delete Issues Comment using comment id Details */
	public void Delete_Issues_Comment_Details(Long issueComment_ID, Integer companyId) throws Exception;

	/**
	 * Issues Photo in details *
	 */

	/** This Interface is save Issues Photo Details */
	public void add_Issues_Photo_Details(org.fleetopgroup.persistence.document.IssuesPhoto issuePhoto) throws Exception;

	/** This Interface is List of one Issues Photo Details */
	public List<org.fleetopgroup.persistence.document.IssuesPhoto> Get_Issues_ID_Photo_Details(Long issue_ID, Integer companyId) throws Exception;

	/** This Interface is Delete Issues Photo using comment id Details */
	public void Delete_Issues_Photo_Details(Long issueComment_ID, Integer companyId) throws Exception;

	/** This Interface is Get Issues Photo using comment id Details */
	public org.fleetopgroup.persistence.document.IssuesPhoto Get_Issues_Photo(Long issueComment_ID, Integer companyId) throws Exception;

	/**
	 * Issues Document in details *
	 */

	/** This Interface is save Issues Document Details */
	public void add_Issues_Document_Details(org.fleetopgroup.persistence.document.IssuesDocument issueDocument) throws Exception;

	/** This Interface is Update Issues Document Details */
	public void Update_Issues_Document_Details(org.fleetopgroup.persistence.document.IssuesDocument issueDocument) throws Exception;

	/** This Interface is Get Issues Document using comment id Details */
	public org.fleetopgroup.persistence.document.IssuesDocument Get_Issues_Document(Long issueComment_ID, Integer companyId) throws Exception;

	/** This Interface is List of one Issues Document Details */
	public List<org.fleetopgroup.persistence.document.IssuesDocument> Get_Issues_ID_Document_Details(Long issue_ID, Integer companyId) throws Exception;

	/** This Interface is Delete Issues Document using comment id Details */
	public void Delete_Issues_Document_Details(Long issueComment_ID, Integer companyId) throws Exception;

	public long countIssues_Comment(Long Issues_ID) throws Exception;

	public long countIssues_Document(Long Issues_ID) throws Exception;
	
	public long countIssues_Photo(Long Issues_ID) throws Exception;

	/**
	 * This interface is Issues find vehicle details and user based value in
	 * vehicle ID Issues
	 */
	public List<IssuesDto> find_Issues_Open_andVehicle_ID_Status(String user_email, Integer vehicle_ID, Integer companyId, Long id) throws Exception;

	/** This interface is Report Issues Search only that user */
	public List<IssuesDto> Report_find_Issues_Search(String reportdateFrom, String reportdateTo, String Search_Query) throws Exception;

	/** This interface is Report_daily_Issues_Search only that user */
	public List<IssuesDto> Report_daily_Issues_Search(String reportdateFrom, String reportdateTo, String Search_Query, CustomUserDetails	userDetails)throws Exception;

	/** This interface is Report_daily_Issues_Search only that user */
	public List<IssuesDto> Report_Issues_Reported_Search(String reportdateFrom, String reportdateTo, String Search_Query, CustomUserDetails	userDetails)throws Exception;

	/** This interface is Report only Your Issues Only that user */
	public List<IssuesDto> Report_YourIssues_Status_Search(String userEmail, String reportdateFrom, String reportdateTo,
			String Search_Query, Integer companyId, Long id);

/*	*//**
	 * Update Vehicle Edit to Vehicle Group using vehicle Id
	 **//*
	public void update_Vehicle_Group_USING_Vehicle_Id(String Vehicle_Group, Integer vehicle_id, long vehicleGroupId);
*/
	/**
	 * @param workorders_id
	 * @return
	 */
	public List<IssuesDto> GET_WORKORDER_ID_TO_ISSUES_DETAILS(Long workorders_id, Integer companyId);

	/** This Count Issues not closed open Status Last 5days Issues*/
	public Object[] count_TOTAL_ISSUES_NOT_CLOSED_FIVEDAYS_ISSUES(String dayOne, String dayTwo, String dayThree,
			String dayFour, String dayFive, CustomUserDetails userDetails) throws Exception;

	/** This Search Issues Date Issues*/
	public List<IssuesDto> find_NOTOPENISSUES_Status_SearchDate(short string, String searchdate, Integer companyId) throws Exception;
	
	public IssuesDto get_IssuesDetailsByNumber(Long Issues_ID, CustomUserDetails userDetails) throws Exception;
	
	public List<IssuesTasksDto>  getIssuesTasksList(Long Issues_ID, Integer companyId) throws Exception;

	public void transferIssuesDocument(List<IssuesDocument> issuesDocumentList) throws Exception;

	public void transferIssuesPhotos(List<IssuesPhoto> issuesPhotoList) throws Exception;
	
	public long getIssuesDocumentMaxId() throws Exception;
	
	public long getIssuesPhotoMaxId() throws Exception;

	public List<IssuesDto> getIssuesByDriver(Integer driverId, String dateRangeFrom, String dateRangeTo) throws Exception;
	
	public Long getTotalIssuesCreatedCount(String startDate, String endDate, Integer companyId ,String vGroupQuery) throws Exception;
	
	public HashMap<Integer, Long>   issuesCreatedOnDate(String	startDate, String endDate) throws Exception;
	
	public HashMap<Integer, Long>   issuesResolvedOnDate(String	startDate, String endDate) throws Exception;
	
	public Long getIssuesAllopenCount(Integer companyId,String vGroupQuery) throws Exception;
	
	public HashMap<Integer, Long>   issuesAllopenData() throws Exception;
	
	public Long getIssuesOpenCountBetweenDates(String startDate, String endDate,Integer companyId,String vGroupQuery) throws Exception;
	
	public HashMap<Integer, Long>   issuesOpenWithinDays(String	startDate, String endDate) throws Exception;
	
	public Long getIssuesOpenCountByDate(String startDate, Integer companyId,String vGroupQuery) throws Exception ;
	
	public HashMap<Integer, Long>   issuesOpen15MoreDays(String	startDate) throws Exception;

	long issuesCountByStatus(String	startDate, String endDate, short status, Integer companyId,String vGroupQuery) throws Exception;
	
	public long issuesCountByInProcessStatus(String	startDate, String endDate, Integer companyID,String vGroupQuery ) throws Exception;

	public List<IssuesDto> getIssueDetailsBetweenDatesByStatus(Integer compId, String issueStatusQuery,String vGroupQuery)throws Exception;

	public long issuesOverDueCount(String todaysDate,Integer companyId,String vGroupQuery)throws Exception;

	public long issuesResolveCount(String startDate, String endDate, short resolvedStatus, Integer companyId)throws Exception;

	public ValueObject saveIssuesServiceEntries(ValueObject object)throws Exception;

	public List<IssuesDto> find_Issues_SE_Created_Status(Integer pageNumber, CustomUserDetails userDetails, short issuesStatusSE_created)throws Exception;

	public IssuesDto GET_SE_ID_TO_ISSUES_DETAILS(Long serviceEntries_id, Integer company_id)throws Exception;

	public void update_Create_SE_Issues_Status(short issuesStatusResolved, Long userId, Date toDate, Long sEID,
			Long issueId)throws Exception;

	public void changeIssueStatusFromSE_createdToOpen(Long serviceEntries_id, short issuesStatusOpen, Long issues_ID,
			CustomUserDetails userDetails)throws Exception;

	public ValueObject saveIssuesDetails(ValueObject valueInObject)throws Exception;

	public ValueObject updateIssuesDetails(ValueObject valueInObject)throws Exception;

	public ValueObject getlastOpenIssue(ValueObject valueInObject)throws Exception;
	
	public Issues getIssueDetailsByIssueId(Long Issues_ID, Integer companyId) throws Exception;

	public void update_SE_Issue_Details(Long issues_ID) throws Exception;

	public void update_WO_Issue_Details(Long issues_ID,int companyId)throws Exception;

	public ValueObject getIssueMaxOdometerRange(ValueObject valueInObject)throws Exception;

	public ValueObject createIssueFromMobile(ValueObject object) throws Exception;
	
	public ValueObject showIssueFromMobile(ValueObject object) throws Exception;
	
	public ValueObject editIssueFromMobile(ValueObject object) throws Exception;
	
	public ValueObject searchIssueFromMobile(ValueObject object) throws Exception;
	
	public ValueObject resolveIssueFromMobile(ValueObject object) throws Exception;
	
	public ValueObject rejectIssueFromMobile(ValueObject object) throws Exception;
	
	public ValueObject reOpenIssueFromMobile(ValueObject object) throws Exception;
	
	public ValueObject closeIssueFromMobile(ValueObject object) throws Exception;
	
	public ValueObject getIssueImageForMobile(ValueObject object) throws Exception;
	
	public ValueObject saveIssueImageFromMobile(ValueObject object) throws Exception;
	
	public void sendNotification(ValueObject valueObject,Issues savedIssues,String msg)throws Exception;
	
	public void sendNotificationWhenIssueResolved(long issuesId,long userId,int companyId)throws Exception;
	
	public List<IssuesDto> getIssuesActivityList(String query , String innerQuery) throws Exception;
	
	public ValueObject check_Issues_Update_Details(Long Issues_ID,String email, CustomUserDetails userDetails) throws Exception;
	
	public IssuesTasks saveIssuestask(IssuesTasks IssuestaskDto, CustomUserDetails userDetails) throws Exception;
	
	public List<IssuesDto> getOpenIssueDetailsByVehicle(int vid, CustomUserDetails userDetails, long issueId, String query,String invoiceDate) throws Exception;

	public List<IssuesDto> findOpenIsueBeforeFiveDays(short issuesStatusOpen, String searchdate, Integer company_id)throws Exception;
	
	public void sendNotificationWhenIssueIsRejected(long issuesId,long userId,int companyId)throws Exception;
	
	public ValueObject getNewIssueReportData(ValueObject valueObject)throws Exception;

	public IssuesDto getIssueBasicDetails(Long issueId, Integer companyId) throws Exception;

	public void update_DSE_Issue_Details(Long issueId, Integer companyId)throws Exception;

	public void updateCreateDSE_IssueStatus(short Status, Long CloseBy, Date close_date, Long WorkOrder_ID, Long Issues_ID,
			Integer companyId)throws Exception;
	
	public Object[] countOverdueIssues(CustomUserDetails userDetails) throws Exception;
	
	public ValueObject prepareValueObject(Long parameterId, int vid , String remark ,Long inspectionSheetId,Long completionToParameterId) throws Exception;
	
	public void deleteExistingIssueIfUpdatedAsSuccess(Long completionToParameterId,int vid) throws Exception;
	
	public ValueObject getIssueBreakDownDetails(ValueObject valueInObject)throws Exception;
	
	public IssueBreakDownDetailsDto  getIssueBreakDownDetailsByIssueId(Long issueId) throws Exception;
	
	public ValueObject updateIssueType(ValueObject valueObject) throws Exception;
	
	public List<IssuesDto> findIssueListByIssueType(CustomUserDetails userDetails, short issueType) throws Exception;

	public void updateTyreAssignmentIssueStatus(short Status, Long CloseBy, Date close_date, Long lpid, Long Issues_ID,
			Integer companyId) throws Exception;

	public ValueObject getIssueAssignTyre(ValueObject valueObject)throws Exception;

	public void updateTyreAssignmentIssueStatusByIssueId(short status, long issueId, int companyId)throws Exception;
	
	public ValueObject saveMultiIssues(ValueObject valueInObject) throws Exception;
	
	public ValueObject getBreakDownAnalysisReport(ValueObject object) throws Exception ;
	
	public Object[] getBreakDownIssuesCount(CustomUserDetails userDetails);
	
	public List<UserProfileDto> getAssigneeListByRespectiveIssueId(List<IssuesDto> issuesDto) throws Exception;
	
	public List<IssuesDto> getWOIssueList(Long Workorders_id,String issueIds, Integer companyId) throws Exception;
	
	public ValueObject getVehicleWiseIssueDetails(ValueObject valueObjectIn) throws Exception;

	
	public ValueObject backdateOdometerValidation(ValueObject object);
	
	public ValueObject getMaxAndMinOdometer(ValueObject object,VehicleDto nextIssue,VehicleDto preIssue,String reportedDate) throws Exception;
	
	public ValueObject getIssueTypeChangedReport(ValueObject object) throws Exception;
	
	public ValueObject getVehicleAndBreakDownReportDetails(ValueObject object) throws Exception ;
	
	public List<IssuesDto> getVehicleAndBreakDownReportList(ValueObject object) throws Exception;

	public List<IssuesDto> getIssueDetailsByIssueIds(String issueIds, int int1)throws Exception;
	
	public List<Issues> getDriverWiseIssueList(ValueObject object);
	

	public Map<String,Object> getPrintDataForIssue(Long issueId);
	
	public Map<String,Object> getMultiPrintDataForIssue(String issueIds);

	public ValueObject getIssueDetails(ValueObject object);

	/** This Page get Issues table to get pagination values */
	public Page<Issues> get_OpenIssues_All_pagination(Integer pageNumber, Integer companyId, Long id, short issueStatus) throws Exception;
	
	/** This Page get Issues table to get pagination values */
	public Page<Issues> get_OverdueIssues_All_pagination(Integer pageNumber, Integer companyId, Long id,short issueStatus, Timestamp date) throws Exception;
	
	/** This Page get Issues table to get pagination values */
	public Page<Issues> get_SECreatedIssues_All_pagination(Integer pageNumber, Integer companyId, Long id, short issueStatus) throws Exception;
		
	/** This Page get Issues table to get pagination values */
	public Page<Issues> get_Resolved_All_pagination(Integer pageNumber, Integer companyId, Long id, short issueStatus) throws Exception;
			
	/** This Page get Issues table to get pagination values */
	public Page<Issues> get_DSE_Created_Issues_All_pagination(Integer pageNumber, Integer companyId, Long id, short issueStatus) throws Exception;
	
	/** This Page get Issues table to get pagination values */
	public Page<Issues> get_VehicleBreakDownIssues_All_pagination(Integer pageNumber, Integer companyId, Long id,short issueStatus) throws Exception;
	
	/** This Page get Issues table to get pagination values */
	public Page<Issues> get_CloseIssues_All_pagination(Integer pageNumber, Integer companyId, Long id,short issueStatus) throws Exception;
	
	/** This Page get Issues table to get pagination values */
	public Page<Issues> get_RejectIssues_All_pagination(Integer pageNumber, Integer companyId, Long id, short issueStatus) throws Exception;
		
	/** This Page get Issues table to get pagination values */
	public Page<Issues> get_WOCreatedIssues_All_pagination(Integer pageNumber, Integer companyId, Long id,short issueStatus) throws Exception;


}
