/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.UreaTransferDetails;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fleetop
 *
 */
public class UreaTransferDetailsBL {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public UreaTransferDetailsBL() {
		super();
	}

	
	public UreaTransferDetails saveUreaTransferDetails(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		Date 		currentDateUpdate  = null;
		Timestamp 	toDate 				= null;	
		                              
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			UreaTransferDetails ureaTransferDetails = new UreaTransferDetails();
			ureaTransferDetails.setUreaTransferDetailsId(valueObject.getLong("ureaTransferDetailsId"));
			ureaTransferDetails.setUreaTransferId(valueObject.getLong("ureaTransferId"));
			ureaTransferDetails.setUreaInvoiceToDetailsId(valueObject.getLong("ureaInvoiceToDetailsId",0));
			ureaTransferDetails.setUreaStockQuantity(valueObject.getDouble("ureaStockQuantity",0));
			ureaTransferDetails.setUreaInventoryTransferQuantity(valueObject.getDouble("ureaInventoryTransferQuantity",0));
			ureaTransferDetails.setUreaTransferFromLocationId(valueObject.getInt("ureaTransferFromLocationId",0));
			ureaTransferDetails.setCreatedById(userDetails.getId());
			ureaTransferDetails.setLastUpdatedById(userDetails.getId());
			ureaTransferDetails.setCreationDate(toDate);
			ureaTransferDetails.setLastUpdatedDate(toDate);
			ureaTransferDetails.setCompanyId(userDetails.getCompany_id());
			ureaTransferDetails.setMarkForDelete(false);

			return ureaTransferDetails;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			
		}
		

	}

	
}
