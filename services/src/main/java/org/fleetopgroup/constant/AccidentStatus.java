package org.fleetopgroup.constant;

public class AccidentStatus {

	public static final short ACCIDENT_STATUS_INCIDENT_CREATED			= 1;
	public static final short ACCIDENT_STATUS_SERVEYER_DETAILS_UPDATED	= 2;
	public static final short ACCIDENT_STATUS_SERVEY_COMPLETED			= 3;
	public static final short ACCIDENT_STATUS_QUOTATION_CREATED			= 4;
	public static final short ACCIDENT_STATUS_FINAL_SERVEY_FOR_DAMAGE	= 5;
	public static final short ACCIDENT_STATUS_QUOTATION_APPROVED		= 6;
	public static final short ACCIDENT_STATUS_SERVICE_COMPLETED			= 7;
	public static final short ACCIDENT_STATUS_FINAL_SERVEY_DONE			= 8;
	public static final short ACCIDENT_STATUS_PAYMENT_DONE				= 9;
	public static final short ACCIDENT_STATUS_KEEP_OPEN					= 10;
	public static final short ACCIDENT_STATUS_BEFORE_FINAL_APPROVAL		= 11;
	
	
	public static final short SERVICE_TYPE_SE	= 1;
	public static final short SERVICE_TYPE_WO	= 2;
	public static final short SERVICE_TYPE_DSE	= 3;
	
	
	public static final String ACCIDENT_STATUS_INCIDENT_CREATED_NAME			= "Incident Created";
	public static final String ACCIDENT_STATUS_SERVEYER_DETAILS_UPDATED_NAME	= "Serveyer Details Updated";
	public static final String ACCIDENT_STATUS_SERVEY_COMPLETED_NAME			= "Servey Completed";
	public static final String ACCIDENT_STATUS_QUOTATION_CREATED_NAME			= "Quotation/Service Created ";
	public static final String ACCIDENT_STATUS_FINAL_SERVEY_FOR_DAMAGE_NAME		= "Final Damage Servey";
	public static final String ACCIDENT_STATUS_QUOTATION_APPROVED_NAME			= "Quotation Approved";
	public static final String ACCIDENT_STATUS_SERVICE_COMPLETED_NAME			= "Service Completed";
	public static final String ACCIDENT_STATUS_FINAL_SERVEY_DONE_NAME			= "Final Servey Done";
	public static final String ACCIDENT_STATUS_PAYMENT_DONE_NAME				= "Payment Done";
	public static final String ACCIDENT_STATUS_KEEP_OPEN_NAME					= "Keep Open";
	public static final String ACCIDENT_STATUS_BEFORE_FINAL_APPROVAL_NAME		= "Before Final Approval";
	
	
	public static final short DAMAGE_AMOUNT_RECEIVED_STATUS_ID	= 1;
	public static final short DAMAGE_AMOUNT_PAID_STATUS_ID		= 2;
	
	
	public static final String DAMAGE_AMOUNT_RECEIVED_STATUS_NAME		= "Damage Amount Received";
	public static final String DAMAGE_AMOUNT_PAID_STATUS_NAME			= "Damage Amount Paid";
	
	public static final short CLAIM	= 1;
	public static final short NO_CLAIM		= 2;
	
	
	public static final String CLAIM_STATUS_NAME		= "Claimed";
	public static final String NO_CLAIM_STATUS_NAME			= "No Claimed";
	
	
	public static final short ACCIDENT_TYPE_OD					= 1;
	public static final short ACCIDENT_TYPE_TPI					= 2;
	public static final short ACCIDENT_TYPE_TPPD				= 3;
	public static final short ACCIDENT_TYPE_OD_TPI				= 4;
	public static final short ACCIDENT_TYPE_OD_TPPD				= 5;
	public static final short ACCIDENT_TYPE_OD_TPI_TPPD			= 6;
	
	
	public static final String ACCIDENT_TYPE_OD_NAME				= "Own Damage";
	public static final String ACCIDENT_TYPE_TPI_NAME				= "Third Party Injury And Death";
	public static final String ACCIDENT_TYPE_TPPD_NAME				= "Third Party Property Damage";
	public static final String ACCIDENT_TYPE_OD_TPI_NAME			= "Own Damage And Third Party Injury And Death";
	public static final String ACCIDENT_TYPE_OD_TPPD_NAME			= "Own Damage And Third Party Property Damage";
	public static final String ACCIDENT_TYPE_OD_TPI_TPPD_NAME		= "Own Damage And Third Party Injury And Death And Third Party Property Damage";
	
	
	public static final short ACCIDENT_PERSON_TYPE_ID_OWN		= 1;
	public static final short ACCIDENT_PERSON_TYPE_ID_TP		= 2;
	
	public static final String ACCIDENT_PERSON_TYPE_ID_OWN_NAME		= "Own Person ";
	public static final String ACCIDENT_PERSON_TYPE_ID_TP_NAME			= "TP Person";
	
