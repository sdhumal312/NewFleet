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
@Table(name = "FuelDocument")
public class FuelDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fueldocid")
	private Long fueldocid;

	@Column(name = "fuel_id")
	private Long fuel_id;

	@Lob
	@Column(name = "fuel_content", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] fuel_content;

	@Column(name = "fuel_content_type")
	private String fuel_contentType;

	@Column(name = "fuel_filename")
	private String fuel_filename;
	
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

	public FuelDocument() {
		super();
	}

	public FuelDocument(Long fueldocid, Long fuel_id, byte[] fuel_content, String fuel_contentType,
			String fuel_filename, Long createdBy, Long lastModifiedBy, boolean MarkForDelete, Date created,
			Date lastupdated, Integer companyId) {
		super();
		this.fueldocid = fueldocid;
		this.fuel_id = fuel_id;
		this.fuel_content = fuel_content;
		this.fuel_contentType = fuel_contentType;
		this.fuel_filename = fuel_filename;
		this.createdById = createdBy;
		this.lastModifiedById = lastModifiedBy;
		this.markForDelete = MarkForDelete;
		this.created = created;
		this.lastupdated = lastupdated;
		this.companyId = companyId;
	}

	/**
	 * @return the fueldocid
	 */
	public Long getFueldocid() {
		return fueldocid;
	}

	/**
	 * @param fueldocid
	 *            the fueldocid to set
	 */
	public void setFueldocid(Long fueldocid) {
		this.fueldocid = fueldocid;
	}

	/**
	 * @return the fuel_id
	 */
	public Long getFuel_id() {
		return fuel_id;
	}

	/**
	 * @param fuel_id
	 *            the fuel_id to set
	 */
	public void setFuel_id(Long fuel_id) {
		this.fuel_id = fuel_id;
	}

	/**
	 * @return the fuel_content
	 */
	public byte[] getFuel_content() {
		return fuel_content;
	}

	/**
	 * @param fuel_content
	 *            the fuel_content to set
	 */
	public void setFuel_content(byte[] fuel_content) {
		this.fuel_content = fuel_content;
	}

	/**
	 * @return the fuel_contentType
	 */
	public String getFuel_contentType() {
		return fuel_contentType;
	}

	/**
	 * @param fuel_contentType
	 *            the fuel_contentType to set
	 */
	public void setFuel_contentType(String fuel_contentType) {
		this.fuel_contentType = fuel_contentType;
	}

	/**
	 * @return the fuel_filename
	 */
	public String getFuel_filename() {
		return fuel_filename;
	}

	/**
	 * @param fuel_filename
	 *            the fuel_filename to set
	 */
	public void setFuel_filename(String fuel_filename) {
		this.fuel_filename = fuel_filename;
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
		result = prime * result + ((fuel_id == null) ? 0 : fuel_id.hashCode());
		result = prime * result + ((fueldocid == null) ? 0 : fueldocid.hashCode());
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
		FuelDocument other = (FuelDocument) obj;
		if (fuel_id == null) {
			if (other.fuel_id != null)
				return false;
		} else if (!fuel_id.equals(other.fuel_id))
			return false;
		if (fueldocid == null) {
			if (other.fueldocid != null)
				return false;
		} else if (!fueldocid.equals(other.fueldocid))
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
		builder.append("FuelDocument [fueldocid=");
		builder.append(fueldocid);
		builder.append(", fuel_id=");
		builder.append(fuel_id);
		builder.append(", fuel_content=");
		builder.append(fuel_content);
		builder.append(", fuel_contentType=");
		builder.append(fuel_contentType);
		builder.append(", fuel_filename=");
		builder.append(fuel_filename);
		builder.append(", companyId=");
		builder.append(companyId);
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
