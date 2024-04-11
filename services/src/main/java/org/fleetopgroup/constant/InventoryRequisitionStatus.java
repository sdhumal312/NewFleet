package org.fleetopgroup.constant;

public class InventoryRequisitionStatus {

	public static final short OPEN				= 1;
	public static final short REQUISTION		= 2;
	public static final short TRANSFERED		= 3;
	public static final short COMPLETED			= 4;
	public static final short REJECTED			= 5;
	
	
	public static final String OPEN_NAME 			= "OPEN";
	public static final String REQUISTION_NAME 		= "REQUISTION";
	public static final String TRANSFERED_NAME 		= "TRANSFERED";
	public static final String COMPLETED_NAME 		= "COMPLETED";
	public static final String REJECTED_NAME 		= "REJECTED";
	
	
	
	public static String getInventoryRequisitionName(short requisition_STATUS_ID) {
		
		String InventoryRequisitionStatusTypeName		=	null;
		switch (requisition_STATUS_ID) {
		  case OPEN:
			  InventoryRequisitionStatusTypeName	= OPEN_NAME;
		        break;
		  case REQUISTION:
			  InventoryRequisitionStatusTypeName	= REQUISTION_NAME;
		        break;
		  case TRANSFERED:
			  InventoryRequisitionStatusTypeName	= TRANSFERED_NAME;
		        break;
		  case COMPLETED:
			  InventoryRequisitionStatusTypeName	= COMPLETED_NAME;
		        break;
		  case REJECTED:
			  InventoryRequisitionStatusTypeName	= REJECTED_NAME;
			  break;
		 
		  default:
			  InventoryRequisitionStatusTypeName = "--";
		        break;
		}
		return InventoryRequisitionStatusTypeName;
	}
	
}
