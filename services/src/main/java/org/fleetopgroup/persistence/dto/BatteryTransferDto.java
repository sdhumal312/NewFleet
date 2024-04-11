package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

public class BatteryTransferDto {

	private Long batteryTransferId;
	
	private Long batteryId;
	
	private Integer fromLocationId;
	
	private Integer toLocationId;
	
	private Integer	transferQuantity;
	
	private Timestamp	transferDate;
	
	private Long	transferById;
	
	private Long	receiveById;
	
	private Timestamp	receiveDate;
	
	private String	receiveRemark;
	
	private short transferViaId;

	private String transferReason;

	private Long batteryInvoiceId;
	
	private short transferStausId;
	
	private boolean markForDelete;
	
	private Long createdById;
	
	private Long lastModifiedById;
	
	private Timestamp createdOn;
	
	private Timestamp lastModifiedOn;
	
	private String batterySerialNumber;
	
	private String fromLocationName;
	
	private String toLocationName;
	
	private String transferBy;
	
	private String receiveBy;
	
	private String transferDateStr;
	
	private String receiveDateStr;
	
	private String cratedOnStr;
	
	private String lastModifiedOnStr;
	
	private String transferStaus;
	
	private String transferVia;
	

	public Long getBatteryTransferId() {
		return batteryTransferId;
	}



	public void setBatteryTransferId(Long batteryTransferId) {
		this.batteryTransferId = batteryTransferId;
	}



	public Long getBatteryId() {
		return batteryId;
	}



	public void setBatteryId(Long batteryId) {
		this.batteryId = batteryId;
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



	public Integer getTransferQuantity() {
		return transferQuantity;
	}



	public void setTransferQuantity(Integer transferQuantity) {
		this.transferQuantity = transferQuantity;
	}



	public Timestamp getTransferDate() {
		return transferDate;
	}



	public void setTransferDate(Timestamp transferDate) {
		this.transferDate = transferDate;
	}



	public Long getTransferById() {
		return transferById;
	}



	public void setTransferById(Long transferById) {
		this.transferById = transferById;
	}



	public Long getReceiveById() {
		return receiveById;
	}



	public void setReceiveById(Long receiveById) {
		this.receiveById = receiveById;
	}



	public Timestamp getReceiveDate() {
		return receiveDate;
	}



	public void setReceiveDate(Timestamp receiveDate) {
		this.receiveDate = receiveDate;
	}



	public String getReceiveRemark() {
		return receiveRemark;
	}



	public void setReceiveRemark(String receiveRemark) {
		this.receiveRemark = receiveRemark;
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



	public Long getBatteryInvoiceId() {
		return batteryInvoiceId;
	}



	public void setBatteryInvoiceId(Long batteryInvoiceId) {
		this.batteryInvoiceId = batteryInvoiceId;
	}



	public short getTransferStausId() {
		return transferStausId;
	}



	public void setTransferStausId(short transferStausId) {
		this.transferStausId = transferStausId;
	}



	public boolean isMarkForDelete() {
		return markForDelete;
	}



	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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



	public Timestamp getCreatedOn() {
		return createdOn;
	}



	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}



	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}



	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}



	public String getBatterySerialNumber() {
		return batterySerialNumber;
	}



	public void setBatterySerialNumber(String batterySerialNumber) {
		this.batterySerialNumber = batterySerialNumber;
	}



	public String getFromLocationName() {
		return fromLocationName;
	}



	public void setFromLocationName(String fromLocationName) {
		this.fromLocationName = fromLocationName;
	}



	public String getToLocationName() {
		return toLocationName;
	}



	public void setToLocationName(String toLocationName) {
		this.toLocationName = toLocationName;
	}



	public String getTransferBy() {
		return transferBy;
	}



	public void setTransferBy(String transferBy) {
		this.transferBy = transferBy;
	}



	public String getReceiveBy() {
		return receiveBy;
	}



	public void setReceiveBy(String receiveBy) {
		this.receiveBy = receiveBy;
	}



	public String getTransferDateStr() {
		return transferDateStr;
	}



	public void setTransferDateStr(String transferDateStr) {
		this.transferDateStr = transferDateStr;
	}



	public String getReceiveDateStr() {
		return receiveDateStr;
	}



	public void setReceiveDateStr(String receiveDateStr) {
		this.receiveDateStr = receiveDateStr;
	}



	public String getCratedOnStr() {
		return cratedOnStr;
	}



	public void setCratedOnStr(String cratedOnStr) {
		this.cratedOnStr = cratedOnStr;
	}



	public String getLastModifiedOnStr() {
		return lastModifiedOnStr;
	}



	public void setLastModifiedOnStr(String lastModifiedOnStr) {
		this.lastModifiedOnStr = lastModifiedOnStr;
	}



	public String getTransferStaus() {
		return transferStaus;
	}



	public void setTransferStaus(String transferStaus) {
		this.transferStaus = transferStaus;
	}



	public String getTransferVia() {
		return transferVia;
	}



	public void setTransferVia(String transferVia) {
		this.transferVia = transferVia;
	}



	@Override
	public String toString() {
		return "BatteryTransfer [batteryTransferId=" + batteryTransferId + ", batteryId=" + batteryId
				+ ", fromLocationId=" + fromLocationId + ", toLocationId=" + toLocationId + ", transferQuantity="
				+ transferQuantity + ", transferDate=" + transferDate + ", transferById=" + transferById
				+ ", receiveById=" + receiveById + ", receiveDate=" + receiveDate + ", receiveRemark=" + receiveRemark
				+ ", transferViaId=" + transferViaId + ", transferReason=" + transferReason + ", batteryInvoiceId="
				+ batteryInvoiceId + ", transferStausId=" + transferStausId + ", markForDelete=" + markForDelete
				+ ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById + ", createdOn=" + createdOn
				+ ", lastModifiedOn=" + lastModifiedOn + "]";
	}
	
	

}
