package org.fleetopgroup.constant;

public class DealerServiceEntriesConstant {
	public static final short DSE_OPEN_STATUS_ID				= 1;
	public static final short DSE_WORK_PENDING_STATUS_ID		= 2;
	public static final short DSE_PAYMENT_PENDING_STATUS_ID		= 3;
	public static final short DSE_ACCOUNT_CLOSE_STATUS_ID		= 4;
	
	public static final String DSE_OPEN_STATUS_NAME 			 	= "IN PROCESS";
	public static final String DSE_WORK_PENDING_STATUS_NAME	 		= "WORK ON HOLD";
	public static final String DSE_PAYMENT_PENDING_STATUS_NAME	 	= "PAYMENT PENDING ";
	public static final String DSE_ACCOUNT_CLOSE_STATUS_NAME	 	= "ACCOUNT CLOSE";
	
	public static  String  getDealerServiceEntriesStatus(short type) throws Exception {
		String statusString = null;
		switch (type) {
		case DSE_OPEN_STATUS_ID:
			statusString = DSE_OPEN_STATUS_NAME;
			break;
		case DSE_WORK_PENDING_STATUS_ID:
			statusString = DSE_WORK_PENDING_STATUS_NAME;
			break;
		case DSE_PAYMENT_PENDING_STATUS_ID:
			statusString = DSE_PAYMENT_PENDING_STATUS_NAME;
			break;
		case DSE_ACCOUNT_CLOSE_STATUS_ID:
			statusString = DSE_ACCOUNT_CLOSE_STATUS_NAME;
			break;
		
		default:
			statusString = "--";
			break;
		}
		return statusString;
	} 
	
	public static final short PART_APPLICABLE_STATUS_ID			= 0;
	public static final short PART_NOT_APPLICABLE_STATUS_ID		= 1;
	
	public static final String PART_APPLICABLE_STATUS	 		= "PART APPLICABLE";
	public static final String PART_NOT_APPLICABLE_STATUS 	 	= "PART NOT APPLICABLE";
	
	public static  String  getPartStatusOfDse(boolean type) throws Exception {
		String statusString = null;
		if(type) {
			statusString = PART_NOT_APPLICABLE_STATUS;
		}else {
			statusString = PART_APPLICABLE_STATUS;
		}
		return statusString;
	} 
	
	public static final short PERCENTAGE_TYPE_ID				= 1;
	public static final short AMOUNT_TYPE_ID					= 2;
	
	public static final String PERCENTAGE_TYPE_NAME 			= "PERCENTAGE";
	public static final String AMOUNT_TYPE_NAME	 				= "AMOUNT";
	
	public static  String  getDiscoutTaxType(short type) throws Exception {
		String statusString = null;
		switch (type) {
		case PERCENTAGE_TYPE_ID:
			statusString = PERCENTAGE_TYPE_NAME;
			break;
		case AMOUNT_TYPE_ID:
			statusString = AMOUNT_TYPE_NAME;
			break;
		default:
			statusString = "--";
			break;
		}
		return statusString;
	} 
	
	public static final short DSE_REMARK_OPEN_STATUS_ID			= 1;
	public static final short DSE_REMARK_HOLD_STATUS_ID			= 2;
	public static final short DSE_REMARK_COMPLETE_STATUS_ID		= 3;
	public static final short DSE_REMARK_REOPEN_STATUS_ID		= 4;
	
	public static final String DSE_REMARK_OPEN_STATUS_NAME 			= "IN PROCESS";
	public static final String DSE_REMARK_HOLD_STATUS_NAME	 		= "ON HOLD";
	public static final String DSE_REMARK_COMPLETE_STATUS_NAME	 	= "COMPLETED ";
	public static final String DSE_REMARK_REOPEN_STATUS_NAME	 		= "REOPEN";
	
	public static  String  getDealerServiceEntriesRemarkStatus(short type) throws Exception {
		String statusString = null;
		switch (type) {
		case DSE_REMARK_OPEN_STATUS_ID:
			statusString = DSE_REMARK_OPEN_STATUS_NAME;
			break;
		case DSE_REMARK_HOLD_STATUS_ID:
			statusString = DSE_REMARK_HOLD_STATUS_NAME;
			break;
		case DSE_REMARK_COMPLETE_STATUS_ID:
			statusString = DSE_REMARK_COMPLETE_STATUS_NAME;
			break;
		case DSE_REMARK_REOPEN_STATUS_ID:
			statusString = DSE_REMARK_REOPEN_STATUS_NAME;
			break;
		
		default:
			statusString = "--";
			break;
		}
		return statusString;
	}
	
	public static final short labourApplicable = 0;
	
	public static final short labourNotApplicable = 1;

	
	public static final String labourApplicableName = "LABOUR APPLICABLE ";
	
	public static final String labourNotApplicableName = "LABOUR NOT APPLICABLE";
	
	public static String getLabourApplicableStatus(boolean value) {
		String returnValue = null;
		if(value) {
			returnValue=labourNotApplicableName;
		}else {
			returnValue = labourApplicableName;
		}
		return returnValue;
	}
}
