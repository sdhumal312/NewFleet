package org.fleetopgroup.constant;

public class IVPaymentTypeMapper {
	
	public static final short PAYMENT_TYPE_CASH_ID									= 1;
	public static final short PAYMENT_TYPE_CHEQUE_ID								= 2;
	public static final short PAYMENT_TYPE_CREDIT_ID								= 3;
	public static final short PAYMENT_TYPE_BILL_CREDIT_ID							= 4;
	public static final short PAYMENT_TYPE_RECEIVE_AT_GODOWN_ID						= 5;
	public static final short PAYMENT_TYPE_DUE_UNDELIVERED_ID						= 6;
	public static final short PAYMENT_TYPE_CROSSING_CREDIT_ID						= 7;
	public static final short PAYMENT_TYPE_ONLINE_RTGS_ID							= 8;
	public static final short PAYMENT_TYPE_ONLINE_NEFT_ID							= 9;
	public static final short PAYMENT_TYPE_CREDIT_CARD_ID							= 10;
	public static final short PAYMENT_TYPE_DEBIT_CARD_ID							= 11;
	public static final short PAYMENT_TYPE_LHPV_ADVANCE_CREDIT_ID					= 12;
	public static final short PAYMENT_TYPE_EXPENSE_CREDIT_ID						= 13;
	public static final short PAYMENT_TYPE_PART_PAYMENT_ID							= 14;
	public static final short PAYMENT_TYPE_PAYTM_ID									= 15;
	public static final short PAYMENT_TYPE_IMPS_ID									= 16;
	public static final short PAYMENT_TYPE_UPI_ID									= 17;
	public static final short PAYMENT_TYPE_PHONE_PAY_ID								= 18;
	public static final short PAYMENT_TYPE_GOOGLE_PAY_ID							= 19;
	public static final short PAYMENT_TYPE_WHATSAPP_PAY_ID							= 20;
	public static final short PAYMENT_TYPE_AMAZON_PAY_ID							= 21;
	
	
	public static short getIVPaymentTypeId(short paymentTypeId) {
		short ivPaymentTypeId = 0;
		switch(paymentTypeId) {
		case PaymentTypeConstant.PAYMENT_TYPE_CASH : ivPaymentTypeId =PAYMENT_TYPE_CASH_ID;
		break;
		case PaymentTypeConstant.PAYMENT_TYPE_CREDIT : ivPaymentTypeId= PAYMENT_TYPE_BILL_CREDIT_ID;
		break;
		case PaymentTypeConstant.PAYMENT_TYPE_NEFT : ivPaymentTypeId=PAYMENT_TYPE_ONLINE_NEFT_ID;
		break;
		case  PaymentTypeConstant.PAYMENT_TYPE_RTGS : ivPaymentTypeId=PAYMENT_TYPE_ONLINE_RTGS_ID;
		break;
		case PaymentTypeConstant.PAYMENT_TYPE_IMPS : ivPaymentTypeId=PAYMENT_TYPE_IMPS_ID;
		break;
		case PaymentTypeConstant.PAYMENT_TYPE_DD : ivPaymentTypeId= PAYMENT_TYPE_CHEQUE_ID;
		break;
		case PaymentTypeConstant.PAYMENT_TYPE_CHEQUE : ivPaymentTypeId= PAYMENT_TYPE_CHEQUE_ID;
		break;
		case PaymentTypeConstant.PAYMENT_TYPE_UPI : ivPaymentTypeId =PAYMENT_TYPE_UPI_ID;
		break;
		}
		return ivPaymentTypeId;
	}

}
