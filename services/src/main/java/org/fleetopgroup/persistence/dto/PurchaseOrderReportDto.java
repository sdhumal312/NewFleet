package org.fleetopgroup.persistence.dto;


public class PurchaseOrderReportDto {

	private Long purchaseOrder_id;
	
	private Long purchaseorder_Number;
	
	private String purchaseorder_vendor_name;
	
	private String date_opended;
	
	private String data_required;
	
	private String purchaseorder_invoiceno;

	private String purchaseorder_invoice_date;
	
	private String purchaseorder_indentno;
	
	private String purchaseorder_partname;

	private String purchaseorder_partnumber;
	
	private Double quantity;

	private Double received_quantity;
	
	private Double parteachcost;

	private Double discount;

	private Double tax;
	
	private Double totalcost;
	
	private short purchaseorder_status;
	
	private String purchaseOrder_StatusName; 

	private String TYRE_MODEL;
	
	private String TYRE_MANUFACTURER; 
	
	private String BATTERY_MANUFACTURER;
	
	private String Battery_partNumber;
	
	private String TYRE_SIZE;
	
	private short PurchaseOrderTypeId;
	
	private int partialReceived;
	
	private	Integer		warrantyPeriod;
	
	private short		warrantyTypeId;
	
	public Double getParteachcost() {
		return parteachcost;
	}

	public void setParteachcost(Double parteachcost) {
		this.parteachcost = parteachcost;
	}
	
	public Long getPurchaseOrder_id() {
		return purchaseOrder_id;
	}

	public void setPurchaseOrder_id(Long purchaseOrder_id) {
		this.purchaseOrder_id = purchaseOrder_id;
	}
	
	public Long getPurchaseorder_Number() {
		return purchaseorder_Number;
	}

	public void setPurchaseorder_Number(Long purchaseorder_Number) {
		this.purchaseorder_Number = purchaseorder_Number;
	}

	public String getPurchaseorder_vendor_name() {
		return purchaseorder_vendor_name;
	}

	public void setPurchaseorder_vendor_name(String purchaseorder_vendor_name) {
		this.purchaseorder_vendor_name = purchaseorder_vendor_name;
	}

	public String getDate_opended() {
		return date_opended;
	}

	public void setDate_opended(String date_opended) {
		this.date_opended = date_opended;
	}

	public String getData_required() {
		return data_required;
	}

	public void setData_required(String data_required) {
		this.data_required = data_required;
	}

	public String getPurchaseorder_invoiceno() {
		return purchaseorder_invoiceno;
	}

	public void setPurchaseorder_invoiceno(String purchaseorder_invoiceno) {
		this.purchaseorder_invoiceno = purchaseorder_invoiceno;
	}

	public String getPurchaseorder_invoice_date() {
		return purchaseorder_invoice_date;
	}

	public void setPurchaseorder_invoice_date(String purchaseorder_invoice_date) {
		this.purchaseorder_invoice_date = purchaseorder_invoice_date;
	}

	public String getPurchaseorder_indentno() {
		return purchaseorder_indentno;
	}

	public void setPurchaseorder_indentno(String purchaseorder_indentno) {
		this.purchaseorder_indentno = purchaseorder_indentno;
	}

	public String getPurchaseorder_partname() {
		return purchaseorder_partname;
	}

	public void setPurchaseorder_partname(String purchaseorder_partname) {
		this.purchaseorder_partname = purchaseorder_partname;
	}

	public String getPurchaseorder_partnumber() {
		return purchaseorder_partnumber;
	}

	public void setPurchaseorder_partnumber(String purchaseorder_partnumber) {
		this.purchaseorder_partnumber = purchaseorder_partnumber;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getReceived_quantity() {
		return received_quantity;
	}

	public void setReceived_quantity(Double received_quantity) {
		this.received_quantity = received_quantity;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(Double totalcost) {
		this.totalcost = totalcost;
	}

	public short getPurchaseorder_status() {
		return purchaseorder_status;
	}

	public void setPurchaseorder_status(short purchaseorder_status) {
		this.purchaseorder_status = purchaseorder_status;
	}

	/**
	 * @return the purchaseOrder_StatusName
	 */
	public String getPurchaseOrder_StatusName() {
		return purchaseOrder_StatusName;
	}

	/**
	 * @param purchaseOrder_StatusName the purchaseOrder_StatusName to set
	 */
	public void setPurchaseOrder_StatusName(String purchaseOrder_StatusName) {
		this.purchaseOrder_StatusName = purchaseOrder_StatusName;
	}
	
	
	public String getTYRE_MODEL() {
		return TYRE_MODEL;
	}

	public void setTYRE_MODEL(String tYRE_MODEL) {
		TYRE_MODEL = tYRE_MODEL;
	}

	public String getTYRE_MANUFACTURER() {
		return TYRE_MANUFACTURER;
	}

	public void setTYRE_MANUFACTURER(String tYRE_MANUFACTURER) {
		TYRE_MANUFACTURER = tYRE_MANUFACTURER;
	}

	public String getBATTERY_MANUFACTURER() {
		return BATTERY_MANUFACTURER;
	}

	public void setBATTERY_MANUFACTURER(String bATTERY_MANUFACTURER) {
		BATTERY_MANUFACTURER = bATTERY_MANUFACTURER;
	}

	public String getBattery_partNumber() {
		return Battery_partNumber;
	}

	public void setBattery_partNumber(String battery_partNumber) {
		Battery_partNumber = battery_partNumber;
	}
	
	public String getTYRE_SIZE() {
		return TYRE_SIZE;
	}

	public void setTYRE_SIZE(String tYRE_SIZE) {
		TYRE_SIZE = tYRE_SIZE;
	}

	
	public short getPurchaseOrderTypeId() {
		return PurchaseOrderTypeId;
	}

	public void setPurchaseOrderTypeId(short purchaseOrderTypeId) {
		PurchaseOrderTypeId = purchaseOrderTypeId;
	}
	
	public int getPartialReceived() {
		return partialReceived;
	}

	public void setPartialReceived(int partialReceived) {
		this.partialReceived = partialReceived;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PurchaseOrderReportDto [purchaseorder_Number=");
		builder.append(purchaseorder_Number);
		builder.append(", purchaseorder_vendor_name=");
		builder.append(purchaseorder_vendor_name);
		builder.append(", date_opended=");
		builder.append(date_opended);
		builder.append(", data_required=");
		builder.append(data_required);
		builder.append(", purchaseorder_invoiceno=");
		builder.append(purchaseorder_invoiceno);
		builder.append(", purchaseorder_invoice_date=");
		builder.append(purchaseorder_invoice_date);
		builder.append(", purchaseorder_indentno=");
		builder.append(purchaseorder_indentno);
		builder.append(", purchaseorder_partname=");
		builder.append(purchaseorder_partname);
		builder.append(", purchaseorder_partnumber=");
		builder.append(purchaseorder_partnumber);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", received_quantity=");
		builder.append(received_quantity);
		builder.append(", discount=");
		builder.append(discount);
		builder.append(", tax=");
		builder.append(tax);
		builder.append(", totalcost=");
		builder.append(totalcost);
		builder.append(", purchaseorder_status=");
		builder.append(purchaseorder_status);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
