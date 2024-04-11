package org.fleetopgroup.persistence.bl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.constant.ClothInvoiceStockType;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.LaundryClothReceiveHistoryType;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.UpholsterySendLaundryInvoiceStatus;
import org.fleetopgroup.constant.UpholsteryTransferStatus;
import org.fleetopgroup.persistence.dto.ClothInvoiceDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreInvoiceDto;
import org.fleetopgroup.persistence.dto.VehicleClothInventoryHistoryDto;
import org.fleetopgroup.persistence.model.ClothInventoryDetails;
import org.fleetopgroup.persistence.model.ClothInventoryStockTypeDetails;
import org.fleetopgroup.persistence.model.ClothInvoice;
import org.fleetopgroup.persistence.model.InventoryDamageAndLostHistory;
import org.fleetopgroup.persistence.model.InventoryUpholsteryTransfer;
import org.fleetopgroup.persistence.model.LaundryClothReceiveHistory;
import org.fleetopgroup.persistence.model.SentLaundryClothDetails;
import org.fleetopgroup.persistence.model.UpholsterySendLaundryInvoice;
import org.fleetopgroup.persistence.model.VehicleClothInventoryDetails;
import org.fleetopgroup.persistence.model.VehicleClothInventoryHistory;
import org.fleetopgroup.persistence.model.VehicleClothMaxAllowedSetting;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

public class ClothInventoryBL {

	SimpleDateFormat	format	= new SimpleDateFormat("dd-MM-yyyy");
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	public ClothInvoice	getClothInvoiceDto(ValueObject	valueObject, MultipartFile file) throws Exception {
		ClothInvoice		clothInvoice		= null;
		CustomUserDetails	userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			clothInvoice	= new ClothInvoice();
			
			clothInvoice.setWareHouseLocation(valueObject.getInt("warehouselocation"));
			clothInvoice.setPaymentTypeId(valueObject.getShort("paymentType"));
			clothInvoice.setPaymentNumber(valueObject.getString("paymentNumber"));
			clothInvoice.setPoNumber(valueObject.getString("poNumber"));
			clothInvoice.setInvoiceNumber(valueObject.getString("invoiceNumber"));
			clothInvoice.setTallyCompanyId(valueObject.getLong("tallyCompanyId",0));
			clothInvoice.setInvoiceDate(DateTimeUtility.getDateTimeFromStr(valueObject.getString("invoiceDate"), DateTimeUtility.DD_MM_YYYY));
			clothInvoice.setInvoiceAmount(valueObject.getDouble("invoiceAmount", 0));
			clothInvoice.setBalanceAmount(valueObject.getDouble("invoiceAmount", 0));
			clothInvoice.setTotalClothAmount(valueObject.getDouble("totalClothAmount", 0));
			clothInvoice.setDescription(valueObject.getString("description"));
			clothInvoice.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
			clothInvoice.setLastModifiedBy(DateTimeUtility.getCurrentTimeStamp());
			clothInvoice.setCreatedById(userDetails.getId());
			clothInvoice.setLastModifiedById(userDetails.getId());
			clothInvoice.setVendorId(valueObject.getInt("vendor", 0));
			clothInvoice.setCompanyId(userDetails.getCompany_id());
			clothInvoice.setClothTypeId(valueObject.getShort("typeOfCloth"));
			clothInvoice.setQuantity(valueObject.getDouble("totalQuantity",0.0));
			if (clothInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
				clothInvoice.setVendorPaymentStatus(InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);
			} else {
				clothInvoice.setVendorPaymentStatus(InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_PAID);
				try {
					clothInvoice.setVendorPaymentDate(DateTimeUtility.getCurrentTimeStamp());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(file != null) {
				if (!file.isEmpty()) {
					clothInvoice.setCloth_document(true);
				} else {
					clothInvoice.setCloth_document(false);
				}
			} else {
				if (valueObject.getString("base64String",null)  != null) {
					clothInvoice.setCloth_document(true);
				} else {
					clothInvoice.setCloth_document(false);
				}
			}
			clothInvoice.setSubLocationId(valueObject.getInt("subLocationId",0));
			return clothInvoice;

		} catch (Exception e) {
			throw e;
		}
	}
	
	public ClothInventoryDetails getClothInventoryDetails(ValueObject object, ValueObject inObject) throws Exception{
		ClothInventoryDetails					batteryAmount				= null;
		CustomUserDetails						userDetails					= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			batteryAmount	= new ClothInventoryDetails();
			
			batteryAmount.setUnitprice(object.getDouble("unitprice", 0));
			batteryAmount.setDiscount(object.getDouble("discount", 0));
			batteryAmount.setTax(object.getDouble("tax", 0));
			batteryAmount.setTotal(object.getDouble("tatalcost", 0));
			batteryAmount.setWareHouseLocation(inObject.getInt("warehouselocation", 0));
			batteryAmount.setClothInvoiceId(inObject.getLong("invoiceId", 0));
			batteryAmount.setMarkForDelete(false);
			batteryAmount.setCompanyId(userDetails.getCompany_id());
			batteryAmount.setClothTypesId(object.getLong("clothTypes"));
			batteryAmount.setQuantity(object.getDouble("quantity", 0));
			batteryAmount.setVendor_id(object.getInt("vendor", 0));
			batteryAmount.setCreatedById(userDetails.getId());
			batteryAmount.setLastModifiedById(userDetails.getId());
			batteryAmount.setCreated(new Date());
			batteryAmount.setLastupdated(new Date());
			batteryAmount.setSubLocationId(inObject.getInt("subLocationId",0));
			batteryAmount.setDiscountTaxTypeId(object.getShort("disTaxTypeId",(short)1));
			
			return batteryAmount;
			
		} catch (Exception e) {
			throw e;
		}finally {
			batteryAmount		= null;
			userDetails			= null;
		}
	}
	
