/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.InventoryTyreInvoiceDto;
import org.fleetopgroup.persistence.dto.InventoryTyreLifeHistoryDto;
import org.fleetopgroup.persistence.dto.InventoryTyreRetreadAmountDto;
import org.fleetopgroup.persistence.dto.InventoryTyreRetreadDto;
import org.fleetopgroup.persistence.dto.InventoryTyreTransferDto;
import org.fleetopgroup.persistence.model.InventoryTyre;
import org.fleetopgroup.persistence.model.InventoryTyreLifeHistory;
import org.fleetopgroup.persistence.model.InventoryTyreRetread;
import org.fleetopgroup.persistence.model.InventoryTyreRetreadAmount;
import org.fleetopgroup.persistence.model.InventoryTyreTransfer;
import org.fleetopgroup.persistence.model.TyreSoldDetails;
import org.fleetopgroup.persistence.model.TyreSoldInvoiceDetails;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author fleetop
 *
 */
public class InventoryTyreInvoiceBL {

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat SQLdateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	DecimalFormat  toFixedTwo	= new DecimalFormat("#.##");
	
	

	public InventoryTyreInvoiceBL() {
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

	public List<InventoryTyreInvoiceDto> prepare_InventoryTyreInvoice_list(List<InventoryTyreInvoiceDto> History) {
		List<InventoryTyreInvoiceDto> Dtos = null;
		if (History != null && !History.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreInvoiceDto>();
			InventoryTyreInvoiceDto Tyre = null;
			for (InventoryTyreInvoiceDto TyreInvoiceDto : History) {
				Tyre = new InventoryTyreInvoiceDto();

				Tyre.setITYRE_ID(TyreInvoiceDto.getITYRE_ID());
				Tyre.setITYRE_NUMBER(TyreInvoiceDto.getITYRE_NUMBER());
				Tyre.setWAREHOUSE_LOCATION(TyreInvoiceDto.getWAREHOUSE_LOCATION());

				Tyre.setPO_NUMBER(TyreInvoiceDto.getPO_NUMBER());
				Tyre.setINVOICE_NUMBER(TyreInvoiceDto.getINVOICE_NUMBER());
				
				Tyre.setINVOICE_AMOUNT(Double.parseDouble(toFixedTwo.format(TyreInvoiceDto.getINVOICE_AMOUNT())));
				Tyre.setBalanceAmount(Double.parseDouble(toFixedTwo.format(TyreInvoiceDto.getBalanceAmount())));
				Tyre.setPaidAmount(Double.parseDouble(toFixedTwo.format(TyreInvoiceDto.getPaidAmount())));
				
				
				//Tyre.setTypeOfPaymentStr(TyreInvoiceDto.getTypeOfPaymentId());
				
				if (TyreInvoiceDto.getINVOICE_DATE_ON() != null) {
					Tyre.setINVOICE_DATE(SQLdateFormat.format(TyreInvoiceDto.getINVOICE_DATE_ON()));
				}

				Tyre.setVENDOR_ID(TyreInvoiceDto.getVENDOR_ID());
				Tyre.setVENDOR_NAME(TyreInvoiceDto.getVENDOR_NAME());
				Tyre.setVENDOR_LOCATION(TyreInvoiceDto.getVENDOR_LOCATION());

				Tyre.setPAYMENT_TYPE_ID(TyreInvoiceDto.getPAYMENT_TYPE_ID());
				Tyre.setPAYMENT_TYPE(PaymentTypeConstant.getPaymentTypeName(TyreInvoiceDto.getPAYMENT_TYPE_ID()));
				Tyre.setVENDOR_PAYMODE_STATUS_ID(TyreInvoiceDto.getVENDOR_PAYMODE_STATUS_ID());
				Tyre.setVENDOR_PAYMODE_STATUS(InventoryTyreInvoiceDto.getPaymentModeName(TyreInvoiceDto.getVENDOR_PAYMODE_STATUS_ID()));
				Tyre.setTyre_document_id(TyreInvoiceDto.getTyre_document_id());
				
				Dtos.add(Tyre);
			}
		}
		return Dtos;
	}
	
	public List<InventoryTyreInvoiceDto> getInventoryTyreInvoice_list(List<InventoryTyreInvoiceDto> History) {
		List<InventoryTyreInvoiceDto> Dtos = null;
		if (History != null && !History.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreInvoiceDto>();
			InventoryTyreInvoiceDto Tyre = null;
			for (InventoryTyreInvoiceDto TyreInvoiceDto : History) {
				Tyre = new InventoryTyreInvoiceDto();

				Tyre.setITYRE_ID(TyreInvoiceDto.getITYRE_ID());
				Tyre.setITYRE_NUMBER(TyreInvoiceDto.getITYRE_NUMBER());
				Tyre.setWAREHOUSE_LOCATION(TyreInvoiceDto.getWAREHOUSE_LOCATION());

				Tyre.setPO_NUMBER(TyreInvoiceDto.getPO_NUMBER());
				Tyre.setINVOICE_NUMBER(TyreInvoiceDto.getINVOICE_NUMBER());
				Tyre.setINVOICE_AMOUNT(TyreInvoiceDto.getINVOICE_AMOUNT());
				if (TyreInvoiceDto.getINVOICE_DATE_ON() != null) {
					Tyre.setINVOICE_DATE(SQLdateFormat.format(TyreInvoiceDto.getINVOICE_DATE_ON()));
				}

				Tyre.setVENDOR_ID(TyreInvoiceDto.getVENDOR_ID());
				Tyre.setVENDOR_NAME(TyreInvoiceDto.getVENDOR_NAME());
				Tyre.setVENDOR_LOCATION(TyreInvoiceDto.getVENDOR_LOCATION());

				Tyre.setPAYMENT_TYPE_ID(TyreInvoiceDto.getPAYMENT_TYPE_ID());
				Tyre.setPAYMENT_TYPE(PaymentTypeConstant.getPaymentTypeName(TyreInvoiceDto.getPAYMENT_TYPE_ID()));
				Tyre.setVENDOR_PAYMODE_STATUS_ID(TyreInvoiceDto.getVENDOR_PAYMODE_STATUS_ID());
				Tyre.setVENDOR_PAYMODE_STATUS(InventoryTyreInvoiceDto.getPaymentModeName(TyreInvoiceDto.getVENDOR_PAYMODE_STATUS_ID()));

				Dtos.add(Tyre);
			}
		}
		return Dtos;
	}
	
	public List<InventoryTyreInvoiceDto> get_InventoryTyreInvoice_list(List<InventoryTyreInvoiceDto> History) {
		List<InventoryTyreInvoiceDto> Dtos = null;
		if (History != null && !History.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreInvoiceDto>();
			InventoryTyreInvoiceDto Tyre = null;
			for (InventoryTyreInvoiceDto TyreInvoiceDto : History) {
				Tyre = new InventoryTyreInvoiceDto();

				Tyre.setITYRE_ID(TyreInvoiceDto.getITYRE_ID());
				Tyre.setITYRE_NUMBER(TyreInvoiceDto.getITYRE_NUMBER());
				Tyre.setWAREHOUSE_LOCATION(TyreInvoiceDto.getWAREHOUSE_LOCATION());

				Tyre.setPO_NUMBER(TyreInvoiceDto.getPO_NUMBER());
				Tyre.setINVOICE_NUMBER(TyreInvoiceDto.getINVOICE_NUMBER());
				Tyre.setINVOICE_AMOUNT(TyreInvoiceDto.getINVOICE_AMOUNT());
				if (TyreInvoiceDto.getINVOICE_DATE_ON() != null) {
					Tyre.setINVOICE_DATE(SQLdateFormat.format(TyreInvoiceDto.getINVOICE_DATE_ON()));
				}

				Tyre.setVENDOR_ID(TyreInvoiceDto.getVENDOR_ID());
				Tyre.setVENDOR_NAME(TyreInvoiceDto.getVENDOR_NAME());
				Tyre.setVENDOR_LOCATION(TyreInvoiceDto.getVENDOR_LOCATION());

				Tyre.setPAYMENT_TYPE_ID(TyreInvoiceDto.getPAYMENT_TYPE_ID());
				Tyre.setPAYMENT_TYPE(PaymentTypeConstant.getPaymentTypeName(TyreInvoiceDto.getPAYMENT_TYPE_ID()));
				Tyre.setVENDOR_PAYMODE_STATUS_ID(TyreInvoiceDto.getVENDOR_PAYMODE_STATUS_ID());
				Tyre.setVENDOR_PAYMODE_STATUS(InventoryTyreInvoiceDto.getPaymentModeName(TyreInvoiceDto.getVENDOR_PAYMODE_STATUS_ID()));
				if(TyreInvoiceDto.getCREATEDBYID() != null) {
				Tyre.setCREATEDBYID(TyreInvoiceDto.getCREATEDBYID());
				}
				if(TyreInvoiceDto.getFirstName() != null) {
				Tyre.setFirstName(TyreInvoiceDto.getFirstName());
				}
				if(TyreInvoiceDto.getLastName() != null) {
				Tyre.setLastName(TyreInvoiceDto.getLastName());
				}
				if(TyreInvoiceDto.getVENDOR_PAYMODE_DATE() != null) {
					Tyre.setVENDOR_PAYMODE_DATE(TyreInvoiceDto.getVENDOR_PAYMODE_DATE());
				}
				Tyre.setTyre_document(TyreInvoiceDto.isTyre_document());
				if(TyreInvoiceDto.getTyre_document_id() != null){
				Tyre.setTyre_document_id(TyreInvoiceDto.getTyre_document_id());
				}
					Tyre.setSubLocationId(TyreInvoiceDto.getSubLocationId());
					Tyre.setSubLocation(TyreInvoiceDto.getSubLocation());
				Dtos.add(Tyre);
			}
		}
		return Dtos;
	}

	public InventoryTyreInvoiceDto Show_inventory_Tyre_invoice(InventoryTyreInvoiceDto TyreInvoiceDto) {

		InventoryTyreInvoiceDto Tyre = new InventoryTyreInvoiceDto();

		Tyre.setITYRE_ID(TyreInvoiceDto.getITYRE_ID());
		Tyre.setITYRE_NUMBER(TyreInvoiceDto.getITYRE_NUMBER());
		Tyre.setWAREHOUSE_LOCATION(TyreInvoiceDto.getWAREHOUSE_LOCATION());
		Tyre.setWAREHOUSE_LOCATION_ID(TyreInvoiceDto.getWAREHOUSE_LOCATION_ID());
		Tyre.setPurchaseOrderId(TyreInvoiceDto.getPurchaseOrderId());
		Tyre.setPO_NUMBER(TyreInvoiceDto.getPO_NUMBER());
		Tyre.setINVOICE_NUMBER(TyreInvoiceDto.getINVOICE_NUMBER());
		Tyre.setINVOICE_AMOUNT(TyreInvoiceDto.getINVOICE_AMOUNT());
		Tyre.setINVOICE_DATE(TyreInvoiceDto.getINVOICE_DATE());

		Tyre.setVENDOR_ID(TyreInvoiceDto.getVENDOR_ID());
		Tyre.setVENDOR_NAME(TyreInvoiceDto.getVENDOR_NAME());
		Tyre.setVENDOR_LOCATION(TyreInvoiceDto.getVENDOR_LOCATION());

		Tyre.setPAYMENT_TYPE_ID(TyreInvoiceDto.getPAYMENT_TYPE_ID());
		Tyre.setPAYMENT_TYPE(PaymentTypeConstant.getPaymentTypeName(TyreInvoiceDto.getPAYMENT_TYPE_ID()));
		Tyre.setPAYMENT_NUMBER(TyreInvoiceDto.getPAYMENT_NUMBER());
		Tyre.setDESCRIPTION(TyreInvoiceDto.getDESCRIPTION());
		Tyre.setVENDOR_PAYMODE_STATUS_ID(TyreInvoiceDto.getVENDOR_PAYMODE_STATUS_ID());
		Tyre.setVENDOR_PAYMODE_STATUS(InventoryTyreInvoiceDto.getPaymentModeName(TyreInvoiceDto.getVENDOR_PAYMODE_STATUS_ID()));

		/** Set Created by email Id */
		Tyre.setCREATEDBY(TyreInvoiceDto.getCREATEDBY());
		Tyre.setLASTMODIFIEDBY(TyreInvoiceDto.getLASTMODIFIEDBY());
		Tyre.setCREATEDBYID(TyreInvoiceDto.getCREATEDBYID());
		Tyre.setLASTMODIFIEDBYID(TyreInvoiceDto.getLASTMODIFIEDBYID());
		Tyre.setSTATUS_OF_TYRE(TyreInvoiceDto.getSTATUS_OF_TYRE());
		/** Set Created Current Date */
		
			Tyre.setCREATED_DATE(TyreInvoiceDto.getCREATED_DATE());
			Tyre.setLASTUPDATED_DATE(TyreInvoiceDto.getLASTUPDATED_DATE());
			Tyre.setTallyCompanyId(TyreInvoiceDto.getTallyCompanyId());
			Tyre.setTallyCompanyName(TyreInvoiceDto.getTallyCompanyName());
			Tyre.setSubLocationId(TyreInvoiceDto.getSubLocationId());
			Tyre.setSubLocation(TyreInvoiceDto.getSubLocation());
			
			Tyre.setApprovalId(TyreInvoiceDto.getApprovalId());
			Tyre.setApprovalNumber(TyreInvoiceDto.getApprovalNumber());
		return Tyre; // this Show the Value Off Inventory Tyre Service Invoice
						// Dtyo Values OF The
	}

	public InventoryTyreInvoiceDto Edit_TyreInventory(InventoryTyreInvoiceDto TyreInvoiceDto) {

		InventoryTyreInvoiceDto Tyre = new InventoryTyreInvoiceDto();

		Tyre.setITYRE_ID(TyreInvoiceDto.getITYRE_ID());
		Tyre.setITYRE_NUMBER(TyreInvoiceDto.getITYRE_NUMBER());
		Tyre.setWAREHOUSE_LOCATION(TyreInvoiceDto.getWAREHOUSE_LOCATION());
		Tyre.setWAREHOUSE_LOCATION_ID(TyreInvoiceDto.getWAREHOUSE_LOCATION_ID());

		Tyre.setPO_NUMBER(TyreInvoiceDto.getPO_NUMBER());
		Tyre.setINVOICE_NUMBER(TyreInvoiceDto.getINVOICE_NUMBER());
		Tyre.setINVOICE_AMOUNT(TyreInvoiceDto.getINVOICE_AMOUNT());
		Tyre.setINVOICE_DATE(TyreInvoiceDto.getINVOICE_DATE());
		Tyre.setPAYMENT_TYPE_ID(TyreInvoiceDto.getPAYMENT_TYPE_ID());
		Tyre.setPAYMENT_TYPE(PaymentTypeConstant.getPaymentTypeName(TyreInvoiceDto.getPAYMENT_TYPE_ID()));
		Tyre.setVENDOR_ID(TyreInvoiceDto.getVENDOR_ID());
		Tyre.setVENDOR_NAME(TyreInvoiceDto.getVENDOR_NAME());
		Tyre.setVENDOR_LOCATION(TyreInvoiceDto.getVENDOR_LOCATION());
		Tyre.setSTATUS_OF_TYRE(TyreInvoiceDto.getSTATUS_OF_TYRE());
		Tyre.setDESCRIPTION(TyreInvoiceDto.getDESCRIPTION());
		Tyre.setTallyCompanyId(TyreInvoiceDto.getTallyCompanyId());
		Tyre.setTallyCompanyName(TyreInvoiceDto.getTallyCompanyName());

		Tyre.setLASTUPDATED_DATE(TyreInvoiceDto.getLASTUPDATED_DATE());
		Tyre.setSubLocationId(TyreInvoiceDto.getSubLocationId());
		Tyre.setSubLocation(TyreInvoiceDto.getSubLocation());

		return Tyre;
	}

	/*public InventoryTyreDto Show_InventoryTyre_Details(InventoryTyre TyreDTo) {

		InventoryTyreDto Tyre = new InventoryTyreDto();

		Tyre.setTYRE_ID(TyreDTo.getTYRE_ID());
		Tyre.setTYRE_IN_NUMBER(TyreDTo.getTYRE_IN_NUMBER());

		Tyre.setITYRE_INVOICE_ID(TyreDTo.getITYRE_INVOICE_ID());
		Tyre.setITYRE_AMOUNT_ID(TyreDTo.getITYRE_AMOUNT_ID());

		Tyre.setTYRE_NUMBER(TyreDTo.getTYRE_NUMBER());
		Tyre.setTYRE_AMOUNT(TyreDTo.getTYRE_AMOUNT());
		Tyre.setTYRE_MANUFACTURER(TyreDTo.getTYRE_MANUFACTURER());
		Tyre.setTYRE_MODEL(TyreDTo.getTYRE_MODEL());
		Tyre.setTYRE_SIZE_ID(TyreDTo.getTYRE_SIZE_ID());
		Tyre.setTYRE_SIZE(TyreDTo.getTYRE_SIZE());
		Tyre.setTYRE_TREAD(TyreDTo.getTYRE_TREAD());
		Tyre.setWAREHOUSE_LOCATION(TyreDTo.getWAREHOUSE_LOCATION());

		Tyre.setVEHICLE_ID(TyreDTo.getVEHICLE_ID());
		Tyre.setVEHICLE_REGISTRATION(TyreDTo.getVEHICLE_REGISTRATION());

		if (TyreDTo.getTYRE_PURCHASE_DATE() != null) {
			Tyre.setTYRE_PURCHASE_DATE(SQLdateFormat.format(TyreDTo.getTYRE_PURCHASE_DATE()));
		}

		if (TyreDTo.getTYRE_ASSIGN_DATE() != null) {
			Tyre.setTYRE_ASSIGN_DATE(SQLdateFormat.format(TyreDTo.getTYRE_ASSIGN_DATE()));
		}

		Tyre.setOPEN_ODOMETER(TyreDTo.getOPEN_ODOMETER());
		Tyre.setCLOSE_ODOMETER(TyreDTo.getCLOSE_ODOMETER());
		Tyre.setTYRE_USEAGE(TyreDTo.getTYRE_USEAGE());

		Tyre.setTYRE_RETREAD_COUNT(TyreDTo.getTYRE_RETREAD_COUNT());

		Tyre.setTYRE_SCRAPED_BY(TyreDTo.getTYRE_SCRAPED_BY());
		if (TyreDTo.getTYRE_SCRAPED_DATE() != null) {
			Tyre.setTYRE_SCRAPED_DATE(SQLdateFormat.format(TyreDTo.getTYRE_SCRAPED_DATE()));
		}

		Tyre.setTYRE_ASSIGN_STATUS(TyreDTo.getTYRE_ASSIGN_STATUS());

		*//** Set Created by email Id *//*
		Tyre.setCREATEDBY(TyreDTo.getCREATEDBY());
		Tyre.setLASTMODIFIEDBY(TyreDTo.getLASTMODIFIEDBY());
		*//** Set Created Current Date *//*
		if (TyreDTo.getCREATED_DATE() != null) {
			*//** Set Created Current Date *//*
			Tyre.setCREATED_DATE(CreatedDateTime.format(TyreDTo.getCREATED_DATE()));
		}
		if (TyreDTo.getLASTUPDATED_DATE() != null) {
			Tyre.setLASTUPDATED_DATE(CreatedDateTime.format(TyreDTo.getLASTUPDATED_DATE()));
		}
		return Tyre;
	}
*/	
	public InventoryTyreDto Show_InventoryTyre_Details(InventoryTyreDto TyreDTo) {

		InventoryTyreDto Tyre = new InventoryTyreDto();

		Tyre.setTYRE_ID(TyreDTo.getTYRE_ID());
		Tyre.setTYRE_IN_NUMBER(TyreDTo.getTYRE_IN_NUMBER());
		Tyre.setITYRE_INVOICE_ID(TyreDTo.getITYRE_INVOICE_ID());
		Tyre.setITYRE_AMOUNT_ID(TyreDTo.getITYRE_AMOUNT_ID());
		Tyre.setTYRE_NUMBER(TyreDTo.getTYRE_NUMBER());
		Tyre.setTYRE_AMOUNT(TyreDTo.getTYRE_AMOUNT());
		Tyre.setTYRE_MANUFACTURER(TyreDTo.getTYRE_MANUFACTURER());		
		Tyre.setTYRE_MANUFACTURER_ID(TyreDTo.getTYRE_MANUFACTURER_ID());
		Tyre.setTYRE_MODEL(TyreDTo.getTYRE_MODEL());
		Tyre.setTYRE_MODEL_ID(TyreDTo.getTYRE_MODEL_ID());
		Tyre.setTYRE_SIZE_ID(TyreDTo.getTYRE_SIZE_ID());
		Tyre.setTYRE_SIZE(TyreDTo.getTYRE_SIZE());		
		Tyre.setTYRE_TREAD(TyreDTo.getTYRE_TREAD());
		Tyre.setWAREHOUSE_LOCATION(TyreDTo.getWAREHOUSE_LOCATION());
		Tyre.setWAREHOUSE_LOCATION_ID(TyreDTo.getWAREHOUSE_LOCATION_ID());
		Tyre.setVEHICLE_ID(TyreDTo.getVEHICLE_ID());
		Tyre.setVEHICLE_REGISTRATION(TyreDTo.getVEHICLE_REGISTRATION());
		if (TyreDTo.getTYRE_PURCHASE_DATE_ON() != null) {
			Tyre.setTYRE_PURCHASE_DATE(SQLdateFormat.format(TyreDTo.getTYRE_PURCHASE_DATE_ON()));
		}
		if (TyreDTo.getTYRE_ASSIGN_DATE_ON() != null) {
			Tyre.setTYRE_ASSIGN_DATE(SQLdateFormat.format(TyreDTo.getTYRE_ASSIGN_DATE_ON()));
		}
		Tyre.setOPEN_ODOMETER(TyreDTo.getOPEN_ODOMETER());
		Tyre.setCLOSE_ODOMETER(TyreDTo.getCLOSE_ODOMETER());
		Tyre.setTYRE_USEAGE(TyreDTo.getTYRE_USEAGE());	
		
		Tyre.setTYRE_RETREAD_COUNT(TyreDTo.getTYRE_RETREAD_COUNT());
		Tyre.setTYRE_SCRAPED_BY(TyreDTo.getTYRE_SCRAPED_BY());
		if (TyreDTo.getTYRE_SCRAPED_DATE_ON() != null) {
			Tyre.setTYRE_SCRAPED_DATE(SQLdateFormat.format(TyreDTo.getTYRE_SCRAPED_DATE_ON()));
		}

		Tyre.setTYRE_ASSIGN_STATUS_ID(TyreDTo.getTYRE_ASSIGN_STATUS_ID());
		Tyre.setTYRE_ASSIGN_STATUS(InventoryTyreDto.getPaymentModeName(TyreDTo.getTYRE_ASSIGN_STATUS_ID()));

		/** Set Created by email Id */
		Tyre.setCREATEDBY(TyreDTo.getCREATEDBY());
		Tyre.setLASTMODIFIEDBY(TyreDTo.getLASTMODIFIEDBY());
		/** Set Created Current Date */
		if (TyreDTo.getCREATED_DATE_ON() != null) {
			/** Set Created Current Date */
			Tyre.setCREATED_DATE(CreatedDateTime.format(TyreDTo.getCREATED_DATE_ON()));
		}
		if (TyreDTo.getLASTUPDATED_DATE_ON() != null) {
			Tyre.setLASTUPDATED_DATE(CreatedDateTime.format(TyreDTo.getLASTUPDATED_DATE_ON()));
		}
		Tyre.setSubLocationId(TyreDTo.getSubLocationId());
		Tyre.setSubLocation(TyreDTo.getSubLocation());
		Tyre.setDismountedTyreStatus(TyreDTo.getDismountedTyreStatus());
		return Tyre;
	}


	/*public List<InventoryTyreHistoryDto> prepareListof_InventoryTyreHistory(List<InventoryTyreHistory> History) {
		List<InventoryTyreHistoryDto> Dtos = null;
		if (History != null && !History.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreHistoryDto>();
			InventoryTyreHistoryDto TyreHistory = null;
			for (InventoryTyreHistory Tyre : History) {
				TyreHistory = new InventoryTyreHistoryDto();

				TyreHistory.setTYRE_ID(Tyre.getTYRE_ID());
				TyreHistory.setTYRE_NUMBER(Tyre.getTYRE_NUMBER());

				TyreHistory.setVEHICLE_ID(Tyre.getVEHICLE_ID());
				TyreHistory.setVEHICLE_REGISTRATION(Tyre.getVEHICLE_REGISTRATION());

				TyreHistory.setPOSITION(Tyre.getPOSITION());
				TyreHistory.setAXLE(Tyre.getAXLE());
				TyreHistory.setTYRE_STATUS_ID(Tyre.getTYRE_STATUS_ID());
				TyreHistory.setTYRE_STATUS(InventoryTyreHistoryDto.getStatusName(Tyre.getTYRE_STATUS_ID()));
				TyreHistory.setOPEN_ODOMETER(Tyre.getOPEN_ODOMETER());
				TyreHistory.setTYRE_USEAGE(Tyre.getTYRE_USEAGE());

				if (Tyre.getTYRE_ASSIGN_DATE() != null) {
					TyreHistory.setTYRE_ASSIGN_DATE(SQLdateFormat.format(Tyre.getTYRE_ASSIGN_DATE()));
				}

				TyreHistory.setTYRE_COMMENT(Tyre.getTYRE_COMMENT());
				Dtos.add(TyreHistory);
			}
		}
		return Dtos;
	}*/

	public List<InventoryTyreRetreadDto> prepareListof_InventoryTyreRetreadDto(List<InventoryTyreRetreadDto> Retread) {
		List<InventoryTyreRetreadDto> Dtos = null;
		if (Retread != null && !Retread.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreRetreadDto>();
			InventoryTyreRetreadDto TyreRetread = null;
			for (InventoryTyreRetreadDto TyreRetreadDto : Retread) {
				TyreRetread = new InventoryTyreRetreadDto();

				TyreRetread.setTRID(TyreRetreadDto.getTRID());
				TyreRetread.setTRNUMBER(TyreRetreadDto.getTRNUMBER());

				if (TyreRetreadDto.getTR_OPEN_DATE_ON() != null) {
					try {
						TyreRetread.setTR_OPEN_DATE(SQLdateFormat.format(TyreRetreadDto.getTR_OPEN_DATE_ON()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (TyreRetreadDto.getTR_REQUIRED_DATE_ON() != null) {
					try {
						TyreRetread.setTR_REQUIRED_DATE(SQLdateFormat.format(TyreRetreadDto.getTR_REQUIRED_DATE_ON()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				TyreRetread.setTR_VENDOR_ID(TyreRetreadDto.getTR_VENDOR_ID());
				TyreRetread.setTR_VENDOR_NAME(TyreRetreadDto.getTR_VENDOR_NAME());
				TyreRetread.setTR_VENDOR_LOCATION(TyreRetreadDto.getTR_VENDOR_LOCATION());

				TyreRetread.setTR_PAYMENT_TYPE_ID(TyreRetreadDto.getTR_PAYMENT_TYPE_ID());
				TyreRetread.setTR_PAYMENT_TYPE(PaymentTypeConstant.getPaymentTypeName(TyreRetreadDto.getTR_PAYMENT_TYPE_ID()));
				TyreRetread.setTR_QUOTE_NO(TyreRetreadDto.getTR_QUOTE_NO());
				TyreRetread.setTR_MANUAL_NO(TyreRetreadDto.getTR_MANUAL_NO());

				TyreRetread.setTR_RECEIVE_TYRE(TyreRetreadDto.getTR_RECEIVE_TYRE());

				TyreRetread.setTR_STATUS_ID(TyreRetreadDto.getTR_STATUS_ID());
				TyreRetread.setTR_STATUS(InventoryTyreRetreadDto.getTRStatusName(TyreRetreadDto.getTR_STATUS_ID()));
				TyreRetread.setTR_VENDOR_PAYMODE_STATUS_ID(TyreRetreadDto.getTR_VENDOR_PAYMODE_STATUS_ID());
				TyreRetread.setTR_VENDOR_PAYMODE_STATUS(InventoryTyreRetreadDto.getVendorPaymentModeName(TyreRetread.getTR_VENDOR_PAYMODE_STATUS_ID()));
				Dtos.add(TyreRetread);
			}
		}
		return Dtos;
	}
	
	public List<InventoryTyreRetreadAmountDto> getInventoryTyreRetreadAmountList(List<InventoryTyreRetreadAmountDto> amounts){
		List<InventoryTyreRetreadAmountDto>	Dtos = null;
		Dtos = new ArrayList<InventoryTyreRetreadAmountDto>();
		if (amounts != null && !amounts.isEmpty()) {
		InventoryTyreRetreadAmountDto list = null;
		for (InventoryTyreRetreadAmountDto result : amounts) {
			list = new InventoryTyreRetreadAmountDto();

			list.setTR_AMOUNT_ID(result.getTR_AMOUNT_ID());
			list.setTYRE_ID(result.getTYRE_ID());
			list.setTYRE_NUMBER(result.getTYRE_NUMBER());
			list.setTYRE_SIZE(result.getTYRE_SIZE());
			list.setRETREAD_AMOUNT(result.getRETREAD_AMOUNT());
			list.setRETREAD_DISCOUNT(result.getRETREAD_DISCOUNT());
			list.setRETREAD_TAX(result.getRETREAD_TAX());
			list.setRETREAD_COST(result.getRETREAD_COST());
			list.setTRA_STATUS_ID(result.getTRA_STATUS_ID());
			list.setTRA_STATUS(InventoryTyreRetreadAmountDto.getTraStatusName(result.getTRA_STATUS_ID()));

			Dtos.add(list);
		}
	}
		return Dtos;
	}

	public List<InventoryTyreRetreadDto> FilterprepareListof_InventoryTyreRetreadDto(
			List<InventoryTyreRetreadDto> Retread) {
		List<InventoryTyreRetreadDto> Dtos = null;
		if (Retread != null && !Retread.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreRetreadDto>();
			InventoryTyreRetreadDto TyreRetread = null;
			for (InventoryTyreRetreadDto TyreRetreadDto : Retread) {
				TyreRetread = new InventoryTyreRetreadDto();

				TyreRetread.setTRID(TyreRetreadDto.getTRID());
				TyreRetread.setTRNUMBER(TyreRetreadDto.getTRNUMBER());

				TyreRetread.setTR_INVOICE_NUMBER(TyreRetreadDto.getTR_INVOICE_NUMBER());
				if (TyreRetreadDto.getTR_INVOICE_DATE_ON() != null) {
					try {
						TyreRetread.setTR_INVOICE_DATE(SQLdateFormat.format(TyreRetreadDto.getTR_INVOICE_DATE_ON()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				TyreRetread.setTR_VENDOR_ID(TyreRetreadDto.getTR_VENDOR_ID());
				TyreRetread.setTR_VENDOR_NAME(TyreRetreadDto.getTR_VENDOR_NAME());
				TyreRetread.setTR_PAYMENT_TYPE_ID(TyreRetreadDto.getTR_PAYMENT_TYPE_ID());
				TyreRetread.setTR_PAYMENT_TYPE(PaymentTypeConstant.getPaymentTypeName(TyreRetreadDto.getTR_PAYMENT_TYPE_ID()));
				TyreRetread.setTR_VENDOR_PAYMODE_STATUS_ID(TyreRetreadDto.getTR_VENDOR_PAYMODE_STATUS_ID());
				TyreRetread.setTR_VENDOR_PAYMODE_STATUS(InventoryTyreRetreadDto.getVendorPaymentModeName(TyreRetreadDto.getTR_VENDOR_PAYMODE_STATUS_ID()));
				
				
				TyreRetread.setTR_AMOUNT(Double.parseDouble(toFixedTwo.format(TyreRetreadDto.getTR_AMOUNT())));
				TyreRetread.setPaidAmount(Double.parseDouble(toFixedTwo.format(TyreRetreadDto.getPaidAmount())));
				TyreRetread.setBalanceAmount(Double.parseDouble(toFixedTwo.format(TyreRetreadDto.getBalanceAmount())));

				Dtos.add(TyreRetread);
			}
		}
		return Dtos;
	}

/*	public InventoryTyreRetreadDto Show_InventoryTyreRetreadDto_Details(InventoryTyreRetreadDto TyreRetreadDto) {

		InventoryTyreRetreadDto TyreRetread = new InventoryTyreRetreadDto();

		TyreRetread.setTRID(TyreRetreadDto.getTRID());
		TyreRetread.setTRNUMBER(TyreRetreadDto.getTRNUMBER());

		if (TyreRetreadDto.getTR_OPEN_DATE() != null) {
			try {
				TyreRetread.setTR_OPEN_DATE(SQLdateFormat.format(TyreRetreadDto.getTR_OPEN_DATE()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (TyreRetreadDto.getTR_REQUIRED_DATE() != null) {
			try {
				TyreRetread.setTR_REQUIRED_DATE(SQLdateFormat.format(TyreRetreadDto.getTR_REQUIRED_DATE()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		TyreRetread.setTR_VENDOR_ID(TyreRetreadDto.getTR_VENDOR_ID());
		TyreRetread.setTR_VENDOR_NAME(TyreRetreadDto.getTR_VENDOR_NAME());
		TyreRetread.setTR_VENDOR_LOCATION(TyreRetreadDto.getTR_VENDOR_LOCATION());

		TyreRetread.setTR_PAYMENT_TYPE_ID(TyreRetreadDto.getTR_PAYMENT_TYPE_ID());
		TyreRetread.setTR_PAYMENT_TYPE(PaymentTypeConstant.getPaymentTypeName(TyreRetreadDto.getTR_PAYMENT_TYPE_ID()));
		TyreRetread.setTR_PAYMENT_NUMBER(TyreRetreadDto.getTR_PAYMENT_NUMBER());

		TyreRetread.setTR_QUOTE_NO(TyreRetreadDto.getTR_QUOTE_NO());
		TyreRetread.setTR_MANUAL_NO(TyreRetreadDto.getTR_MANUAL_NO());

		TyreRetread.setTR_RECEIVE_TYRE(TyreRetreadDto.getTR_RECEIVE_TYRE());

		TyreRetread.setTR_DESCRIPTION(TyreRetreadDto.getTR_DESCRIPTION());
		TyreRetread.setTR_RE_DESCRIPTION(TyreRetreadDto.getTR_RE_DESCRIPTION());

		TyreRetread.setTR_AMOUNT(TyreRetreadDto.getTR_AMOUNT());
		TyreRetread.setTR_ROUNT_AMOUNT(TyreRetreadDto.getTR_ROUNT_AMOUNT());

		TyreRetread.setTR_STATUS_ID(TyreRetreadDto.getTR_STATUS_ID());
		TyreRetread.setTR_STATUS(InventoryTyreRetreadDto.getTRStatusName(TyreRetreadDto.getTR_STATUS_ID()));

		TyreRetread.setTR_INVOICE_NUMBER(TyreRetreadDto.getTR_INVOICE_NUMBER());
		if (TyreRetreadDto.getTR_INVOICE_DATE() != null) {
			try {
				TyreRetread.setTR_INVOICE_DATE(SQLdateFormat.format(TyreRetreadDto.getTR_INVOICE_DATE()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		*//** Set Created by email Id *//*
		TyreRetread.setCREATEDBY(TyreRetreadDto.getCREATEDBY());
		TyreRetread.setLASTMODIFIEDBY(TyreRetreadDto.getLASTMODIFIEDBY());
		*//** Set Created Current Date *//*
		if (TyreRetreadDto.getCREATED_DATE() != null) {
			*//** Set Created Current Date *//*
			TyreRetread.setCREATED_DATE(CreatedDateTime.format(TyreRetreadDto.getCREATED_DATE()));
		}
		if (TyreRetreadDto.getLASTUPDATED_DATE() != null) {
			TyreRetread.setLASTUPDATED_DATE(CreatedDateTime.format(TyreRetreadDto.getLASTUPDATED_DATE()));
		}

		return TyreRetread;
	}*/

	public List<InventoryTyreLifeHistoryDto> prepareListof_InventoryTyreLifeHistory(
			List<InventoryTyreLifeHistory> History, InventoryTyreDto TyreShow) {
		List<InventoryTyreLifeHistoryDto> Dtos = null;
		InventoryTyreLifeHistoryDto TyreHistory = null;
		Dtos = new ArrayList<InventoryTyreLifeHistoryDto>();
		if (History != null && !History.isEmpty()) {
			for (InventoryTyreLifeHistory Tyre : History) {
				TyreHistory = new InventoryTyreLifeHistoryDto();

				TyreHistory.setTYRE_ID(Tyre.getTYRE_ID());
				TyreHistory.setTYRE_NUMBER(Tyre.getTYRE_NUMBER());

				TyreHistory.setLIFE_PERIOD(Tyre.getLIFE_PERIOD());
				TyreHistory.setTYRE_LIFE_COST(Tyre.getTYRE_LIFE_COST());
				TyreHistory.setTYRE_LIFE_USAGE(Tyre.getTYRE_LIFE_USAGE());
				TyreHistory.setTYRE_KM_COST(Tyre.getTYRE_KM_COST());

				if (Tyre.getTYRE_RECEIVED_DATE() != null) {
					TyreHistory.setTYRE_RECEIVED_DATE(SQLdateFormat.format(Tyre.getTYRE_RECEIVED_DATE()));
				}

				Dtos.add(TyreHistory);
			}
		}
		if (TyreShow != null) {

			TyreHistory = new InventoryTyreLifeHistoryDto();

			TyreHistory.setTYRE_ID(TyreShow.getTYRE_ID());
			TyreHistory.setTYRE_NUMBER(TyreShow.getTYRE_NUMBER());

			Integer lIFE_PERIOD = TyreShow.getTYRE_RETREAD_COUNT();

			if (lIFE_PERIOD == 0) {
				TyreHistory.setLIFE_PERIOD("NEW");
			} else {
				TyreHistory.setLIFE_PERIOD(lIFE_PERIOD + " RETREAD");
			}
			TyreHistory.setTYRE_LIFE_COST(TyreShow.getTYRE_AMOUNT());

			Double Amount = TyreShow.getTYRE_AMOUNT();
			Integer usage = TyreShow.getTYRE_USEAGE();

			Double Km = Amount / usage;
			if (usage != 0) {
				TyreHistory.setTYRE_KM_COST(round(Km, 2));
			} else {
				TyreHistory.setTYRE_KM_COST(0.0);
			}
			TyreHistory.setTYRE_LIFE_USAGE(TyreShow.getTYRE_USEAGE());

			if (TyreShow.getTYRE_ASSIGN_DATE() != null) {
				TyreHistory.setTYRE_RECEIVED_DATE(SQLdateFormat.format(TyreShow.getTYRE_ASSIGN_DATE()));
			}

			Dtos.add(TyreHistory);

		}

		return Dtos;
	}

	public InventoryTyreLifeHistoryDto prepareTOTAL_COST_InventoryTyreLifeHistory(
			List<InventoryTyreLifeHistory> History, InventoryTyreDto TyreShow) {
		InventoryTyreLifeHistoryDto Dtos = new InventoryTyreLifeHistoryDto();

		Double Total_Cost = 0.0;
		Integer Total_Usage = 0;
		if (History != null && !History.isEmpty()) {
			for (InventoryTyreLifeHistory Tyre : History) {

				Total_Cost += Tyre.getTYRE_LIFE_COST();
				Total_Usage += Tyre.getTYRE_LIFE_USAGE();

			}
		}
		if (TyreShow != null) {

			Total_Cost += TyreShow.getTYRE_AMOUNT();
			Total_Usage += TyreShow.getTYRE_USEAGE();
		}

		Dtos.setTYRE_LIFE_COST(round(Total_Cost, 2));
		Dtos.setTYRE_LIFE_USAGE(Total_Usage);

		Double TotalKm = Total_Cost / Total_Usage;
		if (Total_Usage != 0) {
			Dtos.setTYRE_KM_COST(round(TotalKm, 2));
		} else {
			Dtos.setTYRE_KM_COST(0.0);
		}

		return Dtos;
	}

	/**
	 * @param fROM_transferInventory
	 * @param inventoryTransfer
	 * @return
	 */
	public InventoryTyreTransfer prepareCreateInventory_To_TransferInventory(InventoryTyreDto MasterTransferInventory,
			InventoryTyreTransferDto TransferCreate_UI) {

		InventoryTyreTransfer part_transfer = new InventoryTyreTransfer();

		part_transfer.setITTID(TransferCreate_UI.getITTID());

		// get search data to get part details
		part_transfer.setTYRE_ID(MasterTransferInventory.getTYRE_ID());
		part_transfer.setTYRE_NUMBER(MasterTransferInventory.getTYRE_NUMBER());

		part_transfer.setTRA_FROM_LOCATION_ID(MasterTransferInventory.getWAREHOUSE_LOCATION_ID());

		part_transfer.setTRA_TO_LOCATION_ID(TransferCreate_UI.getTRA_TO_LOCATION_ID());
		part_transfer.setTRA_QUANTITY((double) 1);
		//part_transfer.setTRANSFER_BY(TransferCreate_UI.getTRANSFER_BY());
		part_transfer.setTRANSFER_VIA_ID(TransferCreate_UI.getTRANSFER_VIA_ID());

		// get Current days
		java.util.Date currentDate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

		part_transfer.setTRANSFER_DATE(toDate);

		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		part_transfer.setTRANSFER_RECEIVEDDATE(null);
		part_transfer.setTRANSFER_REASON(TransferCreate_UI.getTRANSFER_REASON());

		part_transfer.setTRA_INVENTORY_INVOCEID(MasterTransferInventory.getITYRE_INVOICE_ID());

		part_transfer.setMarkForDelete(false);
		part_transfer.setCREATED_DATE(toDate);
		part_transfer.setLASTUPDATED_DATE(toDate);

		//part_transfer.setCREATEDBY(userDetails.getEmail());
		//part_transfer.setLASTMODIFIEDBY(userDetails.getEmail());
		part_transfer.setCREATEDBYID(userDetails.getId());
		part_transfer.setLASTMODIFIEDBYID(userDetails.getId());
		part_transfer.setCOMPANY_ID(userDetails.getCompany_id());

		return part_transfer;
	}

	/**
	 * @param show_Location
	 * @return
	 */
	public List<InventoryTyreTransferDto> prepareListofDto_Inventory_Tyre_Transfer(
			List<InventoryTyreTransferDto> History) {

		List<InventoryTyreTransferDto> Dtos = null;
		InventoryTyreTransferDto TyreHistory = null;
		Dtos = new ArrayList<InventoryTyreTransferDto>();
		if (History != null && !History.isEmpty()) {
			for (InventoryTyreTransferDto Tyre : History) {
				TyreHistory = new InventoryTyreTransferDto();

				TyreHistory.setITTID(Tyre.getITTID());
				
				// get search data to get part details
				TyreHistory.setTYRE_ID(Tyre.getTYRE_ID());
				TyreHistory.setTYRE_NUMBER(Tyre.getTYRE_NUMBER());

				TyreHistory.setTRA_FROM_LOCATION(Tyre.getTRA_FROM_LOCATION());

				TyreHistory.setTRA_TO_LOCATION(Tyre.getTRA_TO_LOCATION());
				TyreHistory.setTRA_QUANTITY(Tyre.getTRA_QUANTITY());
				TyreHistory.setTRANSFER_BY(Tyre.getTRANSFER_BY());
				TyreHistory.setTRANSFER_VIA_ID(Tyre.getTRANSFER_VIA_ID());
				TyreHistory.setTRANSFER_VIA(InventoryTyreTransferDto.getTransferViaName(Tyre.getTRANSFER_VIA_ID()));

				if (Tyre.getTRANSFER_DATE_ON() != null) {
					TyreHistory.setTRANSFER_DATE(dateFormatTime.format(Tyre.getTRANSFER_DATE_ON()));
				}

				TyreHistory.setTRANSFER_RECEIVEDBY(Tyre.getTRANSFER_RECEIVEDBY());
				TyreHistory.setTRANSFER_RECEIVEDBYEMAIL(Tyre.getTRANSFER_RECEIVEDBYEMAIL());
				TyreHistory.setTRANSFER_RECEIVEDREASON(Tyre.getTRANSFER_RECEIVEDREASON());

				TyreHistory.setTRANSFER_STATUS_ID(Tyre.getTRANSFER_STATUS_ID());
				TyreHistory.setTRANSFER_STATUS(InventoryTyreTransferDto.getStatusName(Tyre.getTRANSFER_STATUS_ID()));

				if (Tyre.getTRANSFER_RECEIVEDDATE_ON() != null) {
					TyreHistory.setTRANSFER_RECEIVEDDATE(dateFormatTime.format(Tyre.getTRANSFER_RECEIVEDDATE_ON()));
				}
				TyreHistory.setTRANSFER_REASON(Tyre.getTRANSFER_REASON());

				TyreHistory.setTRA_INVENTORY_INVOCEID(Tyre.getTRA_INVENTORY_INVOCEID());

				TyreHistory.setMarkForDelete(Tyre.isMarkForDelete());
				if (Tyre.getCREATED_DATE_ON() != null) {
					TyreHistory.setCREATED_DATE(dateFormatTime.format(Tyre.getCREATED_DATE_ON()));
				}
				if (Tyre.getLASTUPDATED_DATE_ON() != null) {
					TyreHistory.setLASTUPDATED_DATE(dateFormatTime.format(Tyre.getLASTUPDATED_DATE_ON()));
				}
				TyreHistory.setCREATEDBY(Tyre.getCREATEDBY());
				TyreHistory.setLASTMODIFIEDBY(Tyre.getLASTMODIFIEDBY());
				
				TyreHistory.setTRANSFER_RECEIVEDBY_ID(Tyre.getTRANSFER_RECEIVEDBY_ID());

				Dtos.add(TyreHistory);
			}

		}
		return Dtos;
	}
	

//newy start
public InventoryTyreRetreadAmount saveInventoryTyreRetreadAmount(ValueObject object, InventoryTyreDto tyre,ValueObject vlaueObject) throws Exception{

	InventoryTyreRetreadAmount	chargeTransfer	= null;
	CustomUserDetails	userDetails	= null;
	long retreadTyreId = 0;
	double	retreadCost	= 0;
	double	totalAmount	= 0;
	double	discount	= 0;
	double	tax			= 0;
	short 	status		= 1;
	java.util.Date lastModifiedOn ;
	InventoryTyreRetread	TyreRetreadInvoiceId	= null;

	try {
		lastModifiedOn = new java.util.Date();
		Timestamp sqlDate = new java.sql.Timestamp(lastModifiedOn.getTime());
		userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		retreadTyreId 				= object.getLong("retreadTyreId");
		totalAmount	  				= object.getDouble("totalId");
		retreadCost	  				= object.getDouble("unitCostId");
		discount	 				= object.getDouble("discountId");
		tax	  		  				= object.getDouble("taxId");
		TyreRetreadInvoiceId	 	= (InventoryTyreRetread)vlaueObject.get("invetoryTyreRetreadId");

		chargeTransfer	= new InventoryTyreRetreadAmount();

		chargeTransfer.setRETREAD_AMOUNT(totalAmount);	
		chargeTransfer.setRETREAD_COST(retreadCost);	
		chargeTransfer.setRETREAD_DISCOUNT(discount);	
		chargeTransfer.setRETREAD_TAX(tax);       	
		chargeTransfer.setTYRE_ID(retreadTyreId);
		chargeTransfer.setTRA_STATUS_ID(status);
		chargeTransfer.setInventoryTyreRetread(TyreRetreadInvoiceId);
		chargeTransfer.setTYRE_SIZE_ID(tyre.getTYRE_SIZE_ID());
		chargeTransfer.setTYRE_NUMBER(tyre.getTYRE_NUMBER());
		chargeTransfer.setUPDATEDBYID(userDetails.getId());	
		chargeTransfer.setUPDATED_DATE(sqlDate);
		chargeTransfer.setCOMPANY_ID(userDetails.getCompany_id());
		
		chargeTransfer.setMarkForDelete(false);	  

		return chargeTransfer;

	} catch (Exception e) {
		throw e;
	}finally {
		chargeTransfer	= null;
		userDetails	= null;
	}
}

public TyreSoldInvoiceDetails prepareTyreSoldInvoice(ValueObject valueObject, CustomUserDetails userDetails) throws Exception {
	Date 		currentDateUpdate  = null;
	Timestamp 	toDate 				= null;	
	                              
	try {
		currentDateUpdate 	= new Date();                                             
		toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime()); 
		TyreSoldInvoiceDetails tyreSoldInvoiceDetails = new TyreSoldInvoiceDetails();
		
		tyreSoldInvoiceDetails.setTyreSoldInvoiceDate(DateTimeUtility.getTimeStamp(valueObject.getString("invoiceDate")));
		tyreSoldInvoiceDetails.setSoldTyreWeight(valueObject.getDouble("tyreWeight",0));
		tyreSoldInvoiceDetails.setDiscount(valueObject.getDouble("discount",0));
		tyreSoldInvoiceDetails.setGst(valueObject.getDouble("gst",0));
		tyreSoldInvoiceDetails.setTyreSoldInvoiceAmount(valueObject.getDouble("invoiceCost",0));
		tyreSoldInvoiceDetails.setTyreSoldInvoiceNetAmount(valueObject.getDouble("invoiceNetCost",0));
		tyreSoldInvoiceDetails.setTyreQuantity(valueObject.getLong("tyreQuantity",0));
		tyreSoldInvoiceDetails.setDescription(valueObject.getString("description"));
		tyreSoldInvoiceDetails.setSoldType(valueObject.getShort("soldType",(short)0));
		tyreSoldInvoiceDetails.setTyreStatus(valueObject.getShort("tyreStatus",(short)0));
		tyreSoldInvoiceDetails.setCreatedById(userDetails.getId());
		tyreSoldInvoiceDetails.setLastUpdatedBy(userDetails.getId());
		tyreSoldInvoiceDetails.setCreationDate(toDate);
		tyreSoldInvoiceDetails.setLastUpdatedOn(toDate);
		tyreSoldInvoiceDetails.setCompanyId(userDetails.getCompany_id());
		tyreSoldInvoiceDetails.setMarkForDelete(false);

		return tyreSoldInvoiceDetails;
		
	} catch (Exception e) {
		System.err.println("e"+e);
		//LOGGER.error("Err"+e);
		throw e;
	}finally {
		
	}
	

}

public TyreSoldDetails prepareTyreSoldDetails(ValueObject valueObject, CustomUserDetails userDetails) {
	Date 		currentDateUpdate  = null;
	Timestamp 	toDate 				= null;	
	                              
	try {
		currentDateUpdate 	= new Date();                                             
		toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime()); 
		TyreSoldDetails tyreSoldDetails = new TyreSoldDetails();
		tyreSoldDetails.setTyreId(valueObject.getLong("soldTyreId",0));
		tyreSoldDetails.setDiscount(valueObject.getDouble("soldTyreDiscount",0));
		tyreSoldDetails.setGst(valueObject.getDouble("soldTyreGst",0));
		tyreSoldDetails.setTyreSoldAmount(valueObject.getDouble("soldTyreSoldAmount",0));
		tyreSoldDetails.setTyreSoldNetAmount(valueObject.getDouble("invoiceNetCost",0));
		tyreSoldDetails.setTyreSoldInvoiceId(valueObject.getLong("soldTyreInvoiceId",0));
		tyreSoldDetails.setDescription(valueObject.getString("tyreSoldDescription"));
		tyreSoldDetails.setCreatedById(userDetails.getId());
		tyreSoldDetails.setLastUpdatedBy(userDetails.getId());
		tyreSoldDetails.setCreationDate(toDate);
		tyreSoldDetails.setLastUpdatedOn(toDate);
		tyreSoldDetails.setCompanyId(userDetails.getCompany_id());
		tyreSoldDetails.setMarkForDelete(false);

		return tyreSoldDetails;
		
	} catch (Exception e) {
		System.err.println("e"+e);
		//LOGGER.error("Err"+e);
	//	throw e;
	}finally {
		
	}
	return null;
	

}

public static InventoryTyreTransfer prepareTyreTransfer(InventoryTyre tyre,Long userId,int companyId,int toLocation) {
	System.err.println("tyre *** "+tyre);
	InventoryTyreTransfer part_transfer = new InventoryTyreTransfer();
	Timestamp toDate = DateTimeUtility.getCurrentTimeStamp();
	part_transfer.setTYRE_ID(tyre.getTYRE_ID());
	part_transfer.setTYRE_NUMBER(tyre.getTYRE_NUMBER());
	part_transfer.setTRA_FROM_LOCATION_ID(tyre.getWAREHOUSE_LOCATION_ID());
	part_transfer.setTRA_TO_LOCATION_ID(toLocation);
	part_transfer.setTRA_QUANTITY((double) 1);
	part_transfer.setTRANSFER_DATE(toDate);
	part_transfer.setTRANSFER_RECEIVEDDATE(null);
	part_transfer.setTRA_INVENTORY_INVOCEID(tyre.getITYRE_INVOICE_ID());
	part_transfer.setMarkForDelete(false);
	part_transfer.setCREATED_DATE(toDate);
	part_transfer.setLASTUPDATED_DATE(toDate);
	part_transfer.setCREATEDBYID(userId);
	part_transfer.setLASTMODIFIEDBYID(userId);
	part_transfer.setCOMPANY_ID(companyId);

	return part_transfer;
}

}
