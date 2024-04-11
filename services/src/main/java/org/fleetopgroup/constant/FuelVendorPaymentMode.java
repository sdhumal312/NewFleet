package org.fleetopgroup.constant;

public class FuelVendorPaymentMode {
	
	public static final short PAYMENT_TYPE_PO				= 0;
	public static final short PAYMENT_MODE_PAID				= 1;
	public static final short PAYMENT_MODE_NOT_PAID			= 2;
	public static final short PAYMENT_MODE_APPROVED			= 3;
	public static final short PAYMENT_MODE_PARTIALLY_PAID	= 4;
	public static final short PAYMENT_MODE_NEGOTIABLE_PAID	= 5;
	public static final short PAYMENT_MODE_CREATE_APPROVAL	= 6;
	
	
	public static final String PAYMENT_TYPE_PO_NAME					= "PO";
	public static final String PAYMENT_MODE_PAID_NAME				= "PAID";
	public static final String PAYMENT_MODE_NOT_PAID_NAME			= "NOTPAID";
	public static final String PAYMENT_MODE_APPROVED_NAME			= "APPROVED";
	public static final String PAYMENT_MODE_PARTIALLY_PAID_NAME		= "PARTIAL";
	public static final String PAYMENT_MODE_NEGOTIABLE_PAID_NAME	= "NEGOTIABLE";
	public static final String PAYMENT_MODE_CREATE_APPROVAL_NAME	= "CREATE_APPROVAL";
	
	
	public static String getPaymentMode(short status) throws Exception{
		String statusString		=	null;
		switch (status) {
		case PAYMENT_TYPE_PO:
			statusString	= PAYMENT_TYPE_PO_NAME;
			break;
		  case PAYMENT_MODE_PAID:
			  statusString	= PAYMENT_MODE_PAID_NAME;
		        break;
		  case PAYMENT_MODE_NOT_PAID: 
			  statusString	= PAYMENT_MODE_NOT_PAID_NAME;
		        break;
		  case PAYMENT_MODE_APPROVED: 
			  statusString	= PAYMENT_MODE_APPROVED_NAME;
		        break;
		  case PAYMENT_MODE_PARTIALLY_PAID: 
			  statusString	= PAYMENT_MODE_PARTIALLY_PAID_NAME;
			  break;
		  case PAYMENT_MODE_NEGOTIABLE_PAID: 
			  statusString	= PAYMENT_MODE_NEGOTIABLE_PAID_NAME;
			  break;
		  case PAYMENT_MODE_CREATE_APPROVAL: 
			  statusString	= PAYMENT_MODE_CREATE_APPROVAL_NAME;
			  break;	  

		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	}
}
