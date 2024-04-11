package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "workorderstasks")
public class WorkOrdersTasks implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "workordertaskid")
	private Long workordertaskid;

	@Column(name = "job_typetaskId")
	private Integer job_typetaskId;

	@Column(name = "job_subtypetask_id")
	private Integer job_subtypetask_id;

	@Column(name = "jobTypeRemark", length = 255)
	private String jobTypeRemark;
	

	
	@Column(name = "mark_complete", length = 5)
	private Integer mark_complete;

	@Column(name = "totalpart_cost", length = 10)
	private Double totalpart_cost;

	@Column(name = "totallaber_cost", length = 10)
	private Double totallaber_cost;

	@Column(name = "totaltask_cost", length = 10)
	private Double totaltask_cost;

	@Column(name = "vehicle_vid", length = 10)
	private Integer vehicle_vid;

	@ManyToOne
	@JoinColumn(name = "workorders_id")
	private WorkOrders workorders;

	/** The value for the last_occurred_date DATE field */
	@Basic(optional = true)
	@Column(name = "last_occurred_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date last_occurred_date;

	/** The value for the last_occurred_woId back end process */
	@Column(name = "last_occurred_woId", nullable = true)
	private Long last_occurred_woId;

	/** The value for the last_occurred_odameter back end process */
	@Column(name = "last_occurred_odameter", nullable = true)
	private Integer last_occurred_odameter;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "service_id")
	private Long service_id;
	
	@Column(name = "issueIds")
	private Integer issueIds;
	
	@Column(name ="categoryId")
	private Integer categoryId;
	
	@Column(name = "reasonRepairTypeId")
	private Integer reasonRepairTypeId;
	
	public Integer getReasonRepairTypeId() {
		return reasonRepairTypeId;
	}


	public void setReasonRepairTypeId(Integer reasonRepairTypeId) {
		this.reasonRepairTypeId = reasonRepairTypeId;
	}

	public WorkOrdersTasks() {

	}


	/**
	 * @return the workordertaskid
	 */
	public Long getWorkordertaskid() {
		return workordertaskid;
	}

	/**
	 * @param workordertaskid
	 *            the workordertaskid to set
	 */
	public void setWorkordertaskid(Long workordertaskid) {
		this.workordertaskid = workordertaskid;
	}

	
	/**
	 * @return the mark_complete
	 */
	public Integer getMark_complete() {
		return mark_complete;
	}

	/**
	 * @param mark_complete
	 *            the mark_complete to set
	 */
	public void setMark_complete(Integer mark_complete) {
		this.mark_complete = mark_complete;
	}

	/**
	 * @return the totalpart_cost
	 */
	public Double getTotalpart_cost() {
		return totalpart_cost;
	}

	/**
	 * @param totalpart_cost
	 *            the totalpart_cost to set
	 */
	public void setTotalpart_cost(Double totalpart_cost) {
		this.totalpart_cost = totalpart_cost;
	}

	/**
	 * @return the totallaber_cost
	 */
	public Double getTotallaber_cost() {
		return totallaber_cost;
	}

	/**
	 * @param totallaber_cost
	 *            the totallaber_cost to set
	 */
	public void setTotallaber_cost(Double totallaber_cost) {
		this.totallaber_cost = totallaber_cost;
	}

	/**
	 * @return the totaltask_cost
	 */
	public Double getTotaltask_cost() {
		return totaltask_cost;
	}

	/**
	 * @param totaltask_cost
	 *            the totaltask_cost to set
	 */
	public void setTotaltask_cost(Double totaltask_cost) {
		this.totaltask_cost = totaltask_cost;
	}

	/**
	 * @return the workorders
	 */
	public WorkOrders getWorkorders() {
		return workorders;
	}

	/**
	 * @param workorders
	 *            the workorders to set
	 */
	public void setWorkorders(WorkOrders workorders) {
		this.workorders = workorders;
	}

	

	/**
	 * @return the job_typetaskId
	 */
	public Integer getJob_typetaskId() {
		return job_typetaskId;
	}

	/**
	 * @param job_typetaskId the job_typetaskId to set
	 */
	public void setJob_typetaskId(Integer job_typetaskId) {
		this.job_typetaskId = job_typetaskId;
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

	public Integer getJob_subtypetask_id() {
		return job_subtypetask_id;
	}

	public void setJob_subtypetask_id(Integer job_subtypetask_id) {
		this.job_subtypetask_id = job_subtypetask_id;
	}

	public String getJobTypeRemark() {
		return jobTypeRemark;
	}

	public void setJobTypeRemark(String jobTypeRemark) {
		this.jobTypeRemark = jobTypeRemark;
	}

	/**
	 * @return the last_occurred_date
	 */
	public Date getLast_occurred_date() {
		return last_occurred_date;
	}

	/**
	 * @param last_occurred_date
	 *            the last_occurred_date to set
	 */
	public void setLast_occurred_date(Date last_occurred_date) {
		this.last_occurred_date = last_occurred_date;
	}

	/**
	 * @return the last_occurred_woId
	 */
	public Long getLast_occurred_woId() {
		return last_occurred_woId;
	}

	/**
	 * @param last_occurred_woId
	 *            the last_occurred_woId to set
	 */
	public void setLast_occurred_woId(Long last_occurred_woId) {
		this.last_occurred_woId = last_occurred_woId;
	}

	/**
	 * @return the last_occurred_odameter
	 */
	public Integer getLast_occurred_odameter() {
		return last_occurred_odameter;
	}

	/**
	 * @param last_occurred_odameter
	 *            the last_occurred_odameter to set
	 */
	public void setLast_occurred_odameter(Integer last_occurred_odameter) {
		this.last_occurred_odameter = last_occurred_odameter;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Long getService_id() {
		return service_id;
	}


	public void setService_id(Long service_id) {
		this.service_id = service_id;
	}
	

	

	public int getIssueIds() {
		return issueIds;
	}


	public void setIssueIds(int issueIds) {
		this.issueIds = issueIds;
	}


	public Integer getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkOrdersTasks [workordertaskid=");
		builder.append(workordertaskid);
		builder.append(", job_subtypetask_id=");
		builder.append(job_subtypetask_id);
		builder.append(", jobTypeRemark=");
		builder.append(jobTypeRemark);
		builder.append(", mark_complete=");
		builder.append(mark_complete);
		builder.append(", totalpart_cost=");
		builder.append(totalpart_cost);
		builder.append(", totallaber_cost=");
		builder.append(totallaber_cost);
		builder.append(", totaltask_cost=");
		builder.append(totaltask_cost);
		builder.append(", vehicle_vid=");
		builder.append(vehicle_vid);
		builder.append(", workorders=");
		builder.append(workorders);
		builder.append(", last_occurred_date=");
		builder.append(last_occurred_date);
		builder.append(", last_occurred_woId=");
		builder.append(last_occurred_woId);
		builder.append(", last_occurred_odameter=");
		builder.append(last_occurred_odameter); 
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", service_id=");
		builder.append(service_id);
		builder.append(", categoryId=");
		builder.append(categoryId);
		builder.append(", reasonRepairTypeId=");
		builder.append(reasonRepairTypeId);
		builder.append("]");
		return builder.toString();
	}

}
