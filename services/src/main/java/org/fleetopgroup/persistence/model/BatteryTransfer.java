package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BatteryTransfer")
public class BatteryTransfer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "batteryTransferId")
	private Long batteryTransferId;
	
	@Column(name = "batteryId")
	private Long batteryId;
	
	@Column(name = "fromLocationId")
	private Integer fromLocationId;
	
	@Column(name = "toLocationId")
	private Integer toLocationId;
	
	@Column(name = "transferQuantity")
	private Integer	transferQuantity;
	
	@Column(name = "transferDate")
	private Timestamp	transferDate;
	
	@Column(name = "transferById")
	private Long	transferById;
	
	@Column(name = "receiveById")
	private Long	receiveById;
	
	@Column(name = "receiveDate")
	private Timestamp	receiveDate;
	
	@Column(name = "receiveRemark")
	private String	receiveRemark;
	
	@Column(name = "transferViaId")
	private short transferViaId;

	@Column(name = "transferReason", length = 250)
	private String transferReason;

	@Column(name = "batteryInvoiceId")
	private Long batteryInvoiceId;
	
	@Column(name = "transferStausId")
	private short transferStausId;
	
	@Column(name = "markForDelete")
	private boolean markForDelete;
	
	@Column(name = "createdById")
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;
	
	@Column(name = "createdOn")
	private Timestamp createdOn;
	
	@Column(name = "lastModifiedOn")
	private Timestamp lastModifiedOn;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "requisitionTransferId")
	private Long requisitionTransferId;
	
	public BatteryTransfer() {
		super();
	}

	public BatteryTransfer(Long batteryTransferId, Long batteryId, Integer fromLocationId, Integer toLocationId,
			Integer transferQuantity, Timestamp transferDate, Long transferById, Long receiveById,
			Timestamp receiveDate, String receiveRemark, short transferViaId, String transferReason,
			Long batteryInvoiceId, short transferStausId, boolean markForDelete, Long createdById,
			Long lastModifiedById, Timestamp createdOn, Timestamp lastModifiedOn) {
		super();
		this.batteryTransferId = batteryTransferId;
		this.batteryId = batteryId;
		this.fromLocationId = fromLocationId;
		this.toLocationId = toLocationId;
		this.transferQuantity = transferQuantity;
		this.transferDate = transferDate;
		this.transferById = transferById;
		this.receiveById = receiveById;
		this.receiveDate = receiveDate;
		this.receiveRemark = receiveRemark;
		this.transferViaId = transferViaId;
		this.transferReason = transferReason;
		this.batteryInvoiceId = batteryInvoiceId;
		this.transferStausId = transferStausId;
		this.markForDelete = markForDelete;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.createdOn = createdOn;
		this.lastModifiedOn = lastModifiedOn;
	}

	
	
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getRequisitionTransferId() {
		return requisitionTransferId;
	}

	public void setRequisitionTransferId(Long requisitionTransferId) {
		this.requisitionTransferId = requisitionTransferId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batteryId == null) ? 0 : batteryId.hashCode());
		result = prime * result + ((batteryInvoiceId == null) ? 0 : batteryInvoiceId.hashCode());
		result = prime * result + ((batteryTransferId == null) ? 0 : batteryTransferId.hashCode());
		result = prime * result + ((fromLocationId == null) ? 0 : fromLocationId.hashCode());
		result = prime * result + ((toLocationId == null) ? 0 : toLocationId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BatteryTransfer other = (BatteryTransfer) obj;
		if (batteryId == null) {
			if (other.batteryId != null)
				return false;
		} else if (!batteryId.equals(other.batteryId))
			return false;
		if (batteryInvoiceId == null) {
			if (other.batteryInvoiceId != null)
				return false;
		} else if (!batteryInvoiceId.equals(other.batteryInvoiceId))
			return false;
		if (batteryTransferId == null) {
			if (other.batteryTransferId != null)
				return false;
		} else if (!batteryTransferId.equals(other.batteryTransferId))
			return false;
		if (fromLocationId == null) {
			if (other.fromLocationId != null)
				return false;
		} else if (!fromLocationId.equals(other.fromLocationId))
			return false;
		if (toLocationId == null) {
			if (other.toLocationId != null)
				return false;
		} else if (!toLocationId.equals(other.toLocationId))
			return false;
		return true;
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
