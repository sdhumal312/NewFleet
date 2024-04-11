package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "inventorylocation")
public class InventoryLocation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inventory_location_id")
	private Long inventory_location_id;

	@Column(name = "partid", nullable = false, length = 10)
	private Long partid;

	@Column(name = "locationId")
	private Integer locationId;

	@Column(name = "location_quantity", length = 10)
	private Double location_quantity;
	
	@Column(name = "inTransitQuantity", length = 10)
	private Double inTransitQuantity;
	
	@ManyToOne
	@JoinColumn(name = "inventory_all_id")
	private InventoryAll inventoryall;

	@Column(name ="companyId" , nullable = false)
	private Integer companyId;
	
	@Column(name="markForDelete", nullable = false)
	boolean	markForDelete;
	
	
	public InventoryLocation() {
		super();
	}

	public InventoryLocation(Long inventory_location_id, Long partid, Double location_quantity
			, InventoryAll inventoryall, Integer companyId, Integer locationId) {
		super();
		this.inventory_location_id = inventory_location_id;
		this.partid = partid;
		/*this.partnumber = partnumber;
		this.partname = partname;
		this.part_photoid = part_photoid;
		this.category = category;
		this.location = location;*/
		this.locationId = locationId;
		this.location_quantity = location_quantity;
		this.inventoryall = inventoryall;
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
		this.location_quantity = location_quantity;
	}

	/**
	 * @return the inventoryall
	 */
	public InventoryAll getInventoryall() {
		return inventoryall;
	}

	/**
	 * @param inventoryall the inventoryall to set
	 */
	public void setInventoryall(InventoryAll inventoryall) {
		this.inventoryall = inventoryall;
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

	public Double getInTransitQuantity() {
		return inTransitQuantity;
	}

	public void setInTransitQuantity(Double inTransitQuantity) {
		this.inTransitQuantity = inTransitQuantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inventory_location_id == null) ? 0 : inventory_location_id.hashCode());
		result = prime * result + ((location_quantity == null) ? 0 : location_quantity.hashCode());
		//result = prime * result + ((part_photoid == null) ? 0 : part_photoid.hashCode());
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
		InventoryLocation other = (InventoryLocation) obj;
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
		/*if (part_photoid == null) {
			if (other.part_photoid != null)
				return false;
		} else if (!part_photoid.equals(other.part_photoid))
			return false;*/
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
		/*builder.append(", partnumber=");
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
		builder.append(location);*/
		builder.append(", locationId=");
		builder.append(locationId);
		builder.append(", location_quantity=");
		builder.append(location_quantity);
		builder.append(", inventoryall=");
		builder.append(inventoryall);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append("]");
		return builder.toString();
	}

	
	
	

}