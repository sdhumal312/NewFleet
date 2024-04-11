/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.TripRouteExpenseRange;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fleetop
 *
 */
public class TripRouteExpenseRangeBL {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public TripRouteExpenseRangeBL() {
		super();
	}

	// this is use both on normal save expense and retread expense
	public TripRouteExpenseRange prepareTripRouteExpenseRange(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		Date 						currentDateUpdate  	= null;
		Timestamp 					toDate 				= null;	
		TripRouteExpenseRange 		tripRouteExpenseRange 	= null;
		                              
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			tripRouteExpenseRange = new TripRouteExpenseRange();
			tripRouteExpenseRange.setTripRouteExpenseRangeId(valueObject.getLong("tripRouteExpenseRangeId",0));
			tripRouteExpenseRange.setRouteId(valueObject.getInt("routeId",0));
			tripRouteExpenseRange.setExpenseId(valueObject.getInt("expenseId",0) );
			tripRouteExpenseRange.setExpenseMaxLimt(valueObject.getDouble("expenseMaxLimt", 0) );
			tripRouteExpenseRange.setCreatedBy(userDetails.getId());
			tripRouteExpenseRange.setLastUpdatedBy(userDetails.getId());
			tripRouteExpenseRange.setCreationDate(toDate);
			tripRouteExpenseRange.setLastUpdatedOn(toDate);
			tripRouteExpenseRange.setCompanyId(userDetails.getCompany_id());
			tripRouteExpenseRange.setMarkForDelete(false);

			return tripRouteExpenseRange;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			currentDateUpdate  		= null;   
			toDate 					= null;	  
			tripRouteExpenseRange 	= null;   
		}
		

	}

	
}
