package org.fleetopgroup.constant;

import java.util.HashMap;

public class PropertyFileConstants {
	
	private Integer moduleId;
	private String moduleName;
	
	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public static HashMap<Integer, String>  moduleWiseNameHM  = null;
	public static HashMap<Integer, String>  moduleWisePathHM  = null;

	

	public static final String PROPERTIES_EXTENSION			 = ".yml";
	public static final String DEFAULT						 = "default";
	
	public static final int COMPANY_CONFIGURATION_CONFIG							= 1;
	public static final int DRIVER_CONFIGURATION_CONFIG								= 2;
	public static final int FUEL_CONFIGURATION_CONFIG								= 3;
	public static final int PENALTY_CONFIGURATION_CONFIG							= 4;
	public static final int MASTER_CONFIGURATION_CONFIG								= 5;
	public static final int CALENDER_BREAK_DOWN_CONFIGURATION_CONFIG				= 6;
	public static final int VENDOR_TYPE_CONFIGURATION_CONFIG						= 7;
	public static final int PART_INVENTORY_CONFIGURATION_CONFIG						= 8;
	public static final int WORK_ORDERS_CONFIGURATION_CONFIG						= 9;
	public static final int TRIPSHEET_CONFIGURATION_CONFIG							= 10;
	public static final int VEHICLE_CONFIGURATION_CONFIG							= 11;
	public static final int MASTER_PARTS_CONFIGURATION_CONFIG						= 12;
	public static final int ISSUES_CONFIGURATION_CONFIG								= 13;
	public static final int TYRE_INVENTORY_CONFIG									= 14;
	public static final int TOLL_API_CONFIG											= 15;
	public static final int TRIP_COLLECTION_CONFIG									= 16;
	public static final int RENEWAL_REMINDER_CONFIG									= 17;
	public static final int CASHBOOK_CONFIG											= 18;
	public static final int LHPV_INTEGRATION_CONFIG									= 19;
	public static final int VEHICLE_GPS_CONFIG										= 20;
	public static final int SERVICE_REMINDER_CONFIG									= 21;
	public static final int SMS_CONFIG												= 22;
	public static final int UPHOLSTERY_CONFIG										= 23;
	public static final int VENDOR_CONFIGURATION_CONFIG								= 24;
	public static final int TRIP_ROUTE_CONFIGURATION_CONFIG							= 25;
	public static final int MOBILE_APP_VERSION_CHECK_CONFIG							= 26;
	public static final int FUEL_PRICE_CONFIG										= 27;
	public static final int SERVICE_ENTRIES_CONFIGURATION_CONFIG					= 28;
	public static final int BUS_BOOKING_CONFIGURATION_CONFIG						= 29;
	public static final int USER_CONFIGURATION_CONFIG								= 30;
	public static final int VEHICLE_REPORT_CONFIGURATION_CONFIG						= 31;
	public static final int PICK_DROP_CONFIGURATION_CONFIG							= 32;
	public static final int BATTERY_CONFIGURATION_CONFIG							= 33;
	public static final int CLOTH_CONFIGURATION_CONFIG								= 34;
	public static final int UREA_CONFIGURATION_CONFIG								= 35;
	public static final int SERVICE_PROGRAM_CONFIGURATION_CONFIG					= 36;
	public static final int PURCHASE_ORDER_CONFIG									= 37;
	public static final int PROFIT_AND_LOSS_REPORT_CONFIGURATION_CONFIG				= 38;
	public static final int FUEL_INVOICE_CONFIG										= 39;
	public static final int DSE_CONFIGURATION_CONFIG								= 40;
	public static final int INSPECTION_CONFIG										= 41;
	public static final int REPAIR_CONFIG											= 42;
	public static final int REQUISITION_CONFIG										= 43;
	public static final int LORRY_HIRE_CONFIG										= 44;
	public static final int TRIPSHEET_REPORT_CONFIGURATION_CONFIG	                = 45;
	
	
	public static final String COMPANY_CONFIGURATION_CONFIG_NAME							= "Company Configuration";
	public static final String DRIVER_CONFIGURATION_CONFIG_NAME								= "Driver";
	public static final String FUEL_CONFIGURATION_CONFIG_NAME								= "Fuel";
	public static final String PENALTY_CONFIGURATION_CONFIG_NAME							= "Penalty";
	public static final String MASTER_CONFIGURATION_CONFIG_NAME								= "Master";
	public static final String CALENDER_BREAK_DOWN_CONFIGURATION_CONFIG_NAME				= "Calender Break Down";
	public static final String VENDOR_TYPE_CONFIGURATION_CONFIG_NAME						= "Venodor Type";
	public static final String PART_INVENTORY_CONFIGURATION_CONFIG_NAME						= "Part Inventory";
	public static final String WORK_ORDERS_CONFIGURATION_CONFIG_NAME						= "WorkOrders";
	public static final String TRIPSHEET_CONFIGURATION_CONFIG_NAME							= "Tripsheet";
	public static final String VEHICLE_CONFIGURATION_CONFIG_NAME							= "Vehicle";
	public static final String MASTER_PARTS_CONFIGURATION_CONFIG_NAME						= "Master Parts";
	public static final String ISSUES_CONFIGURATION_CONFIG_NAME								= "Issue";
	public static final String TYRE_INVENTORY_CONFIG_NAME									= "Tyre";
	public static final String TOLL_API_CONFIG_NAME											= "Toll Api";
	public static final String TRIP_COLLECTION_CONFIG_NAME									= "Trip COllection";
	public static final String RENEWAL_REMINDER_CONFIG_NAME									= "Renewal";
	public static final String CASHBOOK_CONFIG_NAME											= "CashBook";
	public static final String LHPV_INTEGRATION_CONFIG_NAME									= "LHPV";
	public static final String VEHICLE_GPS_CONFIG_NAME										= "Vehicle GPS";
	public static final String SERVICE_REMINDER_CONFIG_NAME									= "Service Reminder";
	public static final String SMS_CONFIG_NAME												= "SMS";
	public static final String UPHOLSTERY_CONFIG_NAME										= "Uphostery Inventory";
	public static final String VENDOR_CONFIGURATION_CONFIG_NAME								= "Vendor";
	public static final String TRIP_ROUTE_CONFIGURATION_CONFIG_NAME							= "Trip Route";
	public static final String MOBILE_APP_VERSION_CHECK_CONFIG_NAME							= "Mobile App Version";
	public static final String FUEL_PRICE_CONFIG_NAME										= "Fuel Price";
	public static final String SERVICE_ENTRIES_CONFIGURATION_CONFIG_NAME					= "Service Entry";
	public static final String BUS_BOOKING_CONFIGURATION_CONFIG_NAME						= "Bus Booking";
	public static final String USER_CONFIGURATION_CONFIG_NAME								= "User Config";
	public static final String VEHICLE_REPORT_CONFIGURATION_CONFIG_NAME						= "Vehicle Report";
	public static final String PICK_DROP_CONFIGURATION_CONFIG_NAME							= "Pickup Drop";
	public static final String BATTERY_CONFIGURATION_CONFIG_NAME							= "Battery Inventory";
	public static final String CLOTH_CONFIGURATION_CONFIG_NAME								= "Cloth Inventory";
	public static final String UREA_CONFIGURATION_CONFIG_NAME								= "Urea";
	public static final String SERVICE_PROGRAM_CONFIGURATION_CONFIG_NAME					= "Service Program";
	public static final String PURCHASE_ORDER_CONFIG_NAME									= "Purchase Order";
	public static final String PROFIT_AND_LOSS_REPORT_CONFIGURATION_CONFIG_NAME				= "Profit & Loss";
	public static final String FUEL_INVOICE_CONFIG_NAME										= "Fuel Invoice";
	public static final String DSE_CONFIGURATION_CONFIG_NAME								= "DSE";
	public static final String INSPECTION_CONFIG_NAME										= "Inspection";
	public static final String REPAIR_CONFIG_NAME											= "Repair";
	public static final String REQUISITION_CONFIG_NAME										= "Requisition";
	public static final String LORRY_HIRE_CONFIG_NAME										= "Lorry Hire";
	public static final String TRIPSHEET_REPORT_CONFIGURATION_CONFIG_NAME	           		= "Tripsheet Report";
	
	
	
