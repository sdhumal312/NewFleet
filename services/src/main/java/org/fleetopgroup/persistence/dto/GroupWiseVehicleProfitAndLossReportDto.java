package org.fleetopgroup.persistence.dto;

import java.io.Serializable;

import org.fleetopgroup.web.util.Utility;

public class GroupWiseVehicleProfitAndLossReportDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer		vid;
	
	private String		vehicle_registration;
	
	private Integer		noOfTrips;
	
	private Integer		daysInTrip;
	
	private Integer		noOfDaysIDeal;
	
	private Integer		totalKMRun;
	
	private Double		totalIncome;
	
	private Double		totalExpense;
	
	private Double		totalBalance;
	
	private Double	driverMonthlySalary;

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

	public Integer getNoOfTrips() {
		return noOfTrips;
	}

	public void setNoOfTrips(Integer noOfTrips) {
		this.noOfTrips = noOfTrips;
	}

	public Integer getDaysInTrip() {
		return daysInTrip;
	}

	public void setDaysInTrip(Integer daysInTrip) {
		this.daysInTrip = daysInTrip;
	}

	public Integer getNoOfDaysIDeal() {
		return noOfDaysIDeal;
	}

	public void setNoOfDaysIDeal(Integer noOfDaysIDeal) {
		this.noOfDaysIDeal = noOfDaysIDeal;
	}

	public Integer getTotalKMRun() {
		return totalKMRun;
	}

	public void setTotalKMRun(Integer totalKMRun) {
		this.totalKMRun = totalKMRun;
	}

	public Double getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(Double totalIncome) {
		this.totalIncome = Utility.round(totalIncome, 2);
	}

	public Double getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(Double totalExpense) {
		this.totalExpense = Utility.round(totalExpense,2);
	}

	public Double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(Double totalBalance) {
		this.totalBalance = Utility.round(totalBalance, 2);
	}

	public Double getDriverMonthlySalary() {
		return driverMonthlySalary;
	}

	public void setDriverMonthlySalary(Double driverMonthlySalary) {
		this.driverMonthlySalary = Utility.round(driverMonthlySalary,2);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GroupWiseVehicleProfitAndLossReportDto [vid=");
		builder.append(vid);
		builder.append(", vehicle_registration=");
		builder.append(vehicle_registration);
		builder.append(", noOfTrips=");
		builder.append(noOfTrips);
		builder.append(", daysInTrip=");
		builder.append(daysInTrip);
		builder.append(", noOfDaysIDeal=");
		builder.append(noOfDaysIDeal);
		builder.append(", totalKMRun=");
		builder.append(totalKMRun);
		builder.append(", totalIncome=");
		builder.append(totalIncome);
		builder.append(", totalExpense=");
		builder.append(totalExpense);
		builder.append(", totalBalance=");
		builder.append(totalBalance);
		builder.append(", driverMonthlySalary=");
		builder.append(driverMonthlySalary);
		builder.append("]");
		return builder.toString();
	}
	

	
	
}
