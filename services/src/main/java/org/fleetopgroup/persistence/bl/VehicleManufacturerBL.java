/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.VehicleManufacturer;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fleetop
 *
 */
public class VehicleManufacturerBL {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public VehicleManufacturerBL() {
		super();
	}

	// save the VehicleStatus Model
	public VehicleManufacturer prepareVehicleManufacturer(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		Date 		currentDateUpdate  = null;
		Timestamp 	toDate 				= null;	
		                              
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			VehicleManufacturer vehicleManufacturer = new VehicleManufacturer();
			vehicleManufacturer.setVehicleManufacturerName(valueObject.getString("vehicleManufacturerName"));
			vehicleManufacturer.setDescription(valueObject.getString("description"));
			vehicleManufacturer.setCreatedById(userDetails.getId());
			vehicleManufacturer.setLastUpdatedBy(userDetails.getId());
			vehicleManufacturer.setCreationDate(toDate);
			vehicleManufacturer.setLastUpdatedOn(toDate);
			vehicleManufacturer.setCompanyId(userDetails.getCompany_id());
			vehicleManufacturer.setMarkForDelete(false);

			return vehicleManufacturer;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			
		}
		

	}

	
}
