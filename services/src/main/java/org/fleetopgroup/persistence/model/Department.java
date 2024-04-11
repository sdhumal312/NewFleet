package org.fleetopgroup.persistence.model;
/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="department")
public class Department implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="depart_id")
	private Integer depart_id;
	
	@Column(name="depart_name", unique = false, nullable = false, length=150)
	private String depart_name;
	
	@Column(name="depart_code", length=10)
	private String depart_code;
	
	@Column(name="depart_hod", length=100)
	private String depart_hod;
	
	@Column(name="depart_description", length=200)
	private String depart_description;
	
	@Column(name = "company_id")
	private Integer company_id;

	@Column(name = "company_name", length = 250)
	@Deprecated
	private String company_name;
	
	@Column(name = "createdBy", nullable = true)
	private String createdBy;

	@Column(name = "createdById", nullable = true)
	private Long createdById;
	
	@Column(name = "createdOn", nullable = true)
	private Timestamp createdOn;
	
	@Column(name = "lastModifiedBy", nullable = true)
	private String lastModifiedBy;

	@Column(name = "lastModifiedById", nullable = true)
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	

	public Department() {
		super();
	}


	public Department(String depart_name, String depart_code, String depart_hod, String depart_description, Integer company_id, String createdBy, Long createdById, Timestamp createdOn, String lastModifiedBy, Long lastModifiedById, Timestamp lastModifiedOn) {
		super();
		this.depart_name = depart_name;
		this.depart_code = depart_code;
		this.depart_hod = depart_hod;
		this.depart_description = depart_description;
		this.company_id = company_id;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdOn = createdOn;
		this.lastModifiedOn = lastModifiedOn;
		this.lastModifiedById = lastModifiedById;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((depart_code == null) ? 0 : depart_code.hashCode());
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
		Department other = (Department) obj;
		
		if (depart_code == null) {
			if (other.depart_code != null)
				return false;
		} else if (!depart_code.equals(other.depart_code))
			return false;
		return true;
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
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", lastModifiedOn=");
		builder.append(lastModifiedOn);
		builder.append("]");
		return builder.toString();
	}

	
	
}