	public SentLaundryClothDetails getSentLaundryClothDetails(ValueObject object, ValueObject inObject) throws Exception{ 
		SentLaundryClothDetails					batteryAmount				= null;
		CustomUserDetails						userDetails					= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			batteryAmount	= new SentLaundryClothDetails();
			
			batteryAmount.setClothTypesId(object.getLong("clothTypes"));
			
			batteryAmount.setLaundryInvoiceId(inObject.getLong("invoiceId"));
			batteryAmount.setQuantity(object.getDouble("quantity", 0));
			batteryAmount.setReceivedQuantity(0.0);
			batteryAmount.setLosedQuantity(0.0);
			batteryAmount.setDamagedQuantity(0.0);
			batteryAmount.setClothEachCost(object.getDouble("unitprice", 0));
			batteryAmount.setClothDiscount(object.getDouble("discount", 0));
			batteryAmount.setClothGst(object.getDouble("tax", 0));
			batteryAmount.setClothTotal(object.getDouble("totalcost", 0));
			batteryAmount.setCreationDate(new Date());
			batteryAmount.setLastUpdated(new Date());
			batteryAmount.setCompanyId(userDetails.getCompany_id());
			batteryAmount.setCreatedById(userDetails.getId());
			batteryAmount.setLastModifiedById(userDetails.getId());
			
			return batteryAmount;
			
		} catch (Exception e) {
			throw e;
		}finally {
			batteryAmount		= null;
			userDetails			= null;
		}
	}
	
	
	
	public VehicleClothInventoryDetails getVehicleClothInventoryDetailsDto(ValueObject	valueObject, VehicleClothInventoryDetails	details) throws Exception{
		
		VehicleClothInventoryDetails		vehicleClothInventoryDetails		= null;
		CustomUserDetails					userDetails							= null;
		try {
			
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			
			if(details != null) {
				vehicleClothInventoryDetails	= details;
				vehicleClothInventoryDetails.setLastModifiedById(userDetails.getId());
				vehicleClothInventoryDetails.setLastModifiedOn(new Date());
				vehicleClothInventoryDetails.setQuantity(valueObject.getDouble("quantity") + details.getQuantity());

			}else {
				vehicleClothInventoryDetails	= new VehicleClothInventoryDetails();
				
				vehicleClothInventoryDetails.setClothTypesId(valueObject.getLong("clothTypes",0));
				vehicleClothInventoryDetails.setVid(valueObject.getInt("vid"));
				vehicleClothInventoryDetails.setQuantity(valueObject.getDouble("quantity"));
				vehicleClothInventoryDetails.setCreatedById(userDetails.getId());
				vehicleClothInventoryDetails.setLastModifiedById(userDetails.getId());
				vehicleClothInventoryDetails.setCompanyId(userDetails.getCompany_id());
				vehicleClothInventoryDetails.setCreatedOn(new Date());
				vehicleClothInventoryDetails.setLastModifiedOn(new Date());
				vehicleClothInventoryDetails.setLocationId(valueObject.getInt("locationId"));
			}
			
			
			
			return vehicleClothInventoryDetails;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleClothInventoryDetails		= null;
			userDetails							= null;
		}
	}
	
	public VehicleClothInventoryHistory getVehicleClothInventoryHistory(ValueObject 	valueObject) throws Exception{
		VehicleClothInventoryHistory		vehicleClothInventoryHistory	= null;
		CustomUserDetails					userDetails						= null;
		VehicleClothInventoryHistoryDto		preVehicleClothInventoryHistory	= null;
		
		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			preVehicleClothInventoryHistory = (VehicleClothInventoryHistoryDto)valueObject.get("vehicleClothInventoryPreHistory");
			vehicleClothInventoryHistory	= new VehicleClothInventoryHistory();
			
			if(preVehicleClothInventoryHistory != null ) {// Previous Quantity and Previous Date
				vehicleClothInventoryHistory.setPreAsignedQuantity(preVehicleClothInventoryHistory.getQuantity());
				vehicleClothInventoryHistory.setPreAsignedDate(preVehicleClothInventoryHistory.getCreatedOn());
			}else {//New Fresh Entry Pre-Quantity and Pre-Date As same as current Quantity and Date respectively 
				vehicleClothInventoryHistory.setPreAsignedQuantity(valueObject.getDouble("quantity"));
				vehicleClothInventoryHistory.setPreAsignedDate(new Date());
			}
			vehicleClothInventoryHistory.setClothTypesId(valueObject.getLong("clothTypes",0));
			vehicleClothInventoryHistory.setVid(valueObject.getInt("vid"));
			vehicleClothInventoryHistory.setQuantity(valueObject.getDouble("quantity"));
			vehicleClothInventoryHistory.setCreatedById(userDetails.getId());
			vehicleClothInventoryHistory.setCompanyId(userDetails.getCompany_id());
			vehicleClothInventoryHistory.setCreatedOn(new Date());
			vehicleClothInventoryHistory.setLocationId(valueObject.getInt("locationId"));
			vehicleClothInventoryHistory.setStockTypeId(valueObject.getShort("typeOfCloth"));
			vehicleClothInventoryHistory.setAsignType(ClothInvoiceStockType.CLOTH_ASIGN_TYPE_ASIGN);
			
			return vehicleClothInventoryHistory;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleClothInventoryHistory	= null;
			userDetails						= null;
		}
	}
	
	public VehicleClothInventoryHistory getRemoveVehicleClothInventoryHistory(ValueObject 	valueObject) throws Exception{
		VehicleClothInventoryHistory		vehicleClothInventoryHistory	= null;
		VehicleClothInventoryHistoryDto		preVehicleClothInventoryHistory	= null;
		CustomUserDetails					userDetails						= null;
		try {
			preVehicleClothInventoryHistory = (VehicleClothInventoryHistoryDto)valueObject.get("vehicleClothInventoryPreHistory");
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			
			vehicleClothInventoryHistory	= new VehicleClothInventoryHistory();
			
			if(preVehicleClothInventoryHistory != null ) {// Previous Quantity and Previous Date
				vehicleClothInventoryHistory.setPreAsignedQuantity(preVehicleClothInventoryHistory.getQuantity());
				vehicleClothInventoryHistory.setPreAsignedDate(preVehicleClothInventoryHistory.getCreatedOn());
			}else {//New Fresh Entry Pre-Quantity and Pre-Date As same as current Quantity and Date respectively 
				vehicleClothInventoryHistory.setPreAsignedQuantity(valueObject.getDouble("removeQuantity"));
				vehicleClothInventoryHistory.setPreAsignedDate(new Date());
			}
			
			vehicleClothInventoryHistory.setClothTypesId(valueObject.getLong("clothTypesId",0));
			vehicleClothInventoryHistory.setVid(valueObject.getInt("vid"));
			vehicleClothInventoryHistory.setQuantity(valueObject.getDouble("removeQuantity"));
			vehicleClothInventoryHistory.setCreatedById(userDetails.getId());
			vehicleClothInventoryHistory.setCompanyId(userDetails.getCompany_id());
			vehicleClothInventoryHistory.setCreatedOn(new Date());
			vehicleClothInventoryHistory.setLocationId(valueObject.getInt("removelocationId"));
			vehicleClothInventoryHistory.setStockTypeId(ClothInvoiceStockType.STOCK_TYPE_OLD);
			vehicleClothInventoryHistory.setAsignType(ClothInvoiceStockType.CLOTH_ASIGN_TYPE_REMOVE);
			
			return vehicleClothInventoryHistory;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleClothInventoryHistory	= null;
			userDetails						= null;
		}
	}
	
	
	public UpholsterySendLaundryInvoice	getUpholsterySendLaundryInvoice(ValueObject	valueObject) throws Exception {
		UpholsterySendLaundryInvoice		clothInvoice		= null;
		CustomUserDetails					userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			clothInvoice	= new UpholsterySendLaundryInvoice();
			
			clothInvoice.setWareHouseLocationId(valueObject.getInt("warehouselocation"));
			clothInvoice.setPaymentTypeId(valueObject.getShort("paymentType"));
			clothInvoice.setPaymentNumber(valueObject.getString("paymentNumber"));
			clothInvoice.setDescription(valueObject.getString("description"));
			clothInvoice.setCreatedById(userDetails.getId());
			clothInvoice.setLastModifiedById(userDetails.getId());
			clothInvoice.setVendorId(valueObject.getInt("vendorId", 0));
			clothInvoice.setCompanyId(userDetails.getCompany_id());
			clothInvoice.setTallyCompanyId(valueObject.getLong("tallyCompanyId",0));
			
			clothInvoice.setTotalQuantity(valueObject.getDouble("totalQuantity",0));
			clothInvoice.setReceivedQuantity(0.0);
			clothInvoice.setLosedQuantity(0.0);
			clothInvoice.setDamagedQuantity(0.0);
			clothInvoice.setTotalCost(valueObject.getDouble("totalInvoiceCost", 0));
			clothInvoice.setSentDate(format.parse(valueObject.getString("sentDate")));
			if(!valueObject.getString("receiveDate").equalsIgnoreCase(""))
				clothInvoice.setExpectedReceiveDate(format.parse(valueObject.getString("receiveDate")));
			clothInvoice.setQuoteNumber(valueObject.getString("quoteNumber"));
			clothInvoice.setManualNumber(valueObject.getString("manualNumber"));
			clothInvoice.setCreationDate(new Date());
			clothInvoice.setLastModifiedDate(new Date());
			clothInvoice.setLaundryInvoiceStatus(UpholsterySendLaundryInvoiceStatus.LAUNDRY_INVOICE_STATUS_OPEN);
			if(clothInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
				clothInvoice.setVendorPaymentStatus(InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);
			}
			
			return clothInvoice;

		} catch (Exception e) {
			throw e;
		}
	}
	
	public UpholsterySendLaundryInvoice	getUpholsterySendLaundryInvoiceToUpdate(ValueObject	valueObject) throws Exception {
		UpholsterySendLaundryInvoice		clothInvoice		= null;
		CustomUserDetails					userDetails			= null;
		try {
			userDetails		= (CustomUserDetails) valueObject.get("userDetails");
			clothInvoice	= (UpholsterySendLaundryInvoice) valueObject.get("laundryInvoice");
			
			clothInvoice.setWareHouseLocationId(valueObject.getInt("warehouselocation"));
			clothInvoice.setPaymentTypeId(valueObject.getShort("paymentType"));
			clothInvoice.setPaymentNumber(valueObject.getString("paymentNumber"));
			clothInvoice.setDescription(valueObject.getString("description"));
			clothInvoice.setCreatedById(userDetails.getId());
			clothInvoice.setLastModifiedById(userDetails.getId());
			clothInvoice.setVendorId(valueObject.getInt("vendorId", 0));
			clothInvoice.setCompanyId(userDetails.getCompany_id());
			clothInvoice.setTallyCompanyId(valueObject.getLong("tallyCompanyId",0));
			clothInvoice.setSentDate(format.parse(valueObject.getString("sentDate")));
			if(!valueObject.getString("receiveDate").equalsIgnoreCase(""))
				clothInvoice.setExpectedReceiveDate(format.parse(valueObject.getString("receiveDate")));
			clothInvoice.setQuoteNumber(valueObject.getString("quoteNumber"));
			clothInvoice.setManualNumber(valueObject.getString("manualNumber"));
			clothInvoice.setCreationDate(new Date());
			clothInvoice.setLastModifiedDate(new Date());
			if(clothInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
				clothInvoice.setVendorPaymentStatus(InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);
			}
			
			return clothInvoice;

		} catch (Exception e) {
			throw e;
		}
	}
	
	public LaundryClothReceiveHistory	getLaundryClothReceiveHistory(ValueObject	valueObject) throws Exception {
		LaundryClothReceiveHistory			history				= null;
		CustomUserDetails					userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			history	= new LaundryClothReceiveHistory();
			
			history.setLaundryInvoiceId(valueObject.getLong("laundryInvoiceId",0));
			history.setLaundryClothDetailsId(valueObject.getLong("laundryClothDetailsId", 0));
			history.setReceiveQuantity(valueObject.getDouble("receiveQuantity", 0));
			history.setReceiveDate(DateTimeUtility.getTimeStamp(valueObject.getString("receiveDate"), DateTimeUtility.DD_MM_YYYY));
			history.setReceiveById(userDetails.getId());
			history.setSystemDateTime(DateTimeUtility.getCurrentTimeStamp());
			history.setDescription(valueObject.getString("receiveDescription"));
			history.setCompanyId(userDetails.getCompany_id());
			history.setClothTypesId(valueObject.getLong("clothTypesId", 0));
			history.setDamagedQuantity(valueObject.getDouble("receiveDmgQuantity",0));
			history.setLosedQuantity(valueObject.getDouble("receiveLosedQuantity",0));
			history.setReceiveTypeId(LaundryClothReceiveHistoryType.LAUNDRY_RECEIVE_TYPE_RECEIVED);
			
			return history;

		} catch (Exception e) {
			throw e;
		}
	}
	
	public VehicleClothMaxAllowedSetting getVehicleClothMaxAllowed(ValueObject object, ValueObject inObject) throws Exception{
		VehicleClothMaxAllowedSetting			maxAllowed				= null;
		CustomUserDetails						userDetails				= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			maxAllowed	= new VehicleClothMaxAllowedSetting();
			
			maxAllowed.setVid(inObject.getInt("vehicleId",0));
			maxAllowed.setClothTypesId(object.getLong("clothTypesId",0));
			maxAllowed.setMaxAllowedQuantity(object.getDouble("maxAllowedQuantity",0));
			maxAllowed.setRemark(object.getString("remark",null));
			maxAllowed.setCompanyId(userDetails.getCompany_id());
			maxAllowed.setCreatedById(userDetails.getId());
			maxAllowed.setLastModifiedById(userDetails.getId());
			maxAllowed.setCreationDate(new Date());
			maxAllowed.setLastUpdatedDate(new Date());
			maxAllowed.setMarkForDelete(false);
			
			return maxAllowed;
			
		} catch (Exception e) {
			throw e;
		}finally {
			maxAllowed			= null;
			userDetails			= null;
		}
	}
	
	public LaundryClothReceiveHistory getDamageWashingQty(ValueObject	valueObject) throws Exception {
		LaundryClothReceiveHistory			history				= null;
		CustomUserDetails					userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			history	= new LaundryClothReceiveHistory();
			
			history.setLaundryInvoiceId(valueObject.getLong("damagelaundryInvoiceId",0));
			history.setLaundryClothDetailsId(valueObject.getLong("damagelaundryClothDetailsId", 0));
			history.setReceiveQuantity(valueObject.getDouble("receivedQty", 0));
			history.setReceiveDate(DateTimeUtility.getTimeStamp(valueObject.getString("damageReceiveDate"), DateTimeUtility.DD_MM_YYYY));
			history.setReceiveById(userDetails.getId());
			history.setSystemDateTime(DateTimeUtility.getCurrentTimeStamp());
			history.setDescription(valueObject.getString("damageDescription"));
			history.setCompanyId(userDetails.getCompany_id());
			history.setClothTypesId(valueObject.getLong("damageclothTypesId", 0));
			history.setDamagedQuantity(valueObject.getDouble("damageQuantity", 0));
			history.setLosedQuantity(valueObject.getDouble("damageLosedQuantity", 0));
			history.setReceiveTypeId(LaundryClothReceiveHistoryType.LAUNDRY_RECEIVE_TYPE_DAMAGED);
			
			return history;

		} catch (Exception e) {
			throw e;
		}
	}
	
	public LaundryClothReceiveHistory getLostWashingQty(ValueObject	valueObject) throws Exception {
		LaundryClothReceiveHistory			history				= null;
		CustomUserDetails					userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			history	= new LaundryClothReceiveHistory();
			
			history.setLaundryInvoiceId(valueObject.getLong("lostlaundryInvoiceId",0));
			history.setLaundryClothDetailsId(valueObject.getLong("lostlaundryClothDetailsId", 0));
			history.setReceiveQuantity(valueObject.getDouble("recvQty", 0));
			history.setReceiveDate(DateTimeUtility.getTimeStamp(valueObject.getString("lostReceiveDate"), DateTimeUtility.DD_MM_YYYY));
			history.setReceiveById(userDetails.getId());
			history.setSystemDateTime(DateTimeUtility.getCurrentTimeStamp());
			history.setDescription(valueObject.getString("lostDescription"));
			history.setCompanyId(userDetails.getCompany_id());
			history.setClothTypesId(valueObject.getLong("lostclothTypesId", 0));
			history.setDamagedQuantity(valueObject.getDouble("prevDmgQuantity", 0));
			history.setLosedQuantity(valueObject.getDouble("lostQuantity", 0));
			history.setReceiveTypeId(LaundryClothReceiveHistoryType.LAUNDRY_RECEIVE_TYPE_LOST);
			
			return history;

		} catch (Exception e) {
			throw e;
		}
	}
	
	public InventoryDamageAndLostHistory getInventoryDamageHistoryQty (ValueObject valueObject) throws Exception {
		CustomUserDetails					userDetails					= null;
		InventoryDamageAndLostHistory		inventoryDamageHistory		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			inventoryDamageHistory = new InventoryDamageAndLostHistory();
			
			inventoryDamageHistory.setClothTypesId(valueObject.getLong("damageClothTypes",0));
			inventoryDamageHistory.setWareHouseLocation(valueObject.getInt("damagelocationId",0));
			inventoryDamageHistory.setLaundryInvoiceId(null);
			inventoryDamageHistory.setDamagedQuantity(valueObject.getDouble("damageQuantity", 0)); 
			inventoryDamageHistory.setLosedQuantity((double)0);
			inventoryDamageHistory.setVendor_id(null);
			inventoryDamageHistory.setReceiveById(userDetails.getId());
			inventoryDamageHistory.setCompanyId(userDetails.getCompany_id());
			inventoryDamageHistory.setMarkForDelete(false);
			inventoryDamageHistory.setCreatedDate(new Date());
			inventoryDamageHistory.setStockTypeId(valueObject.getShort("damageTypeOfCloth"));
			inventoryDamageHistory.setRemark(valueObject.getString("damageRemark", null));
			inventoryDamageHistory.setLastModifiedById(userDetails.getId());
			inventoryDamageHistory.setLastModifiedOn(new Date());
			
			return inventoryDamageHistory;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public InventoryDamageAndLostHistory getInventoryLostHistoryQty (ValueObject valueObject) throws Exception {
		CustomUserDetails					userDetails					= null;
		InventoryDamageAndLostHistory		inventoryDamageHistory		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			inventoryDamageHistory = new InventoryDamageAndLostHistory();
			
			inventoryDamageHistory.setClothTypesId(valueObject.getLong("lostClothTypes",0));
			inventoryDamageHistory.setWareHouseLocation(valueObject.getInt("lostlocationId",0));
			inventoryDamageHistory.setLaundryInvoiceId(null);
			inventoryDamageHistory.setDamagedQuantity((double)0); 
			inventoryDamageHistory.setLosedQuantity(valueObject.getDouble("lostQuantity", 0));
			inventoryDamageHistory.setVendor_id(null);
			inventoryDamageHistory.setReceiveById(userDetails.getId());
			inventoryDamageHistory.setCompanyId(userDetails.getCompany_id());
			inventoryDamageHistory.setMarkForDelete(false);
			inventoryDamageHistory.setCreatedDate(new Date());
			inventoryDamageHistory.setStockTypeId(valueObject.getShort("lostTypeOfCloth"));
			inventoryDamageHistory.setRemark(valueObject.getString("lostRemark", null));
			inventoryDamageHistory.setLastModifiedById(userDetails.getId());
			inventoryDamageHistory.setLastModifiedOn(new Date());
			inventoryDamageHistory.setVid(valueObject.getInt("vehicleId",0));
			inventoryDamageHistory.setDriverId(valueObject.getInt("driverConductorId",0));
			
			return inventoryDamageHistory;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public InventoryDamageAndLostHistory saveInventDmgAndLosHistWhileSendingLaundry (ValueObject object, ValueObject inObject) throws Exception {
		CustomUserDetails					userDetails					= null;
		InventoryDamageAndLostHistory		inventoryDamageHistory		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			inventoryDamageHistory = new InventoryDamageAndLostHistory(); 
			
			inventoryDamageHistory.setClothTypesId(object.getLong("clothTypes"));
			inventoryDamageHistory.setWareHouseLocation(inObject.getInt("warehouselocation"));
			inventoryDamageHistory.setLaundryInvoiceId(inObject.getLong("invoiceId"));
			inventoryDamageHistory.setDamagedQuantity((double)0); 
			inventoryDamageHistory.setLosedQuantity((double)0);
			inventoryDamageHistory.setVendor_id(inObject.getInt("vendorId", 0));
			inventoryDamageHistory.setReceiveById(userDetails.getId());
			inventoryDamageHistory.setCompanyId(userDetails.getCompany_id());
			inventoryDamageHistory.setMarkForDelete(false);
			inventoryDamageHistory.setCreatedDate(new Date());
			inventoryDamageHistory.setStockTypeId(ClothInvoiceStockType.STOCK_TYPE_OLD);
			inventoryDamageHistory.setRemark(inObject.getString("description"));
			inventoryDamageHistory.setLastModifiedById(userDetails.getId());
			inventoryDamageHistory.setLastModifiedOn(new Date());
			
			return inventoryDamageHistory;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public ClothInventoryStockTypeDetails saveClothInventoryStockTypeDetails(ValueObject 	valueObject) throws Exception{
		ClothInventoryStockTypeDetails		clothStockTypeDetails			= null;
		CustomUserDetails					userDetails						= null;
		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			
			clothStockTypeDetails	= new ClothInventoryStockTypeDetails();

			clothStockTypeDetails.setUsedStockQuantity(valueObject.getDouble("removeQuantity"));
			clothStockTypeDetails.setNewStockQuantity((double)0);
			clothStockTypeDetails.setInServiceQuantity((double)0);
			clothStockTypeDetails.setInWashingQuantity((double)0);
			clothStockTypeDetails.setClothTypesId(valueObject.getLong("clothTypesId"));
			clothStockTypeDetails.setWareHouseLocationId(valueObject.getInt("removelocationId"));
			clothStockTypeDetails.setCompanyId(userDetails.getCompany_id());
			clothStockTypeDetails.setMarkForDelete(false);
			clothStockTypeDetails.setLosedQuantity((double)0);
			clothStockTypeDetails.setDamagedQuantity((double)0);
			
			return clothStockTypeDetails;
		} catch (Exception e) {
			throw e;
		}finally {
			clothStockTypeDetails			= null;
			userDetails						= null;
		}
	}
	
	public InventoryUpholsteryTransfer saveUpholsteryTransfer(ValueObject object, ValueObject valueObject) throws Exception{
		InventoryUpholsteryTransfer		upholsteryTransfer			= null;
		CustomUserDetails				userDetails					= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			upholsteryTransfer	= new InventoryUpholsteryTransfer();
			
			upholsteryTransfer.setClothTypesId(object.getLong("clothTypeId", 0));
			upholsteryTransfer.setFromLocationId(valueObject.getInt("fromLocationID", 0));
			upholsteryTransfer.setToLocationId(valueObject.getInt("toLocationId", 0));
			upholsteryTransfer.setQuantity(object.getDouble("stckQty",0));
			upholsteryTransfer.setTransferDate(new Date());
			upholsteryTransfer.setTransferById(userDetails.getId()); 
			upholsteryTransfer.setTransferReceivedById(valueObject.getLong("transferReceivedById", 0));
			upholsteryTransfer.setTransferReceivedDate(null);
			upholsteryTransfer.setTransferReceivedReason(null);
			upholsteryTransfer.setTransferViaId(valueObject.getShort("transferViaId"));
			upholsteryTransfer.setTransferReason(valueObject.getString("transferReasonId", null));
			upholsteryTransfer.setTransferStatusId(UpholsteryTransferStatus.TRANSFER_STATUS_TRANSFERED);
			upholsteryTransfer.setCompanyId(userDetails.getCompany_id());
			upholsteryTransfer.setCreatedById(userDetails.getId());
			upholsteryTransfer.setLastModifiedById(userDetails.getId());
			upholsteryTransfer.setMarkForDelete(false);
			upholsteryTransfer.setCreatedDate(new Date());
			upholsteryTransfer.setLastUpdatedDate(new Date());
			upholsteryTransfer.setStockTypeId(object.getShort("stockType"));
			
			return upholsteryTransfer;
			
		} catch (Exception e) {
			throw e;
		}finally {
			upholsteryTransfer			= null;
			userDetails					= null;
		}
	}
	
	public ClothInventoryStockTypeDetails updateNewAndOldStockQty (ValueObject valueObject) throws Exception{
		ClothInventoryStockTypeDetails		clothStockTypeDetails			= null;
		CustomUserDetails					userDetails						= null;
		short								stockType						= 0;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			stockType  			= valueObject.getShort("receiveStockTypeId");
			
			clothStockTypeDetails	= new ClothInventoryStockTypeDetails();

			if(stockType == ClothInvoiceStockType.STOCK_TYPE_NEW) {
				clothStockTypeDetails.setUsedStockQuantity((double)0); 
				clothStockTypeDetails.setNewStockQuantity(valueObject.getDouble("receiveQuantityId"));
			} else {
				clothStockTypeDetails.setUsedStockQuantity(valueObject.getDouble("receiveQuantityId")); 
				clothStockTypeDetails.setNewStockQuantity((double)0);
			}
			
			clothStockTypeDetails.setInServiceQuantity((double)0);
			clothStockTypeDetails.setInWashingQuantity((double)0);
			clothStockTypeDetails.setClothTypesId(valueObject.getLong("receiveClothTypeId"));
			clothStockTypeDetails.setWareHouseLocationId(valueObject.getInt("receivetoLocationId"));
			clothStockTypeDetails.setCompanyId(userDetails.getCompany_id());
			clothStockTypeDetails.setMarkForDelete(false);
			clothStockTypeDetails.setLosedQuantity((double)0);
			clothStockTypeDetails.setDamagedQuantity((double)0);
			clothStockTypeDetails.setInTransferQuantity((double)0);
			
			return clothStockTypeDetails;
		} catch (Exception e) {
			throw e;
		}finally {
			clothStockTypeDetails			= null;
			userDetails						= null;
		}
	}
	
	
