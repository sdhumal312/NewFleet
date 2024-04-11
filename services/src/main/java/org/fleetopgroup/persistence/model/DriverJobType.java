package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "driverJobtype")
public class DriverJobType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driJobId")
	private Integer driJobId;

	@Column(name = "driJobType", unique = false, nullable = false)
	private String driJobType;

	@Column(name = "driJobRemarks", length = 200)
	private String driJobRemarks;

	/** The value for the driJob_delete field */
	@Basic(optional = true)
	@Column(name = "driJob_deleteable", nullable = true)
	private boolean driJob_deleteable = true;

	/** The value for the driJob_delete field */
	@Basic(optional = true)
	@Column(name = "driJob_editable", nullable = true)
	private boolean driJob_editable = true;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	/*@Column(name = "createdBy", nullable = true)
	private String createdBy;*/
	
	@Column(name = "createdById")
	private Long createdById;
	
	@Column(name = "createdOn", nullable = true)
	private Timestamp createdOn;
	
	/*@Column(name = "lastModifiedBy", nullable = true)
	private String lastModifiedBy;*/
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	
	

	public DriverJobType() {
		super();
	}

	public DriverJobType(String driJobType, String driJobRemarks) {
		super();
		this.driJobType = driJobType;
		this.driJobRemarks = driJobRemarks;
	}

	/**
	 * @return the driJobId
	 */
	public Integer getDriJobId() {
		return driJobId;
	}

	/**
	 * @param driJobId
	 *            the driJobId to set
	 */
	public void setDriJobId(Integer driJobId) {
		this.driJobId = driJobId;
	}

	/**
	 * @return the driJobType
	 */
	public String getDriJobType() {
		return driJobType;
	}

	/**
	 * @param driJobType
	 *            the driJobType to set
	 */
	public void setDriJobType(String driJobType) {
		this.driJobType = driJobType;
	}

	/**
	 * @return the driJobRemarks
	 */
	public String getDriJobRemarks() {
		return driJobRemarks;
	}

	/**
	 * @param driJobRemarks
	 *            the driJobRemarks to set
	 */
	public void setDriJobRemarks(String driJobRemarks) {
		this.driJobRemarks = driJobRemarks;
	}

	/**
	 * @return the driJob_deleteable
	 */
	public boolean isDriJob_deleteable() {
		return driJob_deleteable;
	}

	/**
	 * @param driJob_deleteable
	 *            the driJob_deleteable to set
	 */
	public void setDriJob_deleteable(boolean driJob_deleteable) {
		this.driJob_deleteable = driJob_deleteable;
	}

	/**
	 * @return the driJob_editable
	 */
	public boolean isDriJob_editable() {
		return driJob_editable;
	}

	/**
	 * @param driJob_editable
	 *            the driJob_editable to set
	 */
	public void setDriJob_editable(boolean driJob_editable) {
		this.driJob_editable = driJob_editable;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/*public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}*/

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	/*public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}*/

	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driJobType == null) ? 0 : driJobType.hashCode());
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
		DriverJobType other = (DriverJobType) obj;
		if (driJobType == null) {
			if (other.driJobType != null)
				return false;
		} else if (!driJobType.equals(other.driJobType))
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
		builder.append("DriverJobType [driJobId=");
		builder.append(driJobId);
		builder.append(", driJobType=");
		builder.append(driJobType);
		builder.append(", driJobRemarks=");
		builder.append(driJobRemarks);
		builder.append(", driJob_deleteable=");
		builder.append(driJob_deleteable);
		builder.append(", driJob_editable=");
		builder.append(driJob_editable);
		builder.append(", company_id=");
		builder.append(companyId);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", lastModifiedOn=");
		builder.append(lastModifiedOn);
		builder.append("]");
		return builder.toString();
	}

}