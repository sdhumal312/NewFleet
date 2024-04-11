/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */
public class TyreExpenseDetailsDto {

	private Long tyreExpenseDetailsId;

	private Long tyreId;
	
	private Integer tyreExpenseId;

	private String tyreExpenseName;

	private Double tyreExpenseAmount;
	
	private Date tyreExpenseDate;
	
	private String tyreExpenseDateStr;
	
	private Double discount;
	
	private Double gst;
	
	private Double totalTyreExpenseAmount;
	
	private String description;
	
	private short tyreTypeId;
	
	private Long createdById;
	
	private Long lastUpdatedBy;

	boolean markForDelete;

	private Date creationDate;
	
	private String creationDateStr;

	private Date lastUpdatedOn;
	
	private String lastUpdatedOnStr;
	
	private String tyreNumber;
	
	private Double tyreInvoiceAmount;
	
	private Double retreadTyreInvoiceAmount;
	
	private Integer tyreUsage;

	private Double totalTyreExpenseCost;
	
	private Double tyreCostPerKm;

	private Integer vehicleOdomoter;
	
	private Integer openOdometer;
	
	private Integer vendorId;
	
	private String vendorName;
	
	private boolean tyreExpenseDetailsDocument;
	
	private Long tyreExpenseDetailsDocumentId;
	
	public TyreExpenseDetailsDto() {
		super();
		
	}

	public TyreExpenseDetailsDto(Long tyreExpenseDetailsId, Long tyreId, Integer tyreExpenseId,
			Double tyreExpenseAmount, Date tyreExpenseDate, String tyreExpenseDateStr, Double discount, Double gst,
			Double totalTyreExpenseAmount, String description, short tyreTypeId, Long createdById, Long lastUpdatedBy,
			boolean markForDelete, Date creationDate, String creationDateStr, Date lastUpdatedOn,
			String lastUpdatedOnStr,String tyreNumber ,Double tyreInvoiceAmount,Double retreadTyreInvoiceAmount, Integer tyreUsage ) {
		super();
		this.tyreExpenseDetailsId = tyreExpenseDetailsId;
		this.tyreId = tyreId;
		this.tyreExpenseId = tyreExpenseId;
		this.tyreExpenseAmount = tyreExpenseAmount;
		this.tyreExpenseDate = tyreExpenseDate;
		this.tyreExpenseDateStr = tyreExpenseDateStr;
		this.discount = discount;
		this.gst = gst;
		this.totalTyreExpenseAmount = totalTyreExpenseAmount;
		this.description = description;
		this.tyreTypeId = tyreTypeId;
		this.createdById = createdById;
		this.lastUpdatedBy = lastUpdatedBy;
		this.markForDelete = markForDelete;
		this.creationDate = creationDate;
		this.creationDateStr = creationDateStr;
		this.lastUpdatedOn = lastUpdatedOn;
		this.lastUpdatedOnStr = lastUpdatedOnStr;
		this.tyreNumber = tyreNumber;
		this.tyreInvoiceAmount = tyreInvoiceAmount;
		this.retreadTyreInvoiceAmount = retreadTyreInvoiceAmount;
		this.tyreUsage = tyreUsage;
	}

	public Long getTyreExpenseDetailsId() {
		return tyreExpenseDetailsId;
	}

	public void setTyreExpenseDetailsId(Long tyreExpenseDetailsId) {
		this.tyreExpenseDetailsId = tyreExpenseDetailsId;
	}

	public Long getTyreId() {
		return tyreId;
	}

	public void setTyreId(Long tyreId) {
		this.tyreId = tyreId;
	}

	public Integer getTyreExpenseId() {
		return tyreExpenseId;
	}

	public void setTyreExpenseId(Integer tyreExpenseId) {
		this.tyreExpenseId = tyreExpenseId;
	}

	public String getTyreExpenseName() {
		return tyreExpenseName;
	}

	public void setTyreExpenseName(String tyreExpenseName) {
		this.tyreExpenseName = tyreExpenseName;
	}

	public Double getTyreExpenseAmount() {
		return tyreExpenseAmount;
	}

	public void setTyreExpenseAmount(Double tyreExpenseAmount) {
		this.tyreExpenseAmount = Utility.round(tyreExpenseAmount,2);
	}

	public Date getTyreExpenseDate() {
		return tyreExpenseDate;
	}

	public void setTyreExpenseDate(Date tyreExpenseDate) {
		this.tyreExpenseDate = tyreExpenseDate;
	}

	public String getTyreExpenseDateStr() {
		return tyreExpenseDateStr;
	}

