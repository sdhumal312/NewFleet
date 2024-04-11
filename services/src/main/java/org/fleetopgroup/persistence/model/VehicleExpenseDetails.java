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
@Table(name = "VehicleExpenseDetails")
public class VehicleExpenseDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleExpenseDetailsId")
	private Long		vehicleExpenseDetailsId;
	
	@Column(name = "vid")
	private Integer 	vid;
	
	@Column(name = "expenseAmount")
	private Double		expenseAmount;
	
	@Column(name = "expenseType")
	private short		expenseType;
	
	@Column(name = "expenseId")
	private Integer 	expenseId;
	
	@Column(name = "expenseDate", nullable = false)
	private Timestamp	expenseDate;
	
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
	
	@Column(name = "tripExpenseId")
	private Long	tripExpenseId;
	
	public VehicleExpenseDetails() {
		super();
	}

	public VehicleExpenseDetails(Long vehicleExpenseDetailsId, Integer vid, Double expenseAmount, short expenseType,
			Integer expenseId, Timestamp expenseDate, Timestamp cratedOn, Long txnTypeId, Long createdById,
			Timestamp lastUpdatedOn, Long lastUpdatedById, Integer companyId, boolean markForDelete) {
		super();
		this.vehicleExpenseDetailsId = vehicleExpenseDetailsId;
		this.vid = vid;
		this.expenseAmount = expenseAmount;
		this.expenseType = expenseType;
		this.expenseId = expenseId;
		this.expenseDate = expenseDate;
		this.cratedOn = cratedOn;
		this.txnTypeId = txnTypeId;
		this.createdById = createdById;
		this.lastUpdatedOn = lastUpdatedOn;
		this.lastUpdatedById = lastUpdatedById;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}
	
	

	public Long getVehicleExpenseDetailsId() {
		return vehicleExpenseDetailsId;
	}

	public void setVehicleExpenseDetailsId(Long vehicleExpenseDetailsId) {
		this.vehicleExpenseDetailsId = vehicleExpenseDetailsId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Double getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(Double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	public short getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(short expenseType) {
		this.expenseType = expenseType;
	}

	public Integer getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}

	public Timestamp getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(Timestamp expenseDate) {
		this.expenseDate = expenseDate;
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

	public Long getTripExpenseId() {
		return tripExpenseId;
	}

	public void setTripExpenseId(Long tripExpenseId) {
		this.tripExpenseId = tripExpenseId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((expenseDate == null) ? 0 : expenseDate.hashCode());
		result = prime * result + ((expenseId == null) ? 0 : expenseId.hashCode());
		result = prime * result + ((txnTypeId == null) ? 0 : txnTypeId.hashCode());
		result = prime * result + ((vehicleExpenseDetailsId == null) ? 0 : vehicleExpenseDetailsId.hashCode());
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
		VehicleExpenseDetails other = (VehicleExpenseDetails) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (expenseDate == null) {
			if (other.expenseDate != null)
				return false;
		} else if (!expenseDate.equals(other.expenseDate))
			return false;
		if (expenseId == null) {
			if (other.expenseId != null)
				return false;
		} else if (!expenseId.equals(other.expenseId))
			return false;
		if (txnTypeId == null) {
			if (other.txnTypeId != null)
				return false;
		} else if (!txnTypeId.equals(other.txnTypeId))
			return false;
		if (vehicleExpenseDetailsId == null) {
			if (other.vehicleExpenseDetailsId != null)
				return false;
		} else if (!vehicleExpenseDetailsId.equals(other.vehicleExpenseDetailsId))
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
		return "VehicleExpenseDetails [vehicleExpenseDetailsId=" + vehicleExpenseDetailsId + ", vid=" + vid
				+ ", expenseAmount=" + expenseAmount + ", expenseType=" + expenseType + ", expenseId=" + expenseId
				+ ", expenseDate=" + expenseDate + ", cratedOn=" + cratedOn + ", txnTypeId=" + txnTypeId
				+ ", createdById=" + createdById + ", lastUpdatedOn=" + lastUpdatedOn + ", lastUpdatedById="
				+ lastUpdatedById + ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}
	
	

}
