/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */
public class TyreSoldInvoiceDetailsDto {

	private Long tyreSoldInvoiceId;

	private String tyreSoldInvoiceNumber;

	private Double discount;
	
	private Double gst;
	
	private Double tyreSoldInvoiceAmount;
	
	private Double tyreSoldInvoiceNetAmount;
	
	private Timestamp tyreSoldInvoiceDate;
	
	private String tyreSoldInvoiceDateStr;
	
	private String description;
	
	private short soldType;
	
	private String soldTypeStr;
	
	private Long createdById;

	private Long lastUpdatedById;
	
	private String createdBy;

	private String lastUpdatedBy;
	
	private Timestamp createdOn;

	private Timestamp lastUpdatedOn;
	
	private String creationDate;

	private String lastUpdatedDate;

	boolean markForDelete;

	

	public TyreSoldInvoiceDetailsDto() {
		super();
		
	}



	public TyreSoldInvoiceDetailsDto(Long tyreSoldInvoiceId, String tyreSoldInvoiceNumber, Double discount, Double gst,
			Double tyreSoldInvoiceAmount, Timestamp tyreSoldInvoiceDate, String tyreSoldInvoiceDateStr,
			String description, short soldType, String soldTypeStr, Long createdById, Long lastUpdatedById,
			String createdBy, String lastUpdatedBy, Timestamp createdOn, Timestamp lastUpdatedOn, String creationDate,
			String lastUpdatedDate, boolean markForDelete) {
		super();
		this.tyreSoldInvoiceId = tyreSoldInvoiceId;
		this.tyreSoldInvoiceNumber = tyreSoldInvoiceNumber;
		this.discount = discount;
		this.gst = gst;
		this.tyreSoldInvoiceAmount = tyreSoldInvoiceAmount;
		this.tyreSoldInvoiceDate = tyreSoldInvoiceDate;
		this.tyreSoldInvoiceDateStr = tyreSoldInvoiceDateStr;
		this.description = description;
		this.soldType = soldType;
		this.soldTypeStr = soldTypeStr;
		this.createdById = createdById;
		this.lastUpdatedById = lastUpdatedById;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.createdOn = createdOn;
		this.lastUpdatedOn = lastUpdatedOn;
		this.creationDate = creationDate;
		this.lastUpdatedDate = lastUpdatedDate;
		this.markForDelete = markForDelete;
	}



	public Long getTyreSoldInvoiceId() {
		return tyreSoldInvoiceId;
	}



	public void setTyreSoldInvoiceId(Long tyreSoldInvoiceId) {
		this.tyreSoldInvoiceId = tyreSoldInvoiceId;
	}



	public String getTyreSoldInvoiceNumber() {
		return tyreSoldInvoiceNumber;
	}



	public void setTyreSoldInvoiceNumber(String tyreSoldInvoiceNumber) {
		this.tyreSoldInvoiceNumber = tyreSoldInvoiceNumber;
	}



	public Double getDiscount() {
		return discount;
	}



	public void setDiscount(Double discount) {
		this.discount =  Utility.round(discount,2);
	}



	public Double getGst() {
		return gst;
	}



	public void setGst(Double gst) {
		this.gst = Utility.round(gst,2);
	}



	public Double getTyreSoldInvoiceAmount() {
		return tyreSoldInvoiceAmount;
	}



	public void setTyreSoldInvoiceAmount(Double tyreSoldInvoiceAmount) {
		this.tyreSoldInvoiceAmount = Utility.round(tyreSoldInvoiceAmount, 2);
	}



	public Double getTyreSoldInvoiceNetAmount() {
		return tyreSoldInvoiceNetAmount;
	}



	public void setTyreSoldInvoiceNetAmount(Double tyreSoldInvoiceNetAmount) {
		this.tyreSoldInvoiceNetAmount = Utility.round(tyreSoldInvoiceNetAmount, 2);
	}



	public Timestamp getTyreSoldInvoiceDate() {
		return tyreSoldInvoiceDate;
	}



	public void setTyreSoldInvoiceDate(Timestamp tyreSoldInvoiceDate) {
		this.tyreSoldInvoiceDate = tyreSoldInvoiceDate;
	}



	public String getTyreSoldInvoiceDateStr() {
		return tyreSoldInvoiceDateStr;
	}



	public void setTyreSoldInvoiceDateStr(String tyreSoldInvoiceDateStr) {
		this.tyreSoldInvoiceDateStr = tyreSoldInvoiceDateStr;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public short getSoldType() {
		return soldType;
	}



	public void setSoldType(short soldType) {
		this.soldType = soldType;
	}



	public String getSoldTypeStr() {
		return soldTypeStr;
	}



	public void setSoldTypeStr(String soldTypeStr) {
		this.soldTypeStr = soldTypeStr;
	}



	public Long getCreatedById() {
		return createdById;
	}



	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}



	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}



	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
	}



	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}



	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}



	public Timestamp getCreatedOn() {
		return createdOn;
	}



	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}



	public Timestamp getLastUpdatedOn() {
		return lastUpdatedOn;
	}



	public void setLastUpdatedOn(Timestamp lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}



	public String getCreationDate() {
		return creationDate;
	}



	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}



	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}



	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}



	public boolean isMarkForDelete() {
		return markForDelete;
	}



	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}



	@Override
	public String toString() {
		return "TyreSoldInvoiceDetailsDto [tyreSoldInvoiceId=" + tyreSoldInvoiceId + ", tyreSoldInvoiceNumber="
				+ tyreSoldInvoiceNumber + ", discount=" + discount + ", gst=" + gst + ", tyreSoldInvoiceAmount="
				+ tyreSoldInvoiceAmount + ", tyreSoldInvoiceDate=" + tyreSoldInvoiceDate + ", tyreSoldInvoiceDateStr="
				+ tyreSoldInvoiceDateStr + ", description=" + description + ", soldType=" + soldType + ", soldTypeStr="
				+ soldTypeStr + ", createdById=" + createdById + ", lastUpdatedById=" + lastUpdatedById + ", createdBy="
				+ createdBy + ", lastUpdatedBy=" + lastUpdatedBy + ", createdOn=" + createdOn + ", lastUpdatedOn="
				+ lastUpdatedOn + ", creationDate=" + creationDate + ", lastUpdatedDate=" + lastUpdatedDate
				+ ", markForDelete=" + markForDelete + "]";
	}

	
	
}
