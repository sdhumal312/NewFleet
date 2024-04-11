package org.fleetopgroup.constant;

public class LaundryClothReceiveHistoryType {
	
	public static final short LAUNDRY_RECEIVE_TYPE_RECEIVED 			=	1;
	public static final short LAUNDRY_RECEIVE_TYPE_DAMAGED				=	2;
	public static final short LAUNDRY_RECEIVE_TYPE_LOST					=	3;
	
	public static final String LAUNDRY_RECEIVE_TYPE_RECEIVED_NAME   	= "RECEIVED";
	public static final String LAUNDRY_RECEIVE_TYPE_DAMAGED_NAME		= "DAMAGED";	
	public static final String LAUNDRY_RECEIVE_TYPE_LOST_NAME			= "LOST";	
	
	
	public static String getLaundryClothReceiveTypeName(short LaundryReceiveTypeId ) {
		String LaundryReceiveTypeName 				= null;
		
		switch(LaundryReceiveTypeId) {
		case LAUNDRY_RECEIVE_TYPE_RECEIVED :
			LaundryReceiveTypeName = LAUNDRY_RECEIVE_TYPE_RECEIVED_NAME;
			break;
		case LAUNDRY_RECEIVE_TYPE_DAMAGED :
			LaundryReceiveTypeName = LAUNDRY_RECEIVE_TYPE_DAMAGED_NAME;
			break;
		case LAUNDRY_RECEIVE_TYPE_LOST :
			LaundryReceiveTypeName = LAUNDRY_RECEIVE_TYPE_LOST_NAME;
			break;
		default :
			LaundryReceiveTypeName = "--";
			break;
		}
		
		return LaundryReceiveTypeName;
	}
	
}