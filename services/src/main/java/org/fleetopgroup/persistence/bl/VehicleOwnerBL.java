/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleOwnerDto;
import org.fleetopgroup.persistence.model.VehicleOwner;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author fleetop
 *
 */
public class VehicleOwnerBL {

	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");

	/**
	 * 
	 */
	public VehicleOwnerBL() {
		
	}

	public VehicleOwner prepareVehicleOwnerBean(VehicleOwnerDto owner) {

		VehicleOwner NewOwner = new VehicleOwner();

		NewOwner.setVOID(owner.getVOID());
		NewOwner.setVEHID(owner.getVEHID());
		NewOwner.setVEH_OWNER_SERIAL(owner.getVEH_OWNER_SERIAL());
		NewOwner.setVEH_OWNER_NAME(owner.getVEH_OWNER_NAME());
		NewOwner.setVEH_OWNER_PANNO(owner.getVEH_OWNER_PANNO());
		NewOwner.setVEH_OWNER_PHONE(owner.getVEH_OWNER_PHONE());
		NewOwner.setVEH_OWNER_AADHARNO(owner.getVEH_OWNER_AADHARNO());
		NewOwner.setVEH_OWNER_ADDRESS(owner.getVEH_OWNER_ADDRESS());
		NewOwner.setVEH_OWNER_CITY(owner.getVEH_OWNER_CITY());
		NewOwner.setVEH_OWNER_STATE(owner.getVEH_OWNER_STATE());
		NewOwner.setVEH_OWNER_COUNTRY(owner.getVEH_OWNER_COUNTRY());
		NewOwner.setVEH_OWNER_PINCODE(owner.getVEH_OWNER_PINCODE());
		NewOwner.setVEH_DRIVER_NAME(owner.getVEH_DRIVER_NAME());
		NewOwner.setVEH_DRIVER_PHONE(owner.getVEH_DRIVER_PHONE());

		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		NewOwner.setCREATEDBYID(userDetails.getId());
		NewOwner.setLASTMODIFIEDBYID(userDetails.getId());
		NewOwner.setMarkForDelete(false);
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		/** Set Created Current Date */
		NewOwner.setCREATED_DATE(toDate);
		NewOwner.setLASTUPDATED_DATE(toDate);

		return NewOwner;

	}

	/**
	 * @param list_Of_Vehicle_ID_VehicleOwner
	 * @return
	 *//*
	public List<VehicleOwnerDto> prepare_ListVehicleOwnerDetails(List<VehicleOwner> vehicleOwner) {
		List<VehicleOwnerDto> Dtos = null;
		if (vehicleOwner != null && !vehicleOwner.isEmpty()) {
			Dtos = new ArrayList<VehicleOwnerDto>();
			VehicleOwnerDto NewOwner = null;
			for (VehicleOwner owner : vehicleOwner) {

				NewOwner = new VehicleOwnerDto();

				NewOwner.setVOID(owner.getVOID());
				NewOwner.setVEHID(owner.getVEHID());
				NewOwner.setVEH_OWNER_SERIAL(owner.getVEH_OWNER_SERIAL());
				NewOwner.setVEH_OWNER_NAME(owner.getVEH_OWNER_NAME());
				NewOwner.setVEH_OWNER_PANNO(owner.getVEH_OWNER_PANNO());
				NewOwner.setVEH_OWNER_PHONE(owner.getVEH_OWNER_PHONE());
				NewOwner.setVEH_OWNER_AADHARNO(owner.getVEH_OWNER_AADHARNO());
				NewOwner.setVEH_OWNER_ADDRESS(owner.getVEH_OWNER_ADDRESS());
				NewOwner.setVEH_OWNER_CITY(owner.getVEH_OWNER_CITY());
				NewOwner.setVEH_OWNER_STATE(owner.getVEH_OWNER_STATE());
				NewOwner.setVEH_OWNER_COUNTRY(owner.getVEH_OWNER_COUNTRY());
				NewOwner.setVEH_OWNER_PINCODE(owner.getVEH_OWNER_PINCODE());
				NewOwner.setVEH_DRIVER_NAME(owner.getVEH_DRIVER_NAME());
				NewOwner.setVEH_DRIVER_PHONE(owner.getVEH_DRIVER_PHONE());

				NewOwner.setCREATEDBY(owner.getCREATEDBY());
				NewOwner.setLASTMODIFIEDBY(owner.getLASTMODIFIEDBY());
				NewOwner.setMarkForDelete(owner.isMarkForDelete());

				if (owner.getCREATED_DATE() != null) {
					*//** Set Created Current Date *//*
					NewOwner.setCREATED_DATE(CreatedDateTime.format(owner.getCREATED_DATE()));
				}
				if (owner.getLASTUPDATED_DATE() != null) {
					NewOwner.setLASTUPDATED_DATE(CreatedDateTime.format(owner.getLASTUPDATED_DATE()));
				}
				Dtos.add(NewOwner);
			}
		}

		return Dtos;
	}
*/
	/**
	 * @param get_VehiclePurchase
	 * @return
	 */
	public VehicleOwnerDto prepare_VehicleOwnerBean(VehicleOwner owner) {

		VehicleOwnerDto NewOwner = new VehicleOwnerDto();

		NewOwner.setVOID(owner.getVOID());
		NewOwner.setVEHID(owner.getVEHID());
		NewOwner.setVEH_OWNER_SERIAL(owner.getVEH_OWNER_SERIAL());
		NewOwner.setVEH_OWNER_NAME(owner.getVEH_OWNER_NAME());
		NewOwner.setVEH_OWNER_PANNO(owner.getVEH_OWNER_PANNO());
		NewOwner.setVEH_OWNER_PHONE(owner.getVEH_OWNER_PHONE());
		NewOwner.setVEH_OWNER_AADHARNO(owner.getVEH_OWNER_AADHARNO());
		NewOwner.setVEH_OWNER_ADDRESS(owner.getVEH_OWNER_ADDRESS());
		NewOwner.setVEH_OWNER_CITY(owner.getVEH_OWNER_CITY());
		NewOwner.setVEH_OWNER_STATE(owner.getVEH_OWNER_STATE());
		NewOwner.setVEH_OWNER_COUNTRY(owner.getVEH_OWNER_COUNTRY());
		NewOwner.setVEH_OWNER_PINCODE(owner.getVEH_OWNER_PINCODE());
		NewOwner.setVEH_DRIVER_NAME(owner.getVEH_DRIVER_NAME());
		NewOwner.setVEH_DRIVER_PHONE(owner.getVEH_DRIVER_PHONE());

		return NewOwner;
	}
}
