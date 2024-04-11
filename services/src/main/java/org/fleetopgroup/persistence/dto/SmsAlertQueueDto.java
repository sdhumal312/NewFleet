package org.fleetopgroup.persistence.dto;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


public class SmsAlertQueueDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long queueId;
	
	private Integer vid;
	
	private String content;
	
	private short alertType;
	
	private String mobileNumber;
	
	private Integer companyId;
	
	private long transactionId;
	
	private long transactionNumber;
	
	private String alertBeforeDate;
	
	private String alertAfterDate;
	
	private boolean markForDelete;
	
	private Integer threadHoldOdometer;
	
	private boolean isOverDueAlert;
	
	private boolean isSmsSent;
	
	private Date serviceDate;
	
	private Date alertScheduleDate;
	
	

	public long getQueueId() {
		return queueId;
	}

	public void setQueueId(long queueId) {
		this.queueId = queueId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public short getAlertType() {
		return alertType;
	}

	public void setAlertType(short alertType) {
		this.alertType = alertType;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public long getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(long transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getAlertBeforeDate() {
		return alertBeforeDate;
	}

	public void setAlertBeforeDate(String alertBeforeDate) {
		this.alertBeforeDate = alertBeforeDate;
	}

	public String getAlertAfterDate() {
		return alertAfterDate;
	}

	public void setAlertAfterDate(String alertAfterDate) {
		this.alertAfterDate = alertAfterDate;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Integer getThreadHoldOdometer() {
		return threadHoldOdometer;
	}

	public void setThreadHoldOdometer(Integer threadHoldOdometer) {
		this.threadHoldOdometer = threadHoldOdometer;
	}

	public boolean isOverDueAlert() {
		return isOverDueAlert;
	}

	public void setOverDueAlert(boolean isOverDueAlert) {
		this.isOverDueAlert = isOverDueAlert;
	}

	public boolean isSmsSent() {
		return isSmsSent;
	}

	public void setSmsSent(boolean isSmsSent) {
		this.isSmsSent = isSmsSent;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public Date getAlertScheduleDate() {
		return alertScheduleDate;
	}

	public void setAlertScheduleDate(Date alertScheduleDate) {
		this.alertScheduleDate = alertScheduleDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SmsAlertQueue [queueId=");
		builder.append(queueId);
		builder.append(", vid=");
		builder.append(vid);
		builder.append(", content=");
		builder.append(content);
		builder.append(", alertType=");
		builder.append(alertType);
		builder.append(", mobileNumber=");
		builder.append(mobileNumber);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", transactionId=");
		builder.append(transactionId);
		builder.append(", transactionNumber=");
		builder.append(transactionNumber);
		builder.append(", alertBeforeDate=");
		builder.append(alertBeforeDate);
		builder.append(", alertAfterDate=");
		builder.append(alertAfterDate);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", threadHoldOdometer=");
		builder.append(threadHoldOdometer);
		builder.append(", isOverDueAlert=");
		builder.append(isOverDueAlert);
		builder.append(", isSmsSent="); 
		builder.append(isSmsSent);
		builder.append(", serviceDate=");
		builder.append(serviceDate);
		builder.append(", alertScheduleDate=");
		builder.append(alertScheduleDate);
		builder.append("]");
		return builder.toString();
	}
	
}