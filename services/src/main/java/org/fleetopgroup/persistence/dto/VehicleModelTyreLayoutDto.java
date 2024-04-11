package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class VehicleModelTyreLayoutDto {

	private Long vehicleModelTyreLayoutId;
	
	private Long vehicleModelId;
	
	private String vehicleModel;

	private Integer numberOfAxle;
	
	private Integer frontTyreModelId;
	
	private String frontTyreModel;
	
	private Integer rearTyreModelId;
	
	private String rearTyreModel;
	
	private boolean isSpareTyrePresent;
	
	private String isSpareTyrePresentStr;
	
	private Integer spareTyreModelId;
	
	private String spareTyreModel;
	
	private Long createdById;
	
	private String createdBy;

	private Long lastModifiedById;
	
	private String lastModifiedBy;

	private Date creationOn;
	
	private String creationDate;

	private Date lastUpdatedOn;
	
	private String lastUpdatedDate;
	
	private Integer companyId;
	
	private boolean markForDelete;
	
	private Integer frontTyreSizeId;
	
	private Integer rearTyreSizeId;
	
	private Integer spareTyreSizeId;
	
	private String frontTyreSize;
	
	private String rearTyreSize;
	
	private String spareTyreSize;
	
	private String dualTyrePresentAxle;
	
	
	public VehicleModelTyreLayoutDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getVehicleModelTyreLayoutId() {
		return vehicleModelTyreLayoutId;
	}

	public void setVehicleModelTyreLayoutId(Long vehicleModelTyreLayoutId) {
		this.vehicleModelTyreLayoutId = vehicleModelTyreLayoutId;
	}

	public Long getVehicleModelId() {
		return vehicleModelId;
	}

	public void setVehicleModelId(Long vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public Integer getNumberOfAxle() {
		return numberOfAxle;
	}

	public void setNumberOfAxle(Integer numberOfAxle) {
		this.numberOfAxle = numberOfAxle;
	}

	public Integer getFrontTyreModelId() {
		return frontTyreModelId;
	}

	public void setFrontTyreModelId(Integer frontTyreModelId) {
		this.frontTyreModelId = frontTyreModelId;
	}

	public String getFrontTyreModel() {
		return frontTyreModel;
	}

	public void setFrontTyreModel(String frontTyreModel) {
		this.frontTyreModel = frontTyreModel;
	}

	public Integer getRearTyreModelId() {
		return rearTyreModelId;
	}

	public void setRearTyreModelId(Integer rearTyreModelId) {
		this.rearTyreModelId = rearTyreModelId;
	}

	public String getRearTyreModel() {
		return rearTyreModel;
	}

	public void setRearTyreModel(String rearTyreModel) {
		this.rearTyreModel = rearTyreModel;
	}

	public boolean isSpareTyrePresent() {
		return isSpareTyrePresent;
	}

	public void setSpareTyrePresent(boolean isSpareTyrePresent) {
		this.isSpareTyrePresent = isSpareTyrePresent;
	}

	public String getIsSpareTyrePresentStr() {
		return isSpareTyrePresentStr;
	}

	public void setIsSpareTyrePresentStr(String isSpareTyrePresentStr) {
		this.isSpareTyrePresentStr = isSpareTyrePresentStr;
	}

	public Integer getSpareTyreModelId() {
		return spareTyreModelId;
	}

	public void setSpareTyreModelId(Integer spareTyreModelId) {
		this.spareTyreModelId = spareTyreModelId;
	}

	public String getSpareTyreModel() {
		return spareTyreModel;
	}

	public void setSpareTyreModel(String spareTyreModel) {
		this.spareTyreModel = spareTyreModel;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getCreationOn() {
		return creationOn;
	}

	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
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
	
	public Integer getFrontTyreSizeId() {
		return frontTyreSizeId;
	}

	public void setFrontTyreSizeId(Integer frontTyreSizeId) {
		this.frontTyreSizeId = frontTyreSizeId;
	}

	public Integer getRearTyreSizeId() {
		return rearTyreSizeId;
	}

	public void setRearTyreSizeId(Integer rearTyreSizeId) {
		this.rearTyreSizeId = rearTyreSizeId;
	}

	public Integer getSpareTyreSizeId() {
		return spareTyreSizeId;
	}

	public void setSpareTyreSizeId(Integer spareTyreSizeId) {
		this.spareTyreSizeId = spareTyreSizeId;
	}

	public String getFrontTyreSize() {
		return frontTyreSize;
	}

	public void setFrontTyreSize(String frontTyreSize) {
		this.frontTyreSize = frontTyreSize;
	}

	public String getRearTyreSize() {
		return rearTyreSize;
	}

	public void setRearTyreSize(String rearTyreSize) {
		this.rearTyreSize = rearTyreSize;
	}

	public String getSpareTyreSize() {
		return spareTyreSize;
	}

	public void setSpareTyreSize(String spareTyreSize) {
		this.spareTyreSize = spareTyreSize;
	}

	public String getDualTyrePresentAxle() {
		return dualTyrePresentAxle;
	}

	public void setDualTyrePresentAxle(String dualTyrePresentAxle) {
		this.dualTyrePresentAxle = dualTyrePresentAxle;
	}

	@Override
	public String toString() {
		return "VehicleModelTyreLayoutDto [vehicleModelTyreLayoutId=" + vehicleModelTyreLayoutId + ", vehicleModelId="
				+ vehicleModelId + ", vehicleModel=" + vehicleModel + ", numberOfAxle=" + numberOfAxle
				+ ", frontTyreModelId=" + frontTyreModelId + ", frontTyreModel=" + frontTyreModel + ", rearTyreModelId="
				+ rearTyreModelId + ", rearTyreModel=" + rearTyreModel + ", isSpareTyrePresent=" + isSpareTyrePresent
				+ ", isSpareTyrePresentStr=" + isSpareTyrePresentStr + ", spareTyreModelId=" + spareTyreModelId
				+ ", spareTyreModel=" + spareTyreModel + ", createdById=" + createdById + ", createdBy=" + createdBy
				+ ", lastModifiedById=" + lastModifiedById + ", lastModifiedBy=" + lastModifiedBy + ", creationOn="
				+ creationOn + ", creationDate=" + creationDate + ", lastUpdatedOn=" + lastUpdatedOn
				+ ", lastUpdatedDate=" + lastUpdatedDate + ", companyId=" + companyId + ", markForDelete="
				+ markForDelete + ", frontTyreSizeId=" + frontTyreSizeId + ", rearTyreSizeId=" + rearTyreSizeId
				+ ", spareTyreSizeId=" + spareTyreSizeId + ", frontTyreSize=" + frontTyreSize + ", rearTyreSize="
				+ rearTyreSize + ", spareTyreSize=" + spareTyreSize + ", dualTyrePresentAxle=" + dualTyrePresentAxle
				+ "]";
	}

	
	
	
	
}
