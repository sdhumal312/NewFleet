package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

public class VehicleLaundryDetailsDto {

	private Long	vehicleLaundryDetailsId;
	
	private Integer	vid;
	
	private Long	clothTypesId;
	
	private Long	laundryInvoiceId;
	
	private Double  quantity;
	
	private Integer	companyId;
	
	private String	vehicleRegistration;
	
	private String	clothTypesStr;

	public Long getVehicleLaundryDetailsId() {
		return vehicleLaundryDetailsId;
	}

	public void setVehicleLaundryDetailsId(Long vehicleLaundryDetailsId) {
		this.vehicleLaundryDetailsId = vehicleLaundryDetailsId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public Long getLaundryInvoiceId() {
		return laundryInvoiceId;
	}

	public void setLaundryInvoiceId(Long laundryInvoiceId) {
		this.laundryInvoiceId = laundryInvoiceId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = Utility.round( quantity,2);
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getVehicleRegistration() {
		return vehicleRegistration;
	}

	public void setVehicleRegistration(String vehicleRegistration) {
		this.vehicleRegistration = vehicleRegistration;
	}

	public String getClothTypesStr() {
		return clothTypesStr;
	}

	public void setClothTypesStr(String clothTypesStr) {
		this.clothTypesStr = clothTypesStr;
	}
	
	
}
