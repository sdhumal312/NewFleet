package org.fleetopgroup.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MobileNotifications")
public class MobileNotifications {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mobileNotificationId")
	private Long    mobileNotificationId;
	
	@Column(name = "userId",nullable = false)
	private Long userId;
	
	@Column(name = "companyId",nullable = false)
	private Long companyId;

	@Column(name = "notification")
	private String notification;
	
	@Column(name = "notificationModuleIdentifier")
	private short notificationModuleIdentifier;
	
	@Column(name = "isViewedNotification")
	private boolean isViewedNotification;
	
	@Column(name = "markForDelete")
	private boolean markForDelete;

	public Long getMobileNotificationId() {
		return mobileNotificationId;
	}

	public void setMobileNotificationId(Long mobileNotificationId) {
		this.mobileNotificationId = mobileNotificationId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public short getNotificationModuleIdentifier() {
		return notificationModuleIdentifier;
	}

	public void setNotificationModuleIdentifier(short notificationModuleIdentifier) {
		this.notificationModuleIdentifier = notificationModuleIdentifier;
	}

	public boolean isViewedNotification() {
		return isViewedNotification;
	}

	public void setViewedNotification(boolean isViewedNotification) {
		this.isViewedNotification = isViewedNotification;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
}
