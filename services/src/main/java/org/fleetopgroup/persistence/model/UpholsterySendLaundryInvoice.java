package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UpholsterySendLaundryInvoice")
public class UpholsterySendLaundryInvoice implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "laundryInvoiceId")
	private Long laundryInvoiceId;
	
	@Column(name = "laundryInvoiceNumber")
	private Long laundryInvoiceNumber;
	
	@Column(name = "wareHouseLocationId")
	private Integer		wareHouseLocationId;
	
	@Column(name = "vendorId")
	private Integer		vendorId;
	
	@Column(name = "totalQuantity")
	private Double		totalQuantity;
	
	@Column(name = "receivedQuantity")
	private Double		receivedQuantity;
	
	@Column(name = "losedQuantity")
	private Double		losedQuantity;
	
	@Column(name = "damagedQuantity")
	private Double		damagedQuantity;
	
	@Column(name = "totalCost")
	private Double		totalCost;
	
	@Column(name = "sentDate")
	private Date		sentDate;
	
	@Column(name = "expectedReceiveDate")
	private Date		expectedReceiveDate;
	
	@Column(name = "paymentTypeId")
	private	short		paymentTypeId;
	
	@Column(name = "paymentNumber")
	private String		paymentNumber;
	
	@Column(name = "quoteNumber")
	private String		quoteNumber;
	
	@Column(name = "manualNumber")
	private String		manualNumber;
	
	@Column(name = "description")
	private String		description;
	
	@Column(name = "companyId")
	private Integer		companyId;
	
	@Column(name = "createdById", updatable = false)
	private Long		createdById;
	
	@Column(name = "lastModifiedById")
	private Long		lastModifiedById;
	
	@Column(name = "creationDate", updatable = false)
	private Date		creationDate;
	
	@Column(name = "lastModifiedDate")
	private Date		lastModifiedDate;
	
	@Column(name = "markForDelete")
	private boolean		markForDelete;
	
	@Column(name = "laundryInvoiceStatus")
	private short		laundryInvoiceStatus;
	
	@Column(name = "tallyCompanyId")
	private Long		tallyCompanyId;
	
	@Column(name = "balanceAmount")
	private Double		balanceAmount;
	
	@Column(name = "paidAmount")
	private Double		paidAmount;
	
	@Column(name = "discountAmount")
	private Double discountAmount;
	
	@Column(name = "vendorPaymentStatus")
	private short		vendorPaymentStatus;
	
	@Column(name = "laundryApprovalId")
	private Long 		laundryApprovalId;
	
	@Column(name = "expectedPaymentDate")
	private Timestamp	expectedPaymentDate;
	
	@Column(name = "isPendingForTally", nullable = false)
	private boolean		isPendingForTally;
	

	public Long getLaundryInvoiceId() {
		return laundryInvoiceId;
	}

	public void setLaundryInvoiceId(Long laundryInvoiceId) {
		this.laundryInvoiceId = laundryInvoiceId;
	}

	public Long getLaundryInvoiceNumber() {
		return laundryInvoiceNumber;
	}

	public void setLaundryInvoiceNumber(Long laundryInvoiceNumber) {
		this.laundryInvoiceNumber = laundryInvoiceNumber;
	}

	public Integer getWareHouseLocationId() {
		return wareHouseLocationId;
	}

	public void setWareHouseLocationId(Integer wareHouseLocationId) {
		this.wareHouseLocationId = wareHouseLocationId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Double getLosedQuantity() {
		return losedQuantity;
	}

	public void setLosedQuantity(Double losedQuantity) {
		this.losedQuantity = losedQuantity;
	}

	public Double getDamagedQuantity() {
		return damagedQuantity;
	}

	public void setDamagedQuantity(Double damagedQuantity) {
		this.damagedQuantity = damagedQuantity;
	}

	public Double getReceivedQuantity() {
		return receivedQuantity;
	}

	public void setReceivedQuantity(Double receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public Date getExpectedReceiveDate() {
		return expectedReceiveDate;
	}

	public void setExpectedReceiveDate(Date expectedReceiveDate) {
		this.expectedReceiveDate = expectedReceiveDate;
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

	public String getQuoteNumber() {
		return quoteNumber;
	}

	public void setQuoteNumber(String quoteNumber) {
		this.quoteNumber = quoteNumber;
	}

	public String getManualNumber() {
		return manualNumber;
	}

	public void setManualNumber(String manualNumber) {
		this.manualNumber = manualNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void setLastModifiedById(Long lastMOdifiedById) {
		this.lastModifiedById = lastMOdifiedById;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public short getLaundryInvoiceStatus() {
		return laundryInvoiceStatus;
	}

	public void setLaundryInvoiceStatus(short laundryInvoiceStatus) {
		this.laundryInvoiceStatus = laundryInvoiceStatus;
	}

	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public short getVendorPaymentStatus() {
		return vendorPaymentStatus;
	}

	public void setVendorPaymentStatus(short vendorPaymentStatus) {
		this.vendorPaymentStatus = vendorPaymentStatus;
	}

	public Long getLaundryApprovalId() {
		return laundryApprovalId;
	}

	public void setLaundryApprovalId(Long laundryApprovalId) {
		this.laundryApprovalId = laundryApprovalId;
	}

	public Timestamp getExpectedPaymentDate() {
		return expectedPaymentDate;
	}

	public void setExpectedPaymentDate(Timestamp expectedPaymentDate) {
		this.expectedPaymentDate = expectedPaymentDate;
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
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((totalQuantity == null) ? 0 : totalQuantity.hashCode());
		result = prime * result + ((vendorId == null) ? 0 : vendorId.hashCode());
		result = prime * result + ((wareHouseLocationId == null) ? 0 : wareHouseLocationId.hashCode());
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
		UpholsterySendLaundryInvoice other = (UpholsterySendLaundryInvoice) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (createdById == null) {
			if (other.createdById != null)
				return false;
		} else if (!createdById.equals(other.createdById))
			return false;
		if (totalQuantity == null) {
			if (other.totalQuantity != null)
				return false;
		} else if (!totalQuantity.equals(other.totalQuantity))
			return false;
		if (vendorId == null) {
			if (other.vendorId != null)
				return false;
		} else if (!vendorId.equals(other.vendorId))
			return false;
		if (wareHouseLocationId == null) {
			if (other.wareHouseLocationId != null)
				return false;
		} else if (!wareHouseLocationId.equals(other.wareHouseLocationId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpholsterySendLaundryInvoice [laundryInvoiceId=");
		builder.append(laundryInvoiceId);
		builder.append(", laundryInvoiceNumber=");
		builder.append(laundryInvoiceNumber);
		builder.append(", wareHouseLocationId=");
		builder.append(wareHouseLocationId);
		builder.append(", vendorId=");
		builder.append(vendorId);
		builder.append(", totalQuantity=");
		builder.append(totalQuantity);
		builder.append(", receivedQuantity=");
		builder.append(receivedQuantity);
		builder.append(", losedQuantity=");
		builder.append(losedQuantity);
		builder.append(", damagedQuantity=");
		builder.append(damagedQuantity);
		builder.append(", totalCost=");
		builder.append(totalCost);
		builder.append(", sentDate=");
		builder.append(sentDate);
		builder.append(", expectedReceiveDate=");
		builder.append(expectedReceiveDate);
		builder.append(", paymentTypeId=");
		builder.append(paymentTypeId);
		builder.append(", paymentNumber=");
		builder.append(paymentNumber);
		builder.append(", quoteNumber=");
		builder.append(quoteNumber);
		builder.append(", manualNumber=");
		builder.append(manualNumber);
		builder.append(", description=");
		builder.append(description);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append(", lastModifiedDate=");
		builder.append(lastModifiedDate);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", laundryInvoiceStatus=");
		builder.append(laundryInvoiceStatus);
		builder.append("]");
		return builder.toString();
	}

	
	
}
