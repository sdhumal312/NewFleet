package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 *
 *
 */

public class WorkOrdersDto {
	
	public static final short WORK_ORDER		= 1;
	public static final short SERVICE_ENTRIES	= 2;
	public static final short ALL	= 3;

	private Long 	workorders_id;
	private Long 	workorders_Number;
	private Integer vehicle_vid;
	private String 	vehicle_registration;
	private String 	vehicle_chasis;
	private String 	vehicle_engine;
	private Integer vehicle_Odometer;
	private Integer vehicle_Odometer_old;
	private Integer workorders_driver_id;
	private String 	workorders_drivername;
	private String 	workorders_driver_number;
	private String 	assignee;
	private Long   	assigneeId;
	private String 	start_date;
	private String 	start_time;
	private String 	due_date;
	private String 	due_time;
	private String 	out_work_station;
	private Long 	workorders_diesel;
	private Date 	start_date_D;
	private Date 	due_date_D;
	private String 	completed_date;
	private String 	workorders_location;
	private Integer workorders_location_ID;
	private String 	new_workorders_location;
	private String 	workorders_route;
	private String 	workorders_status;
	private Integer progress;
	private String 	indentno;
	private String 	priority;
	private short 	priorityId;
	private Integer total_task;
	private Double 	totalsubworktask_cost;
	private Double 	totalworktax_cost;
	private Double 	totalworkorder_cost;
	private String 	initial_note;
	private boolean workorders_document;
	private Long 	workorders_document_id;
	private long 	ISSUES_ID;
	private String 	createdBy;
	private Long 	createdById;
	private String 	lastModifiedBy;
	private Long 	lastModifiedById;
	private boolean markForDelete;
	private String 	created;
	private String 	lastupdated;
	private short 	workorders_statusId;
	private Date 	start_dateOn;
	private Date 	due_dateOn;
	private Date 	completed_dateOn;
	private Date 	createdOn;
	private Date 	lastupdatedOn;
	private Integer companyId;
	private String	serviceDate;
	private String	serviceMonth;
	private String	serviceYear;
	private String	gpsWorkLocation;
	private Double	gpsOdometer;	
	private Integer job_typetaskId;
	private Integer job_subtypetask_id;
	private String Job_Type;
	private String Job_ROT;
	private Integer laberid;
	private Double laberhourscost;
	private Double eachlabercost;
	private Double totalcost;
	private Long partid;
	private String	completeDateStr;
	private Long workordertaskid;
	private String workorders_Numbers;
	private String labername;
	private int driver_id;
	private String driver_firstname;
	private Long workOrderOpenCount;
	private Long workOrderCloseCount;
	private Long tallyCompanyId;
	private String tallyCompanyName;
	private short	vehicleReOpenStatusId;
	private long ageing;
	private Long service_id;
	private String issue; 
	private Integer subLocationId;
	private String subLocation;
	private short approvalStatusId;
	private String approvalStatus;
	private String	accident;
	private Long	accidentId;
	private String approvalStatusForMob;
	private short	approvalColorId;
	private Long userId;
	private String userName;
	private Long issueNumber;
	private String	issueSummary;
	private String	issueIds;
	private Integer	workLocationId;
	private String	workLocation;
	private Long	vehicleModelId;
	private String	vehicleModel;
	
	private String IssueCreated;
	private String SRCreated;
	private String SubType;
	private String partName;
	private String partNo;
	private Double partQt;
	private String IssueCategory;
	private Long DseNo;
	private Long DseId;
	private String InvNo;
	private String vendorName;
	private String SRprogram;
	private String SRDetails;
	private int woType;
	
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

	/**
	 * @return the workorders_id
	 */
	public Long getWorkorders_id() {
		return workorders_id;
	}

	/**
	 * @param workorders_id
	 *            the workorders_id to set
	 */
	public void setWorkorders_id(Long workorders_id) {
		this.workorders_id = workorders_id;
	}

	public Long getWorkorders_Number() {
		return workorders_Number;
	}

