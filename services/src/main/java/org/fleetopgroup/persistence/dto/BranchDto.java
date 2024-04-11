package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

public class BranchDto {
	private Integer branch_id;
	
	private String branch_name;

	private String branch_code;

	private String branch_address;

	private String branch_city;

	private String branch_state;

	private String branch_country;

	private String branch_pincode;

	private Integer company_id;
	
	private String company_id_encode;

	private String company_name;

	private String branch_email;

	private String branch_phonenumber;

	private String branch_mobilenumber;

	private String branch_incharge;

	private String branch_incharge_phone;

	private String branch_time_from;

	private String branch_time_to;

	private String branch_landmark;

	private String branch_ownership_type;

	private String owner1_name;

	private String owner1_pan;

	private String owner1_address;

	private String owner1_mobile;

	private String owner2_name;

	private String owner2_pan;

	private String owner2_address;

	private String owner2_mobile;

	private String annual_increment;

	private Double advance_paid;

	private Double lease_amount;

	private Double monthly_rent;

	private String monthly_rent_date;

	private String branch_electricity_no;

	private String branch_serviceTax_no;

	private String branch_status;

	private Long createdById;

	private Long lastModifiedById;

	private String createdBy;
	
	private String lastModifiedBy;

	boolean markForDelete;

	private String created;

	private String lastupdated;
	
	private Long ivBranchId;
	private short status;

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

	public Long getIvBranchId() {
		return ivBranchId;
	}

	public void setIvBranchId(Long ivBranchId) {
		this.ivBranchId = ivBranchId;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
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

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
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
		this.advance_paid = Utility.round(advance_paid, 2);
	}

	public Double getLease_amount() {
		return lease_amount;
	}

	public void setLease_amount(Double lease_amount) {
		this.lease_amount = Utility.round(lease_amount, 2);
	}

	public Double getMonthly_rent() {
		return monthly_rent;
	}

	public void setMonthly_rent(Double monthly_rent) {
		this.monthly_rent = Utility.round(monthly_rent, 2);
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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

	public String getMonthly_rent_date() {
		return monthly_rent_date;
	}

	public void setMonthly_rent_date(String monthly_rent_date) {
		this.monthly_rent_date = monthly_rent_date;
	}

	public String getBranch_electricity_no() {
		return branch_electricity_no;
	}

	public void setBranch_electricity_no(String branch_electricity_no) {
		this.branch_electricity_no = branch_electricity_no;
	}

	public String getBranch_serviceTax_no() {
		return branch_serviceTax_no;
	}

	public void setBranch_serviceTax_no(String branch_serviceTax_no) {
		this.branch_serviceTax_no = branch_serviceTax_no;
	}
	
	

	public String getCompany_id_encode() {
		return company_id_encode;
	}

	public void setCompany_id_encode(String company_id_encode) {
		this.company_id_encode = company_id_encode;
	}

	@Override
	public String toString() {
		return "BranchDto [branch_id=" + branch_id + ", branch_name=" + branch_name + ", branch_code=" + branch_code
				+ ", branch_address=" + branch_address + ", branch_city=" + branch_city + ", branch_state="
				+ branch_state + ", branch_country=" + branch_country + ", branch_pincode=" + branch_pincode
				+ ", company_id=" + company_id + ", company_id_encode=" + company_id_encode + ", company_name="
				+ company_name + ", branch_email=" + branch_email + ", branch_phonenumber=" + branch_phonenumber
				+ ", branch_mobilenumber=" + branch_mobilenumber + ", branch_incharge=" + branch_incharge
				+ ", branch_incharge_phone=" + branch_incharge_phone + ", branch_time_from=" + branch_time_from
				+ ", branch_time_to=" + branch_time_to + ", branch_landmark=" + branch_landmark
				+ ", branch_ownership_type=" + branch_ownership_type + ", owner1_name=" + owner1_name + ", owner1_pan="
				+ owner1_pan + ", owner1_address=" + owner1_address + ", owner1_mobile=" + owner1_mobile
				+ ", owner2_name=" + owner2_name + ", owner2_pan=" + owner2_pan + ", owner2_address=" + owner2_address
				+ ", owner2_mobile=" + owner2_mobile + ", annual_increment=" + annual_increment + ", advance_paid="
				+ advance_paid + ", lease_amount=" + lease_amount + ", monthly_rent=" + monthly_rent
				+ ", monthly_rent_date=" + monthly_rent_date + ", branch_electricity_no=" + branch_electricity_no
				+ ", branch_serviceTax_no=" + branch_serviceTax_no + ", branch_status=" + branch_status
				+ ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById + ", createdBy=" + createdBy
				+ ", lastModifiedBy=" + lastModifiedBy + ", markForDelete=" + markForDelete + ", created=" + created
				+ ", lastupdated=" + lastupdated + ", ivBranchId=" + ivBranchId + "]";
	}



}
