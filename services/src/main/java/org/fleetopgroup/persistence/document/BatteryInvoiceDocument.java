package org.fleetopgroup.persistence.document;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author fleetop
 *
 *
 *
 */


@Document(collection = "BatteryInvoiceDocument")
public class BatteryInvoiceDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long _id;
	private Long batteryInvoiceId;
	private byte[] batteryInvoiceContent;
	private String batteryInvoiceContentType;
	private String batteryInvoiceFileName;
	private Integer companyId;
	private Long createdById;
	private Long lastModifiedById;
	private boolean markForDelete;
	private Date created;
	private Date lastupdated;
	
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public Long getBatteryInvoiceId() {
		return batteryInvoiceId;
	}
	public void setBatteryInvoiceId(Long batteryInvoiceId) {
		this.batteryInvoiceId = batteryInvoiceId;
	}
	public byte[] getBatteryInvoiceContent() {
		return batteryInvoiceContent;
	}
	public void setBatteryInvoiceContent(byte[] batteryInvoiceContent) {
		this.batteryInvoiceContent = batteryInvoiceContent;
	}
	public String getBatteryInvoiceContentType() {
		return batteryInvoiceContentType;
	}
	public void setBatteryInvoiceContentType(String batteryInvoiceContentType) {
		this.batteryInvoiceContentType = batteryInvoiceContentType;
	}
	public String getBatteryInvoiceFileName() {
		return batteryInvoiceFileName;
	}
	public void setBatteryInvoiceFileName(String batteryInvoiceFileName) {
		this.batteryInvoiceFileName = batteryInvoiceFileName;
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
	public Long getLastModifiedById() {
		return lastModifiedById;
	}
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
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
	public Date getLastupdated() {
		return lastupdated;
	}
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}
	
	
	
}
