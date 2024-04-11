package org.fleetopgroup.constant;

import org.fleetopgroup.persistence.document.VehicleAccidentDocument;
import org.fleetopgroup.persistence.document.VehicleExpenseDocument;

public class DocumentTypeConstent {
	
	public static final short SERVICE_ENTRIES_DOCUMENT				= 1;
	public static final short DRIVER_DOCUMENT						= 2;
	public static final short VEHICLE_DOCUMENT						= 3;
	public static final short RENEWAL_DOCUMENT						= 4;
	public static final short CASH_BOOK_DOCUMENT					= 5;
	public static final short WORK_ORDER_DOCUMENT					= 6;
	public static final short MAIL_BOX_DOCUMENT						= 7;
	public static final short BRANCH_DOCUMENT						= 8;
	public static final short BRANCH_PHOTO							= 9;
	public static final short FUEL_DOCUMENT							= 10;
	public static final short INVENTORY_TYRE_RETREAD_DOCUMENT		= 11;
	public static final short ISSUES_DOCUMENT						= 12;
	public static final short ISSUES_PHOTO							= 13;
	public static final short PURCHASE_ORDER_DOCUMENT				= 14;
	public static final short RENEWAL_REMINDER_APP_DOCUMENT			= 15;
	public static final short RENEWAL_REMINDER_DOCUMENT_HISTORY		= 16;
	public static final short USER_PROFILE_DOCUMENT					= 17;
	public static final short USER_PROFILE_PHOTO					= 18;
	public static final short VENDOR_DOCUMENT						= 19;
	public static final short DRIVER_PHOTO							= 20;
	public static final short MASTER_PARTS_PHOTO					= 21;
	public static final short VEHICLE_PHOTO							= 22;
	public static final short COMPANY_LOGO							= 23;
	public static final short MASTER_DOCUMENTS						= 24;
	public static final short INSPECTION_PARAMETER_DOCUMENTS		= 25;
	public static final short INSPECTION_PARAMETER					= 26;
	public static final short PART_DOCUMENT							= 27;
	public static final short INVENTORY_TYRE_DOCUMENT				= 28;
	public static final short BATTERY_INVOICE_DOCUMENT				= 29;
	public static final short CLOTH_INVOICE_DOCUMENT				= 30;
	public static final short UREA_INVOICE_DOCUMENT					= 31;
	public static final short TRIP_SHEET_EXPENSE_DOCUMENT			= 32;
	public static final short TRIP_SHEET_DOCUMENT					= 33;
	public static final short FUEL_INVOICE_DOCUMENT					= 34;
	public static final short TYRE_EXPENSE_DETAILS_DOCUMENT			= 35;
	public static final short VEHICLE_ACCIDENT_DOCUMENT				= 36;
	public static final short VEHICLE_EXPENSE_DOCUMENT				= 37;
	

	public static Class<?> getDocumentTypeClass(short type) {
		switch (type) {
		  case 1:
			  return VehicleAccidentDocument.class;
		  case 2:
			  return VehicleExpenseDocument.class;
		 
		  default:
		        break;
		}
		
		return null;
	
	
	}
}
