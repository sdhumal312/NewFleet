package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class DateWiseVehicleExpenseDto {

	private Long		dateWiseVehicleExpenseId;
	
	private Integer 	vid;
	
	private Double		expenseAmount;
	
	private short		expenseType;
	
	private Integer 	expenseId;
	
	private Timestamp	expenseDate;
	
	private Long		txnTypeId;
	
	private Integer		companyId;
	
	private String		vehicle_registration;
	
	private	String		expenseTypeName;
	
	private String		tripExpenseName;
	
	private String		expenseTypeStr;
	
	private Double driverMonthlySalary;
	
	private short	 expenseCategory;
	
	public DateWiseVehicleExpenseDto(Double expenseAmount, String expenseTypeName, short expenseType) {
		super();
		this.expenseAmount 		= expenseAmount;
		this.expenseTypeName 	= expenseTypeName;
		this.expenseType		= expenseType;
		this.tripExpenseName	= expenseTypeName;
	}
	
	public DateWiseVehicleExpenseDto() {
		super();
	}
	

	public Long getDateWiseVehicleExpenseId() {
		return dateWiseVehicleExpenseId;
	}

	public Integer getVid() {
		return vid;
	}

	public Double getExpenseAmount() {
		return expenseAmount;
	}

	public short getExpenseType() {
		return expenseType;
	}

	public Integer getExpenseId() {
		return expenseId;
	}

	public Timestamp getExpenseDate() {
		return expenseDate;
	}

	public Long getTxnTypeId() {
		return txnTypeId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public String getExpenseTypeName() {
		return expenseTypeName;
	}

	public void setDateWiseVehicleExpenseId(Long dateWiseVehicleExpenseId) {
		this.dateWiseVehicleExpenseId = dateWiseVehicleExpenseId;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public void setExpenseAmount(Double expenseAmount) {
		this.expenseAmount = Utility.round(expenseAmount, 2);
	}

	public void setExpenseType(short expenseType) {
		this.expenseType = expenseType;
	}

	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}

	public void setExpenseDate(Timestamp expenseDate) {
		this.expenseDate = expenseDate;
	}

	public void setTxnTypeId(Long txnTypeId) {
		this.txnTypeId = txnTypeId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
	}

	public String getTripExpenseName() {
		return tripExpenseName;
	}

	public void setTripExpenseName(String tripExpenseName) {
		this.tripExpenseName = tripExpenseName;
	}

	public String getExpenseTypeStr() {
		return expenseTypeStr;
	}

	public void setExpenseTypeStr(String expenseTypeStr) {
		this.expenseTypeStr = expenseTypeStr;
	}

	public Double getDriverMonthlySalary() {
		return driverMonthlySalary;
	}

	public void setDriverMonthlySalary(Double driverMonthlySalary) {
		this.driverMonthlySalary = Utility.round(driverMonthlySalary, 2) ;
	}

	public short getExpenseCategory() {
		return expenseCategory;
	}

	public void setExpenseCategory(short expenseCategory) {
		this.expenseCategory = expenseCategory;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DateWiseVehicleExpenseDto [dateWiseVehicleExpenseId=");
		builder.append(dateWiseVehicleExpenseId);
		builder.append(", vid=");
		builder.append(vid);
		builder.append(", expenseAmount=");
		builder.append(expenseAmount);
		builder.append(", expenseType=");
		builder.append(expenseType);
		builder.append(", expenseId=");
		builder.append(expenseId);
		builder.append(", expenseDate=");
		builder.append(expenseDate);
		builder.append(", txnTypeId=");
		builder.append(txnTypeId);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", vehicle_registration=");
		builder.append(vehicle_registration);
		builder.append(", expenseTypeName=");
		builder.append(expenseTypeName);
		builder.append(", driverMonthlySalary=");
		builder.append(driverMonthlySalary);
		builder.append("]");
		return builder.toString();
	}
	
	
}
