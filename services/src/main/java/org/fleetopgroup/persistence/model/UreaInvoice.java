package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UreaInvoice")
public class UreaInvoice implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ureaInvoiceId")
	private Long 		ureaInvoiceId;
	
	@Column(name = "ureaInvoiceNumber")
	private Long 		ureaInvoiceNumber;
	
	@Column(name = "wareHouseLocation")
	private Integer		wareHouseLocation;
	
	@Column(name = "purchaseOrderId")
	private Long		purchaseOrderId;
	
	@Column(name = "invoiceNumber")
	private String		invoiceNumber;
	
	@Column(name = "invoiceDate")
	private Timestamp	invoiceDate;
	
	@Column(name = "invoiceAmount")
	private Double		invoiceAmount;
	
	@Column(name = "vendorId")
	private Integer		vendorId;
	
	@Column(name = "paymentTypeId")
	private short		paymentTypeId;
	
	@Column(name = "paymentNumber")
	private String		paymentNumber;
	
	@Column(name = "totalAmount")
	private Double 		totalAmount;
	
	@Column(name = "description")
	private String 		description;
	
	@Column(name = "vendorPaymentStatus")
	private short		vendorPaymentStatus;
	
	@Column(name = "ureaApprovalId")
	private Long 		ureaApprovalId;
	
	@Column(name = "vendorPaymentDate")
	private Timestamp 	vendorPaymentDate;
	
	@Column(name = "createdById", updatable = false, nullable = false)
	private Long createdById;

	
	@Column(name = "lastModifiedById")
	private Long	 lastModifiedById;

	@Column(name = "markForDelete", nullable = false)
	private boolean			 markForDelete;
	
	@Column(name = "createdOn", nullable = false, updatable = false)
	private Timestamp		createdOn;
	
	@Column(name = "lastModifiedBy")
	private Timestamp		lastModifiedBy;
	
	@Column(name = "companyId", nullable = false)
	private Integer			companyId;
	
	@Column(name = "poNumber")
	private String			poNumber;
	
	@Column(name = "quantity")
	private Double quantity;
	
	@Column(name = "tallyCompanyId")
	private Long tallyCompanyId;
	
	@Column(name = "isPendingForTally", nullable = false)
	private boolean isPendingForTally;
	
	@Basic(optional = false)
	@Column(name = "urea_document", nullable = false)
	private boolean urea_document = false;

	@Column(name = "urea_document_id")
	private Long urea_document_id;

	@Column(name = "subLocationId")
	private Integer subLocationId;
	
	@Column(name = "totalTransferQuantity")
	private Double totalTransferQuantity;
	
	public Long getUreaInvoiceId() {
		return ureaInvoiceId;
	}

	public void setUreaInvoiceId(Long ureaInvoiceId) {
		this.ureaInvoiceId = ureaInvoiceId;
	}

	public Long getUreaInvoiceNumber() {
		return ureaInvoiceNumber;
	}

	public void setUreaInvoiceNumber(Long ureaInvoiceNumber) {
		this.ureaInvoiceNumber = ureaInvoiceNumber;
	}

	public Integer getWareHouseLocation() {
		return wareHouseLocation;
	}

	public void setWareHouseLocation(Integer wareHouseLocation) {
		this.wareHouseLocation = wareHouseLocation;
	}

	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Timestamp getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Timestamp invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getPaymentNumber() {
		return paymentNumber;
	}

	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getVendorPaymentStatus() {
		return vendorPaymentStatus;
	}

	public void setVendorPaymentStatus(short vendorPaymentStatus) {
		this.vendorPaymentStatus = vendorPaymentStatus;
	}

	public Long getUreaApprovalId() {
		return ureaApprovalId;
	}

	public void setUreaApprovalId(Long ureaApprovalId) {
		this.ureaApprovalId = ureaApprovalId;
	}

	public Timestamp getVendorPaymentDate() {
		return vendorPaymentDate;
	}

	public void setVendorPaymentDate(Timestamp vendorPaymentDate) {
		this.vendorPaymentDate = vendorPaymentDate;
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

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Timestamp lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public Double getQuantity() {
		return quantity;
	}

	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public boolean isPendingForTally() {
		return isPendingForTally;
	}

	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}
	

	public boolean isUrea_document() {
		return urea_document;
	}

	public void setUrea_document(boolean urea_document) {
		this.urea_document = urea_document;
	}

	public Long getUrea_document_id() {
		return urea_document_id;
	}

	public void setUrea_document_id(Long urea_document_id) {
		this.urea_document_id = urea_document_id;
	}

	public Integer getSubLocationId() {
		return subLocationId;
	}

	public void setSubLocationId(Integer subLocationId) {
		this.subLocationId = subLocationId;
	}

	public Double getTotalTransferQuantity() {
		return totalTransferQuantity;
	}

	public void setTotalTransferQuantity(Double totalTransferQuantity) {
		this.totalTransferQuantity = totalTransferQuantity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UreaInvoice [ureaInvoiceId=");
		builder.append(ureaInvoiceId);
		builder.append(", ureaInvoiceNumber=");
		builder.append(ureaInvoiceNumber);
		builder.append(", wareHouseLocation=");
		builder.append(wareHouseLocation);
		builder.append(", purchaseOrderId=");
		builder.append(purchaseOrderId);
		builder.append(", invoiceNumber=");
		builder.append(invoiceNumber);
		builder.append(", invoiceDate=");
		builder.append(invoiceDate);
		builder.append(", invoiceAmount=");
		builder.append(invoiceAmount);
		builder.append(", vendorId=");
		builder.append(vendorId);
		builder.append(", paymentTypeId=");
		builder.append(paymentTypeId);
		builder.append(", paymentNumber=");
		builder.append(paymentNumber);
		builder.append(", totalAmount=");
		builder.append(totalAmount);
		builder.append(", description=");
		builder.append(description);
		builder.append(", vendorPaymentStatus=");
		builder.append(vendorPaymentStatus);
		builder.append(", ureaApprovalId=");
		builder.append(ureaApprovalId);
		builder.append(", vendorPaymentDate=");
		builder.append(vendorPaymentDate);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", poNumber=");
		builder.append(poNumber);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", totalTransferQuantity=");
		builder.append(totalTransferQuantity);
		builder.append("]");
		return builder.toString();
	}
	
	

}
