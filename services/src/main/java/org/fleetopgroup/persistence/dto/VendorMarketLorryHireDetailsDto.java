package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class VendorMarketLorryHireDetailsDto {

	private Long	lorryHireDetailsId;
	
	private Long	lorryHireDetailsNumber;
	
	private Integer 	vendorId;
	
	private Integer		vid;
	
	private Double		lorryHire;
	
	private Double		advanceAmount;
	
	private Double		balanceAmount;
	
	private Double		paidAmount;
	
	private short		paymentStatusId;
	
	private Timestamp	hireDate;
	
	private Timestamp	paymentDate;
	
	private Long		paymentById;
	
	private Integer		companyId;
	
	private Long		createdById;
	
	private Long		lastModifiedById;
	
	private Timestamp	createdOn;
	
	private Timestamp	lastModifiedOn;
	
	private String		vehicle_registration;
	
	private String		hireDateStr;
	
	private String		vendorName;
	
	private String		paymentStatus;
	
	private Double		otherCharges;
	
	private String		createdBy;
	
	private String		lastModifiedBy;
	
	private String		createdOnStr;
	
	private String		lastModifiedOnStr;
	
	private String		 expenseName;
	
	private Double			amount;
	
	private String    vendorLocation;
	
	private Double		receiveAmount;
	
	private short		paymentTypeId;
	
	private String		paymentMode;//CAsh,Cheque,Neft....
	
	private String		remark;
	
	private String		transactionNumber;
	
	private Timestamp	transactionDate;
	
	private String		transactionDateStr;
	
	private String		balanceAmountStr;
	
	private String		paymentStatusAction;
	
	private String		tripSheetNumber;
	
	private String		driverName;
	
	private String		incomeName;
	
	private Long		tripSheetId;
	
	private Long		driverId;
	
	private Integer		incomeId;
	
	
	
	public String getTripSheetNumber() {
		return tripSheetNumber;
	}

	public void setTripSheetNumber(String tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getIncomeName() {
		return incomeName;
	}

	public void setIncomeName(String incomeName) {
		this.incomeName = incomeName;
	}

	public Long getTripSheetId() {
		return tripSheetId;
	}

	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public Integer getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(Integer incomeId) {
		this.incomeId = incomeId;
	}

	public String getPaymentStatusAction() {
		return paymentStatusAction;
	}

	public void setPaymentStatusAction(String paymentStatusAction) {
		this.paymentStatusAction = paymentStatusAction;
	}

	public String getBalanceAmountStr() {
		return balanceAmountStr;
	}

	public void setBalanceAmountStr(String balanceAmountStr) {
		this.balanceAmountStr = balanceAmountStr;
	}

	public String getTransactionDateStr() {
		return transactionDateStr;
	}

	public void setTransactionDateStr(String transactionDateStr) {
		this.transactionDateStr = transactionDateStr;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public Double getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(Double receiveAmount) {
		this.receiveAmount = Utility.round(receiveAmount, 2);
	}

	public String getVendorLocation() {
		return vendorLocation;
	}

	public void setVendorLocation(String vendorLocation) {
		this.vendorLocation = vendorLocation;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = Utility.round(amount,2) ;
	}

	public String getExpenseName() {
		return expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public Long getLorryHireDetailsId() {
		return lorryHireDetailsId;
	}

	public void setLorryHireDetailsId(Long lorryHireDetailsId) {
		this.lorryHireDetailsId = lorryHireDetailsId;
	}

	public Long getLorryHireDetailsNumber() {
		return lorryHireDetailsNumber;
	}

	public void setLorryHireDetailsNumber(Long lorryHireDetailsNumber) {
		this.lorryHireDetailsNumber = lorryHireDetailsNumber;
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
		this.lorryHire = Utility.round(lorryHire, 2);
	}

	public Double getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(Double advanceAmount) {
		this.advanceAmount = Utility.round(advanceAmount, 2);
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = Utility.round(balanceAmount, 2) ;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = Utility.round(paidAmount, 2);
	}

	public short getPaymentStatusId() {
		return paymentStatusId;
	}

	public void setPaymentStatusId(short paymentStatusId) {
		this.paymentStatusId = paymentStatusId;
	}

	public Timestamp getHireDate() {
		return hireDate;
	}

	public void setHireDate(Timestamp hireDate) {
		this.hireDate = hireDate;
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


	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public String getHireDateStr() {
		return hireDateStr;
	}

	public void setHireDateStr(String hireDateStr) {
		this.hireDateStr = hireDateStr;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Double getOtherCharges() {
		return otherCharges;
	}

	public void setOtherCharges(Double otherCharges) {
		this.otherCharges = Utility.round(otherCharges, 2) ;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VendorMarketLorryHireDetailsDto [lorryHireDetailsId=");
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
		builder.append(", vehicle_registration=");
		builder.append(vehicle_registration);
		builder.append(", hireDateStr=");
		builder.append(hireDateStr);
		builder.append(", vendorName=");
		builder.append(vendorName);
		builder.append(", paymentStatus=");
		builder.append(paymentStatus);
		builder.append(", otherCharges=");
		builder.append(otherCharges);
		builder.append(", expenseName=");
		builder.append(expenseName);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", vendorLocation=");
		builder.append(vendorLocation);
		builder.append(", receiveAmount=");
		builder.append(receiveAmount);
		builder.append(", paymentTypeId=");
		builder.append(paymentTypeId);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", paymentMode=");
		builder.append(paymentMode);
		builder.append(", transactionNumber=");
		builder.append(transactionNumber);
		builder.append(", transactionDate=");
		builder.append(transactionDate);
		builder.append(", transactionDateStr=");
		builder.append(transactionDateStr);
		builder.append("]");
		return builder.toString();
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}



}
