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
@Table(name="ApprovedRequisitionDetails")
public class ApprovedRequisitionDetails implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "approvedRequisitionId")
	private Long approvedRequisitionId;
	
	@Column(name = "subRequisitionId")
	private Long subRequisitionId;

	@Column(name = "approvedTypeId")
	private Short approvedTypeId;
	
	@Column(name = "approvedStatus")
	private Short approvedStatus;
	
	@Column(name = "createdById")
	private Long createdById;
	
	@Column(name = "branchId")
	private Long branchId;
	
	@Column(name = "poId")
	private Long poId;
	
	@Column(name = "approvedQuantity")
	private Integer approvedQuantity;
	
	@Column(name = "transferedQuantity")
	private Integer transferedQuantity;
	
	@Column(name = "receivedQuantity")
	private Integer receivedQuantity;
	
	@Column(name = "returnedQuantity")
	private Integer returnedQuantity;
	
	@Column(name = "assignedTo")
	private Long assignedTo;
	
	@Column(name = "receiverId")
	private Long receiverId;

	@Column(name = "remark")
	private String remark;
	
	@Column(name = "rejectRemark")
	private String rejectRemark;
	
	//for receival rejected remark or receival remark
	@Column(name = "finalRemark")
	private String finalRemark;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "creationOn")
	private Date creationOn;

	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	@Column(name = "ureaManufacturerId")
	private Long ureaManufacturerId;
	
	public Long getApprovedRequisitionId() {
		return approvedRequisitionId;
	}

	public void setApprovedRequisitionId(Long approvedRequisitionId) {
		this.approvedRequisitionId = approvedRequisitionId;
	}

	public Long getSubRequisitionId() {
		return subRequisitionId;
	}

	public void setSubRequisitionId(Long subRequisitionId) {
		this.subRequisitionId = subRequisitionId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Integer getApprovedQuantity() {
		return approvedQuantity;
	}

	public void setApprovedQuantity(Integer approvedQuantity) {
		this.approvedQuantity = approvedQuantity;
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

	public Long getPoId() {
		return poId;
	}

	public void setPoId(Long poId) {
		this.poId = poId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Long getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Long assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}



	public Integer getTransferedQuantity() {
		return transferedQuantity;
	}

	public void setTransferedQuantity(Integer transferedQuantity) {
		this.transferedQuantity = transferedQuantity;
	}

	public Short getApprovedTypeId() {
		return approvedTypeId;
	}

	public void setApprovedTypeId(Short approvedTypeId) {
		this.approvedTypeId = approvedTypeId;
	}

	public Short getApprovedStatus() {
		return approvedStatus;
	}

	public void setApprovedStatus(Short approvedStatus) {
		this.approvedStatus = approvedStatus;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public Integer getReceivedQuantity() {
		return receivedQuantity;
	}

	public void setReceivedQuantity(Integer receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}

	public String getFinalRemark() {
		return finalRemark;
	}

	public void setFinalRemark(String finalRemark) {
		this.finalRemark = finalRemark;
	}

	public String getRejectRemark() {
		return rejectRemark;
	}

	public void setRejectRemark(String rejectRemark) {
		this.rejectRemark = rejectRemark;
	}

	public Integer getReturnedQuantity() {
		return returnedQuantity;
	}

	public void setReturnedQuantity(Integer returnedQuantity) {
		this.returnedQuantity = returnedQuantity;
	}

	public Long getUreaManufacturerId() {
		return ureaManufacturerId;
	}

	public void setUreaManufacturerId(Long ureaManufacturerId) {
		this.ureaManufacturerId = ureaManufacturerId;
	}

	@Override
	public String toString() {
		return "ApprovedRequisitionDetails [approvedRequisitionId=" + approvedRequisitionId + ", subRequisitionId="
				+ subRequisitionId + ", approvedTypeId=" + approvedTypeId + ", approvedStatus=" + approvedStatus
				+ ", createdById=" + createdById + ", branchId=" + branchId + ", poId=" + poId + ", approvedQuantity="
				+ approvedQuantity + ", transferedQuantity=" + transferedQuantity + ", assignedTo=" + assignedTo
				+ ", remark=" + remark + ", lastModifiedById=" + lastModifiedById + ", creationOn=" + creationOn
				+ ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ "]";
	}
}
