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
@Table(name="TransferRequisitionDetails")
public class TransferRequisitionDetails implements Serializable {

	private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "transferRequisitionId")
		private Long transferRequisitionId;
		
		@Column(name = "approvedRequisitionId")
		private Long approvedRequisitionId;
		
		@Column(name = "subRequisitionId")
		private Long subRequisitionId;
		
		@Column(name = "requisitionTypeId")
		private short requisitionTypeId;
		
		@Column(name = "transferStatusId")
		private short transferStatusId;
		
		@Column(name = "createdById")
		private Long createdById;
		
		@Column(name = "transactionId")
		private Long transactionId;
		
		@Column(name = "fromLocation")
		private Long fromLocation;
		
		@Column(name = "toLocation")
		private Long toLocation;
		
		@Column(name = "invoiceId")
		private Long invoiceId;
		
		@Column(name = "transferQuantity")
		private Double transferQuantity;
		
		@Column(name = "receivedQuantity")
		private Double receivedQuantity;
		
		@Column(name = "receiverId")
		private Long receiverId;

		@Column(name = "remark")
		private String remark;
		
		@Column(name = "lastModifiedById")
		private Long lastModifiedById;
		
		@Column(name = "creationOn")
		private Date creationOn;

		@Column(name = "lastUpdatedOn")
		private Date lastUpdatedOn;
		
		@Column(name = "companyId", nullable = false)
		private Integer companyId;
		
//		@Column(name = "createdInventoryId")
//		private Long createdInventoryId;
		
		@Column(name="ureaManufacturerId")
		private Long ureaManufacturerId;
		
		@Column(name = "markForDelete", nullable = false)
		private boolean markForDelete;

		public Long getTransferRequisitionId() {
			return transferRequisitionId;
		}

		public void setTransferRequisitionId(Long transferRequisitionId) {
			this.transferRequisitionId = transferRequisitionId;
		}

		public Long getApprovedRequisitionId() {
			return approvedRequisitionId;
		}

		public void setApprovedRequisitionId(Long approvedRequisitionId) {
			this.approvedRequisitionId = approvedRequisitionId;
		}

		public short getRequisitionTypeId() {
			return requisitionTypeId;
		}

		public void setRequisitionTypeId(short requisitionTypeId) {
			this.requisitionTypeId = requisitionTypeId;
		}

		public short getTransferStatusId() {
			return transferStatusId;
		}

		public void setTransferStatusId(short transferStatusId) {
			this.transferStatusId = transferStatusId;
		}

		public Long getCreatedById() {
			return createdById;
		}

		public void setCreatedById(Long createdById) {
			this.createdById = createdById;
		}

		public Long getTransactionId() {
			return transactionId;
		}

		public void setTransactionId(Long transactionId) {
			this.transactionId = transactionId;
		}

		public Long getFromLocation() {
			return fromLocation;
		}

		public void setFromLocation(Long fromLocation) {
			this.fromLocation = fromLocation;
		}

		public Long getToLocation() {
			return toLocation;
		}

		public void setToLocation(Long toLocation) {
			this.toLocation = toLocation;
		}

		public Long getInvoiceId() {
			return invoiceId;
		}

		public void setInvoiceId(Long invoiceId) {
			this.invoiceId = invoiceId;
		}

		public Double getTransferQuantity() {
			return transferQuantity;
		}

		public void setTransferQuantity(Double transferQuantity) {
			this.transferQuantity = transferQuantity;
		}

		public Long getReceiverId() {
			return receiverId;
		}

		public void setReceiverId(Long receiverId) {
			this.receiverId = receiverId;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public Long getLastModifiedById() {
			return lastModifiedById;
		}

		public void setLastModifiedById(Long lastModifiedById) {
			this.lastModifiedById = lastModifiedById;
		}

		public Date getCreationOn() {
			return creationOn;
		}

		public void setCreationOn(Date creationOn) {
			this.creationOn = creationOn;
		}

		public Date getLastUpdatedOn() {
			return lastUpdatedOn;
		}

		public void setLastUpdatedOn(Date lastUpdatedOn) {
			this.lastUpdatedOn = lastUpdatedOn;
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

//		public Long getCreatedInventoryId() {
//			return createdInventoryId;
//		}
//
//		public void setCreatedInventoryId(Long createdInventoryId) {
//			this.createdInventoryId = createdInventoryId;
//		}

		public Double getReceivedQuantity() {
			return receivedQuantity;
		}

		public void setReceivedQuantity(Double receivedQuantity) {
			this.receivedQuantity = receivedQuantity;
		}

		public Long getSubRequisitionId() {
			return subRequisitionId;
		}

		public void setSubRequisitionId(Long subRequisitionId) {
			this.subRequisitionId = subRequisitionId;
		}

		public Long getUreaManufacturerId() {
			return ureaManufacturerId;
		}

		public void setUreaManufacturerId(Long ureaManufacturerId) {
			this.ureaManufacturerId = ureaManufacturerId;
		}

		@Override
		public String toString() {
			return "TransferRequisitionDetails [transferRequisitionId=" + transferRequisitionId
					+ ", approvedRequisitionId=" + approvedRequisitionId + ", requisitionTypeId=" + requisitionTypeId
					+ ", transferStatusId=" + transferStatusId + ", createdById=" + createdById + ", transactionId="
					+ transactionId + ", fromLocation=" + fromLocation + ", toLocation=" + toLocation + ", invoiceId="
					+ invoiceId + ", transferQuantity=" + transferQuantity + ", receiverId=" + receiverId + ", remark="
					+ remark + ", lastModifiedById=" + lastModifiedById + ", creationOn=" + creationOn
					+ ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId + ", markForDelete="
					+ markForDelete + "]";
		}

}
