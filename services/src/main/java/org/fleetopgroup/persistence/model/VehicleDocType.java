package org.fleetopgroup.persistence.model;
/**
 * @author fleetop
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
@Table(name="vehicledoctype")
public class VehicleDocType implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dtid")
	private Long dtid;

	@Column(name="vDocType", unique = false, nullable = false,length=25)
	private String vDocType;
	
	@Column(name = "company_Id", nullable = false)
	Integer	company_Id;
	
	@Column(name = "createdBy", nullable = true)
	private String createdBy;

	@Column(name = "createdById", nullable = true)
	private Long createdById;
	
	@Column(name = "createdOn", nullable = true)
	private Timestamp createdOn;
	
	@Column(name = "lastModifiedBy", nullable = true)
	private String lastModifiedBy;

	@Column(name = "lastModifiedById", nullable = true)
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	

	public VehicleDocType() {
		super();
	}

	public VehicleDocType(String vDocType) {
		super();
		this.vDocType = vDocType;
	}

	/**
	 * @return the dtid
	 */
	public Long getDtid() {
		return dtid;
	}

	/**
	 * @param dtid the dtid to set
	 */
	public void setDtid(Long dtid) {
		this.dtid = dtid;
	}

	/**
	 * @return the vDocType
	 */
	public String getvDocType() {
		return vDocType;
	}

	/**
	 * @param vDocType the vDocType to set
	 */
	public void setvDocType(String vDocType) {
		this.vDocType = vDocType;
	}

	public Integer getCompany_Id() {
		return company_Id;
	}

	public void setCompany_Id(Integer company_Id) {
		this.company_Id = company_Id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vDocType == null) ? 0 : vDocType.hashCode());
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
		VehicleDocType other = (VehicleDocType) obj;
		if (vDocType == null) {
			if (other.vDocType != null)
				return false;
		} else if (!vDocType.equals(other.vDocType))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleDocType [vDocType=");
		builder.append(vDocType);
		builder.append(", dtid=");
		builder.append(dtid);
		builder.append(", company_id=");
		builder.append(company_Id);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", lastModifiedOn=");
		builder.append(lastModifiedOn);
		builder.append("]");
		return builder.toString();
	}
	
	
}