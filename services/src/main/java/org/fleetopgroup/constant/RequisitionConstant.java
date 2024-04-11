package org.fleetopgroup.constant;

public class RequisitionConstant {
	
	public static final short PART_RERUISITION 		= 1;
	public static final short TYRE_RERUISITION 		= 2;
	public static final short BATTARY_RERUISITION 	= 3;
	public static final short CLOTH_RERUISITION 	= 4;
	public static final short UREA_RERUISITION 		= 5;
	
	public static final String PART_RERUISITION_NAME 		= "Part";
	public static final String TYRE_RERUISITION_NAME  		= "Tyre";
	public static final String BATTARY_RERUISITION_NAME  	= "Battary";
	public static final String CLOTH_RERUISITION_NAME  		= "Upholstery";
	public static final String UREA_RERUISITION_NAME  		= "Urea";
	
	//Requisition Status
	public static final short REQUISITION_CREATED 				= 1; 
	public static final short REQUISITION_SEND 					= 2; 
	public static final short REQUISITION_APPROVED 				= 3; 
	public static final short REQUISITION_COMPLETE 				= 4; 
	public static final short REQUISITION_MARKED_COMPLETE 		= 5; 
	
	public static final String REQUISITION_CREATED_NAME 		= "Requisition Created";
	public static final String REQUISITION_SEND_NAME 			= "Requisition Send";
	public static final String REQUISITION_APPROVED_NAME 		= "Requisition Approved";
	public static final	String REQUISITION_MARKED_COMPLETE_NAME	= "Requisition Complete";
	
	
	//Sub Requisition Status
	public static final short SUBREQUISITION_REJECTED 		= 1;
	public static final short SUBREQUISITION_APPROVED 		= 2; 
	
	public static final String SUBREQUISITION_APPROVED_NAME 		= "APPROVED"; 
	public static final String SUBREQUISITION_REJECTED_NAME 		= "REJECTED";
	
	
	public static final short TRANSFER_BRANCH 			= 1; 
	public static final short TRANSFER_PO 				= 2;
	
	public static final String TRANSFER_BRANCH_NAME 			= "BRANCH"; 
	public static final String TRANSFER_PO_NAME 				= "PO";
	
	public static final short APPROVAL_STATUS_CREATED 						= 0; 
	public static final short APPROVAL_STATUS_TRANSFER_REJECTED 			= 1;//
	public static final short APPROVAL_STATUS_TRANSFER_COMPLETE 			= 2;
	public static final short APPROVAL_STATUS_TRANSFER_RECEIVED 			= 3;//
	public static final short APPROVAL_STATUS_TRANSFER_RECEIVAL_REJECTED 	= 4;//
	public static final short APPROVAL_STATUS_PO_CREATED 					= 5;//
	public static final short APPROVAL_STATUS_PARTIALLY_RECEIVED 			= 6;
	public static final short APPROVAL_STATUS_PARTIALLY_RECEIVED_COMPLETE 	= 7;//
	public static final short APPROVAL_STATUS_PARTIALLY_RECEIVED_CLOSED 	= 8;
	public static final short APPROVAL_STATUS_PARTIALLY_TRANSFERED		 	= 11;
	
	
	
	public static final short LIST_REQUISITION_ALL 							= 0; 
	public static final short LIST_REQUISITION_CREATED	 					= 1; 
	public static final short LIST_REQUISITION_APPROVED						= 3; 
	public static final short LIST_REQUISITION_YOURTRANSFER					= 4; 
	public static final short LIST_REQUISITION_YOURRECEIVAL					= 5; 
	public static final short LIST_REQUISITION_REQUISITION_ASSIGN_TO_YOU	= 6; 
	public static final short LIST_REQUISITION_COMPLETE						= 7; 
	
	
	public static String getRequasitionName(short status) {
		String returnStatus="";
		if(status == PART_RERUISITION) {
			returnStatus=PART_RERUISITION_NAME;
		}else if (status == TYRE_RERUISITION) {
			returnStatus=TYRE_RERUISITION_NAME;
		}else if (status == BATTARY_RERUISITION) {
			returnStatus=BATTARY_RERUISITION_NAME;
		}else if (status == CLOTH_RERUISITION) {
			returnStatus=CLOTH_RERUISITION_NAME;
		}else if (status == UREA_RERUISITION) {
			returnStatus=UREA_RERUISITION_NAME;
		}
		return returnStatus;
	}
	public static String getRequasitionStatus(short status) {
		String returnStatus="";
		if(status == REQUISITION_CREATED) {
			returnStatus=REQUISITION_CREATED_NAME;
		}else if (status == REQUISITION_SEND) {
			returnStatus=REQUISITION_SEND_NAME;
		}else if (status == REQUISITION_APPROVED) {
			returnStatus=REQUISITION_APPROVED_NAME;
		}else if(status == REQUISITION_COMPLETE || status == REQUISITION_MARKED_COMPLETE ) {
			returnStatus=REQUISITION_MARKED_COMPLETE_NAME;
		}
		return returnStatus;
	}
	
	public static String getTransferType(short status) {
		String returnStatus="";
		if(status == TRANSFER_BRANCH) {
			returnStatus=TRANSFER_BRANCH_NAME;
		}else if (status == TRANSFER_PO) {
			returnStatus=TRANSFER_PO_NAME;
		}
		return returnStatus;
	}
	
	public static String getSubRequasitionStatus(short status) {
		String returnStatus="";
		if(status == SUBREQUISITION_APPROVED) {
			returnStatus=SUBREQUISITION_APPROVED_NAME;
		}else if (status == SUBREQUISITION_REJECTED) {
			returnStatus=SUBREQUISITION_REJECTED_NAME;
		}
		return returnStatus;
	}
	
	

	

}
