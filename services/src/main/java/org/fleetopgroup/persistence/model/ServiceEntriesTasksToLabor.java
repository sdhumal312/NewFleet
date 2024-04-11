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
@Table(name = "ServiceEntriesTasksToLabor")
public class ServiceEntriesTasksToLabor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serviceEntriesto_laberid")
	private Long serviceEntriesto_laberid;

	@Column(name = "laberid", length = 10)
	private Integer laberid;

	@Column(name = "labername", length = 150)
	private String labername;

	@Column(name = "laberhourscost", length = 10)
	private Double laberhourscost;
	
	@Column(name = "laberdiscount", length = 5)
	private Double laberdiscount;

	@Column(name = "labertax", length = 5)
	private Double labertax;

	@Column(name = "eachlabercost", length = 10)
	private Double eachlabercost;

	@Column(name = "totalcost", length = 10)
	private Double totalcost;

	@Column(name = "serviceEntries_id")
	private Long serviceEntries_id;

	@Column(name = "servicetaskid")
	private Long servicetaskid;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	@Column(name = "labourIGST", length = 10)
	private Double labourIGST;

	@Column(name = "labourCGST", length = 10)
	private Double labourCGST;
	
	@Column(name = "labourSGST", length = 10)
	private Double labourSGST;
	
	public ServiceEntriesTasksToLabor() {
		super();
	}

	public ServiceEntriesTasksToLabor(Integer laberid, String labername, Double laberhourscost, Double eachlabercost,
			Double totalcost, Long serviceEntries_id, Long servicetaskid, Integer companyId) {
		super();
		this.laberid = laberid;
		this.labername = labername;
		this.laberhourscost = laberhourscost;
		this.eachlabercost = eachlabercost;
		this.totalcost = totalcost;
		this.serviceEntries_id = serviceEntries_id;
		this.servicetaskid = servicetaskid;
		this.companyId = companyId;
	}

	/**
	 * @return the serviceEntriesto_laberid
	 */
	public Long getServiceEntriesto_laberid() {
		return serviceEntriesto_laberid;
	}

	/**
	 * @param serviceEntriesto_laberid
	 *            the serviceEntriesto_laberid to set
	 */
	public void setServiceEntriesto_laberid(Long serviceEntriesto_laberid) {
		this.serviceEntriesto_laberid = serviceEntriesto_laberid;
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
	 * @return the serviceEntries_id
	 */
	public Long getServiceEntries_id() {
		return serviceEntries_id;
	}

	/**
	 * @param serviceEntries_id
	 *            the serviceEntries_id to set
	 */
	public void setServiceEntries_id(Long serviceEntries_id) {
		this.serviceEntries_id = serviceEntries_id;
	}

	/**
	 * @return the servicetaskid
	 */
	public Long getServicetaskid() {
		return servicetaskid;
	}

	/**
	 * @param servicetaskid
	 *            the servicetaskid to set
	 */
	public void setServicetaskid(Long servicetaskid) {
		this.servicetaskid = servicetaskid;
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

	
	public Double getLabourIGST() {
		return labourIGST;
	}

	public void setLabourIGST(Double labourIGST) {
		this.labourIGST = labourIGST;
	}

	public Double getLabourCGST() {
		return labourCGST;
	}

	public void setLabourCGST(Double labourCGST) {
		this.labourCGST = labourCGST;
	}

	public Double getLabourSGST() {
		return labourSGST;
	}

	public void setLabourSGST(Double labourSGST) {
		this.labourSGST = labourSGST;
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
		result = prime * result + ((labername == null) ? 0 : labername.hashCode());
		result = prime * result + ((serviceEntries_id == null) ? 0 : serviceEntries_id.hashCode());
		result = prime * result + ((servicetaskid == null) ? 0 : servicetaskid.hashCode());
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
		ServiceEntriesTasksToLabor other = (ServiceEntriesTasksToLabor) obj;
		if (labername == null) {
			if (other.labername != null)
				return false;
		} else if (!labername.equals(other.labername))
			return false;
		if (serviceEntries_id == null) {
			if (other.serviceEntries_id != null)
				return false;
		} else if (!serviceEntries_id.equals(other.serviceEntries_id))
			return false;
		if (servicetaskid == null) {
			if (other.servicetaskid != null)
				return false;
		} else if (!servicetaskid.equals(other.servicetaskid))
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
		builder.append("ServiceEntriesTasksToLabor [laberid=");
		builder.append(laberid);
		builder.append(", labername=");
		builder.append(labername);
		builder.append(", laberhourscost=");
		builder.append(laberhourscost);
		builder.append(", eachlabercost=");
		builder.append(eachlabercost);
		builder.append(", totalcost=");
		builder.append(totalcost);
		builder.append(", serviceEntries_id=");
		builder.append(serviceEntries_id);
		builder.append(", servicetaskid=");
		builder.append(servicetaskid);
		builder.append("]");
		return builder.toString();
	}

}