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
@Table(name = "WorkOrdersDocument")
public class WorkOrdersDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "workorder_documentid")
	private Long workorder_documentid;

	@Column(name = "workorder_id")
	private Long workorder_id;

	@Lob
	@Column(name = "workorder_content", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] workorder_content;

	@Column(name = "workorder_content_type")
	private String workorder_contentType;

	@Column(name = "workorder_filename")
	private String workorder_filename;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdBy", length = 25)
	private String createdBy;
	@Column(name = "lastModifiedBy", length = 25)
	private String lastModifiedBy;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@Column(name = "lastupdated", nullable = false, updatable = false)
	private Date lastupdated;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public WorkOrdersDocument() {
		super();
	}

	public WorkOrdersDocument(Long workorder_documentid) {
		super();
		this.workorder_documentid = workorder_documentid;
	}

	/**
	 * @return the workorder_documentid
	 */
	public Long getWorkorder_documentid() {
		return workorder_documentid;
	}

	/**
	 * @param workorder_documentid
	 *            the workorder_documentid to set
	 */
	public void setWorkorder_documentid(Long workorder_documentid) {
		this.workorder_documentid = workorder_documentid;
	}

	/**
	 * @return the workorder_id
	 */
	public Long getWorkorder_id() {
		return workorder_id;
	}

	/**
	 * @param workorder_id
	 *            the workorder_id to set
	 */
	public void setWorkorder_id(Long workorder_id) {
		this.workorder_id = workorder_id;
	}

	
	public byte[] getWorkorder_content() {
		return workorder_content;
	}

	public void setWorkorder_content(byte[] workorder_content) {
		this.workorder_content = workorder_content;
	}

	/**
	 * @return the workorder_contentType
	 */
	public String getWorkorder_contentType() {
		return workorder_contentType;
	}

	/**
	 * @param workorder_contentType
	 *            the workorder_contentType to set
	 */
	public void setWorkorder_contentType(String workorder_contentType) {
		this.workorder_contentType = workorder_contentType;
	}

	/**
	 * @return the workorder_filename
	 */
	public String getWorkorder_filename() {
		return workorder_filename;
	}

	/**
	 * @param workorder_filename
	 *            the workorder_filename to set
	 */
	public void setWorkorder_filename(String workorder_filename) {
		this.workorder_filename = workorder_filename;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
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
		result = prime * result + ((workorder_documentid == null) ? 0 : workorder_documentid.hashCode());
		result = prime * result + ((workorder_filename == null) ? 0 : workorder_filename.hashCode());
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
		WorkOrdersDocument other = (WorkOrdersDocument) obj;
		if (workorder_documentid == null) {
			if (other.workorder_documentid != null)
				return false;
		} else if (!workorder_documentid.equals(other.workorder_documentid))
			return false;
		if (workorder_filename == null) {
			if (other.workorder_filename != null)
				return false;
		} else if (!workorder_filename.equals(other.workorder_filename))
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
		builder.append("WorkOrdersDocument [workorder_id=");
		builder.append(workorder_id);
		builder.append(", workorder_content=");
		builder.append(workorder_content);
		builder.append(", workorder_contentType=");
		builder.append(workorder_contentType);
		builder.append(", workorder_filename=");
		builder.append(workorder_filename);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append("]");
		return builder.toString();
	}

}