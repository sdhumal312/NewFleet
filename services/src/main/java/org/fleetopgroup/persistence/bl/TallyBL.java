package org.fleetopgroup.persistence.bl;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.TallyCompany;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;

public class TallyBL{
	
	
	public TallyCompany	saveTallyCompanyObject(ValueObject	valueObject)throws Exception {
		TallyCompany			tally			= null;
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			tally		= new TallyCompany();
			
			tally.setCompanyName(valueObject.getString("tallyCompanyName").trim());
			tally.setDescription(valueObject.getString("description"));
			tally.setCompanyId(userDetails.getCompany_id());
			tally.setCreatedById(userDetails.getId());
			tally.setCreationDate(DateTimeUtility.getCurrentTimeStamp());	
			tally.setLastUpdatedBy(userDetails.getId());
			tally.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());	
			
			return tally;
			
		} catch (Exception e) {
			throw e;
		}finally {
			tally			= null;
			userDetails		= null;
		}
	}
	
}