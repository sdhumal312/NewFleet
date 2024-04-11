package org.fleetopgroup.persistence.bl;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.ClothTypes;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;

public class ClothTypesBL {

	public ClothTypes	getClothTypesModel(ValueObject	valueObject)throws Exception {
		ClothTypes				clothTypes		= null;
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			clothTypes	= new ClothTypes();
			
			clothTypes.setClothTypeName(valueObject.getString("clothTypeName").trim());
			clothTypes.setDescription(valueObject.getString("description"));
			clothTypes.setCompanyId(userDetails.getCompany_id());
			clothTypes.setCreatedById(userDetails.getId());
			clothTypes.setCreationDate(DateTimeUtility.getCurrentTimeStamp());	
			clothTypes.setLastUpdatedBy(userDetails.getId());
			clothTypes.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());	
			
			return clothTypes;
		} catch (Exception e) {
			throw e;
		}finally {
			clothTypes		= null;
			userDetails		= null;
		}
	}
}
