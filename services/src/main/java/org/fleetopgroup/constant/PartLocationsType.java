package org.fleetopgroup.constant;

public class PartLocationsType {

	public static final short PART_LOCATION_TYPE_MAIN_LOCATION				= 1;
	public static final short PART_LOCATION_TYPE_SUB_LOCATION				= 2;
	
	public static final String PART_LOCATION_TYPE_MAIN_LOCATION_NAME			= "MAIN LOCATION";
	public static final String PART_LOCATION_TYPE_SUB_LOCATION_NAME				= "SUB LOCATION";
	
	public static String getPartLocationType(short partLocationType) {

		String partLocationTypeName		=	null;
		switch (partLocationType) {
		
		  case PART_LOCATION_TYPE_MAIN_LOCATION:
			  partLocationTypeName	= PART_LOCATION_TYPE_MAIN_LOCATION_NAME;
		        break;
		  case PART_LOCATION_TYPE_SUB_LOCATION: 
			  partLocationTypeName	= PART_LOCATION_TYPE_SUB_LOCATION_NAME;
		        break;
		  
		  default:
			  partLocationTypeName = "--";
		        break;
		}
		return partLocationTypeName;
	
	}
}
