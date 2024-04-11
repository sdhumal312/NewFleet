package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class DealerServiceEntriesLabourDto {
	private Long dealerServiceEntriesLabourId;
	
	private Long dealerServiceEntriesId;
	
	private Integer labourId;
	
	private String labourName;
	
	private Double labourWorkingHours;
	
	private Double labourPerHourCost;
	
	private Double labourDiscount;
	
	private Double labourTax;
	
	private Double labourTotalCost;

	private Long createdById;
	
	private String createdBy;

	private Long lastModifiedById;
	
	private String lastModifiedBy;

	private Date creationOn;
	
	private String creationDate;

	private Date lastUpdatedOn;
	
	private String lastUpdatedDate;
	
	private Integer companyId;
	
	private boolean markForDelete;
	
	private Double labourDiscountCost;

	private Double labourTaxCost;
	
	private short labourDiscountTaxTypeId;
	
	private String labourDiscountTaxType;
	
	
	public DealerServiceEntriesLabourDto() {
		super();
	}

	public Long getDealerServiceEntriesLabourId() {
		return dealerServiceEntriesLabourId;
	}

	public Integer getLabourId() {
		return labourId;
	}

	public void setLabourId(Integer labourId) {
		this.labourId = labourId;
	}

	public Long getDealerServiceEntriesId() {
		return dealerServiceEntriesId;
	}

	public String getLabourName() {
		return labourName;
	}

	public Double getLabourWorkingHours() {
		return labourWorkingHours;
	}

	public Double getLabourPerHourCost() {
		return labourPerHourCost;
	}

	public Double getLabourDiscount() {
		return labourDiscount;
	}

	public Double getLabourTax() {
		return labourTax;
	}

	public Double getLabourTotalCost() {
		return labourTotalCost;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public Date getCreationOn() {
		return creationOn;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setDealerServiceEntriesLabourId(Long dealerServiceEntriesLabourId) {
		this.dealerServiceEntriesLabourId = dealerServiceEntriesLabourId;
	}

	public void setDealerServiceEntriesId(Long dealerServiceEntriesId) {
		this.dealerServiceEntriesId = dealerServiceEntriesId;
	}

	public void setLabourName(String labourName) {
		this.labourName = labourName;
	}

	public void setLabourWorkingHours(Double labourWorkingHours) {
		this.labourWorkingHours = Utility.round(labourWorkingHours, 2);
	}

	public void setLabourPerHourCost(Double labourPerHourCost) {
		this.labourPerHourCost =  Utility.round(labourPerHourCost, 2);
	}

	public void setLabourDiscount(Double labourDiscount) {
		this.labourDiscount =  Utility.round(labourDiscount, 2);
	}

	public void setLabourTax(Double labourTax) {
		this.labourTax =  Utility.round(labourTax, 2);
	}

	public void setLabourTotalCost(Double labourTotalCost) {
		this.labourTotalCost =  Utility.round(labourTotalCost, 2);
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	
	public Double getLabourDiscountCost() {
		return labourDiscountCost;
	}

	public Double getLabourTaxCost() {
		return labourTaxCost;
	}

	public void setLabourDiscountCost(Double labourDiscountCost) {
		this.labourDiscountCost = labourDiscountCost;
	}

	public void setLabourTaxCost(Double labourTaxCost) {
		this.labourTaxCost = labourTaxCost;
	}
	
	public short getLabourDiscountTaxTypeId() {
		return labourDiscountTaxTypeId;
	}

	public void setLabourDiscountTaxTypeId(short labourDiscountTaxTypeId) {
		this.labourDiscountTaxTypeId = labourDiscountTaxTypeId;
	}

	public String getLabourDiscountTaxType() {
		return labourDiscountTaxType;
	}

	public void setLabourDiscountTaxType(String labourDiscountTaxType) {
		this.labourDiscountTaxType = labourDiscountTaxType;
	}

	@Override
	public String toString() {
		return "DealerServiceEntriesLabourDto [dealerServiceEntriesLabourId=" + dealerServiceEntriesLabourId
				+ ", dealerServiceEntriesId=" + dealerServiceEntriesId + ", labourId=" + labourId + ", labourName="
				+ labourName + ", labourWorkingHours=" + labourWorkingHours + ", labourPerHourCost=" + labourPerHourCost
				+ ", labourDiscount=" + labourDiscount + ", labourTax=" + labourTax + ", labourTotalCost="
				+ labourTotalCost + ", createdById=" + createdById + ", createdBy=" + createdBy + ", lastModifiedById="
				+ lastModifiedById + ", lastModifiedBy=" + lastModifiedBy + ", creationOn=" + creationOn
				+ ", creationDate=" + creationDate + ", lastUpdatedOn=" + lastUpdatedOn + ", lastUpdatedDate="
				+ lastUpdatedDate + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ ", labourDiscountCost=" + labourDiscountCost + ", labourTaxCost=" + labourTaxCost
				+ ", labourDiscountTaxTypeId=" + labourDiscountTaxTypeId + ", labourDiscountTaxType="
				+ labourDiscountTaxType + "]";
	}

	
}
