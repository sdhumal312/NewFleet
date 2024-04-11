package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class LaundryClothReceiveHistoryDto {

	private Long		clothReceiveHistoryId;
	
	private Long		laundryInvoiceId;
	
	private Long		laundryClothDetailsId;
	
	private Double		receiveQuantity;
	
	private String		description;
	
	private Long		receiveById;
	
	private Timestamp	receiveDate;
	
	private Timestamp	systemDateTime;
	
	private Integer		companyId;
	
	private String		clothTypesName;
	
	private String		receiveDateStr;
	
	private String		receivedBy;
	
	//newy
	private Long	clothTypesId;
	
	private Double clothEachCost;
	
	private Double clothDiscount;
	
	private Double clothGst;
	
	private Double clothTotal;
	
	private Integer		vendorId;
	
	private String vendorName;
	
	private String firstName;

	public Long getClothReceiveHistoryId() {
		return clothReceiveHistoryId;
	}

	public void setClothReceiveHistoryId(Long clothReceiveHistoryId) {
		this.clothReceiveHistoryId = clothReceiveHistoryId;
	}

	public Long getLaundryInvoiceId() {
		return laundryInvoiceId;
	}

	public void setLaundryInvoiceId(Long laundryInvoiceId) {
		this.laundryInvoiceId = laundryInvoiceId;
	}

	public Long getLaundryClothDetailsId() {
		return laundryClothDetailsId;
	}

	public void setLaundryClothDetailsId(Long laundryClothDetailsId) {
		this.laundryClothDetailsId = laundryClothDetailsId;
	}

	public Double getReceiveQuantity() {
		return receiveQuantity;
	}

	public void setReceiveQuantity(Double receiveQuantity) {
		this.receiveQuantity = Utility.round(receiveQuantity, 2);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getReceiveById() {
		return receiveById;
	}

	public void setReceiveById(Long receiveById) {
		this.receiveById = receiveById;
	}

	public Timestamp getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Timestamp receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Timestamp getSystemDateTime() {
		return systemDateTime;
	}

	public void setSystemDateTime(Timestamp systemDateTime) {
		this.systemDateTime = systemDateTime;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getClothTypesName() {
		return clothTypesName;
	}

	public void setClothTypesName(String clothTypesName) {
		this.clothTypesName = clothTypesName;
	}

	public String getReceiveDateStr() {
		return receiveDateStr;
	}

	public void setReceiveDateStr(String receiveDateStr) {
		this.receiveDateStr = receiveDateStr;
	}

	public String getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	
	//newy
	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}
	
	
	public Double getClothEachCost() {
		return clothEachCost;
	}

	public void setClothEachCost(Double clothEachCost) {
		this.clothEachCost =  Utility.round(clothEachCost,2);
	}
	
	public Double getClothDiscount() {
		return clothDiscount;
	}

	public void setClothDiscount(Double clothDiscount) {
		this.clothDiscount = Utility.round(clothDiscount, 2);
	}
	
	public Double getClothGst() {
		return clothGst;
	}

	public void setClothGst(Double clothGst) {
		this.clothGst = Utility.round(clothGst, 2);
	}

	public Double getClothTotal() {
		return clothTotal;
	}

	public void setClothTotal(Double clothTotal) {
		this.clothTotal = Utility.round(clothTotal, 2);
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
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LaundryClothReceiveHistoryDto [clothReceiveHistoryId=");
		builder.append(clothReceiveHistoryId);
		builder.append(", laundryInvoiceId=");
		builder.append(laundryInvoiceId);
		builder.append(", laundryClothDetailsId=");
		builder.append(laundryClothDetailsId);
		builder.append(", receiveQuantity=");
		builder.append(receiveQuantity);
		builder.append(", description=");
		builder.append(description);
		builder.append(", receiveById=");
		builder.append(receiveById);
		builder.append(", receiveDate=");
		builder.append(receiveDate);
		builder.append(", systemDateTime=");
		builder.append(systemDateTime);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", clothTypesName=");
		builder.append(clothTypesName);
		builder.append(", receiveDateStr=");
		builder.append(receiveDateStr);
		builder.append(", receivedBy=");
		builder.append(receivedBy);
		builder.append(", clothTypesId=");
		builder.append(clothTypesId);
		builder.append(", clothEachCost=");
		builder.append(clothEachCost);
		builder.append(", clothDiscount=");
		builder.append(clothDiscount);
		builder.append(", clothGst=");
		builder.append(clothGst);
		builder.append(", clothTotal=");
		builder.append(clothTotal);
		builder.append(", vendorId=");
		builder.append(vendorId);
		builder.append(", vendorName=");
		builder.append(vendorName);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append("]");
		return builder.toString();
	}
	
	
}
