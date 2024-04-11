package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleStatusDto;
import org.fleetopgroup.persistence.model.VehicleStatus;
import org.fleetopgroup.persistence.model.VehicleStatusPermission;

public class VehicleStatusBL {
	public VehicleStatusBL() {
	}

	
	// save the VehicleStatus Model
	public VehicleStatus prepareModel(VehicleStatusDto vehicleStatusBean) {
		VehicleStatus status = new VehicleStatus();
		status.setSid(vehicleStatusBean.getSid());
		status.setvStatus(vehicleStatusBean.getvStatus());
		status.setCreatedBy(vehicleStatusBean.getCreatedBy());
		
		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		
		status.setCreated(toDate);
		status.setLastupdated(toDate);
		status.setMarkForDelete(false);
		return status;
	}
	
	public VehicleStatusPermission prepareModelForVehicleStatusPermission(CustomUserDetails userDetails, VehicleStatus vehicleStatus) {
		VehicleStatusPermission status = new VehicleStatusPermission();
		
		status.setCompanyId(userDetails.getCompany_id());
		status.setSid(vehicleStatus.getSid());
		status.setvStatus(vehicleStatus.getvStatus());
		
		status.setCreatedBy(userDetails.getEmail());
		status.setCreated(new Timestamp(System.currentTimeMillis()));
		
		return status;
	}

	// show the List Of Vehicle Status
	public List<VehicleStatus> prepareListofBean(List<VehicleStatus> vehiclestatus) {
		List<VehicleStatus> beans = null;
		if (vehiclestatus != null && !vehiclestatus.isEmpty()) {
			beans = new ArrayList<VehicleStatus>();
			VehicleStatus bean = null;
			for (VehicleStatus vehicelstatus : vehiclestatus) {
				bean = new VehicleStatus();
				bean.setvStatus(vehicelstatus.getvStatus());
				bean.setSid(vehicelstatus.getSid());
				beans.add(bean);
			}
		}
		return beans;
	}

	// edit Show VehicleStatusBean
	public VehicleStatus prepareVehicleStatusBean(VehicleStatus status) {
		VehicleStatus bean = new VehicleStatus();
		bean.setSid(status.getSid());
		bean.setvStatus(status.getvStatus());
		return bean;
	}
}
