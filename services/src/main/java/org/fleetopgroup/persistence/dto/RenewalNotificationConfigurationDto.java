package org.fleetopgroup.persistence.dto;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 *
 *
 */ 
public class RenewalNotificationConfigurationDto {

	private Long renewalConfig_id;

	private Date createdOn;
	
	private Long createdById;
	
	private Long lastModifiedById;

	boolean markForDelete;
	
	private Short status;
	
	private Integer threshold;
	
	private Integer	companyId;
	
	private String notificationsToIds;

	public RenewalNotificationConfigurationDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RenewalNotificationConfigurationDto(Long renewalConfig_id, Date createdOn, Long createdById,
			Long lastModifiedById, boolean markForDelete, Short status, Integer threshold, Integer companyId,
			String notificationsToIds) {
		super();
		this.renewalConfig_id = renewalConfig_id;
		this.createdOn = createdOn;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.markForDelete = markForDelete;
		this.status = status;
		this.threshold = threshold;
		this.companyId = companyId;
		this.notificationsToIds = notificationsToIds;
	}

	public Long getRenewalConfig_id() {
		return renewalConfig_id;
	}

	public void setRenewalConfig_id(Long renewalConfig_id) {
		this.renewalConfig_id = renewalConfig_id;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getNotificationsToIds() {
		return notificationsToIds;
	}

	public void setNotificationsToIds(String notificationsToIds) {
		this.notificationsToIds = notificationsToIds;
	}

	@Override
	public String toString() {
		return "RenewalNotificationConfigurationDto [renewalConfig_id=" + renewalConfig_id + ", createdOn=" + createdOn
				+ ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById + ", markForDelete="
				+ markForDelete + ", status=" + status + ", threshold=" + threshold + ", companyId=" + companyId
				+ ", notificationsToIds=" + notificationsToIds + "]";
	}
	
}
