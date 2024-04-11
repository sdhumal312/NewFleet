package org.fleetopgroup.persistence.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author fleetop
 *
 */

public class VehiclePhoTypeDto {
	
	private Long ptid;
	
	@NotNull
	@Size(min = 1)
	private String vPhoType;
	
	private short vehiclePhotoTypeId;
	
	private String vehiclePhotoTypeName;

	/**
	 * @return the ptid
	 */
	public Long getPtid() {
		return ptid;
	}

	/**
	 * @param ptid the ptid to set
	 */
	public void setPtid(Long ptid) {
		this.ptid = ptid;
	}

	/**
	 * @return the vPhoType
	 */
	public String getvPhoType() {
		return vPhoType;
	}

	/**
	 * @param vPhoType the vPhoType to set
	 */
	public void setvPhoType(String vPhoType) {
		this.vPhoType = vPhoType;
	}
	
	public short getVehiclePhotoTypeId() {
		return vehiclePhotoTypeId;
	}

	public void setVehiclePhotoTypeId(short vehiclePhotoTypeId) {
		this.vehiclePhotoTypeId = vehiclePhotoTypeId;
	}

	public String getVehiclePhotoTypeName() {
		return vehiclePhotoTypeName;
	}

	public void setVehiclePhotoTypeName(String vehiclePhotoTypeName) {
		this.vehiclePhotoTypeName = vehiclePhotoTypeName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehiclePhoTypeDto [vPhoType=");
		builder.append(vPhoType);
		builder.append(", ptid=");
		builder.append(ptid);
		builder.append("]");
		return builder.toString();
	}
	
	
}