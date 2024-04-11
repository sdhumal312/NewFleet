package org.fleetopgroup.persistence.bl;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.dto.VendorTypeDto;
import org.fleetopgroup.persistence.model.VendorType;
import org.fleetopgroup.persistence.model.VendorTypeHistory;


public class VendorTypeBL {

	public VendorTypeBL() {
	}

	// Save the Renewal settings types
	public VendorType prepareVendorTypeModel(VendorTypeDto vendorTypeDto) {

		// create obj on driverDoctype
		VendorType VendorType = new VendorType();

		VendorType.setVendor_Typeid(vendorTypeDto.getVendor_Typeid());
		VendorType.setVendor_TypeName(vendorTypeDto.getVendor_TypeName().toUpperCase());

		return VendorType;
	}

	// would show the driver List of Document Driver
	public List<VendorTypeDto> VendorListofDto(List<VendorType> vendorType) {

		List<VendorTypeDto> Dtos = null;
		if (vendorType != null && !vendorType.isEmpty()) {
			Dtos = new ArrayList<VendorTypeDto>();

			VendorTypeDto Dto = null;
			for (VendorType VendorType : vendorType) {
				Dto = new VendorTypeDto();

				Dto.setVendor_Typeid(VendorType.getVendor_Typeid());
				Dto.setVendor_TypeName(VendorType.getVendor_TypeName());
				if(VendorType.isCommonMaster()) {
					Dto.setIsCommonMaster((short)1);
				}

				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	// edit the Driver document type
	public VendorTypeDto getVendorTypeDto(VendorType VendorType) {

		VendorTypeDto Dto = new VendorTypeDto();

		Dto.setVendor_Typeid(VendorType.getVendor_Typeid());
		Dto.setVendor_TypeName(VendorType.getVendor_TypeName());

		return Dto;
	}

	public VendorTypeHistory prepareHistoryModel(VendorType vendorType) {
		VendorTypeHistory		vendorTypeHistory		= null;
		try {
			if(vendorType != null) {
				vendorTypeHistory	= new VendorTypeHistory();
				
				vendorTypeHistory.setCompanyId(vendorType.getCompanyId());
				vendorTypeHistory.setLastModifiedById(vendorType.getLastModifiedById());
				vendorTypeHistory.setLastModifiedOn(vendorType.getLastModifiedOn());
				vendorTypeHistory.setMarkForDelete(vendorType.isMarkForDelete());
				vendorTypeHistory.setVendor_Typeid(vendorType.getVendor_Typeid());
				vendorTypeHistory.setVendor_TypeName(vendorType.getVendor_TypeName());
			}
			
			return vendorTypeHistory;
		} catch (Exception e) {
			throw e;
		} finally {
			vendorTypeHistory		= null;
		}
	}

	/***************************************************************************************************************	
	*/

}
