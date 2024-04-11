package org.fleetopgroup.persistence.bl;

/**
 * @author fleetop
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleFuelDto;
import org.fleetopgroup.persistence.model.VehicleFuel;


public class VehicleFuelBL {

	public VehicleFuelBL() {
	}

	public VehicleFuel prepareModel(VehicleFuelDto vehicleFuelDto) {
		VehicleFuel Fuel = new VehicleFuel();
		Fuel.setFid(vehicleFuelDto.getFid());
		Fuel.setvFuel(vehicleFuelDto.getvFuel());
		return Fuel;
	}

	public List<VehicleFuelDto> prepareListofDto(List<VehicleFuel> vehiclefuel) {
		List<VehicleFuelDto> Dtos = null;
		if (vehiclefuel != null && !vehiclefuel.isEmpty()) {
			Dtos = new ArrayList<VehicleFuelDto>();
			VehicleFuelDto Dto = null;
			for (VehicleFuel vehicelFuel : vehiclefuel) {
				Dto = new VehicleFuelDto();
				Dto.setvFuel(vehicelFuel.getvFuel());
				Dto.setFid(vehicelFuel.getFid());
				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	public VehicleFuelDto prepareVehicleFuelDto(VehicleFuel Fuel) {
		VehicleFuelDto Dto = new VehicleFuelDto();
		Dto.setFid(Fuel.getFid());
		Dto.setvFuel(Fuel.getvFuel());
		return Dto;
	}
}