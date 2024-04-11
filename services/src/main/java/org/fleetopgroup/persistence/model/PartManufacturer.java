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
@Table(name = "partmanufacturer")
public class PartManufacturer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pmid")
	private Integer pmid;

	@Column(name = "pmName", unique = false, nullable = false, length = 50)
	private String pmName;

	@Column(name = "pmdescription", length = 150)
	private String pmdescription;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdBy", nullable = true)
	private String createdBy;

	@Column(name = "createdById", nullable = false)
	private Long createdById;
	
	@Column(name = "createdOn", nullable = false)
	private Timestamp createdOn;
	
	@Column(name = "lastModifiedBy", nullable = true)
	private String lastModifiedBy;

	@Column(name = "lastModifiedById", nullable = true)
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "isOwnPartManufacturer", nullable = false)
	private boolean isOwnPartManufacturer;
	

	public PartManufacturer() {
		super();
	}

	public PartManufacturer(String pmName, String pmdescription,Integer companyId, String createdBy, Timestamp createdOn, String lastModifiedBy, Timestamp lastModifiedOn) {
		super();
		this.pmName = pmName;
		this.pmdescription = pmdescription;
		this.companyId = companyId;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedOn = lastModifiedOn;
	}

	/**
	 * @return the pmid
	 */
	public Integer getPmid() {
		return pmid;
	}

	/**
	 * @param pmid
	 *            the pmid to set
	 */
	public void setPmid(Integer pmid) {
		this.pmid = pmid;
	}

	/**
	 * @return the pmName
	 */
	public String getPmName() {
		return pmName;
	}

	/**
	 * @param pmName
	 *            the pmName to set
	 */
	public void setPmName(String pmName) {
		this.pmName = pmName;
	}

	/**
	 * @return the pmdescription
	 */
	public String getPmdescription() {
		return pmdescription;
	}

	/**
	 * @param pmdescription
	 *            the pmdescription to set
	 */
	public void setPmdescription(String pmdescription) {
		this.pmdescription = pmdescription;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	public boolean isOwnPartManufacturer() {
		return isOwnPartManufacturer;
	}

	public void setOwnPartManufacturer(boolean isOwnPartManufacturer) {
		this.isOwnPartManufacturer = isOwnPartManufacturer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pmName == null) ? 0 : pmName.hashCode());
		result = prime * result + ((pmdescription == null) ? 0 : pmdescription.hashCode());
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
		PartManufacturer other = (PartManufacturer) obj;
		if (pmName == null) {
			if (other.pmName != null)
				return false;
		} else if (!pmName.equals(other.pmName))
			return false;
		if (pmdescription == null) {
			if (other.pmdescription != null)
				return false;
		} else if (!pmdescription.equals(other.pmdescription))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PartManufacturer [pmName=");
		builder.append(pmName);
		builder.append(", pmdescription=");
		builder.append(pmdescription);
		builder.append(", company_id=");
		builder.append(companyId);
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