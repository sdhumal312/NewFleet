package org.fleetopgroup.persistence.dto;

public class ServiceProgramSchedulesDto {

	private Long	serviceScheduleId;
	
	private Long	vehicleServiceProgramId;
	
	private Integer	jobTypeId;
	
	private Integer jobSubTypeId;
	
	private Integer meterInterval;
	
	private Integer timeInterval;
	
	private Integer meterThreshold;
	
	private Integer timeThreshold;
	
	private short	timeIntervalType;
	
	private Integer companyId;
	
	private boolean	markForDelete;
	
	private String jobType;
	
	private String  jobSubType;
	
	private String timeIntervalTypeStr;
	
	private String timeThresholdStr;
	
	private short	timeThresholdType;
	
	private String	timeThresholdTypeStr;
	
	private short	serviceTypeId;
	
	private String	serviceType;
	
	private Long serviceReminderCount;

	private String service_subScribedUserId;
	
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

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getJobSubType() {
		return jobSubType;
	}

	public void setJobSubType(String jobSubType) {
		this.jobSubType = jobSubType;
	}

	public String getTimeIntervalTypeStr() {
		return timeIntervalTypeStr;
	}

	public void setTimeIntervalTypeStr(String timeIntervalTypeStr) {
		this.timeIntervalTypeStr = timeIntervalTypeStr;
	}

	public String getTimeThresholdStr() {
		return timeThresholdStr;
	}

	public short getTimeThresholdType() {
		return timeThresholdType;
	}

	public void setTimeThresholdType(short timeThresholdType) {
		this.timeThresholdType = timeThresholdType;
	}

	public String getTimeThresholdTypeStr() {
		return timeThresholdTypeStr;
	}

	public void setTimeThresholdTypeStr(String timeThresholdTypeStr) {
		this.timeThresholdTypeStr = timeThresholdTypeStr;
	}

	public void setTimeThresholdStr(String timeThresholdStr) {
		this.timeThresholdStr = timeThresholdStr;
	}

	public short getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(short serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public Long getServiceReminderCount() {
		return serviceReminderCount;
	}

	public void setServiceReminderCount(Long serviceReminderCount) {
		this.serviceReminderCount = serviceReminderCount;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getService_subScribedUserId() {
		return service_subScribedUserId;
	}

	public void setService_subScribedUserId(String service_subScribedUserId) {
		this.service_subScribedUserId = service_subScribedUserId;
	}
	
	
}
