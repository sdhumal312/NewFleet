package org.fleetopgroup.persistence.model;
/**
 * @author fleetop
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
@Table(name="vehicledoctypehistory")
public class VehicleDocTypeHistory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="vehicle_doc_type_history_id")
	private Long vehicle_doc_type_history_id;
	
	@Column(name="dtid")
	private Long dtid;

	@Column(name="vDocType", unique = false, nullable = false,length=25)
	private String vDocType;
	
	@Column(name = "company_Id", nullable = false)
	Integer	company_Id;
	
	@Column(name = "lastModifiedById", nullable = true)
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	public VehicleDocTypeHistory() {
		super();
	}

	public VehicleDocTypeHistory(String vDocType) {
		super();
		this.vDocType = vDocType;
	}

	public Long getVehicle_doc_type_history_id() {
		return vehicle_doc_type_history_id;
	}

	public void setVehicle_doc_type_history_id(Long vehicle_doc_type_history_id) {
		this.vehicle_doc_type_history_id = vehicle_doc_type_history_id;
	}

	public Long getDtid() {
		return dtid;
	}

	public void setDtid(Long dtid) {
		this.dtid = dtid;
	}

	public String getvDocType() {
		return vDocType;
	}

	public void setvDocType(String vDocType) {
		this.vDocType = vDocType;
	}

	public Integer getCompany_Id() {
		return company_Id;
	}

	public void setCompany_Id(Integer company_Id) {
		this.company_Id = company_Id;
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