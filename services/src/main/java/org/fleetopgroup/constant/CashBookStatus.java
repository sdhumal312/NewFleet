package org.fleetopgroup.constant;

public class CashBookStatus {

	public static final short CASH_BOOK_STATUS_OPEN			= 1;
	public static final short CASH_BOOK_STATUS_CLOSED		= 2;
	
	public static final String CASH_BOOK_STATUS_OPEN_NAME	= "OPEN";
	public static final String CASH_BOOK_STATUS_CLOSED_NAME	= "CLOSED";
	
	public static final String CASH_BOOK_STATUS	= "showCashBookStatus";
	
	public static String getCashBookStatusName(short paymentType) {

		String paymentTypeName		=	null;
		switch (paymentType) {
		
		  case CASH_BOOK_STATUS_OPEN:
			  paymentTypeName	= CASH_BOOK_STATUS_OPEN_NAME;
		        break;
		  case CASH_BOOK_STATUS_CLOSED: 
			  paymentTypeName	= CASH_BOOK_STATUS_CLOSED_NAME;
		        break;
		
		  default:
			  paymentTypeName = "--";
		        break;
		}
		return paymentTypeName;
	
	}
}
