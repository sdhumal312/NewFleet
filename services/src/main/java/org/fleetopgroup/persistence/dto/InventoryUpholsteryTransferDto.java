package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class InventoryUpholsteryTransferDto {
	

	private Long upholsteryTransferId;
	
	private Long clothTypesId;
	
	private Integer fromLocationId;

	private Integer toLocationId;

	private Double quantity;

	private Date transferDate;

	private Long transferById;

	private Long transferReceivedById;

	private Date transferReceivedDate;

	private String transferReceivedReason;

	private short transferViaId;

	private String transferReason;

	private short transferStatusId;
	
	private String transferStatusStr;
	
	private Integer	companyId;
	
	private Long createdById;

	private Long lastModifiedById;

	boolean markForDelete;
	
	private Date createdDate;
	
	private Date lastUpdatedDate;
	
	private short stockTypeId;
	
	private String fromLocationStr;
	
	private String toLocationStr;
	
	private String transferByIdStr;
	
	private String transferReceivedByIdStr;
	
	private String clothTypeName;
	
	private String transferViaIdStr;
	
	private String	transferDateStr;
	
	private String stockTypeStr;
	
	private String transferReceivedDateStr;

	public Long getUpholsteryTransferId() {
		return upholsteryTransferId;
	}

	public void setUpholsteryTransferId(Long upholsteryTransferId) {
		this.upholsteryTransferId = upholsteryTransferId;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public Integer getFromLocationId() {
		return fromLocationId;
	}

	public void setFromLocationId(Integer fromLocationId) {
		this.fromLocationId = fromLocationId;
	}

	public Integer getToLocationId() {
		return toLocationId;
	}

	public void setToLocationId(Integer toLocationId) {
		this.toLocationId = toLocationId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity =Utility.round(quantity, 2);
	}

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public Long getTransferById() {
		return transferById;
	}

	public void setTransferById(Long transferById) {
		this.transferById = transferById;
	}

	public Long getTransferReceivedById() {
		return transferReceivedById;
	}

	public void setTransferReceivedById(Long transferReceivedById) {
		this.transferReceivedById = transferReceivedById;
	}

	public Date getTransferReceivedDate() {
		return transferReceivedDate;
	}

	public void setTransferReceivedDate(Date transferReceivedDate) {
		this.transferReceivedDate = transferReceivedDate;
	}

	public String getTransferReceivedReason() {
		return transferReceivedReason;
	}

	public void setTransferReceivedReason(String transferReceivedReason) {
		this.transferReceivedReason = transferReceivedReason;
	}

	public short getTransferViaId() {
		return transferViaId;
	}

	public void setTransferViaId(short transferViaId) {
		this.transferViaId = transferViaId;
	}

	public String getTransferReason() {
		return transferReason;
	}

	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}

	public short getTransferStatusId() {
		return transferStatusId;
	}

	public void setTransferStatusId(short transferStatusId) {
		this.transferStatusId = transferStatusId;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public short getStockTypeId() {
		return stockTypeId;
	}

	public void setStockTypeId(short stockTypeId) {
		this.stockTypeId = stockTypeId;
	}

	public String getFromLocationStr() {
		return fromLocationStr;
	}

	public void setFromLocationStr(String fromLocationStr) {
		this.fromLocationStr = fromLocationStr;
	}

	public String getToLocationStr() {
		return toLocationStr;
	}

	public void setToLocationStr(String toLocationStr) {
		this.toLocationStr = toLocationStr;
	}

	public String getTransferByIdStr() {
		return transferByIdStr;
	}

	public void setTransferByIdStr(String transferByIdStr) {
		this.transferByIdStr = transferByIdStr;
	}

	public String getTransferReceivedByIdStr() {
		return transferReceivedByIdStr;
	}

	public void setTransferReceivedByIdStr(String transferReceivedByIdStr) {
		this.transferReceivedByIdStr = transferReceivedByIdStr;
	}

	public String getClothTypeName() {
		return clothTypeName;
	}

	public void setClothTypeName(String clothTypeName) {
		this.clothTypeName = clothTypeName;
	}

	public String getTransferViaIdStr() {
		return transferViaIdStr;
	}

	public void setTransferViaIdStr(String transferViaIdStr) {
		this.transferViaIdStr = transferViaIdStr;
	}

	public String getTransferDateStr() {
		return transferDateStr;
	}

	public void setTransferDateStr(String transferDateStr) {
		this.transferDateStr = transferDateStr;
	}
	

	public String getStockTypeStr() {
		return stockTypeStr;
	}

	public void setStockTypeStr(String stockTypeStr) {
		this.stockTypeStr = stockTypeStr;
	}

	public String getTransferReceivedDateStr() {
		return transferReceivedDateStr;
	}

	public void setTransferReceivedDateStr(String transferReceivedDateStr) {
		this.transferReceivedDateStr = transferReceivedDateStr;
	}

	public String getTransferStatusStr() {
		return transferStatusStr;
	}

	public void setTransferStatusStr(String transferStatusStr) {
		this.transferStatusStr = transferStatusStr;
	}


	@Override
	public String toString() {
		return "InventoryUpholsteryTransferDto [upholsteryTransferId=" + upholsteryTransferId + ", clothTypesId="
				+ clothTypesId + ", fromLocationId=" + fromLocationId + ", toLocationId=" + toLocationId + ", quantity="
				+ quantity + ", transferDate=" + transferDate + ", transferById=" + transferById
				+ ", transferReceivedById=" + transferReceivedById + ", transferReceivedDate=" + transferReceivedDate
				+ ", transferReceivedReason=" + transferReceivedReason + ", transferViaId=" + transferViaId
				+ ", transferReason=" + transferReason + ", transferStatusId=" + transferStatusId + ", companyId="
				+ companyId + ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById
				+ ", markForDelete=" + markForDelete + ", createdDate=" + createdDate + ", lastUpdatedDate="
				+ lastUpdatedDate + ", stockTypeId=" + stockTypeId + ", fromLocationStr=" + fromLocationStr
				+ ", toLocationStr=" + toLocationStr + ", transferByIdStr=" + transferByIdStr
				+ ", transferReceivedByIdStr=" + transferReceivedByIdStr + ", clothTypeName=" + clothTypeName
				+ ", transferViaIdStr=" + transferViaIdStr + ", transferDateStr=" + transferDateStr 
				+ ",stockTypeStr=" +stockTypeStr+ ",transferReceivedDateStr=" + transferReceivedDateStr 
				+ ",transferStatusStr="+transferStatusStr+ "]";
	}

	

	
	

}