package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;


public class SubRequisitionDto {
	
	private Long subRequisitionId;
	
	private Long requisitionId;

	private short requisitionType;
	
	private String requisitionTypeName;
	
	private short subRequisitionStatus;
	
	private String subRequisitionStatusName;
	
	private Long createdById;
	
	private Long partId;
	
	private String pmuName;
	
	private String partName;
	
	private Long clothId;
	
	private String clothName;
	
	private Long ureaId;
	
	private String ureaName;
	
	private Long tyreManufacturer;
	
	private String tyreManufacturerName;

	private Long tyreModel;
	
	private String tyreModelName;
	
	private Long tyreSize;
	
	private String tyreSizeName;
	
	private Long batteryManufacturer;
	
	private String batteryManufacturerName;

	private Long batteryModel;
	
	private String batteryModelName;
	
	private Long batteryCapacity;
	
	private String batteryCapacityName;
	
	private Double quantity;
	
	private Double approvedQuantity;
	
	private Long lastModifiedById;

	private Date creationOn;

	private Date lastUpdatedOn;
	
	private Integer companyId;
	
	private String userName;
	
	private String remark;
	
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

	public short getRequisitionType() {
		return requisitionType;
	}

	public void setRequisitionType(short requisitionType) {
		this.requisitionType = requisitionType;
	}

	public String getRequisitionTypeName() {
		return requisitionTypeName;
	}

	public void setRequisitionTypeName(String requisitionTypeName) {
		this.requisitionTypeName = requisitionTypeName;
	}

	public short getSubRequisitionStatus() {
		return subRequisitionStatus;
	}

	public void setSubRequisitionStatus(short subRequisitionStatus) {
		this.subRequisitionStatus = subRequisitionStatus;
	}

	public String getSubRequisitionStatusName() {
		return subRequisitionStatusName;
	}

	public void setSubRequisitionStatusName(String subRequisitionStatusName) {
		this.subRequisitionStatusName = subRequisitionStatusName;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Long getClothId() {
		return clothId;
	}

	public void setClothId(Long clothId) {
		this.clothId = clothId;
	}

	public String getClothName() {
		return clothName;
	}

	public void setClothName(String clothName) {
		this.clothName = clothName;
	}

	public Long getUreaId() {
		return ureaId;
	}

	public void setUreaId(Long ureaId) {
		this.ureaId = ureaId;
	}

	public String getUreaName() {
		return ureaName;
	}

	public void setUreaName(String ureaName) {
		this.ureaName = ureaName;
	}

	public Long getTyreManufacturer() {
		return tyreManufacturer;
	}

	public void setTyreManufacturer(Long tyreManufacturer) {
		this.tyreManufacturer = tyreManufacturer;
	}

	public String getTyreManufacturerName() {
		return tyreManufacturerName;
	}

	public void setTyreManufacturerName(String tyreManufacturerName) {
		this.tyreManufacturerName = tyreManufacturerName;
	}

	public Long getTyreModel() {
		return tyreModel;
	}

	public void setTyreModel(Long tyreModel) {
		this.tyreModel = tyreModel;
	}

	public String getTyreModelName() {
		return tyreModelName;
	}

	public void setTyreModelName(String tyreModelName) {
		this.tyreModelName = tyreModelName;
	}

	public Long getTyreSize() {
		return tyreSize;
	}

	public void setTyreSize(Long tyreSize) {
		this.tyreSize = tyreSize;
	}

	public String getTyreSizeName() {
		return tyreSizeName;
	}

	public void setTyreSizeName(String tyreSizeName) {
		this.tyreSizeName = tyreSizeName;
	}

	public Long getBatteryManufacturer() {
		return batteryManufacturer;
	}

	public void setBatteryManufacturer(Long batteryManufacturer) {
		this.batteryManufacturer = batteryManufacturer;
	}

	public String getBatteryManufacturerName() {
		return batteryManufacturerName;
	}

	public void setBatteryManufacturerName(String batteryManufacturerName) {
		this.batteryManufacturerName = batteryManufacturerName;
	}

	public Long getBatteryModel() {
		return batteryModel;
	}

	public void setBatteryModel(Long batteryModel) {
		this.batteryModel = batteryModel;
	}

	public String getBatteryModelName() {
		return batteryModelName;
	}

	public void setBatteryModelName(String batteryModelName) {
		this.batteryModelName = batteryModelName;
	}

	public Long getBatteryCapacity() {
		return batteryCapacity;
	}

	public void setBatteryCapacity(Long batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	public String getBatteryCapacityName() {
		return batteryCapacityName;
	}

	public void setBatteryCapacityName(String batteryCapacityName) {
		this.batteryCapacityName = batteryCapacityName;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = Utility.round(quantity, 2);
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Double getApprovedQuantity() {
		return approvedQuantity;
	}

	public void setApprovedQuantity(Double approvedQuantity) {
		this.approvedQuantity = Utility.round(approvedQuantity, 2);
	}

	public String getPmuName() {
		return pmuName;
	}

	public void setPmuName(String pmuName) {
		this.pmuName = pmuName;
	}

	@Override
	public String toString() {
		return "SubRequisitionDto [subRequisitionId=" + subRequisitionId + ", requisitionId=" + requisitionId
				+ ", requisitionType=" + requisitionType + ", requisitionTypeName=" + requisitionTypeName
				+ ", subRequisitionStatus=" + subRequisitionStatus + ", subRequisitionStatusName="
				+ subRequisitionStatusName + ", createdById=" + createdById + ", partId=" + partId + ", partName="
				+ partName + ", clothId=" + clothId + ", clothName=" + clothName + ", ureaId=" + ureaId + ", ureaName="
				+ ureaName + ", tyreManufacturer=" + tyreManufacturer + ", tyreManufacturerName=" + tyreManufacturerName
				+ ", tyreModel=" + tyreModel + ", tyreModelName=" + tyreModelName + ", tyreSize=" + tyreSize
				+ ", tyreSizeName=" + tyreSizeName + ", batteryManufacturer=" + batteryManufacturer
				+ ", batteryManufacturerName=" + batteryManufacturerName + ", batteryModel=" + batteryModel
				+ ", batteryModelName=" + batteryModelName + ", batteryCapacity=" + batteryCapacity
				+ ", batteryCapacityName=" + batteryCapacityName + ", quantity=" + quantity + ", lastModifiedById="
				+ lastModifiedById + ", creationOn=" + creationOn + ", lastUpdatedOn=" + lastUpdatedOn + ", companyId="
				+ companyId + ", userName=" + userName + ", remark=" + remark + ", markForDelete=" + markForDelete
				+ "]";
	}

}
