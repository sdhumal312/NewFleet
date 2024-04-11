package org.fleetopgroup.constant;

public class TripCloseStatus {

	public static final short TRIP_CLOSE_STATUS_OFFICE = 1;
	public static final short TRIP_CLOSE_STATUS_DRIVER = 2;
	
	public static final String TRIP_CLOSE_STATUS_OFFICE_NAME	= "OFFICE";
	public static final String TRIP_CLOSE_STATUS_DRIVER_NAME	= "DRIVER";
	
	
	public static String getTripSheetCloseStatusName(short status) {

		String statusName		=	null;
		switch (status) {
		  case TRIP_CLOSE_STATUS_OFFICE:
			  statusName	= TRIP_CLOSE_STATUS_OFFICE_NAME;
		        break;
		  case TRIP_CLOSE_STATUS_DRIVER: 
			  statusName	= TRIP_CLOSE_STATUS_DRIVER_NAME;
		        break;
		 
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	
	}
}
