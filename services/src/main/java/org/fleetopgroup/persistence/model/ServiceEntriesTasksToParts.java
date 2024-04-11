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
@Table(name = "ServiceEntriesTasksToParts")
public class ServiceEntriesTasksToParts implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serviceEntriesTaskto_partid")
	private Long serviceEntriesTaskto_partid;

	@Column(name = "partid")
	private Long partid;

	@Column(name = "quantity", length = 10)
	private Double quantity;

	@Column(name = "parteachcost", length = 10)
	private Double parteachcost;

	@Column(name = "partdisc", length = 10)
	private Double partdisc;

	@Column(name = "parttaxtype", length = 10)
	private String parttaxtype;

	@Column(name = "parttax", length = 10)
	private Double parttax;

	@Column(name = "totalcost", length = 10)
	private Double totalcost;

	@Column(name = "serviceEntries_id")
	private Long serviceEntries_id;

	@Column(name = "servicetaskid")
	private Long servicetaskid;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;;

	@Column(name = "partIGST", length = 10)
	private Double partIGST;

	@Column(name = "partCGST", length = 10)
	private Double partCGST;
	
	@Column(name = "partSGST", length = 10)
	private Double partSGST;
	
	public ServiceEntriesTasksToParts() {
		super();
	}

	public ServiceEntriesTasksToParts(Long partid,  Double quantity,
			Double parteachcost, Double partdisc, String parttaxtype, Double parttax, Double totalcost,
			Long serviceEntries_id, Long servicetaskid, Integer companyId) {
		super();
		this.partid = partid;
		this.quantity = quantity;
		this.parteachcost = parteachcost;
		this.partdisc = partdisc;
		this.parttaxtype = parttaxtype;
		this.parttax = parttax;
		this.totalcost = totalcost;
		this.serviceEntries_id = serviceEntries_id;
		this.servicetaskid = servicetaskid;
		this.companyId = companyId;
	}

	/**
	 * @return the serviceEntriesTaskto_partid
	 */
	public Long getServiceEntriesTaskto_partid() {
		return serviceEntriesTaskto_partid;
	}

	/**
	 * @param serviceEntriesTaskto_partid
	 *            the serviceEntriesTaskto_partid to set
	 */
	public void setServiceEntriesTaskto_partid(Long serviceEntriesTaskto_partid) {
		this.serviceEntriesTaskto_partid = serviceEntriesTaskto_partid;
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
	 * @return the partdisc
	 */
	public Double getPartdisc() {
		return partdisc;
	}

	/**
	 * @param partdisc
	 *            the partdisc to set
	 */
	public void setPartdisc(Double partdisc) {
		this.partdisc = partdisc;
	}

	/**
	 * @return the parttaxtype
	 */
	public String getParttaxtype() {
		return parttaxtype;
	}

	/**
	 * @param parttaxtype
	 *            the parttaxtype to set
	 */
	public void setParttaxtype(String parttaxtype) {
		this.parttaxtype = parttaxtype;
	}

	/**
	 * @return the parttax
	 */
	public Double getParttax() {
		return parttax;
	}

	/**
	 * @param parttax
	 *            the parttax to set
	 */
	public void setParttax(Double parttax) {
		this.parttax = parttax;
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
	
	public Double getPartIGST() {
		return partIGST;
	}

	public void setPartIGST(Double partIGST) {
		this.partIGST = partIGST;
	}

	public Double getPartCGST() {
		return partCGST;
	}

	public void setPartCGST(Double partCGST) {
		this.partCGST = partCGST;
	}

	public Double getPartSGST() {
		return partSGST;
	}

	public void setPartSGST(Double partSGST) {
		this.partSGST = partSGST;
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
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
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
		ServiceEntriesTasksToParts other = (ServiceEntriesTasksToParts) obj;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ServiceEntriesTasksToParts [serviceEntriesTaskto_partid=");
		builder.append(serviceEntriesTaskto_partid);
		builder.append(", partid=");
		builder.append(partid);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", parteachcost=");
		builder.append(parteachcost);
		builder.append(", partdisc=");
		builder.append(partdisc);
		builder.append(", parttaxtype=");
		builder.append(parttaxtype);
		builder.append(", parttax=");
		builder.append(parttax);
		builder.append(", totalcost=");
		builder.append(totalcost);
		builder.append(", serviceEntries_id=");
		builder.append(serviceEntries_id);
		builder.append(", servicetaskid=");
		builder.append(servicetaskid);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(" partIGST=");
		builder.append(partIGST);
		builder.append(" partCGST=");
		builder.append(partCGST);
		builder.append(" partSGST=");
		builder.append(partSGST);
		builder.append("]");
		return builder.toString();
	}

}