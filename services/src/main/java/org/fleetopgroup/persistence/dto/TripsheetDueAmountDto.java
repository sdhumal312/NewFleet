package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;


public class TripsheetDueAmountDto {
	
	private Long	tripsheetDueAmountId;
	
	private Long 	tripSheetID;
	
	private int 	driver_id;
	
	private double	dueAmount;
	
	private Date	approximateDate;
	
	private String	approximateDateStr;
	
	private Date	dueDate;
	
	private String	dueDateStr;
	
	private String	remark;
	
	private Long	createdById;
	
	private Date	creationDate;
	
	private Long	lastUpdatedBy;
	
	private Date	lastUpdatedOn;
	
	private Integer	companyId;
	
	private boolean markForDelete;
	
	private String driver_firstname;
	
	private String driver_Lastname;
	
	private Long tripSheetNumber;
	
	private String dispatchedByTimeStr;
	
	private String vehicle_registration;
	
	private String routeName;
	
	private String tripsheetCreatedStr;
	
	private String vehicleGroup;
	
	private short paymentTypeId;
	
	private String paymentTypeName;
	
	private short paymentModeId;
	
	private String paymentModeName;
	
	private double	balanceAmount;
	
	private short	dueStatus;
	
	private double	paidAmount;
	
	private String driJobType;
	
	private String paidDateStr;
	
	private short transactionMode;
	
	private String transactionName;
	
	private String transactionNo;
	
	private String	reference;
	
	private Short billSelectionId;
	
	private String billType;
	
	
	public TripsheetDueAmountDto() {
		super();
	}

	public TripsheetDueAmountDto(Long tripsheetDueAmountId, Long tripSheetID, int driver_id, double dueAmount,
			Date approximateDate, Date dueDate, String remark, Long createdById, Date creationDate, Long lastUpdatedBy,
			Date lastUpdatedOn, Integer companyId, boolean markForDelete) {
		super();
		this.tripsheetDueAmountId = tripsheetDueAmountId;
		this.tripSheetID = tripSheetID;
		this.driver_id = driver_id;
		this.dueAmount = dueAmount;
		this.approximateDate = approximateDate;
		this.dueDate = dueDate;
		this.remark = remark;
		this.createdById = createdById;
		this.creationDate = creationDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedOn = lastUpdatedOn;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}
	
	public TripsheetDueAmountDto(short paymentTypeId, String paymentTypeName) {
		super();
		this.paymentTypeId = paymentTypeId;
		this.paymentTypeName = paymentTypeName;
	}

	public Long getTripsheetDueAmountId() {
		return tripsheetDueAmountId;
	}

	public void setTripsheetDueAmountId(Long tripsheetDueAmountId) {
		this.tripsheetDueAmountId = tripsheetDueAmountId;
	}

	public Long getTripSheetID() {
		return tripSheetID;
	}

	public void setTripSheetID(Long tripSheetID) {
		this.tripSheetID = tripSheetID;
	}

	public int getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
	}

	public double getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(double dueAmount) {
		this.dueAmount = Utility.round(dueAmount, 2);
	}

	public Date getApproximateDate() {
		return approximateDate;
	}

	public void setApproximateDate(Date approximateDate) {
		this.approximateDate = approximateDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
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
	
	public String getDriver_firstname() {
		return driver_firstname;
	}

	public void setDriver_firstname(String driver_firstname) {
		this.driver_firstname = driver_firstname;
	}

	public String getDriver_Lastname() {
		return driver_Lastname;
	}

	public void setDriver_Lastname(String driver_Lastname) {
		this.driver_Lastname = driver_Lastname;
	}

	public String getApproximateDateStr() {
		return approximateDateStr;
	}

	public void setApproximateDateStr(String approximateDateStr) {
		this.approximateDateStr = approximateDateStr;
	}

	public String getDueDateStr() {
		return dueDateStr;
	}

	public void setDueDateStr(String dueDateStr) {
		this.dueDateStr = dueDateStr;
	}
	
	public Long getTripSheetNumber() {
		return tripSheetNumber;
	}

	public void setTripSheetNumber(Long tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}

	public String getDispatchedByTimeStr() {
		return dispatchedByTimeStr;
	}

	public void setDispatchedByTimeStr(String dispatchedByTimeStr) {
		this.dispatchedByTimeStr = dispatchedByTimeStr;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getTripsheetCreatedStr() {
		return tripsheetCreatedStr;
	}

	public void setTripsheetCreatedStr(String tripsheetCreatedStr) {
		this.tripsheetCreatedStr = tripsheetCreatedStr;
	}

	public String getVehicleGroup() {
		return vehicleGroup;
	}

	public void setVehicleGroup(String vehicleGroup) {
		this.vehicleGroup = vehicleGroup;
	}

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getPaymentTypeName() {
		return paymentTypeName;
	}

	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}

	public short getPaymentModeId() {
		return paymentModeId;
	}

	public void setPaymentModeId(short paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	public String getPaymentModeName() {
		return paymentModeName;
	}

	public void setPaymentModeName(String paymentModeName) {
		this.paymentModeName = paymentModeName;
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = Utility.round( balanceAmount, 2);
	}

	public short getDueStatus() {
		return dueStatus;
	}

	public void setDueStatus(short dueStatus) {
		this.dueStatus = dueStatus;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = Utility.round(paidAmount, 2) ;
	}

	public String getDriJobType() {
		return driJobType;
	}

	public void setDriJobType(String driJobType) {
		this.driJobType = driJobType;
	}

	public String getPaidDateStr() {
		return paidDateStr;
	}

	public void setPaidDateStr(String paidDateStr) {
		this.paidDateStr = paidDateStr;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public short getTransactionMode() {
		return transactionMode;
	}

	public void setTransactionMode(short transactionMode) {
		this.transactionMode = transactionMode;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	
	public Short getBillSelectionId() {
		return billSelectionId;
	}

	public void setBillSelectionId(Short billSelectionId) {
		this.billSelectionId = billSelectionId;
	}

	
	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	@Override
	public String toString() {
		return "TripsheetDueAmountDto [tripsheetDueAmountId=" + tripsheetDueAmountId + ", tripSheetID=" + tripSheetID
				+ ", driver_id=" + driver_id + ", dueAmount=" + dueAmount + ", approximateDate=" + approximateDate
				+ ", approximateDateStr=" + approximateDateStr + ", dueDate=" + dueDate + ", dueDateStr=" + dueDateStr
				+ ", remark=" + remark + ", createdById=" + createdById + ", creationDate=" + creationDate
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + ", driver_firstname=" + driver_firstname + ", driver_Lastname="
				+ driver_Lastname + ", tripSheetNumber=" + tripSheetNumber + ", dispatchedByTimeStr="
				+ dispatchedByTimeStr + ", vehicle_registration=" + vehicle_registration + ", routeName=" + routeName
				+ ", tripsheetCreatedStr=" + tripsheetCreatedStr + ", vehicleGroup=" + vehicleGroup + ", paymentTypeId="
				+ paymentTypeId + ", paymentTypeName=" + paymentTypeName + ", paymentModeId=" + paymentModeId
				+ ", paymentModeName=" + paymentModeName + ", balanceAmount=" + balanceAmount + ", dueStatus="
				+ dueStatus + ", paidAmount=" + paidAmount + ", driJobType=" + driJobType + ", paidDateStr="
				+ paidDateStr + ", transactionMode=" + transactionMode + ", transactionName=" + transactionName
				+ ", transactionNo=" + transactionNo + ", reference=" + reference + ", billSelectionId="
				+ billSelectionId + ", billType=" + billType + "]";
	}
	
}