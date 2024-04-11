package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class DayWiseInventoryStockDto {

	private Long	dayWiseInventoryStockId;
	
	private Long    partId;
	
	private Integer	locationId;
	
	private String	locationName;
	
	private String	partName;
	
	private Double	addedQuantity;
	
	private Double	usedQuantity;
	
	private short 	transactionType;
	
	private String	transactionTypeStr;
	
	private Long  transactionId;
	
	private Date  transactionDate;
	
	private String	transactionDateStr;
	
	private String	transactionNumberWithType;
	
	private Integer companyId;
	
	private boolean markForDelete;
	
	private Date createdOn;
	
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
		this.addedQuantity = Utility.round(addedQuantity,2);
	}

	public Double getUsedQuantity() {
		return usedQuantity;
	}

	public void setUsedQuantity(Double usedQuantity) {
		this.usedQuantity =Utility.round(usedQuantity,2);
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

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getTransactionTypeStr() {
		return transactionTypeStr;
	}

	public void setTransactionTypeStr(String transactionTypeStr) {
		this.transactionTypeStr = transactionTypeStr;
	}

	public String getTransactionDateStr() {
		return transactionDateStr;
	}

	public void setTransactionDateStr(String transactionDateStr) {
		this.transactionDateStr = transactionDateStr;
	}
	

	

}
