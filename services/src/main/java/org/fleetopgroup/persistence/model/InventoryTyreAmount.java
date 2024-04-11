/**
 * 
 */
package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author fleetop
 *
 */
@Entity
@Table(name = "InventoryTyreAmount")
public class InventoryTyreAmount implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value for the ITYRE_AMD_ID Layout Id field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ITYRE_AMD_ID")
	private Long ITYRE_AMD_ID;
	
	@Column(name = "TYRE_MANUFACTURER_ID")
	private Integer TYRE_MANUFACTURER_ID;

	@Column(name = "TYRE_MODEL_ID")
	private Integer TYRE_MODEL_ID;

	/** The value for the TYRE_SIZE_ID field */
	@Column(name = "TYRE_SIZE_ID")
	private Integer TYRE_SIZE_ID;

	/** The value for the TYRE_TREAD field */
	@Column(name = "TYRE_TREAD", length = 50)
	private String TYRE_TREAD;

	/** The value for the TYRE_QUANTITY field */
	@Column(name = "TYRE_QUANTITY")
	private Double TYRE_QUANTITY;

	/** The value for the UNIT_COST field */
	@Column(name = "UNIT_COST")
	private Double UNIT_COST;

	/** The value for the DISCOUNT field */
	@Column(name = "DISCOUNT")
	private Double DISCOUNT;

	/** The value for the TAX field */
	@Column(name = "TAX")
	private Double TAX;

	/** The value for the TOTAL_AMOUNT field */
	@Column(name = "TOTAL_AMOUNT")
	private Double TOTAL_AMOUNT;

	@Column(name = "WAREHOUSE_LOCATION_ID")
	private Integer WAREHOUSE_LOCATION_ID;

	/** The value for the ITYRE_ID field */
	@ManyToOne
	@JoinColumn(name = "ITYRE_ID")
	private InventoryTyreInvoice InventoryTyreInvoice;

	/** The value for the TYRE_ASSIGN_NO Check assign or not field */
	@Column(name = "TYRE_ASSIGN_NO")
	private Integer TYRE_ASSIGN_NO;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "discountTaxTypeId")
	private short discountTaxTypeId;

	public InventoryTyreAmount() {
		super();
	}

	public InventoryTyreAmount(Long iTYRE_AMD_ID,  Integer tYRE_SIZE_ID,
			 Double tYRE_QUANTITY, Double uNIT_COST, Double dISCOUNT, Double tAX, Double tOTAL_AMOUNT,
			String wAREHOUSE_LOCATION, Integer companyId) {
		super();
		ITYRE_AMD_ID = iTYRE_AMD_ID;
		//TYRE_MANUFACTURER = tYRE_MANUFACTURER;
		//TYRE_MODEL = tYRE_MODEL;
		TYRE_SIZE_ID = tYRE_SIZE_ID;
		//TYRE_SIZE = tYRE_SIZE;
		TYRE_QUANTITY = tYRE_QUANTITY;
		UNIT_COST = uNIT_COST;
		DISCOUNT = dISCOUNT;
		TAX = tAX;
		TOTAL_AMOUNT = tOTAL_AMOUNT;
	//	WAREHOUSE_LOCATION = wAREHOUSE_LOCATION;
		COMPANY_ID = companyId;
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
*/
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
	}*/

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
		TYRE_QUANTITY = tYRE_QUANTITY;
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
		UNIT_COST = uNIT_COST;
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
		DISCOUNT = dISCOUNT;
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
		TAX = tAX;
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
		TOTAL_AMOUNT = tOTAL_AMOUNT;
	}

	/**
	 * @return the wAREHOUSE_LOCATION
	 *//*
	public String getWAREHOUSE_LOCATION() {
		return WAREHOUSE_LOCATION;
	}

	*//**
	 * @param wAREHOUSE_LOCATION
	 *            the wAREHOUSE_LOCATION to set
	 *//*
	public void setWAREHOUSE_LOCATION(String wAREHOUSE_LOCATION) {
		WAREHOUSE_LOCATION = wAREHOUSE_LOCATION;
	}
*/

	/**
	 * @return the inventoryTyreInvoice
	 */
	public InventoryTyreInvoice getInventoryTyreInvoice() {
		return InventoryTyreInvoice;
	}

	/**
	 * @param inventoryTyreInvoice
	 *            the inventoryTyreInvoice to set
	 */
	public void setInventoryTyreInvoice(InventoryTyreInvoice inventoryTyreInvoice) {
		InventoryTyreInvoice = inventoryTyreInvoice;
	}

	/**
	 * @return the tYRE_ASSIGN_NO
	 */
	public Integer getTYRE_ASSIGN_NO() {
		return TYRE_ASSIGN_NO;
	}

	/**
	 * @param tYRE_ASSIGN_NO
	 *            the tYRE_ASSIGN_NO to set
	 */
	public void setTYRE_ASSIGN_NO(Integer tYRE_ASSIGN_NO) {
		TYRE_ASSIGN_NO = tYRE_ASSIGN_NO;
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

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public short getDiscountTaxTypeId() {
		return discountTaxTypeId;
	}

	public void setDiscountTaxTypeId(short discountTaxTypeId) {
		this.discountTaxTypeId = discountTaxTypeId;
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
		result = prime * result + ((TYRE_MANUFACTURER_ID == null) ? 0 : TYRE_MANUFACTURER_ID.hashCode());
		result = prime * result + ((TYRE_SIZE_ID == null) ? 0 : TYRE_SIZE_ID.hashCode());
		result = prime * result + ((WAREHOUSE_LOCATION_ID == null) ? 0 : WAREHOUSE_LOCATION_ID.hashCode());
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
		InventoryTyreAmount other = (InventoryTyreAmount) obj;
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
		if (TYRE_MANUFACTURER_ID == null) {
			if (other.TYRE_MANUFACTURER_ID != null)
				return false;
		} else if (!TYRE_MANUFACTURER_ID.equals(other.TYRE_MANUFACTURER_ID))
			return false;
		
		if (TYRE_SIZE_ID == null) {
			if (other.TYRE_SIZE_ID != null)
				return false;
		} else if (!TYRE_SIZE_ID.equals(other.TYRE_SIZE_ID))
			return false;
		if (WAREHOUSE_LOCATION_ID == null) {
			if (other.WAREHOUSE_LOCATION_ID != null)
				return false;
		} else if (!WAREHOUSE_LOCATION_ID.equals(other.WAREHOUSE_LOCATION_ID))
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
		builder.append("InventoryTyreAmount [ITYRE_AMD_ID=");
		builder.append(ITYRE_AMD_ID);
		builder.append(", TYRE_MANUFACTURER_ID=");
		builder.append(TYRE_MANUFACTURER_ID);
		builder.append(", TYRE_MODEL_ID=");
		builder.append(TYRE_MODEL_ID);
		builder.append(", TYRE_SIZE_ID=");
		builder.append(TYRE_SIZE_ID);
		builder.append(", TYRE_TREAD=");
		builder.append(TYRE_TREAD);
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
		builder.append(", WAREHOUSE_LOCATION_ID=");
		builder.append(WAREHOUSE_LOCATION_ID);
		builder.append(", InventoryTyreInvoice=");
		builder.append(InventoryTyreInvoice);
		builder.append(", TYRE_ASSIGN_NO=");
		builder.append(TYRE_ASSIGN_NO);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append("]");
		return builder.toString();
	}
}
