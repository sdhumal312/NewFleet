package org.fleetopgroup.persistence.dto;

/**
 * @author fleetop
 *
 *
 *
 */

import java.util.Date;

public class ServiceReminderDto {
	
	public static final short SERVICE_REMINDER_STATUS_ACTIVE	= 1;
	public static final short SERVICE_REMINDER_STATUS_INACTIVE	= 2;
	
	public static final String SERVICE_REMINDER_STATUS_ACTIVE_NAME		= "ACTIVE";
	public static final String SERVICE_REMINDER_STATUS_INACTIVE_NAME	= "INACTIVE";
	
	public static final short TIME_INTERVAL_PERIOD_DAYS			= 1;
	public static final short TIME_INTERVAL_PERIOD_WEEKS		= 2;
	public static final short TIME_INTERVAL_PERIOD_MONTHS		= 3;
	public static final short TIME_INTERVAL_PERIOD_YEARS		= 4;
	
	public static final String TIME_INTERVAL_PERIOD_DAYS_NAME	= "days";
	public static final String TIME_INTERVAL_PERIOD_WEEKS_NAME	= "weeks";
	public static final String TIME_INTERVAL_PERIOD_MONTHS_NAME	= "months";
	public static final String TIME_INTERVAL_PERIOD_YEARS_NAME	= "years";

	private Long service_id;
	
	private Long service_Number;
	
	private String service_NumberStr;
	
	private Integer vid;

	private String vehicle_registration;

	private String vehicle_Group;

	private String service_type;

	private String service_subtype;

	private Integer meter_interval;

	private Integer vehicle_currentOdometer;

	private Integer meter_serviceodometer;

	private Integer meter_threshold;

	private Integer meter_servicethreshold_odometer;

	private Integer time_interval;

	private String time_intervalperiod;
	
	private short time_intervalperiodId;

	private Date time_interval_currentdate;

	private Date time_servicedate;

	private Integer time_threshold;

	private String time_thresholdperiod;
	
	private short time_thresholdperiodId;

	private Date time_servicethreshold_date;

	private String service_emailnotification;

	private String service_subscribeduser;
	
	private String service_subscribeduser_name;

	private Date last_servicedate;

	private String last_servicecompleldby;

	private Long last_servicecompleldid;

	private Integer service_remider_howtimes;
	
	private String lastServiceDate;

	public String vehicleType;
	
	public Boolean Due;
	
	public String getLastServiceDate() {
		return lastServiceDate;
	}

	public void setLastServiceDate(String lastServiceDate) {
		this.lastServiceDate = lastServiceDate;
	}

	private String createdBy;

	private String lastModifiedBy;
	private String servicestatus;
	private String created;
	private Date createdOn;
	private String lastupdated;
	private Date lastupdatedOn;

	private String diffrent_meter_oddmeter;
	private String diffrent_time_days;
	private String diffenceThrsholdOdometer;
	private long vehicleGroupId;
	private String service_subScribedUserId;
	private Integer serviceTypeId;
	private Integer serviceSubTypeId;
	private short serviceStatusId;
	private Long createdById;
	private Long lastModifiedById;
	private Long last_servicecompleldbyId;
	private String fromDate;
	private String toDate;
	private Integer companyId;
	private short serviceType;
	private String serviceReminderType;
	private Long 	userId;
	private Long 	workOrderId;
	private Long	workOrderNumber;
	private Date	servicedOn;
	private Integer	serviceOdometer;
	private String  threshHoldDate;
	private String  servceDate;
	private String	workOrderCompletedOn;
	private String	workOrderNumberStr;
	private String  companyName;
	private String	vehicleGPSId;
	private Date 	serviceOdometerUpdatedDate;
	private String 	serviceOdometerUpdatedDateStr;
	private String  timeServiceDate;
	private String  timeServiceThresholdDateStr;
	private long    countOfSROnEachVehicle;
	private String  taskAndSchedule;
	private boolean isFirstService;
	private Integer firstMeterInterval;
	private Integer firstTimeInterval;
	private short	firstTimeIntervalType;
	private String	firstTimeIntervalTypeStr;
	private String srNumber;
	private Long	serviceEntriesId;
	private Long	serviceEntriesNumber;
	private Long	dealerServiceEntriesId;
	private Long	dealerServiceEntriesNumber;
	private Long	serviceScheduleId;
	private Long	serviceProgramId;
	private String	serviceSchedule;
	private String	serviceProgram;
	private String	nextDue;
	private String	serviceNumberStr;
	private String	vehicleNumberLink;
	private boolean isSkip;
	private boolean dueSoonOrOverDue;
	private String	isSkipRemark;
	public  String SRBranchName;
	public String  serviceIdsWithSubIds;
	
