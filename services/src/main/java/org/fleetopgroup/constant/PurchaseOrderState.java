package org.fleetopgroup.constant;

public class PurchaseOrderState {
	
	public static final short 	REQUISITION_ID	= 1;
	public static final short 	ORDERED_ID		= 2;
	public static final short 	RECEIVED_ID		= 3;
	public static final short 	COMPLETED_ID	= 4;
	
	
	public static final String 	REQUISITION_NAME	= "REQUISITION";
	public static final String 	ORDERED_NAME		= "ORDERED";
	public static final String 	RECEIVED_NAME		= "RECEIVED";
	public static final String 	COMPLETED_NAME		= "COMPLETED";
	
	public static final short 	NOT_APPROVED		= 1;
	public static final short 	APPROVED			= 2;
	public static final short 	REJECT				= 3;
	public static final short 	BRANCH_TRANSFER		= 4;
	public static final short 	VENDOR_CHANGE		= 5;
	
	public static final String 	NOT_APPROVED_NAME		= "NOT APPROVED";
	public static final String 	APPROVED_NAME			= "APPROVED";
	public static final String 	REJECT_NAME				= "REJECT";
	public static final String 	BRANCH_TRANSFER_NAME	= "BRANCH TRANSFER";
	public static final String 	VENDOR_CHANGE_NAME		= "VENDOR CHANGE";
	
	
	
	public static String getPurchaseOrderStateName(short id) {
		String statusString		=	null;
		switch (id) {
		  case REQUISITION_ID:
			  statusString	= REQUISITION_NAME;
		        break;
		  case ORDERED_ID: 
			  statusString	= ORDERED_NAME;
		        break;
		  case RECEIVED_ID: 
			  statusString	= RECEIVED_NAME;
		        break;
		  case COMPLETED_ID: 
			  statusString	= COMPLETED_NAME;
		        break;
		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	}
	public static String getPurchaseOrderApprovalStatus(short id) {
		String approvalStatus		=	null;
		switch (id) {
		case NOT_APPROVED: 
			approvalStatus	= NOT_APPROVED_NAME;
			break;
		case APPROVED:
			approvalStatus	= APPROVED_NAME;
			break;
		case REJECT:
			approvalStatus	= REJECT_NAME;
			break;
		case BRANCH_TRANSFER: 
			approvalStatus	= BRANCH_TRANSFER_NAME;
			break;
		case VENDOR_CHANGE: 
			approvalStatus	= VENDOR_CHANGE_NAME;
			break;
		default:
			approvalStatus = "--";
			break;
		}
		return approvalStatus;
	}
}
