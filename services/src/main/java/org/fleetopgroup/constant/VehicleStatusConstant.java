package org.fleetopgroup.constant;

import java.io.Serializable;

public class VehicleStatusConstant implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final short VEHICLE_STATUS_ACTIVE			      = 1;
	public static final short VEHICLE_STATUS_INACTIVE		      = 2;
	public static final short VEHICLE_STATUS_SURRENDER		      = 3;
	public static final short VEHICLE_STATUS_SOLD			      = 4;
	public static final short VEHICLE_STATUS_TRIPROUTE		      = 5;
	public static final short VEHICLE_STATUS_WORKSHOP		      = 6;
	public static final short VEHICLE_STATUS_SEIZED		  		  = 7;
	public static final short VEHICLE_STATUS_ACCIDENT		   	  = 8;
	
	public static final String VEHICLE_STATUS_ACTIVE_NAME 			= "ACTIVE";
	public static final String VEHICLE_STATUS_INACTIVE_NAME 		= "INACTIVE";
	public static final String VEHICLE_STATUS_SURRENDER_NAME 		= "SURRENDER";
	public static final String VEHICLE_STATUS_SOLD_NAME 			= "SOLD";
	public static final String VEHICLE_STATUS_TRIPROUTE_NAME 		= "TRIPROUTE";
	public static final String VEHICLE_STATUS_WORKSHOP_NAME 		= "WORKSHOP";
	public static final String VEHICLE_STATUS_SEIZED_NAME 			= "SEIZED";
	public static final String VEHICLE_STATUS_ACCIDENT_NAME 		= "ACCIDENT";
	
	
	public static String getVehicleStatus(short status) throws Exception{
		String statusString		=	null;
		switch (status) {
		  case VEHICLE_STATUS_ACTIVE:
			  statusString	= VEHICLE_STATUS_ACTIVE_NAME;
		        break;
		  case VEHICLE_STATUS_INACTIVE: 
			  statusString	= VEHICLE_STATUS_INACTIVE_NAME;
		        break;
		  case VEHICLE_STATUS_SURRENDER: 
			  statusString	= VEHICLE_STATUS_SURRENDER_NAME;
		        break;
		  case VEHICLE_STATUS_SOLD: 
			  statusString	= VEHICLE_STATUS_SOLD_NAME;
		        break;
		  case VEHICLE_STATUS_TRIPROUTE: 
			  statusString	= VEHICLE_STATUS_TRIPROUTE_NAME;
		        break;
		  case VEHICLE_STATUS_WORKSHOP: 
			  statusString	= VEHICLE_STATUS_WORKSHOP_NAME;
		        break;
		  case VEHICLE_STATUS_SEIZED: 
			  statusString	= VEHICLE_STATUS_SEIZED_NAME;
		        break;
		  case VEHICLE_STATUS_ACCIDENT: 
			  statusString	= VEHICLE_STATUS_ACCIDENT_NAME;
		        break;
		 
		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	}

}
