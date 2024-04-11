package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 *
 *
 */

public class InventoryTransferDto {
	
	public static final String DAY_END_TIME = " 23:59:59.998";

	private Long inventory_transfer_id;

	private Long transfer_partid;

	private String transfer_partnumber;

	private String transfer_partname;
	
	private String transfer_Category;

	private String transfer_from_location;

	private long transfer_from_locationId;

	private String transfer_to_location;
	
	private Integer transfer_to_locationId;

	private Double transfer_quantity;

	private String transfer_date;

	private String transfer_by;

	private String transfer_receivedby;

	private String transfer_byEmail;

	private String transfer_receivedbyEmail;
	
	private Long transfer_receivedby_ID;

	private String transfer_receiveddate;

	private String transfer_receivedReason;

	private String transfer_via;
	
	private short transfer_via_ID;

	private String transfer_description;

	private Long transfer_inventory_id;

	private Long transfer_inventory_location_id;

	private Long transfer_inventory_all_id;

	private String TRANSFER_STATUS;

	private String CREATEDBY;

	private String LASTMODIFIEDBY;

	boolean markForDelete;
	
	private Long INVRID;
	
	private Long INVRID_NUMBER;
	/** The value for the Requisition Auto ID Long field */
	private Long INVRPARTID;

	/** The value for the ISSUES_Due DATE field */
	private String CREATED_DATE;

	/** The value for the ISSUES_Due DATE field */
	private String LASTUPDATED_DATE;
	
	private short TRANSFER_STATUS_ID;

	/**
	 * @return the inventory_transfer_id
	 */
	public Long getInventory_transfer_id() {
		return inventory_transfer_id;
	}

	/**
	 * @param inventory_transfer_id
	 *            the inventory_transfer_id to set
	 */
	public void setInventory_transfer_id(Long inventory_transfer_id) {
		this.inventory_transfer_id = inventory_transfer_id;
	}

	/**
	 * @return the transfer_partid
	 */
	public Long getTransfer_partid() {
		return transfer_partid;
	}

	/**
	 * @param transfer_partid
	 *            the transfer_partid to set
	 */
	public void setTransfer_partid(Long transfer_partid) {
		this.transfer_partid = transfer_partid;
	}

	/**
	 * @return the transfer_partnumber
	 */
	public String getTransfer_partnumber() {
		return transfer_partnumber;
	}

	/**
	 * @param transfer_partnumber
	 *            the transfer_partnumber to set
	 */
	public void setTransfer_partnumber(String transfer_partnumber) {
		this.transfer_partnumber = transfer_partnumber;
	}

	
	public String getTransfer_Category() {
		return transfer_Category;
	}

	public void setTransfer_Category(String transfer_Category) {
		this.transfer_Category = transfer_Category;
	}

	/**
	 * @return the transfer_partname
	 */
	public String getTransfer_partname() {
		return transfer_partname;
	}

	/**
	 * @param transfer_partname
	 *            the transfer_partname to set
	 */
	public void setTransfer_partname(String transfer_partname) {
		this.transfer_partname = transfer_partname;
	}

	/**
	 * @return the transfer_from_location
	 */
	public String getTransfer_from_location() {
		return transfer_from_location;
	}

	/**
	 * @param transfer_from_location
	 *            the transfer_from_location to set
	 */
	public void setTransfer_from_location(String transfer_from_location) {
		this.transfer_from_location = transfer_from_location;
	}

	/**
	 * @return the transfer_to_location
	 */
	public String getTransfer_to_location() {
		return transfer_to_location;
	}

	/**
	 * @param transfer_to_location
	 *            the transfer_to_location to set
	 */
	public void setTransfer_to_location(String transfer_to_location) {
		this.transfer_to_location = transfer_to_location;
	}

	public long getTransfer_from_locationId() {
		return transfer_from_locationId;
	}

	public void setTransfer_from_locationId(long transfer_from_locationId) {
		this.transfer_from_locationId = transfer_from_locationId;
	}

	public Integer getTransfer_to_locationId() {
		return transfer_to_locationId;
	}

