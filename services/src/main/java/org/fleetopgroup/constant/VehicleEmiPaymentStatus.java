package org.fleetopgroup.constant;

import java.util.ArrayList;

public class VehicleEmiPaymentStatus{
	
	public static final short EMI_STATUS_IN_PROCESS			= 1;
	public static final short EMI_STATUS_COMPLETE			= 2;
	public static final short EMI_STATUS_SETTLEMENT			= 3;
	
	public static final String	EMI_STATUS_IN_PROCESS_NAME			= "INPROCESS";
	public static final String	EMI_STATUS_COMPLETE_NAME			= "COMPLETE";
	public static final String	EMI_STATUS_SETTLEMENT_NAME			= "SETTLEMENT";
	
	
	public static String getEmiStatus(short status) throws Exception{
		String statusString		=	null;
		
		switch (status) {
		
		  case EMI_STATUS_IN_PROCESS:
			  statusString	= EMI_STATUS_IN_PROCESS_NAME;
		        break;
		  case EMI_STATUS_COMPLETE: 
			  statusString	= EMI_STATUS_COMPLETE_NAME;
		        break;
		  case EMI_STATUS_SETTLEMENT: 
			  statusString	= EMI_STATUS_SETTLEMENT_NAME;
			  break;

		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	}
	
	
	private short paymentTypeId;
	
	private String paymentTypeName;
	
	
	public VehicleEmiPaymentStatus() {
		super();
	}
	
	
	public VehicleEmiPaymentStatus(short paymentTypeId, String paymentTypeName) {
		super();
		this.paymentTypeId = paymentTypeId;
		this.paymentTypeName = paymentTypeName;
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
	
	public static final short PAYMENT_TYPE_CASH					= 1;
	public static final short PAYMENT_TYPE_NEFT					= 2;
	public static final short PAYMENT_TYPE_RTGS					= 3;
	public static final short PAYMENT_TYPE_IMPS					= 4;
	public static final short PAYMENT_TYPE_DD					= 5;
	public static final short PAYMENT_TYPE_CHEQUE				= 6;
	public static final short PAYMENT_TYPE_BANK_DRAFT			= 7;
	public static final short PAYMENT_TYPE_ON_ACCOUNT			= 8;
	
	public static final String PAYMENT_TYPE_CASH_NAME			= "CASH";
	public static final String PAYMENT_TYPE_NEFT_NAME			= "NEFT";
	public static final String PAYMENT_TYPE_RTGS_NAME			= "RTGS";
	public static final String PAYMENT_TYPE_IMPS_NAME			= "IMPS";
	public static final String PAYMENT_TYPE_DD_NAME				= "DD";
	public static final String PAYMENT_TYPE_CHEQUE_NAME			= "CHEQUE";
	public static final String PAYMENT_TYPE_BANK_DRAFT_NAME		= "BANK DRAFT";
	public static final String PAYMENT_TYPE_ON_ACCOUNT_NAME		= "ON ACCOUNT";
	
	
	public static String getPaymentModeStr(short status) throws Exception{
		String statusString		=	null;
		
		switch (status) {
		  case PAYMENT_TYPE_CASH:
			  statusString	= PAYMENT_TYPE_CASH_NAME;
		        break;
		  case PAYMENT_TYPE_NEFT: 
			  statusString	= PAYMENT_TYPE_NEFT_NAME;
		        break;
		  case PAYMENT_TYPE_RTGS: 
			  statusString	= PAYMENT_TYPE_RTGS_NAME;
			  break;
		  case PAYMENT_TYPE_IMPS: 
			  statusString	= PAYMENT_TYPE_IMPS_NAME;
			  break;
		  case PAYMENT_TYPE_DD: 
			  statusString	= PAYMENT_TYPE_DD_NAME;
			  break;
		  case PAYMENT_TYPE_CHEQUE: 
			  statusString	= PAYMENT_TYPE_CHEQUE_NAME;
			  break;
		  case PAYMENT_TYPE_BANK_DRAFT: 
			  statusString	= PAYMENT_TYPE_BANK_DRAFT_NAME;
			  break;
		  case PAYMENT_TYPE_ON_ACCOUNT: 
			  statusString	= PAYMENT_TYPE_ON_ACCOUNT_NAME;
			  break;

		  default:
			  statusString = "--";
		        break;
		}
		
		return statusString;
	}
	
	
	public static ArrayList<VehicleEmiPaymentStatus> getPaymentTypeList(){
		ArrayList<VehicleEmiPaymentStatus>		list	= new ArrayList<>();
		list.add(new VehicleEmiPaymentStatus(PAYMENT_TYPE_CASH, PAYMENT_TYPE_CASH_NAME));
		list.add(new VehicleEmiPaymentStatus(PAYMENT_TYPE_NEFT, PAYMENT_TYPE_NEFT_NAME));
		list.add(new VehicleEmiPaymentStatus(PAYMENT_TYPE_RTGS, PAYMENT_TYPE_RTGS_NAME));
		list.add(new VehicleEmiPaymentStatus(PAYMENT_TYPE_IMPS, PAYMENT_TYPE_IMPS_NAME));
		list.add(new VehicleEmiPaymentStatus(PAYMENT_TYPE_DD, PAYMENT_TYPE_DD_NAME));
		list.add(new VehicleEmiPaymentStatus(PAYMENT_TYPE_CHEQUE, PAYMENT_TYPE_CHEQUE_NAME));
		list.add(new VehicleEmiPaymentStatus(PAYMENT_TYPE_BANK_DRAFT, PAYMENT_TYPE_BANK_DRAFT_NAME));
		list.add(new VehicleEmiPaymentStatus(PAYMENT_TYPE_ON_ACCOUNT, PAYMENT_TYPE_ON_ACCOUNT_NAME));
		
		return list;
	}
	
}