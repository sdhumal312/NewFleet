package org.fleetopgroup.constant;

public class CashBookApprovalStatus {

	public static final short CASH_BOOK_APPROVAL_STATUS_NOT_APPROVED 		= 1;
	public static final short CASH_BOOK_APPROVAL_STATUS_APPROVED 			= 2;
	
	public static final String CASH_BOOK_APPROVAL_STATUS_NOT_APPROVED_NAME   = "NOT APPROVED";
	public static final String CASH_BOOK_APPROVAL_STATUS_APPROVED_NAME 		 = "APPROVED";
	
	
	public static String getCashBookApprovalStatusName(short paymentType) {

		String paymentTypeName		=	null;
		switch (paymentType) {
		
		  case CASH_BOOK_APPROVAL_STATUS_NOT_APPROVED:
			  paymentTypeName	= CASH_BOOK_APPROVAL_STATUS_NOT_APPROVED_NAME;
		        break;
		  case CASH_BOOK_APPROVAL_STATUS_APPROVED: 
			  paymentTypeName	= CASH_BOOK_APPROVAL_STATUS_APPROVED_NAME;
		        break;
		
		  default:
			  paymentTypeName = "--";
		        break;
		}
		return paymentTypeName;
	
	}
}
