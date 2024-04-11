package org.fleetopgroup.constant;

import java.util.ArrayList;

public class LorryHirePaymentStatus {
	
	private short	paymentStatusId;
	
	private String	paymentStatusName;
	
	
	public LorryHirePaymentStatus() {
		super();
	}
	
	
	

	public LorryHirePaymentStatus(short paymentStatusId, String paymentStatusName) {
		super();
		this.paymentStatusId = paymentStatusId;
		this.paymentStatusName = paymentStatusName;
	}




	public short getPaymentStatusId() {
		return paymentStatusId;
	}

	public void setPaymentStatusId(short paymentStatusId) {
		this.paymentStatusId = paymentStatusId;
	}

	public String getPaymentStatusName() {
		return paymentStatusName;
	}

	public void setPaymentStatusName(String paymentStatusName) {
		this.paymentStatusName = paymentStatusName;
	}

	public static final short PAYMENT_MODE_NOT_PAID			= 1;
	public static final short PAYMENT_MODE_PAID				= 2;
	public static final short PAYMENT_MODE_PARTIALLY_PAID	= 3;
	public static final short PAYMENT_MODE_NEGOTIATED		= 4;
	
	public static final String	PAYMENT_MODE_NOT_PAID_NAME			= "NOTPAID";
	public static final String	PAYMENT_MODE_PAID_NAME				= "PAID";
	public static final String	PAYMENT_MODE_PARTIALLY_PAID_NAME	= "PARTIAL";
	public static final String	PAYMENT_MODE_NEGOTIATED_NAME		= "NEGOTIATED";
	
	
	
	public static String getPaymentMode(short status) throws Exception{
		String statusString		=	null;
		switch (status) {
		  case PAYMENT_MODE_NOT_PAID:
			  statusString	= PAYMENT_MODE_NOT_PAID_NAME;
		        break;
		  case PAYMENT_MODE_PAID: 
			  statusString	= PAYMENT_MODE_PAID_NAME;
		        break;
		  case PAYMENT_MODE_PARTIALLY_PAID: 
			  statusString	= PAYMENT_MODE_PARTIALLY_PAID_NAME;
		        break;
		  case PAYMENT_MODE_NEGOTIATED: 
			  statusString	= PAYMENT_MODE_NEGOTIATED_NAME;
			  break;
		

		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	}
	
	public static ArrayList<LorryHirePaymentStatus> getLorryHirePaymentStatus(){
		ArrayList<LorryHirePaymentStatus>		list	= new ArrayList<>();
		list.add(new LorryHirePaymentStatus(PAYMENT_MODE_NOT_PAID, PAYMENT_MODE_NOT_PAID_NAME));
		list.add(new LorryHirePaymentStatus(PAYMENT_MODE_PAID, PAYMENT_MODE_PAID_NAME));
		list.add(new LorryHirePaymentStatus(PAYMENT_MODE_PARTIALLY_PAID, PAYMENT_MODE_PARTIALLY_PAID_NAME));
		list.add(new LorryHirePaymentStatus(PAYMENT_MODE_NEGOTIATED, PAYMENT_MODE_NEGOTIATED_NAME));
		
		return list;
	}
}
