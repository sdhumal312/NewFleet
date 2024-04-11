package org.fleetopgroup.persistence.bl;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;

import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleDocTypeDto;
import org.fleetopgroup.persistence.model.VehicleDocType;
import org.fleetopgroup.persistence.model.VehicleDocTypeHistory;



public class VehicleDocTypeBL {

	public VehicleDocTypeBL() {
	}

	public VehicleDocType prepareModel(VehicleDocTypeDto vehicleDocTypeDto) {
		VehicleDocType DocType = new VehicleDocType();
		DocType.setDtid(vehicleDocTypeDto.getDtid());
		DocType.setvDocType(vehicleDocTypeDto.getvDocType());
		return DocType;
	}

	public List<VehicleDocTypeDto> prepareListofDto(List<VehicleDocType> vehicleDocType) {
		List<VehicleDocTypeDto> Dtos = null;
		if (vehicleDocType != null && !vehicleDocType.isEmpty()) {
			Dtos = new ArrayList<VehicleDocTypeDto>();
			VehicleDocTypeDto Dto = null;
			for (VehicleDocType vehicelDocType : vehicleDocType) {
				Dto = new VehicleDocTypeDto();
				Dto.setvDocType(vehicelDocType.getvDocType());
				Dto.setDtid(vehicelDocType.getDtid());
				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	public VehicleDocTypeDto prepareVehicleDocTypeDto(VehicleDocType DocType) {
		VehicleDocTypeDto Dto = new VehicleDocTypeDto();
		Dto.setDtid(DocType.getDtid());
		Dto.setvDocType(DocType.getvDocType());
		return Dto;
	}

	public VehicleDocTypeHistory prepareHistoryModel(VehicleDocType vehicleDocType) {
		VehicleDocTypeHistory		docTypeHistory		= new VehicleDocTypeHistory();
		
		docTypeHistory.setDtid(vehicleDocType.getDtid());
		docTypeHistory.setvDocType(vehicleDocType.getvDocType());
		docTypeHistory.setCompany_Id(vehicleDocType.getCompany_Id());
		docTypeHistory.setLastModifiedById(vehicleDocType.getLastModifiedById());
		docTypeHistory.setLastModifiedOn(vehicleDocType.getLastModifiedOn());
		docTypeHistory.setMarkForDelete(vehicleDocType.isMarkForDelete());
		
		return docTypeHistory;
	}

}
