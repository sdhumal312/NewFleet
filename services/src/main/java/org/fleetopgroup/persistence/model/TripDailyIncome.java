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

@Entity
@Table(name = "TripDailyIncome")
public class TripDailyIncome implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripincomeID")
	private Long tripincomeID;

	@Column(name = "incomeId", nullable = false)
	private Integer incomeId;

	@Column(name = "incomeAmount")
	private Double incomeAmount;

	@Column(name = "incomeRefence", length = 20)
	private String incomeRefence;

	@Column(name = "incomeTypeId", nullable = false)
	private short incomeTypeId;

	@Column(name = "incomeCollectedById", nullable = false)
	private Long incomeCollectedById;

	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdById", nullable = false)
	private Long createdById;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	@ManyToOne
	@JoinColumn(name = "TRIPDAILYID")
	private TripDailySheet tripDailysheet;

	public TripDailyIncome() {
		super();
	}

	public TripDailyIncome(Long tripincomeID, Integer incomeId, Double incomeAmount, String incomeRefence,
			Long incomeCollectedById, Long createdById, Date created, TripDailySheet tripCollectionsheet) {
		super();
		this.tripincomeID = tripincomeID;
		this.incomeId = incomeId;
		this.incomeAmount = incomeAmount;
		this.incomeRefence = incomeRefence;
		this.incomeCollectedById = incomeCollectedById;
		this.createdById = createdById;
		this.created = created;
		this.tripDailysheet = tripCollectionsheet;
	}

	public TripDailyIncome(Double incomeAmount, String incomeRefence) {
		super();
		this.incomeAmount = incomeAmount;
		this.incomeRefence = incomeRefence;
	}

	/**
	 * @return the tripincomeID
	 */
	public Long getTripincomeID() {
		return tripincomeID;
	}

	/**
	 * @param tripincomeID
	 *            the tripincomeID to set
	 */
	public void setTripincomeID(Long tripincomeID) {
		this.tripincomeID = tripincomeID;
	}

	/**
	 * @return the incomeAmount
	 */
	public Double getIncomeAmount() {
		return incomeAmount;
	}

	/**
	 * @param incomeAmount
	 *            the incomeAmount to set
	 */
	public void setIncomeAmount(Double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}

	/**
	 * @return the incomeRefence
	 */
	public String getIncomeRefence() {
		return incomeRefence;
	}

	/**
	 * @param incomeRefence
	 *            the incomeRefence to set
	 */
	public void setIncomeRefence(String incomeRefence) {
		this.incomeRefence = incomeRefence;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(Integer incomeId) {
		this.incomeId = incomeId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public short getIncomeTypeId() {
		return incomeTypeId;
	}

	public void setIncomeTypeId(short incomeTypeId) {
		this.incomeTypeId = incomeTypeId;
	}

	public Long getIncomeCollectedById() {
		return incomeCollectedById;
	}

	public void setIncomeCollectedById(Long incomeCollectedById) {
		this.incomeCollectedById = incomeCollectedById;
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
	 * @return the tripDailysheet
	 */
	public TripDailySheet getTripDailysheet() {
		return tripDailysheet;
	}

	/**
	 * @param tripDailysheet
	 *            the tripDailysheet to set
	 */
	public void setTripDailysheet(TripDailySheet tripDailysheet) {
		this.tripDailysheet = tripDailysheet;
	}

	/**
	 * @return the markForDelete
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete
	 *            the markForDelete to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((incomeAmount == null) ? 0 : incomeAmount.hashCode());
		result = prime * result + ((incomeId == null) ? 0 : incomeId.hashCode());
		result = prime * result + ((tripincomeID == null) ? 0 : tripincomeID.hashCode());
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
		TripDailyIncome other = (TripDailyIncome) obj;
		if (incomeAmount == null) {
			if (other.incomeAmount != null)
				return false;
		} else if (!incomeAmount.equals(other.incomeAmount))
			return false;
		if (incomeId == null) {
			if (other.incomeId != null)
				return false;
		} else if (!incomeId.equals(other.incomeId))
			return false;
		if (tripincomeID == null) {
			if (other.tripincomeID != null)
				return false;
		} else if (!tripincomeID.equals(other.tripincomeID))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * table
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripDailyIncome [tripincomeID=");
		builder.append(tripincomeID);
		builder.append(", incomeId=");
		builder.append(incomeId);
		builder.append(", incomeAmount=");
		builder.append(incomeAmount);
		builder.append(", incomeRefence=");
		builder.append(incomeRefence);
		builder.append(", incomeCollectedById=");
		builder.append(incomeCollectedById);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", created=");
		builder.append(created);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", tripDailysheet=");
		builder.append(tripDailysheet);
		builder.append("]");
		return builder.toString();
	}

}