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
@Table(name = "TyreDocument")
public class TyreDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tyredocid")
	private Long tyredocid;

	@Column(name = "tyre_id")
	private Long tyre_id;

	@Lob
	@Column(name = "tyre_content", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] tyre_content;

	@Column(name = "tyre_content_type")
	private String tyre_contentType;

	@Column(name = "tyre_filename")
	private String tyre_filename;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdById")
	private Long createdById;
	
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	@Basic(optional = false)
	@Column(name = "created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Basic(optional = false)
	@Column(name = "lastupdated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdated;

	public TyreDocument() {
		super();
	}

	public TyreDocument(Long tyredocid, Long tyre_id, byte[] tyre_content, String tyre_contentType,
			String tyre_filename, Long createdBy, Long lastModifiedBy, boolean MarkForDelete, Date created,
			Date lastupdated, Integer companyId) {
		super();
		this.tyredocid = tyredocid;
		this.tyre_id = tyre_id;
		this.tyre_content = tyre_content;
		this.tyre_contentType = tyre_contentType;
		this.tyre_filename = tyre_filename;
		this.createdById = createdBy;
		this.lastModifiedById = lastModifiedBy;
		this.markForDelete = MarkForDelete;
		this.created = created;
		this.lastupdated = lastupdated;
		this.companyId = companyId;
	}



	public Long getTyredocid() {
		return tyredocid;
	}

	public void setTyredocid(Long tyredocid) {
		this.tyredocid = tyredocid;
	}

	public Long getTyre_id() {
		return tyre_id;
	}

	public void setTyre_id(Long tyre_id) {
		this.tyre_id = tyre_id;
	}

	public byte[] getTyre_content() {
		return tyre_content;
	}

	public void setTyre_content(byte[] tyre_content) {
		this.tyre_content = tyre_content;
	}

	public String getTyre_contentType() {
		return tyre_contentType;
	}

	public void setTyre_contentType(String tyre_contentType) {
		this.tyre_contentType = tyre_contentType;
	}

	public String getTyre_filename() {
		return tyre_filename;
	}

	public void setTyre_filename(String tyre_filename) {
		this.tyre_filename = tyre_filename;
	}

	/**
	 * @return the createdBy
	 *//*
	public String getCreatedBy() {
		return createdBy;
	}

	*//**
	 * @param createdBy
	 *            the createdBy to set
	 *//*
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	*//**
	 * @return the lastModifiedBy
	 *//*
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	*//**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 *//*
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
*/
	/**
	 * @return the status
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public Date getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
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
		result = prime * result + ((tyre_id == null) ? 0 : tyre_id.hashCode());
		result = prime * result + ((tyredocid == null) ? 0 : tyredocid.hashCode());
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
		TyreDocument other = (TyreDocument) obj;
		if (tyre_id == null) {
			if (other.tyre_id != null)
				return false;
		} else if (!tyre_id.equals(other.tyre_id))
			return false;
		if (tyredocid == null) {
			if (other.tyredocid != null)
				return false;
		} else if (!tyredocid.equals(other.tyredocid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TyreDocument [tyredocid=" + tyredocid + ", tyre_id=" + tyre_id + ", tyre_content="
				+ Arrays.toString(tyre_content) + ", tyre_contentType=" + tyre_contentType + ", tyre_filename="
				+ tyre_filename + ", companyId=" + companyId + ", createdById=" + createdById + ", lastModifiedById="
				+ lastModifiedById + ", markForDelete=" + markForDelete + ", created=" + created + ", lastupdated="
				+ lastupdated + "]";
	}

	



}
