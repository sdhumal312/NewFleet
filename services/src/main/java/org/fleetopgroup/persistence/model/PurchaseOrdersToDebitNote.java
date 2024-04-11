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
import javax.persistence.Table;

@Entity
@Table(name = "purchaseorderstodebitnote")
public class PurchaseOrdersToDebitNote implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purchaseorderto_debitnoteid")
	private Long purchaseorderto_debitnoteid;
	
	@Column(name = "partid", length = 10)
	private Long partid;

	@Column(name = "TYRE_MANUFACTURER_ID")
	private Integer TYRE_MANUFACTURER_ID;

	@Column(name = "TYRE_MODEL_ID")
	private Integer TYRE_MODEL_ID;

	@Column(name = "TYRE_SIZE_ID")
	private Integer TYRE_SIZE_ID;
	
	@Column(name = "batteryManufacturerId")
	private Long batteryManufacturerId;
	
	@Column(name = "batteryTypeId")
	private Long batteryTypeId;
	
	@Column(name = "batteryCapacityId")
	private Long batteryCapacityId;

	@Column(name = "received_quantity_remark", length = 200)
	private String received_quantity_remark;

	@Column(name = "notreceived_quantity", length = 10)
	private Double notreceived_quantity;

	@Column(name = "parteachcost", length = 10)
	private Double parteachcost;

	@Column(name = "unittypeId")
	private Integer unittypeId;
	
	@Column(name = "clothTypesId")
	private Long	clothTypesId;

	@Column(name = "discount", length = 10)
	private Double discount;

	@Column(name = "tax", length = 10)
	private Double tax;

	@Column(name = "total_return_cost", length = 10)
	private Double total_return_cost;

	@Column(name = "purchaseorder_id")
	private Long purchaseorder_id;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	
	@Column(name = "purchaseorderto_partid")
	private Long purchaseorderto_partid;

	public PurchaseOrdersToDebitNote() {
		super();
	}

	public PurchaseOrdersToDebitNote(Long purchaseorderto_debitnoteid, Long partid, String purchaseorder_partname,
			String purchaseorder_partnumber, String received_quantity_remark, Double notreceived_quantity,
			Double parteachcost, String unittype, Double discount, Double tax, Double total_return_cost,
			Long purchaseorder_id, Integer companyId) {
		super();
		this.purchaseorderto_debitnoteid = purchaseorderto_debitnoteid;
		this.partid = partid;
		//this.purchaseorder_partname = purchaseorder_partname;
		//this.purchaseorder_partnumber = purchaseorder_partnumber;
		this.received_quantity_remark = received_quantity_remark;
		this.notreceived_quantity = notreceived_quantity;
		this.parteachcost = parteachcost;
		//this.unittype = unittype;
		this.discount = discount;
		this.tax = tax;
		this.total_return_cost = total_return_cost;
		this.purchaseorder_id = purchaseorder_id;
		this.companyId = companyId;
	}

	/**
	 * @return the purchaseorderto_debitnoteid
	 */
	public Long getPurchaseorderto_debitnoteid() {
		return purchaseorderto_debitnoteid;
	}

	/**
	 * @param purchaseorderto_debitnoteid
	 *            the purchaseorderto_debitnoteid to set
	 */
	public void setPurchaseorderto_debitnoteid(Long purchaseorderto_debitnoteid) {
		this.purchaseorderto_debitnoteid = purchaseorderto_debitnoteid;
	}


	public Long getPurchaseorderto_partid() {
		return purchaseorderto_partid;
	}

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
	}
*/
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
	}*/

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
	 * @return the total_return_cost
	 */
	public Double getTotal_return_cost() {
		return total_return_cost;
	}

	/**
	 * @param total_return_cost
	 *            the total_return_cost to set
	 */
	public void setTotal_return_cost(Double total_return_cost) {
		this.total_return_cost = total_return_cost;
	}

	/**
	 * @return the purchaseorder_id
	 */
	public Long getPurchaseorder_id() {
		return purchaseorder_id;
	}

	/**
	 * @param purchaseorder_id
	 *            the purchaseorder_id to set
	 */
	public void setPurchaseorder_id(Long purchaseorder_id) {
		this.purchaseorder_id = purchaseorder_id;
	}
	
	

	/**
	 * @return the tYRE_MANUFACTURER
	 *//*
	public String getTYRE_MANUFACTURER() {
		return TYRE_MANUFACTURER;
	}

	*//**
	 * @param tYRE_MANUFACTURER the tYRE_MANUFACTURER to set
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
	 * @param tYRE_MODEL the tYRE_MODEL to set
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
	 * @param tYRE_SIZE the tYRE_SIZE to set
	 *//*
	public void setTYRE_SIZE(String tYRE_SIZE) {
		TYRE_SIZE = tYRE_SIZE;
	}
*/
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Long getBatteryManufacturerId() {
		return batteryManufacturerId;
	}

	public void setBatteryManufacturerId(Long batteryManufacturerId) {
		this.batteryManufacturerId = batteryManufacturerId;
	}

	public Long getBatteryTypeId() {
		return batteryTypeId;
	}

	public void setBatteryTypeId(Long batteryTypeId) {
		this.batteryTypeId = batteryTypeId;
	}

	public Long getBatteryCapacityId() {
		return batteryCapacityId;
	}

	public void setBatteryCapacityId(Long batteryCapacityId) {
		this.batteryCapacityId = batteryCapacityId;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((partid == null) ? 0 : partid.hashCode());
		result = prime * result + ((purchaseorder_id == null) ? 0 : purchaseorder_id.hashCode());
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
		PurchaseOrdersToDebitNote other = (PurchaseOrdersToDebitNote) obj;
		if (partid == null) {
			if (other.partid != null)
				return false;
		} else if (!partid.equals(other.partid))
			return false;
		if (purchaseorder_id == null) {
			if (other.purchaseorder_id != null)
				return false;
		} else if (!purchaseorder_id.equals(other.purchaseorder_id))
			return false;
		if (received_quantity_remark == null) {
			if (other.received_quantity_remark != null)
				return false;
		} else if (!received_quantity_remark.equals(other.received_quantity_remark))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PurchaseOrdersToDebitNote [purchaseorderto_debitnoteid=");
		builder.append(purchaseorderto_debitnoteid);
		builder.append(", partid=");
		builder.append(partid);
		builder.append(", purchaseorder_partname=");
		/*builder.append(purchaseorder_partname);
		builder.append(", purchaseorder_partnumber=");
		builder.append(purchaseorder_partnumber);
		builder.append(", TYRE_MANUFACTURER=");
		builder.append(TYRE_MANUFACTURER);
		builder.append(", TYRE_MODEL=");
		builder.append(TYRE_MODEL);
		builder.append(", TYRE_SIZE=");
		builder.append(TYRE_SIZE);*/
		builder.append(", received_quantity_remark=");
		builder.append(received_quantity_remark);
		builder.append(", notreceived_quantity=");
		builder.append(notreceived_quantity);
		builder.append(", parteachcost=");
		builder.append(parteachcost);
		/*builder.append(", unittype=");
		builder.append(unittype);*/
		builder.append(", discount=");
		builder.append(discount);
		builder.append(", tax=");
		builder.append(tax);
		builder.append(", total_return_cost=");
		builder.append(total_return_cost);
		builder.append(", purchaseorder_id=");
		builder.append(purchaseorder_id);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append("]");
		return builder.toString();
	}

}