package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class VehicleClothMaxAllowedSettingDto {
	
	private Long maxAllowedSettingId;

	private Integer	vid;

	private Long clothTypesId;

	private Double maxAllowedQuantity;

	private String	remark;

	private Integer companyId;

	private Long	createdById;

	private Long	lastModifiedById;

	private Date	creationDate;

	private Date	lastUpdatedDate;

	private boolean	markForDelete;
	
	private String  vehicle_registration;
	
	private String  clothTypeName;
	
	

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public String getClothTypeName() {
		return clothTypeName;
	}

	public void setClothTypeName(String clothTypeName) {
		this.clothTypeName = clothTypeName;
	}

	public Long getMaxAllowedSettingId() {
		return maxAllowedSettingId;
	}

	public void setMaxAllowedSettingId(Long maxAllowedSettingId) {
		this.maxAllowedSettingId = maxAllowedSettingId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public Double getMaxAllowedQuantity() {
		return maxAllowedQuantity;
	}

	public void setMaxAllowedQuantity(Double maxAllowedQuantity) {
		this.maxAllowedQuantity = Utility.round(maxAllowedQuantity, 2);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		return "VehicleClothMaxAllowedSettingDto [maxAllowedSettingId=" + maxAllowedSettingId + ", vid=" + vid
				+ ", clothTypesId=" + clothTypesId + ", maxAllowedQuantity=" + maxAllowedQuantity + ", remark=" + remark
				+ ", companyId=" + companyId + ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById
				+ ", creationDate=" + creationDate + ", lastUpdatedDate=" + lastUpdatedDate + ", markForDelete="
				+ markForDelete + ", vehicle_registration=" + vehicle_registration + ", clothTypeName=" + clothTypeName
				+ "]";
	}

	
}