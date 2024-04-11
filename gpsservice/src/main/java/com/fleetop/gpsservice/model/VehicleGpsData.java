package com.fleetop.gpsservice.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Vehiclegpsdata")
public class VehicleGpsData implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleGpsDataId")
	private Long vehicleGpsDataId;
	
	
	@Column(name = "vid")
	private Integer vid;
	
	@Column(name = "vehicleNumber")
	private String  vehicleNumber;
	
	@Column(name="rfid")
	private String 		rfid;

	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "created", updatable = false)
	private Date created;
	
	@Column(name = "gpsOdometer")
	private Double gpsOdometer;
	
	@Column(name = "eventDateTime", updatable = false)
	private Date eventDateTime;
	
	@Column(name = "companyId", nullable = false)
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Double getGpsOdometer() {
		return gpsOdometer;
	}

	public void setGpsOdometer(Double gpsOdometer) {
		this.gpsOdometer = gpsOdometer;
	}

	public Date getEventDateTime() {
		return eventDateTime;
	}

	public void setEventDateTime(Date eventDateTime) {
		this.eventDateTime = eventDateTime;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Long getVehicleGpsDataId() {
		return vehicleGpsDataId;
	}

	public void setVehicleGpsDataId(Long vehicleGpsDataId) {
		this.vehicleGpsDataId = vehicleGpsDataId;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	@Override
	public String toString() {
		return "VehicleGpsData [vehicleGpsDataId=" + vehicleGpsDataId + ", vid=" + vid + ", created=" + created
				+ ", gpsOdometer=" + gpsOdometer + ", eventDateTime=" + eventDateTime + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + "]";
	}
	
}