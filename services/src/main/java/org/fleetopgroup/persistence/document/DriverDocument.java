package org.fleetopgroup.persistence.document;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DriverDocument")
public class DriverDocument {

	@Id
	private Integer driver_documentid;

	private String driver_documentname;

	private Date driver_docFromDate;

	private Date driver_docToDate;

	private Date uploaddate;

	private Date revdate;
	
	private String driver_filename;

	@Indexed(name = "driver_id")
	private Integer driver_id;

	@Indexed(name = "driverRenewalReceiptId")
	private long driverRenewalReceiptId;
	
	private byte[] driver_content;

	private String driver_contentType;
	
	@Indexed(name = "companyId")
	private Integer companyId;
	
	private Long createdById;
	
	private boolean markForDelete;

	public Integer getDriver_documentid() {
		return driver_documentid;
	}
	
	

	public long getDriverRenewalReceiptId() {
		return driverRenewalReceiptId;
	}



	public void setDriverRenewalReceiptId(long driverRenewalReceiptId) {
		this.driverRenewalReceiptId = driverRenewalReceiptId;
	}



	public void setDriver_documentid(Integer driver_documentid) {
		this.driver_documentid = driver_documentid;
	}

	public String getDriver_documentname() {
		return driver_documentname;
	}

	public void setDriver_documentname(String driver_documentname) {
		this.driver_documentname = driver_documentname;
	}

	public Date getDriver_docFromDate() {
		return driver_docFromDate;
	}

	public void setDriver_docFromDate(Date driver_docFromDate) {
		this.driver_docFromDate = driver_docFromDate;
	}

	public Date getDriver_docToDate() {
		return driver_docToDate;
	}

	public void setDriver_docToDate(Date driver_docToDate) {
		this.driver_docToDate = driver_docToDate;
	}

	public Date getUploaddate() {
		return uploaddate;
	}

	public void setUploaddate(Date uploaddate) {
		this.uploaddate = uploaddate;
	}

	public Date getRevdate() {
		return revdate;
	}

	public void setRevdate(Date revdate) {
		this.revdate = revdate;
	}

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

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	
	
}
