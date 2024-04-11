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
@Table(name = "VendorLorryHirePaymentApproval")
public class VendorLorryHirePaymentApproval implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="lorryHirePaymentId")
	private Long	lorryHirePaymentId;
	
	@Column(name="lorryHirePaymentNumber")
	private Long	lorryHirePaymentNumber;
	
	@Column(name="vendorId")
	private Integer	vendorId;
	
	@Column(name="totalAmount")
	private	Double	totalAmount;
	
	@Column(name="paymentReceivedById")
	private Long	paymentReceivedById;
	
	@Column(name="createdOn")
	private Timestamp	createdOn;
	
	@Column(name="systemDateTime")
	private Timestamp	systemDateTime;
	
	@Column(name="companyId")
	private Integer	companyId;
	
	@Column(name="markForDelete")
	private boolean	markForDelete;
	
	public VendorLorryHirePaymentApproval() {
		super();
	}

	public VendorLorryHirePaymentApproval(Long lorryHirePaymentId, Long lorryHirePaymentNumber, Integer vendorId,
			Double totalAmount, Long paymentReceivedById, Timestamp createdOn, Timestamp systemDateTime) {
		super();
		this.lorryHirePaymentId = lorryHirePaymentId;
		this.lorryHirePaymentNumber = lorryHirePaymentNumber;
		this.vendorId = vendorId;
		this.totalAmount = totalAmount;
		this.paymentReceivedById = paymentReceivedById;
		this.createdOn = createdOn;
		this.systemDateTime = systemDateTime;
	}



	public Long getLorryHirePaymentId() {
		return lorryHirePaymentId;
	}

	public void setLorryHirePaymentId(Long lorryHirePaymentId) {
		this.lorryHirePaymentId = lorryHirePaymentId;
	}

	public Long getLorryHirePaymentNumber() {
		return lorryHirePaymentNumber;
	}

	public void setLorryHirePaymentNumber(Long lorryHirePaymentNumber) {
		this.lorryHirePaymentNumber = lorryHirePaymentNumber;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getPaymentReceivedById() {
		return paymentReceivedById;
	}

	public void setPaymentReceivedById(Long paymentReceivedById) {
		this.paymentReceivedById = paymentReceivedById;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getSystemDateTime() {
		return systemDateTime;
	}

	public void setSystemDateTime(Timestamp systemDateTime) {
		this.systemDateTime = systemDateTime;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lorryHirePaymentId == null) ? 0 : lorryHirePaymentId.hashCode());
		result = prime * result + ((lorryHirePaymentNumber == null) ? 0 : lorryHirePaymentNumber.hashCode());
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
		VendorLorryHirePaymentApproval other = (VendorLorryHirePaymentApproval) obj;
		if (lorryHirePaymentId == null) {
			if (other.lorryHirePaymentId != null)
				return false;
		} else if (!lorryHirePaymentId.equals(other.lorryHirePaymentId))
			return false;
		if (lorryHirePaymentNumber == null) {
			if (other.lorryHirePaymentNumber != null)
				return false;
		} else if (!lorryHirePaymentNumber.equals(other.lorryHirePaymentNumber))
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
		StringBuilder builder = new StringBuilder();
		builder.append("VendorLorryHirePaymentApproval [lorryHirePaymentId=");
		builder.append(lorryHirePaymentId);
		builder.append(", lorryHirePaymentNumber=");
		builder.append(lorryHirePaymentNumber);
		builder.append(", vendorId=");
		builder.append(vendorId);
		builder.append(", totalAmount=");
		builder.append(totalAmount);
		builder.append(", paymentReceivedById=");
		builder.append(paymentReceivedById);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", systemDateTime=");
		builder.append(systemDateTime);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}

	
}
