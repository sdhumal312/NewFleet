package org.fleetopgroup.constant;

public class CashBookBalanceStatus {

	public static final short CASHBOOK_BALANCE_STATUS_MAIN 					= 1;
	public static final short CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK		    = 2;
	
	public static final String CASHBOOK_BALANCE_STATUS_MAIN_NAME				= "MAIN";
	public static final String CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK_NAME	= "MAIN-CASH-BOOK";
	
	public static final String SSTS_CASH_BOOK_NAME								= "SSTS CASH BOOK";
	public static final String JBL_CASH_BOOK_NAME								= "JBL CASH BOOK";
	public static final String COMBINED_CASHBOOK_NAME							= "COMBINED CASH BOOK";
	
	public static String getCashBookBalanceStatusName(short paymentType) {

		String paymentTypeName		=	null;
		switch (paymentType) {
		
		  case CASHBOOK_BALANCE_STATUS_MAIN:
			  paymentTypeName	= CASHBOOK_BALANCE_STATUS_MAIN_NAME;
		        break;
		  case CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK: 
			  paymentTypeName	= CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK_NAME;
		        break;
		
		  default:
			  paymentTypeName = "--";
		        break;
		}
		return paymentTypeName;
	
	}
}
