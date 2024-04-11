package org.fleetopgroup.constant;

public class FastTagBankConstant  {

	public static final short FASTTAG_BANK_ICICI_ID		= 1;
	public static final short FASTTAG_BANK_KVB_ID		= 2;
	public static final short FASTTAG_BANK_HDFC_ID		= 3;
	public static final short FASTTAG_BANK_YESBANK_ID	= 4;
	public static final short FASTTAG_BANK_KARINS		= 5;
	
	public static final String FASTTAG_BANK_ICICI_NAME		= "ICICI Bank";
	public static final String FASTTAG_BANK_KVB_NAME		= "KVB Bank";
	public static final String FASTTAG_BANK_HDFC_NAME		= "HDFC Bank";
	public static final String FASTTAG_BANK_YESBANK_NAME	= "Yes Bank";
	public static final String FASTTAG_BANK_KARINS_NAME		= "Karins";
	
	public static String getFastTagBankName(short paymentType) {

		String paymentTypeName		=	null;
		switch (paymentType) {
		
		  case FASTTAG_BANK_ICICI_ID:
			  paymentTypeName	= FASTTAG_BANK_ICICI_NAME;
		        break;
		  case FASTTAG_BANK_KVB_ID: 
			  paymentTypeName	= FASTTAG_BANK_KVB_NAME;
		        break;
		  case FASTTAG_BANK_HDFC_ID: 
			  paymentTypeName	= FASTTAG_BANK_HDFC_NAME;
		        break;
		  case FASTTAG_BANK_YESBANK_ID: 
			  paymentTypeName	= FASTTAG_BANK_YESBANK_NAME;
		        break;
		  case FASTTAG_BANK_KARINS: 
			  paymentTypeName	= FASTTAG_BANK_KARINS_NAME;
		        break;
		
		  default:
			  paymentTypeName = "--";
		        break;
		}
		return paymentTypeName;
	
	}
}
