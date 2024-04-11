/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.constant.UreaRequisitionAndTransferStatus;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.UreaRequisition;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fleetop
 *
 */
public class UreaRequisitionBL {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public UreaRequisitionBL() {
		super();
	}

	
	public UreaRequisition prepareUreaRequisition(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		Date 		currentDateUpdate  = null;
		Timestamp 	toDate 				= null;	
		                              
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			UreaRequisition ureaRequisition = new UreaRequisition();
			ureaRequisition.setUreaRequisitionId(valueObject.getLong("ureaRequisitionId"));
			ureaRequisition.setUreaRequiredLocationId(valueObject.getInt("ureaRequiredLocationId"));
			ureaRequisition.setUreaTransferFromLocationId(valueObject.getInt("ureaTransferFromLocationId"));
			ureaRequisition.setUreaRequisitionSenderId(userDetails.getId());
			ureaRequisition.setUreaRequisitionReceiverId(valueObject.getLong("ureaRequisitionReceiverId"));
			ureaRequisition.setUreaRequiredDate(DateTimeUtility.getTimeStamp(valueObject.getString("ureaRequiredDate")));
			ureaRequisition.setUreaRequiredQuantity(valueObject.getDouble("ureaRequiredQuantity",0));
			ureaRequisition.setUreaReceivedQuantity(valueObject.getDouble("ureaReceivedQuantity",0));
			ureaRequisition.setUreaRejectedQuantity(valueObject.getDouble("ureaRejectedQuantity",0));
			ureaRequisition.setUreaRequisitionStatusId(UreaRequisitionAndTransferStatus.SENT_REQUISTION);
			ureaRequisition.setUreaRequisitionRemark(valueObject.getString("ureaRequisitionRemark"));
			ureaRequisition.setCreatedById(userDetails.getId());
			ureaRequisition.setLastUpdatedById(userDetails.getId());
			ureaRequisition.setCreationDate(toDate);
			ureaRequisition.setLastUpdatedDate(toDate);
			ureaRequisition.setCompanyId(userDetails.getCompany_id());
			ureaRequisition.setMarkForDelete(false);

			return ureaRequisition;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			
		}
		

	}

	
}
