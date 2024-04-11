package org.fleetopgroup.constant;

public class InventoryTransferViaType {

	public static final short AIR 		= 1;
	public static final short COURIER	= 2;
	public static final short EXPEDITED 	= 3;
	public static final short GROUND 	= 4;
	public static final short NEXT_DAY 	= 5;
	public static final short NONE 		= 6;

	public static final String AIR_NAME 			= "AIR";
	public static final String COURIER_NAME 		= "COURIER";
	public static final String EXPEDITED_NAME 	= "EXPEDITED";
	public static final String GROUND_NAME 		= "GROUND";
	public static final String NEXT_DAY_NAME 	= "NEXT_DAY";
	public static final String NONE_NAME 		= "NONE";

	public static String getInventoryTransferViaTypeName(short requisition_STATUS_ID) {

		String InventoryTransferViaTypeTypeName = null;
		switch (requisition_STATUS_ID) {

		case AIR:
			InventoryTransferViaTypeTypeName = AIR_NAME;
			break;
		case COURIER:
			InventoryTransferViaTypeTypeName = COURIER_NAME;
			break;
		case EXPEDITED:
			InventoryTransferViaTypeTypeName = EXPEDITED_NAME;
			break;
		case GROUND:
			InventoryTransferViaTypeTypeName = GROUND_NAME;
			break;
		case NEXT_DAY:
			InventoryTransferViaTypeTypeName = NEXT_DAY_NAME;
			break;
		case NONE:
			InventoryTransferViaTypeTypeName = NONE_NAME;
			break;

		default:
			InventoryTransferViaTypeTypeName = "--";
			break;
		}
		return InventoryTransferViaTypeTypeName;
	}

}
