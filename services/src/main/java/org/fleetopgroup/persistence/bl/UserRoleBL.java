package org.fleetopgroup.persistence.bl;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;

import java.util.List;


import org.fleetopgroup.persistence.model.VehicleStatus;

public class UserRoleBL {
	public UserRoleBL() {
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
