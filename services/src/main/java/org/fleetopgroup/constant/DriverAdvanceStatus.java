package org.fleetopgroup.constant;

public class DriverAdvanceStatus {

	public static final short DRIVER_ADVANCE_STATUS_OPEN	= 1;
	
	public static final short DRIVER_ADVANCE_STATUS_PAID	= 2;
	
	
	public static final String DRIVER_ADVANCE_STATUS_OPEN_NAME	= "OPEN";
	public static final String DRIVER_ADVANCE_STATUS_PAID_NAME	= "PAID";
	
	
	public static String getStausName(short status) {

		String statusName		=	null;
		switch (status) {
		  case DRIVER_ADVANCE_STATUS_OPEN:
			  statusName	= DRIVER_ADVANCE_STATUS_OPEN_NAME;
		        break;
		  case DRIVER_ADVANCE_STATUS_PAID: 
			  statusName	= DRIVER_ADVANCE_STATUS_PAID_NAME;
		        break;
		 
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	
	}
}
