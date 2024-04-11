package org.fleetopgroup.persistence.dto;

import java.io.Serializable;

public class MobileUserDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private long 		userId;
	private String 		email;
	private String 		companyCode;
	private int		 	companyId;
	private String 		firstName;
	private String 		lastName;
	private String 		companyName;
	private boolean 	enabled;
	private String 		password;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	@Override
	public String toString() {
		return "MobileUserDto [userId=" + userId + ", email=" + email + ", companyCode=" + companyCode + ", companyId="
				+ companyId + ", firstName=" + firstName + ", lastName=" + lastName + ", companyName=" + companyName
				+ ", enabled=" + enabled + "]";
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