	/**
	 * @return the service_id
	 */
	public Long getService_id() {
		return service_id;
	}

	/**
	 * @param service_id
	 *            the service_id to set
	 */
	public void setService_id(Long service_id) {
		this.service_id = service_id;
	}

	public Long getService_Number() {
		return service_Number;
	}

	public void setService_Number(Long service_Number) {
		this.service_Number = service_Number;
	}

	/**
	 * @return the vid
	 */
	public Integer getVid() {
		return vid;
	}

	/**
	 * @param vid
	 *            the vid to set
	 */
	public void setVid(Integer vid) {
		this.vid = vid;
	}

	/**
	 * @return the vehicle_registration
	 */
	public String getVehicle_registration() {
		return vehicle_registration;
	}

	/**
	 * @param vehicle_registration
	 *            the vehicle_registration to set
	 */
	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	/**
	 * @return the vehicle_Group
	 */
	public String getVehicle_Group() {
		return vehicle_Group;
	}

	/**
	 * @param vehicle_Group
	 *            the vehicle_Group to set
	 */
	public void setVehicle_Group(String vehicle_Group) {
		this.vehicle_Group = vehicle_Group;
	}

	/**
	 * @return the service_type
	 */
	public String getService_type() {
		return service_type;
	}

	/**
	 * @param service_type
	 *            the service_type to set
	 */
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	/**
	 * @return the service_subtype
	 */
	public String getService_subtype() {
		return service_subtype;
	}

	/**
	 * @param service_subtype
	 *            the service_subtype to set
	 */
	public void setService_subtype(String service_subtype) {
		this.service_subtype = service_subtype;
	}

	/**
	 * @return the meter_interval
	 */
	public Integer getMeter_interval() {
		return meter_interval;
	}

	/**
	 * @param meter_interval
	 *            the meter_interval to set
	 */
	public void setMeter_interval(Integer meter_interval) {
		this.meter_interval = meter_interval;
	}

	/**
	 * @return the vehicle_currentOdometer
	 */
	public Integer getVehicle_currentOdometer() {
		return vehicle_currentOdometer;
	}

	/**
	 * @param vehicle_currentOdometer
	 *            the vehicle_currentOdometer to set
	 */
	public void setVehicle_currentOdometer(Integer vehicle_currentOdometer) {
		this.vehicle_currentOdometer = vehicle_currentOdometer;
	}

	/**
	 * @return the meter_serviceodometer
	 */
	public Integer getMeter_serviceodometer() {
		return meter_serviceodometer;
	}

	/**
	 * @param meter_serviceodometer
	 *            the meter_serviceodometer to set
	 */
	public void setMeter_serviceodometer(Integer meter_serviceodometer) {
		this.meter_serviceodometer = meter_serviceodometer;
	}

	/**
	 * @return the meter_threshold
	 */
	public Integer getMeter_threshold() {
		return meter_threshold;
	}

	/**
	 * @param meter_threshold
	 *            the meter_threshold to set
	 */
	public void setMeter_threshold(Integer meter_threshold) {
		this.meter_threshold = meter_threshold;
	}

	/**
	 * @return the meter_servicethreshold_odometer
	 */
	public Integer getMeter_servicethreshold_odometer() {
		return meter_servicethreshold_odometer;
	}

	/**
	 * @param meter_servicethreshold_odometer
	 *            the meter_servicethreshold_odometer to set
	 */
	public void setMeter_servicethreshold_odometer(Integer meter_servicethreshold_odometer) {
		this.meter_servicethreshold_odometer = meter_servicethreshold_odometer;
	}

