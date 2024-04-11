package org.fleetopgroup.constant;

public class TyreAssignmentConstant {

	
	public static final short ASSIGN_FROM_NEW 								= 1;
	public static final short ASSIGN_FROM_WORKSHOP	 						= 2;
	public static final short ASSIGN_FROM_OWN_SPARE	 						= 3;
	public static final short ASSIGN_FROM_OTHER_VEHICLE_SPARE	 			= 4;
	
	public static final String ASSIGN_FROM_NEW_NAME  						= "NEW TYRE";
	public static final String ASSIGN_FROM_WORKSHOP_NAME 	 				= "WORKSHOP";
	public static final String ASSIGN_FROM_OWN_SPARE_NAME 	 				= "OWN SPARE";
	public static final String ASSIGN_FROM_OTHER_VEHICLE_SPARE_NAME 	 	= "OTHER VEHICLE SPARE";
	
	
	public static final short ALINGMENT_AUTO	 						= 1;
	public static final short ALINGMENT_MANUAL	 						= 2;
	public static final short ALINGMENT_NOT_DONE	 					= 3;
	
	public static final String ALINGMENT_AUTO_NAME 						= "AUTOMATIC";
	public static final String ALINGMENT_MANUAL_NAME 					= "MANUAL";
	public static final String ALINGMENT_NOT_DONE_NAME 					= "NOT DONE";
	
	public static final short OLD_TYRE_MOVED_TO_REMOULD	 				= 1;
	public static final short OLD_TYRE_MOVED_TO_REPAIR	 				= 2;
	public static final short OLD_TYRE_MOVED_TO_BLAST	 				= 3;
	public static final short OLD_TYRE_MOVED_TO_SCRAP	 				= 4;
	public static final short OLD_TYRE_MOVED_TO_WORKSHOP	 			= 5;
	public static final short OLD_TYRE_MOVED_TO_SPARE	 				= 6;
	public static final short REPAIR_IN_PROCESS	 						= 7;
	public static final short REPAIR_COMPLETED	 						= 8;
	public static final short REPAIR_REJECT	 							= 9;
	
	public static final String OLD_TYRE_MOVED_TO_REMOULD_NAME 			= "MOVED TO REMOULD";
	public static final String OLD_TYRE_MOVED_TO_REPAIR_NAME 			= "MOVED TO REPAIR";
	public static final String OLD_TYRE_MOVED_TO_BLAST_NAME 			= "MOVED TO BLAST";
	public static final String OLD_TYRE_MOVED_TO_SCRAP_NAME 			= "MOVED TO SCRAP";
	public static final String OLD_TYRE_MOVED_TO_WORKSHOP_NAME 			= "MOVED TO WORSHOP";
	public static final String OLD_TYRE_MOVED_TO_SPARE_NAME 			= "MOVED TO SPARE";
	public static final String REPAIR_IN_PROCESS_NAME 					= "REPAIR_IN_PROCESS";
	public static final String REPAIR_COMPLETED_NAME 					= "REPAIR_COMPLETED";
	public static final String REPAIR_REJECT_NAME 						= "REPAIR_REJECT";
	
	public static final short KINPIN_WORKING	 						= 1;
	public static final short KINPIN_NOT_WORKING	 					= 2;
	
	public static final String KINPIN_WORKING_NAME 						= "KINPIN WORKING";
	public static final String KINPIN_NOT_WORKING_NAME 					= "KINPIN NOT WORKING";
	
	
	public static final short TRANSACTION_TYPE_ISSUE 					= 1;
	public static final short TRANSACTION_TYPE_WO	 					= 2;
	public static final short TRANSACTION_TYPE_DSE	 					= 3;
	
	public static final String TRANSACTION_TYPE_ISSUE_NAME 				= "TRANSACTION TYPE ISSUE";
	public static final String TRANSACTION_TYPE_WO_NAME 				= "TRANSACTION TYPE WO";
	public static final String TRANSACTION_TYPE_DSE_NAME 				= "TRANSACTION TYPE DSE";

