package org.fleetopgroup.constant;

public class PurchaseOrderType {

	public static final short TRANSFER_VIA_AIR = 1;
	public static final short TRANSFER_VIA_COURIER = 2;
	public static final short TRANSFER_VIA_EXPEDITED = 3;
	public static final short TRANSFER_VIA_GROUND = 4;
	public static final short TRANSFER_VIA_NEXT_DAY = 5;
	public static final short TRANSFER_VIA_NONE = 6;
	public static final short TRANSFER_BY_ROAD = 7;

	public static final String TRANSFER_VIA_AIR_NAME = "AIR";
	public static final String TRANSFER_VIA_COURIER_NAME = "COURIER";
	public static final String TRANSFER_VIA_EXPEDITED_NAME = "EXPEDITED";
	public static final String TRANSFER_VIA_GROUND_NAME = "GROUND";
	public static final String TRANSFER_VIA_NEXT_DAY_NAME = "NEXT DAY";
	public static final String TRANSFER_VIA_NONE_NAME = "NONE";
	public static final String TRANSFER_BY_ROAD_NAME = "BY ROAD";

	public static final short PURCHASEORDER_STATUS_REQUISITION = 1;
	public static final short PURCHASEORDER_STATUS_ORDERED = 2;
	public static final short PURCHASEORDER_STATUS_RECEIVED = 3;
	public static final short PURCHASEORDER_STATUS_COMPLETED = 4;

	public static final String PURCHASEORDER_STATUS_REQUISITION_NAME = "REQUISITION";
	public static final String PURCHASEORDER_STATUS_ORDERED_NAME = "ORDERED";
	public static final String PURCHASEORDER_STATUS_RECEIVED_NAME = "RECEIVED";
	public static final String PURCHASEORDER_STATUS_COMPLETED_NAME = "COMPLETED";

	public static final short PURCHASEORDER_TYPE_PART_PO 	= 1;
	public static final short PURCHASEORDER_TYPE_TYRE_PO 	= 2;
	public static final short PURCHASEORDER_TYPE_BATTERY_PO = 3;
	public static final short PURCHASEORDER_TYPE_CLOTH_PO 	= 4;
	public static final short PURCHASEORDER_TYPE_UREA_PO 	= 5;

	public static final String PURCHASEORDER_TYPE_PART_PO_NAME 		= "PART-PO";
	public static final String PURCHASEORDER_TYPE_TYRE_PO_NAME 		= "TYRE-PO";
	public static final String PURCHASEORDER_TYPE_BATTERY_PO_NAME 	= "BATTERY-PO";
	public static final String PURCHASEORDER_TYPE_CLOTH_PO_NAME 	= "UPHOLSTERY-PO";
	public static final String PURCHASEORDER_TYPE_UREA_PO_NAME 		= "UREA-PO";
	
	public static final short PURCHASE_STATUS_NOT_APPROVED 	= 0;
	public static final short PURCHASE_STATUS_APPROVED 		= 1;
	
	public static final String PURCHASE_STATUS_NOT_APPROVED_NAME 	= "Not Approved";
	public static final String PURCHASE_STATUS_APPROVED_NAME 		= "Approved";

	public PurchaseOrderType() {
		super();
	}

	public static String getTransferViaName(short status) {

		String statusName = null;
		switch (status) {
		case TRANSFER_VIA_AIR:
			statusName = TRANSFER_VIA_AIR_NAME;
			break;
		case TRANSFER_VIA_COURIER:
			statusName = TRANSFER_VIA_COURIER_NAME;
			break;
		case TRANSFER_VIA_EXPEDITED:
			statusName = TRANSFER_VIA_EXPEDITED_NAME;
			break;
		case TRANSFER_VIA_GROUND:
			statusName = TRANSFER_VIA_GROUND_NAME;
			break;
		case TRANSFER_VIA_NEXT_DAY:
			statusName = TRANSFER_VIA_NEXT_DAY_NAME;
			break;
		case TRANSFER_VIA_NONE:
			statusName = TRANSFER_VIA_NONE_NAME;
			break;
		case TRANSFER_BY_ROAD:
			statusName = TRANSFER_BY_ROAD_NAME;
			break;
		default:
			statusName = "--";
			break;
		}
		return statusName;
	}

	public static String getPurchaseStatusName(short status) {

		String statusName = null;
		switch (status) {
		case PURCHASEORDER_STATUS_REQUISITION:
			statusName = PURCHASEORDER_STATUS_REQUISITION_NAME;
			break;
		case PURCHASEORDER_STATUS_ORDERED:
			statusName = PURCHASEORDER_STATUS_ORDERED_NAME;
			break;
		case PURCHASEORDER_STATUS_RECEIVED:
			statusName = PURCHASEORDER_STATUS_RECEIVED_NAME;
			break;
		case PURCHASEORDER_STATUS_COMPLETED:
			statusName = PURCHASEORDER_STATUS_COMPLETED_NAME;
			break;

		default:
			statusName = "--";
			break;
		}
		return statusName;
	}

	public static String getPurchaseorderTypeName(short status) {

		String statusName = null;
		switch (status) {
		case PURCHASEORDER_TYPE_PART_PO:
			statusName = PURCHASEORDER_TYPE_PART_PO_NAME;
			break;
		case PURCHASEORDER_TYPE_TYRE_PO:
			statusName = PURCHASEORDER_TYPE_TYRE_PO_NAME;
			break;
		case PURCHASEORDER_TYPE_BATTERY_PO:
			statusName = PURCHASEORDER_TYPE_BATTERY_PO_NAME;
			break;
		case PURCHASEORDER_TYPE_CLOTH_PO:
			statusName = PURCHASEORDER_TYPE_CLOTH_PO_NAME;
			break;
		case PURCHASEORDER_TYPE_UREA_PO:
			statusName = PURCHASEORDER_TYPE_UREA_PO_NAME;
			break;	
		default:
			statusName = "--";
			break;
		}
		return statusName;
	}
	

	public static String getApprrovalStatusStr(short status) {

		String statusName = null;
		switch (status) {
		case PURCHASE_STATUS_NOT_APPROVED:
			statusName = PURCHASE_STATUS_NOT_APPROVED_NAME;
			break;
		case PURCHASE_STATUS_APPROVED:
			statusName = PURCHASE_STATUS_APPROVED_NAME;
			break;
		default:
			statusName = "--";
			break;
		}
		return statusName;
	}

}
