package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TripsheetPickAndDropInvoice")
public class TripsheetPickAndDropInvoice implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripsheetPickAndDropInvoiceId")
	private Long tripsheetPickAndDropInvoiceId;
	
	@Column(name = "tripsheetPickAndDropInvoiceSummaryId")
	private Long tripsheetPickAndDropInvoiceSummaryId;
	
	@Column(name = "tripsheetPickAndDropId")
	private Long tripsheetPickAndDropId;
	
	@Column(name = "creationDate")
	private Date	creationDate;
	
	@Column(name = "lastUpdatedOn")
	private Date	lastUpdatedOn;
	
	@Column(name = "createdById")
	private Long	createdById;
	
	@Column(name = "lastUpdatedBy")
	private Long	lastUpdatedBy;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	

	public TripsheetPickAndDropInvoice() {
		super();
	}


	public TripsheetPickAndDropInvoice(Long tripsheetPickAndDropInvoiceId, Long tripsheetPickAndDropInvoiceSummaryId,
			Long tripsheetPickAndDropId, Date creationDate, Date lastUpdatedOn, Long createdById, Long lastUpdatedBy,
			Integer companyId, boolean markForDelete) {
		super();
		this.tripsheetPickAndDropInvoiceId = tripsheetPickAndDropInvoiceId;
		this.tripsheetPickAndDropInvoiceSummaryId = tripsheetPickAndDropInvoiceSummaryId;
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


	public Long getTripsheetPickAndDropInvoiceSummaryId() {
		return tripsheetPickAndDropInvoiceSummaryId;
	}


	public void setTripsheetPickAndDropInvoiceSummaryId(Long tripsheetPickAndDropInvoiceSummaryId) {
		this.tripsheetPickAndDropInvoiceSummaryId = tripsheetPickAndDropInvoiceSummaryId;
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


	@Override
	public String toString() {
		return "TripsheetPickAndDropInvoice [tripsheetPickAndDropInvoiceId=" + tripsheetPickAndDropInvoiceId
				+ ", tripsheetPickAndDropInvoiceSummaryId=" + tripsheetPickAndDropInvoiceSummaryId
				+ ", tripsheetPickAndDropId=" + tripsheetPickAndDropId + ", creationDate=" + creationDate
				+ ", lastUpdatedOn=" + lastUpdatedOn + ", createdById=" + createdById + ", lastUpdatedBy="
				+ lastUpdatedBy + ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}

}