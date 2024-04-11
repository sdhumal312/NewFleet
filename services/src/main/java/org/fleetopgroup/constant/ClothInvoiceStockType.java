package org.fleetopgroup.constant;

public class ClothInvoiceStockType {

	public static final short	STOCK_TYPE_NEW	= 1;
	public static final short	STOCK_TYPE_OLD	= 2;
	
	
	public static final String	STOCK_TYPE_NEW_NAME	= "NEW";
	public static final String	STOCK_TYPE_OLD_NAME	= "OLD";
	
	
	public static final short	CLOTH_ASIGN_TYPE_ASIGN	= 1;
	public static final short	CLOTH_ASIGN_TYPE_REMOVE	= 2;
	public static final short	CLOTH_ASIGN_TYPE_EDIT	= 3;
	
	public static final String	CLOTH_ASIGN_TYPE_ASIGN_NAME			= "ASIGN";
	public static final String	CLOTH_ASIGN_TYPE_REMOVE_NAME		= "REMOVE";
	public static final String	CLOTH_ASIGN_TYPE_EDIT_NAME			= "EDIT";
	
	
	public static String getClothInvoiceStockTypeNAme(short driverSalaryTypeId) throws Exception {
		String driverSalaryType = null;
		switch (driverSalaryTypeId) {
		case STOCK_TYPE_NEW:
			driverSalaryType = STOCK_TYPE_NEW_NAME;
			break;
		case STOCK_TYPE_OLD:
			driverSalaryType = STOCK_TYPE_OLD_NAME;
			break;
		default:
			driverSalaryType = "--";
			break;
		}
		return driverSalaryType;
	}
	
	public static String getClothInvoiceAsignNAme(short driverSalaryTypeId) throws Exception {
		String driverSalaryType = null;
		switch (driverSalaryTypeId) {
		case CLOTH_ASIGN_TYPE_ASIGN:
			driverSalaryType = CLOTH_ASIGN_TYPE_ASIGN_NAME;
			break;
		case CLOTH_ASIGN_TYPE_REMOVE:
			driverSalaryType = CLOTH_ASIGN_TYPE_REMOVE_NAME;
			break;
		case CLOTH_ASIGN_TYPE_EDIT:
			driverSalaryType = CLOTH_ASIGN_TYPE_EDIT_NAME;
			break;	
		default:
			driverSalaryType = "--";
			break;
		}
		return driverSalaryType;
	}
}
