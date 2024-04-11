package org.fleetopgroup.constant;

public class ServiceReminderType {

	public static final short SERVICE_TYPE_OPTIONAL		= 1;
	public static final short SERVICE_TYPE_MANDATORY	= 2;
	
	public static final String SERVICE_TYPE_OPTIONAL_NAME 	= "OPTIONAL";
	public static final String SERVICE_TYPE_MANDATORY_NAME 	= "MANDATORY";
	
	
	public static String getServiceReminderType(short status) throws Exception {
		String statusString = null;
		switch (status) {
		case SERVICE_TYPE_OPTIONAL:
			statusString = SERVICE_TYPE_OPTIONAL_NAME;
			break;
		case SERVICE_TYPE_MANDATORY:
			statusString = SERVICE_TYPE_MANDATORY_NAME;
			break;
		

		default:
			statusString = "--";
			break;
		}
		return statusString;
	}
}
