package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.persistence.model.PurchaseOrders;
import org.fleetopgroup.web.util.Utility;

public class PurchaseOrdersToPartsDto {


	private Long purchaseorderto_partid;

	private Long partid;

	private String purchaseorder_partname;

	private String purchaseorder_partnumber;

	private String parttype;

	private String TYRE_MANUFACTURER;
	
	private Integer TYRE_MANUFACTURER_ID;

	private String TYRE_MODEL;
	
	private Integer TYRE_MODEL_ID;

	private String TYRE_SIZE;
	
	private Integer TYRE_SIZE_ID;

	private Integer INVENTORY_TYRE_QUANTITY;

	private String INVENTORY_TYRE_NEW_RT;
	
	private Long BATTERY_QUANTITY;

	private Double quantity;

	private Double received_quantity;

	private String received_quantity_remark;

	private Double notreceived_quantity;

	private Double parteachcost;

	private String unittype;
	
	private Integer unittypeId;

	private Double discount;

	private Double tax;

	private Double totalcost;

	private Long inventory_all_id;

	private Double inventory_all_quantity;
	
	private Integer companyId;

	private boolean markForDelete;

	private PurchaseOrders purchaseorders;
	
	private Long purchaseorder_id;
	
	private Long purchaseorder_Number;
	
	private String batteryPartNumber;
	
	private	Integer		warrantyPeriod;
	
	private short		warrantyTypeId;
	
	private Long BATTERY_MANUFACTURER_ID;
	
	private Long BATTERY_TYPE_ID;
	
	private Long BATTERY_CAPACITY_ID;
	
	private String	clothTypeName;
	
	private Long	clothTypesId;
	
	private String	ureaManufacturer;
	
	private Long	ureaId;
	
	private Long	ureaManufacturerId;
	
	private Long    ureaInvoiceToDetailsId;
	
	private short    approvalPartStatusId;
	private String    approvalPartStatus;
	
	private short    purchaseOrderStatusId;
	private String    purchaseOrderStatus;
	private String	  convertToStr;
	
	private long subRequisitionId;
	
	private Double approvalQuantity;
	
	private boolean fromTransfer;
	
	private String vendorName;
	
	private Double finalReceivedAmount;

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

	public String getParttype() {
		return parttype;
	}

