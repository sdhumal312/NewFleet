package org.fleetopgroup.persistence.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VehicleGPSData")
public class VehicleGPSData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="vehicleGPSDataId")
	private Long vehicleGPSDataId;
	
	@Column(name="vehicleOdometer")
	private Integer		vehicleOdometer;
	
	@Column(name="rfid")
	private String 		rfid;
	
	@Column(name="dateTime")
	private Timestamp	dateTime;
	
	@Column(name="vehicleNumber")
	private String		vehicleNumber;
	
	@Column(name="address")
	private String		address;
	
	@Column(name="latitude")
	private String		latitude;
	
	@Column(name="longitude")
	private String		longitude;
	
	@Column(name="companyId")
	private Integer		companyId;
	
	@Column(name="markForDelete")
	private boolean		markForDelete;
	
	@Column(name="gpsDeviceId")
	private String 	gpsDeviceId;

	public Long getVehicleGPSDataId() {
		return vehicleGPSDataId;
	}

	public Integer getVehicleOdometer() {
		return vehicleOdometer;
	}

	public String getRfid() {
		return rfid;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public String getAddress() {
		return address;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setVehicleGPSDataId(Long vehicleGPSDataId) {
		this.vehicleGPSDataId = vehicleGPSDataId;
	}

	public void setVehicleOdometer(Integer vehicleOdometer) {
		this.vehicleOdometer = vehicleOdometer;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public String getGpsDeviceId() {
		return gpsDeviceId;
	}

	public void setGpsDeviceId(String gpsDeviceId) {
		this.gpsDeviceId = gpsDeviceId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleGPSData [vehicleGPSDataId=");
		builder.append(vehicleGPSDataId);
		builder.append(", vehicleOdometer=");
		builder.append(vehicleOdometer);
		builder.append(", rfid=");
		builder.append(rfid);
		builder.append(", dateTime=");
		builder.append(dateTime);
		builder.append(", vehicleNumber=");
		builder.append(vehicleNumber);
		builder.append(", address=");
		builder.append(address);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}
	
	
}
