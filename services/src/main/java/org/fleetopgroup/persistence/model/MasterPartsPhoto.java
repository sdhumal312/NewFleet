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
@Table(name = "masterpartsphoto")
public class MasterPartsPhoto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "part_photoid")
	private Long part_photoid;

	@Column(name = "part_photoname", length = 150)
	private String part_photoname;

	@Column(name = "part_filename")
	private String part_filename;

	@Column(name = "partid")
	private Long partid;

	@Lob
	@Column(name = "part_content", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] part_content;

	@Column(name = "part_contenttype")
	private String part_contentType;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	/*@Column(name = "createdBy", length = 25)
	private String createdBy;
	
	@Column(name = "lastModifiedBy", length = 25)
	private String lastModifiedBy;*/
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "createdById", nullable = false, updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@Column(name = "lastupdated", nullable = false, updatable = false)
	private Date lastupdated;

	public MasterPartsPhoto() {
		super();
	}

	public MasterPartsPhoto(Long part_photoid, String part_photoname, Long partid, Long createdBy,
			Long lastModifiedBy, boolean MarkForDelete, Date created, Date lastupdated, Integer companyId) {
		super();
		this.part_photoid = part_photoid;
		this.part_photoname = part_photoname;
		this.partid = partid;
		this.createdById = createdBy;
		this.lastModifiedById = lastModifiedBy;
		this.markForDelete = MarkForDelete;
		this.created = created;
		this.lastupdated = lastupdated;
		this.companyId = companyId;
	}

	public Long getPart_photoid() {
		return part_photoid;
	}

	public void setPart_photoid(Long part_photoid) {
		this.part_photoid = part_photoid;
	}

	public String getPart_photoname() {
		return part_photoname;
	}

	public void setPart_photoname(String part_photoname) {
		this.part_photoname = part_photoname;
	}

	public String getPart_filename() {
		return part_filename;
	}

	public void setPart_filename(String part_filename) {
		this.part_filename = part_filename;
	}

	public Long getPartid() {
		return partid;
	}

	public void setPartid(Long partid) {
		this.partid = partid;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/*public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
*/
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((part_photoid == null) ? 0 : part_photoid.hashCode());
		result = prime * result + ((part_photoname == null) ? 0 : part_photoname.hashCode());
		result = prime * result + ((partid == null) ? 0 : partid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MasterPartsPhoto other = (MasterPartsPhoto) obj;
		if (part_photoid == null) {
			if (other.part_photoid != null)
				return false;
		} else if (!part_photoid.equals(other.part_photoid))
			return false;
		if (part_photoname == null) {
			if (other.part_photoname != null)
				return false;
		} else if (!part_photoname.equals(other.part_photoname))
			return false;
		if (partid == null) {
			if (other.partid != null)
				return false;
		} else if (!partid.equals(other.partid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("MasterPartsPhoto [part_photoid=");
		builder.append(part_photoid);
		builder.append(", part_photoname=");
		builder.append(part_photoname);
		builder.append(", part_filename=");
		builder.append(part_filename);
		builder.append(", partid=");
		builder.append(partid);
		builder.append(", part_content=");
		builder.append(part_content != null
				? Arrays.toString(Arrays.copyOf(part_content, Math.min(part_content.length, maxLen))) : null);
		builder.append(", part_contentType=");
		builder.append(part_contentType);
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
