package org.fleetopgroup.constant;

public class InventoryTransferStatus {

	public static final short TRANSFERED			= 1;
	public static final short RECEIVED				= 2;
	public static final short REJECTED				= 3;
	
	
	public static final String TRANSFERED_NAME 		= "TRANSFERED";
	public static final String RECEIVED_NAME 		= "RECEIVED";
	public static final String REJECTED_NAME 		= "REJECTED";
	
	
	
	public static String getInventoryTransferName(short requisition_STATUS_ID) {
		
		String InventoryTransferStatusTypeName		=	null;
		switch (requisition_STATUS_ID) {
		
		  case TRANSFERED:
			  InventoryTransferStatusTypeName	= TRANSFERED_NAME;
		        break;
		  case RECEIVED:
			  InventoryTransferStatusTypeName	= RECEIVED_NAME;
		        break;
		  case REJECTED:
			  InventoryTransferStatusTypeName	= REJECTED_NAME;
			  break;
		 
		  default:
			  InventoryTransferStatusTypeName = "--";
		        break;
		}
		return InventoryTransferStatusTypeName;
	}
	
}
