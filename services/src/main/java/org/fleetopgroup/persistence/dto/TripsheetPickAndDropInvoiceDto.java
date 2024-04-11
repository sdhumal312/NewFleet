package org.fleetopgroup.persistence.dto;

import java.util.Date;



public class TripsheetPickAndDropInvoiceDto {
	
	private Long tripsheetPickAndDropInvoiceId;
	
	private Long tripsheetPickAndDropInvoiceSummaryId;
	
	private Long invoiceNumber;
	
	private Long tripsheetPickAndDropId;
	
	private Date	creationDate;
	
	private Date	lastUpdatedOn;
	
	private Long	createdById;
	
	private Long	lastUpdatedBy;
	
	private Integer	companyId;
	
	private boolean markForDelete;
	
	public TripsheetPickAndDropInvoiceDto() {
		super();
	}


	public TripsheetPickAndDropInvoiceDto(Long tripsheetPickAndDropInvoiceId, Long invoiceNumber,
			Long tripsheetPickAndDropId, Date creationDate, Date lastUpdatedOn, Long createdById, Long lastUpdatedBy,
			Integer companyId, boolean markForDelete) {
		super();
		this.tripsheetPickAndDropInvoiceId = tripsheetPickAndDropInvoiceId;
		this.invoiceNumber = invoiceNumber;
		this.tripsheetPickAndDropId = tripsheetPickAndDropId;
		this.creationDate = creationDate;
		this.lastUpdatedOn = lastUpdatedOn;
		this.createdById = createdById;
		this.lastUpdatedBy = lastUpdatedBy;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}


	public Long getTripsheetPickAndDropInvoiceId() {
		return tripsheetPickAndDropInvoiceId;
	}


	public void setTripsheetPickAndDropInvoiceId(Long tripsheetPickAndDropInvoiceId) {
		this.tripsheetPickAndDropInvoiceId = tripsheetPickAndDropInvoiceId;
	}


	public Long getInvoiceNumber() {
		return invoiceNumber;
	}


	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}


	public Long getTripsheetPickAndDropId() {
		return tripsheetPickAndDropId;
	}


	public void setTripsheetPickAndDropId(Long tripsheetPickAndDropId) {
		this.tripsheetPickAndDropId = tripsheetPickAndDropId;
	}


	public Date getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}


	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}


	public Long getCreatedById() {
		return createdById;
	}


	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}


	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}


	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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
	

	public Long getTripsheetPickAndDropInvoiceSummaryId() {
		return tripsheetPickAndDropInvoiceSummaryId;
	}


	public void setTripsheetPickAndDropInvoiceSummaryId(Long tripsheetPickAndDropInvoiceSummaryId) {
		this.tripsheetPickAndDropInvoiceSummaryId = tripsheetPickAndDropInvoiceSummaryId;
	}


	@Override
	public String toString() {
		return "TripsheetPickAndDropInvoice [tripsheetPickAndDropInvoiceId=" + tripsheetPickAndDropInvoiceId
				+ ", invoiceNumber=" + invoiceNumber + ", tripsheetPickAndDropId=" + tripsheetPickAndDropId
				+ ", creationDate=" + creationDate + ", lastUpdatedOn=" + lastUpdatedOn + ", createdById=" + createdById
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ "]";
	}
	
}