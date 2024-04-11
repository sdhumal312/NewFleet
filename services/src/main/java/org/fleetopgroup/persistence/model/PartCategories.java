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
@Table(name = "partcategories")
public class PartCategories implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pcid")
	private Integer pcid;

	@Column(name = "pcName", unique = false, nullable = false, length = 50)
	private String pcName;

	@Column(name = "pcdescription", length = 150)
	private String pcdescription;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	

	@Column(name = "createdBy", nullable = true)
	private String createdBy;

	@Column(name = "createdById", nullable = false)
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
	private boolean markForDelete;
	
	@Column(name = "isOwnPartCategory", nullable = false)
	private boolean isOwnPartCategory;
	
	
	@Column(name ="incPartIssueCateory", nullable=false)
	private boolean incPartIssueCateory;
	
	
	public PartCategories() {
		super();
	}

	public PartCategories(String pcName, String pcdescription) {
		super();
		this.pcName = pcName;
		this.pcdescription = pcdescription;
	}

	/**
	 * @return the pcid
	 */
	public Integer getPcid() {
		return pcid;
	}

	/**
	 * @param pcid
	 *            the pcid to set
	 */
	public void setPcid(Integer pcid) {
		this.pcid = pcid;
	}

	/**
	 * @return the pcName
	 */
	public String getPcName() {
		return pcName;
	}

	/**
	 * @param pcName
	 *            the pcName to set
	 */
	public void setPcName(String pcName) {
		this.pcName = pcName;
	}

	/**
	 * @return the pcdescription
	 */
	public String getPcdescription() {
		return pcdescription;
	}

	/**
	 * @param pcdescription
	 *            the pcdescription to set
	 */
	public void setPcdescription(String pcdescription) {
		this.pcdescription = pcdescription;
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

	public boolean isOwnPartCategory() {
		return isOwnPartCategory;
	}

	public void setOwnPartCategory(boolean isOwnPartCategory) {
		this.isOwnPartCategory = isOwnPartCategory;
	}

	
	public boolean isIncPartIssueCateory() {
		return incPartIssueCateory;
	}

	public void setIncPartIssueCateory(boolean incPartIssueCateory) {
		this.incPartIssueCateory = incPartIssueCateory;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pcName == null) ? 0 : pcName.hashCode());
		result = prime * result + ((pcdescription == null) ? 0 : pcdescription.hashCode());
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
		PartCategories other = (PartCategories) obj;
		if (pcName == null) {
			if (other.pcName != null)
				return false;
		} else if (!pcName.equals(other.pcName))
			return false;
		if (pcdescription == null) {
			if (other.pcdescription != null)
				return false;
		} else if (!pcdescription.equals(other.pcdescription))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PartCategories [pcName=");
		builder.append(pcName);
		builder.append(", pcdescription=");
		builder.append(pcdescription);
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
		builder.append(" , incPartIssueCateory=");
		builder.append(incPartIssueCateory);
		builder.append("]");
		return builder.toString();
	}
}