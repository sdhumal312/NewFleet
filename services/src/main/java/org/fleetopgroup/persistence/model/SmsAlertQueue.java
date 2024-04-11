package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SmsAlertQueue")
public class SmsAlertQueue implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "queueId")
	private long queueId;
	
	@Column(name = "vid")
	private Integer vid;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "alertType")
	private short alertType;
	
	@Column(name = "mobileNumber")
	private String mobileNumber;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "transactionId")
	private long transactionId;
	
	@Column(name = "transactionNumber")
	private long transactionNumber;
	
	@Column(name = "alertBeforeDate")
	private String alertBeforeDate;
	
	@Column(name = "alertAfterDate")
	private String alertAfterDate;
	
	@Column(name = "markForDelete")
	private boolean markForDelete;
	
	@Column(name = "threadHoldOdometer")
	private Integer threadHoldOdometer;
	
	@Column(name = "isOverDueAlert")
	private boolean isOverDueAlert;
	
	@Column(name = "isSmsSent")
	private boolean isSmsSent;
	
	@Column(name = "serviceDate")
	private Date serviceDate;
	
	@Column(name = "alertScheduleDate")
	private Date alertScheduleDate;
	
	@Column(name = "alertBeforeValues")
	private Integer alertBeforeValues;
	
	@Column(name = "alertAfterValues")
	private Integer alertAfterValues;
	
	public SmsAlertQueue() {
		super();
	}

	public SmsAlertQueue( long queueId, Integer vid, String content, short alertType, String mobileNumber, Integer companyId,
			long transactionId, long transactionNumber, String alertBeforeDate, String alertAfterDate, boolean markForDelete,
			Integer threadHoldOdometer, boolean isOverDueAlert, boolean isSmsSent, Date serviceDate, Date alertScheduleDate,
			Integer alertBeforeValues, Integer alertAfterValues ) {
		super();
		this.queueId = queueId;
		this.vid = vid;
		this.content = content;
		this.alertType = alertType;
		this.mobileNumber = mobileNumber;
		this.companyId = companyId;
		this.transactionId = transactionId;
		this.transactionNumber = transactionNumber;
		this.alertBeforeDate = alertBeforeDate;
		this.alertAfterDate = alertAfterDate;
		this.markForDelete = markForDelete;
		this.threadHoldOdometer = threadHoldOdometer;
		this.isOverDueAlert = isOverDueAlert;
		this.isSmsSent = isSmsSent;
		this.serviceDate = serviceDate;
		this.alertScheduleDate = alertScheduleDate;
		this.alertBeforeValues = alertBeforeValues;
		this.alertAfterValues = alertAfterValues;
	}
	
	
	
	/*get and set QueueId*/
	public long getQueueId() {
		return queueId;
	}

	public void setQueueId(long queueId) {
		this.queueId = queueId;
	}

	/*get and set vid*/
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
	

	public Integer getAlertBeforeValues() {
		return alertBeforeValues;
	}

	public void setAlertBeforeValues(Integer alertBeforeValues) {
		this.alertBeforeValues = alertBeforeValues;
	}

	public Integer getAlertAfterValues() {
		return alertAfterValues;
	}

	public void setAlertAfterValues(Integer alertAfterValues) {
		this.alertAfterValues = alertAfterValues;
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
		builder.append(", alertAfterValues=");
		builder.append(alertAfterValues);
		builder.append(", alertBeforeValues=");
		builder.append(alertBeforeValues);
		builder.append("]");
		return builder.toString();
	}
	
}