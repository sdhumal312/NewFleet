package org.fleetopgroup.persistence.dto;

public class DepartmentDto {
	private Integer depart_id;

	private String depart_name;

	private String depart_code;

	private String depart_hod;

	private String depart_description;

	private Integer company_id;
	
	private String company_id_encode;

	private String company_name;

	public DepartmentDto() {
		super();
	}

	public Integer getDepart_id() {
		return depart_id;
	}

	public void setDepart_id(Integer depart_id) {
		this.depart_id = depart_id;
	}

	public String getDepart_name() {
		return depart_name;
	}

	public void setDepart_name(String depart_name) {
		this.depart_name = depart_name;
	}

	public String getDepart_code() {
		return depart_code;
	}

	public void setDepart_code(String depart_code) {
		this.depart_code = depart_code;
	}

	public String getDepart_hod() {
		return depart_hod;
	}

	public void setDepart_hod(String depart_hod) {
		this.depart_hod = depart_hod;
	}

	public String getDepart_description() {
		return depart_description;
	}

	public void setDepart_description(String depart_description) {
		this.depart_description = depart_description;
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
	

	public String getCompany_id_encode() {
		return company_id_encode;
	}

	public void setCompany_id_encode(String company_id_encode) {
		this.company_id_encode = company_id_encode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Department [depart_name=");
		builder.append(depart_name);
		builder.append(", depart_code=");
		builder.append(depart_code);
		builder.append(", depart_hod=");
		builder.append(depart_hod);
		builder.append(", depart_description=");
		builder.append(depart_description);
		builder.append(", company_id=");
		builder.append(company_id);
		builder.append(", company_name=");
		builder.append(company_name);
		builder.append("]");
		return builder.toString();
	}

}