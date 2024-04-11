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
@Table(name = "SubRequisition")
public class SubRequisition implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subRequisitionId")
	private Long subRequisitionId;
	
	@Column(name = "requisitionId")
	private Long requisitionId;

	@Column(name = "requisitionTypeId")
	private short requisitionTypeId;
	
	@Column(name = "subRequisitionStatusId")
	private short subRequisitionStatusId;
	
	@Column(name = "createdById")
	private Long createdById;
	
	@Column(name = "transactionId")
	private Long transactionId;
	
	@Column(name = "tyreManufacturer")
	private Long tyreManufacturer;

	@Column(name = "tyreModel")
	private Long tyreModel;
	
	@Column(name = "tyreSize")
	private Long tyreSize;
	
	@Column(name = "batteryManufacturer")
	private Long batteryManufacturer;

	@Column(name = "batteryModel")
	private Long batteryModel;
	
	@Column(name = "batteryCapacity")
	private Long batteryCapacity;
	
	@Column(name = "quantity")
	private Double quantity;
	
	@Column(name = "approvalQuantity")
	private Double approvalQuantity;
	
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "creationOn")
	private Date creationOn;

	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public Long getSubRequisitionId() {
		return subRequisitionId;
	}

	public void setSubRequisitionId(Long subRequisitionId) {
		this.subRequisitionId = subRequisitionId;
	}

	public Long getRequisitionId() {
		return requisitionId;
	}

	public void setRequisitionId(Long requisitionId) {
		this.requisitionId = requisitionId;
	}

	public short getRequisitionTypeId() {
		return requisitionTypeId;
	}

	public void setRequisitionTypeId(short requisitionTypeId) {
		this.requisitionTypeId = requisitionTypeId;
	}

	public short getSubRequisitionStatusId() {
		return subRequisitionStatusId;
	}

	public void setSubRequisitionStatusId(short subRequisitionStatusId) {
		this.subRequisitionStatusId = subRequisitionStatusId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Long getTyreManufacturer() {
		return tyreManufacturer;
	}

	public void setTyreManufacturer(Long tyreManufacturer) {
		this.tyreManufacturer = tyreManufacturer;
	}

	public Long getTyreModel() {
		return tyreModel;
	}

	public void setTyreModel(Long tyreModel) {
		this.tyreModel = tyreModel;
	}

	public Long getTyreSize() {
		return tyreSize;
	}

	public void setTyreSize(Long tyreSize) {
		this.tyreSize = tyreSize;
	}

	public Long getBatteryManufacturer() {
		return batteryManufacturer;
	}

	public void setBatteryManufacturer(Long batteryManufacturer) {
		this.batteryManufacturer = batteryManufacturer;
	}

	public Long getBatteryModel() {
		return batteryModel;
	}

	public void setBatteryModel(Long batteryModel) {
		this.batteryModel = batteryModel;
	}

	public Long getBatteryCapacity() {
		return batteryCapacity;
	}

	public void setBatteryCapacity(Long batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getApprovalQuantity() {
		return approvalQuantity;
	}

	public void setApprovalQuantity(Double approvalQuantity) {
		this.approvalQuantity = approvalQuantity;
	}

	@Override
	public String toString() {
		return "SubRequisition [subRequisitionId=" + subRequisitionId + ", requisitionId=" + requisitionId
				+ ", requisitionTypeId=" + requisitionTypeId + ", subRequisitionStatusId=" + subRequisitionStatusId
				+ ", createdById=" + createdById + ", transactionId=" + transactionId + ", tyreManufacturer="
				+ tyreManufacturer + ", tyreModel=" + tyreModel + ", tyreSize=" + tyreSize + ", batteryManufacturer="
				+ batteryManufacturer + ", batteryModel=" + batteryModel + ", batteryCapacity=" + batteryCapacity
				+ ", quantity=" + quantity + ", approvalQuantity=" + approvalQuantity + ", lastModifiedById="
				+ lastModifiedById + ", creationOn=" + creationOn + ", lastUpdatedOn=" + lastUpdatedOn + ", companyId="
				+ companyId + ", remark=" + remark + ", markForDelete=" + markForDelete + "]";
	}




}
