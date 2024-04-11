/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.PartInvoiceDto;
import org.fleetopgroup.persistence.model.PartInvoice;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author fleetop
 *
 */
public class PartInvoiceBL {

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat SQLdateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");

	public PartInvoiceBL() {
		super();
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
	public PartInvoiceDto Edit_PartInvoiceInventory(PartInvoiceDto partInvoiceDto) {

		PartInvoiceDto Part = new PartInvoiceDto();
		
		Part.setPartInvoiceId(partInvoiceDto.getPartInvoiceId());
		Part.setPartInvoiceNumber(partInvoiceDto.getPartInvoiceNumber());
		Part.setPartLocation(partInvoiceDto.getPartLocation());
		Part.setWareHouseLocation(partInvoiceDto.getWareHouseLocation());
		Part.setInvoiceNumber(partInvoiceDto.getInvoiceNumber());
		Part.setInvoiceAmount(partInvoiceDto.getInvoiceAmount());
		Part.setBalanceAmount(partInvoiceDto.getBalanceAmount());
		Part.setInvoiceDate(partInvoiceDto.getInvoiceDate());
		Part.setInvoiceDateOn(partInvoiceDto.getInvoiceDateOn());
		Part.setVendorId(partInvoiceDto.getVendorId());
		Part.setVendorName(partInvoiceDto.getVendorName());
		Part.setVendorLocation(partInvoiceDto.getVendorLocation());
		Part.setPaymentTypeId(partInvoiceDto.getPaymentTypeId());
		Part.setPaymentType(PaymentTypeConstant.getPaymentTypeName(partInvoiceDto.getPaymentTypeId()));
		Part.setDescription(partInvoiceDto.getDescription());
		Part.setCreatedOn(partInvoiceDto.getCreatedOn());
		Part.setLastUpdated_Date(partInvoiceDto.getLastUpdated_Date());
		Part.setCreatedById(partInvoiceDto.getCreatedById());
		Part.setLastModifiedById(partInvoiceDto.getLastModifiedById());
		Part.setAnyPartNumberAsign(partInvoiceDto.isAnyPartNumberAsign());
		Part.setTallyCompanyId(partInvoiceDto.getTallyCompanyId());
		Part.setTallyCompanyName(partInvoiceDto.getTallyCompanyName());
		Part.setSubLocationId(partInvoiceDto.getSubLocationId());
		Part.setSubLocation(partInvoiceDto.getSubLocation());

		return Part;
	}
	
public PartInvoice prepareDirectUpdatePartInvoiceFromPart(InventoryDto Inventory) throws Exception {
		
		CustomUserDetails	userDetails       = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Date 				currentDateUpdate = new Date();
		Timestamp 			toDate            = new java.sql.Timestamp(currentDateUpdate.getTime());
		PartInvoice 		part              = new PartInvoice();
		
		part.setPartInvoiceId(Inventory.getPartInvoiceId());
		part.setPartInvoiceNumber(Inventory.getPartInvoiceNumber());
		part.setWareHouseLocation(Inventory.getLocationId());
		part.setInvoiceDate(DateTimeUtility.getTimeStamp(Inventory.getInvoice_date(), DateTimeUtility.DD_MM_YYYY));
		
		part.setVendorId(Inventory.getVendor_id());
		/*if (PartInvoiceDto.getVendorIdStr() != null && FuelDto.getParseVendorID_STRING_TO_ID(PartInvoiceDto.getVendorIdStr()) != 0) {
			part.setVendorId(FuelDto.getParseVendorID_STRING_TO_ID(PartInvoiceDto.getVendorIdStr()));
		} else {
				Vendor vendor = vendorService.getVendorIdFromNew(PartInvoiceDto.getVendorIdStr(), userDetails.getCompany_id(), "PART-VENDOR", "Part Inventory");
				part.setVendorId(vendor.getVendorId());
		}
		PartInvoiceDto.setVendorId(part.getVendorId());*/
		//part.setPaymentTypeId(PartInvoiceDto.getPaymentTypeId());
		part.setDescription(Inventory.getDescription());
		//part.setQuantity(PartInvoiceDto.getQuantity());
		part.setCreatedById(userDetails.getId());
		part.setLastModifiedById(userDetails.getId());
		part.setCreatedOn(toDate);
		part.setLastModifiedBy(toDate);
		part.setCompanyId(userDetails.getCompany_id());
		part.setLastUpdated_Date(toDate);
	//	part.setAnyPartNumberAsign(PartInvoiceDto.isAnyPartNumberAsign());
	//	part.setLabourCharge(PartInvoiceDto.getLabourCharge());
		return part;
		
	}
	
 }	