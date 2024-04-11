package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

public class VehicleBatteryLayoutDto {

	private Long layoutId;

	private Integer vid;
	
	private Integer noOfBattery;

	private Long batteryCapacityId;

	private Integer companyId;
	
	private Long createdById;
	
	private Timestamp createdOn;
	
	private boolean markForDelete;
	
	private String vehicle_registration;
	
	private String batteryCapacity;
	
	private String createdBy;
	
	private String creationDate;

	private short layoutPosition;
	
	private Integer vehicle_Odometer;

	private boolean batteryAsigned;
	
	private String batterySerialNumber;
	
	private Long batteryId;
	
	
	public Long getLayoutId() {
		return layoutId;
	}


	public Integer getVid() {
		return vid;
	}


	public Integer getNoOfBattery() {
		return noOfBattery;
	}


	public Long getBatteryCapacityId() {
		return batteryCapacityId;
	}


	public Integer getCompanyId() {
		return companyId;
	}


	public Long getCreatedById() {
		return createdById;
	}


	public Timestamp getCreatedOn() {
		return createdOn;
	}


	public void setLayoutId(Long layoutId) {
		this.layoutId = layoutId;
	}


	public void setVid(Integer vid) {
		this.vid = vid;
	}


	public void setNoOfBattery(Integer noOfBattery) {
		this.noOfBattery = noOfBattery;
	}


	public void setBatteryCapacityId(Long batteryCapacity) {
		this.batteryCapacityId = batteryCapacity;
	}


	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}


	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
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


	public String getBatteryCapacity() {
		return batteryCapacity;
	}


	public void setBatteryCapacity(String batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public String getCreationDate() {
		return creationDate;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}


	public short getLayoutPosition() {
		return layoutPosition;
	}


	public void setLayoutPosition(short layoutPosition) {
		this.layoutPosition = layoutPosition;
	}


	public Integer getVehicle_Odometer() {
		return vehicle_Odometer;
	}


	public void setVehicle_Odometer(Integer vehicle_Odometer) {
		this.vehicle_Odometer = vehicle_Odometer;
	}


	public boolean isBatteryAsigned() {
		return batteryAsigned;
	}


	public void setBatteryAsigned(boolean batteryAsigned) {
		this.batteryAsigned = batteryAsigned;
	}


	public String getBatterySerialNumber() {
		return batterySerialNumber;
	}


	public void setBatterySerialNumber(String batterySerialNumber) {
		this.batterySerialNumber = batterySerialNumber;
	}


	public Long getBatteryId() {
		return batteryId;
	}


	public void setBatteryId(Long batteryId) {
		this.batteryId = batteryId;
	}


	@Override
	public String toString() {
		return "VehicleBatteryLayout [layoutId=" + layoutId + ", vid=" + vid + ", noOfBattery=" + noOfBattery
				+ ", batteryCapacityId=" + batteryCapacityId + ", companyId=" + companyId + ", createdById=" + createdById
				+ ", createdOn=" + createdOn + ", markForDelete=" + markForDelete + "]";
	}

}
