package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

public class VehicleInspectionReportDetails {

	private Integer		vid;
	
	private Long		vehicleGroupId;
	
	private String		vehicleGroup;
	
	private String		vehicle_registration;
	
	private String		fromDate;
	
	private String		toDate;
	
	private int			noOfRecord;
	
	private int			noOfTestedRecord;
	
	private int			noOfPassRecord;
	
	private int			noOfFailRecord;
	
	private int			noOfNotTestedRecord;
	
	private Double		testFailPercentage;
	
	private Double		testPassPercentage;
	
	private Double		notTestPercentage;
	
	

	public Integer getVid() {
		return vid;
	}

	public Long getVehicleGroupId() {
		return vehicleGroupId;
	}

	public String getVehicleGroup() {
		return vehicleGroup;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public String getFromDate() {
		return fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public int getNoOfRecord() {
		return noOfRecord;
	}

	public Double getTestFailPercentage() {
		return testFailPercentage;
	}

	public Double getTestPassPercentage() {
		return testPassPercentage;
	}

	public Double getNotTestPercentage() {
		return notTestPercentage;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public void setVehicleGroupId(Long vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
	}

	public void setVehicleGroup(String vehicleGroup) {
		this.vehicleGroup = vehicleGroup;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public void setNoOfRecord(int noOfRecord) {
		this.noOfRecord = noOfRecord;
	}

	public void setTestFailPercentage(Double testFailPercentage) {
		this.testFailPercentage = Utility.round(testFailPercentage, 2);
	}

	public void setTestPassPercentage(Double testPassPercentage) {
		this.testPassPercentage = Utility.round(testPassPercentage,2);
	}

	public void setNotTestPercentage(Double notTestPercentage) {
		this.notTestPercentage = Utility.round(notTestPercentage, 2);
	}

	public int getNoOfPassRecord() {
		return noOfPassRecord;
	}

	public int getNoOfFailRecord() {
		return noOfFailRecord;
	}

	public int getNoOfNotTestedRecord() {
		return noOfNotTestedRecord;
	}

	public void setNoOfPassRecord(int noOfPassRecord) {
		this.noOfPassRecord = noOfPassRecord;
	}

	public void setNoOfFailRecord(int noOfFailRecord) {
		this.noOfFailRecord = noOfFailRecord;
	}

	public void setNoOfNotTestedRecord(int noOfNotTestedRecord) {
		this.noOfNotTestedRecord = noOfNotTestedRecord;
	}

	public int getNoOfTestedRecord() {
		return noOfTestedRecord;
	}

	public void setNoOfTestedRecord(int noOfTestedRecord) {
		this.noOfTestedRecord = noOfTestedRecord;
	}

	@Override
	public String toString() {
		return "VehicleInspectionReportDetails [vid=" + vid + ", vehicleGroupId=" + vehicleGroupId + ", vehicleGroup="
				+ vehicleGroup + ", vehicle_registration=" + vehicle_registration + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", noOfRecord=" + noOfRecord + ", noOfPassRecord=" + noOfPassRecord
				+ ", noOfFailRecord=" + noOfFailRecord + ", noOfNotTestedRecord=" + noOfNotTestedRecord
				+ ", testFailPercentage=" + testFailPercentage + ", testPassPercentage=" + testPassPercentage
				+ ", notTestPercentage=" + notTestPercentage + "]";
	}

	
}
