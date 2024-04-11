package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class TripSheetExpenseDto {

	private Long tripExpenseID;

	private String expenseName;
	
	private Integer expenseId;

	private Double expenseAmount;

	private String expensePlace;
	
	private Integer expensePlaceId;

	private String expenseRefence;

	private String expenseFixed;
	
	private Timestamp	createdOn;
	
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
	
	private String	tripSheetNumberStr;
	
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
	
	private String	vehicle_registration;
	
	private short expenseTypeId;
	
	private String expenseType;
	
	private boolean isAvoidToDelete;

	private boolean isPendingForTally;
	
	private boolean tripSheetExpense_document;
	
	private Long tripSheetExpense_document_id;
	
	private String tripsheetExpenseBase64Document;
	
	private Double	pfAmount;
	
	private	Double	esiAmount;
	
	private Double	balanceAmount;
	
	private String		fileExtension;
	
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

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
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
		this.expenseAmount = Utility.round( expenseAmount, 2);
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

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public short getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(short expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public boolean isAvoidToDelete() {
		return isAvoidToDelete;
	}

	public void setAvoidToDelete(boolean isAvoidToDelete) {
		this.isAvoidToDelete = isAvoidToDelete;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public String getTripSheetNumberStr() {
		return tripSheetNumberStr;
	}

	public void setTripSheetNumberStr(String tripSheetNumberStr) {
		this.tripSheetNumberStr = tripSheetNumberStr;
	}

	public boolean isPendingForTally() {
		return isPendingForTally;
	}

	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}

	public boolean isTripSheetExpense_document() {
		return tripSheetExpense_document;
	}

	public void setTripSheetExpense_document(boolean tripSheetExpense_document) {
		this.tripSheetExpense_document = tripSheetExpense_document;
	}

	public Long getTripSheetExpense_document_id() {
		return tripSheetExpense_document_id;
	}

	public void setTripSheetExpense_document_id(Long tripSheetExpense_document_id) {
		this.tripSheetExpense_document_id = tripSheetExpense_document_id;
	}
	
	

	public String getTripsheetExpenseBase64Document() {
		return tripsheetExpenseBase64Document;
	}

	public void setTripsheetExpenseBase64Document(String tripsheetExpenseBase64Document) {
		this.tripsheetExpenseBase64Document = tripsheetExpenseBase64Document;
	}

	public Double getPfAmount() {
		return pfAmount;
	}

	public void setPfAmount(Double pfAmount) {
		this.pfAmount = Utility.round(pfAmount, 2);
	}

	public Double getEsiAmount() {
		return esiAmount;
	}

	public void setEsiAmount(Double esiAmount) {
		this.esiAmount = Utility.round( esiAmount, 2);
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = Utility.round(balanceAmount, 2) ;
	}
	
	public String getFileExtension() {
		return fileExtension;
	}


	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
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
		builder.append(", ledgerName=");
		builder.append(ledgerName);
		builder.append("]");
		return builder.toString();
	}

	

}
