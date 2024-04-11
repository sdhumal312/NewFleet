package org.fleetopgroup.constant;

public class TripSheetStatus {

	public static final short	TRIP_STATUS_SAVED				= 1;
	public static final short	TRIP_STATUS_DISPATCHED			= 2;
	public static final short	TRIP_STATUS_CLOSED				= 3;
	public static final short	TRIP_STATUS_ACCOUNT_CLOSED		= 4;
	
	public static final String TRIP_STATUS_SAVED_NAME				= "SAVED";
	public static final String TRIP_STATUS_DISPATCHED_NAME			= "DISPATCHED";
	public static final String TRIP_STATUS_CLOSED_NAME				= "CLOSED";
	public static final String TRIP_STATUS_ACCOUNT_CLOSED_NAME		= "A/C CLOSED";
	
	public static final short	SINGLE_TRIP_ROUTE_POINT 		= 1;
	public static final short	ROUND_TRIP_ROUTE_POINT		    = 2;
	public static final String SINGLE_TRIP_ROUTE_POINT_NAME			= "SINGLE";
	public static final String ROUND_TRIP_ROUTE_POINT_NAME			= "ROUND";
	
	public static final short TRIP_CLOSING_KM_STATUS_NOTWORKING					= 1;
	
	public static final String TRIP_CLOSING_KM_STATUS_NOTWORKING_NAME	= "NOTWORKING";
	
	
	public static String getTripSheetStatusName(short status) {

		String statusName		=	null;
		switch (status) {
		  case TRIP_STATUS_SAVED:
			  statusName	= TRIP_STATUS_SAVED_NAME;
		        break;
		  case TRIP_STATUS_DISPATCHED: 
			  statusName	= TRIP_STATUS_DISPATCHED_NAME;
		        break;
		  case TRIP_STATUS_CLOSED: 
			  statusName	= TRIP_STATUS_CLOSED_NAME;
		        break;
		  case TRIP_STATUS_ACCOUNT_CLOSED: 
			  statusName	= TRIP_STATUS_ACCOUNT_CLOSED_NAME;
		        break;
		 
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	
	}
	
	public static String getTripKmClosingStatusName(short status) {

		String statusName		=	null;
		switch (status) {
		  case TRIP_CLOSING_KM_STATUS_NOTWORKING:
			  statusName	= TRIP_CLOSING_KM_STATUS_NOTWORKING_NAME;
		        break;
		 
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	
	}
}
