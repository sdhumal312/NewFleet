package org.fleetopgroup.persistence.bl;

import java.util.Date;

import org.fleetopgroup.persistence.model.PartWarrantyDetails;
import org.fleetopgroup.persistence.model.PartWarrantyDetailsHistory;

public class PartWarrantyBL {
	
	public static final short	PART_WARRANTY_HISTORY_TYPE_ASSIGN		= 1;
	public static final short	PART_WARRANTY_HISTORY_TYPE_UNASSIGN		= 2;
	public static final short	PART_WARRANTY_HISTORY_TYPE_DELETE		= 3;
	

	public static PartWarrantyDetailsHistory getPartWarrantyHistoryDto(Long userId, PartWarrantyDetails	partWarrantyDetails) {
		PartWarrantyDetailsHistory		detailsHistory		= null;
		
		detailsHistory	= new PartWarrantyDetailsHistory();
		
		detailsHistory.setPartWarrantyDetailsId(partWarrantyDetails.getPartWarrantyDetailsId());
		detailsHistory.setPartId(partWarrantyDetails.getPartId());
		detailsHistory.setInventoryId(partWarrantyDetails.getInventoryId());
		detailsHistory.setPartSerialNumber(partWarrantyDetails.getPartSerialNumber());
		detailsHistory.setPartAsignDate(partWarrantyDetails.getPartAsignDate());
		detailsHistory.setWarrantyEndDate(partWarrantyDetails.getWarrantyEndDate());
		detailsHistory.setStatus(partWarrantyDetails.getStatus());
		detailsHistory.setLocationId(partWarrantyDetails.getLocationId());
		detailsHistory.setCompanyId(partWarrantyDetails.getCompanyId());
		detailsHistory.setCreated(new Date());
		detailsHistory.setCreatedById(userId);
		detailsHistory.setVid(partWarrantyDetails.getVid());
		detailsHistory.setServiceId(partWarrantyDetails.getServiceId());
		detailsHistory.setServicePartId(partWarrantyDetails.getServiceId());
		detailsHistory.setReplaceInServiceId(partWarrantyDetails.getReplaceInServiceId());
		detailsHistory.setReplacePartWarrantyDetailsId(partWarrantyDetails.getReplacePartWarrantyDetailsId());
		detailsHistory.setPartWarrantyStatusId(partWarrantyDetails.getPartWarrantyStatusId());
		detailsHistory.setAssignedFrom(partWarrantyDetails.getAssignedFrom());
		
		
		return detailsHistory;
	}
}
