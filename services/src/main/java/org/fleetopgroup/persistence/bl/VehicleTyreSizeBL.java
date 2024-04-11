/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleTyreSizeDto;
import org.fleetopgroup.persistence.model.VehicleTyreSize;
import org.fleetopgroup.persistence.model.VehicleTyreSizeHistory;

/**
 * @author fleetop
 *
 */
public class VehicleTyreSizeBL {

	public VehicleTyreSizeBL() {
		super();
	}

	// save the VehicleStatus Model
	public VehicleTyreSize prepareModel(VehicleTyreSizeDto vehicleTyreSizeDto) {
		Date 		currentDateUpdate 	= new Date();
		Timestamp 	toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());

		VehicleTyreSize type = new VehicleTyreSize();
		type.setTS_ID(vehicleTyreSizeDto.getTS_ID());
		type.setTYRE_SIZE(vehicleTyreSizeDto.getTYRE_SIZE().replaceAll("[/ ]", "-"));
		type.setTYRE_SIZE_DESCRITION(vehicleTyreSizeDto.getTYRE_SIZE_DESCRITION());
		type.setCREATED_DATE(toDate);
		type.setLASTMODIFIED_DATE(toDate);
		type.setMarkForDelete(false);

		return type;
	}

	// Show the List OF Vehicle Type
	public List<VehicleTyreSizeDto> prepareListofDto(List<VehicleTyreSize> vehicletypes) {
		List<VehicleTyreSizeDto> Dtos = null;
		if (vehicletypes != null && !vehicletypes.isEmpty()) {
			Dtos = new ArrayList<VehicleTyreSizeDto>();
			VehicleTyreSizeDto Dto = null;
			for (VehicleTyreSize vehiceltype : vehicletypes) {
				Dto = new VehicleTyreSizeDto();
				Dto.setTYRE_SIZE(vehiceltype.getTYRE_SIZE());
				Dto.setTYRE_SIZE_DESCRITION(vehiceltype.getTYRE_SIZE_DESCRITION());
				Dto.setTS_ID(vehiceltype.getTS_ID());
				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	// edit the vehicle Type GEt the Information
	public VehicleTyreSize prepareVehicleTypeDto(VehicleTyreSize size) {
		VehicleTyreSize Dto = new VehicleTyreSize();
		Dto.setTYRE_SIZE(size.getTYRE_SIZE());
		Dto.setTYRE_SIZE_DESCRITION(size.getTYRE_SIZE_DESCRITION());
		Dto.setTS_ID(size.getTS_ID());
		return Dto;
	}

	public VehicleTyreSizeHistory prepareHistoryModel(VehicleTyreSize vehicleTyreSize) {
		VehicleTyreSizeHistory	vehicleTyreSizeHistory	= new VehicleTyreSizeHistory();
		
		vehicleTyreSizeHistory.setTS_ID(vehicleTyreSize.getTS_ID());
		vehicleTyreSizeHistory.setCompanyId(vehicleTyreSize.getCompanyId());
		vehicleTyreSizeHistory.setTYRE_SIZE(vehicleTyreSize.getTYRE_SIZE());
		vehicleTyreSizeHistory.setTYRE_SIZE_DESCRITION(vehicleTyreSize.getTYRE_SIZE_DESCRITION());
		vehicleTyreSizeHistory.setLASTMODIFIEDBYID(vehicleTyreSize.getLASTMODIFIEDBYID());
		vehicleTyreSizeHistory.setLASTMODIFIED_DATE(vehicleTyreSize.getLASTMODIFIED_DATE());
		vehicleTyreSizeHistory.setMarkForDelete(vehicleTyreSize.isMarkForDelete());
		
		return vehicleTyreSizeHistory;
	}
}