	public static final short ACCIDENT_PERSON_STATUS_INJURED_OWN		= 1;
	public static final short ACCIDENT_PERSON_STATUS_DEAD_TP			= 2;
	
	public static final String ACCIDENT_PERSON_STATUS_ID_INJURED_NAME		= "Injured";
	public static final String ACCIDENT_PERSON_STATUS_ID_DEAD_NAME			= "Dead";
	
	public static final short ACCIDENT_DOCUMENT_STATUS_INCIDENT			= 1;
	public static final short ACCIDENT_DOCUMENT_STATUS_ACCIDENT_TYPE	= 2;
	public static final short ACCIDENT_DOCUMENT_STATUS_SERVEY			= 3;
	public static final short ACCIDENT_DOCUMENT_STATUS_FINAL_SERVEY 	= 4;
	public static final short ACCIDENT_DOCUMENT_STATUS_ESTIMATE 		= 5;
	public static final short ACCIDENT_DOCUMENT_STATUS_KEEP_OPEN 		= 6;
	public static final short ACCIDENT_DOCUMENT_STATUS_BEFORE_APPROVE 		= 7;
	public static final short ACCIDENT_DOCUMENT_STATUS_UPDATE_FINAL_SERVEY  		= 8;
	
	
	public static final String ACCIDENT_DOCUMENT_STATUS_INCIDENT_NAME				= "Accident Photos";
	public static final String ACCIDENT_DOCUMENT_STATUS_ACCIDENT_TYPE_NAME			= "Accident Type";
	public static final String ACCIDENT_DOCUMENT_STATUS_SERVEY_NAME					= "Spot Survey Completion";
	public static final String ACCIDENT_DOCUMENT_STATUS_FINAL_SERVEY_NAME 			= "Final Survey";
	public static final String ACCIDENT_DOCUMENT_STATUS_ESTIMATE_NAME 				= "Preliminary Estimate";
	public static final String ACCIDENT_DOCUMENT_STATUS_KEEP_OPEN_NAME 				= "Keep Open";
	public static final String ACCIDENT_DOCUMENT_STATUS_BEFORE_APPROVE_NAME 		= "Supplementary Estimate";
	public static final String ACCIDENT_DOCUMENT_STATUS_UPDATE_FINAL_SERVEY_NAME  	= "Update Final Survey Photo";
	
	
	
