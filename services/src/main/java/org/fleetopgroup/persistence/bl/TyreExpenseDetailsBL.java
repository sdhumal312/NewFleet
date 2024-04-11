/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.TyreExpenseDetails;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author fleetop
 *
 */
public class TyreExpenseDetailsBL {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public TyreExpenseDetailsBL() {
		super();
	}

	// this is use both on normal save expense and retread expense
	public TyreExpenseDetails prepareTyreExpenseDetails(ValueObject valueObject, CustomUserDetails userDetails) throws Exception {
		Date 					currentDateUpdate  	= null;
		Timestamp 				toDate 				= null;	
		TyreExpenseDetails 		tyreExpenseDetails 	= null;
		                              
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			tyreExpenseDetails = new TyreExpenseDetails();
		
			tyreExpenseDetails.setTyreId(valueObject.getLong("tyreId",0));
			if(valueObject.getInt("tyreExpenseId",0) > 0) {
				tyreExpenseDetails.setTyreExpenseId(valueObject.getInt("tyreExpenseId",0));
			}else {
				tyreExpenseDetails.setTyreExpenseId(0);
			}
			if(valueObject.getString("tyreExpenseDate") != null && !valueObject.getString("tyreExpenseDate").isEmpty()) {
				tyreExpenseDetails.setTyreExpenseDate(DateTimeUtility.getTimeStamp(valueObject.getString("tyreExpenseDate")));
				tyreExpenseDetails.setDescription(valueObject.getString("description"));
				tyreExpenseDetails.setTyreTypeId(valueObject.getShort("tyreType"));
				tyreExpenseDetails.setTyreExpenseAmount(valueObject.getDouble("tyreExpenseAmount",0));
				tyreExpenseDetails.setDiscount(valueObject.getDouble("discount",0));
				tyreExpenseDetails.setGst(valueObject.getDouble("gst",0));
				tyreExpenseDetails.setTotalTyreExpenseAmount(valueObject.getDouble("totalCost",0));
				tyreExpenseDetails.setCreatedById(userDetails.getId());
				tyreExpenseDetails.setLastUpdatedBy(userDetails.getId());
				tyreExpenseDetails.setCreationDate(toDate);
				tyreExpenseDetails.setLastUpdatedOn(toDate);
				tyreExpenseDetails.setCompanyId(userDetails.getCompany_id());
				tyreExpenseDetails.setMarkForDelete(false);
				
				if(valueObject.getString("tyreDocument", "").equals("") && valueObject.getString("tyreDocument", "").equals("")) {
					tyreExpenseDetails.setTyreExpenseDetailsDocument(false);
				}else if(!valueObject.getString("tyreDocument", "").equals("") || !valueObject.getString("tyreDocument", "").equals("")){
					tyreExpenseDetails.setTyreExpenseDetailsDocument(true);
				}
				tyreExpenseDetails.setVendorId(valueObject.getInt("vendorId",0));
	
			}
			return tyreExpenseDetails;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			currentDateUpdate  	= null;   
			toDate 				= null;	  
			tyreExpenseDetails 	= null;   
		}
		

	}

	
}
