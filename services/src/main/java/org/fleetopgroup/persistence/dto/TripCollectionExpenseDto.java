package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class TripCollectionExpenseDto {

	private Long tripExpenseID;

	private String expenseName;

	private Double expenseAmount;

	private String expenseRefence;

	private String createdBy;

	private Date created;
	
	private Integer	expenseId;
	
	

	public TripCollectionExpenseDto() {
		super();
	}

	public TripCollectionExpenseDto(Long tripExpenseID, String expenseName, Double expenseAmount, String expenseRefence,
			String createdBy, Date created) {
		super();
		this.tripExpenseID = tripExpenseID;
		this.expenseName = expenseName;
		this.expenseAmount = expenseAmount;
		this.expenseRefence = expenseRefence;
		this.createdBy = createdBy;
		this.created = created;

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
	 * @return the expenseName
	 */
	public String getExpenseName() {
		return expenseName;
	}

	/**
	 * @param expenseName
	 *            the expenseName to set
	 */
	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
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
		this.expenseAmount = Utility.round(expenseAmount, 2);
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

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	 * @return the expenseId
	 */
	public Integer getExpenseId() {
		return expenseId;
	}

	/**
	 * @param expenseId the expenseId to set
	 */
	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
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
		result = prime * result + ((expenseAmount == null) ? 0 : expenseAmount.hashCode());
		result = prime * result + ((expenseName == null) ? 0 : expenseName.hashCode());
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
		TripCollectionExpenseDto other = (TripCollectionExpenseDto) obj;
		if (expenseAmount == null) {
			if (other.expenseAmount != null)
				return false;
		} else if (!expenseAmount.equals(other.expenseAmount))
			return false;
		if (expenseName == null) {
			if (other.expenseName != null)
				return false;
		} else if (!expenseName.equals(other.expenseName))
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
		builder.append("TripCollectionExpenseDto [tripExpenseID=");
		builder.append(tripExpenseID);
		builder.append(", expenseName=");
		builder.append(expenseName);
		builder.append(", expenseAmount=");
		builder.append(expenseAmount);
		builder.append(", expenseRefence=");
		builder.append(expenseRefence);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", created=");
		builder.append(created);
		builder.append("]");
		return builder.toString();
	}

}