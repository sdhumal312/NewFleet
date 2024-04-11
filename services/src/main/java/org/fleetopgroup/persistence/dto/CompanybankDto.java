package org.fleetopgroup.persistence.dto;


public class CompanybankDto{

	private Integer com_bank_id;

	private String com_bank_name;

	private String com_bank_account;

	private String com_bank_actype;

	private String com_bank_ifsc;

	private String com_bank_bsr;

	private String com_bank_branch;

	private String com_bank_address;

	private String com_bank_city;

	private String com_bank_state;

	private String com_bank_country;

	private String com_bank_pincode;

	private String com_bank_status;

	private Integer company_id;
	
	private String createdBy;

	private String lastModifiedBy;

	public CompanybankDto() {
		super();
	}

	public CompanybankDto(String com_bank_name, String com_bank_account, String com_bank_actype, String com_bank_ifsc,
			String com_bank_bsr, String com_bank_branch, String com_bank_address, String com_bank_city,
			String com_bank_state, String com_bank_country, String com_bank_pincode, String com_bank_status,
			Integer company_id) {
		super();
		this.com_bank_name = com_bank_name;
		this.com_bank_account = com_bank_account;
		this.com_bank_actype = com_bank_actype;
		this.com_bank_ifsc = com_bank_ifsc;
		this.com_bank_bsr = com_bank_bsr;
		this.com_bank_branch = com_bank_branch;
		this.com_bank_address = com_bank_address;
		this.com_bank_city = com_bank_city;
		this.com_bank_state = com_bank_state;
		this.com_bank_country = com_bank_country;
		this.com_bank_pincode = com_bank_pincode;
		this.com_bank_status = com_bank_status;
		this.company_id = company_id;
	}

	public Integer getCom_bank_id() {
		return com_bank_id;
	}

	public void setCom_bank_id(Integer com_bank_id) {
		this.com_bank_id = com_bank_id;
	}

	public String getCom_bank_name() {
		return com_bank_name;
	}

	public void setCom_bank_name(String com_bank_name) {
		this.com_bank_name = com_bank_name;
	}

	public String getCom_bank_account() {
		return com_bank_account;
	}

	public void setCom_bank_account(String com_bank_account) {
		this.com_bank_account = com_bank_account;
	}

	public String getCom_bank_actype() {
		return com_bank_actype;
	}

	public void setCom_bank_actype(String com_bank_actype) {
		this.com_bank_actype = com_bank_actype;
	}

	public String getCom_bank_ifsc() {
		return com_bank_ifsc;
	}

	public void setCom_bank_ifsc(String com_bank_ifsc) {
		this.com_bank_ifsc = com_bank_ifsc;
	}

	public String getCom_bank_bsr() {
		return com_bank_bsr;
	}

	public void setCom_bank_bsr(String com_bank_bsr) {
		this.com_bank_bsr = com_bank_bsr;
	}

	public String getCom_bank_branch() {
		return com_bank_branch;
	}

	public void setCom_bank_branch(String com_bank_branch) {
		this.com_bank_branch = com_bank_branch;
	}

	public String getCom_bank_address() {
		return com_bank_address;
	}

	public void setCom_bank_address(String com_bank_address) {
		this.com_bank_address = com_bank_address;
	}

	public String getCom_bank_city() {
		return com_bank_city;
	}

	public void setCom_bank_city(String com_bank_city) {
		this.com_bank_city = com_bank_city;
	}

	public String getCom_bank_state() {
		return com_bank_state;
	}

	public void setCom_bank_state(String com_bank_state) {
		this.com_bank_state = com_bank_state;
	}

	public String getCom_bank_country() {
		return com_bank_country;
	}

	public void setCom_bank_country(String com_bank_country) {
		this.com_bank_country = com_bank_country;
	}

	public String getCom_bank_pincode() {
		return com_bank_pincode;
	}

	public void setCom_bank_pincode(String com_bank_pincode) {
		this.com_bank_pincode = com_bank_pincode;
	}

	public String getCom_bank_status() {
		return com_bank_status;
	}

	public void setCom_bank_status(String com_bank_status) {
		this.com_bank_status = com_bank_status;
	}

	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CompanybankDto [com_bank_name=");
		builder.append(com_bank_name);
		builder.append(", com_bank_account=");
		builder.append(com_bank_account);
		builder.append(", com_bank_actype=");
		builder.append(com_bank_actype);
		builder.append(", com_bank_ifsc=");
		builder.append(com_bank_ifsc);
		builder.append(", com_bank_bsr=");
		builder.append(com_bank_bsr);
		builder.append(", com_bank_branch=");
		builder.append(com_bank_branch);
		builder.append(", com_bank_address=");
		builder.append(com_bank_address);
		builder.append(", com_bank_city=");
		builder.append(com_bank_city);
		builder.append(", com_bank_state=");
		builder.append(com_bank_state);
		builder.append(", com_bank_country=");
		builder.append(com_bank_country);
		builder.append(", com_bank_pincode=");
		builder.append(com_bank_pincode);
		builder.append(", com_bank_status=");
		builder.append(com_bank_status);
		builder.append(", company_id=");
		builder.append(company_id);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append("]");
		return builder.toString();
	}

}
