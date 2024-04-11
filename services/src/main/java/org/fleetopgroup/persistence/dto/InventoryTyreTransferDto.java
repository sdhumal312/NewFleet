package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class InventoryTyreTransferDto {

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
	
	
	public static final short TRANSFER_STATUS_TRANSFER		= 1;
	public static final short TRANSFER_STATUS_RECEIVED		= 2;
	
	public static final String TRANSFER_STATUS_TRANSFER_NAME	= "TRANSFER";
	public static final String TRANSFER_STATUS_RECEIVED_NAME	= "RECEIVED";
	
	private Long ITTID;

	private Long TYRE_ID;
	

	/** The value for the Vehicle Registration Number field */
	private String TYRE_NUMBER;

	private String TRA_FROM_LOCATION;

	private String TRA_TO_LOCATION;

	private Double TRA_QUANTITY;

	private String TRANSFER_DATE;
	
	private Date TRANSFER_DATE_ON;

	private String TRANSFER_BY;

	private String TRANSFER_RECEIVEDBY;

	private String TRANSFER_BYEMAIL;

	private String TRANSFER_RECEIVEDBYEMAIL;

	private String TRANSFER_RECEIVEDDATE;
	
	private Date TRANSFER_RECEIVEDDATE_ON;

	private String TRANSFER_RECEIVEDREASON;

	private String TRANSFER_VIA;

	private String TRANSFER_REASON;

	private Long TRA_INVENTORY_INVOCEID;

	/** The value for the ISSUES Assign to Status one user name field */
	private String TRANSFER_STATUS;

	private String CREATEDBY;

	private String LASTMODIFIEDBY;

	boolean markForDelete;

	/** The value for the ISSUES_Due DATE field */
	private String CREATED_DATE;
	
	private Date CREATED_DATE_ON;

	/** The value for the ISSUES_Due DATE field */
	private String LASTUPDATED_DATE;
	
	private Date LASTUPDATED_DATE_ON;
	
	private Integer TRA_FROM_LOCATION_ID;
	private Integer TRA_TO_LOCATION_ID;
	private Long   TRANSFER_BY_ID;
	private Long   TRANSFER_RECEIVEDBY_ID;
	private Long	CREATEDBYID;
	private Long	LASTMODIFIEDBYID;
	private short	TRANSFER_VIA_ID;
	private short	TRANSFER_STATUS_ID;

	public InventoryTyreTransferDto() {
		super();
	}

	public InventoryTyreTransferDto(Long iTTID, Long tYRE_ID, String tYRE_NUMBER, String tRA_FROM_LOCATION,
			String tRA_TO_LOCATION, Double tRA_QUANTITY, String tRANSFER_DATE, String tRANSFER_BY,
			String tRANSFER_RECEIVEDBY, String tRANSFER_BYEMAIL, String tRANSFER_RECEIVEDBYEMAIL,
			String tRANSFER_RECEIVEDDATE, String tRANSFER_RECEIVEDREASON, String tRANSFER_VIA, String tRANSFER_REASON,
			Long tRA_INVENTORY_INVOCEID, String tRANSFER_STATUS, String cREATEDBY, String lASTMODIFIEDBY,
			boolean MarkForDelete, String cREATED_DATE, String lASTUPDATED_DATE) {
		super();
		ITTID = iTTID;
		TYRE_ID = tYRE_ID;
		TYRE_NUMBER = tYRE_NUMBER;
		TRA_FROM_LOCATION = tRA_FROM_LOCATION;
		TRA_TO_LOCATION = tRA_TO_LOCATION;
		TRA_QUANTITY = tRA_QUANTITY;
		TRANSFER_DATE = tRANSFER_DATE;
		TRANSFER_BY = tRANSFER_BY;
		TRANSFER_RECEIVEDBY = tRANSFER_RECEIVEDBY;
		TRANSFER_BYEMAIL = tRANSFER_BYEMAIL;
		TRANSFER_RECEIVEDBYEMAIL = tRANSFER_RECEIVEDBYEMAIL;
		TRANSFER_RECEIVEDDATE = tRANSFER_RECEIVEDDATE;
		TRANSFER_RECEIVEDREASON = tRANSFER_RECEIVEDREASON;
		TRANSFER_VIA = tRANSFER_VIA;
		TRANSFER_REASON = tRANSFER_REASON;
		TRA_INVENTORY_INVOCEID = tRA_INVENTORY_INVOCEID;
		TRANSFER_STATUS = tRANSFER_STATUS;
		CREATEDBY = cREATEDBY;
		LASTMODIFIEDBY = lASTMODIFIEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}

	public Long getITTID() {
		return ITTID;
	}

	public void setITTID(Long iTTID) {
		ITTID = iTTID;
	}

	public Long getTYRE_ID() {
		return TYRE_ID;
	}

	public void setTYRE_ID(Long tYRE_ID) {
		TYRE_ID = tYRE_ID;
	}

	
	public String getTYRE_NUMBER() {
		return TYRE_NUMBER;
	}

	public void setTYRE_NUMBER(String tYRE_NUMBER) {
		TYRE_NUMBER = tYRE_NUMBER;
	}

	public String getTRA_FROM_LOCATION() {
		return TRA_FROM_LOCATION;
	}

	public void setTRA_FROM_LOCATION(String tRA_FROM_LOCATION) {
		TRA_FROM_LOCATION = tRA_FROM_LOCATION;
	}

	public String getTRA_TO_LOCATION() {
		return TRA_TO_LOCATION;
	}

	public void setTRA_TO_LOCATION(String tRA_TO_LOCATION) {
		TRA_TO_LOCATION = tRA_TO_LOCATION;
	}

	public Double getTRA_QUANTITY() {
		return TRA_QUANTITY;
	}

	public void setTRA_QUANTITY(Double tRA_QUANTITY) {
		TRA_QUANTITY = Utility.round(tRA_QUANTITY, 2);
	}

	public String getTRANSFER_DATE() {
		return TRANSFER_DATE;
	}

	public void setTRANSFER_DATE(String tRANSFER_DATE) {
		TRANSFER_DATE = tRANSFER_DATE;
	}

	public String getTRANSFER_BY() {
		return TRANSFER_BY;
	}

	public void setTRANSFER_BY(String tRANSFER_BY) {
		TRANSFER_BY = tRANSFER_BY;
	}

	public String getTRANSFER_RECEIVEDBY() {
		return TRANSFER_RECEIVEDBY;
	}

	public void setTRANSFER_RECEIVEDBY(String tRANSFER_RECEIVEDBY) {
		TRANSFER_RECEIVEDBY = tRANSFER_RECEIVEDBY;
	}

	public String getTRANSFER_BYEMAIL() {
		return TRANSFER_BYEMAIL;
	}

	public void setTRANSFER_BYEMAIL(String tRANSFER_BYEMAIL) {
		TRANSFER_BYEMAIL = tRANSFER_BYEMAIL;
	}

	public String getTRANSFER_RECEIVEDBYEMAIL() {
		return TRANSFER_RECEIVEDBYEMAIL;
	}

	public void setTRANSFER_RECEIVEDBYEMAIL(String tRANSFER_RECEIVEDBYEMAIL) {
		TRANSFER_RECEIVEDBYEMAIL = tRANSFER_RECEIVEDBYEMAIL;
	}

	public String getTRANSFER_RECEIVEDDATE() {
		return TRANSFER_RECEIVEDDATE;
	}

	public void setTRANSFER_RECEIVEDDATE(String tRANSFER_RECEIVEDDATE) {
		TRANSFER_RECEIVEDDATE = tRANSFER_RECEIVEDDATE;
	}

	public String getTRANSFER_RECEIVEDREASON() {
		return TRANSFER_RECEIVEDREASON;
	}

	public void setTRANSFER_RECEIVEDREASON(String tRANSFER_RECEIVEDREASON) {
		TRANSFER_RECEIVEDREASON = tRANSFER_RECEIVEDREASON;
	}

	public String getTRANSFER_VIA() {
		return TRANSFER_VIA;
	}

	public void setTRANSFER_VIA(String tRANSFER_VIA) {
		TRANSFER_VIA = tRANSFER_VIA;
	}

	public String getTRANSFER_REASON() {
		return TRANSFER_REASON;
	}

	public void setTRANSFER_REASON(String tRANSFER_REASON) {
		TRANSFER_REASON = tRANSFER_REASON;
	}

	public Long getTRA_INVENTORY_INVOCEID() {
		return TRA_INVENTORY_INVOCEID;
	}

	public void setTRA_INVENTORY_INVOCEID(Long tRA_INVENTORY_INVOCEID) {
		TRA_INVENTORY_INVOCEID = tRA_INVENTORY_INVOCEID;
	}

	public String getTRANSFER_STATUS() {
		return TRANSFER_STATUS;
	}

	public void setTRANSFER_STATUS(String tRANSFER_STATUS) {
		TRANSFER_STATUS = tRANSFER_STATUS;
	}

	public String getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean MarkForDelete) {
		markForDelete = MarkForDelete;
	}

	public String getCREATED_DATE() {
		return CREATED_DATE;
	}

	public void setCREATED_DATE(String cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	public String getLASTUPDATED_DATE() {
		return LASTUPDATED_DATE;
	}

	public void setLASTUPDATED_DATE(String lASTUPDATED_DATE) {
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}

	/**
	 * @return the tRA_FROM_LOCATION_ID
	 */
	public Integer getTRA_FROM_LOCATION_ID() {
		return TRA_FROM_LOCATION_ID;
	}

	/**
	 * @param tRA_FROM_LOCATION_ID the tRA_FROM_LOCATION_ID to set
	 */
	public void setTRA_FROM_LOCATION_ID(Integer tRA_FROM_LOCATION_ID) {
		TRA_FROM_LOCATION_ID = tRA_FROM_LOCATION_ID;
	}

	/**
	 * @return the tRA_TO_LOCATION_ID
	 */
	public Integer getTRA_TO_LOCATION_ID() {
		return TRA_TO_LOCATION_ID;
	}

	/**
	 * @param tRA_TO_LOCATION_ID the tRA_TO_LOCATION_ID to set
	 */
	public void setTRA_TO_LOCATION_ID(Integer tRA_TO_LOCATION_ID) {
		TRA_TO_LOCATION_ID = tRA_TO_LOCATION_ID;
	}

	/**
	 * @return the tRANSFER_BY_ID
	 */
	public Long getTRANSFER_BY_ID() {
		return TRANSFER_BY_ID;
	}

	/**
	 * @param tRANSFER_BY_ID the tRANSFER_BY_ID to set
	 */
	public void setTRANSFER_BY_ID(Long tRANSFER_BY_ID) {
		TRANSFER_BY_ID = tRANSFER_BY_ID;
	}

	/**
	 * @return the tRANSFER_RECEIVEDBY_ID
	 */
	public Long getTRANSFER_RECEIVEDBY_ID() {
		return TRANSFER_RECEIVEDBY_ID;
	}

	/**
	 * @param tRANSFER_RECEIVEDBY_ID the tRANSFER_RECEIVEDBY_ID to set
	 */
	public void setTRANSFER_RECEIVEDBY_ID(Long tRANSFER_RECEIVEDBY_ID) {
		TRANSFER_RECEIVEDBY_ID = tRANSFER_RECEIVEDBY_ID;
	}

	/**
	 * @return the cREATEDBYID
	 */
	public Long getCREATEDBYID() {
		return CREATEDBYID;
	}

	/**
	 * @param cREATEDBYID the cREATEDBYID to set
	 */
	public void setCREATEDBYID(Long cREATEDBYID) {
		CREATEDBYID = cREATEDBYID;
	}

	/**
	 * @return the lASTMODIFIEDBYID
	 */
	public Long getLASTMODIFIEDBYID() {
		return LASTMODIFIEDBYID;
	}

	/**
	 * @param lASTMODIFIEDBYID the lASTMODIFIEDBYID to set
	 */
	public void setLASTMODIFIEDBYID(Long lASTMODIFIEDBYID) {
		LASTMODIFIEDBYID = lASTMODIFIEDBYID;
	}

	/**
	 * @return the tRANSFER_VIA_ID
	 */
	public short getTRANSFER_VIA_ID() {
		return TRANSFER_VIA_ID;
	}

	/**
	 * @param tRANSFER_VIA_ID the tRANSFER_VIA_ID to set
	 */
	public void setTRANSFER_VIA_ID(short tRANSFER_VIA_ID) {
		TRANSFER_VIA_ID = tRANSFER_VIA_ID;
	}

	/**
	 * @return the tRANSFER_STATUS_ID
	 */
	public short getTRANSFER_STATUS_ID() {
		return TRANSFER_STATUS_ID;
	}

	/**
	 * @param tRANSFER_STATUS_ID the tRANSFER_STATUS_ID to set
	 */
	public void setTRANSFER_STATUS_ID(short tRANSFER_STATUS_ID) {
		TRANSFER_STATUS_ID = tRANSFER_STATUS_ID;
	}

	/**
	 * @return the tRANSFER_DATE_ON
	 */
	public Date getTRANSFER_DATE_ON() {
		return TRANSFER_DATE_ON;
	}

	/**
	 * @param tRANSFER_DATE_ON the tRANSFER_DATE_ON to set
	 */
	public void setTRANSFER_DATE_ON(Date tRANSFER_DATE_ON) {
		TRANSFER_DATE_ON = tRANSFER_DATE_ON;
	}

	/**
	 * @return the cREATED_DATE_ON
	 */
	public Date getCREATED_DATE_ON() {
		return CREATED_DATE_ON;
	}

	/**
	 * @param cREATED_DATE_ON the cREATED_DATE_ON to set
	 */
	public void setCREATED_DATE_ON(Date cREATED_DATE_ON) {
		CREATED_DATE_ON = cREATED_DATE_ON;
	}

	/**
	 * @return the lASTUPDATED_DATE_ON
	 */
	public Date getLASTUPDATED_DATE_ON() {
		return LASTUPDATED_DATE_ON;
	}

	/**
	 * @param lASTUPDATED_DATE_ON the lASTUPDATED_DATE_ON to set
	 */
	public void setLASTUPDATED_DATE_ON(Date lASTUPDATED_DATE_ON) {
		LASTUPDATED_DATE_ON = lASTUPDATED_DATE_ON;
	}

	/**
	 * @return the tRANSFER_RECEIVEDDATE_ON
	 */
	public Date getTRANSFER_RECEIVEDDATE_ON() {
		return TRANSFER_RECEIVEDDATE_ON;
	}

	/**
	 * @param tRANSFER_RECEIVEDDATE_ON the tRANSFER_RECEIVEDDATE_ON to set
	 */
	public void setTRANSFER_RECEIVEDDATE_ON(Date tRANSFER_RECEIVEDDATE_ON) {
		TRANSFER_RECEIVEDDATE_ON = tRANSFER_RECEIVEDDATE_ON;
	}

		

	@Override
	public String toString() {
		return "InventoryTyreTransferDto [ITTID=" + ITTID + ", TYRE_ID=" + TYRE_ID + ", TYRE_NUMBER=" + TYRE_NUMBER
				+ ", TRA_FROM_LOCATION=" + TRA_FROM_LOCATION + ", TRA_TO_LOCATION=" + TRA_TO_LOCATION
				+ ", TRA_QUANTITY=" + TRA_QUANTITY + ", TRANSFER_DATE=" + TRANSFER_DATE + ", TRANSFER_DATE_ON="
				+ TRANSFER_DATE_ON + ", TRANSFER_BY=" + TRANSFER_BY + ", TRANSFER_RECEIVEDBY=" + TRANSFER_RECEIVEDBY
				+ ", TRANSFER_BYEMAIL=" + TRANSFER_BYEMAIL + ", TRANSFER_RECEIVEDBYEMAIL=" + TRANSFER_RECEIVEDBYEMAIL
				+ ", TRANSFER_RECEIVEDDATE=" + TRANSFER_RECEIVEDDATE + ", TRANSFER_RECEIVEDDATE_ON="
				+ TRANSFER_RECEIVEDDATE_ON + ", TRANSFER_RECEIVEDREASON=" + TRANSFER_RECEIVEDREASON + ", TRANSFER_VIA="
				+ TRANSFER_VIA + ", TRANSFER_REASON=" + TRANSFER_REASON + ", TRA_INVENTORY_INVOCEID="
				+ TRA_INVENTORY_INVOCEID + ", TRANSFER_STATUS=" + TRANSFER_STATUS + ", CREATEDBY=" + CREATEDBY
				+ ", LASTMODIFIEDBY=" + LASTMODIFIEDBY + ", markForDelete=" + markForDelete + ", CREATED_DATE="
				+ CREATED_DATE + ", CREATED_DATE_ON=" + CREATED_DATE_ON + ", LASTUPDATED_DATE=" + LASTUPDATED_DATE
				+ ", LASTUPDATED_DATE_ON=" + LASTUPDATED_DATE_ON + ", TRA_FROM_LOCATION_ID=" + TRA_FROM_LOCATION_ID
				+ ", TRA_TO_LOCATION_ID=" + TRA_TO_LOCATION_ID + ", TRANSFER_BY_ID=" + TRANSFER_BY_ID
				+ ", TRANSFER_RECEIVEDBY_ID=" + TRANSFER_RECEIVEDBY_ID + ", CREATEDBYID=" + CREATEDBYID
				+ ", LASTMODIFIEDBYID=" + LASTMODIFIEDBYID + ", TRANSFER_VIA_ID=" + TRANSFER_VIA_ID
				+ ", TRANSFER_STATUS_ID=" + TRANSFER_STATUS_ID + "]";
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

	public static String getStatusName(short status) {

		String statusName		=	null;
		switch (status) {
		  case TRANSFER_STATUS_TRANSFER:
			  statusName	= TRANSFER_STATUS_TRANSFER_NAME;
		        break;
		  case TRANSFER_STATUS_RECEIVED: 
			  statusName	= TRANSFER_STATUS_RECEIVED_NAME;
		        break;
		
		
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	}

}