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
@Table(name = "PartInvoice")
public class PartInvoice implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "partInvoiceId")
	private Long partInvoiceId;

	@Column(name = "partInvoiceNumber")
	private Long partInvoiceNumber;

	@Column(name = "wareHouseLocation")
	private Integer	wareHouseLocation;

	@Column(name = "invoiceNumber")
	private String	invoiceNumber;

	@Column(name = "invoiceDate")
	private Timestamp	invoiceDate;
	
	@Column(name = "vendorPaymentDate")
	private Timestamp	vendorPaymentDate;

	@Column(name = "invoiceAmount")
	private String	invoiceAmount;
	
	@Column(name = "labourCharge")
	private Double	labourCharge;

	@Column(name = "vendorId")
	private Integer	vendorId;

	@Column(name = "paymentTypeId")
	private short	paymentTypeId;
	
	@Column(name = "PAYMENT_NUMBER", length = 25)
	private String PAYMENT_NUMBER;

	@Column(name = "description")
	private String description;

	@Column(name = "vendorPaymentStatus")
	private short	vendorPaymentStatus;
	
	@Column(name = "paidAmount")
	private Double paidAmount;
	
	@Column(name = "balanceAmount")
	private Double balanceAmount;
	
	@Column(name = "discountAmount")
	private Double discountAmount;
	
	@Column(name = "partApprovalId")
	private Long 	partApprovalId;
	
	@Column(name = "quantity", length = 10)
	private Double quantity;

	@Column(name = "createdById", updatable = false, nullable = false)
	private Long createdById;

	@Column(name = "lastModifiedById")
	private Long	lastModifiedById;
	
	@Column(name = "purchaseorder_id")
	private Long	purchaseorder_id;

	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;

	@Column(name = "createdOn", nullable = false, updatable = false)
	private Timestamp	createdOn;

	@Column(name = "lastModifiedBy")
	private Timestamp	lastModifiedBy;

	@Column(name = "companyId", nullable = false)
	private Integer	companyId;
	
	@Basic(optional = false)
	@Column(name = "part_document", nullable = false)
	private boolean part_document = false;
	
	@Column(name = "part_document_id")
	private Long part_document_id;
	
	@Column(name = "expectedPaymentDate")
	private Timestamp	expectedPaymentDate;
	
	@Column(name = "tallyCompanyId")
	private Long tallyCompanyId;

	@Column(name = "isPendingForTally", nullable = false)
	private boolean isPendingForTally;
	
	@Column(name = "anyPartNumberAsign", nullable=false, columnDefinition = "bit not null default 1 ")
	private boolean	anyPartNumberAsign;
	
	/** The value for the LASTUPDATED_DATE DATE field */
	@Column(name = "lastUpdated_Date")
	private Timestamp lastUpdated_Date;
	
	@Column(name = "subLocationId")
	private Integer	subLocationId;
	

	public String getPAYMENT_NUMBER() {
		return PAYMENT_NUMBER;
	}

	public void setPAYMENT_NUMBER(String pAYMENT_NUMBER) {
		PAYMENT_NUMBER = pAYMENT_NUMBER;
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
	
	public Long getPurchaseorder_id() {
		return purchaseorder_id;
	}

	public void setPurchaseorder_id(Long purchaseorder_id) {
		this.purchaseorder_id = purchaseorder_id;
	}

	
	public Timestamp getVendorPaymentDate() {
		return vendorPaymentDate;
	}

	public void setVendorPaymentDate(Timestamp vendorPaymentDate) {
		this.vendorPaymentDate = vendorPaymentDate;
	}

	public Long getPartApprovalId() {
		return partApprovalId;
	}

	public void setPartApprovalId(Long partApprovalId) {
		this.partApprovalId = partApprovalId;
	}
	
	public short getVendorPaymentStatus() {
		return vendorPaymentStatus;
	}

	public void setVendorPaymentStatus(short vendorPaymentStatus) {
		this.vendorPaymentStatus = vendorPaymentStatus;
	}
	
	
	
	public PartInvoice() {
		super();
	}
	
	public PartInvoice(Long partInvoiceId, Long partInvoiceNumber, Integer	wareHouseLocation,
			String invoiceNumber, Timestamp	invoiceDate, String	invoiceAmount, Integer	vendorId,
			short	paymentTypeId, String description, Double quantity, Long createdById, 
			Long	lastModifiedById, boolean	markForDelete, Timestamp	createdOn, Timestamp lastModifiedBy,
			Integer	companyId, Timestamp lastUpdated_Date, boolean anyPartNumberAsign
			) {
		super();
		this.partInvoiceId = partInvoiceId;
		this.partInvoiceNumber = partInvoiceNumber;
		this.wareHouseLocation = wareHouseLocation;
		this.invoiceNumber = invoiceNumber;
		this.invoiceDate = invoiceDate;
		this.invoiceAmount = invoiceAmount;
		this.vendorId = vendorId;
		this.paymentTypeId = paymentTypeId;
		this.description = description;
		this.quantity = quantity;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.createdOn = createdOn;
		this.lastModifiedBy = lastModifiedBy;
		this.companyId = companyId;
		this.lastUpdated_Date = lastUpdated_Date;
		this.anyPartNumberAsign = anyPartNumberAsign;
	}

	
	//partInvoice
	public Long getPartInvoiceId() {
		return partInvoiceId;
	}


	public void setPartInvoiceId(Long partInvoiceId) {
		this.partInvoiceId = partInvoiceId;
	}


	public Long getPartInvoiceNumber() {
		return partInvoiceNumber;
	}


	public void setPartInvoiceNumber(Long partInvoiceNumber) {
		this.partInvoiceNumber = partInvoiceNumber;
	}


	public Integer getWareHouseLocation() {
		return wareHouseLocation;
	}


	public void setWareHouseLocation(Integer wareHouseLocation) {
		this.wareHouseLocation = wareHouseLocation;
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


	public String getInvoiceAmount() {
		return invoiceAmount;
	}


	public void setInvoiceAmount(String invoiceAmount) {
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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Double getQuantity() {
		return quantity;
	}


	public void setQuantity(Double quantity) {
		this.quantity = quantity;
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
	

	public boolean isAnyPartNumberAsign() {
		return anyPartNumberAsign;
	}



	public void setAnyPartNumberAsign(boolean anyPartNumberAsign) {
		this.anyPartNumberAsign = anyPartNumberAsign;
	}


	public Timestamp getLastUpdated_Date() {
		return lastUpdated_Date;
	}


	public void setLastUpdated_Date(Timestamp lastUpdated_Date) {
		this.lastUpdated_Date = lastUpdated_Date;
	}


	public Double getLabourCharge() {
		return labourCharge;
	}

	public void setLabourCharge(Double labourCharge) {
		this.labourCharge = labourCharge;
	}

	public boolean isPart_document() {
		return part_document;
	}

	public void setPart_document(boolean part_document) {
		this.part_document = part_document;
	}

	public Long getPart_document_id() {
		return part_document_id;
	}

	public void setPart_document_id(Long part_document_id) {
		this.part_document_id = part_document_id;
	}

	public Timestamp getExpectedPaymentDate() {
		return expectedPaymentDate;
	}

	public void setExpectedPaymentDate(Timestamp expectedPaymentDate) {
		this.expectedPaymentDate = expectedPaymentDate;
	}

	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public Integer getSubLocationId() {
		return subLocationId;
	}

	public void setSubLocationId(Integer subLocationId) {
		this.subLocationId = subLocationId;
	}

	
	public boolean isPendingForTally() {
		return isPendingForTally;
	}

	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}

	
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((invoiceAmount == null) ? 0 : invoiceAmount.hashCode());
		result = prime * result + ((invoiceDate == null) ? 0 : invoiceDate.hashCode());
		result = prime * result + paymentTypeId;
		result = prime * result + ((vendorId == null) ? 0 : vendorId.hashCode());
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
		PartInvoice other = (PartInvoice) obj;
		if (invoiceAmount == null) {
			if (other.invoiceAmount != null)
				return false;
		} else if (!invoiceAmount.equals(other.invoiceAmount))
			return false;
		if (invoiceDate == null) {
			if (other.invoiceDate != null)
				return false;
		} else if (!invoiceDate.equals(other.invoiceDate))
			return false;
		if (paymentTypeId != other.paymentTypeId)
			return false;
		if (vendorId == null) {
			if (other.vendorId != null)
				return false;
		} else if (!vendorId.equals(other.vendorId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PartInvoice [partInvoiceId=" + partInvoiceId + ", partInvoiceNumber=" + partInvoiceNumber
				+ ", wareHouseLocation=" + wareHouseLocation + ", invoiceNumber=" + invoiceNumber + ", invoiceDate="
				+ invoiceDate + ", vendorPaymentDate=" + vendorPaymentDate + ", invoiceAmount=" + invoiceAmount
				+ ", labourCharge=" + labourCharge + ", vendorId=" + vendorId + ", paymentTypeId=" + paymentTypeId
				+ ", PAYMENT_NUMBER=" + PAYMENT_NUMBER + ", description=" + description + ", vendorPaymentStatus="
				+ vendorPaymentStatus + ", paidAmount=" + paidAmount + ", balanceAmount=" + balanceAmount
				+ ", discountAmount=" + discountAmount + ", partApprovalId=" + partApprovalId + ", quantity=" + quantity
				+ ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById + ", purchaseorder_id="
				+ purchaseorder_id + ", markForDelete=" + markForDelete + ", createdOn=" + createdOn
				+ ", lastModifiedBy=" + lastModifiedBy + ", companyId=" + companyId + ", part_document=" + part_document
				+ ", part_document_id=" + part_document_id + ", expectedPaymentDate=" + expectedPaymentDate
				+ ", tallyCompanyId=" + tallyCompanyId + ", isPendingForTally=" + isPendingForTally
				+ ", anyPartNumberAsign=" + anyPartNumberAsign + ", lastUpdated_Date=" + lastUpdated_Date
				+ ", subLocationId=" + subLocationId + "]";
	}

}	