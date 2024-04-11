package org.fleetopgroup.persistence.bl;

import java.text.SimpleDateFormat;

import org.fleetopgroup.constant.TyreAssignmentConstant;
import org.fleetopgroup.persistence.dto.VehicleTyreLayoutPositionDto;
import org.fleetopgroup.persistence.model.TyreUsageHistory;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;

public class TyreUsageHistoryBL {
	public TyreUsageHistoryBL() {
	}

	SimpleDateFormat dateFormatonlyDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm ");

	
	public TyreUsageHistory prepareTyreMountUsageHistory(ValueObject valueObject,ValueObject object) throws Exception {
		try {
			TyreUsageHistory tyreUsageHistory = new TyreUsageHistory();
			
			tyreUsageHistory.setTyreId(object.getLong("newTyreId"));
			tyreUsageHistory.setVid(valueObject.getInt("vid"));
			tyreUsageHistory.setVehiclePreOdometer(valueObject.getInt("vehiclePreOdometer",0));
			tyreUsageHistory.setVehicleOdometer(valueObject.getInt("vehicleOdometer",0));
			tyreUsageHistory.setTyreUsage(valueObject.getInt("tyreUsage",0));
			tyreUsageHistory.setTransactionUsageTypeId(TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_MOUNT);
			tyreUsageHistory.setTransactionId(object.getLong("LP_ID"));
			tyreUsageHistory.setCompanyId(valueObject.getInt("companyId"));
			tyreUsageHistory.setCreatedById(object.getLong("userId"));
			tyreUsageHistory.setLastUpdatedById(object.getLong("userId"));
			tyreUsageHistory.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setMarkForDelete(false);
			return tyreUsageHistory;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public TyreUsageHistory prepareTyreDismountUsageHistory(ValueObject valueObject,ValueObject object) throws Exception {
		try {
			TyreUsageHistory tyreUsageHistory = new TyreUsageHistory();
			
			tyreUsageHistory.setTyreId(object.getLong("tyreId"));
			tyreUsageHistory.setVid(valueObject.getInt("vid"));
			tyreUsageHistory.setVehiclePreOdometer(valueObject.getInt("vehiclePreOdometer",0));
			tyreUsageHistory.setVehicleOdometer(valueObject.getInt("dismountOdometer",0));
			tyreUsageHistory.setTyreUsage(valueObject.getInt("tyreUsage",0));
			tyreUsageHistory.setTransactionUsageTypeId(TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_DISMOUNT);
			tyreUsageHistory.setTransactionId(object.getLong("LP_ID"));
			tyreUsageHistory.setCompanyId(valueObject.getInt("companyId"));
			tyreUsageHistory.setCreatedById(object.getLong("userId"));
			tyreUsageHistory.setLastUpdatedById(object.getLong("userId"));
			tyreUsageHistory.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setMarkForDelete(false);
			return tyreUsageHistory;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public TyreUsageHistory prepareTyreRotateFromUsageHistory(ValueObject valueObject,ValueObject object) throws Exception {
		try {
			TyreUsageHistory tyreUsageHistory = new TyreUsageHistory();
			
			tyreUsageHistory.setTyreId(object.getLong("tyreFromId",0));
			tyreUsageHistory.setVid(valueObject.getInt("vid"));
			tyreUsageHistory.setVehiclePreOdometer(valueObject.getInt("vehiclePreOdometer",0));
			tyreUsageHistory.setVehicleOdometer(valueObject.getInt("vehicleOdometer",0));
			tyreUsageHistory.setTyreUsage(valueObject.getInt("tyreUsage",0));
			tyreUsageHistory.setTransactionUsageTypeId(TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_ROTATE);
			tyreUsageHistory.setTransactionId(object.getLong("tyreFromLP_ID",0));
			tyreUsageHistory.setCompanyId(valueObject.getInt("companyId"));
			tyreUsageHistory.setCreatedById(object.getLong("userId"));
			tyreUsageHistory.setLastUpdatedById(object.getLong("userId"));
			tyreUsageHistory.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setMarkForDelete(false);
			return tyreUsageHistory;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public TyreUsageHistory prepareTyreRotateToUsageHistory(ValueObject valueObject,ValueObject object) throws Exception {
		try {
			TyreUsageHistory tyreUsageHistory = new TyreUsageHistory();
			
			tyreUsageHistory.setTyreId(object.getLong("tyreToId",0));
			tyreUsageHistory.setVid(valueObject.getInt("vid"));
			tyreUsageHistory.setVehiclePreOdometer(valueObject.getInt("vehiclePreOdometer",0));
			tyreUsageHistory.setVehicleOdometer(valueObject.getInt("vehicleOdometer",0));
			tyreUsageHistory.setTyreUsage(valueObject.getInt("tyreUsage",0));
			tyreUsageHistory.setTransactionUsageTypeId(TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_ROTATE);
			tyreUsageHistory.setTransactionId(object.getLong("tyreToLP_ID",0));
			tyreUsageHistory.setCompanyId(valueObject.getInt("companyId"));
			tyreUsageHistory.setCreatedById(object.getLong("userId"));
			tyreUsageHistory.setLastUpdatedById(object.getLong("userId"));
			tyreUsageHistory.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setMarkForDelete(false);
			return tyreUsageHistory;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public TyreUsageHistory prepareTripTyreUsageHistory(ValueObject valueObject,VehicleTyreLayoutPositionDto dto) throws Exception {
		try {
			TyreUsageHistory tyreUsageHistory = new TyreUsageHistory();
			
			tyreUsageHistory.setTyreId(dto.getTYRE_ID());
			tyreUsageHistory.setVid(valueObject.getInt("vid"));
			tyreUsageHistory.setVehiclePreOdometer(valueObject.getInt("vehiclePreOdometer",0));
			tyreUsageHistory.setVehicleOdometer(valueObject.getInt("tripClosingKM",0));
			tyreUsageHistory.setTyreUsage(valueObject.getInt("tyreUsage",0));
			tyreUsageHistory.setTransactionUsageTypeId(TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_TRIP);
			tyreUsageHistory.setTransactionId(valueObject.getLong("tripsheetId"));
			tyreUsageHistory.setCompanyId(valueObject.getInt("companyId"));
			tyreUsageHistory.setCreatedById(valueObject.getLong("userId"));
			tyreUsageHistory.setLastUpdatedById(valueObject.getLong("userId"));
			tyreUsageHistory.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setMarkForDelete(false);
			return tyreUsageHistory;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public TyreUsageHistory prepareFuelTyreUsageHistory(ValueObject valueObject,VehicleTyreLayoutPositionDto dto) throws Exception {
		try {
			TyreUsageHistory tyreUsageHistory = new TyreUsageHistory();
			
			tyreUsageHistory.setTyreId(dto.getTYRE_ID());
			tyreUsageHistory.setVid(valueObject.getInt("vid"));
			tyreUsageHistory.setVehiclePreOdometer(valueObject.getInt("vehiclePreOdometer",0));
			tyreUsageHistory.setVehicleOdometer(valueObject.getInt("fuel_meter",0));
			tyreUsageHistory.setTyreUsage(valueObject.getInt("tyreUsage",0));
			tyreUsageHistory.setTransactionUsageTypeId(TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_FUEL);
			tyreUsageHistory.setTransactionId(valueObject.getLong("SuccessId"));
			tyreUsageHistory.setCompanyId(valueObject.getInt("companyId"));
			tyreUsageHistory.setCreatedById(valueObject.getLong("userId"));
			tyreUsageHistory.setLastUpdatedById(valueObject.getLong("userId"));
			tyreUsageHistory.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setMarkForDelete(false);
			return tyreUsageHistory;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public TyreUsageHistory prepareDSETyreUsageHistory(ValueObject valueObject,VehicleTyreLayoutPositionDto dto) throws Exception {
		try {
			TyreUsageHistory tyreUsageHistory = new TyreUsageHistory();
			
			tyreUsageHistory.setTyreId(dto.getTYRE_ID());
			tyreUsageHistory.setVid(valueObject.getInt("vid"));
			tyreUsageHistory.setVehiclePreOdometer(valueObject.getInt("vehiclePreOdometer",0));
			tyreUsageHistory.setVehicleOdometer(valueObject.getInt("vehicleOdometer",0));
			tyreUsageHistory.setTyreUsage(valueObject.getInt("tyreUsage",0));
			tyreUsageHistory.setTransactionUsageTypeId(TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_DSE);
			tyreUsageHistory.setTransactionId(valueObject.getLong("dealerServiceEntriesId"));
			tyreUsageHistory.setCompanyId(valueObject.getInt("companyId"));
			tyreUsageHistory.setCreatedById(valueObject.getLong("userId"));
			tyreUsageHistory.setLastUpdatedById(valueObject.getLong("userId"));
			tyreUsageHistory.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setMarkForDelete(false);
			return tyreUsageHistory;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public TyreUsageHistory prepareUreaTyreUsageHistory(ValueObject valueObject,VehicleTyreLayoutPositionDto dto) throws Exception {
		try {
			TyreUsageHistory tyreUsageHistory = new TyreUsageHistory();
			
			tyreUsageHistory.setTyreId(dto.getTYRE_ID());
			tyreUsageHistory.setVid(valueObject.getInt("vid"));
			tyreUsageHistory.setVehiclePreOdometer(valueObject.getInt("vehiclePreOdometer",0));
			tyreUsageHistory.setVehicleOdometer(valueObject.getInt("ureaOdometer",0));
			tyreUsageHistory.setTyreUsage(valueObject.getInt("tyreUsage",0));
			tyreUsageHistory.setTransactionUsageTypeId(TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_UREA);
			tyreUsageHistory.setTransactionId(valueObject.getLong("ureaEntriesId"));
			tyreUsageHistory.setCompanyId(valueObject.getInt("companyId"));
			tyreUsageHistory.setCreatedById(valueObject.getLong("userId"));
			tyreUsageHistory.setLastUpdatedById(valueObject.getLong("userId"));
			tyreUsageHistory.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setMarkForDelete(false);
			return tyreUsageHistory;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public TyreUsageHistory prepareIssueTyreUsageHistory(ValueObject valueObject,VehicleTyreLayoutPositionDto dto) throws Exception {
		try {
			TyreUsageHistory tyreUsageHistory = new TyreUsageHistory();
			
			tyreUsageHistory.setTyreId(dto.getTYRE_ID());
			tyreUsageHistory.setVid(valueObject.getInt("vid"));
			tyreUsageHistory.setVehiclePreOdometer(valueObject.getInt("vehiclePreOdometer",0));
			tyreUsageHistory.setVehicleOdometer(valueObject.getInt("odometer",0));
			tyreUsageHistory.setTyreUsage(valueObject.getInt("tyreUsage",0));
			tyreUsageHistory.setTransactionUsageTypeId(TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_ISSUE);
			tyreUsageHistory.setTransactionId(valueObject.getLong("decryptIssueId"));
			tyreUsageHistory.setCompanyId(valueObject.getInt("companyId"));
			tyreUsageHistory.setCreatedById(valueObject.getLong("userId"));
			tyreUsageHistory.setLastUpdatedById(valueObject.getLong("userId"));
			tyreUsageHistory.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setMarkForDelete(false);
			return tyreUsageHistory;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public TyreUsageHistory prepareWOTyreUsageHistory(ValueObject valueObject,VehicleTyreLayoutPositionDto dto) throws Exception {
		try {
			TyreUsageHistory tyreUsageHistory = new TyreUsageHistory();
			
			tyreUsageHistory.setTyreId(dto.getTYRE_ID());
			tyreUsageHistory.setVid(valueObject.getInt("vid"));
			tyreUsageHistory.setVehiclePreOdometer(valueObject.getInt("vehiclePreOdometer",0));
			tyreUsageHistory.setVehicleOdometer(valueObject.getInt("woOdometer",0));
			tyreUsageHistory.setTyreUsage(valueObject.getInt("tyreUsage",0));
			tyreUsageHistory.setTransactionUsageTypeId(TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_WO);
			tyreUsageHistory.setTransactionId(valueObject.getLong("workOrderId"));
			tyreUsageHistory.setCompanyId(valueObject.getInt("companyId"));
			tyreUsageHistory.setCreatedById(valueObject.getLong("userId"));
			tyreUsageHistory.setLastUpdatedById(valueObject.getLong("userId"));
			tyreUsageHistory.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			tyreUsageHistory.setMarkForDelete(false);
			return tyreUsageHistory;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
