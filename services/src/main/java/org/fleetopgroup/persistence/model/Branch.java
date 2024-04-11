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
@Table(name = "branch")
public class Branch implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "branch_id")
	private Integer branch_id;

	@Column(name = "branch_name", nullable = false, unique = false, length = 200)
	private String branch_name;

	@Column(name = "branch_code", length = 30)
	private String branch_code;

	@Column(name = "branch_address", length = 200)
	private String branch_address;

	@Column(name = "branch_city", length = 100)
	private String branch_city;

	@Column(name = "branch_state", length = 100)
	private String branch_state;

	@Column(name = "branch_country", length = 100)
	private String branch_country;

	@Column(name = "branch_pincode", length = 6)
	private String branch_pincode;

	@Column(name = "company_id")
	private Integer company_id;

	@Column(name = "company_name", length = 250)
	@Deprecated
	private String company_name;

	@Column(name = "branch_email", length = 150)
	private String branch_email;

	@Column(name = "branch_phonenumber", length = 15)
	private String branch_phonenumber;

	@Column(name = "branch_mobilenumber", length = 15)
	private String branch_mobilenumber;

	@Column(name = "branch_incharge", length = 200)
	private String branch_incharge;

	@Column(name = "branch_incharge_phone", length = 15)
	private String branch_incharge_phone;

	@Column(name = "branch_time_from", length = 10)
	private String branch_time_from;

	@Column(name = "branch_time_to", length = 10)
	private String branch_time_to;

	@Column(name = "branch_landmark", length = 200)
	private String branch_landmark;

	@Column(name = "branch_ownership_type", length = 100)
	private String branch_ownership_type;

	@Column(name = "owner1_name", length = 150)
	private String owner1_name;

	@Column(name = "owner1_pan", length = 15)
	private String owner1_pan;

	@Column(name = "owner1_address", length = 200)
	private String owner1_address;

	@Column(name = "owner1_mobile", length = 15)
	private String owner1_mobile;

	@Column(name = "owner2_name", length = 150)
	private String owner2_name;

	@Column(name = "owner2_pan", length = 15)
	private String owner2_pan;

	@Column(name = "owner2_address", length = 200)
	private String owner2_address;

	@Column(name = "owner2_mobile", length = 15)
	private String owner2_mobile;

	@Column(name = "annual_increment", length = 10)
	private String annual_increment;

	@Column(name = "advance_paid")
	private Double advance_paid;

	@Column(name = "lease_amount")
	private Double lease_amount;

	@Column(name = "monthly_rent")
	private Double monthly_rent;

	@Column(name = "monthly_rent_date")
	private Date monthly_rent_date;

	@Column(name = "branch_serviceTax_no", length = 30)
	private String branch_serviceTax_no;

	@Column(name = "branch_electricity_no", length = 30)
	private String branch_electricity_no;

	@Column(name = "branch_status", length = 50)
	private String branch_status;

	/** The value for the created by email field */
	@Column(name = "createdById", updatable = false)
	private Long createdById;

	/** The value for the lastUpdated By email field */
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	/** The value for the created by email field */
	@Column(name = "createdBy", updatable = false)
	private String createdBy;
	
	/** The value for the lastUpdated By email field */
	@Column(name = "lastModifiedBy")
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


	public Branch() {
		super();
	}

	public Integer getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Integer branch_id) {
		this.branch_id = branch_id;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getBranch_code() {
		return branch_code;
	}

	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}

	public String getBranch_address() {
		return branch_address;
	}

	public void setBranch_address(String branch_address) {
		this.branch_address = branch_address;
	}

	public String getBranch_city() {
		return branch_city;
	}

	public void setBranch_city(String branch_city) {
		this.branch_city = branch_city;
	}

	public String getBranch_state() {
		return branch_state;
	}

	public void setBranch_state(String branch_state) {
		this.branch_state = branch_state;
	}

	public String getBranch_country() {
		return branch_country;
	}

	public void setBranch_country(String branch_country) {
		this.branch_country = branch_country;
	}

	public String getBranch_pincode() {
		return branch_pincode;
	}

	public void setBranch_pincode(String branch_pincode) {
		this.branch_pincode = branch_pincode;
	}

	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}


	public String getBranch_email() {
		return branch_email;
	}

	public void setBranch_email(String branch_email) {
		this.branch_email = branch_email;
	}

	public String getBranch_phonenumber() {
		return branch_phonenumber;
	}

	public void setBranch_phonenumber(String branch_phonenumber) {
		this.branch_phonenumber = branch_phonenumber;
	}

	public String getBranch_mobilenumber() {
		return branch_mobilenumber;
	}

	public void setBranch_mobilenumber(String branch_mobilenumber) {
		this.branch_mobilenumber = branch_mobilenumber;
	}

	public String getBranch_incharge() {
		return branch_incharge;
	}

	public void setBranch_incharge(String branch_incharge) {
		this.branch_incharge = branch_incharge;
	}

	public String getBranch_incharge_phone() {
		return branch_incharge_phone;
	}

	public void setBranch_incharge_phone(String branch_incharge_phone) {
		this.branch_incharge_phone = branch_incharge_phone;
	}

	public String getBranch_time_from() {
		return branch_time_from;
	}

	public void setBranch_time_from(String branch_time_from) {
		this.branch_time_from = branch_time_from;
	}

	public String getBranch_time_to() {
		return branch_time_to;
	}

	public void setBranch_time_to(String branch_time_to) {
		this.branch_time_to = branch_time_to;
	}

	public String getBranch_landmark() {
		return branch_landmark;
	}

	public void setBranch_landmark(String branch_landmark) {
		this.branch_landmark = branch_landmark;
	}

	public String getBranch_ownership_type() {
		return branch_ownership_type;
	}

	public void setBranch_ownership_type(String branch_ownership_type) {
		this.branch_ownership_type = branch_ownership_type;
	}

	public String getOwner1_name() {
		return owner1_name;
	}

	public void setOwner1_name(String owner1_name) {
		this.owner1_name = owner1_name;
	}

	public String getOwner1_pan() {
		return owner1_pan;
	}

	public void setOwner1_pan(String owner1_pan) {
		this.owner1_pan = owner1_pan;
	}

	public String getOwner1_address() {
		return owner1_address;
	}

	public void setOwner1_address(String owner1_address) {
		this.owner1_address = owner1_address;
	}

	public String getOwner1_mobile() {
		return owner1_mobile;
	}

	public void setOwner1_mobile(String owner1_mobile) {
		this.owner1_mobile = owner1_mobile;
	}

	public String getOwner2_name() {
		return owner2_name;
	}

	public void setOwner2_name(String owner2_name) {
		this.owner2_name = owner2_name;
	}

	public String getOwner2_pan() {
		return owner2_pan;
	}

	public void setOwner2_pan(String owner2_pan) {
		this.owner2_pan = owner2_pan;
	}

	public String getOwner2_address() {
		return owner2_address;
	}

	public void setOwner2_address(String owner2_address) {
		this.owner2_address = owner2_address;
	}

	public String getOwner2_mobile() {
		return owner2_mobile;
	}

	public void setOwner2_mobile(String owner2_mobile) {
		this.owner2_mobile = owner2_mobile;
	}

	public String getAnnual_increment() {
		return annual_increment;
	}

	public void setAnnual_increment(String annual_increment) {
		this.annual_increment = annual_increment;
	}

	public Double getAdvance_paid() {
		return advance_paid;
	}

	public void setAdvance_paid(Double advance_paid) {
		this.advance_paid = advance_paid;
	}

	public Double getLease_amount() {
		return lease_amount;
	}

	public void setLease_amount(Double lease_amount) {
		this.lease_amount = lease_amount;
	}

	public Double getMonthly_rent() {
		return monthly_rent;
	}

	public void setMonthly_rent(Double monthly_rent) {
		this.monthly_rent = monthly_rent;
	}

	public String getBranch_status() {
		return branch_status;
	}

	public void setBranch_status(String branch_status) {
		this.branch_status = branch_status;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
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
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete the markForDelete to set
	 */
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

	public Date getMonthly_rent_date() {
		return monthly_rent_date;
	}

	public void setMonthly_rent_date(Date monthly_rent_date) {
		this.monthly_rent_date = monthly_rent_date;
	}

	public String getBranch_serviceTax_no() {
		return branch_serviceTax_no;
	}

	public void setBranch_serviceTax_no(String branch_serviceTax_no) {
		this.branch_serviceTax_no = branch_serviceTax_no;
	}

	public String getBranch_electricity_no() {
		return branch_electricity_no;
	}

	public void setBranch_electricity_no(String branch_electricity_no) {
		this.branch_electricity_no = branch_electricity_no;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branch_id == null) ? 0 : branch_id.hashCode());
		result = prime * result + ((branch_name == null) ? 0 : branch_name.hashCode());
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
		Branch other = (Branch) obj;
		if (branch_id == null) {
			if (other.branch_id != null)
				return false;
		} else if (!branch_id.equals(other.branch_id))
			return false;
		if (branch_name == null) {
			if (other.branch_name != null)
				return false;
		} else if (!branch_name.equals(other.branch_name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Branch [branch_id=");
		builder.append(branch_id);
		builder.append(", branch_name=");
		builder.append(branch_name);
		builder.append(", branch_code=");
		builder.append(branch_code);
		builder.append(", branch_address=");
		builder.append(branch_address);
		builder.append(", branch_city=");
		builder.append(branch_city);
		builder.append(", branch_state=");
		builder.append(branch_state);
		builder.append(", branch_country=");
		builder.append(branch_country);
		builder.append(", branch_pincode=");
		builder.append(branch_pincode);
		builder.append(", company_id=");
		builder.append(company_id);
		builder.append(", branch_email=");
		builder.append(branch_email);
		builder.append(", branch_phonenumber=");
		builder.append(branch_phonenumber);
		builder.append(", branch_mobilenumber=");
		builder.append(branch_mobilenumber);
		builder.append(", branch_incharge=");
		builder.append(branch_incharge);
		builder.append(", branch_incharge_phone=");
		builder.append(branch_incharge_phone);
		builder.append(", branch_time_from=");
		builder.append(branch_time_from);
		builder.append(", branch_time_to=");
		builder.append(branch_time_to);
		builder.append(", branch_landmark=");
		builder.append(branch_landmark);
		builder.append(", branch_ownership_type=");
		builder.append(branch_ownership_type);
		builder.append(", owner1_name=");
		builder.append(owner1_name);
		builder.append(", owner1_pan=");
		builder.append(owner1_pan);
		builder.append(", owner1_address=");
		builder.append(owner1_address);
		builder.append(", owner1_mobile=");
		builder.append(owner1_mobile);
		builder.append(", owner2_name=");
		builder.append(owner2_name);
		builder.append(", owner2_pan=");
		builder.append(owner2_pan);
		builder.append(", owner2_address=");
		builder.append(owner2_address);
		builder.append(", owner2_mobile=");
		builder.append(owner2_mobile);
		builder.append(", annual_increment=");
		builder.append(annual_increment);
		builder.append(", advance_paid=");
		builder.append(advance_paid);
		builder.append(", lease_amount=");
		builder.append(lease_amount);
		builder.append(", monthly_rent=");
		builder.append(monthly_rent);
		builder.append(", monthly_rent_date=");
		builder.append(monthly_rent_date);
		builder.append(", branch_serviceTax_no=");
		builder.append(branch_serviceTax_no);
		builder.append(", branch_electricity_no=");
		builder.append(branch_electricity_no);
		builder.append(", branch_status=");
		builder.append(branch_status);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append("]");
		return builder.toString();
	}

}
