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
@Table(name = "vehicledocument")

public class VehicleDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/*@Column(name = "name")
	private String name;*/
	
	@Column(name = "docTypeId")
	private Long docTypeId;

	/*@Column(name = "uploaddate")
	private String uploaddate;*/

	@Column(name = "filename")
	private String filename;

	@Column(name = "vehid")
	private Integer vehid;

	@Lob
	@Column(name = "content", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] content;

	@Column(name = "content_type")
	private String contentType;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "created", updatable = false)
	private Date created;
	
	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public VehicleDocument() {
		super();

	}

	public VehicleDocument(Long docTypeId, String uploaddate, String filename, Integer vehid,
			String contentType, Integer companyId) {
		super();
		this.docTypeId = docTypeId;
		//this.uploaddate = uploaddate;
		this.filename = filename;
		this.vehid = vehid;
		this.contentType = contentType;
		this.companyId = companyId;
	}

	public Integer getId() {
		return id;
	}

	/*public String getName() {
		return name;
	}*/

	public String getFilename() {
		return filename;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/*public void setName(String name) {
		this.name = name;
	}*/

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

/*	public String getUploaddate() {
		return uploaddate;
	}

	public void setUploaddate(String uploaddate) {
		this.uploaddate = uploaddate;
	}
	*/
	

	/**
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

	/**
	 * @return the vehid
	 */
	public Integer getVehid() {
		return vehid;
	}

	/**
	 * @param vehid
	 *            the vehid to set
	 */
	public void setVehid(Integer vehid) {
		this.vehid = vehid;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
	 * @return the docTypeId
	 */
	public Long getDocTypeId() {
		return docTypeId;
	}

	/**
	 * @param docTypeId the docTypeId to set
	 */
	public void setDocTypeId(Long docTypeId) {
		this.docTypeId = docTypeId;
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
		result = prime * result + ((docTypeId == null) ? 0 : docTypeId.hashCode());
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
		VehicleDocument other = (VehicleDocument) obj;
		if (docTypeId == null) {
			if (other.docTypeId != null)
				return false;
		} else if (!docTypeId.equals(other.docTypeId))
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
		builder.append("VehicleDocument [docTypeId=");
		builder.append(docTypeId);
		/*builder.append(", uploaddate=");
		builder.append(uploaddate);*/
		builder.append(", filename=");
		builder.append(filename);
		builder.append(", vehid=");
		builder.append(vehid);
		builder.append(", content=");
		builder.append(content);
		builder.append(", contentType=");
		builder.append(contentType);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}

}
