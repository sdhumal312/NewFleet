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
@Table(name = "IssuesPhoto")
public class IssuesPhoto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ISSUE_PHOTOID")
	private Long ISSUE_PHOTOID;

	/** This value Issue photo name */
	@Column(name = "ISSUE_PHOTONAME", length = 250)
	private String ISSUE_PHOTONAME;

	/** This value Issue photo upload date  */
	@Basic(optional = false)
	@Column(name = "ISSUE_UPLOADDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ISSUE_UPLOADDATE;
	

	/** This value Issue photo file name  */
	@Column(name = "ISSUE_FILENAME")
	private String ISSUE_FILENAME;

	/** This value Issue Id  which issues photo id name   */
	@Column(name = "ISSUE_ID")
	private Long ISSUE_ID;

	@Lob
	@Column(name = "ISSUE_CONTENT", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] ISSUE_CONTENT;

	/** This value Issue content type name   */
	@Column(name = "ISSUE_CONTENTTYPE")
	private String ISSUE_CONTENTTYPE;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;
	
	@Column(name = "CREATEDBYID")
	private Long CREATEDBYID;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public IssuesPhoto() {
		super();
	}

	public IssuesPhoto(Long iSSUE_PHOTOID, String iSSUE_PHOTONAME, Date iSSUE_UPLOADDATE, String iSSUE_FILENAME,
			Long iSSUE_ID, byte[] iSSUE_CONTENT, String iSSUE_CONTENTTYPE, Integer cOMPANY_ID) {
		super();
		ISSUE_PHOTOID = iSSUE_PHOTOID;
		ISSUE_PHOTONAME = iSSUE_PHOTONAME;
		ISSUE_UPLOADDATE = iSSUE_UPLOADDATE;
		ISSUE_FILENAME = iSSUE_FILENAME;
		ISSUE_ID = iSSUE_ID;
		ISSUE_CONTENT = iSSUE_CONTENT;
		ISSUE_CONTENTTYPE = iSSUE_CONTENTTYPE;
		COMPANY_ID	= cOMPANY_ID;
	}

	/**
	 * @return the iSSUE_PHOTOID
	 */
	public Long getISSUE_PHOTOID() {
		return ISSUE_PHOTOID;
	}

	/**
	 * @param iSSUE_PHOTOID the iSSUE_PHOTOID to set
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
	 * @param iSSUE_PHOTONAME the iSSUE_PHOTONAME to set
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
	 * @param iSSUE_UPLOADDATE the iSSUE_UPLOADDATE to set
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
	 * @param iSSUE_FILENAME the iSSUE_FILENAME to set
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
	 * @param iSSUE_ID the iSSUE_ID to set
	 */
	public void setISSUE_ID(Long iSSUE_ID) {
		ISSUE_ID = iSSUE_ID;
	}

	/**
	 * @return the iSSUE_CONTENT
	 */
	public byte[] getISSUE_CONTENT() {
		return ISSUE_CONTENT;
	}

	/**
	 * @param iSSUE_CONTENT the iSSUE_CONTENT to set
	 */
	public void setISSUE_CONTENT(byte[] iSSUE_CONTENT) {
		ISSUE_CONTENT = iSSUE_CONTENT;
	}

	/**
	 * @return the iSSUE_CONTENTTYPE
	 */
	public String getISSUE_CONTENTTYPE() {
		return ISSUE_CONTENTTYPE;
	}

	/**
	 * @param iSSUE_CONTENTTYPE the iSSUE_CONTENTTYPE to set
	 */
	public void setISSUE_CONTENTTYPE(String iSSUE_CONTENTTYPE) {
		ISSUE_CONTENTTYPE = iSSUE_CONTENTTYPE;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/* (non-Javadoc)
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

	/* (non-Javadoc)
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
		IssuesPhoto other = (IssuesPhoto) obj;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("IssuesPhoto [ISSUE_PHOTOID=");
		builder.append(ISSUE_PHOTOID);
		builder.append(", ISSUE_PHOTONAME=");
		builder.append(ISSUE_PHOTONAME);
		builder.append(", ISSUE_UPLOADDATE=");
		builder.append(ISSUE_UPLOADDATE);
		builder.append(", ISSUE_FILENAME=");
		builder.append(ISSUE_FILENAME);
		builder.append(", ISSUE_ID=");
		builder.append(ISSUE_ID);
		builder.append(", ISSUE_CONTENT=");
		builder.append(ISSUE_CONTENT != null
				? Arrays.toString(Arrays.copyOf(ISSUE_CONTENT, Math.min(ISSUE_CONTENT.length, maxLen))) : null);
		builder.append(", ISSUE_CONTENTTYPE=");
		builder.append(ISSUE_CONTENTTYPE);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append("]");
		return builder.toString();
	}

	

}
