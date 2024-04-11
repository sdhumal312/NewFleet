package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ServiceProgramSchedules")
public class ServiceProgramSchedules  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serviceScheduleId")
	private Long	serviceScheduleId;
	
	@Column(name = "vehicleServiceProgramId")
	private Long	vehicleServiceProgramId;
	
	@Column(name = "jobTypeId")
	private Integer	jobTypeId;
	
	@Column(name = "jobSubTypeId")
	private Integer jobSubTypeId;
	
	@Column(name = "meterInterval")
	private Integer meterInterval;
	
	@Column(name = "timeInterval")
	private Integer timeInterval;
	
	@Column(name = "meterThreshold")
	private Integer meterThreshold;
	
	@Column(name = "timeThreshold")
	private Integer timeThreshold;
	
	@Column(name = "timeIntervalType")
	private short	timeIntervalType;
	
	@Column(name = "timeThresholdType")
	private short	timeThresholdType;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete")
	private boolean	markForDelete;

	@Column(name = "serviceTypeId")
	private short	serviceTypeId;
	
	@Column(name = "service_subScribedUserId", length = 250)
	private String service_subScribedUserId;

	public String getService_subScribedUserId() {
		return service_subScribedUserId;
	}

	public void setService_subScribedUserId(String service_subScribedUserId) {
		this.service_subScribedUserId = service_subScribedUserId;
	}

	public Long getServiceScheduleId() {
		return serviceScheduleId;
	}

	public void setServiceScheduleId(Long serviceScheduleId) {
		this.serviceScheduleId = serviceScheduleId;
	}

	public Long getVehicleServiceProgramId() {
		return vehicleServiceProgramId;
	}

	public void setVehicleServiceProgramId(Long vehicleServiceProgramId) {
		this.vehicleServiceProgramId = vehicleServiceProgramId;
	}

	public Integer getJobTypeId() {
		return jobTypeId;
	}

	public void setJobTypeId(Integer jobTypeId) {
		this.jobTypeId = jobTypeId;
	}

	public Integer getJobSubTypeId() {
		return jobSubTypeId;
	}

	public void setJobSubTypeId(Integer jobSubTypeId) {
		this.jobSubTypeId = jobSubTypeId;
	}

	public Integer getMeterInterval() {
		return meterInterval;
	}

	public void setMeterInterval(Integer meterInterval) {
		this.meterInterval = meterInterval;
	}

	public Integer getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(Integer timeInterval) {
		this.timeInterval = timeInterval;
	}

	public Integer getMeterThreshold() {
		return meterThreshold;
	}

	public void setMeterThreshold(Integer meterThreshold) {
		this.meterThreshold = meterThreshold;
	}

	public Integer getTimeThreshold() {
		return timeThreshold;
	}

	public void setTimeThreshold(Integer timeThreshold) {
		this.timeThreshold = timeThreshold;
	}

	public short getTimeIntervalType() {
		return timeIntervalType;
	}

	public void setTimeIntervalType(short timeIntervalType) {
		this.timeIntervalType = timeIntervalType;
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

	public short getTimeThresholdType() {
		return timeThresholdType;
	}

	public void setTimeThresholdType(short timeThresholdType) {
		this.timeThresholdType = timeThresholdType;
	}

	public short getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(short serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	
	@Override
	public String toString() {
		return "ServiceProgramSchedules [serviceScheduleId=" + serviceScheduleId + ", vehicleServiceProgramId="
				+ vehicleServiceProgramId + ", jobTypeId=" + jobTypeId + ", jobSubTypeId=" + jobSubTypeId
				+ ", meterInterval=" + meterInterval + ", timeInterval=" + timeInterval + ", meterThreshold="
				+ meterThreshold + ", timeThreshold=" + timeThreshold + ", timeIntervalType=" + timeIntervalType
				+ ", timeThresholdType=" + timeThresholdType + ", companyId=" + companyId + ", markForDelete="
				+ markForDelete + ", serviceTypeId=" + serviceTypeId + ", service_subScribedUserId="
				+ service_subScribedUserId + ", service_subscribeduser_name=" + "]";
	}
	
	
}