	public void setParttype(String parttype) {
		this.parttype = parttype;
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
		this.quantity = Utility.round( quantity,2);
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
		this.received_quantity = Utility.round( received_quantity, 2);
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
		this.notreceived_quantity = Utility.round(notreceived_quantity,2);
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
		this.parteachcost = Utility.round(parteachcost, 2);
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
		this.discount = Utility.round(discount, 2);
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
		this.tax =  Utility.round(tax,2);
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
		this.totalcost = Utility.round(tatalcost,2);
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
	 */
	public String getTYRE_MANUFACTURER() {
		return TYRE_MANUFACTURER;
	}

	public Long getBATTERY_QUANTITY() {
		return BATTERY_QUANTITY;
	}

	public void setBATTERY_QUANTITY(Long bATTERY_QUANTITY) {
		BATTERY_QUANTITY = bATTERY_QUANTITY;
	}

	/**
	 * @param tYRE_MANUFACTURER
	 *            the tYRE_MANUFACTURER to set
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
	 * @param tYRE_MODEL
	 *            the tYRE_MODEL to set
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
	 * @param tYRE_SIZE
	 *            the tYRE_SIZE to set
	 */
	public void setTYRE_SIZE(String tYRE_SIZE) {
		TYRE_SIZE = tYRE_SIZE;
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
		this.inventory_all_quantity =  Utility.round(inventory_all_quantity, 2);
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

	/**
	 * @return the purchaseorder_id
	 */
	public Long getPurchaseorder_id() {
		return purchaseorder_id;
	}

	/**
	 * @param purchaseorder_id the purchaseorder_id to set
	 */
	public void setPurchaseorder_id(Long purchaseorder_id) {
		this.purchaseorder_id = purchaseorder_id;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}


	public Long getPurchaseorder_Number() {
		return purchaseorder_Number;
	}

	public String getBatteryPartNumber() {
		return batteryPartNumber;
	}

	public void setBatteryPartNumber(String batteryPartNumber) {
		this.batteryPartNumber = batteryPartNumber;
	}

	public void setPurchaseorder_Number(Long purchaseorder_Number) {
		this.purchaseorder_Number = purchaseorder_Number;
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

	public String getClothTypeName() {
		return clothTypeName;
	}

	public void setClothTypeName(String clothTypeName) {
		this.clothTypeName = clothTypeName;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public String getUreaManufacturer() {
		return ureaManufacturer;
	}

	public void setUreaManufacturer(String ureaManufacturer) {
		this.ureaManufacturer = ureaManufacturer;
	}

	public Long getUreaId() {
		return ureaId;
	}

	public void setUreaId(Long ureaId) {
		this.ureaId = ureaId;
	}

	public Long getUreaManufacturerId() {
		return ureaManufacturerId;
	}

	public void setUreaManufacturerId(Long ureaManufacturerId) {
		this.ureaManufacturerId = ureaManufacturerId;
	}

	public Long getUreaInvoiceToDetailsId() {
		return ureaInvoiceToDetailsId;
	}

	public void setUreaInvoiceToDetailsId(Long ureaInvoiceToDetailsId) {
		this.ureaInvoiceToDetailsId = ureaInvoiceToDetailsId;
	}

	public short getApprovalPartStatusId() {
		return approvalPartStatusId;
	}

	public void setApprovalPartStatusId(short approvalPartStatusId) {
		this.approvalPartStatusId = approvalPartStatusId;
	}


	public String getApprovalPartStatus() {
		return approvalPartStatus;
	}

	public void setApprovalPartStatus(String approvalPartStatus) {
		this.approvalPartStatus = approvalPartStatus;
	}

	public short getPurchaseOrderStatusId() {
		return purchaseOrderStatusId;
	}

	public void setPurchaseOrderStatusId(short purchaseOrderStatusId) {
		this.purchaseOrderStatusId = purchaseOrderStatusId;
	}

	public String getPurchaseOrderStatus() {
		return purchaseOrderStatus;
	}

	public void setPurchaseOrderStatus(String purchaseOrderStatus) {
		this.purchaseOrderStatus = purchaseOrderStatus;
	}

	public String getConvertToStr() {
		return convertToStr;
	}

	public void setConvertToStr(String convertToStr) {
		this.convertToStr = convertToStr;
	}

	public long getSubRequisitionId() {
		return subRequisitionId;
	}

	public void setSubRequisitionId(long subRequisitionId) {
		this.subRequisitionId = subRequisitionId;
	}

	public boolean isFromTransfer() {
		return fromTransfer;
	}

	public void setFromTransfer(boolean fromTransfer) {
		this.fromTransfer = fromTransfer;
	}

	public Double getApprovalQuantity() {
		return approvalQuantity;
	}

	public void setApprovalQuantity(Double approvalQuantity) {
		this.approvalQuantity = approvalQuantity;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PurchaseOrdersToParts [purchaseorderto_partid=");
		builder.append(purchaseorderto_partid);
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
		builder.append(", INVENTORY_TYRE_QUANTITY=");
		builder.append(INVENTORY_TYRE_QUANTITY);
		builder.append(", INVENTORY_TYRE_NEW_RT=");
		builder.append(INVENTORY_TYRE_NEW_RT);
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
		builder.append(", unittype=");
		builder.append(unittype);
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
		builder.append(", purchaseorders=");
		builder.append(purchaseorders);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @return the finalReceivedAmount
	 */
	public Double getFinalReceivedAmount() {
		return finalReceivedAmount;
	}

	/**
	 * @param finalReceivedAmount the finalReceivedAmount to set
	 */
	public void setFinalReceivedAmount(Double finalReceivedAmount) {
		this.finalReceivedAmount = finalReceivedAmount;
	}


}
