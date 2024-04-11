package org.fleetopgroup.constant;

public class ApprovalType {

	public static final short APPROVAL_TYPE_FUEL_ENTRIES					= 1;
	public static final short APPROVAL_TYPE_SERVICE_ENTRIES					= 2;
	public static final short APPROVAL_TYPE_PURCHASE_ORDER					= 3;
	public static final short APPROVAL_TYPE_INVENTORY_TYRE_INVOICE			= 4;
	public static final short APPROVAL_TYPE_INVENTORY_TYRE_RETREAD			= 5;
	public static final short APPROVAL_TYPE_INVENTORY_BATTERY_INVOICE		= 6;
	public static final short APPROVAL_TYPE_INVENTORY_PART_INVOICE			= 7;
	public static final short APPROVAL_TYPE_INVENTORY_CLOTH_INVOICE			= 8;
	public static final short APPROVAL_TYPE_INVENTORY_UREA_INVOICE			= 9;
	public static final short APPROVAL_TYPE_INVENTORY_LAUNDRY_INVOICE		= 10;
	
	public static final String APPROVAL_TYPE_FUEL_ENTRIES_NAME					= "FUEL_ENTRIES";
	public static final String APPROVAL_TYPE_SERVICE_ENTRIES_NAME				= "SE";
	public static final String APPROVAL_TYPE_PURCHASE_ORDER_NAME				= "PO";
	public static final String APPROVAL_TYPE_INVENTORY_TYRE_INVOICE_NAME 		= "TI";
	public static final String APPROVAL_TYPE_INVENTORY_TYRE_RETREAD_NAME		= "TR";
	public static final String APPROVAL_TYPE_INVENTORY_BATTERY_INVOICE_NAME		= "BI";
	public static final String APPROVAL_TYPE_INVENTORY_PART_INVOICE_NAME		= "PI";
	public static final String APPROVAL_TYPE_INVENTORY_CLOTH_INVOICE_NAME		= "CI";
	public static final String APPROVAL_TYPE_INVENTORY_UREA_INVOICE_NAME		= "UI";
	public static final String APPROVAL_TYPE_INVENTORY_LAUNDRY_INVOICE_NAME		= "LI";
	
	public static String getApprovalType(short paymentType) {

		String paymentTypeName		=	null;
		switch (paymentType) {
		
		  case APPROVAL_TYPE_FUEL_ENTRIES:
			  paymentTypeName	= APPROVAL_TYPE_FUEL_ENTRIES_NAME;
		        break;
		  case APPROVAL_TYPE_SERVICE_ENTRIES: 
			  paymentTypeName	= APPROVAL_TYPE_SERVICE_ENTRIES_NAME;
		        break;
		  case APPROVAL_TYPE_PURCHASE_ORDER: 
			  paymentTypeName	= APPROVAL_TYPE_PURCHASE_ORDER_NAME;
		        break;
		  case APPROVAL_TYPE_INVENTORY_TYRE_INVOICE: 
			  paymentTypeName	= APPROVAL_TYPE_INVENTORY_TYRE_INVOICE_NAME;
		        break;
		  case APPROVAL_TYPE_INVENTORY_TYRE_RETREAD: 
			  paymentTypeName	= APPROVAL_TYPE_INVENTORY_TYRE_RETREAD_NAME;
		        break;
		  case APPROVAL_TYPE_INVENTORY_BATTERY_INVOICE: 
			  paymentTypeName	= APPROVAL_TYPE_INVENTORY_BATTERY_INVOICE_NAME;
			  break;
		  case APPROVAL_TYPE_INVENTORY_PART_INVOICE: 
			  paymentTypeName	= APPROVAL_TYPE_INVENTORY_PART_INVOICE_NAME;
			  break;	  
		  case APPROVAL_TYPE_INVENTORY_CLOTH_INVOICE: 
			  paymentTypeName	= APPROVAL_TYPE_INVENTORY_CLOTH_INVOICE_NAME;
			  break;	
		  case APPROVAL_TYPE_INVENTORY_UREA_INVOICE: 
			  paymentTypeName	= APPROVAL_TYPE_INVENTORY_UREA_INVOICE_NAME;
			  break;
		  case APPROVAL_TYPE_INVENTORY_LAUNDRY_INVOICE: 
			  paymentTypeName	= APPROVAL_TYPE_INVENTORY_LAUNDRY_INVOICE_NAME;
			  break;
		  
		  default:
			  paymentTypeName = "--";
		        break;
		}
		return paymentTypeName;
	
	}
}
