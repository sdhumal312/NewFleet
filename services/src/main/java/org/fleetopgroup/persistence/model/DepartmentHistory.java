package org.fleetopgroup.persistence.model;
/**
 * @author Ashish Tiwari
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
@Table(name="departmenthistory")
public class DepartmentHistory implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="depart_history_id")
	private Integer depart_history_id;
	
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
		
	@Column(name = "lastModifiedById", nullable = true)
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	

	public DepartmentHistory() {
		super();
	}


	public Integer getDepart_history_id() {
		return depart_history_id;
	}


	public void setDepart_history_id(Integer depart_history_id) {
		this.depart_history_id = depart_history_id;
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


	public Long getLastModifiedById() {
		return lastModifiedById;
	}


	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
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
}