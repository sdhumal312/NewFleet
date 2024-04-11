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
@Table(name = "renewalsubtype")
public class RenewalSubType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "renewal_Subid")
	private Integer renewal_Subid;
	
	@Column(name = "renewal_SubType", unique = false, nullable = true, length = 25)
	private String renewal_SubType;

	/*@Column(name = "renewal_Type", nullable = true, length = 25)
	private String renewal_Type;*/
	
	@Column(name = "renewal_id", nullable = false)
	private Integer renewal_id;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	

	/*@Column(name = "createdBy", nullable = true)
	private String createdBy;*/
	
	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;
	
	@Column(name = "createdOn", nullable = true)
	private Timestamp createdOn;
	
	/*@Column(name = "lastModifiedBy", nullable = true)
	private String lastModifiedBy;*/
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	@Column(name = "isMandatory", nullable = false)
	boolean isMandatory;
	
	public RenewalSubType() {
		super();
	}

	public RenewalSubType(Integer renewal_Type, String renewal_SubType) {
		super();
		this.renewal_id = renewal_Type;
		this.renewal_SubType = renewal_SubType;
		
	}

	/*public String getRenewal_Type() {
		return renewal_Type;
	}

	public void setRenewal_Type(String renewal_Type) {
		this.renewal_Type = renewal_Type;
	}
*/
	public String getRenewal_SubType() {
		return renewal_SubType;
	}

	public void setRenewal_SubType(String renewal_SubType) {
		this.renewal_SubType = renewal_SubType;
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

/*	public String getLastModifiedBy() {
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

	public Integer getRenewal_Subid() {
		return renewal_Subid;
	}

	public void setRenewal_Subid(Integer renewal_Subid) {
		this.renewal_Subid = renewal_Subid;
	}
	
	public Integer getRenewal_id() {
		return renewal_id;
	}

	public void setRenewal_id(Integer renewal_id) {
		this.renewal_id = renewal_id;
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

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((renewal_Subid == null) ? 0 : renewal_Subid.hashCode());
		result = prime * result + ((renewal_id == null) ? 0 : renewal_id.hashCode());
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
		RenewalSubType other = (RenewalSubType) obj;
		if (renewal_Subid == null) {
			if (other.renewal_Subid != null)
				return false;
		} else if (!renewal_Subid.equals(other.renewal_Subid))
			return false;
		if (renewal_id == null) {
			if (other.renewal_id != null)
				return false;
		} else if (!renewal_id.equals(other.renewal_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RenewalSubType [renewal_id=");
		builder.append(renewal_id);
		builder.append(", renewal_SubType=");
		builder.append(renewal_SubType);
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
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", isMandatory=");
		builder.append(isMandatory);
		builder.append("]");
		return builder.toString();
	}

}