public List<ClothInvoiceDto> prepareInventoryClothInvoiceList(List<ClothInvoiceDto> clothInvoiceList) throws Exception {
		
		try {
			if(clothInvoiceList != null ) {
				for(ClothInvoiceDto clothInvoice : clothInvoiceList) {
					
					clothInvoice.setVendorPaymentStatusStr(FuelVendorPaymentMode.getPaymentMode(clothInvoice.getVendorPaymentStatus()));
					clothInvoice.setPaymentType(PaymentTypeConstant.getPaymentTypeName(clothInvoice.getPaymentTypeId()));
					clothInvoice.setInvoiceDateStr(DateTimeUtility.getDateFromTimeStamp(clothInvoice.getInvoiceDate(), DateTimeUtility.DD_MM_YYYY));
					clothInvoice.setVendorId(clothInvoice.getVendorId());
					if(clothInvoice.getVendorName() == null)
						clothInvoice.setVendorName("--");
					
				}
			}
			return clothInvoiceList;
		} catch (Exception e) {
			throw e;
		}
	}

/*	public Map<Integer, List<VehicleClothInventoryHistoryDto>> getUpholsteryAssignListHM(List<VehicleClothInventoryHistoryDto> vehicleClothInventoryHistoryList) {
		Map<Integer, List<VehicleClothInventoryHistoryDto>> 			partWiseHM 	= null;
		List<VehicleClothInventoryHistoryDto>						upholsteryList	= null;
		List<VehicleClothInventoryHistoryDto>						upholsterynewList	= null;
		try {
			if(vehicleClothInventoryHistoryList != null) {
				partWiseHM 		= new HashMap<>();
				double removedQuantity = 0,asignQuantity = 0;
				for(VehicleClothInventoryHistoryDto dto : vehicleClothInventoryHistoryList) {
					if(dto.getAsignType() == ClothInvoiceStockType.CLOTH_ASIGN_TYPE_ASIGN) {
						System.err.println("assign) "+dto.getAsignType());
						 asignQuantity =+ dto.getQuantity();
						dto.setAsignedQuantity(asignQuantity);
					}else {
						dto.setAsignedQuantity(0.0);
					}
					if(dto.getAsignType() == ClothInvoiceStockType.CLOTH_ASIGN_TYPE_REMOVE) {
						System.err.println("REmove) "+dto.getAsignType() );
						 removedQuantity += dto.getQuantity();
						dto.setRemovedQuantity(removedQuantity);
						System.err.println("::"+dto.getRemovedQuantity());
					}else {
						dto.setRemovedQuantity(0.0);
					}
					upholsteryList	=  partWiseHM.get(dto.getVid());
					if(upholsteryList == null) {
						upholsteryList 			= new ArrayList<>();
					}
					upholsteryList.add(dto);
					partWiseHM.put(dto.getVid(), upholsteryList);
					//upholsterynewList.add((VehicleClothInventoryHistoryDto) partWiseHM.get(dto.getVid()));
					
					
					
					
				}
			}
		} catch (Exception e) {
			LOGGER.error("ERR"+e);
		}
		return partWiseHM;
	}*/
	public Map<Integer, Map<Long,VehicleClothInventoryHistoryDto>> getUpholsteryAssignHM(List<VehicleClothInventoryHistoryDto> vehicleClothInventoryHistoryList) {
		Map<Integer, Map<Long,VehicleClothInventoryHistoryDto>> 			vehicleWiseHM 				= null;
		Map<Long,VehicleClothInventoryHistoryDto>							clothWiseHM 				= null;
		VehicleClothInventoryHistoryDto										clothInventoryHistoryDto	= null;
		try {
			if(vehicleClothInventoryHistoryList != null) {
				vehicleWiseHM 		= new HashMap<>();
				for(VehicleClothInventoryHistoryDto dto : vehicleClothInventoryHistoryList) {
					
					clothWiseHM	=  vehicleWiseHM.get(dto.getVid());
					
					if(clothWiseHM == null) {//First Time This Will Be Null(VID HM)
						clothWiseHM 		= new HashMap<>();
						if(dto.getAsignType() == ClothInvoiceStockType.CLOTH_ASIGN_TYPE_ASIGN) {
							dto.setAsignedQuantity(dto.getQuantity());
							dto.setRemovedQuantity(0.0);
						}else {
							dto.setRemovedQuantity(dto.getQuantity());
							dto.setAsignedQuantity(0.0);
						}
					
						clothWiseHM.put(dto.getClothTypesId(),dto);//put Data In HM and Get The Key 
					} else {// Now This Block Will Be Execute Till Getting the next key (VID HM)
						clothInventoryHistoryDto	=  clothWiseHM.get(dto.getClothTypesId());
						
						if(clothInventoryHistoryDto == null) {//checking CLOTH HM ,first TIMe This Will Be Null
							if(dto.getAsignType() == ClothInvoiceStockType.CLOTH_ASIGN_TYPE_ASIGN) {
								dto.setAsignedQuantity(dto.getQuantity());
								dto.setRemovedQuantity(0.0);
							}else {
								dto.setRemovedQuantity(dto.getQuantity());
								dto.setAsignedQuantity(0.0);
							}
							
							clothWiseHM.put(dto.getClothTypesId(),dto);// put Date In HM and get the key(cloth HM)
						}else {// now This Block Will Be Execute until found the new key  (cloth HM)
							if(dto.getAsignType() == ClothInvoiceStockType.CLOTH_ASIGN_TYPE_ASIGN) {
								clothInventoryHistoryDto.setAsignedQuantity(dto.getQuantity() + clothInventoryHistoryDto.getAsignedQuantity());
							}else {
								clothInventoryHistoryDto.setRemovedQuantity(dto.getQuantity() + clothInventoryHistoryDto.getRemovedQuantity());
							}
						}
					}
					vehicleWiseHM.put(dto.getVid(), clothWiseHM);
				}
			}
		} catch (Exception e) {
			LOGGER.error("ERR"+e);
		}
		return vehicleWiseHM;
	}
	
	public InventoryUpholsteryTransfer preapreUpholsteryTransfer(ClothInventoryDetails inventoryDetails, ValueObject valueObject) throws Exception{
		InventoryUpholsteryTransfer		upholsteryTransfer			= null;
		CustomUserDetails				userDetails					= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			upholsteryTransfer	= new InventoryUpholsteryTransfer();
			
			upholsteryTransfer.setClothTypesId(inventoryDetails.getClothTypesId());
			upholsteryTransfer.setFromLocationId(inventoryDetails.getWareHouseLocation());
			upholsteryTransfer.setToLocationId(valueObject.getInt("toLocationId", 0));
			upholsteryTransfer.setQuantity(valueObject.getDouble("stckQty",0));
			upholsteryTransfer.setTransferDate(new Date());
			upholsteryTransfer.setTransferById(userDetails.getId()); 
			upholsteryTransfer.setTransferReceivedById(valueObject.getLong("transferReceivedById", 0));
			upholsteryTransfer.setTransferReceivedDate(null);
			upholsteryTransfer.setTransferReceivedReason(null);
			//upholsteryTransfer.setTransferViaId(valueObject.getShort("transferViaId"));
			//upholsteryTransfer.setTransferReason(valueObject.getString("transferReasonId", null));
			upholsteryTransfer.setTransferStatusId(UpholsteryTransferStatus.TRANSFER_STATUS_TRANSFERED);
			upholsteryTransfer.setCompanyId(userDetails.getCompany_id());
			upholsteryTransfer.setCreatedById(userDetails.getId());
			upholsteryTransfer.setLastModifiedById(userDetails.getId());
			upholsteryTransfer.setMarkForDelete(false);
			upholsteryTransfer.setCreatedDate(new Date());
			upholsteryTransfer.setLastUpdatedDate(new Date());
			upholsteryTransfer.setStockTypeId(valueObject.getShort("stockType"));
			upholsteryTransfer.setApprovalId(valueObject.getLong("approvalId",0));
			
			return upholsteryTransfer;
			
		} catch (Exception e) {
			throw e;
		}finally {
			upholsteryTransfer			= null;
			userDetails					= null;
		}
	}

}
