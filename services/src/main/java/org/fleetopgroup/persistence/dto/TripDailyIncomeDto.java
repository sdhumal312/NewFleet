package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.persistence.model.TripDailySheet;
import org.fleetopgroup.web.util.Utility;

public class TripDailyIncomeDto {

	private Long tripincomeID;

	private String incomeName;

	private Integer incomeId;
	
	private Double incomeAmount;

	private String incomeRefence;

	private String incomeCollectedBy;
	
	private String incomeFixed;
	
	private short incomeTypeId;
	
	private Long incomeCollectedById;
	
	private Integer companyId;

	private String createdBy;
	
	private Long createdById;

	private Date created;
	
	private boolean markForDelete;

	private Long tripDailysheetId;

	private TripDailySheet tripDailysheet;

	
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
		this.incomeAmount = Utility.round(incomeAmount,2);
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

	public Long getTripDailysheetId() {
		return tripDailysheetId;
	}

	public void setTripDailysheetId(Long tripDailysheetId) {
		this.tripDailysheetId = tripDailysheetId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripDailyIncome [tripincomeID=");
		builder.append(tripincomeID);
		builder.append(", incomeName=");
		builder.append(incomeName);
		builder.append(", incomeAmount=");
		builder.append(incomeAmount);
		builder.append(", incomeRefence=");
		builder.append(incomeRefence);
		builder.append(", incomeCollectedBy=");
		builder.append(incomeCollectedBy);
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
