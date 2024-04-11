package org.fleetopgroup.persistence.dto;

import java.util.Date;

import javax.persistence.Column;

public class VehicleGpsDto {

	
	private Integer vid;
	
	private String vehicleRegistrationNumber;

	private String vehicleChassisNo;
	
	private Integer	gpsVendorId;
	
	private Integer	company_Id;
	
	private Double odometer;
	
	private Double  lattitude;
	
	private Double  longitude;
	
	private Long  positionDateTime;
	
	private Date position_DateTime;
	
	private Double  speed;
	
	private Object  totalEngineHours;
	
	private Object totalFuelConsumption;
	
	private Object idleTime;
	
	private Object fuelLevel;
	
	private int heading;
	
	private int altitude;
	
	private int ignitionStatus;

	public VehicleGpsDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VehicleGpsDto(Integer vid, String vehicleRegistrationNumber, String vehicleChassisNo, Integer gpsVendorId,
			Integer company_Id, Double odometer, Double lattitude, Double longitude, Long positionDateTime,
			Double speed, Double totalEngineHours, Double totalFuelConsumption, Long idleTime, Double fuelLevel,
			int heading, int altitude, int ignitionStatus) {
		super();
		this.vid = vid;
		this.vehicleRegistrationNumber = vehicleRegistrationNumber;
		this.vehicleChassisNo = vehicleChassisNo;
		this.gpsVendorId = gpsVendorId;
		this.company_Id = company_Id;
		this.odometer = odometer;
		this.lattitude = lattitude;
		this.longitude = longitude;
		this.positionDateTime = positionDateTime;
		this.speed = speed;
		this.totalEngineHours = totalEngineHours;
		this.totalFuelConsumption = totalFuelConsumption;
		this.idleTime = idleTime;
		this.fuelLevel = fuelLevel;
		this.heading = heading;
		this.altitude = altitude;
		this.ignitionStatus = ignitionStatus;
	}

	public Integer getVid() {
		return vid;
	}

	

	public String getVehicleRegistrationNumber() {
		return vehicleRegistrationNumber;
	}

	public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
		this.vehicleRegistrationNumber = vehicleRegistrationNumber;
	}

	public String getVehicleChassisNo() {
		return vehicleChassisNo;
	}

	public void setVehicleChassisNo(String vehicleChassisNo) {
		this.vehicleChassisNo = vehicleChassisNo;
	}

	public Integer getGpsVendorId() {
		return gpsVendorId;
	}

	public void setGpsVendorId(Integer gpsVendorId) {
		this.gpsVendorId = gpsVendorId;
	}

	public Integer getCompany_Id() {
		return company_Id;
	}

	public void setCompany_Id(Integer company_Id) {
		this.company_Id = company_Id;
	}

	public Double getOdometer() {
		return odometer;
	}

	public void setOdometer(Double odometer) {
		this.odometer = odometer;
	}

	public Double getLattitude() {
		return lattitude;
	}

	public void setLattitude(Double lattitude) {
		this.lattitude = lattitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Long getPositionDateTime() {
		return positionDateTime;
	}

	public void setPositionDateTime(Long positionDateTime) {
		this.positionDateTime = positionDateTime;
	}

	public Date getPosition_DateTime() {
		return position_DateTime;
	}

	public void setPosition_DateTime(Date position_DateTime) {
		this.position_DateTime = position_DateTime;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Object getTotalEngineHours() {
		return totalEngineHours;
	}

	public void setTotalEngineHours(Object totalEngineHours) {
		this.totalEngineHours = totalEngineHours;
	}

	public Object getTotalFuelConsumption() {
		return totalFuelConsumption;
	}

	public void setTotalFuelConsumption(Object totalFuelConsumption) {
		this.totalFuelConsumption = totalFuelConsumption;
	}

	public Object getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(Object idleTime) {
		this.idleTime = idleTime;
	}

	public Object getFuelLevel() {
		return fuelLevel;
	}

	public void setFuelLevel(Object fuelLevel) {
		this.fuelLevel = fuelLevel;
	}

	public int getHeading() {
		return heading;
	}

	public void setHeading(int heading) {
		this.heading = heading;
	}

	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public int getIgnitionStatus() {
		return ignitionStatus;
	}

	public void setIgnitionStatus(int ignitionStatus) {
		this.ignitionStatus = ignitionStatus;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	@Override
	public String toString() {
		return "VehicleGpsDto [vehicleChassisNo=" + vehicleChassisNo + ", odometer=" + odometer + ", positionDateTime="
				+ positionDateTime + ", position_DateTime=" + position_DateTime + "]";
	}
	
}
