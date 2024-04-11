package org.fleetopgroup.persistence.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class VendorTypeDto {

	private Long vendor_Typeid;
	@NotNull
	@Size(min = 1)
	private String vendor_TypeName;
	private short isCommonMaster;
	
	public Long getVendor_Typeid() {
		return vendor_Typeid;
	}
	public void setVendor_Typeid(Long vendor_Typeid) {
		this.vendor_Typeid = vendor_Typeid;
	}
	public String getVendor_TypeName() {
		return vendor_TypeName;
	}
	public void setVendor_TypeName(String vendor_TypeName) {
		this.vendor_TypeName = vendor_TypeName;
	}
	public short getIsCommonMaster() {
		return isCommonMaster;
	}
	public void setIsCommonMaster(short isCommonMaster) {
		this.isCommonMaster = isCommonMaster;
	}
	@Override
	public String toString() {
		return "VendorTypeDto [vendor_Typeid=" + vendor_Typeid + ", vendor_TypeName=" + vendor_TypeName
				+ ", isCommonMaster=" + isCommonMaster + "]";
	}
}
