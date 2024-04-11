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
@Table(name = "VehicleServiceProgram")
public class VehicleServiceProgram  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleServiceProgramId")
	private Long	vehicleServiceProgramId;
	
	@Column(name = "programName")
	private String	programName;
	
	@Column(name = "description", length = 500)
	private String	description;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "createdById", nullable = false, updatable = false)
	private Long	createdById;
	
	@Column(name = "lastModifiedById")
	private Long	lastModifiedById;
	
	@Column(name = "createdOn", nullable = false, updatable = false)
	private Date	createdOn;
	
	@Column(name = "lastModifiedOn")
	private Date	lastModifiedOn;
	
	@Column(name = "markForDelete")
	private boolean	markForDelete;
	
	@Column(name = "isVendorProgram")
	private boolean	isVendorProgram;

	public Long getVehicleServiceProgramId() {
		return vehicleServiceProgramId;
	}

	public void setVehicleServiceProgramId(Long vehicleServiceProgramId) {
		this.vehicleServiceProgramId = vehicleServiceProgramId;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public boolean isVendorProgram() {
		return isVendorProgram;
	}

	public void setVendorProgram(boolean isVendorProgram) {
		this.isVendorProgram = isVendorProgram;
	}
	
	
}
