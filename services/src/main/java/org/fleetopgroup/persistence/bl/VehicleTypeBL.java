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

import org.fleetopgroup.persistence.dto.VehicleTypeDto;

import org.fleetopgroup.persistence.model.VehicleType;

public class VehicleTypeBL {

	public VehicleTypeBL() {
	}

	// save the VehicleStatus Model
	public VehicleType prepareModel(VehicleTypeDto vehicletypeDto) {
		VehicleType type = new VehicleType();
		type.setTid(vehicletypeDto.getTid());
		type.setVtype(vehicletypeDto.getVtype());
		type.setCreatedBy(vehicletypeDto.getCreatedBy());
		type.setMaxAllowedOdometer(vehicletypeDto.getMaxAllowedOdometer());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		type.setCreated(toDate);
		type.setLastupdated(toDate);
		type.setMarkForDelete(false);
		return type;
	}

	// Show the List OF Vehicle Type
	public List<VehicleTypeDto> prepareListofDto(List<VehicleType> vehicletypes) {
		List<VehicleTypeDto> Dtos = null;
		if (vehicletypes != null && !vehicletypes.isEmpty()) {
			Dtos = new ArrayList<VehicleTypeDto>();
			VehicleTypeDto Dto = null;
			for (VehicleType vehiceltype : vehicletypes) {
				Dto = new VehicleTypeDto();
				Dto.setVtype(vehiceltype.getVtype());
				Dto.setTid(vehiceltype.getTid());
				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	// edit the vehicel Type GEt the Information
	public VehicleType prepareVehicleTypeDto(VehicleType type) {
		VehicleType Dto = new VehicleType();
		Dto.setTid(type.getTid());
		Dto.setVtype(type.getVtype());
		return Dto;
	}

}
