package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BusLocation")
public class BusLocation implements Serializable {
	
	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long busLocationId;
	
	private Timestamp busBookingDate;
	
	@Column(name = "VehicleNumberMasterId", nullable = false)
	private long vehicleNumberMasterId;
	
	@Column(name = "DriverId", nullable = false)
	private long driverId;
	
	@Column(name = "SourceLocationId", nullable = false)
	private long sourceLocationId;
	
	@Column(name = "DestinationLocationId", nullable = false)
	private long destinationLocationId;
	
	@Column(name = "CompanyId", nullable = false)
	private long companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;
	
	private Timestamp busOutDateTime;

	public long getBusLocationId() {
		return busLocationId;
	}

	public void setBusLocationId(long busLocationId) {
		this.busLocationId = busLocationId;
	}

	public Timestamp getBusBookingDate() {
		return busBookingDate;
	}

	public void setBusBookingDate(Timestamp busBookingDate) {
		this.busBookingDate = busBookingDate;
	}

	public long getVehicleNumberMasterId() {
		return vehicleNumberMasterId;
	}

	public void setVehicleNumberMasterId(long vehicleNumberMasterId) {
		this.vehicleNumberMasterId = vehicleNumberMasterId;
	}

	public long getDriverId() {
		return driverId;
	}

	public void setDriverId(long driverId) {
		this.driverId = driverId;
	}

	public long getSourceLocationId() {
		return sourceLocationId;
	}

	public void setSourceLocationId(long sourceLocationId) {
		this.sourceLocationId = sourceLocationId;
	}

	public long getDestinationLocationId() {
		return destinationLocationId;
	}

	public void setDestinationLocationId(long destinationLocationId) {
		this.destinationLocationId = destinationLocationId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Timestamp getBusOutDateTime() {
		return busOutDateTime;
	}

	public void setBusOutDateTime(Timestamp busOutDateTime) {
		this.busOutDateTime = busOutDateTime;
	}
	
	
}
