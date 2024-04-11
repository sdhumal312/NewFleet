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
@Table(name = "RenewalReminderDocument")
public class RenewalReminderDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rendoc_id")
	private Long rendoc_id;

	@Column(name = "renewal_id")
	private Long renewal_id;
	
	@Column(name = "renewal_R_Number")
	private Long renewal_R_Number;

	@Column(name = "renewalHis_id")
	private Long renewalHis_id;

	@Column(name = "renewal_filename")
	private String renewal_filename;

	@Lob
	@Column(name = "renewal_content", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] renewal_content;

	@Column(name = "renewal_content_type")
	private String renewal_contentType;
	
	@Column(name = "companyId" , nullable = false)
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

	@Column(name = "markForDelete" , nullable = false)
	private boolean markForDelete;
	
	public RenewalReminderDocument() {
		super();
	}

	public RenewalReminderDocument(Long rendoc_id, Long renewal_id, Long renewal_R_Number, String renewal_filename, byte[] renewal_content,
			String renewal_contentType, Long createdBy, Long lastModifiedBy, boolean MarkForDelete, Date created,
			Date lastupdated, Integer companyId) {
		super();
		this.rendoc_id = rendoc_id;
		this.renewal_id = renewal_id;
		this.renewal_R_Number = renewal_R_Number;
		this.renewal_filename = renewal_filename;
		this.renewal_content = renewal_content;
		this.renewal_contentType = renewal_contentType;
		this.createdById = createdBy;
		this.lastModifiedById = lastModifiedBy;
		this.markForDelete = MarkForDelete;
		this.created = created;
		this.lastupdated = lastupdated;
		this.companyId = companyId;
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
	 * @return the renewal_id
	 */
	public Long getRenewal_id() {
		return renewal_id;
	}

	/**
	 * @param renewal_id
	 *            the renewal_id to set
	 */
	public void setRenewal_id(Long renewal_id) {
		this.renewal_id = renewal_id;
	}

	public Long getRenewal_R_Number() {
		return renewal_R_Number;
	}

	public void setRenewal_R_Number(Long renewal_R_Number) {
		this.renewal_R_Number = renewal_R_Number;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	/**
	 * @return the renewalHis_id
	 */
	public Long getRenewalHis_id() {
		return renewalHis_id;
	}

	/**
	 * @param renewalHis_id
	 *            the renewalHis_id to set
	 */
	public void setRenewalHis_id(Long renewalHis_id) {
		this.renewalHis_id = renewalHis_id;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RenewalReminderDocument [rendoc_id=");
		builder.append(rendoc_id);
		builder.append(", renewal_id=");
		builder.append(renewal_id);
		builder.append(", renewal_R_Number=");
		builder.append(renewal_R_Number);
		builder.append(", renewalHis_id=");
		builder.append(renewalHis_id);
		builder.append(", renewal_filename=");
		builder.append(renewal_filename);
		builder.append(", renewal_content=");
		builder.append(renewal_content);
		builder.append(", renewal_contentType=");
		builder.append(renewal_contentType);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", createdBy=");
		builder.append(createdById);
		builder.append(", lastModifiedBy=");
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