package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class UreaTransferDetailsDto {

	private Long ureaTransferDetailsId;
	
	private Long ureaTransferId;
	
	private String ureaTranferBy;
	
	private Integer ureaTransferFromLocationId;
	
	private String ureaTransferFromLocation;
	
	private Long ureaInvoiceToDetailsId;
	
	private Double ureaStockQuantity;
	
	private Double ureaInventoryTransferQuantity;

	private Long createdById;

	private String createdBy;
	
	private Long lastUpdatedById;

	private String lastUpdatedBy;
	
	private Date creationDate;

	private String creationDateStr;
	
	private Date lastUpdatedDate;
	
	private String lastUpdatedDateStr;
	
	private Integer companyId;
	
	private boolean markForDelete;
	
	private Long ureaInvoiceId;
	
	private Long ureaInvoiceNumber;
	
	private Long manufacturerId;

	private Double discount;
	
	private Double tax;
	
	private Double unitprice;
	
	private Integer vendor_id;
	
	

	public UreaTransferDetailsDto() {
		super();
		
	}

	public Long getUreaTransferDetailsId() {
		return ureaTransferDetailsId;
	}

	public void setUreaTransferDetailsId(Long ureaTransferDetailsId) {
		this.ureaTransferDetailsId = ureaTransferDetailsId;
	}

	public Long getUreaTransferId() {
		return ureaTransferId;
	}

	public void setUreaTransferId(Long ureaTransferId) {
		this.ureaTransferId = ureaTransferId;
	}

	public String getUreaTranferBy() {
		return ureaTranferBy;
	}

	public void setUreaTranferBy(String ureaTranferBy) {
		this.ureaTranferBy = ureaTranferBy;
	}

	public Integer getUreaTransferFromLocationId() {
		return ureaTransferFromLocationId;
	}

	public void setUreaTransferFromLocationId(Integer ureaTransferFromLocationId) {
		this.ureaTransferFromLocationId = ureaTransferFromLocationId;
	}

	public String getUreaTransferFromLocation() {
		return ureaTransferFromLocation;
	}

	public void setUreaTransferFromLocation(String ureaTransferFromLocation) {
		this.ureaTransferFromLocation = ureaTransferFromLocation;
	}

	public Long getUreaInvoiceToDetailsId() {
		return ureaInvoiceToDetailsId;
	}

	public void setUreaInvoiceToDetailsId(Long ureaInvoiceToDetailsId) {
		this.ureaInvoiceToDetailsId = ureaInvoiceToDetailsId;
	}

	public Double getUreaStockQuantity() {
		return ureaStockQuantity;
	}

	public void setUreaStockQuantity(Double ureaStockQuantity) {
		this.ureaStockQuantity = Utility.round(ureaStockQuantity, 2);
	}

	public Double getUreaInventoryTransferQuantity() {
		return ureaInventoryTransferQuantity;
	}

	public void setUreaInventoryTransferQuantity(Double ureaInventoryTransferQuantity) {
		this.ureaInventoryTransferQuantity = Utility.round(ureaInventoryTransferQuantity, 2);
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}

	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getLastUpdatedDateStr() {
		return lastUpdatedDateStr;
	}

	public void setLastUpdatedDateStr(String lastUpdatedDateStr) {
		this.lastUpdatedDateStr = lastUpdatedDateStr;
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

	public Long getUreaInvoiceId() {
		return ureaInvoiceId;
	}

	public void setUreaInvoiceId(Long ureaInvoiceId) {
		this.ureaInvoiceId = ureaInvoiceId;
	}

	public Long getUreaInvoiceNumber() {
		return ureaInvoiceNumber;
	}

	public void setUreaInvoiceNumber(Long ureaInvoiceNumber) {
		this.ureaInvoiceNumber = ureaInvoiceNumber;
	}

	public Long getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(Long manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = Utility.round(discount, 2);
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = Utility.round(tax, 2);
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = Utility.round(unitprice, 2);
	}

	public Integer getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(Integer vendor_id) {
		this.vendor_id = vendor_id;
	}

	@Override
	public String toString() {
		return "UreaTransferDetailsDto [ureaTransferDetailsId=" + ureaTransferDetailsId + ", ureaTransferId="
				+ ureaTransferId + ", ureaTranferBy=" + ureaTranferBy + ", ureaTransferFromLocationId="
				+ ureaTransferFromLocationId + ", ureaTransferFromLocation=" + ureaTransferFromLocation
				+ ", ureaInvoiceToDetailsId=" + ureaInvoiceToDetailsId + ", ureaStockQuantity=" + ureaStockQuantity
				+ ", ureaInventoryTransferQuantity=" + ureaInventoryTransferQuantity + ", createdById=" + createdById
				+ ", createdBy=" + createdBy + ", lastUpdatedById=" + lastUpdatedById + ", lastUpdatedBy="
				+ lastUpdatedBy + ", creationDate=" + creationDate + ", creationDateStr=" + creationDateStr
				+ ", lastUpdatedDate=" + lastUpdatedDate + ", lastUpdatedDateStr=" + lastUpdatedDateStr + ", companyId="
				+ companyId + ", markForDelete=" + markForDelete + ", ureaInvoiceId=" + ureaInvoiceId
				+ ", manufacturerId=" + manufacturerId + ", discount=" + discount + ", tax=" + tax + ", unitprice="
				+ unitprice + ", vendor_id=" + vendor_id + "]";
	}

	



	
	   

}
