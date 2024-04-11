package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

public class TripRoutefixedExpenseDto {

	private Integer routefixedID;

	private String expenseName;
	
	private Integer expenseId;

	private Double expenseAmount;

	private String expensePlace;
	
	private Integer expensePlaceId;

	private String expenseRefence;

	private String expenseFixed;
	
	private short	fixedTypeId;

	private Integer companyId;
	
	private boolean markForDelete;
	
	private boolean alreadyExpense;
	
	private Long tripExpenseID;
	/**
	 * @return the routefixedID
	 */
	public Integer getRoutefixedID() {
		return routefixedID;
	}

	/**
	 * @param routefixedID
	 *            the routefixedID to set
	 */
	public void setRoutefixedID(Integer routefixedID) {
		this.routefixedID = routefixedID;
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
	 * @return the expensePlace
	 */
	public String getExpensePlace() {
		return expensePlace;
	}

	/**
	 * @param expensePlace
	 *            the expensePlace to set
	 */
	public void setExpensePlace(String expensePlace) {
		this.expensePlace = expensePlace;
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

	

	public String getExpenseFixed() {
		return expenseFixed;
	}

	public void setExpenseFixed(String expenseFixed) {
		this.expenseFixed = expenseFixed;
	}
	public short getFixedTypeId() {
		return fixedTypeId;
	}

	public void setFixedTypeId(short fixedTypeId) {
		this.fixedTypeId = fixedTypeId;
	}

	public Integer getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}

	public Integer getExpensePlaceId() {
		return expensePlaceId;
	}

	public void setExpensePlaceId(Integer expensePlaceId) {
		this.expensePlaceId = expensePlaceId;
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

	
	
	
	public boolean isAlreadyExpense() {
		return alreadyExpense;
	}

	public void setAlreadyExpense(boolean alreadyExpense) {
		this.alreadyExpense = alreadyExpense;
	}

	public Long getTripExpenseID() {
		return tripExpenseID;
	}

	public void setTripExpenseID(Long tripExpenseID) {
		this.tripExpenseID = tripExpenseID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripRoutefixedExpense [routefixedID=");
		builder.append(routefixedID);
		builder.append(", expenseName=");
		builder.append(expenseName);
		builder.append(", expenseAmount=");
		builder.append(expenseAmount);
		builder.append(", expensePlace=");
		builder.append(expensePlace);
		builder.append(", expenseRefence=");
		builder.append(expenseRefence);
		builder.append(", expenseFixed=");
		builder.append(expenseFixed);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}

	

}
