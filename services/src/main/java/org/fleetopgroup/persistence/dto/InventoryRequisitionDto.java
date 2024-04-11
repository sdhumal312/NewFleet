package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

public class InventoryRequisitionDto {

	/**
	 * 
	 */

	/** The value for the Requisition Auto ID Long field */
	private Long INVRID;

	/** The value for the Requisition Requited Location String field */
	private String REQUITED_LOCATION;

	/** The value for the Requisition Requited total Quantity Double field */
	private Double REQUITED_TOTALQTY;

	/** The value for the Requisition Requited DATE field */
	private String REQUITED_DATE;

	/** The value for the Requisition Status String field */
	private String REQUISITION_STATUS;

	/** The value for the Requisition Send Name String field */
	private String REQUITED_SENDNAME;
	
	private Long REQUITED_SENDER_ID;

	/** The value for the Requisition Send Name String field */
	private String REQUISITION_RECEIVEDID;
	
	private Long REQUISITION_RECEIVER_ID;

	/** The value for the Requisition Received Name String field */
	private String REQUISITION_RECEIVEDNAME;

	/** The value for the Requisition Number String field */
	private String REQUITED_NUMBER;
	
	private String	vehicle_registration;
	

	/** The value for the Requisition Remarks String field */
	private String REQUITED_REMARK;

	private Long INVRID_NUMBER;
	
	/** The value for the Requisition Requited Location String field */
	private Integer REQUITED_LOCATION_ID;
	
	private Integer VID;
	
	private short REQUISITION_STATUS_ID;
	
	/** The value for the Requisition Received Name String field */
	private String CREATEDBY;

	/** The value for the Requisition Received Name String field */
	private String LASTMODIFIEDBY;

	/** The value for the Requisition Received Name String field */
	private boolean markForDelete;

	/** The value for the ISSUES_Due DATE field */
	private String CREATED_DATE;

	/** The value for the ISSUES_Due DATE field */
	private String LASTUPDATED_DATE;

	/** this value inform to which company this record related */
	private Integer COMPANY_ID;
	
	private Long INVRPARTID;
	
	private Long PART_ID;
	
	private Double PART_REQUITED_QTY;
	
	private Double PART_TRANSFER_QTY;
	
	private String PART_NAME;
	
	private Long	userId;
	
	private String	alertMsg;
	
	public InventoryRequisitionDto() {
		super();
	}


	public InventoryRequisitionDto(Long iNVRID, String rEQUITED_LOCATION, Double rEQUITED_TOTALQTY,
			String rEQUITED_DATE, String rEQUISITION_STATUS, String rEQUITED_SENDNAME, Long rEQUITED_SENDER_ID,
			String rEQUISITION_RECEIVEDID, Long rEQUISITION_RECEIVER_ID, String rEQUISITION_RECEIVEDNAME,
			String rEQUITED_NUMBER, String vehicle_registration, String rEQUITED_REMARK, Long iNVRID_NUMBER,
			Integer rEQUITED_LOCATION_ID, Integer vID, short rEQUISITION_STATUS_ID, String cREATEDBY,
			String lASTMODIFIEDBY, boolean markForDelete, String cREATED_DATE, String lASTUPDATED_DATE,
			Integer cOMPANY_ID, Long iNVRPARTID, Long pART_ID, Double pART_REQUITED_QTY, Double pART_TRANSFER_QTY,
			String pART_NAME, Long userId, String alertMsg) {
		super();
		INVRID = iNVRID;
		REQUITED_LOCATION = rEQUITED_LOCATION;
		REQUITED_TOTALQTY = rEQUITED_TOTALQTY;
		REQUITED_DATE = rEQUITED_DATE;
		REQUISITION_STATUS = rEQUISITION_STATUS;
		REQUITED_SENDNAME = rEQUITED_SENDNAME;
		REQUITED_SENDER_ID = rEQUITED_SENDER_ID;
		REQUISITION_RECEIVEDID = rEQUISITION_RECEIVEDID;
		REQUISITION_RECEIVER_ID = rEQUISITION_RECEIVER_ID;
		REQUISITION_RECEIVEDNAME = rEQUISITION_RECEIVEDNAME;
		REQUITED_NUMBER = rEQUITED_NUMBER;
		this.vehicle_registration = vehicle_registration;
		REQUITED_REMARK = rEQUITED_REMARK;
		INVRID_NUMBER = iNVRID_NUMBER;
		REQUITED_LOCATION_ID = rEQUITED_LOCATION_ID;
		VID = vID;
		REQUISITION_STATUS_ID = rEQUISITION_STATUS_ID;
		CREATEDBY = cREATEDBY;
		LASTMODIFIEDBY = lASTMODIFIEDBY;
		this.markForDelete = markForDelete;
		CREATED_DATE = cREATED_DATE;
		LASTUPDATED_DATE = lASTUPDATED_DATE;
		COMPANY_ID = cOMPANY_ID;
		INVRPARTID = iNVRPARTID;
		PART_ID = pART_ID;
		PART_REQUITED_QTY = pART_REQUITED_QTY;
		PART_TRANSFER_QTY = pART_TRANSFER_QTY;
		PART_NAME = pART_NAME;
		this.userId = userId;
		this.alertMsg = alertMsg;
	}





