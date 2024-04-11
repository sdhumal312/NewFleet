package org.fleetopgroup.persistence.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ServiceEntriesDocument")
public class ServiceEntriesDocument implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long service_documentid;

	@Indexed
	private Long serviceEntries_id;
	
	private byte[] service_content;
	
	private String service_contentType;

	private String service_filename;
	
	@Indexed
	private Integer companyId;
	
	private Long createdById;

	private Long lastModifiedById;
	
	private Date created;

	private Date lastupdated;
	
	private boolean markForDelete;

	/**
	 * @return the service_documentid
	 */
	public Long getService_documentid() {
		return service_documentid;
	}

	/**
	 * @param service_documentid the service_documentid to set
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
	 * @param serviceEntries_id the serviceEntries_id to set
	 */
	public void setServiceEntries_id(Long serviceEntries_id) {
		this.serviceEntries_id = serviceEntries_id;
	}

	/**
	 * @return the service_content
	 */
	public byte[] getService_content() {
		return service_content;
	}

	/**
	 * @param service_content the service_content to set
	 */
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
	 * @param service_contentType the service_contentType to set
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
	 * @param service_filename the service_filename to set
	 */
	public void setService_filename(String service_filename) {
		this.service_filename = service_filename;
	}

	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
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
	 * @param lastupdated the lastupdated to set
	 */
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	/**
	 * @return the markForDelete
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete the markForDelete to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	
	
}
