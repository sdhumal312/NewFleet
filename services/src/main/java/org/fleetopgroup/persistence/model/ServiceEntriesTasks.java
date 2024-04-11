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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ServiceEntriesTasks")
public class ServiceEntriesTasks implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "servicetaskid")
	private Long servicetaskid;
	
	@Column(name = "service_typetaskId")
	private Integer service_typetaskId;

	@Column(name = "service_subtypetask_id")
	private Integer service_subtypetask_id;

	@Column(name = "mark_complete", length = 5)
	private Integer mark_complete;

	@Column(name = "totalpart_cost", length = 10)
	private Double totalpart_cost;

	@Column(name = "totallaber_cost", length = 10)
	private Double totallaber_cost;

	@Column(name = "totaltask_cost", length = 10)
	private Double totaltask_cost;
	
	@Column(name = "vid", length = 7)
	private Integer vid;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	@ManyToOne
	@JoinColumn(name = "serviceEntries_id")
	private ServiceEntries ServiceEntries;
	
	@Column(name = "service_id")
	private Long service_id;
	
	@Column(name = "taskRemark")
	private String taskRemark;

	public ServiceEntriesTasks() {
		super();
	}


	public ServiceEntriesTasks(Integer service_typetaskId, Integer service_subtypetask_id) {
		super();
		this.service_typetaskId = service_typetaskId;
		this.service_subtypetask_id = service_subtypetask_id;
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

	
	

	public Integer getService_subtypetask_id() {
		return service_subtypetask_id;
	}

	public void setService_subtypetask_id(Integer service_subtypetask_id) {
		this.service_subtypetask_id = service_subtypetask_id;
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
		this.totalpart_cost = totalpart_cost;
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
		this.totallaber_cost = totallaber_cost;
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
		this.totaltask_cost = totaltask_cost;
	}

	/**
	 * @return the serviceEntries
	 */
	public ServiceEntries getServiceEntries() {
		return ServiceEntries;
	}

	/**
	 * @param serviceEntries the serviceEntries to set
	 */
	public void setServiceEntries(ServiceEntries serviceEntries) {
		ServiceEntries = serviceEntries;
	}

	/**
	 * @return the vid
	 */
	public Integer getVid() {
		return vid;
	}

	/**
	 * @param vid the vid to set
	 */
	public void setVid(Integer vid) {
		this.vid = vid;
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
	
	public Long getService_id() {
		return service_id;
	}

	public void setService_id(Long service_id) {
		this.service_id = service_id;
	}


	public String getTaskRemark() {
		return taskRemark;
	}


	public void setTaskRemark(String taskRemark) {
		this.taskRemark = taskRemark;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((service_subtypetask_id == null) ? 0 : service_subtypetask_id.hashCode());
		result = prime * result + ((servicetaskid == null) ? 0 : servicetaskid.hashCode());
		result = prime * result + ((totallaber_cost == null) ? 0 : totallaber_cost.hashCode());
		result = prime * result + ((totalpart_cost == null) ? 0 : totalpart_cost.hashCode());
		result = prime * result + ((totaltask_cost == null) ? 0 : totaltask_cost.hashCode());
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
		ServiceEntriesTasks other = (ServiceEntriesTasks) obj;
		if (service_subtypetask_id == null) {
			if (other.service_subtypetask_id != null)
				return false;
		} else if (!service_subtypetask_id.equals(other.service_subtypetask_id))
			return false;
		if (servicetaskid == null) {
			if (other.servicetaskid != null)
				return false;
		} else if (!servicetaskid.equals(other.servicetaskid))
			return false;
		if (totallaber_cost == null) {
			if (other.totallaber_cost != null)
				return false;
		} else if (!totallaber_cost.equals(other.totallaber_cost))
			return false;
		if (totalpart_cost == null) {
			if (other.totalpart_cost != null)
				return false;
		} else if (!totalpart_cost.equals(other.totalpart_cost))
			return false;
		if (totaltask_cost == null) {
			if (other.totaltask_cost != null)
				return false;
		} else if (!totaltask_cost.equals(other.totaltask_cost))
			return false;
		return true;
	}


}