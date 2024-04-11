package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

import ch.qos.logback.classic.pattern.Util;

public class TripSheetReportDto {

	private Long	tripSheetId;
	private String	tripSheetNumber;
	private Integer	vid;
	private Date	tripOpenDate;
	private Date	tripCloseDate;
	private String	tripOpenDateStr;
	private String	tripCloseDateStr;
	private String	vehicleRegistration;
	private Double	tripIncomeAmount;
	private Double	tripExpenseAmount;
	private Double	tripAdvanceAmount;
	private Double	tripFuelAmount;
	private Double	tripFastTagAmt;
	private Double	tripBalanceAmt;
	private Double	tripFuelQuantity;
	private Double	tripDueAmount;
	private Double	driverBalanceAmt;
	private String	routeName;
	private Integer	routeId;
	private Integer	driverId;
	private String	driverName;
	private Integer incomeId;
	private Long tripIncomeId;
	private String incomeName;
	private String incomeCreated;
	
	
	
	
	public Long getTripSheetId() {
		return tripSheetId;
	}
	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}
	public String getTripSheetNumber() {
		return tripSheetNumber;
	}
	public void setTripSheetNumber(String tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}
	public Integer getVid() {
		return vid;
	}
	public void setVid(Integer vid) {
		this.vid = vid;
	}
	public Date getTripOpenDate() {
		return tripOpenDate;
	}
	public void setTripOpenDate(Date tripOpenDate) {
		this.tripOpenDate = tripOpenDate;
	}
	public Date getTripCloseDate() {
		return tripCloseDate;
	}
	public void setTripCloseDate(Date tripCloseDate) {
		this.tripCloseDate = tripCloseDate;
	}
	public String getTripOpenDateStr() {
		return tripOpenDateStr;
	}
	public void setTripOpenDateStr(String tripOpenDateStr) {
		this.tripOpenDateStr = tripOpenDateStr;
	}
	public String getTripCloseDateStr() {
		return tripCloseDateStr;
	}
	public void setTripCloseDateStr(String tripCloseDateStr) {
		this.tripCloseDateStr = tripCloseDateStr;
	}
	public String getVehicleRegistration() {
		return vehicleRegistration;
	}
	public void setVehicleRegistration(String vehicleRegistration) {
		this.vehicleRegistration = vehicleRegistration;
	}
	public Double getTripIncomeAmount() {
		return tripIncomeAmount;
	}
	public void setTripIncomeAmount(Double tripIncomeAmount) {
		this.tripIncomeAmount = Utility.round(tripIncomeAmount, 2) ;
	}
	public Double getTripExpenseAmount() {
		return tripExpenseAmount;
	}
	public void setTripExpenseAmount(Double tripExpenseAmount) {
		this.tripExpenseAmount = Utility.round( tripExpenseAmount, 2);
	}
	public Double getTripAdvanceAmount() {
		return tripAdvanceAmount;
	}
	public void setTripAdvanceAmount(Double tripAdvanceAmount) {
		this.tripAdvanceAmount =Utility.round( tripAdvanceAmount,2);
	}
	public Double getTripFuelAmount() {
		return tripFuelAmount;
	}
	public void setTripFuelAmount(Double tripFuelAmount) {
		this.tripFuelAmount = Utility.round(tripFuelAmount, 2) ;
	}
	public Double getTripFastTagAmt() {
		return tripFastTagAmt;
	}
	public void setTripFastTagAmt(Double tripFastTagAmt) {
		this.tripFastTagAmt = Utility.round(tripFastTagAmt, 2);
	}
	public Double getTripBalanceAmt() {
		return tripBalanceAmt;
	}
	public void setTripBalanceAmt(Double tripBalanceAmt) {
		this.tripBalanceAmt = Utility.round(tripBalanceAmt,2) ;
	}
	public Double getTripFuelQuantity() {
		return tripFuelQuantity;
	}
	public void setTripFuelQuantity(Double tripFuelQuantity) {
		this.tripFuelQuantity = Utility.round(tripFuelQuantity, 2) ;
	}
	public Double getTripDueAmount() {
		return tripDueAmount;
	}
	public void setTripDueAmount(Double tripDueAmount) {
		this.tripDueAmount = Utility.round(tripDueAmount, 2) ;
	}
	public Double getDriverBalanceAmt() {
		return driverBalanceAmt;
	}
	public void setDriverBalanceAmt(Double driverBalanceAmt) {
		this.driverBalanceAmt = Utility.round(driverBalanceAmt, 2);
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public Integer getRouteId() {
		return routeId;
	}
	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}
	public Integer getDriverId() {
		return driverId;
	}
	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public Integer getIncomeId() {
		return incomeId;
	}
	public void setIncomeId(Integer incomeId) {
		this.incomeId = incomeId;
	}
	public Long getTripIncomeId() {
		return tripIncomeId;
	}
	public void setTripIncomeId(Long tripIncomeId) {
		this.tripIncomeId = tripIncomeId;
	}
	public String getIncomeName() {
		return incomeName;
	}
	public void setIncomeName(String incomeName) {
		this.incomeName = incomeName;
	}
	public String getIncomeCreated() {
		return incomeCreated;
	}
	public void setIncomeCreated(String incomeCreated) {
		this.incomeCreated = incomeCreated;
	}
	@Override
	public String toString() {
		return "TripSheetReportDto [tripSheetId=" + tripSheetId + ", tripSheetNumber=" + tripSheetNumber + ", vid="
				+ vid + ", tripOpenDate=" + tripOpenDate + ", tripCloseDate=" + tripCloseDate + ", tripOpenDateStr="
				+ tripOpenDateStr + ", tripCloseDateStr=" + tripCloseDateStr + ", vehicleRegistration="
				+ vehicleRegistration + ", tripIncomeAmount=" + tripIncomeAmount + ", tripExpenseAmount="
				+ tripExpenseAmount + ", tripAdvanceAmount=" + tripAdvanceAmount + ", tripFuelAmount=" + tripFuelAmount
				+ ", tripFastTagAmt=" + tripFastTagAmt + ", tripBalanceAmt=" + tripBalanceAmt + ", tripFuelQuantity="
				+ tripFuelQuantity + ", tripDueAmount=" + tripDueAmount + ", driverBalanceAmt=" + driverBalanceAmt
				+ ", routeName=" + routeName + ", routeId=" + routeId + ", driverId=" + driverId + ", driverName="
				+ driverName + ", incomeId=" + incomeId + ", tripIncomeId=" + tripIncomeId + ", incomeName="
				+ incomeName + "]";
	}
	
}
