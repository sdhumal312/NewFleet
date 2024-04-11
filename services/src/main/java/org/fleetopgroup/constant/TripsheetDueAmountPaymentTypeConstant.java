package org.fleetopgroup.constant;

public class TripsheetDueAmountPaymentTypeConstant {
	
	public static final short PAYMENT_MODE_AMOUNT			= 1;
	public static final short PAYMENT_MODE_EXPENSE			= 2;
	
	public static final String PAYMENT_MODE_AMOUNT_NAME			= "AMOUNT";
	public static final String PAYMENT_MODE_EXPENSE_NAME		= "EXPENSE";
	
	public static final short PAYMENT_MODE_NOT_PAID			= 1;
	public static final short PAYMENT_MODE_PARTIALLY_PAID	= 2;
	public static final short PAYMENT_MODE_NEGOTIABLE_PAID	= 3;
	public static final short PAYMENT_MODE_PAID				= 4;
	
	public static final String PAYMENT_MODE_NOT_PAID_NAME			= "NOTPAID";
	public static final String PAYMENT_MODE_PARTIALLY_PAID_NAME		= "PARTIAL";
	public static final String PAYMENT_MODE_NEGOTIABLE_PAID_NAME	= "NEGOTIABLE";
	public static final String PAYMENT_MODE_PAID_NAME				= "PAID";
	
	
	public static String getPaymentMode(short status) throws Exception{
		String statusString		=	null;
		switch (status) {
		  case PAYMENT_MODE_AMOUNT:
			  statusString	= PAYMENT_MODE_AMOUNT_NAME;
		        break;
		  case PAYMENT_MODE_EXPENSE: 
			  statusString	= PAYMENT_MODE_EXPENSE_NAME;
		        break;
		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	}
	
	public static String getPaymentType(short status) throws Exception{
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
			  
		  case PAYMENT_MODE_PAID: 
			  statusString	= PAYMENT_MODE_PAID_NAME;
			  break;
			  
		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	}
	
}	