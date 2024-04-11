package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.web.util.Utility;

public class TripSheetIncomeDto {

	private Long tripincomeID;

	private String incomeName;
	
	private Integer incomeId;

	private Double incomeAmount;

	private String incomePlace;
	
	private Integer incomePlaceId;

	private String incomeRefence;

	private String incomeCollectedBy;
	
	private Long incomeCollectedById;
	
	private Integer companyId;

	private String createdBy;
	
	private Long createdById;

	private Date created;

	private TripSheet tripsheet;
	
	private boolean markForDelete;
	
	private Long lHPVDetailsId;
	
	private short incomeFixedId;
	
	private String incomeFixed;

	private String	fromDate;
	
	private String	toDate;
	
	private Integer	vid;
	
	private Long	tripSheetId;
	
	private Long	tripSheetNumber;
	
	private Integer	expenseId;
	
	private Long	dispatchLedgerId;
	
	private boolean	loadingSheetIncome;
	
	private long ticketIncomeApiId;
	
	private Double netIncomeAmount;
	
	private Integer routeID;
	
	private String tripsheetIncomeDateStr;
	
	private Double fixedIncomeRate;

	private String routeName;
	
	private String createdStr;

	private Double gst;
	
	private Double commission;
	
	private boolean	isDriverExcluded;
	
	private String	driverExcludedStr;
	
	private String 	lsSourceBranch;
	
	private String  lsDestinationBranch;
	
	private String tripDateTime;
	
	public String getTripDateTime() {
		return tripDateTime;
	}

	public void setTripDateTime(String tripDateTime) {
		this.tripDateTime = tripDateTime;
	}
	
	private String billType;
	
	private short billSelectionId;
	
	public String getLsSourceBranch() {
		return lsSourceBranch;
	}

	public void setLsSourceBranch(String lsSourceBranch) {
		this.lsSourceBranch = lsSourceBranch;
	}

	public String getLsDestinationBranch() {
		return lsDestinationBranch;
	}

	public void setLsDestinationBranch(String lsDestinationBranch) {
		this.lsDestinationBranch = lsDestinationBranch;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public Integer getRouteID() {
		return routeID;
	}

	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}

	public String getTripsheetIncomeDateStr() {
		return tripsheetIncomeDateStr;
	}

	public void setTripsheetIncomeDateStr(String tripsheetIncomeDateStr) {
		this.tripsheetIncomeDateStr = tripsheetIncomeDateStr;
	}

	public Double getFixedIncomeRate() {
		return fixedIncomeRate;
	}

	public void setFixedIncomeRate(Double fixedIncomeRate) {
		this.fixedIncomeRate = Utility.round(fixedIncomeRate, 2) ;
	}

	public Double getNetIncomeAmount() {
		return netIncomeAmount;
	}

	public void setNetIncomeAmount(Double netIncomeAmount) {
		this.netIncomeAmount = Utility.round( netIncomeAmount, 2);
	}

	/**
	 * @return the tripincomeID
	 */
	public Long getTripincomeID() {
		return tripincomeID;
	}

	/**
	 * @param tripincomeID
	 *            the tripincomeID to set
	 */
	public void setTripincomeID(Long tripincomeID) {
		this.tripincomeID = tripincomeID;
	}

	/**
	 * @return the incomeName
	 */
	public String getIncomeName() {
		return incomeName;
	}

	/**
	 * @param incomeName
	 *            the incomeName to set
	 */
	public void setIncomeName(String incomeName) {
		this.incomeName = incomeName;
	}

	/**
	 * @return the incomeAmount
	 */
	public Double getIncomeAmount() {
		return incomeAmount;
	}

	/**
	 * @param incomeAmount
	 *            the incomeAmount to set
	 */
	public void setIncomeAmount(Double incomeAmount) {
		this.incomeAmount = Utility.round( incomeAmount, 2);
	}

	/**
	 * @return the incomePlace
	 */
	public String getIncomePlace() {
		return incomePlace;
	}

	/**
	 * @param incomePlace
	 *            the incomePlace to set
	 */
	public void setIncomePlace(String incomePlace) {
		this.incomePlace = incomePlace;
	}

	/**
	 * @return the incomeRefence
	 */
	public String getIncomeRefence() {
		return incomeRefence;
	}

	/**
	 * @param incomeRefence
	 *            the incomeRefence to set
	 */
	public void setIncomeRefence(String incomeRefence) {
		this.incomeRefence = incomeRefence;
	}

	/**
	 * @return the incomeCollectedBy
	 */
	public String getIncomeCollectedBy() {
		return incomeCollectedBy;
	}

