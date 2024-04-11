package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Requisition")
public class Requisition implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "requisitionId")
	private Long requisitionId;
	
	@Column(name = "requisitionNum")
	private Long requisitionNum;
	
	@Column(name = "requisitionStatusId")
	private short requisitionStatusId;
	
	@Column(name = "createdById")
	private Long createdById;
	
	@Column(name = "location")
	private Long location;

	@Column(name = "assignTo")
	private Long assignTo;
	
	@Column(name = "refNumber", length = 50)
	private String refNumber;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;
	
	@Column(name = "requireOn")
	private Date requireOn;

	@Column(name = "creationOn")
	private Date creationOn;

	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public Long getRequisitionId() {
		return requisitionId;
	}

	public void setRequisitionId(Long requisitionId) {
		this.requisitionId = requisitionId;
	}

	public Long getRequisitionNum() {
		return requisitionNum;
	}

	public void setRequisitionNum(Long requisitionNum) {
		this.requisitionNum = requisitionNum;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLocation() {
		return location;
	}

	public void setLocation(Long location) {
		this.location = location;
	}

	public Long getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(Long assignTo) {
		this.assignTo = assignTo;
	}

	public String getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Date getCreationOn() {
		return creationOn;
	}

	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
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

	public short getRequisitionStatus() {
		return requisitionStatusId;
	}

	public void setRequisitionStatus(short requisitionStatus) {
		this.requisitionStatusId = requisitionStatus;
	}

	public Date getRequireOn() {
		return requireOn;
	}

	public void setRequireOn(Date requireOn) {
		this.requireOn = requireOn;
	}

	@Override
	public String toString() {
		return "Requisition [requisitionId=" + requisitionId + ", requisitionNum=" + requisitionNum + ", createdById="
				+ createdById + ", location=" + location + ", assignTo=" + assignTo + ", refNumber=" + refNumber
				+ ", remark=" + remark + ", lastModifiedById=" + lastModifiedById + ", creationOn=" + creationOn
				+ ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ "]";
	}
	
	

}
