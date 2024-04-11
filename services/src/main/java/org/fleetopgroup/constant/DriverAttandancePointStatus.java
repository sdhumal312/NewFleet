package org.fleetopgroup.constant;

public class DriverAttandancePointStatus {

	public static final short DRIVER_ATTANDANCE_POINT_STATUS_POINT	= 1;
	
	public static final short DRIVER_ATTANDANCE_POINT_STATUS_NO		= 2;
	
	public static final String DRIVER_ATTANDANCE_POINT_STATUS_POINT_NAME	= "POINT";
	public static final String DRIVER_ATTANDANCE_POINT_STATUS_NO_NAME		= "NO";
	
	
	public static String getPointStausName(short status) {

		String statusName		=	null;
		switch (status) {
		  case DRIVER_ATTANDANCE_POINT_STATUS_POINT:
			  statusName	= DRIVER_ATTANDANCE_POINT_STATUS_POINT_NAME;
		        break;
		  case DRIVER_ATTANDANCE_POINT_STATUS_NO: 
			  statusName	= DRIVER_ATTANDANCE_POINT_STATUS_NO_NAME;
		        break;
		 
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	
	}
}
