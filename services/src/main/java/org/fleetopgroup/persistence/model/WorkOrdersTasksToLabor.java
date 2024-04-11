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
@Table(name = "WorkOrdersTasksToLabor")
public class WorkOrdersTasksToLabor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "workordertaskto_laberid")
	private Long workordertaskto_laberid;

	@Column(name = "laberid", length = 10)
	private Integer laberid;

	@Column(name = "labername", length = 150)
	private String labername;

	@Column(name = "laberhourscost", length = 10)
	private Double laberhourscost;

	@Column(name = "eachlabercost", length = 10)
	private Double eachlabercost;

	@Column(name = "laberdiscount", length = 5)
	private Double laberdiscount;

	@Column(name = "labertax", length = 5)
	private Double labertax;

	@Column(name = "totalcost", length = 10)
	private Double totalcost;

	@Column(name = "workorders_id")
	private Long workorders_id;

	@Column(name = "workordertaskid")
	private Long workordertaskid;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public WorkOrdersTasksToLabor() {
		super();
	}

	public WorkOrdersTasksToLabor(Integer laberid, String labername, Double laberhourscost, Double eachlabercost,
			Double totalcost, Long workorders_id, Long workordertaskid, Integer companyId) {
		super();
		this.laberid = laberid;
		this.labername = labername;
		this.laberhourscost = laberhourscost;
		this.eachlabercost = eachlabercost;
		this.totalcost = totalcost;
		this.workorders_id = workorders_id;
		this.workordertaskid = workordertaskid;
		this.companyId = companyId;
	}

	/**
	 * @return the workordertaskto_laberid
	 */
	public Long getWorkordertaskto_laberid() {
		return workordertaskto_laberid;
	}

	/**
	 * @param workordertaskto_laberid
	 *            the workordertaskto_laberid to set
	 */
	public void setWorkordertaskto_laberid(Long workordertaskto_laberid) {
		this.workordertaskto_laberid = workordertaskto_laberid;
	}

	/**
	 * @return the laberid
	 */
	public Integer getLaberid() {
		return laberid;
	}

	/**
	 * @param laberid
	 *            the laberid to set
	 */
	public void setLaberid(Integer laberid) {
		this.laberid = laberid;
	}

	/**
	 * @return the labername
	 */
	public String getLabername() {
		return labername;
	}

	/**
	 * @param labername
	 *            the labername to set
	 */
	public void setLabername(String labername) {
		this.labername = labername;
	}

	/**
	 * @return the laberhourscost
	 */
	public Double getLaberhourscost() {
		return laberhourscost;
	}

	/**
	 * @param laberhourscost
	 *            the laberhourscost to set
	 */
	public void setLaberhourscost(Double laberhourscost) {
		this.laberhourscost = laberhourscost;
	}

	/**
	 * @return the eachlabercost
	 */
	public Double getEachlabercost() {
		return eachlabercost;
	}

	/**
	 * @param eachlabercost
	 *            the eachlabercost to set
	 */
	public void setEachlabercost(Double eachlabercost) {
		this.eachlabercost = eachlabercost;
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

	public Double getLaberdiscount() {
		return laberdiscount;
	}

	public void setLaberdiscount(Double laberdiscount) {
		this.laberdiscount = laberdiscount;
	}

	public Double getLabertax() {
		return labertax;
	}

	public void setLabertax(Double labertax) {
		this.labertax = labertax;
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
		result = prime * result + ((workorders_id == null) ? 0 : workorders_id.hashCode());
		result = prime * result + ((workordertaskid == null) ? 0 : workordertaskid.hashCode());
		result = prime * result + ((workordertaskto_laberid == null) ? 0 : workordertaskto_laberid.hashCode());
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
		WorkOrdersTasksToLabor other = (WorkOrdersTasksToLabor) obj;
		if (workorders_id == null) {
			if (other.workorders_id != null)
				return false;
		} else if (!workorders_id.equals(other.workorders_id))
			return false;
		if (workordertaskid == null) {
			if (other.workordertaskid != null)
				return false;
		} else if (!workordertaskid.equals(other.workordertaskid))
			return false;
		if (workordertaskto_laberid == null) {
			if (other.workordertaskto_laberid != null)
				return false;
		} else if (!workordertaskto_laberid.equals(other.workordertaskto_laberid))
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
		builder.append("WorkOrdersTasksToLabor [laberid=");
		builder.append(laberid);
		builder.append(", labername=");
		builder.append(labername);
		builder.append(", laberhourscost=");
		builder.append(laberhourscost);
		builder.append(", eachlabercost=");
		builder.append(eachlabercost);
		builder.append(", totalcost=");
		builder.append(totalcost);
		builder.append(", workorders_id=");
		builder.append(workorders_id);
		builder.append(", workordertaskid=");
		builder.append(workordertaskid);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append("]");
		return builder.toString();
	}

}