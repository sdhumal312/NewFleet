package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vehicleodometerhistory")
public class VehicleOdometerHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "voh_id")
	private Long voh_id;

	@Column(name = "vid")
	private Integer vid;

	@Column(name = "voh_date")
	private Date voh_date;

	@Column(name = "vehicle_Odometer", length = 10)
	private Integer vehicle_Odometer;

	@Column(name = "voh_updatelocation", length = 150)
	private String voh_updatelocation;

	@Column(name = "voh_updateId")
	private Long voh_updateId;
	
	@Column(name = "companyId", nullable = false)
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public VehicleOdometerHistory() {
		super();
	}

	public VehicleOdometerHistory(Integer vid,  Date voh_date, Integer vehicle_Odometer,
			String voh_updatelocation, Long voh_updateId, Integer companyId) {
		super();
		this.vid = vid;
		//this.vehicle_registration = vehicle_registration;
		this.voh_date = voh_date;
		this.vehicle_Odometer = vehicle_Odometer;
		this.voh_updatelocation = voh_updatelocation;
		this.voh_updateId = voh_updateId;
		this.companyId = companyId;
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
	public Date getVoh_date() {
		return voh_date;
	}

	/**
	 * @param voh_date
	 *            the voh_date to set
	 */
	public void setVoh_date(Date voh_date) {
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
	 *//*
	public String getVehicle_registration() {
		return vehicle_registration;
	}

	*//**
	 * @param vehicle_registration
	 *            the vehicle_registration to set
	 *//*
	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}*/

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vehicle_Odometer == null) ? 0 : vehicle_Odometer.hashCode());
		result = prime * result + ((vid == null) ? 0 : vid.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleOdometerHistory other = (VehicleOdometerHistory) obj;
		if (vehicle_Odometer == null) {
			if (other.vehicle_Odometer != null)
				return false;
		} else if (!vehicle_Odometer.equals(other.vehicle_Odometer))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleOdometerHistory [vid=");
		builder.append(vid);
	/*	builder.append(", vehicle_registration=");
		builder.append(vehicle_registration);*/
		builder.append(", voh_date=");
		builder.append(voh_date);
		builder.append(", vehicle_Odometer=");
		builder.append(vehicle_Odometer);
		builder.append(", voh_updatelocation=");
		builder.append(voh_updatelocation);
		builder.append(", voh_updateId=");
		builder.append(voh_updateId);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append("]");
		return builder.toString();
	}

}