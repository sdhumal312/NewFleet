package org.fleetopgroup.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PaymentTypeConstant {
	
	private short paymentTypeId;
	
	private String paymentTypeName;
	
	
	public PaymentTypeConstant() {
		super();
	}
	
	
	public PaymentTypeConstant(short paymentTypeId, String paymentTypeName) {
		super();
		this.paymentTypeId = paymentTypeId;
		this.paymentTypeName = paymentTypeName;
	}

	
	public static ArrayList<PaymentTypeConstant> getPaymentTypeList(){
		ArrayList<PaymentTypeConstant>		list	= new ArrayList<>();
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_CASH, PAYMENT_TYPE_CASH_NAME));
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_CREDIT, PAYMENT_TYPE_CREDIT_NAME));
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_NEFT, PAYMENT_TYPE_NEFT_NAME));
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_RTGS, PAYMENT_TYPE_RTGS_NAME));
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_IMPS, PAYMENT_TYPE_IMPS_NAME));
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_DD, PAYMENT_TYPE_DD_NAME));
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_CHEQUE, PAYMENT_TYPE_CHEQUE_NAME));
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_BANK_DRAFT, PAYMENT_TYPE_BANK_DRAFT_NAME));
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_COD, PAYMENT_TYPE_COD_NAME));
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_ON_ACCOUNT, PAYMENT_TYPE_ON_ACCOUNT_NAME));
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_CARD,PAYMENT_TYPE_CARD_NAME));
		return list;
	}
	
	public static List<Short> getPaymentTypeIdList(){
		return Arrays.asList(PAYMENT_TYPE_NEFT,PAYMENT_TYPE_RTGS,PAYMENT_TYPE_IMPS,PAYMENT_TYPE_DD,PAYMENT_TYPE_CHEQUE,PAYMENT_TYPE_BANK_DRAFT, PAYMENT_TYPE_UPI,PAYMENT_TYPE_CARD);
	}
	
	public static ArrayList<PaymentTypeConstant> getPaymentTypeList2(){
		ArrayList<PaymentTypeConstant>		list	= new ArrayList<>();
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_CASH, PAYMENT_TYPE_CASH_NAME));
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_UPI, PAYMENT_TYPE_UPI_NAME) );
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_NEFT, PAYMENT_TYPE_NEFT_NAME));
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_RTGS, PAYMENT_TYPE_RTGS_NAME));
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_IMPS, PAYMENT_TYPE_IMPS_NAME));
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_DD, PAYMENT_TYPE_DD_NAME));
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_CHEQUE, PAYMENT_TYPE_CHEQUE_NAME));
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_BANK_DRAFT, PAYMENT_TYPE_BANK_DRAFT_NAME));
		list.add(new PaymentTypeConstant(PAYMENT_TYPE_CARD, PAYMENT_TYPE_CARD_NAME));
		return list;
	}



	public short getPaymentTypeId() {
		return paymentTypeId;
	}



	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}



	public String getPaymentTypeName() {
		return paymentTypeName;
	}



	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}



	public static final short PAYMENT_TYPE_PO					= 0;
	public static final short PAYMENT_TYPE_CASH					= 1;
	public static final short PAYMENT_TYPE_CREDIT				= 2;
	public static final short PAYMENT_TYPE_NEFT					= 3;
	public static final short PAYMENT_TYPE_RTGS					= 4;
	public static final short PAYMENT_TYPE_IMPS					= 5;
	public static final short PAYMENT_TYPE_DD					= 6;
	public static final short PAYMENT_TYPE_CHEQUE				= 7;
	public static final short PAYMENT_TYPE_BANK_DRAFT			= 8;
	public static final short PAYMENT_TYPE_COD					= 9;
	public static final short PAYMENT_TYPE_ON_ACCOUNT			= 10;
	public static final short PAYMENT_TYPE_UPI					= 11;
	public static final short PAYMENT_TYPE_CARD					= 12;
	
	public static final String PAYMENT_TYPE_PO_NAME				= "PO";
	public static final String PAYMENT_TYPE_CASH_NAME			= "CASH";
	public static final String PAYMENT_TYPE_CREDIT_NAME			= "CREDIT";
	public static final String PAYMENT_TYPE_NEFT_NAME			= "NEFT";
	public static final String PAYMENT_TYPE_RTGS_NAME			= "RTGS";
	public static final String PAYMENT_TYPE_IMPS_NAME			= "IMPS";
	public static final String PAYMENT_TYPE_DD_NAME				= "DD";
	public static final String PAYMENT_TYPE_CHEQUE_NAME			= "CHEQUE";
	public static final String PAYMENT_TYPE_BANK_DRAFT_NAME		= "BANK DRAFT";
	public static final String PAYMENT_TYPE_COD_NAME			= "COD";
	public static final String PAYMENT_TYPE_ON_ACCOUNT_NAME		= "ON ACCOUNT";
	public static final String PAYMENT_TYPE_UPI_NAME			= "UPI";
	public static final String PAYMENT_TYPE_CARD_NAME			= "CARD";
	
	public static String getPaymentTypeName(short paymentType) {

		String paymentTypeName		=	null;
		switch (paymentType) {
		case PAYMENT_TYPE_PO:
			paymentTypeName	= PAYMENT_TYPE_PO_NAME;
			break;
		  case PAYMENT_TYPE_CASH:
			  paymentTypeName	= PAYMENT_TYPE_CASH_NAME;
		        break;
		  case PAYMENT_TYPE_CREDIT: 
			  paymentTypeName	= PAYMENT_TYPE_CREDIT_NAME;
		        break;
		  case PAYMENT_TYPE_NEFT: 
			  paymentTypeName	= PAYMENT_TYPE_NEFT_NAME;
		        break;
		  case PAYMENT_TYPE_RTGS: 
			  paymentTypeName	= PAYMENT_TYPE_RTGS_NAME;
		        break;
		  case PAYMENT_TYPE_IMPS: 
			  paymentTypeName	= PAYMENT_TYPE_IMPS_NAME;
		        break;
		  case PAYMENT_TYPE_DD: 
			  paymentTypeName	= PAYMENT_TYPE_DD_NAME;
		        break;
		  case PAYMENT_TYPE_CHEQUE: 
			  paymentTypeName	= PAYMENT_TYPE_CHEQUE_NAME;
		        break;
		  case PAYMENT_TYPE_BANK_DRAFT: 
			  paymentTypeName	= PAYMENT_TYPE_BANK_DRAFT_NAME;
		        break;
		  case PAYMENT_TYPE_COD: 
			  paymentTypeName	= PAYMENT_TYPE_COD_NAME;
		        break;
		  case PAYMENT_TYPE_ON_ACCOUNT: 
			  paymentTypeName	= PAYMENT_TYPE_ON_ACCOUNT_NAME;
		        break; 
		  case PAYMENT_TYPE_UPI:
			  paymentTypeName = PAYMENT_TYPE_UPI_NAME; 
			  break;
		  case PAYMENT_TYPE_CARD:
			  paymentTypeName = PAYMENT_TYPE_CARD_NAME;
			  break;
		  default:
			  paymentTypeName = "--";
		        break;
		}
		return paymentTypeName;
	}
}
