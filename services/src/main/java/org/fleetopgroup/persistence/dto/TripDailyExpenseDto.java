package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.persistence.model.TripDailySheet;
import org.fleetopgroup.web.util.Utility;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TripDailyExpenseDto {

	private Long tripExpenseID;

	private String expenseName;

	private Integer expenseId;
	
	private Double expenseAmount;

	private String expenseRefence;

	private String expenseFixed;
	
	private short fixedTypeId;
	
	private Long fuel_id;
	
	private Integer companyId;

	private String createdBy;
	
	private Long createdById;

	private Date created;
	
	@JsonIgnore
	private boolean markForDelete;

	private Long tripDailysheetID;

	private TripDailySheet tripDailysheet;
	
	private Long TRIPDAILYNUMBER;

	private String createdDate;
	
	private String vehicle_registration;
	
	private Integer vid;
	
	
	
	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Long getTRIPDAILYNUMBER() {
		return TRIPDAILYNUMBER;
	}

	public void setTRIPDAILYNUMBER(Long tRIPDAILYNUMBER) {
		TRIPDAILYNUMBER = tRIPDAILYNUMBER;
	}

	/**
	 * @return the fuel_id
	 */
	public Long getFuel_id() {
		return fuel_id;
	}

	/**
	 * @param fuel_id the fuel_id to set
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

	public Long getTripDailysheetID() {
		return tripDailysheetID;
	}

	public void setTripDailysheetID(Long tripDailysheetID) {
		this.tripDailysheetID = tripDailysheetID;
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
	 * @return the expenseFixed
	 */
	public String getExpenseFixed() {
		return expenseFixed;
	}

	/**
	 * @param expenseFixed
	 *            the expenseFixed to set
	 */
	public void setExpenseFixed(String expenseFixed) {
		this.expenseFixed = expenseFixed;
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
		builder.append(", expenseName=");
		builder.append(expenseName);
		builder.append(", expenseAmount=");
		builder.append(expenseAmount);
		builder.append(", expenseRefence=");
		builder.append(expenseRefence);
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