	public void setWorkorders_Number(Long workorders_Number) {
		this.workorders_Number = workorders_Number;
	}

	/**
	 * @return the vehicle_vid
	 */
	public Integer getVehicle_vid() {
		return vehicle_vid;
	}

	/**
	 * @param vehicle_vid
	 *            the vehicle_vid to set
	 */
	public void setVehicle_vid(Integer vehicle_vid) {
		this.vehicle_vid = vehicle_vid;
	}

	
	public Date getStart_date_D() {
		return start_date_D;
	}

	public void setStart_date_D(Date start_date_D) {
		this.start_date_D = start_date_D;
	}

	public Date getDue_date_D() {
		return due_date_D;
	}

	public void setDue_date_D(Date due_date_D) {
		this.due_date_D = due_date_D;
	}

	/**
	 * @return the vehicle_registration
	 */
	public String getVehicle_registration() {
		return vehicle_registration;
	}

	/**
	 * @param vehicle_registration
	 *            the vehicle_registration to set
	 */
	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public String getVehicle_chasis() {
		return vehicle_chasis;
	}

	public void setVehicle_chasis(String vehicle_chasis) {
		this.vehicle_chasis = vehicle_chasis;
	}

	public String getVehicle_engine() {
		return vehicle_engine;
	}

	public void setVehicle_engine(String vehicle_engine) {
		this.vehicle_engine = vehicle_engine;
	}

	/**
	 * @return the vehicle_Odometer
	 */
	public Integer getVehicle_Odometer() {
		return vehicle_Odometer;
	}

	/**
	 * @param vehicle_Odometer
	 *            the vehicle_Odometer to set
	 */
	public void setVehicle_Odometer(Integer vehicle_Odometer) {
		this.vehicle_Odometer = vehicle_Odometer;
	}

	/**
	 * @return the vehicle_Odometer_old
	 */
	public Integer getVehicle_Odometer_old() {
		return vehicle_Odometer_old;
	}

	/**
	 * @param vehicle_Odometer_old
	 *            the vehicle_Odometer_old to set
	 */
	public void setVehicle_Odometer_old(Integer vehicle_Odometer_old) {
		this.vehicle_Odometer_old = vehicle_Odometer_old;
	}

	/**
	 * @return the workorders_drivername
	 */
	public String getWorkorders_drivername() {
		return workorders_drivername;
	}

	/**
	 * @param workorders_drivername
	 *            the workorders_drivername to set
	 */
	public void setWorkorders_drivername(String workorders_drivername) {
		this.workorders_drivername = workorders_drivername;
	}

	public String getIndentno() {
		return indentno;
	}

	public void setIndentno(String indentno) {
		this.indentno = indentno;
	}

	/**
	 * @return the assignee
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * @param assignee
	 *            the assignee to set
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	/**
	 * @return the start_date
	 */
	public String getStart_date() {
		return start_date;
	}

