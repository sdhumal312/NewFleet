package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RenewalMailConfiguration")
public class RenewalMailConfiguration implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "configurationId")
	private Long configurationId;
	
	@Column(name = "emailIds")
	private String emailIds;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "createdOn")
	private Timestamp	createdOn;
	
	@Column(name = "lastModifiedOn")
	private Timestamp	lastModifiedOn;
	
	@Column(name = "createdById")
	private Long		createdById;
	
	@Column(name = "lastModifiedById")
	private Long		lastModifiedById;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "emailType")
	private short emailType;
	
	public RenewalMailConfiguration() {
		super();
	}


	public RenewalMailConfiguration(Long configurationId, String emailIds, Integer companyId, Timestamp createdOn,
			Timestamp lastModifiedOn, Long createdById, Long lastModifiedById, boolean markForDelete, short emailType) {
		super();
		this.configurationId = configurationId;
		this.emailIds = emailIds;
		this.companyId = companyId;
		this.createdOn = createdOn;
		this.lastModifiedOn = lastModifiedOn;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.markForDelete = markForDelete;
		this.emailType = emailType;
	}


	public Long getConfigurationId() {
		return configurationId;
	}


	public String getEmailIds() {
		return emailIds;
	}


	public Integer getCompanyId() {
		return companyId;
	}


	public Timestamp getCreatedOn() {
		return createdOn;
	}


	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}


	public Long getCreatedById() {
		return createdById;
	}


	public Long getLastModifiedById() {
		return lastModifiedById;
	}


	public boolean isMarkForDelete() {
		return markForDelete;
	}


	public void setConfigurationId(Long configurationId) {
		this.configurationId = configurationId;
	}


	public void setEmailIds(String emailIds) {
		this.emailIds = emailIds;
	}


	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}


	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}


	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}


	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}


	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	
	public short getEmailType() {
		return emailType;
	}

	public void setEmailType(short emailType) {
		this.emailType = emailType;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((configurationId == null) ? 0 : configurationId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((emailIds == null) ? 0 : emailIds.hashCode());
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
		RenewalMailConfiguration other = (RenewalMailConfiguration) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (configurationId == null) {
			if (other.configurationId != null)
				return false;
		} else if (!configurationId.equals(other.configurationId))
			return false;
		if (createdById == null) {
			if (other.createdById != null)
				return false;
		} else if (!createdById.equals(other.createdById))
			return false;
		if (emailIds == null) {
			if (other.emailIds != null)
				return false;
		} else if (!emailIds.equals(other.emailIds))
			return false;
		return true;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RenewalMailConfiguration [configurationId=");
		builder.append(configurationId);
		builder.append(", emailIds=");
		builder.append(emailIds);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", lastModifiedOn=");
		builder.append(lastModifiedOn);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", emailType=");
		builder.append(emailType);		
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
