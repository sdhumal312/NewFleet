package org.fleetopgroup.persistence.bl;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleTyreModelSubTypeDto;
import org.fleetopgroup.persistence.model.VehicleCostFixing;
import org.fleetopgroup.persistence.model.VehicleTyreModelSubType;
import org.fleetopgroup.web.util.DateTimeUtility;

public class VehicleCostFixingBL {
	
	public static final short	COST_TYPE_TYRE			= 1;
	public static final short	COST_TYPE_BATTERY		= 2;

	public static VehicleCostFixing	getVehicleCostFixing(VehicleTyreModelSubTypeDto	typeDto , CustomUserDetails	userDetails, VehicleTyreModelSubType	subType) {
		VehicleCostFixing		vehicleCostFixing		= null;
		try {
			
			vehicleCostFixing	= new VehicleCostFixing();
			
			vehicleCostFixing.setCompanyId(userDetails.getCompany_id());
			vehicleCostFixing.setVehicleCostFixingId(typeDto.getVehicleCostFixingId());
			vehicleCostFixing.setCostPerKM(typeDto.getCostPerKM());
			vehicleCostFixing.setTyreSubTypeId(subType.getTYRE_MST_ID());
			vehicleCostFixing.setCreatedById(userDetails.getId());
			vehicleCostFixing.setLastModifiedById(userDetails.getId());
			vehicleCostFixing.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
			vehicleCostFixing.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
			vehicleCostFixing.setCostType(COST_TYPE_TYRE);
			
			
			return vehicleCostFixing;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static VehicleCostFixing	getVehicleCostFixingForEdit(VehicleTyreModelSubTypeDto	typeDto , CustomUserDetails	userDetails, VehicleTyreModelSubType	subType) {
		VehicleCostFixing		vehicleCostFixing		= null;
		try {
			
			vehicleCostFixing	= new VehicleCostFixing();
			
			vehicleCostFixing.setCompanyId(userDetails.getCompany_id());
			vehicleCostFixing.setVehicleCostFixingId(typeDto.getVehicleCostFixingId());
			vehicleCostFixing.setCostPerKM(typeDto.getCostPerKM());
			vehicleCostFixing.setTyreSubTypeId(subType.getTYRE_MST_ID());
			vehicleCostFixing.setCreatedById(userDetails.getId());
			vehicleCostFixing.setLastModifiedById(userDetails.getId());
			vehicleCostFixing.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
			vehicleCostFixing.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
			vehicleCostFixing.setCostType(COST_TYPE_TYRE);
			
			
			return vehicleCostFixing;
		} catch (Exception e) {
			throw e;
		}
	}
}
