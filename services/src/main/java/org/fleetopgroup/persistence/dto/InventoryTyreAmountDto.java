/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import java.io.Serializable;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */
public class InventoryTyreAmountDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value for the ITYRE_AMD_ID Layout Id field */
	private Long ITYRE_AMD_ID;

	/** The value for the TYRE_MANUFACTURER field */
	private String TYRE_MANUFACTURER;
	
	private Integer TYRE_MANUFACTURER_ID;

	/** The value for the TYRE_MODEL field */
	private String TYRE_MODEL;
	
	private Integer TYRE_MODEL_ID;

	/** The value for the TYRE_SIZE_ID field */
	private Integer TYRE_SIZE_ID;

	/** The value for the TYRE_SIZE field */
	private String TYRE_SIZE;

	/** The value for the TYRE_TREAD field */
	private String TYRE_TREAD;

	/** The value for the TYRE_QUANTITY field */
	private Double TYRE_QUANTITY;

	/** The value for the UNIT_COST field */
	private Double UNIT_COST;

	/** The value for the DISCOUNT field */
	private Double DISCOUNT;

	/** The value for the TAX field */
	private Double TAX;

	/** The value for the TOTAL_AMOUNT field */
	private Double TOTAL_AMOUNT;

	/** The value for the WAREHOUSE_LOCATION field */
	private String WAREHOUSE_LOCATION;
	
	private Integer WAREHOUSE_LOCATION_ID;

	/** The value for the ITYRE_ID field */
	private InventoryTyreInvoiceDto INVENTORY_TYRE_INVOICE;
	
	/** The value for the ITYRE_ID Layout Id field */
	private Long ITYRE_ID;

	/** The value for the Check Quentity Tyre Assign or not field */
	private Integer TYRE_ASSIGN_NO;
	
	private short discountTaxTypeId;
	
	private Double invoiceAmount;

	public InventoryTyreAmountDto() {
		super();
	}

	public InventoryTyreAmountDto(Long iTYRE_AMD_ID, String tYRE_MANUFACTURER, String tYRE_MODEL, Integer tYRE_SIZE_ID,
			String tYRE_SIZE, Double tYRE_QUANTITY, Double uNIT_COST, Double dISCOUNT, Double tAX, Double tOTAL_AMOUNT,
			String wAREHOUSE_LOCATION, InventoryTyreInvoiceDto iNVENTORY_TYRE_INVOICE) {
		super();
		ITYRE_AMD_ID = iTYRE_AMD_ID;
		TYRE_MANUFACTURER = tYRE_MANUFACTURER;
		TYRE_MODEL = tYRE_MODEL;
		TYRE_SIZE_ID = tYRE_SIZE_ID;
		TYRE_SIZE = tYRE_SIZE;
		TYRE_QUANTITY = tYRE_QUANTITY;
		UNIT_COST = uNIT_COST;
		DISCOUNT = dISCOUNT;
		TAX = tAX;
		TOTAL_AMOUNT = tOTAL_AMOUNT;
		WAREHOUSE_LOCATION = wAREHOUSE_LOCATION;
		INVENTORY_TYRE_INVOICE = iNVENTORY_TYRE_INVOICE;
	}

	/**
	 * @return the ITYRE_AMD_ID
	 */
	public Long getITYRE_INV_ID() {
		return ITYRE_AMD_ID;
	}

	/**
	 * @param ITYRE_AMD_ID
	 *            the ITYRE_AMD_ID to set
	 */
	public void setITYRE_INV_ID(Long iTYRE_AMD_ID) {
		ITYRE_AMD_ID = iTYRE_AMD_ID;
	}

	/**
	 * @return the tYRE_MANUFACTURER
	 */
	public String getTYRE_MANUFACTURER() {
		return TYRE_MANUFACTURER;
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
	 * @return the tYRE_SIZE_ID
	 */
	public Integer getTYRE_SIZE_ID() {
		return TYRE_SIZE_ID;
	}

	/**
	 * @param tYRE_SIZE_ID
	 *            the tYRE_SIZE_ID to set
	 */
	public void setTYRE_SIZE_ID(Integer tYRE_SIZE_ID) {
		TYRE_SIZE_ID = tYRE_SIZE_ID;
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
	 * @return the tYRE_QUANTITY
	 */
	public Double getTYRE_QUANTITY() {
		return TYRE_QUANTITY;
	}

	/**
	 * @param tYRE_QUANTITY
	 *            the tYRE_QUANTITY to set
	 */
	public void setTYRE_QUANTITY(Double tYRE_QUANTITY) {
		TYRE_QUANTITY = Utility.round(tYRE_QUANTITY, 2);
	}

	/**
	 * @return the uNIT_COST
	 */
	public Double getUNIT_COST() {
		return UNIT_COST;
	}

	/**
	 * @param uNIT_COST
	 *            the uNIT_COST to set
	 */
	public void setUNIT_COST(Double uNIT_COST) {
		UNIT_COST = Utility.round(uNIT_COST,2);
	}

	/**
	 * @return the dISCOUNT
	 */
	public Double getDISCOUNT() {
		return DISCOUNT;
	}

	/**
	 * @param dISCOUNT
	 *            the dISCOUNT to set
	 */
	public void setDISCOUNT(Double dISCOUNT) {
		DISCOUNT = Utility.round(dISCOUNT,2);
	}

	/**
	 * @return the tAX
	 */
	public Double getTAX() {
		return TAX;
	}

	/**
	 * @param tAX
	 *            the tAX to set
	 */
	public void setTAX(Double tAX) {
		TAX = Utility.round(tAX, 2);
	}

	/**
	 * @return the tOTAL_AMOUNT
	 */
	public Double getTOTAL_AMOUNT() {
		return TOTAL_AMOUNT;
	}

	/**
	 * @param tOTAL_AMOUNT
	 *            the tOTAL_AMOUNT to set
	 */
	public void setTOTAL_AMOUNT(Double tOTAL_AMOUNT) {
		TOTAL_AMOUNT = Utility.round(tOTAL_AMOUNT, 2);
	}

	/**
	 * @return the wAREHOUSE_LOCATION
	 */
	public String getWAREHOUSE_LOCATION() {
		return WAREHOUSE_LOCATION;
	}

	/**
	 * @param wAREHOUSE_LOCATION
	 *            the wAREHOUSE_LOCATION to set
	 */
	public void setWAREHOUSE_LOCATION(String wAREHOUSE_LOCATION) {
		WAREHOUSE_LOCATION = wAREHOUSE_LOCATION;
	}

	/**
	 * @return the iNVENTORY_TYRE_INVOCE
	 */
	public InventoryTyreInvoiceDto getINVENTORY_TYRE_INVOICE() {
		return INVENTORY_TYRE_INVOICE;
	}

	/**
	 * @param iNVENTORY_TYRE_INVOCE
	 *            the iNVENTORY_TYRE_INVOCE to set
	 */
	public void setINVENTORY_TYRE_INVOICE(InventoryTyreInvoiceDto iNVENTORY_TYRE_INVOICE) {
		INVENTORY_TYRE_INVOICE = iNVENTORY_TYRE_INVOICE;
	}

	/**
	 * @return the iTYRE_AMD_ID
	 */
	public Long getITYRE_AMD_ID() {
		return ITYRE_AMD_ID;
	}

	/**
	 * @param iTYRE_AMD_ID
	 *            the iTYRE_AMD_ID to set
	 */
	public void setITYRE_AMD_ID(Long iTYRE_AMD_ID) {
		ITYRE_AMD_ID = iTYRE_AMD_ID;
	}

	/**
	 * @return the tYRE_TREAD
	 */
	public String getTYRE_TREAD() {
		return TYRE_TREAD;
	}

	/**
	 * @param tYRE_TREAD
	 *            the tYRE_TREAD to set
	 */
	public void setTYRE_TREAD(String tYRE_TREAD) {
		TYRE_TREAD = tYRE_TREAD;
	}

	
	
	/**
	 * @return the tYRE_ASSIGN_NO
	 */
	public Integer getTYRE_ASSIGN_NO() {
		return TYRE_ASSIGN_NO;
	}

	/**
	 * @param tYRE_ASSIGN_NO the tYRE_ASSIGN_NO to set
	 */
	public void setTYRE_ASSIGN_NO(Integer tYRE_ASSIGN_NO) {
		TYRE_ASSIGN_NO = tYRE_ASSIGN_NO;
	}

	/**
	 * @return the iTYRE_ID
	 */
	public Long getITYRE_ID() {
		return ITYRE_ID;
	}

	/**
	 * @param iTYRE_ID
	 *            the iTYRE_ID to set
	 */
	public void setITYRE_ID(Long iTYRE_ID) {
		ITYRE_ID = iTYRE_ID;
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
	 * @return the wAREHOUSE_LOCATION_ID
	 */
	public Integer getWAREHOUSE_LOCATION_ID() {
		return WAREHOUSE_LOCATION_ID;
	}

	/**
	 * @param wAREHOUSE_LOCATION_ID the wAREHOUSE_LOCATION_ID to set
	 */
	public void setWAREHOUSE_LOCATION_ID(Integer wAREHOUSE_LOCATION_ID) {
		WAREHOUSE_LOCATION_ID = wAREHOUSE_LOCATION_ID;
	}

	public short getDiscountTaxTypeId() {
		return discountTaxTypeId;
	}

	public void setDiscountTaxTypeId(short discountTaxTypeId) {
		this.discountTaxTypeId = discountTaxTypeId;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = Utility.round(invoiceAmount,2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ITYRE_AMD_ID == null) ? 0 : ITYRE_AMD_ID.hashCode());
		result = prime * result + ((TOTAL_AMOUNT == null) ? 0 : TOTAL_AMOUNT.hashCode());
		result = prime * result + ((TYRE_MANUFACTURER == null) ? 0 : TYRE_MANUFACTURER.hashCode());
		result = prime * result + ((TYRE_SIZE == null) ? 0 : TYRE_SIZE.hashCode());
		result = prime * result + ((TYRE_SIZE_ID == null) ? 0 : TYRE_SIZE_ID.hashCode());
		result = prime * result + ((WAREHOUSE_LOCATION == null) ? 0 : WAREHOUSE_LOCATION.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryTyreAmountDto other = (InventoryTyreAmountDto) obj;
		if (ITYRE_AMD_ID == null) {
			if (other.ITYRE_AMD_ID != null)
				return false;
		} else if (!ITYRE_AMD_ID.equals(other.ITYRE_AMD_ID))
			return false;
		if (TOTAL_AMOUNT == null) {
			if (other.TOTAL_AMOUNT != null)
				return false;
		} else if (!TOTAL_AMOUNT.equals(other.TOTAL_AMOUNT))
			return false;
		if (TYRE_MANUFACTURER == null) {
			if (other.TYRE_MANUFACTURER != null)
				return false;
		} else if (!TYRE_MANUFACTURER.equals(other.TYRE_MANUFACTURER))
			return false;
		if (TYRE_SIZE == null) {
			if (other.TYRE_SIZE != null)
				return false;
		} else if (!TYRE_SIZE.equals(other.TYRE_SIZE))
			return false;
		if (TYRE_SIZE_ID == null) {
			if (other.TYRE_SIZE_ID != null)
				return false;
		} else if (!TYRE_SIZE_ID.equals(other.TYRE_SIZE_ID))
			return false;
		if (WAREHOUSE_LOCATION == null) {
			if (other.WAREHOUSE_LOCATION != null)
				return false;
		} else if (!WAREHOUSE_LOCATION.equals(other.WAREHOUSE_LOCATION))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryTyreInvoice [ITYRE_AMD_ID=");
		builder.append(ITYRE_AMD_ID);
		builder.append(", TYRE_MANUFACTURER=");
		builder.append(TYRE_MANUFACTURER);
		builder.append(", TYRE_MODEL=");
		builder.append(TYRE_MODEL);
		builder.append(", TYRE_SIZE_ID=");
		builder.append(TYRE_SIZE_ID);
		builder.append(", TYRE_SIZE=");
		builder.append(TYRE_SIZE);
		builder.append(", TYRE_QUANTITY=");
		builder.append(TYRE_QUANTITY);
		builder.append(", UNIT_COST=");
		builder.append(UNIT_COST);
		builder.append(", DISCOUNT=");
		builder.append(DISCOUNT);
		builder.append(", TAX=");
		builder.append(TAX);
		builder.append(", TOTAL_AMOUNT=");
		builder.append(TOTAL_AMOUNT);
		builder.append(", WAREHOUSE_LOCATION=");
		builder.append(WAREHOUSE_LOCATION);
		builder.append(", INVENTORY_TYRE_INVOICE=");
		builder.append(INVENTORY_TYRE_INVOICE);
		builder.append("]");
		return builder.toString();
	}
}
