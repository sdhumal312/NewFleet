package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class VehicleEmiPaymentDetailsDto{
	
	private Long 		vehicleEmiPaymentDetailsId;

	private Long        vehicleEmiDetailsId ;

	private Double 		monthlyEmiAmount;

	private Integer 	vid;

	private Date 		emiPaidDate;

	private Double 		emiPaidAmount;

	private String 		emiPaidRemark;

	private short		paymentTypeId;
	
	private short		paymentStatusId;

	private Long 		createdById;

	private Long 		lastModifiedById;

	private Date 		createdOn;

	private Date 		lastModifiedOn;

	private Integer 	companyId;

	private boolean 	markForDelete;
	
	private Double		loanAmount;
	
	private Double		downPaymentAmount;
	
	private String 		emiPaidDateStr;
	
	private String		branchName;
	
	private String		accountNumber;
	
	private String		bankName;
	
	private String		abbreviation;
	
	private String 		paymentTypeName;
	
	private Date 		emiLoanDate;
	
	private String		emiLoanDateStr;
	
	private String		loanEmiDate;
	
	private String		paymentStatus;//for report only
	
	private Timestamp   loanStartDate;
	
	private Timestamp   loanEndDate;
	
	public Long getVehicleEmiPaymentDetailsId() {
		return vehicleEmiPaymentDetailsId;
	}

	public void setVehicleEmiPaymentDetailsId(Long vehicleEmiPaymentDetailsId) {
		this.vehicleEmiPaymentDetailsId = vehicleEmiPaymentDetailsId;
	}

	public Long getVehicleEmiDetailsId() {
		return vehicleEmiDetailsId;
	}

	public void setVehicleEmiDetailsId(Long vehicleEmiDetailsId) {
		this.vehicleEmiDetailsId = vehicleEmiDetailsId;
	}

	public Double getMonthlyEmiAmount() {
		return monthlyEmiAmount;
	}

	public void setMonthlyEmiAmount(Double monthlyEmiAmount) {
		this.monthlyEmiAmount = Utility.round(monthlyEmiAmount,2);
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Date getEmiPaidDate() {
		return emiPaidDate;
	}

	public void setEmiPaidDate(Date emiPaidDate) {
		this.emiPaidDate = emiPaidDate;
	}

	public Double getEmiPaidAmount() {
		return emiPaidAmount;
	}

	public void setEmiPaidAmount(Double emiPaidAmount) {
		this.emiPaidAmount = Utility.round(emiPaidAmount, 2);
	}

	public String getEmiPaidRemark() {
		return emiPaidRemark;
	}

	public void setEmiPaidRemark(String emiPaidRemark) {
		this.emiPaidRemark = emiPaidRemark;
	}

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public short getPaymentStatusId() {
		return paymentStatusId;
	}

	public void setPaymentStatusId(short paymentStatusId) {
		this.paymentStatusId = paymentStatusId;
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
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
	
	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = Utility.round(loanAmount, 2);
	}

	public Double getDownPaymentAmount() {
		return downPaymentAmount;
	}

	public void setDownPaymentAmount(Double downPaymentAmount) {
		this.downPaymentAmount = Utility.round(downPaymentAmount,2);
	}

	public String getEmiPaidDateStr() {
		return emiPaidDateStr;
	}

	public void setEmiPaidDateStr(String emiPaidDateStr) {
		this.emiPaidDateStr = emiPaidDateStr;
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
	
	public String getPaymentTypeName() {
		return paymentTypeName;
	}

	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}

	public Date getEmiLoanDate() {
		return emiLoanDate;
	}

	public void setEmiLoanDate(Date emiLoanDate) {
		this.emiLoanDate = emiLoanDate;
	}

	public String getEmiLoanDateStr() {
		return emiLoanDateStr;
	}

	public void setEmiLoanDateStr(String emiLoanDateStr) {
		this.emiLoanDateStr = emiLoanDateStr;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	

	public String getLoanEmiDate() {
		return loanEmiDate;
	}

	public void setLoanEmiDate(String loanEmiDate) {
		this.loanEmiDate = loanEmiDate;
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

	@Override
	public String toString() {
		return "VehicleEmiPaymentDetailsDto [vehicleEmiPaymentDetailsId=" + vehicleEmiPaymentDetailsId
				+ ", vehicleEmiDetailsId=" + vehicleEmiDetailsId + ", monthlyEmiAmount=" + monthlyEmiAmount + ", vid="
				+ vid + ", emiPaidDate=" + emiPaidDate + ", emiPaidAmount=" + emiPaidAmount + ", emiPaidRemark="
				+ emiPaidRemark + ", paymentTypeId=" + paymentTypeId + ", paymentStatusId=" + paymentStatusId
				+ ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById + ", createdOn=" + createdOn
				+ ", lastModifiedOn=" + lastModifiedOn + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ ", loanAmount=" + loanAmount + ", downPaymentAmount=" + downPaymentAmount + ", emiPaidDateStr="
				+ emiPaidDateStr + ", branchName=" + branchName + ", accountNumber=" + accountNumber + ", bankName="
				+ bankName + ", abbreviation=" + abbreviation + ", paymentTypeName=" + paymentTypeName
				+ ", emiLoanDate=" + emiLoanDate + ", emiLoanDateStr=" + emiLoanDateStr + ", loanEmiDate=" + loanEmiDate
				+ ", paymentStatus=" + paymentStatus + ", loanStartDate=" + loanStartDate + ", loanEndDate="
				+ loanEndDate + "]";
	}

	
}