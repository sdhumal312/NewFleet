package org.fleetopgroup.persistence.dto;

import javax.persistence.Column;

import org.fleetopgroup.web.util.Utility;

public class ServiceEntriesTasksToPartsDto {

	private Long serviceEntriesTaskto_partid;

	private Long partid;

	private String partname;

	private String partnumber;

	private String parttype;

	private short partTypeId;

	private Double quantity;

	private Double parteachcost;

	private Double partdisc;

	private String parttaxtype;

	private Double parttax;

	private Double totalcost;

	private Long serviceEntries_id;

	private Long servicetaskid;

	private Integer companyId;

	private boolean markForDelete;;
	
	private Long partCount;
	
	private double totalValuePartConsumed;

	private Double partIGST;

	private Double partCGST;
	
	private Double partSGST;
	
	private Double taxInAmount;
	public ServiceEntriesTasksToPartsDto() {
		super();
	}

	public ServiceEntriesTasksToPartsDto(Long partid, Double quantity, Double parteachcost, Double partdisc,
			String parttaxtype, Double parttax, Double totalcost, Long serviceEntries_id, Long servicetaskid,
			Integer companyId) {
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

	public String getPartname() {
		return partname;
	}

	public void setPartname(String partname) {
		this.partname = partname;
	}

	public String getPartnumber() {
		return partnumber;
	}

	public void setPartnumber(String partnumber) {
		this.partnumber = partnumber;
	}

	public String getParttype() {
		return parttype;
	}

	public void setParttype(String parttype) {
		this.parttype = parttype;
	}

	public short getPartTypeId() {
		return partTypeId;
	}

	public void setPartTypeId(short partTypeId) {
		this.partTypeId = partTypeId;
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
		this.quantity = Utility.round(quantity, 2);
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
		this.parteachcost = Utility.round(parteachcost,2);
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
		this.partdisc = Utility.round(partdisc, 2);
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
		this.parttax =Utility.round(parttax, 2);
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
		this.totalcost =Utility.round( totalcost, 2);
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

	public long getPartCount() {
		return partCount;
	}

	public void setPartCount(long partCount) {
		this.partCount = partCount;
	}

	public double getTotalValuePartConsumed() {
		return totalValuePartConsumed;
	}

	public void setTotalValuePartConsumed(double totalValuePartConsumed) {
		this.totalValuePartConsumed = Utility.round( totalValuePartConsumed, 2);
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

	public void setPartCount(Long partCount) {
		this.partCount = partCount;
	}

	
	public Double getTaxInAmount() {
		return taxInAmount;
	}

	public void setTaxInAmount(Double taxInAmount) {
		this.taxInAmount = taxInAmount;
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
		ServiceEntriesTasksToPartsDto other = (ServiceEntriesTasksToPartsDto) obj;
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
		builder.append(", partCount=");
		builder.append(partCount);
		builder.append(", totalValuePartConsumed=");
		builder.append(totalValuePartConsumed);
		builder.append(" partIGST=");
		builder.append(partIGST);
		builder.append(" partCGST=");
		builder.append(partCGST);
		builder.append(" partSGST=");
		builder.append(partSGST);
		builder.append(" taxInAmount=");
		builder.append(taxInAmount);
		builder.append("]");
		return builder.toString();
	}

	

}