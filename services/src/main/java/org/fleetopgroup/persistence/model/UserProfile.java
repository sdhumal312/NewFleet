package org.fleetopgroup.persistence.model;


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
@Table(name = "userprofile")
public class UserProfile implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userprofile_id;

    @Column(name = "user_id", unique = true, nullable = false)
	private Long user_id;

    @Column(name = "user_email", nullable = true, length = 100)
    @Deprecated
	private String user_email;
    
    @Column(name = "firstName", length = 50)
    @Deprecated
    private String firstName;
    
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

	@Column(name = "lastName", length = 50)
    @Deprecated
    private String lastName;

    @Column(name = "personal_email", length = 50)
   	private String personal_email;
    
    @Column(name = "sex", length = 10)
   	private String sex;
    
    @Column(name = "dateofbirth")
   	private Date dateofbirth;
    
    @Column(name = "designation", length = 150)
   	private String designation;
    
    @Column(name = "employes_id", length = 20)
   	private String employes_id;
    
    @Column(name = "department_id", nullable = false)
   	private Integer department_id;
    
    @Column(name = "department_name", length = 150)
    @Deprecated
   	private String department_name;
    
    @Column(name = "company_id", nullable = false)
   	private Integer company_id;
    
    @Column(name = "company_name", length = 250)
    @Deprecated
	private String company_name;
    
    @Column(name = "branch_id", nullable = false)
   	private Integer branch_id;
    
    @Column(name = "branch_name",  length = 200)
    @Deprecated
	private String branch_name;
    
    @Column(name = "home_number", length = 15)
   	private String home_number;
    
    @Column(name = "mobile_number", length = 15)
   	private String mobile_number;
    
    @Column(name = "work_number", length = 15)
   	private String work_number;
    
    @Column(name = "address_line1", length = 150)
   	private String address_line1;
    
    
    @Column(name = "city", length = 100)
   	private String city;
    
    @Column(name = "state", length = 50)
   	private String state;
    
    @Column(name = "country", length = 50)
   	private String country;

    @Column(name = "pincode", length = 6)
   	private Integer pincode;
    
    @Column(name = "emergency_person", length = 100)
   	private String emergency_person;
    
    @Column(name = "emergency_number", length = 15)
   	private String emergency_number;
    
    @Column(name = "esi_number", length = 20)
   	private String esi_number;
    
    @Column(name = "pf_number", length = 20)
   	private String pf_number;
    
    @Column(name = "insurance_number", length = 20)
   	private String insurance_number;
    
    @Column(name = "working_time_from", length = 10)
   	private String working_time_from;
    
    @Column(name = "working_time_to", length = 10)
   	private String working_time_to;
    
    
    @Column(name = "subscribe", length = 200)
   	private String subscribe;
    
	@Column(name = "photo_id", length = 5)
	private Long photo_id;

	/** The value for vendor id to Link User email ID */
	@Column(name = "vendorId")
	private Integer vendorId;
	
	/** The value for vendor id to Link User email ID */
	@Column(name = "vendorName", length = 150)
	@Deprecated
	private String vendorName;

	/** The value for the created by email field */
	@Column(name = "createdBy", updatable = false, length = 150)
	private String createdBy;

	/** The value for the lastUpdated By email field */
	@Column(name = "lastModifiedBy", length = 150)
	private String lastModifiedBy;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

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
	
	@Column(name = "vehicleGroupPermission")
	@Deprecated
   	private String vehicleGroupPermission;
	
	
	@Column(name = "role_id", unique = true, nullable = false)
	private Long role_id;	
	

	@Column(name = "isTwoFactorLogin", nullable = false)
	private boolean	isTwoFactorLogin;
	
	@Column(name = "lastLoginDate")
	private Date lastLoginDate;
	
	@Column(name = "lastPasswordResetOn")
	private Date lastPasswordResetOn;
	
	@Column(name = "lastLoginIP")
	private String	lastLoginIP;
	
	@Column(name = "rfidCardNo")
	private String rfidCardNo;
	
	
    public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
	
	
	public Long getUserprofile_id() {
		return userprofile_id;
	}

	public void setUserprofile_id(Long userprofile_id) {
		this.userprofile_id = userprofile_id;
	}


	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
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

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

    public Long getPhoto_id() {
		return photo_id;
	}

	public void setPhoto_id(Long photo_id) {
		this.photo_id = photo_id;
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
	 * @return the subscribe
	 */
	public String getSubscribe() {
		return subscribe;
	}

	/**
	 * @param subscribe the subscribe to set
	 */
	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	

	public boolean isTwoFactorLogin() {
		return isTwoFactorLogin;
	}


	public void setTwoFactorLogin(boolean isTwoFactorLogin) {
		this.isTwoFactorLogin = isTwoFactorLogin;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	public Date getLastPasswordResetOn() {
		return lastPasswordResetOn;
	}

	public void setLastPasswordResetOn(Date lastPasswordResetOn) {
		this.lastPasswordResetOn = lastPasswordResetOn;
	}

	public String getRfidCardNo() {
		return rfidCardNo;
	}

	public void setRfidCardNo(String rfidCardNo) {
		this.rfidCardNo = rfidCardNo;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserProfile [userprofile_id=");
		builder.append(userprofile_id);
		builder.append(", user_id=");
		builder.append(user_id);
		builder.append(", personal_email=");
		builder.append(personal_email);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", dateofbirth=");
		builder.append(dateofbirth);
		builder.append(", designation=");
		builder.append(designation);
		builder.append(", employes_id=");
		builder.append(employes_id);
		builder.append(", department_id=");
		builder.append(department_id);
		builder.append(", company_id=");
		builder.append(company_id);
		builder.append(", branch_id=");
		builder.append(branch_id);
		builder.append(", home_number=");
		builder.append(home_number);
		builder.append(", mobile_number=");
		builder.append(mobile_number);
		builder.append(", work_number=");
		builder.append(work_number);
		builder.append(", address_line1=");
		builder.append(address_line1);
		builder.append(", city=");
		builder.append(city);
		builder.append(", state=");
		builder.append(state);
		builder.append(", country=");
		builder.append(country);
		builder.append(", pincode=");
		builder.append(pincode);
		builder.append(", emergency_person=");
		builder.append(emergency_person);
		builder.append(", emergency_number=");
		builder.append(emergency_number);
		builder.append(", esi_number=");
		builder.append(esi_number);
		builder.append(", pf_number=");
		builder.append(pf_number);
		builder.append(", insurance_number=");
		builder.append(insurance_number);
		builder.append(", working_time_from=");
		builder.append(working_time_from);
		builder.append(", working_time_to=");
		builder.append(working_time_to);
		builder.append(", subscribe=");
		builder.append(subscribe);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", photo_id=");
		builder.append(photo_id);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(",role_id="); 
		builder.append(role_id);		
		builder.append(",rfidCardNo="); 
		builder.append(rfidCardNo);		
		builder.append("]");		
		return builder.toString();
	}

}