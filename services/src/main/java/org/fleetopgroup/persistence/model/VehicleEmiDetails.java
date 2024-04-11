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
@Table(name = "VehicleEmiDetails")
public class VehicleEmiDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleEmiDetailsId")
	private Long 		vehicleEmiDetailsId;
	
	@Column(name = "bankAccountId", nullable = false)
	private Long		bankAccountId;
	
	@Column(name = "loanAmount", nullable = false)
	private Double		loanAmount;
	
	@Column(name = "monthlyEmiAmount", nullable = false)
	private Double		monthlyEmiAmount;
	
	@Column(name = "downPaymentAmount")
	private Double		downPaymentAmount;
	
	@Column(name = "tenure", nullable = false)
	private Integer		tenure;
	
	@Column(name = "tenureType", nullable = false)
	private Short		tenureType;
	
	@Column(name = "loanStartDate")
	private Timestamp	loanStartDate;
	
	@Column(name = "loanEndDate")
	private Timestamp	loanEndDate;
	
	@Column(name = "interestRate")
	private Double		interestRate;
	
	@Column(name = "createdById", nullable = false, updatable = false)
	private Long		createdById;
	
	@Column(name = "lastModifiedById", nullable = false)
	private Long		lastModifiedById;
	
	@Column(name = "createdOn", nullable = false, updatable = false)
	private Timestamp	createdOn;
	
	@Column(name = "lastModifiedOn", nullable = false)
	private Timestamp	lastModifiedOn;
	
	@Column(name = "vid", nullable = false)
	private Integer		vid;
	
	@Column(name = "companyId", nullable = false)
	private Integer		companyId;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean		markForDelete;
	
	@Column(name = "emiStatus")
	private short		emiStatus;
	
	public VehicleEmiDetails() {
		super();
	}

	public VehicleEmiDetails(Long vehicleEmiDetailsId, Long bankAccountId, Double loanAmount, Double monthlyEmiAmount,
			Integer tenure, Short tenureType, Timestamp loanStartDate, Timestamp loanEndDate, Double interestRate,
			Long createdById, Long lastModifiedById, Timestamp createdOn, Timestamp lastModifiedOn, Integer vid,
			Integer companyId, boolean markForDelete, Double downPaymentAmount, Short emiStatus) {
		super();
		this.vehicleEmiDetailsId = vehicleEmiDetailsId;
		this.bankAccountId = bankAccountId;
		this.loanAmount = loanAmount;
		this.monthlyEmiAmount = monthlyEmiAmount;
		this.tenure = tenure;
		this.tenureType = tenureType;
		this.loanStartDate = loanStartDate;
		this.loanEndDate = loanEndDate;
		this.interestRate = interestRate;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.createdOn = createdOn;
		this.lastModifiedOn = lastModifiedOn;
		this.vid = vid;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
		this.downPaymentAmount = downPaymentAmount;
		this.emiStatus = emiStatus;
	}
	
	
	public Long getVehicleEmiDetailsId() {
		return vehicleEmiDetailsId;
	}

	public void setVehicleEmiDetailsId(Long vehicleEmiDetailsId) {
		this.vehicleEmiDetailsId = vehicleEmiDetailsId;
	}

	public Long getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(Long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getMonthlyEmiAmount() {
		return monthlyEmiAmount;
	}

	public void setMonthlyEmiAmount(Double monthlyEmiAmount) {
		this.monthlyEmiAmount = monthlyEmiAmount;
	}

	public Integer getTenure() {
		return tenure;
	}

	public void setTenure(Integer tenure) {
		this.tenure = tenure;
	}

	public Short getTenureType() {
		return tenureType;
	}

	public void setTenureType(Short tenureType) {
		this.tenureType = tenureType;
	}

	public Timestamp getLoanStartDate() {
		return loanStartDate;
	}

	public void setLoanStartDate(Timestamp loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	public Timestamp getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanEndDate(Timestamp loanEndDate) {
		this.loanEndDate = loanEndDate;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
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

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getDownPaymentAmount() {
		return downPaymentAmount;
	}

	public void setDownPaymentAmount(Double downPaymentAmount) {
		this.downPaymentAmount = downPaymentAmount;
	}

	public short getEmiStatus() {
		return emiStatus;
	}

	public void setEmiStatus(short emiStatus) {
		this.emiStatus = emiStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bankAccountId == null) ? 0 : bankAccountId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
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
		VehicleEmiDetails other = (VehicleEmiDetails) obj;
		if (bankAccountId == null) {
			if (other.bankAccountId != null)
				return false;
		} else if (!bankAccountId.equals(other.bankAccountId))
			return false;
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
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "VehicleEmiDetails [vehicleEmiDetailsId=" + vehicleEmiDetailsId + ", bankAccountId=" + bankAccountId
				+ ", loanAmount=" + loanAmount + ", monthlyEmiAmount=" + monthlyEmiAmount + ", downPaymentAmount="
				+ downPaymentAmount + ", tenure=" + tenure + ", tenureType=" + tenureType + ", loanStartDate="
				+ loanStartDate + ", loanEndDate=" + loanEndDate + ", interestRate=" + interestRate + ", createdById="
				+ createdById + ", lastModifiedById=" + lastModifiedById + ", createdOn=" + createdOn
				+ ", lastModifiedOn=" + lastModifiedOn + ", vid=" + vid + ", companyId=" + companyId + ", remark="
				+ remark + ", markForDelete=" + markForDelete + ", emiStatus=" + emiStatus + "]";
	}
	
}
