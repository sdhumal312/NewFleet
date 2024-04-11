package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class VehicleOdometerHistoryDto {

	private Long voh_id;

	private Integer vid;

	private String vehicle_registration;

	private String voh_date;
	
	private Date voh_date_On;

	private Integer vehicle_Odometer;

	private String voh_updatelocation;

	private Long voh_updateId;

	private String diffrent_time_day;

	private String diffrent_meter_oddmeter;

	private String voh_updateIdStr;
	
	private String voh_updateNumber;
	
	public String getVoh_updateNumber() {
		return voh_updateNumber;
	}

	public void setVoh_updateNumber(String voh_updateNumber) {
		this.voh_updateNumber = voh_updateNumber;
		
	}

	public String getVoh_updateIdStr() {
		return voh_updateIdStr;
	}

	public void setVoh_updateIdStr(String voh_updateIdStr) {
		this.voh_updateIdStr = voh_updateIdStr;
	}

	/**
	 * @return the voh_id
	 */
	public Long getVoh_id() {
		return voh_id;
	}

	/**
	 * @param voh_id
	 *            the voh_id to set
	 */
	public void setVoh_id(Long voh_id) {
		this.voh_id = voh_id;
	}

	/**
	 * @return the voh_date
	 */
	public String getVoh_date() {
		return voh_date;
	}

	/**
	 * @param voh_date
	 *            the voh_date to set
	 */
	public void setVoh_date(String voh_date) {
		this.voh_date = voh_date;
	}

	/**
	 * @return the vid
	 */
	public Integer getVid() {
		return vid;
	}

	/**
	 * @param vid
	 *            the vid to set
	 */
	public void setVid(Integer vid) {
		this.vid = vid;
	}

	/**
	 * @return the vehicle_registration
	 */
	public String getVehicle_registration() {
		return vehicle_registration;
	}

	/**
	 * @param vehicle_registration
	 *            the vehicle_registration to set
	 */
	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	/**
	 * @return the vehicle_Odometer
	 */
	public Integer getVehicle_Odometer() {
		return vehicle_Odometer;
	}

	/**
	 * @param vehicle_Odometer
	 *            the vehicle_Odometer to set
	 */
	public void setVehicle_Odometer(Integer vehicle_Odometer) {
		this.vehicle_Odometer = vehicle_Odometer;
	}

	/**
	 * @return the voh_updatelocation
	 */
	public String getVoh_updatelocation() {
		return voh_updatelocation;
	}

	/**
	 * @param voh_updatelocation
	 *            the voh_updatelocation to set
	 */
	public void setVoh_updatelocation(String voh_updatelocation) {
		this.voh_updatelocation = voh_updatelocation;
	}

	/**
	 * @return the voh_updateId
	 */
	public Long getVoh_updateId() {
		return voh_updateId;
	}

	/**
	 * @param voh_updateId
	 *            the voh_updateId to set
	 */
	public void setVoh_updateId(Long voh_updateId) {
		this.voh_updateId = voh_updateId;
	}

	/**
	 * @return the diffrent_time_day
	 */
	public String getDiffrent_time_day() {
		return diffrent_time_day;
	}

	/**
	 * @param diffrent_time_day
	 *            the diffrent_time_day to set
	 */
	public void setDiffrent_time_day(String diffrent_time_day) {
		this.diffrent_time_day = diffrent_time_day;
	}

	/**
	 * @return the diffrent_meter_oddmeter
	 */
	public String getDiffrent_meter_oddmeter() {
		return diffrent_meter_oddmeter;
	}

	/**
	 * @param diffrent_meter_oddmeter
	 *            the diffrent_meter_oddmeter to set
	 */
	public void setDiffrent_meter_oddmeter(String diffrent_meter_oddmeter) {
		this.diffrent_meter_oddmeter = diffrent_meter_oddmeter;
	}

	/**
	 * @return the voh_date_On
	 */
	public Date getVoh_date_On() {
		return voh_date_On;
	}

	/**
	 * @param voh_date_On the voh_date_On to set
	 */
	public void setVoh_date_On(Date voh_date_On) {
		this.voh_date_On = voh_date_On;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleOdometerHistoryDto [vid=");
		builder.append(vid);
		builder.append(", vehicle_registration=");
		builder.append(vehicle_registration);
		builder.append(", voh_date=");
		builder.append(voh_date);
		builder.append(", vehicle_Odometer=");
		builder.append(vehicle_Odometer);
		builder.append(", voh_updatelocation=");
		builder.append(voh_updatelocation);
		builder.append(", voh_updateId=");
		builder.append(voh_updateId);
		builder.append(", diffrent_time_day=");
		builder.append(diffrent_time_day);
		builder.append(", diffrent_meter_oddmeter=");
		builder.append(diffrent_meter_oddmeter);
		builder.append("]");
		return builder.toString();
	}

}