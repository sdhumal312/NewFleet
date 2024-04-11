package org.fleetopgroup.constant;

public class TallyVoucherTypeContant {

	public static final short	TRANSACTION_TYPE_FUEL					= 1;
	public static final short	TRANSACTION_TYPE_SERVICE_ENTRIES		= 2;
	public static final short	TRANSACTION_TYPE_WORK_ORDER				= 3;
	public static final short	TRANSACTION_TYPE_TRIPSHEET				= 4;
	public static final short	TRANSACTION_TYPE_RENEWAL				= 5;
	public static final short	TRANSACTION_TYPE_TYRE					= 6;
	public static final short	TRANSACTION_TYPE_BATTERY				= 7;
	public static final short	TRANSACTION_TYPE_VEHICLE_EMI			= 8;
	public static final short	TRANSACTION_TYPE_DRIVER_SALARY			= 9;
	public static final short	TRANSACTION_TYPE_UREA					= 10;
	public static final short	VENDOR_APPROVAL_PAYMENT					= 11;
	public static final short	TRANSACTION_TYPE_PART_INVENTORY			= 12;
	public static final short	TRANSACTION_TYPE_UPHOLSTERY_INVENTORY	= 13;
	public static final short	TRANSACTION_TYPE_FASTTAG				= 14;
	public static final short	TRANSACTION_TYPE_PURCHASE_ORDER			= 15;
	public static final short	TRANSACTION_TYPE_LAUNDRY				= 16;
	public static final short	TRANSACTION_TYPE_DRIVER_BALANCE			= 17;
	public static final short	TRANSACTION_TYPE_DEALER_ENTRIES			= 18;
	public static final short	TRIP_ADVANCE_PAYMENT					= 19;
	
	public static final String	TRANSACTION_TYPE_FUEL_NAME						= "Fuel";
	public static final String	TRANSACTION_TYPE_SERVICE_ENTRIES_NAME			= "Service Entries";
	public static final String	TRANSACTION_TYPE_WORK_ORDER_NAME				= "Work Order";
	public static final String	TRANSACTION_TYPE_TRIPSHEET_NAME					= "TripSheet";
	public static final String	TRANSACTION_TYPE_RENEWAL_NAME					= "Renewal";
	public static final String	TRANSACTION_TYPE_TYRE_NAME						= "Tyre";
	public static final String	TRANSACTION_TYPE_BATTERY_NAME					= "Battery";
	public static final String	TRANSACTION_TYPE_VEHICLE_EMI_NAME				= "Vehicle EMI";
	public static final String	TRANSACTION_TYPE_DRIVER_SALARY_NAME				= "Driver Salary";
	public static final String	TRANSACTION_TYPE_UREA_NAME						= "Urea";
	public static final String	VENDOR_APPROVAL_PAYMENT_NAME					= "Vendor Payment";
	public static final String	TRANSACTION_TYPE_PART_INVENTORY_NAME			= "Part Invoice";
	public static final String	TRANSACTION_TYPE_UPHOLSTERY_INVENTORY_NAME		= "Upholstery Invoice";
	public static final String	TRANSACTION_TYPE_FASTTAG_NAME					= "FastTag";
	public static final String	TRANSACTION_TYPE_PURCHASE_ORDER_NAME			= "Purchase Order";
	public static final String	TRANSACTION_TYPE_LAUNDRY_NAME					= "Laundry";
	public static final String	TRANSACTION_TYPE_DRIVER_BALANCE_NAME			= "Driver Balance";
	public static final String	TRANSACTION_TYPE_DEALER_ENTRIES_NAME			= "Dealer Entires";
	public static final String      TRIP_ADVANCE_PAYMENT_NAME						= "Trip Advance";
	
	public static  String  getExpenseTypeName(short type) throws Exception {
		String statusString = null;
		switch (type) {
		case TRANSACTION_TYPE_FUEL:
			statusString = TRANSACTION_TYPE_FUEL_NAME;
			break;
		case TRANSACTION_TYPE_SERVICE_ENTRIES:
			statusString = TRANSACTION_TYPE_SERVICE_ENTRIES_NAME;
			break;
		case TRANSACTION_TYPE_WORK_ORDER:
			statusString = TRANSACTION_TYPE_WORK_ORDER_NAME;
			break;
		case TRANSACTION_TYPE_TRIPSHEET:
			statusString = TRANSACTION_TYPE_TRIPSHEET_NAME;
			break;
		case TRANSACTION_TYPE_RENEWAL:
			statusString = TRANSACTION_TYPE_RENEWAL_NAME;
			break;
		case TRANSACTION_TYPE_TYRE:
			statusString = TRANSACTION_TYPE_TYRE_NAME;
			break;
		case TRANSACTION_TYPE_BATTERY:
			statusString = TRANSACTION_TYPE_BATTERY_NAME;
			break;
		case TRANSACTION_TYPE_VEHICLE_EMI:
			statusString = TRANSACTION_TYPE_VEHICLE_EMI_NAME;
			break;
		case TRANSACTION_TYPE_DRIVER_SALARY:
			statusString = TRANSACTION_TYPE_DRIVER_SALARY_NAME;
			break;
		case TRANSACTION_TYPE_UREA:
			statusString = TRANSACTION_TYPE_UREA_NAME;
			break;
		case VENDOR_APPROVAL_PAYMENT:
			statusString = VENDOR_APPROVAL_PAYMENT_NAME;
			break;
		case TRANSACTION_TYPE_PART_INVENTORY:
			statusString = TRANSACTION_TYPE_PART_INVENTORY_NAME;
			break;
		case TRANSACTION_TYPE_UPHOLSTERY_INVENTORY:
			statusString = TRANSACTION_TYPE_UPHOLSTERY_INVENTORY_NAME;
			break;
		case TRANSACTION_TYPE_FASTTAG:
			statusString = TRANSACTION_TYPE_FASTTAG_NAME;
			break;	
		case TRANSACTION_TYPE_PURCHASE_ORDER:
			statusString = TRANSACTION_TYPE_PURCHASE_ORDER_NAME;
			break;	
		case TRANSACTION_TYPE_LAUNDRY:
			statusString = TRANSACTION_TYPE_LAUNDRY_NAME;
			break;	
		case TRANSACTION_TYPE_DRIVER_BALANCE:
			statusString = TRANSACTION_TYPE_DRIVER_BALANCE_NAME;
			break;	
		case TRANSACTION_TYPE_DEALER_ENTRIES:
			statusString = TRANSACTION_TYPE_DEALER_ENTRIES_NAME;
			break;
		case TRIP_ADVANCE_PAYMENT:
			statusString = TRIP_ADVANCE_PAYMENT_NAME;
			break;	
		default:
			statusString = "Others";
			break;
		}
		return statusString;
	} 
	
}
