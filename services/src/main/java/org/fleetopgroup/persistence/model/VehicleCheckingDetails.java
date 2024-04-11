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
@Table(name="VehicleCheckingDetails")
public class VehicleCheckingDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "checkingId")
	private Long 		checkingId;
	
	@Column(name = "checkingInspectorId", nullable = false)
	private Integer		checkingInspectorId;
	
	@Column(name = "conductorId")
	private Integer 	conductorId;
	
	@Column(name = "vid", nullable = false)
	private Integer 	vid;
	
	@Column(name = "checkingDateTime", nullable = false)
	private Timestamp	checkingDateTime;
	
	@Column(name = "checkingTime", nullable = false)
	private String		checkingTime;
	
	@Column(name = "checkingOutTime", nullable = false)
	private String		checkingOutTime;
	
	@Column(name = "place")
	private String 		place;
	
	@Column(name = "outPlace")
	private String 		outPlace;
	
	@Column(name = "noOfSeat")
	private Integer		noOfSeat;
	
	@Column(name = "remark")
	private String		remark;
	
	@Column(name = "creationDateTime",  nullable = false, updatable = false)
	private Timestamp	creationDateTime;
	
	@Column(name = "createdById", nullable = false, updatable = false)
	private Long		createdById;
	
	@Column(name = "lastModifiedDateTime",  nullable = false)
	private Timestamp	lastModifiedDateTime;
	
	@Column(name = "lastModifiedById", nullable = false)
	private Long		lastModifiedById;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "vehicleGroupId", nullable = false)
	private Long vehicleGroupId;
	
	@Column(name = "markForDelete", nullable = false)
	private Boolean		markForDelete;
	
	@Column(name = "route")
	private String	route;
	
	@Column(name = "description")
	private String	description;
	
	@Column(name = "punishment")
	private String	punishment;
	
	@Column(name = "orderNoAndDate")
	private String	orderNoAndDate;
	
	
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


	public Integer getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public Long getVehicleGroupId() {
		return vehicleGroupId;
	}


	public void setVehicleGroupId(Long vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
	}


	public Boolean getMarkForDelete() {
		return markForDelete;
	}


	public void setMarkForDelete(Boolean markForDelete) {
		this.markForDelete = markForDelete;
	}


	public VehicleCheckingDetails() {
		super();
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


	public VehicleCheckingDetails(Long checkingId, Integer checkingInspectorId, Integer conductorId, Integer vid,
			Timestamp checkingDateTime, String checkingTime, String place, Integer noOfSeat, String remark,
			Timestamp creationDateTime, Long createdById, Timestamp lastModifiedDateTime, Long lastModifiedById,
			Boolean markForDelete, String route, String	description, String	punishment, String	orderNoAndDate) {
		super();
		this.checkingId = checkingId;
		this.checkingInspectorId = checkingInspectorId;
		this.conductorId = conductorId;
		this.vid = vid;
		this.checkingDateTime = checkingDateTime;
		this.checkingTime = checkingTime;
		this.place = place;
		this.noOfSeat = noOfSeat;
		this.remark = remark;
		this.creationDateTime = creationDateTime;
		this.createdById = createdById;
		this.lastModifiedDateTime = lastModifiedDateTime;
		this.lastModifiedById = lastModifiedById;
		this.markForDelete = markForDelete;
		this.route = route;
		this.description = description;
		this.punishment = punishment;
		this.orderNoAndDate = orderNoAndDate;
		
	}

	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkingInspectorId == null) ? 0 : checkingInspectorId.hashCode());
		result = prime * result + ((checkingTime == null) ? 0 : checkingTime.hashCode());
		result = prime * result + ((conductorId == null) ? 0 : conductorId.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((vid == null) ? 0 : vid.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleCheckingDetails other = (VehicleCheckingDetails) obj;
		if (checkingInspectorId == null) {
			if (other.checkingInspectorId != null)
				return false;
		} else if (!checkingInspectorId.equals(other.checkingInspectorId))
			return false;
		if (checkingTime == null) {
			if (other.checkingTime != null)
				return false;
		} else if (!checkingTime.equals(other.checkingTime))
			return false;
		if (conductorId == null) {
			if (other.conductorId != null)
				return false;
		} else if (!conductorId.equals(other.conductorId))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "VehicleCheckingDetails [checkingId=" + checkingId + ", checkingInspectorId=" + checkingInspectorId
				+ ", conductorId=" + conductorId + ", vid=" + vid + ", checkingDateTime=" + checkingDateTime
				+ ", checkingTime=" + checkingTime + ", checkingOutTime=" + checkingOutTime + ", place=" + place
				+ ", outPlace=" + outPlace + ", noOfSeat=" + noOfSeat + ", remark=" + remark + ", creationDateTime="
				+ creationDateTime + ", createdById=" + createdById + ", lastModifiedDateTime=" + lastModifiedDateTime
				+ ", lastModifiedById=" + lastModifiedById + ", companyId=" + companyId + ", vehicleGroupId="
				+ vehicleGroupId + ", markForDelete=" + markForDelete + ", route=" + route + ", description="
				+ description + ", punishment=" + punishment + ", orderNoAndDate=" + orderNoAndDate + "]";
	}
	
	

}
