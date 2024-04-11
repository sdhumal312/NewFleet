package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

public class LoadingSheetReportDto {

	private Long 	tripSheetID;
	private Long 	tripSheetNumber;
	private Integer vid;
	private String 	vehicle_registration;
	private String 	tripOpenDate;
	private String 	dispatchedByTime;
	private String 	closedByTime;
	private String	noOfLS;
	private	Long	noOfWayBill;
	private Double	totalNoOfPackages;
	private Double	totalFrieght;
	private Double	totalBooking;
	private String	routeName;
	private String	tripSheetNumberStr;
	private Double	dispatchedWeight;
	private Double	tripExpense;
	private Double	tollExpenseAmount;
	private Double	fuelCreditAmount;
	private Double  balanceAmount;
	private String	tripSheetCurrentStatus;
	private String 	noOfDays;
	
	
	public Double getDispatchedWeight() {
		return dispatchedWeight;
	}
	public void setDispatchedWeight(Double dispatchedWeight) {
		this.dispatchedWeight = dispatchedWeight;
	}
	public Long getTripSheetID() {
		return tripSheetID;
	}
	public void setTripSheetID(Long tripSheetID) {
		this.tripSheetID = tripSheetID;
	}
	public Long getTripSheetNumber() {
		return tripSheetNumber;
	}
	public void setTripSheetNumber(Long tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}
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
	public String getTripOpenDate() {
		return tripOpenDate;
	}
	public void setTripOpenDate(String tripOpenDate) {
		this.tripOpenDate = tripOpenDate;
	}
	public String getDispatchedByTime() {
		return dispatchedByTime;
	}
	public void setDispatchedByTime(String dispatchedByTime) {
		this.dispatchedByTime = dispatchedByTime;
	}
	public String getClosedByTime() {
		return closedByTime;
	}
	public void setClosedByTime(String closedByTime) {
		this.closedByTime = closedByTime;
	}
	public String getNoOfLS() {
		return noOfLS;
	}
	public void setNoOfLS(String noOfLS) {
		this.noOfLS = noOfLS;
	}
	public Long getNoOfWayBill() {
		return noOfWayBill;
	}
	public void setNoOfWayBill(Long noOfWayBill) {
		this.noOfWayBill = noOfWayBill;
	}
	public Double getTotalNoOfPackages() {
		return totalNoOfPackages;
	}
	public void setTotalNoOfPackages(Double totalNoOfPackages) {
		this.totalNoOfPackages = totalNoOfPackages;
	}
	public Double getTotalFrieght() {
		return totalFrieght;
	}
	public void setTotalFrieght(Double totalFrieght) {
		this.totalFrieght = totalFrieght;
	}
	public Double getTotalBooking() {
		return totalBooking;
	}
	public void setTotalBooking(Double totalBooking) {
		this.totalBooking = totalBooking;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public String getTripSheetNumberStr() {
		return tripSheetNumberStr;
	}
	public void setTripSheetNumberStr(String tripSheetNumberStr) {
		this.tripSheetNumberStr = tripSheetNumberStr;
	}
	public Double getTripExpense() {
		return tripExpense;
	}
	public void setTripExpense(Double tripExpense) {
		this.tripExpense = Utility.round(tripExpense, 2);
	}
	public Double getTollExpenseAmount() {
		return tollExpenseAmount;
	}
	public void setTollExpenseAmount(Double tollExpenseAmount) {
		this.tollExpenseAmount = Utility.round(tollExpenseAmount, 2) ;
	}
	public Double getFuelCreditAmount() {
		return fuelCreditAmount;
	}
	public void setFuelCreditAmount(Double fuelCreditAmount) {
		this.fuelCreditAmount =  Utility.round(fuelCreditAmount, 2);
	}
	/**
	 * @return the balanceAmount
	 */
	public Double getBalanceAmount() {
		return balanceAmount;
	}
	/**
	 * @param balanceAmount the balanceAmount to set
	 */
	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	/**
	 * @return the tripStutesId
	 */
	/**
	 * @return the tripSheetCurrentStatus
	 */
	public String getTripSheetCurrentStatus() {
		return tripSheetCurrentStatus;
	}
	/**
	 * @param tripSheetCurrentStatus the tripSheetCurrentStatus to set
	 */
	public void setTripSheetCurrentStatus(String tripSheetCurrentStatus) {
		this.tripSheetCurrentStatus = tripSheetCurrentStatus;
	}
	public String getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}
	
	
}
