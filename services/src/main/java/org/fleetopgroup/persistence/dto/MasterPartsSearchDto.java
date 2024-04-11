package org.fleetopgroup.persistence.dto;

public class MasterPartsSearchDto {

	private Long	partid;
	private long makerId;
	private long categoryId;
	private Long oemId;
	private String vehicleMake;
	private String vehicleModel;
	private String warranty;
	private short partTypeCatgory;
	private String repairable;
	public Long getPartid() {
		return partid;
	}
	public void setPartid(Long partid) {
		this.partid = partid;
	}
	public long getMakerId() {
		return makerId;
	}
	public void setMakerId(long makerId) {
		this.makerId = makerId;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getOemId() {
		return oemId;
	}
	public void setOemId(Long oemId) {
		this.oemId = oemId;
	}
	public String getVehicleMake() {
		return vehicleMake;
	}
	public void setVehicleMake(String vehicleMake) {
		this.vehicleMake = vehicleMake;
	}
	public String getVehicleModel() {
		return vehicleModel;
	}
	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	
	public short getPartTypeCatgory() {
		return partTypeCatgory;
	}
	public void setPartTypeCatgory(short partTypeCatgory) {
		this.partTypeCatgory = partTypeCatgory;
	}
	
	public String getWarranty() {
		return warranty;
	}
	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}
	public String getRepairable() {
		return repairable;
	}
	public void setRepairable(String repairable) {
		this.repairable = repairable;
	}
	@Override
	public String toString() {
		return "MasterPartsSearchDto [partid=" + partid + ", makerId=" + makerId + ", categoryId=" + categoryId
				+ ", oemId=" + oemId + ", vehicleMake=" + vehicleMake + ", vehicleModel=" + vehicleModel + ", warranty="
				+ warranty + ", partTypeCatgory=" + partTypeCatgory + ", repairable=" + repairable + "]";
	}
	
	
	
	
}