	public static final short TRANSACTION_USAGE_TYPE_MOUNT 					= 1;
	public static final short TRANSACTION_USAGE_TYPE_DISMOUNT	 			= 2;
	public static final short TRANSACTION_USAGE_TYPE_ROTATE	 				= 3;
	public static final short TRANSACTION_USAGE_TYPE_VEHICLE	 			= 4;
	public static final short TRANSACTION_USAGE_TYPE_WO	 					= 5;
	public static final short TRANSACTION_USAGE_TYPE_DSE	 				= 6;
	public static final short TRANSACTION_USAGE_TYPE_ISSUE	 				= 7;
	public static final short TRANSACTION_USAGE_TYPE_TRIP	 				= 8;
	public static final short TRANSACTION_USAGE_TYPE_FUEL	 				= 9;
	public static final short TRANSACTION_USAGE_TYPE_UREA	 				= 10;
	
	public static final String TRANSACTION_USAGE_TYPE_MOUNT_NAME 				= "TRANSACTION USAGE TYPE MOUNT";
	public static final String TRANSACTION_USAGE_TYPE_DISMOUNT_NAME	 			= "TRANSACTION USAGE TYPE DISMOUNT";
	public static final String TRANSACTION_USAGE_TYPE_ROTATE_NAME	 			= "TRANSACTION USAGE TYPE ROTATE";
	public static final String TRANSACTION_USAGE_TYPE_VEHICLE_NAME	 			= "TRANSACTION USAGE TYPE VEHICLE";
	public static final String TRANSACTION_USAGE_TYPE_WO_NAME	 				= "TRANSACTION USAGE TYPE WO";
	public static final String TRANSACTION_USAGE_TYPE_DSE_NAME	 				= "TRANSACTION USAGE TYPE DSE";
	public static final String TRANSACTION_USAGE_TYPE_ISSUE_NAME	 			= "TRANSACTION USAGE TYPE ISSUE";
	public static final String TRANSACTION_USAGE_TYPE_TRIP_NAME	 				= "TRANSACTION USAGE TYPE TRIP";
	public static final String TRANSACTION_USAGE_TYPE_FUEL_NAME	 				= "TRANSACTION USAGE TYPE FUEL";
	public static final String TRANSACTION_USAGE_TYPE_UREA_NAME	 				= "TRANSACTION USAGE TYPE UREA";

	public static String getTyreAssignFrom (short assignFromId) throws Exception {
		String assignFrom = null;
		switch(assignFromId) {
		case ASSIGN_FROM_NEW :
			assignFrom 	= 	 ASSIGN_FROM_NEW_NAME;
			break;
		case ASSIGN_FROM_WORKSHOP :
			assignFrom 	=	 ASSIGN_FROM_WORKSHOP_NAME;
			break;
		case ASSIGN_FROM_OWN_SPARE :
			assignFrom 	=	 ASSIGN_FROM_OWN_SPARE_NAME;
			break;
		case ASSIGN_FROM_OTHER_VEHICLE_SPARE :
			assignFrom 	=	 ASSIGN_FROM_OTHER_VEHICLE_SPARE_NAME;
			break;
		default :
			assignFrom = "--";
			break;
		}
		return assignFrom;
	}
	
	public static String getAlignment (short alignmentId) throws Exception {
		String alignment = null;
		switch(alignmentId) {
		case ALINGMENT_AUTO :
			alignment 	= 	 ALINGMENT_AUTO_NAME;
			break;
		case ALINGMENT_MANUAL :
			alignment 	=	 ALINGMENT_MANUAL_NAME;
			break;
		case ALINGMENT_NOT_DONE :
			alignment 	=	 ALINGMENT_NOT_DONE_NAME;
			break;
		default :
			alignment = "--";
			break;
		}
		return alignment;
	}
	
