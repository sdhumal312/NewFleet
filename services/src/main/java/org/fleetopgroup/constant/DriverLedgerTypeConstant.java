package org.fleetopgroup.constant;

public class DriverLedgerTypeConstant {

	public static final short TRIPSHEET_ADVANCE							= 1;
	public static final short TRIPSHEET_EXPENSE							= 2;
	public static final short TRIPSHEET_CLOSE_PAID_BY_DRIVER			= 3;
	public static final short TRIPSHEET_CLOSE_PAID_TO_DRIVER			= 4;
	public static final short DRIVER_PENALTY							= 5;
	public static final short DRIVER_SALARY								= 6;
	public static final short TRIPSHEET_ADVANCE_REMOVED					= 7;
	public static final short TRIPSHEET_EXPENSE_REMOVED					= 8;
	public static final short FUEL_ENTRY_ADDED							= 9;
	public static final short FUEL_ENTRY_EDIT							= 10;
	public static final short FUEL_ENTRY_DELETE							= 11;
	

	public static final String TRIPSHEET_ADVANCE_NAME						= "TripSheet Advance";
	public static final String TRIPSHEET_EXPENSE_NAME					    = "TripSheet Expense";
	public static final String TRIPSHEET_CLOSE_PAID_BY_DRIVER_NAME			= "TripSheet Closed Paid By Driver";
	public static final String TRIPSHEET_CLOSE_PAID_TO_DRIVER_NAME			= "TripSheet Closed Paid To Driver";
	public static final String DRIVER_PENALTY_NAME							= "Driver Penalty";
	public static final String DRIVER_SALARY_NAME							= "Driver Salary";
	public static final String TRIPSHEET_ADVANCE_REMOVED_NAME				= "TripSheet Advance Removed";
	public static final String TRIPSHEET_EXPENSE_REMOVED_NAME				= "TripSheet Expense Removed";
	public static final String FUEL_ENTRY_ADDED_NAME						= "Fuel Entry Added";
	public static final String FUEL_ENTRY_EDIT_NAME							= "Fuel Entry Edited";
	public static final String FUEL_ENTRY_DELETE_NAME					    = "Fuel Entry Deleted";
	
	public static boolean DriverLedgerFuelConstant(short status){
		if(status == FUEL_ENTRY_ADDED)
			return true;
		else if(status == FUEL_ENTRY_EDIT)
			return true;
		else if(status == FUEL_ENTRY_DELETE)
			return true;
		else
			return false;
	}
	public static boolean DriverLedgerTripConstant(short status){
		if(status == TRIPSHEET_ADVANCE)
			return true;
		else if(status == TRIPSHEET_EXPENSE)
			return true;
		else if(status == TRIPSHEET_CLOSE_PAID_BY_DRIVER)
			return true;
		else if(status == TRIPSHEET_CLOSE_PAID_TO_DRIVER)
			return true;
		else if(status == TRIPSHEET_ADVANCE_REMOVED)
			return true;
		else if(status == TRIPSHEET_EXPENSE_REMOVED)
			return true;
		else
			return false;
	}
	
	public static String DriverLedgerConstatName(short status)
	{
		String statusName = null;
		switch (status){
			case TRIPSHEET_ADVANCE:
				statusName =  TRIPSHEET_ADVANCE_NAME;
				break;
			case TRIPSHEET_EXPENSE:
				statusName = TRIPSHEET_EXPENSE_NAME;
				break;
			case TRIPSHEET_CLOSE_PAID_BY_DRIVER:
				statusName = TRIPSHEET_CLOSE_PAID_BY_DRIVER_NAME;
				break;
			case TRIPSHEET_CLOSE_PAID_TO_DRIVER:
				statusName = TRIPSHEET_CLOSE_PAID_TO_DRIVER_NAME;
				break;
			case DRIVER_PENALTY:
				statusName = DRIVER_PENALTY_NAME;
				break;
			case DRIVER_SALARY:
				statusName =  DRIVER_SALARY_NAME;
				break;
			case TRIPSHEET_ADVANCE_REMOVED:
				statusName = TRIPSHEET_ADVANCE_REMOVED_NAME;
				break;
			case TRIPSHEET_EXPENSE_REMOVED:
				statusName = TRIPSHEET_EXPENSE_REMOVED_NAME;
				break;
			case FUEL_ENTRY_ADDED:
				statusName = FUEL_ENTRY_ADDED_NAME;
				break;
			case FUEL_ENTRY_EDIT:
				statusName = FUEL_ENTRY_EDIT_NAME;
				break;
			case FUEL_ENTRY_DELETE:
				statusName = FUEL_ENTRY_DELETE_NAME;
				break;
			 default:
				  statusName = "--";
			        break;
		}
		return statusName;
	}
}
