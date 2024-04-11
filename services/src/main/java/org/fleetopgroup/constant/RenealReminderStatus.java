package org.fleetopgroup.constant;

public class RenealReminderStatus {

	public static final short NOT_APPROVED		= 1;
	public static final short APPROVED			= 2;
	public static final short OPEN				= 3;
	public static final short IN_PROGRESS		= 4;
	public static final short CANCELLED			= 5;
	public static final short REJECTED			= 6;
	
	
	public static final String NOT_APPROVED_NAME 		= "NOT APPROVED";
	public static final String APPROVED_NAME 			= "APPROVED";
	public static final String OPEN_NAME 				= "OPEN";
	public static final String IN_PROGRESS_NAME 		= "IN PROGRESS";
	public static final String CANCELLED_NAME 			= "CANCELED";
	public static final String REJECTED_NAME			= "REJECTED";
	
	
	
	public static String getRenewalStatusName(short paymentType) {

		String paymentTypeName		=	null;
		switch (paymentType) {
		  case NOT_APPROVED:
			  paymentTypeName	= NOT_APPROVED_NAME;
		        break;
		  case APPROVED:
			  paymentTypeName	= APPROVED_NAME;
		        break;
		  case OPEN:
			  paymentTypeName	= OPEN_NAME;
		        break;
		  case IN_PROGRESS:
			  paymentTypeName	= IN_PROGRESS_NAME;
		        break;
		  case CANCELLED:
			  paymentTypeName	= CANCELLED_NAME;
		        break;
		  case REJECTED:
			  paymentTypeName	= REJECTED_NAME;
		        break;
		 
		  default:
			  paymentTypeName = "--";
		        break;
		}
		return paymentTypeName;
	
	}
}