	public static String getStatusName(short status) {

		String statusName		=	null;
		switch (status) {
		  case ACCIDENT_STATUS_INCIDENT_CREATED:
			  statusName	= ACCIDENT_STATUS_INCIDENT_CREATED_NAME;
		        break;
		  case ACCIDENT_STATUS_SERVEYER_DETAILS_UPDATED: 
			  statusName	= ACCIDENT_STATUS_SERVEYER_DETAILS_UPDATED_NAME;
		        break;
		  case ACCIDENT_STATUS_SERVEY_COMPLETED: 
			  statusName	= ACCIDENT_STATUS_SERVEY_COMPLETED_NAME;
		        break;
		  case ACCIDENT_STATUS_QUOTATION_CREATED: 
			  statusName	= ACCIDENT_STATUS_QUOTATION_CREATED_NAME;
		        break;
		  case ACCIDENT_STATUS_FINAL_SERVEY_FOR_DAMAGE: 
			  statusName	= ACCIDENT_STATUS_FINAL_SERVEY_FOR_DAMAGE_NAME;
		        break;
		  case ACCIDENT_STATUS_QUOTATION_APPROVED: 
			  statusName	= ACCIDENT_STATUS_QUOTATION_APPROVED_NAME;
		        break;
		  case ACCIDENT_STATUS_SERVICE_COMPLETED: 
			  statusName	= ACCIDENT_STATUS_SERVICE_COMPLETED_NAME;
		        break;
		  case ACCIDENT_STATUS_FINAL_SERVEY_DONE: 
			  statusName	= ACCIDENT_STATUS_FINAL_SERVEY_DONE_NAME;
		        break;
		  case ACCIDENT_STATUS_PAYMENT_DONE: 
			  statusName	= ACCIDENT_STATUS_PAYMENT_DONE_NAME;
		        break;
		  case ACCIDENT_STATUS_KEEP_OPEN: 
			  statusName	= ACCIDENT_STATUS_KEEP_OPEN_NAME;
			  break;
		  case ACCIDENT_STATUS_BEFORE_FINAL_APPROVAL: 
			  statusName	= ACCIDENT_STATUS_BEFORE_FINAL_APPROVAL_NAME;
			  break;
			  
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	
	}
	
	public static String getDamageStatus(short status) {

		String statusName		=	null;
		switch (status) {
		  case DAMAGE_AMOUNT_RECEIVED_STATUS_ID:
			  statusName	= DAMAGE_AMOUNT_RECEIVED_STATUS_NAME;
		        break;
		  case DAMAGE_AMOUNT_PAID_STATUS_ID: 
			  statusName	= DAMAGE_AMOUNT_PAID_STATUS_NAME;
		        break;
		 
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	
	}
	public static String getClaimStatus(boolean status) {
		String claimStatusName		=	null;
		if(status) {
			claimStatusName	= CLAIM_STATUS_NAME;
		}else {
			claimStatusName	= NO_CLAIM_STATUS_NAME;
		}
		
		return claimStatusName;
		
	}
	
	public static String getAccidentType(short status) {

		String accidentType		=	null;
		switch (status) {
		  case ACCIDENT_TYPE_OD:
			  accidentType	= ACCIDENT_TYPE_OD_NAME;
		        break;
		  case ACCIDENT_TYPE_TPI: 
			  accidentType	= ACCIDENT_TYPE_TPI_NAME;
		        break;
		  case ACCIDENT_TYPE_TPPD: 
			  accidentType	= ACCIDENT_TYPE_TPPD_NAME;
			  break;
		  case ACCIDENT_TYPE_OD_TPI: 
			  accidentType	= ACCIDENT_TYPE_OD_TPI_NAME;
			  break;
		  case ACCIDENT_TYPE_OD_TPPD: 
			  accidentType	= ACCIDENT_TYPE_OD_TPPD_NAME;
			  break;
		  case ACCIDENT_TYPE_OD_TPI_TPPD: 
			  accidentType	= ACCIDENT_TYPE_OD_TPI_TPPD_NAME;
			  break;
		 
		  default:
			  accidentType = "--";
		        break;
		}
		return accidentType;
	
	}
	
	public static String getPersonType(short status) {

		String accidentPersonType		=	null;
		switch (status) {
		  case ACCIDENT_PERSON_TYPE_ID_OWN:
			  accidentPersonType	= ACCIDENT_PERSON_TYPE_ID_OWN_NAME;
		        break;
		  case ACCIDENT_PERSON_TYPE_ID_TP: 
			  accidentPersonType	= ACCIDENT_PERSON_TYPE_ID_TP_NAME;
		        break;
		 
		  default:
			  accidentPersonType = "--";
		        break;
		}
		return accidentPersonType;
	
	}
	
	public static String getPersonStatus(short status) {

		String accidentStatusType		=	null;
		switch (status) {
		  case ACCIDENT_PERSON_STATUS_INJURED_OWN:
			  accidentStatusType	= ACCIDENT_PERSON_STATUS_ID_INJURED_NAME;
		        break;
		  case ACCIDENT_PERSON_STATUS_DEAD_TP: 
			  accidentStatusType	= ACCIDENT_PERSON_STATUS_ID_DEAD_NAME;
		        break;
		 
		  default:
			  accidentStatusType = "--";
		        break;
		}
		return accidentStatusType;
	
	}
	
	public static String getAccidnetDocumentStatus(short status) {

		String accidentDocStatusType		=	null;
		switch (status) {
		  case ACCIDENT_DOCUMENT_STATUS_INCIDENT:
			  accidentDocStatusType	= ACCIDENT_DOCUMENT_STATUS_INCIDENT_NAME;
		        break;
		  case ACCIDENT_DOCUMENT_STATUS_ACCIDENT_TYPE: 
			  accidentDocStatusType	= ACCIDENT_DOCUMENT_STATUS_ACCIDENT_TYPE_NAME;
		        break;
		  case ACCIDENT_DOCUMENT_STATUS_SERVEY:
			  accidentDocStatusType	= ACCIDENT_DOCUMENT_STATUS_SERVEY_NAME;
			  break;
		  case ACCIDENT_DOCUMENT_STATUS_FINAL_SERVEY: 
			  accidentDocStatusType	= ACCIDENT_DOCUMENT_STATUS_FINAL_SERVEY_NAME;
			  break;
		  case ACCIDENT_DOCUMENT_STATUS_ESTIMATE: 
			  accidentDocStatusType	= ACCIDENT_DOCUMENT_STATUS_ESTIMATE_NAME;
			  break;
			  
		  case ACCIDENT_DOCUMENT_STATUS_KEEP_OPEN:
			  accidentDocStatusType =ACCIDENT_DOCUMENT_STATUS_KEEP_OPEN_NAME;
			  break;
			  
		  case ACCIDENT_DOCUMENT_STATUS_BEFORE_APPROVE:
			  accidentDocStatusType =ACCIDENT_DOCUMENT_STATUS_BEFORE_APPROVE_NAME;
			  break;
			  
		  case ACCIDENT_DOCUMENT_STATUS_UPDATE_FINAL_SERVEY:
			  accidentDocStatusType =ACCIDENT_DOCUMENT_STATUS_UPDATE_FINAL_SERVEY_NAME;
			  break;
			  
		  default:
			  accidentDocStatusType = "--";
		        break;
		}
		return accidentDocStatusType;
	
	}
	
}
