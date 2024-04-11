package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TransferedInventoryDetails")
public class TransferedInventoryDetails implements Serializable {
	
	private static final long serialVersionUID =1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="transferedInventoryDetailsId")
	private Long  transferedInventoryDetailsId;
	
	@Column(name="transferRequisitionId")
	private long transferRequisitionId;
	
	@Column(name="subRequisitionId")
	private Long  subRequisitionId;
	
	@Column(name="approvalRequisitionId")
	private Long  approvalRequisitionId;
	
	@Column(name="createdInventoryId")
	private Long createdInventoryId;

	@Column(name="companyId")
	private Integer companyId;
	
	@Column(name="createdById")
	private Long createdById;
	
	@Column(name="markForDelete")
	private boolean markForDelete;

	public Long getTransferedInventoryDetailsId() {
		return transferedInventoryDetailsId;
	}

	public void setTransferedInventoryDetailsId(Long transferedInventoryDetailsId) {
		this.transferedInventoryDetailsId = transferedInventoryDetailsId;
	}

	public long getTransferRequisitionId() {
		return transferRequisitionId;
	}

	public void setTransferRequisitionId(long transferRequisitionId) {
		this.transferRequisitionId = transferRequisitionId;
	}

	public Long getSubRequisitionId() {
		return subRequisitionId;
	}

	public Long getCreatedInventoryId() {
		return createdInventoryId;
	}

	public void setCreatedInventoryId(Long createdInventoryId) {
		this.createdInventoryId = createdInventoryId;
	}

	public void setSubRequisitionId(Long subRequisitionId) {
		this.subRequisitionId = subRequisitionId;
	}

	public Long getApprovalRequisitionId() {
		return approvalRequisitionId;
	}

	public void setApprovalRequisitionId(Long approvalRequisitionId) {
		this.approvalRequisitionId = approvalRequisitionId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
 
}
