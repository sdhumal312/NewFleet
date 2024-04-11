package org.fleetopgroup.constant;

public class DriverAdvanceType {
	
	public static final short  DRIVER_ADVANCE_TYPE_ADVANCE	= 1;
	public static final short  DRIVER_ADVANCE_TYPE_PENALTY	= 2;
	
	public static final String DRIVER_ADVANCE_TYPE_ADVANCE_NAME = "ADVANCE";
	public static final String DRIVER_ADVANCE_TYPE_PENALTY_NAME = "PENALTY";
	
	
	
	public static String getAdvanceTypeName(short status) {

		String statusName		=	null;
		switch (status) {
		  case DRIVER_ADVANCE_TYPE_ADVANCE:
			  statusName	= DRIVER_ADVANCE_TYPE_ADVANCE_NAME;
		        break;
		  case DRIVER_ADVANCE_TYPE_PENALTY: 
			  statusName	= DRIVER_ADVANCE_TYPE_PENALTY_NAME;
		        break;
		 
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	
	}
}
