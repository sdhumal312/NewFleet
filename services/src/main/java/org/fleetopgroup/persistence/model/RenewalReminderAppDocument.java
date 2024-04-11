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
@Table(name = "RenewalReminderAppDocument")
public class RenewalReminderAppDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rendoc_id")
	private Long rendoc_id;

	@Column(name = "renewalApproval_id")
	private Long renewalApproval_id;

	@Column(name = "renewal_filename")
	private String renewal_filename;

	@Lob
	@Column(name = "renewal_content", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] renewal_content;

	@Column(name = "renewal_content_type")
	private String renewal_contentType;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	/** The value for the created by field *//*
	@Column(name = "createdBy", updatable = false, length = 150)
	private String createdBy;

	*//** The value for the lastUpdated by field *//*
	@Column(name = "lastModifiedBy", length = 150)
	private String lastModifiedBy;*/
	
	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	/** The value for the lastUpdated DATE field */
	@Basic(optional = false)
	@Column(name = "lastupdated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdated;

	public RenewalReminderAppDocument() {
		super();
	}

	public RenewalReminderAppDocument(Long rendoc_id, Long renewalApproval_id, String renewal_filename,
			byte[] renewal_content, String renewal_contentType, Long createdBy, Long lastModifiedBy, boolean MarkForDelete,
			Date created, Date lastupdated) {
		super();
		this.rendoc_id = rendoc_id;
		this.renewalApproval_id = renewalApproval_id;
		this.renewal_filename = renewal_filename;
		this.renewal_content = renewal_content;
		this.renewal_contentType = renewal_contentType;
		this.createdById = createdBy;
		this.lastModifiedById = lastModifiedBy;
		this.markForDelete = MarkForDelete;
		this.created = created;
		this.lastupdated = lastupdated;
	}

	/**
	 * @return the rendoc_id
	 */
	public Long getRendoc_id() {
		return rendoc_id;
	}

	/**
	 * @param rendoc_id
	 *            the rendoc_id to set
	 */
	public void setRendoc_id(Long rendoc_id) {
		this.rendoc_id = rendoc_id;
	}

	/**
	 * @return the renewalApproval_id
	 */
	public Long getRenewalApproval_id() {
		return renewalApproval_id;
	}

	/**
	 * @param renewalApproval_id
	 *            the renewalApproval_id to set
	 */
	public void setRenewalApproval_id(Long renewalApproval_id) {
		this.renewalApproval_id = renewalApproval_id;
	}

	/**
	 * @return the renewal_filename
	 */
	public String getRenewal_filename() {
		return renewal_filename;
	}

	/**
	 * @param renewal_filename
	 *            the renewal_filename to set
	 */
	public void setRenewal_filename(String renewal_filename) {
		this.renewal_filename = renewal_filename;
	}

	/**
	 * @return the renewal_content
	 */
	public byte[] getRenewal_content() {
		return renewal_content;
	}

	/**
	 * @param renewal_content
	 *            the renewal_content to set
	 */
	public void setRenewal_content(byte[] renewal_content) {
		this.renewal_content = renewal_content;
	}

	/**
	 * @return the renewal_contentType
	 */
	public String getRenewal_contentType() {
		return renewal_contentType;
	}

	/**
	 * @param renewal_contentType
	 *            the renewal_contentType to set
	 */
	public void setRenewal_contentType(String renewal_contentType) {
		this.renewal_contentType = renewal_contentType;
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
		result = prime * result + ((rendoc_id == null) ? 0 : rendoc_id.hashCode());
		result = prime * result + ((renewalApproval_id == null) ? 0 : renewalApproval_id.hashCode());
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
		RenewalReminderAppDocument other = (RenewalReminderAppDocument) obj;
		if (rendoc_id == null) {
			if (other.rendoc_id != null)
				return false;
		} else if (!rendoc_id.equals(other.rendoc_id))
			return false;
		if (renewalApproval_id == null) {
			if (other.renewalApproval_id != null)
				return false;
		} else if (!renewalApproval_id.equals(other.renewalApproval_id))
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
		builder.append("RenewalReminderAppDocument [rendoc_id=");
		builder.append(rendoc_id);
		builder.append(", renewalApproval_id=");
		builder.append(renewalApproval_id);
		builder.append(", renewal_filename=");
		builder.append(renewal_filename);
		builder.append(", renewal_content=");
		builder.append(renewal_content);
		builder.append(", renewal_contentType=");
		builder.append(renewal_contentType);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append("]");
		return builder.toString();
	}

}
