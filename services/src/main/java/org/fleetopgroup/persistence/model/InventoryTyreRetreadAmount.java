/**
 * 
 */
package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author fleetop
 *
 */
@Entity
@Table(name = "InventoryTyreRetreadAmount")
public class InventoryTyreRetreadAmount implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value for the TR_AMOUNT_ID TO MANY TYRE SAVE Layout Id field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TR_AMOUNT_ID")
	private Long TR_AMOUNT_ID;

	/** The value for the ITYRE_ID Layout Id field */
	@Column(name = "TYRE_ID")
	private Long TYRE_ID;

	/** The value for the Vehicle Registration Number field */
	@Column(name = "TYRE_NUMBER", nullable = false)
	private String TYRE_NUMBER;

	/** The value for the TYRE_SIZE field *//*
	@Column(name = "TYRE_SIZE", length = 250)
	private String TYRE_SIZE;*/
	
	@Column(name = "TYRE_SIZE_ID")
	private Integer TYRE_SIZE_ID;

	/** The value for the RETREAD_AMOUNT field */
	@Column(name = "RETREAD_AMOUNT", length = 10)
	private Double RETREAD_AMOUNT;

	/** The value for the RETREAD_AMOUNT field */
	@Column(name = "RETREAD_DISCOUNT", length = 10)
	private Double RETREAD_DISCOUNT;

	/** The value for the RETREAD_TAX field */
	@Column(name = "RETREAD_TAX", length = 10)
	private Double RETREAD_TAX;

	/** The value for the RETREAD_COST field */
	@Column(name = "RETREAD_COST", length = 10)
	private Double RETREAD_COST;

	/** The value for the TRA_STATUS field *//*
	@Column(name = "TRA_STATUS", length = 50)
	private String TRA_STATUS;*/
	
	@Column(name = "TRA_STATUS_ID")
	private short TRA_STATUS_ID;

	@ManyToOne
	@JoinColumn(name = "TRID")
	private InventoryTyreRetread inventoryTyreRetread;

	/*@Column(name = "UPDATEDBY", updatable = false, length = 150)
	private String UPDATEDBY;*/
	
	@Column(name = "UPDATEDBYID")
	private Long UPDATEDBYID;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "UPDATED_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date UPDATED_DATE;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	public InventoryTyreRetreadAmount() {
		super();
	}

	public InventoryTyreRetreadAmount(Long tR_AMOUNT_ID, Long tYRE_ID, String tYRE_NUMBER, Double rETREAD_AMOUNT,
			Double rETREAD_DISCOUNT, Double rETREAD_TAX, Double rETREAD_COST, InventoryTyreRetread inventoryTyreRetread,
			 boolean MarkForDelete, Date uPDATED_DATE, Integer cOMPANY_Id) {
		super();
		TR_AMOUNT_ID = tR_AMOUNT_ID;
		TYRE_ID = tYRE_ID;
		TYRE_NUMBER = tYRE_NUMBER;
		RETREAD_AMOUNT = rETREAD_AMOUNT;
		RETREAD_DISCOUNT = rETREAD_DISCOUNT;
		RETREAD_TAX = rETREAD_TAX;
		RETREAD_COST = rETREAD_COST;
		this.inventoryTyreRetread = inventoryTyreRetread;
		//UPDATEDBY = uPDATEDBY;
		markForDelete = MarkForDelete;
		UPDATED_DATE = uPDATED_DATE;
		COMPANY_ID = cOMPANY_Id;
	}

	/**
	 * @return the tR_AMOUNT_ID
	 */
	public Long getTR_AMOUNT_ID() {
		return TR_AMOUNT_ID;
	}

	/**
	 * @param tR_AMOUNT_ID
	 *            the tR_AMOUNT_ID to set
	 */
	public void setTR_AMOUNT_ID(Long tR_AMOUNT_ID) {
		TR_AMOUNT_ID = tR_AMOUNT_ID;
	}

	/**
	 * @return the tYRE_ID
	 */
	public Long getTYRE_ID() {
		return TYRE_ID;
	}

	/**
	 * @param tYRE_ID
	 *            the tYRE_ID to set
	 */
	public void setTYRE_ID(Long tYRE_ID) {
		TYRE_ID = tYRE_ID;
	}

	/**
	 * @return the tYRE_NUMBER
	 */
	public String getTYRE_NUMBER() {
		return TYRE_NUMBER;
	}

	/**
	 * @param tYRE_NUMBER
	 *            the tYRE_NUMBER to set
	 */
	public void setTYRE_NUMBER(String tYRE_NUMBER) {
		TYRE_NUMBER = tYRE_NUMBER;
	}

	/**
	 * @return the rETREAD_AMOUNT
	 */
	public Double getRETREAD_AMOUNT() {
		return RETREAD_AMOUNT;
	}

	/**
	 * @param rETREAD_AMOUNT
	 *            the rETREAD_AMOUNT to set
	 */
	public void setRETREAD_AMOUNT(Double rETREAD_AMOUNT) {
		RETREAD_AMOUNT = rETREAD_AMOUNT;
	}

	/**
	 * @return the rETREAD_DISCOUNT
	 */
	public Double getRETREAD_DISCOUNT() {
		return RETREAD_DISCOUNT;
	}

	/**
	 * @param rETREAD_DISCOUNT
	 *            the rETREAD_DISCOUNT to set
	 */
	public void setRETREAD_DISCOUNT(Double rETREAD_DISCOUNT) {
		RETREAD_DISCOUNT = rETREAD_DISCOUNT;
	}

	/**
	 * @return the rETREAD_TAX
	 */
	public Double getRETREAD_TAX() {
		return RETREAD_TAX;
	}

	/**
	 * @param rETREAD_TAX
	 *            the rETREAD_TAX to set
	 */
	public void setRETREAD_TAX(Double rETREAD_TAX) {
		RETREAD_TAX = rETREAD_TAX;
	}

	/**
	 * @return the rETREAD_COST
	 */
	public Double getRETREAD_COST() {
		return RETREAD_COST;
	}

	/**
	 * @param rETREAD_COST
	 *            the rETREAD_COST to set
	 */
	public void setRETREAD_COST(Double rETREAD_COST) {
		RETREAD_COST = rETREAD_COST;
	}

	/**
	 * @return the inventoryTyreRetread
	 */
	public InventoryTyreRetread getInventoryTyreRetread() {
		return inventoryTyreRetread;
	}

	/**
	 * @param inventoryTyreRetread
	 *            the inventoryTyreRetread to set
	 */
	public void setInventoryTyreRetread(InventoryTyreRetread inventoryTyreRetread) {
		this.inventoryTyreRetread = inventoryTyreRetread;
	}

	/**
	 * @return the uPDATEDBY
	 *//*
	public String getUPDATEDBY() {
		return UPDATEDBY;
	}

	*//**
	 * @param uPDATEDBY
	 *            the uPDATEDBY to set
	 *//*
	public void setUPDATEDBY(String uPDATEDBY) {
		UPDATEDBY = uPDATEDBY;
	}
*/
	/**
	 * @return the sTATUS
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param sTATUS
	 *            the sTATUS to set
	 */
	public void setMarkForDelete(boolean MarkForDelete) {
		markForDelete = MarkForDelete;
	}

	/**
	 * @return the uPDATED_DATE
	 */
	public Date getUPDATED_DATE() {
		return UPDATED_DATE;
	}

	/**
	 * @param uPDATED_DATE
	 *            the uPDATED_DATE to set
	 */
	public void setUPDATED_DATE(Date uPDATED_DATE) {
		UPDATED_DATE = uPDATED_DATE;
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
	}

	*//**
	 * @return the tRA_STATUS
	 *//*
	public String getTRA_STATUS() {
		return TRA_STATUS;
	}

	*//**
	 * @param tRA_STATUS
	 *            the tRA_STATUS to set
	 *//*
	public void setTRA_STATUS(String tRA_STATUS) {
		TRA_STATUS = tRA_STATUS;
	}
