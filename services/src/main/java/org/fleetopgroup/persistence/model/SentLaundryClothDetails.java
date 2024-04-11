package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SentLaundryClothDetails")
public class SentLaundryClothDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "laundryClothDetailsId")
	private Long	laundryClothDetailsId;

	@Column(name = "clothTypesId")
	private Long	clothTypesId;
	
	@Column(name = "laundryInvoiceId")
	private Long	laundryInvoiceId;
	
	@Column(name = "quantity")
	private Double quantity;
	
	@Column(name = "receivedQuantity")
	private Double		receivedQuantity;
	
	@Column(name = "losedQuantity")
	private Double		losedQuantity;
	
	@Column(name = "damagedQuantity")
	private Double		damagedQuantity;
	
	@Column(name = "clothEachCost")
	private Double clothEachCost;

	/** The value for the PARTDISCOUNT by email field */
	@Column(name = "clothDiscount")
	private Double clothDiscount;

	/** The value for the PARTGST by email field */
	@Column(name = "clothGst")
	private Double clothGst;

	@Column(name = "clothTotal")
	private Double clothTotal;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	/** The value for the created DATE field */
	@Column(name = "creationDate", updatable = false)
	private Date creationDate;

	/** The value for the lastUpdated DATE field */
	@Basic(optional = false)
	@Column(name = "lastUpdated")
	private Date lastUpdated;

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
		this.quantity = quantity;
	}

	public Double getReceivedQuantity() {
		return receivedQuantity;
	}

	public void setReceivedQuantity(Double receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}

	public Double getLosedQuantity() {
		return losedQuantity;
	}

	public void setLosedQuantity(Double losedQuantity) {
		this.losedQuantity = losedQuantity;
	}

	public Double getDamagedQuantity() {
		return damagedQuantity;
	}

	public void setDamagedQuantity(Double damagedQuantity) {
		this.damagedQuantity = damagedQuantity;
	}

	public Double getClothEachCost() {
		return clothEachCost;
	}

	public void setClothEachCost(Double clothEachCost) {
		this.clothEachCost = clothEachCost;
	}

	public Double getClothDiscount() {
		return clothDiscount;
	}

	public void setClothDiscount(Double clothDiscount) {
		this.clothDiscount = clothDiscount;
	}

	public Double getClothGst() {
		return clothGst;
	}

	public void setClothGst(Double clothGst) {
		this.clothGst = clothGst;
	}

	public Double getClothTotal() {
		return clothTotal;
	}

	public void setClothTotal(Double clothTotal) {
		this.clothTotal = clothTotal;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SentLaundryClothDetails [laundryClothDetailsId=");
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
		builder.append("]");
		return builder.toString();
	}
	
	

}
