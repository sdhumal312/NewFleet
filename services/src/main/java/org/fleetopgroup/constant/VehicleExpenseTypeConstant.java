package org.fleetopgroup.constant;

import java.util.HashMap;

public class VehicleExpenseTypeConstant {
	
	public static final String	EXPENSE_TYPE	= "EXPENSE_TYPE";
	public static final String	INCOME_TYPE		= "INCOME_TYPE";

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
	public static final short	TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES	= 12;
	public static final short	INSPECTION_PENALTY						= 13;
	public static final short	TRANSACTION_TYPE_DRIVER_BHATTA			= 14;
	
	public static final String	TRANSACTION_TYPE_FUEL_NAME						= "Fuel";
	public static final String	TRANSACTION_TYPE_SERVICE_ENTRIES_NAME			= "Outside Maintenance Charges";
	public static final String	TRANSACTION_TYPE_WORK_ORDER_NAME				= "In-House Maintenance Charges";
	public static final String	TRANSACTION_TYPE_TRIPSHEET_NAME					= "TripSheet Charges";
	public static final String	TRANSACTION_TYPE_RENEWAL_NAME					= "Compliance Charges";
	public static final String	TRANSACTION_TYPE_TYRE_NAME						= "Tyre";
	public static final String	TRANSACTION_TYPE_BATTERY_NAME					= "Battery";
	public static final String	TRANSACTION_TYPE_VEHICLE_EMI_NAME				= "Vehicle EMI";
	public static final String	TRANSACTION_TYPE_DRIVER_SALARY_NAME				= "Driver Salary";
	public static final String	TRANSACTION_TYPE_UREA_NAME						= "Urea";
	public static final String	VENDOR_APPROVAL_PAYMENT_NAME					= "Vendor Payment";
	public static final String	TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES_NAME	= "Dealer Service Charges";
	public static final String	INSPECTION_PENALTY_NAME							= "Ispection Parameter failed penalty";
	public static final String	TRANSACTION_TYPE_DRIVER_BHATTA_NAME			    = "Driver Bhatta";

	
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
		case TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES:
			statusString = TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES_NAME;
			break;
			
		case INSPECTION_PENALTY:
			statusString = INSPECTION_PENALTY_NAME;
			break;
		case TRANSACTION_TYPE_DRIVER_BHATTA:
			statusString = TRANSACTION_TYPE_DRIVER_BHATTA_NAME;
		
		
		default:
			statusString = "Others";
			break;
		}
		return statusString;
	} 
	
	public static HashMap<Short, String>  getVehicleExpenseTypeMap() throws Exception{
		HashMap<Short, String>		hashMap = new HashMap<Short, String>();
		try {
			
			hashMap.put(TRANSACTION_TYPE_FUEL, TRANSACTION_TYPE_FUEL_NAME);
			hashMap.put(TRANSACTION_TYPE_SERVICE_ENTRIES, TRANSACTION_TYPE_SERVICE_ENTRIES_NAME);
			hashMap.put(TRANSACTION_TYPE_WORK_ORDER, TRANSACTION_TYPE_WORK_ORDER_NAME);
			hashMap.put(TRANSACTION_TYPE_TYRE, TRANSACTION_TYPE_TYRE_NAME);
			hashMap.put(TRANSACTION_TYPE_BATTERY, TRANSACTION_TYPE_BATTERY_NAME);
			hashMap.put(TRANSACTION_TYPE_VEHICLE_EMI, TRANSACTION_TYPE_VEHICLE_EMI_NAME);
			hashMap.put(TRANSACTION_TYPE_DRIVER_SALARY, TRANSACTION_TYPE_DRIVER_SALARY_NAME);
			hashMap.put(TRANSACTION_TYPE_UREA, TRANSACTION_TYPE_UREA_NAME);
			hashMap.put(TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES, TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES_NAME);
			hashMap.put(TRANSACTION_TYPE_DRIVER_BHATTA, TRANSACTION_TYPE_DRIVER_BHATTA_NAME);
			return hashMap;
		} catch (Exception e) {
			throw e;
		}
	}
	
}
