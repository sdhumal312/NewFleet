package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class TyreUsageHistoryDto {
	private Long tyreUsageHistoryId;
	
	private Long tyreId;
	
	private String tyreNumber;
	
	private Integer tyreUsage;
	
	private Integer vid;
	
	private String vehicleRegistration;

	private Integer vehiclePreOdometer;

	private Integer vehicleOdometer;
	
	private Long createdById;

	private Long lastUpdatedById;

	private Date creationOn;

	private Date lastUpdatedOn;
	
	private String createdBy;
	
	private String lastUpdatedBy;
	
	private String creationDate;
	
	private String lastUpdatedDate;
	
	private Integer companyId;
	
	private boolean markForDelete;

	private short transactionUsageTypeId;
	
	private String transactionUsageType;
	
	private Long transactionId;

	private String transactionNumber;

	public TyreUsageHistoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getTyreUsageHistoryId() {
		return tyreUsageHistoryId;
	}

	public void setTyreUsageHistoryId(Long tyreUsageHistoryId) {
		this.tyreUsageHistoryId = tyreUsageHistoryId;
	}

	public Long getTyreId() {
		return tyreId;
	}

	public void setTyreId(Long tyreId) {
		this.tyreId = tyreId;
	}

	public String getTyreNumber() {
		return tyreNumber;
	}

	public void setTyreNumber(String tyreNumber) {
		this.tyreNumber = tyreNumber;
	}

	public Integer getTyreUsage() {
		return tyreUsage;
	}

	public void setTyreUsage(Integer tyreUsage) {
		this.tyreUsage = tyreUsage;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public String getVehicleRegistration() {
		return vehicleRegistration;
	}

	public void setVehicleRegistration(String vehicleRegistration) {
		this.vehicleRegistration = vehicleRegistration;
	}

	public Integer getVehiclePreOdometer() {
		return vehiclePreOdometer;
	}

	public void setVehiclePreOdometer(Integer vehiclePreOdometer) {
		this.vehiclePreOdometer = vehiclePreOdometer;
	}

	public Integer getVehicleOdometer() {
		return vehicleOdometer;
	}

	public void setVehicleOdometer(Integer vehicleOdometer) {
		this.vehicleOdometer = vehicleOdometer;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}

	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
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

	public short getTransactionUsageTypeId() {
		return transactionUsageTypeId;
	}

	public void setTransactionUsageTypeId(short transactionUsageTypeId) {
		this.transactionUsageTypeId = transactionUsageTypeId;
	}

	public String getTransactionUsageType() {
		return transactionUsageType;
	}

	public void setTransactionUsageType(String transactionUsageType) {
		this.transactionUsageType = transactionUsageType;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@Override
	public String toString() {
		return "TyreUsageHistoryDto [tyreUsageHistoryId=" + tyreUsageHistoryId + ", tyreId=" + tyreId + ", tyreNumber="
				+ tyreNumber + ", tyreUsage=" + tyreUsage + ", vid=" + vid + ", vehicleRegistration="
				+ vehicleRegistration + ", vehiclePreOdometer=" + vehiclePreOdometer + ", vehicleOdometer="
				+ vehicleOdometer + ", createdById=" + createdById + ", lastUpdatedById=" + lastUpdatedById
				+ ", creationOn=" + creationOn + ", lastUpdatedOn=" + lastUpdatedOn + ", createdBy=" + createdBy
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", creationDate=" + creationDate + ", lastUpdatedDate="
				+ lastUpdatedDate + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ ", transactionUsageTypeId=" + transactionUsageTypeId + ", transactionUsageType="
				+ transactionUsageType + ", transactionId=" + transactionId + ", transactionNumber=" + transactionNumber
				+ "]";
	}

	
	
	
}
