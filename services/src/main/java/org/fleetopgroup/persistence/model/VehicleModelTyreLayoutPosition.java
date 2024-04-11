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
@Table(name = "VehicleModelTyreLayoutPosition")
public class VehicleModelTyreLayoutPosition implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleModelTyreLayoutPositionId")
	private Long vehicleModelTyreLayoutPositionId;
	
	@Column(name = "vehicleModelTyreLayoutId")
	private Long vehicleModelTyreLayoutId;

	@Column(name = "axle")
	private Integer axle;
	
	@Column(name = "position")
	private String position;
	
	@Column(name = "tyreModelId")
	private Integer tyreModelId;
	
	@Column(name = "createdById")
	private Long createdById;

	@Column(name = "lastUpdatedById")
	private Long lastUpdatedById;

	@Column(name = "creationOn")
	private Date creationOn;

	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public VehicleModelTyreLayoutPosition() {
		super();
		
	}

	public VehicleModelTyreLayoutPosition(Long vehicleModelTyreLayoutPositionId, Long vehicleModelTyreLayoutId,
			String position, Integer tyreModelId, Long createdById, Long lastUpdatedById, Date creationOn,
			Date lastUpdatedOn, Integer companyId, boolean markForDelete) {
		super();
		this.vehicleModelTyreLayoutPositionId = vehicleModelTyreLayoutPositionId;
		this.vehicleModelTyreLayoutId = vehicleModelTyreLayoutId;
		this.position = position;
		this.tyreModelId = tyreModelId;
		this.createdById = createdById;
		this.lastUpdatedById = lastUpdatedById;
		this.creationOn = creationOn;
		this.lastUpdatedOn = lastUpdatedOn;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((creationOn == null) ? 0 : creationOn.hashCode());
		result = prime * result + ((lastUpdatedById == null) ? 0 : lastUpdatedById.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((tyreModelId == null) ? 0 : tyreModelId.hashCode());
		result = prime * result + ((vehicleModelTyreLayoutId == null) ? 0 : vehicleModelTyreLayoutId.hashCode());
		result = prime * result
				+ ((vehicleModelTyreLayoutPositionId == null) ? 0 : vehicleModelTyreLayoutPositionId.hashCode());
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
		VehicleModelTyreLayoutPosition other = (VehicleModelTyreLayoutPosition) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (createdById == null) {
			if (other.createdById != null)
				return false;
		} else if (!createdById.equals(other.createdById))
			return false;
		if (creationOn == null) {
			if (other.creationOn != null)
				return false;
		} else if (!creationOn.equals(other.creationOn))
			return false;
		if (lastUpdatedById == null) {
			if (other.lastUpdatedById != null)
				return false;
		} else if (!lastUpdatedById.equals(other.lastUpdatedById))
			return false;
		if (lastUpdatedOn == null) {
			if (other.lastUpdatedOn != null)
				return false;
		} else if (!lastUpdatedOn.equals(other.lastUpdatedOn))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (tyreModelId == null) {
			if (other.tyreModelId != null)
				return false;
		} else if (!tyreModelId.equals(other.tyreModelId))
			return false;
		if (vehicleModelTyreLayoutId == null) {
			if (other.vehicleModelTyreLayoutId != null)
				return false;
		} else if (!vehicleModelTyreLayoutId.equals(other.vehicleModelTyreLayoutId))
			return false;
		if (vehicleModelTyreLayoutPositionId == null) {
			if (other.vehicleModelTyreLayoutPositionId != null)
				return false;
		} else if (!vehicleModelTyreLayoutPositionId.equals(other.vehicleModelTyreLayoutPositionId))
			return false;
		return true;
	}

	public Long getVehicleModelTyreLayoutPositionId() {
		return vehicleModelTyreLayoutPositionId;
	}

	public void setVehicleModelTyreLayoutPositionId(Long vehicleModelTyreLayoutPositionId) {
		this.vehicleModelTyreLayoutPositionId = vehicleModelTyreLayoutPositionId;
	}

	public Long getVehicleModelTyreLayoutId() {
		return vehicleModelTyreLayoutId;
	}

	public void setVehicleModelTyreLayoutId(Long vehicleModelTyreLayoutId) {
		this.vehicleModelTyreLayoutId = vehicleModelTyreLayoutId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getTyreModelId() {
		return tyreModelId;
	}

	public void setTyreModelId(Integer tyreModelId) {
		this.tyreModelId = tyreModelId;
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

	public Date getCreationOn() {
		return creationOn;
	}

	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
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
	
	public Integer getAxle() {
		return axle;
	}

	public void setAxle(Integer axle) {
		this.axle = axle;
	}

	@Override
	public String toString() {
		return "VehicleModelTyreLayoutPosition [vehicleModelTyreLayoutPositionId=" + vehicleModelTyreLayoutPositionId
				+ ", vehicleModelTyreLayoutId=" + vehicleModelTyreLayoutId + ", position=" + position + ", tyreModelId="
				+ tyreModelId + ", createdById=" + createdById + ", lastUpdatedById=" + lastUpdatedById
				+ ", creationOn=" + creationOn + ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + "]";
	}

	

	

}