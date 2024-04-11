package org.fleetopgroup.constant;

public class VehiclePhotoTypeConstant {
	
	public static final short	VEHICLE_PHOTO				= 1;
	public static final short	VEHICLE_ACCIDENT_PHOTO		= 2;
	
	public static final String VEHICLE_PHOTO_NAME			= "Vehicle Photo";
	public static final String VEHICLE_ACCIDENT_PHOTO_NAME	= "Vehicle Accident Photo";
	
	public static String getVehiclePhotoTypeName(short photoType) {

		String photoTypeName		=	null;
		switch (photoType) {
		
		  case VEHICLE_PHOTO:
			  photoTypeName	= VEHICLE_PHOTO_NAME;
		        break;
		  case VEHICLE_ACCIDENT_PHOTO: 
			  photoTypeName	= VEHICLE_ACCIDENT_PHOTO_NAME;
		        break;
		
		  default:
			  photoTypeName = "--";
		        break;
		}
		return photoTypeName;
	
	}
}
