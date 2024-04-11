/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import java.util.Date;
import java.util.Set;

import org.fleetopgroup.persistence.model.IssuesTasks;
import org.fleetopgroup.web.util.Utility;


public class IssuesDto {
	
	private long ISSUES_ID;
	
	private long ISSUES_NUMBER;
	
	private String issuesNumberStr;

	private String ISSUES_ID_ENCRYPT;

	private String ISSUES_TYPE;

	private short ISSUE_TYPE_ID;
	
	private Integer ISSUES_VID;

	private String ISSUES_VEHICLE_REGISTRATION;

	private String ISSUES_VEHICLE_GROUP;

	private Integer ISSUES_ODOMETER;

	private Integer ISSUES_DRIVER_ID;

	private String ISSUES_DRIVER_NAME;
	
	private String ISSUES_FATHER_NAME;
	
	private String issueDriverLastName;

	private Integer ISSUES_BRANCH_ID;

	private String ISSUES_BRANCH_NAME;

	private Integer ISSUES_DEPARTNMENT_ID;

	private String ISSUES_DEPARTNMENT_NAME;

	private String ISSUES_REPORTED_DATE;

	private String issue_start_time;
	
	private Date ISSUES_REPORTED_DATE_ON;
	
	private Date ISSUES_WORKORDER_DATE_ON;

	private String ISSUES_REPORTED_DATE_DIFFRENT;

	private String ISSUES_SUMMARY;

	private String ISSUES_DESCRIPTION;

	private String ISSUES_LABELS;
	
	private short ISSUES_LABELS_ID;

	private String ISSUES_REPORTED_BY;
	
	private Long ISSUES_REPORTED_BY_ID;

	private String ISSUES_ASSIGN_TO;
	
	private String ISSUES_ASSIGN_TO_ID;
	
	private String ISSUES_ASSIGN_TO_NAME;
	
	private String CUSTOMER_NAME;

	private String ISSUES_DUE_DATE;

	private Integer ISSUES_DUE_ODOMETER;

	private Long ISSUES_WORKORDER_ID;
	
	private Long ISSUES_WORKORDER_NUMBER;

	private String ISSUES_WORKORDER_DATE;

	private String ISSUES_STATUS;
	
	private short ISSUES_STATUS_ID;
	
	private short 	ISSUES_TYPE_ID;
	
	private long VEHICLE_GROUP_ID; 
	
	private Integer COMPANY_ID;

	private String CREATEDBY;

	private String LASTMODIFIEDBY;
	
	private Long CREATEDBYID;

	private Long LASTMODIFIEDBYID;

	private boolean markForDelete;
	
	private Long userId;
	 
	private String userName;
	
	private String tripNumber;

	private String woRemark;

	private Long dealerServiceEntriesId;
	
	private Long issueAnalysisId;
	
	private Long workOrderTaskId;
	
	private boolean isIssueAnalysis;
	
	private String healthStatus;
	
	private String breakDownLocation;
	
	private Integer	replacedWithVid;
	
	private String	vehicleNumber;
	
	private String	vehicleReplacedStr;
	
	private String	tripCancelledStr;
	
	private String	replacedWithVehicle;
	
	private String	transactionNumber;
	
	private String CREATED_DATE;

	private String LASTUPDATED_DATE;
	
	private Date CREATED_DATE_ON;

	private Date LASTUPDATED_DATE_ON;

	private String issueComment_Comment;
	
	private String issueComment_Title;
	
	private Date   issueComment_CreatedDate;
	
	private String   issueComment_CreatedDateStr;
	
	private String   reason;
	
	private String   tempSolution;
	
	private String   fixSolution;
	
	private String   futurePlan;
	
	private String   avoidableStr;
	
	private boolean   avoidable;

	private String ISSUES_DIFFERENCES_DATE;
	
	private Set<IssuesTasks> ISSUESTASK;

	private String fromDate;
	
	private String toDate;
	
	private String driver_mobnumber;
	
	private Double GPS_ODOMETER;
	
	private Long ISSUES_SE_ID;
	
	private Long serviceEntries_Number;
	
	private Date ISSUES_SE_DATEON;
	
	private String ISSUES_SE_DATE;
	
	private long ageing;
	
	private Integer vehicleCurrentOdometer;
	
	private Integer categoryId;
	
	private String partCategoryName;
	
	private Integer routeID;
	
	private String routeName;
	
	private String 	issueTypeName;
	
	private String 	issueLabelName;
	
	private String 	issueStatusName;
	
	private String 	resolvingDate;
	
	private Date 	resolvingDateTime;
	
	private String 	issuesReportedDateStr;
	
	private String 	assignByName;
	
	private Long 	issueTaskId;
	
	private String 	totalTime;
	
	private Long 	issueAssignToFirstId;
	
	private String 	vehicleType;
	
	private String 	vehicleMaker;
	
	private String 	vehicleModel;
	
	private String 	location;

	private Long 	issueLP_ID;
	
	private Boolean isVehicleReplaced;
	
	private Boolean isTripCancelled;
	
	private String replacedVehicle;
	
	private String vehicleStatus;
	
	private String vehicleReplacedLocation;
	
	private String triripCancelledStatus;
	
	private String remark;
	
	private Double cancelledKM;
	
	private String 	issueChangedDate;
	
	private String 	woOrDseLink;
	
	private Date 	issueChangedOn;
	
	private String pcName;
	
