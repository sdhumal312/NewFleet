package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CashBookDocument")
public class CashBookDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CASHDOCID")
	private Long CASHDOCID;

	@Column(name = "CASHID")
	private long CASHID;

	@Lob
	@Column(name = "CASH_CONTENT", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] CASH_CONTENT;

	@Column(name = "CASH_CONTENT_TYPE")
	private String CASH_CONTENT_TYPE;

	@Column(name = "CASH_FILENAME")
	private String CASH_FILENAME;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	@Column(name = "CREATEDBY", updatable = false, length = 150)
	private String CREATEDBY;

	@Column(name = "LASTMODIFIEDBY", length = 150)
	private String LASTMODIFIEDBY;

	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "CREATED_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATED_DATE;

	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "LASTUPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date LASTUPDATED_DATE;

	public CashBookDocument() {
		super();
	}

	public CashBookDocument(Long cASHDOCID, long cASHID, byte[] cASH_CONTENT, String cASH_CONTENT_TYPE,
			String cASH_FILENAME, String cREATEDBY, String lASTMODIFIEDBY,  Date cREATED_DATE,
			Date lASTUPDATED_DATE, Integer companyId) {
		super();
		CASHDOCID = cASHDOCID;
		CASHID = cASHID;
		CASH_CONTENT = cASH_CONTENT;
		CASH_CONTENT_TYPE = cASH_CONTENT_TYPE;
		CASH_FILENAME = cASH_FILENAME;
		CREATEDBY = cREATEDBY;
		LASTMODIFIEDBY = lASTMODIFIEDBY;
		CREATED_DATE = cREATED_DATE;
		LASTUPDATED_DATE = lASTUPDATED_DATE;
		COMPANY_ID	= companyId;
	}

	/**
	 * @return the cASHDOCID
	 */
	public Long getCASHDOCID() {
		return CASHDOCID;
	}

	/**
	 * @param cASHDOCID
	 *            the cASHDOCID to set
	 */
	public void setCASHDOCID(Long cASHDOCID) {
		CASHDOCID = cASHDOCID;
	}

	/**
	 * @return the cASHID
	 */
	public long getCASHID() {
		return CASHID;
	}

	/**
	 * @param cASHID
	 *            the cASHID to set
	 */
	public void setCASHID(long cASHID) {
		CASHID = cASHID;
	}

	/**
	 * @return the cASH_CONTENT
	 */
	public byte[] getCASH_CONTENT() {
		return CASH_CONTENT;
	}

	/**
	 * @param cASH_CONTENT
	 *            the cASH_CONTENT to set
	 */
	public void setCASH_CONTENT(byte[] cASH_CONTENT) {
		CASH_CONTENT = cASH_CONTENT;
	}

	/**
	 * @return the cASH_CONTENT_TYPE
	 */
	public String getCASH_CONTENT_TYPE() {
		return CASH_CONTENT_TYPE;
	}

	/**
	 * @param cASH_CONTENT_TYPE
	 *            the cASH_CONTENT_TYPE to set
	 */
	public void setCASH_CONTENT_TYPE(String cASH_CONTENT_TYPE) {
		CASH_CONTENT_TYPE = cASH_CONTENT_TYPE;
	}

	/**
	 * @return the cASH_FILENAME
	 */
	public String getCASH_FILENAME() {
		return CASH_FILENAME;
	}

	/**
	 * @param cASH_FILENAME
	 *            the cASH_FILENAME to set
	 */
	public void setCASH_FILENAME(String cASH_FILENAME) {
		CASH_FILENAME = cASH_FILENAME;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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
	 * @return the lASTMODIFIEDBY
	 */
	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	/**
	 * @param lASTMODIFIEDBY
	 *            the lASTMODIFIEDBY to set
	 */
	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}


	/**
	 * @return the cREATED_DATE
	 */
	public Date getCREATED_DATE() {
		return CREATED_DATE;
	}

	/**
	 * @return the markForDelete
	 */
	public boolean ismarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete the markForDelete to set
	 */
	public void setmarkForDelete(boolean mARKFORDELETE) {
		markForDelete = mARKFORDELETE;
	}

	/**
	 * @param cREATED_DATE
	 *            the cREATED_DATE to set
	 */
	public void setCREATED_DATE(Date cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	/**
	 * @return the lASTUPDATED_DATE
	 */
	public Date getLASTUPDATED_DATE() {
		return LASTUPDATED_DATE;
	}

	/**
	 * @param lASTUPDATED_DATE
	 *            the lASTUPDATED_DATE to set
	 */
	public void setLASTUPDATED_DATE(Date lASTUPDATED_DATE) {
		LASTUPDATED_DATE = lASTUPDATED_DATE;
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
		result = prime * result + ((CASHDOCID == null) ? 0 : CASHDOCID.hashCode());
		result = prime * result + (int) (CASHID ^ (CASHID >>> 32));
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
		CashBookDocument other = (CashBookDocument) obj;
		if (CASHDOCID == null) {
			if (other.CASHDOCID != null)
				return false;
		} else if (!CASHDOCID.equals(other.CASHDOCID))
			return false;
		if (CASHID != other.CASHID)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CashBookDocument [CASHDOCID=");
		builder.append(CASHDOCID);
		builder.append(", CASHID=");
		builder.append(CASHID);
		builder.append(", CASH_CONTENT=");
		builder.append(Arrays.toString(CASH_CONTENT));
		builder.append(", CASH_CONTENT_TYPE=");
		builder.append(CASH_CONTENT_TYPE);
		builder.append(", CASH_FILENAME=");
		builder.append(CASH_FILENAME);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", LASTUPDATED_DATE=");
		builder.append(LASTUPDATED_DATE);
		builder.append("]");
		return builder.toString();
	}

}