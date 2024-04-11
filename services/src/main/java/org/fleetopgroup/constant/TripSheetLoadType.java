package org.fleetopgroup.constant;

public class TripSheetLoadType {

	public static final short	LOAD_TYPE_OWN		= 1;
	public static final short	LOAD_TYPE_MARKET	= 2;
	
	
	public static final String	LOAD_TYPE_OWN_NAME		= "Own Load";
	public static final String	LOAD_TYPE_MARKET_NAME	= "Market Load";
	
	
	public static String getTripSheetLoadTypeName(short partTypeId) {

		String partTypeName		=	null;
		switch (partTypeId) {
		  case LOAD_TYPE_OWN:
			  partTypeName	= LOAD_TYPE_OWN_NAME;
		        break;
		  case LOAD_TYPE_MARKET: 
			  partTypeName	= LOAD_TYPE_MARKET_NAME;
		        break;
		
		  default:
			  partTypeName = "--";
		        break;
		}
		return partTypeName;
	
	}
}
