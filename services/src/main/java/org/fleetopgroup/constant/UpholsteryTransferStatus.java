package org.fleetopgroup.constant;

public class UpholsteryTransferStatus {
	
	public static final short TRANSFER_STATUS_TRANSFERED		= 1;
	public static final short TRANSFER_STATUS_RECEIVED			= 2;
	public static final short TRANSFER_STATUS_REJECTED			= 3;
	
	public static final String TRANSFER_STATUS_TRANSFERED_NAME		= "TRANSFERED";
	public static final String TRANSFER_STATUS_RECEIVED_NAME		= "RECEIVED";
	public static final String TRANSFER_STATUS_REJECTED_NAME		= "REJECTED";
	
	
	public static final short TRANSFER_VIA_AIR			= 1;
	public static final short TRANSFER_VIA_COURIER		= 2;
	public static final short TRANSFER_VIA_EXPEDITED	= 3;
	public static final short TRANSFER_VIA_GROUND		= 4;
	public static final short TRANSFER_VIA_NEXT_DAY		= 5;
	public static final short TRANSFER_VIA_NONE			= 6;
	
	public static final String TRANSFER_VIA_AIR_NAME		= "AIR";
	public static final String TRANSFER_VIA_COURIER_NAME	= "COURIER";
	public static final String TRANSFER_VIA_EXPEDITED_NAME	= "EXPEDITED";
	public static final String TRANSFER_VIA_GROUND_NAME		= "GROUND";
	public static final String TRANSFER_VIA_NEXT_DAY_NAME	= "NEXT DAY";
	public static final String TRANSFER_VIA_NONE_NAME		= "NONE";
	
	
	public static String getStatusName(short status) {

		String statusName		=	null;
		switch (status) {
		  case TRANSFER_STATUS_TRANSFERED:
			  statusName	= TRANSFER_STATUS_TRANSFERED_NAME;
		        break;
		  case TRANSFER_STATUS_RECEIVED: 
			  statusName	= TRANSFER_STATUS_RECEIVED_NAME;
		        break;
		  case TRANSFER_STATUS_REJECTED: 
			  statusName	= TRANSFER_STATUS_REJECTED_NAME;
			  break;
		
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	}
	
	public static String getTransferViaName(short status) {

		String statusName		=	null;
		switch (status) {
		  case TRANSFER_VIA_AIR:
			  statusName	= TRANSFER_VIA_AIR_NAME;
		        break;
		  case TRANSFER_VIA_COURIER: 
			  statusName	= TRANSFER_VIA_COURIER_NAME;
		        break;
		  case TRANSFER_VIA_EXPEDITED: 
			  statusName	= TRANSFER_VIA_EXPEDITED_NAME;
		        break;
		  case TRANSFER_VIA_GROUND: 
			  statusName	= TRANSFER_VIA_GROUND_NAME;
		        break;
		  case TRANSFER_VIA_NEXT_DAY: 
			  statusName	= TRANSFER_VIA_NEXT_DAY_NAME;
		        break;
		  case TRANSFER_VIA_NONE: 
			  statusName	= TRANSFER_VIA_NONE_NAME;
		        break;
		
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	}
	
}