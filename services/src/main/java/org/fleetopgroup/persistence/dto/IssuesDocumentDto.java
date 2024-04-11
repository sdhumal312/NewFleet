package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class IssuesDocumentDto {

	private Long ISSUE_DOCUMENTID;

	private String ISSUE_DOCUMENTID_ENCRYPT;

	/** Issues Document name */
	private String ISSUE_DOCUMENTNAME;

	/** Issues Document upload date */
	private Date ISSUE_UPLOADDATE;

	/** Issues Document file_name date */
	private String ISSUE_FILENAME;

	/** Issues Id in which issues document */
	private Long ISSUE_ID;

	/** The value for the ISSUES_ID_ENCRYPT field */
	private String ISSUES_ID_ENCRYPT;

	/** Issues document type */
	private String ISSUE_CONTENTTYPE;
	
	private String ISSUE_UPLOADDATE_STR;

	public IssuesDocumentDto() {
		super();
	}

	public IssuesDocumentDto(Long iSSUE_DOCUMENTID, String iSSUE_DOCUMENTID_ENCRYPT, String iSSUE_DOCUMENTNAME,
			Date iSSUE_UPLOADDATE, String iSSUE_FILENAME, Long iSSUE_ID, String iSSUE_CONTENTTYPE) {
		super();
		ISSUE_DOCUMENTID = iSSUE_DOCUMENTID;
		ISSUE_DOCUMENTID_ENCRYPT = iSSUE_DOCUMENTID_ENCRYPT;
		ISSUE_DOCUMENTNAME = iSSUE_DOCUMENTNAME;
		ISSUE_UPLOADDATE = iSSUE_UPLOADDATE;
		ISSUE_FILENAME = iSSUE_FILENAME;
		ISSUE_ID = iSSUE_ID;
		ISSUE_CONTENTTYPE = iSSUE_CONTENTTYPE;
	}

	/**
	 * @return the iSSUE_DOCUMENTID_ENCRYPT
	 */
	public String getISSUE_DOCUMENTID_ENCRYPT() {
		return ISSUE_DOCUMENTID_ENCRYPT;
	}

	/**
	 * @param iSSUE_DOCUMENTID_ENCRYPT
	 *            the iSSUE_DOCUMENTID_ENCRYPT to set
	 */
	public void setISSUE_DOCUMENTID_ENCRYPT(String iSSUE_DOCUMENTID_ENCRYPT) {
		ISSUE_DOCUMENTID_ENCRYPT = iSSUE_DOCUMENTID_ENCRYPT;
	}

	/**
	 * @return the iSSUE_DOCUMENTID
	 */
	public Long getISSUE_DOCUMENTID() {
		return ISSUE_DOCUMENTID;
	}

	/**
	 * @param iSSUE_DOCUMENTID
	 *            the iSSUE_DOCUMENTID to set
	 */
	public void setISSUE_DOCUMENTID(Long iSSUE_DOCUMENTID) {
		ISSUE_DOCUMENTID = iSSUE_DOCUMENTID;
	}

	/**
	 * @return the iSSUE_DOCUMENTNAME
	 */
	public String getISSUE_DOCUMENTNAME() {
		return ISSUE_DOCUMENTNAME;
	}

	/**
	 * @param iSSUE_DOCUMENTNAME
	 *            the iSSUE_DOCUMENTNAME to set
	 */
	public void setISSUE_DOCUMENTNAME(String iSSUE_DOCUMENTNAME) {
		ISSUE_DOCUMENTNAME = iSSUE_DOCUMENTNAME;
	}

	/**
	 * @return the iSSUE_UPLOADDATE
	 */
	public Date getISSUE_UPLOADDATE() {
		return ISSUE_UPLOADDATE;
	}

	/**
	 * @param iSSUE_UPLOADDATE
	 *            the iSSUE_UPLOADDATE to set
	 */
	public void setISSUE_UPLOADDATE(Date iSSUE_UPLOADDATE) {
		ISSUE_UPLOADDATE = iSSUE_UPLOADDATE;
	}

	/**
	 * @return the iSSUE_FILENAME
	 */
	public String getISSUE_FILENAME() {
		return ISSUE_FILENAME;
	}

	/**
	 * @param iSSUE_FILENAME
	 *            the iSSUE_FILENAME to set
	 */
	public void setISSUE_FILENAME(String iSSUE_FILENAME) {
		ISSUE_FILENAME = iSSUE_FILENAME;
	}

	/**
	 * @return the iSSUE_ID
	 */
	public Long getISSUE_ID() {
		return ISSUE_ID;
	}

	/**
	 * @param iSSUE_ID
	 *            the iSSUE_ID to set
	 */
	public void setISSUE_ID(Long iSSUE_ID) {
		ISSUE_ID = iSSUE_ID;
	}

	/**
	 * @return the iSSUE_CONTENTTYPE
	 */
	public String getISSUE_CONTENTTYPE() {
		return ISSUE_CONTENTTYPE;
	}

	/**
	 * @param iSSUE_CONTENTTYPE
	 *            the iSSUE_CONTENTTYPE to set
	 */
	public void setISSUE_CONTENTTYPE(String iSSUE_CONTENTTYPE) {
		ISSUE_CONTENTTYPE = iSSUE_CONTENTTYPE;
	}

	/**
	 * @return the iSSUES_ID_ENCRYPT
	 */
	public String getISSUES_ID_ENCRYPT() {
		return ISSUES_ID_ENCRYPT;
	}

	/**
	 * @param iSSUES_ID_ENCRYPT
	 *            the iSSUES_ID_ENCRYPT to set
	 */
	public void setISSUES_ID_ENCRYPT(String iSSUES_ID_ENCRYPT) {
		ISSUES_ID_ENCRYPT = iSSUES_ID_ENCRYPT;
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
		result = prime * result + ((ISSUE_DOCUMENTID == null) ? 0 : ISSUE_DOCUMENTID.hashCode());
		result = prime * result + ((ISSUE_ID == null) ? 0 : ISSUE_ID.hashCode());
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
		IssuesDocumentDto other = (IssuesDocumentDto) obj;
		if (ISSUE_DOCUMENTID == null) {
			if (other.ISSUE_DOCUMENTID != null)
				return false;
		} else if (!ISSUE_DOCUMENTID.equals(other.ISSUE_DOCUMENTID))
			return false;
		if (ISSUE_ID == null) {
			if (other.ISSUE_ID != null)
				return false;
		} else if (!ISSUE_ID.equals(other.ISSUE_ID))
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
		builder.append("IssuesDocumentDto [ISSUE_DOCUMENTID=");
		builder.append(ISSUE_DOCUMENTID);
		builder.append(", ISSUE_DOCUMENTID_ENCRYPT=");
		builder.append(ISSUE_DOCUMENTID_ENCRYPT);
		builder.append(", ISSUE_DOCUMENTNAME=");
		builder.append(ISSUE_DOCUMENTNAME);
		builder.append(", ISSUE_UPLOADDATE=");
		builder.append(ISSUE_UPLOADDATE);
		builder.append(", ISSUE_FILENAME=");
		builder.append(ISSUE_FILENAME);
		builder.append(", ISSUE_ID=");
		builder.append(ISSUE_ID);
		builder.append(", ISSUES_ID_ENCRYPT=");
		builder.append(ISSUES_ID_ENCRYPT);
		builder.append(", ISSUE_CONTENTTYPE=");
		builder.append(ISSUE_CONTENTTYPE);
		builder.append("]");
		return builder.toString();
	}

	public String getISSUE_UPLOADDATE_STR() {
		return ISSUE_UPLOADDATE_STR;
	}

	public void setISSUE_UPLOADDATE_STR(String iSSUE_UPLOADDATE_STR) {
		ISSUE_UPLOADDATE_STR = iSSUE_UPLOADDATE_STR;
	}

}
