package org.fleetopgroup.constant;

public class CashBookPaymentType {

	public static final short PAYMENT_TYPE_DEBIT  = 1;
	
	public static final short PAYMENT_TYPE_CREDIT = 2;
	
	public static final String PAYMENT_TYPE_DEBIT_NAME  = "DEBIT";
	public static final String PAYMENT_TYPE_CREDIT_NAME = "CREDIT";
	
	
	public static String getPaymentTypeName(short paymentType) {

		String paymentTypeName		=	null;
		switch (paymentType) {
		
		  case PAYMENT_TYPE_DEBIT:
			  paymentTypeName	= PAYMENT_TYPE_DEBIT_NAME;
		        break;
		  case PAYMENT_TYPE_CREDIT: 
			  paymentTypeName	= PAYMENT_TYPE_CREDIT_NAME;
		        break;
		
		  default:
			  paymentTypeName = "--";
		        break;
		}
		return paymentTypeName;
	
	}
}
