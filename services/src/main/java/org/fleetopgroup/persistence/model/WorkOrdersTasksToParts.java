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
@Table(name = "WorkOrdersTasksToParts")
public class WorkOrdersTasksToParts implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "workordertaskto_partid")
	private Long workordertaskto_partid;

	// this part_id this we select InventoryLocation Quantity Id
	@Column(name = "partid")
	private Long partid;

	@Column(name = "locationId")
	private Integer locationId;

	@Column(name = "quantity", length = 10)
	private Double quantity;

	@Column(name = "parteachcost", length = 10)
	private Double parteachcost;

	@Column(name = "totalcost", length = 10)
	private Double totalcost;

	@Column(name = "oldpart", length = 150)
	private String oldpart;

	@Column(name = "inventory_id")
	private Long inventory_id;

	@Column(name = "workorders_id")
	private Long workorders_id;

	@Column(name = "workordertaskid")
	private Long workordertaskid;

	@Column(name = "vehicle_vid", length = 10)
	private Integer vehicle_vid;

	/** The value for the last_occurred_date DATE field */
	@Basic(optional = true)
	@Column(name = "last_occurred_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date last_occurred_date;

	/** The value for the last_occurred_woId back end process */
	@Column(name = "last_occurred_woId", nullable = true)
	private Long last_occurred_woId;

	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "createdById")
	private long createdById;
	
	@Column(name = "last_occ_odometer")
	private Integer last_occ_odometer;
	
	@Basic(optional=false)
	@Column(name = "woPart_document", nullable = false)
	private boolean woPart_document= false;
	
	@Column(name = "assignedNoPart", nullable = false)
	private short	assignedNoPart; 
	

	public WorkOrdersTasksToParts() {
		super();
	}
	
	public WorkOrdersTasksToParts(Long partid,  Double quantity,
			Double parteachcost, Double totalcost, String oldpart, Long inventory_id, Long workorders_id,
			Long workordertaskid, Integer companyId,Long createdById) {
		super();
		this.partid = partid;
		this.quantity = quantity;
		this.parteachcost = parteachcost;
		this.totalcost = totalcost;
		this.oldpart = oldpart;
		this.inventory_id = inventory_id;
		this.workorders_id = workorders_id;
		this.workordertaskid = workordertaskid;
		this.companyId = companyId;
		this.createdById = createdById;
		
	}

	/**
	 * @return the workordertaskto_partid
	 */
	public Long getWorkordertaskto_partid() {
		return workordertaskto_partid;
	}

	/**
	 * @param workordertaskto_partid
	 *            the workordertaskto_partid to set
	 */
	public void setWorkordertaskto_partid(Long workordertaskto_partid) {
		this.workordertaskto_partid = workordertaskto_partid;
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
	 * @return the locationId
	 */
	public Integer getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
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
		this.quantity = quantity;
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
		this.parteachcost = parteachcost;
	}

	/**
	 * @return the totalcost
	 */
	public Double getTotalcost() {
		return totalcost;
	}

	/**
	 * @param totalcost
	 *            the totalcost to set
	 */
	public void setTotalcost(Double totalcost) {
		this.totalcost = totalcost;
	}

	/**
	 * @return the workorders_id
	 */
	public Long getWorkorders_id() {
		return workorders_id;
	}

	/**
	 * @param workorders_id
	 *            the workorders_id to set
	 */
	public void setWorkorders_id(Long workorders_id) {
		this.workorders_id = workorders_id;
	}

	/**
	 * @return the workordertaskid
	 */
	public Long getWorkordertaskid() {
		return workordertaskid;
	}

	/**
	 * @param workordertaskid
	 *            the workordertaskid to set
	 */
	public void setWorkordertaskid(Long workordertaskid) {
		this.workordertaskid = workordertaskid;
	}

	/**
	 * @return the oldpart
	 */
	public String getOldpart() {
		return oldpart;
	}

	/**
	 * @param oldpart
	 *            the oldpart to set
	 */
	public void setOldpart(String oldpart) {
		this.oldpart = oldpart;
	}

	/**
	 * @return the inventory_id
	 */
	public Long getInventory_id() {
		return inventory_id;
	}

	/**
	 * @param inventory_id
	 *            the inventory_id to set
	 */
	public void setInventory_id(Long inventory_id) {
		this.inventory_id = inventory_id;
	}

	/**
	 * @return the vehicle_vid
	 */
	public Integer getVehicle_vid() {
		return vehicle_vid;
	}

	/**
	 * @param vehicle_vid
	 *            the vehicle_vid to set
	 */
	public void setVehicle_vid(Integer vehicle_vid) {
		this.vehicle_vid = vehicle_vid;
	}

	/**
	 * @return the last_occurred_date
	 */
	public Date getLast_occurred_date() {
		return last_occurred_date;
	}

	/**
	 * @param last_occurred_date
	 *            the last_occurred_date to set
	 */
	public void setLast_occurred_date(Date last_occurred_date) {
		this.last_occurred_date = last_occurred_date;
	}

	/**
	 * @return the last_occurred_woId
	 */
	public Long getLast_occurred_woId() {
		return last_occurred_woId;
	}

	/**
	 * @param last_occurred_woId
	 *            the last_occurred_woId to set
	 */
	public void setLast_occurred_woId(Long last_occurred_woId) {
		this.last_occurred_woId = last_occurred_woId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getLast_occ_odometer() {
		return last_occ_odometer;
	}

	public void setLast_occ_odometer(Integer last_occ_odometer) {
		this.last_occ_odometer = last_occ_odometer;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}	
	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}
	
	public boolean isWoPart_document() {
		return woPart_document;
	}

	public void setWoPart_document(boolean woPart_document) {
		this.woPart_document = woPart_document;
	}



	public short getAssignedNoPart() {
		return assignedNoPart;
	}

	public void setAssignedNoPart(short assignedNoPart) {
		this.assignedNoPart = assignedNoPart;
	}

	public void setCreatedById(long createdById) {
		this.createdById = createdById;
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
		result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
		result = prime * result + ((partid == null) ? 0 : partid.hashCode());
		result = prime * result + ((workordertaskid == null) ? 0 : workordertaskid.hashCode());
		result = prime * result + ((workordertaskto_partid == null) ? 0 : workordertaskto_partid.hashCode());
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
		WorkOrdersTasksToParts other = (WorkOrdersTasksToParts) obj;
		if (locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!locationId.equals(other.locationId))
			return false;
		if (partid == null) {
			if (other.partid != null)
				return false;
		} else if (!partid.equals(other.partid))
			return false;
		
		if (workordertaskid == null) {
			if (other.workordertaskid != null)
				return false;
		} else if (!workordertaskid.equals(other.workordertaskid))
			return false;
		if (workordertaskto_partid == null) {
			if (other.workordertaskto_partid != null)
				return false;
		} else if (!workordertaskto_partid.equals(other.workordertaskto_partid))
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
		builder.append("WorkOrdersTasksToParts [workordertaskto_partid=");
		builder.append(workordertaskto_partid);
		builder.append(", partid=");
		builder.append(partid);
		/*builder.append(", partname=");
		builder.append(partname);
		builder.append(", partnumber=");
		builder.append(partnumber);
		builder.append(", location=");
		builder.append(location);*/
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", parteachcost=");
		builder.append(parteachcost);
		builder.append(", totalcost=");
		builder.append(totalcost);
		builder.append(", oldpart=");
		builder.append(oldpart);
		builder.append(", inventory_id=");
		builder.append(inventory_id);
		builder.append(", workorders_id=");
		builder.append(workorders_id);
		builder.append(", workordertaskid=");
		builder.append(workordertaskid);
		builder.append(", vehicle_vid=");
		builder.append(vehicle_vid);
		builder.append(", last_occurred_date=");
		builder.append(last_occurred_date);
		builder.append(", last_occurred_woId=");
		builder.append(last_occurred_woId);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", createdById=");
		builder.append(createdById);		
		builder.append(", last_occ_odometer=");
		builder.append(last_occ_odometer);		
		builder.append("]");
		return builder.toString();
	}

}