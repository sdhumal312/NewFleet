package org.fleetopgroup.persistence.bl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.MasterParts;
import org.fleetopgroup.persistence.model.PartWarrantyDetails;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;

public class PartWarrantyDetailsBL {
	
	public static final short	PART_WARRANTY_STATUS_UNASIGNED		= 1;
	public static final short	PART_WARRANTY_STATUS_ASIGNED		= 2;
	public static final short	PART_WARRANTY_STATUS_EXPIRED		= 3;
	
	
	public static final String	PART_WARRANTY_STATUS_UNASIGNED_NAME		= "Unasigned";
	public static final String	PART_WARRANTY_STATUS_ASIGNED_NAME		= "Asigned";
	public static final String	PART_WARRANTY_STATUS_EXPIRED_NAME		= "Expired";
	
	public static final short	UNASIGNED					= 1;
	public static final short	ASSIGNED					= 2;
	public static final short	REPLACE_UNDER_WARRANTY		= 3;
	public static final short	REPLACE_AFTER_WARRANTY		= 4;
	
	public static final String	UNASIGNED_NAME					= "Unasigned";
	public static final String	ASSIGNED_NAME					= "Assigned";
	public static final String	REPLACE_UNDER_WARRANTY_NAME		= "REPLACE UNDER WARRANTY";
	public static final String	REPLACE_AFTER_WARRANTY_NAME		= "REPLACE AFTER WARRANTY";
	
	public static final short	PART_REPAIR_REPAIRABLE		= 1;
	public static final short	PART_REPAIR_IN_PROCESS		= 2;
	public static final short	PART_REPAIR_COMPLETED		= 3;
	
	
	public static final String	PART_REPAIR_REPAIRABLE_NAME			= "REPAIRABLE";
	public static final String	PART_REPAIR_IN_PROCESS_NAME			= "IN_PROCESS";
	public static final String	PART_REPAIR_COMPLETED_NAME			= "COMPLETE";
	
	public static final short	ASSIGNED_FROM_WO		= 2;
	public static final short	ASSIGNED_FROM_DSE		= 3;
	
	
	public static  String  getRepairStatus(short type) throws Exception {
		String statusString = null;
		switch (type) {
		case PART_REPAIR_REPAIRABLE:
			statusString = PART_REPAIR_REPAIRABLE_NAME;
			break;
		case PART_REPAIR_IN_PROCESS:
			statusString = PART_REPAIR_IN_PROCESS_NAME;
			break;
		case PART_REPAIR_COMPLETED:
			statusString = PART_REPAIR_COMPLETED_NAME;
			break;
		default:
			statusString = "--";
			break;
		}
		return statusString;
	} 
	
	
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	public static PartWarrantyDetails getPartWarrantyDetails(ValueObject	valueObject, ValueObject	object, 
				CustomUserDetails	userDetails, MasterParts	masterParts) throws Exception{
		PartWarrantyDetails		partWarrantyDetails		= null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			partWarrantyDetails	= new PartWarrantyDetails();
			
			partWarrantyDetails.setCompanyId(userDetails.getCompany_id());
			partWarrantyDetails.setPartId(valueObject.getLong("partId",0));
			partWarrantyDetails.setInventoryId(valueObject.getLong("inventoryId",0));
			partWarrantyDetails.setPartSerialNumber(object.getString("partSerialNumber","").trim());
			partWarrantyDetails.setLocationId(valueObject.getInt("locationId",0));
			partWarrantyDetails.setStatus(PART_WARRANTY_STATUS_UNASIGNED);
			partWarrantyDetails.setPartWarrantyStatusId(PART_WARRANTY_STATUS_UNASIGNED);
			partWarrantyDetails.setCreated(new Date());
			partWarrantyDetails.setCreatedById(userDetails.getId());
			partWarrantyDetails.setLastUpdated(new Date());
			partWarrantyDetails.setLastUpdatedById(userDetails.getId());
			partWarrantyDetails.setWarrantyEndDate(DateTimeUtility.addMonths(format.parse(valueObject.getString("invoiceDate")), masterParts.getWarrantyInMonths().intValue()));
			if(valueObject.getBoolean("repairStatusId",false)) {
				partWarrantyDetails.setRepairStatusId(PART_REPAIR_REPAIRABLE);
			}else {
				partWarrantyDetails.setRepairStatusId((short)0);
			}
			
			
			return partWarrantyDetails;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public  PartWarrantyDetails preparePartWarrantyDetails(ValueObject partValueObject ,ValueObject dseValueObject ) throws Exception{
		PartWarrantyDetails		partWarrantyDetails		= null;
		
		try {
			partWarrantyDetails	= new PartWarrantyDetails();
			
			partWarrantyDetails.setCompanyId(dseValueObject.getInt("companyId"));
			partWarrantyDetails.setPartId(partValueObject.getLong("partId",0));
			partWarrantyDetails.setInventoryId(partValueObject.getLong("inventoryId",0));
			partWarrantyDetails.setPartSerialNumber(partValueObject.getString("partSerialNumber","").trim());
			partWarrantyDetails.setLocationId(partValueObject.getInt("locationId",0));
			partWarrantyDetails.setPartWarrantyStatusId(UNASIGNED);
			partWarrantyDetails.setCreated(new Date());
			partWarrantyDetails.setCreatedById(dseValueObject.getLong("userId"));
			partWarrantyDetails.setLastUpdated(new Date());
			partWarrantyDetails.setLastUpdatedById(dseValueObject.getLong("userId"));
			java.util.Date date = dateFormat.parse(dseValueObject.getString("invoiceDate"));
			java.sql.Date asignDate = new java.sql.Date(date.getTime());
			java.sql.Date endDate = new java.sql.Date(DateTimeUtility.getDateAfterNumberOfMonths(date, partValueObject.getInt("warrantyInMonths")).getTime());
		//	partWarrantyDetails.setReplacePartWarrantyDetailsId(dseValueObject.getLong("dealerServiceEntriesId",0));
			partWarrantyDetails.setPartAsignDate(asignDate);
			partWarrantyDetails.setWarrantyEndDate(endDate);
			partWarrantyDetails.setVid(dseValueObject.getInt("vid"));
			partWarrantyDetails.setServiceId(dseValueObject.getLong("dealerServiceEntriesId"));
			partWarrantyDetails.setReplaceInServiceId(dseValueObject.getLong("replaceInServiceId",0));
			partWarrantyDetails.setServicePartId(partValueObject.getLong("dealerServiceEntriesPartId",0));
			
			return partWarrantyDetails;
		} catch (Exception e) {
			throw e;
		}
	}
}
