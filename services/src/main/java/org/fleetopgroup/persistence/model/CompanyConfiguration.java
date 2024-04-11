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
@Table(name = "CompanyConfiguration")
public class CompanyConfiguration implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long companyConfigurationId;
	
	private String name;
	
	private String description;
	
	private Integer moduleId;
	
	private Date addedOn;
	
	private Integer companyId;
	
	private short type;
	
	private String value;
	
	@Column(nullable =  false)
	private boolean markForDelete;

	public Long getCompanyConfigurationId() {
		return companyConfigurationId;
	}

	public void setCompanyConfigurationId(Long companyConfigurationId) {
		this.companyConfigurationId = companyConfigurationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public Date getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		return "CompanyConfiguration [companyConfigurationId=" + companyConfigurationId + ", name=" + name
				+ ", description=" + description + ", moduleId=" + moduleId + ", addedOn=" + addedOn + ", companyId="
				+ companyId + ", type=" + type + ", value=" + value + ", markForDelete=" + markForDelete + "]";
	}
	
	

}
