package org.fleetopgroup.constant;

public class UreaRequisitionAndTransferStatus {
	
	
	public UreaRequisitionAndTransferStatus() {
		super();
		
	}



	public static final short SENT_REQUISTION			= 1;
	public static final short ACCEPTED_REQUISTION		= 2;
	public static final short REJECTED_REQUISTION		= 3;
	public static final short RECEIVED					= 4;
	public static final short REJECTED					= 5;
	public static final short SAVE_TRANSFERED			= 6;
	public static final short COMPLETE_TRANSFERED		= 7;
	
	public static final short YOUR_SENT_REQUISTION			= 1;
	public static final short YOUR_RECEIVED					= 2;
	public static final short YOUR_REJECTED					= 3;
	public static final short YOUR_TRANSFERED				= 4;
	
	
	public static final String SENT_REQUISTION_NAME 				= "SENT_REQUISITION";
	public static final String ACCEPTED_REQUISTION_NAME 			= "ACCEPTED_REQUISTION";
	public static final String REJECTED_REQUISTION_NAME 			= "REJECTED_REQUISTION";
	public static final String RECEIVED_NAME 						= "RECEIVED";
	public static final String REJECTED_NAME 						= "REJECTED";
	public static final String SAVE_TRANSFERED_NAME 				= "SAVE_TRANSFERED";
	public static final String COMPLETE_TRANSFERED_NAME 			= "COMPLETE_TRANSFERED";
	
	public static final String YOUR_SENT_REQUISTION_NAME 			= "YOUR_SENT_REQUISITION";
	public static final String YOUR_RECEIVED_NAME 					= "YOUR_RECEIVED";
	public static final String YOUR_REJECTED_NAME 					= "YOUR_REJECTED";
	public static final String YOUR_TRANSFERED_NAME 				= "YOUR_TRANSFERED";
	
	
	
	public static String getUreaRequisitionAndTransferStatusName(short requisitionAndTransferStatusId) {
		
		String ureaRequisitionAndTransferStatusName		=	null;
		switch (requisitionAndTransferStatusId) {
		  case SENT_REQUISTION:
			  ureaRequisitionAndTransferStatusName	= SENT_REQUISTION_NAME;
		        break;
		  case ACCEPTED_REQUISTION:
			  ureaRequisitionAndTransferStatusName	= ACCEPTED_REQUISTION_NAME;
			  break;
		  case REJECTED_REQUISTION:
			  ureaRequisitionAndTransferStatusName	= REJECTED_REQUISTION_NAME;
			  break;
		  case RECEIVED:
			  ureaRequisitionAndTransferStatusName	= RECEIVED_NAME;
			  break;
		  case REJECTED:
			  ureaRequisitionAndTransferStatusName	= REJECTED_NAME;
			  break;
		  case SAVE_TRANSFERED:
			  ureaRequisitionAndTransferStatusName	= SAVE_TRANSFERED_NAME;
			  break;
		  case COMPLETE_TRANSFERED:
			  ureaRequisitionAndTransferStatusName	= COMPLETE_TRANSFERED_NAME;
			  break;
		 
		  default:
			  ureaRequisitionAndTransferStatusName = "--";
		        break;
		}
		return ureaRequisitionAndTransferStatusName;
	}
	
public static String getYourUreaSendAndReceivedRequisitionStatusName(short yourUreaSendAndReceivedRequisitionStatusId) {
		
		String yourRequisitionAndTransferStatusName		=	null;
		switch (yourUreaSendAndReceivedRequisitionStatusId) {
		  case YOUR_SENT_REQUISTION:
			  yourRequisitionAndTransferStatusName	= YOUR_SENT_REQUISTION_NAME;
		        break;
		  case YOUR_RECEIVED:
			  yourRequisitionAndTransferStatusName	= YOUR_RECEIVED_NAME;
		        break;
		  case YOUR_REJECTED:
			  yourRequisitionAndTransferStatusName	= YOUR_REJECTED_NAME;
			  break;
		  case YOUR_TRANSFERED:
			  yourRequisitionAndTransferStatusName	= YOUR_TRANSFERED_NAME;
			  break;
		  default:
			  yourRequisitionAndTransferStatusName = "--";
		        break;
		}
		return yourRequisitionAndTransferStatusName;
	}
	
}
