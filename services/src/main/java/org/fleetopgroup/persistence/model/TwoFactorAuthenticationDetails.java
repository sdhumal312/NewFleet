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
@Table(name="TwoFactorAuthenticationDetails")
public class TwoFactorAuthenticationDetails  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "authenticationDetailsId")
	private Long authenticationDetailsId;
	
	@Column(name= "userId")
	private Long userId;

	@Column(name= "otpRequiredType")
	private short	otpRequiredType;
	
	@Column(name= "mobileNumber")
	private String	mobileNumber;
	
	@Column(name= "email")
	private String	email;
	
	@Column(name= "OtpValidatedOn")
	private Date	OtpValidatedOn;
	
	@Column(name= "created")
	private Date 	created;
	
	@Column(name= "lastModified")
	private Date	lastModified;
	
	@Column(name= "createdById")
	private Long	createdById;
	
	@Column(name= "lastModifiedBy")
	private Long	lastModifiedBy;
	
	@Column(name= "companyId")
	private Integer	companyId;
	
	@Column(name= "markForDelete")
	private boolean	markForDelete;
	
	@Column(name= "isEnabled")
	private boolean	isEnabled;

	public Long getAuthenticationDetailsId() {
		return authenticationDetailsId;
	}

	

	public short getOtpRequiredType() {
		return otpRequiredType;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public Date getOtpValidatedOn() {
		return OtpValidatedOn;
	}

	public Date getCreated() {
		return created;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setAuthenticationDetailsId(Long authenticationDetailsId) {
		this.authenticationDetailsId = authenticationDetailsId;
	}

	

	public void setOtpRequiredType(short otpRequiredType) {
		this.otpRequiredType = otpRequiredType;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public void setOtpValidatedOn(Date otpValidatedOn) {
		OtpValidatedOn = otpValidatedOn;
	}

	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public void setCreated(Date created) {
		this.created = created;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authenticationDetailsId == null) ? 0 : authenticationDetailsId.hashCode());
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
		TwoFactorAuthenticationDetails other = (TwoFactorAuthenticationDetails) obj;
		if (authenticationDetailsId == null) {
			if (other.authenticationDetailsId != null)
				return false;
		} else if (!authenticationDetailsId.equals(other.authenticationDetailsId))
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TwoFactorAuthenticationDetails [authenticationDetailsId=");
		builder.append(authenticationDetailsId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", otpRequiredType=");
		builder.append(otpRequiredType);
		builder.append(", mobileNumber=");
		builder.append(mobileNumber);
		builder.append(", email=");
		builder.append(email);
		builder.append(", OtpValidatedOn=");
		builder.append(OtpValidatedOn);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastModified=");
		builder.append(lastModified);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", isEnabled=");
		builder.append(isEnabled);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
