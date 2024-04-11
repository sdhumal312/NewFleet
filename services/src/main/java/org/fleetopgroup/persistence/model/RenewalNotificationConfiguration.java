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
@Table(name = "RenewalNotificationConfiguration")
public class RenewalNotificationConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "renewalConfig_id")
	private Long renewalConfig_id;

	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "createdOn", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	@Column(name = "createdById", nullable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById", nullable = false)
	private Long lastModifiedById;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	@Column(name ="status")
	private Short status;
	
	@Column(name="threshold")
	private Integer threshold;
	
	@Column(name = "companyId", nullable = false)
	private Integer		companyId;
	
	@Column(name = "notificationsToIds")  //comma separated values.
	private String notificationsToIds;

	public RenewalNotificationConfiguration() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public RenewalNotificationConfiguration(Long renewalConfig_id, Date createdOn, Long createdById,
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "RenewalNotificationConfiguration [renewalConfig_id=" + renewalConfig_id + ", createdOn=" + createdOn
				+ ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById + ", markForDelete="
				+ markForDelete + ", status=" + status + ", threshold=" + threshold + ", companyId=" + companyId
				+ ", notificationsToIds=" + notificationsToIds + "]";
	}
	
}
