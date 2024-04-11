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
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleGroupDto;
import org.fleetopgroup.persistence.model.VehicleGroup;
import org.fleetopgroup.persistence.model.VehicleGroupHistory;


public class VehicleGroupBL {

	public VehicleGroupBL() {
	}

	// save the vehicle Group in
	public VehicleGroup prepareModel(VehicleGroupDto vehicleGroupDto) {
		VehicleGroup Group = new VehicleGroup();
		Group.setGid(vehicleGroupDto.getGid());
		Group.setvGroup(vehicleGroupDto.getvGroup());
		
		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		
		Group.setCreated(toDate);
		Group.setLastupdated(toDate);
		Group.setMarkForDelete(false);

		return Group;
	}

	// get Vehicle Group List
	public List<VehicleGroupDto> prepareListofDto(List<VehicleGroup> vehicleGroup) {
		List<VehicleGroupDto> Dtos = null;
		if (vehicleGroup != null && !vehicleGroup.isEmpty()) {
			Dtos = new ArrayList<VehicleGroupDto>();
			VehicleGroupDto Dto = null;
			for (VehicleGroup vehicelGroup : vehicleGroup) {
				Dto = new VehicleGroupDto();
				Dto.setvGroup(vehicelGroup.getvGroup());
				Dto.setGid(vehicelGroup.getGid());
				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	// edit Get Vehicle Group List
	public VehicleGroupDto prepareVehicleGroupDto(VehicleGroup group) {
		VehicleGroupDto Dto = new VehicleGroupDto();
		Dto.setGid(group.getGid());
		Dto.setvGroup(group.getvGroup());
		return Dto;
	}
	
	public HashMap<String,VehicleGroup> getVehicleGroupHM(List<VehicleGroup> vehicleGroupList) throws Exception{
		HashMap<String,VehicleGroup>			vehicleGroupHM			= null;
		try {
				vehicleGroupHM	= new HashMap<>();
				for(VehicleGroup vehicleGroup : vehicleGroupList) {
					vehicleGroupHM.put(vehicleGroup.getvGroup(), vehicleGroup);
				}
			return vehicleGroupHM;
		} catch (Exception e) {
			throw e;
		}
	}

	public VehicleGroupHistory prepareHistoryModel(VehicleGroup vehicleGroup) {
		VehicleGroupHistory vehicleGroupHistory = new VehicleGroupHistory();
		vehicleGroupHistory.setGid(vehicleGroup.getGid());
		vehicleGroupHistory.setvGroup(vehicleGroup.getvGroup());
		vehicleGroupHistory.setLastModifiedById(vehicleGroup.getLastModifiedById());
		vehicleGroupHistory.setLastupdated(vehicleGroup.getLastupdated());
		vehicleGroupHistory.setCompany_Id(vehicleGroup.getCompany_Id());
		vehicleGroupHistory.setMarkForDelete(vehicleGroup.isMarkForDelete());

		return vehicleGroupHistory;
	}

}
