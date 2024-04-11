package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class VehicleProfitAndLossReportDto {

	private Integer		vid;
	
	private Integer		companyId;
	
	private Double		expenseAmount;
	
	private Double		incomeAmount;
	
	private short		txnTypeId;
	
	private short		expenseTypeId;
	
	private String		txnType;
	
	private String		expenseType;
	
	private short		incomeType;
	
	private Integer 	incomeId;
	
	private Timestamp	startDateOfMonth;
	
	private String		vehicle_registration;
	
	private String	  incomeName;

	public short getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(short incomeType) {
		this.incomeType = incomeType;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Double getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(Double expenseAmount) {
		this.expenseAmount = Utility.round( expenseAmount,2);
	}

	public Double getIncomeAmount() {
		return incomeAmount;
	}

	public void setIncomeAmount(Double incomeAmount) {
		this.incomeAmount = Utility.round( incomeAmount, 2);
	}

	public short getTxnTypeId() {
		return txnTypeId;
	}

	public void setTxnTypeId(short txnTypeId) {
		this.txnTypeId = txnTypeId;
	}

	public short getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(short expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public Integer getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(Integer incomeId) {
		this.incomeId = incomeId;
	}

	public Timestamp getStartDateOfMonth() {
		return startDateOfMonth;
	}

	public void setStartDateOfMonth(Timestamp startDateOfMonth) {
		this.startDateOfMonth = startDateOfMonth;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public String getIncomeName() {
		return incomeName;
	}

	public void setIncomeName(String incomeName) {
		this.incomeName = incomeName;
	}

	@Override
	public String toString() {
		return "VehicleProfitAndLossReportDto [vid=" + vid + ", companyId=" + companyId + ", expenseAmount="
				+ expenseAmount + ", incomeAmount=" + incomeAmount + ", txnTypeId=" + txnTypeId + ", expenseTypeId="
				+ expenseTypeId + ", txnType=" + txnType + ", expenseType=" + expenseType + "]";
	}
	
	
}
