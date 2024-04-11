package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tripsheetadvance")
public class TripSheetAdvance implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripAdvanceID")
	private Long tripAdvanceID;

	@Column(name = "AdvanceAmount")
	private Double AdvanceAmount;

	/*@Column(name = "advancePlace", length = 150)
	@Deprecated
	private String advancePlace;*/
	
	@Column(name = "advancePlaceId", nullable = false)
	private Integer	advancePlaceId;

	/*@Column(name = "advancePaidby", length = 150)
	@Deprecated
	private String advancePaidby;*/
	
	@Column(name = "advancePaidbyId", nullable = false)
	private Long advancePaidbyId;

	@Column(name = "advanceRefence", length = 250)
	private String advanceRefence;

	@Column(name = "advanceRemarks", length = 250)
	private String advanceRemarks;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdById", nullable = false)
	private Long createdById;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "driverId")
	private Integer driverId;

	@ManyToOne
	@JoinColumn(name = "tripSheetID")
	@JsonBackReference
	private TripSheet tripsheet;
	
	@Column(name = "paymentTypeId")
	private Short paymentTypeId;
	
	@Column(name = "fromBalance")
	private Boolean fromBalance;
	
	@Column(name = "paidDate")
	private Date paidDate;

	@Column(name = "isPendingForTally", nullable = false)
	private boolean isPendingForTally;
	
	public TripSheetAdvance() {
		super();
	}

	public TripSheetAdvance(Double advanceAmount,  String advanceRefence,
			String advanceRemarks,  Date created, TripSheet tripsheet, Integer companyId, Integer driverId) {
		super();
		AdvanceAmount = advanceAmount;
		this.advanceRefence = advanceRefence;
		this.advanceRemarks = advanceRemarks;
		this.created = created;
		this.tripsheet = tripsheet;
		this.companyId = companyId;
		this.driverId = driverId;
	}

	/**
	 * @return the tripAdvanceID
	 */
	public Long getTripAdvanceID() {
		return tripAdvanceID;
	}

	/**
	 * @param tripAdvanceID
	 *            the tripAdvanceID to set
	 */
	public void setTripAdvanceID(Long tripAdvanceID) {
		this.tripAdvanceID = tripAdvanceID;
	}

	/**
	 * @return the advanceAmount
	 */
	public Double getAdvanceAmount() {
		return AdvanceAmount;
	}

	/**
	 * @param advanceAmount
	 *            the advanceAmount to set
	 */
	public void setAdvanceAmount(Double advanceAmount) {
		AdvanceAmount = advanceAmount;
	}


	/**
	 * @return the advanceRefence
	 */
	public String getAdvanceRefence() {
		return advanceRefence;
	}

	public Boolean getFromBalance() {
		return fromBalance;
	}

	public void setFromBalance(Boolean fromBalance) {
		this.fromBalance = fromBalance;
	}

	/**
	 * @param advanceRefence
	 *            the advanceRefence to set
	 */
	public void setAdvanceRefence(String advanceRefence) {
		this.advanceRefence = advanceRefence;
	}

	/**
	 * @return the advanceRemarks
	 */
	public String getAdvanceRemarks() {
		return advanceRemarks;
	}

	/**
	 * @param advanceRemarks
	 *            the advanceRemarks to set
	 */
	public void setAdvanceRemarks(String advanceRemarks) {
		this.advanceRemarks = advanceRemarks;
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


	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the advancePlaceId
	 */
	public Integer getAdvancePlaceId() {
		return advancePlaceId;
	}

	/**
	 * @param advancePlaceId the advancePlaceId to set
	 */
	public void setAdvancePlaceId(Integer advancePlaceId) {
		this.advancePlaceId = advancePlaceId;
	}

	/**
	 * @return the advancePaidbyId
	 */
	public Long getAdvancePaidbyId() {
		return advancePaidbyId;
	}

	/**
	 * @param advancePaidbyId the advancePaidbyId to set
	 */
	public void setAdvancePaidbyId(Long advancePaidbyId) {
		this.advancePaidbyId = advancePaidbyId;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @return the tripsheet
	 */
	public TripSheet getTripsheet() {
		return tripsheet;
	}

	/**
	 * @param tripsheet
	 *            the tripsheet to set
	 */
	public void setTripsheet(TripSheet tripsheet) {
		this.tripsheet = tripsheet;
	}

	//Driver Getters and Setters
	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public Short getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(Short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
	
	public boolean isPendingForTally() {
		return isPendingForTally;
	}

	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((advanceRefence == null) ? 0 : advanceRefence.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TripSheetAdvance other = (TripSheetAdvance) obj;
		if (advanceRefence == null) {
			if (other.advanceRefence != null)
				return false;
		} else if (!advanceRefence.equals(other.advanceRefence))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TripSheetAdvance [tripAdvanceID=" + tripAdvanceID + ", AdvanceAmount=" + AdvanceAmount
				+ ", advancePlaceId=" + advancePlaceId + ", advancePaidbyId=" + advancePaidbyId + ", advanceRefence="
				+ advanceRefence + ", advanceRemarks=" + advanceRemarks + ", companyId=" + companyId + ", createdById="
				+ createdById + ", created=" + created + ", markForDelete=" + markForDelete + ", driverId=" + driverId
				+ ", tripsheet=" + tripsheet + ", paymentTypeId=" + paymentTypeId + ", fromBalance=" + fromBalance
				+ ", isPendingForTally=" + isPendingForTally + "]";
	}

	
	
}