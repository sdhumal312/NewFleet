package org.fleetopgroup.persistence.dto;

public class LowStockInventoryDto {

	private Long lowStockInventoryId;
	
	private Long partid;
	
	private Integer lowstocklevel;

	private Integer reorderquantity;
	
	private Integer locationId;
	
	private String	locationName;

	public Long getLowStockInventoryId() {
		return lowStockInventoryId;
	}

	public void setLowStockInventoryId(Long lowStockInventoryId) {
		this.lowStockInventoryId = lowStockInventoryId;
	}

	public Long getPartid() {
		return partid;
	}

	public void setPartid(Long partid) {
		this.partid = partid;
	}

	public Integer getLowstocklevel() {
		return lowstocklevel;
	}

	public void setLowstocklevel(Integer lowstocklevel) {
		this.lowstocklevel = lowstocklevel;
	}

	public Integer getReorderquantity() {
		return reorderquantity;
	}

	public void setReorderquantity(Integer reorderquantity) {
		this.reorderquantity = reorderquantity;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	
}
