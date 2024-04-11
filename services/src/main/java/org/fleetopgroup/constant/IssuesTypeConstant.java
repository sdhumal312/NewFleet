package org.fleetopgroup.constant;

import java.io.Serializable;
import java.util.ArrayList;

import org.fleetopgroup.persistence.dto.IssuesTypeDto;

public class IssuesTypeConstant implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final short ISSUE_TYPE_VEHICLE			= 1;
	public static final short ISSUE_TYPE_DRIVER				= 2;
	public static final short ISSUE_TYPE_REFUND				= 3;
	public static final short ISSUE_TYPE_CUSTOMER			= 4;
	public static final short ISSUE_TYPE_OTHER				= 5;
	public static final short ISSUE_TYPE_BREAKDOWN			= 6;
	
	public static final String ISSUE_TYPE_VEHICLE_NAME 		= "Vehicle Issue";
	public static final String ISSUE_TYPE_DRIVER_NAME 		= "Driver Issue";
	public static final String ISSUE_TYPE_REFUND_NAME 		= "Refund Issue";
	public static final String ISSUE_TYPE_CUSTOMER_NAME 	= "Customer Issue";
	public static final String ISSUE_TYPE_OTHER_NAME 		= "Other Issue";
	public static final String ISSUE_TYPE_BREAKDOWN_NAME 	= "Vehicle BreakDown";
	
	public static final short ISSUES_STATUS_OPEN				= 1;
	public static final short ISSUES_STATUS_CLOSED				= 2;
	public static final short ISSUES_STATUS_WOCREATED			= 3;
	public static final short ISSUES_STATUS_RESOLVED			= 4;
	public static final short ISSUES_STATUS_REJECT				= 5;
	public static final short ISSUES_STATUS_SE_CREATED			= 6;
	public static final short ISSUES_STATUS_DSE_CREATED			= 7;
	
	public static final String ISSUES_STATUS_OPEN_NAME 				= "OPEN";
	public static final String ISSUES_STATUS_CLOSED_NAME 			= "CLOSED";
	public static final String ISSUES_STATUS_WOCREATED_NAME 		= "WOCREATED";
	public static final String ISSUES_STATUS_RESOLVED_NAME 			= "RESOLVED";
	public static final String ISSUES_STATUS_REJECT_NAME 			= "REJECT";
	public static final String ISSUES_STATUS_SE_CREATED_NAME 		= "SE_CREATED";
	public static final String ISSUES_STATUS_DSE_CREATED_NAME 		= "DSE_CREATED";
	
	
	public static final short ISSUES_TASK_STATUS_CREATE			= 0;
	public static final short ISSUES_TASK_STATUS_OPEN			= 1;
	public static final short ISSUES_TASK_STATUS_CLOSED			= 2;
	public static final short ISSUES_TASK_STATUS_WOCREATED		= 3;
	public static final short ISSUES_TASK_STATUS_RESOLVED		= 4;
	public static final short ISSUES_TASK_STATUS_REJECT			= 5;
	public static final short ISSUES_TASK_STATUS_SE_CREATED		= 6;
	public static final short ISSUES_TASK_STATUS_DSE_CREATED	= 7;
	public static final short ISSUES_TASK_STATUS_TYRE_ASSIGNMENT	= 8;
	public static final short ISSUES_TASK_STATUS_TYPE_CHANGED	= 9;
	
	
	public static final String ISSUES_TASK_STATUS_CREATE_NAME		= "CREATE";
	public static final String ISSUES_TASK_STATUS_OPEN_NAME			= "OPEN";
	public static final String ISSUES_TASK_STATUS_CLOSED_NAME		= "CLOSED";
	public static final String ISSUES_TASK_STATUS_WOCREATED_NAME 	= "WOCREATED";
	public static final String ISSUES_TASK_STATUS_RESOLVED_NAME		= "RESOLVED";
	public static final String ISSUES_TASK_STATUS_REJECT_NAME		= "REJECT";
	public static final String ISSUES_TASK_STATUS_SE_CREATED_NAME 	= "SE_CREATED";
	public static final String ISSUES_TASK_STATUS_DSE_CREATED_NAME 	= "DSE_CREATED";
	public static final String ISSUES_TASK_STATUS_TYRE_ASSIGNMENT_NAME 	= "TYRE_ASSIGNMENT";
	public static final String ISSUES_TASK_STATUS_TYPE_CHANGED_NAME = "TYPE_CHANGED";
	
	
	public static final short ISSUES_CHANGE_STATUS_OPEN				= 1;
	public static final short ISSUES_CHANGE_STATUS_CLOSED			= 2;
	public static final short ISSUES_CHANGE_STATUS_WOCREATED		= 3;
	public static final short ISSUES_CHANGE_STATUS_RESOLVED			= 4;
	public static final short ISSUES_CHANGE_STATUS_REJECT			= 5;
	public static final short ISSUES_CHANGE_STATUS_SE_CREATED		= 6;
	public static final short ISSUES_CHANGE_STATUS_DSE_CREATED		= 7;
	public static final short ISSUES_CHANGE_STATUS_TYRE_ASSIGNMENT		= 8;
	public static final short ISSUES_CHANGE_STATUS_TO_BREAKDOWN		= 9;
	
	public static final String  ISSUES_CHANGE_STATUS_OPEN_NAME			= "OPEN";
	public static final String  ISSUES_CHANGE_STATUS_RESOLVED_NAME		= "RESOLVED";
	public static final String  ISSUES_CHANGE_STATUS_CLOSED_NAME		= "CLOSED";
	public static final String  ISSUES_CHANGE_STATUS_REJECT_NAME		= "REJECT";
	public static final String  ISSUES_CHANGE_STATUS_WOCREATED_NAME		= "WOCREATED";
	public static final String  ISSUES_CHANGE_STATUS_SE_CREATED_NAME	= "SE_CREATED";
	public static final String  ISSUES_CHANGE_STATUS_DSE_CREATED_NAME	= "DSE_CREATED";
	public static final String  ISSUES_CHANGE_STATUS_TYRE_ASSIGNMENT_NAME	= "TYRE_ASSIGNMENT";
	public static final String  ISSUES_CHANGE_STATUS_TO_BREAKDOWN_NAME	= "Type Changed";
	
	public static final short 	ISSUES_LABELS_NORMAL		= 1;
	public static final short 	ISSUES_LABELS_HIGH			= 2;
	public static final short	ISSUES_LABELS_LOW			= 3;
	public static final short	ISSUES_LABELS_URGENT		= 4;
	public static final short	ISSUES_LABELS_VERY_URGENT	= 5;
	public static final short	ISSUES_LABELS_BREAKDOWN		= 6;
	
	public static final String	ISSUES_LABELS_NORMAL_NAME			= "NORMAL";
	public static final String	ISSUES_LABELS_HIGH_NAME				= "HIGH";
	public static final String	ISSUES_LABELS_LOW_NAME				= "LOW";
	public static final String	ISSUES_LABELS_URGENT_NAME			= "URGENT";
	public static final String	ISSUES_LABELS_VERY_URGENT_NAME		= "VERY URGENT";
	public static final String	ISSUES_LABELS_BREAKDOWN_NAME		= "BREAKDOWN";
	
	
	
	
	public static String getIssueTypeName(short status) throws Exception{
		String statusString		=	null;
		switch (status) {
		  case ISSUE_TYPE_VEHICLE:
			  statusString	= ISSUE_TYPE_VEHICLE_NAME;
		        break;
		  case ISSUE_TYPE_DRIVER: 
			  statusString	= ISSUE_TYPE_DRIVER_NAME;
		        break;
		  case ISSUE_TYPE_REFUND: 
			  statusString	= ISSUE_TYPE_REFUND_NAME;
		        break;
		  case ISSUE_TYPE_CUSTOMER: 
			  statusString	= ISSUE_TYPE_CUSTOMER_NAME;
		        break;
		  case ISSUE_TYPE_OTHER: 
			  statusString	= ISSUE_TYPE_OTHER_NAME;
		        break;
		  case ISSUE_TYPE_BREAKDOWN: 
			  statusString	= ISSUE_TYPE_BREAKDOWN_NAME;
		        break;
		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	}

	public static ArrayList<IssuesTypeDto> getIssuesTypeList() throws Exception{
		ArrayList<IssuesTypeDto>		issuesTypeDtoList	= null;
		try {
				issuesTypeDtoList	= new ArrayList<>();
				issuesTypeDtoList.add(new IssuesTypeDto(ISSUE_TYPE_VEHICLE, ISSUE_TYPE_VEHICLE_NAME));
				issuesTypeDtoList.add(new IssuesTypeDto(ISSUE_TYPE_DRIVER, ISSUE_TYPE_DRIVER_NAME));
				issuesTypeDtoList.add(new IssuesTypeDto(ISSUE_TYPE_REFUND, ISSUE_TYPE_REFUND_NAME));
				issuesTypeDtoList.add(new IssuesTypeDto(ISSUE_TYPE_CUSTOMER, ISSUE_TYPE_CUSTOMER_NAME));
				issuesTypeDtoList.add(new IssuesTypeDto(ISSUE_TYPE_OTHER, ISSUE_TYPE_OTHER_NAME));
				issuesTypeDtoList.add(new IssuesTypeDto(ISSUE_TYPE_BREAKDOWN, ISSUE_TYPE_BREAKDOWN_NAME));
				
			return issuesTypeDtoList;
		} catch (Exception e) {
			throw e;
		}finally {
			issuesTypeDtoList	= null;
		}
	}
	
	public static ArrayList<IssuesTypeDto> getIssuesTypeListWithoutCustomerIssue() throws Exception{
		ArrayList<IssuesTypeDto>		issuesTypeDtoList	= null;
		try {
				issuesTypeDtoList	= new ArrayList<>();
				issuesTypeDtoList.add(new IssuesTypeDto(ISSUE_TYPE_VEHICLE, ISSUE_TYPE_VEHICLE_NAME));
				issuesTypeDtoList.add(new IssuesTypeDto(ISSUE_TYPE_DRIVER, ISSUE_TYPE_DRIVER_NAME));
				issuesTypeDtoList.add(new IssuesTypeDto(ISSUE_TYPE_REFUND, ISSUE_TYPE_REFUND_NAME));
				issuesTypeDtoList.add(new IssuesTypeDto(ISSUE_TYPE_OTHER, ISSUE_TYPE_OTHER_NAME));
				issuesTypeDtoList.add(new IssuesTypeDto(ISSUE_TYPE_BREAKDOWN, ISSUE_TYPE_BREAKDOWN_NAME));
				
			return issuesTypeDtoList;
		} catch (Exception e) {
			throw e;
		}finally {
			issuesTypeDtoList	= null;
		}
	}
	
	
	public static String getStatusName(short status) {

		String statusName		=	null;
		switch (status) {
		  case ISSUES_STATUS_OPEN:
			  statusName	= ISSUES_STATUS_OPEN_NAME;
		        break;
		  case ISSUES_STATUS_CLOSED: 
			  statusName	= ISSUES_STATUS_CLOSED_NAME;
		        break;
		  case ISSUES_STATUS_WOCREATED: 
			  statusName	= ISSUES_STATUS_WOCREATED_NAME;
		        break;
		  case ISSUES_STATUS_RESOLVED: 
			  statusName	= ISSUES_STATUS_RESOLVED_NAME;
		        break;
		  case ISSUES_STATUS_REJECT: 
			  statusName	= ISSUES_STATUS_REJECT_NAME;
		        break;
		  case ISSUES_STATUS_SE_CREATED: 
			  statusName	= ISSUES_STATUS_SE_CREATED_NAME;
			  break;
		  case ISSUES_STATUS_DSE_CREATED: 
			  statusName	= ISSUES_STATUS_DSE_CREATED_NAME;
			  break;
		
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	}
	
	public static String getIssuesTaskStatusName(short status) {

		String statusName		=	null;
		switch (status) {
		  case ISSUES_TASK_STATUS_CREATE:
			  statusName	= ISSUES_TASK_STATUS_CREATE_NAME;
		        break;
		  case ISSUES_TASK_STATUS_OPEN: 
			  statusName	= ISSUES_TASK_STATUS_OPEN_NAME;
		        break;
		  case ISSUES_TASK_STATUS_RESOLVED: 
			  statusName	= ISSUES_TASK_STATUS_RESOLVED_NAME;
		        break;
		  case ISSUES_TASK_STATUS_CLOSED: 
			  statusName	= ISSUES_TASK_STATUS_CLOSED_NAME;
		        break;
		  case ISSUES_TASK_STATUS_WOCREATED: 
			  statusName	= ISSUES_TASK_STATUS_WOCREATED_NAME;
		        break;
		  case ISSUES_TASK_STATUS_REJECT: 
			  statusName	= ISSUES_TASK_STATUS_REJECT_NAME;
		        break;
		  case ISSUES_TASK_STATUS_SE_CREATED: 
			  statusName	= ISSUES_TASK_STATUS_SE_CREATED_NAME;
			  break;
		  case ISSUES_TASK_STATUS_DSE_CREATED: 
			  statusName	= ISSUES_TASK_STATUS_DSE_CREATED_NAME;
			  break;
			  
		  case ISSUES_TASK_STATUS_TYPE_CHANGED: 
			  statusName	= ISSUES_TASK_STATUS_TYPE_CHANGED_NAME;
			  break;
		
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	}
	
	
	public static String getIssuesTaskChangeStatusName(short status) {

		String statusName		=	null;
		switch (status) {
		  case ISSUES_CHANGE_STATUS_OPEN:
			  statusName	= ISSUES_CHANGE_STATUS_OPEN_NAME;
		        break;
		  case ISSUES_CHANGE_STATUS_RESOLVED: 
			  statusName	= ISSUES_CHANGE_STATUS_RESOLVED_NAME;
		        break;
		  case ISSUES_CHANGE_STATUS_CLOSED: 
			  statusName	= ISSUES_CHANGE_STATUS_CLOSED_NAME;
		        break;
		  case ISSUES_CHANGE_STATUS_REJECT: 
			  statusName	= ISSUES_CHANGE_STATUS_REJECT_NAME;
		        break;
		  case ISSUES_CHANGE_STATUS_WOCREATED: 
			  statusName	= ISSUES_CHANGE_STATUS_WOCREATED_NAME;
		        break;
		  case ISSUES_CHANGE_STATUS_SE_CREATED: 
			  statusName	= ISSUES_CHANGE_STATUS_SE_CREATED_NAME;
			  break;
		  case ISSUES_CHANGE_STATUS_DSE_CREATED: 
			  statusName	= ISSUES_CHANGE_STATUS_DSE_CREATED_NAME;
			  break;
		  case ISSUES_CHANGE_STATUS_TO_BREAKDOWN: 
			  statusName	= ISSUES_CHANGE_STATUS_TO_BREAKDOWN_NAME;
			  break;	  
		
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	}
	
	public static String getIssuesLabelName(short status) {

		String statusName		=	null;
		switch (status) {
		  case ISSUES_LABELS_NORMAL:
			  statusName	= ISSUES_LABELS_NORMAL_NAME;
		        break;
		  case ISSUES_LABELS_HIGH: 
			  statusName	= ISSUES_LABELS_HIGH_NAME;
		        break;
		  case ISSUES_LABELS_LOW: 
			  statusName	= ISSUES_LABELS_LOW_NAME;
		        break;
		  case ISSUES_LABELS_URGENT: 
			  statusName	= ISSUES_LABELS_URGENT_NAME;
		        break;
		  case ISSUES_LABELS_VERY_URGENT: 
			  statusName	= ISSUES_LABELS_VERY_URGENT_NAME;
		        break;
		  case ISSUES_LABELS_BREAKDOWN: 
			  statusName	= ISSUES_LABELS_BREAKDOWN_NAME;
			  break;
		 
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	
	}

}
