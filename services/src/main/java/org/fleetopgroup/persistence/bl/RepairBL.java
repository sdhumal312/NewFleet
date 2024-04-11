package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.fleetopgroup.constant.BatteryConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.persistence.dto.BatteryDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.InventoryTyreHistoryDto;
import org.fleetopgroup.persistence.dto.InventoryTyreRetreadAmountDto;
import org.fleetopgroup.persistence.dto.InventoryTyreRetreadDto;
import org.fleetopgroup.persistence.model.BatteryHistory;
import org.fleetopgroup.persistence.model.InventoryTyreHistory;
import org.fleetopgroup.persistence.model.InventoryTyreRetread;
import org.fleetopgroup.persistence.model.InventoryTyreRetreadAmount;
import org.fleetopgroup.persistence.model.RepairFromAssetPart;
import org.fleetopgroup.persistence.model.RepairStock;
import org.fleetopgroup.persistence.model.RepairStockToPartDetails;
import org.fleetopgroup.persistence.model.RepairToAssetPart;
import org.fleetopgroup.persistence.model.RepairToLabourDetails;
import org.fleetopgroup.persistence.model.RepairToStockDetails;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.security.core.context.SecurityContextHolder;

public class RepairBL {
	SimpleDateFormat 	dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	DecimalFormat 		toFixedTwo = new DecimalFormat("#.##");
	

	public RepairBL() {
		super();
	}

