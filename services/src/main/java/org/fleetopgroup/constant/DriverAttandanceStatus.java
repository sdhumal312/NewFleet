package org.fleetopgroup.constant;

public class DriverAttandanceStatus {
	
	public static final short	DRIVER_ATTANDANCE_STATUS_PASS  = 1;
	public static final short	DRIVER_ATTANDANCE_STATUS_HPASS = 2;
	
	public static final String DRIVER_ATTANDANCE_STATUS_PASS_NAME  = "PASS";
	public static final String DRIVER_ATTANDANCE_STATUS_HPASS_NAME = "HPASS";
	
	
	public static String getStausName(short status) {

		String statusName		=	null;
		switch (status) {
		  case DRIVER_ATTANDANCE_STATUS_PASS:
			  statusName	= DRIVER_ATTANDANCE_STATUS_PASS_NAME;
		        break;
		  case DRIVER_ATTANDANCE_STATUS_HPASS: 
			  statusName	= DRIVER_ATTANDANCE_STATUS_HPASS_NAME;
		        break;
		 
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	
	}
}
