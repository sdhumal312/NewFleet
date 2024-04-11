package org.fleetopgroup.constant;

public class FuelInvoiceConstant {

	public static final short STOCK_TYPE_DEFAULT		= 0;
	public static final short STOCK_TYPE_SHORT			= 1;
	public static final short STOCK_TYPE_EXCESS			= 2;
	public static final short STOCK_TYPE_FE_CREATE		= 3;
	public static final short STOCK_TYPE_FE_EDIT		= 4;
	public static final short STOCK_TYPE_FE_DELETE		= 5;
	public static final short STOCK_TYPE_FI_EDIT		= 6;
	public static final short STOCK_TYPE_FI_DELETE		= 7;
	
	public static final String STOCK_TYPE_DEFAULT_NAME 	 	= "SAVED FUEL INVOICE";
	public static final String STOCK_TYPE_SHORT_NAME 	 	= "SHORT";
	public static final String STOCK_TYPE_EXCESS_NAME	 	= "EXCESS";
	public static final String STOCK_TYPE_FE_CREATE_NAME	= "FE CREATE";
	public static final String STOCK_TYPE_FE_EDIT_NAME	 	= "FE EDIT";
	public static final String STOCK_TYPE_FE_DELETE_NAME	= "FE DELETE";
	public static final String STOCK_TYPE_FI_EDIT_NAME	 	= "FI EDIT";
	public static final String STOCK_TYPE_FI_DELETE_NAME	= "FI DELETE";
	
	
	
	public static  String  getStockType(short type) throws Exception {
		String statusString = null;
		switch (type) {
		case STOCK_TYPE_SHORT:
			statusString = STOCK_TYPE_SHORT_NAME;
			break;
		case STOCK_TYPE_EXCESS:
			statusString = STOCK_TYPE_EXCESS_NAME;
			break;
		case STOCK_TYPE_FE_CREATE:
			statusString = STOCK_TYPE_FE_CREATE_NAME;
			break;
		case STOCK_TYPE_FE_EDIT:
			statusString = STOCK_TYPE_FE_EDIT_NAME;
			break;
		case STOCK_TYPE_FE_DELETE:
			statusString = STOCK_TYPE_FE_DELETE_NAME;
			break;
		case STOCK_TYPE_FI_EDIT:
			statusString = STOCK_TYPE_FI_EDIT_NAME;
			break;
		case STOCK_TYPE_FI_DELETE:
			statusString = STOCK_TYPE_FI_DELETE_NAME;
			break;
		
		default:
			statusString = STOCK_TYPE_DEFAULT_NAME;
			break;
		}
		return statusString;
	} 
	
}
