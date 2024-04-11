package org.fleetopgroup.constant;

public class DriverAdvanceConstant {

	public static final short DRIVER_ADVANCE_AMOUNT		  = 1;
	public static final short DRIVER_PENALTY_AMOUNT		  = 2;


	public static final String DRIVER_ADVANCE_AMOUNT_NAME		= "ADVANCE";
	public static final String DRIVER_PENALTY_AMOUNT_NAME		= "PENALTY";


	public static String getAdavnceType(short status) {

		String statusName		=	null;
		switch (status) {
		  case DRIVER_ADVANCE_AMOUNT:
			  statusName	= DRIVER_ADVANCE_AMOUNT_NAME;
		        break;
		  case DRIVER_PENALTY_AMOUNT: 
			  statusName	= DRIVER_PENALTY_AMOUNT_NAME;
		        break;
		  default:
			  statusName = "DRIVER_ADVANCE_AMOUNT_NAME";
		        break;
		}
		return statusName;
	}
	
	
}
