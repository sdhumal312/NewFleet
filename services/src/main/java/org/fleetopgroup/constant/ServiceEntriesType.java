package org.fleetopgroup.constant;

public class ServiceEntriesType {

	public static final short SERVICE_ENTRIES_STATUS_OPEN		= 1;
	public static final short SERVICE_ENTRIES_STATUS_INPROCESS	= 2;
	public static final short SERVICE_ENTRIES_STATUS_COMPLETE	= 3;
	
	
	public static final String SERVICE_ENTRIES_STATUS_OPEN_NAME			= "OPEN";
	public static final String SERVICE_ENTRIES_STATUS_INPROCESS_NAME	= "INPROCESS";
	public static final String SERVICE_ENTRIES_STATUS_COMPLETE_NAME		= "COMPLETE";
	
	
	public static final short PAYMENT_MODE_PAID				= 1;
	public static final short PAYMENT_MODE_NOT_PAID			= 2;
	public static final short PAYMENT_MODE_APPROVED			= 3;
	public static final short PAYMENT_MODE_PARTIALLY_PAID	= 4;
	public static final short PAYMENT_MODE_NEGOTIABLE_PAID	= 5;
	public static final short PAYMENT_MODE_CREATE_APPROVAL	= 6;
	
	public static final String	PAYMENT_MODE_PAID_NAME				= "PAID";
	public static final String	PAYMENT_MODE_NOT_PAID_NAME			= "NOTPAID";
	public static final String	PAYMENT_MODE_APPROVED_NAME			= "APPROVED";
	public static final String PAYMENT_MODE_PARTIALLY_PAID_NAME		= "PARTIAL";
	public static final String PAYMENT_MODE_NEGOTIABLE_PAID_NAME	= "NEGOTIABLE";
	public static final String PAYMENT_MODE_CREATE_APPROVAL_NAME	= "CREATE_APPROVAL";
	
	public static String getStatusName(short status) {

		String statusName		=	null;
		switch (status) {
		  case SERVICE_ENTRIES_STATUS_OPEN:
			  statusName	= SERVICE_ENTRIES_STATUS_OPEN_NAME;
		        break;
		  case SERVICE_ENTRIES_STATUS_INPROCESS: 
			  statusName	= SERVICE_ENTRIES_STATUS_INPROCESS_NAME;
		        break;
		  case SERVICE_ENTRIES_STATUS_COMPLETE: 
			  statusName	= SERVICE_ENTRIES_STATUS_COMPLETE_NAME;
		        break;
		  
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	}
	
	public static String getPaymentModeName(short status) {

		String statusName		=	null;
		switch (status) {
		  case PAYMENT_MODE_PAID:
			  statusName	= PAYMENT_MODE_PAID_NAME;
		        break;
		  case PAYMENT_MODE_NOT_PAID: 
			  statusName	= PAYMENT_MODE_NOT_PAID_NAME;
		        break;
		  case PAYMENT_MODE_APPROVED: 
			  statusName	= PAYMENT_MODE_APPROVED_NAME;
		        break;
		  case PAYMENT_MODE_PARTIALLY_PAID: 
			  statusName	= PAYMENT_MODE_PARTIALLY_PAID_NAME;
		        break;
		  case PAYMENT_MODE_NEGOTIABLE_PAID: 
			  statusName	= PAYMENT_MODE_NEGOTIABLE_PAID_NAME;
		        break;
		  case PAYMENT_MODE_CREATE_APPROVAL: 
			  statusName	= PAYMENT_MODE_CREATE_APPROVAL_NAME;
		        break; 
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	}
}
