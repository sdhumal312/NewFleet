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
@Table(name = "companybank")
public class Companybank implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "com_bank_id")
	private Integer com_bank_id;

	@Column(name = "com_bank_name", length = 200)
	private String com_bank_name;

	@Column(name = "com_bank_account", length = 30)
	private String com_bank_account;

	@Column(name = "com_bank_actype", length = 100)
	private String com_bank_actype;

	@Column(name = "com_bank_ifsc", length = 30)
	private String com_bank_ifsc;

	@Column(name = "com_bank_bsr", length = 30)
	private String com_bank_bsr;

	@Column(name = "com_bank_branch", length = 200)
	private String com_bank_branch;

	@Column(name = "com_bank_address", length = 150)
	private String com_bank_address;

	@Column(name = "com_bank_city", length = 150)
	private String com_bank_city;

	@Column(name = "com_bank_state", length = 100)
	private String com_bank_state;

	@Column(name = "com_bank_country", length = 100)
	private String com_bank_country;

	@Column(name = "com_bank_pincode", length = 6)
	private String com_bank_pincode;

	@Column(name = "com_bank_status", length = 50)
	private String com_bank_status;

	@Column(name = "company_id")
	private Integer company_id;
	
	@Column(name = "createdBy", length = 100)
	private String createdBy;

	@Column(name = "lastModifiedBy", length = 100)
	private String lastModifiedBy;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@Column(name = "lastupdated", nullable = false, updatable = false)
	private Date lastupdated;


	public Companybank() {
		super();
	}

	public Companybank(String com_bank_name, String com_bank_account, String com_bank_actype, String com_bank_ifsc,
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((com_bank_account == null) ? 0 : com_bank_account.hashCode());
		result = prime * result + ((com_bank_name == null) ? 0 : com_bank_name.hashCode());
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
		Companybank other = (Companybank) obj;
		if (com_bank_account == null) {
			if (other.com_bank_account != null)
				return false;
		} else if (!com_bank_account.equals(other.com_bank_account))
			return false;
		if (com_bank_name == null) {
			if (other.com_bank_name != null)
				return false;
		} else if (!com_bank_name.equals(other.com_bank_name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Companybank [com_bank_name=");
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
