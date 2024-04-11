package org.fleetopgroup.persistence.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MobileAppUserRegistration")
public class MobileAppUserRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mobileAppUserRegistrationId")
	private Long mobileAppUserRegistrationId;
	
	@Column(name = "userId",nullable = false)
	private Long userId;
	
	@Column(name = "companyId",nullable = false)
	private Long companyId;
	
	@Column(name = "macAddress")
	private String macAddress;
	
	@Column(name = "ipAddress")
	private String ipAddress;
	
	
	@Column(name = "mobileNumber",nullable = false)
	private String mobileNumber;
	
	@Column(name = "deviceName")
	private String deviceName;
	
	@Column(name = "mobileUniqueId",nullable = false)
	private String mobileUniqueId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "emailId")
	private String emailId;
	
	@Column(name = "userAddress")
	private String userAddress;
	
	@Column(name = "registrationDateTime")
	private Timestamp registrationDateTime;

	@Column(name = "activeOrDeactive")
	private boolean activeOrDeactive;
	
	@Column(name = "tokenForNotification")
	private String tokenForNotification;
	
	public Long getMobileAppUserRegistrationId() {
		return mobileAppUserRegistrationId;
	}
	public void setMobileAppUserRegistrationId(Long mobileAppUserRegistrationId) {
		this.mobileAppUserRegistrationId = mobileAppUserRegistrationId;
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
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getMobileUniqueId() {
		return mobileUniqueId;
	}
	public void setMobileUniqueId(String mobileUniqueId) {
		this.mobileUniqueId = mobileUniqueId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public boolean isActiveOrDeactive() {
		return activeOrDeactive;
	}
	public void setActiveOrDeactive(boolean activeOrDeactive) {
		this.activeOrDeactive = activeOrDeactive;
	}
	public Timestamp getRegistrationDateTime() {
		return registrationDateTime;
	}
	public void setRegistrationDateTime(Timestamp registrationDateTime) {
		this.registrationDateTime = registrationDateTime;
	}
	public String getTokenForNotification() {
		return tokenForNotification;
	}
	public void setTokenForNotification(String tokenForNotification) {
		this.tokenForNotification = tokenForNotification;
	}
	@Override
	public String toString() {
		return "MobileAppUserRegistration [mobileAppUserRegistrationId=" + mobileAppUserRegistrationId + ", userId="
				+ userId + ", companyId=" + companyId + ", macAddress=" + macAddress + ", ipAddress=" + ipAddress
				+ ", mobileNumber=" + mobileNumber + ", deviceName=" + deviceName + ", mobileUniqueId=" + mobileUniqueId
				+ ", name=" + name + ", emailId=" + emailId + ", userAddress=" + userAddress + ", registrationDateTime="
				+ registrationDateTime + ", activeOrDeactive=" + activeOrDeactive + "]";
	}
	
	
}