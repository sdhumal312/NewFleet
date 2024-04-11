package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "drivercomment")
public class DriverComment implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driver_commentid")
	private Long driver_commentid;

	@Column(name = "driver_title", length = 25)
	private String driver_title;

	@Column(name = "driver_id")
	private Integer driver_id;

	@Column(name = "driver_comment")
	private String driver_comment;

	/*@Column(name = "createdBy", length = 100)
	private String createdBy;*/
	
	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@Column(name = "createdBy", nullable = false, updatable = false)
	private String createdBy;
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public DriverComment() {
		super();
	}

	public DriverComment(String driver_title, Integer driver_id, String driver_comment, Integer companyId, String createdBy) {
		super();
		this.driver_title = driver_title;
		this.driver_id = driver_id;
		this.driver_comment = driver_comment;
		this.companyId = companyId;
		this.createdBy =createdBy;
	}

	public Long getDriver_commentid() {
		return driver_commentid;
	}

	public void setDriver_commentid(Long driver_commentid) {
		this.driver_commentid = driver_commentid;
	}

	public String getDriver_title() {
		return driver_title;
	}

	public void setDriver_title(String driver_title) {
		this.driver_title = driver_title;
	}

	public Integer getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(Integer driver_id) {
		this.driver_id = driver_id;
	}

	public String getDriver_comment() {
		return driver_comment;
	}

	public void setDriver_comment(String driver_comment) {
		this.driver_comment = driver_comment;
	}

	/*public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}*/

	
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driver_comment == null) ? 0 : driver_comment.hashCode());
		result = prime * result + ((driver_id == null) ? 0 : driver_id.hashCode());
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
		DriverComment other = (DriverComment) obj;
		if (driver_comment == null) {
			if (other.driver_comment != null)
				return false;
		} else if (!driver_comment.equals(other.driver_comment))
			return false;
		if (driver_id == null) {
			if (other.driver_id != null)
				return false;
		} else if (!driver_id.equals(other.driver_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverComment [driver_commentid=");
		builder.append(driver_commentid);
		/*builder.append(", driver_title=");
		builder.append(driver_title);*/
		builder.append(", driver_id=");
		builder.append(driver_id);
		builder.append(", driver_comment=");
		builder.append(driver_comment);
		builder.append(", createdBy=");
		builder.append(createdById);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", created=");
		builder.append(created);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append("]");
		return builder.toString();
	}

	

}
