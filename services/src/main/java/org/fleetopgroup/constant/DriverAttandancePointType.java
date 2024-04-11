package org.fleetopgroup.constant;

public class DriverAttandancePointType {

	public static final short ATTANDANCE_POINT_TYPE_LOCALHALT	= 1;
	public static final short ATTANDANCE_POINT_TYPE_TRIPHALT		= 2;
	public static final short ATTANDANCE_POINT_TYPE_TRIP			= 3;
	
	
	public static final String ATTANDANCE_POINT_TYPE_LOCALHALT_NAME	= "LOCAL_HALT";
	public static final String ATTANDANCE_POINT_TYPE_TRIPHALT_NAME	= "TRIP_HALT";
	public static final String ATTANDANCE_POINT_TYPE_TRIP_NAME		= "TRIP";
	
	
	public static String getPointTypeName(short status) {

		String statusName		=	null;
		switch (status) {
		  case ATTANDANCE_POINT_TYPE_LOCALHALT:
			  statusName	= ATTANDANCE_POINT_TYPE_LOCALHALT_NAME;
		        break;
		  case ATTANDANCE_POINT_TYPE_TRIPHALT:
			  statusName	= ATTANDANCE_POINT_TYPE_TRIPHALT_NAME;
		        break;
		  case ATTANDANCE_POINT_TYPE_TRIP: 
			  statusName	= ATTANDANCE_POINT_TYPE_TRIP_NAME;
		        break;
		 
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	
	}
}
