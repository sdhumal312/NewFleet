package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreInvoiceDto;
import org.fleetopgroup.persistence.model.UreaInvoice;
import org.fleetopgroup.persistence.model.UreaInvoiceToDetails;
import org.fleetopgroup.persistence.model.UreaManufacturer;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

public class UreaInventoryBL {

	public UreaInvoice	getUreaInvoiceDto(ValueObject	valueObject, MultipartFile file) throws Exception {
		UreaInvoice			ureaInvoice		= null;
		CustomUserDetails	userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ureaInvoice	= new UreaInvoice();
			
			ureaInvoice.setWareHouseLocation(valueObject.getInt("warehouselocation"));
			ureaInvoice.setPaymentTypeId(valueObject.getShort("paymentType"));
			ureaInvoice.setPaymentNumber(valueObject.getString("paymentNumber"));
			ureaInvoice.setPoNumber(valueObject.getString("poNumber"));
			ureaInvoice.setInvoiceNumber(valueObject.getString("invoiceNumber"));
			ureaInvoice.setInvoiceDate(DateTimeUtility.getDateTimeFromStr(valueObject.getString("invoiceDate"), DateTimeUtility.DD_MM_YYYY));
			ureaInvoice.setInvoiceAmount(valueObject.getDouble("invoiceAmount", 0));
			ureaInvoice.setTotalAmount(valueObject.getDouble("totalClothAmount", 0));
			ureaInvoice.setDescription(valueObject.getString("description"));
			ureaInvoice.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
			ureaInvoice.setLastModifiedBy(DateTimeUtility.getCurrentTimeStamp());
			ureaInvoice.setCreatedById(userDetails.getId());
			ureaInvoice.setLastModifiedById(userDetails.getId());
			ureaInvoice.setVendorId(valueObject.getInt("vendor", 0));
			ureaInvoice.setCompanyId(userDetails.getCompany_id());
			ureaInvoice.setQuantity(valueObject.getDouble("totalQuantity",0.0));
			ureaInvoice.setPurchaseOrderId(valueObject.getLong("purchaseOrderId",0));
			ureaInvoice.setTallyCompanyId(valueObject.getLong("tallyCompanyId",0));
			ureaInvoice.setTotalTransferQuantity(valueObject.getDouble("totalTransferQuantity",0.0));
			if(valueObject.getBoolean("createdFromPO")) {
				ureaInvoice.setVendorPaymentStatus(ureaInvoice.getPaymentTypeId());
			}else {
				if (ureaInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					ureaInvoice.setVendorPaymentStatus(InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);
				} else {
					ureaInvoice.setVendorPaymentStatus(InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_PAID);
					try {
						ureaInvoice.setVendorPaymentDate(DateTimeUtility.getCurrentTimeStamp());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if(file != null) {
				if (!file.isEmpty()) {
					ureaInvoice.setUrea_document(true);
				} else {
					ureaInvoice.setUrea_document(false);
				}
			} else {
				if (valueObject.getString("base64String",null)  != null) {
					ureaInvoice.setUrea_document(true);
				} else {
					ureaInvoice.setUrea_document(false);
				}
			}
			ureaInvoice.setSubLocationId(valueObject.getInt("subLocationId",0));
			return ureaInvoice;

		} catch (Exception e) {
			throw e;
		}
	}

	public UreaInvoiceToDetails getUreaInvoiceToDetails(ValueObject object, ValueObject inObject) throws Exception{
		UreaInvoiceToDetails					invoiceToDetails				= null;
		CustomUserDetails						userDetails					= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			invoiceToDetails	= new UreaInvoiceToDetails();
			
			invoiceToDetails.setUnitprice(object.getDouble("unitprice", 0));
			invoiceToDetails.setDiscount(object.getDouble("discount", 0));
			invoiceToDetails.setTax(object.getDouble("tax", 0));
			invoiceToDetails.setTotal(object.getDouble("tatalcost", 0));
			invoiceToDetails.setWareHouseLocation(inObject.getInt("warehouselocation", 0));
			invoiceToDetails.setUreaInvoiceId(inObject.getLong("invoiceId", 0));
			invoiceToDetails.setMarkForDelete(false);
			invoiceToDetails.setCompanyId(userDetails.getCompany_id());
			invoiceToDetails.setManufacturerId(object.getLong("manufacturer",0));
			invoiceToDetails.setQuantity(object.getDouble("quantity", 0));
			invoiceToDetails.setStockQuantity(object.getDouble("quantity", 0));
			invoiceToDetails.setUsedQuantity((double) 0);
			invoiceToDetails.setVendor_id(inObject.getInt("vendor", 0));
			invoiceToDetails.setCreatedById(userDetails.getId());
			invoiceToDetails.setLastModifiedById(userDetails.getId());
			invoiceToDetails.setCreated(new Date());
			invoiceToDetails.setLastupdated(new Date());
			invoiceToDetails.setSubLocationId(inObject.getInt("subLocationId",0));
			invoiceToDetails.setDiscountTaxTypeId(object.getShort("disTaxTypeId",(short)1));
			
			invoiceToDetails.setTransferQuantity(object.getDouble("transferQuantity", 0));
			invoiceToDetails.setUreaTransferDetailsId(inObject.getLong("ureaTransferDetailsId", 0));
			return invoiceToDetails;
			
		} catch (Exception e) {
			throw e;
		}finally {
			invoiceToDetails		= null;
			userDetails			= null;
		}
	}

	public UreaInvoiceToDetails saveUreaInvoiveDetails(Integer company_id, Timestamp sqlDate, long id,
			long manufacturerId, long quantity1, double discount1, double cost1, double tax1, double total1,
			long invoiceId, long vendorId, long locationId) {
		
		return null;
	}

	public UreaManufacturer	getUreaTypesModel(ValueObject	valueObject)throws Exception {
		
		UreaManufacturer				ureaTypes		= null;
		CustomUserDetails				userDetails		= null;

		java.util.Date lastModDate = new java.util.Date();
		Timestamp lastMod = new java.sql.Timestamp(lastModDate.getTime());

		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			ureaTypes	= new UreaManufacturer();
			
			ureaTypes.setManufacturerName(valueObject.getString("ureaTypeName").trim());
			ureaTypes.setDescription(valueObject.getString("description"));
			ureaTypes.setCompanyId(userDetails.getCompany_id());
			ureaTypes.setCreatedById(userDetails.getId());
			ureaTypes.setCreated(DateTimeUtility.getCurrentTimeStamp());
			ureaTypes.setLastModifiedById(userDetails.getId());
			ureaTypes.setLastModified(lastMod);
			
			return ureaTypes;
		} catch (Exception e) {
			throw e;
		}finally {
			ureaTypes		= null;
			userDetails		= null;
		}
	}
	
}
