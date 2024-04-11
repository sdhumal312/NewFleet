package org.fleetopgroup.persistence.bl;

import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.persistence.dto.BatteryInvoiceDto;
import org.fleetopgroup.persistence.dto.ClothInvoiceDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesDto;
import org.fleetopgroup.persistence.dto.UreaInvoiceDto;
import org.fleetopgroup.persistence.model.BatteryInvoice;
import org.fleetopgroup.persistence.model.ClothInvoice;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.FuelInvoice;
import org.fleetopgroup.persistence.model.InventoryTyreInvoice;
import org.fleetopgroup.persistence.model.InventoryTyreRetread;
import org.fleetopgroup.persistence.model.PartInvoice;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.UpholsterySendLaundryInvoice;
import org.fleetopgroup.persistence.model.UreaInvoice;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Component;

@Component
public class PendingVendorPaymentBL {

	public static PendingVendorPayment	createPendingVendorPaymentDTOForSE(ServiceEntriesDto	entriesDto) {
		PendingVendorPayment		payment		= null;
		try {
			payment	= new PendingVendorPayment();
			
			payment.setVendorId(entriesDto.getVendor_id());
			payment.setTransactionId(entriesDto.getServiceEntries_id());
			payment.setTransactionNumber("SE-"+entriesDto.getServiceEntries_Number());
			payment.setTransactionDate(entriesDto.getInvoiceDateOn());
			payment.setTxnTypeId(PendingPaymentType.PAYMENT_TYPE_SERVICE_ENTRIES);
			payment.setTransactionAmount(entriesDto.getTotalservice_cost());
			payment.setBalanceAmount(entriesDto.getTotalservice_cost());
			payment.setPaymentStatusId(PendingPaymentType.PAYMENT_STATUS_PENDING);
			payment.setCompanyId(entriesDto.getCompanyId());
			payment.setVid(entriesDto.getVid());
			payment.setInvoiceNumber(entriesDto.getInvoiceNumber());
			payment.setDocumentId(entriesDto.getService_document_id());
			
			return payment;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static PendingVendorPayment	createPendingVendorPaymentDTOForFuel(Fuel entriesDto) {
		PendingVendorPayment		payment		= null;
		try {
			payment	= new PendingVendorPayment();
			
			payment.setVendorId(entriesDto.getVendor_id());
			payment.setTransactionId(entriesDto.getFuel_id());
			payment.setTransactionNumber("F-"+entriesDto.getFuel_Number());
			payment.setTransactionDate(entriesDto.getFuel_date());
			payment.setTxnTypeId(PendingPaymentType.PAYMENT_TYPE_FUEL_ENTRIES);
			payment.setTransactionAmount(entriesDto.getFuel_amount());
			payment.setBalanceAmount(entriesDto.getFuel_amount());
			payment.setPaymentStatusId(PendingPaymentType.PAYMENT_STATUS_PENDING);
			payment.setCompanyId(entriesDto.getCompanyId());
			payment.setVid(entriesDto.getVid());
			payment.setInvoiceNumber(entriesDto.getFuel_reference());
			payment.setDocumentId(entriesDto.getFuel_document_id());
			return payment;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static PendingVendorPayment	createPendingVendorPaymentDTOForPI(PartInvoice	partInvoice) {
		PendingVendorPayment		payment		= null;
		try {
			payment	= new PendingVendorPayment();
			
			payment.setVendorId(partInvoice.getVendorId());
			payment.setTransactionId(partInvoice.getPartInvoiceId());
			payment.setTransactionNumber("PI-"+partInvoice.getPartInvoiceNumber());
			payment.setTransactionDate(partInvoice.getInvoiceDate());
			payment.setTxnTypeId(PendingPaymentType.PAYMENT_TYPE_PART_INVOICE);
			payment.setTransactionAmount(Double.parseDouble(partInvoice.getInvoiceAmount()));
			payment.setBalanceAmount(Double.parseDouble(partInvoice.getInvoiceAmount()));
			payment.setPaymentStatusId(PendingPaymentType.PAYMENT_STATUS_PENDING);
			payment.setCompanyId(partInvoice.getCompanyId());
			payment.setInvoiceNumber(partInvoice.getInvoiceNumber());
			payment.setDocumentId(partInvoice.getPart_document_id());
			
			
			return payment;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static PendingVendorPayment	createPendingVendorPaymentDTOForTI(InventoryTyreInvoice	partInvoice) {
		PendingVendorPayment		payment		= null;
		try {
			payment	= new PendingVendorPayment();
			
			payment.setVendorId(partInvoice.getVENDOR_ID());
			payment.setTransactionId(partInvoice.getITYRE_ID());
			payment.setTransactionNumber("TI-"+partInvoice.getITYRE_NUMBER());
			payment.setTransactionDate(partInvoice.getINVOICE_DATE());
			payment.setTxnTypeId(PendingPaymentType.PAYMENT_TYPE_TYRE_INVOICE);
			payment.setTransactionAmount(partInvoice.getINVOICE_AMOUNT());
			payment.setBalanceAmount(partInvoice.getINVOICE_AMOUNT());
			payment.setPaymentStatusId(PendingPaymentType.PAYMENT_STATUS_PENDING);
			payment.setCompanyId(partInvoice.getCOMPANY_ID());
			payment.setInvoiceNumber(partInvoice.getINVOICE_NUMBER());
			payment.setDocumentId(partInvoice.getTyre_document_id());
			
			return payment;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static PendingVendorPayment	createPendingVendorPaymentDTOForBI(BatteryInvoice	batteryInvoice) {
		PendingVendorPayment		payment		= null;
		try {
			payment	= new PendingVendorPayment();
			
			payment.setVendorId(batteryInvoice.getVendorId());
			payment.setTransactionId(batteryInvoice.getBatteryInvoiceId());
			payment.setTransactionNumber("BI-"+batteryInvoice.getBatteryInvoiceNumber());
			payment.setTransactionDate(batteryInvoice.getInvoiceDate());
			payment.setTxnTypeId(PendingPaymentType.PAYMENT_TYPE_BATTERY_INVOICE);
			payment.setTransactionAmount(batteryInvoice.getInvoiceAmount());
			payment.setBalanceAmount(batteryInvoice.getInvoiceAmount());
			payment.setPaymentStatusId(PendingPaymentType.PAYMENT_STATUS_PENDING);
			payment.setCompanyId(batteryInvoice.getCompanyId());
			payment.setInvoiceNumber(batteryInvoice.getInvoiceNumber());
			payment.setDocumentId(batteryInvoice.getBattery_document_id());
			
			return payment;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static PendingVendorPayment	createPendingVendorPaymentDTOForBIEDIT(BatteryInvoiceDto	batteryInvoice) {
		PendingVendorPayment		payment		= null;
		try {
			payment	= new PendingVendorPayment();
			
			payment.setVendorId(batteryInvoice.getVendorId());
			payment.setTransactionId(batteryInvoice.getBatteryInvoiceId());
			payment.setTransactionNumber("BI-"+batteryInvoice.getBatteryInvoiceNumber());
			payment.setTransactionDate(batteryInvoice.getInvoiceDate());
			payment.setTxnTypeId(PendingPaymentType.PAYMENT_TYPE_BATTERY_INVOICE);
			payment.setTransactionAmount(batteryInvoice.getInvoiceAmount());
			payment.setBalanceAmount(batteryInvoice.getInvoiceAmount());
			payment.setPaymentStatusId(PendingPaymentType.PAYMENT_STATUS_PENDING);
			payment.setCompanyId(batteryInvoice.getCompanyId());
			payment.setInvoiceNumber(batteryInvoice.getInvoiceNumber());
			payment.setDocumentId(batteryInvoice.getBattery_document_id());
			
			return payment;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static PendingVendorPayment	createPendingVendorPaymentDTOForCI(ClothInvoice	batteryInvoice) {
		PendingVendorPayment		payment		= null;
		try {
			payment	= new PendingVendorPayment();
			
			payment.setVendorId(batteryInvoice.getVendorId());
			payment.setTransactionId(batteryInvoice.getClothInvoiceId());
			payment.setTransactionNumber("CI-"+batteryInvoice.getClothInvoiceNumber());
			payment.setTransactionDate(batteryInvoice.getInvoiceDate());
			payment.setTxnTypeId(PendingPaymentType.PAYMENT_TYPE_CLOTH_INVOICE);
			payment.setTransactionAmount(batteryInvoice.getInvoiceAmount());
			payment.setBalanceAmount(batteryInvoice.getInvoiceAmount());
			payment.setPaymentStatusId(PendingPaymentType.PAYMENT_STATUS_PENDING);
			payment.setCompanyId(batteryInvoice.getCompanyId());
			payment.setInvoiceNumber(batteryInvoice.getInvoiceNumber());
			payment.setDocumentId(batteryInvoice.getCloth_document_id());
			
			return payment;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static PendingVendorPayment	createPendingVendorPaymentDTOForLI(UpholsterySendLaundryInvoice	batteryInvoice) {
		PendingVendorPayment		payment		= null;
		try {
			payment	= new PendingVendorPayment();
			
			payment.setVendorId(batteryInvoice.getVendorId());
			payment.setTransactionId(batteryInvoice.getLaundryInvoiceId());
			payment.setTransactionNumber("LI-"+batteryInvoice.getLaundryInvoiceNumber());
			payment.setTransactionDate(batteryInvoice.getSentDate());
			payment.setTxnTypeId(PendingPaymentType.PAYMENT_TYPE_LAUNDRY_INVOICE);
			payment.setTransactionAmount(batteryInvoice.getTotalCost());
			payment.setBalanceAmount(batteryInvoice.getTotalCost());
			payment.setPaymentStatusId(PendingPaymentType.PAYMENT_STATUS_PENDING);
			payment.setCompanyId(batteryInvoice.getCompanyId());
			payment.setInvoiceNumber(batteryInvoice.getPaymentNumber());
			payment.setDocumentId((long) 0);
			
			return payment;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static PendingVendorPayment	createPendingVendorPaymentDTOForLIEdit(UpholsterySendLaundryInvoice	batteryInvoice) {
		PendingVendorPayment		payment		= null;
		try {
			payment	= new PendingVendorPayment();
			
			payment.setVendorId(batteryInvoice.getVendorId());
			payment.setTransactionId(batteryInvoice.getLaundryInvoiceId());
			payment.setTransactionNumber("LI-"+batteryInvoice.getLaundryInvoiceNumber());
			payment.setTransactionDate(batteryInvoice.getSentDate());
			payment.setTxnTypeId(PendingPaymentType.PAYMENT_TYPE_LAUNDRY_INVOICE);
			payment.setTransactionAmount(batteryInvoice.getTotalCost());
			payment.setBalanceAmount(batteryInvoice.getTotalCost());
			payment.setPaymentStatusId(PendingPaymentType.PAYMENT_STATUS_PENDING);
			payment.setCompanyId(batteryInvoice.getCompanyId());
			payment.setInvoiceNumber(batteryInvoice.getPaymentNumber());
			payment.setDocumentId((long) 0);
			
			return payment;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static PendingVendorPayment	createPendingVendorPaymentDTOForCIEdit(ClothInvoiceDto	batteryInvoice) {
		PendingVendorPayment		payment		= null;
		try {
			payment	= new PendingVendorPayment();
			
			payment.setVendorId(batteryInvoice.getVendorId());
			payment.setTransactionId(batteryInvoice.getClothInvoiceId());
			payment.setTransactionNumber("CI-"+batteryInvoice.getClothInvoiceNumber());
			payment.setTransactionDate(batteryInvoice.getInvoiceDate());
			payment.setTxnTypeId(PendingPaymentType.PAYMENT_TYPE_CLOTH_INVOICE);
			payment.setTransactionAmount(batteryInvoice.getInvoiceAmount());
			payment.setBalanceAmount(batteryInvoice.getInvoiceAmount());
			payment.setPaymentStatusId(PendingPaymentType.PAYMENT_STATUS_PENDING);
			payment.setCompanyId(batteryInvoice.getCompanyId());
			payment.setInvoiceNumber(batteryInvoice.getInvoiceNumber());
			payment.setDocumentId(batteryInvoice.getCloth_document_id());
			return payment;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static PendingVendorPayment	createPendingVendorPaymentDTOForUI(UreaInvoice	batteryInvoice) {
		PendingVendorPayment		payment		= null;
		try {
			payment	= new PendingVendorPayment();
			
			payment.setVendorId(batteryInvoice.getVendorId());
			payment.setTransactionId(batteryInvoice.getUreaInvoiceId());
			payment.setTransactionNumber("UI-"+batteryInvoice.getUreaInvoiceNumber());
			payment.setTransactionDate(batteryInvoice.getInvoiceDate());
			payment.setTxnTypeId(PendingPaymentType.PAYMENT_TYPE_UREA_INVOICE);
			payment.setTransactionAmount(batteryInvoice.getInvoiceAmount());
			payment.setBalanceAmount(batteryInvoice.getInvoiceAmount());
			payment.setPaymentStatusId(PendingPaymentType.PAYMENT_STATUS_PENDING);
			payment.setCompanyId(batteryInvoice.getCompanyId());
			payment.setInvoiceNumber(batteryInvoice.getInvoiceNumber());
			payment.setDocumentId(batteryInvoice.getUrea_document_id());
			
			return payment;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static PendingVendorPayment	createPendingVendorPaymentDTOForUIEdit(UreaInvoiceDto	batteryInvoice) {
		PendingVendorPayment		payment		= null;
		try {
			payment	= new PendingVendorPayment();
			
			payment.setVendorId(batteryInvoice.getVendorId());
			payment.setTransactionId(batteryInvoice.getUreaInvoiceId());
			payment.setTransactionNumber("UI-"+batteryInvoice.getUreaInvoiceNumber());
			payment.setTransactionDate(batteryInvoice.getInvoiceDate());
			payment.setTxnTypeId(PendingPaymentType.PAYMENT_TYPE_UREA_INVOICE);
			payment.setTransactionAmount(batteryInvoice.getInvoiceAmount());
			payment.setBalanceAmount(batteryInvoice.getInvoiceAmount());
			payment.setPaymentStatusId(PendingPaymentType.PAYMENT_STATUS_PENDING);
			payment.setCompanyId(batteryInvoice.getCompanyId());
			payment.setInvoiceNumber(batteryInvoice.getInvoiceNumber());
			payment.setDocumentId(batteryInvoice.getUrea_document_id());
			
			return payment;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static PendingVendorPayment	createPendingVendorPaymentDTOForFI(FuelInvoice	batteryInvoice) {
		PendingVendorPayment		payment		= null;
		try {
			payment	= new PendingVendorPayment();
			
			payment.setVendorId(batteryInvoice.getVendorId());
			payment.setTransactionId(batteryInvoice.getFuelInvoiceId());
			payment.setTransactionNumber("FI-"+batteryInvoice.getFuelInvoiceNumber());
			payment.setTransactionDate(batteryInvoice.getInvoiceDate());
			payment.setTxnTypeId(PendingPaymentType.PAYMENT_TYPE_FUEL_INVOICE);
			payment.setTransactionAmount(batteryInvoice.getInvoiceAmount());
			payment.setBalanceAmount(batteryInvoice.getInvoiceAmount());
			payment.setPaymentStatusId(PendingPaymentType.PAYMENT_STATUS_PENDING);
			payment.setCompanyId(batteryInvoice.getCompanyId());
			payment.setInvoiceNumber(batteryInvoice.getInvoiceNumber());
			payment.setDocumentId(batteryInvoice.getDocumentId());
			
			return payment;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static PendingVendorPayment	createPendingVendorPaymentDTOForPO(PurchaseOrdersDto	ordersDto) {
		PendingVendorPayment		payment		= null;
		try {
			payment	= new PendingVendorPayment();
			
			payment.setVendorId(ordersDto.getPurchaseorder_vendor_id());
			payment.setTransactionId(ordersDto.getPurchaseorder_id());
			payment.setTransactionNumber("PO-"+ordersDto.getPurchaseorder_Number());
			payment.setTransactionDate(ordersDto.getPurchaseorder_invoice_date_On());
			payment.setTxnTypeId(PendingPaymentType.PAYMENT_TYPE_PURCHASE_ORDER);
			payment.setTransactionAmount(ordersDto.getPurchaseorder_balancecost());
			payment.setBalanceAmount(ordersDto.getPurchaseorder_balancecost());
			payment.setPaymentStatusId(PendingPaymentType.PAYMENT_STATUS_PENDING);
			payment.setCompanyId(ordersDto.getCompanyId());
			payment.setInvoiceNumber(ordersDto.getPurchaseorder_invoiceno());
			payment.setDocumentId(ordersDto.getPurchaseorder_document_id());
			
			return payment;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static PendingVendorPayment	createPendingVendorPaymentDTOForTRI(InventoryTyreRetread	batteryInvoice) {
		PendingVendorPayment		payment		= null;
		try {
			payment	= new PendingVendorPayment();
			
			payment.setVendorId(batteryInvoice.getTR_VENDOR_ID());
			payment.setTransactionId(batteryInvoice.getTRID());
			payment.setTransactionNumber("TR-"+batteryInvoice.getTRNUMBER());
			payment.setTransactionDate(batteryInvoice.getTR_INVOICE_DATE());
			payment.setTxnTypeId(PendingPaymentType.PAYMENT_TYPE_TYRE_RETREAD_INVOICE);
			payment.setTransactionAmount(batteryInvoice.getTR_AMOUNT());
			payment.setBalanceAmount(batteryInvoice.getTR_AMOUNT());
			payment.setPaymentStatusId(PendingPaymentType.PAYMENT_STATUS_PENDING);
			payment.setCompanyId(batteryInvoice.getCOMPANY_ID());
			payment.setInvoiceNumber(batteryInvoice.getTR_INVOICE_NUMBER());
			payment.setDocumentId((long) 0);
			
			return payment;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static PendingVendorPayment	createPendingVendorPaymentDTOForDSE(ValueObject valuObject) throws Exception {
		PendingVendorPayment		payment		= null;
		try {
			payment	= new PendingVendorPayment();
			
			payment.setVendorId(valuObject.getInt("vendorId"));
			payment.setTransactionId(valuObject.getLong("dealerServiceEntriesId"));
			payment.setTransactionNumber(valuObject.getString("dealerServiceEntriesNumber"));
			payment.setTransactionDate(DateTimeUtility.getTimeStamp(valuObject.getString("invoiceDate")));
			payment.setTxnTypeId(PendingPaymentType.PAYMENT_TYPE_DEALER_SERVICE_ENTRIES);
			payment.setTransactionAmount(valuObject.getDouble("invoiceCost"));
			payment.setBalanceAmount(valuObject.getDouble("invoiceCost"));
			payment.setPaymentStatusId(PendingPaymentType.PAYMENT_STATUS_PENDING);
			payment.setCompanyId(valuObject.getInt("companyId"));
			payment.setVid(valuObject.getInt("vid"));
			payment.setInvoiceNumber(valuObject.getString("invoiceNumber"));
		//	payment.setDocumentId(entriesDto.getService_document_id());
			
			return payment;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}
