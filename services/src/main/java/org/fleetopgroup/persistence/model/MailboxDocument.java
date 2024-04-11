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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MailboxDocument")
public class MailboxDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MAILDOCID")
	private Long MAILDOCID;

	@Column(name = "MAILBOX_ID")
	private long MAILBOX_ID;

	@Lob
	@Column(name = "MAIL_CONTENT", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] MAIL_CONTENT;

	@Column(name = "MAIL_CONTENT_TYPE")
	private String MAIL_CONTENT_TYPE;

	@Column(name = "MAIL_FILENAME")
	private String MAIL_FILENAME;

	@Column(name = "CREATEDBY", updatable = false, length = 150)
	private String CREATEDBY;

	@Column(name = "LASTMODIFIEDBY", length = 150)
	private String LASTMODIFIEDBY;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

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

	public MailboxDocument() {
		super();
	}

	public MailboxDocument(Long mAILDOCID, long mAILBOX_ID, byte[] mAIL_CONTENT, String mAIL_CONTENT_TYPE,
			String mAIL_FILENAME, String cREATEDBY, String lASTMODIFIEDBY, boolean MarkForDelete, Date cREATED_DATE,
			Date lASTUPDATED_DATE) {
		super();
		MAILDOCID = mAILDOCID;
		MAILBOX_ID = mAILBOX_ID;
		MAIL_CONTENT = mAIL_CONTENT;
		MAIL_CONTENT_TYPE = mAIL_CONTENT_TYPE;
		MAIL_FILENAME = mAIL_FILENAME;
		CREATEDBY = cREATEDBY;
		LASTMODIFIEDBY = lASTMODIFIEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}

	/**
	 * @return the mAILDOCID
	 */
	public Long getMAILDOCID() {
		return MAILDOCID;
	}

	/**
	 * @param mAILDOCID
	 *            the mAILDOCID to set
	 */
	public void setMAILDOCID(Long mAILDOCID) {
		MAILDOCID = mAILDOCID;
	}

	/**
	 * @return the mAILBOX_ID
	 */
	public long getMAILBOX_ID() {
		return MAILBOX_ID;
	}

	/**
	 * @param mAILBOX_ID
	 *            the mAILBOX_ID to set
	 */
	public void setMAILBOX_ID(long mAILBOX_ID) {
		MAILBOX_ID = mAILBOX_ID;
	}

	/**
	 * @return the mAIL_CONTENT
	 */
	public byte[] getMAIL_CONTENT() {
		return MAIL_CONTENT;
	}

	/**
	 * @param mAIL_CONTENT
	 *            the mAIL_CONTENT to set
	 */
	public void setMAIL_CONTENT(byte[] mAIL_CONTENT) {
		MAIL_CONTENT = mAIL_CONTENT;
	}

	/**
	 * @return the mAIL_CONTENT_TYPE
	 */
	public String getMAIL_CONTENT_TYPE() {
		return MAIL_CONTENT_TYPE;
	}

	/**
	 * @param mAIL_CONTENT_TYPE
	 *            the mAIL_CONTENT_TYPE to set
	 */
	public void setMAIL_CONTENT_TYPE(String mAIL_CONTENT_TYPE) {
		MAIL_CONTENT_TYPE = mAIL_CONTENT_TYPE;
	}

	/**
	 * @return the mAIL_FILENAME
	 */
	public String getMAIL_FILENAME() {
		return MAIL_FILENAME;
	}

	/**
	 * @param mAIL_FILENAME
	 *            the mAIL_FILENAME to set
	 */
	public void setMAIL_FILENAME(String mAIL_FILENAME) {
		MAIL_FILENAME = mAIL_FILENAME;
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
	 * @return the sTATUS
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param sTATUS
	 *            the sTATUS to set
	 */
	public void setMarkForDelete(boolean MarkForDelete) {
		markForDelete = MarkForDelete;
	}

	/**
	 * @return the cREATED_DATE
	 */
	public Date getCREATED_DATE() {
		return CREATED_DATE;
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
		result = prime * result + (int) (MAILBOX_ID ^ (MAILBOX_ID >>> 32));
		result = prime * result + ((MAILDOCID == null) ? 0 : MAILDOCID.hashCode());
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
		MailboxDocument other = (MailboxDocument) obj;
		if (MAILBOX_ID != other.MAILBOX_ID)
			return false;
		if (MAILDOCID == null) {
			if (other.MAILDOCID != null)
				return false;
		} else if (!MAILDOCID.equals(other.MAILDOCID))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MailboxDocument [MAILDOCID=");
		builder.append(MAILDOCID);
		builder.append(", MAILBOX_ID=");
		builder.append(MAILBOX_ID);
		builder.append(", MAIL_CONTENT=");
		builder.append(MAIL_CONTENT);
		builder.append(", MAIL_CONTENT_TYPE=");
		builder.append(MAIL_CONTENT_TYPE);
		builder.append(", MAIL_FILENAME=");
		builder.append(MAIL_FILENAME);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", LASTUPDATED_DATE=");
		builder.append(LASTUPDATED_DATE);
		builder.append("]");
		return builder.toString();
	}

}
