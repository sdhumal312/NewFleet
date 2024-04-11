package org.fleetopgroup.persistence.dto;

/*
 * @author Manish Singh
 * @since  08-07-2021
 */

public class WorkOrderRemarkDto {
	
	private Long	workOrderRemarkId;
	
	private String	remark;
	
	private Long	workOrderId;
	
	private short	remarkTypeId;
	
	private String	createdOn;
	
	private String	createdBy;
	
	private Integer	companyId;
	
	private String	remarkType;
	
	private Integer driverId;
	
	private Long	assigneeId;
	
	private Long	issueNumber;

	private String	driverName;
	
	private String	assigneeName;

	public Long getWorkOrderRemarkId() {
		return workOrderRemarkId;
	}

	public void setWorkOrderRemarkId(Long workOrderRemarkId) {
		this.workOrderRemarkId = workOrderRemarkId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}

	public short getRemarkTypeId() {
		return remarkTypeId;
	}

	public void setRemarkTypeId(short remarkTypeId) {
		this.remarkTypeId = remarkTypeId;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getRemarkType() {
		return remarkType;
	}

	public void setRemarkType(String remarkType) {
		this.remarkType = remarkType;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public Long getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Long assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public Long getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(Long issueNumber) {
		this.issueNumber = issueNumber;
	}
	
	
}
