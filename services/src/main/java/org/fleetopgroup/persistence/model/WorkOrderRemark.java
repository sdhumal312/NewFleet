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
@Table(name = "WorkOrderRemark")
public class WorkOrderRemark implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "workOrderRemarkId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	workOrderRemarkId;
	
	@Column(name = "remark", length = 1000)
	private String	remark;
	
	@Column(name = "workOrderId")
	private Long	workOrderId;
	
	@Column(name = "remarkTypeId")
	private short	remarkTypeId;
	
	@Column(name = "createdOn")
	private Date	createdOn;
	
	@Column(name = "createdById")
	private Long	createdById;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "driverId")
	private Integer driverId;
	
	@Column(name = "assignee")
	private Long	assignee;
	
	@Column(name = "markForDelete")
	private Boolean	markForDelete;
	
	@Column(name = "issueId")
	private Long	issueId;

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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Boolean getMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(Boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public short getRemarkTypeId() {
		return remarkTypeId;
	}

	public void setRemarkTypeId(short remarkTypeId) {
		this.remarkTypeId = remarkTypeId;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public Long getAssignee() {
		return assignee;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public void setAssignee(Long assignee) {
		this.assignee = assignee;
	}
	
	
}
