package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

public class InventoryLocationDto {

	private Long inventory_location_id;

	private Long partid;

	private String partnumber;

	private String partname;

	private String parttype;
	
	private short partTypeId;

	private Long part_photoid;
	
	private String category;
	
	private String location;
	
	private Integer locationId;

	private Double location_quantity;

	private Double liter_quantity;
	
	private Integer companyId;
	
	boolean	markForDelete;
	
	private Integer lowstocklevel;

	private Integer reorderquantity;

	private String partManufacturer;
	
	private boolean isRepairable;
	
	private boolean isWarrantyApplicable;
	
	private long	unitTypeId;
	
	private String	convertToStr;
	
	private Long	inventory_all_id;
	
	private String partIds;
	
	private String aisle;
	
	private String bin;
	
	private String row;
	
	private Long inventory_id;
	
	private int	serialNoAddedForParts;
	
	
	public InventoryLocationDto() {
		super();
	}

	public InventoryLocationDto(Long inventory_location_id, Long partid, String partnumber, String partname,
			Long part_photoid, String category, String location, Double location_quantity,  Integer companyId, Integer locationId) {
		super();
		this.inventory_location_id = inventory_location_id;
		this.partid = partid;
		this.partnumber = partnumber;
		this.partname = partname;
		this.part_photoid = part_photoid;
		this.category = category;
		this.location = location;
		this.locationId = locationId;
		this.location_quantity = location_quantity;
		this.companyId = companyId;
	}

	/**
	 * @return the inventory_location_id
	 */
	public Long getInventory_location_id() {
		return inventory_location_id;
	}

	/**
	 * @param inventory_location_id
	 *            the inventory_location_id to set
	 */
	public void setInventory_location_id(Long inventory_location_id) {
		this.inventory_location_id = inventory_location_id;
	}

	/**
	 * @return the partid
	 */
	public Long getPartid() {
		return partid;
	}

	/**
	 * @param partid
	 *            the partid to set
	 */
	public void setPartid(Long partid) {
		this.partid = partid;
	}

	

	public String getPartnumber() {
		return partnumber;
	}

	public void setPartnumber(String partnumber) {
		this.partnumber = partnumber;
	}

	public String getPartname() {
		return partname;
	}

	public void setPartname(String partname) {
		this.partname = partname;
	}

	public String getParttype() {
		return parttype;
	}

	public void setParttype(String parttype) {
		this.parttype = parttype;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the part_photoid
	 */
	public Long getPart_photoid() {
		return part_photoid;
	}

	/**
	 * @param part_photoid
	 *            the part_photoid to set
	 */
	public void setPart_photoid(Long part_photoid) {
		this.part_photoid = part_photoid;
	}



	public short getPartTypeId() {
		return partTypeId;
	}

	public void setPartTypeId(short partTypeId) {
		this.partTypeId = partTypeId;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the location_quantity
	 */
	public Double getLocation_quantity() {
		return location_quantity;
	}

	/**
	 * @param location_quantity
	 *            the location_quantity to set
	 */
	public void setLocation_quantity(Double location_quantity) {
		this.location_quantity =  Utility.round(location_quantity, 2);
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

	public String getPartManufacturer() {
		return partManufacturer;
	}

	public void setPartManufacturer(String partManufacturer) {
		this.partManufacturer = partManufacturer;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inventory_location_id == null) ? 0 : inventory_location_id.hashCode());
		result = prime * result + ((location_quantity == null) ? 0 : location_quantity.hashCode());
		result = prime * result + ((part_photoid == null) ? 0 : part_photoid.hashCode());
		result = prime * result + ((partid == null) ? 0 : partid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryLocationDto other = (InventoryLocationDto) obj;
		if (inventory_location_id == null) {
			if (other.inventory_location_id != null)
				return false;
		} else if (!inventory_location_id.equals(other.inventory_location_id))
			return false;
		if (location_quantity == null) {
			if (other.location_quantity != null)
				return false;
		} else if (!location_quantity.equals(other.location_quantity))
			return false;
		if (part_photoid == null) {
			if (other.part_photoid != null)
				return false;
		} else if (!part_photoid.equals(other.part_photoid))
			return false;
		if (partid == null) {
			if (other.partid != null)
				return false;
		} else if (!partid.equals(other.partid))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryLocation [inventory_location_id=");
		builder.append(inventory_location_id);
		builder.append(", partid=");
		builder.append(partid);
		builder.append(", partnumber=");
		builder.append(partnumber);
		builder.append(", partname=");
		builder.append(partname);
		builder.append(", parttype=");
		builder.append(parttype);
		builder.append(", partTypeId=");
		builder.append(partTypeId);
		builder.append(", part_photoid=");
		builder.append(part_photoid);
		builder.append(", category=");
		builder.append(category);
		builder.append(", location=");
		builder.append(location);
		builder.append(", locationId=");
		builder.append(locationId);
		builder.append(", location_quantity=");
		builder.append(location_quantity);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", liter_quantity=");
		builder.append(liter_quantity);
		builder.append("]");
		return builder.toString();
	}

	public Double getLiter_quantity() {
		return liter_quantity;
	}

	public void setLiter_quantity(Double liter_quantity) {
		this.liter_quantity = Utility.round(liter_quantity, 2);
	}

	public boolean isRepairable() {
		return isRepairable;
	}

	public void setRepairable(boolean isRepairable) {
		this.isRepairable = isRepairable;
	}

	public boolean isWarrantyApplicable() {
		return isWarrantyApplicable;
	}

	public void setWarrantyApplicable(boolean isWarrantyApplicable) {
		this.isWarrantyApplicable = isWarrantyApplicable;
	}

	public long getUnitTypeId() {
		return unitTypeId;
	}

	public void setUnitTypeId(long unitTypeId) {
		this.unitTypeId = unitTypeId;
	}

	public String getConvertToStr() {
		return convertToStr;
	}

	public void setConvertToStr(String convertToStr) {
		this.convertToStr = convertToStr;
	}

	public Long getInventory_all_id() {
		return inventory_all_id;
	}

	public void setInventory_all_id(Long inventory_all_id) {
		this.inventory_all_id = inventory_all_id;
	}

	public String getPartIds() {
		return partIds;
	}

	public void setPartIds(String partIds) {
		this.partIds = partIds;
	}

	public Long getInventory_id() {
		return inventory_id;
	}

	public void setInventory_id(Long inventory_id) {
		this.inventory_id = inventory_id;
	}

	public int getSerialNoAddedForParts() {
		return serialNoAddedForParts;
	}

	public void setSerialNoAddedForParts(int serialNoAddedForParts) {
		this.serialNoAddedForParts = serialNoAddedForParts;
	}

	public String getAisle() {
		return aisle;
	}

	public void setAisle(String aisle) {
		this.aisle = aisle;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}
	
}