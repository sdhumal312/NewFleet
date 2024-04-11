package org.fleetopgroup.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ReapairToTyreDetails {


	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reapairToTyreDetailsId")
	private Long reapairToTyreDetailsId;
	
	@Column(name = "repairToDetailsId")
	private Long repairToDetailsId;
	
	@Column(name = "tyreId")
	private Long tyreId;
	
	@Column(name = "statusId")
	private short statusId;

	@Column(name = "repairQuantity")
	private Long repairQuantity;
	
	@Column(name = "assignToId")
	private Long assignToId;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "createdById", nullable = false)
	private Long createdById;

	@Column(name = "lastModifiedById", nullable = false)
	private Long lastModifiedById;

	@Column(name = "creationOn")
	private Date creationOn;

	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public Long getReapairToTyreDetailsId() {
		return reapairToTyreDetailsId;
	}

	public void setReapairToTyreDetailsId(Long reapairToTyreDetailsId) {
		this.reapairToTyreDetailsId = reapairToTyreDetailsId;
	}

	public Long getRepairToDetailsId() {
		return repairToDetailsId;
	}

	public void setRepairToDetailsId(Long repairToDetailsId) {
		this.repairToDetailsId = repairToDetailsId;
	}

	public Long getTyreId() {
		return tyreId;
	}

	public void setTyreId(Long tyreId) {
		this.tyreId = tyreId;
	}

	public short getStatusId() {
		return statusId;
	}

	public void setStatusId(short statusId) {
		this.statusId = statusId;
	}

	public Long getRepairQuantity() {
		return repairQuantity;
	}

	public void setRepairQuantity(Long repairQuantity) {
		this.repairQuantity = repairQuantity;
	}

	public Long getAssignToId() {
		return assignToId;
	}

	public void setAssignToId(Long assignToId) {
		this.assignToId = assignToId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
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

	
	
	



}
