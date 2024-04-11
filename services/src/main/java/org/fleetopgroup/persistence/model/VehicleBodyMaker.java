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
@Table(name="vehiclebodymaker")
public class VehicleBodyMaker implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleBodyMakerId")
	private Integer vehicleBodyMakerId;

	@Column(name = "vehicleBodyMakerName", unique = false, nullable = false, length = 150)
	private String vehicleBodyMakerName;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdById", nullable = false)
	private Long createdById;
	
	@Column(name = "createdOn", nullable = false)
	private Date createdOn;

	@Column(name = "lastModifiedById", nullable = true)
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Date lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public Integer getVehicleBodyMakerId() {
		return vehicleBodyMakerId;
	}

	public void setVehicleBodyMakerId(Integer vehicleBodyMakerId) {
		this.vehicleBodyMakerId = vehicleBodyMakerId;
	}

	public String getVehicleBodyMakerName() {
		return vehicleBodyMakerName;
	}

	public void setVehicleBodyMakerName(String vehicleBodyMakerName) {
		this.vehicleBodyMakerName = vehicleBodyMakerName;
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
