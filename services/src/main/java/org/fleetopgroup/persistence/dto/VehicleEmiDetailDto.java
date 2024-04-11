package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class VehicleEmiDetailDto implements Cloneable{


	private Long 		vehicleEmiDetailsId;
	
	private Long		bankAccountId;
	
	private Double		loanAmount;
	
	private Double		monthlyEmiAmount;
	
	private Integer		tenure;
	
	private Short		tenureType;
	
	private Timestamp	loanStartDate;
	
	private Timestamp	loanEndDate;
	
	private String		loanStartDateStr;
	
	private String		loanEndDateStr;
	
	private Double		interestRate;
	
	private Long		createdById;
	
	private Long		lastModifiedById;
	
	private Timestamp	createdOn;
	
	private Timestamp	lastModifiedOn;
	
	private Integer		vid;
	
	private Integer		companyId;
	
	private boolean		markForDelete;
	
	private String		branchName;
	
	private String		accountNumber;
	
	private String		bankName;
	
	private String		abbreviation;
	
	private String 		remark;
	
	private String 		createdOnStr;
	
	private String		lastModifiedOnStr;
	
	private String		tenureTypeStr;
	
	private Double		downPaymentAmount;
	
	private short		emiStatus;
	
	private String		loanEmiDate;
	
	private String		vehicle_registration;
	
	private String		paymentStatus;// for report 
	
	private Integer		paidEmi;// for report 
	
	public Object clone() throws CloneNotSupportedException {  
		return super.clone();  
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
		this.loanAmount = Utility.round(loanAmount, 2);
	}



	public Double getMonthlyEmiAmount() {
		return monthlyEmiAmount;
	}



	public void setMonthlyEmiAmount(Double monthlyEmiAmount) {
		this.monthlyEmiAmount = Utility.round(monthlyEmiAmount, 2);
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
		this.interestRate = Utility.round(interestRate, 2);
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



	public String getTenureTypeStr() {
		return tenureTypeStr;
	}



	public void setTenureTypeStr(String tenureTypeStr) {
		this.tenureTypeStr = tenureTypeStr;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public Integer getCompanyId() {
		return companyId;
	}



	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}



	public String getBranchName() {
		return branchName;
	}



	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}



	public String getAccountNumber() {
		return accountNumber;
	}



	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}



	public String getBankName() {
		return bankName;
	}



	public void setBankName(String bankName) {
		this.bankName = bankName;
	}



	public String getAbbreviation() {
		return abbreviation;
	}



	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}



	public boolean isMarkForDelete() {
		return markForDelete;
	}



	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}



	public String getLoanStartDateStr() {
		return loanStartDateStr;
	}



	public void setLoanStartDateStr(String loanStartDateStr) {
		this.loanStartDateStr = loanStartDateStr;
	}



	public String getLoanEndDateStr() {
		return loanEndDateStr;
	}



	public void setLoanEndDateStr(String loanEndDateStr) {
		this.loanEndDateStr = loanEndDateStr;
	}



	public String getCreatedOnStr() {
		return createdOnStr;
	}



	public void setCreatedOnStr(String createdOnStr) {
		this.createdOnStr = createdOnStr;
	}



	public String getLastModifiedOnStr() {
		return lastModifiedOnStr;
	}



	public void setLastModifiedOnStr(String lastModifiedOnStr) {
		this.lastModifiedOnStr = lastModifiedOnStr;
	}
	

	public Double getDownPaymentAmount() {
		return downPaymentAmount;
	}


	public void setDownPaymentAmount(Double downPaymentAmount) {
		this.downPaymentAmount = Utility.round(downPaymentAmount,2);
	}


	public short getEmiStatus() {
		return emiStatus;
	}

	public void setEmiStatus(short emiStatus) {
		this.emiStatus = emiStatus;
	}

	public String getLoanEmiDate() {
		return loanEmiDate;
	}

	public void setLoanEmiDate(String loanEmiDate) {
		this.loanEmiDate = loanEmiDate;
	}






	@Override
	public String toString() {
		return "VehicleEmiDetailDto [vehicleEmiDetailsId=" + vehicleEmiDetailsId + ", bankAccountId=" + bankAccountId
				+ ", loanAmount=" + loanAmount + ", monthlyEmiAmount=" + monthlyEmiAmount + ", tenure=" + tenure
				+ ", tenureType=" + tenureType + ", loanStartDate=" + loanStartDate + ", loanEndDate=" + loanEndDate
				+ ", loanStartDateStr=" + loanStartDateStr + ", loanEndDateStr=" + loanEndDateStr + ", interestRate="
				+ interestRate + ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById
				+ ", createdOn=" + createdOn + ", lastModifiedOn=" + lastModifiedOn + ", vid=" + vid + ", companyId="
				+ companyId + ", markForDelete=" + markForDelete + ", branchName=" + branchName + ", accountNumber="
				+ accountNumber + ", bankName=" + bankName + ", abbreviation=" + abbreviation + ", remark=" + remark
				+ ", createdOnStr=" + createdOnStr + ", lastModifiedOnStr=" + lastModifiedOnStr + ", tenureTypeStr="
				+ tenureTypeStr + ", downPaymentAmount=" + downPaymentAmount + ", emiStatus=" + emiStatus
				+ ", loanEmiDate=" + loanEmiDate + "]";
	}


	public String getVehicle_registration() {
		return vehicle_registration;
	}


	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}


	public String getPaymentStatus() {
		return paymentStatus;
	}


	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


	public Integer getPaidEmi() {
		return paidEmi;
	}


	public void setPaidEmi(Integer paidEmi) {
		this.paidEmi = paidEmi;
	}
	

}
