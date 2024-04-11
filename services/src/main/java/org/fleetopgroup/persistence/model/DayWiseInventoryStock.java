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
@Table(name = "DayWiseInventoryStock")
public class DayWiseInventoryStock  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dayWiseInventoryStockId")
	private Long	dayWiseInventoryStockId;
	
	@Column(name = "partId")
	private Long    partId;
	
	@Column(name = "locationId")
	private Integer	locationId;
	
	@Column(name = "addedQuantity")
	private Double	addedQuantity;
	
	@Column(name = "usedQuantity")
	private Double	usedQuantity;
	
	@Column(name = "transactionType")
	private short transactionType;
	
	@Column(name = "transactionId")
	private Long  transactionId;
	
	@Column(name = "transactionDate")
	private Date  transactionDate;
	
	@Column(name = "transactionNumberWithType")
	private String	transactionNumberWithType;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete")
	private boolean markForDelete;
	
	@Column(name = "createdOn", nullable = false, updatable = false)
	private Date createdOn;
	
	@Column(name = "lastUpdatedOn", nullable = false)
	private Date lastUpdatedOn;


	public Long getDayWiseInventoryStockId() {
		return dayWiseInventoryStockId;
	}

	public void setDayWiseInventoryStockId(Long dayWiseInventoryStockId) {
		this.dayWiseInventoryStockId = dayWiseInventoryStockId;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Double getAddedQuantity() {
		return addedQuantity;
	}

	public void setAddedQuantity(Double addedQuantity) {
		this.addedQuantity = addedQuantity;
	}

	public Double getUsedQuantity() {
		return usedQuantity;
	}

	public void setUsedQuantity(Double usedQuantity) {
		this.usedQuantity = usedQuantity;
	}

	public short getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(short transactionType) {
		this.transactionType = transactionType;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
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

	public String getTransactionNumberWithType() {
		return transactionNumberWithType;
	}

	public void setTransactionNumberWithType(String transactionNumberWithType) {
		this.transactionNumberWithType = transactionNumberWithType;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
	

	
}
