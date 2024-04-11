package org.fleetopgroup.constant;

public class UpholsterySendLaundryInvoiceStatus {
	
	public static final short LAUNDRY_INVOICE_STATUS_OPEN 					= 	1;
	public static final short LAUNDRY_INVOICE_STATUS_COMPLETED	 			=	2;
	
	public static final String LAUNDRY_INVOICE_STATUS_OPEN_NAME 			=   "OPEN";
	public static final String LAUNDRY_INVOICE_STATUS_COMPLETED_NAME 		=   "COMPLETED";
	
	
	public static String getLaundryInvoiceStatus (short laundryInvoiceStatusId) throws Exception {
		String laundryInvoiceStatus = null;
		switch(laundryInvoiceStatusId) {
		case LAUNDRY_INVOICE_STATUS_OPEN :
				laundryInvoiceStatus 	= 	 LAUNDRY_INVOICE_STATUS_OPEN_NAME;
			break;
		case LAUNDRY_INVOICE_STATUS_COMPLETED :
				laundryInvoiceStatus 	=	 LAUNDRY_INVOICE_STATUS_COMPLETED_NAME;
			break;
		default :
			laundryInvoiceStatus = "--";
			break;
		}
		return laundryInvoiceStatus;
	}
	
}