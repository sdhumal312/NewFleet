package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

public class WorkOrdersTasksToLaborDto {

	private Long workordertaskto_laberid;
	
	private Integer laberid;

	private String labername;

	private Double laberhourscost;

	private Double eachlabercost;

	private Double laberdiscount;

	private Double labertax;

	private Double totalcost;

	private Long workorders_id;

	private Long workordertaskid;
	
	private Integer companyId;
	
	private boolean markForDelete;


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
		this.laberhourscost = Utility.round(laberhourscost, 2);
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
		this.eachlabercost = Utility.round(eachlabercost, 2);
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
		this.totalcost = Utility.round(totalcost, 2);
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
		this.laberdiscount = Utility.round(laberdiscount, 2);
	}

	public Double getLabertax() {
		return labertax;
	}

	public void setLabertax(Double labertax) {
		this.labertax = Utility.round(labertax,2);
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
