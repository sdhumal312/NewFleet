/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.DriverBasicDetails;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fleetop
 *
 */
public class DriverBasicDetailsBL {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	public DriverBasicDetailsBL() {
		super();
	}

	// save the VehicleStatus Model
	public DriverBasicDetails prepareDriverBasicDetails(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		Date 		currentDateUpdate  = null;
		Timestamp 	toDate 				= null;	
		                              
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			DriverBasicDetails driverBasicDetails = new DriverBasicDetails();
			driverBasicDetails.setDriverBasicDetailsId(valueObject.getLong("driverBasicDetailsId"));
			driverBasicDetails.setDriverId(valueObject.getInt("driverId"));
			driverBasicDetails.setDetailsTypeId(valueObject.getLong("detailsTypeId"));
			driverBasicDetails.setQuantity(valueObject.getDouble("quantity"));
			driverBasicDetails.setAssignDate(DateTimeUtility.getTimeStamp(valueObject.getString("assignDate")));
			driverBasicDetails.setRemark(valueObject.getString("remark"));
			driverBasicDetails.setCreatedById(userDetails.getId());
			driverBasicDetails.setLastUpdatedById(userDetails.getId());
			driverBasicDetails.setCreationDate(toDate);
			driverBasicDetails.setLastUpdatedDate(toDate);
			driverBasicDetails.setCompanyId(userDetails.getCompany_id());
			driverBasicDetails.setMarkForDelete(false);

			return driverBasicDetails;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			
		}
		

	}

	
}
