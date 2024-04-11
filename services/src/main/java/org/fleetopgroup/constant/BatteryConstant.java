package org.fleetopgroup.constant;

public class BatteryConstant {

	public static final short BATTERY_USES_STATUS_NEW	= 1;
	public static final short BATTERY_USES_STATUS_USED	= 2;
	
	public static final String BATTERY_USES_STATUS_NEW_NAME 	 = "NB";
	public static final String BATTERY_USES_STATUS_USED_NAME	 = "UB";
	
	public static final short BATTERY_ASIGNED_STATUS_AVAILABLE		= 1;
	public static final short BATTERY_ASIGNED_STATUS_IN_SERVICE		= 2;
	public static final short BATTERY_ASIGNED_STATUS_IN_SCRAPED		= 3;
	public static final short BATTERY_ASIGNED_STATUS_IN_MOUNT		= 4;
	public static final short BATTERY_ASIGNED_STATUS_IN_DISMOUNT	= 5;
	public static final short BATTERY_ASIGNED_STATUS_IN_TRANSIT		= 6;
	public static final short BATTERY_ASIGNED_STATUS_IN_UNAVAILABLE	= 7;
	
	public static final String BATTERY_ASIGNED_STATUS_AVAILABLE_NAME  	= "AVAILABLE";
	public static final String BATTERY_ASIGNED_STATUS_IN_SERVICE_NAME 	= "IN SERVICE";
	public static final String BATTERY_ASIGNED_STATUS_IN_SCRAPED_NAME 	= "SCRAPED";
	public static final String BATTERY_ASIGNED_STATUS_IN_MOUNT_NAME		= "MOUNTED";
	public static final String BATTERY_ASIGNED_STATUS_IN_DISMOUNT_NAME 	= "DISMOUNT";
	public static final String BATTERY_ASIGNED_STATUS_IN_TRANSIT_NAME 	= "IN TRANSFER";
	public static final String BATTERY_ASIGNED_STATUS_IN_UNAVAILABLE_NAME 	= "UAVAILABLE";
	
	public static final short BATTERY_WARRANTY_STATUS_UNDER_WARRANTY	= 1;
	public static final short BATTERY_WARRANTY_STATUS_OUT_OF_WARRANTY	= 2;
	
	public static final String BATTERY_WARRANTY_STATUS_UNDER_WARRANTY_NAME  = "Under Warranty";
	public static final String BATTERY_WARRANTY_STATUS_OUT_OF_WARRANTY_NAME = "Out of Warranty";
	
	public static final String SHOW_SUB_LOCATION		= "showSubLocation";
	public static final String MAIN_LOCATION_IDS		= "mainLocationIds";
	
	
	public static final short OLD_BATTERY_MOVED_TO_REPAIR	 				= 1;
	public static final short OLD_BATTERY_MOVED_TO_SCRAP	 				= 2;
	public static final short OLD_BATTERY_MOVED_TO_WORKSHOP	 				= 3;
	public static final short REPAIR_IN_PROCESS	 							= 4;
	public static final short REPAIR_COMPLETED	 							= 5;
	public static final short REPAIR_REJECT	 								= 6;
	
	public static final String OLD_BATTERY_MOVED_TO_REPAIR_NAME 			= "MOVED TO REPAIR";
	public static final String OLD_BATTERY_MOVED_TO_SCRAP_NAME 				= "MOVED TO SCRAP";
	public static final String OLD_BATTERY_MOVED_TO_WORKSHOP_NAME 			= "MOVED TO WORSHOP";
	public static final String REPAIR_IN_PROCESS_NAME 						= "REPAIR_IN_PROCESS";
	public static final String REPAIR_COMPLETED_NAME 						= "REPAIR_COMPLETED";
	public static final String REPAIR_REJECT_NAME 							= "REPAIR_REJECT";
	
