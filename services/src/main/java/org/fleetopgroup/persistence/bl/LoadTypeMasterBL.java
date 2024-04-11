package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.LoadTypeMaster;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;

public class LoadTypeMasterBL {
	
	public LoadTypeMaster	getLoadTypesModel(ValueObject	valueObject)throws Exception {
		
		LoadTypeMaster				loadTypes		= null;
		CustomUserDetails		userDetails			= null;
		
		java.util.Date lastModDate = new java.util.Date();
		Timestamp lastMod = new java.sql.Timestamp(lastModDate.getTime());
		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			loadTypes	= new LoadTypeMaster();
			
			
			loadTypes.setLoadTypeName(valueObject.getString("loadTypeName").trim());
			loadTypes.setDescription(valueObject.getString("description"));
			loadTypes.setCompanyId(userDetails.getCompany_id());
			loadTypes.setCreatedById(userDetails.getId());
			loadTypes.setCreationDate(DateTimeUtility.getCurrentTimeStamp());	
			loadTypes.setLastUpdatedBy(userDetails.getId());
			loadTypes.setLastUpdatedOn(lastMod);		
			
			return loadTypes;
		} catch (Exception e) {
			throw e;
		}finally {
			loadTypes		= null;
			userDetails		= null;
		}
	}

}
