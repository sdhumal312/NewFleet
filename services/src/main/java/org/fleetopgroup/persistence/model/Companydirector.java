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
@Table(name = "companydirector")
public class Companydirector implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "com_directors_id")
	private Integer com_directors_id;

	@Column(name = "com_directors_name", length = 150)
	private String com_directors_name;

	@Column(name = "com_designation", length = 150)
	private String com_designation;

	@Column(name = "com_directors_mobile", length = 15)
	private String com_directors_mobile;

	@Column(name = "com_directors_email", length = 50)
	private String com_directors_email;
	
	@Column(name = "com_directors_status", length = 50)
	private String com_directors_status;

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


	public Companydirector() {
		super();
	}

	public Companydirector(String com_directors_name, String com_designation, String com_directors_mobile,
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
		result = prime * result + ((com_directors_name == null) ? 0 : com_directors_name.hashCode());
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
		Companydirector other = (Companydirector) obj;
		if (com_directors_name == null) {
			if (other.com_directors_name != null)
				return false;
		} else if (!com_directors_name.equals(other.com_directors_name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Companydirector [com_directors_name=");
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
