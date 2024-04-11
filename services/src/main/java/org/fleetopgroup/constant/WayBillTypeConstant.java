package org.fleetopgroup.constant;

public class WayBillTypeConstant {
	
	public static final short	WAYBILL_TYPE_PAID	= 1;
	public static final short	WAYBILL_TYPE_TOPAY	= 2;
	public static final short	WAYBILL_TYPE_FOC	= 3;
	public static final short	WAYBILL_TYPE_TBB	= 4;
	
	public static final String	WAYBILL_TYPE_PAID_NAME	= "Paid";
	public static final String	WAYBILL_TYPE_TOPAY_NAME	= "ToPay";
	public static final String	WAYBILL_TYPE_FOC_NAME	= "FOC";
	public static final String	WAYBILL_TYPE_TBB_NAME	= "TBB";
	
	public static final short  WITH_BILL     = 1;
	public static final short  WITHOUT_BILL  = 2;
	
	public static final String	WITH_BILL_NAME    = "B_Income";
	public static final String	WITHOUT_BILL_NAME = "E_Income";
	
	
	
	public static String getVehicleAcTypeName(short wayBillType) {

		String paymentTypeName		=	null;
		switch (wayBillType) {
		
		  case WAYBILL_TYPE_PAID:
			  paymentTypeName	= WAYBILL_TYPE_PAID_NAME;
		        break;
		  case WAYBILL_TYPE_TOPAY: 
			  paymentTypeName	= WAYBILL_TYPE_TOPAY_NAME;
		        break;
		  case WAYBILL_TYPE_FOC: 
			  paymentTypeName	= WAYBILL_TYPE_FOC_NAME;
		        break;
		  case WAYBILL_TYPE_TBB: 
			  paymentTypeName	= WAYBILL_TYPE_TBB_NAME;
		        break;
		      
		
		  default:
			  paymentTypeName = "--";
		        break;
		}
		return paymentTypeName;
	
	}
	
	public static String getIncomeTypeName(short withBillTypeId) {
		String IncomeName		=	null;
		switch (withBillTypeId) {
		  case WITH_BILL:
			  IncomeName	= WITH_BILL_NAME;
		        break;
		  case WITHOUT_BILL: 
			  IncomeName	= WITHOUT_BILL_NAME;
		        break;
		  default:
			  IncomeName = "--";
		        break;
		}
		return IncomeName;
	}
}