	public static final String COMPANY_CONFIGURATION_CONFIG_PATH					= "configuration/companyconfiguration/";
	public static final String DRIVER_CONFIGURATION_CONFIG_PATH						= "configuration/driver/";
	public static final String FUEL_CONFIGURATION_CONFIG_PATH						= "configuration/fuel/";
	public static final String PENALTY_CONFIGURATION_CONFIG_PATH					= "configuration/penalty/";
	public static final String MASTER_CONFIGURATION_CONFIG_PATH						= "configuration/master/";
	public static final String CALENDER_BREAK_DOWN_CONFIGURATION_CONFIG_PATH		= "configuration/calenderbreakdown/";
	public static final String VENDOR_TYPE_CONFIGURATION_CONFIG_PATH				= "configuration/vendortype/";
	public static final String PART_INVENTORY_CONFIGURATION_CONFIG_PATH				= "configuration/partinventory/";
	public static final String WORK_ORDERS_CONFIGURATION_CONFIG_PATH				= "configuration/workorders/";
	public static final String TRIPSHEET_CONFIGURATION_CONFIG_PATH					= "configuration/tripsheet/";
	public static final String VEHICLE_CONFIGURATION_CONFIG_PATH					= "configuration/vehicle/";
	public static final String MASTER_PARTS_CONFIGURATION_CONFIG_PATH				= "configuration/masterparts/";
	public static final String ISSUES_CONFIGURATION_CONFIG_PATH						= "configuration/issues/";
	public static final String TYRE_INVENTORY_CONFIG_PATH							= "configuration/tyreinventory/";
	public static final String TOLL_API_CONFIG_PATH									= "configuration/tollapi/";
	public static final String TRIP_COLLECTION_CONFIG_PATH							= "configuration/tripcollection/";
	public static final String RENEWAL_REMINDER_CONFIG_PATH							= "configuration/renewal/";
	public static final String CASHBOOK_CONFIG_PATH									= "configuration/cashbook/";
	public static final String LHPV_INTEGRATION_CONFIG_PATH							= "configuration/lhpv/";
	public static final String VEHICLE_GPS_CONFIG_PATH								= "configuration/gps/";
	public static final String SERVICE_REMINDER_CONFIG_PATH							= "configuration/servicereminder/";
	public static final String SMS_CONFIG_PATH										= "configuration/sms/";
	public static final String UPHOLSTERY_CONFIG_PATH								= "configuration/upholstery/";
	public static final String VENDOR_CONFIGURATION_CONFIG_PATH						= "configuration/vendor/";
	public static final String TRIP_ROUTE_CONFIGURATION_CONFIG_PATH					= "configuration/triproute/";
	public static final String MOBILE_APP_VERSION_CHECK_CONFIG_PATH					= "configuration/mobileVersionCheck/";
	public static final String FUEL_PRICE_CONFIG_PATH								= "configuration/fuelPrice/";
	public static final String SERVICE_ENTRIES_CONFIGURATION_CONFIG_PATH			= "configuration/SE/";
	public static final String BUS_BOOKING_CONFIGURATION_CONFIG_PATH				= "configuration/busbooking/";
	public static final String USER_CONFIGURATION_CONFIG_PATH						= "configuration/user/";
	public static final String VEHICLE_REPORT_CONFIGURATION_CONFIG_PATH				= "configuration/report/vehicle/";
	public static final String PICK_DROP_CONFIGURATION_CONFIG_PATH					= "configuration/pickDrop/";
	public static final String BATTERY_CONFIGURATION_CONFIG_PATH					= "configuration/batteryinventory/";
	public static final String CLOTH_CONFIGURATION_CONFIG_PATH						= "configuration/clothinventory/";
	public static final String UREA_CONFIGURATION_CONFIG_PATH						= "configuration/ureainventory/";
	public static final String SERVICE_PROGRAM_CONFIGURATION_CONFIG_PATH			= "configuration/serviceprogram/";
	public static final String PURCHASE_ORDER_CONFIG_PATH							= "configuration/purchaseorder/";
	public static final String PROFIT_AND_LOSS_REPORT_CONFIGURATION_CONFIG_PATH		= "configuration/report/profitAndLoss/";
	public static final String FUEL_INVOICE_CONFIG_PATH								= "configuration/fuelinventory/";
	public static final String DSE_CONFIGURATION_CONFIG_PATH						= "configuration/DSE/";
	public static final String INSPECTION_CONFIG_PATH								= "configuration/inspection/";
	public static final String REQUISITION_CONFIG_PATH								= "configuration/requisition/";
	public static final String REPAIR_CONFIG_PATH									= "configuration/repair/";
	public static final String LORRY_HIRE_CONFIG_PATH								= "configuration/lorryhire/";
	public static final String TRIPSHEET_REPORT_CONFIGURATION_CONFIG_PATH	        = "configuration/report/tripsheet/";

	
	static{
		moduleWiseNameHM  = new HashMap<>();
		moduleWiseNameHM.put(COMPANY_CONFIGURATION_CONFIG, COMPANY_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(FUEL_CONFIGURATION_CONFIG, FUEL_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(WORK_ORDERS_CONFIGURATION_CONFIG, WORK_ORDERS_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(TRIPSHEET_CONFIGURATION_CONFIG, TRIPSHEET_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(SERVICE_ENTRIES_CONFIGURATION_CONFIG, SERVICE_ENTRIES_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(PURCHASE_ORDER_CONFIG, PURCHASE_ORDER_CONFIG_NAME);
		moduleWiseNameHM.put(TOLL_API_CONFIG, TOLL_API_CONFIG_NAME);
		moduleWiseNameHM.put(DRIVER_CONFIGURATION_CONFIG, DRIVER_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(PENALTY_CONFIGURATION_CONFIG,PENALTY_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(MASTER_CONFIGURATION_CONFIG, MASTER_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(CALENDER_BREAK_DOWN_CONFIGURATION_CONFIG, CALENDER_BREAK_DOWN_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(VENDOR_TYPE_CONFIGURATION_CONFIG, VENDOR_TYPE_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(PART_INVENTORY_CONFIGURATION_CONFIG, PART_INVENTORY_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(MASTER_PARTS_CONFIGURATION_CONFIG,  MASTER_PARTS_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(VEHICLE_CONFIGURATION_CONFIG, VEHICLE_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(ISSUES_CONFIGURATION_CONFIG, ISSUES_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(TYRE_INVENTORY_CONFIG, TYRE_INVENTORY_CONFIG_NAME);
		moduleWiseNameHM.put(TRIP_COLLECTION_CONFIG, TRIP_COLLECTION_CONFIG_NAME);
		moduleWiseNameHM.put(RENEWAL_REMINDER_CONFIG, RENEWAL_REMINDER_CONFIG_NAME);
		moduleWiseNameHM.put(CASHBOOK_CONFIG, CASHBOOK_CONFIG_NAME);
		moduleWiseNameHM.put(LHPV_INTEGRATION_CONFIG, LHPV_INTEGRATION_CONFIG_NAME);
		moduleWiseNameHM.put(VEHICLE_GPS_CONFIG, VEHICLE_GPS_CONFIG_NAME);
		moduleWiseNameHM.put(SERVICE_REMINDER_CONFIG, SERVICE_REMINDER_CONFIG_NAME);
		moduleWiseNameHM.put(SMS_CONFIG, SMS_CONFIG_NAME);
		moduleWiseNameHM.put(UPHOLSTERY_CONFIG, UPHOLSTERY_CONFIG_NAME);
		moduleWiseNameHM.put(VENDOR_CONFIGURATION_CONFIG, VENDOR_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(TRIP_ROUTE_CONFIGURATION_CONFIG, TRIP_ROUTE_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(MOBILE_APP_VERSION_CHECK_CONFIG, MOBILE_APP_VERSION_CHECK_CONFIG_NAME);
		moduleWiseNameHM.put(FUEL_PRICE_CONFIG, FUEL_PRICE_CONFIG_NAME);
		moduleWiseNameHM.put(BUS_BOOKING_CONFIGURATION_CONFIG, BUS_BOOKING_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(USER_CONFIGURATION_CONFIG, USER_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(VEHICLE_REPORT_CONFIGURATION_CONFIG, VEHICLE_REPORT_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(PICK_DROP_CONFIGURATION_CONFIG, PICK_DROP_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(BATTERY_CONFIGURATION_CONFIG, BATTERY_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(CLOTH_CONFIGURATION_CONFIG, CLOTH_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(UREA_CONFIGURATION_CONFIG, UREA_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(SERVICE_PROGRAM_CONFIGURATION_CONFIG, SERVICE_PROGRAM_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(PROFIT_AND_LOSS_REPORT_CONFIGURATION_CONFIG, PROFIT_AND_LOSS_REPORT_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(FUEL_INVOICE_CONFIG, FUEL_INVOICE_CONFIG_NAME);
		moduleWiseNameHM.put(DSE_CONFIGURATION_CONFIG, DSE_CONFIGURATION_CONFIG_NAME);
		moduleWiseNameHM.put(INSPECTION_CONFIG, INSPECTION_CONFIG_NAME);
		moduleWiseNameHM.put(REPAIR_CONFIG, REPAIR_CONFIG_NAME);
		moduleWiseNameHM.put(REQUISITION_CONFIG, REQUISITION_CONFIG_NAME);
		moduleWiseNameHM.put(LORRY_HIRE_CONFIG, LORRY_HIRE_CONFIG_NAME);
		moduleWiseNameHM.put(TRIPSHEET_REPORT_CONFIGURATION_CONFIG, TRIPSHEET_REPORT_CONFIGURATION_CONFIG_NAME);
	}
	
	static{
		moduleWisePathHM  = new HashMap<>();
		
		moduleWisePathHM.put(COMPANY_CONFIGURATION_CONFIG, COMPANY_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(FUEL_CONFIGURATION_CONFIG, FUEL_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(WORK_ORDERS_CONFIGURATION_CONFIG, WORK_ORDERS_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(TRIPSHEET_CONFIGURATION_CONFIG, TRIPSHEET_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(SERVICE_ENTRIES_CONFIGURATION_CONFIG, SERVICE_ENTRIES_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(PURCHASE_ORDER_CONFIG, PURCHASE_ORDER_CONFIG_PATH);
		moduleWisePathHM.put(TOLL_API_CONFIG, TOLL_API_CONFIG_PATH);
		moduleWisePathHM.put(DRIVER_CONFIGURATION_CONFIG, DRIVER_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(PENALTY_CONFIGURATION_CONFIG,PENALTY_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(MASTER_CONFIGURATION_CONFIG, MASTER_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(CALENDER_BREAK_DOWN_CONFIGURATION_CONFIG, CALENDER_BREAK_DOWN_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(VENDOR_TYPE_CONFIGURATION_CONFIG, VENDOR_TYPE_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(PART_INVENTORY_CONFIGURATION_CONFIG, PART_INVENTORY_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(MASTER_PARTS_CONFIGURATION_CONFIG,  MASTER_PARTS_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(VEHICLE_CONFIGURATION_CONFIG, VEHICLE_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(ISSUES_CONFIGURATION_CONFIG, ISSUES_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(TYRE_INVENTORY_CONFIG, TYRE_INVENTORY_CONFIG_PATH);
		moduleWisePathHM.put(TRIP_COLLECTION_CONFIG, TRIP_COLLECTION_CONFIG_PATH);
		moduleWisePathHM.put(RENEWAL_REMINDER_CONFIG, RENEWAL_REMINDER_CONFIG_PATH);
		moduleWisePathHM.put(CASHBOOK_CONFIG, CASHBOOK_CONFIG_PATH);
		moduleWisePathHM.put(LHPV_INTEGRATION_CONFIG, LHPV_INTEGRATION_CONFIG_PATH);
		moduleWisePathHM.put(VEHICLE_GPS_CONFIG, VEHICLE_GPS_CONFIG_PATH);
		moduleWisePathHM.put(SERVICE_REMINDER_CONFIG, SERVICE_REMINDER_CONFIG_PATH);
		moduleWisePathHM.put(SMS_CONFIG, SMS_CONFIG_PATH);
		moduleWisePathHM.put(UPHOLSTERY_CONFIG, UPHOLSTERY_CONFIG_PATH);
		moduleWisePathHM.put(VENDOR_CONFIGURATION_CONFIG, VENDOR_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(TRIP_ROUTE_CONFIGURATION_CONFIG, TRIP_ROUTE_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(MOBILE_APP_VERSION_CHECK_CONFIG, MOBILE_APP_VERSION_CHECK_CONFIG_PATH);
		moduleWisePathHM.put(FUEL_PRICE_CONFIG, FUEL_PRICE_CONFIG_PATH);
		moduleWisePathHM.put(BUS_BOOKING_CONFIGURATION_CONFIG, BUS_BOOKING_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(USER_CONFIGURATION_CONFIG, USER_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(VEHICLE_REPORT_CONFIGURATION_CONFIG, VEHICLE_REPORT_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(PICK_DROP_CONFIGURATION_CONFIG, PICK_DROP_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(BATTERY_CONFIGURATION_CONFIG, BATTERY_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(CLOTH_CONFIGURATION_CONFIG, CLOTH_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(UREA_CONFIGURATION_CONFIG, UREA_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(SERVICE_PROGRAM_CONFIGURATION_CONFIG, SERVICE_PROGRAM_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(PROFIT_AND_LOSS_REPORT_CONFIGURATION_CONFIG, PROFIT_AND_LOSS_REPORT_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(FUEL_INVOICE_CONFIG, FUEL_INVOICE_CONFIG_PATH);
		moduleWisePathHM.put(DSE_CONFIGURATION_CONFIG, DSE_CONFIGURATION_CONFIG_PATH);
		moduleWisePathHM.put(INSPECTION_CONFIG, INSPECTION_CONFIG_PATH);
		moduleWisePathHM.put(REPAIR_CONFIG, REPAIR_CONFIG_PATH);
		moduleWisePathHM.put(REQUISITION_CONFIG, REQUISITION_CONFIG_PATH);
		moduleWisePathHM.put(LORRY_HIRE_CONFIG, LORRY_HIRE_CONFIG_PATH);
		moduleWisePathHM.put(TRIPSHEET_REPORT_CONFIGURATION_CONFIG, TRIPSHEET_REPORT_CONFIGURATION_CONFIG_PATH);
	}
	
    public static HashMap<Integer, String>  getModulesListHM() {
 		
		return moduleWiseNameHM;
	}
	
	public static String  getModulesPathById(Integer moduleId) {
		
		return moduleWisePathHM.get(moduleId);
	}

}
