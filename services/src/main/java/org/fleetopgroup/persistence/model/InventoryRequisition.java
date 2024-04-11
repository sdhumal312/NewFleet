package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "InventoryRequisition")
public class InventoryRequisition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The value for the Requisition Auto ID Long field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INVRID")
	private Long INVRID;

	/** The value for the Requisition Requited Location String field *//*
	@Column(name = "REQUITED_LOCATION", nullable = true, length = 250)
	@Deprecated
	private String REQUITED_LOCATION;*/
	
	/** The value for the Requisition Requited Location String field */
	@Column(name = "REQUITED_LOCATION_ID")
	private Integer REQUITED_LOCATION_ID;


	/** The value for the Requisition Requited total Quantity Double field */
	@Column(name = "REQUITED_TOTALQTY", length = 10)
	private Double REQUITED_TOTALQTY;

	/** The value for the Requisition Requited DATE field */
	@Column(name = "REQUITED_DATE")
	private Date REQUITED_DATE;

	/** The value for the Requisition Status String field *//*
	@Column(name = "REQUISITION_STATUS", length = 25)
	@Deprecated
	private String REQUISITION_STATUS;

	*//** The value for the Requisition Send Name String field *//*
	@Column(name = "REQUITED_SENDNAME", length = 150)
	@Deprecated
	private String REQUITED_SENDNAME;*/
	
	/** The value for the Requisition Send Name String field */
	@Column(name = "REQUITED_SENDER_ID", nullable=  false)
	private Long REQUITED_SENDER_ID;

	/** The value for the Requisition Received Name String field *//*
	@Column(name = "REQUISITION_RECEIVEDNAME", length = 150)
	@Deprecated
	private String REQUISITION_RECEIVEDNAME;*/
	
	/** The value for the Requisition Received Name String field */
	@Column(name = "REQUISITION_RECEIVER_ID", nullable=  false)
	private Long REQUISITION_RECEIVER_ID;

	/** The value for the Requisition Number String field */
	@Column(name = "REQUITED_NUMBER", length = 50)
	private String REQUITED_NUMBER;

	/** The value for the Requisition Remarks String field */
	@Column(name = "REQUITED_REMARK", length = 500)
	private String REQUITED_REMARK;

	/** The value for the Requisition Received Name String field *//*
	@Column(name = "CREATEDBY", updatable = false, length = 150)
	private String CREATEDBY;

	*//** The value for the Requisition Received Name String field *//*
	@Column(name = "LASTMODIFIEDBY", length = 150)
	private String LASTMODIFIEDBY;*/
	
	@Column(name = "CREATEDBYID")
	private Long CREATEDBYID;
	
	@Column(name = "LASTMODIFIEDBYID")
	private Long LASTMODIFIEDBYID;

	
	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATED_DATE;

	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "LASTUPDATED_DATE", nullable = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date LASTUPDATED_DATE;

	/** this value inform to which company this record related */
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;
	
	@Column(name = "INVRID_NUMBER", nullable = false)
	private Long INVRID_NUMBER;

	/** this is for marking this value is required or not */
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	/** The value for the Requisition Status ID  field */
	@Column(name = "REQUISITION_STATUS_ID")
	private short REQUISITION_STATUS_ID;
	
	@Column(name = "VID")
	private Integer	VID;
	
	
	
	public InventoryRequisition() {
		super();
	}

	public InventoryRequisition(Long iNVRID, Double rEQUITED_TOTALQTY, Date rEQUITED_DATE,
			 Long cREATEDBY,
			Long lASTMODIFIEDBY,  Date cREATED_DATE, Date lASTUPDATED_DATE, Integer cOMPANY_ID,
			boolean mARKFORDELETE) {
		super();
		INVRID = iNVRID;
	//	REQUITED_LOCATION = rEQUITED_LOCATION;
		REQUITED_TOTALQTY = rEQUITED_TOTALQTY;
		REQUITED_DATE = rEQUITED_DATE;
		//REQUISITION_STATUS = rEQUISITION_STATUS;
		//REQUITED_SENDNAME = rEQUITED_SENDNAME;
		//REQUISITION_RECEIVEDNAME = rEQUISITION_RECEIVEDNAME;
		CREATEDBYID = cREATEDBY;
		LASTMODIFIEDBYID = lASTMODIFIEDBY;
		CREATED_DATE = cREATED_DATE;
		LASTUPDATED_DATE = lASTUPDATED_DATE;
		COMPANY_ID = cOMPANY_ID;
		markForDelete = mARKFORDELETE;
	}

	public Long getINVRID() {
		return INVRID;
	}

	public void setINVRID(Long iNVRID) {
		INVRID = iNVRID;
	}


	public Double getREQUITED_TOTALQTY() {
		return REQUITED_TOTALQTY;
	}

	public void setREQUITED_TOTALQTY(Double rEQUITED_TOTALQTY) {
		REQUITED_TOTALQTY = rEQUITED_TOTALQTY;
	}

	public Date getREQUITED_DATE() {
		return REQUITED_DATE;
	}

	public void setREQUITED_DATE(Date rEQUITED_DATE) {
		REQUITED_DATE = rEQUITED_DATE;
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

	/*public String getCREATEDBY() {
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
	}*/


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

	public Date getCREATED_DATE() {
		return CREATED_DATE;
	}

	public void setCREATED_DATE(Date cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	public Date getLASTUPDATED_DATE() {
		return LASTUPDATED_DATE;
	}

	public void setLASTUPDATED_DATE(Date lASTUPDATED_DATE) {
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	/*public boolean ismarkForDelete() {
		return markForDelete;
	}

	public void setmarkForDelete(boolean mARKFORDELETE) {
		markForDelete = mARKFORDELETE;
	}*/
	

	public Long getINVRID_NUMBER() {
		return INVRID_NUMBER;
	}

	public void setINVRID_NUMBER(Long iNVRID_NUMBER) {
		INVRID_NUMBER = iNVRID_NUMBER;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	

	public short getREQUISITION_STATUS_ID() {
		return REQUISITION_STATUS_ID;
	}

	public void setREQUISITION_STATUS_ID(short rEQUISITION_STATUS_ID) {
		REQUISITION_STATUS_ID = rEQUISITION_STATUS_ID;
	}

	
	public Integer getREQUITED_LOCATION_ID() {
		return REQUITED_LOCATION_ID;
	}

	public void setREQUITED_LOCATION_ID(Integer rEQUITED_LOCATION_ID) {
		REQUITED_LOCATION_ID = rEQUITED_LOCATION_ID;
	}

	public Integer getVID() {
		return VID;
	}

	public void setVID(Integer vID) {
		VID = vID;
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
		InventoryRequisition other = (InventoryRequisition) obj;
		if (INVRID == null) {
			if (other.INVRID != null)
				return false;
		} else if (!INVRID.equals(other.INVRID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryRequisition [INVRID=");
		builder.append(INVRID);
	/*	builder.append(", REQUITED_LOCATION=");
		builder.append(REQUITED_LOCATION);*/
		builder.append(", REQUITED_TOTALQTY=");
		builder.append(REQUITED_TOTALQTY);
		builder.append(", REQUITED_DATE=");
		builder.append(REQUITED_DATE);
		/*builder.append(", REQUISITION_STATUS=");
		builder.append(REQUISITION_STATUS);
		builder.append(", REQUITED_SENDNAME=");
		builder.append(REQUITED_SENDNAME);
		builder.append(", REQUISITION_RECEIVEDNAME=");
		builder.append(REQUISITION_RECEIVEDNAME);*/
		builder.append(", REQUITED_NUMBER=");
		builder.append(REQUITED_NUMBER);
		builder.append(", REQUITED_REMARK=");
		builder.append(REQUITED_REMARK);
		builder.append(", CREATEDBYID=");
		builder.append(CREATEDBYID);
		builder.append(", LASTMODIFIEDBYID=");
		builder.append(LASTMODIFIEDBYID);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", LASTUPDATED_DATE=");
		builder.append(LASTUPDATED_DATE);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", INVRID_NUMBER=");
		builder.append(INVRID_NUMBER);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", REQUISITION_STATUS_ID=");
		builder.append(REQUISITION_STATUS_ID);
		builder.append("]");
		return builder.toString();
	}

}