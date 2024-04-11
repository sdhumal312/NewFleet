/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.fleetopgroup.constant.TyreAssignmentConstant;
import org.fleetopgroup.persistence.dto.InventoryTyreHistoryDto;
import org.fleetopgroup.persistence.model.InventoryTyre;
import org.fleetopgroup.persistence.model.InventoryTyreHistory;
import org.fleetopgroup.persistence.model.VehicleModelTyreLayout;
import org.fleetopgroup.persistence.model.VehicleModelTyreLayoutPosition;
import org.fleetopgroup.persistence.model.VehicleTyreLayoutPosition;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;


public class VehicleModelTyreLayoutBL {

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	 DecimalFormat toFixedTwo = new DecimalFormat("#.##");

	public VehicleModelTyreLayoutBL() {
		super();
	}


	public VehicleModelTyreLayout prepareVehicleModelTyreLayout(ValueObject valueObject) throws Exception {
		try {
			VehicleModelTyreLayout vehicleModelTyreLayout = new VehicleModelTyreLayout();
			vehicleModelTyreLayout.setNumberOfAxle(valueObject.getInt("numberOfAxle",0));
			vehicleModelTyreLayout.setVehicleModelId(valueObject.getLong("vehicleModelId",0));
			vehicleModelTyreLayout.setFrontTyreModelId(valueObject.getInt("frontTryeModelId",0));
			vehicleModelTyreLayout.setRearTyreModelId(valueObject.getInt("rearTyreModelId",0));
			vehicleModelTyreLayout.setSpareTyreModelId(valueObject.getInt("spareTyreModelId",0));
			vehicleModelTyreLayout.setSpareTyrePresent(valueObject.getBoolean("isSpareTyrePresent",false));
			vehicleModelTyreLayout.setCreatedById(valueObject.getLong("userId"));
			vehicleModelTyreLayout.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			vehicleModelTyreLayout.setLastUpdatedById(valueObject.getLong("userId"));
			vehicleModelTyreLayout.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			vehicleModelTyreLayout.setCompanyId(valueObject.getInt("companyId"));
			vehicleModelTyreLayout.setDualTyrePresentAxle(valueObject.getString("dualTyrePresentAxle",""));
			vehicleModelTyreLayout.setMarkForDelete(false);
			return vehicleModelTyreLayout;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}


	public VehicleModelTyreLayoutPosition prepareVehicleModelTyreLayoutPosition(String position, Integer tyreModelId, ValueObject valueObject) throws Exception {
		
		try {
			VehicleModelTyreLayoutPosition vehicleModelTyreLayoutPosition = new VehicleModelTyreLayoutPosition();
			vehicleModelTyreLayoutPosition.setVehicleModelTyreLayoutId(valueObject.getLong("vehicleModelTyreLayoutId"));
			vehicleModelTyreLayoutPosition.setPosition(position);
			vehicleModelTyreLayoutPosition.setTyreModelId(tyreModelId);
			vehicleModelTyreLayoutPosition.setCreatedById(valueObject.getLong("userId"));
			vehicleModelTyreLayoutPosition.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			vehicleModelTyreLayoutPosition.setLastUpdatedById(valueObject.getLong("userId"));
			vehicleModelTyreLayoutPosition.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			vehicleModelTyreLayoutPosition.setCompanyId(valueObject.getInt("companyId"));
			vehicleModelTyreLayoutPosition.setMarkForDelete(false);
			vehicleModelTyreLayoutPosition.setAxle(Integer.parseInt(position.split("-")[1]));
			return vehicleModelTyreLayoutPosition;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}
	
	
	public InventoryTyreHistory prepareInventoryTyreHistory(ValueObject valueObject,ValueObject object) throws Exception {
		try {
			InventoryTyreHistory TyreHistory = new InventoryTyreHistory();
			
			TyreHistory.setTYRE_ID(object.getLong("newTyreId"));
			TyreHistory.setTYRE_NUMBER(object.getString("newTyreNumber",""));
			TyreHistory.setVEHICLE_ID(valueObject.getInt("vid"));
			TyreHistory.setPOSITION(object.getString("tyrePositionId").split("-")[0]);
			TyreHistory.setAXLE(object.getString("tyrePositionId").split("-")[1]);
			TyreHistory.setTYRE_STATUS_ID(InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_MOUNT);
			TyreHistory.setOPEN_ODOMETER(valueObject.getInt("vehicleOdometer",0));
			TyreHistory.setTYRE_USEAGE(0);
			
			java.util.Date date = dateFormat.parse(valueObject.getString("assignDate"));
			java.sql.Date assignDate = new java.sql.Date(date.getTime());
			TyreHistory.setTYRE_ASSIGN_DATE(assignDate);
			TyreHistory.setTYRE_COMMENT(InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_MOUNT_NAME);
			TyreHistory.setCOMPANY_ID(valueObject.getInt("companyId"));
			TyreHistory.setCreatedById(valueObject.getLong("userId"));
			TyreHistory.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
			TyreHistory.setTransactionTypeId(valueObject.getShort("transactionTypeId",(short)0));
			TyreHistory.setTransactionId(valueObject.getLong("transactionId",0));
			TyreHistory.setTransactionSubId(valueObject.getLong("transactionSubId",0));
			return TyreHistory;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	
	public VehicleTyreLayoutPosition prepareVehicleTyreLayoutPosition(ValueObject valueObject,ValueObject object) throws Exception {
		try {
			VehicleTyreLayoutPosition vehicleTyreLayoutPosition = new VehicleTyreLayoutPosition();
			vehicleTyreLayoutPosition.setLP_ID(object.getLong("LP_ID"));
			vehicleTyreLayoutPosition.setAlignment(object.getShort("alignmentId",(short)0));
			vehicleTyreLayoutPosition.setAXLE(Integer.parseInt(object.getString("tyrePositionId").split("-")[1]));
			vehicleTyreLayoutPosition.setCOMPANY_ID(valueObject.getInt("companyId"));
			vehicleTyreLayoutPosition.setKinpin(object.getShort("kinpin",(short)0));
			vehicleTyreLayoutPosition.setMarkForDelete(false);
			vehicleTyreLayoutPosition.setOldTyreId(object.getLong("oldTyreId",0));
			vehicleTyreLayoutPosition.setOldTyreMoveId(object.getShort("oldTyreMoveId",(short)0));
			vehicleTyreLayoutPosition.setPOSITION(object.getString("tyrePositionId",""));
			vehicleTyreLayoutPosition.setTYRE_ASSIGNED(true);
			vehicleTyreLayoutPosition.setTYRE_ID(object.getLong("newTyreId",0));
			vehicleTyreLayoutPosition.setTYRE_SERIAL_NO(object.getString("newTyreNumber",""));
			vehicleTyreLayoutPosition.setTyreTypeId(object.getShort("tyreTypeId",(short)0));
			vehicleTyreLayoutPosition.setVEHICLE_ID(valueObject.getInt("vid",0));
			vehicleTyreLayoutPosition.setTyreModelId(object.getInt("tyreModelId",0));
			vehicleTyreLayoutPosition.setTyreGauge(object.getInt("tyreGauge",0));
			vehicleTyreLayoutPosition.setRemark(valueObject.getString("remark",""));
			vehicleTyreLayoutPosition.setTransactionTypeId(valueObject.getShort("transactionTypeId",(short)0));
			vehicleTyreLayoutPosition.setTransactionId(valueObject.getLong("transactionId",0));
			vehicleTyreLayoutPosition.setTransactionSubId(valueObject.getLong("transactionSubId",0));
			return vehicleTyreLayoutPosition;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
/*public VehicleTyreLayout prepareMultipleVehicleTyreLayout(String position, Integer tyreModelId, ValueObject valueObject) throws Exception {
		
		try {
			VehicleTyreLayout Tyre = new VehicleTyreLayout();

			Tyre.setVEHICLE_ID(VEHICLE_ID);
			//Tyre.setVEHICLE_REGISTRATION(VEHICLE_REGIS);
			Tyre.setAXLE(i);

			Tyre.setTYRE_FRONT_SIZE_ID(layout.getTYRE_FRONT_SIZE_ID());
			Tyre.setTYRE_FRONT_PRESSURE(layout.getTYRE_FRONT_PRESSURE());
			Tyre.setTYRE_REAR_SIZE_ID(layout.getTYRE_REAR_SIZE_ID());
			Tyre.setTYRE_REAR_PRESSURE(layout.getTYRE_REAR_PRESSURE());
			
			Tyre.setCREATEDBYID(userDetails.getId());
			Tyre.setCOMPANY_ID(userDetails.getCompany_id());

			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			Tyre.setCREATED_DATE(toDate);
			VehicleTyreLayoutService.registerNewTyreLayout(Tyre);
			return Tyre;
		
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}*/
	
public VehicleTyreLayoutPosition prepareMultipleVehicleTyreLayoutPosition(String position, Integer tyreModelId, ValueObject valueObject) throws Exception {
		
		try {
			VehicleTyreLayoutPosition vehicleTyreLayoutPosition = new VehicleTyreLayoutPosition();
			
			vehicleTyreLayoutPosition.setAXLE(Integer.parseInt(position.split("-")[1]));
			vehicleTyreLayoutPosition.setCOMPANY_ID(valueObject.getInt("companyId",0));
			vehicleTyreLayoutPosition.setMarkForDelete(false);
			vehicleTyreLayoutPosition.setPOSITION(position);
			vehicleTyreLayoutPosition.setTYRE_ASSIGNED(false);
			vehicleTyreLayoutPosition.setVEHICLE_ID(valueObject.getInt("vid",0));
			vehicleTyreLayoutPosition.setTyreModelId(tyreModelId);
			return vehicleTyreLayoutPosition;
		
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}


public InventoryTyreHistory prepareInventoryTyreDismountHistory(ValueObject valueObject,InventoryTyre Tyre,ValueObject object) throws Exception {
	int usageKm	= 0;
	try {
		InventoryTyreHistory TyreHistory = new InventoryTyreHistory();

		TyreHistory.setTYRE_ID(Tyre.getTYRE_ID());
		TyreHistory.setTYRE_NUMBER(Tyre.getTYRE_NUMBER());
		TyreHistory.setVEHICLE_ID(valueObject.getInt("vid"));
		TyreHistory.setPOSITION(object.getString("tyrePositionId").split("-")[0]);
		TyreHistory.setAXLE(object.getString("tyrePositionId").split("-")[1]);
		TyreHistory.setTYRE_STATUS_ID(InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_DISMOUNT);
		TyreHistory.setOPEN_ODOMETER(valueObject.getInt("dismountOdometer",0));
		if(object.getString("tyrePositionId").equals("SP-0")) {
			TyreHistory.setTYRE_USEAGE(usageKm);
		}else {
			if (Tyre.getOPEN_ODOMETER() != null) {
				if (valueObject.getInt("dismountOdometer",0) > Tyre.getOPEN_ODOMETER()) {
					usageKm = valueObject.getInt("dismountOdometer",0) - Tyre.getOPEN_ODOMETER();
				}
			}
			TyreHistory.setTYRE_USEAGE(usageKm);

		}
		java.util.Date date = dateFormat.parse(valueObject.getString("dismountDate"));
		java.sql.Date dismountDate = new java.sql.Date(date.getTime());
		TyreHistory.setTYRE_ASSIGN_DATE(dismountDate);
		TyreHistory.setTYRE_COMMENT(valueObject.getString("remark"));
		TyreHistory.setCOMPANY_ID(valueObject.getInt("companyId"));
		TyreHistory.setCreatedById(valueObject.getLong("userId"));
		TyreHistory.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
		TyreHistory.setTyreGauge(valueObject.getInt("tyreGauge",0));
		return TyreHistory;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}

}

public InventoryTyreHistory prepareInventoryTyreRotationToHistory(ValueObject valueObject,InventoryTyre tyre,ValueObject object) throws Exception {
	int usageKm	= 0;
	try {
		InventoryTyreHistory TyreHistory = new InventoryTyreHistory();
		
		TyreHistory.setTYRE_ID(tyre.getTYRE_ID());
		TyreHistory.setTYRE_NUMBER(tyre.getTYRE_NUMBER());
		TyreHistory.setVEHICLE_ID(valueObject.getInt("vid"));
		TyreHistory.setPOSITION(object.getString("rotateFromPositionId").split("-")[0]);
		TyreHistory.setAXLE(object.getString("rotateFromPositionId").split("-")[1]);
		TyreHistory.setTYRE_STATUS_ID(InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_ROTATION);
		TyreHistory.setOPEN_ODOMETER(valueObject.getInt("vehicleOdometer",0));
		if(object.getString("rotateToPositionId").equals("SP-0")) {
			TyreHistory.setTYRE_USEAGE(usageKm);
		}else {
			if (tyre.getOPEN_ODOMETER() != null) {
				if (valueObject.getInt("vehicleOdometer",0) > tyre.getOPEN_ODOMETER()) {
					usageKm = valueObject.getInt("vehicleOdometer",0) - tyre.getOPEN_ODOMETER();
				}
			}
			TyreHistory.setTYRE_USEAGE(usageKm);

		}
		
		java.util.Date date = dateFormat.parse(valueObject.getString("assignDate"));
		java.sql.Date assignDate = new java.sql.Date(date.getTime());
		TyreHistory.setTYRE_ASSIGN_DATE(assignDate);
		TyreHistory.setTYRE_COMMENT("Rotate From "+object.getString("rotateToPositionId")+"");
		TyreHistory.setCOMPANY_ID(valueObject.getInt("companyId"));
		TyreHistory.setCreatedById(valueObject.getLong("userId"));
		TyreHistory.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
		return TyreHistory;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	
}

public InventoryTyreHistory prepareInventoryTyreRotationFromHistory(ValueObject valueObject,InventoryTyre tyre,ValueObject object) throws Exception {
	int usageKm	= 0;
	try {
		InventoryTyreHistory TyreHistory = new InventoryTyreHistory();
		
		TyreHistory.setTYRE_ID(tyre.getTYRE_ID());
		TyreHistory.setTYRE_NUMBER(tyre.getTYRE_NUMBER());
		TyreHistory.setVEHICLE_ID(valueObject.getInt("vid"));
		TyreHistory.setPOSITION(object.getString("rotateToPositionId").split("-")[0]);
		TyreHistory.setAXLE(object.getString("rotateToPositionId").split("-")[1]);
		TyreHistory.setTYRE_STATUS_ID(InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_ROTATION);
		TyreHistory.setOPEN_ODOMETER(valueObject.getInt("vehicleOdometer",0));
		if(object.getString("rotateToPositionId").equals("SP-0")) {
			TyreHistory.setTYRE_USEAGE(usageKm);
		}else {
			if (tyre.getOPEN_ODOMETER() != null) {
				if (valueObject.getInt("vehicleOdometer",0) > tyre.getOPEN_ODOMETER()) {
					usageKm = valueObject.getInt("vehicleOdometer",0) - tyre.getOPEN_ODOMETER();
				}
			}
			TyreHistory.setTYRE_USEAGE(usageKm);

		}
		java.util.Date date = dateFormat.parse(valueObject.getString("assignDate"));
		java.sql.Date assignDate = new java.sql.Date(date.getTime());
		TyreHistory.setTYRE_ASSIGN_DATE(assignDate);
		TyreHistory.setTYRE_COMMENT("Rotate From "+object.getString("rotateFromPositionId")+" ");
		TyreHistory.setCOMPANY_ID(valueObject.getInt("companyId"));
		TyreHistory.setCreatedById(valueObject.getLong("userId"));
		TyreHistory.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
		return TyreHistory;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	
}

public InventoryTyreHistory prepareOwnSpareAssignToHistory(ValueObject valueObject,InventoryTyre tyre,ValueObject object) throws Exception {
	int usageKm	= 0;
	try {
		InventoryTyreHistory TyreHistory = new InventoryTyreHistory();
		
		TyreHistory.setTYRE_ID(tyre.getTYRE_ID());
		TyreHistory.setTYRE_NUMBER(tyre.getTYRE_NUMBER());
		TyreHistory.setVEHICLE_ID(valueObject.getInt("vid"));
		TyreHistory.setPOSITION("SP");
		TyreHistory.setAXLE("0");
		if(object.getShort("oldTyreMoveId") == TyreAssignmentConstant.OLD_TYRE_MOVED_TO_SPARE) {
		TyreHistory.setTYRE_STATUS_ID(InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_ROTATION);
		}else {
			TyreHistory.setTYRE_STATUS_ID(InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_DISMOUNT);
		}
		TyreHistory.setOPEN_ODOMETER(valueObject.getInt("vehicleOdometer",0));
		if (tyre.getOPEN_ODOMETER() != null) {
			if (valueObject.getInt("dismountOdometer",0) > tyre.getOPEN_ODOMETER()) {
				usageKm = valueObject.getInt("dismountOdometer",0) - tyre.getOPEN_ODOMETER();
			}
		}
		TyreHistory.setTYRE_USEAGE(usageKm);
		
		java.util.Date date = dateFormat.parse(valueObject.getString("assignDate"));
		java.sql.Date assignDate = new java.sql.Date(date.getTime());
		TyreHistory.setTYRE_ASSIGN_DATE(assignDate);
		if(object.getShort("oldTyreMoveId") == TyreAssignmentConstant.OLD_TYRE_MOVED_TO_SPARE) {
		TyreHistory.setTYRE_COMMENT("Rotate From "+object.getString("tyrePositionId")+" ");
		}else {
			TyreHistory.setTYRE_COMMENT(TyreAssignmentConstant.getOldTyreMovedTo(object.getShort("oldTyreMoveId")));
			
		}
		TyreHistory.setCOMPANY_ID(valueObject.getInt("companyId"));
		TyreHistory.setCreatedById(valueObject.getLong("userId"));
		TyreHistory.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
		return TyreHistory;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	
}
public InventoryTyreHistory prepareOwnSpareAssignFromHistory(ValueObject valueObject,InventoryTyre tyre,ValueObject object) throws Exception {
	int usageKm	= 0;
	try {
		InventoryTyreHistory TyreHistory = new InventoryTyreHistory();
		
		TyreHistory.setTYRE_ID(tyre.getTYRE_ID());
		TyreHistory.setTYRE_NUMBER(tyre.getTYRE_NUMBER());
		TyreHistory.setVEHICLE_ID(valueObject.getInt("vid"));
		TyreHistory.setPOSITION(object.getString("tyrePositionId").split("-")[0]);
		TyreHistory.setAXLE(object.getString("tyrePositionId").split("-")[1]);
		TyreHistory.setTYRE_STATUS_ID(InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_ROTATION);
		TyreHistory.setOPEN_ODOMETER(valueObject.getInt("vehicleOdometer",0));
		if (tyre.getOPEN_ODOMETER() != null) {
			if (valueObject.getInt("dismountOdometer",0) > tyre.getOPEN_ODOMETER()) {
				usageKm = valueObject.getInt("dismountOdometer",0) - tyre.getOPEN_ODOMETER();
			}
		}
		TyreHistory.setTYRE_USEAGE(usageKm);
		
		java.util.Date date = dateFormat.parse(valueObject.getString("assignDate"));
		java.sql.Date assignDate = new java.sql.Date(date.getTime());
		TyreHistory.setTYRE_ASSIGN_DATE(assignDate);
		TyreHistory.setTYRE_COMMENT("Rotate From SP-0");
		TyreHistory.setCOMPANY_ID(valueObject.getInt("companyId"));
		TyreHistory.setCreatedById(valueObject.getLong("userId"));
		TyreHistory.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
		return TyreHistory;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	
}
}
