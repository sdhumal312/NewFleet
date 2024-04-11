package org.fleetopgroup.persistence.dto;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.Role;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserProfileDto {

	private Long userprofile_id;

	private Long user_id;

	private String user_email;

	private String firstName;

	private String lastName;

	private String personal_email;

	private String sex;

	private String dateofbirth;

	private String designation;

	private String employes_id;

	private Integer department_id;

	private String department_name;

	private Integer company_id;
	
	private String company_id_encode;

	private String company_name;

	private Integer branch_id;

	private String branch_name;

	private String home_number;

	private String mobile_number;

	private String work_number;

	private String address_line1;

	private String city;

	private String state;

	private String country;

	private Integer pincode;

	private String emergency_person;

	private String emergency_number;

	private String esi_number;

	private String pf_number;

	private String insurance_number;

	private String subscribe;

	private String working_time_from;

	private String working_time_to;

	private Integer vendorId;

	private String vendorName;

	private String createdBy;

	private String lastModifiedBy;
	
	private String company_address;

	public String getCompany_address() {
		return company_address;
	}

	public void setCompany_address(String company_address) {
		this.company_address = company_address;
	}

	@JsonIgnore
	private boolean markForDelete;

	private Long photo_id;

	private String created;

	private String lastupdated;

	private String group_Permissions;

	private boolean userCurrent;

	private String lastlogin;

	private Long company_esi_pf_days;

	private Integer company_esi_pf_disable;

	private Long role_id;
	
	private boolean	isTwoFactorLogin;
	
	private short	otpTypeId;
	
	private Date	lastLoginDate;
	
	private String	lastLoginIP;
	
	private String	lastLoginDateStr;
	
	private String	rfidCardNo;
	
	private String	companyEmail;
	
	private List<Role> roles;
	
	private String role_name;

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public UserProfileDto() {
		super();

	}

	public Long getUserprofile_id() {
		return userprofile_id;
	}

	public void setUserprofile_id(Long userprofile_id) {
		this.userprofile_id = userprofile_id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getPersonal_email() {
		return personal_email;
	}

	public void setPersonal_email(String personal_email) {
		this.personal_email = personal_email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmployes_id() {
		return employes_id;
	}

	public void setEmployes_id(String employes_id) {
		this.employes_id = employes_id;
	}

	public Integer getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Integer department_id) {
		this.department_id = department_id;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

	public Integer getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Integer branch_id) {
		this.branch_id = branch_id;
	}

	public String getHome_number() {
		return home_number;
	}

	public void setHome_number(String home_number) {
		this.home_number = home_number;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getWork_number() {
		return work_number;
	}

	public void setWork_number(String work_number) {
		this.work_number = work_number;
	}

	public String getAddress_line1() {
		return address_line1;
	}

	public void setAddress_line1(String address_line1) {
		this.address_line1 = address_line1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getPincode() {
		return pincode;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}

	public String getEmergency_person() {
		return emergency_person;
	}

	public void setEmergency_person(String emergeny_person) {
		this.emergency_person = emergeny_person;
	}

	public String getEmergency_number() {
		return emergency_number;
	}

	public void setEmergency_number(String emergeny_number) {
		this.emergency_number = emergeny_number;
	}

	public String getEsi_number() {
		return esi_number;
	}

	public void setEsi_number(String esi_number) {
		this.esi_number = esi_number;
	}

	public String getPf_number() {
		return pf_number;
	}

	public void setPf_number(String pf_number) {
		this.pf_number = pf_number;
	}

	public String getInsurance_number() {
		return insurance_number;
	}

	public void setInsurance_number(String insurance_number) {
		this.insurance_number = insurance_number;
	}

	public String getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getWorking_time_from() {
		return working_time_from;
	}

	public void setWorking_time_from(String working_time_from) {
		this.working_time_from = working_time_from;
	}

	public String getWorking_time_to() {
		return working_time_to;
	}

	public void setWorking_time_to(String working_time_to) {
		this.working_time_to = working_time_to;
	}

	/**
	 * @return the vendorId
	 */
	public Integer getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId
	 *            the vendorId to set
	 */
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * @param vendorName
	 *            the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the markForDelete
	 */
	public boolean ismarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete
	 *            the markForDelete to set
	 */
	public void setmarkForDelete(boolean mARKFORDELETE) {
		markForDelete = mARKFORDELETE;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(String lastupdated) {
		this.lastupdated = lastupdated;
	}

	public Long getPhoto_id() {
		return photo_id;
	}

	public void setPhoto_id(Long photo_id) {
		this.photo_id = photo_id;
	}

	/**
	 * @return the subscribe
	 */
	public String getSubscribe() {
		return subscribe;
	}

	/**
	 * @param subscribe
	 *            the subscribe to set
	 */
	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	/**
	 * @return the group_Permissions
	 */
	public String getGroup_Permissions() {
		return group_Permissions;
	}

	/**
	 * @param group_Permissions
	 *            the group_Permissions to set
	 */
	public void setGroup_Permissions(String group_Permissions) {
		this.group_Permissions = group_Permissions;
	}

	/**
	 * @return the markForDelete
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete
	 *            the markForDelete to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the userCurrent
	 */
	public boolean isUserCurrent() {
		return userCurrent;
	}

	/**
	 * @param userCurrent
	 *            the userCurrent to set
	 */
	public void setUserCurrent(boolean userCurrent) {
		this.userCurrent = userCurrent;
	}

	/**
	 * @return the lastlogin
	 */
	public String getLastlogin() {
		return lastlogin;
	}

	/**
	 * @param lastlogin
	 *            the lastlogin to set
	 */
	public void setLastlogin(String lastlogin) {
		this.lastlogin = lastlogin;
	}

	
	
	/**
	 * @return the company_esi_pf_days
	 */
	public Long getCompany_esi_pf_days() {
		return company_esi_pf_days;
	}

	/**
	 * @param company_esi_pf_days the company_esi_pf_days to set
	 */
	public void setCompany_esi_pf_days(Long company_esi_pf_days) {
		this.company_esi_pf_days = company_esi_pf_days;
	}

	/**
	 * @return the company_esi_pf_disable
	 */
	public Integer getCompany_esi_pf_disable() {
		return company_esi_pf_disable;
	}

	/**
	 * @param company_esi_pf_disable the company_esi_pf_disable to set
	 */
	public void setCompany_esi_pf_disable(Integer company_esi_pf_disable) {
		this.company_esi_pf_disable = company_esi_pf_disable;
	}
	
	

	public String getCompany_id_encode() {
		return company_id_encode;
	}

	public void setCompany_id_encode(String company_id_encode) {
		this.company_id_encode = company_id_encode;
	}
	
	public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

	public boolean isTwoFactorLogin() {
		return isTwoFactorLogin;
	}

	public short getOtpTypeId() {
		return otpTypeId;
	}

	public void setTwoFactorLogin(boolean isTwoFactorLogin) {
		this.isTwoFactorLogin = isTwoFactorLogin;
	}

	public void setOtpTypeId(short otpTypeId) {
		this.otpTypeId = otpTypeId;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public String getLastLoginDateStr() {
		return lastLoginDateStr;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	public void setLastLoginDateStr(String lastLoginDateStr) {
		this.lastLoginDateStr = lastLoginDateStr;
	}

	public String getRfidCardNo() {
		return rfidCardNo;
	}

	public void setRfidCardNo(String rfidCardNo) {
		this.rfidCardNo = rfidCardNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	
	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserProfileDto [userprofile_id=" + userprofile_id + ", user_id=" + user_id + ", user_email="
				+ user_email + ", firstName=" + firstName + ", lastName=" + lastName + ", personal_email="
				+ personal_email + ", sex=" + sex + ", dateofbirth=" + dateofbirth + ", designation=" + designation
				+ ", employes_id=" + employes_id + ", department_id=" + department_id + ", department_name="
				+ department_name + ", company_id=" + company_id + ", company_id_encode=" + company_id_encode
				+ ", company_name=" + company_name + ", branch_id=" + branch_id + ", branch_name=" + branch_name
				+ ", home_number=" + home_number + ", mobile_number=" + mobile_number + ", work_number=" + work_number
				+ ", address_line1=" + address_line1 + ", city=" + city + ", state=" + state + ", country=" + country
				+ ", pincode=" + pincode + ", emergency_person=" + emergency_person + ", emergency_number="
				+ emergency_number + ", esi_number=" + esi_number + ", pf_number=" + pf_number + ", insurance_number="
				+ insurance_number + ", subscribe=" + subscribe + ", working_time_from=" + working_time_from
				+ ", working_time_to=" + working_time_to + ", vendorId=" + vendorId + ", vendorName=" + vendorName
				+ ", createdBy=" + createdBy + ", lastModifiedBy=" + lastModifiedBy + ", company_address="
				+ company_address + ", markForDelete=" + markForDelete + ", photo_id=" + photo_id + ", created="
				+ created + ", lastupdated=" + lastupdated + ", group_Permissions=" + group_Permissions
				+ ", userCurrent=" + userCurrent + ", lastlogin=" + lastlogin + ", company_esi_pf_days="
				+ company_esi_pf_days + ", company_esi_pf_disable=" + company_esi_pf_disable + ", role_id=" + role_id
				+ ", isTwoFactorLogin=" + isTwoFactorLogin + ", otpTypeId=" + otpTypeId + ", lastLoginDate="
				+ lastLoginDate + ", lastLoginIP=" + lastLoginIP + ", lastLoginDateStr=" + lastLoginDateStr
				+ ", rfidCardNo=" + rfidCardNo + ", companyEmail=" + companyEmail + ", roles=" + roles + ", role_name="
				+ role_name + "]";
	}

	

}