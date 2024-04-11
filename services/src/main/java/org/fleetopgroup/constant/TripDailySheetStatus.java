package org.fleetopgroup.constant;

public class TripDailySheetStatus {

	public static final short TRIP_STATUS_OPEN		= 1;
	public static final short TRIP_STATUS_CLOSED	= 2;
	
	public static final String TRIP_STATUS_OPEN_NAME 	= "OPEN";
	public static final String TRIP_STATUS_CLOSED_NAME  = "CLOSED";
	
	
	public static String getTripSheetStatusName(short status) {

		String statusName		=	null;
		switch (status) {
		  case TRIP_STATUS_OPEN:
			  statusName	= TRIP_STATUS_OPEN_NAME;
		        break;
		  case TRIP_STATUS_CLOSED: 
			  statusName	= TRIP_STATUS_CLOSED_NAME;
		        break;
		 
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	
	}
}
