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
@Table(name = "TyreUsageHistory")
public class TyreUsageHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tyreUsageHistoryId")
	private Long tyreUsageHistoryId;
	
	@Column(name = "tyreId")
	private Long tyreId;
	
	@Column(name = "tyreUsage")
	private Integer tyreUsage;
	
	@Column(name = "vid")
	private Integer vid;

	@Column(name = "vehiclePreOdometer")
	private Integer vehiclePreOdometer;

	@Column(name = "vehicleOdometer")
	private Integer vehicleOdometer;
	
	@Column(name = "createdById")
	private Long createdById;

	@Column(name = "lastUpdatedById")
	private Long lastUpdatedById;

	@Column(name = "creationOn")
	private Date creationOn;

	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	@Column(name = "transactionUsageTypeId")
	private short transactionUsageTypeId;
	
	@Column(name = "transactionId")
	private Long transactionId;

	public TyreUsageHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TyreUsageHistory(Long tyreUsageHistoryId, Long tyreId, Integer tyreUsage, Integer vid,
			Integer vehiclePreOdometer, Integer vehicleOdometer, Long createdById, Long lastUpdatedById,
			Date creationOn, Date lastUpdatedOn, Integer companyId, boolean markForDelete, short transactionUsageTypeId,
			Long transactionId) {
		super();
		this.tyreUsageHistoryId = tyreUsageHistoryId;
		this.tyreId = tyreId;
		this.tyreUsage = tyreUsage;
		this.vid = vid;
		this.vehiclePreOdometer = vehiclePreOdometer;
		this.vehicleOdometer = vehicleOdometer;
		this.createdById = createdById;
		this.lastUpdatedById = lastUpdatedById;
		this.creationOn = creationOn;
		this.lastUpdatedOn = lastUpdatedOn;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
		this.transactionUsageTypeId = transactionUsageTypeId;
		this.transactionId = transactionId;
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

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		return "TyreUsageHistory [tyreUsageHistoryId=" + tyreUsageHistoryId + ", tyreId=" + tyreId + ", tyreUsage="
				+ tyreUsage + ", vid=" + vid + ", vehiclePreOdometer=" + vehiclePreOdometer
				+ ", vehicleCurrentOdometer=" + vehicleOdometer + ", createdById=" + createdById
				+ ", lastUpdatedById=" + lastUpdatedById + ", creationOn=" + creationOn + ", lastUpdatedOn="
				+ lastUpdatedOn + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ ", transactionUsageTypeId=" + transactionUsageTypeId + ", transactionId=" + transactionId + "]";
	}

	
	

}