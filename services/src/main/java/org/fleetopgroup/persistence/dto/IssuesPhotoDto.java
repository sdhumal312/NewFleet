package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class IssuesPhotoDto {

	private Long ISSUE_PHOTOID;

	/** The value for the ISSUES_PHOTOID_ENCRYPT field */
	private String ISSUES_PHOTOID_ENCRYPT;

	/** This value Issue photo name */
	private String ISSUE_PHOTONAME;

	/** This value Issue photo upload date */
	private Date ISSUE_UPLOADDATE;

	/** This value Issue photo file name */
	private String ISSUE_FILENAME;

	/** This value Issue Id which issues photo id name */
	private Long ISSUE_ID;

	/** The value for the ISSUES_ID_ENCRYPT field */
	private String ISSUES_ID_ENCRYPT;

	/** This value Issue content type name */
	private String ISSUE_CONTENTTYPE;

	private String ISSUE_UPLOADDATE_STR;
	
	public IssuesPhotoDto() {
		super();
	}

	public IssuesPhotoDto(Long iSSUE_PHOTOID, String iSSUES_PHOTOID_ENCRYPT, String iSSUE_PHOTONAME,
			Date iSSUE_UPLOADDATE, String iSSUE_FILENAME, Long iSSUE_ID, String iSSUES_ID_ENCRYPT,
			String iSSUE_CONTENTTYPE) {
		super();
		ISSUE_PHOTOID = iSSUE_PHOTOID;
		ISSUES_PHOTOID_ENCRYPT = iSSUES_PHOTOID_ENCRYPT;
		ISSUE_PHOTONAME = iSSUE_PHOTONAME;
		ISSUE_UPLOADDATE = iSSUE_UPLOADDATE;
		ISSUE_FILENAME = iSSUE_FILENAME;
		ISSUE_ID = iSSUE_ID;
		ISSUES_ID_ENCRYPT = iSSUES_ID_ENCRYPT;
		ISSUE_CONTENTTYPE = iSSUE_CONTENTTYPE;
	}

	/**
	 * @return the iSSUE_PHOTOID
	 */
	public Long getISSUE_PHOTOID() {
		return ISSUE_PHOTOID;
	}

	/**
	 * @param iSSUE_PHOTOID
	 *            the iSSUE_PHOTOID to set
	 */
	public void setISSUE_PHOTOID(Long iSSUE_PHOTOID) {
		ISSUE_PHOTOID = iSSUE_PHOTOID;
	}

	/**
	 * @return the iSSUE_PHOTONAME
	 */
	public String getISSUE_PHOTONAME() {
		return ISSUE_PHOTONAME;
	}

	/**
	 * @param iSSUE_PHOTONAME
	 *            the iSSUE_PHOTONAME to set
	 */
	public void setISSUE_PHOTONAME(String iSSUE_PHOTONAME) {
		ISSUE_PHOTONAME = iSSUE_PHOTONAME;
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
	 * @return the iSSUES_PHOTOID_ENCRYPT
	 */
	public String getISSUES_PHOTOID_ENCRYPT() {
		return ISSUES_PHOTOID_ENCRYPT;
	}

	/**
	 * @param iSSUES_PHOTOID_ENCRYPT
	 *            the iSSUES_PHOTOID_ENCRYPT to set
	 */
	public void setISSUES_PHOTOID_ENCRYPT(String iSSUES_PHOTOID_ENCRYPT) {
		ISSUES_PHOTOID_ENCRYPT = iSSUES_PHOTOID_ENCRYPT;
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
		result = prime * result + ((ISSUE_ID == null) ? 0 : ISSUE_ID.hashCode());
		result = prime * result + ((ISSUE_PHOTOID == null) ? 0 : ISSUE_PHOTOID.hashCode());
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
		IssuesPhotoDto other = (IssuesPhotoDto) obj;
		if (ISSUE_ID == null) {
			if (other.ISSUE_ID != null)
				return false;
		} else if (!ISSUE_ID.equals(other.ISSUE_ID))
			return false;
		if (ISSUE_PHOTOID == null) {
			if (other.ISSUE_PHOTOID != null)
				return false;
		} else if (!ISSUE_PHOTOID.equals(other.ISSUE_PHOTOID))
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
		builder.append("IssuesPhotoDto [ISSUE_PHOTOID=");
		builder.append(ISSUE_PHOTOID);
		builder.append(", ISSUES_PHOTOID_ENCRYPT=");
		builder.append(ISSUES_PHOTOID_ENCRYPT);
		builder.append(", ISSUE_PHOTONAME=");
		builder.append(ISSUE_PHOTONAME);
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
