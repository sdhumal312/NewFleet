/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.VehicleModel;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fleetop
 *
 */
public class VehicleModelBL {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public VehicleModelBL() {
		super();
	}

	// save the VehicleStatus Model
	public VehicleModel prepareVehicleModel(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		Date 		currentDateUpdate  = null;
		Timestamp 	toDate 				= null;	
		                              
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			VehicleModel vehicleModel = new VehicleModel();
			vehicleModel.setVehicleManufacturerId(valueObject.getLong("manufacturer",0));
			vehicleModel.setVehicleModelName(valueObject.getString("vehicleModelName"));
			vehicleModel.setDescription(valueObject.getString("description"));
			vehicleModel.setCreatedById(userDetails.getId());
			vehicleModel.setLastUpdatedBy(userDetails.getId());
			vehicleModel.setCreationDate(toDate);
			vehicleModel.setLastUpdatedOn(toDate);
			vehicleModel.setCompanyId(userDetails.getCompany_id());
			vehicleModel.setMarkForDelete(false);

			return vehicleModel;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			
		}
		

	}

	
}