*/
	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
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
	 * @return the tRA_STATUS_ID
	 */
	public short getTRA_STATUS_ID() {
		return TRA_STATUS_ID;
	}

	/**
	 * @param tRA_STATUS_ID the tRA_STATUS_ID to set
	 */
	public void setTRA_STATUS_ID(short tRA_STATUS_ID) {
		TRA_STATUS_ID = tRA_STATUS_ID;
	}

	/**
	 * @return the uPDATEDBYID
	 */
	public Long getUPDATEDBYID() {
		return UPDATEDBYID;
	}

	/**
	 * @param uPDATEDBYID the uPDATEDBYID to set
	 */
	public void setUPDATEDBYID(Long uPDATEDBYID) {
		UPDATEDBYID = uPDATEDBYID;
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
		result = prime * result + ((TR_AMOUNT_ID == null) ? 0 : TR_AMOUNT_ID.hashCode());
		result = prime * result + ((TYRE_ID == null) ? 0 : TYRE_ID.hashCode());
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
		InventoryTyreRetreadAmount other = (InventoryTyreRetreadAmount) obj;
		if (TR_AMOUNT_ID == null) {
			if (other.TR_AMOUNT_ID != null)
				return false;
		} else if (!TR_AMOUNT_ID.equals(other.TR_AMOUNT_ID))
			return false;
		if (TYRE_ID == null) {
			if (other.TYRE_ID != null)
				return false;
		} else if (!TYRE_ID.equals(other.TYRE_ID))
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
		builder.append("InventoryTyreRetreadAmount [TR_AMOUNT_ID=");
		builder.append(TR_AMOUNT_ID);
		builder.append(", TYRE_ID=");
		builder.append(TYRE_ID);
		builder.append(", TYRE_NUMBER=");
		builder.append(TYRE_NUMBER);
	/*	builder.append(", TYRE_SIZE=");
		builder.append(TYRE_SIZE);*/
		builder.append(", RETREAD_AMOUNT=");
		builder.append(RETREAD_AMOUNT);
		builder.append(", RETREAD_DISCOUNT=");
		builder.append(RETREAD_DISCOUNT);
		builder.append(", RETREAD_TAX=");
		builder.append(RETREAD_TAX);
		builder.append(", RETREAD_COST=");
		builder.append(RETREAD_COST);
	/*	builder.append(", TRA_STATUS=");
		builder.append(TRA_STATUS);*/
		builder.append(", inventoryTyreRetread=");
		builder.append(inventoryTyreRetread);
		/*builder.append(", UPDATEDBY=");
		builder.append(UPDATEDBY);*/
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", UPDATED_DATE=");
		builder.append(UPDATED_DATE);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append("]");
		return builder.toString();
	}

}