	/**
	 * @param incomeCollectedBy
	 *            the incomeCollectedBy to set
	 */
	public void setIncomeCollectedBy(String incomeCollectedBy) {
		this.incomeCollectedBy = incomeCollectedBy;
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

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the incomeId
	 */
	public Integer getIncomeId() {
		return incomeId;
	}

	/**
	 * @param incomeId the incomeId to set
	 */
	public void setIncomeId(Integer incomeId) {
		this.incomeId = incomeId;
	}

	/**
	 * @return the incomePlaceId
	 */
	public Integer getIncomePlaceId() {
		return incomePlaceId;
	}

	/**
	 * @param incomePlaceId the incomePlaceId to set
	 */
	public void setIncomePlaceId(Integer incomePlaceId) {
		this.incomePlaceId = incomePlaceId;
	}

	/**
	 * @return the incomeCollectedById
	 */
	public Long getIncomeCollectedById() {
		return incomeCollectedById;
	}

	/**
	 * @param incomeCollectedById the incomeCollectedById to set
	 */
	public void setIncomeCollectedById(Long incomeCollectedById) {
		this.incomeCollectedById = incomeCollectedById;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getlHPVDetailsId() {
		return lHPVDetailsId;
	}

	public void setlHPVDetailsId(Long lHPVDetailsId) {
		this.lHPVDetailsId = lHPVDetailsId;
	}

	/**
	 * @return the tripsheet
	 */
	public TripSheet getTripsheet() {
		return tripsheet;
	}

	/**
	 * @param tripsheet
	 *            the tripsheet to set
	 */
	public void setTripsheet(TripSheet tripsheet) {
		this.tripsheet = tripsheet;
	}
	
	public short getIncomeFixedId() {
		return incomeFixedId;
	}

	public void setIncomeFixedId(short incomeFixedId) {
		this.incomeFixedId = incomeFixedId;
	}

	public String getIncomeFixed() {
		return incomeFixed;
	}

	public void setIncomeFixed(String incomeFixed) {
		this.incomeFixed = incomeFixed;
	}

	public String getFromDate() {
		return fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Long getTripSheetId() {
		return tripSheetId;
	}

	public Long getTripSheetNumber() {
		return tripSheetNumber;
	}

	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}

	public void setTripSheetNumber(Long tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}

	public Integer getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}

	public Long getDispatchLedgerId() {
		return dispatchLedgerId;
	}

	public void setDispatchLedgerId(Long dispatchLedgerId) {
		this.dispatchLedgerId = dispatchLedgerId;
	}

	public boolean isLoadingSheetIncome() {
		return loadingSheetIncome;
	}

	public void setLoadingSheetIncome(boolean loadingSheetIncome) {
		this.loadingSheetIncome = loadingSheetIncome;
	}
	
	public long getTicketIncomeApiId() {
		return ticketIncomeApiId;
	}

	public void setTicketIncomeApiId(long ticketIncomeApiId) {
		this.ticketIncomeApiId = ticketIncomeApiId;
	}

	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = Utility.round(gst, 2);
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = Utility.round( commission, 2);
	}

	public String getCreatedStr() {
		return createdStr;
	}

	public void setCreatedStr(String createdStr) {
		this.createdStr = createdStr;
	}

	public boolean isDriverExcluded() {
		return isDriverExcluded;
	}

	public void setDriverExcluded(boolean isDriverExcluded) {
		this.isDriverExcluded = isDriverExcluded;
	}

	public String getDriverExcludedStr() {
		return driverExcludedStr;
	}

	public void setDriverExcludedStr(String driverExcludedStr) {
		this.driverExcludedStr = driverExcludedStr;
	}

	
	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	
	public short getBillSelectionId() {
		return billSelectionId;
	}

	public void setBillSelectionId(short billSelectionId) {
		this.billSelectionId = billSelectionId;
	}

	@Override
	public String toString() {
		return "TripSheetIncomeDto [tripincomeID=" + tripincomeID + ", incomeName=" + incomeName + ", incomeId="
				+ incomeId + ", incomeAmount=" + incomeAmount + ", incomePlace=" + incomePlace + ", incomePlaceId="
				+ incomePlaceId + ", incomeRefence=" + incomeRefence + ", incomeCollectedBy=" + incomeCollectedBy
				+ ", incomeCollectedById=" + incomeCollectedById + ", companyId=" + companyId + ", createdBy="
				+ createdBy + ", createdById=" + createdById + ", created=" + created + ", tripsheet=" + tripsheet
				+ ", markForDelete=" + markForDelete + ", lHPVDetailsId=" + lHPVDetailsId + ", incomeFixedId="
				+ incomeFixedId + ", incomeFixed=" + incomeFixed + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", vid=" + vid + ", tripSheetId=" + tripSheetId + ", tripSheetNumber=" + tripSheetNumber
				+ ", expenseId=" + expenseId + ", dispatchLedgerId=" + dispatchLedgerId + ", loadingSheetIncome="
				+ loadingSheetIncome + ", ticketIncomeApiId=" + ticketIncomeApiId + ", netIncomeAmount="
				+ netIncomeAmount + ", routeID=" + routeID + ", tripsheetIncomeDateStr=" + tripsheetIncomeDateStr
				+ ", fixedIncomeRate=" + fixedIncomeRate + ", routeName=" + routeName + ", createdStr=" + createdStr
				+ ", gst=" + gst + ", commission=" + commission + ", isDriverExcluded=" + isDriverExcluded
				+ ", driverExcludedStr=" + driverExcludedStr + ", lsSourceBranch=" + lsSourceBranch
				+ ", lsDestinationBranch=" + lsDestinationBranch + ", tripDateTime=" + tripDateTime + ", billType="
				+ billType + ", billSelectionId=" + billSelectionId + "]";
	}
	 
	
}
