package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class InventoryAllDto  {

	private Long inventory_all_id;

	private Long partid;

	private String partnumber;

	private String partname;

	private String parttype;
	
	private short partTypeId;

	private Long part_photoid;

	private String category;

	private Double all_quantity;
	
	private Integer companyId;

	private String createdBy;

	private String lastModifiedBy;

	boolean markForDelete;

	private Date created;

	private Date lastupdated;
	
	private double unitCost;
	
	private double discount;
	 
	private double tax;
	
	private long unitTypeId;
	
	private String	convertToStr;
	
	private double totalSubstitudeQty;

	public double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(double unitCost) {
		this.unitCost = Utility.round(unitCost,2);
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = Utility.round(discount, 2);
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = Utility.round(tax,2);
	}

	/**
	 * @return the inventory_all_id
	 */
	public Long getInventory_all_id() {
		return inventory_all_id;
	}

	/**
	 * @param inventory_all_id
	 *            the inventory_all_id to set
	 */
	public void setInventory_all_id(Long inventory_all_id) {
		this.inventory_all_id = inventory_all_id;
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

	
	public short getPartTypeId() {
		return partTypeId;
	}

	public void setPartTypeId(short partTypeId) {
		this.partTypeId = partTypeId;
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

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the all_quantity
	 */
	public Double getAll_quantity() {
		return all_quantity;
	}

	/**
	 * @param all_quantity
	 *            the all_quantity to set
	 */
	public void setAll_quantity(Double all_quantity) {
		this.all_quantity = Utility.round(all_quantity, 2);
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the status
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public Date getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
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

	public double getTotalSubstitudeQty() {
		return totalSubstitudeQty;
	}

	public void setTotalSubstitudeQty(double totalSubstitudeQty) {
		this.totalSubstitudeQty = totalSubstitudeQty;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryAllDto [inventory_all_id=");
		builder.append(inventory_all_id);
		builder.append(", partid=");
		builder.append(partid);
		builder.append(", partnumber=");
		builder.append(partnumber);
		builder.append(", partname=");
		builder.append(partname);
		builder.append(", unitCost=");
		builder.append(unitCost);
		builder.append(", discount=");
		builder.append(discount);
		builder.append(", tax=");
		builder.append(tax);
		builder.append("]");
		return builder.toString();
	}
}