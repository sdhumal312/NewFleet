package org.fleetopgroup.persistence.bl;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.PartSubCategory;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;

public class PartSubCategoriesBL{
	
	
	public PartSubCategory	savePartSubCatrgories(ValueObject	valueObject)throws Exception {
		PartSubCategory			partSubCategory			= null;
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			partSubCategory		= new PartSubCategory();
			
			partSubCategory.setCategoryId(valueObject.getInt("partCategory"));
			partSubCategory.setCompanyId(userDetails.getCompany_id());
			partSubCategory.setCreatedById(userDetails.getId());
			partSubCategory.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());	
			partSubCategory.setDescription(valueObject.getString("description"));
			partSubCategory.setLastModifiedById(userDetails.getId());
			partSubCategory.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
			partSubCategory.setMarkForDelete(false);
			partSubCategory.setSubCategoryName(valueObject.getString("partSubCategory").trim());
			
			return partSubCategory;
			
		} catch (Exception e) {
			throw e;
		}finally {
			partSubCategory			= null;
			userDetails				= null;
		}
	}
	
}