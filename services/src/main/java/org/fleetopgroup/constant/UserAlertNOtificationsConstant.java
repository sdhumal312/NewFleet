package org.fleetopgroup.constant;

public class UserAlertNOtificationsConstant {

	public static final short ALERT_TYPE_PART_REQUISITION	= 1;
	public static final short ALERT_TYPE_ISSUE_NOTIFICATION	= 2;
	public static final short ALERT_TYPE_REQUISITION	= 3;
	public static final short ALERT_TYPE_RENWAL_REMINDER	= 4;
	
	public static final String ALERT_TYPE_PART_REQUISITION_NAME  	= "Part Requisition";
	public static final String ALERT_TYPE_ISSUE_NOTIFICATION_NAME	= "Issue Notification";
	public static final String ALERT_TYPE_REQUISITION_NAME  		= "Requisition Notification";
	public static final String ALERT_TYPE_RENWAL_REMINDER_NAME      = "Renewal Reminder Notification";
	
	
	public static final short NOTIFICATION_STATUS_UNREAD	= 1;
	public static final short NOTIFICATION_STATUS_READ		= 2;
	
	public static final String NOTIFICATION_STATUS_UNREAD_NAME 	= "Unread";
	public static final String NOTIFICATION_STATUS_READ_NAME 	= "Read";
	
	
	public static String getAlertTypeName(short partTypeId) {

		String partTypeName		=	null;
		switch (partTypeId) {
		  case ALERT_TYPE_PART_REQUISITION:
			  partTypeName	= ALERT_TYPE_PART_REQUISITION_NAME;
		        break;
		  case ALERT_TYPE_ISSUE_NOTIFICATION:
			  partTypeName	= ALERT_TYPE_ISSUE_NOTIFICATION_NAME;
			  break;
		  case ALERT_TYPE_REQUISITION:
			  partTypeName	= ALERT_TYPE_REQUISITION_NAME;
			  break;
		  case ALERT_TYPE_RENWAL_REMINDER:
			  partTypeName = ALERT_TYPE_RENWAL_REMINDER_NAME;
		  default:
			  partTypeName = "--";
		        break;
		}
		return partTypeName;
	
	}
	
	public static String getAlertStatusName(short partTypeId) {

		String partTypeName		=	null;
		switch (partTypeId) {
		  case NOTIFICATION_STATUS_UNREAD:
			  partTypeName	= NOTIFICATION_STATUS_UNREAD_NAME;
		        break;
		  case NOTIFICATION_STATUS_READ:
			  partTypeName	= NOTIFICATION_STATUS_READ_NAME;
		        break;
		
		  default:
			  partTypeName = "--";
		        break;
		}
		return partTypeName;
	
	}
	
}
