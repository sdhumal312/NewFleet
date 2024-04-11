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
@Table(name = "VehicleAccidentTypeDetails")
public class VehicleAccidentTypeDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleAccidentTypeDetailsId")
	private Long vehicleAccidentTypeDetailsId;

	@Column(name = "accidentDetailsId")
	private Long accidentDetailsId;
	
	@Column(name = "VehicleAccidentTypeId")
	private short VehicleAccidentTypeId;
	
	@Column(name = "approxOwnDamgeCost")
	private Double approxOwnDamgeCost;
	
	@Column(name = "approxTPDamgeCost")
	private Double approxTPDamgeCost;
	
	@Column(name = "natureOfOwnDamage")
	private String	natureOfOwnDamage;

	@Column(name = "natureOfTPDamage")
	private String	natureOfTPDamage;
	
	@Column(name = "description")
	private String	description;

	@Column(name = "createdById")
	private Long createdById;

	@Column(name = "lastUpdatedBy")
	private Long lastUpdatedBy;

	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public VehicleAccidentTypeDetails() {
		super();
		
	}

	public Long getVehicleAccidentTypeDetailsId() {
		return vehicleAccidentTypeDetailsId;
	}

	public void setVehicleAccidentTypeDetailsId(Long vehicleAccidentTypeDetailsId) {
		this.vehicleAccidentTypeDetailsId = vehicleAccidentTypeDetailsId;
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

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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

	

}