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

@Entity
@Table(name = "ServiceEntriesDocument")

public class ServiceEntriesDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "service_documentid")
	private Long service_documentid;

	@Column(name = "serviceEntries_id")
	private Long serviceEntries_id;

	@Lob
	@Column(name = "service_content", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] service_content;

	@Column(name = "service_content_type")
	private String service_contentType;

	@Column(name = "service_filename")
	private String service_filename;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	/*@Column(name = "createdBy", length = 25)
	private String createdBy;*/
	
	@Column(name = "createdById")
	private Long createdById;
	
	/*@Column(name = "lastModifiedBy", length = 25)
	private String lastModifiedBy;*/
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@Column(name = "lastupdated", nullable = false, updatable = false)
	private Date lastupdated;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public ServiceEntriesDocument() {
		super();
	}

	public ServiceEntriesDocument(Long service_documentid, Long serviceEntries_id, Long createdBy,
			Long lastModifiedBy, Date created, Date lastupdated, Integer companyId) {
		super();
		this.service_documentid = service_documentid;
		this.serviceEntries_id = serviceEntries_id;
		this.createdById = createdBy;
		this.lastModifiedById = lastModifiedBy;
		this.created = created;
		this.lastupdated = lastupdated;
		this.companyId = companyId;
	}

	/**
	 * @return the service_documentid
	 */
	public Long getService_documentid() {
		return service_documentid;
	}

	/**
	 * @param service_documentid
	 *            the service_documentid to set
	 */
	public void setService_documentid(Long service_documentid) {
		this.service_documentid = service_documentid;
	}

	/**
	 * @return the serviceEntries_id
	 */
	public Long getServiceEntries_id() {
		return serviceEntries_id;
	}

	/**
	 * @param serviceEntries_id
	 *            the serviceEntries_id to set
	 */
	public void setServiceEntries_id(Long serviceEntries_id) {
		this.serviceEntries_id = serviceEntries_id;
	}

	public byte[] getService_content() {
		return service_content;
	}

	public void setService_content(byte[] service_content) {
		this.service_content = service_content;
	}

	/**
	 * @return the service_contentType
	 */
	public String getService_contentType() {
		return service_contentType;
	}

	/**
	 * @param service_contentType
	 *            the service_contentType to set
	 */
	public void setService_contentType(String service_contentType) {
		this.service_contentType = service_contentType;
	}

	/**
	 * @return the service_filename
	 */
	public String getService_filename() {
		return service_filename;
	}

	/**
	 * @param service_filename
	 *            the service_filename to set
	 */
	public void setService_filename(String service_filename) {
		this.service_filename = service_filename;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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
		result = prime * result + ((serviceEntries_id == null) ? 0 : serviceEntries_id.hashCode());
		result = prime * result + ((service_documentid == null) ? 0 : service_documentid.hashCode());
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
		ServiceEntriesDocument other = (ServiceEntriesDocument) obj;
		if (serviceEntries_id == null) {
			if (other.serviceEntries_id != null)
				return false;
		} else if (!serviceEntries_id.equals(other.serviceEntries_id))
			return false;
		if (service_documentid == null) {
			if (other.service_documentid != null)
				return false;
		} else if (!service_documentid.equals(other.service_documentid))
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
		builder.append("ServiceEntriesDocument [serviceEntries_id=");
		builder.append(serviceEntries_id);
		builder.append(", service_content=");
		builder.append(service_content);
		builder.append(", service_contentType=");
		builder.append(service_contentType);
		builder.append(", service_filename=");
		builder.append(service_filename);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append("]");
		return builder.toString();
	}

}