	public Long getINVRID_NUMBER() {
		return INVRID_NUMBER;
	}

	public void setINVRID_NUMBER(Long iNVRID_NUMBER) {
		INVRID_NUMBER = iNVRID_NUMBER;
	}


	public Long getINVRID() {
		return INVRID;
	}

	public void setINVRID(Long iNVRID) {
		INVRID = iNVRID;
	}

	public String getREQUITED_LOCATION() {
		return REQUITED_LOCATION;
	}

	public void setREQUITED_LOCATION(String rEQUITED_LOCATION) {
		REQUITED_LOCATION = rEQUITED_LOCATION;
	}

	public Double getREQUITED_TOTALQTY() {
		return REQUITED_TOTALQTY;
	}

	public void setREQUITED_TOTALQTY(Double rEQUITED_TOTALQTY) {
		REQUITED_TOTALQTY =  Utility.round(rEQUITED_TOTALQTY, 2);
	}

	public String getREQUITED_DATE() {
		return REQUITED_DATE;
	}

	public void setREQUITED_DATE(String rEQUITED_DATE) {
		REQUITED_DATE = rEQUITED_DATE;
	}

	public String getREQUISITION_STATUS() {
		return REQUISITION_STATUS;
	}

	public void setREQUISITION_STATUS(String rEQUISITION_STATUS) {
		REQUISITION_STATUS = rEQUISITION_STATUS;
	}

	public String getREQUITED_SENDNAME() {
		return REQUITED_SENDNAME;
	}

	public void setREQUITED_SENDNAME(String rEQUITED_SENDNAME) {
		REQUITED_SENDNAME = rEQUITED_SENDNAME;
	}

	public String getREQUISITION_RECEIVEDID() {
		return REQUISITION_RECEIVEDID;
	}

	public void setREQUISITION_RECEIVEDID(String rEQUISITION_RECEIVEDID) {
		REQUISITION_RECEIVEDID = rEQUISITION_RECEIVEDID;
	}

	public String getREQUISITION_RECEIVEDNAME() {
		return REQUISITION_RECEIVEDNAME;
	}

	public void setREQUISITION_RECEIVEDNAME(String rEQUISITION_RECEIVEDNAME) {
		REQUISITION_RECEIVEDNAME = rEQUISITION_RECEIVEDNAME;
	}

	public String getREQUITED_NUMBER() {
		return REQUITED_NUMBER;
	}

	public void setREQUITED_NUMBER(String rEQUITED_NUMBER) {
		REQUITED_NUMBER = rEQUITED_NUMBER;
	}

	public String getREQUITED_REMARK() {
		return REQUITED_REMARK;
	}

