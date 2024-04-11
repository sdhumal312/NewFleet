package org.fleetopgroup.persistence.bl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.fleetopgroup.constant.InventoryStockTypeConstant;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.DayWiseInventoryStock;
import org.fleetopgroup.persistence.model.RefreshmentEntry;
import org.fleetopgroup.web.util.ValueObject;

public class RefreshmentEntriesBL {

	SimpleDateFormat	format = new SimpleDateFormat("dd-MM-yyyy");
	
	public RefreshmentEntry getRefreshmentEntry(ValueObject	valueObject) throws Exception{
		RefreshmentEntry		refreshmentEntry		= null;
		CustomUserDetails		userDetails				= null;
		try {
			userDetails		= (CustomUserDetails) valueObject.get("userDetails");
			refreshmentEntry = new RefreshmentEntry();
			
			refreshmentEntry.setVid(valueObject.getInt("vid",0));
			refreshmentEntry.setTripsheetId(valueObject.getLong("tripsheetId",0));
			refreshmentEntry.setPartid(valueObject.getLong("partid",0));
			refreshmentEntry.setPartLocationId(valueObject.getInt("locationId", 0));
			refreshmentEntry.setRouteId(valueObject.getInt("routeId",0));
			refreshmentEntry.setDriverId(valueObject.getInt("driverId",0));
			refreshmentEntry.setAsignmentDate(format.parse(valueObject.getString("refreshmentDate")));
			refreshmentEntry.setReturnQuantity(0.0);
			refreshmentEntry.setComment(valueObject.getString("comment"));
			refreshmentEntry.setCreatedById(userDetails.getId());
			refreshmentEntry.setLastModifiedById(userDetails.getId());
			refreshmentEntry.setCreated(new Date());
			refreshmentEntry.setLastupdated(new Date());
			refreshmentEntry.setCompanyId(userDetails.getCompany_id());
			refreshmentEntry.setInventoryId(valueObject.getLong("inventoryId",0));
			refreshmentEntry.setQuantity(valueObject.getDouble("quantity",0));
			refreshmentEntry.setUnitprice(valueObject.getDouble("unitprice",0));
			refreshmentEntry.setTotalAmount(valueObject.getDouble("totalcost",0));
			refreshmentEntry.setRefreshmentEntryNumber(valueObject.getLong("refreshmentEntryNumber",0));
			
			
			return refreshmentEntry;
		} catch (Exception e) {
			throw e;
		}finally {
			refreshmentEntry		= null;
			userDetails				= null;
		}
	}
	
	public DayWiseInventoryStock  getDayWiseInventoryStockDTO(ValueObject	valueObject) throws Exception{
		DayWiseInventoryStock		dayWiseInventoryStock			= null;
		try {
				dayWiseInventoryStock	= new DayWiseInventoryStock();
				
				dayWiseInventoryStock.setPartId(valueObject.getLong("partId",0));
				dayWiseInventoryStock.setLocationId(valueObject.getInt("locationId",0));
				dayWiseInventoryStock.setAddedQuantity(valueObject.getDouble("quantity",0));
				dayWiseInventoryStock.setUsedQuantity(0.0);
				dayWiseInventoryStock.setTransactionType(InventoryStockTypeConstant.STOCK_TYPE_CLOSING);
				dayWiseInventoryStock.setTransactionId(valueObject.getLong("transactionId", 0));
				dayWiseInventoryStock.setTransactionDate((Date) valueObject.get("transactionDate"));
				dayWiseInventoryStock.setCompanyId(valueObject.getInt("companyId",0));
				dayWiseInventoryStock.setCreatedOn(new Date());
				dayWiseInventoryStock.setLastUpdatedOn(new Date());
				
				
			
			return dayWiseInventoryStock;
			
		} catch (Exception e) {
			throw e;
		}finally {
			dayWiseInventoryStock			= null;
		}
	}
	
	public DayWiseInventoryStock  getDayWiseInventoryStockDTOForInsert(ValueObject	valueObject) throws Exception{
		DayWiseInventoryStock		dayWiseInventoryStock			= null;
		try {
				dayWiseInventoryStock	= new DayWiseInventoryStock();
				
				dayWiseInventoryStock.setPartId(valueObject.getLong("partId",0));
				dayWiseInventoryStock.setLocationId(valueObject.getInt("locationId",0));
				dayWiseInventoryStock.setAddedQuantity(valueObject.getDouble("addedQuantity",0));
				dayWiseInventoryStock.setUsedQuantity(valueObject.getDouble("usedQuantity",0));
				dayWiseInventoryStock.setTransactionType(valueObject.getShort("transactionType", (short) 0));
				dayWiseInventoryStock.setTransactionId(valueObject.getLong("transactionId", 0));
				dayWiseInventoryStock.setTransactionDate((Date) valueObject.get("transactionDate"));
				dayWiseInventoryStock.setTransactionNumberWithType(valueObject.getString("numberType"));
				dayWiseInventoryStock.setCompanyId(valueObject.getInt("companyId",0));
				dayWiseInventoryStock.setCreatedOn(new Date());
				dayWiseInventoryStock.setLastUpdatedOn(new Date());
				
			
			return dayWiseInventoryStock;
			
		} catch (Exception e) {
			throw e;
		}finally {
			dayWiseInventoryStock			= null;
		}
	}
}
