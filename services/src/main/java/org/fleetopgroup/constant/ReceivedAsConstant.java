package org.fleetopgroup.constant;

public class ReceivedAsConstant {

	public static final short PAYMENT_MODE_CLEARED			= 2;
	public static final short PAYMENT_MODE_PARTIALLY_PAID	= 3;
	public static final short PAYMENT_MODE_NEGOTIATED		= 4;
	
	public static final String	PAYMENT_MODE_CLEARED_NAME			= "Clear Payment";
	public static final String	PAYMENT_MODE_PARTIALLY_PAID_NAME	= "Partial Payment";
	public static final String	PAYMENT_MODE_NEGOTIATED_NAME		= "Negotiated";
	
	public static String getPaymentMode(short status) throws Exception{
		String statusString		=	null;
		switch (status) {
		 
		  case PAYMENT_MODE_CLEARED: 
			  statusString	= PAYMENT_MODE_CLEARED_NAME;
		        break;
		  case PAYMENT_MODE_PARTIALLY_PAID: 
			  statusString	= PAYMENT_MODE_PARTIALLY_PAID_NAME;
		        break;
		  case PAYMENT_MODE_NEGOTIATED: 
			  statusString	= PAYMENT_MODE_NEGOTIATED_NAME;
			  break;
		

		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	}
	
}
