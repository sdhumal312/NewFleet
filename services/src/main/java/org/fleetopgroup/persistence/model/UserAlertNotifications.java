package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserAlertNotifications")
public class UserAlertNotifications  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "userAlertNotificationsId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	userAlertNotificationsId;
	
	@Column(name = "userId")
	private Long	userId;
	
	@Column(name = "transactionId")
	private Long	transactionId;
	
	@Column(name = "txnTypeId")
	private short	txnTypeId;
	
	@Column(name = "status")
	private short	status;
	
	@Column(name = "alertMsg")
	private String	alertMsg;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "createdById")
	private Long	createdById;
	
	@Column(name = "createdOn")
	private Date	createdOn;
	
	@Column(name = "markForDelete")
	private boolean	markForDelete;
	

	public Long getUserAlertNotificationsId() {
		return userAlertNotificationsId;
	}

	public void setUserAlertNotificationsId(Long userAlertNotificationsId) {
		this.userAlertNotificationsId = userAlertNotificationsId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public short getTxnTypeId() {
		return txnTypeId;
	}

	public void setTxnTypeId(short txnTypeId) {
		this.txnTypeId = txnTypeId;
	}

	public String getAlertMsg() {
		return alertMsg;
	}

	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
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

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alertMsg == null) ? 0 : alertMsg.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAlertNotifications other = (UserAlertNotifications) obj;
		if (alertMsg == null) {
			if (other.alertMsg != null)
				return false;
		} else if (!alertMsg.equals(other.alertMsg))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
	
}
