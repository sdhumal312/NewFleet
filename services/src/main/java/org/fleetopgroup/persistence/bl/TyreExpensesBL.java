/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.TyreExpenses;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fleetop
 *
 */
public class TyreExpensesBL {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public TyreExpensesBL() {
		super();
	}

	// save the VehicleStatus Model
	public TyreExpenses prepareTyreExpense(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		Date 		currentDateUpdate  = null;
		Timestamp 	toDate 				= null;	
		                              
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			TyreExpenses tyreExpenses = new TyreExpenses();
			tyreExpenses.setTyreExpenseName(valueObject.getString("addTyreExpenseName"));
			tyreExpenses.setDescription(valueObject.getString("addDescription"));
			tyreExpenses.setCreatedById(userDetails.getId());
			tyreExpenses.setLastUpdatedBy(userDetails.getId());
			tyreExpenses.setCreationDate(toDate);
			tyreExpenses.setLastUpdatedOn(toDate);
			tyreExpenses.setCompanyId(userDetails.getCompany_id());
			tyreExpenses.setMarkForDelete(false);

			return tyreExpenses;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			
		}
		

	}

	
}
