package org.fleetopgroup.persistence.dto;

public class PartPurchaseVendorDto {

	private Long	partPurchaseVendorId;
	
	private Integer vendorId;
	
	private String	vendorName;
	
	public Long getPartPurchaseVendorId() {
		return partPurchaseVendorId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public Long getPartId() {
		return partId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setPartPurchaseVendorId(Long partPurchaseVendorId) {
		this.partPurchaseVendorId = partPurchaseVendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	private Long	partId;
	
	private Integer companyId;
}