	public void setTyreExpenseDateStr(String tyreExpenseDateStr) {
		this.tyreExpenseDateStr = tyreExpenseDateStr;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = Utility.round(discount, 2);
	}

	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = Utility.round(gst, 2);
	}

	public Double getTotalTyreExpenseAmount() {
		return totalTyreExpenseAmount;
	}

	public void setTotalTyreExpenseAmount(Double totalTyreExpenseAmount) {
		this.totalTyreExpenseAmount = Utility.round(totalTyreExpenseAmount, 2);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getTyreTypeId() {
		return tyreTypeId;
	}

	public void setTyreTypeId(short tyreTypeId) {
		this.tyreTypeId = tyreTypeId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreationDateStr() {
		return creationDateStr;
	}

	public void setCreationDateStr(String creationDateStr) {
		this.creationDateStr = creationDateStr;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getLastUpdatedOnStr() {
		return lastUpdatedOnStr;
	}

	public void setLastUpdatedOnStr(String lastUpdatedOnStr) {
		this.lastUpdatedOnStr = lastUpdatedOnStr;
	}
	
	public String getTyreNumber() {
		return tyreNumber;
	}

	public void setTyreNumber(String tyreNumber) {
		this.tyreNumber = tyreNumber;
	}

	public Double getTyreInvoiceAmount() {
		return tyreInvoiceAmount;
	}

	public void setTyreInvoiceAmount(Double tyreInvoiceAmount) {
		this.tyreInvoiceAmount = Utility.round(tyreInvoiceAmount,2) ;
	}

	public Double getRetreadTyreInvoiceAmount() {
		return retreadTyreInvoiceAmount;
	}

	public void setRetreadTyreInvoiceAmount(Double retreadTyreInvoiceAmount) {
		this.retreadTyreInvoiceAmount = Utility.round(retreadTyreInvoiceAmount, 2);
	}

	public Integer getTyreUsage() {
		return tyreUsage;
	}

	public void setTyreUsage(Integer tyreUsage) {
		this.tyreUsage = tyreUsage;
	}

	public Double getTotalTyreExpenseCost() {
		return totalTyreExpenseCost;
	}

	public void setTotalTyreExpenseCost(Double totalTyreExpenseCost) {
		this.totalTyreExpenseCost = Utility.round(totalTyreExpenseCost, 2);
	}

	public Double getTyreCostPerKm() {
		return tyreCostPerKm;
	}

	public void setTyreCostPerKm(Double tyreCostPerKm) {
		this.tyreCostPerKm = Utility.round(tyreCostPerKm, 2);
	}

	public Integer getVehicleOdomoter() {
		return vehicleOdomoter;
	}

	public void setVehicleOdomoter(Integer vehicleOdomoter) {
		this.vehicleOdomoter = vehicleOdomoter;
	}

	public Integer getOpenOdometer() {
		return openOdometer;
	}

	public void setOpenOdometer(Integer openOdometer) {
		this.openOdometer = openOdometer;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public boolean isTyreExpenseDetailsDocument() {
		return tyreExpenseDetailsDocument;
	}

	public void setTyreExpenseDetailsDocument(boolean tyreExpenseDetailsDocument) {
		this.tyreExpenseDetailsDocument = tyreExpenseDetailsDocument;
	}

	public Long getTyreExpenseDetailsDocumentId() {
		return tyreExpenseDetailsDocumentId;
	}

	public void setTyreExpenseDetailsDocumentId(Long tyreExpenseDetailsDocumentId) {
		this.tyreExpenseDetailsDocumentId = tyreExpenseDetailsDocumentId;
	}

	@Override
	public String toString() {
		return "TyreExpenseDetailsDto [tyreExpenseDetailsId=" + tyreExpenseDetailsId + ", tyreId=" + tyreId
				+ ", tyreExpenseId=" + tyreExpenseId + ", tyreExpenseAmount=" + tyreExpenseAmount + ", tyreExpenseDate="
				+ tyreExpenseDate + ", tyreExpenseDateStr=" + tyreExpenseDateStr + ", discount=" + discount + ", gst="
				+ gst + ", totalTyreExpenseAmount=" + totalTyreExpenseAmount + ", description=" + description
				+ ", tyreTypeId=" + tyreTypeId + ", createdById=" + createdById + ", lastUpdatedBy=" + lastUpdatedBy
				+ ", markForDelete=" + markForDelete + ", creationDate=" + creationDate + ", creationDateStr="
				+ creationDateStr + ", lastUpdatedOn=" + lastUpdatedOn + ", lastUpdatedOnStr=" + lastUpdatedOnStr
				+ ", tyreNumber=" + tyreNumber + ", tyreInvoiceAmount=" + tyreInvoiceAmount
				+ ", retreadTyreInvoiceAmount=" + retreadTyreInvoiceAmount + ", tyreUsage=" + tyreUsage + "]";
	}

	
	
	
	
}
