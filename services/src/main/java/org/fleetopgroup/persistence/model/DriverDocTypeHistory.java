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
@Table(name="DriverDocTypeHistory")
public class DriverDocTypeHistory implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="DriverDocTypeHistoryId")
	private Long DriverDocTypeHistoryId;

	@Column(name="dri_id")
	private Long dri_id;
	
	@Column(name="dri_DocType", unique = false, nullable = false)
	private String dri_DocType;
	
	@Column(name= "company_Id", nullable = false)
	private Integer company_Id;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "lastModifiedById", nullable = true)
	private Long lastModifiedById;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public Long getDriverDocTypeHistoryId() {
		return DriverDocTypeHistoryId;
	}

	public void setDriverDocTypeHistoryId(Long driverDocTypeHistoryId) {
		DriverDocTypeHistoryId = driverDocTypeHistoryId;
	}

	public Long getDri_id() {
		return dri_id;
	}

	public void setDri_id(Long dri_id) {
		this.dri_id = dri_id;
	}

	public String getDri_DocType() {
		return dri_DocType;
	}

	public void setDri_DocType(String dri_DocType) {
		this.dri_DocType = dri_DocType;
	}

	public Integer getCompany_Id() {
		return company_Id;
	}

	public void setCompany_Id(Integer company_Id) {
		this.company_Id = company_Id;
	}

	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}	
}