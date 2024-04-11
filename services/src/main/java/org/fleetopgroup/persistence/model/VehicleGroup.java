package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
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
@Table(name = "vehiclegroup")
public class VehicleGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gid")
	private Long gid;

	@Column(name = "vGroup", unique = false, nullable = false, length = 25)
	private String vGroup;

	@Column(name = "createdBy", length = 25)
	private String createdBy;

	@Column(name = "createdById")
	private Long createdById;

	@Column(name = "lastModifiedBy", length = 25)
	private String lastModifiedBy;

	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@Column(name = "lastupdated", nullable = false, updatable = false)
	private Date lastupdated;

	@Column(name = "company_Id", nullable = false)
	Integer company_Id;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	public VehicleGroup() {
		super();
	}

	public VehicleGroup(String vGroup) {
		super();
		this.vGroup = vGroup;
	}

	/**
	 * @return the gid
	 */
	public Long getGid() {
		return gid;
	}

	/**
	 * @param gid
	 *            the gid to set
	 */
	public void setGid(Long gid) {
		this.gid = gid;
	}

	/**
	 * @return the vGroup
	 */
	public String getvGroup() {
		return vGroup;
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

	/**
	 * @param vGroup
	 *            the vGroup to set
	 */
	public void setvGroup(String vGroup) {
		this.vGroup = vGroup;
	}

	public Integer getCompany_Id() {
		return company_Id;
	}

	public void setCompany_Id(Integer company_Id) {
		this.company_Id = company_Id;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
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
		result = prime * result + ((vGroup == null) ? 0 : vGroup.hashCode());
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
		VehicleGroup other = (VehicleGroup) obj;
		if (vGroup == null) {
			if (other.vGroup != null)
				return false;
		} else if (!vGroup.equals(other.vGroup))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VehicleGroup [gid=" + gid + ", vGroup=" + vGroup + ", createdBy=" + createdBy + ", createdById="
				+ createdById + ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedById=" + lastModifiedById
				+ ", created=" + created + ", lastupdated=" + lastupdated + ", company_Id=" + company_Id
				+ ", markForDelete=" + markForDelete + "]";
	}

	
}