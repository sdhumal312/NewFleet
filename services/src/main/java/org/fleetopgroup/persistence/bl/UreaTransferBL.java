/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.constant.UreaRequisitionAndTransferStatus;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.UreaTransfer;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fleetop
 *
 */
public class UreaTransferBL {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public UreaTransferBL() {
		super();
	}

	
	public UreaTransfer saveUreaTransfer(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		Date 		currentDateUpdate  = null;
		Timestamp 	toDate 				= null;	
		                              
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			UreaTransfer ureaTransfer = new UreaTransfer();
			ureaTransfer.setUreaTransferId(valueObject.getLong("ureaTransferId"));
			ureaTransfer.setUreaRequisitionId(valueObject.getLong("ureaRequisitionId"));
			ureaTransfer.setUreaTransferFromLocationId(valueObject.getInt("ureaTransferFromLocationId"));
			ureaTransfer.setUreaTransferToLoactionId(valueObject.getInt("ureaTransferToLoactionId"));
			ureaTransfer.setUreaRequisitionSenderId(valueObject.getLong("ureaRequisitionSenderId"));
			ureaTransfer.setUreaTransferById(valueObject.getLong("ureaRequisitionSenderId"));
			ureaTransfer.setUreaTransferDate(toDate);
			ureaTransfer.setUreaTransferQuantity(valueObject.getDouble("ureaTransferQuantity",0));
			ureaTransfer.setUreaTransferStatusId(UreaRequisitionAndTransferStatus.SAVE_TRANSFERED);
			ureaTransfer.setUreaTransferRemark(valueObject.getString("ureaTransferRemark"));
			ureaTransfer.setCreatedById(userDetails.getId());
			ureaTransfer.setLastUpdatedById(userDetails.getId());
			ureaTransfer.setCreationDate(toDate);
			ureaTransfer.setLastUpdatedDate(toDate);
			ureaTransfer.setCompanyId(userDetails.getCompany_id());
			ureaTransfer.setMarkForDelete(false);

			return ureaTransfer;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			
		}
		

	}

	
}
