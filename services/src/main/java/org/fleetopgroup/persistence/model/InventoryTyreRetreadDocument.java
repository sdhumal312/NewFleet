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

@Entity
@Table(name = "InventoryTyreRetreadDocument")
public class InventoryTyreRetreadDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRDOCID")
	private Long TRDOCID;

	@Column(name = "TRID")
	private Long TRID;

	@Lob
	@Column(name = "TR_CONTENT", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] TR_CONTENT;

	@Column(name = "TR_CONTENT_TYRE")
	private String TR_CONTENT_TYRE;

	@Column(name = "TR_FILENAME")
	private String TR_FILENAME;

	@Column(name = "CREATED_BY", length = 200)
	private String CREATED_BY;

	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	private Date CREATED_DATE;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete ;

	public InventoryTyreRetreadDocument() {
		super();
	}

	public InventoryTyreRetreadDocument(Long tRDOCID, Long tRID, byte[] tR_CONTENT, String tR_CONTENT_TYRE,
			String tR_FILENAME, String cREATED_BY, Date cREATED_DATE, Integer companyid) {
		super();
		TRDOCID = tRDOCID;
		TRID = tRID;
		TR_CONTENT = tR_CONTENT;
		TR_CONTENT_TYRE = tR_CONTENT_TYRE;
		TR_FILENAME = tR_FILENAME;
		CREATED_BY = cREATED_BY;
		CREATED_DATE = cREATED_DATE;
		COMPANY_ID = companyid;
	}

	/**
	 * @return the tRDOCID
	 */
	public Long getTRDOCID() {
		return TRDOCID;
	}

	/**
	 * @param tRDOCID
	 *            the tRDOCID to set
	 */
	public void setTRDOCID(Long tRDOCID) {
		TRDOCID = tRDOCID;
	}

	/**
	 * @return the tRID
	 */
	public Long getTRID() {
		return TRID;
	}

	/**
	 * @param tRID
	 *            the tRID to set
	 */
	public void setTRID(Long tRID) {
		TRID = tRID;
	}

	/**
	 * @return the tR_CONTENT
	 */
	public byte[] getTR_CONTENT() {
		return TR_CONTENT;
	}

	/**
	 * @param tR_CONTENT
	 *            the tR_CONTENT to set
	 */
	public void setTR_CONTENT(byte[] tR_CONTENT) {
		TR_CONTENT = tR_CONTENT;
	}

	/**
	 * @return the tR_CONTENT_TYRE
	 */
	public String getTR_CONTENT_TYRE() {
		return TR_CONTENT_TYRE;
	}

	/**
	 * @param tR_CONTENT_TYRE
	 *            the tR_CONTENT_TYRE to set
	 */
	public void setTR_CONTENT_TYRE(String tR_CONTENT_TYRE) {
		TR_CONTENT_TYRE = tR_CONTENT_TYRE;
	}

	/**
	 * @return the tR_FILENAME
	 */
	public String getTR_FILENAME() {
		return TR_FILENAME;
	}

	/**
	 * @param tR_FILENAME
	 *            the tR_FILENAME to set
	 */
	public void setTR_FILENAME(String tR_FILENAME) {
		TR_FILENAME = tR_FILENAME;
	}

	/**
	 * @return the cREATED_BY
	 */
	public String getCREATED_BY() {
		return CREATED_BY;
	}

	/**
	 * @param cREATED_BY
	 *            the cREATED_BY to set
	 */
	public void setCREATED_BY(String cREATED_BY) {
		CREATED_BY = cREATED_BY;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
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
		result = prime * result + ((TRDOCID == null) ? 0 : TRDOCID.hashCode());
		result = prime * result + ((TRID == null) ? 0 : TRID.hashCode());
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
		InventoryTyreRetreadDocument other = (InventoryTyreRetreadDocument) obj;
		if (TRDOCID == null) {
			if (other.TRDOCID != null)
				return false;
		} else if (!TRDOCID.equals(other.TRDOCID))
			return false;
		if (TRID == null) {
			if (other.TRID != null)
				return false;
		} else if (!TRID.equals(other.TRID))
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
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryTyreRetreadDocument [TRDOCID=");
		builder.append(TRDOCID);
		builder.append(", TRID=");
		builder.append(TRID);
		builder.append(", TR_CONTENT=");
		builder.append(TR_CONTENT != null
				? Arrays.toString(Arrays.copyOf(TR_CONTENT, Math.min(TR_CONTENT.length, maxLen))) : null);
		builder.append(", TR_CONTENT_TYRE=");
		builder.append(TR_CONTENT_TYRE);
		builder.append(", TR_FILENAME=");
		builder.append(TR_FILENAME);
		builder.append(", CREATED_BY=");
		builder.append(CREATED_BY);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append("]");
		return builder.toString();
	}

}