	/**
	 * @return the time_interval
	 */
	public Integer getTime_interval() {
		return time_interval;
	}

	/**
	 * @param time_interval
	 *            the time_interval to set
	 */
	public void setTime_interval(Integer time_interval) {
		this.time_interval = time_interval;
	}
	
	

	/**
	 * @return the service_subscribeduser_name
	 */
	public String getService_subscribeduser_name() {
		return service_subscribeduser_name;
	}

	/**
	 * @param service_subscribeduser_name the service_subscribeduser_name to set
	 */
	public void setService_subscribeduser_name(String service_subscribeduser_name) {
		this.service_subscribeduser_name = service_subscribeduser_name;
	}

	/**
	 * @return the time_intervalperiod
	 */
	public String getTime_intervalperiod() {
		return time_intervalperiod;
	}

	/**
	 * @param time_intervalperiod
	 *            the time_intervalperiod to set
	 */
	public void setTime_intervalperiod(String time_intervalperiod) {
		this.time_intervalperiod = time_intervalperiod;
	}

	/**
	 * @return the time_interval_currentdate
	 */
	public Date getTime_interval_currentdate() {
		return time_interval_currentdate;
	}

	/**
	 * @param time_interval_currentdate
	 *            the time_interval_currentdate to set
	 */
	public void setTime_interval_currentdate(Date time_interval_currentdate) {
		this.time_interval_currentdate = time_interval_currentdate;
	}

	/**
	 * @return the time_servicedate
	 */
	public Date getTime_servicedate() {
		return time_servicedate;
	}

	/**
	 * @param time_servicedate
	 *            the time_servicedate to set
	 */
	public void setTime_servicedate(Date time_servicedate) {
		this.time_servicedate = time_servicedate;
	}

	/**
	 * @return the time_threshold
	 */
	public Integer getTime_threshold() {
		return time_threshold;
	}

	/**
	 * @param time_threshold
	 *            the time_threshold to set
	 */
	public void setTime_threshold(Integer time_threshold) {
		this.time_threshold = time_threshold;
	}

	/**
	 * @return the time_thresholdperiod
	 */
	public String getTime_thresholdperiod() {
		return time_thresholdperiod;
	}

	/**
	 * @param time_thresholdperiod
	 *            the time_thresholdperiod to set
	 */
	public void setTime_thresholdperiod(String time_thresholdperiod) {
		this.time_thresholdperiod = time_thresholdperiod;
	}

	/**
	 * @return the time_servicethreshold_date
	 */
	public Date getTime_servicethreshold_date() {
		return time_servicethreshold_date;
	}

	/**
	 * @param time_servicethreshold_date
	 *            the time_servicethreshold_date to set
	 */
	public void setTime_servicethreshold_date(Date time_servicethreshold_date) {
		this.time_servicethreshold_date = time_servicethreshold_date;
	}

	/**
	 * @return the service_emailnotification
	 */
	public String getService_emailnotification() {
		return service_emailnotification;
	}

	/**
	 * @param service_emailnotification
	 *            the service_emailnotification to set
	 */
	public void setService_emailnotification(String service_emailnotification) {
		this.service_emailnotification = service_emailnotification;
	}

	/**
	 * @return the service_subscribeduser
	 */
	public String getService_subscribeduser() {
		return service_subscribeduser;
	}

	/**
	 * @param service_subscribeduser
	 *            the service_subscribeduser to set
	 */
	public void setService_subscribeduser(String service_subscribeduser) {
		this.service_subscribeduser = service_subscribeduser;
	}

	/**
	 * @return the last_servicedate
	 */
	public Date getLast_servicedate() {
		return last_servicedate;
	}

	/**
	 * @param last_servicedate
	 *            the last_servicedate to set
	 */
	public void setLast_servicedate(Date last_servicedate) {
		this.last_servicedate = last_servicedate;
	}

	/**
	 * @return the last_servicecompleldby
	 */
	public String getLast_servicecompleldby() {
		return last_servicecompleldby;
	}

