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
@Table(name = "driverdochistory")
public class DriverDocumentHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driver_docHisid")
	private Integer driver_doHisid;

	@Column(name = "driver_docHisname")
	private String driver_docHisname;

	/*@Column(name = "driver_docHisFrom")
	private String driver_docHisFrom;*/
	
	@Column(name = "driver_docHisFromDate")
	private Date driver_docHisFromDate;
/*
	@Column(name = "driver_docHisTo")
	private String driver_docHisTo;*/
	
	@Column(name = "driver_docHisToDate")
	private Date driver_docHisToDate;

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

	@Column(name = "driver_content_type")
	private String driver_contentType;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	

	public DriverDocumentHistory() {
		super();
	}
	
	

	



	public DriverDocumentHistory(Integer driver_doHisid, Date driver_docHisFrom, Date driver_docHisTo,
			String driver_filename, Integer companyId) {
		super();
		this.driver_doHisid = driver_doHisid;
		this.driver_docHisFromDate = driver_docHisFrom;
		this.driver_docHisToDate = driver_docHisTo;
		this.driver_filename = driver_filename;
		this.companyId = companyId;
	}


	public Integer getDriver_doHisid() {
		return driver_doHisid;
	}

	public void setDriver_doHisid(Integer driver_doHisid) {
		this.driver_doHisid = driver_doHisid;
	}

	public String getDriver_docHisname() {
		return driver_docHisname;
	}

	public void setDriver_docHisname(String driver_docHisname) {
		this.driver_docHisname = driver_docHisname;
	}

	/*public String getDriver_docHisFrom() {
		return driver_docHisFrom;
	}

	public void setDriver_docHisFrom(String driver_docHisFrom) {
		this.driver_docHisFrom = driver_docHisFrom;
	}

	public String getDriver_docHisTo() {
		return driver_docHisTo;
	}

	public void setDriver_docHisTo(String driver_docHisTo) {
		this.driver_docHisTo = driver_docHisTo;
	}

	public String getDriver_uploaddate() {
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



	/**
	 * @return the driver_docHisFromDate
	 */
	public Date getDriver_docHisFromDate() {
		return driver_docHisFromDate;
	}







	/**
	 * @param driver_docHisFromDate the driver_docHisFromDate to set
	 */
	public void setDriver_docHisFromDate(Date driver_docHisFromDate) {
		this.driver_docHisFromDate = driver_docHisFromDate;
	}







	/**
	 * @return the driver_docHisToDate
	 */
	public Date getDriver_docHisToDate() {
		return driver_docHisToDate;
	}







	/**
	 * @param driver_docHisToDate the driver_docHisToDate to set
	 */
	public void setDriver_docHisToDate(Date driver_docHisToDate) {
		this.driver_docHisToDate = driver_docHisToDate;
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







	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driver_doHisid == null) ? 0 : driver_doHisid.hashCode());
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
		DriverDocumentHistory other = (DriverDocumentHistory) obj;
		if (driver_doHisid == null) {
			if (other.driver_doHisid != null)
				return false;
		} else if (!driver_doHisid.equals(other.driver_doHisid))
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
		builder.append("DriverDocumentHistory [driver_doHisid=");
		builder.append(driver_doHisid);
		builder.append(", driver_docHisname=");
		builder.append(driver_docHisname);
		builder.append(", driver_docHisFromDate=");
		builder.append(driver_docHisFromDate);
		builder.append(", driver_docHisToDate=");
		builder.append(driver_docHisToDate);
		builder.append(", uploaddate=");
		builder.append(uploaddate);
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
