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
@Table(name = "driverdocument")
public class DriverDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driver_documentid")
	private Integer driver_documentid;

	@Column(name = "driver_documentname", length = 45)
	private String driver_documentname;

/*	@Column(name = "driver_docFrom")
	private String driver_docFrom;*/
	
	@Column(name = "driver_docFromDate")
	private Date driver_docFromDate;

	/*@Column(name = "driver_docTo")
	private String driver_docTo;*/
	
	@Column(name = "driver_docToDate")
	private Date driver_docToDate;

	/*@Column(name = "driver_uploaddate")
	private String driver_uploaddate;*/
	
	@Column(name = "uploaddate", updatable = false)
	private Date uploaddate;

	/*@Column(name = "driver_revdate")
	private String driver_revdate;*/
	
	@Column(name = "revdate")
	private Date revdate;

	@Column(name = "driver_filename")
	private String driver_filename;

	@Column(name = "dirver_id")
	private Integer driver_id;

	@Lob
	@Column(name = "driver_content", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] driver_content;

	@Column(name = "driver_content_type")
	private String driver_contentType;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "createdById", nullable = false, updatable = false)
	private Long createdById;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public Integer getDriver_documentid() {
		return driver_documentid;
	}
	
	 

	public DriverDocument() {
		super();
	}


	public void setDriver_documentid(Integer driver_documentid) {
		this.driver_documentid = driver_documentid;
	}

	/*public String getDriver_docFrom() {
		return driver_docFrom;
	}

	public void setDriver_docFrom(String driver_docFrom) {
		this.driver_docFrom = driver_docFrom;
	}

	public String getDriver_docTo() {
		return driver_docTo;
	}

	public void setDriver_docTo(String driver_docTo) {
		this.driver_docTo = driver_docTo;
	}
*/
	public String getDriver_documentname() {
		return driver_documentname;
	}

	public void setDriver_documentname(String driver_documentname) {
		this.driver_documentname = driver_documentname;
	}

	/*public String getDriver_uploaddate() {
		return driver_uploaddate;
	}

	public void setDriver_uploaddate(String driver_uploaddate) {
		this.driver_uploaddate = driver_uploaddate;
	}

	public String getDriver_revdate() {
		return driver_revdate;
	}

	public void setDriver_revdate(String driver_revdate) {
		this.driver_revdate = driver_revdate;
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
	 * @return the driver_docFromDate
	 */
	public Date getDriver_docFromDate() {
		return driver_docFromDate;
	}



	/**
	 * @param driver_docFromDate the driver_docFromDate to set
	 */
	public void setDriver_docFromDate(Date driver_docFromDate) {
		this.driver_docFromDate = driver_docFromDate;
	}



	/**
	 * @return the driver_docToDate
	 */
	public Date getDriver_docToDate() {
		return driver_docToDate;
	}



	/**
	 * @param driver_docToDate the driver_docToDate to set
	 */
	public void setDriver_docToDate(Date driver_docToDate) {
		this.driver_docToDate = driver_docToDate;
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



	/**
	 * @return the revdate
	 */
	public Date getRevdate() {
		return revdate;
	}



	/**
	 * @param revdate the revdate to set
	 */
	public void setRevdate(Date revdate) {
		this.revdate = revdate;
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
		result = prime * result + ((driver_documentid == null) ? 0 : driver_documentid.hashCode());
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
		DriverDocument other = (DriverDocument) obj;
		if (driver_documentid == null) {
			if (other.driver_documentid != null)
				return false;
		} else if (!driver_documentid.equals(other.driver_documentid))
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
		builder.append("DriverDocument [driver_documentid=");
		builder.append(driver_documentid);
		builder.append(", driver_documentname=");
		builder.append(driver_documentname);
		builder.append(", driver_docFromDate=");
		builder.append(driver_docFromDate);
		builder.append(", driver_docToDate=");
		builder.append(driver_docToDate);
		builder.append(", uploaddate=");
		builder.append(uploaddate);
		builder.append(", revdate=");
		builder.append(revdate);
		builder.append(", driver_filename=");
		builder.append(driver_filename);
		builder.append(", driver_id=");
		builder.append(driver_id);
		builder.append(", driver_content=");
		builder.append(driver_content);
		builder.append(", driver_contentType=");
		builder.append(driver_contentType);
		builder.append("]");
		return builder.toString();
	}
	
	

}
