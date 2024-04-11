package org.fleetopgroup.constant;

public class ModuleConstant {
	
	private ModuleConstant() {
		
	}
	
	public static final short PART_INVENTORY =1;
	public static final short TYRE_INVENTORY =2;
	public static final short BATTRRY_INVENTORY =3;
	public static final short FUEL_INVENTORY =4;
	public static final short UREA_INVENTORY =5;
	public static final short UPHOLSTERY_INVENTORY =6;
	public static final short RENEWAL_REMINDER = 7;
	public static final short DEALER_SERVICE = 8;
	public static final short APPROVAL_ENTRIES = 9;
	public static final short CLOTH_LAUNDRY = 10;
	public static final short PURCHASE_ORDER = 11;
	public static final short FUEL_ENTRY	 = 12;
	public static final short TRIP_INCOME	 = 13;
	public static final short TRIP_EXPENSE	 = 14;
	public static final short TRIP_ADVANCE	 = 15;
	public static final short TRIP_CLOSE	 = 16;
	public static final short SERVICE_ENTRIES = 17;
	
	
	
	public static final String PART_INVENTORY_NAME = "Part Inventory";
	public static final String TYRE_INVENTORY_NAME = "Tyre Inventory";
	public static final String BATTRRY_INVENTORY_NAME ="Battery Inventory";
	public static final String FUEL_INVENTORY_NAME ="Fuel Inventory";
	public static final String UREA_INVENTORY_NAME ="Urea Inventiry";
	public static final String UPHOLSTERY_INVENTORY_NAME ="Upholstery Inventory";
	public static final String RENEWAL_REMINDER_NAME = "Renewal Reminder";
	public static final String DEALER_SERVICE_NAME = "Dealer Service";
	public static final String APPROVAL_ENTRIES_NAME = "Approval Entries";
	public static final String CLOTH_LAUNDRY_NAME = "Cloth Inventory";
	public static final String PURCHASE_ORDER_NAME = "purchase Order";
	public static final String FUEL_ENTRY_NAME	   = "Fuel Entry";
	public static final String TRIP_INCOME_NAME	   = "Trip income";
	public static final String TRIP_EXPENSE_NAME   = "Trip expense";
	public static final String TRIP_ADVANCE_NAME   = "Trip advance";
	public static final String TRIP_CLOSE_NAME	 = "Trip Close";
	public static final String SERVICE_ENTRIES_NAME	 = "Service Entries";
	
