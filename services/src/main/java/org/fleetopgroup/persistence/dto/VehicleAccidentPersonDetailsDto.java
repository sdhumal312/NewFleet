package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class VehicleAccidentPersonDetailsDto {
	
	private Long vehicleAccidentPersonDetailsId;
	
	private Long accidentDetailsId;
	
	private short vehicleAccidentPersonTypeId;
	
	private String vehicleAccidentPersonType;
	
	private short vehicleAccidentPersonStatusId;
	
	private String vehicleAccidentPersonStatus;
	
	private String	name;
	
	private Integer	age;
	
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

	public VehicleAccidentPersonDetailsDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getVehicleAccidentPersonDetailsId() {
		return vehicleAccidentPersonDetailsId;
	}

	public void setVehicleAccidentPersonDetailsId(Long vehicleAccidentPersonDetailsId) {
		this.vehicleAccidentPersonDetailsId = vehicleAccidentPersonDetailsId;
	}

	public Long getAccidentDetailsId() {
		return accidentDetailsId;
	}

	public void setAccidentDetailsId(Long accidentDetailsId) {
		this.accidentDetailsId = accidentDetailsId;
	}

	public short getVehicleAccidentPersonTypeId() {
		return vehicleAccidentPersonTypeId;
	}

	public void setVehicleAccidentPersonTypeId(short vehicleAccidentPersonTypeId) {
		this.vehicleAccidentPersonTypeId = vehicleAccidentPersonTypeId;
	}

	public String getVehicleAccidentPersonType() {
		return vehicleAccidentPersonType;
	}

	public void setVehicleAccidentPersonType(String vehicleAccidentPersonType) {
		this.vehicleAccidentPersonType = vehicleAccidentPersonType;
	}

	public short getVehicleAccidentPersonStatusId() {
		return vehicleAccidentPersonStatusId;
	}

	public void setVehicleAccidentPersonStatusId(short vehicleAccidentPersonStatusId) {
		this.vehicleAccidentPersonStatusId = vehicleAccidentPersonStatusId;
	}

	public String getVehicleAccidentPersonStatus() {
		return vehicleAccidentPersonStatus;
	}

	public void setVehicleAccidentPersonStatus(String vehicleAccidentPersonStatus) {
		this.vehicleAccidentPersonStatus = vehicleAccidentPersonStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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
