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
@Table(name = "FuelTransferDetails")
public class FuelTransferDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "fuelTransferDetailsId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	fuelTransferDetailsId;

	@Column(name = "toPetrolPumpId")
	private Integer	toPetrolPumpId;
	
	@Column(name = "fromPetrolPumpId")
	private Integer	fromPetrolPumpId;
	
	@Column(name = "transferQuantity")
	private Double	transferQuantity;
	
	@Column(name = "rate")
	private Double	rate;
	
	@Column(name = "discount")
	private Double	discount;
	
	@Column(name = "gst")
	private Double	gst;
	
	@Column(name = "discountGstTypeId")
	private short	discountGstTypeId;
	
	@Column(name = "totalFuelCost")
	private Double	totalFuelCost;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "fuelInvoiceId")
	private Long	fuelInvoiceId;
	
	@Column(name = "createdInvoiceId")
	private Long	createdInvoiceId;
	
	@Column(name = "transferedById")
	private Long	transferedById;
	
	@Column(name = "transferedOn")
	private Date	transferedOn;
	
	@Column(name = "remark")
	private String	remark;
	
	@Column(name = "markForDelete")
	private boolean	markForDelete;
	
	public Long getFuelTransferDetailsId() {
		return fuelTransferDetailsId;
	}

	public void setFuelTransferDetailsId(Long fuelTransferDetailsId) {
		this.fuelTransferDetailsId = fuelTransferDetailsId;
	}

	public Integer getToPetrolPumpId() {
		return toPetrolPumpId;
	}

	public void setToPetrolPumpId(Integer toPetrolPumpId) {
		this.toPetrolPumpId = toPetrolPumpId;
	}

	public Integer getFromPetrolPumpId() {
		return fromPetrolPumpId;
	}

	public void setFromPetrolPumpId(Integer fromPetrolPumpId) {
		this.fromPetrolPumpId = fromPetrolPumpId;
	}

	public Double getTransferQuantity() {
		return transferQuantity;
	}

	public void setTransferQuantity(Double transferQuantity) {
		this.transferQuantity = transferQuantity;
	}

	public Double getTotalFuelCost() {
		return totalFuelCost;
	}

	public void setTotalFuelCost(Double totalFuelCost) {
		this.totalFuelCost = totalFuelCost;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getFuelInvoiceId() {
		return fuelInvoiceId;
	}

	public void setFuelInvoiceId(Long fuelInvoiceId) {
		this.fuelInvoiceId = fuelInvoiceId;
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

	public Long getTransferedById() {
		return transferedById;
	}

	public void setTransferedById(Long transferedById) {
		this.transferedById = transferedById;
	}

	public Date getTransferedOn() {
		return transferedOn;
	}

	public void setTransferedOn(Date transferedOn) {
		this.transferedOn = transferedOn;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreatedInvoiceId() {
		return createdInvoiceId;
	}

	public void setCreatedInvoiceId(Long createdInvoiceId) {
		this.createdInvoiceId = createdInvoiceId;
	}
	

}
