package org.fleetopgroup.persistence.dto;

import java.io.Serializable;

public class VehicleGroupPermissionDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long 		userId;
	private long 		vehicleGroupId;
	private String		vGroupName;
	
	
	
	public long getUserId() {
		return userId;
	}



	public void setUserId(long userId) {
		this.userId = userId;
	}



	public long getVehicleGroupId() {
		return vehicleGroupId;
	}



	public void setVehicleGroupId(long vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
	}



	public String getvGroupName() {
		return vGroupName;
	}



	public void setvGroupName(String vGroupName) {
		this.vGroupName = vGroupName;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleGroupPermissionDto [userId=");
		builder.append(userId);
		builder.append(", vehicleGroupId=");
		builder.append(vehicleGroupId);
		builder.append(", vGroupName=");
		builder.append(vGroupName);
		
		return builder.toString();
	}

}
