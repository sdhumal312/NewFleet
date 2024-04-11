package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

public class VehicleCheckingDetailsDto {

	private Long 		checkingId;
	
	private Integer		checkingInspectorId;
	
	private Integer 	conductorId;
	
	private Integer 	vid;
	
	private Timestamp	checkingDateTime;
	
	private String		checkingTime;
	
	private String		checkingOutTime;
	
	private String 		place;
	
	private String 		outPlace;
	
	private Integer		noOfSeat;
	
	private String		remark;
	
	private Timestamp	creationDateTime;
	
	private Long		createdById;
	
	private Long		vehicleGroupId;
	
	private Timestamp	lastModifiedDateTime;
	
	private Long		lastModifiedById;
	
	private Boolean		markForDelete;
	
	private String		fromDate;
	
	private String		toDate;
	
	private Integer		companyId;
	
	private String		conductorName;
	
	private String		checkingInspectorName;
	
	private String		vehicle_registration;
	
	private String		checkingDate;
	
	private String		route;
	
	private String		description;
	
	private String		punishment;
	
	private String		orderNoAndDate;
	
	
	
	public Long getCheckingId() {
		return checkingId;
	}


	public void setCheckingId(Long checkingId) {
		this.checkingId = checkingId;
	}


	public Integer getCheckingInspectorId() {
		return checkingInspectorId;
	}


	public void setCheckingInspectorId(Integer checkingInspectorId) {
		this.checkingInspectorId = checkingInspectorId;
	}


	public Integer getConductorId() {
		return conductorId;
	}


	public void setConductorId(Integer conductorId) {
		this.conductorId = conductorId;
	}


	public Integer getVid() {
		return vid;
	}


	public void setVid(Integer vid) {
		this.vid = vid;
	}


	public Timestamp getCheckingDateTime() {
		return checkingDateTime;
	}


	public void setCheckingDateTime(Timestamp checkingDateTime) {
		this.checkingDateTime = checkingDateTime;
	}


	public String getCheckingTime() {
		return checkingTime;
	}


	public void setCheckingTime(String checkingTime) {
		this.checkingTime = checkingTime;
	}


	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}


	public Integer getNoOfSeat() {
		return noOfSeat;
	}


	public void setNoOfSeat(Integer noOfSeat) {
		this.noOfSeat = noOfSeat;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Timestamp getCreationDateTime() {
		return creationDateTime;
	}


	public void setCreationDateTime(Timestamp creationDateTime) {
		this.creationDateTime = creationDateTime;
	}


	public Long getCreatedById() {
		return createdById;
	}


	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}


	public Timestamp getLastModifiedDateTime() {
		return lastModifiedDateTime;
	}


	public void setLastModifiedDateTime(Timestamp lastModifiedDateTime) {
		this.lastModifiedDateTime = lastModifiedDateTime;
	}


	public Long getLastModifiedById() {
		return lastModifiedById;
	}


	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}


	public Boolean getMarkForDelete() {
		return markForDelete;
	}


	public void setMarkForDelete(Boolean markForDelete) {
		this.markForDelete = markForDelete;
	}


	public String getFromDate() {
		return fromDate;
	}


	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}


	public String getToDate() {
		return toDate;
	}


	public void setToDate(String toDate) {
		this.toDate = toDate;
	}


	public Integer getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public String getConductorName() {
		return conductorName;
	}


	public void setConductorName(String conductorName) {
		this.conductorName = conductorName;
	}


	public String getCheckingInspectorName() {
		return checkingInspectorName;
	}


	public void setCheckingInspectorName(String checkingInspectorName) {
		this.checkingInspectorName = checkingInspectorName;
	}


	public String getVehicle_registration() {
		return vehicle_registration;
	}


	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}


	public String getCheckingOutTime() {
		return checkingOutTime;
	}


	public void setCheckingOutTime(String checkingOutTime) {
		this.checkingOutTime = checkingOutTime;
	}


	public String getOutPlace() {
		return outPlace;
	}


	public void setOutPlace(String outPlace) {
		this.outPlace = outPlace;
	}


	public Long getVehicleGroupId() {
		return vehicleGroupId;
	}


	public void setVehicleGroupId(Long vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
	}


	public String getCheckingDate() {
		return checkingDate;
	}


	public void setCheckingDate(String checkingDate) {
		this.checkingDate = checkingDate;
	}


	public String getRoute() {
		return route;
	}


	public void setRoute(String route) {
		this.route = route;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getPunishment() {
		return punishment;
	}


	public void setPunishment(String punishment) {
		this.punishment = punishment;
	}


	public String getOrderNoAndDate() {
		return orderNoAndDate;
	}


	public void setOrderNoAndDate(String orderNoAndDate) {
		this.orderNoAndDate = orderNoAndDate;
	}


	
	@Override
	public String toString() {
		return "VehicleCheckingDetailsDto [checkingId=" + checkingId + ", checkingInspectorId=" + checkingInspectorId
				+ ", conductorId=" + conductorId + ", vid=" + vid + ", checkingDateTime=" + checkingDateTime
				+ ", checkingTime=" + checkingTime + ", checkingOutTime=" + checkingOutTime + ", place=" + place
				+ ", outPlace=" + outPlace + ", noOfSeat=" + noOfSeat + ", remark=" + remark + ", creationDateTime="
				+ creationDateTime + ", createdById=" + createdById + ", vehicleGroupId=" + vehicleGroupId
				+ ", lastModifiedDateTime=" + lastModifiedDateTime + ", lastModifiedById=" + lastModifiedById
				+ ", markForDelete=" + markForDelete + ", fromDate=" + fromDate + ", toDate=" + toDate + ", companyId="
				+ companyId + ", conductorName=" + conductorName + ", checkingInspectorName=" + checkingInspectorName
				+ ", vehicle_registration=" + vehicle_registration + ", checkingDate=" + checkingDate + ", route="
				+ route + ", description=" + description + ", punishment=" + punishment + ", orderNoAndDate="
				+ orderNoAndDate + "]";
	}



}
