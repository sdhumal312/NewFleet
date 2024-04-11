package org.fleetopgroup.constant;

public class RepairConstant {

	public static final short REPAIR_TYPE_PART_ID 		= 1;
	public static final short REPAIR_TYPE_TYRE_ID 		= 2;
	public static final short REPAIR_TYPE_BATTERY_ID 	= 3;
	
	public static final String REPAIR_TYPE_PART_NAME 		= "PART";
	public static final String REPAIR_TYPE_TYRE_NAME 		= "TYRE";
	public static final String REPAIR_TYPE_BATTERY_NAME 	= "BATTERY";
	
	public static final short REPAIR_WORKSHOP_OWN 			= 1;
	public static final short REPAIR_WORKSHOP_OUTSIDE 		= 2;
	
	public static final String REPAIR_WORKSHOP_OWN_NAME 			= "OWN";
	public static final String REPAIR_WORKSHOP_OUTSIDE_NAME 		= "OUTSIDE";
	
	public static final short REPAIR_STATUS_CREATED			= 1;
	public static final short REPAIR_STATUS_SENT_TO_REPAIR 	= 2;
	public static final short REPAIR_STATUS_COMPLETE 		= 3;
	
	public static final String REPAIR_STATUS_CREATED_NAME 			= "CREATED";
	public static final String REPAIR_STATUS_SENT_TO_REPAIR_NAME 	= "SENT_TO_REPAIR";
	public static final String REPAIR_STATUS_COMPLETE_NAME 			= "COMPLETE";
	
	public static final short REPAIR_STOCK_STATUS_OPEN		 		= 0;
	public static final short REPAIR_STOCK_STATUS_INPROCESS 		= 1;
	public static final short REPAIR_STOCK_STATUS_REJECT 			= 2;
	public static final short REPAIR_STOCK_STATUS_REPAIRED 			= 3;
	
	public static final String REPAIR_STOCK_STATUS_OPEN_NAME  			= "STOCK_OPEN";
	public static final String REPAIR_STOCK_STATUS_INPROCESS_NAME  		= "STOCK_IN_PROCESS";
	public static final String REPAIR_STOCK_STATUS_REJECT_NAME  		= "STOCK_REJECTED";
	public static final String REPAIR_STOCK_STATUS_REPAIRED_NAME  		= "STOCK_REPAIRED";
	
	public static final short DEFAULT_DIS_TAX_TYPE_ID			= 0;
	public static final short PERCENTAGE_TYPE_ID				= 1;
	public static final short AMOUNT_TYPE_ID					= 2;
	
	public static final String DEFAULT_DIS_TAX_TYPE_NAME 		= "DEFAULT";
	public static final String PERCENTAGE_TYPE_NAME 			= "PERCENTAGE";
	public static final String AMOUNT_TYPE_NAME	 				= "AMOUNT";
	
	public static final short REJECT_STOCK_MOVE_TO_RETREAD 		= 1;
	public static final short REJECT_STOCK_MOVE_TO_SCRAPED 		= 2;
	
	public static final String REJECT_STOCK_MOVE_TO_RETREAD_NAME 		= "STOCK MOVE TO RETREAD";
	public static final String REJECT_STOCK_MOVE_TO_SCRAPED_NAME 		= "STOCK MOVE TO SCRAPED";
	
	public static final short SENT_MAIN_ASSET_TO_REPAIR					= 1;
	public static final short SENT_ADDITIONAL_ASSET_TO_REPAIR			= 2;
	
	public static final String SENT_MAIN_ASSET_TO_REPAIR_NAME 			= "SENT_MAIN_ASSET_TO_REPAIR";
	public static final String SENT_ADDITIONAL_ASSET_TO_REPAIR_NAME 	= "SENT_ADDITIONAL_ASSET_TO_REPAIR";
	
	
	public static  String  getDiscoutTaxType(short type) throws Exception {
		String statusString = null;
		switch (type) {
		case DEFAULT_DIS_TAX_TYPE_ID:
			statusString = DEFAULT_DIS_TAX_TYPE_NAME;
			break;
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
	
	
	public RepairConstant() {
		super();
	}

	public static String getRepairType(short status) {

		String repairType = null;
		switch (status) {
		case REPAIR_TYPE_PART_ID:
			repairType = REPAIR_TYPE_PART_NAME;
			break;
		case REPAIR_TYPE_TYRE_ID:
			repairType = REPAIR_TYPE_TYRE_NAME;
			break;
		case REPAIR_TYPE_BATTERY_ID:
			repairType = REPAIR_TYPE_BATTERY_NAME;
			break;
		
		default:
			repairType = "--";
			break;
		}
		return repairType;
	}
	
	public static String getRepairWorkshop(short status) {

		String repairWorkshop = null;
		switch (status) {
		case REPAIR_WORKSHOP_OWN:
			repairWorkshop = REPAIR_WORKSHOP_OWN_NAME;
			break;
		case REPAIR_WORKSHOP_OUTSIDE:
			repairWorkshop = REPAIR_WORKSHOP_OUTSIDE_NAME;
			break;
		
		default:
			repairWorkshop = "--";
			break;
		}
		return repairWorkshop;
	}
	
	public static String getRepairStatus(short status) {

		String repairStatus = null;
		switch (status) {
		case REPAIR_STATUS_CREATED:
			repairStatus = REPAIR_STATUS_CREATED_NAME;
			break;
		case REPAIR_STATUS_SENT_TO_REPAIR:
			repairStatus = REPAIR_STATUS_SENT_TO_REPAIR_NAME;
			break;
		case REPAIR_STATUS_COMPLETE:
			repairStatus = REPAIR_STATUS_COMPLETE_NAME;
			break;
		
		default:
			repairStatus = "--";
			break;
		}
		return repairStatus;
	}
	
	
	public static String getRepairStockStatus(short status) {

		String repairStockStatus = null;
		switch (status) {
		case REPAIR_STOCK_STATUS_OPEN:
			repairStockStatus = REPAIR_STOCK_STATUS_OPEN_NAME;
			break;
		case REPAIR_STOCK_STATUS_INPROCESS:
			repairStockStatus = REPAIR_STOCK_STATUS_INPROCESS_NAME;
			break;
		case REPAIR_STOCK_STATUS_REJECT:
			repairStockStatus = REPAIR_STOCK_STATUS_REJECT_NAME;
			break;
		case REPAIR_STOCK_STATUS_REPAIRED:
			repairStockStatus = REPAIR_STOCK_STATUS_REPAIRED_NAME;
			break;
		
		default:
			repairStockStatus = "--";
			break;
		}
		return repairStockStatus;
	}
	
	
}
