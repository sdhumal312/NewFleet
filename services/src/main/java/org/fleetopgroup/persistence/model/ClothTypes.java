package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="ClothTypes")
public class ClothTypes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "clothTypesId")
	private Long	clothTypesId;
	
	@Column(name = "clothTypeName")
	private String	clothTypeName;
	
	@Column(name = "description")
	private String	description;
	
	@Column(name = "creationDate")
	private Date	creationDate;
	
	@Column(name = "lastUpdatedOn")
	private Date	lastUpdatedOn;
	
	@Column(name = "createdById")
	private Long	createdById;
	
	@Column(name = "lastUpdatedBy")
	private Long	lastUpdatedBy;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public String getClothTypeName() {
		return clothTypeName;
	}

	public String getDescription() {
		return description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public void setClothTypeName(String clothTypeName) {
		this.clothTypeName = clothTypeName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clothTypeName == null) ? 0 : clothTypeName.hashCode());
		result = prime * result + ((clothTypesId == null) ? 0 : clothTypesId.hashCode());
		result = prime * result + ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
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
		ClothTypes other = (ClothTypes) obj;
		if (clothTypeName == null) {
			if (other.clothTypeName != null)
				return false;
		} else if (!clothTypeName.equals(other.clothTypeName))
			return false;
		if (clothTypesId == null) {
			if (other.clothTypesId != null)
				return false;
		} else if (!clothTypesId.equals(other.clothTypesId))
			return false;
		if (lastUpdatedBy == null) {
			if (other.lastUpdatedBy != null)
				return false;
		} else if (!lastUpdatedBy.equals(other.lastUpdatedBy))
			return false;
		if (lastUpdatedOn == null) {
			if (other.lastUpdatedOn != null)
				return false;
		} else if (!lastUpdatedOn.equals(other.lastUpdatedOn))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClothTypes [clothTypesId=");
		builder.append(clothTypesId);
		builder.append(", clothTypeName=");
		builder.append(clothTypeName);
		builder.append(", description=");
		builder.append(description);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append(", lastUpdatedOn=");
		builder.append(lastUpdatedOn);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastUpdatedBy=");
		builder.append(lastUpdatedBy);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append("]");
		return builder.toString();
	}
	
	

}
