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
@Table(name = "BatteryInvoice")
public class BatteryInvoice implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "batteryInvoiceId")
	private Long 		batteryInvoiceId;
	
	@Column(name = "batteryInvoiceNumber")
	private Long 		batteryInvoiceNumber;
	
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
	
	@Column(name = "totalBatteryAmount")
	private Double 		totalBatteryAmount;
	
	@Column(name = "description")
	private String 		description;
	
	@Column(name = "vendorPaymentStatus")
	private short		vendorPaymentStatus;
	
	@Column(name = "paidAmount")
	private Double paidAmount;
	
	@Column(name = "balanceAmount")
	private Double balanceAmount;
	
	@Column(name = "discountAmount")
	private Double discountAmount;
	
	@Column(name = "batteryApprovalId")
	private Long 		batteryApprovalId;
	
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
	
	@Column(name = "expectedPaymentDate")
	private Timestamp	expectedPaymentDate;
	
	@Column(name = "tallyCompanyId")
	private Long	tallyCompanyId;
	
	@Column(name = "isPendingForTally", nullable = false)
	private boolean isPendingForTally;
	
	@Basic(optional = false)
	@Column(name = "battery_document", nullable = false)
	private boolean battery_document = false;

	@Column(name = "battery_document_id")
	private Long battery_document_id;
	
	@Column(name = "subLocationId")
	private Integer	subLocationId;
	
	public BatteryInvoice() {
		super();
	}

	public BatteryInvoice(Long batteryInvoiceId, Long batteryInvoiceNumber, Integer wareHouseLocation,
			Long purchaseOrderId, String invoiceNumber, Timestamp invoiceDate, Double invoiceAmount, Integer vendorId,
			short paymentTypeId, String paymentNumber, Double totalBatteryAmount, String description,
			short vendorPaymentStatus, Long batteryApprovalId, Timestamp vendorPaymentDate, Long createdById,
			Long lastModifiedById, boolean markForDelete, Timestamp createdOn, Timestamp lastModifiedBy,
			Integer companyId, short typeOfPaymentId, Double paidAmoount,Double balanceAmount, Double discountAmount,
			boolean battery_document, Long battery_document_id) {
		super();
		this.batteryInvoiceId = batteryInvoiceId;
		this.batteryInvoiceNumber = batteryInvoiceNumber;
		this.wareHouseLocation = wareHouseLocation;
		this.purchaseOrderId = purchaseOrderId;
		this.invoiceNumber = invoiceNumber;
		this.invoiceDate = invoiceDate;
		this.invoiceAmount = invoiceAmount;
		this.vendorId = vendorId;
		this.paymentTypeId = paymentTypeId;
		this.paymentNumber = paymentNumber;
		this.totalBatteryAmount = totalBatteryAmount;
		this.description = description;
		this.vendorPaymentStatus = vendorPaymentStatus;
		this.batteryApprovalId = batteryApprovalId;
		this.vendorPaymentDate = vendorPaymentDate;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.markForDelete = markForDelete;
		this.createdOn = createdOn;
		this.lastModifiedBy = lastModifiedBy;
		this.companyId = companyId;
		this.paidAmount = paidAmoount;
		this.balanceAmount = balanceAmount;
		this.discountAmount = discountAmount;
		this.battery_document = battery_document;
		this.battery_document_id = battery_document_id;
	}
	
	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
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
	
	public Long getBatteryInvoiceId() {
		return batteryInvoiceId;
	}

	public Long getBatteryInvoiceNumber() {
		return batteryInvoiceNumber;
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

	public Double getTotalBatteryAmount() {
		return totalBatteryAmount;
	}

	public String getDescription() {
		return description;
	}

	public short getVendorPaymentStatus() {
		return vendorPaymentStatus;
	}

	public Long getBatteryApprovalId() {
		return batteryApprovalId;
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

	public void setBatteryInvoiceId(Long batteryInvoiceId) {
		this.batteryInvoiceId = batteryInvoiceId;
	}

	public void setBatteryInvoiceNumber(Long batteryInvoiceNumber) {
		this.batteryInvoiceNumber = batteryInvoiceNumber;
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

	public void setTotalBatteryAmount(Double totalBatteryAmount) {
		this.totalBatteryAmount = totalBatteryAmount;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setVendorPaymentStatus(short vendorPaymentStatus) {
		this.vendorPaymentStatus = vendorPaymentStatus;
	}

	public void setBatteryApprovalId(Long batteryApprovalId) {
		this.batteryApprovalId = batteryApprovalId;
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

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
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

	public boolean isPendingForTally() {
		return isPendingForTally;
	}

	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}

	public boolean isBattery_document() {
		return battery_document;
	}

	public void setBattery_document(boolean battery_document) {
		this.battery_document = battery_document;
	}

	public Long getBattery_document_id() {
		return battery_document_id;
	}

	public void setBattery_document_id(Long battery_document_id) {
		this.battery_document_id = battery_document_id;
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
		BatteryInvoice other = (BatteryInvoice) obj;
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
		return "BatteryInvoice [batteryInvoiceId=" + batteryInvoiceId + ", batteryInvoiceNumber=" + batteryInvoiceNumber
				+ ", wareHouseLocation=" + wareHouseLocation + ", purchaseOrderId=" + purchaseOrderId
				+ ", invoiceNumber=" + invoiceNumber + ", invoiceDate=" + invoiceDate + ", invoiceAmount="
				+ invoiceAmount + ", vendorId=" + vendorId + ", paymentTypeId=" + paymentTypeId + ", paymentNumber="
				+ paymentNumber + ", totalBatteryAmount=" + totalBatteryAmount + ", description=" + description
				+ ", vendorPaymentStatus=" + vendorPaymentStatus + ", paidAmount=" + paidAmount + ", balanceAmount="
				+ balanceAmount + ", discountAmount=" + discountAmount + ", batteryApprovalId=" + batteryApprovalId
				+ ", vendorPaymentDate=" + vendorPaymentDate + ", createdById=" + createdById + ", lastModifiedById="
				+ lastModifiedById + ", markForDelete=" + markForDelete + ", createdOn=" + createdOn
				+ ", lastModifiedBy=" + lastModifiedBy + ", companyId=" + companyId + ", poNumber=" + poNumber
				+ ", expectedPaymentDate=" + expectedPaymentDate + ", tallyCompanyId=" + tallyCompanyId
				+ ", isPendingForTally=" + isPendingForTally + ", battery_document=" + battery_document
				+ ", battery_document_id=" + battery_document_id + "]";
	}

	/*@Override
	public String toString() {
		return "BatteryInvoice [batteryInvoiceId=" + batteryInvoiceId + ", batteryInvoiceNumber=" + batteryInvoiceNumber
				+ ", wareHouseLocation=" + wareHouseLocation + ", purchaseOrderId=" + purchaseOrderId
				+ ", invoiceNumber=" + invoiceNumber + ", invoiceDate=" + invoiceDate + ", invoiceAmount="
				+ invoiceAmount + ", vendorId=" + vendorId + ", paymentTypeId=" + paymentTypeId + ", paymentNumber="
				+ paymentNumber + ", totalBatteryAmount=" + totalBatteryAmount + ", description=" + description
				+ ", vendorPaymentStatus=" + vendorPaymentStatus 
				+ ", paidAmount=" + paidAmount + ", balanceAmount=" + balanceAmount + ", batteryApprovalId="
				+ batteryApprovalId + ", vendorPaymentDate=" + vendorPaymentDate + ", createdById=" + createdById
				+ ", lastModifiedById=" + lastModifiedById + ", markForDelete=" + markForDelete + ", createdOn="
				+ createdOn + ", lastModifiedBy=" + lastModifiedBy + ", companyId=" + companyId + ", poNumber="
				+ poNumber + "]";
	}*/
	
	


	
	
}
