package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class VehicleAccidentTypeDetailsDto {
	
	private Long VehicleAccidentTypeDetailsId;

	private Long accidentDetailsId;
	
	private short VehicleAccidentTypeId;
	
	private String VehicleAccidentTypeName;
	
	private Double approxOwnDamgeCost;
	
	private Double approxTPDamgeCost;
	
	private String	natureOfOwnDamage;

	private String	natureOfTPDamage;
	
	private String	description;

	private Long createdById;
	
	private String createdBy;
	
	private Long lastModifiedById;
	
	private String lastModifiedBy;
	
	private Date creationOn;
	
	private String creationDate;
	
	private Date lastUpdatedOn;
	
	private String lastUpdatedDate;
	
	private Integer companyId;
	
	private boolean markForDelete;

	public VehicleAccidentTypeDetailsDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getVehicleAccidentTypeDetailsId() {
		return VehicleAccidentTypeDetailsId;
	}

	public void setVehicleAccidentTypeDetailsId(Long vehicleAccidentTypeDetailsId) {
		VehicleAccidentTypeDetailsId = vehicleAccidentTypeDetailsId;
	}

	public Long getAccidentDetailsId() {
		return accidentDetailsId;
	}

	public void setAccidentDetailsId(Long accidentDetailsId) {
		this.accidentDetailsId = accidentDetailsId;
	}

	public short getVehicleAccidentTypeId() {
		return VehicleAccidentTypeId;
	}

	public void setVehicleAccidentTypeId(short vehicleAccidentTypeId) {
		VehicleAccidentTypeId = vehicleAccidentTypeId;
	}

	public String getVehicleAccidentTypeName() {
		return VehicleAccidentTypeName;
	}

	public void setVehicleAccidentTypeName(String vehicleAccidentTypeName) {
		VehicleAccidentTypeName = vehicleAccidentTypeName;
	}

	public Double getApproxOwnDamgeCost() {
		return approxOwnDamgeCost;
	}

	public void setApproxOwnDamgeCost(Double approxOwnDamgeCost) {
		this.approxOwnDamgeCost = approxOwnDamgeCost;
	}

	public Double getApproxTPDamgeCost() {
		return approxTPDamgeCost;
	}

	public void setApproxTPDamgeCost(Double approxTPDamgeCost) {
		this.approxTPDamgeCost = approxTPDamgeCost;
	}

	public String getNatureOfOwnDamage() {
		return natureOfOwnDamage;
	}

	public void setNatureOfOwnDamage(String natureOfOwnDamage) {
		this.natureOfOwnDamage = natureOfOwnDamage;
	}

	public String getNatureOfTPDamage() {
		return natureOfTPDamage;
	}

	public void setNatureOfTPDamage(String natureOfTPDamage) {
		this.natureOfTPDamage = natureOfTPDamage;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getCreationOn() {
		return creationOn;
	}

	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
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
