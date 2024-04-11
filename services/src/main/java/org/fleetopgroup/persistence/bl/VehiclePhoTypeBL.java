package org.fleetopgroup.persistence.bl;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;

import java.util.List;

import org.fleetopgroup.persistence.dto.VehiclePhoTypeDto;
import org.fleetopgroup.persistence.model.VehiclePhoType;
import org.fleetopgroup.persistence.model.VehiclePhoTypeHistory;


public class VehiclePhoTypeBL {

	public VehiclePhoTypeBL() {
	}

	public VehiclePhoType prepareModel(VehiclePhoTypeDto vehiclePhoTypeDto) {
		VehiclePhoType PhoType = new VehiclePhoType();
		PhoType.setPtid(vehiclePhoTypeDto.getPtid());
		PhoType.setvPhoType(vehiclePhoTypeDto.getvPhoType());
		PhoType.setVehiclePhotoTypeId(vehiclePhoTypeDto.getVehiclePhotoTypeId());
		return PhoType;
	}

	public List<VehiclePhoTypeDto> prepareListofDto(List<VehiclePhoType> vehiclePhoType) {
		List<VehiclePhoTypeDto> Dtos = null;
		if (vehiclePhoType != null && !vehiclePhoType.isEmpty()) {
			Dtos = new ArrayList<VehiclePhoTypeDto>();
			VehiclePhoTypeDto Dto = null;
			for (VehiclePhoType vehicelPhoType : vehiclePhoType) {
				Dto = new VehiclePhoTypeDto();
				Dto.setvPhoType(vehicelPhoType.getvPhoType());
				Dto.setPtid(vehicelPhoType.getPtid());
				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	public VehiclePhoTypeDto prepareVehiclePhoTypeDto(VehiclePhoType PhoType) {
		VehiclePhoTypeDto Dto = new VehiclePhoTypeDto();
		Dto.setPtid(PhoType.getPtid());
		Dto.setvPhoType(PhoType.getvPhoType());
		return Dto;
	}

	public VehiclePhoTypeHistory prepareHistoryModel(VehiclePhoType vehiclePhoType) {
		VehiclePhoTypeHistory  vehiclePhoTypeHistory	= new VehiclePhoTypeHistory();
		
		vehiclePhoTypeHistory.setPtid(vehiclePhoType.getPtid());
		vehiclePhoTypeHistory.setvPhoType(vehiclePhoType.getvPhoType());
		vehiclePhoTypeHistory.setCompany_Id(vehiclePhoType.getCompany_Id());
		vehiclePhoTypeHistory.setLastModifiedById(vehiclePhoType.getLastModifiedById());
		vehiclePhoTypeHistory.setLastModifiedOn(vehiclePhoType.getLastModifiedOn());
		vehiclePhoTypeHistory.setMarkForDelete(vehiclePhoType.isMarkForDelete());
		
		return vehiclePhoTypeHistory;
	}

}
