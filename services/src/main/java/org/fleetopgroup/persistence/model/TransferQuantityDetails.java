package org.fleetopgroup.persistence.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TransferQuantityDetails")
public class TransferQuantityDetails implements Serializable  {
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "partiallyReceivedId")
	private Long partiallyReceivedId;
	
	@Column(name = "approvedId")
	private Long approvedId;
	
	@Column(name = "createdById")
	private Long createdById;
	
	@Column(name = "transferedQuantity")
	private Integer transferedQuantity;
	
	@Column(name = "remark")
	private String remark;

	@Column(name = "creationOn", nullable = false, updatable = false)
	private Timestamp creationOn;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public Long getPartiallyReceivedId() {
		return partiallyReceivedId;
	}

	public void setPartiallyReceivedId(Long partiallyReceivedId) {
		this.partiallyReceivedId = partiallyReceivedId;
	}

	public Long getApprovedId() {
		return approvedId;
	}

	public void setApprovedId(Long approvedId) {
		this.approvedId = approvedId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Integer getTransferedQuantity() {
		return transferedQuantity;
	}

	public void setTransferedQuantity(Integer transferedQuantity) {
		this.transferedQuantity = transferedQuantity;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getCreationOn() {
		return creationOn;
	}

	public void setCreationOn(Timestamp creationOn) {
		this.creationOn = creationOn;
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