	public String getTripNumber() {
		return tripNumber;
	}
	
	
	public void setTripNumber(String tripNumber) {
		this.tripNumber = tripNumber;
	}
	
	public String getISSUES_FATHER_NAME() {
		return ISSUES_FATHER_NAME;
	}


	public void setISSUES_FATHER_NAME(String iSSUES_FATHER_NAME) {
		ISSUES_FATHER_NAME = iSSUES_FATHER_NAME;
	}


	public String getIssueDriverLastName() {
		return issueDriverLastName;
	}


	public void setIssueDriverLastName(String issueDriverLastName) {
		this.issueDriverLastName = issueDriverLastName;
	}


	public String getIssuesNumberStr() {
		return issuesNumberStr;
	}


	public void setIssuesNumberStr(String issuesNumberStr) {
		this.issuesNumberStr = issuesNumberStr;
	}

	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public IssuesDto() {
		super();
	}
	

	public String getIssueComment_CreatedDateStr() {
		return issueComment_CreatedDateStr;
	}


	public void setIssueComment_CreatedDateStr(String issueComment_CreatedDateStr) {
		this.issueComment_CreatedDateStr = issueComment_CreatedDateStr;
	}




	public long getISSUES_ID() {
		return ISSUES_ID;
	}

	public void setISSUES_ID(long iSSUES_ID) {
		ISSUES_ID = iSSUES_ID;
	}

	public long getISSUES_NUMBER() {
		return ISSUES_NUMBER;
	}

	public void setISSUES_NUMBER(long iSSUES_NUMBER) {
		ISSUES_NUMBER = iSSUES_NUMBER;
	}

	public String getISSUES_TYPE() {
		return ISSUES_TYPE;
	}

	public void setISSUES_TYPE(String iSSUES_TYPE) {
		ISSUES_TYPE = iSSUES_TYPE;
	}

	public Integer getISSUES_VID() {
		return ISSUES_VID;
	}

	public void setISSUES_VID(Integer iSSUES_VID) {
		ISSUES_VID = iSSUES_VID;
	}

	public String getISSUES_VEHICLE_REGISTRATION() {
		return ISSUES_VEHICLE_REGISTRATION;
	}

	public void setISSUES_VEHICLE_REGISTRATION(String iSSUES_VEHICLE_REGISTRATION) {
		ISSUES_VEHICLE_REGISTRATION = iSSUES_VEHICLE_REGISTRATION;
	}

	public String getISSUES_VEHICLE_GROUP() {
		return ISSUES_VEHICLE_GROUP;
	}

	public void setISSUES_VEHICLE_GROUP(String iSSUES_VEHICLE_GROUP) {
		ISSUES_VEHICLE_GROUP = iSSUES_VEHICLE_GROUP;
	}

	public Integer getISSUES_ODOMETER() {
		return ISSUES_ODOMETER;
	}

	public void setISSUES_ODOMETER(Integer iSSUES_ODOMETER) {
		ISSUES_ODOMETER = iSSUES_ODOMETER;
	}

	public String getISSUES_ASSIGN_TO_ID() {
		return ISSUES_ASSIGN_TO_ID;
	}

	public void setISSUES_ASSIGN_TO_ID(String iSSUES_ASSIGN_TO_ID) {
		ISSUES_ASSIGN_TO_ID = iSSUES_ASSIGN_TO_ID;
	}

	public Integer getISSUES_DRIVER_ID() {
		return ISSUES_DRIVER_ID;
	}

	public void setISSUES_DRIVER_ID(Integer iSSUES_DRIVER_ID) {
		ISSUES_DRIVER_ID = iSSUES_DRIVER_ID;
	}

	public String getISSUES_DRIVER_NAME() {
		return ISSUES_DRIVER_NAME;
	}

	public void setISSUES_DRIVER_NAME(String iSSUES_DRIVER_NAME) {
		ISSUES_DRIVER_NAME = iSSUES_DRIVER_NAME;
	}

	public Integer getISSUES_BRANCH_ID() {
		return ISSUES_BRANCH_ID;
	}

	
	public short getISSUE_TYPE_ID() {
		return ISSUE_TYPE_ID;
	}

	public void setISSUE_TYPE_ID(short iSSUE_TYPE_ID) {
		ISSUE_TYPE_ID = iSSUE_TYPE_ID;
	}

	public void setISSUES_BRANCH_ID(Integer iSSUES_BRANCH_ID) {
		ISSUES_BRANCH_ID = iSSUES_BRANCH_ID;
	}

	public String getISSUES_BRANCH_NAME() {
		return ISSUES_BRANCH_NAME;
	}

	public void setISSUES_BRANCH_NAME(String iSSUES_BRANCH_NAME) {
		ISSUES_BRANCH_NAME = iSSUES_BRANCH_NAME;
	}

	public String getISSUES_REPORTED_DATE() {
		return ISSUES_REPORTED_DATE;
	}

	public void setISSUES_REPORTED_DATE(String iSSUES_REPORTED_DATE) {
		ISSUES_REPORTED_DATE = iSSUES_REPORTED_DATE;
	}

	public String getIssue_start_time() {
		return issue_start_time;
	}

	public void setIssue_start_time(String issue_start_time) {
		this.issue_start_time = issue_start_time;
	}

	public String getISSUES_SUMMARY() {
		return ISSUES_SUMMARY;
	}

	public void setISSUES_SUMMARY(String iSSUES_SUMMARY) {
		ISSUES_SUMMARY = iSSUES_SUMMARY;
	}

