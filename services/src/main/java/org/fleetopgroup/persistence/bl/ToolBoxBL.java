package org.fleetopgroup.persistence.bl;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.ToolBox;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;

public class ToolBoxBL 
{
	public ToolBox saveToolBoxObject(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		ToolBox					toolBox			= null;
		try
		{
			toolBox		= new ToolBox();
			toolBox.setToolBoxName(valueObject.getString("toolBoxName"));
			toolBox.setDescription(valueObject.getString("description"));
			toolBox.setCompanyId(userDetails.getCompany_id());
			toolBox.setCreatedById(userDetails.getId());
			toolBox.setCreationDate(DateTimeUtility.getCurrentTimeStamp());
			toolBox.setLastUpdatedBy(userDetails.getId());
			toolBox.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			
			return toolBox;
		}
		catch (Exception e) 
		{
			throw e;
		}
		finally 
		{
			toolBox			= null;
		}
		
		
	}
}
