package org.fleetopgroup.persistence.model;
/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="driverdoctype")
public class DriverDocType implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dri_id")
	private Long dri_id;
	
	@Column(name="dri_DocType", unique = false, nullable = false)
	private String dri_DocType;
	
	@Column(name= "company_Id", nullable = false)
	Integer company_Id;
	
	/*@Column(name = "createdBy", nullable = true)
	private String createdBy;
	*/
	@Column(name = "createdOn", nullable = true)
	private Timestamp createdOn;
	
	/*@Column(name = "lastModifiedBy", nullable = true)
	private String lastModifiedBy;*/
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "createdById", nullable = true, updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById", nullable = true)
	private Long lastModifiedById;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	
	
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public DriverDocType() {
		super();
	}

	public DriverDocType(String dri_DocType) {
		super();
		this.dri_DocType = dri_DocType;
	}

	public DriverDocType(Long dri_id, String dri_DocType) {
		super();
		this.dri_id = dri_id;
		this.dri_DocType = dri_DocType;
	}

	/**
	 * @return the dri_id
	 */
	public Long getDri_id() {
		return dri_id;
	}

	/**
	 * @param dri_id the dri_id to set
	 */
	public void setDri_id(Long dri_id) {
		this.dri_id = dri_id;
	}

	/**
	 * @return the dri_DocType
	 */
	public String getDri_DocType() {
		return dri_DocType;
	}

	/**
	 * @param dri_DocType the dri_DocType to set
	 */
	public void setDri_DocType(String dri_DocType) {
		this.dri_DocType = dri_DocType;
	}

	public Integer getCompany_Id() {
		return company_Id;
	}

	public void setCompany_Id(Integer company_Id) {
		this.company_Id = company_Id;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dri_DocType == null) ? 0 : dri_DocType.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		DriverDocType other = (DriverDocType) obj;
		if (dri_DocType == null) {
			if (other.dri_DocType != null)
				return false;
		} else if (!dri_DocType.equals(other.dri_DocType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverDocType [dri_DocType=");
		builder.append(dri_DocType);
		builder.append(", company_id=");
		builder.append(company_Id);
		builder.append(", createdBy=");
		builder.append(createdById);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedById);
		builder.append(", lastModifiedOn=");
		builder.append(lastModifiedOn);
		builder.append("]");
		return builder.toString();
	}
	
}