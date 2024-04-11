package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class VehicleModelTyreLayoutPositionDto {

	private Long vehicleModelTyreLayoutPositionId;
	
	private Long vehicleModelTyreLayoutId;

	private Integer axle;
	
	private String position;
	
	private Integer tyreModelId;
	
	private String tyreModel;
	
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
	
	private short tyreModelTypeId;
	
	private String tyreModelType;
	
	private Integer tyreGauge;
	
	private Integer gauageMeasurementLine;
	
	private short tyreTubeTypeId;
	
	private String tyreTubeType;
	
	private Integer ply;
	
	private Integer psi;
	
	private Integer tyreModelSizeId;
	
	private String tyreModelSize;

	public VehicleModelTyreLayoutPositionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getVehicleModelTyreLayoutPositionId() {
		return vehicleModelTyreLayoutPositionId;
	}

	public void setVehicleModelTyreLayoutPositionId(Long vehicleModelTyreLayoutPositionId) {
		this.vehicleModelTyreLayoutPositionId = vehicleModelTyreLayoutPositionId;
	}

	public Long getVehicleModelTyreLayoutId() {
		return vehicleModelTyreLayoutId;
	}

	public void setVehicleModelTyreLayoutId(Long vehicleModelTyreLayoutId) {
		this.vehicleModelTyreLayoutId = vehicleModelTyreLayoutId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getTyreModelId() {
		return tyreModelId;
	}

	public void setTyreModelId(Integer tyreModelId) {
		this.tyreModelId = tyreModelId;
	}

	public String getTyreModel() {
		return tyreModel;
	}

	public void setTyreModel(String tyreModel) {
		this.tyreModel = tyreModel;
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
	
	public Integer getAxle() {
		return axle;
	}

	public void setAxle(Integer axle) {
		this.axle = axle;
	}

	
	public short getTyreModelTypeId() {
		return tyreModelTypeId;
	}

	public void setTyreModelTypeId(short tyreModelTypeId) {
		this.tyreModelTypeId = tyreModelTypeId;
	}

	public String getTyreModelType() {
		return tyreModelType;
	}

	public void setTyreModelType(String tyreModelType) {
		this.tyreModelType = tyreModelType;
	}

	public Integer getTyreGauge() {
		return tyreGauge;
	}

	public void setTyreGauge(Integer tyreGauge) {
		this.tyreGauge = tyreGauge;
	}

	public Integer getGauageMeasurementLine() {
		return gauageMeasurementLine;
	}

	public void setGauageMeasurementLine(Integer gauageMeasurementLine) {
		this.gauageMeasurementLine = gauageMeasurementLine;
	}

	public short getTyreTubeTypeId() {
		return tyreTubeTypeId;
	}

	public void setTyreTubeTypeId(short tyreTubeTypeId) {
		this.tyreTubeTypeId = tyreTubeTypeId;
	}

	public String getTyreTubeType() {
		return tyreTubeType;
	}

	public void setTyreTubeType(String tyreTubeType) {
		this.tyreTubeType = tyreTubeType;
	}

	public Integer getPly() {
		return ply;
	}

	public void setPly(Integer ply) {
		this.ply = ply;
	}

	public Integer getPsi() {
		return psi;
	}

	public void setPsi(Integer psi) {
		this.psi = psi;
	}

	public Integer getTyreModelSizeId() {
		return tyreModelSizeId;
	}

	public void setTyreModelSizeId(Integer tyreModelSizeId) {
		this.tyreModelSizeId = tyreModelSizeId;
	}

	public String getTyreModelSize() {
		return tyreModelSize;
	}

	public void setTyreModelSize(String tyreModelSize) {
		this.tyreModelSize = tyreModelSize;
	}

	@Override
	public String toString() {
		return "VehicleModelTyreLayoutPositionDto [vehicleModelTyreLayoutPositionId=" + vehicleModelTyreLayoutPositionId
				+ ", vehicleModelTyreLayoutId=" + vehicleModelTyreLayoutId + ", axle=" + axle + ", position=" + position
				+ ", tyreModelId=" + tyreModelId + ", tyreModel=" + tyreModel + ", createdById=" + createdById
				+ ", createdBy=" + createdBy + ", lastModifiedById=" + lastModifiedById + ", lastModifiedBy="
				+ lastModifiedBy + ", creationOn=" + creationOn + ", creationDate=" + creationDate + ", lastUpdatedOn="
				+ lastUpdatedOn + ", lastUpdatedDate=" + lastUpdatedDate + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + ", tyreModelTypeId=" + tyreModelTypeId + ", tyreModelType="
				+ tyreModelType + ", tyreGauge=" + tyreGauge + ", gauageMeasurementLine=" + gauageMeasurementLine
				+ ", tyreTubeTypeId=" + tyreTubeTypeId + ", tyreTubeType=" + tyreTubeType + ", ply=" + ply + ", psi="
				+ psi + ", tyreModelSizeId=" + tyreModelSizeId + ", tyreModelSize=" + tyreModelSize + "]";
	}

	

}
