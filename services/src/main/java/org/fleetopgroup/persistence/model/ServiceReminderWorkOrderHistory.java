package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ServiceReminderWorkOrderHistory")
public class ServiceReminderWorkOrderHistory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serviceReminderWorkOrderHistoryId")
	private Long serviceReminderWorkOrderHistoryId;
	
	@Column(name = "service_id", nullable = false)
	private Long service_id;
	
	@Column(name = "workOrderId", nullable = false)
	private Long workOrderId;
	
	@Column(name = "service_Number", nullable = false)
	private Long service_Number;
	
	@Column(name = "serviceThresholdDate")
	private Date serviceThresholdDate;
	
	@Column(name = "serviceDate")
	private Date serviceDate;
	
	@Column(name = "serviceTheshHoldOdometer")
	private Integer serviceTheshHoldOdometer;
	
	@Column(name = "serviceOdometer")
	private Integer serviceOdometer;
	
	@Column(name = "workOrderCompletedOn")
	private Date workOrderCompletedOn;
	
	@Column(name = "workOrderCompletedById")
	private Long workOrderCompletedById;
	
	@Column(name = "workOrderOdometer")
	private Integer workOrderOdometer;
	
	@Column(name = "vid")
	private Integer	vid;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete")
	private boolean markForDelete;
	
	public ServiceReminderWorkOrderHistory() {
		super();
	}

	public ServiceReminderWorkOrderHistory(Long serviceReminderWorkOrderHistoryId, Long service_id, Long workOrderId,
			Long service_Number, Timestamp serviceThresholdDate, Timestamp serviceDate,
			Integer serviceTheshHoldOdometer, Integer serviceOdometer, Timestamp workOrderCompletedOn,
			Long workOrderCompletedById, Integer workOrderOdometer) {
		super();
		this.serviceReminderWorkOrderHistoryId = serviceReminderWorkOrderHistoryId;
		this.service_id = service_id;
		this.workOrderId = workOrderId;
		this.service_Number = service_Number;
		this.serviceThresholdDate = serviceThresholdDate;
		this.serviceDate = serviceDate;
		this.serviceTheshHoldOdometer = serviceTheshHoldOdometer;
		this.serviceOdometer = serviceOdometer;
		this.workOrderCompletedOn = workOrderCompletedOn;
		this.workOrderCompletedById = workOrderCompletedById;
		this.workOrderOdometer = workOrderOdometer;
	}

	
	
	
	public Long getServiceReminderWorkOrderHistoryId() {
		return serviceReminderWorkOrderHistoryId;
	}

	public void setServiceReminderWorkOrderHistoryId(Long serviceReminderWorkOrderHistoryId) {
		this.serviceReminderWorkOrderHistoryId = serviceReminderWorkOrderHistoryId;
	}

	public Long getService_id() {
		return service_id;
	}

	public void setService_id(Long service_id) {
		this.service_id = service_id;
	}

	public Long getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}

	public Long getService_Number() {
		return service_Number;
	}

	public void setService_Number(Long service_Number) {
		this.service_Number = service_Number;
	}

	public Date getServiceThresholdDate() {
		return serviceThresholdDate;
	}

	public void setServiceThresholdDate(Date serviceThresholdDate) {
		this.serviceThresholdDate = serviceThresholdDate;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public Integer getServiceTheshHoldOdometer() {
		return serviceTheshHoldOdometer;
	}

	public void setServiceTheshHoldOdometer(Integer serviceTheshHoldOdometer) {
		this.serviceTheshHoldOdometer = serviceTheshHoldOdometer;
	}

	public Integer getServiceOdometer() {
		return serviceOdometer;
	}

	public void setServiceOdometer(Integer serviceOdometer) {
		this.serviceOdometer = serviceOdometer;
	}

	public Date getWorkOrderCompletedOn() {
		return workOrderCompletedOn;
	}

	public void setWorkOrderCompletedOn(Date workOrderCompletedOn) {
		this.workOrderCompletedOn = workOrderCompletedOn;
	}

	public Long getWorkOrderCompletedById() {
		return workOrderCompletedById;
	}

	public void setWorkOrderCompletedById(Long workOrderCompletedById) {
		this.workOrderCompletedById = workOrderCompletedById;
	}

	public Integer getWorkOrderOdometer() {
		return workOrderOdometer;
	}

	public void setWorkOrderOdometer(Integer workOrderOdometer) {
		this.workOrderOdometer = workOrderOdometer;
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

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((serviceReminderWorkOrderHistoryId == null) ? 0 : serviceReminderWorkOrderHistoryId.hashCode());
		result = prime * result + ((service_Number == null) ? 0 : service_Number.hashCode());
		result = prime * result + ((service_id == null) ? 0 : service_id.hashCode());
		result = prime * result + ((workOrderId == null) ? 0 : workOrderId.hashCode());
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
		ServiceReminderWorkOrderHistory other = (ServiceReminderWorkOrderHistory) obj;
		if (serviceReminderWorkOrderHistoryId == null) {
			if (other.serviceReminderWorkOrderHistoryId != null)
				return false;
		} else if (!serviceReminderWorkOrderHistoryId.equals(other.serviceReminderWorkOrderHistoryId))
			return false;
		if (service_Number == null) {
			if (other.service_Number != null)
				return false;
		} else if (!service_Number.equals(other.service_Number))
			return false;
		if (service_id == null) {
			if (other.service_id != null)
				return false;
		} else if (!service_id.equals(other.service_id))
			return false;
		if (workOrderId == null) {
			if (other.workOrderId != null)
				return false;
		} else if (!workOrderId.equals(other.workOrderId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServiceReminderWorkOrderHistory [serviceReminderWorkOrderHistoryId=" + serviceReminderWorkOrderHistoryId
				+ ", service_id=" + service_id + ", workOrderId=" + workOrderId + ", service_Number=" + service_Number
				+ ", serviceThresholdDate=" + serviceThresholdDate + ", serviceDate=" + serviceDate
				+ ", serviceTheshHoldOdometer=" + serviceTheshHoldOdometer + ", serviceOdometer=" + serviceOdometer
				+ ", workOrderCompletedOn=" + workOrderCompletedOn + ", workOrderCompletedById="
				+ workOrderCompletedById + ", workOrderOdometer=" + workOrderOdometer + "]";
	}
	
	

}
