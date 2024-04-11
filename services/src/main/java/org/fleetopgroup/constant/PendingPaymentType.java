package org.fleetopgroup.constant;

public class PendingPaymentType {

	public static final short	PAYMENT_TYPE_FUEL_ENTRIES			= 1;
	public static final short	PAYMENT_TYPE_SERVICE_ENTRIES		= 2;
	public static final short	PAYMENT_TYPE_PURCHASE_ORDER			= 3;
	public static final short	PAYMENT_TYPE_TYRE_INVOICE			= 4;
	public static final short	PAYMENT_TYPE_TYRE_RETREAD_INVOICE	= 5;
	public static final short	PAYMENT_TYPE_BATTERY_INVOICE		= 6;
	public static final short	PAYMENT_TYPE_PART_INVOICE			= 7;
	public static final short	PAYMENT_TYPE_CLOTH_INVOICE			= 8;
	public static final short	PAYMENT_TYPE_UREA_INVOICE			= 9;
	public static final short	PAYMENT_TYPE_LAUNDRY_INVOICE		= 10;
	public static final short	PAYMENT_TYPE_FUEL_INVOICE			= 11;
	public static final short	PAYMENT_TYPE_DEALER_SERVICE_ENTRIES			= 12;
	
	
	public static final short PAYMENT_STATUS_PENDING			= 1;
	public static final short PAYMENT_STATUS_APPROVAL_CREATED	= 2;
	public static final short PAYMENT_STATUS_PARTIAL			= 3;
	public static final short PAYMENT_STATUS_NEGOTIATED			= 4;
	public static final short PAYMENT_STATUS_PAID				= 5;
	
	public static final String PAYMENT_STATUS_PENDING_NAME 				= "Pending";
	public static final String PAYMENT_STATUS_APPROVAL_CREATED_NAME 	= "Approval Created";
	public static final String PAYMENT_STATUS_PARTIAL_NAME				= "Partial";
	public static final String PAYMENT_STATUS_NEGOTIATED_NAME   		= "Negotiated";
	public static final String PAYMENT_STATUS_PAID_NAME 				= "Paid";
	
	public static String getPaymentMode(short status) throws Exception{
		String statusString		=	null;
		switch (status) {
		  case PAYMENT_STATUS_PENDING:
			  statusString	= PAYMENT_STATUS_PENDING_NAME;
		        break;
		  case PAYMENT_STATUS_APPROVAL_CREATED:
			  statusString	= PAYMENT_STATUS_APPROVAL_CREATED_NAME;
		        break;
		  case PAYMENT_STATUS_PARTIAL: 
			  statusString	= PAYMENT_STATUS_PARTIAL_NAME;
		        break;
		  case PAYMENT_STATUS_NEGOTIATED: 
			  statusString	= PAYMENT_STATUS_NEGOTIATED_NAME;
		        break;
		  case PAYMENT_STATUS_PAID: 
			  statusString	= PAYMENT_STATUS_PAID_NAME;
			  break;

		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	}
	
	public static String getTransactionUrl(Long transactionId, short type) {
		String statusString		=	null;
		switch (type) {
		  case PAYMENT_TYPE_FUEL_ENTRIES:
			  statusString	= "showFuel.in?FID="+transactionId+"";
		        break;
		  case PAYMENT_TYPE_SERVICE_ENTRIES:
			  statusString	= "showServiceEntryDetails?serviceEntryId="+transactionId+"";
		        break;
		  case PAYMENT_TYPE_PURCHASE_ORDER: 
			  statusString	= "PurchaseOrders_Parts.in?ID="+transactionId+"";
		        break;
		  case PAYMENT_TYPE_TYRE_INVOICE: 
			  statusString	= "showTyreInventory.in?Id="+transactionId+"";
		        break;
		  case PAYMENT_TYPE_TYRE_RETREAD_INVOICE: 
			  statusString	= "ShowRetreadTyre?RID="+transactionId+"";
			  break;
		  case PAYMENT_TYPE_BATTERY_INVOICE: 
			  statusString	= "showBatteryInvoice?Id="+transactionId+"";
			  break;
		  case PAYMENT_TYPE_PART_INVOICE: 
			  statusString	= "showInvoice.in?Id="+transactionId+"";
			  break;
		  case PAYMENT_TYPE_CLOTH_INVOICE: 
			  statusString	= "showClothInvoice?Id="+transactionId+"";
			  break;
		  case PAYMENT_TYPE_UREA_INVOICE: 
			  statusString	= "showUreaInvoice?Id="+transactionId+"";
			  break;
		  case PAYMENT_TYPE_LAUNDRY_INVOICE: 
			  statusString	= "showLaundryInvoice?Id="+transactionId+"";
			  break;
		  case PAYMENT_TYPE_FUEL_INVOICE: 
			  statusString	= "showFuelInvoice?Id="+transactionId+"";
			  break;
		  case PAYMENT_TYPE_DEALER_SERVICE_ENTRIES: 
			  statusString	= "showDealerServiceEntries?dealerServiceEntriesId="+transactionId+"";
			  break;


		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	}
}
