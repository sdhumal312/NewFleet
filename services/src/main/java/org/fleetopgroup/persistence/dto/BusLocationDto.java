package org.fleetopgroup.persistence.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class BusLocationDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long		busLocationId;
	private Timestamp	busBookingDate;
	private String		busBookingDateStr;
	private long 		vehicleNumberMatserId;
	private String		vehicleNumber;
	private long		driverMatserId;
	private String 		driverName;
	private long 		sourceLocationId;
	private String 		sourceLocationName;
	private long		destinationLocationId;
	private String 		destinationLocationName;
	private String		busOutDateStr;

	public Timestamp getBusBookingDate() {
		return busBookingDate;
	}
	
	public void setBusBookingDate(Timestamp busBookingDate) {
		this.busBookingDate = busBookingDate;
	}
	
	public long getVehicleNumberMatserId() {
		return vehicleNumberMatserId;
	}
	
	public void setVehicleNumberMatserId(long vehicleNumberMatserId) {
		this.vehicleNumberMatserId = vehicleNumberMatserId;
	}

	public long getDriverMatserId() {
		return driverMatserId;
	}
	
	public void setDriverMatserId(long driverMatserId) {
		this.driverMatserId = driverMatserId;
	}
	
	public String getDriverName() {
		return driverName;
	}
	
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	public long getSourceLocationId() {
		return sourceLocationId;
	}
	
	public void setSourceLocationId(long sourceLocationId) {
		this.sourceLocationId = sourceLocationId;
	}
	
	public String getSourceLocationName() {
		return sourceLocationName;
	}
	
	public void setSourceLocationName(String sourceLocationName) {
		this.sourceLocationName = sourceLocationName;
	}
	
	public long getDestinationLocationId() {
		return destinationLocationId;
	}

	public void setDestinationLocationId(long destinationLocationId) {
		this.destinationLocationId = destinationLocationId;
	}
	
	public String getDestinationLocationName() {
		return destinationLocationName;
	}
	
	public void setDestinationLocationName(String destinationLocationName) {
		this.destinationLocationName = destinationLocationName;
	}

	public String getBusBookingDateStr() {
		return busBookingDateStr;
	}

	public void setBusBookingDateStr(String busBookingDateStr) {
		this.busBookingDateStr = busBookingDateStr;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public long getBusLocationId() {
		return busLocationId;
	}

	public void setBusLocationId(long busLocationId) {
		this.busLocationId = busLocationId;
	}

	public String getBusOutDateStr() {
		return busOutDateStr;
	}

	public void setBusOutDateStr(String busOutDateStr) {
		this.busOutDateStr = busOutDateStr;
	}

}
