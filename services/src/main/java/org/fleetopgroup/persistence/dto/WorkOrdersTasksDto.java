package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.persistence.model.WorkOrders;
import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 *
 *
 */

public class WorkOrdersTasksDto {

	private Long workordertaskid;
	private String job_typetask;
	private Integer job_subtypetask_id;
	private String job_subtypetask;
	private String jobTypeRemark;
	private String mechanic;
	private Integer mark_complete;
	private Double totalpart_cost;
	private Double totallaber_cost;
	private Double totaltask_cost;
	private Long workorders_id;
	private Integer vehicle_vid;
	private String last_occurred_date;
	private Date last_occurred_dateOn;
	private int partCount;
	private Integer companyId;
	private String vehicle_registration;

	/** The value for the last_occurred_woId back end process */
	private Long last_occurred_woId;

	/** The value for the last_occurred_odameter back end process */
	private Integer last_occurred_odameter;
	
	private Integer job_typetaskId;
	
	private WorkOrders workorders;
	
	private long service_id;
	
	private String service_Number;
	
	private String service_NumberStr;
	
	private Long	partId;
	
	private Integer issueIds;
	
	private String issueIdsEncry;
	
	private String issueNumber;
	
	private String issueSummary;
	
	private String pcName;

	public WorkOrdersTasksDto() {

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
	 * @return the job_subtypetask
	 */
	public String getJob_subtypetask() {
		return job_subtypetask;
	}

	/**
	 * @param job_subtypetask
	 *            the job_subtypetask to set
	 */
	public void setJob_subtypetask(String job_subtypetask) {
		this.job_subtypetask = job_subtypetask;
	}

	public String getJobTypeRemark() {
		return jobTypeRemark;
	}

	public void setJobTypeRemark(String jobTypeRemark) {
		this.jobTypeRemark = jobTypeRemark;
	}

	public String getMechanic() {
		return mechanic;
	}

	public void setMechanic(String mechanic) {
		this.mechanic = mechanic;
	}

	public int getPartCount() {
		return partCount;
	}

	public void setPartCount(int partCount) {
		this.partCount = partCount;
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
		this.totalpart_cost = Utility.round(totalpart_cost, 2);
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
		this.totallaber_cost = Utility.round(totallaber_cost, 2);
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
		this.totaltask_cost = Utility.round(totaltask_cost, 2);
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

	/**
	 * @return the job_typetask
	 */
	public String getJob_typetask() {
		return job_typetask;
	}

	/**
	 * @param job_typetask
	 *            the job_typetask to set
	 */
	public void setJob_typetask(String job_typetask) {
		this.job_typetask = job_typetask;
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
	 * @return the last_occurred_date
	 */
	public String getLast_occurred_date() {
		return last_occurred_date;
	}

	/**
	 * @param last_occurred_date
	 *            the last_occurred_date to set
	 */
	public void setLast_occurred_date(String last_occurred_date) {
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

	/**
	 * @return the job_subtypetask_id
	 */
	public Integer getJob_subtypetask_id() {
		return job_subtypetask_id;
	}

	/**
	 * @param job_subtypetask_id
	 *            the job_subtypetask_id to set
	 */
	public void setJob_subtypetask_id(Integer job_subtypetask_id) {
		this.job_subtypetask_id = job_subtypetask_id;
	}

	/**
	 * @return the last_occurred_dateOn
	 */
	public Date getLast_occurred_dateOn() {
		return last_occurred_dateOn;
	}

	/**
	 * @param last_occurred_dateOn the last_occurred_dateOn to set
	 */
	public void setLast_occurred_dateOn(Date last_occurred_dateOn) {
		this.last_occurred_dateOn = last_occurred_dateOn;
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
	 * @return the workorders
	 */
	public WorkOrders getWorkorders() {
		return workorders;
	}

	/**
	 * @param workorders the workorders to set
	 */
	public void setWorkorders(WorkOrders workorders) {
		this.workorders = workorders;
	}

	public long getService_id() {
		return service_id;
	}

	public void setService_id(long service_id) {
		this.service_id = service_id;
	}
	

	public String getService_Number() {
		return service_Number;
	}

	public void setService_Number(String service_Number) {
		this.service_Number = service_Number;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public String getService_NumberStr() {
		return service_NumberStr;
	}

	public void setService_NumberStr(String service_NumberStr) {
		this.service_NumberStr = service_NumberStr;
	}

	public Integer getIssueIds() {
		return issueIds;
	}

	public void setIssueIds(Integer issueIds) {
		this.issueIds = issueIds;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getIssueIdsEncry() {
		return issueIdsEncry;
	}

	public void setIssueIdsEncry(String issueIdsEncry) {
		this.issueIdsEncry = issueIdsEncry;
	}

	public String getIssueSummary() {
		return issueSummary;
	}

	public void setIssueSummary(String issueSummary) {
		this.issueSummary = issueSummary;
	}

	
	public String getPcName() {
		return pcName;
	}

	public void setPcName(String pcName) {
		this.pcName = pcName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkOrdersTasksDto [workordertaskid=");
		builder.append(workordertaskid);
		builder.append(", job_typetask=");
		builder.append(job_typetask);
		builder.append(", job_subtypetask_id=");
		builder.append(job_subtypetask_id);
		builder.append(", job_subtypetask=");
		builder.append(job_subtypetask);
		builder.append(", mark_complete=");
		builder.append(mark_complete);
		builder.append(", totalpart_cost=");
		builder.append(totalpart_cost);
		builder.append(", totallaber_cost=");
		builder.append(totallaber_cost);
		builder.append(", totaltask_cost=");
		builder.append(totaltask_cost);
		builder.append(", workorders_id=");
		builder.append(workorders_id);
		builder.append(", vehicle_vid=");
		builder.append(vehicle_vid);
		builder.append(", last_occurred_date=");
		builder.append(last_occurred_date);
		builder.append(", last_occurred_woId=");
		builder.append(last_occurred_woId);  
		builder.append(", last_occurred_odameter=");
		builder.append(last_occurred_odameter); 
		builder.append(", service_id=");
		builder.append(service_id);
		builder.append(", service_Number=");
		builder.append(service_Number);
		builder.append("]");
		return builder.toString();
	}

	

}
