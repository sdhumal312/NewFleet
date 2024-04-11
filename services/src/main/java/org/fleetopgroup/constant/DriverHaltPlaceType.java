package org.fleetopgroup.constant;

public class DriverHaltPlaceType {

	public static final short DRIVERHALT_PALCE_TYPE_LOCALHALT	= 1;
	public static final short DRIVERHALT_PALCE_TYPE_TRIPHALT		= 2;
	
	
	public static final String ATTANDANCE_POINT_TYPE_LOCALHALT_NAME	= "LOCAL_HALT";
	public static final String ATTANDANCE_POINT_TYPE_TRIPHALT_NAME	= "TRIP_HALT";
	
	
	public static String getDriverHaltPlaceTypeName(short status) {

		String statusName		=	null;
		switch (status) {
		  case DRIVERHALT_PALCE_TYPE_LOCALHALT:
			  statusName	= ATTANDANCE_POINT_TYPE_LOCALHALT_NAME;
		        break;
		  case DRIVERHALT_PALCE_TYPE_TRIPHALT:
			  statusName	= ATTANDANCE_POINT_TYPE_TRIPHALT_NAME;
		        break;
		  
		 
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	
	}
}
