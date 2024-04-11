package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VehicleInspectionSheet")
public class VehicleInspectionSheet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleInspectionSheetId")
	private Long		vehicleInspectionSheetId;
	
	@Column(name = "inspectionSheetName")
	private String		inspectionSheetName;
	
	@Column(name = "vehicleGroupId")
	private String		vehicleGroupId;
	
	@Column(name = "noOfVehicleAsigned")
	private int		noOfVehicleAsigned;
	
	@Column(name = "createdById")
	private Long		createdById;
	
	@Column(name = "createdOn")
	private Timestamp	createdOn;
	
	@Column(name = "markForDelete")
	private boolean		markForDelete;
	
	@Column(name = "lastModifiedById")
	private Long		lastModifiedById;
	
	@Column(name = "lastModifiedOn")
	private Timestamp	lastModifiedOn;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name="vehicleTypeId")
	private String vehicleTypeId;
	
	
	public VehicleInspectionSheet() {
		super();
	}
	

	public VehicleInspectionSheet(Long vehicleInspectionSheetId, String inspectionSheetName, String vehicleGroupId,
			Long createdById, Timestamp createdOn, boolean markForDelete) {
		super();
		this.vehicleInspectionSheetId = vehicleInspectionSheetId;
		this.inspectionSheetName = inspectionSheetName;
		this.vehicleGroupId = vehicleGroupId;
		this.createdById = createdById;
		this.createdOn = createdOn;
		this.markForDelete = markForDelete;
	}




	public String getVehicleTypeId() {
		return vehicleTypeId;
	}


	public void setVehicleTypeId(String vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}


	public Long getVehicleInspectionSheetId() {
		return vehicleInspectionSheetId;
	}

	public void setVehicleInspectionSheetId(Long vehicleInspectionSheetId) {
		this.vehicleInspectionSheetId = vehicleInspectionSheetId;
	}

	public String getInspectionSheetName() {
		return inspectionSheetName;
	}

	public void setInspectionSheetName(String inspectionSheetName) {
		this.inspectionSheetName = inspectionSheetName;
	}

	public String getVehicleGroupId() {
		return vehicleGroupId;
	}

	public void setVehicleGroupId(String vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	
	
	

	public int getNoOfVehicleAsigned() {
		return noOfVehicleAsigned;
	}




	public void setNoOfVehicleAsigned(int noOfVehicleAsigned) {
		this.noOfVehicleAsigned = noOfVehicleAsigned;
	}




	public Long getLastModifiedById() {
		return lastModifiedById;
	}


	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}


	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}


	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}


	public Integer getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((inspectionSheetName == null) ? 0 : inspectionSheetName.hashCode());
		result = prime * result + ((vehicleGroupId == null) ? 0 : vehicleGroupId.hashCode());
		result = prime * result + ((vehicleInspectionSheetId == null) ? 0 : vehicleInspectionSheetId.hashCode());
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
		VehicleInspectionSheet other = (VehicleInspectionSheet) obj;
		if (createdById == null) {
			if (other.createdById != null)
				return false;
		} else if (!createdById.equals(other.createdById))
			return false;
		if (inspectionSheetName == null) {
			if (other.inspectionSheetName != null)
				return false;
		} else if (!inspectionSheetName.equals(other.inspectionSheetName))
			return false;
		if (vehicleGroupId == null) {
			if (other.vehicleGroupId != null)
				return false;
		} else if (!vehicleGroupId.equals(other.vehicleGroupId))
			return false;
		if (vehicleInspectionSheetId == null) {
			if (other.vehicleInspectionSheetId != null)
				return false;
		} else if (!vehicleInspectionSheetId.equals(other.vehicleInspectionSheetId))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "VehicleInspectionSheet [vehicleInspectionSheetId=" + vehicleInspectionSheetId + ", inspectionSheetName="
				+ inspectionSheetName + ", vehicleGroupId=" + vehicleGroupId + ", noOfVehicleAsigned="
				+ noOfVehicleAsigned + ", createdById=" + createdById + ", createdOn=" + createdOn + ", markForDelete="
				+ markForDelete + "]";
	}

	
}
