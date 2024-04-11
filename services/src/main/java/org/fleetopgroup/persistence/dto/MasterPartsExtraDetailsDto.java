package org.fleetopgroup.persistence.dto;

public class MasterPartsExtraDetailsDto {

	private Long	partsExtraDetailsId;
	
	private Long partid;
	
	private String originalBrand;
	
	private Long originalBrandId;
	
	private Long vehicleManufacturerId;
	
	private String	vehicleManufacturer;
	
	private Long vehicleModelId;
	
	private String	vehicleModel;
	
	private String mainPacking;
	
	private String uomPacking;
	
	private String looseItem;
	
	private String looseUom;
	
	private String barCodeNumber;
	
	private String itemType;
	
	private short itemTypeId;
	
	private Integer	companyId;
	
	private String	dimention;

	public Long getPartsExtraDetailsId() {
		return partsExtraDetailsId;
	}

	public void setPartsExtraDetailsId(Long partsExtraDetailsId) {
		this.partsExtraDetailsId = partsExtraDetailsId;
	}

	public Long getPartid() {
		return partid;
	}

	public void setPartid(Long partid) {
		this.partid = partid;
	}

	public String getOriginalBrand() {
		return originalBrand;
	}

	public void setOriginalBrand(String originalBrand) {
		this.originalBrand = originalBrand;
	}

	public Long getVehicleManufacturerId() {
		return vehicleManufacturerId;
	}

	public void setVehicleManufacturerId(Long vehicleManufacturerId) {
		this.vehicleManufacturerId = vehicleManufacturerId;
	}

	public Long getVehicleModelId() {
		return vehicleModelId;
	}

	public void setVehicleModelId(Long vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}

	public String getMainPacking() {
		return mainPacking;
	}

	public void setMainPacking(String mainPacking) {
		this.mainPacking = mainPacking;
	}

	public String getUomPacking() {
		return uomPacking;
	}

	public void setUomPacking(String uomPacking) {
		this.uomPacking = uomPacking;
	}

	public String getLooseItem() {
		return looseItem;
	}

	public void setLooseItem(String looseItem) {
		this.looseItem = looseItem;
	}

	public Long getOriginalBrandId() {
		return originalBrandId;
	}

	public void setOriginalBrandId(Long originalBrandId) {
		this.originalBrandId = originalBrandId;
	}

	public String getLooseUom() {
		return looseUom;
	}

	public void setLooseUom(String looseUom) {
		this.looseUom = looseUom;
	}

	public String getBarCodeNumber() {
		return barCodeNumber;
	}

	public void setBarCodeNumber(String barCodeNumber) {
		this.barCodeNumber = barCodeNumber;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	
	public short getItemTypeId() {
		return itemTypeId;
	}

	public void setItemTypeId(short itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getVehicleManufacturer() {
		return vehicleManufacturer;
	}

	public void setVehicleManufacturer(String vehicleManufacturer) {
		this.vehicleManufacturer = vehicleManufacturer;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public String getDimention() {
		return dimention;
	}

	public void setDimention(String dimention) {
		this.dimention = dimention;
	}
	
	
	
}
