package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DealerServiceReminderHistory")
public class DealerServiceReminderHistory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dealerServiceReminderHistoryId")
	private Long dealerServiceReminderHistoryId;
	
	@Column(name = "service_id", nullable = false)
	private Long service_id;
	
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
	
	@Column(name = "dealerServiceEntriesId", nullable = false)
	private Long dealerServiceEntriesId;
	
	@Column(name = "DSE_CompletedOn")
	private Date DSE_CompletedOn;
	
	@Column(name = "DSE_CompletedById")
	private Long DSE_CompletedById;
	
	@Column(name = "DSE_Odometer")
	private Integer DSE_Odometer;
	
	@Column(name = "vid")
	private Integer	vid;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete")
	private boolean markForDelete;

	public DealerServiceReminderHistory() {
		super();
	}

	public DealerServiceReminderHistory(Long dealerServiceReminderHistoryId, Long service_id, Long service_Number,
			Date serviceThresholdDate, Date serviceDate, Integer serviceTheshHoldOdometer, Integer serviceOdometer,
			Long dealerServiceEntriesId, Date dSE_CompletedOn, Long dSE_CompletedById, Integer dSE_Odometer,
			Integer vid, Integer companyId, boolean markForDelete) {
		super();
		this.dealerServiceReminderHistoryId = dealerServiceReminderHistoryId;
		this.service_id = service_id;
		this.service_Number = service_Number;
		this.serviceThresholdDate = serviceThresholdDate;
		this.serviceDate = serviceDate;
		this.serviceTheshHoldOdometer = serviceTheshHoldOdometer;
		this.serviceOdometer = serviceOdometer;
		this.dealerServiceEntriesId = dealerServiceEntriesId;
		DSE_CompletedOn = dSE_CompletedOn;
		DSE_CompletedById = dSE_CompletedById;
		DSE_Odometer = dSE_Odometer;
		this.vid = vid;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DSE_CompletedById == null) ? 0 : DSE_CompletedById.hashCode());
		result = prime * result + ((DSE_CompletedOn == null) ? 0 : DSE_CompletedOn.hashCode());
		result = prime * result + ((DSE_Odometer == null) ? 0 : DSE_Odometer.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((dealerServiceEntriesId == null) ? 0 : dealerServiceEntriesId.hashCode());
		result = prime * result
				+ ((dealerServiceReminderHistoryId == null) ? 0 : dealerServiceReminderHistoryId.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((serviceDate == null) ? 0 : serviceDate.hashCode());
		result = prime * result + ((serviceOdometer == null) ? 0 : serviceOdometer.hashCode());
		result = prime * result + ((serviceTheshHoldOdometer == null) ? 0 : serviceTheshHoldOdometer.hashCode());
		result = prime * result + ((serviceThresholdDate == null) ? 0 : serviceThresholdDate.hashCode());
		result = prime * result + ((service_Number == null) ? 0 : service_Number.hashCode());
		result = prime * result + ((service_id == null) ? 0 : service_id.hashCode());
		result = prime * result + ((vid == null) ? 0 : vid.hashCode());
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
		DealerServiceReminderHistory other = (DealerServiceReminderHistory) obj;
		if (DSE_CompletedById == null) {
			if (other.DSE_CompletedById != null)
				return false;
		} else if (!DSE_CompletedById.equals(other.DSE_CompletedById))
			return false;
		if (DSE_CompletedOn == null) {
			if (other.DSE_CompletedOn != null)
				return false;
		} else if (!DSE_CompletedOn.equals(other.DSE_CompletedOn))
			return false;
		if (DSE_Odometer == null) {
			if (other.DSE_Odometer != null)
				return false;
		} else if (!DSE_Odometer.equals(other.DSE_Odometer))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (dealerServiceEntriesId == null) {
			if (other.dealerServiceEntriesId != null)
				return false;
		} else if (!dealerServiceEntriesId.equals(other.dealerServiceEntriesId))
			return false;
		if (dealerServiceReminderHistoryId == null) {
			if (other.dealerServiceReminderHistoryId != null)
				return false;
		} else if (!dealerServiceReminderHistoryId.equals(other.dealerServiceReminderHistoryId))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (serviceDate == null) {
			if (other.serviceDate != null)
				return false;
		} else if (!serviceDate.equals(other.serviceDate))
			return false;
		if (serviceOdometer == null) {
			if (other.serviceOdometer != null)
				return false;
		} else if (!serviceOdometer.equals(other.serviceOdometer))
			return false;
		if (serviceTheshHoldOdometer == null) {
			if (other.serviceTheshHoldOdometer != null)
				return false;
		} else if (!serviceTheshHoldOdometer.equals(other.serviceTheshHoldOdometer))
			return false;
		if (serviceThresholdDate == null) {
			if (other.serviceThresholdDate != null)
				return false;
		} else if (!serviceThresholdDate.equals(other.serviceThresholdDate))
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
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getDealerServiceReminderHistoryId() {
		return dealerServiceReminderHistoryId;
	}

	public Long getService_id() {
		return service_id;
	}

	public Long getService_Number() {
		return service_Number;
	}

	public Date getServiceThresholdDate() {
		return serviceThresholdDate;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public Integer getServiceTheshHoldOdometer() {
		return serviceTheshHoldOdometer;
	}

	public Integer getServiceOdometer() {
		return serviceOdometer;
	}

	public Long getDealerServiceEntriesId() {
		return dealerServiceEntriesId;
	}

	public Date getDSE_CompletedOn() {
		return DSE_CompletedOn;
	}

	public Long getDSE_CompletedById() {
		return DSE_CompletedById;
	}

	public Integer getDSE_Odometer() {
		return DSE_Odometer;
	}

	public Integer getVid() {
		return vid;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setDealerServiceReminderHistoryId(Long dealerServiceReminderHistoryId) {
		this.dealerServiceReminderHistoryId = dealerServiceReminderHistoryId;
	}

	public void setService_id(Long service_id) {
		this.service_id = service_id;
	}

	public void setService_Number(Long service_Number) {
		this.service_Number = service_Number;
	}

	public void setServiceThresholdDate(Date serviceThresholdDate) {
		this.serviceThresholdDate = serviceThresholdDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public void setServiceTheshHoldOdometer(Integer serviceTheshHoldOdometer) {
		this.serviceTheshHoldOdometer = serviceTheshHoldOdometer;
	}

	public void setServiceOdometer(Integer serviceOdometer) {
		this.serviceOdometer = serviceOdometer;
	}

	public void setDealerServiceEntriesId(Long dealerServiceEntriesId) {
		this.dealerServiceEntriesId = dealerServiceEntriesId;
	}

	public void setDSE_CompletedOn(Date dSE_CompletedOn) {
		DSE_CompletedOn = dSE_CompletedOn;
	}

	public void setDSE_CompletedById(Long dSE_CompletedById) {
		DSE_CompletedById = dSE_CompletedById;
	}

	public void setDSE_Odometer(Integer dSE_Odometer) {
		DSE_Odometer = dSE_Odometer;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		return "DealerServiceReminderHistory [dealerServiceReminderHistoryId=" + dealerServiceReminderHistoryId
				+ ", service_id=" + service_id + ", service_Number=" + service_Number + ", serviceThresholdDate="
				+ serviceThresholdDate + ", serviceDate=" + serviceDate + ", serviceTheshHoldOdometer="
				+ serviceTheshHoldOdometer + ", serviceOdometer=" + serviceOdometer + ", dealerServiceEntriesId="
				+ dealerServiceEntriesId + ", DSE_CompletedOn=" + DSE_CompletedOn + ", DSE_CompletedById="
				+ DSE_CompletedById + ", DSE_Odometer=" + DSE_Odometer + ", vid=" + vid + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + "]";
	}
	
	

}