	public void setTransfer_to_locationId(Integer transfer_to_locationId) {
		this.transfer_to_locationId = transfer_to_locationId;
	}

	/**
	 * @return the transfer_quantity
	 */
	public Double getTransfer_quantity() {
		return transfer_quantity;
	}
	

	public short getTransfer_via_ID() {
		return transfer_via_ID;
	}

	public void setTransfer_via_ID(short transfer_via_ID) {
		this.transfer_via_ID = transfer_via_ID;
	}

	/**
	 * @param transfer_quantity
	 *            the transfer_quantity to set
	 */
	public void setTransfer_quantity(Double transfer_quantity) {
		this.transfer_quantity = Utility.round(transfer_quantity,2);
	}

	/**
	 * @return the transfer_date
	 */
	public String getTransfer_date() {
		return transfer_date;
	}

	/**
	 * @param transfer_date
	 *            the transfer_date to set
	 */
	public void setTransfer_date(String transfer_date) {
		this.transfer_date = transfer_date;
	}

	/**
	 * @return the transfer_by
	 */
	public String getTransfer_by() {
		return transfer_by;
	}

	/**
	 * @param transfer_by
	 *            the transfer_by to set
	 */
	public void setTransfer_by(String transfer_by) {
		this.transfer_by = transfer_by;
	}

	/**
	 * @return the transfer_via
	 */
	public String getTransfer_via() {
		return transfer_via;
	}

	/**
	 * @param transfer_via
	 *            the transfer_via to set
	 */
	public void setTransfer_via(String transfer_via) {
		this.transfer_via = transfer_via;
	}

	/**
	 * @return the transfer_description
	 */
	public String getTransfer_description() {
		return transfer_description;
	}

	/**
	 * @param transfer_description
	 *            the transfer_description to set
	 */
	public void setTransfer_description(String transfer_description) {
		this.transfer_description = transfer_description;
	}

	
	public Long getTransfer_receivedby_ID() {
		return transfer_receivedby_ID;
	}

	public void setTransfer_receivedby_ID(Long transfer_receivedby_ID) {
		this.transfer_receivedby_ID = transfer_receivedby_ID;
	}

	/**
	 * @return the transfer_inventory_id
	 */
	public Long getTransfer_inventory_id() {
		return transfer_inventory_id;
	}

	/**
	 * @param transfer_inventory_id
	 *            the transfer_inventory_id to set
	 */
	public void setTransfer_inventory_id(Long transfer_inventory_id) {
		this.transfer_inventory_id = transfer_inventory_id;
	}

	/**
	 * @return the transfer_inventory_location_id
	 */
	public Long getTransfer_inventory_location_id() {
		return transfer_inventory_location_id;
	}

	/**
	 * @param transfer_inventory_location_id
	 *            the transfer_inventory_location_id to set
	 */
	public void setTransfer_inventory_location_id(Long transfer_inventory_location_id) {
		this.transfer_inventory_location_id = transfer_inventory_location_id;
	}

	/**
	 * @return the transfer_inventory_all_id
	 */
	public Long getTransfer_inventory_all_id() {
		return transfer_inventory_all_id;
	}

	/**
	 * @param transfer_inventory_all_id
	 *            the transfer_inventory_all_id to set
	 */
	public void setTransfer_inventory_all_id(Long transfer_inventory_all_id) {
		this.transfer_inventory_all_id = transfer_inventory_all_id;
	}

	public String getTransfer_receivedby() {
		return transfer_receivedby;
	}

	public void setTransfer_receivedby(String transfer_recivedby) {
		this.transfer_receivedby = transfer_recivedby;
	}

	public String getTransfer_byEmail() {
		return transfer_byEmail;
	}

	public void setTransfer_byEmail(String transfer_byEmail) {
		this.transfer_byEmail = transfer_byEmail;
	}

	public String getTransfer_receivedbyEmail() {
		return transfer_receivedbyEmail;
	}

	public void setTransfer_receivedbyEmail(String transfer_receivedbyEmail) {
		this.transfer_receivedbyEmail = transfer_receivedbyEmail;
	}

