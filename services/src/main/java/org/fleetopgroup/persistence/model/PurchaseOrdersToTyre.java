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
@Table(name = "PurchaseOrdersToTyre")
public class PurchaseOrdersToTyre implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PO_TO_TYREID")
	private Long PO_TO_TYREID;

	/** The value for the TYRE_SERIAL_NUMBER field */
	@Column(name = "TYRE_SERIAL_NUMBER", length = 50)
	private String TYRE_SERIAL_NUMBER;

	@Column(name = "RECEIVED_REMARKS", length = 250)
	private String RECEIVED_REMARKS;

	/** PurchaseOrder Id Data values  */
	@Column(name = "PO_ID")
	private Long PO_ID;
	
	/** PurchaseOrderToTyre Id Data values  */
	@Column(name = "TYRE_PART_ID")
	private Long TYRE_PART_ID;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public PurchaseOrdersToTyre() {
		super();
	}

	public PurchaseOrdersToTyre(Long pO_TO_TYREID, String tYRE_SERIAL_NUMBER, String rECEIVED_REMARKS, Long pO_ID,
			Long tYRE_PART_ID, Integer companyId) {
		super();
		PO_TO_TYREID = pO_TO_TYREID;
		TYRE_SERIAL_NUMBER = tYRE_SERIAL_NUMBER;
		RECEIVED_REMARKS = rECEIVED_REMARKS;
		PO_ID = pO_ID;
		TYRE_PART_ID = tYRE_PART_ID;
		this.companyId = companyId;
	}

	/**
	 * @return the pO_TO_TYREID
	 */
	public Long getPO_TO_TYREID() {
		return PO_TO_TYREID;
	}

	/**
	 * @param pO_TO_TYREID the pO_TO_TYREID to set
	 */
	public void setPO_TO_TYREID(Long pO_TO_TYREID) {
		PO_TO_TYREID = pO_TO_TYREID;
	}

	/**
	 * @return the tYRE_SERIAL_NUMBER
	 */
	public String getTYRE_SERIAL_NUMBER() {
		return TYRE_SERIAL_NUMBER;
	}

	/**
	 * @param tYRE_SERIAL_NUMBER the tYRE_SERIAL_NUMBER to set
	 */
	public void setTYRE_SERIAL_NUMBER(String tYRE_SERIAL_NUMBER) {
		TYRE_SERIAL_NUMBER = tYRE_SERIAL_NUMBER;
	}

	/**
	 * @return the rECEIVED_REMARKS
	 */
	public String getRECEIVED_REMARKS() {
		return RECEIVED_REMARKS;
	}

	/**
	 * @param rECEIVED_REMARKS the rECEIVED_REMARKS to set
	 */
	public void setRECEIVED_REMARKS(String rECEIVED_REMARKS) {
		RECEIVED_REMARKS = rECEIVED_REMARKS;
	}

	/**
	 * @return the pO_ID
	 */
	public Long getPO_ID() {
		return PO_ID;
	}

	/**
	 * @param pO_ID the pO_ID to set
	 */
	public void setPO_ID(Long pO_ID) {
		PO_ID = pO_ID;
	}

	/**
	 * @return the tYRE_PART_ID
	 */
	public Long getTYRE_PART_ID() {
		return TYRE_PART_ID;
	}

	/**
	 * @param tYRE_PART_ID the tYRE_PART_ID to set
	 */
	public void setTYRE_PART_ID(Long tYRE_PART_ID) {
		TYRE_PART_ID = tYRE_PART_ID;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((PO_ID == null) ? 0 : PO_ID.hashCode());
		result = prime * result + ((PO_TO_TYREID == null) ? 0 : PO_TO_TYREID.hashCode());
		result = prime * result + ((TYRE_PART_ID == null) ? 0 : TYRE_PART_ID.hashCode());
		result = prime * result + ((TYRE_SERIAL_NUMBER == null) ? 0 : TYRE_SERIAL_NUMBER.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		PurchaseOrdersToTyre other = (PurchaseOrdersToTyre) obj;
		if (PO_ID == null) {
			if (other.PO_ID != null)
				return false;
		} else if (!PO_ID.equals(other.PO_ID))
			return false;
		if (PO_TO_TYREID == null) {
			if (other.PO_TO_TYREID != null)
				return false;
		} else if (!PO_TO_TYREID.equals(other.PO_TO_TYREID))
			return false;
		if (TYRE_PART_ID == null) {
			if (other.TYRE_PART_ID != null)
				return false;
		} else if (!TYRE_PART_ID.equals(other.TYRE_PART_ID))
			return false;
		if (TYRE_SERIAL_NUMBER == null) {
			if (other.TYRE_SERIAL_NUMBER != null)
				return false;
		} else if (!TYRE_SERIAL_NUMBER.equals(other.TYRE_SERIAL_NUMBER))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PurchaseOrdersToTyre [PO_TO_TYREID=");
		builder.append(PO_TO_TYREID);
		builder.append(", TYRE_SERIAL_NUMBER=");
		builder.append(TYRE_SERIAL_NUMBER);
		builder.append(", RECEIVED_REMARKS=");
		builder.append(RECEIVED_REMARKS);
		builder.append(", PO_ID=");
		builder.append(PO_ID);
		builder.append(", TYRE_PART_ID=");
		builder.append(TYRE_PART_ID);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append("]");
		return builder.toString();
	}

	
}