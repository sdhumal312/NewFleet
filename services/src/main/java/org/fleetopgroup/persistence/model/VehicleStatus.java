package org.fleetopgroup.persistence.model;
/**
 * @author fleetop
 *
 *
 *
 */

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VehicleStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sid")
	private Long sid;

	@Column(name = "vStatus", unique = true, nullable = false, length = 25)
	private String vStatus;

	@Column(name = "createdBy", length = 25)
	private String createdBy;

	@Column(name = "lastModifiedBy", length = 25)
	private String lastModifiedBy;


	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@Column(name = "lastupdated")
	private Date lastupdated;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete = false;
	
	

	public VehicleStatus() {
		super();
	}

	public VehicleStatus(final String vStatus) {
		super();
		this.vStatus = vStatus;
	}

	/**
	 * @return the sid
	 */
	public Long getSid() {
		return sid;
	}

	/**
	 * @param sid
	 *            the sid to set
	 */
	public void setSid(Long sid) {
		this.sid = sid;
	}

	/**
	 * @return the vStatus
	 */
	public String getvStatus() {
		return vStatus;
	}

	/**
	 * @param vStatus
	 *            the vStatus to set
	 */
	public void setvStatus(String vStatus) {
		this.vStatus = vStatus;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the status
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public Date getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
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
		result = prime * result + ((vStatus == null) ? 0 : vStatus.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleStatus other = (VehicleStatus) obj;
		if (vStatus == null) {
			if (other.vStatus != null)
				return false;
		} else if (!vStatus.equals(other.vStatus))
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
		builder.append("VehicleStatus [vStatus=");
		builder.append(vStatus);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append("]");
		return builder.toString();
	}

}