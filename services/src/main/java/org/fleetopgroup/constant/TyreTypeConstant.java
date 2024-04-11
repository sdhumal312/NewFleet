package org.fleetopgroup.constant;

public class TyreTypeConstant {
	
	public static final short NEW_TYRE 										= 	1;
	public static final short RETREAD_TYRE	 								=	2;
	public static final short AVAILABLE_TO_SOLD	 							=	3;
	public static final short SCRAPED_TO_SOLD	 							=	4;
	public static final short WORKSHOP	 									=	5;
	
	public static final String NEW_TYRE_NAME 								=   "NEW_TYRE";
	public static final String RETREAD_TYRE_NAME 							=   "RETREAD_TYRE";
	public static final String AVAILABLE_TO_SOLD_NAME 						=   "AVAILABLE_TO_SOLD";
	public static final String SCRAPED_TO_SOLD_NAME 						=   "SCRAPED_TO_SOLD";
	public static final String WORKSHOP_NAME 								=   "WORKSHOP";
	
	
	public static String getTyreStatus (short tyreStatusId) throws Exception {
		String tyreStatus = null;
		switch(tyreStatusId) {
		case NEW_TYRE :
			tyreStatus 	= 	 NEW_TYRE_NAME;
			break;
		case RETREAD_TYRE :
			tyreStatus 	=	 RETREAD_TYRE_NAME;
			break;
		case AVAILABLE_TO_SOLD :
			tyreStatus 	=	 AVAILABLE_TO_SOLD_NAME;
			break;
		case SCRAPED_TO_SOLD :
			tyreStatus 	=	 SCRAPED_TO_SOLD_NAME;
			break;	
		default :
			tyreStatus = "--";
			break;
		}
		return tyreStatus;
	}
	
}