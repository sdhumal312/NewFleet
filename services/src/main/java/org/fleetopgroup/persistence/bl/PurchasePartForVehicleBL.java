/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.PurchasePartForVehicle;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fleetop
 *
 */
public class PurchasePartForVehicleBL {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public PurchasePartForVehicleBL() {
		super();
	}

	// save the VehicleStatus Model
	public PurchasePartForVehicle preparePurchasePartForVehicle(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		Date 		currentDateUpdate  = null;
		Timestamp 	toDate 				= null;	
		                              
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			PurchasePartForVehicle purchasePartForVehicle = new PurchasePartForVehicle();
			purchasePartForVehicle.setVid(valueObject.getInt("vid"));
			purchasePartForVehicle.setPurchaseOrderId(valueObject.getLong("purchaseOrderId"));
			purchasePartForVehicle.setPurchaseorderToPartId(valueObject.getLong("purchaseOrderToPartId"));
			purchasePartForVehicle.setPartQuantity(valueObject.getDouble("partQuantity"));
			purchasePartForVehicle.setCreatedById(userDetails.getId());
			purchasePartForVehicle.setLastUpdatedBy(userDetails.getId());
			purchasePartForVehicle.setCreationDate(toDate);
			purchasePartForVehicle.setLastUpdatedOn(toDate);
			purchasePartForVehicle.setCompanyId(userDetails.getCompany_id());
			purchasePartForVehicle.setMarkForDelete(false);

			return purchasePartForVehicle;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			
		}
		

	}

	
}
