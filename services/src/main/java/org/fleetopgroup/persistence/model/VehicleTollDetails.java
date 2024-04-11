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
@Table(name = "VehicleTollDetails")
public class VehicleTollDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleTollDetailsId")
	private Long	vehicleTollDetailsId;
	
	@Column(name = "apiKey")
	private String	apiKey;
	
	@Column(name = "apiClientId")
	private Long	apiClientId;
	
	@Column(name = "customerId")
	private String	customerId;
	
	@Column(name = "walletId")
	private String	walletId;
	
	@Column(name = "createdById", updatable = false)
	private Long	createdById;
	
	@Column(name = "lastUpdatedById")
	private Long	lastUpdatedById;
	
	@Column(name = "createdOn", updatable = false)
	private Date	createdOn;
	
	@Column(name = "lastUpdatedOn")
	private Date	lastUpdatedOn;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "description")
	private String	description;
	
	@Column(name = "markForDelete")
	private boolean	markForDelete;

	public Long getVehicleTollDetailsId() {
		return vehicleTollDetailsId;
	}

	public void setVehicleTollDetailsId(Long vehicleTollDetailsId) {
		this.vehicleTollDetailsId = vehicleTollDetailsId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public Long getApiClientId() {
		return apiClientId;
	}

	public void setApiClientId(Long apiClientId) {
		this.apiClientId = apiClientId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}

	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}
}
