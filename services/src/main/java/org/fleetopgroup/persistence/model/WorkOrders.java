package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "workorders")
public class WorkOrders implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "workorders_id")
	private Long workorders_id;
	
	@Column(name = "workorders_Number", nullable = false)
	private Long workorders_Number;
	
	@Column(name = "vehicle_vid", length = 10)
	private Integer vehicle_vid;

	@Column(name = "vehicle_Odometer", length = 10)
	private Integer vehicle_Odometer;

	@Column(name = "vehicle_Odometer_old", length = 10)
	private Integer vehicle_Odometer_old;

	@Column(name = "workorders_driver_id")
	private Integer workorders_driver_id;

	@Column(name = "workorders_driver_number")
	private String workorders_driver_number;
	
	@Column(name = "assigneeId")
	private Long assigneeId;

	@Column(name = "start_date")
	private Date start_date;

	@Column(name = "due_date")
	private Date due_date;

	@Column(name = "completed_date")
	private Date completed_date;

	@Column(name = "workorders_location_ID")
	private Integer workorders_location_ID;

	@Column(name = "out_work_station")
	private String out_work_station;

	@Column(name = "workorders_route")
	private String workorders_route;

	@Column(name = "workorders_diesel")
	private Long workorders_diesel;
	
	@Column(name = "workorders_statusId")
	private short workorders_statusId;

	@Column(name = "progress", length = 10)
	private Integer progress;

	@Column(name = "indentno", length = 25)
	private String indentno;
	
	@Column(name = "priorityId")
	private short priorityId;

	@Column(name = "total_task", length = 10)
	private Integer total_task;

	@Column(name = "totalsubworktask_cost", length = 10)
	private Double totalsubworktask_cost;

	@Column(name = "totalworktax_cost", length = 10)
	private Double totalworktax_cost;

	@Column(name = "totalworkorder_cost", length = 10)
	private Double totalworkorder_cost;

	@Column(name = "initial_note", length = 250)
	private String initial_note;

	@Basic(optional = false)
	@Column(name = "workorders_document", nullable = false)
	private boolean workorders_document = false;

	@Column(name = "workorders_document_id")
	private Long workorders_document_id;

	/** The value for the Workorders Issues Id field */
	@Column(name = "ISSUES_ID")
	private long ISSUES_ID;
	
	@Column(name = "issueIds")
	private String issueIds;

	@OneToMany(mappedBy = "workorders")
	private Set<WorkOrdersTasks> WorkOrdersTasks;
	
	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	/** The value for the lastUpdated DATE field */
	@Basic(optional = false)
	@Column(name = "lastupdated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdated;
	
	/** The value for which company this record relate */
	@Column(name ="companyId" , nullable = false)
	private Integer companyId;
	
	/** The value for Marking that this record is required or not */
	@Column(name="markForDelete", nullable = false)
	boolean	markForDelete;
	
	@Column(name = "gpsWorkLocation")
	private String	gpsWorkLocation;
	
	@Column(name = "gpsOdometer")
	private Double	gpsOdometer;
	
	@Column(name = "tallyCompanyId")
	private Long tallyCompanyId;
	
	@Column(name = "vehicleReOpenStatusId")
	private short vehicleReOpenStatusId;
	
	@Column(name = "isPendingForTally", nullable = false)
	private boolean isPendingForTally;
	
	@Column(name = "subLocationId")
	private Integer subLocationId;
	
	@Column(name = "approvalStatusId", nullable = false)
	private short	approvalStatusId;
	
	@Column(name = "accidentId")
	private Long	accidentId;

	@Column(name = "workLocationId")
	private Integer workLocationId;

	public WorkOrders() {
		super();
	}

	public WorkOrders(Long workorders_Number, Integer vehicle_vid, Integer vehicle_Odometer,
			Integer vehicle_Odometer_old, Integer workorders_driver_id, 
			Date start_date, Date due_date, Date completed_date, 
			Integer progress,  Integer total_task, Double totalsubworktask_cost,
			Double totalworktax_cost, Double totalworkorder_cost, String initial_note,
			Set<WorkOrdersTasks> workOrdersTasks, 
			Date created, Date lastupdated, Integer companyId) {
		super();
		this.workorders_Number = workorders_Number;
		this.vehicle_vid = vehicle_vid;
		this.vehicle_Odometer = vehicle_Odometer;
		this.vehicle_Odometer_old = vehicle_Odometer_old;
		this.workorders_driver_id = workorders_driver_id;
		this.start_date = start_date;
		this.due_date = due_date;
		this.completed_date = completed_date;
		this.progress = progress;
		this.total_task = total_task;
		this.totalsubworktask_cost = totalsubworktask_cost;
		this.totalworktax_cost = totalworktax_cost;
		this.totalworkorder_cost = totalworkorder_cost;
		this.initial_note = initial_note;
		WorkOrdersTasks = workOrdersTasks;
		this.created = created;
		this.lastupdated = lastupdated;
		this.companyId = companyId;
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
	 * @return the start_date
	 */
	public Date getStart_date() {
		return start_date;
	}

	/**
	 * @param start_date
	 *            the start_date to set
	 */
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	/**
	 * @return the due_date
	 */
	public Date getDue_date() {
		return due_date;
	}

	/**
	 * @param due_date
	 *            the due_date to set
	 */
	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}

	/**
	 * @return the completed_date
	 */
	public Date getCompleted_date() {
		return completed_date;
	}

	public String getWorkorders_driver_number() {
		return workorders_driver_number;
	}

	public void setWorkorders_driver_number(String workorders_driver_number) {
		this.workorders_driver_number = workorders_driver_number;
	}

	public String getOut_work_station() {
		return out_work_station;
	}

	public void setOut_work_station(String out_work_station) {
		this.out_work_station = out_work_station;
	}

	public String getWorkorders_route() {
		return workorders_route;
	}

	public void setWorkorders_route(String workorders_route) {
		this.workorders_route = workorders_route;
	}

	public Long getWorkorders_diesel() {
		return workorders_diesel;
	}

	public void setWorkorders_diesel(Long workorders_diesel) {
		this.workorders_diesel = workorders_diesel;
	}

	/**
	 * @param completed_date
	 *            the completed_date to set
	 */
	public void setCompleted_date(Date completed_date) {
		this.completed_date = completed_date;
	}

	/**
	 * @return the progress
	 */
	public Integer getProgress() {
		return progress;
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
	 * @param progress
	 *            the progress to set
	 */
	public void setProgress(Integer progress) {
		this.progress = progress;
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
		this.totalsubworktask_cost = totalsubworktask_cost;
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
		this.totalworktax_cost = totalworktax_cost;
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
		this.totalworkorder_cost = totalworkorder_cost;
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
	 * @return the workOrdersTasks
	 */
	public Set<WorkOrdersTasks> getWorkOrdersTasks() {
		return WorkOrdersTasks;
	}

	/**
	 * @param workOrdersTasks
	 *            the workOrdersTasks to set
	 */
	public void setWorkOrdersTasks(Set<WorkOrdersTasks> workOrdersTasks) {
		WorkOrdersTasks = workOrdersTasks;
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


	public Integer getWorkorders_location_ID() {
		return workorders_location_ID;
	}

	public void setWorkorders_location_ID(Integer workorders_location_ID) {
		this.workorders_location_ID = workorders_location_ID;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public Date getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	public String getIndentno() {
		return indentno;
	}

	public void setIndentno(String indentno) {
		this.indentno = indentno;
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
		this.gpsOdometer = gpsOdometer;
	}

	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public short getVehicleReOpenStatusId() {
		return vehicleReOpenStatusId;
	}

	public void setVehicleReOpenStatusId(short vehicleReOpenStatusId) {
		this.vehicleReOpenStatusId = vehicleReOpenStatusId;
	}

	public boolean isPendingForTally() {
		return isPendingForTally;
	}

	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}

	public Integer getSubLocationId() {
		return subLocationId;
	}

	public void setSubLocationId(Integer subLocationId) {
		this.subLocationId = subLocationId;
	}

	public short getApprovalStatusId() {
		return approvalStatusId;
	}

	public void setApprovalStatusId(short approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}

	public Long getAccidentId() {
		return accidentId;
	}

	public void setAccidentId(Long accidentId) {
		this.accidentId = accidentId;
	}
	
	public Integer getWorkLocationId() {
		return workLocationId;
	}

	public void setWorkLocationId(Integer workLocationId) {
		this.workLocationId = workLocationId;
	}

	public String getIssueIds() {
		return issueIds;
	}

	public void setIssueIds(String issueIds) {
		this.issueIds = issueIds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vehicle_Odometer == null) ? 0 : vehicle_Odometer.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkOrders other = (WorkOrders) obj;
		if (vehicle_Odometer == null) {
			if (other.vehicle_Odometer != null)
				return false;
		} else if (!vehicle_Odometer.equals(other.vehicle_Odometer))
			return false;
		
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("WorkOrders [workorders_id=");
		builder.append(workorders_id);
		builder.append(", workorders_Number=");
		builder.append(workorders_Number);
		builder.append(", vehicle_vid=");
		builder.append(vehicle_vid);
		builder.append(", vehicle_Odometer=");
		builder.append(vehicle_Odometer);
		builder.append(", vehicle_Odometer_old=");
		builder.append(vehicle_Odometer_old);
		builder.append(", workorders_driver_id=");
		builder.append(workorders_driver_id);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", due_date=");
		builder.append(due_date);
		builder.append(", completed_date=");
		builder.append(completed_date);
		builder.append(", progress=");
		builder.append(progress);
		builder.append(", indentno=");
		builder.append(indentno);
		builder.append(", total_task=");
		builder.append(total_task);
		builder.append(", totalsubworktask_cost=");
		builder.append(totalsubworktask_cost);
		builder.append(", totalworktax_cost=");
		builder.append(totalworktax_cost);
		builder.append(", totalworkorder_cost=");
		builder.append(totalworkorder_cost);
		builder.append(", initial_note=");
		builder.append(initial_note);
		builder.append(", workorders_document=");
		builder.append(workorders_document);
		builder.append(", workorders_document_id=");
		builder.append(workorders_document_id);
		builder.append(", ISSUES_ID=");
		builder.append(ISSUES_ID);
		builder.append(", WorkOrdersTasks=");
		builder.append(WorkOrdersTasks != null ? toString(WorkOrdersTasks, maxLen) : null);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", tallyCompanyId=");
		builder.append(tallyCompanyId);
		builder.append(", subLocationId=");
		builder.append(subLocationId);
		builder.append("]");
		return builder.toString();
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

}
