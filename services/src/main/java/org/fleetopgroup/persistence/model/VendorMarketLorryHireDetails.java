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
@Table(name="VendorMarketLorryHireDetails")
public class VendorMarketLorryHireDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="lorryHireDetailsId")
	private Long	lorryHireDetailsId;
	
	@Column(name="lorryHireDetailsNumber")
	private Long	lorryHireDetailsNumber;
	
	@Column(name = "vendorId")
	private Integer 	vendorId;
	
	@Column(name = "vid")
	private Integer		vid;
	
	@Column(name = "lorryHire")
	private Double		lorryHire;
	
	@Column(name = "advanceAmount")
	private Double		advanceAmount;
	
	@Column(name = "balanceAmount")
	private Double		balanceAmount;
	
	@Column(name = "paidAmount")
	private Double		paidAmount;
	
	@Column(name = "paymentStatusId")
	private short		paymentStatusId;
	
	@Column(name = "hireDate")
	private Timestamp	hireDate;
	
	@Column(name = "paymentDate")
	private Timestamp	paymentDate;
	
	@Column(name = "paymentById")
	private Long		paymentById;
	
	@Column(name = "lorryHirePaymentId")
	private Long	lorryHirePaymentId;
	
	@Column(name = "companyId")
	private Integer		companyId;
	
	@Column(name = "createdById")
	private Long		createdById;
	
	@Column(name = "lastModifiedById")
	private Long		lastModifiedById;
	
	@Column(name = "createdOn")
	private Timestamp	createdOn;
	
	@Column(name = "lastModifiedOn")
	private Timestamp	lastModifiedOn;
	
	@Column(name = "markForDelete")
	private boolean		markForDelete;
	
	@Column(name = "otherCharges")
	private Double		otherCharges;
	
	@Column(name = "tripSheetId")
	private Long	tripSheetId;
	
	@Column(name = "driverId")
	private Long driverId;
	
	@Column(name = "remark", length = 512)
	private String remark;
	
	private Integer	incomeId;

	public Integer getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(Integer incomeId) {
		this.incomeId = incomeId;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public Long getTripSheetId() {
		return tripSheetId;
	}

	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getLorryHireDetailsId() {
		return lorryHireDetailsId;
	}

	public void setLorryHireDetailsId(Long lorryHireDetailsId) {
		this.lorryHireDetailsId = lorryHireDetailsId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Double getLorryHire() {
		return lorryHire;
	}

	public void setLorryHire(Double lorryHire) {
		this.lorryHire = lorryHire;
	}

	public Double getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(Double advanceAmount) {
		this.advanceAmount = advanceAmount;
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

	public short getPaymentStatusId() {
		return paymentStatusId;
	}

	public void setPaymentStatusId(short paymentStatusId) {
		this.paymentStatusId = paymentStatusId;
	}

	public Timestamp getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Timestamp paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Long getPaymentById() {
		return paymentById;
	}

	public void setPaymentById(Long paymentById) {
		this.paymentById = paymentById;
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

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public Long getLorryHirePaymentId() {
		return lorryHirePaymentId;
	}

	public void setLorryHirePaymentId(Long lorryHirePaymentId) {
		this.lorryHirePaymentId = lorryHirePaymentId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	
	
	
	

	public Timestamp getHireDate() {
		return hireDate;
	}

	public void setHireDate(Timestamp hireDate) {
		this.hireDate = hireDate;
	}

	public Long getLorryHireDetailsNumber() {
		return lorryHireDetailsNumber;
	}

	public void setLorryHireDetailsNumber(Long lorryHireDetailsNumber) {
		this.lorryHireDetailsNumber = lorryHireDetailsNumber;
	}

	public Double getOtherCharges() {
		return otherCharges;
	}

	public void setOtherCharges(Double otherCharges) {
		this.otherCharges = otherCharges;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
		result = prime * result + ((vendorId == null) ? 0 : vendorId.hashCode());
		result = prime * result + ((vid == null) ? 0 : vid.hashCode());
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
		VendorMarketLorryHireDetails other = (VendorMarketLorryHireDetails) obj;
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
		if (createdOn == null) {
			if (other.createdOn != null)
				return false;
		} else if (!createdOn.equals(other.createdOn))
			return false;
		if (vendorId == null) {
			if (other.vendorId != null)
				return false;
		} else if (!vendorId.equals(other.vendorId))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VendorMarketLorryHireDetails [lorryHireDetailsId=");
		builder.append(lorryHireDetailsId);
		builder.append(", lorryHireDetailsNumber=");
		builder.append(lorryHireDetailsNumber);
		builder.append(", vendorId=");
		builder.append(vendorId);
		builder.append(", vid=");
		builder.append(vid);
		builder.append(", lorryHire=");
		builder.append(lorryHire);
		builder.append(", advanceAmount=");
		builder.append(advanceAmount);
		builder.append(", balanceAmount=");
		builder.append(balanceAmount);
		builder.append(", paidAmount=");
		builder.append(paidAmount);
		builder.append(", paymentStatusId=");
		builder.append(paymentStatusId);
		builder.append(", hireDate=");
		builder.append(hireDate);
		builder.append(", paymentDate=");
		builder.append(paymentDate);
		builder.append(", paymentById=");
		builder.append(paymentById);
		builder.append(", lorryHirePaymentId=");
		builder.append(lorryHirePaymentId);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", lastModifiedOn=");
		builder.append(lastModifiedOn);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", otherCharges=");
		builder.append(otherCharges);
		builder.append("]");
		return builder.toString();
	}

	
}
