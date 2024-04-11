package org.fleetopgroup.constant;

public class DriverStatus {

	public static final short DRIVER_STATUS_ACTIVE 		= 1;
	public static final short DRIVER_STATUS_INACTIVE 	= 2;
	public static final short DRIVER_STATUS_TRIPROUTE 	= 3;
	public static final short DRIVER_STATUS_LOCAL_HALT 	= 4;
	public static final short DRIVER_STATUS_TRIP_HALT 	= 5;
	public static final short DRIVER_STATUS_SUSPEND 	= 6;
	public static final short DRIVER_STATUS_HOLD 		= 7;
	public static final short DRIVER_STATUS_RESIGN 		= 8;

	public static final String DRIVER_STATUS_ACTIVE_NAME 			= "ACTIVE";
	public static final String DRIVER_STATUS_INACTIVE_NAME 			= "INACTIVE";
	public static final String DRIVER_STATUS_TRIPROUTE_NAME 		= "TRIPROUTE";
	public static final String DRIVER_STATUS_LOCAL_HALT_NAME 		= "LOCAL_HALT";
	public static final String DRIVER_STATUS_TRIP_HALT_NAME 		= "TRIP_HALT";
	public static final String DRIVER_STATUS_SUSPEND_NAME 			= "SUSPEND";
	public static final String DRIVER_STATUS_HOLD_NAME 				= "HOLD";
	public static final String DRIVER_STATUS_RESIGN_NAME 			= "RESIGN";

	public static final short DRIVER_SALARY_TYPE_PER_DAY 		= 1;
	public static final short DRIVER_SALARY_TYPE_PER_TRIP 		= 2;
	public static final short DRIVER_SALARY_TYPE_PER_KM 		= 3;
	
	public static final String DRIVER_SALARY_TYPE_PER_DAY_STRING 			= "PER DAY WISE SALARY";
	public static final String DRIVER_SALARY_TYPE_PER_TRIP_STRING 			= "TRIP WISE SALARY";
	public static final String DRIVER_SALARY_TYPE_PER_KM_STRING 			= "KM WISE SALARY";

	public static String getDriverStatus(short status) throws Exception {
		String statusString = null;
		switch (status) {
		case DRIVER_STATUS_ACTIVE:
			statusString = DRIVER_STATUS_ACTIVE_NAME;
			break;
		case DRIVER_STATUS_INACTIVE:
			statusString = DRIVER_STATUS_INACTIVE_NAME;
			break;
		case DRIVER_STATUS_TRIPROUTE:
			statusString = DRIVER_STATUS_TRIPROUTE_NAME;
			break;
		case DRIVER_STATUS_LOCAL_HALT:
			statusString = DRIVER_STATUS_LOCAL_HALT_NAME;
			break;
		case DRIVER_STATUS_TRIP_HALT:
			statusString = DRIVER_STATUS_TRIP_HALT_NAME;
			break;
		case DRIVER_STATUS_SUSPEND:
			statusString = DRIVER_STATUS_SUSPEND_NAME;
			break;
		case DRIVER_STATUS_HOLD:
			statusString =DRIVER_STATUS_HOLD_NAME;
			break;
		case DRIVER_STATUS_RESIGN:
			statusString=DRIVER_STATUS_RESIGN_NAME;
			break;
			
		default:
			statusString = "--";
			break;
		}
		return statusString;
	}

	public static String getDriverSalaryType(short driverSalaryTypeId) throws Exception {
		String driverSalaryType = null;
		switch (driverSalaryTypeId) {
		case DRIVER_SALARY_TYPE_PER_DAY:
			driverSalaryType = DRIVER_SALARY_TYPE_PER_DAY_STRING;
			break;
		case DRIVER_SALARY_TYPE_PER_TRIP:
			driverSalaryType = DRIVER_SALARY_TYPE_PER_TRIP_STRING;
			break;
		case DRIVER_SALARY_TYPE_PER_KM:
			driverSalaryType = DRIVER_SALARY_TYPE_PER_KM_STRING;
			break;
		default:
			driverSalaryType = "--";
			break;
		}
		return driverSalaryType;
	}
}
