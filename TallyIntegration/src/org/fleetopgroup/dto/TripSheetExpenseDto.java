package org.fleetopgroup.dto;

import java.util.Date;

public class TripSheetExpenseDto {

	private Long tripExpenseID;

	private String expenseName;
	
	private Integer expenseId;

	private Double expenseAmount;

	private String expensePlace;
	
	private Integer expensePlaceId;

	private String expenseRefence;

	private String expenseFixed;
	
	private short expenseFixedId;

	private Long fuel_id;

	private Long DHID;
	
	private Integer companyId;


	private String createdBy;
	
	private Long createdById;

	private Date created;
	
	private boolean markForDelete;
	
	private String	fromDate;
	
	private String	toDate;
	
	private Long	tripSheetId;
	
	private Long	tripSheetNumber;
	
	private String  createdStr;
	
	private boolean  isCredit;
	
	private String  vendorName;
	
	private Integer  vendorId;
	
	private String	tallyCompanyName;
	
	private String	ledgerName;
	
	private Long tallyCompanyId;
	
	private String remark;
	
	private String voucherDate;

	private Integer vid;
	
	private short expenseTypeId;

	/**
	 * @return the tripExpenseID
	 */
	public Long getTripExpenseID() {
		return tripExpenseID;
	}

	/**
	 * @return the expenseName
	 */
	public String getExpenseName() {
		return expenseName;
	}

	/**
	 * @param expenseName
	 *            the expenseName to set
	 */
	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	/**
	 * @param tripExpenseID
	 *            the tripExpenseID to set
	 */
	public void setTripExpenseID(Long tripExpenseID) {
		this.tripExpenseID = tripExpenseID;
	}

	/**
	 * @return the expenseAmount
	 */
	public Double getExpenseAmount() {
		return expenseAmount;
	}

	/**
	 * @param expenseAmount
	 *            the expenseAmount to set
	 */
	public void setExpenseAmount(Double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	/**
	 * @return the expensePlace
	 */
	public String getExpensePlace() {
		return expensePlace;
	}

	/**
	 * @param expensePlace
	 *            the expensePlace to set
	 */
	public void setExpensePlace(String expensePlace) {
		this.expensePlace = expensePlace;
	}

	/**
	 * @return the expenseRefence
	 */
	public String getExpenseRefence() {
		return expenseRefence;
	}

	/**
	 * @param expenseRefence
	 *            the expenseRefence to set
	 */
	public void setExpenseRefence(String expenseRefence) {
		this.expenseRefence = expenseRefence;
	}

	public String getExpenseFixed() {
		return expenseFixed;
	}

	public void setExpenseFixed(String expenseFixed) {
		this.expenseFixed = expenseFixed;
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

	
	public Long getFuel_id() {
		return fuel_id;
	}

	public void setFuel_id(Long fuel_id) {
		this.fuel_id = fuel_id;
	}

	public Long getDHID() {
		return DHID;
	}

	public void setDHID(Long dHID) {
		DHID = dHID;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the expenseId
	 */
	public Integer getExpenseId() {
		return expenseId;
	}

	/**
	 * @param expenseId the expenseId to set
	 */
	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}

	/**
	 * @return the expensePlaceId
	 */
	public Integer getExpensePlaceId() {
		return expensePlaceId;
	}

	/**
	 * @param expensePlaceId the expensePlaceId to set
	 */
	public void setExpensePlaceId(Integer expensePlaceId) {
		this.expensePlaceId = expensePlaceId;
	}

	/**
	 * @return the expenseFixedId
	 */
	public short getExpenseFixedId() {
		return expenseFixedId;
	}

	/**
	 * @param expenseFixedId the expenseFixedId to set
	 */
	public void setExpenseFixedId(short expenseFixedId) {
		this.expenseFixedId = expenseFixedId;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	
	public String getFromDate() {
		return fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public Long getTripSheetId() {
		return tripSheetId;
	}

	public Long getTripSheetNumber() {
		return tripSheetNumber;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}

	public void setTripSheetNumber(Long tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}

	public String getCreatedStr() {
		return createdStr;
	}

	public void setCreatedStr(String createdStr) {
		this.createdStr = createdStr;
	}

	public boolean isCredit() {
		return isCredit;
	}

	public void setCredit(boolean isCredit) {
		this.isCredit = isCredit;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getTallyCompanyName() {
		return tallyCompanyName;
	}

	public void setTallyCompanyName(String tallyCompanyName) {
		this.tallyCompanyName = tallyCompanyName;
	}

	public String getLedgerName() {
		return ledgerName;
	}

	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}

	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(String voucherDate) {
		this.voucherDate = voucherDate;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public short getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(short expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripSheetExpenseDto [tripExpenseID=");
		builder.append(tripExpenseID);
		builder.append(", expenseName=");
		builder.append(expenseName);
		builder.append(", expenseId=");
		builder.append(expenseId);
		builder.append(", expenseAmount=");
		builder.append(expenseAmount);
		builder.append(", expensePlace=");
		builder.append(expensePlace);
		builder.append(", expensePlaceId=");
		builder.append(expensePlaceId);
		builder.append(", expenseRefence=");
		builder.append(expenseRefence);
		builder.append(", expenseFixed=");
		builder.append(expenseFixed);
		builder.append(", expenseFixedId=");
		builder.append(expenseFixedId);
		builder.append(", fuel_id=");
		builder.append(fuel_id);
		builder.append(", DHID=");
		builder.append(DHID);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", created=");
		builder.append(created);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", fromDate=");
		builder.append(fromDate);
		builder.append(", toDate=");
		builder.append(toDate);
		builder.append(", tripSheetId="); 
		builder.append(tripSheetId);
		builder.append(", tripSheetNumber=");
		builder.append(tripSheetNumber);
		builder.append(", createdStr=");
		builder.append(createdStr);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", tallyCompanyId=");
		builder.append(tallyCompanyId);
		builder.append("]");
		return builder.toString();
	}

	

}