	/**
	 * @param start_date
	 *            the start_date to set
	 */
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public short getApprovalStatusId() {
		return approvalStatusId;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public void setApprovalStatusId(short approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}

	/**
	 * @return the due_date
	 */
	public String getDue_date() {
		return due_date;
	}

	/**
	 * @param due_date
	 *            the due_date to set
	 */
	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	/**
	 * @return the completed_date
	 */
	public String getCompleted_date() {
		return completed_date;
	}

	/**
	 * @param completed_date
	 *            the completed_date to set
	 */
	public void setCompleted_date(String completed_date) {
		this.completed_date = completed_date;
	}

	/**
	 * @return the workorders_location
	 */
	public String getWorkorders_location() {
		return workorders_location;
	}

	/**
	 * @param workorders_location
	 *            the workorders_location to set
	 */
	public void setWorkorders_location(String workorders_location) {
		this.workorders_location = workorders_location;
	}

	/**
	 * @return the workorders_status
	 */
	public String getWorkorders_status() {
		return workorders_status;
	}

	
	public Integer getWorkorders_location_ID() {
		return workorders_location_ID;
	}

	public void setWorkorders_location_ID(Integer workorders_location_ID) {
		this.workorders_location_ID = workorders_location_ID;
	}

	/**
	 * @param workorders_status
	 *            the workorders_status to set
	 */
	public void setWorkorders_status(String workorders_status) {
		this.workorders_status = workorders_status;
	}

	/**
	 * @return the iSSUES_ID
	 */
	public long getISSUES_ID() {
		return ISSUES_ID;
	}

	/**
	 * @param iSSUES_ID
	 *            the iSSUES_ID to set
	 */
	public void setISSUES_ID(long iSSUES_ID) {
		ISSUES_ID = iSSUES_ID;
	}

	/**
	 * @return the progress
	 */
	public Integer getProgress() {
		return progress;
	}

	/**
	 * @param progress
	 *            the progress to set
	 */
	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * @return the total_task
	 */
	public Integer getTotal_task() {
		return total_task;
	}

	/**
	 * @param total_task
	 *            the total_task to set
	 */
	public void setTotal_task(Integer total_task) {
		this.total_task = total_task;
	}

	/**
	 * @return the totalsubworktask_cost
	 */
	public Double getTotalsubworktask_cost() {
		return totalsubworktask_cost;
	}

	/**
	 * @param totalsubworktask_cost
	 *            the totalsubworktask_cost to set
	 */
	public void setTotalsubworktask_cost(Double totalsubworktask_cost) {
		this.totalsubworktask_cost = Utility.round(totalsubworktask_cost, 2);
	}

	/**
	 * @return the totalworktax_cost
	 */
	public Double getTotalworktax_cost() {
		return totalworktax_cost;
	}

	/**
	 * @param totalworktax_cost
	 *            the totalworktax_cost to set
	 */
	public void setTotalworktax_cost(Double totalworktax_cost) {
		this.totalworktax_cost = Utility.round(totalworktax_cost, 2);
	}

	/**
	 * @return the totalworkorder_cost
	 */
	public Double getTotalworkorder_cost() {
		return totalworkorder_cost;
	}

	/**
	 * @param totalworkorder_cost
	 *            the totalworkorder_cost to set
	 */
	public void setTotalworkorder_cost(Double totalworkorder_cost) {
		this.totalworkorder_cost = Utility.round(totalworkorder_cost, 2);
	}

	/**
	 * @return the initial_note
	 */
	public String getInitial_note() {
		return initial_note;
	}

	/**
	 * @param initial_note
	 *            the initial_note to set
	 */
	public void setInitial_note(String initial_note) {
		this.initial_note = initial_note;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the status
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public String getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(String lastupdated) {
		this.lastupdated = lastupdated;
	}

	/**
	 * @return the workorders_driver_id
	 */
	public Integer getWorkorders_driver_id() {
		return workorders_driver_id;
	}

	/**
	 * @param workorders_driver_id
	 *            the workorders_driver_id to set
	 */
	public void setWorkorders_driver_id(Integer workorders_driver_id) {
		this.workorders_driver_id = workorders_driver_id;
	}

	/**
	 * @return the workorders_document
	 */
	public boolean isWorkorders_document() {
		return workorders_document;
	}

	/**
	 * @param workorders_document
	 *            the workorders_document to set
	 */
	public void setWorkorders_document(boolean workorders_document) {
		this.workorders_document = workorders_document;
	}

	/**
	 * @return the workorders_document_id
	 */
	public Long getWorkorders_document_id() {
		return workorders_document_id;
	}

	/**
	 * @param workorders_document_id
	 *            the workorders_document_id to set
	 */
	public void setWorkorders_document_id(Long workorders_document_id) {
		this.workorders_document_id = workorders_document_id;
	}

	/**
	 * @return the assigneeId
	 */
	public Long getAssigneeId() {
		return assigneeId;
	}

	/**
	 * @param assigneeId the assigneeId to set
	 */
	public void setAssigneeId(Long assigneeId) {
		this.assigneeId = assigneeId;
	}

	/**
	 * @return the priorityId
	 */
	public short getPriorityId() {
		return priorityId;
	}

	/**
	 * @param priorityId the priorityId to set
	 */
	public void setPriorityId(short priorityId) {
		this.priorityId = priorityId;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	/**
	 * @return the workorders_statusId
	 */
	public short getWorkorders_statusId() {
		return workorders_statusId;
	}

	/**
	 * @param workorders_statusId the workorders_statusId to set
	 */
	public void setWorkorders_statusId(short workorders_statusId) {
		this.workorders_statusId = workorders_statusId;
	}

	/**
	 * @return the start_dateOn
	 */
	public Date getStart_dateOn() {
		return start_dateOn;
	}

	/**
	 * @param start_dateOn the start_dateOn to set
	 */
	public void setStart_dateOn(Date start_dateOn) {
		this.start_dateOn = start_dateOn;
	}

	/**
	 * @return the due_dateOn
	 */
	public Date getDue_dateOn() {
		return due_dateOn;
	}

	/**
	 * @param due_dateOn the due_dateOn to set
	 */
	public void setDue_dateOn(Date due_dateOn) {
		this.due_dateOn = due_dateOn;
	}

	/**
	 * @return the completed_dateOn
	 */
	public Date getCompleted_dateOn() {
		return completed_dateOn;
	}

	/**
	 * @param completed_dateOn the completed_dateOn to set
	 */
	public void setCompleted_dateOn(Date completed_dateOn) {
		this.completed_dateOn = completed_dateOn;
	}

	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the lastupdatedOn
	 */
	public Date getLastupdatedOn() {
		return lastupdatedOn;
	}

	/**
	 * @param lastupdatedOn the lastupdatedOn to set
	 */
	public void setLastupdatedOn(Date lastupdatedOn) {
		this.lastupdatedOn = lastupdatedOn;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getServiceMonth() {
		return serviceMonth;
	}

	public void setServiceMonth(String serviceMonth) {
		this.serviceMonth = serviceMonth;
	}

	public String getServiceYear() {
		return serviceYear;
	}

	public void setServiceYear(String serviceYear) {
		this.serviceYear = serviceYear;
	}

	public String getWorkorders_driver_number() {
		return workorders_driver_number;
	}

	public void setWorkorders_driver_number(String workorders_driver_number) {
		this.workorders_driver_number = workorders_driver_number;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getDue_time() {
		return due_time;
	}

	public void setDue_time(String due_time) {
		this.due_time = due_time;
	}

	public String getOut_work_station() {
		return out_work_station;
	}

	public void setOut_work_station(String out_work_station) {
		this.out_work_station = out_work_station;
	}

	public Long getWorkorders_diesel() {
		return workorders_diesel;
	}

	public void setWorkorders_diesel(Long workorders_diesel) {
		this.workorders_diesel = workorders_diesel;
	}

	public String getNew_workorders_location() {
		return new_workorders_location;
	}

	public void setNew_workorders_location(String new_workorders_location) {
		this.new_workorders_location = new_workorders_location;
	}

	public String getWorkorders_route() {
		return workorders_route;
	}

	public void setWorkorders_route(String workorders_route) {
		this.workorders_route = workorders_route;
	}

	public String getGpsWorkLocation() {
		return gpsWorkLocation;
	}

	public Double getGpsOdometer() {
		return gpsOdometer;
	}

	public void setGpsWorkLocation(String gpsWorkLocation) {
		this.gpsWorkLocation = gpsWorkLocation;
	}

	public void setGpsOdometer(Double gpsOdometer) {
		this.gpsOdometer = Utility.round(gpsOdometer, 2) ;
	}
	
	public Integer getJob_typetaskId() {
		return job_typetaskId;
	}

	public void setJob_typetaskId(Integer job_typetaskId) {
		this.job_typetaskId = job_typetaskId;
	}
	
	public Integer getJob_subtypetask_id() {
		return job_subtypetask_id;
	}

	public void setJob_subtypetask_id(Integer job_subtypetask_id) {
		this.job_subtypetask_id = job_subtypetask_id;
	}
	

	public String getJob_Type() {
		return Job_Type;
	}

	public void setJob_Type(String job_Type) {
		Job_Type = job_Type;
	}
	
	
	public String getJob_ROT() {
		return Job_ROT;
	}

	public void setJob_ROT(String job_ROT) {
		Job_ROT = job_ROT;
	}
	
	public Integer getLaberid() {
		return laberid;
	}

	public void setLaberid(Integer laberid) {
		this.laberid = laberid;
	}

	public Double getLaberhourscost() {
		return laberhourscost;
	}

	public void setLaberhourscost(Double laberhourscost) {
		this.laberhourscost = Utility.round( laberhourscost, 2);
	}

	public Double getEachlabercost() {
		return eachlabercost;
	}

	public void setEachlabercost(Double eachlabercost) {
		this.eachlabercost = Utility.round(eachlabercost, 2);
	}
	
	public Double getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(Double totalcost) {
		this.totalcost =   Utility.round(totalcost, 2);
	}
	
	public Long getPartid() {
		return partid;
	}

	public void setPartid(Long partid) {
		this.partid = partid;
	}
	
	
	public String getCompleteDateStr() {
		return completeDateStr;
	}

	public void setCompleteDateStr(String completeDateStr) {
		this.completeDateStr = completeDateStr;
	}

	
	public Long getWorkordertaskid() {
		return workordertaskid;
	}

	public void setWorkordertaskid(Long workordertaskid) {
		this.workordertaskid = workordertaskid;
	}
	
	public String getWorkorders_Numbers() {
		return workorders_Numbers;
	}

	public void setWorkorders_Numbers(String workorders_Numbers) {
		this.workorders_Numbers = workorders_Numbers;
	}

	
	public String getLabername() {
		return labername;
	}

	public void setLabername(String labername) {
		this.labername = labername;
	}
	
	public int getDriver_id() {
		return driver_id;
	}

	public String getDriver_firstname() {
		return driver_firstname;
	}

	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
	}

	public void setDriver_firstname(String driver_firstname) {
		this.driver_firstname = driver_firstname;
	}

	public Long getWorkOrderOpenCount() {
		return workOrderOpenCount;
	}

	public void setWorkOrderOpenCount(Long workOrderCount) {
		this.workOrderOpenCount = workOrderCount;
	}

	public Long getWorkOrderCloseCount() {
		return workOrderCloseCount;
	}

	public void setWorkOrderCloseCount(Long workOrderCloseCount) {
		this.workOrderCloseCount = workOrderCloseCount;
	}

	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public String getTallyCompanyName() {
		return tallyCompanyName;
	}

	public void setTallyCompanyName(String tallyCompanyName) {
		this.tallyCompanyName = tallyCompanyName;
	}

	public short getVehicleReOpenStatusId() {
		return vehicleReOpenStatusId;
	}

	public void setVehicleReOpenStatusId(short vehicleReOpenStatusId) {
		this.vehicleReOpenStatusId = vehicleReOpenStatusId;
	}

	public Long getService_id() {
		return service_id;
	}

	public void setService_id(Long service_id) {
		this.service_id = service_id;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public long getAgeing() {
		return ageing;
	}

	public void setAgeing(long ageing) {
		this.ageing = ageing;
	}

	public Integer getSubLocationId() {
		return subLocationId;
	}

	public void setSubLocationId(Integer subLocationId) {
		this.subLocationId = subLocationId;
	}

	public String getSubLocation() {
		return subLocation;
	}

	public void setSubLocation(String subLocation) {
		this.subLocation = subLocation;
	}
	
	public static final int getParseDriverID_STRING_TO_ID(String value) {
		if (value == null) {
			return 0;
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}
	

	public String getAccident() {
		return accident;
	}

	public void setAccident(String accident) {
		this.accident = accident;
	}

	public Long getAccidentId() {
		return accidentId;
	}

	public void setAccidentId(Long accidentId) {
		this.accidentId = accidentId;
	}

	public Long getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(Long issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getIssueSummary() {
		return issueSummary;
	}

	public void setIssueSummary(String issueSummary) {
		this.issueSummary = issueSummary;
	}
	
	public String getWorkLocation() {
		return workLocation;
	}

	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}
	
	public Long getVehicleModelId() {
		return vehicleModelId;
	}

	public void setVehicleModelId(Long vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	
	public String getIssueCreated() {
		return IssueCreated;
	}

	public void setIssueCreated(String issueCreated) {
		IssueCreated = issueCreated;
	}

	public String getSRCreated() {
		return SRCreated;
	}

	public void setSRCreated(String sRCreated) {
		SRCreated = sRCreated;
	}

	public String getSubType() {
		return SubType;
	}

	public void setSubType(String subType) {
		SubType = subType;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public Double getPartQt() {
		return partQt;
	}

	public void setPartQt(Double partQt) {
		this.partQt = partQt;
	}

	public String getIssueCategory() {
		return IssueCategory;
	}

	public void setIssueCategory(String issueCategory) {
		IssueCategory = issueCategory;
	}

	public Long getDseNo() {
		return DseNo;
	}

	public void setDseNo(Long dseNo) {
		DseNo = dseNo;
	}

	public Long getDseId() {
		return DseId;
	}

	public void setDseId(Long dseId) {
		DseId = dseId;
	}

	public String getInvNo() {
		return InvNo;
	}

	public void setInvNo(String invNo) {
		InvNo = invNo;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getSRprogram() {
		return SRprogram;
	}

	public void setSRprogram(String sRprogram) {
		SRprogram = sRprogram;
	}

	public String getSRDetails() {
		return SRDetails;
	}

	public void setSRDetails(String sRDetails) {
		SRDetails = sRDetails;
	}

	public int getWoType() {
		return woType;
	}

	public void setWoType(int woType) {
		this.woType = woType;
	}

	public static short getWorkOrder() {
		return WORK_ORDER;
	}

	public static short getServiceEntries() {
		return SERVICE_ENTRIES;
	}

	public static short getAll() {
		return ALL;
	}
	
	@Override
	public String toString() {
		return "WorkOrdersDto [workorders_id=" + workorders_id + ", workorders_Number=" + workorders_Number
				+ ", vehicle_vid=" + vehicle_vid + ", vehicle_registration=" + vehicle_registration
				+ ", vehicle_chasis=" + vehicle_chasis + ", vehicle_engine=" + vehicle_engine + ", vehicle_Odometer="
				+ vehicle_Odometer + ", vehicle_Odometer_old=" + vehicle_Odometer_old + ", workorders_driver_id="
				+ workorders_driver_id + ", workorders_drivername=" + workorders_drivername
				+ ", workorders_driver_number=" + workorders_driver_number + ", assignee=" + assignee + ", assigneeId="
				+ assigneeId + ", start_date=" + start_date + ", start_time=" + start_time + ", due_date=" + due_date
				+ ", due_time=" + due_time + ", out_work_station=" + out_work_station + ", workorders_diesel="
				+ workorders_diesel + ", start_date_D=" + start_date_D + ", due_date_D=" + due_date_D
				+ ", completed_date=" + completed_date + ", workorders_location=" + workorders_location
				+ ", workorders_location_ID=" + workorders_location_ID + ", new_workorders_location="
				+ new_workorders_location + ", workorders_route=" + workorders_route + ", workorders_status="
				+ workorders_status + ", progress=" + progress + ", indentno=" + indentno + ", priority=" + priority
				+ ", priorityId=" + priorityId + ", total_task=" + total_task + ", totalsubworktask_cost="
				+ totalsubworktask_cost + ", totalworktax_cost=" + totalworktax_cost + ", totalworkorder_cost="
				+ totalworkorder_cost + ", initial_note=" + initial_note + ", workorders_document="
				+ workorders_document + ", workorders_document_id=" + workorders_document_id + ", ISSUES_ID="
				+ ISSUES_ID + ", createdBy=" + createdBy + ", createdById=" + createdById + ", lastModifiedBy="
				+ lastModifiedBy + ", lastModifiedById=" + lastModifiedById + ", markForDelete=" + markForDelete
				+ ", created=" + created + ", lastupdated=" + lastupdated + ", workorders_statusId="
				+ workorders_statusId + ", start_dateOn=" + start_dateOn + ", due_dateOn=" + due_dateOn
				+ ", completed_dateOn=" + completed_dateOn + ", createdOn=" + createdOn + ", lastupdatedOn="
				+ lastupdatedOn + ", companyId=" + companyId + ", serviceDate=" + serviceDate + ", serviceMonth="
				+ serviceMonth + ", serviceYear=" + serviceYear + ", gpsWorkLocation=" + gpsWorkLocation
				+ ", gpsOdometer=" + gpsOdometer + ", job_typetaskId=" + job_typetaskId + ", job_subtypetask_id="
				+ job_subtypetask_id + ", Job_Type=" + Job_Type + ", Job_ROT=" + Job_ROT + ", laberid=" + laberid
				+ ", laberhourscost=" + laberhourscost + ", eachlabercost=" + eachlabercost + ", totalcost=" + totalcost
				+ ", partid=" + partid + ", completeDateStr=" + completeDateStr + ", workordertaskid=" + workordertaskid
				+ ", workorders_Numbers=" + workorders_Numbers + ", labername=" + labername + ", driver_id=" + driver_id
				+ ", driver_firstname=" + driver_firstname + ", workOrderOpenCount=" + workOrderOpenCount
				+ ", workOrderCloseCount=" + workOrderCloseCount + ", tallyCompanyId=" + tallyCompanyId
				+ ", tallyCompanyName=" + tallyCompanyName + ", vehicleReOpenStatusId=" + vehicleReOpenStatusId
				+ ", ageing=" + ageing + ", service_id=" + service_id + ", issue=" + issue + ", subLocationId="
				+ subLocationId + ", subLocation=" + subLocation + ", approvalStatusId=" + approvalStatusId
				+ ", approvalStatus=" + approvalStatus + ", accident=" + accident + ", accidentId=" + accidentId
				+ ", approvalStatusForMob=" + approvalStatusForMob + ", approvalColorId=" + approvalColorId
				+ ", userId=" + userId + ", userName=" + userName + ", issueNumber=" + issueNumber + ", issueSummary="
				+ issueSummary + ", issueIds=" + issueIds + ", workLocationId=" + workLocationId + ", workLocation="
				+ workLocation + ", vehicleModelId=" + vehicleModelId + ", vehicleModel=" + vehicleModel
				+ ", IssueCreated=" + IssueCreated + ", SRCreated=" + SRCreated + ", SubType=" + SubType + ", partName="
				+ partName + ", partNo=" + partNo + ", partQt=" + partQt + ", IssueCategory=" + IssueCategory
				+ ", DseNo=" + DseNo + ", DseId=" + DseId + ", InvNo=" + InvNo + ", vendorName=" + vendorName
				+ ", SRprogram=" + SRprogram + ", SRDetails=" + SRDetails + ", woType=" + woType + "]";
	}

	public String getApprovalStatusForMob() {
		return approvalStatusForMob;
	}

	public void setApprovalStatusForMob(String approvalStatusForMob) {
		this.approvalStatusForMob = approvalStatusForMob;
	}

	public short getApprovalColorId() {
		return approvalColorId;
	}

	public void setApprovalColorId(short approvalColorId) {
		this.approvalColorId = approvalColorId;
	}

	public Integer getWorkLocationId() {
		return workLocationId;
	}

	public String getIssueIds() {
		return issueIds;
	}

	public void setIssueIds(String issueIds) {
		this.issueIds = issueIds;
	}

	public void setWorkLocationId(Integer workLocationId) {
		this.workLocationId = workLocationId;
	}

	
}
