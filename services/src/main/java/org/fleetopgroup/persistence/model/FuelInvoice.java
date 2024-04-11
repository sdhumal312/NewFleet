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
@Table(name = "FuelInvoice")
public class FuelInvoice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "fuelInvoiceId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	fuelInvoiceId;
	
	@Column(name = "fuelInvoiceNumber")
	private Long	fuelInvoiceNumber;
	
	@Column(name = "quantity")
	private Double	quantity;
	
	@Column(name = "rate")
	private Double	rate;
	
	@Column(name = "discount")
	private Double	discount;
	
	@Column(name = "gst")
	private Double	gst;
	
	@Column(name = "discountGstTypeId")
	private short	discountGstTypeId;
	
	@Column(name = "totalAmount")
	private Double	totalAmount;
	
	@Column(name = "invoiceDate")
	private Date	invoiceDate;
	
	@Column(name = "invoiceNumber")
	private String	invoiceNumber;
	
	@Column(name = "invoiceAmount")
	private Double	invoiceAmount;
	
	@Column(name = "vendorId")
	private Integer vendorId;
	
	@Column(name = "paymentTypeId")
	private short	paymentTypeId;
	
	@Column(name = "tallyCompanyId")
	private Long	tallyCompanyId;
	
	@Column(name = "paymentNumber")
	private String	paymentNumber;
	
	@Column(name = "poNumber")
	private String	poNumber;
	
	@Column(name = "description")
	private String	description;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "documentId")
	private Long	documentId;
	
	@Column(name = "createdById")
	private Long	createdById;
	
	@Column(name = "lastUpdatedById")
	private Long	lastUpdatedById;
	
	@Column(name = "createdOn")
	private Date	createdOn;
	
	@Column(name = "lastUpdatedOn")
	private Date	lastUpdatedOn;
	
	@Column(name = "markForDelete")
	private boolean	markForDelete;
	
	@Column(name = "petrolPumpId")
	private Integer petrolPumpId;
	
	@Column(name = "balanceStock")
	private Double balanceStock;
	
	@Column(name = "updatedStock")
	private Double updatedStock;
	
	@Column(name = "stockTypeId")
	private short stockTypeId;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "approvalId")
	private Long 	approvalId;
	
	@Column(name = "paymentStatusId")
	private short	paymentStatusId;
	
	@Column(name = "transferedFrom") 
	private Long transferedFrom;
	
//	@Column(name = "createdAsTransfered", nullable = false) 
//	private boolean	createdAsTransfered;

	public FuelInvoice() {
		super();
	}

	public FuelInvoice(Long fuelInvoiceId, Long fuelInvoiceNumber, Double quantity, Double rate, Double discount,
			Double gst, short discountGstTypeId, Double totalAmount, Date invoiceDate, String invoiceNumber,
			Double invoiceAmount, Integer vendorId, short paymentTypeId, Long tallyCompanyId, String paymentNumber,
			String poNumber, String description, Integer companyId, Long documentId, Long createdById,
			Long lastUpdatedById, Date createdOn, Date lastUpdatedOn, boolean markForDelete, Integer petrolPumpId,
			Double balanceStock, Double updatedStock, short stockTypeId) {
		super();
		this.fuelInvoiceId = fuelInvoiceId;
		this.fuelInvoiceNumber = fuelInvoiceNumber;
		this.quantity = quantity;
		this.rate = rate;
		this.discount = discount;
		this.gst = gst;
		this.discountGstTypeId = discountGstTypeId;
		this.totalAmount = totalAmount;
		this.invoiceDate = invoiceDate;
		this.invoiceNumber = invoiceNumber;
		this.invoiceAmount = invoiceAmount;
		this.vendorId = vendorId;
		this.paymentTypeId = paymentTypeId;
		this.tallyCompanyId = tallyCompanyId;
		this.paymentNumber = paymentNumber;
		this.poNumber = poNumber;
		this.description = description;
		this.companyId = companyId;
		this.documentId = documentId;
		this.createdById = createdById;
		this.lastUpdatedById = lastUpdatedById;
		this.createdOn = createdOn;
		this.lastUpdatedOn = lastUpdatedOn;
		this.markForDelete = markForDelete;
		this.petrolPumpId = petrolPumpId;
		this.balanceStock = balanceStock;
		this.updatedStock = updatedStock;
		this.stockTypeId = stockTypeId;
	}


	public Long getFuelInvoiceId() {
		return fuelInvoiceId;
	}

	public void setFuelInvoiceId(Long fuelInvoiceId) {
		this.fuelInvoiceId = fuelInvoiceId;
	}

	public Long getFuelInvoiceNumber() {
		return fuelInvoiceNumber;
	}

	public void setFuelInvoiceNumber(Long fuelInvoiceNumber) {
		this.fuelInvoiceNumber = fuelInvoiceNumber;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = gst;
	}

	public short getDiscountGstTypeId() {
		return discountGstTypeId;
	}

	public void setDiscountGstTypeId(short discountGstTypeId) {
		this.discountGstTypeId = discountGstTypeId;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
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


	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
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

	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}

	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Integer getPetrolPumpId() {
		return petrolPumpId;
	}

	public void setPetrolPumpId(Integer petrolPumpId) {
		this.petrolPumpId = petrolPumpId;
	}

	public Double getBalanceStock() {
		return balanceStock;
	}

	public void setBalanceStock(Double balanceStock) {
		this.balanceStock = balanceStock;
	}

	public Double getUpdatedStock() {
		return updatedStock;
	}

	public void setUpdatedStock(Double updatedStock) {
		this.updatedStock = updatedStock;
	}

	public short getStockTypeId() {
		return stockTypeId;
	}

	public void setStockTypeId(short stockTypeId) {
		this.stockTypeId = stockTypeId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public String getPaymentNumber() {
		return paymentNumber;
	}

	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}


	public Long getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}

	public short getPaymentStatusId() {
		return paymentStatusId;
	}

	public void setPaymentStatusId(short paymentStatusId) {
		this.paymentStatusId = paymentStatusId;
	}

	public Long getTransferedFrom() {
		return transferedFrom;
	}

	public void setTransferedFrom(Long transferedFrom) {
		this.transferedFrom = transferedFrom;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((invoiceDate == null) ? 0 : invoiceDate.hashCode());
		result = prime * result + ((invoiceNumber == null) ? 0 : invoiceNumber.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
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
		FuelInvoice other = (FuelInvoice) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (invoiceDate == null) {
			if (other.invoiceDate != null)
				return false;
		} else if (!invoiceDate.equals(other.invoiceDate))
			return false;
		if (invoiceNumber == null) {
			if (other.invoiceNumber != null)
				return false;
		} else if (!invoiceNumber.equals(other.invoiceNumber))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		return true;
	}
}
