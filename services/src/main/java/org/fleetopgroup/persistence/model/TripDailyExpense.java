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
@Table(name = "TripDailyExpense")
public class TripDailyExpense implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripExpenseID")
	private Long tripExpenseID;

	@Column(name = "expenseId", nullable = false)
	private Integer expenseId;

	@Column(name = "expenseAmount")
	private Double expenseAmount;

	@Column(name = "expenseRefence", length = 20)
	private String expenseRefence;

	@Column(name = "fixedTypeId", nullable = false)
	private short fixedTypeId;

	@Column(name = "fuel_id")
	private Long fuel_id;

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

	public TripDailyExpense() {
		super();
	}

	public TripDailyExpense(Long tripExpenseID, Double expenseAmount, String expenseRefence, Date created,
			Integer companyId, TripDailySheet tripCollectionsheet) {
		super();
		this.tripExpenseID = tripExpenseID;
		this.expenseAmount = expenseAmount;
		this.expenseRefence = expenseRefence;
		this.created = created;
		this.companyId = companyId;
		this.tripDailysheet = tripCollectionsheet;
	}

	public TripDailyExpense(Double expenseAmount, String expenseRefence) {
		super();
		this.expenseAmount = expenseAmount;
		this.expenseRefence = expenseRefence;
	}

	public TripDailyExpense(Double expenseAmount, String expenseRefence, Long fuel_id) {
		super();
		this.expenseAmount = expenseAmount;
		this.expenseRefence = expenseRefence;
		this.fuel_id = fuel_id;
	}

	/**
	 * @return the fuel_id
	 */
	public Long getFuel_id() {
		return fuel_id;
	}

	/**
	 * @param fuel_id
	 *            the fuel_id to set
	 */
	public void setFuel_id(Long fuel_id) {
		this.fuel_id = fuel_id;
	}

	/**
	 * @return the markForDelete
	 */
	public boolean ismarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete
	 *            the markForDelete to set
	 */
	public void setmarkForDelete(boolean mARKFORDELETE) {
		markForDelete = mARKFORDELETE;
	}

	/**
	 * @return the tripExpenseID
	 */
	public Long getTripExpenseID() {
		return tripExpenseID;
	}

	/**
	 * @param tripExpenseID
	 *            the tripExpenseID to set
	 */
	public void setTripExpenseID(Long tripExpenseID) {
		this.tripExpenseID = tripExpenseID;
	}

	/**
	 * @return the expenseAmount
	 */
	public Double getExpenseAmount() {
		return expenseAmount;
	}

	/**
	 * @param expenseAmount
	 *            the expenseAmount to set
	 */
	public void setExpenseAmount(Double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	/**
	 * @return the expenseRefence
	 */
	public String getExpenseRefence() {
		return expenseRefence;
	}

	/**
	 * @param expenseRefence
	 *            the expenseRefence to set
	 */
	public void setExpenseRefence(String expenseRefence) {
		this.expenseRefence = expenseRefence;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}

	public short getFixedTypeId() {
		return fixedTypeId;
	}

	public void setFixedTypeId(short fixedTypeId) {
		this.fixedTypeId = fixedTypeId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
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
		result = prime * result + ((expenseAmount == null) ? 0 : expenseAmount.hashCode());
		result = prime * result + ((expenseId == null) ? 0 : expenseId.hashCode());
		result = prime * result + ((tripExpenseID == null) ? 0 : tripExpenseID.hashCode());
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
		TripDailyExpense other = (TripDailyExpense) obj;
		if (expenseAmount == null) {
			if (other.expenseAmount != null)
				return false;
		} else if (!expenseAmount.equals(other.expenseAmount))
			return false;
		if (expenseId == null) {
			if (other.expenseId != null)
				return false;
		} else if (!expenseId.equals(other.expenseId))
			return false;
		if (tripExpenseID == null) {
			if (other.tripExpenseID != null)
				return false;
		} else if (!tripExpenseID.equals(other.tripExpenseID))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripDailyExpense [tripExpenseID=");
		builder.append(tripExpenseID);
		builder.append(", expenseId=");
		builder.append(expenseId);
		builder.append(", expenseAmount=");
		builder.append(expenseAmount);
		builder.append(", expenseRefence=");
		builder.append(expenseRefence);
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