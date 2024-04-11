/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.fleetopgroup.persistence.model.Labour;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fleetop
 *
 */

public class LabourBL {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");

	public LabourBL() {
		super();
	}
	
	public Labour prepareLabour(ValueObject valueObject)
			throws Exception {
		Labour 	labour 	= null;
		try {
			labour = new Labour();
			labour.setLabourId(valueObject.getInt("labourId"));
			labour.setLabourName(valueObject.getString("labourName"));
			labour.setDescription(valueObject.getString("description"));
			labour.setCreatedById(valueObject.getLong("userId"));
			labour.setLastModifiedById(valueObject.getLong("userId"));
			labour.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
			labour.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
			labour.setCompanyId(valueObject.getInt("companyId"));
			labour.setMarkForDelete(false);
			return labour;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} 
	}
	
	public Labour prepareNewLabourFromDSE(ValueObject valueObject, ValueObject dseValueObject)
			throws Exception {
		Labour 	labour 	= null;
		try {
			labour = new Labour();
			labour.setLabourId(valueObject.getInt("labourId"));
			labour.setLabourName(valueObject.getString("labourName"));
			labour.setDescription(valueObject.getString("description"));
			labour.setCreatedById(dseValueObject.getLong("userId"));
			labour.setLastModifiedById(dseValueObject.getLong("userId"));
			labour.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
			labour.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
			labour.setCompanyId(dseValueObject.getInt("companyId"));
			labour.setMarkForDelete(false);
			return labour;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} 
	}

}
