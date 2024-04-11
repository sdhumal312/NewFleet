package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "company")
public class Company implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id")
	private Integer company_id;

	@Column(name = "company_name", nullable = false, unique = true, length = 250)
	private String company_name;

	@Column(name = "company_address", length = 200)
	private String company_address;

	@Column(name = "company_city", length = 100)
	private String company_city;

	@Column(name = "company_state", length = 100)
	private String company_state;

	@Column(name = "company_country", length = 100)
	private String company_country;

	@Column(name = "company_pincode", length = 6)
	private String company_pincode;

	@Column(name = "company_website", length = 150)
	private String company_website;

	@Column(name = "company_email", length = 150)
	private String company_email;

	@Column(name = "company_mobilenumber", length = 15)
	private String company_mobilenumber;

	@Column(name = "company_type", length = 200)
	private String company_type;

	@Column(name = "company_pan_no", length = 50)
	private String company_pan_no;

	@Column(name = "company_tan_no", length = 50)
	private String company_tan_no;

	@Column(name = "company_tax_no", length = 50)
	private String company_tax_no;

	@Column(name = "company_tin_no", length = 50)
	private String company_tin_no;

	@Column(name = "company_cin_no", length = 50)
	private String company_cin_no;

	@Column(name = "company_abount", length = 500)
	private String company_abount;

	@Column(name = "company_parentName", length = 250)
	private String company_parentName;

	@Column(name = "company_parent_id")
	private Integer company_parent_id;

	@Column(name = "company_esi_pf_days")
	private Long company_esi_pf_days;
	
	@Column(name = "company_esi_pf_disable")
	private Integer company_esi_pf_disable;

	@Column(name = "company_status", length = 50)
	private String company_status;
	
	@Column(name = "company_email_prefix", length = 50)
	private String company_email_prefix;
	
	@Column(name = "companyCode", nullable = false)
	private String companyCode;

	@Column(name = "createdBy", length = 100)
	private String createdBy;

	@Column(name = "lastModifiedBy", length = 100)
	private String lastModifiedBy;

	@Column(name = "markForDelete", nullable = false)
	@JsonIgnore
	private boolean markForDelete;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@Column(name = "lastupdated", nullable = false, updatable = false)
	private Date lastupdated;

	public Company() {
		super();
	}

	public Company(String company_name, String company_address, String company_city, String company_state,
			String company_country, String company_pincode, String company_website, String company_email,
			String company_mobilenumber, String company_type, String company_pan_no, String company_tan_no,
			String company_tax_no, String company_tin_no, String company_cin_no, String company_abount,
			String company_parentName, Integer company_parent_id, String company_status, String createdBy,
			String lastModifiedBy, Date created, Date lastupdated) {
		super();
		this.company_name = company_name;
		this.company_address = company_address;
		this.company_city = company_city;
		this.company_state = company_state;
		this.company_country = company_country;
		this.company_pincode = company_pincode;
		this.company_website = company_website;
		this.company_email = company_email;
		this.company_mobilenumber = company_mobilenumber;
		this.company_type = company_type;
		this.company_pan_no = company_pan_no;
		this.company_tan_no = company_tan_no;
		this.company_tax_no = company_tax_no;
		this.company_tin_no = company_tin_no;
		this.company_cin_no = company_cin_no;
		this.company_abount = company_abount;
		this.company_parentName = company_parentName;
		this.company_parent_id = company_parent_id;
		this.company_status = company_status;
		this.createdBy = createdBy;
		this.lastModifiedBy = lastModifiedBy;
		this.created = created;
		this.lastupdated = lastupdated;
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

	public String getCompany_address() {
		return company_address;
	}

	public void setCompany_address(String company_address) {
		this.company_address = company_address;
	}

	public String getCompany_city() {
		return company_city;
	}

	public void setCompany_city(String company_city) {
		this.company_city = company_city;
	}

	public String getCompany_state() {
		return company_state;
	}

	public void setCompany_state(String company_state) {
		this.company_state = company_state;
	}

	public String getCompany_country() {
		return company_country;
	}

	public void setCompany_country(String company_country) {
		this.company_country = company_country;
	}

	public String getCompany_pincode() {
		return company_pincode;
	}

	public void setCompany_pincode(String company_pincode) {
		this.company_pincode = company_pincode;
	}

	public String getCompany_website() {
		return company_website;
	}

	public void setCompany_website(String company_website) {
		this.company_website = company_website;
	}

	public String getCompany_email() {
		return company_email;
	}

	public void setCompany_email(String company_email) {
		this.company_email = company_email;
	}

	public String getCompany_mobilenumber() {
		return company_mobilenumber;
	}

	public void setCompany_mobilenumber(String company_mobilenumber) {
		this.company_mobilenumber = company_mobilenumber;
	}

	public String getCompany_type() {
		return company_type;
	}

	public void setCompany_type(String company_type) {
		this.company_type = company_type;
	}

	public String getCompany_pan_no() {
		return company_pan_no;
	}

	public void setCompany_pan_no(String company_pan_no) {
		this.company_pan_no = company_pan_no;
	}

	public String getCompany_tan_no() {
		return company_tan_no;
	}

	public void setCompany_tan_no(String company_tan_no) {
		this.company_tan_no = company_tan_no;
	}

	public String getCompany_tax_no() {
		return company_tax_no;
	}

	public void setCompany_tax_no(String company_tax_no) {
		this.company_tax_no = company_tax_no;
	}

	public String getCompany_tin_no() {
		return company_tin_no;
	}

	public void setCompany_tin_no(String company_tin_no) {
		this.company_tin_no = company_tin_no;
	}

	public String getCompany_cin_no() {
		return company_cin_no;
	}

	public void setCompany_cin_no(String company_cin_no) {
		this.company_cin_no = company_cin_no;
	}

	public String getCompany_abount() {
		return company_abount;
	}

	public void setCompany_abount(String company_abount) {
		this.company_abount = company_abount;
	}

	public String getCompany_parentName() {
		return company_parentName;
	}

	public void setCompany_parentName(String company_parentName) {
		this.company_parentName = company_parentName;
	}

	public Integer getCompany_parent_id() {
		return company_parent_id;
	}

	public void setCompany_parent_id(Integer company_parent_id) {
		this.company_parent_id = company_parent_id;
	}

	public String getCompany_status() {
		return company_status;
	}

	public void setCompany_status(String company_status) {
		this.company_status = company_status;
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

	/**
	 * @return the company_esi_pf_days
	 */
	public Long getCompany_esi_pf_days() {
		return company_esi_pf_days;
	}

	/**
	 * @param company_esi_pf_days
	 *            the company_esi_pf_days to set
	 */
	public void setCompany_esi_pf_days(Long company_esi_pf_days) {
		this.company_esi_pf_days = company_esi_pf_days;
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
	

	public String getCompany_email_prefix() {
		return company_email_prefix;
	}

	public void setCompany_email_prefix(String company_email_prefix) {
		this.company_email_prefix = company_email_prefix;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company_name == null) ? 0 : company_name.hashCode());
		result = prime * result + ((company_status == null) ? 0 : company_status.hashCode());
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
		Company other = (Company) obj;
		if (company_name == null) {
			if (other.company_name != null)
				return false;
		} else if (!company_name.equals(other.company_name))
			return false;
		if (company_status == null) {
			if (other.company_status != null)
				return false;
		} else if (!company_status.equals(other.company_status))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Company [company_id=");
		builder.append(company_id);
		builder.append(", company_name=");
		builder.append(company_name);
		builder.append(", company_address=");
		builder.append(company_address);
		builder.append(", company_city=");
		builder.append(company_city);
		builder.append(", company_state=");
		builder.append(company_state);
		builder.append(", company_country=");
		builder.append(company_country);
		builder.append(", company_pincode=");
		builder.append(company_pincode);
		builder.append(", company_website=");
		builder.append(company_website);
		builder.append(", company_email=");
		builder.append(company_email);
		builder.append(", company_mobilenumber=");
		builder.append(company_mobilenumber);
		builder.append(", company_type=");
		builder.append(company_type);
		builder.append(", company_pan_no=");
		builder.append(company_pan_no);
		builder.append(", company_tan_no=");
		builder.append(company_tan_no);
		builder.append(", company_tax_no=");
		builder.append(company_tax_no);
		builder.append(", company_tin_no=");
		builder.append(company_tin_no);
		builder.append(", company_cin_no=");
		builder.append(company_cin_no);
		builder.append(", company_abount=");
		builder.append(company_abount);
		builder.append(", company_parentName=");
		builder.append(company_parentName);
		builder.append(", company_parent_id=");
		builder.append(company_parent_id);
		builder.append(", company_esi_pf_days=");
		builder.append(company_esi_pf_days);
		builder.append(", company_status=");
		builder.append(company_status);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", companyCode=");
		builder.append(companyCode);
		builder.append("]");
		return builder.toString();
	}

}
