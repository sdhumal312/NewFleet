package org.fleetopgroup.persistence.dto;


public class CompanydirectorDto {

	private Integer com_directors_id;

	private String com_directors_name;

	private String com_designation;

	private String com_directors_mobile;

	private String com_directors_email;
	
	private String com_directors_status;

	private Integer company_id;
	
	private String createdBy;

	private String lastModifiedBy;

	public CompanydirectorDto() {
		super();
	}

	public CompanydirectorDto(String com_directors_name, String com_designation, String com_directors_mobile,
			String com_directors_email, String com_directors_status, Integer company_id) {
		super();
		this.com_directors_name = com_directors_name;
		this.com_designation = com_designation;
		this.com_directors_mobile = com_directors_mobile;
		this.com_directors_email = com_directors_email;
		this.com_directors_status = com_directors_status;
		this.company_id = company_id;
	}

	public Integer getCom_directors_id() {
		return com_directors_id;
	}

	public void setCom_directors_id(Integer com_directors_id) {
		this.com_directors_id = com_directors_id;
	}

	public String getCom_directors_name() {
		return com_directors_name;
	}

	public void setCom_directors_name(String com_directors_name) {
		this.com_directors_name = com_directors_name;
	}

	public String getCom_designation() {
		return com_designation;
	}

	public void setCom_designation(String com_designation) {
		this.com_designation = com_designation;
	}

	public String getCom_directors_mobile() {
		return com_directors_mobile;
	}

	public void setCom_directors_mobile(String com_directors_mobile) {
		this.com_directors_mobile = com_directors_mobile;
	}

	public String getCom_directors_email() {
		return com_directors_email;
	}

	public void setCom_directors_email(String com_directors_email) {
		this.com_directors_email = com_directors_email;
	}

	public String getCom_directors_status() {
		return com_directors_status;
	}

	public void setCom_directors_status(String com_directors_status) {
		this.com_directors_status = com_directors_status;
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
		builder.append("CompanydirectorDto [com_directors_name=");
		builder.append(com_directors_name);
		builder.append(", com_designation=");
		builder.append(com_designation);
		builder.append(", com_directors_mobile=");
		builder.append(com_directors_mobile);
		builder.append(", com_directors_email=");
		builder.append(com_directors_email);
		builder.append(", com_directors_status=");
		builder.append(com_directors_status);
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
