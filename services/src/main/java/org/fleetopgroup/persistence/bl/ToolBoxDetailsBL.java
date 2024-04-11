package org.fleetopgroup.persistence.bl;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.ToolBoxDetails;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;

public class ToolBoxDetailsBL 
{
	public ToolBoxDetails prepareToolBoxDetails(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		ToolBoxDetails					toolBoxDetails			= null;
		try
		{
			toolBoxDetails		= new ToolBoxDetails();
			toolBoxDetails.setVid(valueObject.getInt("vid"));
			toolBoxDetails.setToolBoxId(valueObject.getLong("toolBoxId"));
			toolBoxDetails.setQuantity(valueObject.getDouble("quantity"));
			toolBoxDetails.setDescription(valueObject.getString("description"));
			toolBoxDetails.setCompanyId(userDetails.getCompany_id());
			toolBoxDetails.setCreatedById(userDetails.getId());
			toolBoxDetails.setCreationDate(DateTimeUtility.getCurrentTimeStamp());
			toolBoxDetails.setLastUpdatedBy(userDetails.getId());
			toolBoxDetails.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			
			return toolBoxDetails;
		}
		catch (Exception e) 
		{
			throw e;
		}
		finally 
		{
			toolBoxDetails			= null;
		}
		
		
	}
}
