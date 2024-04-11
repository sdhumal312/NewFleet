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
@Table(name = "PurchaseOrdersToParts")
public class PurchaseOrdersToParts implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purchaseorderto_partid")
	private Long purchaseorderto_partid;

	@Column(name = "partid")
	private Long partid;

	@Column(name = "TYRE_MANUFACTURER_ID")
	private Integer TYRE_MANUFACTURER_ID;
	
	@Column(name = "TYRE_MODEL_ID")
	private Integer TYRE_MODEL_ID;
	
	@Column(name = "TYRE_SIZE_ID")
	private Integer TYRE_SIZE_ID;

	@Column(name = "INVENTORY_TYRE_QUANTITY")
	private Integer INVENTORY_TYRE_QUANTITY;

	@Column(name = "INVENTORY_TYRE_NEW_RT", length = 100)
	private String INVENTORY_TYRE_NEW_RT;
	
	@Column(name = "BATTERY_MANUFACTURER_ID")
	private Long BATTERY_MANUFACTURER_ID;
	
	@Column(name = "BATTERY_TYPE_ID")
	private Long BATTERY_TYPE_ID;
	
	@Column(name = "BATTERY_CAPACITY_ID")
	private Long BATTERY_CAPACITY_ID;
	
	@Column(name = "BATTERY_QUANTITY")
	private Long BATTERY_QUANTITY;

	@Column(name = "quantity", length = 10)
	private Double quantity;

	@Column(name = "received_quantity", length = 10)
	private Double received_quantity;

	@Column(name = "received_quantity_remark", length = 200)
	private String received_quantity_remark;

	@Column(name = "notreceived_quantity", length = 10)
	private Double notreceived_quantity;

	@Column(name = "parteachcost", length = 10)
	private Double parteachcost;

	@Column(name = "unittypeId")
	private Integer unittypeId;

	@Column(name = "discount", length = 10)
	private Double discount;

	@Column(name = "tax", length = 10)
	private Double tax;

	@Column(name = "totalcost", length = 10)
	private Double totalcost;

	@Column(name = "inventory_all_id")
	private Long inventory_all_id;

	@Column(name = "inventory_all_quantity")
	private Double inventory_all_quantity;
	
	@Column(name = "clothTypesId")
	private Long clothTypesId;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "approvalPartStatusId", nullable = false)
	private short approvalPartStatusId;

	@ManyToOne
	@JoinColumn(name = "purchaseorder_id")
	private PurchaseOrders purchaseorders;
	
	@Column(name = "requestedQuantity", length = 10)
	private Double requestedQuantity;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "vendorId")
	private Integer vendorId;

	public PurchaseOrdersToParts() {
		super();
	}

	public PurchaseOrdersToParts(Long purchaseorderto_partid, Long partid, String purchaseorder_partname,
			String purchaseorder_partnumber, Double quantity, Double received_quantity, String received_quantity_remark,
			Double notreceived_quantity, Double parteachcost, String unittype, Double discount, Double tax,
			Double totalcost, PurchaseOrders purchaseorders, Integer companyId) {
		super();
		this.purchaseorderto_partid = purchaseorderto_partid;
		this.partid = partid;
		this.quantity = quantity;
		this.received_quantity = received_quantity;
		this.received_quantity_remark = received_quantity_remark;
		this.notreceived_quantity = notreceived_quantity;
		this.parteachcost = parteachcost;
		this.discount = discount;
		this.tax = tax;
		this.totalcost = totalcost;
		this.purchaseorders = purchaseorders;
		this.companyId = companyId;
	}

	/**
	 * @return the purchaseorderto_partid
	 */
	public Long getPurchaseorderto_partid() {
		return purchaseorderto_partid;
	}

	/**
	 * @param purchaseorderto_partid
	 *            the purchaseorderto_partid to set
	 */
	public void setPurchaseorderto_partid(Long purchaseorderto_partid) {
		this.purchaseorderto_partid = purchaseorderto_partid;
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

	/**
	 * @return the purchaseorder_partname
	 *//*
	public String getPurchaseorder_partname() {
		return purchaseorder_partname;
	}

	*//**
	 * @param purchaseorder_partname
	 *            the purchaseorder_partname to set
	 *//*
	public void setPurchaseorder_partname(String purchaseorder_partname) {
		this.purchaseorder_partname = purchaseorder_partname;
	}

	public String getParttype() {
		return parttype;
	}

	public void setParttype(String parttype) {
		this.parttype = parttype;
	}

	*//**
	 * @return the purchaseorder_partnumber
	 *//*
	public String getPurchaseorder_partnumber() {
		return purchaseorder_partnumber;
	}

	*//**
	 * @param purchaseorder_partnumber
	 *            the purchaseorder_partnumber to set
	 *//*
	public void setPurchaseorder_partnumber(String purchaseorder_partnumber) {
		this.purchaseorder_partnumber = purchaseorder_partnumber;
	}*/

	/**
	 * @return the quantity
	 */
	public Double getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the received_quantity
	 */
	public Double getReceived_quantity() {
		return received_quantity;
	}

	/**
	 * @param received_quantity
	 *            the received_quantity to set
	 */
	public void setReceived_quantity(Double received_quantity) {
		this.received_quantity = received_quantity;
	}

	/**
	 * @return the notreceived_quantity
	 */
	public Double getNotreceived_quantity() {
		return notreceived_quantity;
	}

	/**
	 * @param notreceived_quantity
	 *            the notreceived_quantity to set
	 */
	public void setNotreceived_quantity(Double notreceived_quantity) {
		this.notreceived_quantity = notreceived_quantity;
	}

	/**
	 * @return the parteachcost
	 */
	public Double getParteachcost() {
		return parteachcost;
	}

	/**
	 * @param parteachcost
	 *            the parteachcost to set
	 */
	public void setParteachcost(Double parteachcost) {
		this.parteachcost = parteachcost;
	}

	/**
	 * @return the unittype
	 *//*
	public String getUnittype() {
		return unittype;
	}

	*//**
	 * @param unittype
	 *            the unittype to set
	 *//*
	public void setUnittype(String unittype) {
		this.unittype = unittype;
	}
*/
	/**
	 * @return the discount
	 */
	public Double getDiscount() {
		return discount;
	}

	/**
	 * @param discount
	 *            the discount to set
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	/**
	 * @return the tax
	 */
	public Double getTax() {
		return tax;
	}

	/**
	 * @param tax
	 *            the tax to set
	 */
	public void setTax(Double tax) {
		this.tax = tax;
	}

	/**
	 * @return the totalcost
	 */
	public Double getTotalcost() {
		return totalcost;
	}

	/**
	 * @param tatalcost
	 *            the totalcost to set
	 */
	public void setTotalcost(Double tatalcost) {
		this.totalcost = tatalcost;
	}

	/**
	 * @return the purchaseorders
	 */
	public PurchaseOrders getPurchaseorders() {
		return purchaseorders;
	}

	/**
	 * @param purchaseorders
	 *            the purchaseorders to set
	 */
	public void setPurchaseorders(PurchaseOrders purchaseorders) {
		this.purchaseorders = purchaseorders;
	}

	/**
	 * @return the received_quantity_remark
	 */
	public String getReceived_quantity_remark() {
		return received_quantity_remark;
	}

	/**
	 * @param received_quantity_remark
	 *            the received_quantity_remark to set
	 */
	public void setReceived_quantity_remark(String received_quantity_remark) {
		this.received_quantity_remark = received_quantity_remark;
	}

	/**
	 * @return the tYRE_MANUFACTURER
	 *//*
	public String getTYRE_MANUFACTURER() {
		return TYRE_MANUFACTURER;
	}

	*//**
	 * @param tYRE_MANUFACTURER
	 *            the tYRE_MANUFACTURER to set
	 *//*
	public void setTYRE_MANUFACTURER(String tYRE_MANUFACTURER) {
		TYRE_MANUFACTURER = tYRE_MANUFACTURER;
	}

	*//**
	 * @return the tYRE_MODEL
	 *//*
	public String getTYRE_MODEL() {
		return TYRE_MODEL;
	}

	*//**
	 * @param tYRE_MODEL
	 *            the tYRE_MODEL to set
	 *//*
	public void setTYRE_MODEL(String tYRE_MODEL) {
		TYRE_MODEL = tYRE_MODEL;
	}

	*//**
	 * @return the tYRE_SIZE
	 *//*
	public String getTYRE_SIZE() {
		return TYRE_SIZE;
	}

	*//**
	 * @param tYRE_SIZE
	 *            the tYRE_SIZE to set
	 *//*
	public void setTYRE_SIZE(String tYRE_SIZE) {
		TYRE_SIZE = tYRE_SIZE;
	}
*/
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
	 * @return the inventory_all_quantity
	 */
	public Double getInventory_all_quantity() {
		return inventory_all_quantity;
	}

	/**
	 * @param inventory_all_quantity
	 *            the inventory_all_quantity to set
	 */
	public void setInventory_all_quantity(Double inventory_all_quantity) {
		this.inventory_all_quantity = inventory_all_quantity;
	}

	/**
	 * @return the iNVENTORY_TYRE_QUANTITY
	 */
	public Integer getINVENTORY_TYRE_QUANTITY() {
		return INVENTORY_TYRE_QUANTITY;
	}

	/**
	 * @param iNVENTORY_TYRE_QUANTITY
	 *            the iNVENTORY_TYRE_QUANTITY to set
	 */
	public void setINVENTORY_TYRE_QUANTITY(Integer iNVENTORY_TYRE_QUANTITY) {
		INVENTORY_TYRE_QUANTITY = iNVENTORY_TYRE_QUANTITY;
	}

	/**
	 * @return the iNVENTORY_TYRE_NEW_RT
	 */
	public String getINVENTORY_TYRE_NEW_RT() {
		return INVENTORY_TYRE_NEW_RT;
	}

	/**
	 * @param iNVENTORY_TYRE_NEW_RT
	 *            the iNVENTORY_TYRE_NEW_RT to set
	 */
	public void setINVENTORY_TYRE_NEW_RT(String iNVENTORY_TYRE_NEW_RT) {
		INVENTORY_TYRE_NEW_RT = iNVENTORY_TYRE_NEW_RT;
	}

	/**
	 * @return the tYRE_MANUFACTURER_ID
	 */
	public Integer getTYRE_MANUFACTURER_ID() {
		return TYRE_MANUFACTURER_ID;
	}

	/**
	 * @param tYRE_MANUFACTURER_ID the tYRE_MANUFACTURER_ID to set
	 */
	public void setTYRE_MANUFACTURER_ID(Integer tYRE_MANUFACTURER_ID) {
		TYRE_MANUFACTURER_ID = tYRE_MANUFACTURER_ID;
	}

	/**
	 * @return the tYRE_MODEL_ID
	 */
	public Integer getTYRE_MODEL_ID() {
		return TYRE_MODEL_ID;
	}

	/**
	 * @param tYRE_MODEL_ID the tYRE_MODEL_ID to set
	 */
	public void setTYRE_MODEL_ID(Integer tYRE_MODEL_ID) {
		TYRE_MODEL_ID = tYRE_MODEL_ID;
	}

	/**
	 * @return the tYRE_SIZE_ID
	 */
	public Integer getTYRE_SIZE_ID() {
		return TYRE_SIZE_ID;
	}

	/**
	 * @param tYRE_SIZE_ID the tYRE_SIZE_ID to set
	 */
	public void setTYRE_SIZE_ID(Integer tYRE_SIZE_ID) {
		TYRE_SIZE_ID = tYRE_SIZE_ID;
	}

	/**
	 * @return the unittypeId
	 */
	public Integer getUnittypeId() {
		return unittypeId;
	}

	/**
	 * @param unittypeId the unittypeId to set
	 */
	public void setUnittypeId(Integer unittypeId) {
		this.unittypeId = unittypeId;
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

	public Long getBATTERY_MANUFACTURER_ID() {
		return BATTERY_MANUFACTURER_ID;
	}

	public void setBATTERY_MANUFACTURER_ID(Long bATTERY_MANUFACTURER_ID) {
		BATTERY_MANUFACTURER_ID = bATTERY_MANUFACTURER_ID;
	}

	public Long getBATTERY_TYPE_ID() {
		return BATTERY_TYPE_ID;
	}

	public void setBATTERY_TYPE_ID(Long bATTERY_TYPE_ID) {
		BATTERY_TYPE_ID = bATTERY_TYPE_ID;
	}

	public Long getBATTERY_CAPACITY_ID() {
		return BATTERY_CAPACITY_ID;
	}

	public void setBATTERY_CAPACITY_ID(Long bATTERY_CAPACITY_ID) {
		BATTERY_CAPACITY_ID = bATTERY_CAPACITY_ID;
	}

	public Long getBATTERY_QUANTITY() {
		return BATTERY_QUANTITY;
	}

	public void setBATTERY_QUANTITY(Long bATTERY_QUANTITY) {
		BATTERY_QUANTITY = bATTERY_QUANTITY;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}
	
	public Double getRequestedQuantity() {
		return requestedQuantity;
	}

	public void setRequestedQuantity(Double requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}

	public short getApprovalPartStatusId() {
		return approvalPartStatusId;
	}

	public void setApprovalPartStatusId(short approvalPartStatusId) {
		this.approvalPartStatusId = approvalPartStatusId;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((partid == null) ? 0 : partid.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((received_quantity == null) ? 0 : received_quantity.hashCode());
		result = prime * result + ((received_quantity_remark == null) ? 0 : received_quantity_remark.hashCode());
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
		PurchaseOrdersToParts other = (PurchaseOrdersToParts) obj;
		if (partid == null) {
			if (other.partid != null)
				return false;
		} else if (!partid.equals(other.partid))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (received_quantity == null) {
			if (other.received_quantity != null)
				return false;
		} else if (!received_quantity.equals(other.received_quantity))
			return false;
		if (received_quantity_remark == null) {
			if (other.received_quantity_remark != null)
				return false;
		} else if (!received_quantity_remark.equals(other.received_quantity_remark))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PurchaseOrdersToParts [purchaseorderto_partid=");
		builder.append(purchaseorderto_partid);
		builder.append(", partid=");
		builder.append(partid);
		builder.append(", TYRE_MANUFACTURER_ID=");
		builder.append(TYRE_MANUFACTURER_ID);
		builder.append(", TYRE_MODEL_ID=");
		builder.append(TYRE_MODEL_ID);
		builder.append(", TYRE_SIZE_ID=");
		builder.append(TYRE_SIZE_ID);
		builder.append(", INVENTORY_TYRE_QUANTITY=");
		builder.append(INVENTORY_TYRE_QUANTITY);
		builder.append(", INVENTORY_TYRE_NEW_RT=");
		builder.append(INVENTORY_TYRE_NEW_RT);
		builder.append(", BATTERY_MANUFACTURER_ID=");
		builder.append(BATTERY_MANUFACTURER_ID);
		builder.append(", BATTERY_TYPE_ID=");
		builder.append(BATTERY_TYPE_ID);
		builder.append(", BATTERY_CAPACITY_ID=");
		builder.append(BATTERY_CAPACITY_ID);
		builder.append(", BATTERY_QUANTITY=");
		builder.append(BATTERY_QUANTITY);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", received_quantity=");
		builder.append(received_quantity);
		builder.append(", received_quantity_remark=");
		builder.append(received_quantity_remark);
		builder.append(", notreceived_quantity=");
		builder.append(notreceived_quantity);
		builder.append(", parteachcost=");
		builder.append(parteachcost);
		builder.append(", unittypeId=");
		builder.append(unittypeId);
		builder.append(", discount=");
		builder.append(discount);
		builder.append(", tax=");
		builder.append(tax);
		builder.append(", totalcost=");
		builder.append(totalcost);
		builder.append(", inventory_all_id=");
		builder.append(inventory_all_id);
		builder.append(", inventory_all_quantity=");
		builder.append(inventory_all_quantity);
		builder.append(", clothTypesId=");
		builder.append(clothTypesId);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}

}