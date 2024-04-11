package org.fleetopgroup.constant;


public class TripsheetPickAndDropConstant{
	
	public static final short PICKUP						= 1;
	public static final short DROP							= 2;
	
	public static final String PICKUP_NAME 					= "PickUp";
	public static final String DROP_NAME 					= "Drop";
	
	public static final short INVOICE_NOT_MADE				= 1;
	public static final short INVOICE_MADE					= 2;
	
	public static final String INVOICE_NOT_MADE_NAME 		= "InvoiceNotMade";
	public static final String INVOICE_MADE_NAME 			= "InvoiceMade";
	
	public static final short PAYMENT_MODE_NOT_PAID			= 1;
	public static final short PAYMENT_MODE_PARTIALLY_PAID	= 2;
	public static final short PAYMENT_MODE_NEGOTIABLE_PAID	= 3;
	public static final short PAYMENT_MODE_CLEAR_PAYMENT	= 4;
	
	public static final String PAYMENT_MODE_NOT_PAID_NAME			= "NOTPAID";
	public static final String PAYMENT_MODE_PARTIALLY_PAID_NAME		= "PARTIAL";
	public static final String PAYMENT_MODE_NEGOTIABLE_PAID_NAME	= "NEGOTIABLE";
	public static final String PAYMENT_MODE_CLEAR_PAYMENT_NAME		= "CLEAR PAYMENT";
	
	public static final short INVOICE_PAYMENT_NOT_MADE				= 1;
	public static final short INVOICE_PAYMENT_MADE					= 2;
	
	public static final String INVOICE_PAYMENT_NOT_MADE_NAME 		= "InvoicePaymentNotMade";
	public static final String INVOICE_PAYMENT_MADE_NAME 			= "InvoicePaymentMade";
	
	
	public static String getPickAndDrop(short status) throws Exception{
		String statusString		=	null;
		switch (status) {
		  case PICKUP:
			  statusString	= PICKUP_NAME;
		        break;
		  case DROP: 
			  statusString	= DROP_NAME;
		        break;
		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	}
	
	public static String getPaymentMode(short status) throws Exception{
		String statusString		=	null;
		switch (status) {
		  case PAYMENT_MODE_NOT_PAID:
			  statusString	= PAYMENT_MODE_NOT_PAID_NAME;
		        break;
		        
		  case PAYMENT_MODE_PARTIALLY_PAID: 
			  statusString	= PAYMENT_MODE_PARTIALLY_PAID_NAME;
		        break;
		        
		  case PAYMENT_MODE_NEGOTIABLE_PAID: 
			  statusString	= PAYMENT_MODE_NEGOTIABLE_PAID_NAME;
			  break;
			  
		  case PAYMENT_MODE_CLEAR_PAYMENT: 
			  statusString	= PAYMENT_MODE_CLEAR_PAYMENT_NAME;
			  break;
			  
		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	}
	
}