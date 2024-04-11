package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

public class InventoryRequisitionPartsDto extends MasterPartsDto {

	/**
	 * 
	 */

	/** The value for the Requisition Auto ID Long field */
	private Long INVRPARTID;

	/** The value for the PART_ID lONG field */
	private Long PART_ID;

	/** The value for the Requisition Requited Quantity Double field */
	private Double PART_REQUITED_QTY;
	
	/** The value for the Requisition Requited Quantity Double field */
	private Double PART_TRANSFER_QTY;
	
	/** The value for the Requisition Requited Quantity Double field */
	private Double PART_PENDING_QTY;

	/** this is for marking this value is required or not */
	private boolean markForDelete;
	
	/** The value for the Requisition Auto ID Long field */
	private Long INVRID;
	
	/** The value for the Requisition Send Name String field */
	private String REQUITED_SENDNAME;
	
	private Long REQUITED_SENDER_ID;
	
	private Long REQUISITION_RECEIVER_ID;
	
	/** The value for the Requisition Requited Location String field */
	private String REQUITED_LOCATION;
	
	/** The value for the Requisition Requited Location String field */
	private Integer REQUITED_LOCATION_ID;

	/** The value for the Requisition Requited total Quantity Double field */
	private Double REQUITED_TOTALQTY;


	public InventoryRequisitionPartsDto() {
		super();
	}

	public InventoryRequisitionPartsDto(Long iNVRPARTID, Long pART_ID, Double pART_REQUITED_QTY,
			boolean mARKFORDELETE) {
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
		PART_REQUITED_QTY = Utility.round(pART_REQUITED_QTY, 2);
	}

	public boolean ismarkForDelete() {
		return markForDelete;
	}

	public void setmarkForDelete(boolean mARKFORDELETE) {
		markForDelete = mARKFORDELETE;
	}

	
	public Long getREQUITED_SENDER_ID() {
		return REQUITED_SENDER_ID;
	}

	public void setREQUITED_SENDER_ID(Long rEQUITED_SENDER_ID) {
		REQUITED_SENDER_ID = rEQUITED_SENDER_ID;
	}

	public Long getREQUISITION_RECEIVER_ID() {
		return REQUISITION_RECEIVER_ID;
	}

	public void setREQUISITION_RECEIVER_ID(Long rEQUISITION_RECEIVER_ID) {
		REQUISITION_RECEIVER_ID = rEQUISITION_RECEIVER_ID;
	}

	public String getREQUITED_SENDNAME() {
		return REQUITED_SENDNAME;
	}

	public void setREQUITED_SENDNAME(String rEQUITED_SENDNAME) {
		REQUITED_SENDNAME = rEQUITED_SENDNAME;
	}

	public String getREQUITED_LOCATION() {
		return REQUITED_LOCATION;
	}

	public void setREQUITED_LOCATION(String rEQUITED_LOCATION) {
		REQUITED_LOCATION = rEQUITED_LOCATION;
	}

	public Double getREQUITED_TOTALQTY() {
		return REQUITED_TOTALQTY;
	}

	public void setREQUITED_TOTALQTY(Double rEQUITED_TOTALQTY) {
		REQUITED_TOTALQTY = Utility.round(rEQUITED_TOTALQTY,2);
	}

	public Long getINVRID() {
		return INVRID;
	}

	public void setINVRID(Long iNVRID) {
		INVRID = iNVRID;
	}

	public Double getPART_TRANSFER_QTY() {
		return PART_TRANSFER_QTY;
	}

	public void setPART_TRANSFER_QTY(Double pART_TRANSFER_QTY) {
		PART_TRANSFER_QTY = Utility.round(pART_TRANSFER_QTY, 2);
	}

	public Double getPART_PENDING_QTY() {
		return PART_PENDING_QTY;
	}

	public void setPART_PENDING_QTY(Double pART_PENDING_QTY) {
		PART_PENDING_QTY = Utility.round(pART_PENDING_QTY,2 );
	}
	

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Integer getREQUITED_LOCATION_ID() {
		return REQUITED_LOCATION_ID;
	}

	public void setREQUITED_LOCATION_ID(Integer rEQUITED_LOCATION_ID) {
		REQUITED_LOCATION_ID = rEQUITED_LOCATION_ID;
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
		InventoryRequisitionPartsDto other = (InventoryRequisitionPartsDto) obj;
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
		return "InventoryRequisitionPartsDto [INVRPARTID=" + INVRPARTID + ", PART_ID=" + PART_ID
				+ ", PART_REQUITED_QTY=" + PART_REQUITED_QTY + ", markForDelete=" + markForDelete + "]";
	}

}
