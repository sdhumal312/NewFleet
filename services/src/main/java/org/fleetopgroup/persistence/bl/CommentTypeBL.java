package org.fleetopgroup.persistence.bl;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.CommentType;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;

public class CommentTypeBL{
	
	
	public CommentType  saveCommentType(ValueObject	valueObject)throws Exception {
		CommentType				commentType			= null;
		CustomUserDetails		userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			commentType		= new CommentType();
			
			commentType.setCommentTypeName(valueObject.getString("commentTypeName"));
			commentType.setDescription(valueObject.getString("description"));
			commentType.setCompanyId(userDetails.getCompany_id());
			commentType.setCreatedById(userDetails.getId());
			commentType.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());	
			commentType.setLastModifiedById(userDetails.getId());
			commentType.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
			commentType.setMarkForDelete(false);
			
			return commentType;
			
		} catch (Exception e) {
			throw e;
		}finally {
			commentType				= null;
			userDetails				= null;
		}
	}
	
}