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
@Table(name = "ClothInvoice")
public class ClothInvoice implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "clothInvoiceId")
	private Long 		clothInvoiceId;
	
	@Column(name = "clothInvoiceNumber")
	private Long 		clothInvoiceNumber;
	
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
	
	@Column(name = "totalClothAmount")
	private Double 		totalClothAmount;
	
	@Column(name = "description")
	private String 		description;
	
	@Column(name = "vendorPaymentStatus")
	private short		vendorPaymentStatus;
	
	@Column(name = "clothApprovalId")
	private Long 		clothApprovalId;
	
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
	
	@Column(name = "clothTypeId")
	private short	clothTypeId;
	
	@Column(name = "quantity")
	private Double quantity;
	
	@Column(name = "paidAmount")
	private Double paidAmount;
	
	@Column(name = "balanceAmount")
	private Double balanceAmount;
	
	@Column(name = "discountAmount")
	private Double discountAmount;
	
	@Column(name = "expectedPaymentDate")
	private Timestamp	expectedPaymentDate;
	
	@Column(name = "tallyCompanyId")
	private Long	tallyCompanyId;
	
	@Column(name = "isPendingForTally", nullable = false)
	private boolean isPendingForTally;
	
	@Basic(optional = false)
	@Column(name = "cloth_document", nullable = false)
	private boolean cloth_document = false;

	@Column(name = "cloth_document_id")
	private Long cloth_document_id;
	
	@Column(name = "subLocationId")
	private Integer subLocationId;
	
	public Timestamp getExpectedPaymentDate() {
		return expectedPaymentDate;
	}

	public void setExpectedPaymentDate(Timestamp expectedPaymentDate) {
		this.expectedPaymentDate = expectedPaymentDate;
	}

	public Long getClothInvoiceId() {
		return clothInvoiceId;
	}

	public Long getClothInvoiceNumber() {
		return clothInvoiceNumber;
	}

	public Integer getWareHouseLocation() {
		return wareHouseLocation;
	}

	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public Timestamp getInvoiceDate() {
		return invoiceDate;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public String getPaymentNumber() {
		return paymentNumber;
	}

	public Double getTotalClothAmount() {
		return totalClothAmount;
	}

	public String getDescription() {
		return description;
	}

	public short getVendorPaymentStatus() {
		return vendorPaymentStatus;
	}

	public Long getClothApprovalId() {
		return clothApprovalId;
	}

	public Timestamp getVendorPaymentDate() {
		return vendorPaymentDate;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public Timestamp getLastModifiedBy() {
		return lastModifiedBy;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public String getPoNumber() {
		return poNumber;
	}

	
	public void setClothInvoiceId(Long clothInvoiceId) {
		this.clothInvoiceId = clothInvoiceId;
	}

	public void setClothInvoiceNumber(Long clothInvoiceNumber) {
		this.clothInvoiceNumber = clothInvoiceNumber;
	}

	public void setWareHouseLocation(Integer wareHouseLocation) {
		this.wareHouseLocation = wareHouseLocation;
	}

	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public void setInvoiceDate(Timestamp invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	public void setTotalClothAmount(Double totalClothAmount) {
		this.totalClothAmount = totalClothAmount;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setVendorPaymentStatus(short vendorPaymentStatus) {
		this.vendorPaymentStatus = vendorPaymentStatus;
	}

	public void setClothApprovalId(Long clothApprovalId) {
		this.clothApprovalId = clothApprovalId;
	}

	public void setVendorPaymentDate(Timestamp vendorPaymentDate) {
		this.vendorPaymentDate = vendorPaymentDate;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public void setLastModifiedBy(Timestamp lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	

	public short getClothTypeId() {
		return clothTypeId;
	}

	public void setClothTypeId(short clothTypeId) {
		this.clothTypeId = clothTypeId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}


	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public boolean isPendingForTally() {
		return isPendingForTally;
	}

	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}


	public boolean isCloth_document() {
		return cloth_document;
	}

	public void setCloth_document(boolean cloth_document) {
		this.cloth_document = cloth_document;
	}

	public Long getCloth_document_id() {
		return cloth_document_id;
	}

	public void setCloth_document_id(Long cloth_document_id) {
		this.cloth_document_id = cloth_document_id;
	}

	public Integer getSubLocationId() {
		return subLocationId;
	}

	public void setSubLocationId(Integer subLocationId) {
		this.subLocationId = subLocationId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clothInvoiceId == null) ? 0 : clothInvoiceId.hashCode());
		result = prime * result + ((clothInvoiceNumber == null) ? 0 : clothInvoiceNumber.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
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
		ClothInvoice other = (ClothInvoice) obj;
		if (clothInvoiceId == null) {
			if (other.clothInvoiceId != null)
				return false;
		} else if (!clothInvoiceId.equals(other.clothInvoiceId))
			return false;
		if (clothInvoiceNumber == null) {
			if (other.clothInvoiceNumber != null)
				return false;
		} else if (!clothInvoiceNumber.equals(other.clothInvoiceNumber))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClothInvoice [clothInvoiceId=");
		builder.append(clothInvoiceId);
		builder.append(", clothInvoiceNumber=");
		builder.append(clothInvoiceNumber);
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
		builder.append(", totalClothAmount=");
		builder.append(totalClothAmount);
		builder.append(", description=");
		builder.append(description);
		builder.append(", vendorPaymentStatus=");
		builder.append(vendorPaymentStatus);
		builder.append(", clothApprovalId=");
		builder.append(clothApprovalId);
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
		builder.append(", clothTypeId=");
		builder.append(clothTypeId);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append("]");
		return builder.toString();
	}


}
