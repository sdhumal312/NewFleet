package org.fleetopgroup.constant;

public class VehicleInspectionStatus {

	public static final short	VEHICLE_INSPECTION_STATUS_OPEN			= 0;
	public static final short	VEHICLE_INSPECTION_STATUS_SAVED			= 1;
	public static final short	VEHICLE_INSPECTION_STATUS_INSPECTED		= 2;
	
	public static final String	VEHICLE_INSPECTION_STATUS_OPEN_NAME				= "OPEN";
	public static final String	VEHICLE_INSPECTION_STATUS_SAVED_NAME			= "SAVED";
	public static final String	VEHICLE_INSPECTION_STATUS_INSPECTED_NAME		= "INSPECTED";
	
	
	public static final short	VEHICLE_INSPECTION_COMPLETION_STATUS_SAVE		= 1;
	public static final short	VEHICLE_INSPECTION_COMPLETION_STATUS_SUBMIT		= 2;
	
	public static final String	VEHICLE_INSPECTION_COMPLETION_STATUS_SAVE_NAME			= "SAVED";
	public static final String	VEHICLE_INSPECTION_COMPLETION_STATUS_SUBMIT_NAME		= "SUBMITTED";
	
	
	public static String getVehicleInspectionStatusName(short partTypeId) {

		String partTypeName		=	null;
		switch (partTypeId) {
		  case VEHICLE_INSPECTION_STATUS_OPEN:
			  partTypeName	= VEHICLE_INSPECTION_STATUS_OPEN_NAME;
		        break;
		  case VEHICLE_INSPECTION_STATUS_SAVED: 
			  partTypeName	= VEHICLE_INSPECTION_STATUS_SAVED_NAME;
		        break;
		  case VEHICLE_INSPECTION_STATUS_INSPECTED: 
			  partTypeName	= VEHICLE_INSPECTION_STATUS_INSPECTED_NAME;
		        break;
		
		  default:
			  partTypeName = "--";
		        break;
		}
		return partTypeName;
	
	}
	
	public static String getVehicleInspectionCompletionStatusName(short partTypeId) {

		String partTypeName		=	null;
		switch (partTypeId) {
		  case VEHICLE_INSPECTION_COMPLETION_STATUS_SAVE:
			  partTypeName	= VEHICLE_INSPECTION_COMPLETION_STATUS_SAVE_NAME;
		        break;
		  case VEHICLE_INSPECTION_COMPLETION_STATUS_SUBMIT: 
			  partTypeName	= VEHICLE_INSPECTION_COMPLETION_STATUS_SUBMIT_NAME;
		        break;
		 
		
		  default:
			  partTypeName = "--";
		        break;
		}
		return partTypeName;
	
	}
	
	
}
