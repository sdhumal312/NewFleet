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
@Table(name = "servicereminder")
public class ServiceReminder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "service_id")
	private Long service_id;
	
	@Column(name = "service_Number", nullable = false)
	private Long service_Number;

	@Column(name = "vid", length = 7)
	private Integer vid;
	
	@Column(name = "serviceTypeId")
	private Integer serviceTypeId;

	@Column(name = "serviceSubTypeId")
	private Integer serviceSubTypeId;

	@Column(name = "meter_interval", length = 10)
	private Integer meter_interval;

	@Column(name = "vehicle_currentOdometer", length = 10)
	private Integer vehicle_currentOdometer;

	@Column(name = "meter_serviceodometer", length = 10)
	private Integer meter_serviceodometer;

	@Column(name = "meter_threshold", length = 10)
	private Integer meter_threshold;

	@Column(name = "meter_servicethreshold_odometer", length = 10)
	private Integer meter_servicethreshold_odometer;

	@Column(name = "time_interval", length = 10)
	private Integer time_interval;
	
	@Column(name = "time_intervalperiodId")
	private short time_intervalperiodId;

	@Column(name = "time_interval_currentdate")
	private Date time_interval_currentdate;

	@Column(name = "time_servicedate")
	private Date time_servicedate;

	@Column(name = "time_threshold", length = 10)
	private Integer time_threshold;
	
	@Column(name = "time_thresholdperiodId")
	private short time_thresholdperiodId;

	@Column(name = "time_servicethreshold_date")
	private Date time_servicethreshold_date;

	@Column(name = "service_emailnotification", length = 100)
	private String service_emailnotification;
	
	@Column(name = "service_subScribedUserId", length = 250)
	private String service_subScribedUserId;

	@Column(name = "service_subscribeduser_name", length = 150)
	private String service_subscribeduser_name;

	@Column(name = "last_servicedate")
	private Date last_servicedate;
	
	@Column(name = "last_servicecompleldbyId")
	private Long last_servicecompleldbyId;

	@Column(name = "last_servicecompleldid", length = 150)
	private Long last_servicecompleldid;

	@Column(name = "service_remider_howtimes")
	private Integer service_remider_howtimes;
	
	@Column(name = "serviceStatusId", nullable = false)
	private short serviceStatusId;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "vehicleGroupId", nullable = false)
	private long vehicleGroupId;
	
	@Column(name = "workorders_id")
	private Long workorders_id;

	@Column(name = "createdById", updatable = false)
	private Long createdById;

	@Column(name = "lastModifiedById")
	private Long lastModifiedById;
	
	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	/** The value for the lastUpdated DATE field */
	@Basic(optional = false)
	@Column(name = "lastupdated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdated;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "serviceType", nullable = false)
	private short serviceType;
	
	@Column(name = "serviceOdometerUpdatedDate")
	private Date serviceOdometerUpdatedDate;
	
	@Column(name = "thresholdUpdateOdometer")
	private Integer thresholdUpdateOdometer;
	
	@Column(name = "serviceEntries_id")
	private Long serviceEntries_id;
	
	@Column(name = "isFirstService", nullable = false)
	private boolean isFirstService;
	
	@Column(name = "isSkip", nullable = false)
	private boolean isSkip;
	
	@Column(name = "skipRemark", length = 1000)
	private String	skipRemark;
	
	@Column(name = "firstMeterInterval")
	private Integer firstMeterInterval;
	
	@Column(name = "firstTimeInterval")
	private Integer firstTimeInterval;
	
	@Column(name = "firstTimeIntervalType")
	private short	firstTimeIntervalType;
	
	@Column(name = "dealerServiceEntriesId")
	private Long dealerServiceEntriesId;

	@Column(name = "serviceProgramId")
	private Long serviceProgramId;
	
	
	@Column(name = "serviceScheduleId")
	private Long serviceScheduleId;
	
	
	public ServiceReminder() {
		super();

	}

	public ServiceReminder(Long service_Number, Integer vid, Integer meter_interval, Integer vehicle_currentOdometer,
			Integer meter_serviceodometer, Integer meter_threshold, Integer meter_servicethreshold_odometer,
			Integer time_interval, Date time_interval_currentdate, Date time_servicedate,
			Integer time_threshold, Date time_servicethreshold_date,
			String service_emailnotification, Date last_servicedate,
			String last_servicecompleldby, Long last_servicecompleldid, Integer service_remider_howtimes,
			Date created, Date lastupdated, Integer companyId, long vehicleGroupId,
			Date serviceOdometerUpdatedDate) {
		super();
		this.service_Number = service_Number;
		this.vid = vid;
		this.meter_interval = meter_interval;
		this.vehicle_currentOdometer = vehicle_currentOdometer;
		this.meter_serviceodometer = meter_serviceodometer;
		this.meter_threshold = meter_threshold;
		this.meter_servicethreshold_odometer = meter_servicethreshold_odometer;
		this.time_interval = time_interval;
		this.time_interval_currentdate = time_interval_currentdate;
		this.time_servicedate = time_servicedate;
		this.time_threshold = time_threshold;
		this.time_servicethreshold_date = time_servicethreshold_date;
		this.service_emailnotification = service_emailnotification;
		this.last_servicedate = last_servicedate;
		this.last_servicecompleldid = last_servicecompleldid;
		this.service_remider_howtimes = service_remider_howtimes;
		this.created = created;
		this.lastupdated = lastupdated;
		this.companyId = companyId;
		this.vehicleGroupId	= vehicleGroupId;
		this.serviceOdometerUpdatedDate	= serviceOdometerUpdatedDate;
	}

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
	 * @return the service_subscribeduser_name
	 */
	public String getService_subscribeduser_name() {
		return service_subscribeduser_name;
	}

	/**
	 * @param service_subscribeduser_name
	 *            the service_subscribeduser_name to set
	 */
	public void setService_subscribeduser_name(String service_subscribeduser_name) {
		this.service_subscribeduser_name = service_subscribeduser_name;
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
	 * @return the last_servicecompleldid
	 */
	public Long getLast_servicecompleldid() {
		return last_servicecompleldid;
	}

	/**
	 * @param long1
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
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public Date getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public long getVehicleGroupId() {
		return vehicleGroupId;
	}

	public void setVehicleGroupId(long vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
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

	/**
	 * @return the workorders_id
	 */
	public Long getWorkorders_id() {
		return workorders_id;
	}

	/**
	 * @param workorders_id the workorders_id to set
	 */
	public void setWorkorders_id(Long workorders_id) {
		this.workorders_id = workorders_id;
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
	

	public Integer getThresholdUpdateOdometer() {
		return thresholdUpdateOdometer;
	}

	public void setThresholdUpdateOdometer(Integer thresholdUpdateOdometer) {
		this.thresholdUpdateOdometer = thresholdUpdateOdometer;
	}

	public Long getServiceEntries_id() {
		return serviceEntries_id;
	}

	public void setServiceEntries_id(Long serviceEntries_id) {
		this.serviceEntries_id = serviceEntries_id;
	}
	
	public Date getServiceOdometerUpdatedDate() {
		return serviceOdometerUpdatedDate;
	}

	public void setServiceOdometerUpdatedDate(Date serviceOdometerUpdatedDate) {
		this.serviceOdometerUpdatedDate = serviceOdometerUpdatedDate;
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
	
	

	public Long getDealerServiceEntriesId() {
		return dealerServiceEntriesId;
	}

	public void setDealerServiceEntriesId(Long dealerServiceEntriesId) {
		this.dealerServiceEntriesId = dealerServiceEntriesId;
	}

	public Long getServiceProgramId() {
		return serviceProgramId;
	}

	public void setServiceProgramId(Long serviceProgramId) {
		this.serviceProgramId = serviceProgramId;
	}

	public Long getServiceScheduleId() {
		return serviceScheduleId;
	}

	public void setServiceScheduleId(Long serviceScheduleId) {
		this.serviceScheduleId = serviceScheduleId;
	}

	public boolean isSkip() {
		return isSkip;
	}

	public void setSkip(boolean isSkip) {
		this.isSkip = isSkip;
	}

	public String getSkipRemark() {
		return skipRemark;
	}

	public void setSkipRemark(String skipRemark) {
		this.skipRemark = skipRemark;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((serviceSubTypeId == null) ? 0 : serviceSubTypeId.hashCode());
		result = prime * result + ((serviceTypeId == null) ? 0 : serviceTypeId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceReminder other = (ServiceReminder) obj;
		if (serviceSubTypeId == null) {
			if (other.serviceSubTypeId != null)
				return false;
		} else if (!serviceSubTypeId.equals(other.serviceSubTypeId))
			return false;
		if (serviceTypeId == null) {
			if (other.serviceTypeId != null)
				return false;
		} else if (!serviceTypeId.equals(other.serviceTypeId))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ServiceReminder [vid=");
		builder.append(vid);
		builder.append(", service_id=");
		builder.append(service_id);
		builder.append(", service_Number=");
		builder.append(service_Number);
		builder.append(", meter_interval=");
		builder.append(meter_interval);
		builder.append(", vehicle_currentOdometer=");
		builder.append(vehicle_currentOdometer);
		builder.append(", meter_serviceodometer=");
		builder.append(meter_serviceodometer);
		builder.append(", meter_threshold=");
		builder.append(meter_threshold);
		builder.append(", meter_servicethreshold_odometer=");
		builder.append(meter_servicethreshold_odometer);
		builder.append(", time_interval=");
		builder.append(time_interval);
		builder.append(", time_interval_currentdate=");
		builder.append(time_interval_currentdate);
		builder.append(", time_servicedate=");
		builder.append(time_servicedate);
		builder.append(", time_threshold=");
		builder.append(time_threshold);
		builder.append(", time_servicethreshold_date=");
		builder.append(time_servicethreshold_date);
		builder.append(", service_emailnotification=");
		builder.append(service_emailnotification);
		builder.append(", last_servicedate=");
		builder.append(last_servicedate);
		builder.append(", last_servicecompleldid=");
		builder.append(last_servicecompleldid);
		builder.append(", service_remider_howtimes=");
		builder.append(service_remider_howtimes);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", vehicleGroupId=");
		builder.append(vehicleGroupId);
		builder.append(", serviceTypeId=");
		builder.append(serviceTypeId);
		builder.append(", serviceSubTypeId=");
		builder.append(serviceSubTypeId);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", serviceStatusId="); 
		builder.append(serviceStatusId);
		builder.append(", service_subScribedUserId=");
		builder.append(service_subScribedUserId);
		builder.append(", serviceOdometerUpdatedDate=");
		builder.append(serviceOdometerUpdatedDate);
		builder.append(", serviceEntries_id=");
		builder.append(serviceEntries_id);
		builder.append("]");
		return builder.toString();
	}

}