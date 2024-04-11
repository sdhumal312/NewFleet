package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class TripCollectionIncomeDto {

	private Long tripincomeID;

	private String incomeName;

	private Double incomeAmount;

	private String incomeRefence;

	private String incomeCollectedBy;

	private String createdBy;

	private Date created;
	
	private Integer incomeId;

	public TripCollectionIncomeDto() {
		super();
	}

	public TripCollectionIncomeDto(Long tripincomeID, String incomeName, Double incomeAmount, String incomeRefence,
			String incomeCollectedBy, String createdBy, Date created) {
		super();
		this.tripincomeID = tripincomeID;
		this.incomeName = incomeName;
		this.incomeAmount = incomeAmount;
		this.incomeRefence = incomeRefence;
		this.incomeCollectedBy = incomeCollectedBy;
		this.createdBy = createdBy;
		this.created = created;
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
	 * @return the incomeName
	 */
	public String getIncomeName() {
		return incomeName;
	}

	/**
	 * @param incomeName
	 *            the incomeName to set
	 */
	public void setIncomeName(String incomeName) {
		this.incomeName = incomeName;
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
		this.incomeAmount =Utility.round(incomeAmount, 2);
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

	/**
	 * @return the incomeCollectedBy
	 */
	public String getIncomeCollectedBy() {
		return incomeCollectedBy;
	}

	/**
	 * @param incomeCollectedBy
	 *            the incomeCollectedBy to set
	 */
	public void setIncomeCollectedBy(String incomeCollectedBy) {
		this.incomeCollectedBy = incomeCollectedBy;
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
	 * @return the incomeId
	 */
	public Integer getIncomeId() {
		return incomeId;
	}

	/**
	 * @param incomeId the incomeId to set
	 */
	public void setIncomeId(Integer incomeId) {
		this.incomeId = incomeId;
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
		result = prime * result + ((incomeAmount == null) ? 0 : incomeAmount.hashCode());
		result = prime * result + ((incomeName == null) ? 0 : incomeName.hashCode());
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
		TripCollectionIncomeDto other = (TripCollectionIncomeDto) obj;
		if (incomeAmount == null) {
			if (other.incomeAmount != null)
				return false;
		} else if (!incomeAmount.equals(other.incomeAmount))
			return false;
		if (incomeName == null) {
			if (other.incomeName != null)
				return false;
		} else if (!incomeName.equals(other.incomeName))
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
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripCollectionIncomeDto [tripincomeID=");
		builder.append(tripincomeID);
		builder.append(", incomeName=");
		builder.append(incomeName);
		builder.append(", incomeAmount=");
		builder.append(incomeAmount);
		builder.append(", incomeRefence=");
		builder.append(incomeRefence);
		builder.append(", incomeCollectedBy=");
		builder.append(incomeCollectedBy);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", created=");
		builder.append(created);
		builder.append("]");
		return builder.toString();
	}

}