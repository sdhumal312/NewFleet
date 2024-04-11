package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "serviceReminderHistory")
public class ServiceReminderHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sr_historyId")
	private Long sr_historyId;
	
	@Column(name = "service_reminderId", nullable = false)
	private Long service_reminderId;
	
	@Column(name = "workorders_id")
	private Long workorders_id;

	@Column(name = "dealerServiceEntriesId")
	private Long dealerServiceEntriesId;

	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "serviceType", nullable = false)
	private short serviceType;
	
	@Column(name = "serviceEntries_id")
	private Long serviceEntries_id;
	
	@Basic(optional = false)
	@Column(name = "serviceDueDate", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date serviceDueDate;
	
	
	public ServiceReminderHistory(Long sr_historyId, Long service_reminderId, Long workorders_id,
			Long dealerServiceEntriesId, Integer companyId, Date created, boolean markForDelete, short serviceType) {
		super();
		this.sr_historyId = sr_historyId;
		this.service_reminderId = service_reminderId;
		this.workorders_id = workorders_id;
		this.dealerServiceEntriesId = dealerServiceEntriesId;
		this.companyId = companyId;
		this.created = created;
		this.markForDelete = markForDelete;
		this.serviceType = serviceType;
		this.serviceEntries_id = serviceEntries_id;
	}

	public ServiceReminderHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getSr_historyId() {
		return sr_historyId;
	}

	public void setSr_historyId(Long sr_historyId) {
		this.sr_historyId = sr_historyId;
	}

	public Long getService_reminderId() {
		return service_reminderId;
	}

	public Long getServiceEntries_id() {
		return serviceEntries_id;
	}

	public void setServiceEntries_id(Long serviceEntries_id) {
		this.serviceEntries_id = serviceEntries_id;
	}

	public void setService_reminderId(Long service_reminderId) {
		this.service_reminderId = service_reminderId;
	}

	public Long getWorkorders_id() {
		return workorders_id;
	}

	public void setWorkorders_id(Long workorders_id) {
		this.workorders_id = workorders_id;
	}

	public Long getDealerServiceEntriesId() {
		return dealerServiceEntriesId;
	}

	public void setDealerServiceEntriesId(Long dealerServiceEntriesId) {
		this.dealerServiceEntriesId = dealerServiceEntriesId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public short getServiceType() {
		return serviceType;
	}

	public void setServiceType(short serviceType) {
		this.serviceType = serviceType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public Date getServiceDueDate() {
		return serviceDueDate;
	}

	public void setServiceDueDate(Date serviceDueDate) {
		this.serviceDueDate = serviceDueDate;
	}

	@Override
	public String toString() {
		return "ServiceReminderHistory [sr_historyId=" + sr_historyId + ", service_reminderId=" + service_reminderId
				+ ", workorders_id=" + workorders_id + ", dealerServiceEntriesId=" + dealerServiceEntriesId
				+ ", companyId=" + companyId + ", created=" + created + ", markForDelete=" + markForDelete
				+ ", serviceType=" + serviceType + ", serviceEntries_id=" + serviceEntries_id + ", serviceDueDate="
				+ serviceDueDate + "]";
	}


}