	public static final String PART_INVENTORY_NUMBER_PREFIX = "PI";
	public static final String TYRE_INVENTORY_NUMBER_PREFIX = "TI";
	public static final String BATTRRY_INVENTORY_NUMBER_PREFIX ="BI";
	public static final String FUEL_INVENTORY_NUMBER_PREFIX ="FI";
	public static final String UREA_INVENTORY_NUMBER_PREFIX ="UI";
	public static final String UPHOLSTERY_INVENTORY_NUMBER_PREFIX ="CI";
	public static final String RENEWAL_REMINDER_NUMBER_PREFIX = "RR";
	public static final String DEALER_SERVICE_NUMBER_PREFIX = "DSE";
	public static final String APPROVAL_ENTRIES_NUMBER_PREFIX = "A";
	public static final String CLOTH_LAUNDRY_NUMBER_PREFIX = "LI";
	public static final String PURCHASE_ORDER_NUMBER_PREFIX = "PO";
	public static final String FUEL_ENTRY_NUMBER_PREFIX = "F";
	public static final String TRIP_SHEET_NUMBER_PREFIX = "T";
	public static final String SERVICE_ENTRIES_NUMBER_PREFIX = "SE";
	
	
	public static String getNumberPrefixByModuleIdentfier(short moduleIdentifier) {
		
		String moduleName = null;
		switch (moduleIdentifier) {
		case PART_INVENTORY:
			moduleName = PART_INVENTORY_NUMBER_PREFIX;
			break;
		case TYRE_INVENTORY:
			moduleName = TYRE_INVENTORY_NUMBER_PREFIX;
			break;
		case BATTRRY_INVENTORY:
			moduleName = BATTRRY_INVENTORY_NUMBER_PREFIX;
			break;
		case FUEL_INVENTORY:
			moduleName = FUEL_INVENTORY_NUMBER_PREFIX;
			break;
		case UREA_INVENTORY:
			moduleName = UREA_INVENTORY_NUMBER_PREFIX;
			break;
		case UPHOLSTERY_INVENTORY:
			moduleName = UPHOLSTERY_INVENTORY_NUMBER_PREFIX;
			break;
		case RENEWAL_REMINDER:
			moduleName = RENEWAL_REMINDER_NUMBER_PREFIX;
			break;
		case DEALER_SERVICE:
			moduleName = DEALER_SERVICE_NUMBER_PREFIX;
			break;
		case APPROVAL_ENTRIES:
			moduleName = APPROVAL_ENTRIES_NUMBER_PREFIX;
			break;
		case CLOTH_LAUNDRY:
			moduleName = CLOTH_LAUNDRY_NUMBER_PREFIX;
			break;
		case PURCHASE_ORDER:
			moduleName = PURCHASE_ORDER_NUMBER_PREFIX;
			break;
		case FUEL_ENTRY:
			moduleName = FUEL_ENTRY_NUMBER_PREFIX;
			break;
		case TRIP_INCOME:
			moduleName = TRIP_SHEET_NUMBER_PREFIX;
			break;
		case TRIP_EXPENSE:
			moduleName = TRIP_SHEET_NUMBER_PREFIX;
			break;
		case TRIP_ADVANCE:
			moduleName = TRIP_SHEET_NUMBER_PREFIX;
			break;
		case TRIP_CLOSE:
			moduleName = TRIP_SHEET_NUMBER_PREFIX;
			break;
		case SERVICE_ENTRIES:
			moduleName = SERVICE_ENTRIES_NUMBER_PREFIX;
			break;	
		default:
			moduleName = "-";
		}
		return moduleName;
		
	}
	
	public static String getModuleNameByModuleIdentifier(short moduleIdentifier) {
		String moduleName = null;
		switch (moduleIdentifier) {
		case PART_INVENTORY:
			moduleName = PART_INVENTORY_NAME;
			break;
		case TYRE_INVENTORY:
			moduleName = TYRE_INVENTORY_NAME;
			break;
		case BATTRRY_INVENTORY:
			moduleName = BATTRRY_INVENTORY_NAME;
			break;
		case FUEL_INVENTORY:
			moduleName = FUEL_INVENTORY_NAME;
			break;
		case UREA_INVENTORY:
			moduleName = UREA_INVENTORY_NAME;
			break;
		case UPHOLSTERY_INVENTORY:
			moduleName = UPHOLSTERY_INVENTORY_NAME;
			break;
		case RENEWAL_REMINDER:
			moduleName = RENEWAL_REMINDER_NAME;
			break;
		case DEALER_SERVICE:
			moduleName = DEALER_SERVICE_NAME;
			break;
		case APPROVAL_ENTRIES:
			moduleName = APPROVAL_ENTRIES_NAME;
			break;
		case CLOTH_LAUNDRY:
			moduleName = CLOTH_LAUNDRY_NAME;
			break;
		case PURCHASE_ORDER:
			moduleName = PURCHASE_ORDER_NAME;
			break;
		case FUEL_ENTRY:
			moduleName = FUEL_ENTRY_NAME;
			break;
		case TRIP_INCOME:
			moduleName = TRIP_INCOME_NAME;
			break;
		case TRIP_EXPENSE:
			moduleName = TRIP_EXPENSE_NAME;
			break;
		case TRIP_ADVANCE:
			moduleName = TRIP_ADVANCE_NAME;
			break;
		case TRIP_CLOSE:
			moduleName = TRIP_CLOSE_NAME;
			break;
		case SERVICE_ENTRIES:
			moduleName = SERVICE_ENTRIES_NAME;
			break;	
		default:
			moduleName = "-";
		}
		return moduleName;
	}

}
