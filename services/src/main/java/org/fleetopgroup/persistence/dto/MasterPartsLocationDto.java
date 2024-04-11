package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

public class MasterPartsLocationDto {

	private Integer partlocationid;

	private String location;
	
	private Integer locationId;

	private String Aisle;

	private String row;

	private String bin;

	private Long partid;
	
	/** The value for which company this record relate */
	private Integer companyId;
	
	/** The value for Marking that this record is required or not */
	private boolean	markForDelete;
	
	private Double location_quantity;
	
	private Long inventory_location_id;

	public MasterPartsLocationDto() {

	}

	public MasterPartsLocationDto(Integer location_ID, String aisle, String row, String bin) {
		super();
		this.locationId = location_ID;
		Aisle = aisle;
		this.row = row;
		this.bin = bin;

	}

	public MasterPartsLocationDto(Integer partlocationid, Integer location_ID, String aisle, String row, String bin) {
		super();
		this.partlocationid = partlocationid;
		this.locationId = location_ID;
		Aisle = aisle;
		this.row = row;
		this.bin = bin;

	}

	/**
	 * @return the partlocationid
	 */
	public Integer getPartlocationid() {
		return partlocationid;
	}

	/**
	 * @param partlocationid
	 *            the partlocationid to set
	 */
	public void setPartlocationid(Integer partlocationid) {
		this.partlocationid = partlocationid;
	}

	
	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the aisle
	 */
	public String getAisle() {
		return Aisle;
	}

	/**
	 * @param aisle
	 *            the aisle to set
	 */
	public void setAisle(String aisle) {
		Aisle = aisle;
	}

	/**
	 * @return the row
	 */
	public String getRow() {
		return row;
	}

	/**
	 * @param row
	 *            the row to set
	 */
	public void setRow(String row) {
		this.row = row;
	}

	/**
	 * @return the bin
	 */
	public String getBin() {
		return bin;
	}

	/**
	 * @param bin
	 *            the bin to set
	 */
	public void setBin(String bin) {
		this.bin = bin;
	}

	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	

	public Long getPartid() {
		return partid;
	}

	public void setPartid(Long partid) {
		this.partid = partid;
	}

	public Double getLocation_quantity() {
		return location_quantity;
	}

	public void setLocation_quantity(Double location_quantity) {
		this.location_quantity = Utility.round(location_quantity, 2);
	}

	public Long getInventory_location_id() {
		return inventory_location_id;
	}

	public void setInventory_location_id(Long inventory_location_id) {
		this.inventory_location_id = inventory_location_id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MasterPartsLocation [partlocationid=");
		builder.append(partlocationid);
		builder.append(", locationId=");
		builder.append(locationId);
		builder.append(", Aisle=");
		builder.append(Aisle);
		builder.append(", row=");
		builder.append(row);
		builder.append(", bin=");
		builder.append(bin);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}

	

}