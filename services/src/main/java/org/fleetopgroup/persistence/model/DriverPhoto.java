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
@Table(name = "driverphoto")
public class DriverPhoto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driver_photoid")
	private Integer driver_photoid;

	@Column(name = "driver_photoname", length = 25)
	private String driver_photoname;

	/*@Column(name = "driver_uploaddate")
	private String driver_uploaddate;*/
	
	@Column(name = "uploaddate")
	private Date uploaddate;

	@Column(name = "driver_filename")
	private String driver_filename;

	@Column(name = "dirver_id")
	private Integer driver_id;

	@Lob
	@Column(name = "driver_content", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] driver_content;

	@Column(name = "driver_contenttype")
	private String driver_contentType;
	
	@Column(name = "companyId", nullable = false)
	private Integer	companyId;
	
	@Column(name = "createdById")
	private Long createdById;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public DriverPhoto() {
		super();
	}

	public DriverPhoto(Integer driver_photoid, String driver_photoname, Date driver_uploaddate, Integer companyId) {
		super();
		this.driver_photoid = driver_photoid;
		this.driver_photoname = driver_photoname;
		this.uploaddate = driver_uploaddate;
		this.companyId = companyId;
	}

	public Integer getDriver_photoid() {
		return driver_photoid;
	}

	public void setDriver_photoid(Integer driver_photoid) {
		this.driver_photoid = driver_photoid;
	}

	public String getDriver_photoname() {
		return driver_photoname;
	}

	public void setDriver_photoname(String driver_photoname) {
		this.driver_photoname = driver_photoname;
	}

	/*public String getDriver_uploaddate() {
		return driver_uploaddate;
	}

	public void setDriver_uploaddate(String driver_uploaddate) {
		this.driver_uploaddate = driver_uploaddate;
	}*/

	public String getDriver_filename() {
		return driver_filename;
	}

	public void setDriver_filename(String driver_filename) {
		this.driver_filename = driver_filename;
	}

	public Integer getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(Integer driver_id) {
		this.driver_id = driver_id;
	}

	public byte[] getDriver_content() {
		return driver_content;
	}

	public void setDriver_content(byte[] driver_content) {
		this.driver_content = driver_content;
	}

	public String getDriver_contentType() {
		return driver_contentType;
	}

	public void setDriver_contentType(String driver_contentType) {
		this.driver_contentType = driver_contentType;
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
	 * @return the uploaddate
	 */
	public Date getUploaddate() {
		return uploaddate;
	}

	/**
	 * @param uploaddate the uploaddate to set
	 */
	public void setUploaddate(Date uploaddate) {
		this.uploaddate = uploaddate;
	}


	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driver_id == null) ? 0 : driver_id.hashCode());
		result = prime * result + ((driver_photoid == null) ? 0 : driver_photoid.hashCode());
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
		DriverPhoto other = (DriverPhoto) obj;
		if (driver_id == null) {
			if (other.driver_id != null)
				return false;
		} else if (!driver_id.equals(other.driver_id))
			return false;
		if (driver_photoid == null) {
			if (other.driver_photoid != null)
				return false;
		} else if (!driver_photoid.equals(other.driver_photoid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverPhoto [driver_photoid=");
		builder.append(driver_photoid);
		builder.append(", driver_photoname=");
		builder.append(driver_photoname);
		/*builder.append(", driver_uploaddate=");
		builder.append(driver_uploaddate);*/
		builder.append(", driver_filename=");
		builder.append(driver_filename);
		builder.append(", driver_id=");
		builder.append(driver_id);
		builder.append(", driver_content=");
		builder.append(driver_content);
		builder.append(", driver_contentType=");
		builder.append(driver_contentType);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append("]");
		return builder.toString();
	}

}
