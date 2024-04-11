package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 *
 *
 */



public class ServiceEntriesTasksDto{

	private Long servicetaskid;
	private String service_typetask;
	private String service_subtypetask;
	private Integer mark_complete;
	private Double totalpart_cost;
	private Double totallaber_cost;
	private Double totaltask_cost;
	private Integer service_subtypetask_id;
	private Integer vid;
	private Long serviceEntries_id;
	private Integer service_typetaskId;
	private long service_id;
	private Long service_Number;
	private String	taskRemark;
	
	//private ServiceEntries serviceentries;

	public ServiceEntriesTasksDto() {

	}

	public ServiceEntriesTasksDto(String service_typetask, String service_subtypetask) {
		super();
		this.service_typetask = service_typetask;
		this.service_subtypetask = service_subtypetask;
	}

	/**
	 * @return the servicetaskid
	 */
	public Long getServicetaskid() {
		return servicetaskid;
	}

	/**
	 * @param servicetaskid the servicetaskid to set
	 */
	public void setServicetaskid(Long servicetaskid) {
		this.servicetaskid = servicetaskid;
	}

	/**
	 * @return the service_typetask
	 */
	public String getService_typetask() {
		return service_typetask;
	}

	/**
	 * @param service_typetask the service_typetask to set
	 */
	public void setService_typetask(String service_typetask) {
		this.service_typetask = service_typetask;
	}

	/**
	 * @return the service_subtypetask
	 */
	public String getService_subtypetask() {
		return service_subtypetask;
	}

	/**
	 * @param service_subtypetask the service_subtypetask to set
	 */
	public void setService_subtypetask(String service_subtypetask) {
		this.service_subtypetask = service_subtypetask;
	}

	/**
	 * @return the mark_complete
	 */
	public Integer getMark_complete() {
		return mark_complete;
	}

	/**
	 * @param mark_complete the mark_complete to set
	 */
	public void setMark_complete(Integer mark_complete) {
		this.mark_complete = mark_complete;
	}

	/**
	 * @return the totalpart_cost
	 */
	public Double getTotalpart_cost() {
		return totalpart_cost;
	}

	/**
	 * @param totalpart_cost the totalpart_cost to set
	 */
	public void setTotalpart_cost(Double totalpart_cost) {
		this.totalpart_cost = Utility.round(totalpart_cost, 2);
	}

	/**
	 * @return the totallaber_cost
	 */
	public Double getTotallaber_cost() {
		return totallaber_cost;
	}

	/**
	 * @param totallaber_cost the totallaber_cost to set
	 */
	public void setTotallaber_cost(Double totallaber_cost) {
		this.totallaber_cost = Utility.round(totallaber_cost, 2);
	}

	/**
	 * @return the totaltask_cost
	 */
	public Double getTotaltask_cost() {
		return totaltask_cost;
	}

	/**
	 * @param totaltask_cost the totaltask_cost to set
	 */
	public void setTotaltask_cost(Double totaltask_cost) {
		this.totaltask_cost = Utility.round(totaltask_cost, 2);
	}

	
	

	public Integer getService_subtypetask_id() {
		return service_subtypetask_id;
	}

	public void setService_subtypetask_id(Integer service_subtypetask_id) {
		this.service_subtypetask_id = service_subtypetask_id;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Long getServiceEntries_id() {
		return serviceEntries_id;
	}

	public void setServiceEntries_id(Long serviceEntries_id) {
		this.serviceEntries_id = serviceEntries_id;
	}

	/**
	 * @return the service_typetaskId
	 */
	public Integer getService_typetaskId() {
		return service_typetaskId;
	}

	/**
	 * @param service_typetaskId the service_typetaskId to set
	 */
	public void setService_typetaskId(Integer service_typetaskId) {
		this.service_typetaskId = service_typetaskId;
	}

	public long getService_id() {
		return service_id;
	}

	public void setService_id(long service_id) {
		this.service_id = service_id;
	}

	public Long getService_Number() {
		return service_Number;
	}

	public void setService_Number(Long service_Number) {
		this.service_Number = service_Number;
	}

	public String getTaskRemark() {
		return taskRemark;
	}

	public void setTaskRemark(String taskRemark) {
		this.taskRemark = taskRemark;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ServiceEntriesTasksDto [servicetaskid=");
		builder.append(servicetaskid);
		builder.append(", service_typetask=");
		builder.append(service_typetask);
		builder.append(", service_subtypetask=");
		builder.append(service_subtypetask);
		builder.append(", mark_complete=");
		builder.append(mark_complete);
		builder.append(", totalpart_cost=");
		builder.append(totalpart_cost);
		builder.append(", totallaber_cost=");
		builder.append(totallaber_cost);
		builder.append(", totaltask_cost=");
		builder.append(totaltask_cost);
		builder.append(", service_id=");
		builder.append(service_id);
		builder.append(", service_Number=");
		builder.append(service_Number);
		builder.append("]");
		return builder.toString();
	}
	
	

}