	public String getISSUES_DESCRIPTION() {
		return ISSUES_DESCRIPTION;
	}

	public void setISSUES_DESCRIPTION(String iSSUES_DESCRIPTION) {
		ISSUES_DESCRIPTION = iSSUES_DESCRIPTION;
	}

	public String getISSUES_LABELS() {
		return ISSUES_LABELS;
	}

	/**
	 * @param iSSUES_LABELS
	 *            the iSSUES_LABELS to set
	 */
	public void setISSUES_LABELS(String iSSUES_LABELS) {
		ISSUES_LABELS = iSSUES_LABELS;
	}

	/**
	 * @return the iSSUES_REPORTED_BY
	 */
	public String getISSUES_REPORTED_BY() {
		return ISSUES_REPORTED_BY;
	}

	/**
	 * @param iSSUES_REPORTED_BY
	 *            the iSSUES_REPORTED_BY to set
	 */
	public void setISSUES_REPORTED_BY(String iSSUES_REPORTED_BY) {
		ISSUES_REPORTED_BY = iSSUES_REPORTED_BY;
	}

	/**
	 * @return the iSSUES_ASSIGN_TO
	 */
	public String getISSUES_ASSIGN_TO() {
		return ISSUES_ASSIGN_TO;
	}

	/**
	 * @param iSSUES_ASSIGN_TO
	 *            the iSSUES_ASSIGN_TO to set
	 */
	public void setISSUES_ASSIGN_TO(String iSSUES_ASSIGN_TO) {
		ISSUES_ASSIGN_TO = iSSUES_ASSIGN_TO;
	}

	/**
	 * @return the iSSUES_DUE_DATE
	 */
	public String getISSUES_DUE_DATE() {
		return ISSUES_DUE_DATE;
	}

	/**
	 * @param iSSUES_DUE_DATE
	 *            the iSSUES_DUE_DATE to set
	 */
	public void setISSUES_DUE_DATE(String iSSUES_DUE_DATE) {
		ISSUES_DUE_DATE = iSSUES_DUE_DATE;
	}

	/**
	 * @return the iSSUES_DUE_ODOMETER
	 */
	public Integer getISSUES_DUE_ODOMETER() {
		return ISSUES_DUE_ODOMETER;
	}

	/**
	 * @param iSSUES_DUE_ODOMETER
	 *            the iSSUES_DUE_ODOMETER to set
	 */
	public void setISSUES_DUE_ODOMETER(Integer iSSUES_DUE_ODOMETER) {
		ISSUES_DUE_ODOMETER = iSSUES_DUE_ODOMETER;
	}

	/**
	 * @return the cREATEDBY
	 */
	public String getCREATEDBY() {
		return CREATEDBY;
	}

