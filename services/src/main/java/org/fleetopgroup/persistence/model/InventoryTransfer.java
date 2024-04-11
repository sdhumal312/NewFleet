package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "inventorytransfer")
public class InventoryTransfer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inventory_transfer_id")
	private Long inventory_transfer_id;

	@Column(name = "transfer_partid", nullable = false)
	private Long transfer_partid;

	
	@Column(name = "transfer_from_locationId", nullable = false)
	private Integer transfer_from_locationId;

	
	@Column(name = "transfer_to_locationId", nullable = false)
	private Integer transfer_to_locationId;

	@Column(name = "transfer_quantity", length = 10)
	private Double transfer_quantity;

	@Column(name = "transfer_date")
	private Date transfer_date;

	
	@Column(name = "transfer_by_ID", nullable = false)
	private Long transfer_by_ID;
	
	@Column(name = "transfer_receivedby_ID", nullable = false)
	private Long transfer_receivedby_ID;

	@Column(name = "transfer_receiveddate")
	private Date transfer_receiveddate;

	@Column(name = "transfer_receivedReason", length = 250)
	private String transfer_receivedReason;

	@Column(name = "transfer_via_ID", nullable = false)
	private short transfer_via_ID;

	@Column(name = "transfer_description", length = 250)
	private String transfer_description;

	@Column(name = "transfer_inventory_id")
	private Long transfer_inventory_id;

	@Column(name = "transfer_inventory_location_id")
	private Long transfer_inventory_location_id;

	@Column(name = "transfer_inventory_all_id")
	private Long transfer_inventory_all_id;

	
	/** The value for the Requisition Status ID  field */
	@Column(name = "TRANSFER_STATUS_ID", nullable = false)
	private short TRANSFER_STATUS_ID;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdById", nullable = false, updatable = false)
	private Long createdById;

	
	@Column(name = "lastModifiedById", nullable = false)
	private Long lastModifiedById;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "CREATED_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATED_DATE;

	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "LASTUPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date LASTUPDATED_DATE;
	
	@Column(name = "inventoryRequisitionId")
	private Long inventoryRequisitionId;
	
	@Column(name = "rejectRemark", length = 250)
	private String rejectRemark;
	
	// Centralized Transfer .... 
	@Column(name = "requisitionApprovalId")
	private Long requisitionApprovalId;

	public InventoryTransfer() {
		super();
	}

	public InventoryTransfer(Long inventory_transfer_id, Long transfer_partid, 
			Double transfer_quantity, Date transfer_date,  
			String transfer_description, Long transfer_inventory_id, Long transfer_inventory_location_id,
			Long transfer_inventory_all_id, Integer transfer_from_locationId, Integer transfer_to_locationId, Integer companyId) {
		super();
		this.inventory_transfer_id = inventory_transfer_id;
		this.transfer_partid = transfer_partid;
		this.transfer_quantity = transfer_quantity;
		this.transfer_date = transfer_date;
		this.transfer_description = transfer_description;
		this.transfer_inventory_id = transfer_inventory_id;
		this.transfer_inventory_location_id = transfer_inventory_location_id;
		this.transfer_inventory_all_id = transfer_inventory_all_id;
		this.transfer_from_locationId	= transfer_from_locationId;
		this.transfer_to_locationId	= transfer_to_locationId;
		this.companyId = companyId;
	}

	public Long getInventory_transfer_id() {
		return inventory_transfer_id;
	}

	public void setInventory_transfer_id(Long inventory_transfer_id) {
		this.inventory_transfer_id = inventory_transfer_id;
	}

	public Long getTransfer_partid() {
		return transfer_partid;
	}

	public void setTransfer_partid(Long transfer_partid) {
		this.transfer_partid = transfer_partid;
	}


	public Integer getTransfer_from_locationId() {
		return transfer_from_locationId;
	}

	public void setTransfer_from_locationId(Integer transfer_from_locationId) {
		this.transfer_from_locationId = transfer_from_locationId;
	}

	public Integer getTransfer_to_locationId() {
		return transfer_to_locationId;
	}

	public void setTransfer_to_locationId(Integer transfer_to_locationId) {
		this.transfer_to_locationId = transfer_to_locationId;
	}

	public Double getTransfer_quantity() {
		return transfer_quantity;
	}

	public void setTransfer_quantity(Double transfer_quantity) {
		this.transfer_quantity = transfer_quantity;
	}

	public Date getTransfer_date() {
		return transfer_date;
	}

	public void setTransfer_date(Date transfer_date) {
		this.transfer_date = transfer_date;
	}


	public Long getTransfer_by_ID() {
		return transfer_by_ID;
	}

	public void setTransfer_by_ID(Long transfer_by_ID) {
		this.transfer_by_ID = transfer_by_ID;
	}

	public Long getTransfer_receivedby_ID() {
		return transfer_receivedby_ID;
	}

	public void setTransfer_receivedby_ID(Long transfer_receivedby_ID) {
		this.transfer_receivedby_ID = transfer_receivedby_ID;
	}

	public Date getTransfer_receiveddate() {
		return transfer_receiveddate;
	}

	public void setTransfer_receiveddate(Date transfer_receiveddate) {
		this.transfer_receiveddate = transfer_receiveddate;
	}

	public String getTransfer_receivedReason() {
		return transfer_receivedReason;
	}

	public void setTransfer_receivedReason(String transfer_receivedReson) {
		this.transfer_receivedReason = transfer_receivedReson;
	}

	
	public short getTransfer_via_ID() {
		return transfer_via_ID;
	}

	public void setTransfer_via_ID(short transfer_via_ID) {
		this.transfer_via_ID = transfer_via_ID;
	}

	public String getTransfer_description() {
		return transfer_description;
	}

	public void setTransfer_description(String transfer_description) {
		this.transfer_description = transfer_description;
	}

	public Long getTransfer_inventory_id() {
		return transfer_inventory_id;
	}

	public void setTransfer_inventory_id(Long transfer_inventory_id) {
		this.transfer_inventory_id = transfer_inventory_id;
	}

	public Long getTransfer_inventory_location_id() {
		return transfer_inventory_location_id;
	}

	public void setTransfer_inventory_location_id(Long transfer_inventory_location_id) {
		this.transfer_inventory_location_id = transfer_inventory_location_id;
	}

	public Long getTransfer_inventory_all_id() {
		return transfer_inventory_all_id;
	}

	public void setTransfer_inventory_all_id(Long transfer_inventory_all_id) {
		this.transfer_inventory_all_id = transfer_inventory_all_id;
	}

	public short getTRANSFER_STATUS_ID() {
		return TRANSFER_STATUS_ID;
	}

	public void setTRANSFER_STATUS_ID(short tRANSFER_STATUS_ID) {
		TRANSFER_STATUS_ID = tRANSFER_STATUS_ID;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/*public String getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}*/

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean MarkForDelete) {
		markForDelete = MarkForDelete;
	}

	public Date getCREATED_DATE() {
		return CREATED_DATE;
	}

	public void setCREATED_DATE(Date cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	public Date getLASTUPDATED_DATE() {
		return LASTUPDATED_DATE;
	}

	public void setLASTUPDATED_DATE(Date lASTUPDATED_DATE) {
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}
	
	

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Long getInventoryRequisitionId() {
		return inventoryRequisitionId;
	}

	public void setInventoryRequisitionId(Long inventoryRequisitionId) {
		this.inventoryRequisitionId = inventoryRequisitionId;
	}

	public String getRejectRemark() {
		return rejectRemark;
	}

	public void setRejectRemark(String rejectRemark) {
		this.rejectRemark = rejectRemark;
	}

	public Long getRequisitionApprovalId() {
		return requisitionApprovalId;
	}

	public void setRequisitionApprovalId(Long requisitionApprovalId) {
		this.requisitionApprovalId = requisitionApprovalId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inventory_transfer_id == null) ? 0 : inventory_transfer_id.hashCode());
		result = prime * result + ((transfer_inventory_all_id == null) ? 0 : transfer_inventory_all_id.hashCode());
		result = prime * result + ((transfer_inventory_id == null) ? 0 : transfer_inventory_id.hashCode());
		result = prime * result
				+ ((transfer_inventory_location_id == null) ? 0 : transfer_inventory_location_id.hashCode());
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
		InventoryTransfer other = (InventoryTransfer) obj;
		if (inventory_transfer_id == null) {
			if (other.inventory_transfer_id != null)
				return false;
		} else if (!inventory_transfer_id.equals(other.inventory_transfer_id))
			return false;
		if (transfer_inventory_all_id == null) {
			if (other.transfer_inventory_all_id != null)
				return false;
		} else if (!transfer_inventory_all_id.equals(other.transfer_inventory_all_id))
			return false;
		if (transfer_inventory_id == null) {
			if (other.transfer_inventory_id != null)
				return false;
		} else if (!transfer_inventory_id.equals(other.transfer_inventory_id))
			return false;
		if (transfer_inventory_location_id == null) {
			if (other.transfer_inventory_location_id != null)
				return false;
		} else if (!transfer_inventory_location_id.equals(other.transfer_inventory_location_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryTransfer [inventory_transfer_id=");
		builder.append(inventory_transfer_id);
		builder.append(", transfer_partid=");
		builder.append(transfer_partid);
		builder.append(", transfer_from_locationId=");
		builder.append(transfer_from_locationId);
		builder.append(", transfer_to_locationId=");
		builder.append(transfer_to_locationId);
		builder.append(", transfer_quantity=");
		builder.append(transfer_quantity);
		builder.append(", transfer_date=");
		builder.append(transfer_date);
		/*builder.append(", transfer_by=");
		builder.append(transfer_by);
		builder.append(", transfer_receivedby=");
		builder.append(transfer_receivedby);
		builder.append(", transfer_byEmail=");
		builder.append(transfer_byEmail);
		builder.append(", transfer_receivedbyEmail=");
		builder.append(transfer_receivedbyEmail);*/
		builder.append(", transfer_receiveddate=");
		builder.append(transfer_receiveddate);
		builder.append(", transfer_receivedReason=");
		builder.append(transfer_receivedReason);
		builder.append(", transfer_via_ID=");
		builder.append(transfer_via_ID);
		builder.append(", transfer_description=");
		builder.append(transfer_description);
		builder.append(", transfer_inventory_id=");
		builder.append(transfer_inventory_id);
		builder.append(", transfer_inventory_location_id=");
		builder.append(transfer_inventory_location_id);
		builder.append(", transfer_inventory_all_id=");
		builder.append(transfer_inventory_all_id);
		builder.append(", companyId=");
		builder.append(companyId);
		/*builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);*/
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