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
@Table(name = "VehicleModelTyreLayout")
public class VehicleModelTyreLayout implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleModelTyreLayoutId")
	private Long vehicleModelTyreLayoutId;
	
	@Column(name = "vehicleModelId")
	private Long vehicleModelId;

	@Column(name = "numberOfAxle")
	private Integer numberOfAxle;
	
	@Column(name = "frontTyreModelId")
	private Integer frontTyreModelId;
	
	@Column(name = "rearTyreModelId")
	private Integer rearTyreModelId;
	
	@Column(name = "isSpareTyrePresent")
	private boolean isSpareTyrePresent;
	
	@Column(name = "spareTyreModelId")
	private Integer spareTyreModelId;
	
	@Column(name = "dualTyrePresentAxle")
	private String dualTyrePresentAxle;
	
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

	public VehicleModelTyreLayout() {
		super();
		
	}

	public VehicleModelTyreLayout(Long vehicleModelTyreLayoutId, Long vehicleModelId, Integer numberOfAxle,
			Integer frontTyreModelId, Integer rearTyreModelId, boolean isSpareTyrePresent, Integer spareTyreModelId,
			Long createdById, Long lastUpdatedById, Date creationOn, Date lastUpdatedOn, Integer companyId,
			boolean markForDelete) {
		super();
		this.vehicleModelTyreLayoutId = vehicleModelTyreLayoutId;
		this.vehicleModelId = vehicleModelId;
		this.numberOfAxle = numberOfAxle;
		this.frontTyreModelId = frontTyreModelId;
		this.rearTyreModelId = rearTyreModelId;
		this.isSpareTyrePresent = isSpareTyrePresent;
		this.spareTyreModelId = spareTyreModelId;
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
		result = prime * result + ((frontTyreModelId == null) ? 0 : frontTyreModelId.hashCode());
		result = prime * result + (isSpareTyrePresent ? 1231 : 1237);
		result = prime * result + ((lastUpdatedById == null) ? 0 : lastUpdatedById.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((numberOfAxle == null) ? 0 : numberOfAxle.hashCode());
		result = prime * result + ((rearTyreModelId == null) ? 0 : rearTyreModelId.hashCode());
		result = prime * result + ((spareTyreModelId == null) ? 0 : spareTyreModelId.hashCode());
		result = prime * result + ((vehicleModelId == null) ? 0 : vehicleModelId.hashCode());
		result = prime * result + ((vehicleModelTyreLayoutId == null) ? 0 : vehicleModelTyreLayoutId.hashCode());
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
		VehicleModelTyreLayout other = (VehicleModelTyreLayout) obj;
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
		if (frontTyreModelId == null) {
			if (other.frontTyreModelId != null)
				return false;
		} else if (!frontTyreModelId.equals(other.frontTyreModelId))
			return false;
		if (isSpareTyrePresent != other.isSpareTyrePresent)
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
		if (numberOfAxle == null) {
			if (other.numberOfAxle != null)
				return false;
		} else if (!numberOfAxle.equals(other.numberOfAxle))
			return false;
		if (rearTyreModelId == null) {
			if (other.rearTyreModelId != null)
				return false;
		} else if (!rearTyreModelId.equals(other.rearTyreModelId))
			return false;
		if (spareTyreModelId == null) {
			if (other.spareTyreModelId != null)
				return false;
		} else if (!spareTyreModelId.equals(other.spareTyreModelId))
			return false;
		if (vehicleModelId == null) {
			if (other.vehicleModelId != null)
				return false;
		} else if (!vehicleModelId.equals(other.vehicleModelId))
			return false;
		if (vehicleModelTyreLayoutId == null) {
			if (other.vehicleModelTyreLayoutId != null)
				return false;
		} else if (!vehicleModelTyreLayoutId.equals(other.vehicleModelTyreLayoutId))
			return false;
		return true;
	}

	public Long getVehicleModelTyreLayoutId() {
		return vehicleModelTyreLayoutId;
	}

	public void setVehicleModelTyreLayoutId(Long vehicleModelTyreLayoutId) {
		this.vehicleModelTyreLayoutId = vehicleModelTyreLayoutId;
	}

	public Long getVehicleModelId() {
		return vehicleModelId;
	}

	public void setVehicleModelId(Long vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}

	public Integer getNumberOfAxle() {
		return numberOfAxle;
	}

	public void setNumberOfAxle(Integer numberOfAxle) {
		this.numberOfAxle = numberOfAxle;
	}

	public Integer getFrontTyreModelId() {
		return frontTyreModelId;
	}

	public void setFrontTyreModelId(Integer frontTyreModelId) {
		this.frontTyreModelId = frontTyreModelId;
	}

	public Integer getRearTyreModelId() {
		return rearTyreModelId;
	}

	public void setRearTyreModelId(Integer rearTyreModelId) {
		this.rearTyreModelId = rearTyreModelId;
	}

	public boolean isSpareTyrePresent() {
		return isSpareTyrePresent;
	}

	public void setSpareTyrePresent(boolean isSpareTyrePresent) {
		this.isSpareTyrePresent = isSpareTyrePresent;
	}

	public Integer getSpareTyreModelId() {
		return spareTyreModelId;
	}

	public void setSpareTyreModelId(Integer spareTyreModelId) {
		this.spareTyreModelId = spareTyreModelId;
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
	
	public String getDualTyrePresentAxle() {
		return dualTyrePresentAxle;
	}

	public void setDualTyrePresentAxle(String dualTyrePresentAxle) {
		this.dualTyrePresentAxle = dualTyrePresentAxle;
	}

	@Override
	public String toString() {
		return "VehicleModelTyreLayout [vehicleModelTyreLayoutId=" + vehicleModelTyreLayoutId + ", vehicleModelId="
				+ vehicleModelId + ", numberOfAxle=" + numberOfAxle + ", frontTyreModelId=" + frontTyreModelId
				+ ", rearTyreModelId=" + rearTyreModelId + ", isSpareTyrePresent=" + isSpareTyrePresent
				+ ", spareTyreModelId=" + spareTyreModelId + ", dualTyrePresentAxle=" + dualTyrePresentAxle
				+ ", createdById=" + createdById + ", lastUpdatedById=" + lastUpdatedById + ", creationOn=" + creationOn
				+ ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ "]";
	}

	
	

}