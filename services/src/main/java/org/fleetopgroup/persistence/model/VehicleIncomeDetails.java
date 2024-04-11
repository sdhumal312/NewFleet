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
@Table(name = "VehicleIncomeDetails")
public class VehicleIncomeDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleIncomeDetailsId")
	private Long		vehicleIncomeDetailsId;
	
	@Column(name = "vid")
	private Integer 	vid;
	
	@Column(name = "incomeAmount")
	private Double		incomeAmount;
	
	@Column(name = "incomeType")
	private short		incomeType;
	
	@Column(name = "incomeId")
	private Integer 	incomeId;
	
	@Column(name = "incomeDate", nullable = false)
	private Timestamp	incomeDate;
	
	@Column(name = "cratedOn")
	private Timestamp	cratedOn;
	
	@Column(name = "txnTypeId")
	private Long		txnTypeId;
	
	@Column(name = "createdById", nullable = false, updatable = false)
	private Long		createdById;
	
	@Column(name = "lastUpdatedOn")
	private Timestamp	lastUpdatedOn;
	
	@Column(name = "lastUpdatedById")
	private Long		lastUpdatedById;
	
	@Column(name = "companyId", nullable = false)
	private Integer		companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean		markForDelete;
	
	@Column(name = "tripincomeId")
	private Long tripincomeId;
	
	public VehicleIncomeDetails() {
		super();
	}

	public VehicleIncomeDetails(Long vehicleIncomeDetailsId, Integer vid, Double incomeAmount, short incomeType,
			Integer incomeId, Timestamp incomeDate, Timestamp cratedOn, Long txnTypeId, Long createdById,
			Timestamp lastUpdatedOn, Long lastUpdatedById, Integer companyId, boolean markForDelete) {
		super();
		this.vehicleIncomeDetailsId = vehicleIncomeDetailsId;
		this.vid = vid;
		this.incomeAmount = incomeAmount;
		this.incomeType = incomeType;
		this.incomeId = incomeId;
		this.incomeDate = incomeDate;
		this.cratedOn = cratedOn;
		this.txnTypeId = txnTypeId;
		this.createdById = createdById;
		this.lastUpdatedOn = lastUpdatedOn;
		this.lastUpdatedById = lastUpdatedById;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	public Long getVehicleIncomeDetailsId() {
		return vehicleIncomeDetailsId;
	}

	public void setVehicleIncomeDetailsId(Long vehicleIncomeDetailsId) {
		this.vehicleIncomeDetailsId = vehicleIncomeDetailsId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Double getIncomeAmount() {
		return incomeAmount;
	}

	public void setIncomeAmount(Double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}

	public short getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(short incomeType) {
		this.incomeType = incomeType;
	}

	public Integer getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(Integer incomeId) {
		this.incomeId = incomeId;
	}

	public Timestamp getIncomeDate() {
		return incomeDate;
	}

	public void setIncomeDate(Timestamp incomeDate) {
		this.incomeDate = incomeDate;
	}

	public Timestamp getCratedOn() {
		return cratedOn;
	}

	public void setCratedOn(Timestamp cratedOn) {
		this.cratedOn = cratedOn;
	}

	public Long getTxnTypeId() {
		return txnTypeId;
	}

	public void setTxnTypeId(Long txnTypeId) {
		this.txnTypeId = txnTypeId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Timestamp getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Timestamp lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}

	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
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

	public Long getTripincomeId() {
		return tripincomeId;
	}

	public void setTripincomeId(Long tripincomeId) {
		this.tripincomeId = tripincomeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((incomeId == null) ? 0 : incomeId.hashCode());
		result = prime * result + incomeType;
		result = prime * result + ((vehicleIncomeDetailsId == null) ? 0 : vehicleIncomeDetailsId.hashCode());
		result = prime * result + ((vid == null) ? 0 : vid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleIncomeDetails other = (VehicleIncomeDetails) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (incomeId == null) {
			if (other.incomeId != null)
				return false;
		} else if (!incomeId.equals(other.incomeId))
			return false;
		if (incomeType != other.incomeType)
			return false;
		if (vehicleIncomeDetailsId == null) {
			if (other.vehicleIncomeDetailsId != null)
				return false;
		} else if (!vehicleIncomeDetailsId.equals(other.vehicleIncomeDetailsId))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VehicleIncomeDetails [vehicleIncomeDetailsId=" + vehicleIncomeDetailsId + ", vid=" + vid
				+ ", incomeAmount=" + incomeAmount + ", incomeType=" + incomeType + ", incomeId=" + incomeId
				+ ", incomeDate=" + incomeDate + ", cratedOn=" + cratedOn + ", txnTypeId=" + txnTypeId
				+ ", createdById=" + createdById + ", lastUpdatedOn=" + lastUpdatedOn + ", lastUpdatedById="
				+ lastUpdatedById + ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}
	
	

}
