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
@Table(name = "VendorDocument")
public class VendorDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VDID")
	private Long VDID;

	@Column(name = "VENDOR_DOCNAME", length = 45)
	private String VENDOR_DOCNAME;

	@Column(name = "VENDOR_FILENAME")
	private String VENDOR_FILENAME;

	@Column(name = "VENDORID")
	private Integer VENDORID;

	@Lob
	@Column(name = "VENDOR_CONTENT", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] VENDOR_CONTENT;

	@Column(name = "VENDOR_CONTENTTYPE")
	private String VENDOR_CONTENTTYPE;

	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	/*@Column(name = "CREATEDBY", updatable = false, length = 150)
	private String CREATEDBY;

	@Column(name = "LASTMODIFIEDBY", length = 150)
	private String LASTMODIFIEDBY;*/
	
	@Column(name = "CREATEDBYID")
	private Long CREATEDBYID;
	
	@Column(name = "LASTMODIFIEDBYID")
	private Long LASTMODIFIEDBYID;

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

	public VendorDocument() {
		super();
	}

	public VendorDocument(Long vDID, String vENDOR_DOCNAME, String vENDOR_FILENAME, Integer vENDORID,
			byte[] vENDOR_CONTENT, String vENDOR_CONTENTTYPE, Long cREATEDBY,
			Long lASTMODIFIEDBY, boolean MarkForDelete, Date cREATED_DATE, Date lASTUPDATED_DATE, Integer cOMPANY_ID) {
		super();
		VDID = vDID;
		VENDOR_DOCNAME = vENDOR_DOCNAME;
		VENDOR_FILENAME = vENDOR_FILENAME;
		VENDORID = vENDORID;
		VENDOR_CONTENT = vENDOR_CONTENT;
		VENDOR_CONTENTTYPE = vENDOR_CONTENTTYPE;
		CREATEDBYID = cREATEDBY;
		LASTMODIFIEDBYID = lASTMODIFIEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		LASTUPDATED_DATE = lASTUPDATED_DATE;
		COMPANY_ID = cOMPANY_ID;
	}

	/**
	 * @return the vDID
	 */
	public Long getVDID() {
		return VDID;
	}

	/**
	 * @param vDID
	 *            the vDID to set
	 */
	public void setVDID(Long vDID) {
		VDID = vDID;
	}

	/**
	 * @return the vENDOR_DOCNAME
	 */
	public String getVENDOR_DOCNAME() {
		return VENDOR_DOCNAME;
	}

	/**
	 * @param vENDOR_DOCNAME
	 *            the vENDOR_DOCNAME to set
	 */
	public void setVENDOR_DOCNAME(String vENDOR_DOCNAME) {
		VENDOR_DOCNAME = vENDOR_DOCNAME;
	}

	/**
	 * @return the vENDOR_FILENAME
	 */
	public String getVENDOR_FILENAME() {
		return VENDOR_FILENAME;
	}

	/**
	 * @param vENDOR_FILENAME
	 *            the vENDOR_FILENAME to set
	 */
	public void setVENDOR_FILENAME(String vENDOR_FILENAME) {
		VENDOR_FILENAME = vENDOR_FILENAME;
	}

	/**
	 * @return the vENDORID
	 */
	public Integer getVENDORID() {
		return VENDORID;
	}

	/**
	 * @param vENDORID
	 *            the vENDORID to set
	 */
	public void setVENDORID(Integer vENDORID) {
		VENDORID = vENDORID;
	}

	/**
	 * @return the vENDOR_CONTENT
	 */
	public byte[] getVENDOR_CONTENT() {
		return VENDOR_CONTENT;
	}

	/**
	 * @param vENDOR_CONTENT
	 *            the vENDOR_CONTENT to set
	 */
	public void setVENDOR_CONTENT(byte[] vENDOR_CONTENT) {
		VENDOR_CONTENT = vENDOR_CONTENT;
	}

	/**
	 * @return the vENDOR_CONTENTTYPE
	 */
	public String getVENDOR_CONTENTTYPE() {
		return VENDOR_CONTENTTYPE;
	}

	/**
	 * @param vENDOR_CONTENTTYPE
	 *            the vENDOR_CONTENTTYPE to set
	 */
	public void setVENDOR_CONTENTTYPE(String vENDOR_CONTENTTYPE) {
		VENDOR_CONTENTTYPE = vENDOR_CONTENTTYPE;
	}


	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	/**
	 * @return the cREATEDBY
	 *//*
	public String getCREATEDBY() {
		return CREATEDBY;
	}

	*//**
	 * @param cREATEDBY
	 *            the cREATEDBY to set
	 *//*
	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	*//**
	 * @return the lASTMODIFIEDBY
	 *//*
	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	*//**
	 * @param lASTMODIFIEDBY
	 *            the lASTMODIFIEDBY to set
	 *//*
	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}
*/
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
		result = prime * result + ((VDID == null) ? 0 : VDID.hashCode());
		result = prime * result + ((VENDORID == null) ? 0 : VENDORID.hashCode());
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
		VendorDocument other = (VendorDocument) obj;
		if (VDID == null) {
			if (other.VDID != null)
				return false;
		} else if (!VDID.equals(other.VDID))
			return false;
		if (VENDORID == null) {
			if (other.VENDORID != null)
				return false;
		} else if (!VENDORID.equals(other.VENDORID))
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
		builder.append("VendorDocument [VDID=");
		builder.append(VDID);
		builder.append(", VENDOR_DOCNAME=");
		builder.append(VENDOR_DOCNAME);
		builder.append(", VENDOR_FILENAME=");
		builder.append(VENDOR_FILENAME);
		builder.append(", VENDORID=");
		builder.append(VENDORID);
		builder.append(", VENDOR_CONTENT=");
		builder.append(VENDOR_CONTENT);
		builder.append(", VENDOR_CONTENTTYPE=");
		builder.append(VENDOR_CONTENTTYPE);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", CREATEDBYID=");
		builder.append(CREATEDBYID);
		builder.append(", LASTMODIFIEDBYID=");
		builder.append(LASTMODIFIEDBYID);
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