	/**
	 * @param last_servicecompleldby
	 *            the last_servicecompleldby to set
	 */
	public void setLast_servicecompleldby(String last_servicecompleldby) {
		this.last_servicecompleldby = last_servicecompleldby;
	}

	/**
	 * @return the last_servicecompleldid
	 */
	public Long getLast_servicecompleldid() {
		return last_servicecompleldid;
	}

	/**
	 * @param last_servicecompleldid
	 *            the last_servicecompleldid to set
	 */
	public void setLast_servicecompleldid(Long last_servicecompleldid) {
		this.last_servicecompleldid = last_servicecompleldid;
	}

	/**
	 * @return the service_remider_howtimes
	 */
	public Integer getService_remider_howtimes() {
		return service_remider_howtimes;
	}

	/**
	 * @param service_remider_howtimes
	 *            the service_remider_howtimes to set
	 */
	public void setService_remider_howtimes(Integer service_remider_howtimes) {
		this.service_remider_howtimes = service_remider_howtimes;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the status
	 */
	public String getServicestatus() {
		return servicestatus;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setServicestatus(String servicestatus) {
		this.servicestatus = servicestatus;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public String getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(String lastupdated) {
		this.lastupdated = lastupdated;
	}

	/**
	 * @return the diffrent_meter_oddmeter
	 */
	public String getDiffrent_meter_oddmeter() {
		return diffrent_meter_oddmeter;
	}

	/**
	 * @param diffrent_meter_oddmeter
	 *            the diffrent_meter_oddmeter to set
	 */
	public void setDiffrent_meter_oddmeter(String diffrent_meter_oddmeter) {
		this.diffrent_meter_oddmeter = diffrent_meter_oddmeter;
	}

	/**
	 * @return the diffrent_time_days
	 */
	public String getDiffrent_time_days() {
		return diffrent_time_days;
	}

	/**
	 * @param diffrent_time_days
	 *            the diffrent_time_days to set
	 */
	public void setDiffrent_time_days(String diffrent_time_days) {
		this.diffrent_time_days = diffrent_time_days;
	}

	/**
	 * @return the diffenceThrsholdOdometer
	 */
	public String getDiffenceThrsholdOdometer() {
		return diffenceThrsholdOdometer;
	}

	/**
	 * @param diffenceThrsholdOdometer the diffenceThrsholdOdometer to set
	 */
	public void setDiffenceThrsholdOdometer(String diffenceThrsholdOdometer) {
		this.diffenceThrsholdOdometer = diffenceThrsholdOdometer;
	}

	/**
	 * @return the vehicleGroupId
	 */
	public long getVehicleGroupId() {
		return vehicleGroupId;
	}

	/**
	 * @param vehicleGroupId the vehicleGroupId to set
	 */
	public void setVehicleGroupId(long vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
	}

	/**
	 * @return the service_subScribedUserId
	 */
	public String getService_subScribedUserId() {
		return service_subScribedUserId;
	}

	/**
	 * @param service_subScribedUserId the service_subScribedUserId to set
	 */
	public void setService_subScribedUserId(String service_subScribedUserId) {
		this.service_subScribedUserId = service_subScribedUserId;
	}

	/**
	 * @return the serviceTypeId
	 */
	public Integer getServiceTypeId() {
		return serviceTypeId;
	}

	/**
	 * @param serviceTypeId the serviceTypeId to set
	 */
	public void setServiceTypeId(Integer serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	/**
	 * @return the serviceSubTypeId
	 */
	public Integer getServiceSubTypeId() {
		return serviceSubTypeId;
	}

	/**
	 * @param serviceSubTypeId the serviceSubTypeId to set
	 */
	public void setServiceSubTypeId(Integer serviceSubTypeId) {
		this.serviceSubTypeId = serviceSubTypeId;
	}

	/**
	 * @return the serviceStatusId
	 */
	public short getServiceStatusId() {
		return serviceStatusId;
	}

	/**
	 * @param serviceStatusId the serviceStatusId to set
	 */
	public void setServiceStatusId(short serviceStatusId) {
		this.serviceStatusId = serviceStatusId;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getServiceOdometerUpdatedDateStr() {
		return serviceOdometerUpdatedDateStr;
	}

	public void setServiceOdometerUpdatedDateStr(String serviceOdometerUpdatedDateStr) {
		this.serviceOdometerUpdatedDateStr = serviceOdometerUpdatedDateStr;
	}

	public String getTimeServiceDate() {
		return timeServiceDate;
	}

	public void setTimeServiceDate(String timeServiceDate) {
		this.timeServiceDate = timeServiceDate;
	}

	public String getTimeServiceThresholdDateStr() {
		return timeServiceThresholdDateStr;
	}

	public void setTimeServiceThresholdDateStr(String timeServiceThresholdDateStr) {
		this.timeServiceThresholdDateStr = timeServiceThresholdDateStr;
	}

	public long getCountOfSROnEachVehicle() {
		return countOfSROnEachVehicle;
	}

	public void setCountOfSROnEachVehicle(long countOfSROnEachVehicle) {
		this.countOfSROnEachVehicle = countOfSROnEachVehicle;
	}

	public Long getServiceEntriesId() {
		return serviceEntriesId;
	}

	public void setServiceEntriesId(Long serviceEntriesId) {
		this.serviceEntriesId = serviceEntriesId;
	}
 
	public Long getServiceEntriesNumber() {
		return serviceEntriesNumber;
	}

	public void setServiceEntriesNumber(Long serviceEntriesNumber) {
		this.serviceEntriesNumber = serviceEntriesNumber;
	}

	public static String getServiceReminderStatus(short status) throws Exception{
		String statusString		=	null;
		switch (status) {
		  case SERVICE_REMINDER_STATUS_ACTIVE:
			  statusString	= SERVICE_REMINDER_STATUS_ACTIVE_NAME;
		        break;
		  case SERVICE_REMINDER_STATUS_INACTIVE: 
			  statusString	= SERVICE_REMINDER_STATUS_INACTIVE_NAME;
		        break;
		 
		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	}
	
	public static String getTimeInterValName(short status) throws Exception{
		String statusString		=	null;
		switch (status) {
		  case TIME_INTERVAL_PERIOD_DAYS:
			  statusString	= TIME_INTERVAL_PERIOD_DAYS_NAME;
		        break;
		  case TIME_INTERVAL_PERIOD_WEEKS: 
			  statusString	= TIME_INTERVAL_PERIOD_WEEKS_NAME;
		        break;
		  case TIME_INTERVAL_PERIOD_MONTHS: 
			  statusString	= TIME_INTERVAL_PERIOD_MONTHS_NAME;
		        break;
		  case TIME_INTERVAL_PERIOD_YEARS: 
			  statusString	= TIME_INTERVAL_PERIOD_YEARS_NAME;
		        break;
		 
		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	}

	/**
	 * @return the time_intervalperiodId
	 */
	public short getTime_intervalperiodId() {
		return time_intervalperiodId;
	}

	/**
	 * @param time_intervalperiodId the time_intervalperiodId to set
	 */
	public void setTime_intervalperiodId(short time_intervalperiodId) {
		this.time_intervalperiodId = time_intervalperiodId;
	}

	/**
	 * @return the time_thresholdperiodId
	 */
	public short getTime_thresholdperiodId() {
		return time_thresholdperiodId;
	}

	/**
	 * @param time_thresholdperiodId the time_thresholdperiodId to set
	 */
	public void setTime_thresholdperiodId(short time_thresholdperiodId) {
		this.time_thresholdperiodId = time_thresholdperiodId;
	}

	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the lastupdatedOn
	 */
	public Date getLastupdatedOn() {
		return lastupdatedOn;
	}

	/**
	 * @param lastupdatedOn the lastupdatedOn to set
	 */
	public void setLastupdatedOn(Date lastupdatedOn) {
		this.lastupdatedOn = lastupdatedOn;
	}

	/**
	 * @return the last_servicecompleldbyId
	 */
	public Long getLast_servicecompleldbyId() {
		return last_servicecompleldbyId;
	}

	/**
	 * @param last_servicecompleldbyId the last_servicecompleldbyId to set
	 */
	public void setLast_servicecompleldbyId(Long last_servicecompleldbyId) {
		this.last_servicecompleldbyId = last_servicecompleldbyId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public short getServiceType() {
		return serviceType;
	}

	public void setServiceType(short serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceReminderType() {
		return serviceReminderType;
	}

	public void setServiceReminderType(String serviceReminderType) {
		this.serviceReminderType = serviceReminderType;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Long getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}

	public Long getWorkOrderNumber() {
		return workOrderNumber;
	}

	public void setWorkOrderNumber(Long workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}

	public Date getServicedOn() {
		return servicedOn;
	}

	public void setServicedOn(Date servicedOn) {
		this.servicedOn = servicedOn;
	}

	public Integer getServiceOdometer() {
		return serviceOdometer;
	}

	public void setServiceOdometer(Integer serviceOdometer) {
		this.serviceOdometer = serviceOdometer;
	}

	public String getThreshHoldDate() {
		return threshHoldDate;
	}

	public void setThreshHoldDate(String threshHoldDate) {
		this.threshHoldDate = threshHoldDate;
	}

	public String getServceDate() {
		return servceDate;
	}

	public void setServceDate(String servceDate) {
		this.servceDate = servceDate;
	}

	public String getWorkOrderCompletedOn() {
		return workOrderCompletedOn;
	}

	public void setWorkOrderCompletedOn(String workOrderCompletedOn) {
		this.workOrderCompletedOn = workOrderCompletedOn;
	}

	public String getWorkOrderNumberStr() {
		return workOrderNumberStr;
	}

	public void setWorkOrderNumberStr(String workOrderNumberStr) {
		this.workOrderNumberStr = workOrderNumberStr;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getVehicleGPSId() {
		return vehicleGPSId;
	}

	public void setVehicleGPSId(String vehicleGPSId) {
		this.vehicleGPSId = vehicleGPSId;
	}

	public Date getServiceOdometerUpdatedDate() {
		return serviceOdometerUpdatedDate;
	}

	public void setServiceOdometerUpdatedDate(Date serviceOdometerUpdatedDate) {
		this.serviceOdometerUpdatedDate = serviceOdometerUpdatedDate;
	}

	public String getTaskAndSchedule() {
		return taskAndSchedule;
	}

	public void setTaskAndSchedule(String taskAndSchedule) {
		this.taskAndSchedule = taskAndSchedule;
	}

	public boolean isFirstService() {
		return isFirstService;
	}

	public void setFirstService(boolean isFirstService) {
		this.isFirstService = isFirstService;
	}

	public Integer getFirstMeterInterval() {
		return firstMeterInterval;
	}

	public void setFirstMeterInterval(Integer firstMeterInterval) {
		this.firstMeterInterval = firstMeterInterval;
	}

	public Integer getFirstTimeInterval() {
		return firstTimeInterval;
	}

	public void setFirstTimeInterval(Integer firstTimeInterval) {
		this.firstTimeInterval = firstTimeInterval;
	}

	public short getFirstTimeIntervalType() {
		return firstTimeIntervalType;
	}

	public void setFirstTimeIntervalType(short firstTimeIntervalType) {
		this.firstTimeIntervalType = firstTimeIntervalType;
	}

	public String getFirstTimeIntervalTypeStr() {
		return firstTimeIntervalTypeStr;
	}

	public void setFirstTimeIntervalTypeStr(String firstTimeIntervalTypeStr) {
		this.firstTimeIntervalTypeStr = firstTimeIntervalTypeStr;
	}

	public String getSrNumber() {
		return srNumber;
	}

	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}

	public String getService_NumberStr() {
		return service_NumberStr;
	}

	public void setService_NumberStr(String service_NumberStr) {
		this.service_NumberStr = service_NumberStr;
	}

	public Long getDealerServiceEntriesId() {
		return dealerServiceEntriesId;
	}

	public void setDealerServiceEntriesId(Long dealerServiceEntriesId) {
		this.dealerServiceEntriesId = dealerServiceEntriesId;
	}

	public Long getDealerServiceEntriesNumber() {
		return dealerServiceEntriesNumber;
	}

	public void setDealerServiceEntriesNumber(Long dealerServiceEntriesNumber) {
		this.dealerServiceEntriesNumber = dealerServiceEntriesNumber;
	}

	public Long getServiceScheduleId() {
		return serviceScheduleId;
	}

	public void setServiceScheduleId(Long serviceScheduleId) {
		this.serviceScheduleId = serviceScheduleId;
	}

	public Long getServiceProgramId() {
		return serviceProgramId;
	}

	public void setServiceProgramId(Long serviceProgramId) {
		this.serviceProgramId = serviceProgramId;
	}

	public String getServiceSchedule() {
		return serviceSchedule;
	}

	public void setServiceSchedule(String serviceSchedule) {
		this.serviceSchedule = serviceSchedule;
	}

	public String getServiceProgram() {
		return serviceProgram;
	}

	public void setServiceProgram(String serviceProgram) {
		this.serviceProgram = serviceProgram;
	}

	public String getNextDue() {
		return nextDue;
	}

	public void setNextDue(String nextDue) {
		this.nextDue = nextDue;
	}

	public String getServiceNumberStr() {
		return serviceNumberStr;
	}

	public void setServiceNumberStr(String serviceNumberStr) {
		this.serviceNumberStr = serviceNumberStr;
	}

	public String getVehicleNumberLink() {
		return vehicleNumberLink;
	}

	public void setVehicleNumberLink(String vehicleNumberLink) {
		this.vehicleNumberLink = vehicleNumberLink;
	}

	public boolean isSkip() {
		return isSkip;
	}

	public void setSkip(boolean isSkip) {
		this.isSkip = isSkip;
	}

	public String getIsSkipRemark() {
		return isSkipRemark;
	}

	public void setIsSkipRemark(String isSkipRemark) {
		this.isSkipRemark = isSkipRemark;
	}

	public boolean isDueSoonOrOverDue() {
		return dueSoonOrOverDue;
	}

	public void setDueSoonOrOverDue(boolean dueSoonOrOverDue) {
		this.dueSoonOrOverDue = dueSoonOrOverDue;
	}
	
	

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	
	public String getSRBranchName() {
		return SRBranchName;
	}

	public void setSRBranchName(String sRBranchName) {
		SRBranchName = sRBranchName;
	}
	
	public Boolean getDue() {
		return Due;
	}
	public String getServiceIdsWithSubIds() {
		return serviceIdsWithSubIds;
	}
	public void setDue(Boolean due) {
		Due = due;
	}

	public void setServiceIdsWithSubIds(String serviceIdsWithSubIds) {
		this.serviceIdsWithSubIds = serviceIdsWithSubIds;
	}

	@Override
	public String toString() {
		return "ServiceReminderDto [service_id=" + service_id + ", service_Number=" + service_Number
				+ ", service_NumberStr=" + service_NumberStr + ", vid=" + vid + ", vehicle_registration="
				+ vehicle_registration + ", vehicle_Group=" + vehicle_Group + ", service_type=" + service_type
				+ ", service_subtype=" + service_subtype + ", meter_interval=" + meter_interval
				+ ", vehicle_currentOdometer=" + vehicle_currentOdometer + ", meter_serviceodometer="
				+ meter_serviceodometer + ", meter_threshold=" + meter_threshold + ", meter_servicethreshold_odometer="
				+ meter_servicethreshold_odometer + ", time_interval=" + time_interval + ", time_intervalperiod="
				+ time_intervalperiod + ", time_intervalperiodId=" + time_intervalperiodId
				+ ", time_interval_currentdate=" + time_interval_currentdate + ", time_servicedate=" + time_servicedate
				+ ", time_threshold=" + time_threshold + ", time_thresholdperiod=" + time_thresholdperiod
				+ ", time_thresholdperiodId=" + time_thresholdperiodId + ", time_servicethreshold_date="
				+ time_servicethreshold_date + ", service_emailnotification=" + service_emailnotification
				+ ", service_subscribeduser=" + service_subscribeduser + ", service_subscribeduser_name="
				+ service_subscribeduser_name + ", last_servicedate=" + last_servicedate + ", last_servicecompleldby="
				+ last_servicecompleldby + ", last_servicecompleldid=" + last_servicecompleldid
				+ ", service_remider_howtimes=" + service_remider_howtimes + ", lastServiceDate=" + lastServiceDate
				+ ", vehicleType=" + vehicleType + ", Due=" + Due + ", createdBy=" + createdBy + ", lastModifiedBy="
				+ lastModifiedBy + ", servicestatus=" + servicestatus + ", created=" + created + ", createdOn="
				+ createdOn + ", lastupdated=" + lastupdated + ", lastupdatedOn=" + lastupdatedOn
				+ ", diffrent_meter_oddmeter=" + diffrent_meter_oddmeter + ", diffrent_time_days=" + diffrent_time_days
				+ ", diffenceThrsholdOdometer=" + diffenceThrsholdOdometer + ", vehicleGroupId=" + vehicleGroupId
				+ ", service_subScribedUserId=" + service_subScribedUserId + ", serviceTypeId=" + serviceTypeId
				+ ", serviceSubTypeId=" + serviceSubTypeId + ", serviceStatusId=" + serviceStatusId + ", createdById="
				+ createdById + ", lastModifiedById=" + lastModifiedById + ", last_servicecompleldbyId="
				+ last_servicecompleldbyId + ", fromDate=" + fromDate + ", toDate=" + toDate + ", companyId="
				+ companyId + ", serviceType=" + serviceType + ", serviceReminderType=" + serviceReminderType
				+ ", userId=" + userId + ", workOrderId=" + workOrderId + ", workOrderNumber=" + workOrderNumber
				+ ", servicedOn=" + servicedOn + ", serviceOdometer=" + serviceOdometer + ", threshHoldDate="
				+ threshHoldDate + ", servceDate=" + servceDate + ", workOrderCompletedOn=" + workOrderCompletedOn
				+ ", workOrderNumberStr=" + workOrderNumberStr + ", companyName=" + companyName + ", vehicleGPSId="
				+ vehicleGPSId + ", serviceOdometerUpdatedDate=" + serviceOdometerUpdatedDate
				+ ", serviceOdometerUpdatedDateStr=" + serviceOdometerUpdatedDateStr + ", timeServiceDate="
				+ timeServiceDate + ", timeServiceThresholdDateStr=" + timeServiceThresholdDateStr
				+ ", countOfSROnEachVehicle=" + countOfSROnEachVehicle + ", taskAndSchedule=" + taskAndSchedule
				+ ", isFirstService=" + isFirstService + ", firstMeterInterval=" + firstMeterInterval
				+ ", firstTimeInterval=" + firstTimeInterval + ", firstTimeIntervalType=" + firstTimeIntervalType
				+ ", firstTimeIntervalTypeStr=" + firstTimeIntervalTypeStr + ", srNumber=" + srNumber
				+ ", serviceEntriesId=" + serviceEntriesId + ", serviceEntriesNumber=" + serviceEntriesNumber
				+ ", dealerServiceEntriesId=" + dealerServiceEntriesId + ", dealerServiceEntriesNumber="
				+ dealerServiceEntriesNumber + ", serviceScheduleId=" + serviceScheduleId + ", serviceProgramId="
				+ serviceProgramId + ", serviceSchedule=" + serviceSchedule + ", serviceProgram=" + serviceProgram
				+ ", nextDue=" + nextDue + ", serviceNumberStr=" + serviceNumberStr + ", vehicleNumberLink="
				+ vehicleNumberLink + ", isSkip=" + isSkip + ", dueSoonOrOverDue=" + dueSoonOrOverDue
				+ ", isSkipRemark=" + isSkipRemark + ", SRBranchName=" + SRBranchName + ", serviceIdsWithSubIds="
				+ serviceIdsWithSubIds + "]";
	}

}
