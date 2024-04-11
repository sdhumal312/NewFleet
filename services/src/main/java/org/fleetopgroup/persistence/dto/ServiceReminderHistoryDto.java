package org.fleetopgroup.persistence.dto;

/**
 * @author fleetop
 *
 *
 *
 */

import java.util.Date;

public class ServiceReminderHistoryDto {
	
	private String completed_Date;
	
	private String due_Date;
	
	private String service_Type;
	
	private String date_compliances;
	
	private String odometer_compliances;

	private Integer due_odometer;

	private Integer completed_odometer;
	
	private Long workorders_id;
	
	private Long workorders_Number;

	private String compliance;

	public ServiceReminderHistoryDto(String completed_Date, String due_Date, String service_Type,
			String date_compliances, String odometer_compliances, Integer due_odometer, Integer completed_odometer,
			Long workorders_id, Long workorders_Number, String compliance) {
		super();
		this.completed_Date = completed_Date;
		this.due_Date = due_Date;
		this.service_Type = service_Type;
		this.date_compliances = date_compliances;
		this.odometer_compliances = odometer_compliances;
		this.due_odometer = due_odometer;
		this.completed_odometer = completed_odometer;
		this.workorders_id = workorders_id;
		this.workorders_Number = workorders_Number;
		this.compliance = compliance;
	}


	public ServiceReminderHistoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Long getWorkorders_id() {
		return workorders_id;
	}


	public void setWorkorders_id(Long workorders_id) {
		this.workorders_id = workorders_id;
	}


	public Long getWorkorders_Number() {
		return workorders_Number;
	}


	public void setWorkorders_Number(Long workorders_Number) {
		this.workorders_Number = workorders_Number;
	}

	public String getCompleted_Date() {
		return completed_Date;
	}


	public void setCompleted_Date(String completed_Date) {
		this.completed_Date = completed_Date;
	}


	public String getDue_Date() {
		return due_Date;
	}


	public void setDue_Date(String due_Date) {
		this.due_Date = due_Date;
	}


	public String getService_Type() {
		return service_Type;
	}

	public void setService_Type(String service_Type) {
		this.service_Type = service_Type;
	}

	public String getDate_compliances() {
		return date_compliances;
	}


	public void setDate_compliances(String date_compliances) {
		this.date_compliances = date_compliances;
	}


	public String getOdometer_compliances() {
		return odometer_compliances;
	}


	public void setOdometer_compliances(String odometer_compliances) {
		this.odometer_compliances = odometer_compliances;
	}


	public Integer getDue_odometer() {
		return due_odometer;
	}


	public void setDue_odometer(Integer due_odometer) {
		this.due_odometer = due_odometer;
	}


	public Integer getCompleted_odometer() {
		return completed_odometer;
	}

	public void setCompleted_odometer(Integer completed_odometer) {
		this.completed_odometer = completed_odometer;
	}


	public String getCompliance() {
		return compliance;
	}


	public void setCompliance(String compliance) {
		this.compliance = compliance;
	}


	@Override
	public String toString() {
		return "ServiceReminderHistoryDto [completed_Date=" + completed_Date + ", due_Date=" + due_Date
				+ ", service_Type=" + service_Type + ", date_compliances=" + date_compliances
				+ ", odometer_compliances=" + odometer_compliances + ", due_odometer=" + due_odometer
				+ ", completed_odometer=" + completed_odometer + ", workorders_id=" + workorders_id
				+ ", workorders_Number=" + workorders_Number + ", compliance=" + compliance + "]";
	}


}