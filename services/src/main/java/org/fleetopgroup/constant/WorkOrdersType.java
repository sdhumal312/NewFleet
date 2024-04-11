package org.fleetopgroup.constant;

public class WorkOrdersType {

	public static final short WORKORDERS_STATUS_OPEN = 1;
	public static final short WORKORDERS_STATUS_INPROCESS = 2;
	public static final short WORKORDERS_STATUS_ONHOLD = 3;
	public static final short WORKORDERS_STATUS_COMPLETE = 4;

	public static final String WORKORDERS_STATUS_OPEN_NAME = "OPEN";
	public static final String WORKORDERS_STATUS_INPROCESS_NAME = "INPROCESS";
	public static final String WORKORDERS_STATUS_ONHOLD_NAME = "ONHOLD";
	public static final String WORKORDERS_STATUS_COMPLETE_NAME = "COMPLETE";

	public static final short WORKORDER_PRIORITY_NORMAL = 1;
	public static final short WORKORDER_PRIORITY_HIGH = 2;
	public static final short WORKORDER_PRIORITY_LOW = 3;
	public static final short WORKORDER_PRIORITY_URGENT = 4;
	public static final short WORKORDER_PRIORITY_VERY_URGENT = 5;

	public static final String WORKORDER_PRIORITY_NORMAL_NAME = "NORMAL";
	public static final String WORKORDER_PRIORITY_HIGH_NAME = "HIGH";
	public static final String WORKORDER_PRIORITY_LOW_NAME = "LOW";
	public static final String WORKORDER_PRIORITY_URGENT_NAME = "URGENT";
	public static final String WORKORDER_PRIORITY_VERY_URGENT_NAME = "VERY URGENT";
	public static final String WORK_ORDER_STATUS                   = "workOrderStatus";

	
	public static final short ISSSUES_REGARDING_WO    = 1;
	public static final short ISSSUES_REGARDING_DSE   = 2;
	public static final short SERVICE_REMINDER_WO     = 3;
	public static final short SERVICE_REMINDER_DSE    = 4;
	public static final short SERVICE_REMINDER_SE    =  5;
	
	public WorkOrdersType() {
		super();
	}

	public static String getStatusName(short status) {

		String statusName = null;
		switch (status) {
		case WORKORDERS_STATUS_OPEN:
			statusName = WORKORDERS_STATUS_OPEN_NAME;
			break;
		case WORKORDERS_STATUS_INPROCESS:
			statusName = WORKORDERS_STATUS_INPROCESS_NAME;
			break;
		case WORKORDERS_STATUS_ONHOLD:
			statusName = WORKORDERS_STATUS_ONHOLD_NAME;
			break;
		case WORKORDERS_STATUS_COMPLETE:
			statusName = WORKORDERS_STATUS_COMPLETE_NAME;
			break;

		default:
			statusName = "--";
			break;
		}
		return statusName;
	}

	public static String getPriorityName(short status) {

		String statusName = null;
		switch (status) {
		case WORKORDER_PRIORITY_NORMAL:
			statusName = WORKORDER_PRIORITY_NORMAL_NAME;
			break;
		case WORKORDER_PRIORITY_HIGH:
			statusName = WORKORDER_PRIORITY_HIGH_NAME;
			break;
		case WORKORDER_PRIORITY_LOW:
			statusName = WORKORDER_PRIORITY_LOW_NAME;
			break;
		case WORKORDER_PRIORITY_URGENT:
			statusName = WORKORDER_PRIORITY_URGENT_NAME;
			break;
		case WORKORDER_PRIORITY_VERY_URGENT:
			statusName = WORKORDER_PRIORITY_VERY_URGENT_NAME;
			break;

		default:
			statusName = "--";
			break;
		}
		return statusName;

	}
}
