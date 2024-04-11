package org.fleetopgroup.constant;

public class RenewalApprovalStatus {
	
	public static final short OPEN				= 1;
	public static final short APPROVED			= 2;
	public static final short PAID				= 3;
	
	
	public static final String OPEN_NAME 			= "OPEN";
	public static final String APPROVED_NAME 		= "APPROVED";
	public static final String PAID_NAME 			= "PAID";
	
	
	
	public static String getRenewalStatusName(short paymentType) {

		String paymentTypeName		=	null;
		switch (paymentType) {
		
		  case OPEN:
			  paymentTypeName	= OPEN_NAME;
		        break;
		        
		  case APPROVED:
			  paymentTypeName	= APPROVED_NAME;
		        break;
		
		  case PAID:
			  paymentTypeName	= PAID_NAME;
		        break;
		 
		  default:
			  paymentTypeName = "--";
		        break;
		}
		return paymentTypeName;
	
	}
}
