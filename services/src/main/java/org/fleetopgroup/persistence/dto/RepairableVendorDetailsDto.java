package org.fleetopgroup.persistence.dto;

public class RepairableVendorDetailsDto {

	private Long	repairableVendorDetailsId;
	
	private Integer vendorId;
	
	private Long	partId;
	
	private Integer companyId;
	
	private String	vendorName;
	
	private String	partName;

	public Long getRepairableVendorDetailsId() {
		return repairableVendorDetailsId;
	}

	public void setRepairableVendorDetailsId(Long repairableVendorDetailsId) {
		this.repairableVendorDetailsId = repairableVendorDetailsId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}
}
