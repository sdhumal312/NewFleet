package org.fleetopgroup.constant;

public class VendorGSTRegistered {

	public static final short VENDOR_GST_NOT_REGISTERED		= 1;
	public static final short VENDOR_GST_REGISTERED			= 2;
	
	public static final String VENDOR_GST_NOT_REGISTERED_NAME	= "Turnover Below 25 lakhs";
	public static final String VENDOR_GST_REGISTERED_NAME		= "Turnover Above 25 lakhs";
	
	public static String getVendorRegisterdName(short paymentType) {

		String paymentTypeName		=	null;
		switch (paymentType) {
		
		  case VENDOR_GST_NOT_REGISTERED:
			  paymentTypeName	= VENDOR_GST_NOT_REGISTERED_NAME;
		        break;
		  case VENDOR_GST_REGISTERED: 
			  paymentTypeName	= VENDOR_GST_REGISTERED_NAME;
		        break;
		 
		
		  default:
			  paymentTypeName = "--";
		        break;
		}
		return paymentTypeName;
	
	}
}
