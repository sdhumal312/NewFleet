package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CashBookName")
public class CashBookName implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NAMEID")
	private Integer NAMEID;

	/** This Cash Book Name Master Of values */
	@Column(name = "CASHBOOK_NAME", unique = false, nullable = false, length = 250)
	private String CASHBOOK_NAME;

	/** This Cash Book Name Master remarks Of values */
	@Column(name = "CASHBOOK_REMARKS", length = 250)
	private String CASHBOOK_REMARKS;
	
	@Column(name = "CASHBOOK_CODE", length = 50)
	private String	CASHBOOK_CODE;

	/** This Cash Book Name Created by Of values */
	@Column(name = "CREATEDBY", length = 150)
	private String CREATEDBY;

	@Column(name = "CREATEDBYID", nullable = false)
	private Long CREATEDBYID;

	/** This Cash Book Name Create Date Of values */
	@Column(name = "CREATED", nullable = false, updatable = false)
	private Date CREATED;
	
	/** this value inform to which company this record related*/
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;
	
	/** This Cash Book Name LAST Modified by Of values */
	@Column(name = "LASTMODIFIEDBY", length = 150)
	private String LASTMODIFIEDBY;

	@Column(name = "LASTMODIFIEDBYID")
	private Long LASTMODIFIEDBYID;

	/** This Cash Book Name LAST Modified Date Of values */
	@Column(name = "LASTMODIFIEDON", nullable = true, updatable = true)
	private Date LASTMODIFIEDON;
	
	@Column(name = "VEHICLE_GROUP_ID", nullable = false)
	private long VEHICLE_GROUP_ID;
	
	
	/** this is for marking this value is required or not */
	@Column(name = "markForDelete" , nullable = false)
	private boolean	markForDelete;

	public CashBookName() {
		super();
	}

	public CashBookName(Integer nAMEID, String cASHBOOK_NAME, String cASHBOOK_REMARKS, String cREATEDBY, Date cREATED
			,Integer companyId) {
		super();
		NAMEID = nAMEID;
		CASHBOOK_NAME = cASHBOOK_NAME;
		CASHBOOK_REMARKS = cASHBOOK_REMARKS;
		CREATEDBY = cREATEDBY;
		CREATED = cREATED;
		this.COMPANY_ID = companyId;
	}
	
	public CashBookName(String cASHBOOK_NAME, String cASHBOOK_REMARKS, Long cREATEDBY, Date cREATED
			,Integer companyId, long vehicleGroupId) {
		super();
		this.CASHBOOK_NAME = cASHBOOK_NAME;
		this.CASHBOOK_REMARKS = cASHBOOK_REMARKS;
		this.CREATEDBYID = cREATEDBY;
		this.CREATED = cREATED;
		this.COMPANY_ID = companyId;
		this.VEHICLE_GROUP_ID = vehicleGroupId;
	}

	/**
	 * @return the nAMEID
	 */
	public Integer getNAMEID() {
		return NAMEID;
	}

	/**
	 * @param nAMEID
	 *            the nAMEID to set
	 */
	public void setNAMEID(Integer nAMEID) {
		NAMEID = nAMEID;
	}

	/**
	 * @return the cASHBOOK_NAME
	 */
	public String getCASHBOOK_NAME() {
		return CASHBOOK_NAME;
	}

	/**
	 * @param cASHBOOK_NAME
	 *            the cASHBOOK_NAME to set
	 */
	public void setCASHBOOK_NAME(String cASHBOOK_NAME) {
		CASHBOOK_NAME = cASHBOOK_NAME;
	}

	/**
	 * @return the cASHBOOK_REMARKS
	 */
	public String getCASHBOOK_REMARKS() {
		return CASHBOOK_REMARKS;
	}

	/**
	 * @param cASHBOOK_REMARKS
	 *            the cASHBOOK_REMARKS to set
	 */
	public void setCASHBOOK_REMARKS(String cASHBOOK_REMARKS) {
		CASHBOOK_REMARKS = cASHBOOK_REMARKS;
	}

	/**
	 * @return the cREATEDBY
	 */
	public String getCREATEDBY() {
		return CREATEDBY;
	}

	/**
	 * @param cREATEDBY
	 *            the cREATEDBY to set
	 */
	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	/**
	 * @return the cREATED
	 */
	public Date getCREATED() {
		return CREATED;
	}

	/**
	 * @param cREATED
	 *            the cREATED to set
	 */
	public void setCREATED(Date cREATED) {
		CREATED = cREATED;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}

	public Date getLASTMODIFIEDON() {
		return LASTMODIFIEDON;
	}

	public void setLASTMODIFIEDON(Date lASTMODIFIEDON) {
		LASTMODIFIEDON = lASTMODIFIEDON;
	}

	

	/**
	 * @return the vEHICLE_GROUP_ID
	 */
	public long getVEHICLE_GROUP_ID() {
		return VEHICLE_GROUP_ID;
	}

	/**
	 * @param vEHICLE_GROUP_ID the vEHICLE_GROUP_ID to set
	 */
	public void setVEHICLE_GROUP_ID(long vEHICLE_GROUP_ID) {
		VEHICLE_GROUP_ID = vEHICLE_GROUP_ID;
	}

	/**
	 * @return the markForDelete
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete the markForDelete to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Long getCREATEDBYID() {
		return CREATEDBYID;
	}

	public void setCREATEDBYID(Long cREATEDBYID) {
		CREATEDBYID = cREATEDBYID;
	}

	public Long getLASTMODIFIEDBYID() {
		return LASTMODIFIEDBYID;
	}

	public void setLASTMODIFIEDBYID(Long lASTMODIFIEDBYID) {
		LASTMODIFIEDBYID = lASTMODIFIEDBYID;
	}

	public String getCASHBOOK_CODE() {
		return CASHBOOK_CODE;
	}

	public void setCASHBOOK_CODE(String cASHBOOK_CODE) {
		CASHBOOK_CODE = cASHBOOK_CODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CASHBOOK_NAME == null) ? 0 : CASHBOOK_NAME.hashCode());
		result = prime * result + ((NAMEID == null) ? 0 : NAMEID.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CashBookName other = (CashBookName) obj;
		if (CASHBOOK_NAME == null) {
			if (other.CASHBOOK_NAME != null)
				return false;
		} else if (!CASHBOOK_NAME.equals(other.CASHBOOK_NAME))
			return false;
		if (NAMEID == null) {
			if (other.NAMEID != null)
				return false;
		} else if (!NAMEID.equals(other.NAMEID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CashBookName [NAMEID=");
		builder.append(NAMEID);
		builder.append(", CASHBOOK_NAME=");
		builder.append(CASHBOOK_NAME);
		builder.append(", CASHBOOK_REMARKS=");
		builder.append(CASHBOOK_REMARKS);
		builder.append(", CASHBOOK_CODE=");
		builder.append(CASHBOOK_CODE);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", CREATEDBYID=");
		builder.append(CREATEDBYID);
		builder.append(", CREATED=");
		builder.append(CREATED);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);
		builder.append(", LASTMODIFIEDBYID=");
		builder.append(LASTMODIFIEDBYID);
		builder.append(", LASTMODIFIEDON=");
		builder.append(LASTMODIFIEDON);
		builder.append(", VEHICLE_GROUP_ID=");
		builder.append(VEHICLE_GROUP_ID);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}

	

}