	/**
	 * @param cREATEDBY
	 *            the cREATEDBY to set
	 */
	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	/**
	 * @return the lASTMODIFIEDBY
	 */
	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	/**
	 * @param lASTMODIFIEDBY
	 *            the lASTMODIFIEDBY to set
	 */
	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}

	/**
	 * @return the sTATUS
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param sTATUS
	 *            the sTATUS to set
	 */
	public void setMarkForDelete(boolean MarkForDelete) {
		markForDelete = MarkForDelete;
	}

	/**
	 * @return the cREATED_DATE
	 */
	public String getCREATED_DATE() {
		return CREATED_DATE;
	}

	/**
	 * @param cREATED_DATE
	 *            the cREATED_DATE to set
	 */
	public void setCREATED_DATE(String cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	/**
	 * @return the lASTUPDATED_DATE
	 */
	public String getLASTUPDATED_DATE() {
		return LASTUPDATED_DATE;
	}

	/**
	 * @param lASTUPDATED_DATE
	 *            the lASTUPDATED_DATE to set
	 */
	public void setLASTUPDATED_DATE(String lASTUPDATED_DATE) {
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}

	/**
	 * @return the iSSUES_STATUS
	 */
	public String getISSUES_STATUS() {
		return ISSUES_STATUS;
	}

	/**
	 * @param iSSUES_STATUS
	 *            the iSSUES_STATUS to set
	 */
	public void setISSUES_STATUS(String iSSUES_STATUS) {
		ISSUES_STATUS = iSSUES_STATUS;
	}

	/**
	 * @return the iSSUES_DEPARTNMENT_ID
	 */
	public Integer getISSUES_DEPARTNMENT_ID() {
		return ISSUES_DEPARTNMENT_ID;
	}

	/**
	 * @param iSSUES_DEPARTNMENT_ID
	 *            the iSSUES_DEPARTNMENT_ID to set
	 */
	public void setISSUES_DEPARTNMENT_ID(Integer iSSUES_DEPARTNMENT_ID) {
		ISSUES_DEPARTNMENT_ID = iSSUES_DEPARTNMENT_ID;
	}

	/**
	 * @return the iSSUES_DEPARTNMENT_NAME
	 */
	public String getISSUES_DEPARTNMENT_NAME() {
		return ISSUES_DEPARTNMENT_NAME;
	}

	/**
	 * @param iSSUES_DEPARTNMENT_NAME
	 *            the iSSUES_DEPARTNMENT_NAME to set
	 */
	public void setISSUES_DEPARTNMENT_NAME(String iSSUES_DEPARTNMENT_NAME) {
		ISSUES_DEPARTNMENT_NAME = iSSUES_DEPARTNMENT_NAME;
	}

	/**
	 * @return the iSSUES_REPORTED_DATE_DIFFRENT
	 */
	public String getISSUES_REPORTED_DATE_DIFFRENT() {
		return ISSUES_REPORTED_DATE_DIFFRENT;
	}

	/**
	 * @param iSSUES_REPORTED_DATE_DIFFRENT
	 *            the iSSUES_REPORTED_DATE_DIFFRENT to set
	 */
	public void setISSUES_REPORTED_DATE_DIFFRENT(String iSSUES_REPORTED_DATE_DIFFRENT) {
		ISSUES_REPORTED_DATE_DIFFRENT = iSSUES_REPORTED_DATE_DIFFRENT;
	}

	/**
	 * @return the iSSUES_ASSIGN_TO_NAME
	 */
	public String getISSUES_ASSIGN_TO_NAME() {
		return ISSUES_ASSIGN_TO_NAME;
	}

	/**
	 * @param iSSUES_ASSIGN_TO_NAME
	 *            the iSSUES_ASSIGN_TO_NAME to set
	 */
	public void setISSUES_ASSIGN_TO_NAME(String iSSUES_ASSIGN_TO_NAME) {
		ISSUES_ASSIGN_TO_NAME = iSSUES_ASSIGN_TO_NAME;
	}

	/**
	 * @return the iSSUES_WORKORDER_ID
	 */
	public Long getISSUES_WORKORDER_ID() {
		return ISSUES_WORKORDER_ID;
	}

	/**
	 * @param iSSUES_WORKORDER_ID
	 *            the iSSUES_WORKORDER_ID to set
	 */
	public void setISSUES_WORKORDER_ID(Long iSSUES_WORKORDER_ID) {
		ISSUES_WORKORDER_ID = iSSUES_WORKORDER_ID;
	}

	/**
	 * @return the iSSUES_WORKORDER_DATE
	 */
	public String getISSUES_WORKORDER_DATE() {
		return ISSUES_WORKORDER_DATE;
	}

	/**
	 * @param iSSUES_WORKORDER_DATE
	 *            the iSSUES_WORKORDER_DATE to set
	 */
	public void setISSUES_WORKORDER_DATE(String iSSUES_WORKORDER_DATE) {
		ISSUES_WORKORDER_DATE = iSSUES_WORKORDER_DATE;
	}

	/**
	 * @return the iSSUES_DIFFERENCES_DATE
	 */
	public String getISSUES_DIFFERENCES_DATE() {
		return ISSUES_DIFFERENCES_DATE;
	}

	/**
	 * @param iSSUES_DIFFERENCES_DATE
	 *            the iSSUES_DIFFERENCES_DATE to set
	 */
	public void setISSUES_DIFFERENCES_DATE(String iSSUES_DIFFERENCES_DATE) {
		ISSUES_DIFFERENCES_DATE = iSSUES_DIFFERENCES_DATE;
	}

	/**
	 * @return the iSSUES_ID_ENCRYPT
	 */
	public String getISSUES_ID_ENCRYPT() {
		return ISSUES_ID_ENCRYPT;
	}

	/**
	 * @param iSSUES_ID_ENCRYPT
	 *            the iSSUES_ID_ENCRYPT to set
	 */
	public void setISSUES_ID_ENCRYPT(String iSSUES_ID_ENCRYPT) {
		ISSUES_ID_ENCRYPT = iSSUES_ID_ENCRYPT;
	}

	public short getISSUES_TYPE_ID() {
		return ISSUES_TYPE_ID;
	}

	public void setISSUES_TYPE_ID(short iSSUES_TYPE_ID) {
		ISSUES_TYPE_ID = iSSUES_TYPE_ID;
	}


	public long getVEHICLE_GROUP_ID() {
		return VEHICLE_GROUP_ID;
	}

	public void setVEHICLE_GROUP_ID(long vEHICLE_GROUP_ID) {
		VEHICLE_GROUP_ID = vEHICLE_GROUP_ID;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	/**
	 * @return the iSSUES_LABELS_ID
	 */
	public short getISSUES_LABELS_ID() {
		return ISSUES_LABELS_ID;
	}

	/**
	 * @param iSSUES_LABELS_ID the iSSUES_LABELS_ID to set
	 */
	public void setISSUES_LABELS_ID(short iSSUES_LABELS_ID) {
		ISSUES_LABELS_ID = iSSUES_LABELS_ID;
	}

	/**
	 * @return the iSSUES_REPORTED_BY_ID
	 */
	public Long getISSUES_REPORTED_BY_ID() {
		return ISSUES_REPORTED_BY_ID;
	}

	/**
	 * @param iSSUES_REPORTED_BY_ID the iSSUES_REPORTED_BY_ID to set
	 */
	public void setISSUES_REPORTED_BY_ID(Long iSSUES_REPORTED_BY_ID) {
		ISSUES_REPORTED_BY_ID = iSSUES_REPORTED_BY_ID;
	}



	/**
	 * @return the iSSUES_STATUS_ID
	 */
	public short getISSUES_STATUS_ID() {
		return ISSUES_STATUS_ID;
	}

	/**
	 * @param iSSUES_STATUS_ID the iSSUES_STATUS_ID to set
	 */
	public void setISSUES_STATUS_ID(short iSSUES_STATUS_ID) {
		ISSUES_STATUS_ID = iSSUES_STATUS_ID;
	}

	/**
	 * @return the cREATEDBYID
	 */
	public Long getCREATEDBYID() {
		return CREATEDBYID;
	}

	/**
	 * @param cREATEDBYID the cREATEDBYID to set
	 */
	public void setCREATEDBYID(Long cREATEDBYID) {
		CREATEDBYID = cREATEDBYID;
	}

	/**
	 * @return the lASTMODIFIEDBYID
	 */
	public Long getLASTMODIFIEDBYID() {
		return LASTMODIFIEDBYID;
	}

	/**
	 * @param lASTMODIFIEDBYID the lASTMODIFIEDBYID to set
	 */
	public void setLASTMODIFIEDBYID(Long lASTMODIFIEDBYID) {
		LASTMODIFIEDBYID = lASTMODIFIEDBYID;
	}

	/**
	 * @return the cREATED_DATE_ON
	 */
	public Date getCREATED_DATE_ON() {
		return CREATED_DATE_ON;
	}

	/**
	 * @param cREATED_DATE_ON the cREATED_DATE_ON to set
	 */
	public void setCREATED_DATE_ON(Date cREATED_DATE_ON) {
		CREATED_DATE_ON = cREATED_DATE_ON;
	}

	/**
	 * @return the lASTUPDATED_DATE_ON
	 */
	public Date getLASTUPDATED_DATE_ON() {
		return LASTUPDATED_DATE_ON;
	}

	/**
	 * @param lASTUPDATED_DATE_ON the lASTUPDATED_DATE_ON to set
	 */
	public void setLASTUPDATED_DATE_ON(Date lASTUPDATED_DATE_ON) {
		LASTUPDATED_DATE_ON = lASTUPDATED_DATE_ON;
	}

	/**
	 * @return the iSSUES_REPORTED_DATE_ON
	 */
	public Date getISSUES_REPORTED_DATE_ON() {
		return ISSUES_REPORTED_DATE_ON;
	}

	/**
	 * @param iSSUES_REPORTED_DATE_ON the iSSUES_REPORTED_DATE_ON to set
	 */
	public void setISSUES_REPORTED_DATE_ON(Date iSSUES_REPORTED_DATE_ON) {
		ISSUES_REPORTED_DATE_ON = iSSUES_REPORTED_DATE_ON;
	}

	/**
	 * @return the iSSUES_WORKORDER_DATE_ON
	 */
	public Date getISSUES_WORKORDER_DATE_ON() {
		return ISSUES_WORKORDER_DATE_ON;
	}

	/**
	 * @param iSSUES_WORKORDER_DATE_ON the iSSUES_WORKORDER_DATE_ON to set
	 */
	public void setISSUES_WORKORDER_DATE_ON(Date iSSUES_WORKORDER_DATE_ON) {
		ISSUES_WORKORDER_DATE_ON = iSSUES_WORKORDER_DATE_ON;
	}

	public String getDriver_mobnumber() {
		return driver_mobnumber;
	}

	public void setDriver_mobnumber(String driver_mobnumber) {
		this.driver_mobnumber = driver_mobnumber;
	}

	public Long getISSUES_WORKORDER_NUMBER() {
		return ISSUES_WORKORDER_NUMBER;
	}

	public void setISSUES_WORKORDER_NUMBER(Long iSSUES_WORKORDER_NUMBER) {
		ISSUES_WORKORDER_NUMBER = iSSUES_WORKORDER_NUMBER;
	}

	
	
	
	public String getIssueComment_Comment() {
		return issueComment_Comment;
	}

	public void setIssueComment_Comment(String issueComment_Comment) {
		this.issueComment_Comment = issueComment_Comment;
	}

	public String getIssueComment_Title() {
		return issueComment_Title;
	}

	public void setIssueComment_Title(String issueComment_Title) {
		this.issueComment_Title = issueComment_Title;
	}

	public Date getIssueComment_CreatedDate() {
		return issueComment_CreatedDate;
	}

	public void setIssueComment_CreatedDate(Date issueComment_CreatedDate) {
		this.issueComment_CreatedDate = issueComment_CreatedDate;
	}

	/**
	 * @return the iSSUESTASK
	 */
	public Set<IssuesTasks> getISSUESTASK() {
		return ISSUESTASK;
	}

	/**
	 * @param iSSUESTASK the iSSUESTASK to set
	 */
	public void setISSUESTASK(Set<IssuesTasks> iSSUESTASK) {
		ISSUESTASK = iSSUESTASK;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	



	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}

	public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
		CUSTOMER_NAME = cUSTOMER_NAME;
	}

	public Double getGPS_ODOMETER() {
		return GPS_ODOMETER;
	}




	public void setGPS_ODOMETER(Double gPS_ODOMETER) {
		GPS_ODOMETER = Utility.round(gPS_ODOMETER,2);
	}




	public Long getISSUES_SE_ID() {
		return ISSUES_SE_ID;
	}




	public void setISSUES_SE_ID(Long iSSUES_SE_ID) {
		ISSUES_SE_ID = iSSUES_SE_ID;
	}




	public Long getServiceEntries_Number() {
		return serviceEntries_Number;
	}




	public void setServiceEntries_Number(Long serviceEntries_Number) {
		this.serviceEntries_Number = serviceEntries_Number;
	}




	public Date getISSUES_SE_DATEON() {
		return ISSUES_SE_DATEON;
	}




	public void setISSUES_SE_DATEON(Date iSSUES_SE_DATEON) {
		ISSUES_SE_DATEON = iSSUES_SE_DATEON;
	}




	public String getISSUES_SE_DATE() {
		return ISSUES_SE_DATE;
	}




	public void setISSUES_SE_DATE(String iSSUES_SE_DATE) {
		ISSUES_SE_DATE = iSSUES_SE_DATE;
	}


	public long getAgeing() {
		return ageing;
	}


	public void setAgeing(long ageing) {
		this.ageing = ageing;
	}


	public Integer getVehicleCurrentOdometer() {
		return vehicleCurrentOdometer;
	}


	public void setVehicleCurrentOdometer(Integer vehicleCurrentOdometer) {
		this.vehicleCurrentOdometer = vehicleCurrentOdometer;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getPartCategoryName() {
		return partCategoryName;
	}

	public void setPartCategoryName(String partCategoryName) {
		this.partCategoryName = partCategoryName;
	}
	public Integer getRouteID() {
		return routeID;
	}

	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	
	public Long getDealerServiceEntriesId() {
		return dealerServiceEntriesId;
	}


	public void setDealerServiceEntriesId(Long dealerServiceEntriesId) {
		this.dealerServiceEntriesId = dealerServiceEntriesId;
	}


	public String getIssueTypeName() {
		return issueTypeName;
	}

	public void setIssueTypeName(String issueTypeName) {
		this.issueTypeName = issueTypeName;
	}

	public String getIssueLabelName() {
		return issueLabelName;
	}

	public void setIssueLabelName(String issueLabelName) {
		this.issueLabelName = issueLabelName;
	}

	public String getIssueStatusName() {
		return issueStatusName;
	}

	public void setIssueStatusName(String issueStatusName) {
		this.issueStatusName = issueStatusName;
	}

	public String getResolvingDate() {
		return resolvingDate;
	}

	public void setResolvingDate(String resolvingDate) {
		this.resolvingDate = resolvingDate;
	}

	public Date getResolvingDateTime() {
		return resolvingDateTime;
	}

	public void setResolvingDateTime(Date resolvingDateTime) {
		this.resolvingDateTime = resolvingDateTime;
	}

	public String getIssuesReportedDateStr() {
		return issuesReportedDateStr;
	}

	public void setIssuesReportedDateStr(String issuesReportedDateStr) {
		this.issuesReportedDateStr = issuesReportedDateStr;
	}

	public String getAssignByName() {
		return assignByName;
	}

	public void setAssignByName(String assignByName) {
		this.assignByName = assignByName;
	}

	public Long getIssueTaskId() {
		return issueTaskId;
	}

	public void setIssueTaskId(Long issueTaskId) {
		this.issueTaskId = issueTaskId;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}


	public Long getIssueAssignToFirstId() {
		return issueAssignToFirstId;
	}


	public void setIssueAssignToFirstId(Long issueAssignToFirstId) {
		this.issueAssignToFirstId = issueAssignToFirstId;
	}


	public String getVehicleType() {
		return vehicleType;
	}


	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}


	public String getVehicleMaker() {
		return vehicleMaker;
	}


	public void setVehicleMaker(String vehicleMaker) {
		this.vehicleMaker = vehicleMaker;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getVehicleModel() {
		return vehicleModel;
	}


	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	

	public Long getIssueLP_ID() {
		return issueLP_ID;
	}

	public Long getIssueAnalysisId() {
		return issueAnalysisId;
	}


	public void setIssueAnalysisId(Long issueAnalysisId) {
		this.issueAnalysisId = issueAnalysisId;
	}


	public boolean isIssueAnalysis() {
		return isIssueAnalysis;
	}


	public void setIssueAnalysis(boolean isIssueAnalysis) {
		this.isIssueAnalysis = isIssueAnalysis;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getTempSolution() {
		return tempSolution;
	}


	public void setTempSolution(String tempSolution) {
		this.tempSolution = tempSolution;
	}


	public String getFixSolution() {
		return fixSolution;
	}


	public void setFixSolution(String fixSolution) {
		this.fixSolution = fixSolution;
	}


	public String getFuturePlan() {
		return futurePlan;
	}


	public void setFuturePlan(String futurePlan) {
		this.futurePlan = futurePlan;
	}


	public boolean isAvoidable() {
		return avoidable;
	}


	public void setAvoidable(boolean avoidable) {
		this.avoidable = avoidable;
	}


	public String getAvoidableStr() {
		return avoidableStr;
	}


	public void setAvoidableStr(String avoidableStr) {
		this.avoidableStr = avoidableStr;
	}


	public String getHealthStatus() {
		return healthStatus;
	}


	public void setHealthStatus(String healthStatus) {
		this.healthStatus = healthStatus;
	}


	public String getWoRemark() {
		return woRemark;
	}


	public void setWoRemark(String woRemark) {
		this.woRemark = woRemark;
	}


	public Long getWorkOrderTaskId() {
		return workOrderTaskId;
	}


	public void setWorkOrderTaskId(Long workOrderTaskId) {
		this.workOrderTaskId = workOrderTaskId;
	}


	public String getReplacedVehicle() {
		return replacedVehicle;
	}


	public void setReplacedVehicle(String replacedVehicle) {
		this.replacedVehicle = replacedVehicle;
	}


	public String getVehicleStatus() {
		return vehicleStatus;
	}


	public void setVehicleStatus(String vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}


	

	public String getTriripCancelledStatus() {
		return triripCancelledStatus;
	}


	public void setTriripCancelledStatus(String triripCancelledStatus) {
		this.triripCancelledStatus = triripCancelledStatus;
	}



	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Date getIssueChangedOn() {
		return issueChangedOn;
	}


	public void setIssueChangedOn(Date issueChangedOn) {
		this.issueChangedOn = issueChangedOn;
	}


	public String getIssueChangedDate() {
		return issueChangedDate;
	}


	public void setIssueChangedDate(String issueChangedDate) {
		this.issueChangedDate = issueChangedDate;
	}


	public String getWoOrDseLink() {
		return woOrDseLink;
	}


	public void setWoOrDseLink(String woOrDseLink) {
		this.woOrDseLink = woOrDseLink;
	}



	public void setIssueLP_ID(Long issueLP_ID) {
		this.issueLP_ID = issueLP_ID;
	}

	public String getBreakDownLocation() {
		return breakDownLocation;
	}


	public void setBreakDownLocation(String breakDownLocation) {
		this.breakDownLocation = breakDownLocation;
	}


	public Boolean getIsVehicleReplaced() {
		return isVehicleReplaced;
	}


	public void setIsVehicleReplaced(Boolean isVehicleReplaced) {
		this.isVehicleReplaced = isVehicleReplaced;
	}


	public Integer getReplacedWithVid() {
		return replacedWithVid;
	}


	public void setReplacedWithVid(Integer replacedWithVid) {
		this.replacedWithVid = replacedWithVid;
	}


	public String getVehicleReplacedLocation() {
		return vehicleReplacedLocation;
	}


	public void setVehicleReplacedLocation(String vehicleReplacedLocation) {
		this.vehicleReplacedLocation = vehicleReplacedLocation;
	}


	public Boolean getIsTripCancelled() {
		return isTripCancelled;
	}


	public void setIsTripCancelled(Boolean isTripCancelled) {
		this.isTripCancelled = isTripCancelled;
	}


	public Double getCancelledKM() {
		return cancelledKM;
	}


	public void setCancelledKM(Double cancelledKM) {
		this.cancelledKM = cancelledKM;
	}


	public String getVehicleNumber() {
		return vehicleNumber;
	}


	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}


	public String getVehicleReplacedStr() {
		return vehicleReplacedStr;
	}


	public void setVehicleReplacedStr(String vehicleReplacedStr) {
		this.vehicleReplacedStr = vehicleReplacedStr;
	}


	public String getTripCancelledStr() {
		return tripCancelledStr;
	}


	public void setTripCancelledStr(String tripCancelledStr) {
		this.tripCancelledStr = tripCancelledStr;
	}


	public String getReplacedWithVehicle() {
		return replacedWithVehicle;
	}


	public void setReplacedWithVehicle(String replacedWithVehicle) {
		this.replacedWithVehicle = replacedWithVehicle;
	}

	
	public String getPcName() {
		return pcName;
	}

	public void setPcName(String pcName) {
		this.pcName = pcName;
	}


	public String getTransactionNumber() {
		return transactionNumber;
	}


	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}


	@Override
	public String toString() {
		return "IssuesDto [ISSUES_ID=" + ISSUES_ID + ", ISSUES_NUMBER=" + ISSUES_NUMBER + ", issuesNumberStr="
				+ issuesNumberStr + ", ISSUES_ID_ENCRYPT=" + ISSUES_ID_ENCRYPT + ", ISSUES_TYPE=" + ISSUES_TYPE
				+ ", ISSUE_TYPE_ID=" + ISSUE_TYPE_ID + ", ISSUES_VID=" + ISSUES_VID + ", ISSUES_VEHICLE_REGISTRATION="
				+ ISSUES_VEHICLE_REGISTRATION + ", ISSUES_VEHICLE_GROUP=" + ISSUES_VEHICLE_GROUP + ", ISSUES_ODOMETER="
				+ ISSUES_ODOMETER + ", ISSUES_DRIVER_ID=" + ISSUES_DRIVER_ID + ", ISSUES_DRIVER_NAME="
				+ ISSUES_DRIVER_NAME + ", ISSUES_FATHER_NAME=" + ISSUES_FATHER_NAME + ", issueDriverLastName="
				+ issueDriverLastName + ", ISSUES_BRANCH_ID=" + ISSUES_BRANCH_ID + ", ISSUES_BRANCH_NAME="
				+ ISSUES_BRANCH_NAME + ", ISSUES_DEPARTNMENT_ID=" + ISSUES_DEPARTNMENT_ID + ", ISSUES_DEPARTNMENT_NAME="
				+ ISSUES_DEPARTNMENT_NAME + ", ISSUES_REPORTED_DATE=" + ISSUES_REPORTED_DATE + ", issue_start_time="
				+ issue_start_time + ", ISSUES_REPORTED_DATE_ON=" + ISSUES_REPORTED_DATE_ON
				+ ", ISSUES_WORKORDER_DATE_ON=" + ISSUES_WORKORDER_DATE_ON + ", ISSUES_REPORTED_DATE_DIFFRENT="
				+ ISSUES_REPORTED_DATE_DIFFRENT + ", ISSUES_SUMMARY=" + ISSUES_SUMMARY + ", ISSUES_DESCRIPTION="
				+ ISSUES_DESCRIPTION + ", ISSUES_LABELS=" + ISSUES_LABELS + ", ISSUES_LABELS_ID=" + ISSUES_LABELS_ID
				+ ", ISSUES_REPORTED_BY=" + ISSUES_REPORTED_BY + ", ISSUES_REPORTED_BY_ID=" + ISSUES_REPORTED_BY_ID
				+ ", ISSUES_ASSIGN_TO=" + ISSUES_ASSIGN_TO + ", ISSUES_ASSIGN_TO_ID=" + ISSUES_ASSIGN_TO_ID
				+ ", ISSUES_ASSIGN_TO_NAME=" + ISSUES_ASSIGN_TO_NAME + ", CUSTOMER_NAME=" + CUSTOMER_NAME
				+ ", ISSUES_DUE_DATE=" + ISSUES_DUE_DATE + ", ISSUES_DUE_ODOMETER=" + ISSUES_DUE_ODOMETER
				+ ", ISSUES_WORKORDER_ID=" + ISSUES_WORKORDER_ID + ", ISSUES_WORKORDER_NUMBER="
				+ ISSUES_WORKORDER_NUMBER + ", ISSUES_WORKORDER_DATE=" + ISSUES_WORKORDER_DATE + ", ISSUES_STATUS="
				+ ISSUES_STATUS + ", ISSUES_STATUS_ID=" + ISSUES_STATUS_ID + ", ISSUES_TYPE_ID=" + ISSUES_TYPE_ID
				+ ", VEHICLE_GROUP_ID=" + VEHICLE_GROUP_ID + ", COMPANY_ID=" + COMPANY_ID + ", CREATEDBY=" + CREATEDBY
				+ ", LASTMODIFIEDBY=" + LASTMODIFIEDBY + ", CREATEDBYID=" + CREATEDBYID + ", LASTMODIFIEDBYID="
				+ LASTMODIFIEDBYID + ", markForDelete=" + markForDelete + ", userId=" + userId + ", userName="
				+ userName + ", tripNumber=" + tripNumber + ", woRemark=" + woRemark + ", dealerServiceEntriesId="
				+ dealerServiceEntriesId + ", issueAnalysisId=" + issueAnalysisId + ", workOrderTaskId="
				+ workOrderTaskId + ", isIssueAnalysis=" + isIssueAnalysis + ", healthStatus=" + healthStatus
				+ ", breakDownLocation=" + breakDownLocation + ", replacedWithVid=" + replacedWithVid
				+ ", vehicleNumber=" + vehicleNumber + ", vehicleReplacedStr=" + vehicleReplacedStr
				+ ", tripCancelledStr=" + tripCancelledStr + ", replacedWithVehicle=" + replacedWithVehicle
				+ ", transactionNumber=" + transactionNumber + ", CREATED_DATE=" + CREATED_DATE + ", LASTUPDATED_DATE="
				+ LASTUPDATED_DATE + ", CREATED_DATE_ON=" + CREATED_DATE_ON + ", LASTUPDATED_DATE_ON="
				+ LASTUPDATED_DATE_ON + ", issueComment_Comment=" + issueComment_Comment + ", issueComment_Title="
				+ issueComment_Title + ", issueComment_CreatedDate=" + issueComment_CreatedDate
				+ ", issueComment_CreatedDateStr=" + issueComment_CreatedDateStr + ", reason=" + reason
				+ ", tempSolution=" + tempSolution + ", fixSolution=" + fixSolution + ", futurePlan=" + futurePlan
				+ ", avoidableStr=" + avoidableStr + ", avoidable=" + avoidable + ", ISSUES_DIFFERENCES_DATE="
				+ ISSUES_DIFFERENCES_DATE + ", ISSUESTASK=" + ISSUESTASK + ", fromDate=" + fromDate + ", toDate="
				+ toDate + ", driver_mobnumber=" + driver_mobnumber + ", GPS_ODOMETER=" + GPS_ODOMETER
				+ ", ISSUES_SE_ID=" + ISSUES_SE_ID + ", serviceEntries_Number=" + serviceEntries_Number
				+ ", ISSUES_SE_DATEON=" + ISSUES_SE_DATEON + ", ISSUES_SE_DATE=" + ISSUES_SE_DATE + ", ageing=" + ageing
				+ ", vehicleCurrentOdometer=" + vehicleCurrentOdometer + ", categoryId=" + categoryId
				+ ", partCategoryName=" + partCategoryName + ", routeID=" + routeID + ", routeName=" + routeName
				+ ", issueTypeName=" + issueTypeName + ", issueLabelName=" + issueLabelName + ", issueStatusName="
				+ issueStatusName + ", resolvingDate=" + resolvingDate + ", resolvingDateTime=" + resolvingDateTime
				+ ", issuesReportedDateStr=" + issuesReportedDateStr + ", assignByName=" + assignByName
				+ ", issueTaskId=" + issueTaskId + ", totalTime=" + totalTime + ", issueAssignToFirstId="
				+ issueAssignToFirstId + ", vehicleType=" + vehicleType + ", vehicleMaker=" + vehicleMaker
				+ ", vehicleModel=" + vehicleModel + ", location=" + location + ", issueLP_ID=" + issueLP_ID
				+ ", isVehicleReplaced=" + isVehicleReplaced + ", isTripCancelled=" + isTripCancelled
				+ ", replacedVehicle=" + replacedVehicle + ", vehicleStatus=" + vehicleStatus
				+ ", vehicleReplacedLocation=" + vehicleReplacedLocation + ", triripCancelledStatus="
				+ triripCancelledStatus + ", remark=" + remark + ", cancelledKM=" + cancelledKM + ", issueChangedDate="
				+ issueChangedDate + ", woOrDseLink=" + woOrDseLink + ", issueChangedOn=" + issueChangedOn + ", pcName="
				+ pcName + "]";
	}



	
}
