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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "WorkOrdersNotes")
public class WorkOrdersNotes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "workorder_noteid")
	private Long workorder_noteid;

	@Column(name = "remarks", length = 250)
	private String remarks;

	@Column(name = "createdBy", length = 25)
	private String createdBy;
	
	@Column(name = "lastModifiedBy", length = 25)
	private Date lastModifiedBy;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	@Column(name = "created", nullable = false, updatable = false)
	private String created;

	@Column(name = "lastupdated", nullable = false, updatable = false)
	private String lastupdated;

	@ManyToOne
	@JoinColumn(name = "workorders_id")
	private WorkOrders workorders;

	public WorkOrdersNotes() {
		super();
	}

	public WorkOrdersNotes(String remarks, String lastupdated) {
		super();
		this.remarks = remarks;
		this.lastupdated = lastupdated;
	}

	/**
	 * @return the workorder_noteid
	 */
	public Long getWorkorder_noteid() {
		return workorder_noteid;
	}

	/**
	 * @param workorder_noteid
	 *            the workorder_noteid to set
	 */
	public void setWorkorder_noteid(Long workorder_noteid) {
		this.workorder_noteid = workorder_noteid;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public Date getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(Date lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the status
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public String getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(String lastupdated) {
		this.lastupdated = lastupdated;
	}

	/**
	 * @return the workorders
	 */
	public WorkOrders getWorkorders() {
		return workorders;
	}

	/**
	 * @param workorders
	 *            the workorders to set
	 */
	public void setWorkorders(WorkOrders workorders) {
		this.workorders = workorders;
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
		result = prime * result + ((lastModifiedBy == null) ? 0 : lastModifiedBy.hashCode());
		result = prime * result + ((lastupdated == null) ? 0 : lastupdated.hashCode());
		result = prime * result + ((workorder_noteid == null) ? 0 : workorder_noteid.hashCode());
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
		WorkOrdersNotes other = (WorkOrdersNotes) obj;
		if (lastModifiedBy == null) {
			if (other.lastModifiedBy != null)
				return false;
		} else if (!lastModifiedBy.equals(other.lastModifiedBy))
			return false;
		if (lastupdated == null) {
			if (other.lastupdated != null)
				return false;
		} else if (!lastupdated.equals(other.lastupdated))
			return false;
		if (workorder_noteid == null) {
			if (other.workorder_noteid != null)
				return false;
		} else if (!workorder_noteid.equals(other.workorder_noteid))
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
		builder.append("WorkOrdersNotes [remarks=");
		builder.append(remarks);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", workorders=");
		builder.append(workorders);
		builder.append("]");
		return builder.toString();
	}

}