package org.fleetopgroup.constant;

public class EmiTenureType {

	public static final short  TENURE_TYPE_MONTHLY  = 1;
	public static final short  TENURE_TYPE_YEARLY   = 2;
	public static final short  TENURE_TYPE_DAYS	 	= 3;
	
	public static final String TENURE_TYPE_MONTHLY_NAME	= "MONTHLY";
	public static final String TENURE_TYPE_YEARLY_NAME	= "YEAR";
	public static final String TENURE_TYPE_DAYS_NAME	= "DAYS";
	
	
	public static String getName(short paymentType) {

		String paymentTypeName		=	null;
		switch (paymentType) {
		
		  case TENURE_TYPE_MONTHLY:
			  paymentTypeName	= TENURE_TYPE_MONTHLY_NAME;
		        break;
		  case TENURE_TYPE_YEARLY: 
			  paymentTypeName	= TENURE_TYPE_YEARLY_NAME;
		        break;
		  case TENURE_TYPE_DAYS: 
			  paymentTypeName	= TENURE_TYPE_DAYS_NAME;
		        break;
		
		  default:
			  paymentTypeName = "--";
		        break;
		}
		return paymentTypeName;
	
	}
}