	public static String getOldTyreMovedTo (short oldTyreMovedToId) throws Exception {
		String oldTyreMovedTo = null;
		switch(oldTyreMovedToId) {
		case OLD_TYRE_MOVED_TO_REMOULD :
			oldTyreMovedTo 	= 	 OLD_TYRE_MOVED_TO_REMOULD_NAME;
			break;
		case OLD_TYRE_MOVED_TO_REPAIR :
			oldTyreMovedTo 	=	 OLD_TYRE_MOVED_TO_REPAIR_NAME;
			break;
		case OLD_TYRE_MOVED_TO_BLAST :
			oldTyreMovedTo 	=	 OLD_TYRE_MOVED_TO_BLAST_NAME;
			break;
		case OLD_TYRE_MOVED_TO_SCRAP :
			oldTyreMovedTo 	=	 OLD_TYRE_MOVED_TO_SCRAP_NAME;
			break;
		case OLD_TYRE_MOVED_TO_WORKSHOP :
			oldTyreMovedTo 	=	 OLD_TYRE_MOVED_TO_WORKSHOP_NAME;
			break;
		case OLD_TYRE_MOVED_TO_SPARE :
			oldTyreMovedTo 	=	 OLD_TYRE_MOVED_TO_SPARE_NAME;
			break;
		case REPAIR_IN_PROCESS :
			oldTyreMovedTo 	=	 REPAIR_IN_PROCESS_NAME;
			break;
		case REPAIR_COMPLETED :
			oldTyreMovedTo 	=	 REPAIR_COMPLETED_NAME;
			break;
		default :
			oldTyreMovedTo = "--";
			break;
		}
		return oldTyreMovedTo;
	}
	
	public static String getKinpin (short kinpinId) throws Exception {
		String kinpin = null;
		switch(kinpinId) {
		case KINPIN_WORKING :
			kinpin 	= 	 KINPIN_WORKING_NAME;
			break;
		case KINPIN_NOT_WORKING :
			kinpin 	=	 KINPIN_NOT_WORKING_NAME;
			break;
		default :
			kinpin = "--";
			break;
		}
		return kinpin;
	}
	
	public static String getTransactionType (short transactionTypeId) throws Exception {
		String transactionType = null;
		switch(transactionTypeId) {
		case TRANSACTION_TYPE_ISSUE :
			transactionType 	= 	 TRANSACTION_TYPE_ISSUE_NAME;
			break;
		case TRANSACTION_TYPE_WO :
			transactionType 	=	 TRANSACTION_TYPE_WO_NAME;
			break;
		case TRANSACTION_TYPE_DSE :
			transactionType 	=	 TRANSACTION_TYPE_DSE_NAME;
			break;
		default :
			transactionType = "--";
			break;
		}
		return transactionType;
	}
	
	
	public static String getTransactionUsageType (short transactionUsageTypeId) throws Exception {
		String transactionUsageType = null;
		switch(transactionUsageTypeId) {
		case TRANSACTION_USAGE_TYPE_MOUNT :
			transactionUsageType 	= 	 TRANSACTION_USAGE_TYPE_MOUNT_NAME;
			break;
		case TRANSACTION_USAGE_TYPE_DISMOUNT :
			transactionUsageType 	=	 TRANSACTION_USAGE_TYPE_DISMOUNT_NAME;
			break;
		case TRANSACTION_USAGE_TYPE_ROTATE :
			transactionUsageType 	=	 TRANSACTION_USAGE_TYPE_ROTATE_NAME;
			break;
		case TRANSACTION_USAGE_TYPE_VEHICLE :
			transactionUsageType 	= 	 TRANSACTION_USAGE_TYPE_VEHICLE_NAME;
			break;
		case TRANSACTION_USAGE_TYPE_WO :
			transactionUsageType 	=	 TRANSACTION_USAGE_TYPE_WO_NAME;
			break;
		case TRANSACTION_USAGE_TYPE_DSE :
			transactionUsageType 	=	 TRANSACTION_USAGE_TYPE_DSE_NAME;
			break;
		case TRANSACTION_USAGE_TYPE_ISSUE :
			transactionUsageType 	= 	 TRANSACTION_USAGE_TYPE_ISSUE_NAME;
			break;
		case TRANSACTION_USAGE_TYPE_TRIP :
			transactionUsageType 	=	 TRANSACTION_USAGE_TYPE_TRIP_NAME;
			break;
		case TRANSACTION_USAGE_TYPE_FUEL :
			transactionUsageType 	=	 TRANSACTION_USAGE_TYPE_FUEL_NAME;
			break;
		case TRANSACTION_USAGE_TYPE_UREA :
			transactionUsageType 	=	 TRANSACTION_USAGE_TYPE_UREA_NAME;
			break;
		default :
			transactionUsageType = "--";
			break;
		}
		return transactionUsageType;
	}
}
