package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "workorderstaskstoreceived")
public class WorkOrdersTasksToReceived implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "received_id")
	private Long received_id;

	@Column(name = "received_workorders_id")
	private Long received_workorders_id;

	@Column(name = "received_partid", length = 10)
	private Long received_partid;

	@Column(name = "received_partname", length = 150)
	@Deprecated
	private String received_partname;

	@Column(name = "received_partnumber", length = 150)
	@Deprecated
	private String received_partnumber;

	@Column(name = "received_location", length = 150)
	@Deprecated
	private String received_location;
	
	@Column(name = "received_locationId")
	private Integer received_locationId;

	@Column(name = "received_quantity", length = 10)
	private Double received_quantity;

	@Column(name = "received_date", nullable = false, updatable = false)
	private Date received_date;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public WorkOrdersTasksToReceived() {
		super();
	}

	public WorkOrdersTasksToReceived(Long received_workorders_id, Long received_partid, Double received_quantity, Date received_date, Integer companyId) {
		super();
		this.received_workorders_id = received_workorders_id;
		this.received_partid = received_partid;
		this.received_quantity = received_quantity;
		this.received_date = received_date;
		this.companyId = companyId;
	}

	/**
	 * @return the received_id
	 */
	public Long getReceived_id() {
		return received_id;
	}

	/**
	 * @param received_id
	 *            the received_id to set
	 */
	public void setReceived_id(Long received_id) {
		this.received_id = received_id;
	}

	/**
	 * @return the received_workorders_id
	 */
	public Long getReceived_workorders_id() {
		return received_workorders_id;
	}

	/**
	 * @param received_workorders_id
	 *            the received_workorders_id to set
	 */
	public void setReceived_workorders_id(Long received_workorders_id) {
		this.received_workorders_id = received_workorders_id;
	}

	/**
	 * @return the received_partid
	 */
	public Long getReceived_partid() {
		return received_partid;
	}

	/**
	 * @param received_partid
	 *            the received_partid to set
	 */
	public void setReceived_partid(Long received_partid) {
		this.received_partid = received_partid;
	}

	
	/**
	 * @return the received_locationId
	 */
	public Integer getReceived_locationId() {
		return received_locationId;
	}

	/**
	 * @param received_locationId the received_locationId to set
	 */
	public void setReceived_locationId(Integer received_locationId) {
		this.received_locationId = received_locationId;
	}

	/**
	 * @return the received_quantity
	 */
	public Double getReceived_quantity() {
		return received_quantity;
	}

	/**
	 * @param received_quantity
	 *            the received_quantity to set
	 */
	public void setReceived_quantity(Double received_quantity) {
		this.received_quantity = received_quantity;
	}

	/**
	 * @return the received_date
	 */
	public Date getReceived_date() {
		return received_date;
	}

	/**
	 * @param received_date
	 *            the received_date to set
	 */
	public void setReceived_date(Date received_date) {
		this.received_date = received_date;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((received_quantity == null) ? 0 : received_quantity.hashCode());
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
		WorkOrdersTasksToReceived other = (WorkOrdersTasksToReceived) obj;
		if (received_quantity == null) {
			if (other.received_quantity != null)
				return false;
		} else if (!received_quantity.equals(other.received_quantity))
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
		builder.append("WorkOrdersTasksToReceived [received_workorders_id=");
		builder.append(received_workorders_id);
		builder.append(", received_partid=");
		builder.append(received_partid);
		builder.append(", received_quantity=");
		builder.append(received_quantity);
		builder.append(", received_date=");
		builder.append(received_date);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append("]");
		return builder.toString();
	}

}