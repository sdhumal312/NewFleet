package org.fleetopgroup.persistence.bl;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.dto.VehiclePurchaseInfoTypeDto;
import org.fleetopgroup.persistence.model.VehiclePurchaseInfoType;


public class VehiclePurchaseTypeBL {

	public VehiclePurchaseTypeBL() {
	}

	public VehiclePurchaseInfoType prepareModel(VehiclePurchaseInfoTypeDto vehiclePurchaseInfoTypeDto) {
		VehiclePurchaseInfoType PurchaseInfoType = new VehiclePurchaseInfoType();
		PurchaseInfoType.setPtid(vehiclePurchaseInfoTypeDto.getPtid());
		PurchaseInfoType.setvPurchaseInfoType(vehiclePurchaseInfoTypeDto.getvPurchaseInfoType());
		return PurchaseInfoType;
	}

	public List<VehiclePurchaseInfoTypeDto> prepareListofDto(List<VehiclePurchaseInfoType> vehiclePurchaseInfoType) {
		List<VehiclePurchaseInfoTypeDto> Dtos = null;
		if (vehiclePurchaseInfoType != null && !vehiclePurchaseInfoType.isEmpty()) {
			Dtos = new ArrayList<VehiclePurchaseInfoTypeDto>();
			VehiclePurchaseInfoTypeDto Dto = null;
			for (VehiclePurchaseInfoType vehicelPurchaseInfoType : vehiclePurchaseInfoType) {
				Dto = new VehiclePurchaseInfoTypeDto();
				Dto.setvPurchaseInfoType(vehicelPurchaseInfoType.getvPurchaseInfoType());
				Dto.setPtid(vehicelPurchaseInfoType.getPtid());
				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	public VehiclePurchaseInfoTypeDto prepareVehiclePurchaseInfoTypeDto(VehiclePurchaseInfoType PurchaseInfoType) {
		VehiclePurchaseInfoTypeDto Dto = new VehiclePurchaseInfoTypeDto();
		Dto.setPtid(PurchaseInfoType.getPtid());
		Dto.setvPurchaseInfoType(PurchaseInfoType.getvPurchaseInfoType());
		return Dto;
	}

}