	public RepairStock prepareRepairStockInvoice(ValueObject valueObject) throws Exception {
		RepairStock repairStock = null;
		try {
			repairStock = new RepairStock();
			repairStock.setRepairStockId(valueObject.getLong("repairStockId"));
			repairStock.setRepairTypeId(valueObject.getShort("repairTypeId"));
			repairStock.setRepairWorkshopId(valueObject.getShort("repairWorkshopId"));
			repairStock.setVendorId(valueObject.getInt("vendorId",0));
			repairStock.setOpenDate(dateFormat.parse(valueObject.getString("createRepairInvoiceDate")));
			repairStock.setDateOfSent(dateFormat.parse(valueObject.getString("createRepairInvoiceDate")));
			repairStock.setRequiredDate(dateFormat.parse(valueObject.getString("repairStockRequiredDate")));
			repairStock.setRefNumber(valueObject.getString("referenceNumber","").trim());
			repairStock.setDescription(valueObject.getString("description","").trim());
			repairStock.setTotalRepairingCost(valueObject.getDouble("totalRepairingCost",0));
			repairStock.setLocationId(valueObject.getInt("locationId",0));
			repairStock.setRepairStatusId((short)1);
			repairStock.setPartDiscountTaxTypeId(valueObject.getShort("partDiscountTaxTypeId",(short)0));
			repairStock.setLabourDiscountTaxTypeId(valueObject.getShort("labourDiscountTaxTypeId",(short)0));
			repairStock.setCreatedById(valueObject.getLong("userId"));
			repairStock.setLastModifiedById(valueObject.getLong("userId"));
			repairStock.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			repairStock.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			repairStock.setCompanyId(valueObject.getInt("companyId"));
			repairStock.setAdditionalPartLocationId(valueObject.getInt("additionalPartLocationId",0));
			repairStock.setMarkForDelete(false);
			
			return repairStock;
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public RepairToStockDetails prepareRepairToStockDetails(ValueObject object,ValueObject valueObject) throws Exception {
		RepairToStockDetails repairToStockDetails = null;
		try {
			repairToStockDetails = new RepairToStockDetails();
			repairToStockDetails.setRepairToStockDetailsId(valueObject.getLong("repairToStockDetailsId"));
			repairToStockDetails.setRepairStockId(valueObject.getLong("repairStockId"));
			repairToStockDetails.setRepairStockPartId(object.getLong("repairStockPartId",0));
			repairToStockDetails.setRepairInWarranty(object.getBoolean("isRepairInWarranty"));
			repairToStockDetails.setRepairStockStatusId((short)0);
			repairToStockDetails.setWorkDetails(object.getString("workDetails"));
			if(object.getString("dateOfRemoved",null) != null) {
				repairToStockDetails.setDateOfRemoved(dateFormat.parse(object.getString("dateOfRemoved")));
			}
			repairToStockDetails.setCreatedById(valueObject.getLong("userId"));
			repairToStockDetails.setLastModifiedById(valueObject.getLong("userId"));
			repairToStockDetails.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			repairToStockDetails.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			repairToStockDetails.setCompanyId(valueObject.getInt("companyId"));
			repairToStockDetails.setInventoryLocationId(object.getLong("inventoryLocationId",0));
			repairToStockDetails.setMarkForDelete(false);
			
			return repairToStockDetails;
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public RepairStockToPartDetails prepareRepairStockToPartDetails(ValueObject object,ValueObject valueObject) throws Exception {
		RepairStockToPartDetails repairStockToPartDetails = null;
		try {
			repairStockToPartDetails = new RepairStockToPartDetails();
			repairStockToPartDetails.setRepairStockToPartDetailsId(valueObject.getLong("repairStockToPartDetailsId"));
			repairStockToPartDetails.setRepairToStockDetailsId(valueObject.getLong("repairToStockDetailsId"));
			repairStockToPartDetails.setPartId(object.getLong("partId"));
			repairStockToPartDetails.setQuantity(object.getDouble("partQty",0));
			repairStockToPartDetails.setUnitCost(object.getDouble("partUnitCost",0));
			repairStockToPartDetails.setGst(object.getDouble("partGst",0));
			repairStockToPartDetails.setDiscount(object.getDouble("partDiscount",0));
			repairStockToPartDetails.setTotalCost(object.getDouble("partTotalCost",0));
			repairStockToPartDetails.setInventoryLocationId(object.getLong("inventoryLocationId",0));
			repairStockToPartDetails.setCreatedById(valueObject.getLong("userId"));
			repairStockToPartDetails.setLastModifiedById(valueObject.getLong("userId"));
			repairStockToPartDetails.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			repairStockToPartDetails.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			repairStockToPartDetails.setCompanyId(valueObject.getInt("companyId"));
			repairStockToPartDetails.setMarkForDelete(false);
			
			return repairStockToPartDetails;
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public RepairToLabourDetails prepareRepairToLabourDetailsDetails(ValueObject object,ValueObject valueObject) throws Exception {
		RepairToLabourDetails repairToLabourDetails = null;
		try {
			repairToLabourDetails = new RepairToLabourDetails();
			repairToLabourDetails.setRepairToLabourDetailsId(valueObject.getLong("repairToLabourDetailsId"));
			repairToLabourDetails.setRepairStockId(valueObject.getLong("repairStockId"));
			repairToLabourDetails.setRepairToStockDetailsId(valueObject.getLong("repairToStockDetailsId",0));
			repairToLabourDetails.setLabourId(object.getLong("labourId"));
			repairToLabourDetails.setHours(object.getDouble("hours",0));
			repairToLabourDetails.setUnitCost(object.getDouble("unitCost",0));
			repairToLabourDetails.setGst(object.getDouble("gst",0));
			repairToLabourDetails.setDiscount(object.getDouble("discount",0));
			repairToLabourDetails.setTotalCost(object.getDouble("totalCost",0));
			repairToLabourDetails.setCreatedById(valueObject.getLong("userId"));
			repairToLabourDetails.setLastModifiedById(valueObject.getLong("userId"));
			repairToLabourDetails.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			repairToLabourDetails.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			repairToLabourDetails.setCompanyId(valueObject.getInt("companyId"));
			repairToLabourDetails.setMarkForDelete(false);
			
			return repairToLabourDetails;
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public InventoryTyreHistory prepareInventoryTyreHistory(InventoryTyreDto Tyre, String decription) {

		InventoryTyreHistory TyreHistory = new InventoryTyreHistory();

		TyreHistory.setTYRE_ID(Tyre.getTYRE_ID());
		TyreHistory.setTYRE_NUMBER(Tyre.getTYRE_NUMBER());

		TyreHistory.setVEHICLE_ID(0);
		//TyreHistory.setVEHICLE_REGISTRATION(null);

		TyreHistory.setPOSITION(null);
		TyreHistory.setAXLE(null);

		TyreHistory.setTYRE_STATUS_ID(InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_SCRAPED);
		TyreHistory.setOPEN_ODOMETER(null);
		TyreHistory.setTYRE_USEAGE(Tyre.getTYRE_USEAGE());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp Retread_DATE = new java.sql.Timestamp(currentDateUpdate.getTime());

		TyreHistory.setTYRE_ASSIGN_DATE(Retread_DATE);

		TyreHistory.setTYRE_COMMENT(decription);

		return TyreHistory;
	}
	
	public InventoryTyreRetread prepare_Retread_Tyre(ValueObject object) throws Exception {

		InventoryTyreRetread TyreRetread = new InventoryTyreRetread();

		//TyreRetread.setTRID(object.getLong("repairStockPartId"));
		TyreRetread.setTR_OPEN_DATE(DateTimeUtility.getCurrentTimeStamp());
		TyreRetread.setTR_VENDOR_ID(0);
		if (TyreRetread.getTR_PAYMENT_TYPE_ID() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
			TyreRetread.setTR_VENDOR_PAYMODE_STATUS_ID(InventoryTyreRetreadDto.TR_VENDOR_PAYMODE_STATUS_NOTPAID);
		} else {
			TyreRetread.setTR_VENDOR_PAYMODE_STATUS_ID(InventoryTyreRetreadDto.TR_VENDOR_PAYMODE_STATUS_PAID);
			try {
				TyreRetread.setTR_VENDOR_PAYMODE_DATE(DateTimeUtility.getCurrentTimeStamp());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		TyreRetread.setTR_DESCRIPTION(object.getString("rejectRemark"));

		TyreRetread.setTR_AMOUNT(0.0);

		TyreRetread.setTR_ROUNT_AMOUNT(0.0);
		TyreRetread.setTR_RECEIVE_TYRE(0);

		TyreRetread.setTR_STATUS_ID(InventoryTyreRetreadDto.INVENTORY_TYRE_TR_STATUS_OPEN);

		/** who Create this Issues get email id to user profile details */
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		TyreRetread.setCREATEDBYID(userDetails.getId());
		TyreRetread.setLASTMODIFIEDBYID(userDetails.getId());
		TyreRetread.setMarkForDelete(false);
		TyreRetread.setCOMPANY_ID(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp CREATED_DATE = new java.sql.Timestamp(currentDateUpdate.getTime());

		TyreRetread.setCREATED_DATE(CREATED_DATE);
		TyreRetread.setLASTUPDATED_DATE(CREATED_DATE);
		TyreRetread.setTR_SEND_TYRE(1);

		return TyreRetread;
	}
	
	public InventoryTyreRetreadAmount prepare_RetreadAmount_Tyre(InventoryTyreDto Tyre) {

		InventoryTyreRetreadAmount TyreRetread = new InventoryTyreRetreadAmount();

		TyreRetread.setTR_AMOUNT_ID(TyreRetread.getTR_AMOUNT_ID());

		TyreRetread.setTYRE_ID(Tyre.getTYRE_ID());
		// get Vendor id to Vendor name
		// show Tyre name
		TyreRetread.setTYRE_NUMBER(Tyre.getTYRE_NUMBER());
		//TyreRetread.setTYRE_SIZE(Tyre.getTYRE_SIZE());
		TyreRetread.setTYRE_SIZE_ID(Tyre.getTYRE_SIZE_ID());

		TyreRetread.setRETREAD_AMOUNT(0.0);
		TyreRetread.setRETREAD_COST(0.0);
		TyreRetread.setRETREAD_DISCOUNT(0.0);
		TyreRetread.setRETREAD_TAX(0.0);
		//TyreRetread.setTRA_STATUS("OPEN");
		TyreRetread.setTRA_STATUS_ID(InventoryTyreRetreadAmountDto.TRA_STATUS_OPEN);

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp UPDATED_DATE = new java.sql.Timestamp(currentDateUpdate.getTime());
		TyreRetread.setUPDATED_DATE(UPDATED_DATE);

		/** who Create this Issues get email id to user profile details */
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		//TyreRetread.setUPDATEDBY(userDetails.getEmail());
		TyreRetread.setUPDATEDBYID(userDetails.getId());
		TyreRetread.setCOMPANY_ID(userDetails.getCompany_id());

		return TyreRetread;
	}
	
	public InventoryTyreHistory prepare_InventoryTyreHistory_SENT_RETREAD(InventoryTyreDto Tyre) {

		InventoryTyreHistory TyreHistory = new InventoryTyreHistory();

		TyreHistory.setTYRE_ID(Tyre.getTYRE_ID());
		TyreHistory.setTYRE_NUMBER(Tyre.getTYRE_NUMBER());

		TyreHistory.setVEHICLE_ID(0);

		TyreHistory.setPOSITION(null);
		TyreHistory.setAXLE(null);

		TyreHistory.setTYRE_STATUS_ID(InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_SENT_RETREAD);
		TyreHistory.setOPEN_ODOMETER(null);
		TyreHistory.setTYRE_USEAGE(Tyre.getTYRE_USEAGE());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp Retread_DATE = new java.sql.Timestamp(currentDateUpdate.getTime());

		TyreHistory.setTYRE_ASSIGN_DATE(Retread_DATE);

		TyreHistory.setTYRE_COMMENT("Tyre Send to Retread Enter Created");

		return TyreHistory;
	}
	
	public BatteryHistory prepareBatteryHistoryToScrap(BatteryDto batteryDto, ValueObject object) throws Exception {

		BatteryHistory batteryHistory = new BatteryHistory();

		batteryHistory.setBatteryId(batteryDto.getBatteryId());
		batteryHistory.setBatterySerialNumber(batteryDto.getBatterySerialNumber());

		batteryHistory.setVid(0);

		batteryHistory.setLayoutPosition((short) 0);
		batteryHistory.setBatteryStatusId(BatteryConstant.BATTERY_ASIGNED_STATUS_IN_SCRAPED);
		batteryHistory.setOpenOdometer(null);
		batteryHistory.setBatteryUseage(batteryDto.getBatteryUsesOdometer());
		batteryHistory.setUsesNoOfDay(batteryDto.getUsesNoOfTime());
		batteryHistory.setBatteryAsignDate(DateTimeUtility.getCurrentTimeStamp());
		batteryHistory.setBatteryComment(object.getString("rejectRemark"));
		batteryHistory.setScrapreason(object.getString("rejectRemark"));

		return batteryHistory;
	}
	
	public RepairFromAssetPart prepareRepairFromAssetPart(ValueObject object) throws Exception {

		RepairFromAssetPart repairFromAssetPart = new RepairFromAssetPart();
		
	//	repairFromAssetPart.setRepairFromAssetPartId(object.getLong("assetId"));
		repairFromAssetPart.setAssetId(object.getLong("assetId"));
		repairFromAssetPart.setRepairToStockDetailsId(object.getLong("repairToStockDetailsId"));
		repairFromAssetPart.setCreatedById(object.getLong("userId"));
		repairFromAssetPart.setLastModifiedById(object.getLong("userId"));
		repairFromAssetPart.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
		repairFromAssetPart.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
		repairFromAssetPart.setCompanyId(object.getInt("companyId"));
		repairFromAssetPart.setMarkForDelete(false);
		

		return repairFromAssetPart;
	}
	
	public RepairToAssetPart prepareRepairToAssetPart(ValueObject object) throws Exception {

		RepairToAssetPart repairFromAssetPart = new RepairToAssetPart();
		
	//	repairFromAssetPart.setRepairFromAssetPartId(object.getLong("assetId"));
		repairFromAssetPart.setAssetId(object.getLong("assetId"));
		repairFromAssetPart.setRepairStockToPartDetailsId(object.getLong("repairToStockDetailsId"));
		repairFromAssetPart.setCreatedById(object.getLong("userId"));
		repairFromAssetPart.setLastModifiedById(object.getLong("userId"));
		repairFromAssetPart.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
		repairFromAssetPart.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
		repairFromAssetPart.setCompanyId(object.getInt("companyId"));
		repairFromAssetPart.setMarkForDelete(false);
		

		return repairFromAssetPart;
	}
}
