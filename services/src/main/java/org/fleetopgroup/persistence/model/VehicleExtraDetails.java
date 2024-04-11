package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="VehicleExtraDetails")
public class VehicleExtraDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleExtraDetailsId")
	private Long	vehicleExtraDetailsId;
	
	@Column(name = "vid")
	private Integer vid;
	
	@Column(name = "busId")
	private long	busId;
	
	@Column(name = "deviceId")
	private long	deviceId;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "vehicleTollDetailsId")
	private Long	vehicleTollDetailsId;
	
	@Column(name = "vehicleGPSCredentialId")
	private Long	vehicleGPSCredentialId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	

	public VehicleExtraDetails() {
		super();
		
	}

	public VehicleExtraDetails(Long vehicleExtraDetailsId, Integer vid, long busId, long deviceId, Integer companyId,
			boolean markForDelete) {
		super();
		this.vehicleExtraDetailsId = vehicleExtraDetailsId;
		this.vid = vid;
		this.busId = busId;
		this.deviceId = deviceId;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	
	public Long getVehicleExtraDetailsId() {
		return vehicleExtraDetailsId;
	}

	public void setVehicleExtraDetailsId(Long vehicleExtraDetailsId) {
		this.vehicleExtraDetailsId = vehicleExtraDetailsId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public long getBusId() {
		return busId;
	}

	public void setBusId(long busId) {
		this.busId = busId;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
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


	public Long getVehicleTollDetailsId() {
		return vehicleTollDetailsId;
	}

	public void setVehicleTollDetailsId(Long vehicleTollDetailsId) {
		this.vehicleTollDetailsId = vehicleTollDetailsId;
	}

	public Long getVehicleGPSCredentialId() {
		return vehicleGPSCredentialId;
	}

	public void setVehicleGPSCredentialId(Long vehicleGPSCredentialId) {
		this.vehicleGPSCredentialId = vehicleGPSCredentialId;
	}

	@Override
	public String toString() {
		return "VehicleExtraDetails [vehicleExtraDetailsId=" + vehicleExtraDetailsId + ", vid=" + vid + ", busId="
				+ busId + ", deviceId=" + deviceId + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ "]";
	}
	
}	