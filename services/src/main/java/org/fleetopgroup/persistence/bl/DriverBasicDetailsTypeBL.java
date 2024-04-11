/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.DriverBasicDetailsType;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fleetop
 *
 */
public class DriverBasicDetailsTypeBL {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	public DriverBasicDetailsTypeBL() {
		super();
	}

	// save the VehicleStatus Model
	public DriverBasicDetailsType prepareDriverBasicDetailsType(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		Date 		currentDateUpdate  = null;
		Timestamp 	toDate 				= null;	
		                              
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			DriverBasicDetailsType driverBasicDetailsType = new DriverBasicDetailsType();
			driverBasicDetailsType.setDriverBasicDetailsTypeId(valueObject.getLong("driverBasicDetailsTypeId"));
			driverBasicDetailsType.setDriverBasicDetailsTypeName(valueObject.getString("driverBasicDetailsTypeName"));
			driverBasicDetailsType.setDescription(valueObject.getString("description"));
			driverBasicDetailsType.setCreatedById(userDetails.getId());
			driverBasicDetailsType.setLastUpdatedById(userDetails.getId());
			driverBasicDetailsType.setCreationDate(toDate);
			driverBasicDetailsType.setLastUpdatedDate(toDate);
			driverBasicDetailsType.setCompanyId(userDetails.getCompany_id());
			driverBasicDetailsType.setMarkForDelete(false);

			return driverBasicDetailsType;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			
		}
		

	}

	
}
