package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "InventoryUpholsteryTransfer")
public class InventoryUpholsteryTransfer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "upholsteryTransferId")
	private Long upholsteryTransferId;
	
	@Column(name = "clothTypesId")
	private Long clothTypesId;
	
	@Column(name = "fromLocationId")
	private Integer fromLocationId;

	@Column(name = "toLocationId")
	private Integer toLocationId;

	@Column(name = "quantity", length = 10)
	private Double quantity;

	@Column(name = "transferDate")
	private Date transferDate;

	@Column(name = "transferById")
	private Long transferById;

	@Column(name = "transferReceivedById")
	private Long transferReceivedById;

	@Column(name = "transferReceivedDate")
	private Date transferReceivedDate;

	@Column(name = "transferReceivedReason", length = 250)
	private String transferReceivedReason;

	@Column(name = "transferViaId")
	private short transferViaId;

	@Column(name = "transferReason", length = 250)
	private String transferReason;

	@Column(name = "transferStatusId")
	private short transferStatusId;
	
	@Column(name = "companyId", nullable = false)
	private Integer	companyId;
	
	@Column(name = "createdById", nullable = false)
	private Long createdById;

	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	@Column(name = "createdDate")
	private Date createdDate;
	
	@Column(name = "lastUpdatedDate")
	private Date lastUpdatedDate;
	
	@Column(name = "stockTypeId")
	private short stockTypeId;
	
	//for centrelised Transfer 
	@Column(name = "invoiceId")
	private Long invoiceId;
	
	@Column(name = "approvalId")
	private Long approvalId;
	
	
	public InventoryUpholsteryTransfer() {
		super();
	}

	public InventoryUpholsteryTransfer(Long upholsteryTransferId, Long clothTypesId, Integer fromLocationId,
			Integer toLocationId, Double quantity, Date transferDate, Long transferById, Long transferReceivedById,
			Date transferReceivedDate, String transferReceivedReason, short transferViaId, String transferReason,
			short transferStatusId, Integer companyId, Long createdById, Long lastModifiedById,
			boolean markForDelete, Date createdDate, Date lastUpdatedDate, short stockTypeId) {
		super();
		this.upholsteryTransferId = upholsteryTransferId;
		this.clothTypesId = clothTypesId;
		this.fromLocationId = fromLocationId;
		this.toLocationId = toLocationId;
		this.quantity = quantity;
		this.transferDate = transferDate;
		this.transferById = transferById;
		this.transferReceivedById = transferReceivedById;
		this.transferReceivedDate = transferReceivedDate;
		this.transferReceivedReason = transferReceivedReason;
		this.transferViaId = transferViaId;
		this.transferReason = transferReason;
		this.transferStatusId = transferStatusId;
		this.companyId = companyId;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.markForDelete = markForDelete;
		this.createdDate = createdDate;
		this.lastUpdatedDate = lastUpdatedDate;
		this.stockTypeId = stockTypeId;
	}

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
		this.quantity = quantity;
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

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}

	@Override
	public String toString() {
		return "InventoryUpholsteryTransfer [upholsteryTransferId=" + upholsteryTransferId + ", clothTypesId="
				+ clothTypesId + ", fromLocationId=" + fromLocationId + ", toLocationId=" + toLocationId + ", quantity="
				+ quantity + ", transferDate=" + transferDate + ", transferById=" + transferById
				+ ", transferReceivedById=" + transferReceivedById + ", transferReceivedDate=" + transferReceivedDate
				+ ", transferReceivedReason=" + transferReceivedReason + ", transferViaId=" + transferViaId
				+ ", transferReason=" + transferReason + ", transferStatusId=" + transferStatusId + ", companyId="
				+ companyId + ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById
				+ ", markForDelete=" + markForDelete + ", createdDate=" + createdDate + ", lastUpdatedDate="
				+ lastUpdatedDate + ", stockTypeId=" + stockTypeId + "]";
	}
	
}