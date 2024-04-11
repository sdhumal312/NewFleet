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
@Table(name = "VehicleGPSCredential")
public class VehicleGPSCredential  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "vehicleGPSCredentialId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	vehicleGPSCredentialId;
	
	@Column(name = "userName")
	private String userName;
	
	@Column(name = "password")
	private String	password;
	
	@Column(name = "description")
	private String	description;
	
	@Column(name = "createdById", updatable = false)
	private Long	createdById;
	
	@Column(name = "lastModifiedById")
	private Long	lastModifiedById;
	
	@Column(name = "createdOn", updatable = false)
	private Date	createdOn;
	
	@Column(name = "lastModifiedOn")
	private Date	lastModifiedOn;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;

	public Long getVehicleGPSCredentialId() {
		return vehicleGPSCredentialId;
	}

	public void setVehicleGPSCredentialId(Long vehicleGPSCredentialId) {
		this.vehicleGPSCredentialId = vehicleGPSCredentialId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	

}
