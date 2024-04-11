package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

public class PurchaseOrdersToDebitNoteDto {

	private Long purchaseorderto_debitnoteid;
	
	private Long partid;

	private String purchaseorder_partname;

	private String purchaseorder_partnumber;
	
	private String TYRE_MANUFACTURER;
	
	private Integer TYRE_MANUFACTURER_ID;

	private String TYRE_MODEL;
	
	private Integer TYRE_MODEL_ID;

	private String TYRE_SIZE;
	
	private Integer TYRE_SIZE_ID;

	private String received_quantity_remark;

	private Double notreceived_quantity;

	private Double parteachcost;

	private String unittype;
	
	private Integer unittypeId;

	private Double discount;

	private Double tax;

	private Double total_return_cost;

	private Long purchaseorder_id;
	
	private Integer companyId;
	
	private boolean markForDelete;
	
	private String manufacturerName;
	
	private String batteryType;
	
	private String batteryCapacity;
	
	private String		partNumber;
	
	private	Integer		warrantyPeriod;
	
	private short		warrantyTypeId;
	
	private Long		clothTypesId;
	
	private String		clothTypeName;
	

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
	 */
	public String getPurchaseorder_partname() {
		return purchaseorder_partname;
	}

	/**
	 * @param purchaseorder_partname
	 *            the purchaseorder_partname to set
	 */
	public void setPurchaseorder_partname(String purchaseorder_partname) {
		this.purchaseorder_partname = purchaseorder_partname;
	}

	/**
	 * @return the purchaseorder_partnumber
	 */
	public String getPurchaseorder_partnumber() {
		return purchaseorder_partnumber;
	}

	/**
	 * @param purchaseorder_partnumber
	 *            the purchaseorder_partnumber to set
	 */
	public void setPurchaseorder_partnumber(String purchaseorder_partnumber) {
		this.purchaseorder_partnumber = purchaseorder_partnumber;
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
		this.notreceived_quantity = Utility.round(notreceived_quantity, 2);
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
		this.parteachcost = Utility.round(parteachcost,2);
	}

	/**
	 * @return the unittype
	 */
	public String getUnittype() {
		return unittype;
	}

	/**
	 * @param unittype
	 *            the unittype to set
	 */
	public void setUnittype(String unittype) {
		this.unittype = unittype;
	}

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
		this.discount =Utility.round(discount,2);
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
		this.tax = Utility.round(tax, 2);
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
		this.total_return_cost = Utility.round(total_return_cost, 2);
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
	 */
	public String getTYRE_MANUFACTURER() {
		return TYRE_MANUFACTURER;
	}

	/**
	 * @param tYRE_MANUFACTURER the tYRE_MANUFACTURER to set
	 */
	public void setTYRE_MANUFACTURER(String tYRE_MANUFACTURER) {
		TYRE_MANUFACTURER = tYRE_MANUFACTURER;
	}

	/**
	 * @return the tYRE_MODEL
	 */
	public String getTYRE_MODEL() {
		return TYRE_MODEL;
	}

	/**
	 * @param tYRE_MODEL the tYRE_MODEL to set
	 */
	public void setTYRE_MODEL(String tYRE_MODEL) {
		TYRE_MODEL = tYRE_MODEL;
	}

	/**
	 * @return the tYRE_SIZE
	 */
	public String getTYRE_SIZE() {
		return TYRE_SIZE;
	}

	/**
	 * @param tYRE_SIZE the tYRE_SIZE to set
	 */
	public void setTYRE_SIZE(String tYRE_SIZE) {
		TYRE_SIZE = tYRE_SIZE;
	}

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


	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public Integer getWarrantyPeriod() {
		return warrantyPeriod;
	}

	public void setWarrantyPeriod(Integer warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}

	public short getWarrantyTypeId() {
		return warrantyTypeId;
	}

	public void setWarrantyTypeId(short warrantyTypeId) {
		this.warrantyTypeId = warrantyTypeId;
	}

	public String getBatteryType() {
		return batteryType;
	}

	public void setBatteryType(String batteryType) {
		this.batteryType = batteryType;
	}

	public String getBatteryCapacity() {
		return batteryCapacity;
	}

	public void setBatteryCapacity(String batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public String getClothTypeName() {
		return clothTypeName;
	}

	public void setClothTypeName(String clothTypeName) {
		this.clothTypeName = clothTypeName;
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
		builder.append(purchaseorder_partname);
		builder.append(", purchaseorder_partnumber=");
		builder.append(purchaseorder_partnumber);
		builder.append(", TYRE_MANUFACTURER=");
		builder.append(TYRE_MANUFACTURER);
		builder.append(", TYRE_MODEL=");
		builder.append(TYRE_MODEL);
		builder.append(", TYRE_SIZE=");
		builder.append(TYRE_SIZE);
		builder.append(", received_quantity_remark=");
		builder.append(received_quantity_remark);
		builder.append(", notreceived_quantity=");
		builder.append(notreceived_quantity);
		builder.append(", parteachcost=");
		builder.append(parteachcost);
		builder.append(", unittype=");
		builder.append(unittype);
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
