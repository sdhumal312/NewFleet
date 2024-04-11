/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.CorporateAccount;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fleetop
 *
 */
public class CorporateAccountBL {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public CorporateAccountBL() {
		super();
	}

	// save the VehicleStatus Model
	public CorporateAccount preparePartyMaster(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		try {
			CorporateAccount partyMaster = new CorporateAccount();
			partyMaster.setCorporateName(valueObject.getString("corporateName"));
			partyMaster.setAddress(valueObject.getString("address"));
			partyMaster.setMobileNumber(valueObject.getString("mobileNumber"));
			partyMaster.setAlternateMobileNumber(valueObject.getString("alternateMobileNumber"));
			partyMaster.setGstNumber(valueObject.getString("gstNumber"));
			partyMaster.setPerKMRate(valueObject.getDouble("perKMRate", 0));
			partyMaster.setCompanyId(userDetails.getCompany_id());
			partyMaster.setMarkForDelete(false);

			return partyMaster;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			
		}
		

	}

	
}
