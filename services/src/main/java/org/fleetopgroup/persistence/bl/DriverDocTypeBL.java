package org.fleetopgroup.persistence.bl;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.dto.DriverDocTypeDto;
import org.fleetopgroup.persistence.model.DriverDocType;
import org.fleetopgroup.persistence.model.DriverDocTypeHistory;


public class DriverDocTypeBL {

	public DriverDocTypeBL() {
	}

	// Save the driver Document settings types
	public DriverDocType prepareDriDocTypeModel(DriverDocTypeDto driverDocTypeDto) {

		// create obj on driverDoctype
		DriverDocType DocType = new DriverDocType();
		DocType.setDri_id(driverDocTypeDto.getDri_id());
		DocType.setDri_DocType(driverDocTypeDto.getDri_DocType());

		return DocType;
	}

	// would show the driver List of Document Driver
	public List<DriverDocTypeDto> DriDocTypeListofDto(List<DriverDocType> driverDocType) {

		List<DriverDocTypeDto> Dtos = null;
		if (driverDocType != null && !driverDocType.isEmpty()) {
			Dtos = new ArrayList<DriverDocTypeDto>();

			DriverDocTypeDto Dto = null;
			for (DriverDocType DriverDocType : driverDocType) {
				Dto = new DriverDocTypeDto();
				Dto.setDri_DocType(DriverDocType.getDri_DocType());
				Dto.setDri_id(DriverDocType.getDri_id());

				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	// edit the Driver document type
	public DriverDocTypeDto prepareDriverDocTypeDto(DriverDocType DocType) {
		DriverDocTypeDto Dto = new DriverDocTypeDto();

		Dto.setDri_id(DocType.getDri_id());
		Dto.setDri_DocType(DocType.getDri_DocType());
		return Dto;
	}

	public DriverDocTypeHistory prepareHistoryModel(DriverDocType driverDocType) {
		DriverDocTypeHistory		driverDocTypeHistory		= null;
		try {
			driverDocTypeHistory	= new DriverDocTypeHistory();
			
			driverDocTypeHistory.setCompany_Id(driverDocType.getCompany_Id());
			driverDocTypeHistory.setDri_DocType(driverDocType.getDri_DocType());
			driverDocTypeHistory.setDri_id(driverDocType.getDri_id());
			driverDocTypeHistory.setLastModifiedById(driverDocType.getLastModifiedById());
			driverDocTypeHistory.setLastModifiedOn(driverDocType.getLastModifiedOn());
			driverDocTypeHistory.setMarkForDelete(driverDocType.isMarkForDelete());
			
			return driverDocTypeHistory;
		} catch (Exception e) {
			throw e;
		}
	}

}
