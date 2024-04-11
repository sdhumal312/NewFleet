package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class VehicleProfitAndLossDto {
	
	public static final String 	TRANSACTION_TYPE					= "TRANSACTION_TYPE";
	public static final String 	TRANSACTION_ID						= "transactionId";
	public static final String 	TRANSACTION__TYPE_ID				= "transactionTypeId";
	public static final String 	TRANSACTION_SUB_TYPE_ID				= "transactionSubTypeId";
	public static final String 	TRANSACTION_VID						= "vid";
	public static final String 	COMPANY_ID							= "companyId";
	public static final String	TXN_CHECKER_ID						= "txnChekerId";
	public static final String	TXN_EXPENSE_ID						= "expenseId";
	public static final String	TXN_INCOME_ID						= "incomeId";
	public static final String	TRIP_EXPENSE_ID						= "tripExpenseId";
	public static final String	TRIP_INCOME_ID						= "tripIncomeId";

	public static final  short			TRANSACTION_TYPE_INCOME			= 1;
	public static final  short			TRANSACTION_TYPE_EXPENSE		= 2;
	
	
	
	private Integer			vid;
	
	private Long			txnTypeId;
	
	private short			txnType;
	
	private short			type;
	
	private double			txnAmount;
	
	private Integer 		companyId;
	
	private Timestamp		transactionDateTime;
	
	private Timestamp		systemDateTime;
	
	private Timestamp		cratedOn;
	
	private Long			createdById;
	
	private Timestamp		lastUpdatedOn;
	
	private Long			lastUpdatedById;
	
	private Long			txnCheckerId;
	
	private boolean			isEdit;
	
	private Double			amountToBeAdded;
	
	private Double			previousAmount;
	
	private boolean			isDateChanged;
	
	private Timestamp		previousDate;
	
	private Long			expenseId;
	
	private Long			incomeId;
	
	private Long			tripIncomeId;
	
	private Long			tripExpenseId;
	
	private Double			previousDownPaymentAmount;

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Long getTxnTypeId() {
		return txnTypeId;
	}

	public void setTxnTypeId(Long txnTypeId) {
		this.txnTypeId = txnTypeId;
	}

	public short getTxnType() {
		return txnType;
	}

	public void setTxnType(short txnType) {
		this.txnType = txnType;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public double getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(double txnAmount) {
		this.txnAmount = Utility.round(txnAmount, 2);
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Timestamp getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(Timestamp transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public Timestamp getSystemDateTime() {
		return systemDateTime;
	}

	public void setSystemDateTime(Timestamp systemDateTime) {
		this.systemDateTime = systemDateTime;
	}

	public Timestamp getCratedOn() {
		return cratedOn;
	}

	public void setCratedOn(Timestamp cratedOn) {
		this.cratedOn = cratedOn;
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

	public Long getTxnCheckerId() {
		return txnCheckerId;
	}

	public void setTxnCheckerId(Long txnCheckerId) {
		this.txnCheckerId = txnCheckerId;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public Double getAmountToBeAdded() {
		return amountToBeAdded;
	}

	public void setAmountToBeAdded(Double amountToBeAdded) {
		this.amountToBeAdded = Utility.round(amountToBeAdded,2);
	}

	public Double getPreviousAmount() {
		return previousAmount;
	}

	public void setPreviousAmount(Double previousAmount) {
		this.previousAmount = Utility.round(previousAmount,2);
	}

	public boolean isDateChanged() {
		return isDateChanged;
	}

	public void setDateChanged(boolean isDateChanged) {
		this.isDateChanged = isDateChanged;
	}

	public Timestamp getPreviousDate() {
		return previousDate;
	}

	public void setPreviousDate(Timestamp previousDate) {
		this.previousDate = previousDate;
	}

	public Long getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}

	public Long getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(Long incomeId) {
		this.incomeId = incomeId;
	}

	public Long getTripIncomeId() {
		return tripIncomeId;
	}

	public void setTripIncomeId(Long tripIncomeId) {
		this.tripIncomeId = tripIncomeId;
	}

	public Long getTripExpenseId() {
		return tripExpenseId;
	}

	public void setTripExpenseId(Long tripExpenseId) {
		this.tripExpenseId = tripExpenseId;
	}

	public Double getPreviousDownPaymentAmount() {
		return previousDownPaymentAmount;
	}

	public void setPreviousDownPaymentAmount(Double previousDownPaymentAmount) {
		this.previousDownPaymentAmount = Utility.round( previousDownPaymentAmount, 2);
	}

	@Override
	public String toString() {
		return "VehicleProfitAndLossDto [vid=" + vid + ", txnTypeId=" + txnTypeId + ", txnType=" + txnType + ", type="
				+ type + ", txnAmount=" + txnAmount + ", companyId=" + companyId + ", transactionDateTime="
				+ transactionDateTime + ", systemDateTime=" + systemDateTime + ", cratedOn=" + cratedOn
				+ ", createdById=" + createdById + ", lastUpdatedOn=" + lastUpdatedOn + ", lastUpdatedById="
				+ lastUpdatedById + ", txnCheckerId=" + txnCheckerId + ", isEdit=" + isEdit + ", amountToBeAdded="
				+ amountToBeAdded + ", previousAmount=" + previousAmount + ", isDateChanged=" + isDateChanged
				+ ", previousDate=" + previousDate + ", expenseId=" + expenseId + ", incomeId=" + incomeId
				+ ", tripIncomeId=" + tripIncomeId + ", tripExpenseId=" + tripExpenseId + ", previousDownPaymentAmount="
				+ previousDownPaymentAmount + "]";
	}

	

	
}
