package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.persistence.model.TripDailySheet;
import org.fleetopgroup.web.util.Utility;

public class TripDailyTimeIncomeDto {

	private Long TDTIMEID;

	private String incomeName;
	
	private Integer incomeId;

	private Double incomeAmount;

	private String incomeRefence;

	private String incomeCollectedBy;
	
	private String incomeFixed;
	
	private short incomeFixedId;
	
	private Integer companyId;

	private String createdBy;
	
	private Long createdById;

	private Date created;
	
	private boolean markForDelete;

	private TripDailySheet tripDailysheet;

	
	/**
	 * @return the tDTIMEID
	 */
	public Long getTDTIMEID() {
		return TDTIMEID;
	}

	/**
	 * @param tDTIMEID the tDTIMEID to set
	 */
	public void setTDTIMEID(Long tDTIMEID) {
		TDTIMEID = tDTIMEID;
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
		this.incomeAmount = Utility.round(incomeAmount, 2);
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
	 * @return the incomeFixed
	 */
	public String getIncomeFixed() {
		return incomeFixed;
	}

	/**
	 * @param incomeFixed the incomeFixed to set
	 */
	public void setIncomeFixed(String incomeFixed) {
		this.incomeFixed = incomeFixed;
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

	/**
	 * @return the incomeFixedId
	 */
	public short getIncomeFixedId() {
		return incomeFixedId;
	}

	/**
	 * @param incomeFixedId the incomeFixedId to set
	 */
	public void setIncomeFixedId(short incomeFixedId) {
		this.incomeFixedId = incomeFixedId;
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
	 * @return the markForDelete
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete the markForDelete to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripDailyTimeIncome [TDTIMEID=");
		builder.append(TDTIMEID);
		builder.append(", incomeName=");
		builder.append(incomeName);
		builder.append(", incomeAmount=");
		builder.append(incomeAmount);
		builder.append(", incomeRefence=");
		builder.append(incomeRefence);
		builder.append(", incomeCollectedBy=");
		builder.append(incomeCollectedBy);
		builder.append(", incomeFixed=");
		builder.append(incomeFixed);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", createdBy=");
		builder.append(createdBy);
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