	public String getTransfer_receiveddate() {
		return transfer_receiveddate;
	}

	public void setTransfer_receiveddate(String transfer_receiveddate) {
		this.transfer_receiveddate = transfer_receiveddate;
	}

	public String getTransfer_receivedReason() {
		return transfer_receivedReason;
	}

	public void setTransfer_receivedReason(String transfer_receivedReason) {
		this.transfer_receivedReason = transfer_receivedReason;
	}

	public String getTRANSFER_STATUS() {
		return TRANSFER_STATUS;
	}

	public void setTRANSFER_STATUS(String tRANSFER_STATUS) {
		TRANSFER_STATUS = tRANSFER_STATUS;
	}

	public String getCREATEDBY() {
		return CREATEDBY;
	}
	
	

	public Long getINVRPARTID() {
		return INVRPARTID;
	}

	public void setINVRPARTID(Long iNVRPARTID) {
		INVRPARTID = iNVRPARTID;
	}

	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean MarkForDelete) {
		markForDelete = MarkForDelete;
	}

	public String getCREATED_DATE() {
		return CREATED_DATE;
	}

	public void setCREATED_DATE(String cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	public String getLASTUPDATED_DATE() {
		return LASTUPDATED_DATE;
	}

	public void setLASTUPDATED_DATE(String lASTUPDATED_DATE) {
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}

	
	public Long getINVRID() {
		return INVRID;
	}

	public void setINVRID(Long iNVRID) {
		INVRID = iNVRID;
	}

	public short getTRANSFER_STATUS_ID() {
		return TRANSFER_STATUS_ID;
	}

	public void setTRANSFER_STATUS_ID(short tRANSFER_STATUS_ID) {
		TRANSFER_STATUS_ID = tRANSFER_STATUS_ID;
	}

	public Long getINVRID_NUMBER() {
		return INVRID_NUMBER;
	}

	public void setINVRID_NUMBER(Long iNVRID_NUMBER) {
		INVRID_NUMBER = iNVRID_NUMBER;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryTransferDto [inventory_transfer_id=");
		builder.append(inventory_transfer_id);
		builder.append(", transfer_partid=");
		builder.append(transfer_partid);
		builder.append(", transfer_partnumber=");
		builder.append(transfer_partnumber);
		builder.append(", transfer_partname=");
		builder.append(transfer_partname);
		builder.append(", transfer_from_location=");
		builder.append(transfer_from_location);
		builder.append(", transfer_from_locationId=");
		builder.append(transfer_from_locationId);
		builder.append(", transfer_to_location=");
		builder.append(transfer_to_location);
		builder.append(", transfer_to_locationId=");
		builder.append(transfer_to_locationId);
		builder.append(", transfer_quantity=");
		builder.append(transfer_quantity);
		builder.append(", transfer_date=");
		builder.append(transfer_date);
		builder.append(", transfer_by=");
		builder.append(transfer_by);
		builder.append(", transfer_receivedby=");
		builder.append(transfer_receivedby);
		builder.append(", transfer_byEmail=");
		builder.append(transfer_byEmail);
		builder.append(", transfer_receivedbyEmail=");
		builder.append(transfer_receivedbyEmail);
		builder.append(", transfer_receiveddate=");
		builder.append(transfer_receiveddate);
		builder.append(", transfer_receivedReason=");
		builder.append(transfer_receivedReason);
		builder.append(", transfer_via=");
		builder.append(transfer_via);
		builder.append(", transfer_description=");
		builder.append(transfer_description);
		builder.append(", transfer_inventory_id=");
		builder.append(transfer_inventory_id);
		builder.append(", transfer_inventory_location_id=");
		builder.append(transfer_inventory_location_id);
		builder.append(", transfer_inventory_all_id=");
		builder.append(transfer_inventory_all_id);
		builder.append(", TRANSFER_STATUS=");
		builder.append(TRANSFER_STATUS);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", LASTUPDATED_DATE=");
		builder.append(LASTUPDATED_DATE);
		builder.append("]");
		return builder.toString();
	}

}