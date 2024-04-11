package org.fleetopgroup.persistence.bl;

import org.fleetopgroup.persistence.model.VehicleLaundryDetails;
import org.fleetopgroup.web.util.ValueObject;

public class VehicleLaundryDetailsBL {

	public static VehicleLaundryDetails getVehicleLaundryDetailsDTO(ValueObject		object) throws Exception {
		VehicleLaundryDetails		vehicleLaundryDetails		= null;
		try {
			vehicleLaundryDetails	= new VehicleLaundryDetails();
			
			vehicleLaundryDetails.setVid(object.getInt("vid"));
			vehicleLaundryDetails.setClothTypesId(object.getLong("clothTypesId",0));
			vehicleLaundryDetails.setLaundryInvoiceId(object.getLong("laundryInvoiceId",0));
			vehicleLaundryDetails.setQuantity(object.getDouble("vehicleQuantity",0));
			vehicleLaundryDetails.setCompanyId(object.getInt("companyId"));
			vehicleLaundryDetails.setLaundryClothDetailsId(object.getLong("laundryClothDetailsId",0));
			
			return vehicleLaundryDetails;
		} catch (Exception e) {
			throw e;
		}
	}
}
