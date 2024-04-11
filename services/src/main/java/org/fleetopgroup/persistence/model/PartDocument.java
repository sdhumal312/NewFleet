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
@Table(name = "PartDocument")
public class PartDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "partdocid")
	private Long partdocid;

	@Column(name = "part_id")
	private Long part_id;

	@Lob
	@Column(name = "part_content", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] part_content;

	@Column(name = "part_content_type")
	private String part_contentType;

	@Column(name = "part_filename")
	private String part_filename;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	/*@Column(name = "createdBy", length = 25)
	private String createdBy;

	@Column(name = "lastModifiedBy", length = 25)
	private String lastModifiedBy;*/
	
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

	public PartDocument() {
		super();
	}

	public PartDocument(Long partdocid, Long part_id, byte[] part_content, String part_contentType,
			String part_filename, Long createdBy, Long lastModifiedBy, boolean MarkForDelete, Date created,
			Date lastupdated, Integer companyId) {
		super();
		this.partdocid = partdocid;
		this.part_id = part_id;
		this.part_content = part_content;
		this.part_contentType = part_contentType;
		this.part_filename = part_filename;
		this.createdById = createdBy;
		this.lastModifiedById = lastModifiedBy;
		this.markForDelete = MarkForDelete;
		this.created = created;
		this.lastupdated = lastupdated;
		this.companyId = companyId;
	}



	public Long getPartdocid() {
		return partdocid;
	}

	public void setPartdocid(Long partdocid) {
		this.partdocid = partdocid;
	}

	public Long getPart_id() {
		return part_id;
	}

	public void setPart_id(Long part_id) {
		this.part_id = part_id;
	}

	public byte[] getPart_content() {
		return part_content;
	}

	public void setPart_content(byte[] part_content) {
		this.part_content = part_content;
	}

	public String getPart_contentType() {
		return part_contentType;
	}

	public void setPart_contentType(String part_contentType) {
		this.part_contentType = part_contentType;
	}

	public String getPart_filename() {
		return part_filename;
	}

	public void setPart_filename(String part_filename) {
		this.part_filename = part_filename;
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
		result = prime * result + ((part_id == null) ? 0 : part_id.hashCode());
		result = prime * result + ((partdocid == null) ? 0 : partdocid.hashCode());
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
		PartDocument other = (PartDocument) obj;
		if (part_id == null) {
			if (other.part_id != null)
				return false;
		} else if (!part_id.equals(other.part_id))
			return false;
		if (partdocid == null) {
			if (other.partdocid != null)
				return false;
		} else if (!partdocid.equals(other.partdocid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PartDocument [partdocid=" + partdocid + ", part_id=" + part_id + ", part_content="
				+ Arrays.toString(part_content) + ", part_contentType=" + part_contentType + ", part_filename="
				+ part_filename + ", companyId=" + companyId + ", createdById=" + createdById + ", lastModifiedById="
				+ lastModifiedById + ", markForDelete=" + markForDelete + ", created=" + created + ", lastupdated="
				+ lastupdated + "]";
	}



}