	public void setREQUITED_REMARK(String rEQUITED_REMARK) {
		REQUITED_REMARK = rEQUITED_REMARK;
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

	
	

	public Long getREQUITED_SENDER_ID() {
		return REQUITED_SENDER_ID;
	}

	public void setREQUITED_SENDER_ID(Long rEQUITED_SENDER_ID) {
		REQUITED_SENDER_ID = rEQUITED_SENDER_ID;
	}

	public Long getREQUISITION_RECEIVER_ID() {
		return REQUISITION_RECEIVER_ID;
	}

	public void setREQUISITION_RECEIVER_ID(Long rEQUISITION_RECEIVER_ID) {
		REQUISITION_RECEIVER_ID = rEQUISITION_RECEIVER_ID;
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

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	public boolean ismarkForDelete() {
		return markForDelete;
	}

	public void setmarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	
	public Integer getREQUITED_LOCATION_ID() {
		return REQUITED_LOCATION_ID;
	}

	public void setREQUITED_LOCATION_ID(Integer rEQUITED_LOCATION_ID) {
		REQUITED_LOCATION_ID = rEQUITED_LOCATION_ID;
	}

	public short getREQUISITION_STATUS_ID() {
		return REQUISITION_STATUS_ID;
	}

	public void setREQUISITION_STATUS_ID(short rEQUISITION_STATUS_ID) {
		REQUISITION_STATUS_ID = rEQUISITION_STATUS_ID;
	}

	public Integer getVID() {
		return VID;
	}

	public void setVID(Integer vID) {
		VID = vID;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}


	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	

	public Long getINVRPARTID() {
		return INVRPARTID;
	}

	public void setINVRPARTID(Long iNVRPARTID) {
		INVRPARTID = iNVRPARTID;
	}

	public Long getPART_ID() {
		return PART_ID;
	}

	public void setPART_ID(Long pART_ID) {
		PART_ID = pART_ID;
	}

	public Double getPART_REQUITED_QTY() {
		return PART_REQUITED_QTY;
	}

	public void setPART_REQUITED_QTY(Double pART_REQUITED_QTY) {
		PART_REQUITED_QTY = Utility.round(pART_REQUITED_QTY, 2);
	}

	public Double getPART_TRANSFER_QTY() {
		return PART_TRANSFER_QTY;
	}

	public void setPART_TRANSFER_QTY(Double pART_TRANSFER_QTY) {
		PART_TRANSFER_QTY = Utility.round(pART_TRANSFER_QTY,2);
	}

	public String getPART_NAME() {
		return PART_NAME;
	}

	public void setPART_NAME(String pART_NAME) {
		PART_NAME = pART_NAME;
	}

	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAlertMsg() {
		return alertMsg;
	}

	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((INVRID == null) ? 0 : INVRID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryRequisitionDto other = (InventoryRequisitionDto) obj;
		if (INVRID == null) {
			if (other.INVRID != null)
				return false;
		} else if (!INVRID.equals(other.INVRID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InventoryRequisitionDto [INVRID=" + INVRID + ", REQUITED_LOCATION=" + REQUITED_LOCATION
				+ ", REQUITED_TOTALQTY=" + REQUITED_TOTALQTY + ", REQUITED_DATE=" + REQUITED_DATE
				+ ", REQUISITION_STATUS=" + REQUISITION_STATUS + ", REQUITED_SENDNAME=" + REQUITED_SENDNAME
				+ ", REQUITED_SENDER_ID=" + REQUITED_SENDER_ID + ", REQUISITION_RECEIVEDID=" + REQUISITION_RECEIVEDID
				+ ", REQUISITION_RECEIVER_ID=" + REQUISITION_RECEIVER_ID + ", REQUISITION_RECEIVEDNAME="
				+ REQUISITION_RECEIVEDNAME + ", REQUITED_NUMBER=" + REQUITED_NUMBER + ", vehicle_registration="
				+ vehicle_registration + ", REQUITED_REMARK=" + REQUITED_REMARK + ", INVRID_NUMBER=" + INVRID_NUMBER
				+ ", REQUITED_LOCATION_ID=" + REQUITED_LOCATION_ID + ", VID=" + VID + ", REQUISITION_STATUS_ID="
				+ REQUISITION_STATUS_ID + ", CREATEDBY=" + CREATEDBY + ", LASTMODIFIEDBY=" + LASTMODIFIEDBY
				+ ", markForDelete=" + markForDelete + ", CREATED_DATE=" + CREATED_DATE + ", LASTUPDATED_DATE="
				+ LASTUPDATED_DATE + ", COMPANY_ID=" + COMPANY_ID + ", INVRPARTID=" + INVRPARTID + ", PART_ID="
				+ PART_ID + ", PART_REQUITED_QTY=" + PART_REQUITED_QTY + ", PART_TRANSFER_QTY=" + PART_TRANSFER_QTY
				+ ", PART_NAME=" + PART_NAME + "]";
	}



}