	public static  String  getBatteryUsesStatusName(short type) throws Exception {
		String statusString = null;
		switch (type) {
		case BATTERY_USES_STATUS_NEW:
			statusString = BATTERY_USES_STATUS_NEW_NAME;
			break;
		case BATTERY_USES_STATUS_USED:
			statusString = BATTERY_USES_STATUS_USED_NAME;
			break;
		
		default:
			statusString = BATTERY_USES_STATUS_NEW_NAME;
			break;
		}
		return statusString;
	} 
	
	public static  String  getBatteryAsignedStatusName(short type) throws Exception {
		String statusString = null;
		switch (type) {
		case BATTERY_ASIGNED_STATUS_AVAILABLE:
			statusString = BATTERY_ASIGNED_STATUS_AVAILABLE_NAME;
			break;
		case BATTERY_ASIGNED_STATUS_IN_SERVICE:
			statusString = BATTERY_ASIGNED_STATUS_IN_SERVICE_NAME;
			break;
		case BATTERY_ASIGNED_STATUS_IN_SCRAPED:
			statusString = BATTERY_ASIGNED_STATUS_IN_SCRAPED_NAME;
			break;
		case BATTERY_ASIGNED_STATUS_IN_MOUNT:
			statusString = BATTERY_ASIGNED_STATUS_IN_MOUNT_NAME;
			break;
		case BATTERY_ASIGNED_STATUS_IN_DISMOUNT:
			statusString = BATTERY_ASIGNED_STATUS_IN_DISMOUNT_NAME;
			break;
		case BATTERY_ASIGNED_STATUS_IN_TRANSIT:
			statusString = BATTERY_ASIGNED_STATUS_IN_TRANSIT_NAME;
			break;
		case BATTERY_ASIGNED_STATUS_IN_UNAVAILABLE:
			statusString = BATTERY_ASIGNED_STATUS_IN_UNAVAILABLE_NAME;
			break;
		
		default:
			statusString = "--";
			break;
		}
		return statusString;
	} 
	
	public static  String  getWarrantyStatusName(short type) throws Exception {
		String statusString = null;
		switch (type) {
		case BATTERY_WARRANTY_STATUS_UNDER_WARRANTY:
			statusString = BATTERY_WARRANTY_STATUS_UNDER_WARRANTY_NAME;
			break;
		case BATTERY_WARRANTY_STATUS_OUT_OF_WARRANTY:
			statusString = BATTERY_WARRANTY_STATUS_OUT_OF_WARRANTY_NAME;
			break;
		
		default:
			statusString = "--";
			break;
		}
		return statusString;
	} 
	
	public static String getOldBatteryMovedTo (short oldBatteryMovedToId) throws Exception {
		String oldBatteryMovedTo = null;
		switch(oldBatteryMovedToId) {
		case OLD_BATTERY_MOVED_TO_REPAIR :
			oldBatteryMovedTo 	= 	 OLD_BATTERY_MOVED_TO_REPAIR_NAME;
			break;
		case OLD_BATTERY_MOVED_TO_SCRAP :
			oldBatteryMovedTo 	=	 OLD_BATTERY_MOVED_TO_SCRAP_NAME;
			break;
		case OLD_BATTERY_MOVED_TO_WORKSHOP :
			oldBatteryMovedTo 	=	 OLD_BATTERY_MOVED_TO_WORKSHOP_NAME;
			break;
		case REPAIR_IN_PROCESS :
			oldBatteryMovedTo 	=	 REPAIR_IN_PROCESS_NAME;
			break;
		case REPAIR_COMPLETED :
			oldBatteryMovedTo 	=	 REPAIR_COMPLETED_NAME;
			break;
		case REPAIR_REJECT :
			oldBatteryMovedTo 	=	 REPAIR_REJECT_NAME;
			break;
		
		default :
			oldBatteryMovedTo = "--";
			break;
		}
		return oldBatteryMovedTo;
	}
	
}
