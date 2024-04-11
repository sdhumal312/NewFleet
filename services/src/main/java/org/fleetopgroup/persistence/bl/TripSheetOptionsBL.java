/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.dto.TripSheetOptionsDto;
import org.fleetopgroup.persistence.dto.VehicleTyreSizeDto;
import org.fleetopgroup.persistence.model.TripSheetOptions;
import org.fleetopgroup.persistence.model.TripSheetOptionsHistory;
import org.fleetopgroup.persistence.model.VehicleTyreSize;

/**
 * @author fleetop
 *
 */
public class TripSheetOptionsBL {

	public TripSheetOptionsBL() {
		super();
	}

	// save the TripSheetOptions Model
	public TripSheetOptions prepareModel(TripSheetOptionsDto tripSheetOptionsDto) {
		Date 		currentDateUpdate 	= new Date();
		Timestamp 	toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());

		TripSheetOptions type = new TripSheetOptions();
		type.setTripsheetoptionsId(tripSheetOptionsDto.getTripsheetoptionsId());
		type.setTripsheetextraname(tripSheetOptionsDto.getTripsheetextraname());
		type.setTripsheetextradescription(tripSheetOptionsDto.getTripsheetextradescription());
		type.setCreated(toDate);
		type.setLastupdated(toDate);
						
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

	public TripSheetOptionsHistory prepareHistoryModel(TripSheetOptions tripSheetOptions) {
		TripSheetOptionsHistory	tripSheetOptionsHistory	= new TripSheetOptionsHistory();
		
		tripSheetOptionsHistory.setTripsheetoptionsId(tripSheetOptions.getTripsheetoptionsId());
		tripSheetOptionsHistory.setCompanyId(tripSheetOptions.getCompanyId());
		tripSheetOptionsHistory.setTripsheetextraname(tripSheetOptions.getTripsheetextraname());
		tripSheetOptionsHistory.setTripsheetextradescription(tripSheetOptions.getTripsheetextradescription());
		tripSheetOptionsHistory.setLASTMODIFIEDBYID(tripSheetOptions.getLastModifiedById());
		tripSheetOptionsHistory.setLASTMODIFIED_DATE(tripSheetOptions.getLastupdated());
		tripSheetOptionsHistory.setMarkForDelete(tripSheetOptions.isMarkForDelete());
		
		return tripSheetOptionsHistory;
	}
}
