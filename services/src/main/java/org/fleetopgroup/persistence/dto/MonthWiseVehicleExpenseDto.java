package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class MonthWiseVehicleExpenseDto {

	
	private Long		monthWiseVehicleExpenseId;

	private Integer 	vid;
	
	private Double		expenseAmount;
	
	private short		expenseType;
	
	private Integer 	expenseId;
	
	private Long		txnTypeId;
	
	private int			expenseMonth;
	
	private int			expenseYear;
	
	private Integer		companyId;
	
	private boolean		markForDelete;
	
	private Timestamp	startDateOfMonth;
	
	private String		vehicle_registration;
	
	private String		expenseTypeStr;
	
	private String		tripExpenseName;
	
	private Double driverMonthlySalary;
	
	public MonthWiseVehicleExpenseDto() {
		super();
	}
	
	


	public MonthWiseVehicleExpenseDto(Double expenseAmount, String expenseTypeStr, short expenseType) {
		super();
		this.expenseAmount 		= expenseAmount;
		this.expenseTypeStr 	= expenseTypeStr;
		this.expenseType		= expenseType;
		this.tripExpenseName	= expenseTypeStr;
	}

	

	public Double getDriverMonthlySalary() {
		return driverMonthlySalary;
	}




	public void setDriverMonthlySalary(Double driverMonthlySalary) {
		this.driverMonthlySalary = Utility.round(driverMonthlySalary, 2);
	}




	public Long getMonthWiseVehicleExpenseId() {
		return monthWiseVehicleExpenseId;
	}


	public void setMonthWiseVehicleExpenseId(Long monthWiseVehicleExpenseId) {
		this.monthWiseVehicleExpenseId = monthWiseVehicleExpenseId;
	}


	public Integer getVid() {
		return vid;
	}


	public void setVid(Integer vid) {
		this.vid = vid;
	}


	public Double getExpenseAmount() {
		return expenseAmount;
	}


	public void setExpenseAmount(Double expenseAmount) {
		this.expenseAmount = Utility.round(expenseAmount, 2);
	}


	public short getExpenseType() {
		return expenseType;
	}


	public void setExpenseType(short expenseType) {
		this.expenseType = expenseType;
	}


	public Integer getExpenseId() {
		return expenseId;
	}


	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}


	public Long getTxnTypeId() {
		return txnTypeId;
	}


	public void setTxnTypeId(Long txnTypeId) {
		this.txnTypeId = txnTypeId;
	}


	public int getExpenseMonth() {
		return expenseMonth;
	}


	public void setExpenseMonth(int expenseMonth) {
		this.expenseMonth = expenseMonth;
	}


	public int getExpenseYear() {
		return expenseYear;
	}


	public void setExpenseYear(int expenseYear) {
		this.expenseYear = expenseYear;
	}


	public Integer getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public Timestamp getStartDateOfMonth() {
		return startDateOfMonth;
	}


	public void setStartDateOfMonth(Timestamp startDateOfMonth) {
		this.startDateOfMonth = startDateOfMonth;
	}


	public boolean isMarkForDelete() {
		return markForDelete;
	}


	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}


	public String getVehicle_registration() {
		return vehicle_registration;
	}


	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}


	public String getExpenseTypeStr() {
		return expenseTypeStr;
	}


	public void setExpenseTypeStr(String expenseTypeStr) {
		this.expenseTypeStr = expenseTypeStr;
	}


	public String getTripExpenseName() {
		return tripExpenseName;
	}


	public void setTripExpenseName(String tripExpenseName) {
		this.tripExpenseName = tripExpenseName;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + expenseType;
		result = prime * result + ((monthWiseVehicleExpenseId == null) ? 0 : monthWiseVehicleExpenseId.hashCode());
		result = prime * result + ((vid == null) ? 0 : vid.hashCode());
		return result;
	}




	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MonthWiseVehicleExpenseDto [monthWiseVehicleExpenseId=");
		builder.append(monthWiseVehicleExpenseId);
		builder.append(", vid=");
		builder.append(vid);
		builder.append(", expenseAmount=");
		builder.append(expenseAmount);
		builder.append(", expenseType=");
		builder.append(expenseType);
		builder.append(", expenseId=");
		builder.append(expenseId);
		builder.append(", txnTypeId=");
		builder.append(txnTypeId);
		builder.append(", expenseMonth=");
		builder.append(expenseMonth);
		builder.append(", expenseYear=");
		builder.append(expenseYear);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", startDateOfMonth=");
		builder.append(startDateOfMonth);
		builder.append(", vehicle_registration=");
		builder.append(vehicle_registration);
		builder.append(", expenseTypeStr=");
		builder.append(expenseTypeStr);
		builder.append(", tripExpenseName=");
		builder.append(tripExpenseName);
		builder.append(", driverMonthlySalary=");
		builder.append(driverMonthlySalary);
		builder.append("]");
		return builder.toString();
	}




	
	


	

	
}
