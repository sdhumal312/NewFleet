package org.fleetopgroup.constant;

public class TripSheetFixedExpenseType {
	
	public static final short	EXPENSE_TYPE_HALT_BATA	= 1;
	
	public static final String EXPENSE_TYPE_HALT_BATA_NAME	= "HALT BATA";
	
	
	public static String getTripExpenseName(short status) {

		String statusName		=	null;
		switch (status) {
		  case EXPENSE_TYPE_HALT_BATA:
			  statusName	= EXPENSE_TYPE_HALT_BATA_NAME;
		        break;
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	
	}
	
}
