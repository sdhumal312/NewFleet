package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class SentLaundryClothDetailsDto {

	private Long	laundryClothDetailsId;

	private Long	clothTypesId;
	
	private Long	laundryInvoiceId;
	
	private Double  quantity;
	
	private Double	receivedQuantity;
	
	private Double	losedQuantity;
	
	private Double	damagedQuantity;
	
	private Double clothEachCost;

	private Double clothDiscount;

	private Double clothGst;

	private Double clothTotal;
	
	private Integer companyId;

	private Long 	createdById;
	
	private Long 	lastModifiedById;

	private	boolean markForDelete;

	private Date	creationDate;

	private Date 	lastUpdated;
	
	private String	clothTypeName;
	
	private double  remainingQuantity;

	public Long getLaundryClothDetailsId() {
		return laundryClothDetailsId;
	}

	public void setLaundryClothDetailsId(Long laundryClothDetailsId) {
		this.laundryClothDetailsId = laundryClothDetailsId;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public Long getLaundryInvoiceId() {
		return laundryInvoiceId;
	}

	public void setLaundryInvoiceId(Long laundryInvoiceId) {
		this.laundryInvoiceId = laundryInvoiceId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = Utility.round(quantity, 2);
	}

	public Double getReceivedQuantity() {
		return receivedQuantity;
	}

	public void setReceivedQuantity(Double receivedQuantity) {
		this.receivedQuantity =Utility.round(receivedQuantity, 2);
	}

	public Double getLosedQuantity() {
		return losedQuantity;
	}

	public void setLosedQuantity(Double losedQuantity) {
		this.losedQuantity = Utility.round(losedQuantity, 2) ;
	}

	public Double getDamagedQuantity() {
		return damagedQuantity;
	}

	public void setDamagedQuantity(Double damagedQuantity) {
		this.damagedQuantity = Utility.round(damagedQuantity, 2) ;
	}

	public Double getClothEachCost() {
		return clothEachCost;
	}

	public void setClothEachCost(Double clothEachCost) {
		this.clothEachCost = Utility.round(clothEachCost,2) ;
	}

	public Double getClothDiscount() {
		return clothDiscount;
	}

	public void setClothDiscount(Double clothDiscount) {
		this.clothDiscount = Utility.round(clothDiscount, 2) ;
	}

	public Double getClothGst() {
		return clothGst;
	}

	public void setClothGst(Double clothGst) {
		this.clothGst = Utility.round(clothGst, 2) ;
	}

	public Double getClothTotal() {
		return clothTotal;
	}

	public void setClothTotal(Double clothTotal) {
		this.clothTotal =  Utility.round(clothTotal, 2);
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

	public String getClothTypeName() {
		return clothTypeName;
	}

	public void setClothTypeName(String clothTypeName) {
		this.clothTypeName = clothTypeName;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public double getRemainingQuantity() {
		return remainingQuantity;
	}

	public void setRemainingQuantity(double remainingQuantity) {
		this.remainingQuantity = Utility.round(remainingQuantity, 2) ;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SentLaundryClothDetailsDto [laundryClothDetailsId=");
		builder.append(laundryClothDetailsId);
		builder.append(", clothTypesId=");
		builder.append(clothTypesId);
		builder.append(", laundryInvoiceId=");
		builder.append(laundryInvoiceId);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", receivedQuantity=");
		builder.append(receivedQuantity);
		builder.append(", losedQuantity=");
		builder.append(losedQuantity);
		builder.append(", damagedQuantity=");
		builder.append(damagedQuantity);
		builder.append(", clothEachCost=");
		builder.append(clothEachCost);
		builder.append(", clothDiscount=");
		builder.append(clothDiscount);
		builder.append(", clothGst=");
		builder.append(clothGst);
		builder.append(", clothTotal=");
		builder.append(clothTotal);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append(", lastUpdated=");
		builder.append(lastUpdated);
		builder.append(", clothTypeName=");
		builder.append(clothTypeName);
		builder.append(", remainingQuantity=");
		builder.append(remainingQuantity);
		builder.append("]");
		return builder.toString();
	}

}
