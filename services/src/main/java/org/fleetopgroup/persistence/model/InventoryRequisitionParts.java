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
@Table(name = "InventoryRequisitionParts")
public class InventoryRequisitionParts implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The value for the Requisition Auto ID Long field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INVRPARTID")
	private Long INVRPARTID;

	/** The value for the PART_ID lONG field */
	@Column(name = "PART_ID", nullable = false)
	private Long PART_ID;

	/** The value for the Requisition Requited Quantity Double field */
	@Column(name = "PART_REQUITED_QTY", length = 10)
	private Double PART_REQUITED_QTY;

	/** The value for the Requisition Requited Quantity Double field */
	@Column(name = "PART_TRANSFER_QTY", length = 10)
	private Double PART_TRANSFER_QTY;

	/** this is for marking this value is required or not */
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	@Column(name = "INVRID", nullable = false)
	private Long INVRID;
	
	/** this value inform to which company this record related */
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	public InventoryRequisitionParts() {
		super();
	}

	public InventoryRequisitionParts(Long iNVRPARTID, Long pART_ID, Double pART_REQUITED_QTY, boolean mARKFORDELETE) {
		super();
		INVRPARTID = iNVRPARTID;
		PART_ID = pART_ID;
		PART_REQUITED_QTY = pART_REQUITED_QTY;
		markForDelete = mARKFORDELETE;
	}

	public Long getINVRPARTID() {
		return INVRPARTID;
	}

	public void setINVRPARTID(Long iNVRPARTID) {
		INVRPARTID = iNVRPARTID;
	}

	public Long getPART_ID() {
		return PART_ID;
	}

	public void setPART_ID(Long pART_ID) {
		PART_ID = pART_ID;
	}

	public Double getPART_REQUITED_QTY() {
		return PART_REQUITED_QTY;
	}

	public void setPART_REQUITED_QTY(Double pART_REQUITED_QTY) {
		PART_REQUITED_QTY = pART_REQUITED_QTY;
	}

	public Double getPART_TRANSFER_QTY() {
		return PART_TRANSFER_QTY;
	}

	public void setPART_TRANSFER_QTY(Double pART_TRANSFER_QTY) {
		PART_TRANSFER_QTY = pART_TRANSFER_QTY;
	}

	public boolean ismarkForDelete() {
		return markForDelete;
	}

	public void setmarkForDelete(boolean mARKFORDELETE) {
		markForDelete = mARKFORDELETE;
	}

	public Long getINVRID() {
		return INVRID;
	}

	public void setINVRID(Long iNVRID) {
		INVRID = iNVRID;
	}
	

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((INVRPARTID == null) ? 0 : INVRPARTID.hashCode());
		result = prime * result + ((PART_ID == null) ? 0 : PART_ID.hashCode());
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
		InventoryRequisitionParts other = (InventoryRequisitionParts) obj;
		if (INVRPARTID == null) {
			if (other.INVRPARTID != null)
				return false;
		} else if (!INVRPARTID.equals(other.INVRPARTID))
			return false;
		if (PART_ID == null) {
			if (other.PART_ID != null)
				return false;
		} else if (!PART_ID.equals(other.PART_ID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryRequisitionParts [INVRPARTID=");
		builder.append(INVRPARTID);
		builder.append(", PART_ID=");
		builder.append(PART_ID);
		builder.append(", PART_REQUITED_QTY=");
		builder.append(PART_REQUITED_QTY);
		builder.append(", PART_TRANSFER_QTY=");
		builder.append(PART_TRANSFER_QTY);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", INVRID=");
		builder.append(INVRID);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append("]");
		return builder.toString();
	}

}
